package com.example.com.criteo.sdktestapp;

import org.json.JSONObject;

import android.util.Log;

import com.mobileapptracker.MATResponse;

/**
 * This class initializes debug information output module.
 * @param void
 * @return void
 */
public class PrintInfo implements MATResponse {

	@Override
	public void didFailWithError(JSONObject error) {
		// TODO Auto-generated method stub
		String err = error.toString();
		if(err != null)
		{	Log.d("MAT.failure", error.toString()); } 
		else { Log.d("MAT.failure", "Error is null");   }
		
	}

	@Override
	public void didReceiveDeeplink(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void didSucceedWithData(JSONObject data) {
		// TODO Auto-generated method stub
		Log.d("MAT.success", data.toString());
	}

	@Override
	public void enqueuedActionWithRefId(String error) {
		// TODO Auto-generated method stub

	}

}
