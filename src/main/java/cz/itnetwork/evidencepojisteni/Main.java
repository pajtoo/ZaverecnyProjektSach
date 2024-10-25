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

import java.util.Scanner;

/**
 *
 * @author Pavel Šach
 */
public class Main {

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        Scanner scanner = new Scanner(System.in, "UTF-8");
        UzivatelskeRozhrani ui = new UzivatelskeRozhraniImpl(scanner);

        SpravcePojistenych spravcePojistenych = new SpravcePojistenychImpl();
        ValidatorVstupu validator = new ValidatorVstupu();
        MappingDataProvider pojistenecMappingDataProvider = new MappingDataProvider(PojistenecDTO.class);
        InputDTOMapper<PojistenecDTO> insuredInputDTOMapper = new InputDTOMapper<>();

        InsuredController insuredController = new InsuredController(
                ui,
                spravcePojistenych,
                validator,
                pojistenecMappingDataProvider,
                insuredInputDTOMapper);
        insuredController.run();
    }
}
