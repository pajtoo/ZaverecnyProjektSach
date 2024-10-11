/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.view;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.view.enums.ZpravyOVysledkuOperaceEnum;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Uživatelské rozhrani
 * @author Pavel
 */
public class UzivatelskeRozhraniImpl implements UzivatelskeRozhrani {

    private Scanner scanner;
    private String oddelovac = "------------------------------------------------";
    private String oddelovacln = oddelovac + System.lineSeparator();

    /**
     * Konstruktor
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
            System.out.println("4 - Upravit pojištěnce");
            System.out.println("5 - Odstranit pojištěnce");
            System.out.println("6 - Konec");
    }

    @Override
    public void vypisVsechnyPojistence(List<PojistenecDTO> pojistenci) {
        if (!pojistenci.isEmpty()) {
            System.out.println("----- Přehled pojištěných osob (ID: přijmení, jméno, věk, tel.) -----");

            for (PojistenecDTO pojistenec : pojistenci) {
                System.out.println(pojistenec);
            }
            System.out.println(oddelovacln);
        } else {
            vypisZpravu(ZpravyOVysledkuOperaceEnum.ITEMS_NOT_FOUND.message);
        }
    }

    @Override
    public void vypisVyhledanePojistence() {
        System.out.println("----- Vyhledávání pojištěných -----");
        System.out.println("Zadejte údaje o pojištěném");

        System.out.print("Křestní jméno: ");
        String jmeno = vstup.zvalidujJmenoPrijmeni(scanner.nextLine());
        System.out.print("Příjmení: ");
        String prijmeni = vstup.zvalidujJmenoPrijmeni(scanner.nextLine());

        List<PojistenecDTO> nalezeniPojistenci = spravcePojistenych.najdiPojistene(jmeno, prijmeni);
        if (!nalezeniPojistenci.isEmpty()) {
            System.out.println(oddelovac);
            System.out.println("Nalezení pojištěnci (ID: příjmení, jméno, věk, tel.): ");
            for (PojistenecDTO pojistenec : nalezeniPojistenci) {
                System.out.println(pojistenec);
            }
            System.out.println(oddelovacln);
        } else {
            vypisZpravu(ZpravyOVysledkuOperaceEnum.INSURED_PARAMETERS_NOT_FOUND.message);
        }
    }

    @Override
    public List<String> pridejPojistence(List<PopiskyEnum> polozky) {
        System.out.println("----- Přidání nového pojištěného -----");
        System.out.println("Zadejte údaje o pojištěném");

        List<String> zadaneHodnoty = new ArrayList<>();
        for (PopiskyEnum polozka : polozky) {
            System.out.print(polozka.popisek);
            zadaneHodnoty.add(scanner.nextLine());
        }

        return vyplnenePolozky;

        jmeno
        String jmeno = vstup.zvalidujJmenoPrijmeni(scanner.nextLine());
        System.out.print("Příjmení: ");
        String prijmeni = vstup.zvalidujJmenoPrijmeni(scanner.nextLine());
        System.out.print("Věk: ");
        int vek = vstup.zvalidujVek(scanner.nextLine());
        System.out.printf("Telefonní číslo: ");
        String telefon = vstup.zvalidujTelefon(scanner.nextLine());

        spravcePojistenych.pridejPojisteneho(jmeno, prijmeni, vek, telefon);

        vypisZpravu(ZpravyOVysledkuOperaceEnum.CREATE_SUCCESS.message);
    }

    @Override
    public void upravPojistence() {

        System.out.println("----- Editace pojištěnce -----");
        System.out.print("Zadejte id pojištěnce, kterého chcete upravit: ");
        int id = vstup.zvalidujCislo(scanner.nextLine());
        PojistenecDTO pojisteny = spravcePojistenych.najdiPojisteneho(id);
        if (pojisteny != null) {
            System.out.println("Zadejte nové údaje (pokud nic nezadáte, zůstane původní údaj.");

            System.out.print("Zadejte nové jméno: ");
            String noveJmeno = scanner.nextLine().trim();
            if (!noveJmeno.isEmpty()) {
                noveJmeno = vstup.zvalidujJmenoPrijmeni(noveJmeno);
                pojisteny.setJmeno(noveJmeno);
            }

            System.out.print("Zadejte nové příjmení: ");
            String novePrijmeni = scanner.nextLine().trim();
            if (!novePrijmeni.isEmpty()) {
                novePrijmeni = vstup.zvalidujJmenoPrijmeni(novePrijmeni);
                pojisteny.setPrijmeni(novePrijmeni);
            }

            System.out.print("Zadejte nový věk: ");
            String novyVekText = scanner.nextLine().trim();
            int novyVek;
            if (!novyVekText.isEmpty()) {
                novyVek = vstup.zvalidujVek(novyVekText);
                pojisteny.setVek(novyVek);
            }

            System.out.printf("Zadejte nový telefon: ");
            String novyTelefon = scanner.nextLine().trim();
            if (!novyTelefon.isEmpty()) {
                novyTelefon = vstup.zvalidujTelefon(novyTelefon);
                pojisteny.setTelefon(novyTelefon);
            }

            vypisZpravu(ZpravyOVysledkuOperaceEnum.UPDATE_SUCCESS.message);

        } else {
            vypisZpravu(ZpravyOVysledkuOperaceEnum.INSURED_ID_NOT_FOUND.message);
        }
    }

    @Override
    public void odstranPojistence() {
        System.out.println("----- Odstranění pojištěnce -----");
        System.out.print("Zadejte id pojištěnce, kterého chcete odstranit: ");
        int id = vstup.zvalidujCislo(scanner.nextLine());
        if (spravcePojistenych.odstranPojisteneho(id)) {
            vypisZpravu(ZpravyOVysledkuOperaceEnum.DELETE_SUCCESS.message);
        } else {
            vypisZpravu(ZpravyOVysledkuOperaceEnum.INSURED_ID_NOT_FOUND.message);
        }

    }

    public void vypisZpravu(String zprava) {
        System.out.println(oddelovac);
        System.out.println(zprava);
        System.out.println(oddelovacln);
    }

    public void vypisChybovouHlasku(Exception ex) {
        System.out.println(ex.getMessage());
    }
    public void vyzviKOpakovaniZadani() {
        System.out.print("Opakujte prosím zadání: ");
    }

    @Override
    public String ziskejOpravenyVstup() {
        return scanner.nextLine();
    }

}
