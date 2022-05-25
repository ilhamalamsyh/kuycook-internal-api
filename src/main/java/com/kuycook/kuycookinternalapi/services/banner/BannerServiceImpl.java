package com.kuycook.kuycookinternalapi.services.banner;

import com.kuycook.kuycookinternalapi.models.Banner;
import com.kuycook.kuycookinternalapi.repositories.BannerRepository;
import com.kuycook.kuycookinternalapi.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionScoped;
import java.time.LocalDateTime;

@Service
@TransactionScoped
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public BannerServiceImpl(BannerRepository bannerRepository) {
        super();
        this.bannerRepository = bannerRepository;
    }

    @Override
    public Banner createBanner(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public Banner updateBanner(Long id, Banner banner) {
        Banner existingBanner = bannerRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new ResourceNotFoundException("Banner", "ID", id));

        existingBanner.setTitle(banner.getTitle());
        existingBanner.setImage(banner.getImage());
        existingBanner.setUpdatedAt(banner.getUpdatedAt());

        bannerRepository.save(existingBanner);
        return existingBanner;
    }

    @Override
    public Banner deletedBanner(Long id) {
        Banner existingBanner = bannerRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(
                () -> new ResourceNotFoundException("Banner", "ID", id));

        existingBanner.setDeletedAt(LocalDateTime.now());
        bannerRepository.save(existingBanner);
        return existingBanner;
    }

    public Iterable<Banner> findByName(String title, Pageable pageable) {
        return bannerRepository.findByTitleContainsAndDeletedAtIsNull(title, pageable);
    }

    public Banner findBannerById(Long id) {
        return bannerRepository.findBannerByIdAndDeletedAtIsNull(id);
    }
}
