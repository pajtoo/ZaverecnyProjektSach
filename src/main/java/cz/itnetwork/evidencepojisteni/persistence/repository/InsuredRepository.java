package cz.itnetwork.evidencepojisteni.persistence.repository;

import cz.itnetwork.evidencepojisteni.persistence.entity.InsuredEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuredRepository extends JpaRepository<InsuredEntity,Long> {

}
