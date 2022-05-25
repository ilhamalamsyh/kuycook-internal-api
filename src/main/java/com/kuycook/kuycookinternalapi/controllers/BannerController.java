package com.kuycook.kuycookinternalapi.controllers;

import com.kuycook.kuycookinternalapi.dto.banner.response.BannerResponse;
import com.kuycook.kuycookinternalapi.dto.banner.request.BannerRequest;
import com.kuycook.kuycookinternalapi.dto.requests.SearchDataRequest;
import com.kuycook.kuycookinternalapi.models.Banner;
import com.kuycook.kuycookinternalapi.repositories.BannerRepository;
import com.kuycook.kuycookinternalapi.services.banner.BannerService;
import com.kuycook.kuycookinternalapi.services.banner.BannerServiceImpl;
import com.kuycook.kuycookinternalapi.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin(maxAge = 3600, origins = "*")
@RequestMapping("/kuycook/api/banners")
public class BannerController {

    private BannerService bannerService;

    @Autowired
    private BannerServiceImpl bannerServiceImpl;

    @Autowired
    BannerRepository bannerRepository;

    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @PostMapping("/search/{size}/{page}/{sort}")
    public Iterable<Banner> findBannerByName(@RequestBody SearchDataRequest searchData,
                                             @PathVariable("size") int size, @PathVariable("page") int page,
                                             @PathVariable("sort") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        if (sort.equalsIgnoreCase("asc"))
            pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        return bannerServiceImpl.findByName(searchData.getSearchKey(), pageable);
    }

    @GetMapping()
    public Iterable<Banner> findBannerByName(@RequestParam(required = false, value = "search", defaultValue = "") String searchData,
                                             @RequestParam(required = false, value = "size", defaultValue = "5") int size,
                                             @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                             @RequestParam(required = false, value = "sort", defaultValue = "desc") String sort)
            throws IOException {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            if (sort.equalsIgnoreCase("asc")) {
                pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            }
            return bannerServiceImpl.findByName(searchData, pageable);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @GetMapping("/{id}")
    public Object bannerDetail(@PathVariable("id") Long id) {
        System.out.println("banner detail: " + bannerServiceImpl.findBannerById(id));
        if (bannerServiceImpl.findBannerById(id) == null) {
            return new ResourceNotFoundException("Banner", "ID", id);
        }
        return bannerServiceImpl.findBannerById(id);
    }

    @PostMapping("/") //TODO: (!) for Errors error, should be on the third parameter
    public ResponseEntity<BannerResponse<Banner>> bannerCreate(@Valid @RequestBody BannerRequest bannerRequest, Errors errors) {
        BannerResponse<Banner> responsedata = new BannerResponse<>();
        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responsedata.getMessages().add(error.getDefaultMessage());
            }
            responsedata.setStatus(false);
            responsedata.setCode(400);
            responsedata.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsedata);
        }
        Banner banner = new Banner(bannerRequest.getTitle(), bannerRequest.getImage(),
                bannerRequest.getCreatedAt(), bannerRequest.getUpdatedAt());
        responsedata.setStatus(true);
        responsedata.setCode(200);
        responsedata.setData(bannerServiceImpl.createBanner(banner));

        return ResponseEntity.ok(responsedata);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BannerResponse<Banner>> bannerUpdate(@Valid @RequestBody BannerRequest bannerRequest,
                                                               Errors errors, @PathVariable("id") Long id) {
        BannerResponse<Banner> responseData = new BannerResponse<>();

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setCode(400);
            responseData.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Banner banner = new Banner(bannerRequest.getTitle(), bannerRequest.getImage(),
                bannerRequest.getCreatedAt(), bannerRequest.getUpdatedAt());
        responseData.setData(bannerServiceImpl.updateBanner(id, banner));
        responseData.setStatus(true);
        responseData.setCode(200);
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/{id}")
    public Banner deleteBanner(@PathVariable("id") Long id) {
        return bannerServiceImpl.deletedBanner(id);
    }
}
