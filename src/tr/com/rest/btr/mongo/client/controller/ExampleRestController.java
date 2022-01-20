package tr.com.rest.btr.mongo.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tr.com.rest.btr.mongo.client.model.ExampleModel;
import tr.com.rest.btr.mongo.service.logic.ExampleService;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class ExampleRestController {
    ExampleService service;

    @Autowired
    public ExampleRestController(ExampleService service) {
        this.service = service;
    }

    @PostMapping(value = "/create",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ExampleModel create(ExampleModel model) {
        return service.save(model);
    }

    @PutMapping(value = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ExampleModel update(ExampleModel model) throws Exception {
        return service.update(model);
    }

    @DeleteMapping(value = "/delete/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteById(ExampleModel model, @PathParam(value = "id") Long id) throws Exception {
        service.deleteById(id);
    }


    @GetMapping(value = "/get/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void getById(@PathParam(value = "id") Long id) throws Exception {
        service.getById(id);
    }

    @GetMapping(value = "/getList",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExampleModel> getList() {
        return service.getList();
    }
}
