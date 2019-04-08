package com.spider.policy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author joel
 */

@SpringBootApplication
@EnableScheduling
@MapperScan("com.spider.policy.mapper")
public class PolicyApplication {
    public static void main(String[] args) {
        System.setProperty("javax.xml.transform.TransformerFactory","com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl");
        SpringApplication.run(PolicyApplication.class, args);
    }
}

