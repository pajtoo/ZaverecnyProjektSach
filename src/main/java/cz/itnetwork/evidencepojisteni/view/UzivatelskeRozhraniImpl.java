/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.view;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.view.enums.ZpravyOVysledkuOperaceEnum;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Uživatelské rozhrani
 *
 * @author Pavel
 */
@Component
public class UzivatelskeRozhraniImpl implements UzivatelskeRozhrani {

    private final Scanner scanner;
    private final String oddelovac = "------------------------------------------------";
    private final String oddelovacln = oddelovac + System.lineSeparator();

    /**
     * Konstruktor
     *
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
    public void vypisPojistence(List<PojistenecDTO> pojistenci) {
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
    public String zahajVyhledavaniPojisteneho() {
        System.out.println("----- Vyhledávání pojištěných -----");
        System.out.println("Zvolte si prosím vyhledávací kritérium: ");
        System.out.println("1 - Podle osobních údajů");
        System.out.println("2 - Podle ID");
        return scanner.nextLine();
    }

    @Override
    public List<String> ziskejParametryVyhledavani(List<PopiskyEnum> popisky) {
        List<String> polozky = new ArrayList<>();
        // Extrakce základní varianty popisku
        popisky.forEach(popisek -> polozky.add(popisek.popisek));
        return ziskejHodnotyKPolozkam(polozky);
    }

    @Override
    public List<String> pridejPojistence(List<PopiskyEnum> popisky) {
        System.out.println("----- Přidání nového pojištěného -----");
        List<String> polozky = new ArrayList<>();
        // Extrakce základní varianty popisku
        popisky.forEach(popisek -> polozky.add(popisek.popisek));
        return ziskejHodnotyKPolozkam(polozky);
    }

    @Override
    public List<String> upravPojistence(List<PopiskyEnum> popisky) {
        System.out.println("--------- Editace pojištěného --------");
        System.out.println(oddelovac);
        System.out.println("- Pokud ponecháte u libovolného pole -");
        System.out.println("- prázdnou hodnotu, bude zachována ----");
        System.out.println("- hodnota stávající ------------------");
        System.out.println(oddelovac);

        List<String> polozky = new ArrayList<>();
        // Extrakce varianty popisku pro editaci
        popisky.forEach(popisek -> polozky.add(popisek.popisekEditace));
        return ziskejHodnotyKPolozkam(polozky);
    }

    @Override
    public void odstranPojistence() {
        /*System.out.println("----- Odstranění pojištěnce -----");
        System.out.print("Zadejte id pojištěnce, kterého chcete odstranit: ");
        int id = vstup.zvalidujCislo(scanner.nextLine());
        if (spravcePojistenych.odstranPojisteneho(id)) {
            vypisZpravu(ZpravyOVysledkuOperaceEnum.DELETE_SUCCESS.message);
        } else {
            vypisZpravu(ZpravyOVysledkuOperaceEnum.INSURED_ID_NOT_FOUND.message);
        }
*/
    }

    /**
     * Každou z položek uvede popiskem a následně si vyžádá zadání příslušné hodnoty.
     *
     * @param polozky Položky, jejichž hodnota je požadována
     * @return Seznam zadaných hodnot
     */
    @Override
    public List<String> ziskejHodnotyKPolozkam(List<String> polozky) {
        System.out.println("Zadejte údaje: ");
        List<String> zadaneHodnoty = new ArrayList<>();
        for (String polozka : polozky) {
            System.out.print(polozka);
            zadaneHodnoty.add(scanner.nextLine());
        }
        return zadaneHodnoty;
    }

    @Override
    public void vypisZpravu(String zprava) {
        System.out.println(oddelovac);
        System.out.println(zprava);
        System.out.println(oddelovacln);
    }

    @Override
    public void vypisChybovouHlasku(Exception ex) {
        System.out.println(ex.getMessage());
    }

    /**
     * Vyzve uzivatele k zadání textu
     *
     * @return zadaný text
     */
    @Override
    public String ziskejVstup() {
        return scanner.nextLine();
    }

    @Override
    public void vyzviKOpakovaniZadani() {
        System.out.print("Opakujte prosím zadání: ");
    }

}
