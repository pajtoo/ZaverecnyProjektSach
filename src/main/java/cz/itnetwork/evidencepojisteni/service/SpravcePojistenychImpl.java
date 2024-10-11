/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.service;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída je implementací pro perzistenci pomocí PostgreSQL
 *
 * @author Pavel Šach
 */
public class SpravcePojistenychImpl implements SpravcePojistenych {

    public List<PojistenecDTO> vratVsechnyPojistene() {
        //TODO
    };

    @Override
    public List<PojistenecDTO> najdiPojistene(String jmeno, String prijmeni) {
        List<PojistenecDTO> vyhledaniPojistenci = new ArrayList<>();
        for (PojistenecDTO pojisteny : pojistenci) {
            if (pojisteny.getJmeno().equalsIgnoreCase(jmeno) && pojisteny.getPrijmeni().equalsIgnoreCase(prijmeni)) {
                vyhledaniPojistenci.add(pojisteny);
            }
        }
        return vyhledaniPojistenci;
    }

    @Override
    public PojistenecDTO najdiPojisteneho(int id) {
        PojistenecDTO pojistenecDTO = null;
        for (PojistenecDTO pojisteny : pojistenci) {
            if (pojisteny.getId() == id) {
                pojistenecDTO = pojisteny;
            }
        }
        return pojistenecDTO;
    }

    @Override
    public void pridejPojisteneho(String jmeno, String prijmeni, int vek, String telefon) {
        pojistenci.add(new PojistenecDTO(jmeno, prijmeni, vek, telefon));
    }

    @Override
    public boolean upravPojisteneho(int id, String noveJmeno, String novePrijmeni, int novyVek, String novyTelefon) {
        for (PojistenecDTO pojistenec : pojistenci) {
            if (pojistenec.getId() == id) {
                pojistenec.setJmeno(noveJmeno);
                pojistenec.setPrijmeni(novePrijmeni);
                pojistenec.setVek(novyVek);
                pojistenec.setTelefon(novyTelefon);
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
