package com.hq;

import static com.hq.common.enums.EnvEnum.notProd;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 高振中
 * @date 2024-05-10 21:44:15
 **/
@Slf4j
//@EnableAsync
//@EnableScheduling
@SpringBootApplication
public class ApplicationServer {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(ApplicationServer.class, args);
    }

    @PostConstruct
    public void init() throws UnknownHostException {
        if (notProd(env.getActiveProfiles())) {
            log.info("在线文档:{}", "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + env.getProperty("server.port") + "/swagger-ui/index.html");
        }
    }
}