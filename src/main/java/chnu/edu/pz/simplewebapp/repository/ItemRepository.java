package chnu.edu.pz.simplewebapp.repository;


/*
  @author   george
  @project   simple-web-app
  @class  ItemRepository
  @version  1.0.0 
  @since 12.10.25 - 20.38
*/

import chnu.edu.pz.simplewebapp.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
}
