package com.bank.user.application.openfeign;

import feign.RetryableException;
import feign.Retryer;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@Data
public class CustomRetryer extends Retryer.Default {
    
    private static Logger log = LoggerFactory.getLogger(CustomRetryer.class);

    public  CustomRetryer() {
        super();
    }
    public CustomRetryer(long period, long maxPeriod, int maxAttempts){
        super(period, maxPeriod, maxAttempts);
    }

    @Override
    public void continueOrPropagate(RetryableException e){
        log.info("Going to retry for ", e);
        super.continueOrPropagate(e);
    }

    @Override
    public Retryer clone(){
        return new CustomRetryer(5, TimeUnit.SECONDS.toMillis(3L), 5);
    }
}
