package pl.damianrowinski.charity.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.damianrowinski.charity.domain.entities.Donation;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d from Donation d join fetch d.categories")
    List<Donation> findAllWithCategories();
}
