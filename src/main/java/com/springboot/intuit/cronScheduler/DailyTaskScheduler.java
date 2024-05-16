package com.springboot.intuit.cronScheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.springboot.intuit.service.ExchangeInfoService;

@Component
@EnableScheduling
public class DailyTaskScheduler {
    private ExchangeInfoService exchangeInfoService;

    public DailyTaskScheduler(ExchangeInfoService exchangeInfoService){
        this.exchangeInfoService= exchangeInfoService;
    }

    @Scheduled(cron = "0 0 6 * * *") // cron expression for 6:00 AM every day
    public void runDailyTask() {
        exchangeInfoService.notifyUsersForReturns();
        System.out.println("Running daily task at 6 am in the morning...");
    }
}

