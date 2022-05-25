package com.kuycook.kuycookinternalapi.services.banner;

import com.kuycook.kuycookinternalapi.models.Banner;

public interface BannerService {
    Banner createBanner(Banner banner);

    Banner updateBanner(Long id, Banner banner);

    Banner deletedBanner(Long id);
}
