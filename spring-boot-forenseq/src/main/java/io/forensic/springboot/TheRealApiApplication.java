package io.forensic.springboot;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import io.forensic.springboot.property.FileStorageProperties;

@EnableConfigurationProperties({
    FileStorageProperties.class
})
@EntityScan(basePackageClasses = {
		TheRealApiApplication.class,
		Jsr310JpaConverters.class
})

@SpringBootApplication
public class TheRealApiApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TheRealApiApplication.class, args);
	}

}

