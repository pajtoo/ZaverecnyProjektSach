package cz.itnetwork.evidencepojisteni.service;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;

import java.util.List;

public interface SpravcePojistenych {
    /**
     * Vrátí seznam všech pojištěných
     *
     * @return Seznam všech pojištěných
     */
    List<PojistenecDTO> vratVsechnyPojistene();

    /**
     * Vrátí seznam pojištěných odpovídajících kritériím
     *
     * @param jmeno    Jméno hledaného pojištěného
     * @param prijmeni Příjmení hledaného pojištěného
     * @return Seznam pojištěných odpovídajících kritériím
     */
    List<PojistenecDTO> najdiPojistene(String jmeno, String prijmeni);

    /**
     * Najde a vrátí pojištěného podle jeho ID
     *
     * @param id ID pojištěného
     * @return Vrátí pojištěného se zadaným ID
     */
    PojistenecDTO najdiPojisteneho(int id);

    /**
     * Vytvoří nového pojištěného
     *
     * @param jmeno    Jméno
     * @param prijmeni Příjmení
     * @param vek      Věk
     * @param telefon  Telefonní číslo
     */
    void pridejPojisteneho(String jmeno, String prijmeni, int vek, String telefon);

    /**
     * Upraví atributy pojištěného s určitým id
     *
     * @param id           ID pojištěného
     * @param noveJmeno    Nové jméno pojištěného
     * @param novePrijmeni Nové příjmení pojištěného
     * @param novyVek      Nový věk pojištěného
     * @param novyTelefon  Nový telefon pojištěného
     * @return Pokud uživatel s daným id existuje, vrátí true, jinak vrátí false
     */
    boolean upravPojisteneho(int id, String noveJmeno, String novePrijmeni, int novyVek, String novyTelefon);

    /**
     * Odstraní pojištěného se zadaným id, pokud existuje
     *
     * @param id ID odstraňovaného pojištěnce
     * @return Vrací true, pokud pojištěný se zadaným id existuje, jinak vrací
     * false.
     */
    boolean odstranPojisteneho(int id);
}
