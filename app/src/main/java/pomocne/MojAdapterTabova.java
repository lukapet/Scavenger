package pomocne;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import org.json.JSONObject;
import moje.src.BrowserFragment;
import moje.src.FragmentVuforia;
import moje.src.GalerijaFragment;
import moje.src.NFCFragment;
import moje.src.OpisFragment;
import moje.src.QRFragment;
import moje.src.TastaturaFragment;
import moje.src.TekstFragment;
import com.fon.Scavenger.R;

/**
 * Created by Luka on 31.12.2015..
 */
public class MojAdapterTabova extends FragmentStatePagerAdapter {

    private Context co;
    private String hardver;
    private String softver;
    private String naziv;
    private String opis;
    private String tekst;
    private String resenje;
    private String potrebnoZnanje;
    private String lokacija;
    private String slike;
    protected String hint;
    private String url;
    private int tezina;
    private Bundle b;
    private StringFetcher sf;
    private Zadatak zadatak;

    public MojAdapterTabova(FragmentManager fm, Context c, JSONObject podaci) {
        super(fm);
        co=c;
        sf = new StringFetcher(co);
        iscupaj(podaci);
    }

    public String getLokacija() {
        return lokacija;
    }

    public int getTezina() {
        return tezina;
    }

    public String getHardver() {
        return hardver;
    }

    public String getHint() {
        return hint;
    }

    public String getResenje() {
        return resenje;
    }

    public void iscupaj(JSONObject podaci){
        JsonCitac jsonCitac= new JsonCitac(co);
        zadatak = jsonCitac.uzmiZadatak(podaci);
        hardver = zadatak.getHardver();
        softver = zadatak.getSoftver();
        naziv = zadatak.getNaziv();
        opis = zadatak.getOpis();
        tekst = zadatak.getTekst();
        resenje = zadatak.getResenje();
        potrebnoZnanje = zadatak.getPotrebnoZnanje();
        lokacija = zadatak.getLokacija();
        slike = zadatak.getSlike();
        url = zadatak.getUrl();
        hint = zadatak.getHint();
        tezina = zadatak.getTezina();
   }


    @Override
    public Fragment getItem(int position) {
        b = new Bundle();
        switch(position) {
            case 0:
                return vratiTekstFragment(naziv, lokacija, tekst);
            case 1:
                return vratiOpisFragment(opis);
            case 2:
                return vratiGalerijaFragment(slike);
            case 3:
                return selektujSoftver(softver);
            case 4:
                return selektujHardver(hardver);
        }
        return null;
    }

    public Fragment vratiOpisFragment(String tekstualni){
        return zapakuj(new OpisFragment(), sf.uzmiString(R.string.OpisFragmentKey),tekstualni);
    }

    public Fragment vratiGalerijaFragment(String slike){
        return zapakuj(new GalerijaFragment(), sf.uzmiString(R.string.JsonMetaNazivSlike),slike);
    }

    public Fragment vratiBrowserFragment(String url){
        return zapakuj(new BrowserFragment(),sf.uzmiString(R.string.JsonMetaNazivURL), url);
    }

    public Fragment vratiTekstFragment(String naziv, String lokacija, String tekst){
        TekstFragment tf= new TekstFragment();
        ubaciUBundle(sf.uzmiString(R.string.JsonMetaNazivNaziv),naziv );
        ubaciUBundle(sf.uzmiString(R.string.JsonMetaNazivLokacija),lokacija );
        ubaciUBundle(sf.uzmiString(R.string.JsonMetaNazivTekst),tekst );
        return zapakuj(tf);
    }

    public Fragment selektujSoftver(String softver){
        switch (softver){
            case "Python":
                return vratiOpisFragment(potrebnoZnanje);
            case "Vuforia":
                return new FragmentVuforia();
            case "Browser":
                return vratiBrowserFragment(url);
        }
        return null;
    }

    public Fragment selektujHardver(String hardver){
        switch(hardver){
            case "QR":
                return new QRFragment();
            case "RFID":
                NFCFragment nf = new NFCFragment();
                return zapakuj(nf, "resenje", resenje);
            case "Tastatura":
                return new TastaturaFragment();
        }
        return null;
    }

    public Fragment zapakuj(Fragment fragment, String key, String value){
        ubaciUBundle(key,value);
        fragment.setArguments(b);
        return fragment;
    }

    public Fragment zapakuj(Fragment fragment){
        fragment.setArguments(b);
        return fragment;
    }

    public void ubaciUBundle(String key, String value){
        b.putString(key,value);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return sf.uzmiString(R.string.TabNazivZadatak);
            case 1:
                return sf.uzmiString(R.string.TabNazivOpis);
            case 2:
                return sf.uzmiString(R.string.TabNazivGakeija);
            case 3:
                return sf.uzmiString(R.string.TabNazivPomoc);
            case 4:
                return sf.uzmiString(R.string.TabNazivResenje);

        }
        return null;
    }

}
