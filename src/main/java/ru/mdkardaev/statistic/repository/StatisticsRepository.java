package ru.mdkardaev.statistic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mdkardaev.statistic.entity.Statistics;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
}
