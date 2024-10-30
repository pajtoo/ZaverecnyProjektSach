package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class InputDTOMapperTest {

    private final InputDTOMapper<PojistenecDTO> inputDTOMapper = new InputDTOMapper<>();
    private final PojistenecDTO pojistenecDTO = new PojistenecDTO(
            "Milan",
            null,
            24,
            "+420123456789"
    );

    @Test
    void createDTO() {
        Map<String, String> mapperArgumentObject = getMapperArgumentObject(pojistenecDTO);
        PojistenecDTO createdDTO = inputDTOMapper.createDTO(PojistenecDTO.class, mapperArgumentObject);

        assertThat(createdDTO).usingRecursiveComparison().isEqualTo(pojistenecDTO);
    }

    @Test
    void updateDTO() {
        Map<String, String> valuesBeingUpdated = new HashMap<>();
        valuesBeingUpdated.put("jmeno", "Karel");
        PojistenecDTO updatedDTO = inputDTOMapper.updateDTO(pojistenecDTO, valuesBeingUpdated);
        pojistenecDTO.setJmeno("Karel");

        assertThat(updatedDTO).usingRecursiveComparison().isEqualTo(pojistenecDTO);
    }

    /**
     * Tato metoda slouží k tomu, abychom nemuseli ručně vytvářet Map s hodnotami pro jednotlivé atributy DTO
     * @param <T> DTO
     * @return Map s hodnotami (key - název atributu, value - hodnota)
     */
    private <T> Map<String, String> getMapperArgumentObject(T dto) {
        Map<String, String> polozky = new HashMap<>();

        Arrays.stream(dto.getClass().getMethods())
                .filter(metoda ->
                        metoda.getName().substring(0,3).equalsIgnoreCase("get") &&
                                !metoda.getName().equalsIgnoreCase("getId")
                ).forEach(metoda -> {
                    String value;
                    try {
                        if (metoda.invoke(dto) == null) {
                            value = null;
                        } else value = String.valueOf(metoda.invoke(dto));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    polozky.put(metoda.getName().substring(3).toLowerCase(), value);
                });
        return polozky;
    }
}