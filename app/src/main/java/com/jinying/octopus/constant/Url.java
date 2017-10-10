package com.jinying.octopus.constant;

/**
 * Created by omyrobin on 2017/8/21.
 */

public class Url {
    public static final String BASE_URL = "http://app.octopus.hotread.com/";

    public static final String USER_REGISTER = "m1/user/register/guest";//新用户注册游客账号
    public static final String USER_AUTOLOGIN = "m1/user/autoLogin";//自动登陆
    public static final String USER_PHONELOGIN = "m1/user/login";//手机号登陆
    public static final String USER_PHONEREGISTER = "m1/user/register";//已是手机用户注册新账号
    public static final String USER_BIND = "m1/user/binding";//游客绑定手机
    public static final String USER_THIRDLOGIN = "m1/user/thirdLogin";//第三方登陆
    public static final String GET_BANNER = "m1/banner/list";//获取Banner
    public static final String GET_BOOKLIST = "m1/shelf/myStories";//获取书架图书列表
    public static final String GET_SEARCH_KEYWORDS = "m1/search/keywords";//大家都在搜
    public static final String GET_SEARCH_BYWORD = "m1/search/searchByWord";//APP关键字查询书籍
    public static final String GET_SEARCH_NAMES = "m1/search/searchNames";//模糊查询
    public static final String GET_STORYVO = "m1/story/get";//获取小说详情
    public static final String GET_CHAPTERS = "m1/storyChapter/getChapters";//获取小说目录
    public static final String GET_STORYRELATION = "m1/recommend/relation";//获取相关推荐
    public static final String GET_LISTPAGE = "m1/story/listpage";
    public static final String GET_FREELISTPAGE = " m1/ranking/freeListPage";
    public static final String GET_TYPEN = "m1/recommendtag/getOne";//火星基地标签
    public static final String TYPEN_SEARCH = "m1/recommendtag/search";
    public static final String USER_SMS_RB_CODE = "m1/sms/code/register";//发送注册短信验证码(用户注册、绑定用户等)
    public static final String GET_CHAPTER_CONTENT = "m1/storyChapter/content";//获取小说正文内容
    public static final String ADD_MYSHELF = "m1/shelf/add";//加入书架
    public static final String REMOVE_MYSHELF = "m1/shelf/remove";//删除图书
    public static final String SVAE_READING = "m1/storyChapter/saveReading";//保存阅读记录
    public static final String REQ_WEIXIN = "https://api.weixin.qq.com/sns/oauth2/access_token?";//获取openID
    public static final String WEIXIN_USERINFO = "https://api.weixin.qq.com/sns/userinfo?";//获取微信用户个人信息
    public static final String ADD_FEEDBACK = "m1/feedback/add";//意见反馈

    public static final String BOOKMALL_URL = "https://wap.octopus.hotread.com/freestory/book";//书城
    public static final String CLASSIFY_URL = "https://wap.octopus.hotread.com/freestory/type";//分类
    public static final String RANKING_URL = "https://wap.octopus.hotread.com/freestory/rank";//排行
    public static final String ABOUT_URL = "https://wap.octopus.hotread.com/freestory/about";//关于

}
