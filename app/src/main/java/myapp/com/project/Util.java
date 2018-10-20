package myapp.com.project;

import android.net.Uri;

import java.util.Date;

public class Util {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Users.db";

    public static final String TAB_NAME = "User";
    public static final String TAB_NAME1 = "Customer";
    public static final String TAB_NAME2 = "Bill";


    public static final String COL_ID ="_ID";
    public static final String COL_QUANTITY = "QUANTITY";
    public static final String COL_RATE = "RATE";
    public static final String COL_DATE = "DATE";
    public static final String COL_TYPE = "TYPE";

    public static final String COL_ID1="_ID1";
    public static final String COL_QUANTITYCUSTOMER = "QUANTITY1";
    public static final String COL_RATECUSTOMER= "RATE1";
    public static final String COL_NAME = "NAME";
    public static final String COL_PHONE = "PHONE";
    public static final String COL_ADDRESS= "ADDRESS";
    public static final String COL_TYPE1= "TYPE1";
    public static final String COL_STATUS= "STATUS";
    public static final String COL_BID="_ID2";
    public static final String COL_QUANTITYBILL = "QUANTITY2";
    public static final String COL_RATEBILL= "RATE2";

    public static final String CREATE_TAB_QUERY = "create table User(" +
            "_ID integer primary key autoincrement ," +
            "QUANTITY double, " +
            "RATE double, " +
            "TYPE varchar(25), " +
            "DATE DATETIME DEFAULT CURRENT_DATE" +
            ")";
    public static final String CREATE_TAB_Q = "create table Customer(" +
            "_ID1 integer primary key autoincrement ," +
            "QUANTITY1 varchar(10), " +
            "RATE1 double, " +
            "NAME varchar(15), " +
            "PHONE varchar(20)," +
            "ADDRESS varchar(20)," +
            "TYPE1 varchar(14) " +
            "DATE DATETIME DEFAULT CURRENT_DATE" +
            "STATUS integer , " +



            ")";


    public static Uri USER_URI = Uri.parse("content://myapp.com.project.mycp/" + TAB_NAME);
    public static Uri USER_URI1 = Uri.parse("content://myapp.com.project.mycp/" + TAB_NAME1);

}


