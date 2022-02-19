package space.vika.chasiki;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private Context context;
    private String DB_NAME = "game.db";

    private SQLiteDatabase db;

    private static DBManager dbManager;

    public static DBManager getInstance(Context context) {
        if (dbManager == null) {
            dbManager = new DBManager(context);
        }
        return dbManager;
    }

    private DBManager(Context context) {
        this.context = context;
        db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        createTablesIfNeedBe();
    }

    void addResult(String username, String mail) {
        db.execSQL("INSERT INTO RESULTS VALUES ('" + username + "', " + mail
                + ");");
    }

    ArrayList<User> getAllResults() {
        ArrayList<User> data = new ArrayList<User>();
        Cursor cursor = db.rawQuery("SELECT * FROM RESULTS ;", null);
        boolean hasMoreData = cursor.moveToFirst();
        while (hasMoreData) {
            String name = cursor.getString(cursor.getColumnIndex("USERNAME"));
            String mail = cursor.getString(cursor.getColumnIndex("MAIL"));
            data.add(new User(name, mail));
            hasMoreData = cursor.moveToNext();
        }
        return data;
    }
    private void createTablesIfNeedBe() {
        db.execSQL("CREATE TABLE IF NOT EXISTS RESULTS (USERNAME TEXT, MAIL TEXT);");
    }
}
