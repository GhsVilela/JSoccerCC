package com.example.ghsvi.jsoccercc;

import android.annotation.SuppressLint;
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
 * Created by ghsvi on 29/12/2017.
 */

public class OrganizarDataSinglePlayer extends AsyncTask<Void, String, Void> {


    String data = "";
    ArrayList<EstruturaPlayers> lista = new ArrayList<>();
    int tam;

    @Override
    protected Void doInBackground(Void... voids) {
        try {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://www.thesportsdb.com/api/v1/json/1/searchplayers.php?p=" + InserirPesquisaSingleJogador.singlePlayer.getText())
                    .build();

            Response response = client.newCall(request).execute();
            data = response.body().string();

            JSONObject jo = new JSONObject(data);
            JSONArray jsonArray = jo.getJSONArray("player");

            for (int i = 0; i < jsonArray.length(); i++) {
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


        if(data.isEmpty())
        {
            PesquisaSinglePlayer.getmProgressBar().cancel();
            Snackbar.make(PesquisaSinglePlayer.getLinearLayout(), "API is Offline, try again later!!", Snackbar.LENGTH_LONG).show();

            TextView result = new TextView(PesquisaTimes.getContext());
            result.setText("API is Offline, try again later.");
            result.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            result.setTextSize(20);
            result.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(result);

        }
        else
        {
            if(tam==0)
            {
                PesquisaSinglePlayer.getmProgressBar().cancel();
                Snackbar.make(PesquisaSinglePlayer.getLinearLayout(), "No results found, please try again using a different name!!", Snackbar.LENGTH_LONG).show();

                TextView result = new TextView(PesquisaSinglePlayer.getContext());
                result.setText("No results found, please try again using a different name.");
                result.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
                result.setTextSize(20);
                result.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
                PesquisaSinglePlayer.getLinearLayout().addView(result);
            }
            else
            {
                if(tam==1)
                    Toast.makeText(PesquisaSinglePlayer.getContext(), tam + " Result found for " + InserirPesquisaSingleJogador.singlePlayer.getText(), Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(PesquisaSinglePlayer.getContext(), tam + " Results found for " + InserirPesquisaSingleJogador.singlePlayer.getText(), Toast.LENGTH_LONG).show();
            }
        }


        PesquisaSinglePlayer.getLinearLayout().setGravity(Gravity.CENTER);

        for (int i = 0; i < lista.size(); i++)
        {

            if (!lista.get(i).getStrCutout().equals("null") && !lista.get(i).getStrThumb().equals("null"))
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
            else if(lista.get(i).getStrThumb().equals("null") && !lista.get(i).getStrCutout().equals("null"))
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

            else if(lista.get(i).getStrCutout().equals("null") && !lista.get(i).getStrThumb().equals("null"))
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

            else if(lista.get(i).getStrCutout().equals("null") && lista.get(i).getStrThumb().equals("null"))
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
            nome.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(nome);

            TextView nomeText = new TextView(PesquisaSinglePlayer.getContext());
            nomeText.setText(lista.get(i).getStrPlayer());
            nomeText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nomeText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
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
            nationality.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(nationality);

            TextView nationalityText = new TextView(PesquisaSinglePlayer.getContext());
            nationalityText.setText(lista.get(i).getStrNationality());
            nationalityText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            nationalityText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
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
            team.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(team);

            TextView teamText = new TextView(PesquisaSinglePlayer.getContext());
            teamText.setText(lista.get(i).getStrTeam());
            teamText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            teamText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(teamText);

            View view3 = new View(PesquisaSinglePlayer.getContext());
            view3.setLayoutParams(lpView);
            view3.setBackgroundColor(ContextCompat.getColor(view3.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view3);

            TextView sport = new TextView(PesquisaSinglePlayer.getContext());
            sport.setText("Sport: ");
            sport.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            sport.setTextSize(20);
            sport.setTypeface(nome.getTypeface(), Typeface.BOLD);
            sport.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(sport);

            TextView sportText = new TextView(PesquisaSinglePlayer.getContext());
            sportText.setText(lista.get(i).getStrSport());
            sportText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            sportText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(sportText);

            View view4 = new View(PesquisaSinglePlayer.getContext());
            view4.setLayoutParams(lpView);
            view4.setBackgroundColor(ContextCompat.getColor(view4.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view4);

            TextView dateSigned = new TextView(PesquisaSinglePlayer.getContext());
            dateSigned.setText("Date Signed: ");
            dateSigned.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            dateSigned.setTextSize(20);
            dateSigned.setTypeface(nome.getTypeface(), Typeface.BOLD);
            dateSigned.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(dateSigned);

            TextView dateSignedText = new TextView(PesquisaSinglePlayer.getContext());
            dateSignedText.setText(lista.get(i).getDateSigned());
            dateSignedText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            dateSignedText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(dateSignedText);

            View view5 = new View(PesquisaSinglePlayer.getContext());
            view5.setLayoutParams(lpView);
            view5.setBackgroundColor(ContextCompat.getColor(view5.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view5);

            TextView position = new TextView(PesquisaSinglePlayer.getContext());
            position.setText("Position: ");
            position.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            position.setTextSize(20);
            position.setTypeface(nome.getTypeface(), Typeface.BOLD);
            position.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(position);

            TextView positionText = new TextView(PesquisaSinglePlayer.getContext());
            positionText.setText(lista.get(i).getStrPosition());
            positionText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            positionText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(positionText);

            View view6 = new View(PesquisaSinglePlayer.getContext());
            view6.setLayoutParams(lpView);
            view6.setBackgroundColor(ContextCompat.getColor(view6.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view6);

            TextView gender = new TextView(PesquisaSinglePlayer.getContext());
            gender.setText("Gender: ");
            gender.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            gender.setTextSize(20);
            gender.setTypeface(nome.getTypeface(), Typeface.BOLD);
            gender.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(gender);

            TextView genderText = new TextView(PesquisaSinglePlayer.getContext());
            genderText.setText(lista.get(i).getStrGender());
            genderText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            genderText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(genderText);

            View view7 = new View(PesquisaSinglePlayer.getContext());
            view7.setLayoutParams(lpView);
            view7.setBackgroundColor(ContextCompat.getColor(view7.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view7);

            TextView weight = new TextView(PesquisaSinglePlayer.getContext());
            weight.setText("Weight: ");
            weight.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            weight.setTextSize(20);
            weight.setTypeface(nome.getTypeface(), Typeface.BOLD);
            weight.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(weight);

            TextView weightText = new TextView(PesquisaSinglePlayer.getContext());
            weightText.setText(lista.get(i).getStrWeight());
            weightText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            weightText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(weightText);

            View view8 = new View(PesquisaSinglePlayer.getContext());
            view8.setLayoutParams(lpView);
            view8.setBackgroundColor(ContextCompat.getColor(view8.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view8);

            TextView height = new TextView(PesquisaSinglePlayer.getContext());
            height.setText("Height: ");
            height.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            height.setTextSize(20);
            height.setTypeface(nome.getTypeface(), Typeface.BOLD);
            height.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(height);

            TextView heightText = new TextView(PesquisaSinglePlayer.getContext());
            heightText.setText(lista.get(i).getStrHeight());
            heightText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            heightText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(heightText);

            View view9 = new View(PesquisaSinglePlayer.getContext());
            view9.setLayoutParams(lpView);
            view9.setBackgroundColor(ContextCompat.getColor(view9.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view9);

            TextView dateBorn = new TextView(PesquisaSinglePlayer.getContext());
            dateBorn.setText("Date Born: ");
            dateBorn.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            dateBorn.setTextSize(20);
            dateBorn.setTypeface(nome.getTypeface(), Typeface.BOLD);
            dateBorn.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(dateBorn);

            TextView dateBornText = new TextView(PesquisaSinglePlayer.getContext());
            dateBornText.setText(lista.get(i).getDateBorn());
            dateBornText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            dateBornText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(dateBornText);

            View view10 = new View(PesquisaSinglePlayer.getContext());
            view10.setLayoutParams(lpView);
            view10.setBackgroundColor(ContextCompat.getColor(view10.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view10);

            TextView birthLocation = new TextView(PesquisaSinglePlayer.getContext());
            birthLocation.setText("Birth Location: ");
            birthLocation.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            birthLocation.setTextSize(20);
            birthLocation.setTypeface(nome.getTypeface(), Typeface.BOLD);
            birthLocation.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(birthLocation);

            TextView birthLocationText = new TextView(PesquisaSinglePlayer.getContext());
            birthLocationText.setText(lista.get(i).getStrBirthLocation());
            birthLocationText.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            birthLocationText.setTextColor(ContextCompat.getColor(PesquisaSinglePlayer.getContext(), R.color.AppColor));
            PesquisaSinglePlayer.getLinearLayout().addView(birthLocationText);

            TextView space = new TextView(PesquisaSinglePlayer.getContext());
            space.setText("\n");
            PesquisaSinglePlayer.getLinearLayout().addView(space);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(60, 60);

            //WebSite

            ImageView[] imageInternet;

            imageInternet = new ImageView[lista.size()];
            imageInternet[i] = new ImageView(PesquisaSinglePlayer.getContext());
            imageInternet[i].setLayoutParams(lp);
            Glide.with(PesquisaSinglePlayer.getContext())
                    .load(R.drawable.internet).override(60, 60)
                    .into(imageInternet[i]);
            imageInternet[i].setId(i);

            //Facebook

            ImageView[] imageFacebook;

            imageFacebook = new ImageView[lista.size()];
            imageFacebook[i] = new ImageView(PesquisaSinglePlayer.getContext());
            imageFacebook[i].setLayoutParams(lp);
            Glide.with(PesquisaSinglePlayer.getContext())
                    .load(R.drawable.facebook).override(60, 60)
                    .into(imageFacebook[i]);
            imageFacebook[i].setId(i);

            //Instagram

            ImageView[] imageInstagram;

            imageInstagram = new ImageView[lista.size()];
            imageInstagram[i] = new ImageView(PesquisaSinglePlayer.getContext());
            imageInstagram[i].setLayoutParams(lp);
            Glide.with(PesquisaSinglePlayer.getContext())
                    .load(R.drawable.instagram).override(60, 60)
                    .into(imageInstagram[i]);
            imageInstagram[i].setId(i);

            //Twitter

            ImageView[] imageTwitter;

            imageTwitter = new ImageView[lista.size()];
            imageTwitter[i] = new ImageView(PesquisaSinglePlayer.getContext());
            imageTwitter[i].setLayoutParams(lp);
            Glide.with(PesquisaSinglePlayer.getContext())
                    .load(R.drawable.twitter).override(60, 60)
                    .into(imageTwitter[i]);
            imageTwitter[i].setId(i);

            //Youtube

            ImageView[] imageYoutube;

            imageYoutube = new ImageView[lista.size()];
            imageYoutube[i] = new ImageView(PesquisaSinglePlayer.getContext());
            imageYoutube[i].setLayoutParams(lp);
            Glide.with(PesquisaSinglePlayer.getContext())
                    .load(R.drawable.youtube).override(60, 60)
                    .into(imageYoutube[i]);
            imageYoutube[i].setId(i);


            LinearLayout socialNetwork = new LinearLayout(PesquisaSinglePlayer.getContext());
            socialNetwork.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(10, 0, 10, 5);
            //socialNetwork.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            if(!lista.get(i).getStrWebsite().isEmpty() && !lista.get(i).getStrWebsite().equals("null"))
                socialNetwork.addView(imageInternet[i], layoutParams);

            if(!lista.get(i).getStrFacebook().isEmpty() && !lista.get(i).getStrFacebook().equals("null"))
                socialNetwork.addView(imageFacebook[i], layoutParams);

            if(!lista.get(i).getStrInstagram().isEmpty() && !lista.get(i).getStrInstagram().equals("null"))
                socialNetwork.addView(imageInstagram[i], layoutParams);

            if(!lista.get(i).getStrTwitter().isEmpty() && !lista.get(i).getStrTwitter().equals("null"))
                socialNetwork.addView(imageTwitter[i], layoutParams);

            if(!lista.get(i).getStrYoutube().isEmpty() && !lista.get(i).getStrYoutube().equals("null"))
                socialNetwork.addView(imageYoutube[i], layoutParams);

            PesquisaSinglePlayer.getLinearLayout().addView(socialNetwork, layoutParams);

            imageInternet[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Uri uri = Uri.parse("http://" + lista.get(v.getId()).getStrWebsite());

                    Intent website = new Intent(Intent.ACTION_VIEW, uri);

                    PesquisaSinglePlayer.getContext().startActivity(website);
                }
            });


            imageFacebook[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    /*
                    if(lista.get(v.getId()).getStrFacebook().isEmpty())
                    {
                        Toast.makeText(PesquisaTimes.getContext(), "This team doesn't contain any information about his facebook!!" , Toast.LENGTH_LONG).show();
                    }
                    */
                    PesquisaSinglePlayer.getContext().startActivity(newFacebookIntent(PesquisaSinglePlayer.getContext().getPackageManager(), "http://" + lista.get(v.getId()).getStrFacebook()));
                }
            });

            imageInstagram[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {



                    PesquisaSinglePlayer.getContext().startActivity(newInstagramIntent(PesquisaSinglePlayer.getContext().getPackageManager(),"http://" + lista.get(v.getId()).getStrInstagram()));
                }
            });

            imageTwitter[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {



                    PesquisaSinglePlayer.getContext().startActivity(newTwitterIntent(PesquisaSinglePlayer.getContext().getPackageManager(), "http://" + lista.get(v.getId()).getStrTwitter()));

                }
            });

            imageYoutube[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {



                    PesquisaSinglePlayer.getContext().startActivity(newYoutubeIntent(PesquisaSinglePlayer.getContext().getPackageManager(), "http://" + lista.get(v.getId()).getStrYoutube()));

                }
            });

            View view11 = new View(PesquisaSinglePlayer.getContext());
            view11.setLayoutParams(lpView);
            view11.setBackgroundColor(ContextCompat.getColor(view11.getContext(), R.color.DarkGray));

            PesquisaSinglePlayer.getLinearLayout().addView(view11);
        }
    }

    private Intent newFacebookIntent(PackageManager pm, String url) {
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

    private  Intent newInstagramIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);

        try {
            pm.getPackageInfo("com.instagram.android", 0);
            Intent instaApp = new Intent(Intent.ACTION_VIEW, uri);
            instaApp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return instaApp;

        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, uri);
        }
    }

    private  Intent newTwitterIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            // get the Twitter app if possible
            pm.getPackageInfo("com.twitter.android", 0);
            Intent intentTwitter = new Intent(Intent.ACTION_VIEW, uri);
            intentTwitter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intentTwitter;
        } catch (Exception e) {
            // no Twitter app, revert to browser
            return new Intent(Intent.ACTION_VIEW, uri);
        }
    }

    private  Intent newYoutubeIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            // get the youtube app if possible
            pm.getPackageInfo("com.youtube.android", 0);
            Intent intentYoutube;
            intentYoutube = new Intent(Intent.ACTION_VIEW, uri);
            //intentYoutube.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intentYoutube;
        } catch (Exception e) {
            // no Twitter app, revert to browser
            return new Intent(Intent.ACTION_VIEW, uri);
        }
    }
}
