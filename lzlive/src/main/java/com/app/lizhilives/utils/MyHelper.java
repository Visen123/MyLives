package com.app.lizhilives.utils;


import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.app.lizhilives.db.DaoMaster;
import com.app.lizhilives.db.DaoSession;
import com.app.lizhilives.db.UserDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.EncryptedDatabase;

public class MyHelper {
    private MyHelper Instance;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public MyHelper getInstance() {
        if (Instance == null) {
            Instance = this;
        }
        return Instance;
    }

    /**
     * 获取DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {

        if (daoMaster == null) {

            try{
                ContextWrapper wrapper = new ContextWrapper(context) {

                };
                DaoMaster.OpenHelper helper = new MySQLiteOpenHelper(wrapper,"test.db",null);
                daoMaster = new DaoMaster(helper.getWritableDatabase()); //获取未加密的数据库

                //MyEncryptedSQLiteOpenHelper helper = new MyEncryptedSQLiteOpenHelper(wrapper,"test.db",null);
                //daoMaster = new DaoMaster(helper.getEncryptedWritableDb("1234"));//获取加密的数据库

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return daoMaster;
    }

    /**
     * 获取DaoSession对象
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {

        if (daoSession == null) {
            if (daoMaster == null) {
                getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }

        return daoSession;
    }

    private static class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        private static final String UPGRADE="upgrade";

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

                MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
                    @Override
                    public void onCreateAllTables(Database db, boolean ifNotExists) {
                        DaoMaster.createAllTables(db,ifNotExists);
                    }

                    @Override
                    public void onDropAllTables(Database db, boolean ifExists) {
                        DaoMaster.dropAllTables(db,ifExists);
                    }
                });



            Log.e(UPGRADE,"upgrade run success");
        }
    }



    private static class MyEncryptedSQLiteOpenHelper extends DaoMaster.OpenHelper {

        private final Context context;
        private final String name;
        private final int version = DaoMaster.SCHEMA_VERSION;

        private boolean loadSQLCipherNativeLibs = true;

        public MyEncryptedSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
            this.context=context;
            this.name=name;
        }

        private static final String UPGRADE="upgrade";

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {

            EncryptedMigrationHelper.migrate((EncryptedDatabase) db,UserDao.class);
            Log.e(UPGRADE,"upgrade run success");
        }

        @Override
        public Database getEncryptedWritableDb(String password) {
            MyEncryptedHelper encryptedHelper = new MyEncryptedHelper(context,name,version,loadSQLCipherNativeLibs);
            return encryptedHelper.wrap(encryptedHelper.getReadableDatabase(password));
        }

        private class MyEncryptedHelper extends net.sqlcipher.database.SQLiteOpenHelper {
            public MyEncryptedHelper(Context context, String name, int version, boolean loadLibs) {
                super(context, name, null, version);
                if (loadLibs) {
                    net.sqlcipher.database.SQLiteDatabase.loadLibs(context);
                }
            }

            @Override
            public void onCreate(net.sqlcipher.database.SQLiteDatabase db) {
                MyEncryptedSQLiteOpenHelper.this.onCreate(wrap(db));
            }

            @Override
            public void onUpgrade(net.sqlcipher.database.SQLiteDatabase db, int oldVersion, int newVersion) {
                MyEncryptedSQLiteOpenHelper.this.onUpgrade(wrap(db), oldVersion, newVersion);
            }

            @Override
            public void onOpen(net.sqlcipher.database.SQLiteDatabase db) {
                MyEncryptedSQLiteOpenHelper.this.onOpen(wrap(db));
            }

            protected Database wrap(net.sqlcipher.database.SQLiteDatabase sqLiteDatabase) {
                return new EncryptedDatabase(sqLiteDatabase);
            }
        }
    }
}

