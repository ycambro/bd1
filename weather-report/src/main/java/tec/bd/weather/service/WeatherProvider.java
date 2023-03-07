package tec.bd.weather.service;

import tec.bd.weather.model.Report;

public interface WeatherProvider {

    Report getByZipCode(String zipCode);

    Report getByCity(String city);
}
