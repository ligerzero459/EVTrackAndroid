package com.mingproductions.evtracker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;

public class EVDetailFragment extends SherlockFragment {
	// TODO: Create fragment to rename pokemon
	
	private EVPokemon mPokemon;
	private int mGamePos;
	private int mPokemonPos;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		setHasOptionsMenu(true);
		setRetainInstance(true);
		
		mGamePos = getArguments().getInt("game");
		mPokemonPos = getArguments().getInt("mPokemon");
		
		/**
		 * Breakdown: retrieves GameStore, looks for the game at mGamePos and then
		 * finds a specific Pokemon in that game
		 */
		mPokemon = GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).pokemonAtIndex(mPokemonPos);
		
		// Set logo in title bar
		if (mPokemon.getPokemonNumber() < 10)
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p00" + mPokemon.getPokemonNumber(), null, null)));
		else if (mPokemon.getPokemonNumber() < 100)
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p0" + mPokemon.getPokemonNumber(), null, null)));
		else
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p" + mPokemon.getPokemonNumber(), null, null)));
		
		getSherlockActivity().getSupportActionBar().setTitle(mPokemon.getPokemonName());
		FragmentStorage.sharedStore(getActivity()).addFragmentToList(this);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		GameStore.sharedStore(getActivity()).saveGames();
		
		TextView hp = (TextView)getView().findViewById(R.id.hp_evs);
		hp.setText(mPokemon.getHp() + "/255");
		
		TextView atk = (TextView)getView().findViewById(R.id.atk_evs);
		atk.setText(mPokemon.getAtk() + "/255");
		
		TextView def = (TextView)getView().findViewById(R.id.def_evs);
		def.setText(mPokemon.getDef() + "/255");
		
		TextView spatk = (TextView)getView().findViewById(R.id.sp_atk_evs);
		spatk.setText(mPokemon.getSpAtk() + "/255");
		
		TextView spdef = (TextView)getView().findViewById(R.id.sp_def_evs);
		spdef.setText(mPokemon.getSpDef() + "/255");
		
		TextView speed = (TextView)getView().findViewById(R.id.speed_evs);
		speed.setText(mPokemon.getSpeed() + "/255");
		
		TextView total = (TextView)getView().findViewById(R.id.total_evs);
		total.setText(mPokemon.getTotal() + "/510");
		
		// Set logo in title bar
		if (mPokemon.getPokemonNumber() < 10)
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p00" + mPokemon.getPokemonNumber(), null, null)));
		else if (mPokemon.getPokemonNumber() < 100)
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p0" + mPokemon.getPokemonNumber(), null, null)));
		else
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p" + mPokemon.getPokemonNumber(), null, null)));

		getSherlockActivity().getSupportActionBar().setTitle(mPokemon.getPokemonName());
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		GameStore.sharedStore(getActivity()).saveGames();
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_ev_details, parent, false);
		
		// TODO: Look how to retrieve touch events from a layout vs a button (onClickListener?)
		TextView pokemonName = (TextView)v.findViewById(R.id.pokemon_name);
		pokemonName.setText(mPokemon.getPokemonName());
		
		ImageView pokemonImage = (ImageView)v.findViewById(R.id.pokemon_image);
		Drawable image = null;
		
		if (mPokemon.getPokemonNumber() < 10)
			image = getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p00" + mPokemon.getPokemonNumber(), null, null));
		else if (mPokemon.getPokemonNumber() < 100)
			image = getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p0" + mPokemon.getPokemonNumber(), null, null));
		else
			image = getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p" + mPokemon.getPokemonNumber(), null, null));
		pokemonImage.setImageDrawable(image);
		
		TextView hp = (TextView)v.findViewById(R.id.hp_evs);
		hp.setText(mPokemon.getHp() + "/255");
		
		TextView atk = (TextView)v.findViewById(R.id.atk_evs);
		atk.setText(mPokemon.getAtk() + "/255");
		
		TextView def = (TextView)v.findViewById(R.id.def_evs);
		def.setText(mPokemon.getDef() + "/255");
		
		TextView spatk = (TextView)v.findViewById(R.id.sp_atk_evs);
		spatk.setText(mPokemon.getSpAtk() + "/255");
		
		TextView spdef = (TextView)v.findViewById(R.id.sp_def_evs);
		spdef.setText(mPokemon.getSpDef() + "/255");
		
		TextView speed = (TextView)v.findViewById(R.id.speed_evs);
		speed.setText(mPokemon.getSpeed() + "/255");
		
		TextView total = (TextView)v.findViewById(R.id.total_evs);
		total.setText(mPokemon.getTotal() + "/510");
		
		Button battled = (Button)v.findViewById(R.id.battled_pokemon_button);
		battled.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction()
				.replace(R.id.host_view, EVBattledPokemonFragment.newInstance(mPokemonPos, mGamePos)).addToBackStack(null).commit();
			}
		});
		
		Button fixEvs = (Button)v.findViewById(R.id.fix_evs_button);
		fixEvs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getFragmentManager().beginTransaction()
				.replace(R.id.host_view, EVFixFragment.newInstance(mPokemonPos, mGamePos)).addToBackStack(null).commit();
			}
		});
		
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_ev_details_menu, menu);
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
		case R.id.menu_item_delete_pokemon:
			FragmentStorage.sharedStore(getActivity()).removeFragmentFromList(this);
			getFragmentManager().popBackStackImmediate();
			GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).removePokemon(mPokemon);
			return true;
		case R.id.menu_item_ev_items:
		{
			getFragmentManager().beginTransaction()
			.replace(R.id.host_view, EVItemsFragment.newInstance(mPokemonPos, mGamePos)).addToBackStack(null).commit();
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public static EVDetailFragment newInstance(int position, int game)
	{
		Bundle args = new Bundle();
		args.putInt("mPokemon", position);
		args.putInt("game", game);
		
		EVDetailFragment fragment = new EVDetailFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
