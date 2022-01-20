package tr.com.rest.btr.mongo.client.model;

import com.sun.istack.NotNull;

public class ExampleModel {
    private Long id;

    @NotNull
    private String name;

    public ExampleModel(Long id, String name) {
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
