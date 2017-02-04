package pomocne;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.fon.Scavenger.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;
import moje.src.FBLogin;

/**
 * Created by Luka on 9.8.2016..
 */
public class Loader {

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private JSONObject metapodaci;
    private JSONObject jsonZadatak;
    private Context context;
    private String domen;
    private String pocetniServis;
    private StringFetcher sf;
    private String kod;
    private long milisec;
    private String serviceName;
    public static final String SUFIKS = "?kod=";
    private String kraj;
    private int[] bodovi;
    private int brZadataka;
    private long time;

    public Loader(Context context) {
        this.context = context;
        sf = new StringFetcher(context);
        preference = this.context.getSharedPreferences(sf.uzmiString(R.string.Preference), 0);
        editor = preference.edit();
        domen = sf.uzmiString(R.string.Domen);
        pocetniServis = sf.uzmiString(R.string.Pocetni_Servis);
        serviceName = preference.getString("servis", null);
    }

    public void pokreni(){
        skiniMetapodatke();
        setujZadatak(kod);
        editor.putInt("progresBar",0);
        editor.putInt("progresHint", 0);
        time = System.currentTimeMillis();
        editor.putLong("time", time);
        editor.apply();
    }

    public void skiniMetapodatke() {
        proveriServis();
        uzmiBroj(domen + "brZadataka.php" + SUFIKS, "-1");
        try{
            kod = preference.getString(sf.uzmiString(R.string.Preference_Key), null);
            editor.putString(sf.uzmiString(R.string.Preference_Key), "-1");
            editor.putInt("brZadataka",brZadataka);
            String s = "";
            for (int i = 0; i < bodovi.length -1; i++){
                s = s + String.valueOf(bodovi[i]) + ",";
            }
            s+=String.valueOf(bodovi[bodovi.length-1]);
            editor.putString("bodovi",s);
            editor.apply();
            kod = preference.getString(sf.uzmiString(R.string.Preference_Key), null);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void proveriServis() {
        try {
            metapodaci = new Asink().execute(domen + pocetniServis, "").get();
            JsonCitac citac = new JsonCitac(context);
            milisec = Long.parseLong(citac.izvuciPodatke(metapodaci, sf.uzmiString(R.string.JsonMetaNazivTajmer)));
            serviceName = citac.izvuciPodatke(metapodaci, sf.uzmiString(R.string.JsonMetaNazivServis));
            editor.putLong("milisec", milisec);
            editor.putString("servis", serviceName);
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, sf.uzmiString(R.string.Nema_Interneta), Toast.LENGTH_SHORT).show();
        }
    }

    public void uzmiZadatak(String kod) {
        try {
            jsonZadatak = new Asink().execute(domen + serviceName + SUFIKS, kod).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();

        }
    }

    public void setujZadatak(String kod) {
        uzmiZadatak(kod);
        if (jsonZadatak != null) {
            try {
                kraj = jsonZadatak.getString(sf.uzmiString(R.string.JsonMetaNazivKraj));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (kraj == null) {
                editor.putString("zadatak", jsonZadatak.toString());
                editor.apply();
            }
        }else{
            Toast.makeText(context, sf.uzmiString(R.string.Nema_Interneta), Toast.LENGTH_SHORT).show();
        }
    }


    public void uzmiBroj(String adresa, String kod) {
        JSONObject jo;
        try {
            jo = new Asink().execute(adresa, kod).get();
            brZadataka = Integer.parseInt(new JsonCitac(context).izvuciPodatke(jo, sf.uzmiString(R.string.JsonMetaNazivBrojZadataka)));
            bodovi = new int[brZadataka];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
