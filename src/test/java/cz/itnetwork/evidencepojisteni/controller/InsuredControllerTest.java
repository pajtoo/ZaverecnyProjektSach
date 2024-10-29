package cz.itnetwork.evidencepojisteni.controller;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.exception.InvalidUserInputException;
import cz.itnetwork.evidencepojisteni.mapping.InputDTOMapper;
import cz.itnetwork.evidencepojisteni.mapping.MappingDataProvider;
import cz.itnetwork.evidencepojisteni.service.SpravcePojistenych;
import cz.itnetwork.evidencepojisteni.validation.ValidatorVstupu;
import cz.itnetwork.evidencepojisteni.view.UzivatelskeRozhrani;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsuredControllerTest {

    @Mock
    private UzivatelskeRozhrani ui;
    @Mock
    private SpravcePojistenych spravcePojistenych;
    @Mock
    private ValidatorVstupu validator;
    @Mock
    private MappingDataProvider pojistenecMappingDataProvider;
    @Mock
    private InputDTOMapper<PojistenecDTO> inputDTOMapper;
    @InjectMocks
    InsuredController insuredController;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testRunMenuOption1() {
        // Arrange
        when(ui.ziskejVstup()).thenReturn("1");


        // Act
        insuredController.run();

        // Assert
    }

    @Test
    void testRunMenuOption2() {
        // Arrange
        when(ui.ziskejVstup()).thenReturn("2");


        // Act
        insuredController.run();

        // Assert
    }

    @Test
    void testRunMenuOption3() {
        // Arrange
        when(ui.ziskejVstup()).thenReturn("3");


        // Act
        insuredController.run();

        // Assert
    }

    @Test
    void testRunMenuOption4() {
        // Arrange
        when(ui.ziskejVstup()).thenReturn("4");


        // Act
        insuredController.run();

        // Assert
    }

    @Test
    void testRunMenuOption5() {
        // Arrange
        when(ui.ziskejVstup()).thenReturn("5");


        // Act
        insuredController.run();

        // Assert
    }

    @Test
    void testRunMenuOption6() {
        // Arrange
        when(ui.ziskejVstup()).thenReturn("6");


        // Act
        insuredController.run();

        // Assert
    }


    // Otestuje, že se metoda v případě chybného vstupu volá znovu, dokud nezíská správný vstup
    @Test
    void testRunMenuInvalidOptionThen1() throws Exception {
        // Arrange
        when(ui.ziskejVstup()).thenReturn("0", "1");
        InOrder inOrder = inOrder(ui);

        // Act
        insuredController.run();

        // Assert
        inOrder.verify(ui).vypisChybovouHlasku(any());
        inOrder.verify(ui).vyzviKOpakovaniZadani();
        inOrder.verify(ui).ziskejVstup();
        inOrder.verify(ui).vypisPojistence(any());


    }
}