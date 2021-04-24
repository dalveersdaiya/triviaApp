package com.dsd.triviaapp.model.modelAccessLayer;

import androidx.annotation.NonNull;

import com.dsd.triviaapp.helper.LogHelper;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;


public abstract class DataLayerHelper {

    /**
     * Initialize the realm db transaction using this
     * @return
     */
    public static Realm openRealmTransaction() {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        return realm;
    }

    public static Realm getRealmInstance() {
        Realm realm = Realm.getDefaultInstance();
        return realm;
    }

    public static void closeRealmTransaction() {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isClosed())
            realm.close();
    }

    /**
     * Commits and close the realm transactions
     */
    public static void closeAndCommitTransaction() {
        Realm realm = Realm.getDefaultInstance();

        if (realm.isInTransaction())
            realm.commitTransaction();

        if (!realm.isClosed())
            realm.close();
    }

    public static void closeAndCommitTransaction(Realm realm) {
        if (realm.isInTransaction())
            realm.commitTransaction();

        if (!realm.isClosed())
            realm.close();
    }

    /**
     * Updates realm object without primary keys
     * @param realmObject
     */
    public static void copyToRealm(RealmObject realmObject) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        realm.insertOrUpdate(realmObject);
    }

    /**
     * Updates realm object with primary keys
     * @param realmObject
     */
    public static void copyToRealmOrUpdate(RealmObject realmObject) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        realm.copyToRealmOrUpdate(realmObject);
    }


    public static void createOrUpdateObjectFromJson(Class<RealmModel> clazz, String objectString) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        realm.createOrUpdateObjectFromJson(clazz, objectString);
    }



    public static void copyToRealmOrUpdate(Realm realm, RealmObject realmObject) {
        if (!realm.isInTransaction())
            realm.beginTransaction();

        realm.copyToRealmOrUpdate(realmObject);
    }

    public static void insertOrUpdate(RealmObject realmObject) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        realm.insertOrUpdate(realmObject);
    }

    public static <E extends RealmModel> E copyFromRealm(E realmObject) {
        Realm realm = Realm.getDefaultInstance();
        return realm.copyFromRealm(realmObject);
    }

    public static <E extends RealmModel> E createObject(Class<E> clazz) {

        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        return realm.createObject(clazz);
    }

    /**
     * Create a realm db object with primary key
     * @param clazz
     * @param primaryKeyValue
     * @param <E>
     * @return
     */
    public static <E extends RealmModel> E createObject(Class<E> clazz, @Nullable Object primaryKeyValue) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();
        return realm.createObject(clazz, primaryKeyValue);
    }

    public static void delete(Class<? extends RealmModel> clazz) {
        Realm realm = Realm.getDefaultInstance();
        if (!realm.isInTransaction())
            realm.beginTransaction();

        realm.delete(clazz);

    }

    public static void deleteRealmObject(RealmObject realmObject) {
        if (realmObject != null) {
            realmObject.deleteFromRealm();
        }
    }

    public static void deleteRealmDb() {
        Realm realm = Realm.getDefaultInstance();
        realm.deleteAll();
    }

    /**
     * For async db operations
     * @param realmObject
     * @param onRealmTransactionCompleted
     */
    public void updateDbData(final RealmObject realmObject, @NonNull final OnRealmTransactionCompleted onRealmTransactionCompleted) {

        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realmObject);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                onRealmTransactionCompleted.onRealmTransactionCompleted("Success.");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                LogHelper.printStackTrace(error);
            }
        });

    }

    public interface OnRealmTransactionCompleted {
        void onRealmTransactionCompleted(String result);
    }


}
