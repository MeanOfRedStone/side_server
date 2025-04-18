package com.server.side.item.service.impl;

import com.server.side.config.FileProperties;
import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemRepository;
import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.server.side.item.dto.ItemDto.fromEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private FileProperties fileProperties;
    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        given(fileProperties.getUploadDir()).willReturn("../uploads/images/");
    }


    @Test
    void addItemThenReturnSame() throws Exception {
        String dir = fileProperties.getUploadDir();
        MockMultipartFile thumbnail = new MockMultipartFile(
                "thumbnail",
                "thumbnail.jpg",
                "image/jpeg",
                "dummy-thumbnail-content".getBytes()
        );

        List<MultipartFile> detailImages = List.of(
                new MockMultipartFile("detail1", "detail1.jpg", "image/jpeg", "img1".getBytes()),
                new MockMultipartFile("detail2", "detail2.jpg", "image/jpeg", "img2".getBytes())
        );
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .build();

        given(itemRepository.save(any(Item.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        ItemDto result1 = itemService.addItem(request1, thumbnail, detailImages);
        assertEquals(request1.getName(), result1.getName());
        assertEquals(request1.getPrice(), result1.getPrice());
        assertEquals(request1.getCategory(), result1.getCategory());
        assertEquals(dir + thumbnail.getOriginalFilename().toString(), result1.getImage());
        for(int i = 0; i < detailImages.size(); i++) {
            assertEquals(dir + detailImages.get(i).getOriginalFilename().toString(), result1.getInformation().get(i));
        }
        verify(itemRepository, times(1)).save(any(Item.class));

        Files.deleteIfExists(Path.of(result1.getImage()));
        for (String path : result1.getInformation()) {
            Files.deleteIfExists(Path.of(path));
        }

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("청바지")
                .price(2000)
                .category("하의")
                .build();

        ItemDto result2 = itemService.addItem(request2, thumbnail, detailImages);
        assertEquals(request2.getName(), result2.getName());
        assertEquals(request2.getPrice(), result2.getPrice());
        assertEquals(request2.getCategory(), result2.getCategory());
        assertEquals(dir + thumbnail.getOriginalFilename().toString(), result2.getImage());
        for(int i = 0; i < detailImages.size(); i++) {
            assertEquals(dir + detailImages.get(i).getOriginalFilename().toString(), result2.getInformation().get(i));
        }
        verify(itemRepository, times(2)).save(any(Item.class));

        Files.deleteIfExists(Path.of(result2.getImage()));
        for (String path : result2.getInformation()) {
            Files.deleteIfExists(Path.of(path));
        }
    }

    @Test
    void findAllItemsWhenItemsExistThenReturnAllItemDtos() {
        ItemRegistrationRequest request1 = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .image("셔츠.png")
                .information(List.of("img1", "img2"))
                .build();

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("청바지")
                .price(2000)
                .category("하의")
                .image("청바지.png")
                .information(List.of("img1", "img2"))
                .build();

        List<Item> mockItemList = List.of(request1.toEntity(), request2.toEntity());
        given(itemRepository.findAll()).willReturn(mockItemList);

        List<ItemDto> result = itemService.findAllItems();

        List<ItemDto> expected = List.of(fromEntity(request1.toEntity()), fromEntity(request2.toEntity()));

        assertEquals(result, expected);
        verify(itemRepository, times(1)).findAll();
    }
}
