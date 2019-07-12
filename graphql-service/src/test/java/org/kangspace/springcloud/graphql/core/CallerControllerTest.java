package org.kangspace.springcloud.graphql.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kangspace.springcloud.graphql.core.controller.RESTfulController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 2019/5/17 10:05
 *
 * @author kango2ger@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CallerControllerTest {
    @Autowired
    RESTfulController callerController;

    @Test
    public void testCallers(){
        System.out.println(callerController.callers());
    }
}
