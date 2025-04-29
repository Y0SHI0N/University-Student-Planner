package Application;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

//public class AI_model {
//    public String modelName = "gemma2:2b";
//    public static String reponseText;
//    public HttpURLConnection conn;
//
//    public void initialiseAIModel(){
//        try{
//        URL url = new URL("http://localhost:11434/api/generate");
//        conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/json; utf-8");
//        conn.setRequestProperty("Accept", "application/json");
//        conn.setDoOutput(true);
//        } catch (Exception e){
//            System.out.println(e);
//        }
//    }
//    public void promptAI(String promptText)
//    {
//        try{
//            String formattedString = "{\"model\": \"%s\", \"prompt\": \"%s\", \"stream\": false}";
//            String jsonInputString = String.format(formattedString, modelName, promptText);
//
//            try(OutputStream os = conn.getOutputStream()){
//                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//            int code = conn.getResponseCode(); // sets the connection state to a code
//            System.out.println(code); // prints the connections state to the console for debugging
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
//            StringBuilder response = new StringBuilder();
//            String line;
//            while((line = in.readLine()) != null){
//                response.append(line);
//            }
//            in.close();
//
//            JSONObject jsonResponse = new JSONObject(response.toString());
//            reponseText = jsonResponse.getString("response");
//            System.out.println(reponseText); // prints the response in json format
//            conn.disconnect();
//        }catch(Exception e){
//            System.out.println(e);
//        }
//    }
//}

public class AI_model {
    public String modelName = "gemma2:2b";
    public HttpURLConnection conn;

    public void initialiseAIModel() {
        try {
            URL url = new URL("http://localhost:11434/api/generate");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String promptAI(String promptText) {
        try {
            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // move here
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            String formattedString = "{\"model\": \"%s\", \"prompt\": \"%s\", \"stream\": false}";
            String jsonInputString = String.format(formattedString, modelName, promptText);

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int code = conn.getResponseCode();
            System.out.println(code);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            String result = jsonResponse.getString("response");

            conn.disconnect();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "AI error occurred";
        }
    }
}

