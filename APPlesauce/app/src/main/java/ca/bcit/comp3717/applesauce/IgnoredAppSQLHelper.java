package ca.bcit.comp3717.applesauce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class IgnoredAppSQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME   = "IgnoredApps";
    public static final String COLUMN_ID    = "_id";
    public static final String COLUMN_APPNAME = "appname";

    public static final String DATABASE_NAME    = "ignoredApp.db";
    private static final int DATABASE_VERSION   = 1;
    // Create database
    private static final String DATABASE_CREATE = "create table " + TABLE_NAME + "(" +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_APPNAME + " text not null);";

    public IgnoredAppSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(IgnoredAppSQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
