package cz.itnetwork.evidencepojisteni.service;

import cz.itnetwork.evidencepojisteni.Pojistenec;

import java.util.List;

public interface SpravcePojistenych {
    List<Pojistenec> vratVsechnyPojistene();

    List<Pojistenec> najdiPojistene(String jmeno, String prijmeni);

    Pojistenec najdiPojisteneho(int id);

    void pridejPojisteneho(String jmeno, String prijmeni, int vek, String telefon);

    boolean upravPojisteneho(int id, String noveJmeno, String novePrijmeni, int novyVek, String novyTelefon);

    boolean odstranPojisteneho(int id);
}
