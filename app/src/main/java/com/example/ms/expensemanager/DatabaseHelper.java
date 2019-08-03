package com.example.ms.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Expense.db";


    public static final String TABLE_NAME = "income_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "INCOME";
    public static final String COL_3 = "PAYER";
    public static final String COL_4 = "CAT";
    public static final String COL_5 = "DES";
    public static final String COL_6 = "REF";
    public static final String COL_7 = "DATE_T";
    public static final String COL_8 = "IMAGE";
    public static final String COL_9 = "UID";



    public static final String TABLE_NAME2 = "expense_table";
    public static final String COL_21 = "ID";
    public static final String COL_22 = "EXP";
    public static final String COL_23 = "PAYEE";
    public static final String COL_24 = "CAT";
    public static final String COL_25 = "DES";
    public static final String COL_26 = "REF";
    public static final String COL_27 = "DATE_T";
    public static final String COL_28 = "IMAGE";
    public static final String COL_29 = "UID";



    public static final String TABLE_NAME3 = "balance_table";
    public static final String COL_31 = "ID";
    public static final String COL_32 = "BAL";
    public static final String COL_33 = "UID";


    public static final String TABLE_NAME4 = "notes_table";
    public static final String COL_41 = "ID";
    public static final String COL_42 = "NOTE";
    public static final String COL_43 = "DATE";
    public static final String COL_44 = "UID";


    public static final String TABLE_NAME5 = "budget_table";
    public static final String COL_51 = "ID";
    public static final String COL_52 = "CATEGORY";
    public static final String COL_53 = "PE";
    public static final String COL_54 = "AMOUNT";
    public static final String COL_55 = "ALERT";
    public static final String COL_56 = "DATE";
    public static final String COL_57 = "SPEND";
    public static final String COL_58 = "DATEEND";
    public static final String COL_59 = "UID";






    public static final String TABLE_NAME6 = "reminder_table";
    public static final String COL_61 = "ID";
    public static final String COL_62 = "DATE";
    public static final String COL_63 = "TIME";
    public static final String COL_64 = "PAY";
    public static final String COL_65 = "AMOUNT";
    public static final String COL_66 = "STATUS";
    public static final String COL_67 = "UID";
    public static final String COL_68 = "TYPE";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,INCOME INTEGER ,PAYER TEXT,CAT TEXT,DES TEXT,REF TEXT,DATE_T DATE,IMAGE TEXT,UID TEXT)");
        db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,EXP INTEGER ,PAYEE TEXT,CAT TEXT,DES TEXT,REF TEXT,DATE_T DATE,IMAGE TEXT,UID TEXT)");
        db.execSQL("create table " + TABLE_NAME3 +" (BAL INTEGER,UID TEXT)");
        db.execSQL("create table " + TABLE_NAME4 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NOTE TEXT,DATE TEXT,UID TEXT)");
        db.execSQL("create table " + TABLE_NAME5 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CATEGORY TEXT,PE TEXT,AMOUNT TEXT,ALERT TEXT,DATE TEXT,SPEND TEXT,DATEEND TEXT,UID TEXT)");
        db.execSQL("create table " + TABLE_NAME6 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,TIME TEXT,PAY TEXT,AMOUNT TEXT,STATUS TEXT,UID TEXT,TYPE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
         db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME5);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME6);
        onCreate(db);
    }

    public boolean insertData(int income ,String payer,String cat,String des,String date,String ref,String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,income);
        contentValues.put(COL_3,payer);
        contentValues.put(COL_4,cat);
        contentValues.put(COL_5,des);
        contentValues.put(COL_6,date);
        contentValues.put(COL_7,ref);
        contentValues.put(COL_9,uid);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else

            return true;
    }


    public boolean insertExpense(int expense ,String payee2,String cat2,String des2,String ref2,String date2,String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_22,expense);
        contentValues.put(COL_23,payee2);
        contentValues.put(COL_24,cat2);
        contentValues.put(COL_25,des2);
        contentValues.put(COL_26,ref2);
        contentValues.put(COL_27,date2);
        contentValues.put(COL_29,uid);
        long result = db.insert(TABLE_NAME2,null ,contentValues);

        if(result == -1)
            return false;
        else

            return true;


    }



    public boolean insertBudget(String category ,String period,String amount,String alert,String date,String edate,String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_52,category);
        contentValues.put(COL_53,period);
        contentValues.put(COL_54,amount);
        contentValues.put(COL_55,alert);
        contentValues.put(COL_56,date);
        String spend="0";
        contentValues.put(COL_57,spend);
        contentValues.put(COL_58,edate);
        contentValues.put(COL_59,uid);

        long result = db.insert(TABLE_NAME5,null ,contentValues);

        if(result == -1)
            return false;
        else

            return true;


    }


    public boolean insertCurrent(int b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_22,b);
        long result = db.insert(TABLE_NAME3,null ,contentValues);
        if(result == -1)
            return false;
        else

            return true;


    }

    public Cursor getAllData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor res = db.rawQuery("select * from "+TABLE_NAME +"where UID =+id+"" ",null);
        Cursor res =  db.rawQuery("select * from " + TABLE_NAME + " where " + COL_9 + "='" + id + "'" , null);
        return res;
    }

     public Cursor getbal() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME3,null);
        return res;
    }


    public Cursor getAllexpense(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res2 = db.rawQuery("select * from "+TABLE_NAME2 + " where " + COL_9 + "='" + id + "'",null);
        return res2;
    }

    public Cursor getNotes(String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME4 +" where uid='"+uid+"'",null);
        return res;
    }

    public Cursor getNotewithid(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor res = db.rawQuery("select * from "+TABLE_NAME4,"ID = ?",new String[] {id});
        Cursor res =  db.rawQuery("select "+ COL_42+" from " + TABLE_NAME4 + " where " + COL_1 + "='" + id + "'" , null);
        return res;
    }

    public Cursor getbud(String p,String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor res = db.rawQuery("select * from "+TABLE_NAME4,"ID = ?",new String[] {id});
        Cursor res =  db.rawQuery("select * from " + TABLE_NAME5 + " where " + COL_53 + "='" + p +"' and "+ COL_59 + "='" + uid +"'" , null);
        return res;
    }

    public Cursor getbudwithid(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor res = db.rawQuery("select * from "+TABLE_NAME4,"ID = ?",new String[] {id});
        //Cursor res =  db.rawQuery("select * from " + TABLE_NAME5 + " where " + COL_53 + "='" + p + "'" , null);
        Cursor res =  db.rawQuery("select * from " + TABLE_NAME5 + " where " + COL_51 + "='" + id + "'" , null);
        return res;
    }

    public Cursor getBudget() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res =  db.rawQuery("select * from '" +  TABLE_NAME5 + "' where CATEGORY = 'All'" , null);
        return res;
    }
    public Cursor getIncomeWithID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor res = db.rawQuery("select * from "+TABLE_NAME4,"ID = ?",new String[] {id});
        Cursor res =  db.rawQuery("select * from "+ TABLE_NAME + " where " + COL_1 + "='" + id + "'" , null);
        return res;
    }
    public Cursor getExpenseWithID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor res = db.rawQuery("select * from "+TABLE_NAME4,"ID = ?",new String[] {id});
        Cursor res =  db.rawQuery("select * from "+ TABLE_NAME2 + " where " + COL_1 + "='" + id + "'" , null);
        return res;
    }

    public boolean updateData(String id,int income ,String payer,String cat,String des,String date,String ref) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,income);
        contentValues.put(COL_3,payer);
        contentValues.put(COL_4,cat);
        contentValues.put(COL_5,des);
        contentValues.put(COL_6,date);
        contentValues.put(COL_7,ref);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

  /*  public boolean updateExpense(String id,int income2 ,String payee2,String cat2,String des2,String date2,String ref2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_21,id);
        contentValues.put(COL_22,income2);
        contentValues.put(COL_23,payee2);
        contentValues.put(COL_24,cat2);
        contentValues.put(COL_25,des2);
        contentValues.put(COL_26,date2);
        contentValues.put(COL_27,ref2);
        db.update(TABLE_NAME2, contentValues, "ID = ?",new String[] { id });
        return true;
    }*/



    public boolean updatebudget(String id,String scat ,String speriod,String samount,String salert,String dmy,String dateInString) {
        //(id,scat, speriod, samount, salert, dmy, dateInString)
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();



        contentValues.put(COL_52,scat);
        contentValues.put(COL_53,speriod);
        contentValues.put(COL_54,samount);
        contentValues.put(COL_55,salert);
        contentValues.put(COL_56,dmy);
        contentValues.put(COL_58,dateInString);
        db.update(TABLE_NAME5, contentValues, "ID = ?",new String[] { id });
        return true;
    }


    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }
    public Integer deleteIncome (String id,String income) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from balance_table",null);
        res.moveToNext();
        String cbal=res.getString(0);
        int old_bal=Integer.valueOf(cbal);
        int inc=Integer.valueOf(income);
        int new_bal=old_bal-inc;
        ContentValues contentValues = new ContentValues();
        contentValues.put(" bal",new_bal);
        /* db.insert("balance_table",null ,contentValues);*/
        db.update("balance_table", contentValues, "BAL = ?",new String[] { cbal });

        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});

    }
    public Integer deleteExpense (String id,String expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from balance_table",null);
        res.moveToNext();
        String cbal=res.getString(0);
        int old_bal=Integer.valueOf(cbal);
        int exp=Integer.valueOf(expense);
        int new_bal=old_bal+exp;
        ContentValues contentValues = new ContentValues();
        contentValues.put(" bal",new_bal);
        /* db.insert("balance_table",null ,contentValues);*/
        db.update("balance_table", contentValues, "BAL = ?",new String[] { cbal });
        return db.delete(TABLE_NAME2, "ID = ?",new String[] {id});
    }
    public Integer deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME4, "ID = ?",new String[] {id});
    }



    public Integer deleteAllInc(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, null,null);
    }
    public Integer deleteAllExp(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME2, null,null);
    }


    public boolean insertnotes(String notes,String dmy,String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_42,notes);
        contentValues.put(COL_43,dmy);
        contentValues.put(COL_44,uid);
        long result = db.insert(TABLE_NAME4,null ,contentValues);
        if(result == -1)
            return false;
        else

            return true;
    }
    public boolean updatenotes(String notes,String dmy,String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_42,notes);
        contentValues.put(COL_43,dmy);
        //long result = db.insert(TABLE_NAME4,null ,contentValues);
        long result= db.update(TABLE_NAME4, contentValues, "ID = ?",new String[] { id });
        if(result == -1)
            return false;
        else

            return true;
    }



    public boolean updatIncome(String ssid,String samount,String spay,String scat,String sdes,String sref,String sdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,samount);
        contentValues.put(COL_3,spay);
        contentValues.put(COL_4,scat);
        contentValues.put(COL_5,sdes);
        contentValues.put(COL_6,sref);
        contentValues.put(COL_7,sdate);

        //long result = db.insert(TABLE_NAME4,null ,contentValues);
        long result= db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { ssid });
        if(result == -1)
            return false;
        else

            return true;
    }
    public boolean updatExpense(String ssid,String samount,String spay,String scat,String sdes,String sref,String sdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_22,samount);
        contentValues.put(COL_23,spay);
        contentValues.put(COL_24,scat);
        contentValues.put(COL_25,sdes);
        contentValues.put(COL_26,sref);
        contentValues.put(COL_27,sdate);

        //long result = db.insert(TABLE_NAME4,null ,contentValues);
        long result= db.update(TABLE_NAME2, contentValues, "ID = ?",new String[] { ssid });
        if(result == -1)
            return false;
        else

            return true;
    }


}
