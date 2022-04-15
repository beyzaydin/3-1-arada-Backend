package winx.bitirme.sleep.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import winx.bitirme.sleep.service.entity.SleepStatisticEntity;

import java.util.List;

@Repository
public interface SleepStatisticRepository extends MongoRepository<SleepStatisticEntity, Long> {
}
