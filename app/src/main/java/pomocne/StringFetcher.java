package pomocne;

import android.content.Context;

/**
 * Created by Luka on 29.7.2016..
 */
public class StringFetcher {
    private Context context;

    public StringFetcher(Context context){
        this.context = context;
    }

    public String uzmiString(int resurs){

        return context.getResources().getString(resurs);
    }
}
