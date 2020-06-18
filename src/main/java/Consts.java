public class Consts {

    // API https://restcountries.eu/
    static final String RESTCOUNTRIES_URL = "https://restcountries.eu/rest/v2/";
    static final String RESTCOUNTRIES_CAPITAL_URL = "capital/";
    static final String RESTCOUNTRIES_CODES_URL = "alpha?codes=";

    // API https://openweathermap.org/
    static final String OPENWEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";
    static final String CITY_PARAM = "q";
    static final String LANG_PARAM = "lang";
    static final String LANG = "en";
    static final String UNITS_PARAM = "units";
    static final String UNITS = "metric";
    static final String APPID = "appid";
    //static final String APPID_VALUE = "2913fefa0f2d453e270542b141194436";

    // API Vault
    static final String CONNECTION_GET_TOKEN = "getToken";
    static final String CONNECTION_GET_APPID = "getAPPID";
    static final String VAULT_URL = "https://dmoneyapu01.alfa.bank.int:8200/";
    static final String VAULT_URL_AUTH = "v1/auth/userpass/login/";
    static final String VAULT_URL_SECRET = "v1/mw/api/restcountries";
    static final String VAULT_USER = "test";
    static final String VAULT_PASS = "{\"password\": \"test\"}";


}
