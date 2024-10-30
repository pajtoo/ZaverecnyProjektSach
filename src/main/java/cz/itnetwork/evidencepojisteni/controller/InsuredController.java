package cz.itnetwork.evidencepojisteni.controller;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import cz.itnetwork.evidencepojisteni.mapping.InputDTOMapper;
import cz.itnetwork.evidencepojisteni.mapping.MappingDataProvider;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenych;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu.ValidatorEnum;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;
import cz.itnetwork.evidencepojisteni.view.enums.ZpravyOVysledkuOperaceEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InsuredController {

    private static final Logger logger = LoggerFactory.getLogger(InsuredController.class);
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
        ziskejVolbuZNabidky();
    }

    
    private void ziskejVolbuZNabidky() {
        boolean isKonec = false;
        while (!isKonec) {
            ui.vypisUvodniNabidku();
            int volba = Integer.parseInt(zajistiValidniVstup(ValidatorEnum.VOLBA_AKCE_V_MENU, true));

            switch (volba) {
                case 1:
                    vypisVsechnyPojistence();
                    break;
                case 2:
                    vyhledejPojistence();
                    break;
                case 3:
                    pridejPojistence();
                    break;
                case 4:
                    upravPojistence();
                    break;
                case 5:
                    odstranPojistence();
                    break;
                case 6:
                    ui.vypisZpravu("Děkujeme za využití aplikace. Nashledanou!");
                    isKonec = true;
                    break;
            }
        }
    }

    private void vypisVsechnyPojistence() {
        List<PojistenecDTO> pojistenci = spravcePojistenych.vratVsechnyPojistene();
        ui.vypisPojistence(pojistenci);
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
        PojistenecDTO savedData = spravcePojistenych.pridejPojisteneho(pojistenecDTO);
        ui.vypisZpravu(ZpravyOVysledkuOperaceEnum.CREATE_SUCCESS.message + savedData.getId());
    }

    private void vyhledejPojistence() {
        ui.zahajVyhledavaniPojisteneho();
        int volba = Integer.parseInt(zajistiValidniVstup(ValidatorEnum.VOLBA_ZPUSOBU_VYHLEDAVANI, true));
        List<String> hodnotyProVyhledavani = new ArrayList<>();
        switch (volba) {
            case 1:
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
                PojistenecDTO pojistenecDTO = inputDTOMapper.createDTO(PojistenecDTO.class, validniPolozky);

                //spravcePojistenych.najdiPojisteneho()
                break;
            case 2:
                List<PopiskyEnum> popisky = new ArrayList<>();
                popisky.add(PopiskyEnum.ID);
                hodnotyProVyhledavani = ui.ziskejHodnotyProVyhledavani(popisky);
                break;
        }

        ui.vypisPojistence(null);
    }
    private void upravPojistence() {
        //vyhledání pojištěnce a fetchnutí z databáze

        // Získání dat z uživatelského vstupu
        List<String> zadaneHodnoty = ui.upravPojistence(pojistenecMappingDataProvider.getFieldLabels());

        // Validace dat
        LinkedHashMap<ValidatorEnum, String> validatorArgumentObjects
                = getValidatorArgumentObject(zadaneHodnoty);
        List<String> zvalidovaneHodnoty = new ArrayList<>();
        validatorArgumentObjects.forEach((key, value) -> {
            zvalidovaneHodnoty.add(zajistiValidniVstup(key, false, value));
        });

        //


    }

    private void odstranPojistence() {
        throw new UnsupportedOperationException("Tato funkce zatím nebyla implementována");
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
            zpracujChybnyVstup(ex);
            ui.vyzviKOpakovaniZadani();
            return zajistiValidniVstup(validatorEnum, jePovinny);
        }
        return vstup;
    }

    /**
     * Zaloguje chybu a pošle zprávu uživateli
     * @param ex Výjimka, která nastala
     */
    private void zpracujChybnyVstup(Exception ex) {
        logger.info("An invalid user input has been entered. ", ex);
        ui.vypisChybovouHlasku(ex);
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
