package pl.damianrowinski.charity.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import pl.damianrowinski.charity.assemblers.DonationAssembler;
import pl.damianrowinski.charity.domain.entities.Donation;
import pl.damianrowinski.charity.domain.repositories.DonationRepository;
import pl.damianrowinski.charity.domain.resource.DonationResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class DonationServiceTest {

    @Mock DonationRepository donationRepository;
    @Mock DonationAssembler donationAssembler;

    private DonationService donationService;
    private ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        donationService = new DonationService(donationRepository, donationAssembler);
    }

    @Test
    void shouldReturnNumberOfBagsFromAllDonations() {
        List<Donation> donationList = getListDonationsWithQuantity();
        Integer quantityExpected = donationList.stream()
                .map(Donation::getQuantity)
                .reduce(0, Integer::sum);

        Mockito.when(donationRepository.findAll()).thenReturn(donationList);

        List<DonationResource> donationResourceList = donationList.stream()
                .map(donation -> modelMapper.map(donation, DonationResource.class))
                .collect(Collectors.toList());

        Mockito.when(donationAssembler.getResourceList(donationList)).thenReturn(donationResourceList);

        int quantityReturned = donationService.countBags();

        assertThat(quantityReturned).isEqualTo(quantityExpected);
    }

    private List<Donation> getListDonationsWithQuantity() {
        List<Donation> donationList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            Donation donation = new Donation();
            donation.setQuantity(random.nextInt());
            donationList.add(donation);
        }

        return donationList;
    }

}