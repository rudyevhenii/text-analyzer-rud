package lab.devops.text_analyzer_rud.repository;

import lab.devops.text_analyzer_rud.entity.TextStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextStatsRepository extends JpaRepository<TextStatsEntity, Long> {

}
