/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cz.itnetwork.evidencepojisteni;

import cz.itnetwork.evidencepojisteni.controller.InsuredController;
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
        Scanner scanner = new Scanner(System.in, "UTF-8");
        ValidatorVstupu validator = new ValidatorVstupu();
        UzivatelskeRozhrani ui = new UzivatelskeRozhraniImpl(scanner);
        SpravcePojistenych spravcePojistenych = new SpravcePojistenychImpl();
        InsuredController insuredController = new InsuredController(ui, spravcePojistenych, validator);
        insuredController.run();
    }
}
