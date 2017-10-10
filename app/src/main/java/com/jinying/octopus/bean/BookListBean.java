package com.jinying.octopus.bean;

import java.util.List;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class BookListBean {

    /**
     * pageNo : 0
     * pageSize : 10
     * sort : DESC_latestChapterTime
     * total : 59
     * totalPages : 6
     * list : [{"id":"1107280","flag":null,"userId":"1000001866","name":"十二生肖大战十三香","author":"华谊聚星","cover":"https://c.hotread.com/assets/images/default-cover-boy.jpg","channel":"1","introduce":"","type":"玄幻仙侠","resourceType":"","tag":"奇幻,玄幻,其他","isPay":0,"price":774,"wordNumber":150418,"recommentTicket":0,"monthTicket":0,"sstate":"2","isDel":0,"latestChapter":"第三十章","latestChapterTime":1494307284446,"createDate":1494307235835,"latestRevisionDate":1495002277000,"lastChapterId":1000371799,"recommendIntroduce":"","source":"huayi","sourceParams":"","fireValue":1000,"pcIntroduce":"","isDown":0,"commentable":1,"monthable":0,"pv":0,"number":"1107280","valid":true,"idStr":"1107280","smallCover":"https://c.hotread.com/assets/images/default-cover-boy.jpg","allowPay":false}]
     * title : 完本
     * systemTime : null
     */

    private int pageNo;
    private int pageSize;
    private String sort;
    private int total;
    private int totalPages;
    private String title;
    private Object systemTime;
    private List<ListBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Object systemTime) {
        this.systemTime = systemTime;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1107280
         * flag : null
         * userId : 1000001866
         * name : 十二生肖大战十三香
         * author : 华谊聚星
         * cover : https://c.hotread.com/assets/images/default-cover-boy.jpg
         * channel : 1
         * introduce :
         * type : 玄幻仙侠
         * resourceType :
         * tag : 奇幻,玄幻,其他
         * isPay : 0
         * price : 774
         * wordNumber : 150418
         * recommentTicket : 0
         * monthTicket : 0
         * state : 2
         * isDel : 0
         * latestChapter : 第三十章
         * latestChapterTime : 1494307284446
         * createDate : 1494307235835
         * latestRevisionDate : 1495002277000
         * lastChapterId : 1000371799
         * recommendIntroduce :
         * source : huayi
         * sourceParams :
         * fireValue : 1000
         * pcIntroduce :
         * isDown : 0
         * commentable : 1
         * monthable : 0
         * pv : 0
         * number : 1107280
         * valid : true
         * idStr : 1107280
         * smallCover : https://c.hotread.com/assets/images/default-cover-boy.jpg
         * allowPay : false
         */

        private String id;
        private Object flag;
        private String userId;
        private String name;
        private String author;
        private String cover;
        private String channel;
        private String introduce;
        private String type;
        private String resourceType;
        private String tag;
        private int isPay;
        private int price;
        private int wordNumber;
        private int recommentTicket;
        private int monthTicket;
        private String state;
        private int isDel;
        private String latestChapter;
        private long latestChapterTime;
        private long createDate;
        private long latestRevisionDate;
        private int lastChapterId;
        private String recommendIntroduce;
        private String source;
        private String sourceParams;
        private int fireValue;
        private String pcIntroduce;
        private int isDown;
        private int commentable;
        private int monthable;
        private int pv;
        private String number;
        private boolean valid;
        private String idStr;
        private String smallCover;
        private boolean allowPay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getFlag() {
            return flag;
        }

        public void setFlag(Object flag) {
            this.flag = flag;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
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

        public int getRecommentTicket() {
            return recommentTicket;
        }

        public void setRecommentTicket(int recommentTicket) {
            this.recommentTicket = recommentTicket;
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

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
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

        public int getLastChapterId() {
            return lastChapterId;
        }

        public void setLastChapterId(int lastChapterId) {
            this.lastChapterId = lastChapterId;
        }

        public String getRecommendIntroduce() {
            return recommendIntroduce;
        }

        public void setRecommendIntroduce(String recommendIntroduce) {
            this.recommendIntroduce = recommendIntroduce;
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

        public int getIsDown() {
            return isDown;
        }

        public void setIsDown(int isDown) {
            this.isDown = isDown;
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

        public int getPv() {
            return pv;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }

        public String getIdStr() {
            return idStr;
        }

        public void setIdStr(String idStr) {
            this.idStr = idStr;
        }

        public String getSmallCover() {
            return smallCover;
        }

        public void setSmallCover(String smallCover) {
            this.smallCover = smallCover;
        }

        public boolean isAllowPay() {
            return allowPay;
        }

        public void setAllowPay(boolean allowPay) {
            this.allowPay = allowPay;
        }
    }
}
