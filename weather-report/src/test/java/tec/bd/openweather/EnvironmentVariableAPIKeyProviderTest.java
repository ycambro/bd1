package tec.bd.openweather;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.assertj.core.api.Assertions.*;

public class EnvironmentVariableAPIKeyProviderTest {

    @Test
    public void GivenAPIKeyRequested_WhenAPIKeyAsEnvironmentVariable_ThenReturn() {

        var apiKeyProvider = new EnvironmentVariableAPIKeyProvider();
        var actual = apiKeyProvider.getAPIKey();

        assertThat(actual).isNotNull();
    }
}
