package com.travel.toy3.domain.itinerary.controller;

import com.travel.toy3.domain.itinerary.dto.AccommodationDTO;
import com.travel.toy3.domain.itinerary.dto.ItineraryDTO;
import com.travel.toy3.domain.itinerary.dto.MovingDTO;
import com.travel.toy3.domain.itinerary.dto.StayDTO;
import com.travel.toy3.domain.itinerary.service.ItineraryService;
import com.travel.toy3.domain.itinerary.type.ItineraryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItineraryRestController.class)
class ItineraryRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItineraryService itineraryService;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    @Test
    @WithMockUser
    void getAllItineraries() throws Exception {
        ItineraryDTO movingItineraryDTO = ItineraryDTO.builder()
                .tripId((long) 1)
                .itineraryId((long) 1)
                .itineraryType(ItineraryType.MOVING)
                .itineraryName("비행기 타고 제주도로 이동")
                .moving(MovingDTO.builder()
                        .vehicle("비행기")
                        .departurePlace("김포공항")
                        .destinationPlace("제주공항")
                        .departurePlaceAddress("서울특별시 강서구 하늘길 112")
                        .destinationPlaceAddress("제주특별자치도 제주시 공항로 2")
                        .departureDatetime(LocalDateTime.parse("2024-02-26T09:00:00"))
                        .arrivalDatetime(LocalDateTime.parse("2024-02-26T10:00:00"))
                        .build())
                .build();
        ItineraryDTO accommodationItineraryDTO = ItineraryDTO.builder()
                .tripId((long) 1)
                .itineraryId((long) 2)
                .itineraryType(ItineraryType.ACCOMMODATION)
                .itineraryName("첫 번째 숙소")
                .accommodation(AccommodationDTO.builder()
                        .accommodationPlaceName("제주신라호텔")
                        .accommodationPlaceAddress("제주특별자치도 서귀포시 중문관광로72번길 75")
                        .checkIn(LocalDateTime.parse("2024-02-26T17:00:00"))
                        .checkOut(LocalDateTime.parse("2024-02-27T11:00:00"))
                        .build())
                .build();
        ItineraryDTO stayItineraryDTO = ItineraryDTO.builder()
                .tripId((long) 1)
                .itineraryId((long) 3)
                .itineraryType(ItineraryType.STAY)
                .itineraryName("오설록에서 맛있는 녹차 롤케이크 먹음")
                .stay(StayDTO.builder()
                        .stayPlaceName("오설록티뮤지엄")
                        .stayPlaceAddress("제주특별자치도 서귀포시 신화역사로 15")
                        .arrivalDatetime(LocalDateTime.parse("2024-02-27T11:30:00"))
                        .departureDatetime(LocalDateTime.parse("2024-02-27T13:00:00"))
                        .build())
                .build();

        given(itineraryService.getAllItineraries((long) 1))
                .willReturn(Arrays.asList(movingItineraryDTO, accommodationItineraryDTO, stayItineraryDTO));

        mockMvc.perform(get("/api/itineraries/trip/1").contentType(contentType))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data.[0].itineraryId", is(1)))
                .andExpect(jsonPath("$.data.[0].itineraryType", is(ItineraryType.MOVING.name())))
                .andExpect(jsonPath("$.data.[1].itineraryId", is(2)))
                .andExpect(jsonPath("$.data.[1].itineraryType", is(ItineraryType.ACCOMMODATION.name())))
                .andExpect(jsonPath("$.data.[2].itineraryId", is(3)))
                .andExpect(jsonPath("$.data.[2].itineraryType", is(ItineraryType.STAY.name())));
    }
}