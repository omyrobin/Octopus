package com.jinying.octopus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jinying.octopus.event.EventNetConnection;
import com.jinying.octopus.launch.LaunchContract;
import com.jinying.octopus.user.state.ErrorState;
import com.jinying.octopus.user.state.UserState;
import com.jinying.octopus.util.NetworkUtil;

import org.greenrobot.eventbus.EventBus;

public class ConnectionChangeReceiver extends BroadcastReceiver {
	
	private boolean isNotFirstRegister;

	private LaunchContract.Presenter mPresneter;
	
	public ConnectionChangeReceiver(LaunchContract.Presenter mPresneter) {
		this.mPresneter = mPresneter;
	}

	@Override
    public void onReceive(Context context, Intent intent) {
    	if(UserState.getInstance().getState() instanceof ErrorState && isNotFirstRegister){
			mPresneter.regrestOrLogin(true);
    	}else if(!NetworkUtil.isConnected(App.getInstance())){
//    		Toaster.showLong("当前无网络连接");
    	}
		isNotFirstRegister = true;
    }
}
    
