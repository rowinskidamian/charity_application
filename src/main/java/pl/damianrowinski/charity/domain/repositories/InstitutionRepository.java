package pl.damianrowinski.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.charity.domain.entities.Institution;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
}
