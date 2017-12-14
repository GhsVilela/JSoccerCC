package com.example.ghsvi.jsoccercc;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class PesquisaTimes extends AppCompatActivity {

    //public Button click;
    private static Context mContext;
    private static LinearLayout linearLayout;
    private static ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesquisa_times);
        mContext = getApplicationContext();
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        // click = (Button) findViewById(R.id.button);

        mProgressBar = new ProgressDialog(PesquisaTimes.this);
        mProgressBar.setCancelable(false);
        mProgressBar.setTitle("Carregando Dados...");
        mProgressBar.setMessage("Iniciando API");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        mProgressBar.show();

        OrganizarDataTimes process = new OrganizarDataTimes();
        process.execute();


            /*
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrganizarDataTimes process = new OrganizarDataTimes();
                process.execute();
                //brasao.setImageDrawable(LoadImageFromWebOperations(OrganizarDataTimes.urlBrasao));
                //dataText.setText(OrganizarDataTimes.urlBrasao);


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
