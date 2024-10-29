package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Třída slouží jako úložiště mapovacích položek atributů různých DTO (Data Transfer Objects) používaných v aplikaci.
 * Klíč položky je jedinečným identifikátorem třídy a atributu, hodnota je MappingObject, který obsahuje všechny relevantní informace
 * spojené s daným atributem, nezbytné pro fungování dalších komponent a služeb aplikace.
 */
public class FieldMap {

    // Slovník obsahující
    private static final LinkedHashMap<String,MappingObject> MAPPING_MAP = new LinkedHashMap<>();

    // Insured mapping
    static {
        MAPPING_MAP.put("PojistenecDTO:jmeno", new MappingObject(PopiskyEnum.JMENO, ValidatorVstupu.ValidatorEnum.JMENO));
        MAPPING_MAP.put("PojistenecDTO:prijmeni", new MappingObject(PopiskyEnum.PRIJMENI, ValidatorVstupu.ValidatorEnum.PRIJMENI));
        MAPPING_MAP.put("PojistenecDTO:vek", new MappingObject(PopiskyEnum.VEK, ValidatorVstupu.ValidatorEnum.VEK));
        MAPPING_MAP.put("PojistenecDTO:telefon", new MappingObject(PopiskyEnum.TELEFON, ValidatorVstupu.ValidatorEnum.TELEFON));
    }

    private static LinkedHashMap<String, MappingObject> getMappingMap() {
        return MAPPING_MAP;
    }

    /**
     * Vrací slovník (map) s položkami (atribut, MappingObject) ke každému z atributů přítomných v předané třídě s výjimkou id.
     * Potřebné k iteraci jednotlivých atributů.
     * @param clazz Třída, k jejímž atributům potřebujeme získat slovníkové položky.
     * @return slovník s mapovacími objekty k atributům přítomným v PojištěnecDTO bez id
     */
    public static LinkedHashMap<String, MappingObject> getFieldMapping(Class<?> clazz) {
        Field[] atributy = clazz.getDeclaredFields();
        LinkedHashMap<String, MappingObject> mapAtributyBezId = new LinkedHashMap<>();
        // Cyklus, který ze slovníku vybere pouze relevantní položky
        for (int i = 0; i < atributy.length; i++) {
            for (Map.Entry<String, MappingObject> entry : getMappingMap().entrySet()) {
                String key = entry.getKey();
                MappingObject value = entry.getValue();

                // Identifikace třídy a atributu položky
                String keyClass, keyIdentificator;
                int separatorPosition = key.indexOf(":");
                keyClass = key.substring(0, separatorPosition);
                keyIdentificator = key.substring(separatorPosition + 1);

                // Vytvoření slovníku, v němž klíčem je jméno atributu a hodnotou MappingObject
                if (
                        clazz.getSimpleName().equalsIgnoreCase(keyClass) &&
                        atributy[i].getName().equalsIgnoreCase(keyIdentificator) &&
                        !atributy[i].getName().equalsIgnoreCase("id")) {
                    mapAtributyBezId.put(keyIdentificator, value);
                    break;
                }
            }
        }
        return mapAtributyBezId;
    }

}