package winx.bitirme.mongo.service.logic;



import winx.bitirme.mongo.client.model.ExampleModel;

import java.util.List;

public interface ExampleService {

    public ExampleModel save(ExampleModel model);

    public ExampleModel update(ExampleModel model) throws Exception;

    public void deleteById(Long id) throws Exception;

    public ExampleModel getById(Long id);

    public List<ExampleModel> getList();
}
