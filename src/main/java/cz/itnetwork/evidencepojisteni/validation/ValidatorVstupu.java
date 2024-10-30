/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.validation;

import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Zajišťuje validaci uživatelských vstupů
 *
 * @author Pavel Šach
 */
@Component
public class ValidatorVstupu {

    /**
     * Výčet osobních údajů s popisky pro View a validačními kritérii (min. a max. hodnota).
     * Hodnota v případě String určuje délku textu, v případě Integer určuje uzavřený interval povolených číselných hodnot.
     */
    public enum ValidatorEnum {
        /**
         * Unikátní identifikátor
         */
        ID(
                1,
                Integer.MAX_VALUE
        ),
        /**
         * Křestní jméno
         */
        JMENO(
            2,
            30,
            ParametryValidaceEnum.TEXT_ODSTRAN_PREBYTECNE_MEZERY,
            ParametryValidaceEnum.VLASTNI_JMENO_DEJ_VELKE_PISMENO
        ),
        /**
         * Příjmení
         */
        PRIJMENI(
            2,
            30,
            ParametryValidaceEnum.TEXT_ODSTRAN_PREBYTECNE_MEZERY,
            ParametryValidaceEnum.VLASTNI_JMENO_DEJ_VELKE_PISMENO
        ),
        /**
         * Telefon
         */
        TELEFON(
            6,
            16, // Počet znaků včetně znaménka "+"
            ParametryValidaceEnum.TELEFON_DOPLN_MEZINARODNI_PREDVOLBU
        ),
        /**
         * Věk
         */
        VEK(
            0,
            130
        ),

        VOLBA_AKCE_V_MENU(
            1,
            6
        ),

        VOLBA_ZPUSOBU_VYHLEDAVANI(
            1,
            2
        );


        private final int min;
        private final int max;
        private final ParametryValidaceEnum[] parametryValidace;

        ValidatorEnum(
                int min,
                int max,
                ParametryValidaceEnum... parametryValidaceEnum
        ) {
            this.min = min;
            this.max = max;
            this.parametryValidace = parametryValidaceEnum;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public ParametryValidaceEnum[] getParametryValidace() {
            return parametryValidace;
        }

        /**
         * Výčet doplňkových parametrů validace vstupu
         */
        public enum ParametryValidaceEnum {
            /**
             * Parametr pro odstranění přebytečných mezer uvnitř textu.
             * Odstranění mezer na začátku a konci se dělá automaticky u všech vstupů
             */
            TEXT_ODSTRAN_PREBYTECNE_MEZERY,
            /**
             * Parametr pro nastavení prvního písmena jako velkého
             */
            VLASTNI_JMENO_DEJ_VELKE_PISMENO,
            /**
             * Parametr nastavující automatické doplnění telefonní předvolby +420 v případě jejího neuvedení
             */
            TELEFON_DOPLN_MEZINARODNI_PREDVOLBU
        }
    }

    /**
     * Univerzální metoda k validaci uživatelského vstupu, jejíž funkce se parametrizuje prostřednictvím předaných parametrů.
     * @param vstup Vstup k validaci
     * @param validatorEnum Identifikátor položky pro správné zpracování příslušné položky validátorem
     * @return Validní, standardizovaný vstup
     */
    public String zvaliduj(ValidatorEnum validatorEnum, boolean jePovinny, String vstup) throws InvalidUserInputException, NumberFormatException {
        vstup = vstup.trim();

        boolean isText = false;
        boolean isNumber = false;

        // Určení, zda se vstup má posuzovat jako číslo nebo text
        if (
                validatorEnum.toString().equals(ValidatorEnum.JMENO.toString()) ||
                validatorEnum.toString().equals(ValidatorEnum.PRIJMENI.toString()) ||
                validatorEnum.toString().equals(ValidatorEnum.TELEFON.toString())
        ) isText = true;
        else if (
                validatorEnum.toString().equals(ValidatorEnum.ID.toString()) ||
                validatorEnum.toString().equals(ValidatorEnum.VEK.toString()) ||
                validatorEnum.toString().equals(ValidatorEnum.VOLBA_AKCE_V_MENU.toString()) ||
                validatorEnum.toString().equals(ValidatorEnum.VOLBA_ZPUSOBU_VYHLEDAVANI.toString())
        ) isNumber = true;

        if (vstup.isEmpty() && jePovinny) {
            throw new InvalidUserInputException("Pole nemůže být prázdné. ");
        }
        // Pokud je vstup prázdný a není povinný, jedná se o editaci položky a data se za této podmínky nemění
        else if (vstup.isEmpty()) {
            return vstup;
        }

        // Validace a standardizace textového vstupu
        if (isText) {
                // Validace, standardizace telefonu
                if (validatorEnum.toString().equals(ValidatorEnum.TELEFON.toString())) {
                    vstup = zvalidujTelefon(vstup, validatorEnum);
                    return vstup;
                }
                // Validace, standardizace jména, příjmení
                if (
                    validatorEnum.toString().equals(ValidatorEnum.JMENO.toString()) ||
                    validatorEnum.toString().equals(ValidatorEnum.PRIJMENI.toString())
                ) {
                    vstup = zvalidujJmenoPrijmeni(vstup, validatorEnum);
                }
                // Validace délky textu
                int minimalniDelka;
                int maximalniDelka = validatorEnum.getMax();
                if (validatorEnum.getMin() <= 0) {
                    minimalniDelka = 1;
                } else {
                    minimalniDelka = validatorEnum.getMin();
                }
                if (
                        vstup.length() < minimalniDelka ||
                        vstup.length() > maximalniDelka
                ) {
                    throw new InvalidUserInputException("Neplatná délka textu. Váš text musí mít délku minimálně " + validatorEnum.getMin() + " a maximálně " + validatorEnum.getMax() + " znaků. ");
                }
                return vstup;
        }
        // Validace čísla
        else if (isNumber) {
                int vstupCislo;
                try {
                    vstupCislo = Integer.parseInt(vstup);
                } catch (NumberFormatException ex) {
                    throw new NumberFormatException("Nezadali jste platné číslo.");
                }
                if (vstupCislo < validatorEnum.getMin() && vstupCislo > validatorEnum.getMax())
                    throw new InvalidUserInputException("Neplatná hodnota. Lze zadat pouze hodnotu od " + validatorEnum.getMin() + " do " + validatorEnum.getMax() + ". ");
                return vstup;
        }
        else {
            throw new RuntimeException("Chyba validátoru. Datový typ není validátorem podporován");
        }
    }

    /**
     * Zvaliduje telefonní číslo a převede je do mezinárodního formátu bez mezer,
     * pokud je přítomen argument ParametryValidaceEnum.TELEFON_DOPLN_MEZINARODNI_PREDVOLBU
     * @param vstup Vstup
     * @return Validní telefonní číslo
     */
    private String zvalidujTelefon(String vstup, ValidatorEnum validatorEnum) throws InvalidUserInputException {
        //odstraní mezery uvnitř telefonního čísla
        vstup = vstup.replaceAll("\\s", "");
        // nahrazuje případné dvě nuly u mezinárodní předvolby znaménkem "+"
        if (vstup.charAt(0) == '0' && vstup.charAt(1) == '0') {
            vstup = "+" + vstup.substring(2);
        }
        // zjišťuje, zda má číslo mezinárodní předvolbu
        boolean maMezinarodniPredvolbu = vstup.charAt(0) == '+';
        // ověřuje platnost prvního znaku
        boolean prvniZnakJePlatny = maMezinarodniPredvolbu || Character.isDigit(vstup.charAt(0));
        // ověřuje platnost ostatních znaků
        boolean ostatniZnakyJsouPlatne = true;
        for (int i = 1; i < vstup.length(); i++) {
            if (!Character.isDigit(vstup.charAt(i))) {
                ostatniZnakyJsouPlatne = false;
            }
        }
        // ověřuje platnost délky čísla
        boolean delkaCislaJeValidni = false;
        if (
                (maMezinarodniPredvolbu && vstup.length() >= validatorEnum.getMin() && vstup.length() <= validatorEnum.getMax()) ||
                (!maMezinarodniPredvolbu && vstup.length() >= validatorEnum.getMin() && vstup.length() <= validatorEnum.getMax() - 4)
        ) {
            delkaCislaJeValidni = true;
        }
        // Pokud je vše v pořádku, vrací se standardizovaná hodnota
        if (prvniZnakJePlatny && ostatniZnakyJsouPlatne && delkaCislaJeValidni) {
            if (
                    !maMezinarodniPredvolbu &&
                    Arrays.asList(validatorEnum.getParametryValidace()).contains(ValidatorEnum.ParametryValidaceEnum.TELEFON_DOPLN_MEZINARODNI_PREDVOLBU)
            ) {
                vstup = "+420" + vstup;
            }
            return vstup;
        } else {
            throw new InvalidUserInputException("Neplatné telefonní číslo. ");
        }
    }

     /**
     * Zajistí validní jméno nebo příjmení, aby obsahovalo pouze platné znaky. Nakonec nastaví první písmeno jako velké.
     *
     * @param vstup Jméno nebo příjmení k validaci
     * @return Validní jméno nebo příjmení ve správném formátu
     */
    private String zvalidujJmenoPrijmeni(String vstup, ValidatorEnum validatorEnum) throws InvalidUserInputException {
        for (char znak : vstup.toCharArray()) {
            if (!Character.isLetter(znak) && !Character.isSpaceChar(znak)) {
                throw new InvalidUserInputException("Obsahuje neplatné znaky. ");
            }
        }
        // Odstraní vícečetnost mezer
        if (Arrays.asList(validatorEnum.getParametryValidace()).contains(ValidatorEnum.ParametryValidaceEnum.TEXT_ODSTRAN_PREBYTECNE_MEZERY)) {
            vstup = vstup.replaceAll(" +", " ");
        }
        // Nastaví první písmeno jako velké
        if (Arrays.asList(validatorEnum.getParametryValidace()).contains(ValidatorEnum.ParametryValidaceEnum.VLASTNI_JMENO_DEJ_VELKE_PISMENO)) {
            vstup = vstup.substring(0, 1).toUpperCase() + vstup.substring(1);
        }
        return vstup;
    }
}
