/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cz.itnetwork.evidencepojisteni;

import cz.itnetwork.evidencepojisteni.controller.InsuredController;
import cz.itnetwork.evidencepojisteni.exception.handler.UncaughtExceptionHandlerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Pavel Šach
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(Main.class, args);
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandlerImpl());
        InsuredController insuredController = appContext.getBean(InsuredController.class);
        insuredController.run();
    }
}
