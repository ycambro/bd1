package tec.bd.weather.storage;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import tec.bd.weather.model.Report;
import tec.bd.weather.model.ReportType;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class InMemoryWeatherReportStorageTest {

    @Test
    public void GivenZipCode_SaveReport() {

        InMemoryWeatherReportStorage storage = new InMemoryWeatherReportStorage();
        Report report = new Report();
        report.setDate(new Date());
        report.setLocation("Buenos Aires");
        report.setReportType(ReportType.BY_CITY);
        storage.save(report, null);
        assertThat(storage.find(storage.generateKeyFromReport(report, "Buenos Aires"))).isNotNull();

    }

    @Test
    public void GivenReportKey_RemoveReport() {

        InMemoryWeatherReportStorage weatherReportStorage = new InMemoryWeatherReportStorage();
        Report report = new Report();
        report.setDate(new Date());
        report.setLocation("Georgia");
        report.setReportType(ReportType.BY_CITY);
        weatherReportStorage.save(report, null);

        var key = weatherReportStorage.generateKeyFromReport(report, "Georgia");
        assertThat(weatherReportStorage.find(key)).isNotNull();
        weatherReportStorage.remove(key);
        assertThat(weatherReportStorage.find(key)).isNull();

    }

    @Test
    public void GivenReport_GenerateKey() {
        var weatherReportStorage = new InMemoryWeatherReportStorage();
        Report report = new Report();
        report.setDate(new Date());
        report.setLocation("New York");
        report.setReportType(ReportType.BY_CITY);
        weatherReportStorage.save(report, null);

        var key = weatherReportStorage.generateKeyFromReport(report, "New York");
        assertThat(key).isNotNull();
        var dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        assertThat(key).isEqualTo(dateFormat.format(report.getDate()) + "-" + ReportType.BY_CITY + "-" + "New York");

    }

    @Test
    public void GivenKey_FindSavedReport() {
        InMemoryWeatherReportStorage weatherReportStorage = new InMemoryWeatherReportStorage();
        Report report = new Report();
        report.setDate(new Date());
        report.setLocation("Paris");
        report.setReportType(ReportType.BY_CITY);
        weatherReportStorage.save(report, null);

        var key = weatherReportStorage.generateKeyFromReport(report, "Paris");
        var savedReport = weatherReportStorage.find(key);
        assertThat(savedReport).isNotNull();

    }

    @Test
    public void GivenNewReport_Update() {
        InMemoryWeatherReportStorage weatherReportStorage = new InMemoryWeatherReportStorage();
        Report oldReport = new Report();
        oldReport.setDate(new Date());
        oldReport.setLocation("Buenos Aires");
        oldReport.setReportType(ReportType.BY_CITY);
        weatherReportStorage.save(oldReport, null);

        Report newReport = new Report();
        newReport.setDate(new Date());
        newReport.setLocation("Buenos Aires");
        newReport.setReportType(ReportType.BY_CITY);

        var report = weatherReportStorage.update(newReport);
        assertThat(report).isNotNull();
    }
}