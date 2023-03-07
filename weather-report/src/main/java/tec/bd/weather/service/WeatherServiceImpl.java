package tec.bd.weather.service;

import tec.bd.weather.model.Report;
import tec.bd.weather.model.ReportType;
import tec.bd.weather.storage.WeatherReportStorage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherServiceImpl implements WeatherService {

    private final WeatherService remoteWeatherProvider;

    private final WeatherReportStorage weatherReportStorage;

    public WeatherServiceImpl(WeatherService remoteWeatherProvider,
            WeatherReportStorage weatherReportStorage) {

        this.remoteWeatherProvider = remoteWeatherProvider;
        this.weatherReportStorage = weatherReportStorage;
    }

    @Override
    public Report getByZipCode(String zipCode) {

        // 1. Solicitar el dato a el almacenamiento local. Se envia el zipCode y
        // el almacenamiento deberá de resolver si hay datos
        // var now = new Date(System.currentTimeMillis()).toString();
        var now = new Date();
        var formats = new SimpleDateFormat("dd-MM-YY");

        var report = this.weatherReportStorage
                .find((formats.format(now)) + "-" + ReportType.BY_ZIPCODE + "-" + zipCode);

        if (report != null) {
            return report;
        }

        // 2. Solicitar el reporte del clima de forma remota
        var weatheProviderReport = this.remoteWeatherProvider.getByZipCode(zipCode);

        // 3. Guardar el reporte obtenido de forma remota localmente
        weatherReportStorage.save(weatheProviderReport, zipCode);

        return weatheProviderReport;
    }

    @Override
    public Report getByCity(String city) {
        // 1. Solicitar el dato a el almacenamiento local. Se envia la ciudad y
        // el almacenamiento deberá de resolver si hay datos
        // var now = new Date(System.currentTimeMillis()).toString();
        var now = new Date();
        var formats = new SimpleDateFormat("dd-MM-YY");

        var report = this.weatherReportStorage.find((formats.format(now)) + "-" + ReportType.BY_CITY + "-" + city);

        if (report != null) {
            return report;
        }

        // 2. Solicitar el reporte del clima de forma remota
        var weatheProviderReport = this.remoteWeatherProvider.getByCity(city);

        // 3. Guardar el reporte obtenido de forma remota localmente
        weatherReportStorage.save(weatheProviderReport, null);

        return weatheProviderReport;
    }

}
