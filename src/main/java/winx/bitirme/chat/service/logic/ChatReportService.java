package winx.bitirme.chat.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import winx.bitirme.chat.client.model.ChatReportModel;
import winx.bitirme.chat.service.converter.ChatReportMapper;
import winx.bitirme.chat.service.entity.ChatReportEntity;
import winx.bitirme.chat.service.repository.ChatReportRepository;
import winx.bitirme.mongo.service.logic.SequenceGeneratorService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatReportService {
    private final ChatReportMapper mapper;
    private final ChatReportRepository repository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public ChatReportService(ChatReportMapper mapper,
                             ChatReportRepository repository,
                             SequenceGeneratorService sequenceGeneratorService, MongoTemplate mongoTemplate) {
        this.mapper = mapper;
        this.repository = repository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.mongoTemplate = mongoTemplate;
    }


    public ChatReportModel save(ChatReportModel model) {
        long id = sequenceGeneratorService.generateSequence(ChatReportEntity.SEQUENCE_NAME);
        model.setId(id);
        model.setReportDate(OffsetDateTime.now());
        return mapper.convertToModel(repository.save(mapper.convertToEntity(model)));
    }

    public ChatReportModel update(ChatReportModel model) throws Exception {
        Optional<ChatReportEntity> entity = repository.findById(model.getId());
        if (!entity.isPresent())
            throw new Exception("This entity does not exist.");
        return mapper.convertToModel(repository.save(mapper.convertToEntity(model)));
    }

    public void delete(ChatReportModel model) throws Exception {
        Optional<ChatReportEntity> entity = repository.findById(model.getId());
        if (!entity.isPresent())
            throw new Exception("This entity does not exist.");
        repository.delete(mapper.convertToEntity(model));
    }

    public List<ChatReportModel> getListByReportedEmail(String reported) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reported").is(reported));
        return mapper.convertToModelList(mongoTemplate.find(query, ChatReportEntity.class));
    }

    public List<ChatReportModel> getListByReporterEmail(String reporter) {
        Query query = new Query();
        query.addCriteria(Criteria.where("reporter").is(reporter));
        return mapper.convertToModelList(mongoTemplate.find(query, ChatReportEntity.class));
    }
}
