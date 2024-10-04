/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.service;

import cz.itnetwork.evidencepojisteni.Pojistenec;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída obsahuje databázi pojištěných a metody pro práci s touto databází
 *
 * @author Pavel Šach
 */
public class SpravcePojistenychImpl implements SpravcePojistenych {

    // Databáze pojištěnců
    private List<Pojistenec> pojistenci = new ArrayList<>();

     /**
     * Vrátí seznam všech pojištěných
     *
     * @return Seznam všech pojištěných
     */
    @Override
    public List<Pojistenec> vratVsechnyPojistene() {
        return pojistenci;
    }

    /**
     * Vrátí seznam pojištěných odpovídajících kritériím
     *
     * @param jmeno Jméno hledaného pojištěného
     * @param prijmeni Příjmení hledaného pojištěného
     * @return Seznam pojištěných odpovídajících kritériím
     */
    @Override
    public List<Pojistenec> najdiPojistene(String jmeno, String prijmeni) {
        List<Pojistenec> vyhledaniPojistenci = new ArrayList<>();
        for (Pojistenec pojisteny : pojistenci) {
            if (pojisteny.getJmeno().equalsIgnoreCase(jmeno) && pojisteny.getPrijmeni().equalsIgnoreCase(prijmeni)) {
                vyhledaniPojistenci.add(pojisteny);
            }
        }
        return vyhledaniPojistenci;
    }

    /**
     * Najde a vrátí pojištěného podle jeho ID
     *
     * @param id ID pojištěného
     * @return Vrátí pojištěného se zadaným ID
     */
    @Override
    public Pojistenec najdiPojisteneho(int id) {
        Pojistenec pojistenec = null;
        for (Pojistenec pojisteny : pojistenci) {
            if (pojisteny.getId() == id) {
                pojistenec = pojisteny;
            }
        }
        return pojistenec;
    }

    /**
     * Vytvoří nového pojištěného
     *
     * @param jmeno Jméno
     * @param prijmeni Příjmení
     * @param vek Věk
     * @param telefon Telefonní číslo
     */
    @Override
    public void pridejPojisteneho(String jmeno, String prijmeni, int vek, String telefon) {
        pojistenci.add(new Pojistenec(jmeno, prijmeni, vek, telefon));
    }

    /**
     * Upraví atributy pojištěného s určitým id
     *
     * @param id ID pojištěného
     * @param noveJmeno Nové jméno pojištěného
     * @param novePrijmeni Nové příjmení pojištěného
     * @param novyVek Nový věk pojištěného
     * @param novyTelefon Nový telefon pojištěného
     * @return Pokud uživatel s daným id existuje, vrátí true, jinak vrátí false
     */
    @Override
    public boolean upravPojisteneho(int id, String noveJmeno, String novePrijmeni, int novyVek, String novyTelefon) {
        for (Pojistenec pojisteny : pojistenci) {
            if (pojisteny.getId() == id) {
                pojisteny.setJmeno(noveJmeno);
                pojisteny.setPrijmeni(novePrijmeni);
                pojisteny.setVek(novyVek);
                pojisteny.setTelefon(novyTelefon);
                return true;
            }
        }
        return false;
    }

    /**
     * Odstraní pojištěného se zadaným id, pokud existuje
     *
     * @param id ID odstraňovaného pojištěnce
     * @return Vrací true, pokud pojištěný se zadaným id existuje, jinak vrací
     * false.
     */
    @Override
    public boolean odstranPojisteneho(int id) {
        return pojistenci.remove(najdiPojisteneho(id));
    }
}
