package org.kangspace.springcloud.dtxs.two.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "op_table_one")
public class OpTableOne {
    @Id
    private Long id;

    /**
     * 操作id-雪花算法id
     */
    @Column(name = "op_id")
    private String opId;

    /**
     * 操作名称
     */
    @Column(name = "op_name")
    private String opName;

    /**
     * 操作描述
     */
    @Column(name = "op_desc")
    private String opDesc;

    /**
     * 创建时间
     */
    @Column(name = "c_time")
    private Date cTime;

    /**
     * 更新时间
     */
    @Column(name = "m_time")
    private Date mTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取操作id-雪花算法id
     *
     * @return op_id - 操作id-雪花算法id
     */
    public String getOpId() {
        return opId;
    }

    /**
     * 设置操作id-雪花算法id
     *
     * @param opId 操作id-雪花算法id
     */
    public void setOpId(String opId) {
        this.opId = opId == null ? null : opId.trim();
    }

    /**
     * 获取操作名称
     *
     * @return op_name - 操作名称
     */
    public String getOpName() {
        return opName;
    }

    /**
     * 设置操作名称
     *
     * @param opName 操作名称
     */
    public void setOpName(String opName) {
        this.opName = opName == null ? null : opName.trim();
    }

    /**
     * 获取操作描述
     *
     * @return op_desc - 操作描述
     */
    public String getOpDesc() {
        return opDesc;
    }

    /**
     * 设置操作描述
     *
     * @param opDesc 操作描述
     */
    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc == null ? null : opDesc.trim();
    }

    /**
     * 获取创建时间
     *
     * @return c_time - 创建时间
     */
    public Date getcTime() {
        return cTime;
    }

    /**
     * 设置创建时间
     *
     * @param cTime 创建时间
     */
    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    /**
     * 获取更新时间
     *
     * @return m_time - 更新时间
     */
    public Date getmTime() {
        return mTime;
    }

    /**
     * 设置更新时间
     *
     * @param mTime 更新时间
     */
    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }
}