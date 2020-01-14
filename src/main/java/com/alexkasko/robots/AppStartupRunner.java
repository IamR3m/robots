package com.alexkasko.robots;

import com.alexkasko.robots.service.ActivityTrackerRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync(proxyTargetClass=true)
public class AppStartupRunner implements ApplicationRunner {

    private final ActivityTrackerRunner activityTrackerRunner;

    public AppStartupRunner(ActivityTrackerRunner activityTrackerRunner) {
        this.activityTrackerRunner = activityTrackerRunner;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Bean
    public CommandLineRunner schedulingRunner(@Qualifier("taskExecutor") TaskExecutor executor) {
        return args -> executor.execute(activityTrackerRunner);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
    }
}