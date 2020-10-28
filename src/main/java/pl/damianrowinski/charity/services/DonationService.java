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

    public DonationResource findById(Long id) {
        Donation donation = getDonationByIdOrThrow(id);
        return donationAssembler.getResource(donation);
    }

    public DonationResource findByIdWithCategories(Long id) {
        Optional<Donation> optionalDonation = donationRepository.findByIdWithCategories(id);
        if (optionalDonation.isEmpty()) throw new ObjectNotFoundException("not.found.donation");
        return donationAssembler.getResource(optionalDonation.get());
    }

    public DonationResource add(DonationResource donationToAdd) {
        Donation donation = donationAssembler.getDonation(donationToAdd);
        Donation savedDonation = donationRepository.save(donation);
        return donationAssembler.getResource(savedDonation);
    }

    public DonationResource update(DonationResource donationUpdated) {
        findById(donationUpdated.getId());
        Donation donation = donationAssembler.getDonation(donationUpdated);
        Donation updatedDonation = donationRepository.save(donation);
        return donationAssembler.getResource(updatedDonation);
    }

    public void delete(Long id) {
        Donation donation = getDonationByIdOrThrow(id);
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

    private Donation getDonationByIdOrThrow(Long id) {
        Optional<Donation> optionalDonation = donationRepository.findById(id);
        if (optionalDonation.isEmpty()) throw new ObjectNotFoundException("not.found.donation");
        return optionalDonation.get();
    }



}
