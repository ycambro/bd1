package tec.bd.openweather;

public class EnvironmentVariableAPIKeyProvider implements OpenWeatherAPIKeyProvider {

    private static final String OW_API_KEY_ENV_VAR_NAME = "OW_API_KEY";

    public EnvironmentVariableAPIKeyProvider() {

    }

    @Override
    public String getAPIKey() {
        // return System.getenv(OW_API_KEY_ENV_VAR_NAME);
        return "f2edfe27947cf9026ce4931dd3d5d5ab";
    }
}
