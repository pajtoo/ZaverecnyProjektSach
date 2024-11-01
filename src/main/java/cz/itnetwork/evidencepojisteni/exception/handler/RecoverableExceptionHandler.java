package cz.itnetwork.evidencepojisteni.exception.handler;

import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RecoverableExceptionHandler {

    @Autowired
    private UzivatelskeRozhrani ui;
    private static final Logger log = LoggerFactory.getLogger(RecoverableExceptionHandler.class);


    /**
     * Zaloguje chybu a pošle zprávu uživateli. Určeno pro chybná zadání hodnoty od uživatele.
     * @param ex Výjimka, která nastala
     */
    public void zpracujVyjimku(Exception ex) {
        log.warn("A recoverable Exception has arisen: ", ex);
        ui.vypisChybovouHlasku(ex);
    }

    /**
     * Zaloguje chybu a pošle zprávu uživateli. Určeno pro chybná zadání hodnoty od uživatele.
     * @param ex Výjimka, která nastala
     */
    public void zpracujChybnyVstup(Exception ex) {
        log.info("An invalid user input has been entered. ", ex);
        ui.vypisChybovouHlasku(ex);
    }
}
