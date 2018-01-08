package com.example.ghsvi.jsoccercc;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
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
    ArrayList<EstruturaTimes> lista = new ArrayList<>();
    LinearLayout linearLayout;
    int tam=0;

    @Override
    protected Void doInBackground(Void... voids) {
        try {

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=" + InserirPesquisaTimes.time.getText())
                        .build();

                Response response = client.newCall(request).execute();
                data = response.body().string();

                JSONObject jo = new JSONObject(data);
                JSONArray jsonArray = jo.getJSONArray("teams");

               //int total = jsonArray.length()-1;

                for(int i=0; i<jsonArray.length(); i++)
                {
                    //Thread.sleep(500); // 2 segundos

                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    if(jsonObject.get("strSport").toString().equals("Soccer"))
                    {
                        EstruturaTimes e = new EstruturaTimes(jsonObject.get("strTeam").toString(), jsonObject.get("strStadium").toString(), jsonObject.get("strDescriptionEN").toString(), jsonObject.get("strTeamBadge").toString(), jsonObject.get("strFacebook").toString(), jsonObject.get("strLeague").toString(), jsonObject.get("intFormedYear").toString(), jsonObject.get("strManager").toString(), jsonObject.get("strCountry").toString(), jsonObject.get("strWebsite").toString(), jsonObject.get("strTwitter").toString(), jsonObject.get("strInstagram").toString(), jsonObject.get("strYoutube").toString());
                        lista.add(e);
                        tam++;
                    }
                }

                for(int j=0; j<=tam; j++)
                {
                    Thread.sleep(500); // 2 segundos

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
        else
        {
            if(tam==0)
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
            else
            {
                if(tam==1)
                    Toast.makeText(PesquisaTimes.getContext(), tam + " Result found for " + InserirPesquisaTimes.time.getText(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(PesquisaTimes.getContext(), tam + " Results found for " + InserirPesquisaTimes.time.getText(), Toast.LENGTH_LONG).show();
            }
        }

        PesquisaTimes.getLinearLayout().setGravity(Gravity.CENTER);

        for(int i=0; i<lista.size(); i++) {
            if (lista.get(i).getStrTeamBadge() == "null") {
                TextView space = new TextView(PesquisaTimes.getContext());
                space.setText("\n");
                PesquisaTimes.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaTimes.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaTimes.getContext())
                        .load(R.drawable.noimage).override(250, 250)
                        .into(image);
                PesquisaTimes.getLinearLayout().addView(image);
            } else {
                TextView space = new TextView(PesquisaTimes.getContext());
                space.setText("\n");
                PesquisaTimes.getLinearLayout().addView(space);

                ImageView image = new ImageView(PesquisaTimes.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaTimes.getContext())
                        .load(lista.get(i).getStrTeamBadge()).override(250, 250)
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

            TextView league = new TextView(PesquisaTimes.getContext());
            league.setText("League: ");
            league.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            league.setTextSize(20);
            league.setTypeface(league.getTypeface(), Typeface.BOLD);
            league.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(league);

            TextView leagueText = new TextView(PesquisaTimes.getContext());
            leagueText.setText(lista.get(i).getStrLeague());
            leagueText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            leagueText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(leagueText);

            View view2 = new View(PesquisaTimes.getContext());
            view2.setLayoutParams(lpView);
            view2.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view2);

            TextView formedYear = new TextView(PesquisaTimes.getContext());
            formedYear.setText("Formed Year: ");
            formedYear.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            formedYear.setTextSize(20);
            formedYear.setTypeface(formedYear.getTypeface(), Typeface.BOLD);
            formedYear.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(formedYear);

            TextView formedYearText = new TextView(PesquisaTimes.getContext());

            if(lista.get(i).getIntFormedYear().equals("0"))
            {
                formedYearText.setText("Formed Year is not available");
            }
            else
            {
                formedYearText.setText(lista.get(i).getIntFormedYear());
            }

            formedYearText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            formedYearText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(formedYearText);

            View view3 = new View(PesquisaTimes.getContext());
            view3.setLayoutParams(lpView);
            view3.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view3);

            TextView manager = new TextView(PesquisaTimes.getContext());
            manager.setText("Manager: ");
            manager.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            manager.setTextSize(20);
            manager.setTypeface(manager.getTypeface(), Typeface.BOLD);
            manager.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(manager);

            TextView managerText = new TextView(PesquisaTimes.getContext());

            if (lista.get(i).getStrManager().isEmpty()) {
                managerText.setText("Manager is not available");
            } else {
                managerText.setText(lista.get(i).getStrManager());
            }

            managerText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            managerText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(managerText);

            View view4 = new View(PesquisaTimes.getContext());
            view4.setLayoutParams(lpView);
            view4.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view4);

            TextView estadio = new TextView(PesquisaTimes.getContext());
            estadio.setText("Stadium: ");
            estadio.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            estadio.setTextSize(20);
            estadio.setTypeface(estadio.getTypeface(), Typeface.BOLD);
            estadio.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(estadio);

            TextView estadioText = new TextView(PesquisaTimes.getContext());

            if (lista.get(i).getStrStadium().isEmpty()) {
                estadioText.setText("Stadium is not available");
            } else {
                estadioText.setText(lista.get(i).getStrStadium());
            }

            estadioText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            estadioText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(estadioText);

            View view5 = new View(PesquisaTimes.getContext());
            view5.setLayoutParams(lpView);
            view5.setBackgroundColor(ContextCompat.getColor(view2.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view5);

            TextView country = new TextView(PesquisaTimes.getContext());
            country.setText("Country: ");
            country.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            country.setTextSize(20);
            country.setTypeface(country.getTypeface(), Typeface.BOLD);
            country.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(country);

            TextView countryText = new TextView(PesquisaTimes.getContext());
            countryText.setText(lista.get(i).getStrCountry());
            countryText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            countryText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(countryText);

            View view6 = new View(PesquisaTimes.getContext());
            view6.setLayoutParams(lpView);
            view6.setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view6);

            TextView descricao = new TextView(PesquisaTimes.getContext());
            descricao.setText("Description: ");
            descricao.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            descricao.setTextSize(20);
            descricao.setTypeface(descricao.getTypeface(), Typeface.BOLD);
            descricao.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(descricao);

            TextView descricaoText = new TextView(PesquisaTimes.getContext());
            if (lista.get(i).getStrDescriptionEN() == "null") {
                descricaoText.setText("Description is not available");
            } else {
                descricaoText.setText(lista.get(i).getStrDescriptionEN());
            }

            descricaoText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            descricaoText.setTextColor(Color.parseColor("#37474F"));
            PesquisaTimes.getLinearLayout().addView(descricaoText);

            TextView space = new TextView(PesquisaTimes.getContext());
            space.setText("\n");
            PesquisaTimes.getLinearLayout().addView(space);

            ImageView[] imageFacebook;

            imageFacebook = new ImageView[lista.size()];
            imageFacebook[i] = new ImageView(PesquisaTimes.getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, 100);
            imageFacebook[i].setLayoutParams(lp);
            Glide.with(PesquisaTimes.getContext())
                    .load(R.drawable.facebook).override(100, 100)
                    .into(imageFacebook[i]);
            imageFacebook[i].setId(i);

            ImageView[] imageInstagram;

            imageInstagram = new ImageView[lista.size()];
            imageInstagram[i] = new ImageView(PesquisaTimes.getContext());
            imageInstagram[i].setLayoutParams(lp);
            Glide.with(PesquisaTimes.getContext())
                    .load(R.drawable.instagram).override(100, 100)
                    .into(imageInstagram[i]);
            imageInstagram[i].setId(i);

            LinearLayout socialNetwork = new LinearLayout(PesquisaTimes.getContext());
            socialNetwork.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            socialNetwork.setOrientation(LinearLayout.HORIZONTAL);

            if(!lista.get(i).getStrFacebook().isEmpty())
            socialNetwork.addView(imageFacebook[i]);

            if(!lista.get(i).getStrInstagram().isEmpty())
            socialNetwork.addView(imageInstagram[i]);


            PesquisaTimes.getLinearLayout().addView(socialNetwork);


            imageFacebook[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /*
                    if(lista.get(v.getId()).getStrFacebook().isEmpty())
                    {
                        Toast.makeText(PesquisaTimes.getContext(), "This team doesn't contain any information about his facebook!!" , Toast.LENGTH_LONG).show();
                    }
                    */
                        PesquisaTimes.getContext().startActivity(newFacebookIntent(PesquisaTimes.getContext().getPackageManager(), "http://" + lista.get(v.getId()).getStrFacebook()));
                }
            });

            imageInstagram[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /*
                    if(lista.get(v.getId()).getStrFacebook().isEmpty())
                    {
                        Toast.makeText(PesquisaTimes.getContext(), "This team doesn't contain any information about his facebook!!" , Toast.LENGTH_LONG).show();
                    }
                    */
                    PesquisaTimes.getContext().startActivity(newInstagramIntent("http://" + lista.get(v.getId()).getStrInstagram()));
                }
            });





            View view7 = new View(PesquisaTimes.getContext());
            view7.setLayoutParams(lpView);
            view7.setBackgroundColor(ContextCompat.getColor(view3.getContext(), R.color.DarkGray));

            PesquisaTimes.getLinearLayout().addView(view7);


        
        }
    }

    public  Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    public  Intent newInstagramIntent(String url) {
        Uri uri = Uri.parse(url);

        try {
            Intent instaApp = new Intent(Intent.ACTION_VIEW, uri);
            instaApp.setPackage("com.instagram.android");
            return instaApp;

        } catch (ActivityNotFoundException e) {
            return new Intent(Intent.ACTION_VIEW, uri);
        }
    }

}
