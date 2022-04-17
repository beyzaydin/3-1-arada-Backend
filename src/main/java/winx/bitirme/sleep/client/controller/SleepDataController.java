package winx.bitirme.sleep.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import winx.bitirme.achievement.client.model.MobileSleepResponse;
import winx.bitirme.sleep.client.model.SleepDataRequest;
import winx.bitirme.sleep.client.model.SleepWebResponse;
import winx.bitirme.sleep.service.entity.DailyStatisticEntity;
import winx.bitirme.sleep.service.service.SleepService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/sleep")
@CrossOrigin("http://localhost:3000")
public class SleepDataController {
    private final SleepService service;

    @Autowired
    public SleepDataController(SleepService service) {
        this.service = service;
    }

    @PostMapping
    public void saveList(@RequestBody SleepDataRequest model) throws ParseException {
        model.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        service.saveSleepData(model.getModel());
        System.out.println(model);
    }

    @GetMapping("/mobile")
    public MobileSleepResponse mobile() {
        MobileSleepResponse model = new MobileSleepResponse();
        model.setList(service.getWeeklyDataForMobile());
        return model;
    }

    @GetMapping("/web")
    public SleepWebResponse web(@RequestParam Integer week) {
        return service.getWeeklyDataForWeb(week);
    }
}
