package cz.itnetwork.evidencepojisteni.controller;

import cz.itnetwork.evidencepojisteni.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import cz.itnetwork.evidencepojisteni.mapping.MappingObject;
import cz.itnetwork.evidencepojisteni.mapping.PojistenecMapper;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenych;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu.ValidatorEnum;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class InsuredController {

    private static final Logger log = LoggerFactory.getLogger(InsuredController.class);
    private UzivatelskeRozhrani ui;
    private SpravcePojistenych spravcePojistenych;
    private ValidatorVstupu validator;

    public InsuredController(
            UzivatelskeRozhrani ui,
            SpravcePojistenych spravcePojistenych,
            ValidatorVstupu validator
            ) {
        this.ui = ui;
        this.spravcePojistenych = spravcePojistenych;
        this.validator = validator;
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
        Map<String,MappingObject> itemsMap = ziskejMappingAtributu();
        List<String> zadaneHodnoty = ui.pridejPojistence(
                itemsMap.values().stream()
                        .map(MappingObject::getPopisek)
                        .toList()
        );

        HashMap<ValidatorEnum, String> vyplnenePolozky = new HashMap<>();
        // namapování ziskaných položek na položky validátoru
        for (PopiskyEnum polozka : itemsMap) {
            for (String zadanaHodnota : zadaneHodnoty) {
                vyplnenePolozky.put(
                        itemsMap.values().stream()
                                .map(MappingObject::getPopisek)
                                .toList(),
                    zadanaHodnota
                );
            }       
        }

        List<String> zvalidovanePolozky = new ArrayList<>();
        zadaneHodnoty.forEach((key, value) -> {
            zvalidovanePolozky.add(zajistiValidniVstup(key, value));
        });
    }

    /**
     * Získá slovník (map) s položkami (atribut, MappingObject) ke každému z atributů přítomných v PojištěnecDTO s výjimkou id.
     * Poskytne aktuální soubor položek zjišťovaných u pojištěnce, který slouží jako prostředek k
     * automatizaci procesu získávání hodnot k jednotlivým položkám.
     * @return slovník s mapovacími objekty k atributům přítomným v PojištěnecDTO be id
     */
    private Map<String, MappingObject> ziskejMappingAtributu() {
        Field[] atributy = PojistenecDTO.class.getFields();
        Map<String, MappingObject> mapAtributyBezId = new HashMap<>();
        //cyklu
        for (int i = 1; i < atributy.length; i++) {
            for (Map.Entry<String, MappingObject> entry : PojistenecMapper.getMappingMap().entrySet()) {
                String key = entry.getKey();
                MappingObject value = entry.getValue();
                if (atributy[i].getName().equals(key)) {
                    mapAtributyBezId.put(key, value);
                    break;
                }
            }
        }
        return mapAtributyBezId;
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
    private String zajistiValidniVstup(ValidatorVstupu.ValidatorEnum validatorEnum, String... vstupVolitelny) {   
        String vstup;
        if (vstupVolitelny.length > 0) {
            vstup = vstupVolitelny[0];
        } else
            vstup = ui.ziskejVstup();
        try {
            vstup = validator.zvaliduj(volba, validatorEnum);
        } catch (InvalidUserInputException | NumberFormatException ex) {
            zpracujChybnyVstup(ex);
            ui.vyzviKOpakovaniZadani();
            return zajistiValidniVstup(vstup, validatorEnum);
        }
        return vstup;
    }

    private void zpracujChybnyVstup(Exception ex) {
        // zpracujVyjimku() TODO: přidat logování, předej obecnému Handleru všech chyb a cele to dat do Exception Handler třídy
        ui.vypisChybovouHlasku(ex);
    }
}
