package ca.bcit.comp3717.applesauce;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AchievementSQLHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME   = "Achievements2";
    public static final String COLUMN_ID    = "_id";
    public static final String COLUMN_APPNAME = "achievement";
    public static final String COLUMN_DESC = "description";

    public static final String DATABASE_NAME    = "achievement.db";
    private static final int DATABASE_VERSION   = 3;
    // Create database
    private static final String DATABASE_CREATE2 = "create table " + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_APPNAME + " text not null, " + COLUMN_DESC + " text not null);";

    public AchievementSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(AchievementSQLHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
