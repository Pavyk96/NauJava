package ru.Daniil.NauJava.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.Daniil.NauJava.model.Report;

/**
 * ReportRepository
 *
 * @author Daniil Mezev
 */
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
