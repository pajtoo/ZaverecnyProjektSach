package cz.itnetwork.evidencepojisteni.validation;

import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import org.junit.jupiter.api.Test;
import utils.TestIntegerGenerator;
import utils.TestStringGenerator;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class ValidatorVstupuTest {

    private final ValidatorVstupu validatorVstupu = new ValidatorVstupu();
    private final TestStringGenerator testStringGenerator = new TestStringGenerator();
    private final TestIntegerGenerator testIntegerGenerator = new TestIntegerGenerator();

    @Test
    void zvalidujDelkuTextu() throws InvalidUserInputException {
        int min = ValidatorVstupu.ValidatorEnum.PRIJMENI.getMin();
        int max = ValidatorVstupu.ValidatorEnum.PRIJMENI.getMax();
        List<String> validStrings = testStringGenerator.generateValidStrings(min, max, 'A');
        List<String> invalidStrings = testStringGenerator.generateInvalidStrings(min, max, 'A');

        for (String validString : validStrings) {
            String zvalidovanyVstup = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.PRIJMENI, true, validString);
            assertThat(zvalidovanyVstup).isEqualTo(validString);
        }

        for (String invalidString : invalidStrings) {
            assertThatThrownBy(() -> {
                validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.PRIJMENI, true, invalidString);
            }).isInstanceOf(InvalidUserInputException.class).hasMessageContaining("Neplatná délka textu. Váš text musí mít délku");
        }
    }

    @Test
    void zvalidujZnakyJmena() {

        String[] namesinvalidCharacters = {"P4vel", "Jan-Petr", "karel@eshopy.cz"};
        for (String invalidName : namesinvalidCharacters) {
            assertThatThrownBy(() -> {
                validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.JMENO, true, invalidName);
            }).isInstanceOf(InvalidUserInputException.class).hasMessage("Obsahuje neplatné znaky. ");
        }
    }

    @Test
    void zvalidujVelikostCisla() throws InvalidUserInputException {
        int min = ValidatorVstupu.ValidatorEnum.VEK.getMin();
        int max = ValidatorVstupu.ValidatorEnum.VEK.getMax();
        List<Integer> validNumbers = testIntegerGenerator.generateValidValues(min, max);
        for (int validNumber : validNumbers) {
            String validatedNumberString = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.VEK, true, String.valueOf(validNumber));
            assertThat(String.valueOf(validNumber)).isEqualTo(validatedNumberString);
        }

    }

    @Test
    void zvalidujZnakyCisla() {
        String cislo = "45a";
        assertThatThrownBy(() -> {
            validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.VEK, true, cislo);
        }).isInstanceOf(NumberFormatException.class).hasMessage("Nezadali jste platné číslo.");
    }

    @Test
    void zvalidujDelkuTelefonnihoCisla() throws InvalidUserInputException {
        int min = ValidatorVstupu.ValidatorEnum.TELEFON.getMin();
        int max = ValidatorVstupu.ValidatorEnum.TELEFON.getMax();
        List<String> validPhoneNumbers = testStringGenerator.generateValidStrings(min, max - 4, '7');
        List<String> invalidPhoneNumbers = testStringGenerator.generateInvalidStrings(min, max - 4, '7');

        for (String validPhoneNumber : validPhoneNumbers) {
            String validatedPhoneNumber = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.TELEFON, true, validPhoneNumber);
            // ValidatorEnum obsahuje parametr s požadavkem na doplnění mezinárodní předvolby, pokud není zadána
            validPhoneNumber = "+420" + validPhoneNumber;
            assertThat(validatedPhoneNumber).isEqualTo(validPhoneNumber);
        }

        for (String invalidPhoneNumber : invalidPhoneNumbers) {
            assertThatThrownBy(() -> {
                validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.TELEFON, true, invalidPhoneNumber);
            }).isInstanceOf(InvalidUserInputException.class).hasMessage("Neplatné telefonní číslo. ");
        }
    }

    @Test
    void zvalidujZnakyTelefonnihoCisla() {
        String[] telefonniCisla = {"n777777777", "777-777777"};

        for (String telefonniCislo : telefonniCisla) {
            assertThatThrownBy(() -> {
                validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.TELEFON, true, telefonniCislo);
            }).isInstanceOf(InvalidUserInputException.class).hasMessage("Neplatné telefonní číslo. ");
        }
    }

    @Test
    void zvalidujTelefonSMezinarodniPredvolbou() throws InvalidUserInputException {
        String telefonniCislo = "+420777777777";
        String zvalidovanyTelefon = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.TELEFON, true, telefonniCislo);
        assertThat(telefonniCislo).isEqualTo(zvalidovanyTelefon);
    }

    @Test
    void odstranyMezeryVTelefonnimCisle() throws InvalidUserInputException {
        String telefonniCislo = "+420 777 777 777";
        String standardizovanyTelefon = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.TELEFON, true, telefonniCislo);
        assertThat("+420777777777").isEqualTo(standardizovanyTelefon);
    }

    @Test
    void povinnyVstupPrazdnyString() {
        String vstup = "";
        assertThatThrownBy(() -> {
            validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.JMENO, true, vstup);
        }).isInstanceOf(InvalidUserInputException.class).hasMessage("Pole nemůže být prázdné. ");

    };

    @Test
    void nepovinnyVstupPrazdnyString() throws InvalidUserInputException {
        String vstup = "";
        String zvalidovanyVstup = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.JMENO, false, vstup);
        assertThat(vstup).isEqualTo(zvalidovanyVstup);
    };

    // Standardizace vstupů

    @Test
    void trim() throws InvalidUserInputException {
        String textToTrim = "   Milan  ";
        String trimmedText = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.JMENO, true, textToTrim);
        assertThat(trimmedText).isEqualTo("Milan");
    }

    @Test
    void odstranPrebytecneMezeryVTextu() throws InvalidUserInputException {
        String text = "Milan   Jan";
        // ValidatorEnum.JMENO aktuálně obsahuje příkaz na odstranění přebytečných mezer v textu
        String trimmedText = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.JMENO, true, text);
        assertThat(trimmedText).isEqualTo("Milan Jan");
    }

    @Test
    void vlastniJmenoDejVelkePismeno() throws InvalidUserInputException {
        String jmeno = "milan";
        String standardizovaneJmeno = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.JMENO, true, jmeno);
        assertThat("Milan").isEqualTo(standardizovaneJmeno);
    }

    @Test
    void doplnMezinarodniPredvolbu() throws InvalidUserInputException {
        String cisloBezPredvolby = "767897777";
        String[] cislaSPredvolbou = {"+420777777777", "00420777777777"};

        String standardizovaneCislo = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.TELEFON, true, cisloBezPredvolby);
        assertThat(standardizovaneCislo).isEqualTo("+420767897777");

        for (String cisloSPredvolbou : cislaSPredvolbou) {
            String zvalidovaneCislo = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.TELEFON, true, cisloSPredvolbou);
            assertThat("+420777777777").isEqualTo(zvalidovaneCislo);
        }
    }
}