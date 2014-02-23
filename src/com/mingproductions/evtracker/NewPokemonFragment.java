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
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mingproductions.evtracker.adapter.PokemonAdapter;
import com.mingproductions.evtracker.listener.EVAdListener;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.PokedexStore;

public class NewPokemonFragment extends SherlockListFragment {

	private ArrayList<EVPokemon> mAllPokemon;
	private int mGamePos;
	
	private PokemonAdapter adapter;
	private AdView adView;
	private AdRequest request;

	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		try
		{
			super.onCreate(savedInstanceBundle);

			mGamePos = (int)getArguments().getInt(ListGameFragment.EXTRA_GAME_POSITION);
			mAllPokemon = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getSherlockActivity()).allPokemon());

			adapter = new PokemonAdapter(mAllPokemon, getSherlockActivity());
			setListAdapter(adapter);

			setHasOptionsMenu(true);
			setRetainInstance(true);

			FragmentStorage.sharedStore(getSherlockActivity()).addFragmentToList(this);
		}
		catch (Exception ex)
		{
			FragmentStorage.sharedStore(getSherlockActivity()).clearFragmentList();
			getSherlockActivity().getSupportActionBar().selectTab(getSherlockActivity().getSupportActionBar().getTabAt(0));
		}
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
	}
	
	@Override
	public void onResume()
	{
		try {
			super.onResume();
			mAllPokemon = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getSherlockActivity()).allPokemon());
			getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			getSherlockActivity().getSupportActionBar().setTitle("New Pokemon");
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
		catch (Exception ex)
		{
			FragmentStorage.sharedStore(getSherlockActivity()).clearFragmentList();
			getSherlockActivity().getSupportActionBar().selectTab(getSherlockActivity().getSupportActionBar().getTabAt(0));
		}
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
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		int selectedIndex = mAllPokemon.indexOf(l.getItemAtPosition(position));
		EVPokemon newPokemon = new EVPokemon(mAllPokemon.get(selectedIndex).getPokemonNumber(), 
				mAllPokemon.get(selectedIndex).getPokemonName());

		GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).addPokemon(newPokemon);
		GameStore.sharedStore(getSherlockActivity()).saveGames();
		
		FragmentStorage.sharedStore(getSherlockActivity()).removeFragmentFromList(this);
		getSherlockActivity().getSupportFragmentManager().popBackStackImmediate();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
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
	
	public static NewPokemonFragment newInstance(int position)
	{
		Bundle args = new Bundle();
		args.putInt(ListGameFragment.EXTRA_GAME_POSITION, position);

		NewPokemonFragment fragment = new NewPokemonFragment();
		fragment.setArguments(args);

		return fragment;
	}
	
}
