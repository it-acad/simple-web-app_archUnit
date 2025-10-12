package chnu.edu.pz.simplewebapp.service;


/*
  @author   george
  @project   simple-web-app
  @class  ItemService
  @version  1.0.0 
  @since 12.10.25 - 20.27
*/

import chnu.edu.pz.simplewebapp.model.Item;
import chnu.edu.pz.simplewebapp.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>(
            Arrays.asList(
                    new Item("1", " name1", "001", "d1"),
                    new Item("2", " name2", "002", "d2"),
                    new Item("3", " name3", "003", "d3")

            )
    );

  //  @PostConstruct
//    void init(){
//        itemRepository.saveAll(items);
//    }



    public List<Item> getItems(){
        return itemRepository.findAll();
    }

    public Item create(Item item) {
        return itemRepository.save(item);
    }

    public Item getItem(String id) {
      return   itemRepository.findById(id).get();
    }

    public void delete(String id) {
        itemRepository.deleteById(id);
    }

    public Item update(Item item) {
        return itemRepository.save(item);
    }
}
