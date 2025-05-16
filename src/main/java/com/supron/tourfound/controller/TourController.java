package com.supron.tourfound.controller;

import com.supron.tourfound.dto.TourDto;
import com.supron.tourfound.entity.TourEntity;
import com.supron.tourfound.entity.UserEntity;
import com.supron.tourfound.service.TourService;
import com.supron.tourfound.service.UserTourRattingService;
import com.supron.tourfound.service.UserTourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;
    private final UserTourService userTourService;
    private final UserTourRattingService userTourRattingService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin")
    public String getAdminPage(@AuthenticationPrincipal UserEntity user) {
        log.debug("ADMIN userDto - > {}", user);

        return "admin";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create-tour")
    public String createNewTour(TourDto tourDto) {
        log.debug("POST create new tour, tour = {}", tourDto);

        tourService.createNewTour(tourDto);

        return "redirect:/tours";
    }

    @GetMapping("/tours")
    public String findAllTours(
            @RequestParam(required = false) Map<String, String> searchParams,
            Model model
    ) {
        List<TourEntity> tourEntities = tourService.findTourEntity(searchParams);

        Set<String> toursCountryOrderedByCount = tourService.getAggregateValuesByField(tourEntities, TourEntity::getStartCountry);
        Set<String> toursDurationOrderedByCount = tourService.getAggregateValuesByField(tourEntities, TourEntity::getDuration);

        log.debug("toursCountryOrderedByCount = {}", toursCountryOrderedByCount);
        log.debug("toursDurationOrderedByCount = {}", toursDurationOrderedByCount);

        //List of tours
        model.addAttribute("tours", tourEntities);

        //Aggregation info for filters: duration(days), countries
        model.addAttribute("toursCountry", toursCountryOrderedByCount);
        model.addAttribute("toursDuration", toursDurationOrderedByCount);

        //selected item for filters (optional, sometimes value = null)
        model.addAttribute("selectedCountry", searchParams.get("filterByCountry"));
        model.addAttribute("selectedDuration", searchParams.get("filterByDuration"));

        return "tours";
    }

    @GetMapping("/tour-info/{tourId}")
    public String getTourById(@PathVariable Long tourId, Model model, @AuthenticationPrincipal UserEntity user) {
        log.debug("GET tour by id, tourId = {}", tourId);

        model.addAttribute("tour", tourService.findById(tourId));
        model.addAttribute("userTourRatting", userTourRattingService.findRattingByUserAndTour(user.getId(), tourId));
        model.addAttribute("userTourRattingCount", userTourRattingService.countRatingsForTour(tourId));

        return "tour-page";
    }

    @PostMapping("/book-tour/{tourId}")
    public String bookTour(@PathVariable Long tourId, @AuthenticationPrincipal UserEntity user) {
        log.debug("POST book tour by id, tourId = {}", tourId);

        userTourService.bookTourById(user, tourId);

        return "redirect:/profile";
    }

    @PostMapping("/rate-tour/{tourId}")
    public String bookTour(@PathVariable Long tourId,
                           @AuthenticationPrincipal UserEntity user,
                           @RequestParam Byte ratting) {
        log.debug("rate tour, tourId = {}, userId = {}, ratting = {}", tourId, user.getId(), ratting);

        //save user ratting and recalculate avg tour ratting
        userTourRattingService.rateTour(tourId, user, ratting);

        return "redirect:/tour-info/" + tourId;
    }


}
