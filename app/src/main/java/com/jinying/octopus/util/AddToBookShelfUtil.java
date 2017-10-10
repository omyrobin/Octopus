package com.jinying.octopus.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.jinying.octopus.R;
import com.jinying.octopus.bean.StoryVoBean;


public class AddToBookShelfUtil {
	
	private Context context;
	
	private StoryVoBean mStoryVo;
	
	public AlertDialog mDialog;

	public AddToBookShelfUtil(Context context, StoryVoBean mStoryInfo) {
		this.context = context;
		this.mStoryVo = mStoryInfo;
		initAlertDialog();
	}


	private void initAlertDialog(){
		//使用v7的dialog
		AlertDialog.Builder builder=new AlertDialog.Builder(context, R.style.BaseAlterDiaLog);  //先得到构造器
        builder.setTitle("提示"); //设置标题  
        builder.setMessage("是否将这个小说加入到书架,方便您继续阅读"); //设置内容
        builder.setPositiveButton("加入", new DialogInterface.OnClickListener() { //设置确定按钮  
            @Override  
            public void onClick(DialogInterface dialog, int which) {
            	toAddBook();
            	dialog.dismiss(); //关闭dialog 
            	((FragmentActivity)context).finish();
            }  
        });  
        builder.setNegativeButton("不加入", new DialogInterface.OnClickListener() { //设置取消按钮  
            @Override  
            public void onClick(DialogInterface dialog, int which) { 
            	dialog.dismiss();  
            	((FragmentActivity)context).finish();
            }  
        });  
        //参数都设置完成了，创建并显示出来  
        mDialog = builder.create();
        
	}
	
	public void show(){
		if(!mDialog.isShowing()){
			mDialog.show();  
		}
		
	}
	
	public void dismiss(){
		mDialog.dismiss();  
	}
	
	public void isShow(){
		if(mDialog!=null&&mDialog.isShowing()){
			mDialog.dismiss();
		}
	}
	
	public void toAddBook(){
		addToBookShelf();
    	requestChapter();
	}
	
	private void addToBookShelf(){

	}
	
	private void requestChapter(){
//		RequestParams params = new RequestParams();
//		params.put("storyId", mStoryVo.getId());
//		Logger.i(params.toString());
//		HttpUtils.get(Url.NEW_API+Url.GET_CHAPTERS, params,new JsonHttpResponseHandler(){
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					String responseString, Throwable throwable) {
//				super.onFailure(statusCode, headers, responseString, throwable);
//			}
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers,
//					JSONObject response) {
//				NovelChapterEntity novelChapter = ParseUtils.getPerson(ResultDataUtils.getData(response), NovelChapterEntity.class);
//				String mChapterListJosn = new Gson().toJson(novelChapter.getChapterList());
//				FileUtils.writeTextFile(FileUtils.getNovelChapterList(mStoryVo.getId()+""), mChapterListJosn);
//			}
//
//		});
	}
	
	/**加入书架,并保存章节Json**/
	private void addToMyBookShelf() {
		//通知详情变成加入书架
//		EventBus.getDefault().post(new EventAddToBook());
	}
	
}
