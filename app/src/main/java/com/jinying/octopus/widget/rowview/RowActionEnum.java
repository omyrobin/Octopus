package com.jinying.octopus.widget.rowview;



public enum RowActionEnum {
	
	MINE_READER_LOG(0,"阅读记录"),
	MINE_CLEAR(0,"清理缓存"),
	MINE_GRADE(0,"给个评分"),
	MINE_OPINION(0, "意见反馈"),
	MINE_ABOUT(0,"关于我们"),
	MINE_SWITCH(0,"切换账号");
	
	
	private final int value;  
	private final String lable; 
    public int getValue() {  
        return value;  
    }  
    public String getLable(){
    	 return lable;  
    }

    //构造器默认也只能是private, 从而保证构造函数只能在内部使用  
    RowActionEnum(int value,String lable) {  
        this.value = value;
        this.lable = lable;
    }  

}
