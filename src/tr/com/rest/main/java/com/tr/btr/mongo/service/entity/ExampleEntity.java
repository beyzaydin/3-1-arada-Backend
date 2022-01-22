package com.tr.btr.mongo.service.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "examples")
@Data
public class ExampleEntity {
    @Transient
    public static final String SEQUENCE_NAME = "examples_sequence";

    @Id
    private Long id;

    @NotNull
    @Field(name = "name")
    private String name;

    public ExampleEntity() {

    }

    public ExampleEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
