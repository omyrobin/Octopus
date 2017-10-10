package com.jinying.octopus.bean;

/**
 * Created by omyrobin on 2017/8/22.
 */
public class BookShelfBean {

    /**
     * userId : 502832580
     * storyId : 1000497
     * chapterCount : 3
     * unReadingCount : 3
     * readingChapterName :
     * readingChapterId : 0
     */
    private Long _id;

    private String userId;
    private String storyId;
    private int chapterCount;
    private int unReadingCount;
    private String readingChapterName;
    private String readingChapterId;
    private StoryVoBean storyVo;
    private String chapterDesc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public int getChapterCount() {
        return chapterCount;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

    public int getUnReadingCount() {
        return unReadingCount;
    }

    public void setUnReadingCount(int unReadingCount) {
        this.unReadingCount = unReadingCount;
    }

    public String getReadingChapterName() {
        return readingChapterName;
    }

    public void setReadingChapterName(String readingChapterName) {
        this.readingChapterName = readingChapterName;
    }

    public String getReadingChapterId() {
        return readingChapterId;
    }

    public void setReadingChapterId(String readingChapterId) {
        this.readingChapterId = readingChapterId;
    }

    public StoryVoBean getStoryVo() {
        return storyVo;
    }

    public void setStoryVo(StoryVoBean storyVo) {
        this.storyVo = storyVo;
    }

    public String getChapterDesc() {
        return chapterDesc;
    }

    public void setChapterDesc(String chapterDesc) {
        this.chapterDesc = chapterDesc;
    }
}
