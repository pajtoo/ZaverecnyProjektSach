package cz.itnetwork.evidencepojisteni.config;

import cz.itnetwork.evidencepojisteni.dto.PojistenecDTO;
import cz.itnetwork.evidencepojisteni.mapping.InputDTOMapper;
import cz.itnetwork.evidencepojisteni.mapping.MappingDataProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class AppConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public InputDTOMapper<PojistenecDTO> insuredInputDTOMapper() {
        return new InputDTOMapper<>();
    }

    @Bean
    public MappingDataProvider pojistenecMappingDataProvider() {
       return new MappingDataProvider(PojistenecDTO.class);
    }
}