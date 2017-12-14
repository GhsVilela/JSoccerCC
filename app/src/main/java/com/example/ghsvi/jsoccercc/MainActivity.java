package com.example.ghsvi.jsoccercc;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    //public Button click;
    private static Context mContext;
    private static LinearLayout linearLayout;
    private static ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        // click = (Button) findViewById(R.id.button);

        mProgressBar = new ProgressDialog(MainActivity.this);
        mProgressBar.setCancelable(false);
        mProgressBar.setTitle("Carregando Dados...");
        mProgressBar.setMessage("Iniciando API");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mProgressBar.show();

        fetchData process = new fetchData();
        process.execute();


            /*
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
                //brasao.setImageDrawable(LoadImageFromWebOperations(fetchData.urlBrasao));
                //dataText.setText(fetchData.urlBrasao);


            }
        });
        */
    }
    /*
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }
    */

    public static Context getContext() {
        return mContext;
    }

    public static LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public static ProgressDialog getmProgressBar() {
        return mProgressBar;
    }
}
