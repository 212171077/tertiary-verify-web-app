package com.pc.common;

import com.pc.mail.MailSender;
import com.pc.service.InstitutionService;
import com.pc.service.lookup.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {
    /**
     * https://www.callicoder.com/spring-boot-task-scheduling-with-scheduled-annotation/
     * https://dzone.com/articles/running-on-time-with-springs-scheduled-tasks
     **/

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private InstitutionService InstitutionService;

    @Autowired
    private MailSender mailSender;

    /**
     * @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
     * --------------------------------------------------------------------------------------------
     * @Scheduled(cron = "0 * * * * ?")=Task will be executed every minute
     * @Scheduled(cron = "0 0 12 * * ?")=Fires at 12 PM every day
     * @Scheduled(cron = "0 15 10 * * ? 2005")=Fires at 10:15 AM every day in the year 2005
     * @Scheduled(cron = "0/20 * * * * ?")=Fires every 20 seconds
     * --------------------------------------------------------------------------------------------
     */

    @Scheduled(cron = "0 0 12 * * ?")
    public void scheduleTaskWithCronExpression() {

        InstitutionService.updateExpiredIntitution();
    }

    @Scheduled(fixedDelay = 2000)
    public void scheduleTaskWithFixedDelay() {
        mailSender.sendEmailContent();
    }


    /**Method will be executed
     * every 2 seconds.*/
   /* @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
    	logger.info("Fixed Rate Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()) );
    }*/

    /**The fixedDelay parameter 
     * counts the delay after 
     * the completion of the 
     * last invocation.**/
    
   /* @Scheduled(fixedDelay = 2000)
    public void scheduleTaskWithFixedDelay() {
    	logger.info("Fixed Delay Task :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            logger.error("Ran into an error {}", ex);
            throw new IllegalStateException(ex);
        }
    }*/

    /**The first execution of the task 
     * will be delayed by 5 seconds 
     * and then it will be executed 
     * normally at a fixed interval of 2 seconds */
    
    /*@Scheduled(fixedRate = 2000, initialDelay = 5000)
    public void scheduleTaskWithInitialDelay() {
        logger.info("Fixed Rate Task with Initial Delay :: Execution Time - {}", dateTimeFormatter.format(LocalDateTime.now()));
    }*/


}