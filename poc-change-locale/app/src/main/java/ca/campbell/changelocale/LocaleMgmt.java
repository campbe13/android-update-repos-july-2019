package ca.campbell.changelocale;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;
/**
 * Manage Locale
 * ref https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
 * ref https://developer.android.com/reference/java/util/Locale
 *
 * This code does not consider API >= 25 where there is another change involved
 * in the Android specific code (not here) wrt context & config.
 *
 * @author tricia
 *
 **/

public class LocaleMgmt  {
    private Locale locale;

    public LocaleMgmt(Context context) {
        this.locale = context.getResources().getConfiguration().locale;
    }
    public String getCountry() {
        return this.locale.getCountry();
    }
    public String getLanguage() {
        return this.locale.getLanguage();
    }
    public void setNewLocale(Context context, String language) {
          updateLanguage(context, language);
    }
    public  Context updateLanguage(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        this.locale = locale;
        if (android.os.Build.VERSION.SDK_INT < 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }

        return context;
    }
}

