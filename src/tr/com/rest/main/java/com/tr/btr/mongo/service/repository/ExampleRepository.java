package com.tr.btr.mongo.service.repository;

import com.tr.btr.mongo.service.entity.ExampleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends MongoRepository<ExampleEntity, Long> {
}
