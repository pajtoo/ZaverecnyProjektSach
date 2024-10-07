package cz.itnetwork.evidencepojisteni.controller;

import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenych;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        ui.vypisUvodniNabidku();
        String volba = ui.ziskejVstup();
        try {
        validator.zvaliduj(volba, true, ValidatorVstupu.ValidatorEnum.VOLBA_AKCE_V_MENU);
        } catch (InvalidUserInputException | NumberFormatException ex) {
            zpracujChybnyVstup(Exception ex);
        }

        boolean vstupValidni = false;
        while (!vstupValidni) {
            try {
                //volání validátoru null 1 6
                vstupValidni = true;
            }
            catch ()
            ui.ziskejVstup();

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
                    vypisZpravu("Děkujeme Vám za využití této aplikace! Nashledanou!");
                    isKonec = true;
                    break;
                default:
                    vypisZpravu("Neplatná volba.");
            }
        }
    }

    private void zpracujChybnyVstup(Exception ex) {
        // TODO přidat logování předej obecnému Handleru všech chyb
        ui.vypisChybovouHlasku(ex);
        ui.vyzviKOpakovaniZadani();

    }

    private String ziskejOpravenyVstup(NumberFormatException ex) {
        ui.vypisChy
    }
}
