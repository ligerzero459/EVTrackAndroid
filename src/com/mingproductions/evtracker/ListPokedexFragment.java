package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockListFragment;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mingproductions.evtracker.adapter.PokedexAdapter;
import com.mingproductions.evtracker.listener.EVAdListener;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.PokedexStore;

public class ListPokedexFragment extends SherlockListFragment {
	
	private ArrayList<EVPokemon> mPokedex;
	
	private PokedexAdapter adapter;
	private AdView adView;
	private AdRequest request;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);

		mPokedex = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getActivity()).allPokemon());

		adapter = new PokedexAdapter(mPokedex, getActivity());
		setListAdapter(adapter);

		setRetainInstance(true);
		
		getSherlockActivity().getSupportActionBar().setTitle("Pokedex");
		getSherlockActivity().getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		mPokedex = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getActivity()).allPokemon());
		getSherlockActivity().getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSherlockActivity().getSupportActionBar().setTitle("Pokedex");
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		adView.pause();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		adView.destroy();
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_search_list, parent, false);
		
		EditText searchBox = (EditText)v.findViewById(R.id.inputSearch);
		searchBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Required, but not used				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// Required, but not used
			}
		});
		
		request = new AdRequest.Builder().addTestDevice("B3EEABB8EE11C2BE770B684D95219ECB").build();
		adView  = (AdView) v.findViewById(R.id.adView);
		adView.setAdListener(new EVAdListener(adView));
		adView.loadAd(request);
		
		return v;
	} 
	
}

