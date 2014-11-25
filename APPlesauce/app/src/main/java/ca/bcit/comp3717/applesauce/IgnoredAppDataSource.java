package ca.bcit.comp3717.applesauce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IgnoredAppDataSource {

    // Database fields
    private SQLiteDatabase database;
    private IgnoredAppSQLHelper   dbHelper;
    private String[] allColumns = { IgnoredAppSQLHelper.COLUMN_ID,
            IgnoredAppSQLHelper.COLUMN_APPNAME };

    public IgnoredAppDataSource(Context context) {
        dbHelper = new IgnoredAppSQLHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean checkIfIgnored(String appname)
    {
        Cursor cursor = database.query(IgnoredAppSQLHelper.TABLE_NAME,
                allColumns,
                IgnoredAppSQLHelper.COLUMN_APPNAME + " = '" + appname + "'",
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

    public Ignored ignoreApp(String appname) {
        ContentValues values = new ContentValues();

        if(checkIfIgnored(appname))
        {
            return null;
        }

        values.put(IgnoredAppSQLHelper.COLUMN_APPNAME, appname);

        long insertId = database.insert(IgnoredAppSQLHelper.TABLE_NAME,
                null,
                values);

        Cursor cursor = database.query(IgnoredAppSQLHelper.TABLE_NAME,
                allColumns,
                IgnoredAppSQLHelper.COLUMN_ID + " = " + insertId,
                null,
                null,
                null,
                null);


        cursor.moveToFirst();
        Ignored ignoredApp = cursorToIgnoredApp(cursor);
        cursor.close();

        return ignoredApp;
    }

    public void deleteIgnoredApp(String appname) {

        database.delete(IgnoredAppSQLHelper.TABLE_NAME,
                IgnoredAppSQLHelper.COLUMN_APPNAME + " = '" + appname + "'",
                null);
    }

    public List<Ignored> getAllIgnoredApps() {
        List<Ignored> ignoredApps = new ArrayList<Ignored>();
        Cursor cursor = database.query(IgnoredAppSQLHelper.TABLE_NAME,
                allColumns,
                null,
                null,
                null,
                null,
                null);

        try {
            cursor.moveToFirst();

            while(!(cursor.isAfterLast())) {
                Ignored app = cursorToIgnoredApp(cursor);
                ignoredApps.add(app);
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }

        return ignoredApps;
    }

    private Ignored cursorToIgnoredApp(final Cursor cursor) {
        Ignored app = new Ignored();
        app.setId(cursor.getLong(0));
        app.setAppName(cursor.getString(1));

        return app;
    }
}
