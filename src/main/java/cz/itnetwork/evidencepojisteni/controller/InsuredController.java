package cz.itnetwork.evidencepojisteni.controller;

import cz.itnetwork.evidencepojisteni.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenych;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import cz.itnetwork.evidencepojisteni.view.enums.PopiskyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
            int volba = Integer.parseInt(zajistiValidniVstup(ValidatorVstupu.ValidatorEnum.VOLBA_AKCE_V_MENU));

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
        List<PopiskyEnum> polozky = Arrays.asList(
            PopiskyEnum.JMENO, 
            PopiskyEnum.PRIJMENI,
            PopiskyEnum.TELEFON,
            PopiskyEnum.VEK
        );
        List<String> zadaneHodnoty = ui.pridejPojistence(polozky);

        HashMap<ValidatorVstupu.ValidatorEnum, String> vyplnenePolozky = new HashMap<>();
        // namapování ziskaných položek na položky validátoru
        for (PopiskyEnum polozka : polozky) {
            for (String zadanaHodnota : zadaneHodnoty) {
                vyplnenePolozky.put(
                    PopiskyValidatorEnumMapper.map(polozka),
                    zadanaHodnota
                );
            }       
        }

        List<String> zvalidovanePolozky = new ArrayList<>();
        zadaneHodnoty.forEach((key, value) -> {
            zvalidovanePolozky.add(zajistiValidniVstup(key, value));
        });
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
