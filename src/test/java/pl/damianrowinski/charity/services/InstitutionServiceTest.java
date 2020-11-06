package pl.damianrowinski.charity.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import pl.damianrowinski.charity.assemblers.InstitutionAssembler;
import pl.damianrowinski.charity.domain.entities.Institution;
import pl.damianrowinski.charity.domain.repositories.InstitutionRepository;
import pl.damianrowinski.charity.domain.resource.InstitutionResource;
import pl.damianrowinski.charity.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstitutionServiceTest {

    @Mock private InstitutionRepository institutionRepository;
    @Mock private DonationService donationService;
    @Mock private InstitutionAssembler institutionAssembler;

    private InstitutionService institutionService;
    private ModelMapper modelMapper = new ModelMapper();
    private InstitutionResource institutionResource;
    private Institution institution;
    private final long id1 = 1L;

    @BeforeEach
    private void init() {
        MockitoAnnotations.openMocks(this);
        institutionService = new InstitutionService(institutionRepository, institutionAssembler, donationService);
        institutionResource = new InstitutionResource();
        institutionResource.setId(id1);
        institutionResource.setName("test name");
        institutionResource.setDescription("test description");
        institution = modelMapper.map(institutionResource, Institution.class);
    }

    @Test
    void shouldReturnInstitutionResourceList() {
        //given
        Institution institution1 = new Institution();
        institution1.setName("Test name institution1");
        institution1.setDescription("Test description insitution1");
        institution1.setId(id1);

        Institution institution2 = new Institution();
        institution2.setName("Institution 2 name");
        institution2.setDescription("Institution 2 description");
        institution2.setId(2L);

        List<Institution> institutionList = List.of(institution1, institution2);

        InstitutionResource institution1Resource = modelMapper.map(institution1, InstitutionResource.class);
        InstitutionResource institution2Resource = modelMapper.map(institution2, InstitutionResource.class);

        List<InstitutionResource> institutionResourceList = List.of(institution1Resource, institution2Resource);

        Mockito.when(institutionRepository.findAll()).thenReturn(institutionList);
        Mockito.when(institutionAssembler.getResourceList(institutionList)).thenReturn(institutionResourceList);

        //when
        List<InstitutionResource> resultList = institutionService.findAll();

        //then
        assertThat(resultList).isEqualTo(List.of(institution1Resource, institution2Resource));
    }

    @Test
    void givenIdShouldReturnInstitution() {
        Mockito.when(institutionRepository.findById(id1)).thenReturn(Optional.ofNullable(institution));

        Institution institutionReturned = institutionService.findById(id1);

        assertThat(institutionReturned).isEqualTo(institution);
    }

    @Test
    void givenIdNotPresentShouldThrowException() {
        Mockito.when(institutionRepository.findById(id1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> institutionService.findById(id1))
                .isInstanceOf(ObjectNotFoundException.class);
    }

    @Test
    void whenDeleteShouldFirstCheckIfInstitutionInDonation() {
        Mockito.when(institutionRepository.findById(id1)).thenReturn(Optional.of(institution));

        institutionService.delete(id1);

        Mockito.verify(donationService, Mockito.atLeastOnce()).findDonationByInstitution(institution);
    }

    @Test
    void whenDeleteShouldUseDeleteInRepository() {
        Mockito.when(institutionRepository.findById(id1)).thenReturn(Optional.of(institution));

        institutionService.delete(id1);

        Mockito.verify(institutionRepository, Mockito.atLeastOnce()).delete(institution);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "institution_resource_data.csv", numLinesToSkip = 1)
    void givenInstitutionResourceShouldReturnSavedResource(Long id, String name, String description) {
        institutionResource.setName(name);
        institutionResource.setDescription(description);
        institutionResource.setId(id);

        institution = modelMapper.map(institutionResource, Institution.class);

        Mockito.when(institutionAssembler.getInstitution(institutionResource)).thenReturn(institution);
        Mockito.when(institutionRepository.save(institution)).thenReturn(institution);
        Mockito.when(institutionAssembler.getResource(institution)).thenReturn(institutionResource);

        InstitutionResource returnedResource = institutionService.save(institutionResource);

        assertThat(returnedResource).isEqualTo(institutionResource);
    }

}