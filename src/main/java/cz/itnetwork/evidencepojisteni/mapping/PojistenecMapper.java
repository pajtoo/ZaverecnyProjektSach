package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;
import java.util.HashMap;
import java.util.Map;

public class PojistenecMapper {
    private static final Map<String,MappingObject> MAPPING_MAP = new HashMap<String, MappingObject>();

    static {
        MAPPING_MAP.put("jmeno", new MappingObject(PopiskyEnum.JMENO, ValidatorVstupu.ValidatorEnum.JMENO));
        MAPPING_MAP.put("prijmeni", new MappingObject(PopiskyEnum.PRIJMENI, ValidatorVstupu.ValidatorEnum.PRIJMENI));
        MAPPING_MAP.put("vek", new MappingObject(PopiskyEnum.VEK, ValidatorVstupu.ValidatorEnum.VEK));
        MAPPING_MAP.put("telefon", new MappingObject(PopiskyEnum.TELEFON, ValidatorVstupu.ValidatorEnum.TELEFON));
    }

    public static Map<String, MappingObject> getMappingMap() {
        return MAPPING_MAP;
    }
}