package cz.itnetwork.evidencepojisteni.controller;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import cz.itnetwork.evidencepojisteni.mapping.MappingDataProvider;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenych;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu.ValidatorEnum;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class InsuredController {

    private static final Logger log = LoggerFactory.getLogger(InsuredController.class);
    private UzivatelskeRozhrani ui;
    private SpravcePojistenych spravcePojistenych;
    private ValidatorVstupu validator;
    private MappingDataProvider pojistenecMappingDataProvider;

    public InsuredController(
            UzivatelskeRozhrani ui,
            SpravcePojistenych spravcePojistenych,
            ValidatorVstupu validator,
            MappingDataProvider pojistenecMappingDataProvider
            ) {
        this.ui = ui;
        this.spravcePojistenych = spravcePojistenych;
        this.validator = validator;
        this.pojistenecMappingDataProvider = pojistenecMappingDataProvider;
    }

    public void run() {
        ui.vykresliUvodniObrazovku();
        ziskejVolbuZNabidky();
    }

    
    private void ziskejVolbuZNabidky() {
        boolean isKonec = false;
        while (!isKonec) {
            ui.vypisUvodniNabidku();
            int volba = Integer.parseInt(zajistiValidniVstup(ValidatorEnum.VOLBA_AKCE_V_MENU));

            switch (volba) {
                case 1:
                    vypisVsechnyPojistence();
                    break;
                case 2:
                    vypisVyhledanePojistence();
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
        ui.vypisVsechnyPojistence(pojistenci);
    }

    private void pridejPojistence() {
        // Získání dat z uživatelského vstupu
        List<String> zadaneHodnoty = ui.pridejPojistence(pojistenecMappingDataProvider.getFieldLabels());

        // Příprava vstupních dat pro validátor
        HashMap<ValidatorEnum, String> hodnotyKValidaci = new HashMap<>();
        for (ValidatorEnum fieldCriteria : pojistenecMappingDataProvider.getValidatorCriteria()) {
            for (String zadanaHodnota : zadaneHodnoty) {
                hodnotyKValidaci.put(
                    fieldCriteria,
                    zadanaHodnota
                );
            }       
        }

        // Validace dat
        List<String> zvalidovaneHodnoty = new ArrayList<>();
        hodnotyKValidaci.forEach((key, value) -> {
            zvalidovaneHodnoty.add(zajistiValidniVstup(key, value));
        });
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

        // Namapování na PojistenecDTO
        PojistenecDTO pojistenecDTO = new PojistenecDTO();
        PojistenecDTO.class.getConstructor()
    }



    private void vypisVyhledanePojistence() {
        ui.vypis
    }

    /**
     * Metoda zajistí validaci vstupu od uživatele. Je možné ji volat bez předání vstupu v argumentu. Tato možnost slouží pro
     * případ, že původní předaný vstup není validní a metoda volá samu sebe a sama volá po získání nového vstupu od uživatele.
     * @param validatorEnum Identifikátor vstupu
     * @param vstupVolitelny Textový vstup - nepovinný argument (zpracuje pouze první položku z pole)
     * @return Validní a standardizovaný vstup
     */
    private String zajistiValidniVstup(ValidatorEnum validatorEnum, String... vstupVolitelny) {
        String vstup;
        if (vstupVolitelny.length > 0) {
            vstup = vstupVolitelny[0];
        } else
            vstup = ui.ziskejVstup();
        try {
            vstup = validator.zvaliduj(validatorEnum, vstup);
        } catch (InvalidUserInputException | NumberFormatException ex) {
            zpracujChybnyVstup(ex);
            ui.vyzviKOpakovaniZadani();
            return zajistiValidniVstup(validatorEnum, vstup);
        }
        return vstup;
    }

    private void zpracujChybnyVstup(Exception ex) {
        // zpracujVyjimku() TODO: přidat logování, předej obecnému Handleru všech chyb a cele to dat do Exception Handler třídy
        ui.vypisChybovouHlasku(ex);
    }
}
