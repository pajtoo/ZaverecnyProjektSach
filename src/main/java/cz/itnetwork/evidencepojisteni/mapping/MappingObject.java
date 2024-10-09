package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu.ValidatorEnum;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;

public class MappingObject {

    public MappingObject(
        String nazevAtributu,
        PopiskyEnum popisek,
        ValidatorEnum validator
    ) {
        this.nazevAtributu = nazevAtributu;
        this.popisek = popisek;
        this.validator = validator;
    }

    private final String nazevAtributu;
    private final PopiskyEnum popisek;
    private final ValidatorEnum validator;

    public String getNazevAtributu() {
        return nazevAtributu;
    }

    public PopiskyEnum getPopisek() {
        return popisek;
    }

    public ValidatorEnum getValidator() {
        return validator;
    }
}
