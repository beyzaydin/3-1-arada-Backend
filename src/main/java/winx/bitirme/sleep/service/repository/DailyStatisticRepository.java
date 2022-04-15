package winx.bitirme.sleep.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.sleep.service.entity.DailyStatisticEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface DailyStatisticRepository extends MongoRepository<DailyStatisticEntity, Long> {
    DailyStatisticEntity findByEmail(String email);

    List<DailyStatisticEntity> findAllByEmail(String email);

    DailyStatisticEntity findByEmailAndDate(String email, LocalDate date);

    List<DailyStatisticEntity> findBySleepStartTimeBetween(Date from, Date to);
}
