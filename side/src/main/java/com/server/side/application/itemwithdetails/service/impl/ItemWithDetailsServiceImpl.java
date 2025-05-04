package com.server.side.application.itemwithdetails.service.impl;

import com.server.side.exception.FileValidationException;
import com.server.side.item.domain.Item;
import com.server.side.itemdetail.domain.ItemDetail;
import com.server.side.itemdetail.repository.ItemDetailRepository;
import com.server.side.item.repository.ItemRepository;
import com.server.side.application.itemwithdetails.dto.ItemWithDetailsDTO;
import com.server.side.application.itemwithdetails.dto.ItemWithDetailsRegistrationRequest;
import com.server.side.application.itemwithdetails.service.ItemWithDetailsService;
import com.server.side.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.server.side.application.itemwithdetails.dto.ItemWithDetailsDTO.fromEntities;

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

        return fromEntities(resultItem, resultItemDetails);
    }

    @Override
    public List<ItemWithDetailsDTO> findAllItemsWithDetails() {
        return itemRepository.findAll().stream()
                .map(item -> {
                    List<ItemDetail> details = itemDetailRepository.findAllByItemId(item.getId());
                    return fromEntities(item, details);
                })
                .collect(Collectors.toList());
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
