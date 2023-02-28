package tec.bd.weather.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class IMNProviderTest {

    @Test
    public void GivenZipCode_WhenWeatherReportRequested_ThenReturnValue() {

        var imnProvider = new IMNProvider();

        var report = imnProvider.byZipCode(10000);

        assertThat(report.getTemperature()).isEqualTo(250);
        assertThat(report.getHumidity()).isEqualTo(82);
        assertThat(report.getPressure()).isEqualTo(957);
        assertThat(report.getTempMax()).isEqualTo(350);
        assertThat(report.getTempMin()).isEqualTo(200);

    }

}
