package cz.itnetwork.evidencepojisteni.mapping;

public class PopiskyValidatorEnumMapper {
    private static final Map<FirstEnum, SecondEnum> enumMap = new HashMap<>();

    static {
        enumMap.put(PopiskyEnum.JMENO, ValidatorVstupu.ValidatorEnum.JMENO);
        enumMap.put(PopiskyEnum.PRIJMENI, ValidatorVstupu.ValidatorEnum.PRIJMENI);
        enumMap.put(PopiskyEnum.TELEFON, ValidatorVstupu.ValidatorEnum.TELEFON);
        enumMap.put(PopiskyEnum.VEK, ValidatorVstupu.ValidatorEnum.VEK);
    }

    public static ValidatorEnum map(PopiskyEnum popiskyEnum) {
        return enumMap.get(popiskyEnum);
    }

}