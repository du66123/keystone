package com.keystone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.mybatis.spring.annotation.MapperScan;

/**
 * Keystone 脚手架启动类
 */
@SpringBootApplication
@MapperScan("com.keystone.**.mapper")
public class KeystoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeystoneApplication.class, args);
        System.out.println("""
                
                  _  __          _                   
                 | |/ /         | |                  
                 | ' / ___ _   _| |_ ___  _ __   ___ 
                 |  < / _ \\ | | / __/ _ \\| '_ \\ / _ \\
                 | . \\  __/ |_| \\__ \\  __/| | | |  __/
                 |_|\\_\\___|\\__, |___/\\___||_| |_|\\___|
                            __/ |                     
                           |___/                      
                
                :: Keystone 基础脚手架 ::    (v1.0.0-SNAPSHOT)
                :: Spring Boot 4.0  ::    Powered by Java 21+
                
                启动成功！访问接口文档: http://localhost:8080/swagger-ui/index.html
                """);
    }
}
