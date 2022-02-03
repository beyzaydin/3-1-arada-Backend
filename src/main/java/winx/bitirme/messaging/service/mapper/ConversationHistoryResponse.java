package winx.bitirme.messaging.service.mapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.function.EntityResponse;
import winx.bitirme.messaging.service.entity.Conversation;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConversationHistoryResponse extends ResponseEntity<Conversation> {

    public ConversationHistoryResponse(HttpStatus status) {
        super(status);
    }
}
