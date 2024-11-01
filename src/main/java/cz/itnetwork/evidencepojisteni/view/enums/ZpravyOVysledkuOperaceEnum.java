package cz.itnetwork.evidencepojisteni.view.enums;

public enum ZpravyOVysledkuOperaceEnum {
    ITEMS_NOT_FOUND ("Nenalezeny žádné záznamy."),
    INSURED_ID_NOT_FOUND ("Pojištěnec se zadaným ID nebyl nalezen. Id: "),
    SEARCH_FAIL ("Při vyhledávání v databázi nastala chyba. "),
    CREATE_SUCCESS ("Nový pojištěný byl úspěšně přidán. Id: "),
    SAVE_FAIL ("Při ukládání pojištěného nastala chyba. "),
    UPDATE_SUCCESS ("Údaje pojištěnce byly upraveny. "),
    DELETE_SUCCESS ("Pojištěný byl úspěšně odstraněn."),
    DELETE_FAIL("Při odstraňování pojištěného nastala chyba."),
    ID_NOT_FOUND_IN_FETCHED_INSURED ("Pojištěnec s tímto ID ve vyhledaných osobách není. Id: ");

    ZpravyOVysledkuOperaceEnum(String message) {
        this.message = message;
    }

    public final String message;
}