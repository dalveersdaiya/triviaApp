package com.dsd.triviaapp.application;

import android.app.Application;

import com.dsd.triviaapp.BuildConfig;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class TriviaApp extends Application {

    private static final String REALM_DB_NAME = "triviaApp.realm";
    private static final int REALM_DB_VERSION = 1;
    private static final int OLD_REALM_DB_VERSION = 0;

    /**
     * Migration for future db updates
     */
    RealmMigration migration = new RealmMigration() {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);// Initialising Realm Db

        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .build();
        DynamicRealm dynRealm = DynamicRealm.getInstance(realmConfig);
        long version = dynRealm.getVersion();
        dynRealm.close();

        if (version != -1 && version < OLD_REALM_DB_VERSION) {
            Realm.deleteRealm(realmConfig);// Delete Realm db if older version is installed
        }

        RealmConfiguration.Builder configBuilder = new RealmConfiguration.Builder()
                .name(REALM_DB_NAME)
                .migration(migration)
                .schemaVersion(REALM_DB_VERSION);

        if (BuildConfig.DEBUG) {
            configBuilder.deleteRealmIfMigrationNeeded();//If build type is debug, db will be deleted for key/version mismatch
        }
        Realm.setDefaultConfiguration(configBuilder.build());
    }


}
