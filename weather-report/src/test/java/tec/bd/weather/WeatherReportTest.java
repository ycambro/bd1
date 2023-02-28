package tec.bd.weather;

import org.junit.jupiter.api.Test;
import tec.bd.weather.service.WeatherService;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

public class WeatherReportTest {

    @Test
    public void GivenZipCodeWithNegativeValue_WhenWeatherReportRequested_ThenException() {
        var openWeatherService = mock(WeatherService.class);
        var weatherReport = new WeatherReport(openWeatherService);
        try {
            weatherReport.getByZipCode(-1);
            fail("Negative values are not allowed");
        } catch (IllegalArgumentException e) {
            assertThat(true).isTrue();
        }
    }

    @Test
    public void GivenZipCodeWithOutOfRangeMaxValue_WhenWeatherReportRequested_ThenException() {
        var openWeatherService = mock(WeatherService.class);
        var weatherReport = new WeatherReport(openWeatherService);
        try {
            weatherReport.getByZipCode(1_000_000);
            fail("Zip code value is not allowed");
        } catch (IllegalArgumentException e) {
            assertThat(true).isTrue();
        }
        // c559e941a0da745aa0139aef272bf16c
    }

    @Test
    public void GivenZipCodeWithZeroValue_WhenWeatherReportRequested_ThenException() {
        var openWeatherService = mock(WeatherService.class);
        var weatherReport = new WeatherReport(openWeatherService);
        try {
            weatherReport.getByZipCode(0);
            fail("Zip code value is not allowed");
        } catch (IllegalArgumentException e) {
            assertThat(true).isTrue();
        }
        // c559e941a0da745aa0139aef272bf16c
    }
}
