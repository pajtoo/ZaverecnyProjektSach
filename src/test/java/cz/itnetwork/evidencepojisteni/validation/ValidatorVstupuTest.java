package cz.itnetwork.evidencepojisteni.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import utils.TestIntegerGenerator;
import utils.TestStringGenerator;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class ValidatorVstupuTest {

    @Autowired
    private ValidatorVstupu validatorVstupu;

    private TestStringGenerator testStringGenerator = new TestStringGenerator();
    private TestIntegerGenerator testIntegerGenerator = new TestIntegerGenerator();

    @BeforeEach
    void setUp() {
    }
    @Test
    void testZvalidujJmeno() {

        validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.JMENO, null, );
    }

    @Test
    void testZvalidujPrijmeni() {
        Integer min = ValidatorVstupu.ValidatorEnum.PRIJMENI.getMin();
        Integer max = ValidatorVstupu.ValidatorEnum.PRIJMENI.getMax();
        LinkedHashMap<String, String> validValues = testStringGenerator.generateValidValues(min, max);
        LinkedHashMap<String, String> invalidValues = testStringGenerator.generateInvalidValues(min, max);

        for (Map.Entry<String, String> validValue : validValues.entrySet()){
            String zvalidovanyVstup = validatorVstupu.zvaliduj(ValidatorVstupu.ValidatorEnum.PRIJMENI, true, validValue.getValue());
            assertThat(zvalidovanyVstup).
        }
        // volání s minLength = null;
        // volání generateValidValues s negativní nebo nulovou zápornou hodnotou minLength = v pořádku je String o délce 1
        // přidat test na empty jako validní, pokud jePovinny je false, vracející empty String
        //pokud je povinný tak invalidUserInputException
    }

    @Test
    void testZvalidujVek() {

    }

    @Test
    void testZvalidujTelefon() {

    }

    @Test
    void testZvalidujCislo() {

    }

    @Test
    void testZvalidujPovinnyVstup() {

    };

    @Test
    void testZvalidujNepovinnyVstup() {

    };

}