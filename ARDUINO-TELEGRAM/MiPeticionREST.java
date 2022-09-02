package com.lics.proyectou2lectorqr;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MiPeticionREST extends AsyncTask<String,String,String> {

    String docenteTelegramID;
    HttpURLConnection urlConnection;
    StringBuilder json;
    String datos = "";


    MiPeticionREST(String docenteTelegramID){
        this.docenteTelegramID = docenteTelegramID;
    }

    @Override
    public void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... info) {
        String res = "";

        try
        {


            if( info[0].contains("GET-SEND")){
                URL url = new URL("https://api.telegram.org/bot5206571114:"+docenteTelegramID+"/sendMessage?chat_id="+"1311215385"+"&text=" + info[1]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-length", "0");
                conn.setUseCaches(false);
                conn.setAllowUserInteraction(false);
                conn.setConnectTimeout(1000);
                conn.setReadTimeout(1000);
                conn.connect();

                int status = conn.getResponseCode();

                if ( status == 200 ) {
                    res = "Message Send as  BOT";
                }

                conn.disconnect();
            }

            
        }
        catch (MalformedURLException e) {
            Log.e("ENVIOREST", "[MalformedURLException]=>" + e.getMessage());
            e.printStackTrace();
            System.out.print("Hubo un error");

        } catch (IOException e) {
            Log.e("ENVIOREST", "[IOException]=>" + e.getMessage());
            e.printStackTrace();
            System.out.print("Hubo un error");
        }

        return res;
    }

    @Override
    protected void onProgressUpdate(String... progress){

    }

    @Override
    protected void onPostExecute(String result) {
        //this.output.setText("["+result+"]");
    }


}