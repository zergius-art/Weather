import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

class Connection {

    static String request(Map <String, String> param){
        String resultContent = "";

        try {
            URL obj = new URL(param.get("url"));
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            if (Consts.CONNECTION_GET_TOKEN.equals(param.get("type"))) {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                OutputStream out = conn.getOutputStream();
                out.write(Consts.VAULT_PASS.getBytes(StandardCharsets.UTF_8));
                out.close();
            }else if (Consts.CONNECTION_GET_APPID.equals(param.get("type"))) {
                conn.setRequestMethod("GET");
                conn.setRequestProperty("X-Vault-Token", param.get("token"));
            } else {
                conn.setRequestMethod("GET");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            resultContent = content.toString();
            in.close();
            return resultContent;
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e);
        }

        return resultContent;
    }
}
