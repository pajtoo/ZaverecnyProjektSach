/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cz.itnetwork.evidencepojisteni;

import cz.itnetwork.evidencepojisteni.controller.InsuredController;
import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.exception.MyUncaughtExceptionHandler;
import cz.itnetwork.evidencepojisteni.mapping.InputDTOMapper;
import cz.itnetwork.evidencepojisteni.mapping.MappingDataProvider;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenych;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenychImpl;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhraniImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

/**
 *
 * @author Pavel Šach
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext appContext = SpringApplication.run(Main.class, args);
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        InsuredController insuredController = appContext.getBean(InsuredController.class);
        insuredController.run();
    }
}
