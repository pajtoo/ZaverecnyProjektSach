package cz.itnetwork.evidencepojisteni.view;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;

import java.util.List;

public interface UzivatelskeRozhrani {
    /**
     * Zahájí program vykreslením úvodní obrazovky a nabídky
     */
    void vykresliUvodniObrazovku();

    /**
     * Vypíše úvodní nabídku
     */
    void vypisUvodniNabidku();

    /**
     * Vypíše pojištěnce předané v argumentu
     * @param pojistenci Pojištěnci
     */
    void vypisPojistence(List<PojistenecDTO> pojistenci);

    /**
     * Vypíše nabídku pro práci s pojištěncem
     */
    void vypisNabidkuPraceSPojistencem();

    /**
     * Vypíše předaný text do konzole (metodou System.out.print)
     * @param text Text k vypsání
     */
    void vypisText(String text);

    /**
     * Vypíše uživateli zprávu (např. o výsledku operace)
     * @param zprava Zpráva
     */
    void vypisZpravu(String zprava);

    /**
     * Vypíše chybovou hlášku
     * @param ex Výjimka
     */
    void vypisChybovouHlasku(Exception ex);

    /**
     * Vyzve uzivatele k zadání textu
     * @return zadaný text
     */
    String ziskejVstup();

    /**
     * Vyzve uživatele k opakování zadání
     */
    void vyzviKOpakovaniZadani();
}
