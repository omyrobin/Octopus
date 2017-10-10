package com.jinying.octopus;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.jinying.octopus.bean.UserBean;
import com.jinying.octopus.user.state.BindState;
import com.jinying.octopus.user.state.TouristState;
import com.jinying.octopus.user.state.UserState;
import com.jinying.octopus.util.FileUtil;
import com.jinying.octopus.util.Logger;
import com.jinying.octopus.util.ScreenUtil;

/**
 * Created by omyrobin on 2017/8/15.
 */

public class App extends Application {

    private static volatile App app;

//    private static DaoSession daoSession;

    public static float width;

    public static float height;

    private UserBean userBean;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initScreen();
        FileUtil.instance(this);
    }

    private void initScreen(){
        width = ScreenUtil.getScreenWidth(this);
        height = ScreenUtil.getScreenHeight(this);
    }

    public static App getInstance(){
        return app;
    }

    /**
     * 配置数据库
     */
//    private void setupDatabase() {
//        //创建数据库shop.db"
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
//        //获取可写数据库
//        SQLiteDatabase db = helper.getWritableDatabase();
//        //获取数据库对象
//        DaoMaster daoMaster = new DaoMaster(db);
//        //获取Dao对象管理者
//        daoSession = daoMaster.newSession();
//    }
//
//    public static DaoSession getDaoInstant() {
//        return daoSession;
//    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
        if(userBean!=null){
            if(userBean.getIsBinding() == 1){
                Logger.i("当前属于Bind状态");
                UserState.getInstance().setState(new BindState());
            }else{
                Logger.i("当前属于游客状态");
                UserState.getInstance().setState(new TouristState());
            }
        }
    }
}
