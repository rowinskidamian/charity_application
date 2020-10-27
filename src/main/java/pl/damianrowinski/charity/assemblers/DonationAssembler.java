package pl.damianrowinski.charity.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.charity.domain.entities.Category;
import pl.damianrowinski.charity.domain.entities.Donation;
import pl.damianrowinski.charity.domain.resource.CategoryResource;
import pl.damianrowinski.charity.domain.resource.DonationResource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DonationAssembler {
    private final ModelMapper modelMapper;

    public List<DonationResource> getResourceList(List<Donation> listToConvert) {
        List<DonationResource> donationResourceList = new ArrayList<>();
        for (Donation donation : listToConvert) {
            DonationResource donationResource = getResource(donation);
            donationResourceList.add(donationResource);
        }
        return donationResourceList;
    }

    private void convertCategoryListToResourceList(Donation donationSource, DonationResource donationResourceToSet) {
        List<Category> categories = donationSource.getCategories();
        List<CategoryResource> categoryResources = categories.stream()
                .map(category -> modelMapper.map(category, CategoryResource.class))
                .collect(Collectors.toList());
        donationResourceToSet.setCategories(categoryResources);
    }

    public DonationResource getResource(Donation donation) {
        DonationResource donationResource = modelMapper.map(donation, DonationResource.class);
        convertCategoryListToResourceList(donation, donationResource);
        return donationResource;
    }

    public Donation getDonation(DonationResource resourceToConvert) {
        Donation donation = modelMapper.map(resourceToConvert, Donation.class);
        convertResourceListToCategoryList(resourceToConvert, donation);
        return donation;
    }

    private void convertResourceListToCategoryList(DonationResource donationSource, Donation donationToSet) {
        List<CategoryResource> categoryResourceList = donationSource.getCategories();
        List<Category> categoryList = categoryResourceList.stream()
                .map(categoryResource -> modelMapper.map(categoryResource, Category.class))
                .collect(Collectors.toList());
        donationToSet.setCategories(categoryList);
    }
}
