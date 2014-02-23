package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mingproductions.evtracker.adapter.PokedexAdapter;
import com.mingproductions.evtracker.listener.EVAdListener;
import com.mingproductions.evtracker.model.BattledPokemon;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.PokedexStore;

public class EVBattledPokemonFragment extends SherlockListFragment {

	private ArrayList<EVPokemon> mPokedex;
	private EVPokemon mPokemon;
	private int mGamePos;
	private int mPokemonPos;

	private PokedexAdapter adapter;
	private AdView adView;
	private AdRequest request;

	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		setHasOptionsMenu(true);
		setRetainInstance(true);

		mGamePos = getArguments().getInt("game");
		mPokemonPos = getArguments().getInt("mPokemon");

		mPokedex = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getSherlockActivity()).allPokemon());
		mPokemon = GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).pokemonAtIndex(mPokemonPos);
		
		adapter = new PokedexAdapter(mPokedex, getSherlockActivity());
		setListAdapter(adapter);
		
		FragmentStorage.sharedStore(getSherlockActivity()).addFragmentToList(this);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		mPokedex = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getSherlockActivity()).allPokemon());
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSherlockActivity().getSupportActionBar().setTitle("Battled Pokemon");
		adapter.notifyDataSetChanged();
		if (adView != null)
			adView.resume();
		else
		{
			request = new AdRequest.Builder().addTestDevice("CC76AC3414081FCAA8F95B228B622FBB").build();
			adView  = (AdView) getView().findViewById(R.id.adView);
			adView.setAdListener(new EVAdListener(adView));
			adView.loadAd(request);
		}
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

	public void onListItemClick(ListView l, View v, int position, long id)
	{
		boolean alreadyBattled = false;
		EVPokemon selectedP = (EVPokemon)l.getItemAtPosition(position);

		mPokemon.addPokemon(selectedP);
		for (BattledPokemon recent : mPokemon.getRecentBattled())
		{
			if (recent.getPokemonName().contentEquals(selectedP.getPokemonName()))
			{
				alreadyBattled = true;
				recent.incrementBattled();
			}
		}
		if (!alreadyBattled)
		{
			mPokemon.addRecent(new BattledPokemon(selectedP));
		}

		GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).replacePokemon(mPokemonPos, mPokemon);
		
		FragmentStorage.sharedStore(getSherlockActivity()).removeFragmentFromList(this);
		getSherlockActivity().getSupportFragmentManager().popBackStackImmediate();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			FragmentStorage.sharedStore(getSherlockActivity()).removeFragmentFromList(this);
			getSherlockActivity().getSupportFragmentManager().popBackStackImmediate();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static EVBattledPokemonFragment newInstance(int pokemonPos, int gamePos)
	{
		Bundle args = new Bundle();
		args.putInt("mPokemon", pokemonPos);
		args.putInt("game", gamePos);

		EVBattledPokemonFragment fragment = new EVBattledPokemonFragment();
		fragment.setArguments(args);

		return fragment;
	}
	
}
