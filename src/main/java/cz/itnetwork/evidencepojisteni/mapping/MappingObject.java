package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu.ValidatorEnum;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;

public class MappingObject {

    public MappingObject(
            PopiskyEnum popisek,
            ValidatorEnum validator
    ) {
        this.popisek = popisek;
        this.validator = validator;
    }

    private final PopiskyEnum popisek;
    private final ValidatorEnum validator;

    public PopiskyEnum getPopisek() {
        return popisek;
    }

    public ValidatorEnum getValidator() {
        return validator;
    }
}
