package cz.itnetwork.evidencepojisteni.persistence.repository;

import cz.itnetwork.evidencepojisteni.persistence.entity.InsuredEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InsuredRepositoryTest {

    @Autowired
    private InsuredRepository insuredRepository;

    private InsuredEntity insuredEntity;

    @BeforeEach
    void setUp() {
        insuredEntity = new InsuredEntity(
                null,
                "Karel",
                "Petráček",
                "+420777877977",
                35
        );

    }

    @Test
    void saveAndFind() {
        InsuredEntity savedEntity = insuredRepository.save(insuredEntity);
        System.out.println(savedEntity);

        assertThat(savedEntity.getId()).isNotNull();
        assertThat(insuredRepository.findById(savedEntity.getId())).isPresent();
    }

    @Test
    void update() {
        InsuredEntity savedEntity = insuredRepository.save(insuredEntity);
        String phone = "+420123456789";
        savedEntity.setTelefon(phone);
        insuredRepository.save(savedEntity);

        InsuredEntity updatedEntity = insuredRepository.findById(savedEntity.getId()).orElseThrow();

        assertThat(phone).isEqualTo(updatedEntity.getTelefon());
    }

    @Test
    void delete() {
        InsuredEntity savedEntity = insuredRepository.save(insuredEntity);
        insuredRepository.delete(savedEntity);

        assertThat(insuredRepository.findById(savedEntity.getId())).isEmpty();
    }

}
