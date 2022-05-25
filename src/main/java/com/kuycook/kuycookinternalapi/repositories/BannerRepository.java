package com.kuycook.kuycookinternalapi.repositories;


import com.kuycook.kuycookinternalapi.models.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BannerRepository extends PagingAndSortingRepository<Banner, Long> {

    @Query(value = "select * from banners where deleted_at is null", nativeQuery = true)
    List<Banner> bannerList();

    Page<Banner> findByTitleContainsAndDeletedAtIsNull(String title, Pageable pageable);

    Banner findBannerByIdAndDeletedAtIsNull(Long id);

    Optional<Banner> findByIdAndDeletedAtIsNull(Long id);
}
