package org.home.devhub.faq.repository;

import java.util.List;

import org.home.devhub.faq.model.Faq;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends MongoRepository<Faq, Long>{
	
    public List<Faq> findAllByOrderByPositionAsc();


}
