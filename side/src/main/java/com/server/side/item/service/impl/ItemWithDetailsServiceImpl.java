package com.server.side.item.service.impl;

import com.server.side.exception.FileValidationException;
import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemDetail;
import com.server.side.item.repository.ItemDetailRepository;
import com.server.side.item.repository.ItemRepository;
import com.server.side.item.dto.ItemWithDetailsDTO;
import com.server.side.item.dto.ItemWithDetailsRegistrationRequest;
import com.server.side.item.service.ItemWithDetailsService;
import com.server.side.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemWithDetailsServiceImpl implements ItemWithDetailsService {

    private final ItemRepository itemRepository;
    private final ItemDetailRepository itemDetailRepository;
    private final FileManager manager;

    @Override
    public ItemWithDetailsDTO addItemWithDetails(ItemWithDetailsRegistrationRequest request, MultipartFile thumbnail, List<MultipartFile> detailImages) {
        validateImageFiles(thumbnail, detailImages);

        String thumbnailUrl = manager.saveFile(thumbnail);
        List<String> detailImageUrls = new ArrayList<>();
        for(MultipartFile file : detailImages) {
            detailImageUrls.add(manager.saveFile(file));
        }

        Item item = request.toItemEntity();
        item.setImage(thumbnailUrl);
        item.setInformation(detailImageUrls);
        Item resultItem = itemRepository.save(item);
        List<ItemDetail> resultItemDetails = itemDetailRepository.saveAll(request.toItemDetailEntity());

        return ItemWithDetailsDTO.fromEntities(resultItem, resultItemDetails);
    }

    @Override
    public List<ItemWithDetailsDTO> findAllItems() {
//        return repository.findAll().stream()
//                .map(ItemWithDetailsDTO::fromEntities)
//                .collect(Collectors.toList());
        return null;
    }

    private void validateImageFiles(MultipartFile thumbnail, List<MultipartFile> detailImages) {
        if(thumbnail.isEmpty()) throw new FileValidationException("file.image.empty");

        detailImages.stream()
                .filter(MultipartFile::isEmpty)
                .findFirst()
                .ifPresent(file -> {
                    throw new FileValidationException("file.image.empty");
                });
    }
}
