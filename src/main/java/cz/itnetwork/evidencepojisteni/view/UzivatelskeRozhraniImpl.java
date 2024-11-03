/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.view;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.view.enums.ZpravyOVysledkuOperaceEnum;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Uživatelské rozhrani
 *
 * @author Pavel
 */
@Component
public class UzivatelskeRozhraniImpl implements UzivatelskeRozhrani {

    private final Scanner scanner;
    private final String oddelovac = "------------------------------------------------";
    private final String oddelovacln = oddelovac + System.lineSeparator();

    /**
     * Konstruktor
     *
     * @param scanner Scanner
     */
    public UzivatelskeRozhraniImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void vykresliUvodniObrazovku() {
        System.out.println(oddelovac);
        System.out.println("*** E V I D E N C E  P O J I Š T Ě N Ý C H ***");
        System.out.println(oddelovacln);
    }

    @Override
    public void vypisUvodniNabidku() {
        System.out.println("Zvolte si z následující nabídky (zadejte číslo volby a stiskněte enter):");
        System.out.println("1 - Vypsat všechny pojištěnce");
        System.out.println("2 - Vyhledat pojištěnce");
        System.out.println("3 - Přidat pojištěnce");
        System.out.println("4 - Konec");
    }

    @Override
    public void vypisPojistence(List<PojistenecDTO> pojistenci) {
        if (!pojistenci.isEmpty()) {
            for (PojistenecDTO pojistenec : pojistenci) {
                System.out.println(pojistenec);
            }
            System.out.println(oddelovacln);
        } else {
            vypisZpravu(ZpravyOVysledkuOperaceEnum.ITEMS_NOT_FOUND.message);
        }
    }

    @Override
    public void vypisNabidkuPraceSPojistencem() {
        System.out.println("1 - Upravit pojištěnce");
        System.out.println("2 - Odstranit pojištěnce");
        System.out.println("3 - Návrat do hlavní nabídky");
    }

    @Override
    public void vypisText(String text) {
        System.out.printf(text);
    }

    @Override
    public void vypisZpravu(String zprava) {
        System.out.println(oddelovac);
        System.out.println(zprava);
        System.out.println(oddelovacln);
    }

    @Override
    public void vypisChybovouHlasku(Exception ex) {
        System.out.println(ex.getMessage());
    }

    @Override
    public String ziskejVstup() {
        return scanner.nextLine();
    }

    @Override
    public void vyzviKOpakovaniZadani() {
        System.out.print("Opakujte prosím zadání: ");
    }
}
