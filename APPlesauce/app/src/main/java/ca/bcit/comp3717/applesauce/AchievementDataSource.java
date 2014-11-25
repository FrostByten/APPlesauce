package ca.bcit.comp3717.applesauce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AchievementDataSource {

    // Database fields
    private SQLiteDatabase database;
    private AchievementSQLHelper   dbHelper;
    private String[] allColumns = { AchievementSQLHelper.COLUMN_ID,
            AchievementSQLHelper.COLUMN_APPNAME, AchievementSQLHelper.COLUMN_DESC };

    public AchievementDataSource(Context context) {
        dbHelper = new AchievementSQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean checkIfIgnored(String appname)
    {
        Cursor cursor = database.query(AchievementSQLHelper.TABLE_NAME,
                allColumns,
                AchievementSQLHelper.COLUMN_APPNAME + " = '" + appname + "'",
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst())
        {
            return true;
        }

        return false;
    }

    public static void makeChieve(Context c, String name, String desc)
    {
        AchievementDataSource datasource = new AchievementDataSource(c);
        try { datasource.open(); }
        catch(SQLException e) { Log.d("Achievements: Add ", e.getLocalizedMessage()); }
        if(datasource.addAchievement(name, desc))
            Toast.makeText(c, Main.c.getString(R.string.unlocked) + name, Toast.LENGTH_SHORT).show();
        datasource.close();
    }

    public boolean addAchievement(String name, String desc)
    {
        ContentValues values = new ContentValues();

        if(checkIfIgnored(name))
            return false;

        values.put(AchievementSQLHelper.COLUMN_APPNAME, name);
        values.put(AchievementSQLHelper.COLUMN_DESC, desc);

        long insertId = database.insert(AchievementSQLHelper.TABLE_NAME,
                null,
                values);

        Main.chievecount++;
        if(Main.chievecount == 1)
            AchievementDataSource.makeChieve(Main.c, Main.c.getString(R.string.muchname), Main.c.getString(R.string.muchdesc));
        else if(Main.chievecount == 5)
            AchievementDataSource.makeChieve(Main.c, Main.c.getString(R.string.suchname), Main.c.getString(R.string.suchdesc));
        else if(Main.chievecount == 10)
            AchievementDataSource.makeChieve(Main.c, Main.c.getString(R.string.wowname), Main.c.getString(R.string.wowdesc));
        else if(Main.chievecount == 20)
            AchievementDataSource.makeChieve(Main.c, Main.c.getString(R.string.getalifename), Main.c.getString(R.string.getalifedesc));

        return true;
    }

    public void resetAchievements()
    {
        database.delete(AchievementSQLHelper.TABLE_NAME, "1=1", null);
    }

    public List<String> getAllIgnoredApps()
    {
        List<String> ignoredApps = new ArrayList<String>();
        Cursor cursor = database.query(AchievementSQLHelper.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        try
        {
            cursor.moveToLast();

            while(!(cursor.isBeforeFirst()))
            {
                String app = cursorToAchievement(cursor);
                ignoredApps.add(app);
                cursor.moveToPrevious();
            }
        }
        finally
        {
            cursor.close();
        }

        return ignoredApps;
    }

    public List<String> getAllIgnoredAppsDesc()
    {
        List<String> ignoredApps = new ArrayList<String>();
        Cursor cursor = database.query(AchievementSQLHelper.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        try
        {
            cursor.moveToLast();

            while(!(cursor.isBeforeFirst()))
            {
                String app = cursorToAchievementDesc(cursor);
                ignoredApps.add(app);
                cursor.moveToPrevious();
            }
        }
        finally
        {
            cursor.close();
        }

        return ignoredApps;
    }

    private String cursorToAchievement(final Cursor cursor)
    {
        return cursor.getString(1);
    }

    private String cursorToAchievementDesc(final Cursor cursor)
    {
        try
        {
            return cursor.getString(2);
        }
        catch(Exception e)
        {
            return "";
        }
    }
}
