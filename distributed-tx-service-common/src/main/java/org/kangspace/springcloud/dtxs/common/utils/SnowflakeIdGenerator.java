package org.kangspace.springcloud.dtxs.common.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * Twitter Snowflake
 * [img](https://image-static.segmentfault.com/350/263/350263808-59c2254083397_articlex)
 * SnowFlake算法生成id的结果是一个64bit大小的整数：
 * + (1位)最高位是符号位，二进制中最高位为1的都是负数，但是我们生成的id一般都使用整数，所以这个最高位固定是0，不可用。
 * + 41位的时间序列，记录时间戳,精确到毫秒级，
 * 41位可以表示$2^{41}-1$个数字，
 * 如果只用来表示正整数（计算机中正数包含0），可以表示的数值范围是：0 至 $2^{41}-1$，减1是因为可表示的数值范围是从0开始算的，而不是1。
 * 也就是说41位可以表示$2^{41}-1$个毫秒的值，转化成单位年则是$(2^{41}-1) / (1000 * 60 * 60 * 24 * 365) = 69$年
 * + 10位的机器标识，10位的长度最多支持部署1024个节点
 * 可以部署在$2^{10} = 1024$个节点，包括5位datacenterId和5位workerId
 * 5位（bit）可以表示的最大正整数是$2^{5}-1 = 31$，即可以用0、1、2、3、....31这32个数字，来表示不同的datecenterId或workerId
 * + 12位的计数序列号，序列号即一系列的自增id，可以支持同一节点同一毫秒生成多个ID序号，12位的计数序列号支持每个节点每毫秒产生4096个ID序号:
 * 12位（bit）可以表示的最大正整数是$2^{12}-1 = 4095$，即可以用0、1、2、3、....4094这4095个数字，来表示同一机器同一时间截（毫秒)内产生的4095个ID序号
 * <p>
 * 其中，10位器标识符一般是5位IDC+5位machine编号，唯一确定一台机器。
 * SnowFlake可以保证：
 * <p>
 * 所有生成的id按时间趋势递增
 * 整个分布式系统内不会产生重复id（因为有datacenterId和workerId来做区分）
 *
 *
 * 实现:
 *      默认使用hostname%32 作为 datacenterId
 *      默认使用ip%32 作为 workId
 * 2019/5/9 10:08
 *
 * @author kango2ger@gmail.com
 * @TODO
 * @see <a href="https://segmentfault.com/a/1190000011282426">https://segmentfault.com/a/1190000011282426</a>
 */
public class SnowflakeIdGenerator {
    /**
     * 5位机器id (0-31)
     */
    private static Long workerIdBits = 5L;
    /**
     * 5位数据中心id (0-31)
     */
    private static Long datacenterIdBits = 5L;
    /**
     * 最大机器id,掩码
     */
    private static Long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 最大数据中心Id,掩码
     */
    private static Long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /**
     * 12位序列数据长度
     */
    private static Long sequenceBits = 12L;
    /**
     * 41位序列时间戳长度
     */
    private static Long timestampBits = 41L;

    /**
     * workId偏移量
     */
    private static Long workerIdShift = sequenceBits;

    /**
     * datacenterId偏移量
     */
    private static Long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间戳偏移量
     */
    private static Long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /**
     * 序列掩码,最大序列值
     */
    private static Long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 时间戳掩码2的41次方减1
     */
    public static Long timestampMask = -1L ^ (-1L << timestampBits);

    /**
     * 实现时的时间戳
     */
    public static Long twepoch = 1558065942761L;

    public Long lastTimestamp = -1L;

    /**
     * workId(0-31)
     */
    private long workerId;
    /**
     * datacenterId(0-31)
     */
    private long datacenterId;
    /**
     * 每个节点每毫秒(0~4095)
     */
    private long sequence;

    private SnowflakeIdGenerator() {
        this.workerId = getWorkId();
        this.datacenterId = getDataCenterId();
    }

    private SnowflakeIdGenerator(long workerId, long datacenterId, long sequence) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        //System.out.println("timestamp:"+timestamp+",sequence:"+sequence+",workerId:"+workerId+",datacenterId:"+datacenterId+",timestamp:"+timestamp);
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    private Long timeGen() {
        return System.currentTimeMillis();
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    public static Long getWorkId(){
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int sums = 0;
            for(String b : hostAddress.split(".")){
                sums += Integer.valueOf(b);
            }
            return (long)(sums % 32);
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return Long.valueOf(new Random().nextInt(32));

        }
    }

    public static Long getDataCenterId(){
        try {
            String hostname = Inet4Address.getLocalHost().getHostName();
            int sums = 0;
            for (char b : hostname.toCharArray()) {
                sums += Integer.valueOf(b);
            }
            return (long) (sums % 32);
        }catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return Long.valueOf(new Random().nextInt(32));
         }
    }


    private static SnowflakeIdGenerator idGenerator;

    public static SnowflakeIdGenerator getInstance() {
        synchronized (SnowflakeIdGenerator.class) {
            if (idGenerator == null) {
                idGenerator = new SnowflakeIdGenerator();
            }
            return idGenerator;
        }
    }

    public static SnowflakeIdGenerator getInstance(Long datacenterId,Long workerId) {
        synchronized (SnowflakeIdGenerator.class) {
            if (idGenerator == null) {
                idGenerator = new SnowflakeIdGenerator(datacenterId,workerId,0L);
            }
            return idGenerator;
        }
    }

    public static SnowflakeIdGenerator.ID getID(long id){
        return new ID(
                id & sequenceMask,
                (id >>> workerIdShift) & maxWorkerId ,
                (id >>> datacenterIdShift) & maxDatacenterId,
                ((id >>> timestampLeftShift) & timestampMask)+twepoch
        );
    }

    public static class ID{
        /**
         * workId(0-31)
         */
        private long workerId;
        /**
         * datacenterId(0-31)
         */
        private long datacenterId;
        /**
         * 每个节点每毫秒(0~4095)
         */
        private long sequence;

        private long timestemp;

        public long getWorkerId() {
            return workerId;
        }

        public void setWorkerId(long workerId) {
            this.workerId = workerId;
        }

        public long getDatacenterId() {
            return datacenterId;
        }

        public void setDatacenterId(long datacenterId) {
            this.datacenterId = datacenterId;
        }

        public long getSequence() {
            return sequence;
        }

        public void setSequence(long sequence) {
            this.sequence = sequence;
        }

        public long getTimestemp() {
            return timestemp;
        }

        public void setTimestemp(long timestamp) {
            this.timestemp = timestamp;
        }

        public ID() {
        }

        public ID(long sequence, long workerId, long datacenterId, long timestemp) {
            this.workerId = workerId;
            this.datacenterId = datacenterId;
            this.sequence = sequence;
            this.timestemp = timestemp;
        }

        @Override
        public String toString() {
            return "ID{" +
                    "workerId=" + workerId +
                    ", datacenterId=" + datacenterId +
                    ", sequence=" + sequence +
                    ", timestemp=" + timestemp +
                    '}';
        }
    }

    public static void main(String[] args) throws UnknownHostException {
//        SnowflakeIdGenerator generator = new SnowflakeIdGenerator();
//        System.out.println(generator.nextId());
//        System.out.println(generator.nextId());
//        long id1 = new SnowflakeIdGenerator().nextId();
//        System.out.println(id1);
        System.out.println("workId:"+getWorkId());
        System.out.println("datacenterId:"+getDataCenterId());
        long id2 = SnowflakeIdGenerator.getInstance().nextId();
        System.out.println(id2);
        System.out.println(SnowflakeIdGenerator.getID(id2));
//        long id3 = SnowflakeIdGenerator.getInstance(1L,1L).nextId();
//        System.out.println(id3);
        long id4 = SnowflakeIdGenerator.getInstance().nextId();
        System.out.println(id4);
        System.out.println(SnowflakeIdGenerator.getID(id4));
        long id5 = SnowflakeIdGenerator.getInstance().nextId();
        System.out.println(id5);
        System.out.println(SnowflakeIdGenerator.getID(id5));
//        int i = 0;
//        while (i<100000) {
//            System.out.println(new Random().nextInt(32));
//            i++;
//        }
        System.out.println(System.getenv("hostname"));
        System.out.println(InetAddress.getLocalHost().getHostName());
        System.out.println(Long.toBinaryString(System.currentTimeMillis()));
    }
}
