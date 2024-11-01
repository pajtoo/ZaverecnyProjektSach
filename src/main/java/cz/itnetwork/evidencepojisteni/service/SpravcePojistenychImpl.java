/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.service;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.mapping.InsuredDTOEntityMapper;
import cz.itnetwork.evidencepojisteni.persistence.entity.InsuredEntity;
import cz.itnetwork.evidencepojisteni.persistence.repository.InsuredRepository;
import cz.itnetwork.evidencepojisteni.view.enums.ZpravyOVysledkuOperaceEnum;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Třída je implementací pro perzistenci pomocí PostgreSQL
 *
 * @author Pavel Šach
 */
@Service
public class SpravcePojistenychImpl implements SpravcePojistenych {

    private final InsuredRepository insuredRepository;
    private final InsuredDTOEntityMapper insuredDTOEntityMapper;

    @Autowired
    public SpravcePojistenychImpl(InsuredRepository insuredRepository, InsuredDTOEntityMapper insuredDTOEntityMapper) {
        this.insuredRepository = insuredRepository;
        this.insuredDTOEntityMapper = insuredDTOEntityMapper;
    }

    public List<PojistenecDTO> vratVsechnyPojistene() {
        List<PojistenecDTO> pojistenci = new ArrayList<>();
        insuredRepository.findAll()
                .forEach(pojistenec -> {
                    pojistenci.add(insuredDTOEntityMapper.toDTO(pojistenec));
                });
        return pojistenci;
    }

    @Override
    public List<PojistenecDTO> najdiPojistene(PojistenecDTO hledanyPojistenec) {
        InsuredEntity insuredEntity = insuredDTOEntityMapper.toEntity(hledanyPojistenec);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id");
        Example<InsuredEntity> example = Example.of(insuredEntity, matcher);
        List<InsuredEntity> foundEntities = insuredRepository.findAll(example);
        List<PojistenecDTO> foundPersons = new ArrayList<>();
        for (InsuredEntity foundEntity : foundEntities) {
            foundPersons.add(insuredDTOEntityMapper.toDTO(foundEntity));
        }
        return foundPersons;
    }

    @Override
    public PojistenecDTO najdiPojistene(Long id) {
        InsuredEntity insuredEntity = insuredRepository
                .findById(id).
                orElseThrow(() -> new EntityNotFoundException(ZpravyOVysledkuOperaceEnum.INSURED_ID_NOT_FOUND.message + id);
        return insuredDTOEntityMapper.toDTO(insuredEntity);
    }

    @Override
    public PojistenecDTO ulozPojisteneho(PojistenecDTO pojistenec) {
        InsuredEntity insuredEntity = insuredRepository.save(insuredDTOEntityMapper.toEntity(pojistenec));
        return insuredDTOEntityMapper.toDTO(insuredEntity);
    }

    @Override
    public void odstranPojisteneho(PojistenecDTO pojistenec) {
        insuredRepository.delete(insuredDTOEntityMapper.toEntity(pojistenec));
    }
}
