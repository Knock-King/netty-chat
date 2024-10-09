package com.king;

import com.king.config.NettyServer;
import com.king.utils.SpringBeanUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.king.mapper")
public class NettyChatApplication implements CommandLineRunner {
    @Autowired
    private NettyServer nettyServer;

    @Bean
    public SpringBeanUtil getSpringBeanUtil() {
        return new SpringBeanUtil();
    }

    public static void main(String[] args) {
        SpringApplication.run(NettyChatApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.nettyServer.start();
    }
}
