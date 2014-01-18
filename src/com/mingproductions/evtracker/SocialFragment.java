package com.mingproductions.evtracker;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragment;

public class SocialFragment extends SherlockFragment {
	
	private static String twitterId = "EvTracker";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_social, parent, false);
		
		ImageButton facebook_button = (ImageButton)v.findViewById(R.id.facebook_button);
		facebook_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String fbURL = "fb://profile/149272685277959";
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(fbURL));
				
				final PackageManager packageManager = getActivity().getPackageManager();
		        List<ResolveInfo> list =
		            packageManager.queryIntentActivities(intent,
		            PackageManager.MATCH_DEFAULT_ONLY);
		        if (list.size() == 0) {
		            final String urlBrowser = "https://www.facebook.com/PokemonEvTracker?ref=hl";
		            intent.setData(Uri.parse(urlBrowser));
		        }

		        startActivity(intent);
			}
		});
		
		ImageButton twitter_button = (ImageButton)v.findViewById(R.id.twitter_button);
		twitter_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = null;
				final String[] twitterURLarray = { "twitter://user?screen_name={handle}", // Twitter
	                     "tweetbot:///user_profile/{handle}", // TweetBot
	                     "echofon:///user_timeline?{handle}", // Echofon
	                     "twit:///user?screen_name={handle}", // Twittelator Pro
	                     "x-seesmic://twitter_profile?twitter_screen_name={handle}", // Seesmic
	                     "x-birdfeed://user?screen_name={handle}", // Birdfeed
	                     "tweetings:///user?screen_name={handle}", // Tweetings
	                     "simplytweet:?link=http://twitter.com/{handle}", // SimplyTweet
	                     "icebird://user?screen_name={handle}", // IceBird
	                     "fluttr://user/{handle}", // Fluttr
	                     "http://twitter.com/{handle}" };
				
				final PackageManager packageManager = getActivity().getPackageManager();
				List<ResolveInfo> list = new LinkedList<ResolveInfo>();
				
				for (String twitterURL : twitterURLarray)
				{
					twitterURL = twitterURL.replace("{handle}", twitterId);
					intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse(twitterURL));
					list.add(packageManager.resolveService(intent, PackageManager.MATCH_DEFAULT_ONLY));
				}
						
				if (list.size() == 0)
				{
					final String urlBrowser = "http://twitter.com/EVTracker";
					intent.setData(Uri.parse(urlBrowser));
				}
				
				startActivity(intent);
			}
		});
		
		
		return v;
	}
}
