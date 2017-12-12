package com.example.ghsvi.jsoccercc;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * Created by ghsvi on 09/12/2017.
 */

public class fetchData extends AsyncTask<Void, Void, Void> {

    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    String  urlBrasao = "";
    ArrayList<Estrutura> lista = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://www.thesportsdb.com/api/v1/json/1/searchteams.php?t=" + TelaInicialActivity.time.getText());

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line!=null)
            {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONObject jo = new JSONObject(data);
            JSONArray jsonArray = jo.getJSONArray("teams");

            for(int i=0; i<jsonArray.length(); i++)
            {

                    /*
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    singleParsed += "Time: " + jsonObject.get("strTeam") + "\n" +
                            "Liga: " + jsonObject.get("strLeague") + "\n" +
                            "Técnico: " + jsonObject.get("strManager") + "\n" +
                            "Estádio: " + jsonObject.get("strStadium") + "\n" +
                            "Capacidade Estádio: " + jsonObject.get("intStadiumCapacity") + "\n" +
                            "WebSite: " + jsonObject.get("strWebsite") + "\n" +
                            "Facebook: " + jsonObject.get("strFacebook") + "\n" +
                            "Twitter: " + jsonObject.get("strTwitter") + "\n" +
                            "Descrição: " + jsonObject.get("strDescriptionEN") + "\n" +
                            "Badge: " + jsonObject.get("strTeamBadge");








                    dataParsed = dataParsed + singleParsed + "\n";

                    urlBrasao = jsonObject.getString("strTeamBadge");
                    */


                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                Estrutura e = new Estrutura(jsonObject.get("strTeam").toString(), jsonObject.get("strStadium").toString(), jsonObject.get("strDescriptionEN").toString(), jsonObject.get("strTeamBadge").toString());
                lista.add(e);

            }



            /*



            JSONArray jsonArray = new JSONArray(data);

            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                singleParsed = "Time: " + jsonObject.get("strTeam") + "\n" +
                               "Liga: " + jsonObject.get("strLeague") + "\n" +
                               "Técnico: " + jsonObject.get("strManager") + "\n" +
                               "Estádio: " + jsonObject.get("strStadium") + "\n";

                dataParsed = dataParsed + singleParsed + "\n";



            }
             */
           // 

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String times = "";
        MainActivity.getLinearLayout().setGravity(Gravity.CENTER);

        for(int i=0; i<lista.size(); i++)
        {
            times = "\n\nNome: " + lista.get(i).getStrTeam() + "\n\nEstádio: " + lista.get(i).getStrStadium() + "\n\nDescrição: "  + lista.get(i).getStrDescriptionEN() + "\n";

            if(lista.get(i).getStrTeamBadge()=="null")
            {
                ImageView image = new ImageView(MainActivity.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(MainActivity.getContext())
                        .load(R.drawable.noimage).override(250,250)
                        .into(image);
                MainActivity.getLinearLayout().addView(image);
            }
            else
            {
                ImageView image = new ImageView(MainActivity.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(MainActivity.getContext())
                        .load(lista.get(i).getStrTeamBadge()).override(250,250)
                        .into(image);

                MainActivity.getLinearLayout().addView(image);
            }

            TextView textView = new TextView(MainActivity.getContext());
            textView.setText(times);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            MainActivity.getLinearLayout().addView(textView);



            //MainActivity.dataText.setText(times);

        }
    }

}
