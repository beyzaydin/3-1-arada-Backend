package winx.bitirme.messaging.service.mapper;

import winx.bitirme.messaging.service.entity.ContactList;

public class ContactListResponse {
    private boolean isEmpty;
    private ContactList contacts;
    public ContactListResponse(ContactList data){
        if (data.getContacts().size()== 0){
            this.isEmpty = true;
        }
        else {
            this.isEmpty = false;
            this.contacts = data;
        }
    }
}
