package ch.supsi.dti.isin.meteoapp.HTTPrequest;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.supsi.dti.isin.meteoapp.model.Location;

class TestFetcher {

    private static final String KEY="c28b4f4dae5395d941714f12cba6c3a5";

    private String getUrlString(String url) throws IOException {
        return new String(getUrlBytes(url));
    }

    private byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);

            int bytesRead;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();

            return out.toByteArray();
        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        finally {
            connection.disconnect();
        }
    }

    public Location fetchItem(Location location) {

        Location retLocation=new Location();
        String url=null;
        if (location.getName().equals("Your position")){
            try {
                url = Uri.parse("https://api.openweathermap.org/data/2.5/weather")
                        .buildUpon()
                        .appendQueryParameter("q",location.getName())
                        .appendQueryParameter("appid",KEY)
                        .build().toString();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else {

            try {
                url = Uri.parse("https://api.openweathermap.org/data/2.5/weather")
                        .buildUpon()
                        .appendQueryParameter("q",location.getName())
                        .appendQueryParameter("appid",KEY)
                        .build().toString();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        try {
            String jsonString = getUrlString(url);
            JSONObject jsonObject = new JSONObject(jsonString);
            parseItem(retLocation, jsonObject);
        }catch(Exception e){
            e.printStackTrace();
        }
        return retLocation;
    }

    private void parseItem(Location location, JSONObject jsonObject)throws IOException, JSONException
    {
        JSONObject main = jsonObject.getJSONObject("main");
        JSONObject coord = jsonObject.getJSONObject("coord");
        JSONArray weatherArray = jsonObject.getJSONArray("weather");

        location.setLongitude(coord.getDouble("lon"));
        location.setLatitude(coord.getDouble("lat"));
        location.setTemperatura(main.getDouble("temp")-273.15);
        location.setStatus(weatherArray.getJSONObject(0).getString("description"));
        location.setIcon(weatherArray.getJSONObject(0).getString("icon"));

        Log.i("weather description", weatherArray.getJSONObject(0).getString("icon"));
        Log.i("weather description", weatherArray.getJSONObject(0).getString("description"));
        Log.i("coord json", coord.toString());
        Log.i("main json", main.toString());
        Log.i("weather Array", weatherArray.toString());
    }

}
