package com.cidca.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    
	//3.添加定时任务
	/*
	 * @Scheduled(cron = "0/5 * * * * ?") private void configureTasks() {
	 * System.err.println("****定时任务执行****"); }
	 */
    
}