/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.view;

import cz.itnetwork.evidencepojisteni.Pojistenec;

import java.util.List;
import java.util.Scanner;

/**
 * Uživatelské rozhrani
 * @author Pavel
 */
public class UzivatelskeRozhraniImpl implements UzivatelskeRozhrani {

    private Scanner scanner;

    /**
     * Konstruktor
     * @param scanner Scanner
     */
    public UzivatelskeRozhraniImpl(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void vykresliUvodniObrazovku() {
        System.out.println("------------------------------------------------");
        System.out.println("*** E V I D E N C E  P O J I Š T Ě N Ý C H ***");
        System.out.println("------------------------------------------------");
        System.out.println("");
    }

    @Override
    public String vypisUvodniNabidku() {
            System.out.println("Zvolte si z následující nabídky (zadejte číslo volby a stiskněte enter):");
            System.out.println("1 - Vypsat všechny pojištěnce");
            System.out.println("2 - Vyhledat pojištěnce");
            System.out.println("3 - Přidat pojištěnce");
            System.out.println("4 - Upravit pojištěnce");
            System.out.println("5 - Odstranit pojištěnce");
            System.out.println("6 - Konec");

            return scanner.nextLine();
    }

    @Override
    public void vypisVsechnyPojistence() {
        if (!spravcePojistenych.vratVsechnyPojistene().isEmpty()) {
            System.out.println("----- Přehled pojištěných osob (ID: přijmení, jméno, věk, tel.) -----");

            for (Pojistenec pojistenec : spravcePojistenych.vratVsechnyPojistene()) {
                System.out.println(pojistenec);
            }
            System.out.println("----------------------------------------");
            System.out.println("");
        } else {
            vypisZpravuUspech("Nenalezeny žádné záznamy.");
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

        List<Pojistenec> nalezeniPojistenci = spravcePojistenych.najdiPojistene(jmeno, prijmeni);
        if (!nalezeniPojistenci.isEmpty()) {
            System.out.println("----------------------------------------");
            System.out.println("Nalezení pojištěnci (ID: příjmení, jméno, věk, tel.): ");
            for (Pojistenec pojistenec : nalezeniPojistenci) {
                System.out.println(pojistenec);
            }
            System.out.println("----------------------------------------");
            System.out.println("");
        } else {
            vypisZpravuUspech("Nebyl nalezen žádný pojištěný odpovídající daným kritériím.");
        }
    }

    @Override
    public void pridejPojistence() {
        System.out.println("----- Přidání nového pojištěného -----");
        System.out.println("Zadejte údaje o pojištěném");

        System.out.print("Křestní jméno: ");
        String jmeno = vstup.zvalidujJmenoPrijmeni(scanner.nextLine());
        System.out.print("Příjmení: ");
        String prijmeni = vstup.zvalidujJmenoPrijmeni(scanner.nextLine());
        System.out.print("Věk: ");
        int vek = vstup.zvalidujVek(scanner.nextLine());
        System.out.printf("Telefonní číslo: ");
        String telefon = vstup.zvalidujTelefon(scanner.nextLine());

        spravcePojistenych.pridejPojisteneho(jmeno, prijmeni, vek, telefon);

        vypisZpravuUspech("Nový pojištěný byl úspěšně přidán.");
    }

    @Override
    public void upravPojistence() {

        System.out.println("----- Editace pojištěnce -----");
        System.out.print("Zadejte id pojištěnce, kterého chcete upravit: ");
        int id = vstup.zvalidujCislo(scanner.nextLine());
        Pojistenec pojisteny = spravcePojistenych.najdiPojisteneho(id);
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

            vypisZpravuUspech("Údaje pojištěnce byly upraveny.");

        } else {
            vypisZpravuUspech("Pojištěnec se zadaným ID nebyl nalezen.");
        }
    }

    @Override
    public void odstranPojistence() {
        System.out.println("----- Odstranění pojištěnce -----");
        System.out.print("Zadejte id pojištěnce, kterého chcete odstranit: ");
        int id = vstup.zvalidujCislo(scanner.nextLine());
        if (spravcePojistenych.odstranPojisteneho(id)) {
            vypisZpravuUspech("Pojištěný byl úspěšně odstraněn.");
        } else {
            vypisZpravuUspech("Pojištěnec se zadaným ID nebyl nalezen.");
        }

    }

    @Override
    public void vypisZpravuUspech(String zprava) {
        System.out.println("----------------------------------------");
        System.out.println(zprava);
        System.out.println("----------------------------------------");
        System.out.println("");
    }

    @Override
    public String ziskejVstup() {
        return "";
    }

    public void vypisChybovouHlasku(Exception ex) {
        System.out.println(ex.getMessage());
    }
    public void vyzviKOpakovaniZadani() {
        System.out.print("Opakujte prosím zadání: ");
    }
}
