package presenter.app_config;

/**
 * Created by ddopi on 5/30/2017.
 */

import android.app.Application;
import android.os.StrictMode;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by ddopikMain on 2/28/2017.
 */

///this is (Application) class will provide Dagger Depandaces
// for all activites as our application Running
public class MainApp extends Application {


    public static MainApp app;
    public static Realm realm;
    public static final String SittingActivity_sharedPreferance = "MyPrefsFile";

    public static final String loginUrl="http://nomw.code-court.com/index/login/" ;
    public static final String cources_url="http://nomw.code-court.com/index/courses";
    public static final String trainers_url="http://nomw.code-court.com/index/courses";
    public static final String unActiveCources_flag="0";
    public static final String activeCources_flag="1";
    public static final String bestCources_flag="2";
    public static final String offers_flag="3";


    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
        initializeDepInj(); ///intializing Dagger Dependancy
        intializeRealmInstance(); //intializing Realm Config Instance

//        SharedPreferences.Editor editor2 = getApplicationContext().getSharedPreferences(SittingActivity_sharedPreferance, MODE_PRIVATE).edit();
//        editor2.putString("notification_switch","true");
//        editor2.commit();
    }

    private void initializeDepInj() {

    }

    public static MainApp getMainAppApplication() {

        return app;
    }


    public void intializeRealmInstance() {

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());


        // Initialize Realm
        Realm.init(app); // Initialize Realm. Should only be done once when the application starts.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();

        Realm.deleteRealm(realmConfig); // Delete Realm between app restarts.


        Realm.setDefaultConfiguration(realmConfig);
        this.realm = Realm.getDefaultInstance();
    }

    public static  boolean isInternetAvailable() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        boolean connected = false;
        String instanceURL = "www.google.com";
        Socket socket;
        try {
            socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(instanceURL, 80);
            socket.connect(socketAddress, 5000);
            if (socket.isConnected()) {
                connected = true;
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket = null;
        }
        Log.e("MainApp","ConnectionState---->"+connected);
        return connected;
    }

}

