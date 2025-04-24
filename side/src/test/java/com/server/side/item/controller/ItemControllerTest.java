package com.server.side.item.controller;

import com.google.gson.Gson;
import com.server.side.item.domain.Item;
import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.server.side.item.dto.ItemDto.fromEntity;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ItemServiceImpl itemService;

    @MockitoBean
    private MessageSource messageSource;

    @Autowired
    private Gson gson;

    @Test
    void createItemThenReturnSameItem() throws Exception {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .build();
        String requestJson = gson.toJson(request1);

        MockMultipartFile requestPart = new MockMultipartFile(
                "request",         // @RequestPart 이름
                "",                // 파일 이름 필요 없음
                "application/json",
                requestJson.getBytes()
        );

        MockMultipartFile thumbnail = new MockMultipartFile(
                "thumbnailImage",
                "thumbnail.jpg",
                "image/jpeg",
                "dummy-thumbnail-content".getBytes()
        );

        List<MockMultipartFile> detailImages = List.of(
                new MockMultipartFile("detailImages", "detail1.jpg", "image/jpeg", "img1".getBytes()),
                new MockMultipartFile("detailImages", "detail2.jpg", "image/jpeg", "img2".getBytes())
        );

        List<String> detailImagePaths = detailImages.stream()
                .map(MockMultipartFile::getOriginalFilename)
                .collect(Collectors.toList());

        Item requestedItem = request1.toEntity();
        requestedItem.setImage(thumbnail.getOriginalFilename().toString());
        requestedItem.setInformation(detailImagePaths);

        ItemDto result1 = fromEntity(requestedItem);

        given(itemService.addItem(
                eq(request1),
                eq(thumbnail),
                argThat(list -> list.size() == detailImages.size() &&
                        IntStream.range(0, list.size())
                                .allMatch(i -> list.get(i).getOriginalFilename().equals(detailImages.get(i).getOriginalFilename())))
        )).willReturn(result1);

        MockMultipartHttpServletRequestBuilder multipartRequest = multipart("/items")
                .file(requestPart)
                .file(thumbnail);

        for(MockMultipartFile detailImage : detailImages) {
            multipartRequest.file(detailImage);
        }

        multipartRequest
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(multipartRequest)
                        .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(result1.getName()))
                .andExpect(jsonPath("$.price").value(result1.getPrice()))
                .andExpect(jsonPath("$.category").value(result1.getCategory()))
                .andExpect(jsonPath("$.description").value(result1.getDescription()))
                .andExpect(jsonPath("$.image").value(result1.getImage()))
                .andExpect(jsonPath("$.information", containsInAnyOrder(result1.getInformation().toArray(new String[0]))));

    }

    @Test
    void itemListThenReturnAllItems() throws Exception{
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("청바지")
                .price(2000)
                .category("하의")
                .description("멋진 바지")
                .image("청바지.png")
                .information(List.of("img1", "img2"))
                .build();

        List<ItemDto> expectedList = List.of(fromEntity(request1.toEntity()), fromEntity(request2.toEntity()));
        given(itemService.findAllItems()).willReturn(expectedList);

        ResultActions actions = mockMvc.perform(
                get("/items")
                        .accept(MediaType.APPLICATION_JSON)
        );

        actions.andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(expectedList)));
    }

    @Test
    void shouldReturnBadRequestWhenThumbnailImageIsMissing() throws Exception {
        LocalDateTime fixedTimestamp = LocalDateTime.now();
        try (MockedStatic<LocalDateTime> mockedDateTime = Mockito.mockStatic(LocalDateTime.class)) {
            mockedDateTime.when(LocalDateTime::now).thenReturn(fixedTimestamp);

            MockMultipartFile request = new MockMultipartFile(
                    "request",
                    "",
                    "application/json",
                    "{\"name\":\"Test Item\", \"price\":1000, \"category\":\"상의\", \"description\" : \"멋진 셔츠\"}".getBytes()
            );

            MockMultipartFile detailImage1 = new MockMultipartFile(
                    "detailImage",
                    "detail.png",
                    MediaType.IMAGE_PNG_VALUE,
                    "detail-image-content1".getBytes()
            );
            MockMultipartFile detailImage2 = new MockMultipartFile(
                    "detailImage",
                    "detail2.png",
                    MediaType.IMAGE_PNG_VALUE,
                    "detail-image-content2".getBytes()
            );

            MockMultipartHttpServletRequestBuilder multipartRequest = multipart("/items")
                    .file(request)
                    .file(detailImage1)
                    .file(detailImage2);

            multipartRequest
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .accept(MediaType.APPLICATION_JSON);

            mockMvc.perform(multipartRequest)
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value("REQUIRED_IMAGE_NOT_FOUND"))
                    .andExpect(jsonPath("$.message").value(messageSource.getMessage("file.image.required", null, Locale.getDefault())))
                    .andExpect(jsonPath("$.timestamp").value(fixedTimestamp.toString()));

        }
    }

    @Test
    void shouldReturnBadRequestWhenDetailImageAreMissing() throws Exception {
        LocalDateTime fixedTimestamp = LocalDateTime.now();
        try (MockedStatic<LocalDateTime> mockedDateTime = Mockito.mockStatic(LocalDateTime.class)) {
            mockedDateTime.when(LocalDateTime::now).thenReturn(fixedTimestamp);

            MockMultipartFile request = new MockMultipartFile(
                    "request",
                    "",
                    "application/json",
                    "{\"name\":\"Test Item\", \"price\":1000, \"category\":\"상의\", \"description\" : \"멋진 셔츠\"}".getBytes()
            );

            MockMultipartFile thumbnailImage = new MockMultipartFile(
                    "thumbnailImage",
                    "thumbnail.png",
                    MediaType.IMAGE_PNG_VALUE,
                    "thumbnail-image-content1".getBytes()
            );

            MockMultipartHttpServletRequestBuilder multipartRequest = multipart("/items")
                    .file(request)
                    .file(thumbnailImage);

            multipartRequest
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .accept(MediaType.APPLICATION_JSON);

            mockMvc.perform(multipartRequest)
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorCode").value("REQUIRED_IMAGE_NOT_FOUND"))
                    .andExpect(jsonPath("$.message").value(messageSource.getMessage("file.image.required", null, Locale.getDefault())))
                    .andExpect(jsonPath("$.timestamp").value(fixedTimestamp.toString()));

        }

    }

}
