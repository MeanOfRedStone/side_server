package com.server.side.item.service.impl;

import com.server.side.config.FileProperties;
import com.server.side.exception.FileValidationException;
import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemDetail;
import com.server.side.item.dto.ItemDetailRegistrationRequest;
import com.server.side.item.dto.ItemWithDetailsDTO;
import com.server.side.item.dto.ItemWithDetailsRegistrationRequest;
import com.server.side.item.repository.ItemDetailRepository;
import com.server.side.item.repository.ItemRepository;
import com.server.side.util.FileManager;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ItemWithDetailsServiceImplTest {

    @Mock
    FileProperties fileProperties;
    @InjectMocks
    ItemWithDetailsServiceImpl itemService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    ItemDetailRepository itemDetailRepository;
    @Mock
    FileManager fileManager;

    @BeforeEach
    void setUp() {
        lenient().when(fileProperties.getUploadDir()).thenReturn("/uploads/images/");
    }

    @Test
    void addItemWithDetailsThenReturnSame() throws Exception {
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
        ItemDetailRegistrationRequest detailRequest = ItemDetailRegistrationRequest.builder()
                .option("L")
                .quantity(100)
                .build();

        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .itemDetails(List.of(detailRequest))
                .build();

        given(fileManager.saveFile(thumbnail)).willReturn(dir + thumbnail.getOriginalFilename());
        for(MultipartFile file : detailImages) {
            given(fileManager.saveFile(file)).willReturn(dir + file.getOriginalFilename());
        }
        given(itemRepository.save(any(Item.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        given(itemDetailRepository.saveAll(anyList()))
                .willAnswer(invocation -> {
                    @SuppressWarnings("unchecked")
                    List<ItemDetail> savedList = (List<ItemDetail>) invocation.getArgument(0);
                    return savedList;
                });

        ItemWithDetailsDTO result1 = itemService.addItemWithDetails(request1, thumbnail, detailImages);
        assertItemWithDetails(request1, result1, thumbnail, detailImages);

        verify(itemRepository, times(1)).save(any(Item.class));
        verify(itemDetailRepository, times(1)).saveAll(anyList());

        deleteFile(result1);

        ItemWithDetailsRegistrationRequest request2 = ItemWithDetailsRegistrationRequest.builder()
                .name("청바지")
                .price(2000)
                .category("하의")
                .description("멋진 바지")
                .itemDetails(List.of(detailRequest))
                .build();

        ItemWithDetailsDTO result2 = itemService.addItemWithDetails(request2, thumbnail, detailImages);
        assertItemWithDetails(request2, result2, thumbnail, detailImages);

        verify(itemRepository, times(2)).save(any(Item.class));
        verify(itemDetailRepository, times(2)).saveAll(anyList());
        deleteFile(result2);
    }

    @Test
    void shouldFailWhenThumbNailIsBlank() throws Exception{
        ItemDetailRegistrationRequest detailRequest = ItemDetailRegistrationRequest.builder()
                .option("L")
                .quantity(100)
                .build();
        ItemWithDetailsRegistrationRequest request = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .itemDetails(List.of(detailRequest))
                .build();

        List<MultipartFile> detailImages1 = List.of(
                new MockMultipartFile("detail1", "detail1.jpg", "image/jpeg", "img1".getBytes()),
                new MockMultipartFile("detail2", "detail2.jpg", "image/jpeg", "img2".getBytes())
        );

        MockMultipartFile thumbnail2 = new MockMultipartFile(
                "thumbnail",
                "thumbnail.jpg",
                "image/jpeg",
                "dummy-thumbnail-content".getBytes()
        );

        given(itemRepository.save(any(Item.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        given(itemDetailRepository.saveAll(anyList()))
                .willAnswer(invocation -> {
                    @SuppressWarnings("unchecked")
                    List<ItemDetail> savedList = (List<ItemDetail>) invocation.getArgument(0);
                    return savedList;
                });

        assertDoesNotThrow(() -> itemService.addItemWithDetails(request, thumbnail2, detailImages1));

        MockMultipartFile thumbnail3 = new MockMultipartFile(
                "thumbnail",
                "thumbnail.jpg",
                "image/jpeg",
                new byte[0]
        );

        assertThatThrownBy(() ->itemService.addItemWithDetails(request, thumbnail3, detailImages1))
                .isInstanceOf(FileValidationException.class)
                .hasMessage("file.image.empty");
    }

    @Test
    void shouldFailWhenDetailImagesAreBlank() throws Exception{
        ItemDetailRegistrationRequest detailRequest = ItemDetailRegistrationRequest.builder()
                .option("L")
                .quantity(100)
                .build();
        ItemWithDetailsRegistrationRequest request = ItemWithDetailsRegistrationRequest.builder()
                .name("셔츠")
                .price(1000)
                .category("상의")
                .description("멋진 셔츠")
                .itemDetails(List.of(detailRequest))
                .build();
        MockMultipartFile thumbnail1 = new MockMultipartFile(
                "thumbnail",
                "thumbnail.jpg",
                "image/jpeg",
                "dummy-thumbnail-content".getBytes()
        );

        List<MultipartFile> detailImages2 = List.of(
                new MockMultipartFile("detail1", "detail1.jpg", "image/jpeg", "img1".getBytes()),
                new MockMultipartFile("detail2", "detail2.jpg", "image/jpeg", "img2".getBytes())
        );
        given(itemRepository.save(any(Item.class)))
                .willAnswer(invocation -> invocation.getArgument(0));
        given(itemDetailRepository.saveAll(anyList()))
                .willAnswer(invocation -> {
                    @SuppressWarnings("unchecked")
                    List<ItemDetail> savedList = (List<ItemDetail>) invocation.getArgument(0);
                    return savedList;
                });

        assertDoesNotThrow(() -> itemService.addItemWithDetails(request, thumbnail1, detailImages2));

        List<MultipartFile> detailImages3 = List.of(
                new MockMultipartFile("detail1", "detail1.jpg", "image/jpeg", new byte[0]),
                new MockMultipartFile("detail2", "detail2.jpg", "image/jpeg", "img2".getBytes())
        );

        assertThatThrownBy(() -> itemService.addItemWithDetails(request, thumbnail1, detailImages3))
                .isInstanceOf(FileValidationException.class)
                .hasMessage("file.image.empty");
    }

//    @Test
//    void findAllItemsWhenItemsExistThenReturnAllItemDtos() {
//        ItemDetailRegistrationRequest detailRequest = ItemDetailRegistrationRequest.builder()
//                .option("L")
//                .quantity(100)
//                .build();
//        ItemWithDetailsRegistrationRequest request1 = ItemWithDetailsRegistrationRequest.builder()
//                .name("셔츠")
//                .price(1000)
//                .category("상의")
//                .description("멋진 셔츠")
//                .image("셔츠.png")
//                .information(List.of("img1", "img2"))
//                .itemDetails(List.of(detailRequest))
//                .build();
//
//        ItemWithDetailsRegistrationRequest request2 = ItemWithDetailsRegistrationRequest.builder()
//                .name("청바지")
//                .price(2000)
//                .category("하의")
//                .description("멋진 바지")
//                .image("청바지.png")
//                .information(List.of("img1", "img2"))
//                .itemDetails(List.of(detailRequest))
//                .build();
//
//        List<Item> mockItemList = List.of(request1.toItemEntity(), request2.toItemEntity());
//        given(itemRepository.findAll()).willReturn(mockItemList);
//        List<ItemDetail> mockItemDetailList
//
//        List<ItemWithDetailsDTO> result = itemService.findAllItems();
//
//        List<ItemWithDetailsDTO> expected = List.of(fromEntities(request1.toItemEntity()), fromEntities(request2.toItemEntity()));
//
//        assertEquals(result, expected);
//        verify(itemRepository, times(1)).findAll();
//    }

    private void assertItemWithDetails(ItemWithDetailsRegistrationRequest request, ItemWithDetailsDTO result, MockMultipartFile thumbnail, List<MultipartFile> detailImages) {
        assertEquals(request.getName(), result.getName());
        assertEquals(request.getPrice(), result.getPrice());
        assertEquals(request.getCategory(), result.getCategory());
        for(int i = 0; i < request.getItemDetails().size(); i++) {
            assertEquals(request.getItemDetails().get(i).getOption(), result.getItemDetails().get(i).getOption());
            assertEquals(request.getItemDetails().get(i).getQuantity(), result.getItemDetails().get(i).getQuantity());
        }
        assertEquals(fileProperties.getUploadDir() + thumbnail.getOriginalFilename(), result.getImage());
        for(int i = 0; i < detailImages.size(); i++) {
            assertEquals(fileProperties.getUploadDir() + detailImages.get(i).getOriginalFilename(), result.getInformation().get(i));
        }
    }

    private void deleteFile(ItemWithDetailsDTO result) throws Exception{
        Files.deleteIfExists(Path.of(result.getImage()));
        for (String path : result.getInformation()) {
            Files.deleteIfExists(Path.of(path));
        }
    }
}
