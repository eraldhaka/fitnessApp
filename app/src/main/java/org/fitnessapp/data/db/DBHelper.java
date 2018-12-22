package org.fitnessapp.data.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.fitnessapp.data.db.model.Users;

/**
 * Created by user on 11/14/2017.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "fitness_app_ormlite.db";
    private static final int    DATABASE_VERSION = 3;

    private Dao<Users, Integer> mUserDao = null;

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Users.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Users.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

    }

    /* User */

    public Dao<Users, Integer> getUserDao() throws SQLException {
        if (mUserDao == null) {
            try {
                mUserDao = getDao(Users.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }

        return mUserDao;
    }

    @Override
    public void close() {
        mUserDao = null;

        super.close();
    }
}