package com.zemoso.ztalent;

import com.zemoso.ztalent.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties(AppProperties.class)
public class ZtalentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZtalentApplication.class, args);
	}
}
