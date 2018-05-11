package com.mcc.tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mcc.adpter.MyReleaseAdapter;
import com.mcc.data.Good;
import com.mcc.data.ItemConfigEntity;
import com.mcc.data.User;
import com.mcc.sharedPreferences.MySharePreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/4/28.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;//数据库版本，修改表结构要增加1，否则onCreate不执行
    private static DatabaseHelper instance;
    public final static byte[] _writeLock = new byte[0];
    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context, "SchoolMarket.db");
        }
        return instance;
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        ToastUtil.showShortToast("数据库创建");
        db.execSQL(" create table if not exists SCHOOLS (" +
                " id INTEGER PRIMARY KEY autoincrement, " +
                "schoolName TEXT)");
        db.execSQL(" create table if not exists SM_USERS ( " //
                + "	user_id INTEGER PRIMARY KEY autoincrement, " //
                + "	phone TEXT, " //
                + "	password TEXT, " //
                + "	school_id INTEGER, " //
                + "	name TEXT, " //
                + "	sex TEXT, " //
                + "	student_num TEXT, " //
                + "	head_img TEXT, " //
                + "	nick_name TEXT, " //
                + "	regist_time LONG) "); //
        db.execSQL("create table if not exists GOODS (" +
                "id  INTEGER PRIMARY KEY autoincrement, " +
                "user_id INTEGER," +
                "old_price TEXT," +
                "price TEXT," +
                "title TEXT," +
                "describe TEXT," +
                "images TEXT," +
                "class_id INTEGER," +
                "reply_num INTEGER," +
                "collect_num INTEGER," +
                "read_num INTEGER," +
                "publishTime LONG," +
                "school_id INTEGER," +
                "is_deal TEXT" +
                ")");
        db.execSQL("create table if not exists CLASSES(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "name TEXT)");
        db.execSQL("create table if not exists COLLECTIONS(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "good_id INTEGER)");
        db.execSQL("create table if not exists GOODS_REPLYS(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "good_id INTEGER," +
                "content TEXT" +
                ")");
        db.execSQL("create table if not exists COODS_REPLYS_REPLYS(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "good_reply_id INTEGER," +
                "content TEXT" +
                ")");
        db.execSQL("create table if not exists TRENDS(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "like_num INTEGER," +
                "reply_num INTEGER," +
                "describe TEXT," +
                "read_num INTEGER," +
                "publishTime LONG" +
                ")");
        db.execSQL("insert into CLASSES (name) values('数码')");
        db.execSQL("insert into CLASSES (name) values('美妆')");
        db.execSQL("insert into CLASSES (name) values('服饰')");
        db.execSQL("insert into CLASSES (name) values('电器')");
        db.execSQL("insert into CLASSES (name) values('书籍')");
        db.execSQL("insert into CLASSES (name) values('运动')");
        db.execSQL("insert into CLASSES (name) values('百货')");
        db.execSQL("insert into CLASSES (name) values('其他')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Drop tables
            db.execSQL("DROP TABLE SM_USERS");
            db.execSQL("DROP TABLE rtx_near");
            db.execSQL("drop table rtx_recent");
            db.execSQL("drop table rtx_gn");
            onCreate(db);
        }
    }
    public void login(String phone,String password,CallBackListener listener){
       User user=new User();
        synchronized (_writeLock) {
            String message = "";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select * from SM_USERS where phone=" +phone, null);
                if(!cursor.moveToFirst()){//不存在
                    message="该用户不存在";
                    listener.onError(message);
                    return;
                }
                cursor = db.rawQuery(
                        "select user_id,name,school_id,sex,student_num,head_img,nick_name from SM_USERS where phone= ? and password = ?",
                        new String[]{phone,password});
                if(!cursor.moveToFirst()){//不存在
                    message="密码错误";
                    listener.onError(message);
                    return;
                }else {
                    user.setPhone(phone);
                    user.setPassword(password);
                    user.setUserId(cursor.getInt(0));;
                    user.setName(cursor.getString(1));
                    user.setSchool_id(cursor.getInt(2));
                    String sex=cursor.getString(3);
                    user.setSex(MyUtil.parseBoolean(sex));
                    user.setStudentNum(cursor.getString(4));
                    user.setHead_image(cursor.getString(5));
                    user.setNick_name(cursor.getString(6));
                    listener.onOK(user);
                }

            } catch (Exception e) {
            e.printStackTrace();
            message="发生错误";
            listener.onError(message);
        } finally {
            if(db!=null)
                db.endTransaction();
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        }

    }
    public void regist(User user,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select * from SM_USERS where phone="+user.getPhone(),null);
                System.out.print("SM_USERS"+cursor.getCount());
                if(cursor.moveToFirst()){//存在
                    message="该手机号已存在";
                    listener.onError(message);
                    return;
                }
                cursor = db.rawQuery(
                        "select * from SM_USERS where school_id = ? and student_num = ?",
                        new String[]{user.getSchool_id()+"",user.getStudentNum()});
                if(cursor.moveToFirst()){//存在
                    message="该学号已存在";
                    listener.onError(message);
                    return;
                }
                 db.execSQL(
                            " insert into SM_USERS(phone,nick_name,name,password,school_id,sex,student_num,regist_time) " +
                                    "values (?,?,?,?,?,?,?,?)",
                            new Object[]{
                                    user.getPhone(),
                                    user.getNick_name(),
                                    user.getName(),
                                    user.getPassword(),
                                    user.getSchool_id(),
                                    user.getSex()+"",
                                    user.getStudentNum(),
                                    MyUtil.getNowLongTime()});
                db.setTransactionSuccessful();
                listener.onOK(null);
            } catch (Exception e) {
                e.printStackTrace();
                message="发生错误";
                listener.onError(message);
            } finally {
                if(db!=null)
                    db.endTransaction();
                if (cursor != null)
                    cursor.close();
                if (db != null)
                    db.close();
            }
        }

    }
    public void setUser(User user,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)return;
                db.beginTransaction();
                db.execSQL(
                        " update SM_USERS set nick_name= ? ,password = ? ,sex = ?,head_img = ? where user_id = ?",
                        new Object[]{
                                user.getNick_name(),
                                user.getPassword(),
                                user.getSex(),
                                user.getHead_image(),
                                user.getUserId()
                                });
                db.setTransactionSuccessful();
                listener.onOK(null);
            } catch (Exception e) {
                e.printStackTrace();
                message="发生错误";
                listener.onError(message);
            } finally {
                if(db!=null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }


    public void getMyGoodsList(int userID,int pageIndex,CallBackListener listener){
        List<Good> goodList=new ArrayList<>();
        synchronized (_writeLock) {
            String message = "";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select id,class_id,price,old_price,title,images,read_num,reply_num ,collect_num ,publishTime,is_deal from GOODS where user_id= ? limit 10 offset ? ",
                        new String[]{userID+"",pageIndex+""});
                  while (cursor.moveToNext()){
                      Good good=new Good();
                      good.setId(cursor.getInt(0));
                      good.setClass_id(cursor.getInt(1));
                      good.setPrice(cursor.getString(2));
                      good.setOld_price(cursor.getString(3));
                      good.setTitle(cursor.getString(4));
                      good.setCover_image(cursor.getString(5).split(";")[0]);
                      good.setRead_num(cursor.getInt(6));
                      good.setReply_num(cursor.getInt(7));
                      good.setCollect_num(cursor.getInt(8));
                      good.setPublishTime(MyUtil.getMsgTime(cursor.getLong(9)));
                      good.setIs_deal(MyUtil.parseBoolean(cursor.getString(10)));
                      goodList.add(good);
                  }
                  listener.onOK(goodList);

            } catch (Exception e) {
                e.printStackTrace();
                message="发生错误";
                listener.onError(message);
            } finally {
                if(db!=null)
                    db.endTransaction();
                if (cursor != null)
                    cursor.close();
                if (db != null)
                    db.close();
            }
        }

    }
    public void findClasses(CallBackListener listener){
        List<ItemConfigEntity> list=new ArrayList<>();
        synchronized (_writeLock) {
            String message = "";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select id,name from CLASSES ",
                       null);
                while (cursor.moveToNext()){
                    ItemConfigEntity itemConfigEntity=new ItemConfigEntity();
                    itemConfigEntity.setId(cursor.getInt(0));
                    itemConfigEntity.setName(cursor.getString(1));
                    list.add(itemConfigEntity);
                }
                listener.onOK(list);

            } catch (Exception e) {
                e.printStackTrace();
                message="发生错误";
                listener.onError(message);
            } finally {
                if(db!=null)
                    db.endTransaction();
                if (cursor != null)
                    cursor.close();
                if (db != null)
                    db.close();
            }
        }
    }

}
