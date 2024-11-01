package cz.itnetwork.evidencepojisteni.view.enums;

public enum ZpravyOVysledkuOperaceEnum {
    ITEMS_NOT_FOUND("Nenalezeny žádné záznamy."),
    INSURED_ID_NOT_FOUND ("Pojištěnec se zadaným ID nebyl nalezen. Id: "),
    CREATE_SUCCESS ("Nový pojištěný byl úspěšně přidán. Id: "),
    UPDATE_SUCCESS ("Údaje pojištěnce byly upraveny. "),
    DELETE_SUCCESS ("Pojištěný byl úspěšně odstraněn."),
    ID_NOT_FOUND_IN_FETCHED_INSURED ("Pojištěnec s tímto ID ve vyhledaných osobách není. Id: ");

    ZpravyOVysledkuOperaceEnum(String message) {
        this.message = message;
    }

    public final String message;
}