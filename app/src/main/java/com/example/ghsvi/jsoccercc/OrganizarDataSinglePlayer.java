package com.example.ghsvi.jsoccercc;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

/**
 * Created by ghsvi on 29/12/2017.
 */

public class OrganizarDataSinglePlayer extends AsyncTask<Void, String, Void> {


    String data = "";
    ArrayList<EstruturaPlayers> lista = new ArrayList<>();
    int tam;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://www.thesportsdb.com/api/v1/json/1/searchplayers.php?p=" + InserirPesquisaSingleJogador.singlePlayer.getText());

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONObject jo = new JSONObject(data);
            JSONArray jsonArray = jo.getJSONArray("player");

            tam = jsonArray.length();

            int total = jsonArray.length() - 1;

            for (int i = 0; i < jsonArray.length(); i++) {
                Thread.sleep(200);

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                EstruturaPlayers e = new EstruturaPlayers(jsonObject.get("strPlayer").toString(), jsonObject.get("strGender").toString(), jsonObject.get("strNationality").toString(), jsonObject.get("strTeam").toString(), jsonObject.get("strSport").toString(), jsonObject.get("dateSigned").toString(), jsonObject.get("dateBorn").toString(), jsonObject.get("strBirthLocation").toString(), jsonObject.get("strPosition").toString(), jsonObject.get("strHeight").toString(), jsonObject.get("strWeight").toString(), jsonObject.get("strThumb").toString(), jsonObject.get("strCutout").toString(), jsonObject.get("strDescriptionEN").toString());
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

        PesquisaSinglePlayer.getmProgressBar().setProgress((int) ((progress / total) * 100));
        PesquisaSinglePlayer.getmProgressBar().setMessage(message);

        // se os valores são iguais, termianos nosso processamento
        if (values[0].equals(values[1])) {
            // removemos a dialog
            PesquisaSinglePlayer.getmProgressBar().cancel();
        }
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        if (tam == 0) {
            PesquisaSinglePlayer.getmProgressBar().cancel();
            Toast.makeText(PesquisaSinglePlayer.getContext(), "No results found, please try again using a different name!!", Toast.LENGTH_LONG).show();

            TextView result = new TextView(PesquisaSinglePlayer.getContext());
            result.setText("No results found, please try again using a different name.");
            result.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            result.setTextSize(20);
            result.setTextColor(Color.parseColor("#37474F"));
            PesquisaSinglePlayer.getLinearLayout().addView(result);
        }
        else
        {
            Toast.makeText(PesquisaSinglePlayer.getContext(), tam + " Results found for " + InserirPesquisaSingleJogador.singlePlayer.getText(), Toast.LENGTH_LONG).show();
        }


        PesquisaSinglePlayer.getLinearLayout().setGravity(Gravity.CENTER);

        for (int i = 0; i < lista.size(); i++)
        {

            if (lista.get(i).getStrCutout() != "null" && lista.get(i).getStrThumb()!="null")
            {
                TextView space = new TextView(PesquisaSinglePlayer.getContext());
                space.setText("\n");
                PesquisaSinglePlayer.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaSinglePlayer.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaSinglePlayer.getContext())
                        .load(lista.get(i).getStrCutout()).override(250, 250)
                        .into(image);
                PesquisaSinglePlayer.getLinearLayout().addView(image);
            }
            else if(lista.get(i).getStrThumb()=="null" && lista.get(i).getStrCutout()!="null")
            {
                TextView space = new TextView(PesquisaSinglePlayer.getContext());
                space.setText("\n");
                PesquisaSinglePlayer.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaSinglePlayer.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaSinglePlayer.getContext())
                        .load(lista.get(i).getStrCutout()).override(250, 250)
                        .into(image);
                PesquisaSinglePlayer.getLinearLayout().addView(image);
            }

            else if(lista.get(i).getStrCutout()=="null" && lista.get(i).getStrThumb()!="null")
            {
                TextView space = new TextView(PesquisaSinglePlayer.getContext());
                space.setText("\n");
                PesquisaSinglePlayer.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaSinglePlayer.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaSinglePlayer.getContext())
                        .load(lista.get(i).getStrThumb()).override(250, 250)
                        .into(image);
                PesquisaSinglePlayer.getLinearLayout().addView(image);
            }

            else if(lista.get(i).getStrCutout() == "null" && lista.get(i).getStrThumb()=="null")
            {
                TextView space = new TextView(PesquisaSinglePlayer.getContext());
                space.setText("\n");
                PesquisaSinglePlayer.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaSinglePlayer.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaSinglePlayer.getContext())
                        .load(R.drawable.noplayer).override(250, 250)
                        .into(image);
                PesquisaSinglePlayer.getLinearLayout().addView(image);
            }

            LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);

            TextView nome = new TextView(PesquisaSinglePlayer.getContext());
            nome.setText("Name: ");
            nome.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nome.setTextSize(20);
            nome.setTypeface(nome.getTypeface(), Typeface.BOLD);
            nome.setTextColor(Color.parseColor("#37474F"));
            PesquisaSinglePlayer.getLinearLayout().addView(nome);

            TextView nomeText = new TextView(PesquisaSinglePlayer.getContext());
            nomeText.setText(lista.get(i).getStrPlayer());
            nomeText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nomeText.setTextColor(Color.parseColor("#37474F"));
            PesquisaSinglePlayer.getLinearLayout().addView(nomeText);

            View view = new View(PesquisaSinglePlayer.getContext());
            view.setLayoutParams(lpView);
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view);

            TextView nationality = new TextView(PesquisaSinglePlayer.getContext());
            nationality.setText("Nationality: ");
            nationality.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nationality.setTextSize(20);
            nationality.setTypeface(nome.getTypeface(), Typeface.BOLD);
            nationality.setTextColor(Color.parseColor("#37474F"));
            PesquisaSinglePlayer.getLinearLayout().addView(nationality);

            TextView nationalityText = new TextView(PesquisaSinglePlayer.getContext());
            nationalityText.setText(lista.get(i).getStrNationality());
            nationalityText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nationalityText.setTextColor(Color.parseColor("#37474F"));
            PesquisaSinglePlayer.getLinearLayout().addView(nationalityText);

            View view2 = new View(PesquisaSinglePlayer.getContext());
            view2.setLayoutParams(lpView);
            view2.setBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view2);

            TextView team = new TextView(PesquisaSinglePlayer.getContext());
            team.setText("Team: ");
            team.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            team.setTextSize(20);
            team.setTypeface(nome.getTypeface(), Typeface.BOLD);
            team.setTextColor(Color.parseColor("#37474F"));
            PesquisaSinglePlayer.getLinearLayout().addView(team);

            TextView teamText = new TextView(PesquisaSinglePlayer.getContext());
            teamText.setText(lista.get(i).getStrTeam());
            teamText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            teamText.setTextColor(Color.parseColor("#37474F"));
            PesquisaSinglePlayer.getLinearLayout().addView(teamText);

            View view3 = new View(PesquisaSinglePlayer.getContext());
            view3.setLayoutParams(lpView);
            view3.setBackgroundColor(ContextCompat.getColor(view3.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view3);
        }
    }
}
