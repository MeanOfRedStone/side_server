package com.server.side.item.service.impl;

import com.server.side.exception.FileValidationException;
import com.server.side.item.domain.Item;
import com.server.side.item.repository.ItemRepository;
import com.server.side.item.dto.ItemDto;
import com.server.side.item.dto.ItemRegistrationRequest;
import com.server.side.item.service.ItemService;
import com.server.side.util.FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.server.side.item.dto.ItemDto.fromEntity;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;
    private final FileManager manager;

    @Override
    public ItemDto addItem(ItemRegistrationRequest request, MultipartFile thumbnail, List<MultipartFile> detailImages) {
        validateImageFiles(thumbnail, detailImages);

        String thumbnailUrl = manager.saveFile(thumbnail);
        List<String> detailImageUrls = new ArrayList<>();
        for(MultipartFile file : detailImages) {
            detailImageUrls.add(manager.saveFile(file));
        }

        Item item = request.toEntity();
        item.setImage(thumbnailUrl);
        item.setInformation(detailImageUrls);
        return fromEntity(repository.save(item));
    }

    @Override
    public List<ItemDto> findAllItems() {
        return repository.findAll().stream()
                .map(ItemDto::fromEntity)
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
