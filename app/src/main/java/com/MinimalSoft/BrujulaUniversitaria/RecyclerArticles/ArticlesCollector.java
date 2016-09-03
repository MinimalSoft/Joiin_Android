package com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles;

import com.MinimalSoft.BrujulaUniversitaria.Tabs.Articles;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ArticlesCollector extends AsyncTask<Void, Void, Boolean> {
    private String wp_url;
    private String message;
    private List<Article> articleList;
    private Articles articlesFragment;

    public ArticlesCollector(Articles fragment, String api_url) {
        articlesFragment = fragment;
        wp_url = api_url;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            String jsonData = this.fetchJSONData();
            JSONArray posts = new JSONArray(jsonData);

            int id;
            JSONObject json;
            String title, link, url;
            short count = (short) posts.length();
            articleList = new ArrayList<>(count);

            for (short i = 0; i < count; i++) {
                json = posts.getJSONObject(i);
                id = json.getInt("id");
                url = json.getString("image");
                link = json.getString("link");
                title = json.getJSONObject("title").getString("rendered");

                articleList.add(new Article(id, url, link, title));
            }
        } catch (IOException exc) {
            message = exc.getMessage();
            return Boolean.FALSE;
        } catch (JSONException exc) {
            message = exc.getMessage();
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private String fetchJSONData() throws IOException {
        URL url = new URL(wp_url);
        InputStream inputStream = url.openStream();
        InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        StringBuilder webData = new StringBuilder();

        int data = bufferedReader.read();
        while (data != -1) {
            webData.append((char) data);
            data = bufferedReader.read();
        }

        bufferedReader.close();
        streamReader.close();
        inputStream.close();

        return webData.toString();
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);

        if (bool) {
            articlesFragment.onPostUpdateSucceed(articleList);
        } else {
            articlesFragment.onPostUpdateFailed(message);
        }
    }
}