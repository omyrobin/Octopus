package com.jinying.octopus.read;

/**
 * 章节信息，包括标题和内容，及顺序
 * @author MJZ
 *
 */
public class Chapter {	
	private String title;
	private String content;
	private int currIndex;
	
	//付费章节需要用到的属性
	private int wordNum;
	private int price;
	private String gold;
	private boolean isAutoPay;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCurrIndex() {
		return currIndex;
	}
	public void setCurrIndex(int currIndex) {
		this.currIndex = currIndex;
	}
	public int getWordNum() {
		return wordNum;
	}
	public void setWordNum(int wordNum) {
		this.wordNum = wordNum;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getGold() {
		return gold;
	}
	public void setGold(String gold) {
		this.gold = gold;
	}
	public boolean isAutoPay() {
		return isAutoPay;
	}
	public void setAutoPay(boolean isAutoPay) {
		this.isAutoPay = isAutoPay;
	}
}
