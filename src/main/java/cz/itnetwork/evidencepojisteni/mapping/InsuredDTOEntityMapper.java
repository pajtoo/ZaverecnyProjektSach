package cz.itnetwork.evidencepojisteni.mapping;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.persistence.entity.InsuredEntity;
import org.mapstruct.Mapper;

@Mapper
public interface InsuredDTOEntityMapper {

    PojistenecDTO toDTO(InsuredEntity insuredEntity);
    InsuredEntity toEntity(PojistenecDTO pojistenecDTO);

}
