package com.example.herokudemo;

import com.afweb.service.*;
import com.afweb.util.*;

import java.util.concurrent.TimeUnit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class HerokuDemoApplication {

    private static AFwebService afWebService = new AFwebService();
    private static RESTtimer restTimer = new RESTtimer();
    public static boolean webapp = true;

    public static void main(String[] args) {

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                String cmd = args[i];

                if (cmd.indexOf("javamain") != -1) {
                    webapp = false;
                    ServiceAFweb.javamainflag= true;
                    Javamain.javamain(args);
                }
                Javamain.checkParameterFlag(cmd);
            }
        }
        SpringApplication.run(HerokuDemoApplication.class, args);

    }
    public static int timerSchCnt = 0;
    public static boolean init = false;

    // just for testing to use 1 minute delay
    @Scheduled(fixedDelay = 10000) //60000) //2000)
    public void scheduleTaskWithFixedDelay() {

        timerSchCnt++;
        if (timerSchCnt < 0) {
            timerSchCnt = 100;
        }
//        if (webapp == true) {
//            Javamain.javamain(null);
//        }
        try {
            if (getEnv.checkLocalPC() == true) {
                restTimer.RestTimerHandler();
                return;
            }

            restTimer.RestTimerHandler();

            TimeUnit.SECONDS.sleep(1);

        } catch (Exception ex) {

        }
    }

}
