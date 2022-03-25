package winx.bitirme.chat.service.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import winx.bitirme.chat.service.converter.ChatReportMapper;
import winx.bitirme.chat.service.repository.ChatReportRepository;

@Service
public class ChatReportService {
    private final ChatReportMapper mapper;
    private final ChatReportRepository repository;

    @Autowired
    public ChatReportService(ChatReportMapper mapper, ChatReportRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }


}
