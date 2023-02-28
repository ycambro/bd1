package tec.bd.weather.service;

import tec.bd.weather.Report;

public class IMNProvider implements WeatherService {

    @Override
    public Report byZipCode(int zipCode) {
        Report report = new Report();
        report.setTemperature(250);
        report.setHumidity(82);
        report.setPressure(957);
        report.setTempMax(350);
        report.setTempMin(200);
        return report;
    }

    @Override
    public Report byCity(String cityName) {
        Report report = new Report();
        report.setTemperature(250);
        report.setHumidity(82);
        report.setPressure(957);
        report.setTempMax(350);
        report.setTempMin(200);
        return report;
    }

}