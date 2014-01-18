package com.mingproductions.evtracker.listener;

import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class EVAdListener extends AdListener {
	private AdView mAdView; 
	
	 public EVAdListener(AdView adView)
	 {
		 mAdView = adView;
	 }
	 
	 @Override
	    public void onAdFailedToLoad(int errorCode) {
	        String errorReason = "";
	        switch(errorCode) {
	            case AdRequest.ERROR_CODE_INTERNAL_ERROR:
	                errorReason = "Internal error";
	                break;
	            case AdRequest.ERROR_CODE_INVALID_REQUEST:
	                errorReason = "Invalid request";
	                break;
	            case AdRequest.ERROR_CODE_NETWORK_ERROR:
	                errorReason = "Network Error";
	                break;
	            case AdRequest.ERROR_CODE_NO_FILL:
	                errorReason = "No fill";
	                break;
	        }
	        Log.e("AdView", "Ad failed to load: " + errorReason);
	    }

	    @Override
	    public void onAdOpened() {
	    	mAdView.setVisibility(View.VISIBLE);
	    }

	    @Override
	    public void onAdClosed() {
	    	mAdView.setVisibility(View.GONE);
	    }

	    @Override
	    public void onAdLeftApplication() {
	    	mAdView.setVisibility(View.GONE);
	    }
}
