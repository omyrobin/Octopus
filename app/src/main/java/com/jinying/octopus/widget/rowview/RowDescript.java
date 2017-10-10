package com.jinying.octopus.widget.rowview;

public class RowDescript {
	public int iconResId;
	public String lable;
	public String money;
	public RowActionEnum action;
	
	public int visable;
	
	public RowDescript(int iconResId, String lable, String money,
			RowActionEnum action) {
		super();
		this.iconResId = iconResId;
		this.lable = lable;
		this.money = money;
		this.action = action;
	}

	public RowDescript(int iconResId, String lable, String money,
			RowActionEnum action, int visable) {
		super();
		this.iconResId = iconResId;
		this.lable = lable;
		this.money = money;
		this.action = action;
		this.visable = visable;
	}
	
	
	
	
}
