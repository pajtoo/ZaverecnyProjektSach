package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class FieldMap {
    private static final java.util.Map<String,MappingObject> MAPPING_MAP = new HashMap<String, MappingObject>();

    // Insured mapping
    static {
        MAPPING_MAP.put("jmeno", new MappingObject(PopiskyEnum.JMENO, ValidatorVstupu.ValidatorEnum.JMENO));
        MAPPING_MAP.put("prijmeni", new MappingObject(PopiskyEnum.PRIJMENI, ValidatorVstupu.ValidatorEnum.PRIJMENI));
        MAPPING_MAP.put("vek", new MappingObject(PopiskyEnum.VEK, ValidatorVstupu.ValidatorEnum.VEK));
        MAPPING_MAP.put("telefon", new MappingObject(PopiskyEnum.TELEFON, ValidatorVstupu.ValidatorEnum.TELEFON));
    }

    private static java.util.Map<String, MappingObject> getMappingMap() {
        return MAPPING_MAP;
    }

    /**
     * Vrací slovník (map) s položkami (atribut, MappingObject) ke každému z atributů přítomných v předané třídě s výjimkou id.
     * Potřebné k iteraci jednotlivých atributů.
     * @param clazz Třída, k jejímž atributům potřebujeme získat slovníkové položky.
     * @return slovník s mapovacími objekty k atributům přítomným v PojištěnecDTO be id
     */
    public static java.util.Map<String, MappingObject> getFieldMapping(Class<?> clazz) {
        Field[] atributy = clazz.getFields();
        Map<String, MappingObject> mapAtributyBezId = new HashMap<>();
        //cyklu
        for (int i = 1; i < atributy.length; i++) {
            for (Map.Entry<String, MappingObject> entry : getMappingMap().entrySet()) {
                String key = entry.getKey();
                MappingObject value = entry.getValue();
                if (atributy[i].getName().equals(key)) {
                    mapAtributyBezId.put(key, value);
                    break;
                }
            }
        }
        return mapAtributyBezId;
    }

}