package Extras;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import ParseDBCommunicator.ParseDBCommunicator;

/**
 * Created by Saboor Salaam on 3/10/2015.
 */
public class AlertHandler {
    Context thisContext;
    final static String EMAIL_VERIFIED = "email_verified";
    boolean verified = false;
    List<Alert> alerts = new ArrayList<Alert>();



    public AlertHandler(Context thisContext)
    {
       this.thisContext = thisContext;
       verified = getEmailVerified();
       checkForAlerts();
    }


    public boolean getEmailVerified()
    {
        boolean verified = false;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(thisContext);
        verified = preferences.getBoolean(EMAIL_VERIFIED, false);
        return verified;
    }

    public void VerifyEmail()
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(thisContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(EMAIL_VERIFIED, true);
        editor.apply();
        this.verified = true;

    }

    private void checkForAlerts()
    {
        alerts = new ParseDBCommunicator(thisContext).getAlerts();
    }

    public boolean wasAlertRead(String alert_id)
    {
        boolean read = false;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(thisContext);
        read = preferences.getBoolean(alert_id, false);
        return read;
    }

    public void markAlertRead(String alert_id)
    {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(thisContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(alert_id, true);
        editor.apply();
    }
}
