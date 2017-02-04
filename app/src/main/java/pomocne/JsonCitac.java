package pomocne;

import android.content.Context;
import com.fon.Scavenger.R;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Luka on 26.7.2016..
 */
public class JsonCitac {

    private String rezultat;
    private Zadatak zadatak;
    private StringFetcher sf;

    public JsonCitac(Context context){
        rezultat="";
        sf = new StringFetcher(context);
        zadatak = new Zadatak();
    }

    public String izvuciPodatke(JSONObject objekat, String polje){
        try {
            rezultat = objekat.getString(polje);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rezultat;
    }

    public Zadatak uzmiZadatak(JSONObject objekat){
        zadatak.setHardver(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivHardver)));
        zadatak.setSoftver(izvuciPodatke(objekat, sf.uzmiString(R.string.JsonMetaNazivSoftver)));
        zadatak.setNaziv(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivNaziv)));
        zadatak.setOpis(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivOpis)));
        zadatak.setTekst(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivTekst)));
        zadatak.setResenje(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivResenje)));
        zadatak.setPotrebnoZnanje(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivPredZnanje)));
        zadatak.setLokacija(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivLokacija)));
        zadatak.setSlike(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivSlike)));
        zadatak.setUrl(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivURL)));
        zadatak.setHint(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivHint)));
        zadatak.setTezina(Integer.parseInt(izvuciPodatke(objekat,sf.uzmiString(R.string.JsonMetaNazivTezina))));
        return zadatak;
    }
}
