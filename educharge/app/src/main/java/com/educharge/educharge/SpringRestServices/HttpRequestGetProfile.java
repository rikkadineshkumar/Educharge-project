package com.educharge.educharge.SpringRestServices;

import android.os.AsyncTask;
import android.util.Log;

import com.educharge.educharge.Models.Profile;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by R Dinesh Kumar on 22-9-17.
 */

public class HttpRequestGetProfile extends AsyncTask<Void, Void, Profile > {
    @Override
    protected Profile doInBackground(Void... params) {
        try {
            final String url = "http://52.14.15.227/Profiles/showProfile/2";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Profile profile = restTemplate.getForObject(url, Profile.class);
            return profile;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
           // Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Profile profile) {
    }
}
