package com.zbl.zh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.zbl.zh.*.dao"})
public class ZhApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhApplication.class, args);
	}
}
