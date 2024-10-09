package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;

import java.util.ArrayList;
import java.util.List;

public class PojistenecMapper {
    private static final List<MappingObject> mappingList = new ArrayList<>();

    static {
        mappingList.add(new MappingObject("jmeno", PopiskyEnum.JMENO, ValidatorVstupu.ValidatorEnum.JMENO));
        mappingList.add(new MappingObject("prijmeni", PopiskyEnum.PRIJMENI, ValidatorVstupu.ValidatorEnum.PRIJMENI));
        mappingList.add(new MappingObject("vek", PopiskyEnum.VEK, ValidatorVstupu.ValidatorEnum.VEK));
        mappingList.add(new MappingObject("telefon", PopiskyEnum.TELEFON, ValidatorVstupu.ValidatorEnum.TELEFON));
    }

    public static List<MappingObject> getMappingList() {
        return mappingList;
    }
}