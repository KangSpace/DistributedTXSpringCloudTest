package org.kangspace.springcloud.dtxs.two.controller;

import org.kangspace.springcloud.dtxs.two.ServiceTwoApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 简单controller
 * 在{@link ServiceTwoApplication#main(String[])} 中启动}
 * 2017/10/11 14:03
 *
 * @author kango2ger@gmail.com
 */
@Controller
@EnableAutoConfiguration
public class SimpleController{
    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "Hello World!";
    }
}
