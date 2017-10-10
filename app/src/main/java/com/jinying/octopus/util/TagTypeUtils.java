package com.jinying.octopus.util;

/**
 * Created by omyrobin on 2017/8/25.
 */

public class TagTypeUtils {

    public static String getTagType(String channel, String type){
        String tagType;
        if("1".equals(channel)){
            switch (type){
                case "1":
                    tagType = "现代都市";
                    break;

                case "2":
                    tagType = "悬疑灵异";
                    break;

                case "3":
                    tagType = "玄幻仙侠";
                    break;

                default:
                    tagType = "历史军事";
                    break;
            }
        }else{
            switch (type){
                case "1":
                    tagType = "古代言情";
                    break;

                case "2":
                    tagType = "纯爱作品";
                    break;

                case "3":
                    tagType = "现代言情";
                    break;

                default:
                    tagType = "仙侠奇幻";
                    break;
            }
        }
        return tagType;
    }

}
