package com.github.lxs.peep.db;


import com.github.lxs.peep.App;

public class DBManager {
    private final static String dbName = "peep.db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private DaoSession mDaoSession;

    public DBManager() {
        openHelper = new DaoMaster.DevOpenHelper(App.getApplication(), dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @return
     */
    public static DBManager getInstance() {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager();
                }
            }
        }
        return mInstance;
    }

    public DaoSession getDaoSession() {
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        mDaoSession = daoMaster.newSession();
        return mDaoSession;
    }
}
