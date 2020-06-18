import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Weather {



    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Map <String, String> param = new HashMap<>();

        //get Vault Token
        param.put("url", Consts.VAULT_URL + Consts.VAULT_URL_AUTH + Consts.VAULT_USER);
        param.put("type", Consts.CONNECTION_GET_TOKEN);
        JSONObject jsonObject = new JSONObject(Connection.request(param));
        JSONObject auth = (JSONObject) jsonObject.get("auth");
        String vaultToken = auth.get("client_token").toString();

        //get APPID from Vault
        param.put("url", Consts.VAULT_URL + Consts.VAULT_URL_SECRET);
        param.put("type", Consts.CONNECTION_GET_APPID);
        param.put("token", vaultToken);
        jsonObject = new JSONObject(Connection.request(param));
        JSONObject data = (JSONObject) jsonObject.get("data");
        String appid = data.get("appid").toString();

        System.out.println("Input City: ");

        //go to restcountries.eu
        param.put("url", getURLForCountries(sc.nextLine()));
        param.remove("type");
        JSONArray response = new JSONArray(Connection.request(param));

        //get neighbours
        JSONObject countryObject = (JSONObject) response.get(0);
        JSONArray neighbours = (JSONArray) countryObject.get("borders");
        param.put("url", getURLForNeighbours(neighbours));
        response = new JSONArray(Connection.request(param));

        //get Capitals' weather
        for (Object it : response) {
            String capital = ((JSONObject)it).get("capital").toString();
            param.put("url", getURLForOpenWeather(capital, appid));
            JSONObject response1 = new JSONObject(Connection.request(param));
            JSONObject main = (JSONObject) response1.get("main");
            JSONArray weather = (JSONArray) response1.get("weather");
            JSONObject weatherDescription = (JSONObject) weather.get(0);

            System.out.printf("In %s it is %sC, %s \n", capital, main.get("temp").toString(), weatherDescription.get("description").toString());
        }

    }


    private static String getURLForCountries (String city) {
        return Consts.RESTCOUNTRIES_URL + Consts.RESTCOUNTRIES_CAPITAL_URL + city;
    }

    private static String getURLForNeighbours (JSONArray neighbours) {
        StringBuilder url = new StringBuilder(Consts.RESTCOUNTRIES_URL + Consts.RESTCOUNTRIES_CODES_URL);
        String prefix = "";
        for (Object neighbour : neighbours) {
            url.append(prefix);
            prefix = ";";
            url.append((String) neighbour);
        }
        return url.toString();
    }

    private static String getURLForOpenWeather (String city, String appid) {
        StringBuilder result = new StringBuilder();
        result.append(Consts.OPENWEATHER_URL);
        result.append("?");

        Map<String, String> params = new HashMap<>();
        params.put(Consts.CITY_PARAM, city);
        params.put(Consts.UNITS_PARAM, Consts.UNITS);
        params.put(Consts.APPID, appid);
        params.put(Consts.LANG_PARAM, Consts.LANG);

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}