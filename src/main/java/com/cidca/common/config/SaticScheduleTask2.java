package com.cidca.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Configuration
//@EnableScheduling
@SuppressWarnings("unused")
public class SaticScheduleTask2 {
    
	
	  //@Scheduled(cron = "0/5 * * * * ?") 
	private void configureTasks() {
		  	System.err.println("****定时任务执行****");
	  }
	 
    
}