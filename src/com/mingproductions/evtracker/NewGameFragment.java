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
import com.mingproductions.evtracker.adapter.GameAdapter;
import com.mingproductions.evtracker.listener.EVAdListener;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.GameTableStore;
import com.mingproductions.evtracker.model.PokemonGame;

public class NewGameFragment extends SherlockListFragment {

	private ArrayList<PokemonGame> mAllGames;
	private GameAdapter adapter;
	private AdView adView;
	private AdRequest request;

	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);

		mAllGames = new ArrayList<PokemonGame>(GameTableStore.sharedStore(getActivity()).allGames());

		adapter = new GameAdapter(mAllGames, getActivity());
		setListAdapter(adapter);
		
		setHasOptionsMenu(true);
		
		FragmentStorage.sharedStore(getActivity()).addFragmentToList(this);
		
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
		getSherlockActivity().getSupportActionBar().setTitle("New Game");
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		adapter.notifyDataSetChanged();
		adView.resume();
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
		
		request = new AdRequest.Builder().addTestDevice("CC76AC3414081FCAA8F95B228B622FBB").build();
		adView  = (AdView) v.findViewById(R.id.adView);
		adView.setAdListener(new EVAdListener(adView));
		adView.loadAd(request);
		
		return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		PokemonGame newGame = (PokemonGame)l.getItemAtPosition(position);

		GameStore.sharedStore(getActivity()).addGame(newGame);
		GameStore.sharedStore(getActivity()).saveGames();

		getFragmentManager().popBackStackImmediate();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			FragmentStorage.sharedStore(getActivity()).removeFragmentFromList(this);
			getFragmentManager().popBackStackImmediate();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
