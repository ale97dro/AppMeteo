package ch.supsi.dti.isin.meteoapp.HTTPrequest;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.supsi.dti.isin.meteoapp.model.Location;

class TestFetcher {

    private static final String KEY="";
    private String getUrlString(String url){
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
        } finally {
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

            }
        }else {

            try {
                url = Uri.parse("https://api.openweathermap.org/data/2.5/weather")
                        .buildUpon()
                        .appendQueryParameter("q",location.getName())
                        .appendQueryParameter("appid",KEY)
                        .build().toString();

            }catch (Exception e){

            }

        }
        try {
            String jsonString = getUrlString(url);
            JSONObject jsonObject = new JSONObject(jsonString);
            parseItem(retLocation, jsonObject);
        }catch(Exception e){

        }
        return retLocation;
    }

    private void parseItem(Location location, JSONObject jsonObject)throws IOException, JSONException {

    }

}
