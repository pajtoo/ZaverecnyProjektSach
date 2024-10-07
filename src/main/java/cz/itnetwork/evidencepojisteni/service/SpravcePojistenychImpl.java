/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.service;

import cz.itnetwork.evidencepojisteni.Pojistenec;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída je implementací pro perzistenci pomocí PostgreSQL
 *
 * @author Pavel Šach
 */
public class SpravcePojistenychImpl implements SpravcePojistenych {

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

    @Override
    public void pridejPojisteneho(String jmeno, String prijmeni, int vek, String telefon) {
        pojistenci.add(new Pojistenec(jmeno, prijmeni, vek, telefon));
    }

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

    @Override
    public boolean odstranPojisteneho(int id) {
        return pojistenci.remove(najdiPojisteneho(id));
    }
}
