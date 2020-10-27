package pl.damianrowinski.charity.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damianrowinski.charity.assemblers.DonationAssembler;
import pl.damianrowinski.charity.domain.entities.Donation;
import pl.damianrowinski.charity.domain.repositories.DonationRepository;
import pl.damianrowinski.charity.domain.resource.DonationResource;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor

public class DonationService {

    private final DonationRepository donationRepository;
    private final DonationAssembler donationAssembler;

    public Donation findById(Long id) {
        Optional<Donation> optionalDonation = donationRepository.findById(id);
        if (optionalDonation.isEmpty()) throw new ObjectNotFoundException("not.found.donation");

        return optionalDonation.get();
    }

    public void add(Donation donation) {
        donationRepository.save(donation);
    }

    public void update(Donation donationUpdated) {
        Donation donationToUpdate = findById(donationUpdated.getId());
        donationUpdated.setId(donationToUpdate.getId());
        donationRepository.save(donationUpdated);
    }

    public void delete(Long id) {
        Donation donation = findById(id);
        donationRepository.delete(donation);
    }

    public List<DonationResource> findAllWithCategories() {
        List<Donation> donationsList = donationRepository.findAllWithCategories();
        return donationAssembler.getResourceList(donationsList);
    }

    public List<DonationResource> findAll() {
        List<Donation> donationsList = donationRepository.findAll();
        return donationAssembler.getResourceList(donationsList);
    }

    public long countDonations() {
        return donationRepository.count();
    }

    public int countBags() {
        List<DonationResource> donationList = findAll();
        return donationList.stream()
                .map(DonationResource::getQuantity)
                .reduce(0, Integer::sum);
    }

}
