package com.travel.toy3.util;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class Geocoding {
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json";

    private static String apiKey;

    @Autowired
    public Geocoding(GeocodingProperties properties) {
        this.apiKey = properties.getApiKey();
    }

    public static GoogleMap getGeoInfo(String address) throws IOException { // ex. 서울특별시 강남구 강남대로 364
        String url = BASE_URL + "?address=" + address + "&key=" + apiKey;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(jsonData, Map.class);

        Map<String,Object> result = ((List<Map<String,Object>>) map.get("results")).get(0);
        Map<String,Object> geometry = (Map<String,Object>) result.get("geometry");
        Map<String,Object> location = (Map<String,Object>) geometry.get("location");

        return GoogleMap.builder()
                .latitude((double) location.get("lat"))
                .longitude((double) location.get("lng"))
                .formattedAddress(address)
                .build();
    }

    public static String getAddress(double latitude, double longitude) throws IOException {
        String url = BASE_URL + "?latlng=" + latitude + "," + longitude + "&language=ko&key=" + apiKey;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String jsonData = response.body().string();

        Gson gson = new Gson();
        Map<String,Object> map = gson.fromJson(jsonData, Map.class);

        Map<String,Object> result = ((List<Map<String,Object>>) map.get("results")).get(0);
        String address = (String) result.get("formatted_address");

        return address;
    }
}
