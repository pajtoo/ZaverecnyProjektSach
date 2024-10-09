
public class PopiskyValidatorEnumMapper {
    private static final ArrayList<MappingObject> mappingList = new ArrayList<>();

    static {
        mappingList.add(new MappingObject(jmeno, PopiskyEnum.JMENO, ValidatorEnum.JMENO));
    }

    public static ValidatorEnum map(PopiskyEnum popiskyEnum) {
        return enumMap.get(popiskyEnum);
    }

}