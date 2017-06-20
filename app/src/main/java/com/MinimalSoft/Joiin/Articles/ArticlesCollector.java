package com.MinimalSoft.Joiin.Articles;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.MinimalSoft.Joiin.Joiin;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

class ArticlesCollector extends AsyncTask<Void, Void, String> {
    private SwipeRefreshLayout spinner;
    private ArticlesAdapter adapter;
    private TextView textView;

    private List<Article> articles;

    ArticlesCollector(ArticlesAdapter adapter, SwipeRefreshLayout spinner, TextView textView) {
        this.textView = textView;
        this.spinner = spinner;
        this.adapter = adapter;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected String doInBackground(Void... params) {
        String apiURL = Joiin.WP_URL + "/wp-json/wp/v2/posts?items=title,image,link";

        try {
            //OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(apiURL).get().build();
            Response response = new OkHttpClient().newCall(request).execute();

            if (response.isSuccessful()) {
                String json = response.body().string();
                Type type = new TypeToken<List<Article>>() {
                }.getType();
                articles = new Gson().fromJson(json, type);
                return null;
            } else {
                return response.message();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == null && articles != null) {
            adapter.updateArticles(articles);
            textView.setVisibility(View.INVISIBLE);
        } else {
            textView.setText(result);
        }

        spinner.setRefreshing(false);
    }
}
