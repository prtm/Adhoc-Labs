package io.adhoclabs.prtm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ghost on 17/1/17.
 */

public class L {

    public void tmlong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }


    public void tmshort(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public void tlog(String msg) {
        Log.d("preetam", msg);
    }
}
