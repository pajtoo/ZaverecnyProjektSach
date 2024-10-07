package cz.itnetwork.evidencepojisteni.view;

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
    String vypisUvodniNabidku();

    /**
     * Vypíše všechny pojištěnce
     */
    void vypisVsechnyPojistence();

    /**
     * Vypíše pojištěnce odpovídající kritériím
     */
    void vypisVyhledanePojistence();

    /**
     * Přidá nového pojištěnce
     */
    void pridejPojistence();

    /**
     * Upraví pojištěnce se zadaným ID
     */
    void upravPojistence();

    /**
     * Odstraní pojištěnce se zadaným ID
     */
    void odstranPojistence();

    /**
     * Zpráva pro uživatele o úspěšném výsledku operace.
     *
     * @param zprava Sdělení uživateli
     */
    void vypisZpravuUspech(String zprava);

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


    void vyzviKOpakovaniZadani();
}
