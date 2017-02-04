package pomocne;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.fon.Scavenger.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import moje.src.Main2Activity;

/**
 * Created by Luka on 8.8.2016..
 */
public class Asink extends AsyncTask<String, String, JSONObject> {



    private String kraj = "";
    private String dobijeno;
    private JSONObject jsonDobijeni;
    private JSONObject jsonGlavni;

    public Asink() {
    }

    @Override
    protected JSONObject doInBackground(String... params) {

        String adresa = params[0] + params[1];
        URL url = null;
        try {
            url = new URL(adresa);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        con.setReadTimeout(10000);
        con.setConnectTimeout(10000);
        try {
            con.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        con.setDoOutput(true);
        try {
            con.connect();
        } catch (IOException e) {
            e.printStackTrace();

        }

        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader
                    (con.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            if (bf != null){
                while ((dobijeno = bf.readLine()) != null) {
                    kraj += dobijeno;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        con.disconnect();


        try {
            jsonDobijeni = new JSONObject(kraj);
            jsonGlavni = jsonDobijeni.getJSONObject("Zadaci");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonGlavni;

    }
}