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
     * @param hledanyPojistenec Data o pojištěnci
     * @return Seznam pojištěných odpovídajících kritériím
     */
    List<PojistenecDTO> najdiPojistene(PojistenecDTO hledanyPojistenec);

    /**
     * Najde a vrátí pojištěného podle jeho ID
     *
     * @param id ID pojištěného
     * @return Vrátí pojištěného se zadaným ID
     */
    PojistenecDTO najdiPojistene(Long id);

    /**
     * Vytvoří nového pojištěného
     *
     * @param pojistenec Pojištěnec
     */
    PojistenecDTO ulozPojisteneho(PojistenecDTO pojistenec);

    /**
     * Odstraní pojištěného se zadaným id, pokud existuje
     *
     * @param pojistenec odstraňovaný pojištěnec
     * @return Vrací true, pokud pojištěný se zadaným id existuje, jinak vrací
     * false.
     */
    void odstranPojisteneho(PojistenecDTO pojistenec);
}
