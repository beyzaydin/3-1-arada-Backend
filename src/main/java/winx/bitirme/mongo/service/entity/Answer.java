package winx.bitirme.mongo.service.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Answer {
    ClusteringQuestion question;
    String answer;
}
