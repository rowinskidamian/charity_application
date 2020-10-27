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
            DonationResource donationResource = modelMapper.map(donation, DonationResource.class);
            convertCategoryListToResourceList(donation, donationResource);
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
}
