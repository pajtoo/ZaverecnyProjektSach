/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cz.itnetwork.evidencepojisteni.service;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.mapping.InsuredDTOEntityMapper;
import cz.itnetwork.evidencepojisteni.persistence.repository.InsuredRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return insuredRepository.findAll();
    }

    @Override
    public List<PojistenecDTO> najdiPojistene(String jmeno, String prijmeni) {
        throw new UnsupportedOperationException("Tato funkce zatím nebyla implementována");
    }

    @Override
    public PojistenecDTO najdiPojisteneho(int id) {
        throw new UnsupportedOperationException("Tato funkce zatím nebyla implementována");
    }

    @Override
    public void pridejPojisteneho(String jmeno, String prijmeni, int vek, String telefon) {
        throw new UnsupportedOperationException("Tato funkce zatím nebyla implementována");
    }

    @Override
    public boolean upravPojisteneho(int id, String noveJmeno, String novePrijmeni, int novyVek, String novyTelefon) {
        throw new UnsupportedOperationException("Tato funkce zatím nebyla implementována");
    }

    @Override
    public boolean odstranPojisteneho(int id) {
        throw new UnsupportedOperationException("Tato funkce zatím nebyla implementována");
    }
}
