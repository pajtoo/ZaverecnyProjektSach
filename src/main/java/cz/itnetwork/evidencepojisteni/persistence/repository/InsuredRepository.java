package cz.itnetwork.evidencepojisteni.persistence.repository;

import cz.itnetwork.evidencepojisteni.persistence.entity.InsuredEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuredRepository extends JpaRepository<InsuredEntity,Long> {
}
