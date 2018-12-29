package com.educharge.educharge.SpringRestServices;

import android.os.AsyncTask;
import android.util.Log;

import com.educharge.educharge.Models.Profile;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by R Dinesh Kumar on 22-9-17.
 */

public class HttpRequestPostProfile extends AsyncTask<Void, Void, Profile> {

    public  Profile profile;
    public String res="0";

    @Override
    protected Profile doInBackground(Void... params) {
            try {
                final String url = "http://52.14.15.227/Profiles/createProfile";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Profile p = restTemplate.postForObject(url,profile,Profile.class);
                res = "Registration Successful";
                return p;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
                res = "Failed to Register";
                // Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            return null;
    }
    @Override
    protected void onPostExecute(Profile profile) {
    }
}