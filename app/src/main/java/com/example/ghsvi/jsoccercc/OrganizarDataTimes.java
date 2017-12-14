package com.example.ghsvi.jsoccercc;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class OrganizarDataTimes extends AsyncTask<Void, String, Void> {

    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    String  urlBrasao = "";
    ArrayList<Estrutura> lista = new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
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

            JSONObject jo = new JSONObject(data);
            JSONArray jsonArray = jo.getJSONArray("teams");

            int total = jsonArray.length()-1;

            for(int i=0; i<jsonArray.length(); i++)
            {
                Thread.sleep(500); // 2 segundos
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


                String m = i % 2 == 0 ? "Organizando Componentes" : "Aguarde!!";

                // exibimos o progresso
                this.publishProgress(String.valueOf(i), String.valueOf(total), m);

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

        // se os valores são iguais, termianos nosso processamento
        if (values[0].equals(values[1])) {
            // removemos a dialog
            PesquisaTimes.getmProgressBar().cancel();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        String times = "";
        PesquisaTimes.getLinearLayout().setGravity(Gravity.CENTER);

        for(int i=0; i<lista.size(); i++)
        {
            times = "\n\nName: " + lista.get(i).getStrTeam() + "\n\nStadium: " + lista.get(i).getStrStadium() + "\n\nDescription: "  + lista.get(i).getStrDescriptionEN() + "\n";

            if(lista.get(i).getStrTeamBadge()=="null")
            {
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
                ImageView image = new ImageView(PesquisaTimes.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setLayoutParams(lp);
                Glide.with(PesquisaTimes.getContext())
                        .load(lista.get(i).getStrTeamBadge()).override(250,250)
                        .into(image);

                PesquisaTimes.getLinearLayout().addView(image);
            }

            TextView textView = new TextView(PesquisaTimes.getContext());
            textView.setText(times);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
            PesquisaTimes.getLinearLayout().addView(textView);



            //PesquisaTimes.dataText.setText(times);

        }
    }

}
