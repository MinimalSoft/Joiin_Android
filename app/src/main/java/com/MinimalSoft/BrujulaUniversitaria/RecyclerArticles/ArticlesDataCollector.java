package com.MinimalSoft.BrujulaUniversitaria.RecyclerArticles;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import android.os.AsyncTask;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.MinimalSoft.BrujulaUniversitaria.Tabs.Articles;

public class ArticlesDataCollector extends AsyncTask<Void, Void, Boolean> {
    private static final String URL_JSON_DATA = "http://brujulauniversitaria.com.mx/wp-json/wp/v2/posts?_wp_json_nonce=4355d0c4b3&items=id,title,image,link";
    private Articles articlesFragment;
    private ArticleAdapter articlesAdapter;
    private List<Article> items;

    public ArticlesDataCollector(final Articles fragment, final ArticleAdapter entryAdapter) {
        articlesAdapter = entryAdapter;
        articlesFragment = fragment;
    }

    private String fetchJSONData () throws IOException {
        URL url = new URL(URL_JSON_DATA);
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

    private Bitmap downloadBitmap (String link) throws IOException {
        URL url = new URL(link);
        InputStream stream = (InputStream) url.getContent();
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        stream.close();
        return bitmap;
    }

    @Override
    protected Boolean doInBackground (Void... params) {
        try {
            String jsonData = this.fetchJSONData();
            JSONArray posts = new JSONArray(jsonData);

            int id;
            Bitmap image;
            JSONObject json;
            String title, link, url;
            short count = (short) posts.length();
            items = new ArrayList<>(count);

            for (short i = 0; i < count; i++) {
                json = posts.getJSONObject(i);
                id = json.getInt("id");
                url = json.getString("image");
                link = json.getString("link");
                image = this.downloadBitmap(url);
                title = json.getJSONObject("title").getString("rendered");

                items.add(new Article(title, id, image, link));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        } catch (JSONException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);

        if (bool) {
            articlesAdapter.removeOld();
            articlesAdapter.addNew(items);
            articlesFragment.onPostUpdateSucceed();
        } else {
            articlesAdapter.stopCollector();
            articlesFragment.onPostUpdateFailed();
        }
    }
}