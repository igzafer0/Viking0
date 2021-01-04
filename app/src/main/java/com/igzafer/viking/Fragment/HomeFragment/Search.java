package com.igzafer.viking.Fragment.HomeFragment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.igzafer.viking.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;


public class Search extends Fragment implements Html.ImageGetter{

    public Search() {
        // Required empty public constructor
    }
    View view;
    TextView tw;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fsearch, container, false);
        tw=((TextView)view.findViewById(R.id.tw));

       // tw.setText(Html.fromHtml(text, new URLImageParser(tw, getContext()), null));
        Spanned spanned = Html.fromHtml("s", this, null);

        tw.setText(spanned);

        return view;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
      Drawable empty = getResources().getDrawable(R.drawable.svguser);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, 0, empty.getIntrinsicHeight());
        new LoadImage().execute(source, d);

        return d;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            //Log.d("TAG", "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d("TAG", "onPostExecute drawable " + mDrawable);
            Log.d("TAG", "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, tw.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = tw.getText();
                tw.setText(t);
                Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
                    public final String transformUrl(final Matcher match, String url) {
                        return "";
                    } };



            }
        }
    }
}