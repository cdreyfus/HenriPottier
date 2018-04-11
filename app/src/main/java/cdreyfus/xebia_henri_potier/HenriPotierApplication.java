package cdreyfus.xebia_henri_potier;

import android.app.Application;

import com.facebook.stetho.Stetho;

import org.greenrobot.greendao.database.Database;

import cdreyfus.xebia_henri_potier.logs.FileLoggingTree;
import cdreyfus.xebia_henri_potier.models.DaoMaster;
import cdreyfus.xebia_henri_potier.models.DaoSession;
import timber.log.Timber;

public class HenriPotierApplication extends Application{

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.plant(new FileLoggingTree(getApplicationContext()));

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "xebia_henri_potier-db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
