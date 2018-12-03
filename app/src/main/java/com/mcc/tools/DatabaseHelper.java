package com.mcc.tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcc.adpter.MyReleaseAdapter;
import com.mcc.data.Good;
import com.mcc.data.GoodReply;
import com.mcc.data.GoodReply2Reply;
import com.mcc.data.ItemConfigEntity;
import com.mcc.data.Trend;
import com.mcc.data.TrendReply;
import com.mcc.data.TrendReply2Reply;
import com.mcc.data.User;
import com.mcc.sharedPreferences.MySharePreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zw on 2018/4/28.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 5;//数据库版本，修改表结构要增加1，否则onCreate不执行
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
                "content TEXT," +
                "publishTime LONG" +
                ")");
        db.execSQL("create table if not exists COODS_REPLYS_REPLYS(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "good_reply_id INTEGER," +
                "content TEXT," +
                "reply_user_name TEXT" +//被回复者姓名
                ")");
        db.execSQL("create table if not exists TRENDS(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "like_num INTEGER," +
                "reply_num INTEGER," +
                "describe TEXT," +
                "read_num INTEGER," +
                "images TEXT," +
                "publishTime LONG" +
                ")");
        db.execSQL("create table if not exists TRENDS_REPLYS(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "trend_id INTEGER," +
                "content TEXT," +
                "publishTime LONG" +
                ")");
        db.execSQL("create table if not exists TRENDS_REPLYS_REPLYS(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "trend_reply_id INTEGER," +
                "content TEXT," +
                "reply_user_name TEXT" +//被回复者姓名
                ")");
        db.execSQL("create table if not exists LIKES(" +
                "id INTEGER PRIMARY KEY autoincrement," +
                "user_id INTEGER," +
                "trend_id INTEGER)");
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
            /*db.execSQL("DROP TABLE SM_USERS");
            db.execSQL("DROP TABLE SCHOOLS");
            db.execSQL("drop table GOODS");
            db.execSQL("drop table CLASSES");
            db.execSQL("drop table COLLECTIONS");
            db.execSQL("drop table GOODS_REPLYS");
            db.execSQL("drop table COODS_REPLYS_REPLYS");
            db.execSQL("drop table TRENDS_REPLYS");
            db.execSQL("drop table TRENDS_REPLYS_REPLYS");*/
/*            db.execSQL("drop table GOODS_REPLYS");
            db.execSQL("drop table COODS_REPLYS_REPLYS");
            db.execSQL("drop table TRENDS_REPLYS");
            db.execSQL("drop table TRENDS_REPLYS_REPLYS");
            db.execSQL("drop table TRENDS");*/
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
                        "select id,class_id,price,old_price,title,images,read_num,reply_num ,collect_num ,publishTime,is_deal from GOODS where user_id= ? order by id desc limit 10 offset ? ",
                        new String[]{userID+"",pageIndex*10+""});
                    System.out.print("cursor:"+cursor.getCount());
                  while (cursor.moveToNext()){
                      Good good=new Good();
                      good.setId(cursor.getInt(0));
                      good.setClass_id(cursor.getInt(1));
                      good.setPrice(cursor.getString(2));
                      good.setOld_price(cursor.getString(3));
                      good.setTitle(cursor.getString(4));
                      Gson gson = new Gson();
                      List<String> imageList=gson.fromJson(cursor.getString(5),new TypeToken<List<String>>() {}.getType());
                      good.setCover_image(imageList.get(0));
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
    public void releaseGood(Good good,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                db.execSQL(
                        " insert into GOODS(user_id,class_id,title,price,old_price,describe,images,reply_num,collect_num,read_num,publishTime,is_deal,school_id) " +
                                "values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{
                                good.getUser_id(),
                                good.getClass_id(),
                                good.getTitle(),
                                good.getPrice(),
                                good.getOld_price(),
                                good.getDescribe(),
                                good.getImages(),
                                0,
                                0,
                                0,
                                MyUtil.getNowLongTime(),
                                "false",
                                1});
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
    //设置商品已成交
    public void setGoodComplete(int good_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)return;
                db.beginTransaction();
                db.execSQL(
                        " update GOODS set is_deal = ? where id = ?",
                        new Object[]{
                                "true",
                                good_id
                        });
                db.setTransactionSuccessful();
                listener.onOK(null);
            } catch (Exception e) {
                e.printStackTrace();
                message="设置失败";
                listener.onError(message);
            } finally {
                if(db!=null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }
    public void removeGood(int good_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)return;
                db.beginTransaction();
                db.execSQL(
                        " delete from GOODS  where id = ?",
                        new Object[]{
                                good_id
                        });
                db.execSQL("delete from COLLECTIONS where good_id = ?",
                        new Object[]{good_id});
                db.setTransactionSuccessful();
                listener.onOK(null);
            } catch (Exception e) {
                e.printStackTrace();
                message="删除失败";
                listener.onError(message);
            } finally {
                if(db!=null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }
    public void findMyGood(int good_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message = "";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select id,class_id,price,old_price,title,images,read_num,reply_num ,collect_num ,publishTime,describe from GOODS where id= ? ",
                        new String[]{good_id+""});
                    Good good=new Good();
                    if(!cursor.moveToFirst()){
                        message="查询的商品已经删除或下架";
                        listener.onError(message);
                    }
                        good.setId(cursor.getInt(0));
                        good.setClass_id(cursor.getInt(1));
                        good.setPrice(cursor.getString(2));
                        good.setOld_price(cursor.getString(3));
                        good.setTitle(cursor.getString(4));
                        good.setImages(cursor.getString(5));
                        good.setRead_num(cursor.getInt(6));
                        good.setReply_num(cursor.getInt(7));
                        good.setCollect_num(cursor.getInt(8));
                        good.setPublishTime(MyUtil.getMsgTime(cursor.getLong(9)));
                        good.setDescribe(cursor.getString(10));
                listener.onOK(good);

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
    public void updateGood(Good good,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                db.execSQL(
                        " update GOODS set class_id =?,title =?,price=?,old_price=?,describe=?,images =?,publishTime=? where id= ?" ,
                        new Object[]{
                                good.getClass_id(),
                                good.getTitle(),
                                good.getPrice(),
                                good.getOld_price(),
                                good.getDescribe(),
                                good.getImages(),
                                MyUtil.getNowLongTime(),
                                good.getId()
                         });
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

    public void findGoodsListByClassId(int pageIndex,int classId,CallBackListener listener){
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
                        "select GOODS.id,GOODS.price,GOODS.title,GOODS.images,GOODS.reply_num ,GOODS.collect_num ,GOODS.publishTime,GOODS.read_num ,SM_USERS.head_img,SM_USERS.nick_name from GOODS left join SM_USERS on (GOODS.user_id=SM_USERS.user_id)  where GOODS.is_deal = ? and GOODS.class_id = ?  order by GOODS.id desc limit 20 offset ? ",
                        new String[]{"false",classId+"",pageIndex*20+""});
                while (cursor.moveToNext()){
                    Good good=new Good();
                    good.setId(cursor.getInt(0));
                    good.setPrice(cursor.getString(1));
                    good.setTitle(cursor.getString(2));
                    good.setImages(cursor.getString(3));
                    good.setReply_num(cursor.getInt(4));
                    good.setCollect_num(cursor.getInt(5));
                    good.setPublishTime(MyUtil.getMsgTime(cursor.getLong(6)));
                    good.setRead_num(cursor.getInt(7));
                    good.setUser_head_img(cursor.getString(8));
                    good.setUser_nick_name(cursor.getString(9));
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
    public void findGoodsListByKeyord(int pageIndex,String keyword,CallBackListener listener){
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
                        "select GOODS.id,GOODS.price,GOODS.title,GOODS.images,GOODS.reply_num ,GOODS.collect_num ,GOODS.publishTime,GOODS.read_num ,SM_USERS.head_img,SM_USERS.nick_name from GOODS left join SM_USERS on (GOODS.user_id=SM_USERS.user_id)  where GOODS.is_deal = ? and GOODS.title like ?  order by GOODS.id desc limit 20 offset ? ",
                        new String[]{"false","%"+keyword+"%",pageIndex*20+""});
                while (cursor.moveToNext()){
                    Good good=new Good();
                    good.setId(cursor.getInt(0));
                    good.setPrice(cursor.getString(1));
                    good.setTitle(cursor.getString(2));
                    good.setImages(cursor.getString(3));
                    good.setReply_num(cursor.getInt(4));
                    good.setCollect_num(cursor.getInt(5));
                    good.setPublishTime(MyUtil.getMsgTime(cursor.getLong(6)));
                    good.setRead_num(cursor.getInt(7));
                    good.setUser_head_img(cursor.getString(8));
                    good.setUser_nick_name(cursor.getString(9));
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

    public void findHotGoodsList(int pageIndex,CallBackListener listener){
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
                        "select GOODS.id,GOODS.price,GOODS.title,GOODS.images,GOODS.reply_num ,GOODS.collect_num ,GOODS.publishTime,GOODS.read_num ,SM_USERS.head_img,SM_USERS.nick_name from GOODS left join SM_USERS on (GOODS.user_id=SM_USERS.user_id)  where is_deal = ?  order by id desc limit 20 offset ? ",
                        new String[]{"false",pageIndex*20+""});
                while (cursor.moveToNext()){
                    Good good=new Good();
                    good.setId(cursor.getInt(0));
                    good.setPrice(cursor.getString(1));
                    good.setTitle(cursor.getString(2));
                    good.setImages(cursor.getString(3));
                    good.setReply_num(cursor.getInt(4));
                    good.setCollect_num(cursor.getInt(5));
                    good.setPublishTime(MyUtil.getMsgTime(cursor.getLong(6)));
                    good.setRead_num(cursor.getInt(7));
                    good.setUser_head_img(cursor.getString(8));
                    good.setUser_nick_name(cursor.getString(9));
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
    public void findGoodDetail(int good_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message = "";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select GOODS.id,GOODS.price,GOODS.title,GOODS.images,GOODS.reply_num ,GOODS.collect_num ,GOODS.publishTime,GOODS.read_num ,SM_USERS.head_img,SM_USERS.nick_name ,GOODS.describe,GOODS.old_price ,SM_USERS.sex,SM_USERS.phone,GOODS.user_id from GOODS left join SM_USERS on (GOODS.user_id=SM_USERS.user_id)  where GOODS.id = ? ",
                        new String[]{good_id+""});
                Good good=new Good();
                if(!cursor.moveToFirst()){
                    message="查询的商品已经删除或下架";
                    listener.onError(message);
                }
                    good.setId(cursor.getInt(0));
                    good.setPrice(cursor.getString(1));
                    good.setTitle(cursor.getString(2));
                    good.setImages(cursor.getString(3));
                    good.setReply_num(cursor.getInt(4));
                    good.setCollect_num(cursor.getInt(5));
                    good.setPublishTime(MyUtil.getMsgTime(cursor.getLong(6)));
                    good.setRead_num(cursor.getInt(7)+1);
                    good.setUser_head_img(cursor.getString(8));
                    good.setUser_nick_name(cursor.getString(9));
                    good.setDescribe(cursor.getString(10));
                    good.setOld_price("￥"+cursor.getString(11));
                    String sex=cursor.getString(12);
                    good.setUser_sex(MyUtil.parseBoolean(sex));
                    good.setPhone(cursor.getString(13));
                    good.setUser_id(cursor.getInt(14));
                    db.execSQL("update GOODS set read_num = read_num+1 where id = ?",
                            new Object[]{good_id});
                    db.setTransactionSuccessful();
                    listener.onOK(good);
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
    public List<GoodReply> findReplys(int good_id){
        List<GoodReply> list=new ArrayList<>();
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return list;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select GOODS_REPLYS.id,GOODS_REPLYS.user_id,GOODS_REPLYS.good_id," +
                                "GOODS_REPLYS.content,GOODS_REPLYS.publishTime,SM_USERS.nick_name," +
                                "SM_USERS.head_img,SM_USERS.sex from " +
                                "GOODS_REPLYS left join SM_USERS on(GOODS_REPLYS.user_id=SM_USERS.user_id)" +
                                "where GOODS_REPLYS.good_id=? ",
                        new String[]{good_id+""});
                while (cursor.moveToNext()){
                   GoodReply goodReply=new GoodReply();
                   goodReply.setId(cursor.getInt(0));
                   goodReply.setUserId(cursor.getInt(1));
                   goodReply.setGoodId(cursor.getInt(2));
                   goodReply.setContent(cursor.getString(3));
                   goodReply.setReply_time(MyUtil.getMsgTime(cursor.getLong(4)));
                   goodReply.setUser_nick_name(cursor.getString(5));
                   goodReply.setUser_head_img(cursor.getString(6));
                   String sex=cursor.getString(7);
                   goodReply.setUser_sex(MyUtil.parseBoolean(sex));
                   list.add(goodReply);
                }
                return list;

            } catch (Exception e) {
                e.printStackTrace();
                return list;
            }finally {
                if(db!=null)
                    db.endTransaction();
                if (cursor != null)
                    cursor.close();
                if (db != null)
                    db.close();
            }
        }
    }
    public List<GoodReply2Reply> findReply2Replys(int replyId){
        List<GoodReply2Reply> list=new ArrayList<>();
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return list;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select COODS_REPLYS_REPLYS.id,COODS_REPLYS_REPLYS.user_id,COODS_REPLYS_REPLYS.good_reply_id," +
                                "COODS_REPLYS_REPLYS.reply_user_name,COODS_REPLYS_REPLYS.content ,SM_USERS.nick_name from " +
                                "COODS_REPLYS_REPLYS left join SM_USERS on(COODS_REPLYS_REPLYS.user_id=SM_USERS.user_id)" +
                                "where COODS_REPLYS_REPLYS.good_reply_id=?",
                        new String[]{replyId+""});
                while (cursor.moveToNext()){
                   GoodReply2Reply goodReply2Reply=new GoodReply2Reply();
                   goodReply2Reply.setId(cursor.getInt(0));
                   goodReply2Reply.setUserId(cursor.getInt(1));
                   goodReply2Reply.setGood_reply_id(cursor.getInt(2));
                   goodReply2Reply.setReply_user_name(cursor.getString(3));
                   goodReply2Reply.setContent(cursor.getString(4));
                   goodReply2Reply.setUser_nick_name(cursor.getString(5));
                   list.add(goodReply2Reply);
                }
                return list;
            } catch (Exception e) {
                e.printStackTrace();
                return list;
            }finally {
                if(db!=null)
                    db.endTransaction();
                if (cursor != null)
                    cursor.close();
                if (db != null)
                    db.close();
            }
        }
    }
    public boolean isCollect(int good_id,int user_id){
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return false;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select * from COLLECTIONS where user_id = ? and good_id = ?",
                        new String[]{user_id+"",good_id+""});
                if(!cursor.moveToFirst()){//不存在
                  return false;
                }else {
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
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
    public boolean isLiked(int trend_id,int user_id){
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return false;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select * from LIKES where user_id = ? and trend_id = ?",
                        new String[]{user_id+"",trend_id+""});
                if(!cursor.moveToFirst()){//不存在
                    return false;
                }else {
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return false;
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
    public void goodReply(int good_id,String content,int user_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                db.execSQL(
                        " insert into GOODS_REPLYS(user_id,good_id,content,publishTime) " +
                                "values (?,?,?,?)",
                        new Object[]{
                                user_id,
                                good_id,
                                content,
                                MyUtil.getNowLongTime(),
                                });
                db.execSQL("update GOODS set reply_num = reply_num+1 where id = ?",
                        new Object[]{good_id});
                db.setTransactionSuccessful();
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
            listener.onOK(null);
        }
    }
    public void goodReply2Reply(int reply_id,String content,int user_id,String reply_name,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                db.execSQL(
                        " insert into COODS_REPLYS_REPLYS(user_id,good_reply_id,content,reply_user_name) " +
                                "values (?,?,?,?)",
                        new Object[]{
                                user_id,
                                reply_id,
                                content,
                                reply_name,
                        });
                db.setTransactionSuccessful();
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
            listener.onOK(null);
        }
    }

    public void trendReply(int trend_id,String content,int user_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                db.execSQL(
                        " insert into TRENDS_REPLYS(user_id,trend_id,content,publishTime) " +
                                "values (?,?,?,?)",
                        new Object[]{
                                user_id,
                                trend_id,
                                content,
                                MyUtil.getNowLongTime(),
                        });
                db.execSQL("update TRENDS set reply_num = reply_num+1 where id = ?",
                        new Object[]{trend_id});
                db.setTransactionSuccessful();
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
            listener.onOK(null);
        }
    }
    public void trendReply2Reply(int reply_id,String content,int user_id,String reply_name,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                db.execSQL(
                        " insert into TRENDS_REPLYS_REPLYS(user_id,trend_reply_id,content,reply_user_name) " +
                                "values (?,?,?,?)",
                        new Object[]{
                                user_id,
                                reply_id,
                                content,
                                reply_name,
                        });
                db.setTransactionSuccessful();
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
            listener.onOK(null);
        }
    }

    public void collect(int good_id,int user_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select * from COLLECTIONS where user_id= ? and good_id = ?",
                        new String[]{user_id+"",good_id+""});
                if(cursor.moveToFirst()){//存在
                   db.execSQL("delete from COLLECTIONS where user_id=? and good_id=? ",
                           new Object[]{user_id,good_id});
                    db.execSQL("update GOODS set collect_num = collect_num-1 where id = ?",
                            new Object[]{good_id});
                   message="取消收藏成功";
                   listener.onOK(message);
                }else {
                    db.execSQL(
                            " insert into COLLECTIONS(user_id,good_id) " +
                                    "values (?,?)",
                            new Object[]{
                                    user_id,
                                    good_id,
                            });
                    db.execSQL("update GOODS set collect_num = collect_num+1 where id = ?",
                            new Object[]{good_id});
                    message="收藏成功";
                    listener.onOK(message);
                }

                db.setTransactionSuccessful();
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
    public void like(int trend_id,int user_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select * from LIKES where user_id= ? and trend_id = ?",
                        new String[]{user_id+"",trend_id+""});
                if(cursor.moveToFirst()){//存在
                    db.execSQL("delete from LIKES where user_id=? and trend_id=? ",
                            new Object[]{user_id,trend_id});
                    db.execSQL("update TRENDS set like_num = like_num-1 where id = ?",
                            new Object[]{trend_id});
                    message="取消点赞成功";
                    listener.onOK(message);
                }else {
                    db.execSQL(
                            " insert into LIKES(user_id,trend_id) " +
                                    "values (?,?)",
                            new Object[]{
                                    user_id,
                                    trend_id,
                            });
                    db.execSQL("update TRENDS set like_num = like_num+1 where id = ?",
                            new Object[]{trend_id});
                    message="点赞成功";
                    listener.onOK(message);
                }

                db.setTransactionSuccessful();
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

    public void findMyCollectGoods(int pageIndex,int user_id,CallBackListener listener){
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
                        "select GOODS.id,GOODS.price,GOODS.title,GOODS.images,GOODS.reply_num ,GOODS.collect_num ,GOODS.publishTime,GOODS.read_num ,SM_USERS.head_img,SM_USERS.nick_name from COLLECTIONS left join GOODS on(COLLECTIONS.good_id=GOODS.id) left join SM_USERS on (GOODS.user_id=SM_USERS.user_id)  where GOODS.is_deal = ? and  COLLECTIONS.user_id = ? order by COLLECTIONS.id desc limit 10 offset ? ",
                        new String[]{"false",user_id+"",pageIndex*10+""});
                while (cursor.moveToNext()){
                    Good good=new Good();
                    good.setId(cursor.getInt(0));
                    good.setPrice(cursor.getString(1));
                    good.setTitle(cursor.getString(2));
                    good.setImages(cursor.getString(3));
                    good.setReply_num(cursor.getInt(4));
                    good.setCollect_num(cursor.getInt(5));
                    good.setPublishTime(MyUtil.getMsgTime(cursor.getLong(6)));
                    good.setRead_num(cursor.getInt(7));
                    good.setUser_head_img(cursor.getString(8));
                    good.setUser_nick_name(cursor.getString(9));
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

    public void getMyTrendsList(int userID,int pageIndex,CallBackListener listener) {
        List<Trend> trendList = new ArrayList<>();
        synchronized (_writeLock) {
            String message = "";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return;

                db.beginTransaction();
                cursor = db.rawQuery(
                        "select TRENDS.id,TRENDS.describe,TRENDS.images,TRENDS.reply_num,TRENDS.like_num ,TRENDS.publishTime,TRENDS.read_num ,SM_USERS.head_img,SM_USERS.nick_name,SM_USERS.sex from TRENDS left join SM_USERS on (TRENDS.user_id=SM_USERS.user_id)  where TRENDS.user_id = ?  order by id desc limit 10 offset ? ",
                        new String[]{userID + "", pageIndex*10+""});
                while (cursor.moveToNext()) {
                    Trend trend = new Trend();
                    trend.setId(cursor.getInt(0));
                    trend.setDescribe(cursor.getString(1));
                    trend.setImages(cursor.getString(2));
                    trend.setReply_num(cursor.getInt(3));
                    trend.setLike_num(cursor.getInt(4));
                    trend.setPublishTime(MyUtil.getMsgTime(cursor.getLong(5)));
                    trend.setRead_num(cursor.getInt(6));
                    trend.setUser_head_img(cursor.getString(7));
                    trend.setUser_nick_name(cursor.getString(8));
                    String sex = cursor.getString(9);
                    trend.setUser_sex(MyUtil.parseBoolean(sex));
                    trendList.add(trend);
                }
                listener.onOK(trendList);

            } catch (Exception e) {
                e.printStackTrace();
                message = "发生错误";
                listener.onError(message);
            } finally {
                if (db != null)
                    db.endTransaction();
                if (cursor != null)
                    cursor.close();
                if (db != null)
                    db.close();
            }
        }
    }
    public void getTrendsList(int pageIndex,CallBackListener listener) {
        List<Trend> trendList = new ArrayList<>();
        synchronized (_writeLock) {
            String message = "";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return;

                db.beginTransaction();
                cursor = db.rawQuery(
                        "select TRENDS.id,TRENDS.describe,TRENDS.images,TRENDS.reply_num,TRENDS.like_num ,TRENDS.publishTime,TRENDS.read_num ,SM_USERS.head_img,SM_USERS.nick_name,SM_USERS.sex from TRENDS left join SM_USERS on (TRENDS.user_id=SM_USERS.user_id)  order by id desc limit 10 offset ? ",
                        new String[]{pageIndex*10+""});
                while (cursor.moveToNext()) {
                    Trend trend = new Trend();
                    trend.setId(cursor.getInt(0));
                    trend.setDescribe(cursor.getString(1));
                    trend.setImages(cursor.getString(2));
                    trend.setReply_num(cursor.getInt(3));
                    trend.setLike_num(cursor.getInt(4));
                    trend.setPublishTime(MyUtil.getMsgTime(cursor.getLong(5)));
                    trend.setRead_num(cursor.getInt(6));
                    trend.setUser_head_img(cursor.getString(7));
                    trend.setUser_nick_name(cursor.getString(8));
                    String sex = cursor.getString(9);
                    trend.setUser_sex(MyUtil.parseBoolean(sex));
                    trendList.add(trend);
                }
                listener.onOK(trendList);

            } catch (Exception e) {
                e.printStackTrace();
                message = "发生错误";
                listener.onError(message);
            } finally {
                if (db != null)
                    db.endTransaction();
                if (cursor != null)
                    cursor.close();
                if (db != null)
                    db.close();
            }
        }
    }
    public void releaseTrend(Trend trend,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)
                    return;
                db.beginTransaction();
                db.execSQL(
                        " insert into TRENDS(user_id,describe,images,reply_num,like_num,read_num,publishTime) " +
                                "values (?,?,?,?,?,?,?)",
                        new Object[]{
                                trend.getUser_id(),
                                trend.getDescribe(),
                                trend.getImages(),
                                0,
                                0,
                                0,
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

    public void removeTrend(int trend_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message="";
            SQLiteDatabase db = null;
            try {
                db = this.getWritableDatabase();
                if(db==null)return;
                db.beginTransaction();
                db.execSQL(
                        " delete from TRENDS  where id = ?",
                        new Object[]{
                                trend_id
                        });
                db.setTransactionSuccessful();
                listener.onOK(null);
            } catch (Exception e) {
                e.printStackTrace();
                message="删除失败";
                listener.onError(message);
            } finally {
                if(db!=null) {
                    db.endTransaction();
                    db.close();
                }
            }
        }
    }
    public void findTrendDetail (int trend_id,CallBackListener listener){
        synchronized (_writeLock) {
            String message = "";
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select TRENDS.id,TRENDS.describe,TRENDS.images,TRENDS.reply_num,TRENDS.like_num ,TRENDS.publishTime,TRENDS.read_num ,SM_USERS.head_img,SM_USERS.nick_name,SM_USERS.sex from TRENDS left join SM_USERS on (TRENDS.user_id=SM_USERS.user_id) where TRENDS.id = ? ",
                        new String[]{trend_id+""});
                Trend trend=new Trend();
                if(!cursor.moveToFirst()){
                    message="查询的商品已经删除或下架";
                    listener.onError(message);
                }
                trend.setId(cursor.getInt(0));
                trend.setDescribe(cursor.getString(1));
                trend.setImages(cursor.getString(2));
                trend.setReply_num(cursor.getInt(3));
                trend.setLike_num(cursor.getInt(4));
                trend.setPublishTime(MyUtil.getMsgTime(cursor.getLong(5)));
                trend.setRead_num(cursor.getInt(6)+1);
                trend.setUser_head_img(cursor.getString(7));
                trend.setUser_nick_name(cursor.getString(8));
                String sex = cursor.getString(9);
                trend.setUser_sex(MyUtil.parseBoolean(sex));
                db.execSQL("update TRENDS set read_num = read_num+1 where id = ?",
                        new Object[]{trend_id});
                db.setTransactionSuccessful();
                listener.onOK(trend);
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

    public List<TrendReply> findTrendReplys(int trend_id){
        List<TrendReply> list=new ArrayList<>();
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return list;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select TRENDS_REPLYS.id,TRENDS_REPLYS.user_id,TRENDS_REPLYS.trend_id," +
                                "TRENDS_REPLYS.content,TRENDS_REPLYS.publishTime,SM_USERS.nick_name," +
                                "SM_USERS.head_img,SM_USERS.sex from " +
                                "TRENDS_REPLYS left join SM_USERS on(TRENDS_REPLYS.user_id=SM_USERS.user_id)" +
                                "where TRENDS_REPLYS.trend_id=? ",
                        new String[]{trend_id+""});
                while (cursor.moveToNext()){
                    TrendReply trendReply=new TrendReply();
                    trendReply.setId(cursor.getInt(0));
                    trendReply.setUserId(cursor.getInt(1));
                    trendReply.setGoodId(cursor.getInt(2));
                    trendReply.setContent(cursor.getString(3));
                    trendReply.setReply_time(MyUtil.getMsgTime(cursor.getLong(4)));
                    trendReply.setUser_nick_name(cursor.getString(5));
                    trendReply.setUser_head_img(cursor.getString(6));
                    String sex=cursor.getString(7);
                    trendReply.setUser_sex(MyUtil.parseBoolean(sex));
                    list.add(trendReply);
                }
                return list;

            } catch (Exception e) {
                e.printStackTrace();
                return list;
            }finally {
                if(db!=null)
                    db.endTransaction();
                if (cursor != null)
                    cursor.close();
                if (db != null)
                    db.close();
            }
        }
    }
    public List<TrendReply2Reply> findTrendReply2Replys(int replyId){
        List<TrendReply2Reply> list=new ArrayList<>();
        synchronized (_writeLock) {
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = this.getWritableDatabase();
                if (db == null) return list;
                db.beginTransaction();
                cursor = db.rawQuery(
                        "select TRENDS_REPLYS_REPLYS.id,TRENDS_REPLYS_REPLYS.user_id,TRENDS_REPLYS_REPLYS.trend_reply_id," +
                                "TRENDS_REPLYS_REPLYS.reply_user_name,TRENDS_REPLYS_REPLYS.content ,SM_USERS.nick_name from " +
                                "TRENDS_REPLYS_REPLYS left join SM_USERS on(TRENDS_REPLYS_REPLYS.user_id=SM_USERS.user_id)" +
                                "where TRENDS_REPLYS_REPLYS.trend_reply_id=?",
                        new String[]{replyId+""});
                while (cursor.moveToNext()){
                    TrendReply2Reply trendReply2Reply=new TrendReply2Reply();
                    trendReply2Reply.setId(cursor.getInt(0));
                    trendReply2Reply.setUserId(cursor.getInt(1));
                    trendReply2Reply.setTrend_reply_id(cursor.getInt(2));
                    trendReply2Reply.setReply_user_name(cursor.getString(3));
                    trendReply2Reply.setContent(cursor.getString(4));
                    trendReply2Reply.setUser_nick_name(cursor.getString(5));
                    list.add(trendReply2Reply);
                }
                return list;
            } catch (Exception e) {
                e.printStackTrace();
                return list;
            }finally {
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
