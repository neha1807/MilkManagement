package myapp.com.project.Provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import myapp.com.project.Util;

public class MyContentProvider extends ContentProvider {

    DBHelper dbHelper;
     public static SQLiteDatabase sqLiteDatabase;


    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String tabname=uri.getLastPathSegment();
        int id=sqLiteDatabase.delete(tabname,selection,null);
        return id;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tabName = uri.getLastPathSegment();
        long id = sqLiteDatabase.insert(tabName,null,values);
        Uri returnUri = Uri.parse("anything://any/"+id);
        return returnUri;

    }

    @Override
    public boolean onCreate() {
        dbHelper=new DBHelper(getContext(), Util.DB_NAME,null,Util.DB_VERSION);
        sqLiteDatabase=dbHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        String tabname=uri.getLastPathSegment();
        Cursor cursor=sqLiteDatabase.query(tabname,projection,selection,null,null,null,null);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String tabname=uri.getLastPathSegment();
        int i=sqLiteDatabase.update(tabname,values,selection,null);
        return i;
    }

    class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            sqLiteDatabase.execSQL(Util.CREATE_TAB_QUERY);
            sqLiteDatabase.execSQL(Util.CREATE_TAB_Q);
            }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
