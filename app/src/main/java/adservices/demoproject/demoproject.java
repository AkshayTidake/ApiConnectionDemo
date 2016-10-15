package adservices.demoproject;

import android.app.Application;

import com.firebase.client.Firebase;

import static com.google.android.gms.internal.zzng.fa;


public class demoproject extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
