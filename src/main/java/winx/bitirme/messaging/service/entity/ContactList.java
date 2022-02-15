package winx.bitirme.messaging.service.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document

public class ContactList {
    @Id
    private long userId;
    private List<Long> contacts;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Long> getContacts() {
        return contacts;
    }

    public void setContacts(List<Long> contacts) {
        this.contacts = contacts;
    }
}
