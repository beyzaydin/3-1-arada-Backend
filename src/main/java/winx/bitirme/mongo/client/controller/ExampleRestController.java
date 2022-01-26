package winx.bitirme.mongo.client.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.mongo.client.model.ExampleModel;
import winx.bitirme.mongo.service.logic.ExampleService;

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
    public ExampleModel create(@RequestBody ExampleModel model) {
        return service.save(model);
    }

    @PutMapping(value = "/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ExampleModel update(@RequestBody ExampleModel model) throws Exception {
        return service.update(model);
    }

    @DeleteMapping(value = "/delete/{id}")
    public void deleteById(@PathVariable Long id) throws Exception {
        service.deleteById(id);
    }


    @GetMapping(value = "/get/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ExampleModel getById(@PathVariable Long id) throws Exception {
        return service.getById(id);
    }

    @GetMapping(value = "/getList",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExampleModel> getList() {
        return service.getList();
    }
}
