package chnu.edu.pz.simplewebapp.controller;


/*
  @author   george
  @project   simple-web-app
  @class  ItemRestController
  @version  1.0.0 
  @since 12.10.25 - 20.20
*/

import chnu.edu.pz.simplewebapp.model.Item;
import chnu.edu.pz.simplewebapp.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/items/")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemService itemService;

    @RequestMapping("hello")
    public String hello(){
        return "hello from rest controller";
    }

    @RequestMapping("")
    public List<Item> getAllItems(){
        return itemService.getItems();
    }

    // CRUD  -  create read update delete

    @PostMapping
    public Item createItem(@RequestBody Item item){
        return itemService.create(item);
    }

    @RequestMapping("{id}")
    public Item getAOneItem(@PathVariable String id){
        return itemService.getItem(id);
    }
        @DeleteMapping("{id}")
    public void deleteAOneItem(@PathVariable String id){
        itemService.delete(id);
    }


    @PutMapping
    public Item updateItem(@RequestBody Item item){
        return itemService.update(item);
    }









}
