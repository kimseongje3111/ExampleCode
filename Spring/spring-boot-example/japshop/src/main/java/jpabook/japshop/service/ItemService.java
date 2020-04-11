package jpabook.japshop.service;

import jpabook.japshop.domain.item.Item;
import jpabook.japshop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    /*
     * 상품 등록
     */
    @Transactional
    public void saveItem(Item item) {
        repository.save(item);
    }

    /*
     * 상품 조회
     */
    public List<Item> findItems() {
        return repository.findAll();
    }

    public Item findOne(Long id) {
        return repository.findOne(id);
    }
}
