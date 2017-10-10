package com.jinying.octopus.bean;

/**
 * Created by omyrobin on 2017/8/24.
 */

public class StoryListBean {

    /**
     * id : 4398
     * novelColumn : 7402
     * updateTime : 1502959268179
     * updateAdminId : 1
     * updateAdminName : 超级管理员
     * pic :
     * topNum : 1
     * beginTime : 1502899200000
     * endTime : 1818345599999
     * storyId : 1000076
     * story : {"id":1000076,"userId":0,"name":"公主不相思2","author":"董珊","cover":"http://p2.duyao001.com/image/test/711cf2f40ef71b3a65dc5493eb3b433d_147x215.jpg","channel":"2","type":"古代言情","resourceType":"","tag":"架空","isPay":1,"price":315,"wordNumber":62022,"recommendTicket":1066,"monthTicket":0,"state":"2","isDelete":0,"lastChapterId":1000001287,"latestChapter":"第十章　 雪什么时候停","latestChapterTime":1459440000000,"createDate":1501556271000,"latestRevisionDate":1503507928855,"appIntroduce":"","number":"534100","source":"huayu","sourceParams":"28928","isDown":0,"fireValue":9602,"commentable":1,"monthable":1,"introduce":"　　从第一眼看到他，她就开始厌恶他，推他、骂他、陷害他，看他受伤，得意洋洋而幸灾乐祸。他总是那样孤傲，对应她的唯一表情就是冷漠。他聪明、出众，愈得父母的喜爱，她愈看他不顺眼。她是家里的掌上明珠，众人娇宠的公主。他是领养的孤儿，无依无靠，寄人篱下。他与她，是兄妹，又是冥冥之中前生后世难解的纠缠。爱恨情仇，只一晃眼间，便烙下此生最轻狂、无悔的一点光阴。 \n　　","pcIntroduce":"　　从第一眼看到他，她就开始厌恶他，推他、骂他、陷害他，看他受伤，得意洋洋而幸灾乐祸。他总是那样孤傲，对应她的唯一表情就是冷漠。他聪明、出众，愈得父母的喜爱，她愈看他不顺眼。她是家里的掌上明珠，众人娇宠的公主。他是领养的孤儿，无依无靠，寄人篱下。他与她，是兄妹，又是冥冥之中前生后世难解的纠缠。爱恨情仇，只一晃眼间，便烙下此生最轻狂、无悔的一点光阴。 \n　　"}
     * remark : 现代都市
     * appIntroduceEditor : null
     * copyrightStatus : null
     * copyrightType : null
     * createTime : 1502956603419
     */

    private String id;
    private int novelColumn;
    private long updateTime;
    private int updateAdminId;
    private String updateAdminName;
    private String pic;
    private int topNum;
    private long beginTime;
    private long endTime;
    private String storyId;
    private StoryBean story;
    private String remark;
    private Object appIntroduceEditor;
    private Object copyrightStatus;
    private Object copyrightType;
    private long createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNovelColumn() {
        return novelColumn;
    }

    public void setNovelColumn(int novelColumn) {
        this.novelColumn = novelColumn;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateAdminId() {
        return updateAdminId;
    }

    public void setUpdateAdminId(int updateAdminId) {
        this.updateAdminId = updateAdminId;
    }

    public String getUpdateAdminName() {
        return updateAdminName;
    }

    public void setUpdateAdminName(String updateAdminName) {
        this.updateAdminName = updateAdminName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getTopNum() {
        return topNum;
    }

    public void setTopNum(int topNum) {
        this.topNum = topNum;
    }

    public long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(long beginTime) {
        this.beginTime = beginTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public StoryBean getStory() {
        return story;
    }

    public void setStory(StoryBean story) {
        this.story = story;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Object getAppIntroduceEditor() {
        return appIntroduceEditor;
    }

    public void setAppIntroduceEditor(Object appIntroduceEditor) {
        this.appIntroduceEditor = appIntroduceEditor;
    }

    public Object getCopyrightStatus() {
        return copyrightStatus;
    }

    public void setCopyrightStatus(Object copyrightStatus) {
        this.copyrightStatus = copyrightStatus;
    }

    public Object getCopyrightType() {
        return copyrightType;
    }

    public void setCopyrightType(Object copyrightType) {
        this.copyrightType = copyrightType;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public static class StoryBean {
        /**
         * id : 1000076
         * userId : 0
         * name : 公主不相思2
         * author : 董珊
         * cover : http://p2.duyao001.com/image/test/711cf2f40ef71b3a65dc5493eb3b433d_147x215.jpg
         * channel : 2
         * type : 古代言情
         * resourceType :
         * tag : 架空
         * isPay : 1
         * price : 315
         * wordNumber : 62022
         * recommendTicket : 1066
         * monthTicket : 0
         * state : 2
         * isDelete : 0
         * lastChapterId : 1000001287
         * latestChapter : 第十章　 雪什么时候停
         * latestChapterTime : 1459440000000
         * createDate : 1501556271000
         * latestRevisionDate : 1503507928855
         * appIntroduce :
         * number : 534100
         * source : huayu
         * sourceParams : 28928
         * isDown : 0
         * fireValue : 9602
         * commentable : 1
         * monthable : 1
         * introduce : 　　从第一眼看到他，她就开始厌恶他，推他、骂他、陷害他，看他受伤，得意洋洋而幸灾乐祸。他总是那样孤傲，对应她的唯一表情就是冷漠。他聪明、出众，愈得父母的喜爱，她愈看他不顺眼。她是家里的掌上明珠，众人娇宠的公主。他是领养的孤儿，无依无靠，寄人篱下。他与她，是兄妹，又是冥冥之中前生后世难解的纠缠。爱恨情仇，只一晃眼间，便烙下此生最轻狂、无悔的一点光阴。

         * pcIntroduce : 　　从第一眼看到他，她就开始厌恶他，推他、骂他、陷害他，看他受伤，得意洋洋而幸灾乐祸。他总是那样孤傲，对应她的唯一表情就是冷漠。他聪明、出众，愈得父母的喜爱，她愈看他不顺眼。她是家里的掌上明珠，众人娇宠的公主。他是领养的孤儿，无依无靠，寄人篱下。他与她，是兄妹，又是冥冥之中前生后世难解的纠缠。爱恨情仇，只一晃眼间，便烙下此生最轻狂、无悔的一点光阴。

         */

        private long id;
        private int userId;
        private String name;
        private String author;
        private String cover;
        private String channel;
        private String type;
        private String resourceType;
        private String tag;
        private int isPay;
        private int price;
        private int wordNumber;
        private int recommendTicket;
        private int monthTicket;
        private String state;
        private int isDelete;
        private int lastChapterId;
        private String latestChapter;
        private long latestChapterTime;
        private long createDate;
        private long latestRevisionDate;
        private String appIntroduce;
        private String number;
        private String source;
        private String sourceParams;
        private int isDown;
        private int fireValue;
        private int commentable;
        private int monthable;
        private String introduce;
        private String pcIntroduce;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getWordNumber() {
            return wordNumber;
        }

        public void setWordNumber(int wordNumber) {
            this.wordNumber = wordNumber;
        }

        public int getRecommendTicket() {
            return recommendTicket;
        }

        public void setRecommendTicket(int recommendTicket) {
            this.recommendTicket = recommendTicket;
        }

        public int getMonthTicket() {
            return monthTicket;
        }

        public void setMonthTicket(int monthTicket) {
            this.monthTicket = monthTicket;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(int isDelete) {
            this.isDelete = isDelete;
        }

        public int getLastChapterId() {
            return lastChapterId;
        }

        public void setLastChapterId(int lastChapterId) {
            this.lastChapterId = lastChapterId;
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

        public long getLatestRevisionDate() {
            return latestRevisionDate;
        }

        public void setLatestRevisionDate(long latestRevisionDate) {
            this.latestRevisionDate = latestRevisionDate;
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

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSourceParams() {
            return sourceParams;
        }

        public void setSourceParams(String sourceParams) {
            this.sourceParams = sourceParams;
        }

        public int getIsDown() {
            return isDown;
        }

        public void setIsDown(int isDown) {
            this.isDown = isDown;
        }

        public int getFireValue() {
            return fireValue;
        }

        public void setFireValue(int fireValue) {
            this.fireValue = fireValue;
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

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getPcIntroduce() {
            return pcIntroduce;
        }

        public void setPcIntroduce(String pcIntroduce) {
            this.pcIntroduce = pcIntroduce;
        }
    }
}
