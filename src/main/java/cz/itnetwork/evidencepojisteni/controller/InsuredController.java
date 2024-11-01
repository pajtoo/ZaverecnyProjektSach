package cz.itnetwork.evidencepojisteni.controller;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.exception.DatabaseOperationException;
import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import cz.itnetwork.evidencepojisteni.exception.handler.RecoverableExceptionHandler;
import cz.itnetwork.evidencepojisteni.mapping.InputDTOMapper;
import cz.itnetwork.evidencepojisteni.mapping.MappingDataProvider;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenych;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu.ValidatorEnum;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;
import cz.itnetwork.evidencepojisteni.view.enums.ZpravyOVysledkuOperaceEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InsuredController {

    @Autowired
    private RecoverableExceptionHandler exceptionHandler;
    private final UzivatelskeRozhrani ui;
    private final SpravcePojistenych spravcePojistenych;
    private final ValidatorVstupu validator;
    private final MappingDataProvider pojistenecMappingDataProvider;
    private final InputDTOMapper<PojistenecDTO> inputDTOMapper;

    public InsuredController(
            UzivatelskeRozhrani ui,
            SpravcePojistenych spravcePojistenych,
            ValidatorVstupu validator,
            MappingDataProvider pojistenecMappingDataProvider,
            InputDTOMapper<PojistenecDTO> inputDTOMapper
    ) {
        this.ui = ui;
        this.spravcePojistenych = spravcePojistenych;
        this.validator = validator;
        this.pojistenecMappingDataProvider = pojistenecMappingDataProvider;
        this.inputDTOMapper = inputDTOMapper;
    }

    public void run() {
        ui.vykresliUvodniObrazovku();
        ziskejVolbuZUvodniNabidky();
    }

    
    private void ziskejVolbuZUvodniNabidky() {
        boolean isKonec = false;
        while (!isKonec) {
            ui.vypisUvodniNabidku();
            int volba = Integer.parseInt(zajistiValidniVstup(ValidatorEnum.VOLBA_AKCE_V_MENU, true));

            switch (volba) {
                case 1:
                    ziskejVsechnyPojistence();
                    break;
                case 2:
                    vyhledejPojistence();
                    break;
                case 3:
                    pridejPojistence();
                    break;
                case 4:
                    ui.vypisZpravu("Děkujeme za využití aplikace. Nashledanou!");
                    isKonec = true;
                    break;
            }
        }
    }

    private void ziskejVsechnyPojistence() {
        List<PojistenecDTO> pojistenci = spravcePojistenych.vratVsechnyPojistene();
        vratNalezenePojistence(pojistenci);
    }

    private void vyhledejPojistence() {
        ui.zahajVyhledavaniPojisteneho();
        int volba = Integer.parseInt(zajistiValidniVstup(ValidatorEnum.VOLBA_ZPUSOBU_VYHLEDAVANI, true));
        List<PojistenecDTO> vyhledaniPojistenci = new ArrayList<>();
        switch (volba) {
            case 1:
                vyhledaniPojistenci = vyhledejPodleParametru();
                break;
            case 2:
                PojistenecDTO nalezenyPojistenec = vyhledejPodleId();
                vyhledaniPojistenci.add(nalezenyPojistenec);
                break;
        }
        vratNalezenePojistence(vyhledaniPojistenci);
    }

    private List<PojistenecDTO> vyhledejPodleParametru() {
        List<PojistenecDTO> vyhledaniPojistenci;
        List<String> hodnotyProVyhledavani;
        // Získání dat z uživatelského vstupu
        hodnotyProVyhledavani = ui.ziskejHodnotyProVyhledavani(pojistenecMappingDataProvider.getFieldLabels());
        // Validace
        LinkedHashMap<ValidatorEnum, String> validatorArgumentObjects = getValidatorArgumentObject(hodnotyProVyhledavani);
        List<String> zvalidovaneHodnoty = new ArrayList<>();
        validatorArgumentObjects.forEach((key, value) -> {
            zvalidovaneHodnoty.add(zajistiValidniVstup(key, false, value));
        });
        // Namapování na PojistenecDTO
        Map<String, String> validniPolozky = getMapperArgumentObject(zvalidovaneHodnoty);
        PojistenecDTO hledanyPojistenec = new PojistenecDTO();
        inputDTOMapper.updateDTO(hledanyPojistenec, validniPolozky);
        try {
            vyhledaniPojistenci = spravcePojistenych.najdiPojistene(hledanyPojistenec);
        } catch (DataAccessException | PersistenceException ex) {
            DatabaseOperationException exception = new DatabaseOperationException(ZpravyOVysledkuOperaceEnum.SEARCH_FAIL.message, ex);
            exceptionHandler.zpracujVyjimku(exception);
        }
        return vyhledaniPojistenci;
    }

    private PojistenecDTO vyhledejPodleId() {
        List<PopiskyEnum> popisky = new ArrayList<>();
        popisky.add(PopiskyEnum.ID);
        List<String> hodnotyProVyhledavani = ui.ziskejHodnotyProVyhledavani(popisky);
        Long zvalidovaneId = Long.parseLong(zajistiValidniVstup(ValidatorEnum.ID, true, hodnotyProVyhledavani.getFirst()));

        PojistenecDTO nalezenyPojistenec = new PojistenecDTO();
        try {
            nalezenyPojistenec = spravcePojistenych.najdiPojistene(zvalidovaneId);
        } catch (EntityNotFoundException ex) {
            exceptionHandler.zpracujVyjimku(ex);
        } catch (DataAccessException | PersistenceException ex) {
            DatabaseOperationException exception = new DatabaseOperationException(ZpravyOVysledkuOperaceEnum.SEARCH_FAIL.message, ex);
        }
        return nalezenyPojistenec;
    }

    private void vratNalezenePojistence(List<PojistenecDTO> pojistenci) {
        ui.vypisPojistence(pojistenci);
        if (!pojistenci.isEmpty()) {
            zahajPraciSPojistencem(pojistenci);
        } else ziskejVolbuZUvodniNabidky();
    }

    private void zahajPraciSPojistencem(List<PojistenecDTO> pojistenci) {
        String volba = ui.ziskejVolbuPraceSPojistencem();
        int volbaCislo = Integer.parseInt(zajistiValidniVstup(ValidatorEnum.VOLBA_AKCE_V_MENU, true, volba));
        PojistenecDTO pojistenec = null;
        if (pojistenci.size() > 1) {
            // Získání validního id
            List<PopiskyEnum> popisky = new ArrayList<>();
            popisky.add(PopiskyEnum.ID);
            List<String> hodnotyProVyhledavani = ui.ziskejHodnotyProVyhledavani(popisky);

            Long zvalidovaneId = Long.parseLong(zajistiValidniVstup(ValidatorEnum.ID, true, hodnotyProVyhledavani.getFirst()));

            // Vyhledání pojištěnce v Listu
            for (PojistenecDTO pojistenecDTO : pojistenci) {
                if (pojistenecDTO.getId().equals(zvalidovaneId)) {
                    pojistenec = pojistenecDTO;
                    break;
                }
            }
            if (pojistenec == null) {
                ui.vypisZpravu(ZpravyOVysledkuOperaceEnum.ID_NOT_FOUND_IN_FETCHED_INSURED.message + zvalidovaneId);
                zahajPraciSPojistencem(pojistenci);
            }
        } else pojistenec = pojistenci.getFirst();

        switch (volbaCislo) {
            case 1:
                upravPojistence(pojistenec);
                break;
            case 2:
                odstranPojistence(pojistenec);
            case 3:
                ziskejVolbuZUvodniNabidky();
        }
    }

    private void pridejPojistence() {
        // Získání dat z uživatelského vstupu
        List<String> zadaneHodnoty = ui.pridejPojistence(pojistenecMappingDataProvider.getFieldLabels());

        // Validace dat
        LinkedHashMap<ValidatorEnum, String> validatorArgumentObjects = getValidatorArgumentObject(zadaneHodnoty);
        List<String> zvalidovaneHodnoty = new ArrayList<>();
        validatorArgumentObjects.forEach((key, value) -> {
            zvalidovaneHodnoty.add(zajistiValidniVstup(key, true, value));
        });

        // Namapování na PojistenecDTO
        Map<String, String> validniPolozky = getMapperArgumentObject(zvalidovaneHodnoty);
        PojistenecDTO pojistenecDTO = inputDTOMapper.createDTO(PojistenecDTO.class, validniPolozky);

        // Uložení do databáze
        try {
            PojistenecDTO savedData = spravcePojistenych.ulozPojisteneho(pojistenecDTO);
            ui.vypisZpravu(ZpravyOVysledkuOperaceEnum.CREATE_SUCCESS.message + savedData.getId());
        } catch (DataAccessException | PersistenceException ex) {
            DatabaseOperationException exception = new DatabaseOperationException(ZpravyOVysledkuOperaceEnum.SAVE_FAIL.message, ex);
        }
    }

    private void upravPojistence(PojistenecDTO pojistenec) {

        // Získání dat z uživatelského vstupu
        List<String> zadaneHodnoty = ui.upravPojistence(pojistenecMappingDataProvider.getFieldLabels());

        // Validace dat
        LinkedHashMap<ValidatorEnum, String> validatorArgumentObjects
                = getValidatorArgumentObject(zadaneHodnoty);
        List<String> zvalidovaneHodnoty = new ArrayList<>();
        validatorArgumentObjects.forEach((key, value) -> {
            zvalidovaneHodnoty.add(zajistiValidniVstup(key, false, value));
        });

        // Namapování na PojistenecDTO
        Map<String, String> validniPolozky = getMapperArgumentObject(zvalidovaneHodnoty);
        PojistenecDTO updatedDTO = inputDTOMapper.updateDTO(pojistenec, validniPolozky);

        // Uložení do databáze
        try {
            spravcePojistenych.ulozPojisteneho(updatedDTO);
            ui.vypisZpravu(ZpravyOVysledkuOperaceEnum.UPDATE_SUCCESS.message);
        } catch (DataAccessException | PersistenceException ex) {
            DatabaseOperationException exception = new DatabaseOperationException(ZpravyOVysledkuOperaceEnum.SAVE_FAIL.message, ex);
            exceptionHandler.zpracujVyjimku(exception);
        }
    }

    private void odstranPojistence(PojistenecDTO pojistenec) {
        try {
            spravcePojistenych.odstranPojisteneho(pojistenec);
        } catch (DataAccessException | PersistenceException ex) {
            DatabaseOperationException exception = new DatabaseOperationException(ZpravyOVysledkuOperaceEnum.DELETE_FAIL.message, ex);
            exceptionHandler.zpracujVyjimku(exception);
        }
    }

    /**
     * Metoda zajistí validaci vstupu od uživatele. Je možné ji volat bez předání vstupu v argumentu. Tato možnost slouží pro
     * případ, že původní předaný vstup není validní a metoda volá samu sebe a sama volá po získání nového vstupu od uživatele.
     * @param validatorEnum Identifikátor a parametrizace validace vstupu
     * @param vstupVolitelny Textový vstup - nepovinný argument (zpracuje pouze první položku z pole)
     * @return Validní a standardizovaný vstup
     */
    private String zajistiValidniVstup(ValidatorEnum validatorEnum, boolean jePovinny, String... vstupVolitelny) {
        String vstup;
        if (vstupVolitelny.length > 0) {
            vstup = vstupVolitelny[0];
        } else
            vstup = ui.ziskejVstup();
        try {
            vstup = validator.zvaliduj(validatorEnum, jePovinny, vstup);
        } catch (InvalidUserInputException | NumberFormatException ex) {
            exceptionHandler.zpracujChybnyVstup(ex);
            ui.vyzviKOpakovaniZadani();
            return zajistiValidniVstup(validatorEnum, jePovinny);
        }
        return vstup;
    }

    private Map<String, String> getMapperArgumentObject(List<String> zvalidovaneHodnoty) {
        // Seznam atributů
        List<String> seznamAtributu = pojistenecMappingDataProvider.getFieldNames();
        //Příprava slovníku pro InputDTOMapper
        Map<String, String> validniPolozky= new HashMap<>();
        for (int i = 0; i < zvalidovaneHodnoty.size(); i++) {
            validniPolozky.put(
                    seznamAtributu.get(i),
                    zvalidovaneHodnoty.get(i)
            );
        }
        return validniPolozky;
    }

    private LinkedHashMap<ValidatorEnum, String> getValidatorArgumentObject(List<String> zadaneHodnoty) {
        LinkedHashMap<ValidatorEnum, String> hodnotyKValidaci = new LinkedHashMap<>();
        for (ValidatorEnum fieldCriteria : pojistenecMappingDataProvider.getValidatorCriteria()) {
            for (String zadanaHodnota : zadaneHodnoty) {
                hodnotyKValidaci.put(
                        fieldCriteria,
                        zadanaHodnota
                );
            }
        }
        return hodnotyKValidaci;
    }
}
