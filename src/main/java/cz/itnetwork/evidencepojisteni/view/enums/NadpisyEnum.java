package cz.itnetwork.evidencepojisteni.view.enums;

public enum NadpisyEnum {
    VYPIS("----- Přehled pojištěných osob (ID: přijmení, jméno, věk, tel.) -----%n"),
    VYHLEDAVANI(String.format("----- Vyhledávání pojištěných -----%n" +
                    "Zvolte si prosím vyhledávací kritérium: %n" +
                    "1 - Podle osobních údajů%n" +
                    "2 - Podle ID%n")),
    PRIDANI("----- Přidání nového pojištěného -----%n"),
    UPRAVA(String.format("--------- Editace pojištěného --------%n" +
            "- prázdné pole = původní hodnota -%n")),
    ODSTRANENI("----- Odstranění pojištěnce -----%n" +
            "----- Odstranění pojištěnce -----%n");

    NadpisyEnum(String nadpis) {
        this.nadpis = nadpis;
    }

    public final String nadpis;

}
