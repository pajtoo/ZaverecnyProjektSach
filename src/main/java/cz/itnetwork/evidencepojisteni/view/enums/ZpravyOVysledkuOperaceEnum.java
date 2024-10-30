package cz.itnetwork.evidencepojisteni.view.enums;

public enum ZpravyOVysledkuOperaceEnum {
    ITEMS_NOT_FOUND("Nenalezeny žádné záznamy."),
    INSURED_ID_NOT_FOUND ("Pojištěnec se zadaným ID nebyl nalezen."),
    CREATE_SUCCESS ("Nový pojištěný byl úspěšně přidán. Id pojištěného: "),
    UPDATE_SUCCESS ("Údaje pojištěnce byly upraveny."),
    DELETE_SUCCESS ("Pojištěný byl úspěšně odstraněn.");

    ZpravyOVysledkuOperaceEnum(String message) {
        this.message = message;
    }

    public final String message;
}