package tec.bd.weather.storage;

import tec.bd.weather.model.Report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryWeatherReportStorage implements WeatherReportStorage {

    private Map<String, Report> db;

    public InMemoryWeatherReportStorage() {
        this.db = new LinkedHashMap<>();
    }

    @Override
    public void save(Report report, String zipCode) {
        if (zipCode == null) {
            String key = generateKeyFromReport(report, report.getLocation());
            db.put(key, report);
        } else {
            String key = generateKeyFromReport(report, zipCode);
            db.put(key, report);
        }
    }

    @Override
    public void remove(String reportKey) {
        if (db.containsKey(reportKey)) {
            db.remove(reportKey);
        }
    }

    @Override
    public Report update(Report newReport) {
        String key = generateKeyFromReport(newReport, newReport.getLocation());
        var oldReport = this.db.get(key);

        if (oldReport != null) {
            this.db.remove(key);
            this.db.put(key, newReport);
            return newReport;
        }
        return oldReport;
    }

    @Override
    public Report find(String reportKey) {
        if (!db.containsKey(reportKey)) {
            return null;
        }
        return db.get(reportKey);
    }

    @Override
    public List<Report> find() {
        return new ArrayList<Report>(db.values());
    }

    public String generateKeyFromReport(Report report, String type) {
        var dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        return (dateFormat.format(report.getDate()) + "-" + report.getReportType() + "-" + type);
    }
}
