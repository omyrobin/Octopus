package com.jinying.octopus.util;

/**
 * Created by omyrobin on 2017/8/31.
 */

public class RankingUtil {

    public static String getRankingName(String type){
        String tagType;
        switch (type) {
            case "1":
                tagType = "新书榜";
                break;

            case "2":
                tagType = "人气榜";
                break;

            default:
                tagType = "收藏榜";
                break;
        }
        return tagType;
    }
}
