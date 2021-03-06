package com.github.lxs.peep.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.github.lxs.peep.bean.Photo;

import com.github.lxs.peep.db.PhotoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig photoDaoConfig;

    private final PhotoDao photoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        photoDaoConfig = daoConfigMap.get(PhotoDao.class).clone();
        photoDaoConfig.initIdentityScope(type);

        photoDao = new PhotoDao(photoDaoConfig, this);

        registerDao(Photo.class, photoDao);
    }
    
    public void clear() {
        photoDaoConfig.clearIdentityScope();
    }

    public PhotoDao getPhotoDao() {
        return photoDao;
    }

}
