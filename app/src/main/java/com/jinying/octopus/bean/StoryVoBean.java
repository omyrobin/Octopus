package com.jinying.octopus.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by omyrobin on 2017/8/21.
 */
public class StoryVoBean implements Serializable{

    /**
     * id : 1000548
     * name : 慕水千城
     * authorId : 1000000157
     * authorName : 瑾年
     * cover : https://p0.duyao001.com/image/article/f1c32c087774c2ba2ae9b4d31db1326f_450x590.png
     * channel : 2
     * introduce : 新婚之夜，江慕水惨遭绑架，还失节！
     自此两年婚姻，如入风雨飘摇之境，丈夫夜不归宿，绯闻不断，江慕水对此隐忍不发。
     终于有一天，一个女人挺着肚子找上门来……
     ***
     殷千城第一次见江慕水这个女人就觉得熟悉，她锁骨之下的那枚褐色的小痣性感无比。
     他问：“我跟江律师是否在哪儿见过？”
     “谈判席上吧，”江慕水毫不客气地回应，“我的手下败将都对我印象深刻！”
     人前精明干练，人后脆弱不堪，殷千城目睹这仿若有千面的小女人，愈发觉得探索欲旺盛。
     终于一场轰动全城的绯闻，将男女主角推上风口浪尖。
     江慕水急了，恳求道：“殷先生，请您跟记者解释，您借工作之便对我情愫暗生，跟我暗度陈仓，这都是误会！”
     “什么误会？”他浅笑，“后一句是假的还没到时候，可你又怎么知道，我对你没有图谋不轨？”
     他的意有所指，太明显了！
     ***
     以你之水，绕我千城，慕水，于你来说我是一场遭遇，于我来说，你却是一场耗尽我所有思念的，久别重逢。
     * type : 现代言情
     * resourceType :
     * tag : 总裁,虐恋,商战
     * isPay : 1
     * wordNumber : 9268
     * state : 1
     * latestChapter : 第三百三十一章 终于明白，有些矛盾无从解决
     * latestChapterTime : 1492992000000
     * createDate : 1463039497155
     * lastChapterId : 1000358860
     * appIntroduce : 丈夫夜不归宿，绯闻不断，她隐忍不发。直到那天，一个女人挺着肚子找上门来……

     * number : 1000548
     * fireValue : 3301338
     * pcIntroduce : 婚后，遇到最会撩人的总裁，约吗？？
     * commentable : 1
     * monthable : 1
     * doubleMonthTicket : false
     * limitFreeStory : false
     * limitFreeLastTime : null
     * limitFreeEndTime : null
     * isDown : 0
     * pv : 0
     * latesetChapterTimeStr : 2017-04-24 08:00:00
     * latestChapterHour : 2888
     * onShelf : false
     * smallCover : https://p0.duyao001.com/image/article/f1c32c087774c2ba2ae9b4d31db1326f_450x590.png
     * createDateStr : 2016-05-12 15:51:37
     * tagList : ["总裁","虐恋","商战"]
     */
    private Long _id;

    private String id;
    private String name;
    private String authorId;
    private String authorName;
    private String cover;
    private String channel;
    private String introduce;
    private String type;
    private String resourceType;
    private String tag;
    private int isPay;
    private int wordNumber;
    private String state;
    private String latestChapter;
    private long latestChapterTime;
    private long createDate;
    private String lastChapterId;
    private String appIntroduce;
    private String number;
    private int fireValue;
    private String pcIntroduce;
    private int commentable;
    private int monthable;
    private boolean doubleMonthTicket;
    private boolean limitFreeStory;
    private String limitFreeLastTime;
    private String limitFreeEndTime;
    private int isDown;
    private int pv;
    private String latesetChapterTimeStr;
    private int latestChapterHour;
    private boolean onShelf;
    private String smallCover;
    private String createDateStr;
    private List<String> tagList;

    //扩充字段
    private boolean isChecked;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public int getWordNumber() {
        return wordNumber;
    }

    public void setWordNumber(int wordNumber) {
        this.wordNumber = wordNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatestChapter() {
        return latestChapter;
    }

    public void setLatestChapter(String latestChapter) {
        this.latestChapter = latestChapter;
    }

    public long getLatestChapterTime() {
        return latestChapterTime;
    }

    public void setLatestChapterTime(long latestChapterTime) {
        this.latestChapterTime = latestChapterTime;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getLastChapterId() {
        return lastChapterId;
    }

    public void setLastChapterId(String lastChapterId) {
        this.lastChapterId = lastChapterId;
    }

    public String getAppIntroduce() {
        return appIntroduce;
    }

    public void setAppIntroduce(String appIntroduce) {
        this.appIntroduce = appIntroduce;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getFireValue() {
        return fireValue;
    }

    public void setFireValue(int fireValue) {
        this.fireValue = fireValue;
    }

    public String getPcIntroduce() {
        return pcIntroduce;
    }

    public void setPcIntroduce(String pcIntroduce) {
        this.pcIntroduce = pcIntroduce;
    }

    public int getCommentable() {
        return commentable;
    }

    public void setCommentable(int commentable) {
        this.commentable = commentable;
    }

    public int getMonthable() {
        return monthable;
    }

    public void setMonthable(int monthable) {
        this.monthable = monthable;
    }

    public boolean isDoubleMonthTicket() {
        return doubleMonthTicket;
    }

    public void setDoubleMonthTicket(boolean doubleMonthTicket) {
        this.doubleMonthTicket = doubleMonthTicket;
    }

    public boolean isLimitFreeStory() {
        return limitFreeStory;
    }

    public void setLimitFreeStory(boolean limitFreeStory) {
        this.limitFreeStory = limitFreeStory;
    }

    public String getLimitFreeLastTime() {
        return limitFreeLastTime;
    }

    public void setLimitFreeLastTime(String limitFreeLastTime) {
        this.limitFreeLastTime = limitFreeLastTime;
    }

    public String getLimitFreeEndTime() {
        return limitFreeEndTime;
    }

    public void setLimitFreeEndTime(String limitFreeEndTime) {
        this.limitFreeEndTime = limitFreeEndTime;
    }

    public int getIsDown() {
        return isDown;
    }

    public void setIsDown(int isDown) {
        this.isDown = isDown;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getLatesetChapterTimeStr() {
        return latesetChapterTimeStr;
    }

    public void setLatesetChapterTimeStr(String latesetChapterTimeStr) {
        this.latesetChapterTimeStr = latesetChapterTimeStr;
    }

    public int getLatestChapterHour() {
        return latestChapterHour;
    }

    public void setLatestChapterHour(int latestChapterHour) {
        this.latestChapterHour = latestChapterHour;
    }

    public boolean isOnShelf() {
        return onShelf;
    }

    public void setOnShelf(boolean onShelf) {
        this.onShelf = onShelf;
    }

    public String getSmallCover() {
        return smallCover;
    }

    public void setSmallCover(String smallCover) {
        this.smallCover = smallCover;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if(o!=null){
            return o.hashCode() == this.hashCode();
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {

        return id.hashCode();
    }
}
