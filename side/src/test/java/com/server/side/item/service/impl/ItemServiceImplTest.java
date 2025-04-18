package com.server.side.item.service.impl;

import com.server.side.config.FileProperties;
import com.server.side.exception.FileValidationException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    FileProperties fileProperties;
    @InjectMocks
    ItemServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        lenient().when(fileProperties.getUploadDir()).thenReturn("../uploads/images/");
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
        assertItem(request1, result1, thumbnail, detailImages);

        verify(itemRepository, times(1)).save(any(Item.class));

        deleteFile(result1);

        ItemRegistrationRequest request2 = ItemRegistrationRequest.builder()
                .name("청바지")
                .price(2000)
                .category("하의")
                .build();

        ItemDto result2 = itemService.addItem(request2, thumbnail, detailImages);
        assertItem(request2, result2, thumbnail, detailImages);

        verify(itemRepository, times(2)).save(any(Item.class));

        deleteFile(result2);
    }

    private void assertItem(ItemRegistrationRequest request, ItemDto result, MockMultipartFile thumbnail, List<MultipartFile> detailImages) {
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getPrice(), result.getPrice());
        assertEquals(request.getCategory(), result.getCategory());
        assertEquals(fileProperties.getUploadDir() + thumbnail.getOriginalFilename().toString(), result.getImage());
        for(int i = 0; i < detailImages.size(); i++) {
            assertEquals(fileProperties.getUploadDir() + detailImages.get(i).getOriginalFilename().toString(), result.getInformation().get(i));
        }
    }

    @Test
    void shouldFailWhenThumbNailIsNull() throws Exception{
        ItemRegistrationRequest request = ItemRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .build();

        MockMultipartFile thumbnail1 = null;
        List<MultipartFile> detailImages1 = List.of(
                new MockMultipartFile("detail1", "detail1.jpg", "image/jpeg", "img1".getBytes()),
                new MockMultipartFile("detail2", "detail2.jpg", "image/jpeg", "img2".getBytes())
        );

        assertThatThrownBy(() -> itemService.addItem(request, thumbnail1, detailImages1))
                .isInstanceOf(FileValidationException.class)
                .hasMessage("{file.thumbnail.required}");

        MockMultipartFile thumbnail2 = new MockMultipartFile(
                "thumbnail",
                "thumbnail.jpg",
                "image/jpeg",
                "dummy-thumbnail-content".getBytes()
        );

        given(itemRepository.save(any(Item.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        ItemDto result = assertDoesNotThrow(() -> itemService.addItem(request, thumbnail2, detailImages1));

        deleteFile(result);
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

    private void deleteFile(ItemDto result) throws Exception{
        Files.deleteIfExists(Path.of(result.getImage()));
        for (String path : result.getInformation()) {
            Files.deleteIfExists(Path.of(path));
        }
    }
}
