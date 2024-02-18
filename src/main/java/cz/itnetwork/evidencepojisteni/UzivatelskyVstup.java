/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni;

import java.util.Scanner;

/**
 * Zajišťuje validaci uživatelských vstupů
 *
 * @author Pavel Šach
 */
public class UzivatelskyVstup {

    private Scanner scanner;

    /**
     * Konstruktor
     *
     * @param scanner Scanner
     */
    public UzivatelskyVstup(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Zajistí validní jméno nebo příjmení, konkrétně aby nebylo prázdné,
     * obsahovalo pouze platné znaky. Nakonec nastaví první písmeno jako velké.
     *
     * @param jmenoPrijmeni Jméno nebo příjmení k validaci
     * @return Validní jméno nebo příjmení ve správném formátu
     */
    public String zvalidujJmenoPrijmeni(String jmenoPrijmeni) {
        jmenoPrijmeni = jmenoPrijmeni.trim().replaceAll(" +", " "); //odstraní vícečetnost mezer

        boolean vstupJePlatny = false;
        boolean obsahujeNeplatneZnaky = false;

        while (!vstupJePlatny) {

            for (char znak : jmenoPrijmeni.toCharArray()) {
                if (!Character.isLetter(znak) && !Character.isSpaceChar(znak)) {
                    obsahujeNeplatneZnaky = true;
                }
            }

            if (!jmenoPrijmeni.isBlank() && !obsahujeNeplatneZnaky) {
                vstupJePlatny = true;
                jmenoPrijmeni = jmenoPrijmeni.substring(0, 1).toUpperCase() + jmenoPrijmeni.substring(1);
            } else {
                System.out.print("Neplatné zadání. Opakujte zadání: ");
                jmenoPrijmeni = scanner.nextLine().trim();
                obsahujeNeplatneZnaky = false;
            }
        }
        return jmenoPrijmeni;
    }

    /**
     * Zajistí validní uživatelský číselný vstup
     *
     * @param cislo Číslo k validaci
     * @return validní číslo
     */
    public int zvalidujCiselnyVstup(String cislo) {
        int ciselnyVstup = 0;
        try {
            ciselnyVstup = Integer.parseInt(cislo.trim());
        } catch (NumberFormatException ex) {
            System.out.print("Neplatné číslo. Opakujte zadání: ");
            return zvalidujCiselnyVstup(scanner.nextLine());
        }
        return ciselnyVstup;
    }

    /**
     * Zajistí validní věk člověka
     *
     * @param vekText Věk String
     * @return Validní věk
     */
    public int zvalidujVek(String vekText) {
        int vek = 0;
        boolean vekJePlatny = false;
        while (!vekJePlatny) {
            vek = zvalidujCiselnyVstup(vekText);
            if (vek >= 0 && vek <= 130) {
                vekJePlatny = true;
            } else {
                System.out.print("Neplatný věk. Opakujte zadání: ");
                vekText = scanner.nextLine();
            }
        }

        return vek;
    }

    /**
     * Zvaliduje telefonní číslo a převede je do mezinárodního formátu bez mezer
     *
     * @param telefon Telefonní číslo
     * @return Validní telefonní číslo v jednotném mezinárodním formátu
     */
    public String zvalidujTelefon(String telefon) {
        telefon = telefon.trim().replaceAll("\\s", ""); //odstraní mezery

        boolean telefonJePlatny = false; //validita čísla
        while (!telefonJePlatny) {
            boolean maMezinarodniPredvolbu = false;
            boolean prvniZnakJePlatny = false;

            if (telefon.charAt(0) == '+' || (telefon.charAt(0) == '0' && telefon.charAt(1) == '0')) {
                maMezinarodniPredvolbu = true;
            } // kontroluje, zda má číslo mezinárodní předvolbu

            if (maMezinarodniPredvolbu || Character.isDigit(telefon.charAt(0))) {
                prvniZnakJePlatny = true;
            } // ověřuje platnost prvního znaku

            boolean ostatniZnakyJsouPlatne = true;
            for (int i = 1; i < telefon.length(); i++) {
                if (!Character.isDigit(telefon.charAt(i))) {
                    ostatniZnakyJsouPlatne = false;
                }
            } // ověřuje platnost ostatních znaků

            boolean delkaCislaJeValidni = false;
            if (telefon.length() > 6 && telefon.length() < 15) {
                delkaCislaJeValidni = true;
            } // ověřuje platnost délky čísla

            if (prvniZnakJePlatny && ostatniZnakyJsouPlatne && delkaCislaJeValidni) {
                telefonJePlatny = true;
                if (!maMezinarodniPredvolbu) {
                    telefon = "+420" + telefon;
                }
            } else {
                System.out.print("Neplatné telefonní číslo. Opakujte zadání: ");
                telefon = scanner.nextLine();
            }
        }
        return telefon;
    }
}
