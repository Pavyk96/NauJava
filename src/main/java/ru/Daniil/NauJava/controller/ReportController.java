package ru.Daniil.NauJava.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.Daniil.NauJava.model.Report;
import ru.Daniil.NauJava.service.ReportService;

import java.util.Map;

/**
 * Контроллер отчетов.
 *
 * @author Daniil Mezev
 */
@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Map<String, Long>> createReport() {
        Long reportId = reportService.createReport();
        return ResponseEntity.ok(Map.of("reportId", reportId));
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getReport(@PathVariable("id") Long reportId, Model model) {
        Report report = reportService.getReport(reportId);
        model.addAttribute("report", report);
        return "report";
    }
}
