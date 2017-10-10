package com.jinying.octopus.bean;

import java.util.List;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class RankDetailBean {

    /**
     * pageNo : 1
     * pageSize : 1
     * sort : null
     * total : 22
     * totalPages : 22
     * list : [{"name":"戮仙弑神记","channel":"1","pcIntroduce":"乱世殇，苦命人，跨界行，独修真，不入宗门。","cover":"http://p0.duyao001.com/image/article/29bcadcf56fa39feaecaadd4039f71b2_450x590.jpg","introduce":"乱世殇，苦命人。\n跨界行，独修真！         \n绝域开派，逆天行。\n对战五宗，声威立。 \n仙兵成，傲世锋！\n大出天下， 纵横无敌寻。\n灭门破宗，心系俗世人。\n戮仙弑神独称尊！\n碎星钟起，宗门散，\n大公至正万界行，大同世界万古帝。","appIntroduce":"碎星钟起，宗门散，大公至正万界行，大同世界万古帝。","author":"小甜瓜","type":"玄幻仙侠","tag":"热血,仙侠,异世大陆,武侠,西游","pvAll":null,"pvLast3Day":null,"id":1001117}]
     */

    private int pageNo;
    private int pageSize;
    private Object sort;
    private int total;
    private int totalPages;
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

    public Object getSort() {
        return sort;
    }

    public void setSort(Object sort) {
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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * name : 戮仙弑神记
         * channel : 1
         * pcIntroduce : 乱世殇，苦命人，跨界行，独修真，不入宗门。
         * cover : http://p0.duyao001.com/image/article/29bcadcf56fa39feaecaadd4039f71b2_450x590.jpg
         * introduce : 乱世殇，苦命人。
         跨界行，独修真！
         绝域开派，逆天行。
         对战五宗，声威立。
         仙兵成，傲世锋！
         大出天下， 纵横无敌寻。
         灭门破宗，心系俗世人。
         戮仙弑神独称尊！
         碎星钟起，宗门散，
         大公至正万界行，大同世界万古帝。
         * appIntroduce : 碎星钟起，宗门散，大公至正万界行，大同世界万古帝。
         * author : 小甜瓜
         * type : 玄幻仙侠
         * tag : 热血,仙侠,异世大陆,武侠,西游
         * pvAll : null
         * pvLast3Day : null
         * id : 1001117
         */

        private String name;
        private String channel;
        private String pcIntroduce;
        private String cover;
        private String introduce;
        private String appIntroduce;
        private String author;
        private String type;
        private String tag;
        private Long pvAll;
        private Long pvLast3Day;
        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getPcIntroduce() {
            return pcIntroduce;
        }

        public void setPcIntroduce(String pcIntroduce) {
            this.pcIntroduce = pcIntroduce;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getAppIntroduce() {
            return appIntroduce;
        }

        public void setAppIntroduce(String appIntroduce) {
            this.appIntroduce = appIntroduce;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public Long getPvAll() {
            return pvAll;
        }

        public void setPvAll(Long pvAll) {
            this.pvAll = pvAll;
        }

        public Long getPvLast3Day() {
            return pvLast3Day;
        }

        public void setPvLast3Day(Long pvLast3Day) {
            this.pvLast3Day = pvLast3Day;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
