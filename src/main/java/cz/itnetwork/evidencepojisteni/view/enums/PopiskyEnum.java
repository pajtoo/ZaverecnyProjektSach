package cz.itnetwork.evidencepojisteni.view.enums;

public enum PopiskyEnum {
    /**
     * Unikátní identifikátor
     */
    ID (
"id"
    ),

    /**
     * Křestní jméno
     */
    JMENO(
        "jméno"
    ),

    /**
     * Příjmení
     */
    PRIJMENI(
        "příjmení"
    ),

    /**
     * Telefon
     */
    TELEFON(
        "telefon"
    ),

    /**
     * Věk
     */
    VEK(
        "věk"
    );


    public final String popisek;
    public final String popisekEditace;

    PopiskyEnum(
            String popisek
    ) {
        this.popisek = popisek.substring(0, 1).toUpperCase() + popisek.substring(1) + ": ";
        this.popisekEditace = "Nové " + popisek + ": ";
    }
}
