package cz.itnetwork.evidencepojisteni.view;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
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
     */
    void vypisUvodniNabidku();

    /**
     * Vypíše pojištěnce předané v argumentu
     */
    void vypisPojistence(List<PojistenecDTO> pojistenci);

    /**
     * Vypíše rozhraní pro vyhledávání Pojištěného a vyzve k volbě kritéria vyhledávání
     * @return Číslo (jako String) odpovídající volbě způsobu vyhledávání
     */
    String zahajVyhledavaniPojisteneho();

    /**
     * Získá hodnoty k jednotlivým parametrům vyhledávání
     * @param popisky Seznam parametrů vyhledávání
     * @return Seznam hodnot
     */
    List<String> ziskejParametryVyhledavani(List<PopiskyEnum> popisky);

    /**
     * Každou z položek uvede popiskem a následně si vyžádá zadání příslušné hodnoty.
     *
     * @param polozky Položky, jejichž hodnota je požadována
     * @return Seznam zadaných hodnot
     */
    List<String> ziskejHodnotyKPolozkam(List<String> polozky);

    /**
     * Přidá nového pojištěnce
     *
     */
    List<String> pridejPojistence(List<PopiskyEnum> popisky);

    /**
     * Upraví pojištěnce se zadaným ID
     */
    List<String> upravPojistence(List<PopiskyEnum> popisky);

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
     * Vyzve uzivatele k zadání textu
     * @return zadaný text
     */
    String ziskejVstup();


    void vyzviKOpakovaniZadani();
}
