package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

class FieldMapTest {

    @Test
    void getFieldMapping() {
        Map<String, MappingObject> fieldMapping = FieldMap.getFieldMapping(PojistenecDTO.class);

        assertThat(fieldMapping).isNotEmpty();
        assertThat(fieldMapping).doesNotContainKeys("id", "ID", "Id");
    }
}