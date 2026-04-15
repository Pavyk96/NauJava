package ru.Daniil.NauJava.service;

import ru.Daniil.NauJava.model.Report;

/**
 * ReportService
 *
 * @author Daniil Mezev
 */
public interface ReportService {
    Long createReport();

    Report getReport(Long reportId);
}
