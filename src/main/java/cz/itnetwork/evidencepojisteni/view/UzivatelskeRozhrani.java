package cz.itnetwork.evidencepojisteni.view;

import cz.itnetwork.evidencepojisteni.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;

import java.util.List;

public interface UzivatelskeRozhrani {
    /**
     * Spustí program vykreslením úvodní obrazovky a nabídky
     */
    void vykresliUvodniObrazovku();

    /**
     * Vypisuje úvodní nabídku, dokud uživatel nezvolí konec programu
     *
     * @return
     */
    void vypisUvodniNabidku();

    /**
     * Vypíše všechny pojištěnce
     */
    void vypisVsechnyPojistence(List<PojistenecDTO> pojistenci);

    /**
     * Vypíše pojištěnce odpovídající kritériím
     */
    void vypisVyhledanePojistence();

    /**
     * Přidá nového pojištěnce
     *
     * @return
     */
    List<String> pridejPojistence(List<PopiskyEnum> polozky);

    /**
     * Upraví pojištěnce se zadaným ID
     */
    void upravPojistence();

    /**
     * Odstraní pojištěnce se zadaným ID
     */
    void odstranPojistence();

    /**
     * Zpráva pro uživatele o výsledku operace.
     * @param zprava Sdělení uživateli
     */
    void vypisZpravu(String zprava);

    /**
     * Vypíše chybovou hlášku
     * @param ex Výjimka
     */
    void vypisChybovouHlasku(Exception ex);

    /**
     * Vypíše popisek charakterizující
     */
    void vypisPolozku();
    /**
     * Vyzve uzivatele k zadání textu
     * @return zadaný text
     */
    String ziskejVstup();


    void vyzviKOpakovaniZadani();
}
