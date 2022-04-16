package winx.bitirme.sleep.service.service;

import winx.bitirme.sleep.client.model.SleepStatisticModel;
import winx.bitirme.sleep.service.entity.DailyStatisticEntity;

import java.text.ParseException;
import java.util.List;

public interface SleepService {
    DailyStatisticEntity saveSleepData(List<SleepStatisticModel> list) throws ParseException;

    List<DailyStatisticEntity> getWeeklyDataForMobile();

    List<DailyStatisticEntity> getWeeklyDataForWeb(int weekDay);
}
