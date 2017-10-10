package com.jinying.octopus.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;
@SuppressLint("HandlerLeak")
public class TimerButton extends AppCompatButton {
	private int len = 60;
	private int recLen; 
	
	public String preText = "";
	private String afterText = "";
	
	public TimerButton(Context context) {
		super(context);
	}
	public TimerButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setLen(int len){
		this.len = len;
		this.recLen = len;
	}
	
	public void setPreText(String preText){
		this.preText = preText == null ? "" : preText;
	}
	
	public void setAfterText(String afterText){
		this.afterText = afterText == null ? "" : afterText;
	}
	
	private mHandler myHandler;
	
	/**
	 * 开始倒计时
	 */
	public void startTimer(){
		myHandler = new mHandler();
		this.setText(preText + recLen-- + "秒");
		Message message = myHandler.obtainMessage(1); 
		myHandler.sendMessageDelayed(message, 1000);
		setEnabled(false);
	}
	private OnStopListener onStopListener;
	public void setOnStopListener(OnStopListener l){
		onStopListener = l;
	}
	
	private class mHandler extends Handler{
		public void handleMessage(Message msg){         // handle message  
			switch (msg.what) {  
			case 1: 
				setText(preText + recLen + "秒");  
				recLen--; 
				if(recLen >= 0){ 
					Message message = this.obtainMessage(1);
					sendMessageDelayed(message, 1000);      // send message  
				}else{
					if(onStopListener != null){
						onStopListener.onStop();
					}
					recLen = len;
					TimerButton.this.setText(afterText);
					setEnabled(true);
				}  
			}  
		}
	}
	
	public interface OnStopListener{
		void onStop();
	}
	
	public void stopTimer(){
		if(myHandler!=null && myHandler.hasMessages(1)){
			myHandler.removeMessages(1);
			myHandler = null;
		}

	}
}
