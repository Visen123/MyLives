package com.app.lizhilives.utils;

import android.content.Context;

import com.app.lizhilives.db.DaoMaster;
import com.app.lizhilives.db.DaoSession;
import com.app.lizhilives.db.UserDao;
import com.app.lizhilives.model.User;

import java.util.List;

//分装数据库工具类
public class DBManager {
    private Context mContext;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private UserDao userDao;

    private DBManager(Context context) {
        mContext = context;
    }

    private static volatile DBManager instance = null;
    public static DBManager getInstance(Context context){
        if (instance==null){
            synchronized (DBManager.class){
                if (instance==null){
                    instance = new DBManager(context);
                }
            }
        }
        return instance;
    }

    public void init() {
        //初始化数据库对象，配置数据库升级
        MyHelper.getDaoMaster(mContext);
        DaoSession daoSession = MyHelper.getDaoSession(mContext);
        userDao=daoSession.getUserDao();

      /*  DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "user.db");
        mDaoMaster = new DaoMaster(helper.getWritableDb());
        mDaoSession = mDaoMaster.newSession();
        userDao = mDaoSession.getUserDao();*/
    }

    public void insertTopicMo(User user) {
        userDao.insertOrReplace(user);
    }

    public void insertTopicMo(List<User> user) {
        userDao.insertOrReplaceInTx(user);
    }
    //单个删除
    public void deleteTopicMo(User user) {
        userDao.delete(user);
    }
    //删除所有
    public void deleteTopicMo() {
        userDao.deleteAll();
    }
    //更新
    public void updateTopicMo(User user) {
        userDao.update(user);
    }
    //查询所有
    public List<User> queryAllTopicMo() {
        return userDao.queryBuilder().build().list();
    }
    //where查询
    public List<User> query(String name) {
        return userDao.queryBuilder().where(UserDao.Properties.Name.eq(name)).list();
    }
    //between
    public List<User> queryarrang(int a, int b) {
        return userDao.queryBuilder().where(UserDao.Properties.Uid.between(a,b)).list();
    }
}

