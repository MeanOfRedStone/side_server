package com.server.side.item.service.impl;

import com.server.side.exception.FileValidationException;
import com.server.side.item.domain.Item;
import com.server.side.item.domain.ItemRepository;
import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.server.side.item.dto.ItemDto.fromEntity;
import static com.server.side.util.FileManager.saveFile;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Override
    public ItemDto addItem(ItemRegistrationRequest request, MultipartFile thumbnail, List<MultipartFile> detailImages) {
        validateImageFiles(thumbnail, detailImages);

        String thumbnailUrl = saveFile(thumbnail);
        List<String> detailImageUrls = new ArrayList<>();
        for(MultipartFile file : detailImages) {
            detailImageUrls.add(saveFile(file));
        }

        Item item = request.toEntity();
        item.setImage(thumbnailUrl);
        item.setInformation(detailImageUrls);

        return fromEntity(repository.save(item));
    }

    private void validateImageFiles(MultipartFile thumbnail, List<MultipartFile> detailImages) {
        if(thumbnail == null || thumbnail.isEmpty()) throw new FileValidationException("{file.thumbnail.required}");
    }

    @Override
    public List<ItemDto> findAllItems() {
        return repository.findAll().stream()
                .map(ItemDto::fromEntity)
                .collect(Collectors.toList());
    }
}
