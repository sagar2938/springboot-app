package com.assessment.vmware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication
@EnableAsync
public class VmwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(VmwareApplication.class, args);
	}

	@Bean
	public Executor taskExecutor(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        return executorService;
    }

}
