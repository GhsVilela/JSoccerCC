package com.example.ghsvi.jsoccercc;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ghsvi on 09/12/2017.
 */

public class OrganizarDataTimes extends AsyncTask<Void, String, Void> {

    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    String  urlBrasao = "";
    ArrayList<EstruturaTimes> lista = new ArrayList<>();
    LinearLayout linearLayout;
    int tam;

    @Override
    protected Void doInBackground(Void... voids) {
        try {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=" + InserirPesquisaTimes.time.getText())
                        .build();

                Response response = client.newCall(request).execute();
                data = response.body().string();

                /*

                URL url = new URL("http://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=" + InserirPesquisaTimes.time.getText());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


                String line = "";

                while(line!=null)
                {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                */

                JSONObject jo = new JSONObject(data);
                JSONArray jsonArray = jo.getJSONArray("teams");

                tam = jsonArray.length();

                int total = jsonArray.length()-1;

                for(int i=0; i<jsonArray.length(); i++)
                {
                    Thread.sleep(500); // 2 segundos

                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    EstruturaTimes e = new EstruturaTimes(jsonObject.get("strTeam").toString(), jsonObject.get("strStadium").toString(), jsonObject.get("strDescriptionEN").toString(), jsonObject.get("strTeamBadge").toString());
                    lista.add(e);

                    String m = i % 2 == 0 ? "Organizing Components" : "Wait!!";

                    // exibimos o progresso
                    this.publishProgress(String.valueOf(i), String.valueOf(total), m);

                }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        Float progress = Float.valueOf(values[0]);
        Float total = Float.valueOf(values[1]);

        String message = values[2];

        PesquisaTimes.getmProgressBar().setProgress((int) ((progress / total) * 100));
        PesquisaTimes.getmProgressBar().setMessage(message);

        // se os valores são iguais, terminanos nosso processamento
        if (values[0].equals(values[1])) {
            // removemos a dialog
            PesquisaTimes.getmProgressBar().cancel();
        }
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if(data.isEmpty())
        {
            PesquisaTimes.getmProgressBar().cancel();
            Snackbar.make(PesquisaTimes.getLinearLayout(), "API is Offline, try again later!!", Snackbar.LENGTH_LONG).show();

            TextView result = new TextView(PesquisaTimes.getContext());
            result.setText("API is Offline, try again later.");
            result.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            result.setTextSize(20);
            result.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(result);

        }

        if(tam==0 && !data.isEmpty())
        {
            PesquisaTimes.getmProgressBar().cancel();
            Snackbar.make(PesquisaTimes.getLinearLayout(), "No results found, please try again using a different name!!", Snackbar.LENGTH_LONG).show();

            TextView result = new TextView(PesquisaTimes.getContext());
            result.setText("No results found, please try again using a different name.");
            result.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            result.setTextSize(20);
            result.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(result);
        }
        else if(tam!=0 && !data.isEmpty())
        {
            if(tam==1)
                Toast.makeText(PesquisaTimes.getContext(), tam + " Result found for " + InserirPesquisaTimes.time.getText(), Toast.LENGTH_LONG).show();
            else
                Toast.makeText(PesquisaTimes.getContext(), tam + " Results found for " + InserirPesquisaTimes.time.getText(), Toast.LENGTH_LONG).show();
        }


        String times = "";
        PesquisaTimes.getLinearLayout().setGravity(Gravity.CENTER);

        for(int i=0; i<lista.size(); i++)
        {
            times = "\n\nName: " + lista.get(i).getStrTeam() + "\n\nStadium: " + lista.get(i).getStrStadium() + "\n\nDescription: "  + lista.get(i).getStrDescriptionEN() + "\n";

            if(lista.get(i).getStrTeamBadge()=="null")
            {
                TextView space = new TextView(PesquisaTimes.getContext());
                space.setText("\n");
                PesquisaTimes.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaTimes.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaTimes.getContext())
                        .load(R.drawable.noimage).override(250,250)
                        .into(image);
                PesquisaTimes.getLinearLayout().addView(image);
            }
            else
            {
                TextView space = new TextView(PesquisaTimes.getContext());
                space.setText("\n");
                PesquisaTimes.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaTimes.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaTimes.getContext())
                        .load(lista.get(i).getStrTeamBadge()).override(250,250)
                        .into(image);
                PesquisaTimes.getLinearLayout().addView(image);
            }

            LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);

            TextView nome = new TextView(PesquisaTimes.getContext());
            nome.setText("Name: ");
            nome.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nome.setTextSize(20);
            nome.setTypeface(nome.getTypeface(), Typeface.BOLD);
            nome.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(nome);

            TextView nomeText = new TextView(PesquisaTimes.getContext());
            nomeText.setText(lista.get(i).getStrTeam());
            nomeText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nomeText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(nomeText);

            View view = new View(PesquisaTimes.getContext());
            view.setLayoutParams(lpView);
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view);

            TextView estadio = new TextView(PesquisaTimes.getContext());
            estadio.setText("Stadium: ");
            estadio.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            estadio.setTextSize(20);
            estadio.setTypeface(estadio.getTypeface(), Typeface.BOLD);
            estadio.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(estadio);

            TextView estadioText = new TextView(PesquisaTimes.getContext());

            if(lista.get(i).getStrStadium().isEmpty())
            {
                estadioText.setText("Stadium not available");
            }
            else
            {
                estadioText.setText(lista.get(i).getStrStadium());
            }

            estadioText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            estadioText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(estadioText);

            View view2 = new View(PesquisaTimes.getContext());
            view2.setLayoutParams(lpView);
            view2.setBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view2);

            TextView descricao = new TextView(PesquisaTimes.getContext());
            descricao.setText("Description: ");
            descricao.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            descricao.setTextSize(20);
            descricao.setTypeface(descricao.getTypeface(), Typeface.BOLD);
            descricao.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(descricao);

            TextView descricaoText = new TextView(PesquisaTimes.getContext());
            if(lista.get(i).getStrDescriptionEN()=="null")
            {
                descricaoText.setText("Description not available");
            }
            else
            {
                descricaoText.setText(lista.get(i).getStrDescriptionEN());
            }

            descricaoText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            descricaoText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(descricaoText);

            View view3 = new View(PesquisaTimes.getContext());
            view3.setLayoutParams(lpView);
            view3.setBackgroundColor(ContextCompat.getColor(view3.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view3);
        }
    }

}
