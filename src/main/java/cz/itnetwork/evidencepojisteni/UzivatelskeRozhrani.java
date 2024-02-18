/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni;

import java.util.List;
import java.util.Scanner;

/**
 * Uživatelské rozhrani
 * @author Pavel
 */
public class UzivatelskeRozhrani {

    private Scanner scanner;
    private SpravcePojistenych spravcePojistenych = new SpravcePojistenych();
    private UzivatelskyVstup vstup;

    /**
     * Konstruktor
     * @param scanner Scanner
     * @param vstup Vstup
     */
    public UzivatelskeRozhrani(Scanner scanner, UzivatelskyVstup vstup) {
        this.scanner = scanner;
        this.vstup = vstup;
    }

    /**
     * Spustí program vykreslením úvodní obrazovky a nabídky
     */
    public void vykresliUvodniObrazovku() {
        System.out.println("------------------------------------------------");
        System.out.println("*** E V I D E N C E  P O J I Š T Ě N Ý C H ***");
        System.out.println("------------------------------------------------");
        System.out.println("");
        vypisUvodniNabidku();
    }

    /**
     * Vypisuje úvodní nabídku, dokud uživatel nezvolí konec programu
     */
    private void vypisUvodniNabidku() {
        boolean isKonec = false;
        while (!isKonec) {
            System.out.println("Zvolte si z následující nabídky (zadejte číslo volby a stiskněte enter):");
            System.out.println("1 - Vypsat všechny pojištěnce");
            System.out.println("2 - Vyhledat pojištěnce");
            System.out.println("3 - Přidat pojištěnce");
            System.out.println("4 - Upravit pojištěnce");
            System.out.println("5 - Odstranit pojištěnce");
            System.out.println("6 - Konec");

            int volba = vstup.zvalidujCiselnyVstup(scanner.nextLine());
            switch (volba) {
                case 1:
                    vypisVsechnyPojistence();
                    break;
                case 2:
                    vypisVyhledanePojistence();
                    break;
                case 3:
                    pridejPojistence();
                    break;
                case 4:
                    upravPojistence();
                    break;
                case 5:
                    odstranPojistence();
                    break;
                case 6:
                    vypisZpravu("Děkujeme Vám za využití této aplikace! Nashledanou!");
                    isKonec = true;
                    break;
                default:
                    vypisZpravu("Neplatná volba.");
            }
        }
    }

    /**
     * Vypíše všechny pojištěnce
     */
    private void vypisVsechnyPojistence() {
        if (!spravcePojistenych.vratVsechnyPojistene().isEmpty()) {
            System.out.println("----- Přehled pojištěných osob (ID: přijmení, jméno, věk, tel.) -----");

            for (Pojistenec pojistenec : spravcePojistenych.vratVsechnyPojistene()) {
                System.out.println(pojistenec);
            }
            System.out.println("----------------------------------------");
            System.out.println("");
        } else {
            vypisZpravu("Nenalezeny žádné záznamy.");
        }
    }

    /**
     * Vypíše pojištěnce odpovídající kritériím
     */
    private void vypisVyhledanePojistence() {
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
            vypisZpravu("Nebyl nalezen žádný pojištěný odpovídající daným kritériím.");
        }
    }

    /**
     * Přidá nového pojištěnce
     */
    private void pridejPojistence() {
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

        vypisZpravu("Nový pojištěný byl úspěšně přidán.");
    }

    /**
     * Upraví pojištěnce se zadaným ID
     */
    private void upravPojistence() {

        System.out.println("----- Editace pojištěnce -----");
        System.out.print("Zadejte id pojištěnce, kterého chcete upravit: ");
        int id = vstup.zvalidujCiselnyVstup(scanner.nextLine());
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

            vypisZpravu("Údaje pojištěnce byly upraveny.");

        } else {
            vypisZpravu("Pojištěnec se zadaným ID nebyl nalezen.");
        }
    }

    /**
     * Odstraní pojištěnce se zadaným ID
     */
    private void odstranPojistence() {
        System.out.println("----- Odstranění pojištěnce -----");
        System.out.print("Zadejte id pojištěnce, kterého chcete odstranit: ");
        int id = vstup.zvalidujCiselnyVstup(scanner.nextLine());
        if (spravcePojistenych.odstranPojisteneho(id)) {
            vypisZpravu("Pojištěný byl úspěšně odstraněn.");
        } else {
            vypisZpravu("Pojištěnec se zadaným ID nebyl nalezen.");
        }

    }

    /**
     * Zpráva pro uživatele o výsledku operace.
     *
     * @param zprava Sdělení uživateli
     */
    private void vypisZpravu(String zprava) {
        System.out.println("----------------------------------------");
        System.out.println(zprava);
        System.out.println("----------------------------------------");
        System.out.println("");
    }
}
