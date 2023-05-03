package com.shoppio.backend.controller;


import com.shoppio.backend.model.Category;
import com.shoppio.backend.model.Item;
import com.shoppio.backend.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new RuntimeException("The item does not exist"));
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable(value = "id") Long itemId,
                                           @RequestBody Map<String, Object> fields) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            Item itemToUpdate = optionalItem.get();
            fields.forEach((key, value) -> {
                switch (key) {
                    case "title":
                        itemToUpdate.setTitle((String) value);
                        break;
                    case "price":
                        itemToUpdate.setPrice((Double) value);
                        break;
                    case "priceBefore":
                        itemToUpdate.setPriceBefore((Double) value);
                        break;
                    case "rating":
                        itemToUpdate.setRating((Integer) value);
                        break;
                    case "amount":
                        itemToUpdate.setAmount((Integer) value);
                        break;
                    case "description":
                        itemToUpdate.setDescription((String) value);
                        break;
                    case "category":
                        itemToUpdate.setCategory(Category.valueOf((String) value));
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid field: " + key);
                }
            });
            itemToUpdate.setModifyTime(LocalDateTime.now());
            Item updatedItem = itemRepository.save(itemToUpdate);
            return ResponseEntity.ok(updatedItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
