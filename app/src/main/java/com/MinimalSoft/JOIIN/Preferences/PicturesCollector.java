package com.MinimalSoft.JOIIN.Preferences;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.MinimalSoft.JOIIN.BU;
import com.MinimalSoft.JOIIN.Utilities.ImageUtility;
import com.bumptech.glide.Glide;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

class PicturesCollector extends AsyncTask<Integer, Void, Boolean> {
    private ProfileFragment fragment;
    private Bitmap profileBitmap;
    private Bitmap coverBitmap;
    private String profileURL;
    private String coverURL;

    PicturesCollector(ProfileFragment fragment, String profileURL, String coverURL) {
        this.profileURL = profileURL;
        this.coverURL = coverURL;
        this.fragment = fragment;
    }

    /*---------------- BitmapTransformation Methods ----------------*/
    /*@Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmapARGB = toTransform.copy(Bitmap.Config.ARGB_8888, false);
        Bitmap blurredCover = ImageUtility.blur(context, bitmapARGB);
        return blurredCover;
    }*/

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
    protected Boolean doInBackground(Integer... params) {
        try {
            //Todo: java.lang.RuntimeException: java.net.SocketTimeoutException: timeout
            profileBitmap = Glide.with(fragment).load(profileURL).asBitmap().into(-1, -1).get();
            Bitmap bitmapRGB = Glide.with(fragment).load(coverURL).asBitmap().into(params[0], params[1]).get();
            Bitmap bitmapARGB = bitmapRGB.copy(Bitmap.Config.ARGB_8888, false);
            coverBitmap = ImageUtility.blur(fragment.getContext(), bitmapARGB);

            savePicture(profileBitmap, BU.USER_PHOTO);
            savePicture(coverBitmap, BU.USER_COVER);

            return true;
        } catch (InterruptedException | ExecutionException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            fragment.setBitmaps(profileBitmap, coverBitmap);
        }
    }

    private void savePicture(Bitmap profileBitmap, String name) throws IOException {
        FileOutputStream fileOutputStream = fragment.getContext().openFileOutput(name, Context.MODE_PRIVATE);
        profileBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.close();

        /*SharedPreferences.Editor preferencesEditor = context.getSharedPreferences("BU_PREF", Context.MODE_PRIVATE).edit();
        preferencesEditor.putBoolean("USER_PICS", true);
        preferencesEditor.apply();*/
    }
}