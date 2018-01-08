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
 * Created by ghsvi on 29/12/2017.
 */

public class OrganizarDataAllPlayers extends AsyncTask<Void, String, Void> {

    private String data = "";
    private ArrayList<EstruturaPlayers> lista = new ArrayList<>();
    private int tam;

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://www.thesportsdb.com/api/v1/json/1/searchplayers.php?t=" + InserirPesquisaAllJogadores.allPlayers.getText())
                    .build();

            Response response = client.newCall(request).execute();
            data = response.body().string();

            JSONObject jo = new JSONObject(data);
            JSONArray jsonArray = jo.getJSONArray("player");

            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if(jsonObject.get("strSport").toString().equals("Soccer"))
                {
                    EstruturaPlayers e = new EstruturaPlayers(jsonObject.get("strPlayer").toString(), jsonObject.get("strGender").toString(), jsonObject.get("strNationality").toString(), jsonObject.get("strTeam").toString(), jsonObject.get("strSport").toString(), jsonObject.get("dateSigned").toString(), jsonObject.get("dateBorn").toString(), jsonObject.get("strBirthLocation").toString(),jsonObject.get("strPosition").toString(), jsonObject.get("strHeight").toString(),jsonObject.get("strWeight").toString(), jsonObject.get("strThumb").toString(), jsonObject.get("strCutout").toString(), jsonObject.get("strDescriptionEN").toString(), jsonObject.get("strWebsite").toString(), jsonObject.get("strFacebook").toString(), jsonObject.get("strTwitter").toString(), jsonObject.get("strInstagram").toString(), jsonObject.get("strYoutube").toString());
                    lista.add(e);
                    tam++;
                }
            }

            for(int j=0; j<=tam; j++)
            {
                Thread.sleep(300); // 2 segundos

                String m = j % 2 == 0 ? "Organizing Components" : "Wait!!";

                // exibimos o progresso
                this.publishProgress(String.valueOf(j), String.valueOf(tam), m);
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

        PesquisaAllPlayers.getmProgressBar().setProgress((int) ((progress / total) * 100));
        PesquisaAllPlayers.getmProgressBar().setMessage(message);

        // se os valores são iguais, termianos nosso processamento
        if (values[0].equals(values[1])) {
            // removemos a dialog
            PesquisaAllPlayers.getmProgressBar().cancel();
        }
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        if(data.isEmpty())
        {
            PesquisaAllPlayers.getmProgressBar().cancel();
            Snackbar.make(PesquisaAllPlayers.getLinearLayout(), "API is Offline, try again later!!", Snackbar.LENGTH_LONG).show();

            TextView result = new TextView(PesquisaTimes.getContext());
            result.setText("API is Offline, try again later.");
            result.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            result.setTextSize(20);
            result.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(result);

        }
        else
        {
            if(tam==0)
            {
                PesquisaAllPlayers.getmProgressBar().cancel();
                Snackbar.make(PesquisaAllPlayers.getLinearLayout(), "No results found, please try again using a different name!!", Snackbar.LENGTH_LONG).show();

                TextView result = new TextView(PesquisaAllPlayers.getContext());
                result.setText("No results found, please try again using a different name.");
                result.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
                result.setTextSize(20);
                result.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
                PesquisaAllPlayers.getLinearLayout().addView(result);
            }
            else
            {
                if(tam==1)
                    Toast.makeText(PesquisaAllPlayers.getContext(), tam + " Result found for " + InserirPesquisaAllJogadores.allPlayers.getText(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(PesquisaAllPlayers.getContext(), tam + " Results found for " + InserirPesquisaAllJogadores.allPlayers.getText(), Toast.LENGTH_LONG).show();
            }
        }


        PesquisaAllPlayers.getLinearLayout().setGravity(Gravity.CENTER);

        for(int i=0; i<lista.size(); i++)
        {

            if (!lista.get(i).getStrCutout().equals("null") && !lista.get(i).getStrThumb().equals("null"))
            {
                TextView space = new TextView(PesquisaAllPlayers.getContext());
                space.setText("\n");
                PesquisaAllPlayers.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaAllPlayers.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaAllPlayers.getContext())
                        .load(lista.get(i).getStrCutout()).override(250, 250)
                        .into(image);
                PesquisaAllPlayers.getLinearLayout().addView(image);
            }
            else if(lista.get(i).getStrThumb().equals("null") && !lista.get(i).getStrCutout().equals("null"))
            {
                TextView space = new TextView(PesquisaAllPlayers.getContext());
                space.setText("\n");
                PesquisaAllPlayers.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaAllPlayers.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaAllPlayers.getContext())
                        .load(lista.get(i).getStrCutout()).override(250, 250)
                        .into(image);
                PesquisaAllPlayers.getLinearLayout().addView(image);
            }

            else if(lista.get(i).getStrCutout().equals("null") && !lista.get(i).getStrThumb().equals("null"))
            {
                TextView space = new TextView(PesquisaAllPlayers.getContext());
                space.setText("\n");
                PesquisaAllPlayers.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaAllPlayers.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaAllPlayers.getContext())
                        .load(lista.get(i).getStrThumb()).override(250, 250)
                        .into(image);
                PesquisaAllPlayers.getLinearLayout().addView(image);
            }

            else if(lista.get(i).getStrCutout().equals("null") && lista.get(i).getStrThumb().equals("null"))
            {
                TextView space = new TextView(PesquisaAllPlayers.getContext());
                space.setText("\n");
                PesquisaAllPlayers.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaAllPlayers.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaAllPlayers.getContext())
                        .load(R.drawable.noplayer).override(250, 250)
                        .into(image);
                PesquisaAllPlayers.getLinearLayout().addView(image);
            }

            LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);

            TextView nome = new TextView(PesquisaAllPlayers.getContext());
            nome.setText("Name: ");
            nome.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nome.setTextSize(20);
            nome.setTypeface(nome.getTypeface(), Typeface.BOLD);
            nome.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(nome);

            TextView nomeText = new TextView(PesquisaAllPlayers.getContext());
            nomeText.setText(lista.get(i).getStrPlayer());
            nomeText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nomeText.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(nomeText);

            View view = new View(PesquisaAllPlayers.getContext());
            view.setLayoutParams(lpView);
            view.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.DarkGray));

            PesquisaAllPlayers.getLinearLayout().addView(view);

            TextView nationality = new TextView(PesquisaAllPlayers.getContext());
            nationality.setText("Nationality: ");
            nationality.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nationality.setTextSize(20);
            nationality.setTypeface(nome.getTypeface(), Typeface.BOLD);
            nationality.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(nationality);

            TextView nationalityText = new TextView(PesquisaAllPlayers.getContext());
            nationalityText.setText(lista.get(i).getStrNationality());
            nationalityText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nationalityText.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(nationalityText);

            View view2 = new View(PesquisaAllPlayers.getContext());
            view2.setLayoutParams(lpView);
            view2.setBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.DarkGray));

            PesquisaAllPlayers.getLinearLayout().addView(view2);

            TextView team = new TextView(PesquisaAllPlayers.getContext());
            team.setText("Team: ");
            team.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            team.setTextSize(20);
            team.setTypeface(nome.getTypeface(), Typeface.BOLD);
            team.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(team);

            TextView teamText = new TextView(PesquisaAllPlayers.getContext());
            teamText.setText(lista.get(i).getStrTeam());
            teamText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            teamText.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(teamText);

            View view3 = new View(PesquisaAllPlayers.getContext());
            view3.setLayoutParams(lpView);
            view3.setBackgroundColor(ContextCompat.getColor(view3.getContext(), R.color.DarkGray));

            PesquisaAllPlayers.getLinearLayout().addView(view3);

            TextView description = new TextView(PesquisaAllPlayers.getContext());
            description.setText("Description: ");
            description.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            description.setTextSize(20);
            description.setTypeface(nome.getTypeface(), Typeface.BOLD);
            description.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(description);

            TextView descriptionText = new TextView(PesquisaAllPlayers.getContext());
            descriptionText.setText(lista.get(i).getStrDescriptionEN());
            descriptionText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            descriptionText.setTextColor(ContextCompat.getColor(PesquisaAllPlayers.getContext(), R.color.AppColor));
            PesquisaAllPlayers.getLinearLayout().addView(descriptionText);

            View view4 = new View(PesquisaAllPlayers.getContext());
            view4.setLayoutParams(lpView);
            view4.setBackgroundColor(ContextCompat.getColor(view4.getContext(), R.color.DarkGray));

            PesquisaAllPlayers.getLinearLayout().addView(view4);

        }

    }

}
