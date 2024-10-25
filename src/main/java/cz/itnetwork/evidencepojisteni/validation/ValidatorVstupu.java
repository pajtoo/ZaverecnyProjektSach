/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.validation;

import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;

import java.util.Arrays;

/**
 * Zajišťuje validaci uživatelských vstupů
 *
 * @author Pavel Šach
 */
public class ValidatorVstupu {

    /**
     * Výčet osobních údajů s popisky pro View a validačními kritérii (min. a max. hodnota).
     * Hodnota v případě String určuje délku textu, v případě Integer určuje uzavřený interval povolených číselných hodnot.
     */
    public enum ValidatorEnum {
        /**
         * Křestní jméno
         */
        JMENO(
            true,
            null,
            null,
            ParametryValidaceEnum.TEXT_ODSTRAN_PREBYTECNE_MEZERY,
            ParametryValidaceEnum.VLASTNI_JMENO_DEJ_VELKE_PISMENO
        ),
        /**
         * Příjmení
         */
        PRIJMENI(
            true,
            null,
            null,
            ParametryValidaceEnum.TEXT_ODSTRAN_PREBYTECNE_MEZERY,
            ParametryValidaceEnum.VLASTNI_JMENO_DEJ_VELKE_PISMENO
        ),
        /**
         * Telefon
         */
        TELEFON(
            true,
            6,
            15,
            ParametryValidaceEnum.TELEFON_DOPLN_MEZINARODNI_PREDVOLBU
        ),
        /**
         * Věk
         */
        VEK(
            true,
            0,
            130
        ),

        VOLBA_AKCE_V_MENU(
            true,
            null,
            null
        );


        private final boolean povinny;
        private final Integer min;
        private final Integer max;
        private final ParametryValidaceEnum[] parametryValidace;

        ValidatorEnum(
                boolean povinny,
                Integer min,
                Integer max,
                ParametryValidaceEnum... parametryValidaceEnum
        ) {
            this.povinny = povinny;
            this.min = min;
            this.max = max;
            this.parametryValidace = parametryValidaceEnum;
        }

        private boolean jePovinny() {
            return povinny;
        }

        private Integer getMin() {
            return min;
        }

        private Integer getMax() {
            return max;
        }

        private ParametryValidaceEnum[] getParametryValidace() {
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
     * Univerzální metoda k validaci uživatelského vstupu, jejíž funkce se parametrizuje prostřednictvím parametrů.
     * @param vstup Vstup k validaci
     * @param validatorEnum Identifikátor položky pro správné zpracování příslušné položky validátorem
     * @return Validní, standardizovaný vstup
     */
    public String zvaliduj(ValidatorEnum validatorEnum, String vstup) throws InvalidUserInputException, NumberFormatException {
        vstup = vstup.trim();

        boolean isText = false;
        boolean isNumber = false;

        // Určení, zda se vstup má posuzovat jako číslo nebo text
        if (
                validatorEnum.toString().equals(ValidatorEnum.JMENO.toString()) ||
                validatorEnum.toString().equals(ValidatorEnum.PRIJMENI.toString()) ||
                validatorEnum.toString().equals(ValidatorEnum.TELEFON.toString())
        ) isText = true;
        if (
                validatorEnum.toString().equals(ValidatorEnum.VEK.toString()) ||
                validatorEnum.toString().equals(ValidatorEnum.VOLBA_AKCE_V_MENU.toString())
        ) isNumber = true;

        if (vstup.isEmpty() && validatorEnum.jePovinny()) {
            throw new InvalidUserInputException("Pole nemůže být prázdné. ");
        }
        // Pokud je vstup prázdný a není povinný, jedná se o editaci položky a data se za této podmínky nemění
        if (vstup.isEmpty()) {
            return vstup;
        }

        // Validace a standardizace textového vstupu
        if (isText) {
                // Validace, standardizace telefonu
                if (validatorEnum.toString().equals(ValidatorEnum.TELEFON.toString())) {
                    vstup = zvalidujTelefon(vstup, validatorEnum);
                }
                // Validace, standardizace jména, příjmení
                if (validatorEnum.toString().equals(ValidatorEnum.TELEFON.toString())) {
                    vstup = zvalidujJmenoPrijmeni(vstup, validatorEnum);
                }
                // Validace délky textu
                int minimalniDelka;
                int maximalniDelka;
                if (validatorEnum.getMin() != null) {
                    minimalniDelka = validatorEnum.getMin();
                } else minimalniDelka = 1;
                if (validatorEnum.getMax() != null) {
                    maximalniDelka = validatorEnum.getMax();
                } else maximalniDelka = 10000;
                if (
                        vstup.length() < minimalniDelka ||
                        vstup.length() > maximalniDelka
                ) {
                    throw new InvalidUserInputException("Neplatná délka textu. Váš text musí mít délku minimálně " + validatorEnum.getMin() + " a maximálně " + validatorEnum.getMax() + " znaků. ");
                }
            return vstup;
        }
        // Validace čísla
        if (isNumber) {
            int vstupCislo;
            try {
                vstupCislo = Integer.parseInt(vstup);
            } catch (NumberFormatException ex) {
                throw new NumberFormatException("Nezadali jste platné číslo.");
            }
            if (vstupCislo < validatorEnum.getMin() && vstupCislo > validatorEnum.getMax())
                throw new InvalidUserInputException("Neplatný věk. Lze zadat pouze hodnotu od " + validatorEnum.getMin() + " do " + validatorEnum.getMax() + ". ");
            return vstup;
        }
        return vstup;
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
        // zjišťuje, zda má číslo mezinárodní předvolbu
        boolean maMezinarodniPredvolbu = vstup.charAt(0) == '+' || (vstup.charAt(0) == '0' && vstup.charAt(1) == '0');

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
        boolean delkaCislaJeValidni = vstup.length() >= validatorEnum.getMin() && vstup.length() <= validatorEnum.getMax();
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
