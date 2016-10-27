package org.globant.mybus.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ScreenManager {

	public static void showFragInFrameLayout(FragmentActivity activity,Fragment frag,int fragLayoutId) {
		FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
		ft.replace(fragLayoutId, frag);
		ft.commit();
	}
	
	public static void ShowMapActivity(Context context,int mode){
		Intent intent = new Intent(context, MapActivity.class);
		Bundle bun_request = new Bundle();
		bun_request.putInt("request_type", mode);
		intent.putExtras(bun_request);
		context.startActivity(intent);
	}
	

}
