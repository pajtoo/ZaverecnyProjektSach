package cz.itnetwork.evidencepojisteni.exception;

import cz.itnetwork.evidencepojisteni.controller.InsuredController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(InsuredController.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("An uncaught exception has occured: ", e);
        System.out.println("V aplikaci se vyskytla chyba, obraťte se prosím na zákaznickou podporu. ");
        System.out.println("Aplikace se nyní ukončí. ");
    }
}
