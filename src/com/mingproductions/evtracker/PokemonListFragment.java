package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;

public class PokemonListFragment extends SherlockListFragment {
	
	private ArrayList<EVPokemon> allPokemon;
	private int gamePos;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		gamePos = (int)getArguments().getInt(GameListFragment.EXTRA_GAME_POSITION);
		allPokemon = GameStore.sharedStore(getActivity()).allGames().get(gamePos).getAllPokemon();

		PokemonAdapter adapter = new PokemonAdapter(allPokemon);
		setListAdapter(adapter);
		
		setHasOptionsMenu(true);
		setRetainInstance(true);

		// Set the title & image to the correct game
		getActivity().setTitle(GameStore.sharedStore(getActivity()).allGames().get(gamePos).getGameName());

		Resources resource = getResources();

		int imageId = resource.getIdentifier("com.mingproductions.evtracker:drawable/" 
				+ GameStore.sharedStore(getActivity()).allGames().get(gamePos).getImageName()
				, null, null);
		((PokemonListActivity)getActivity()).getSupportActionBar().setLogo(imageId);
		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		((PokemonAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent i = new Intent(getActivity(), EVDetailActivity.class);
		
		Bundle b = new Bundle();
		b.putInt("pokemon", position);
		b.putInt("game", gamePos);
		i.putExtras(b);
		
		startActivity(i);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_pokemon_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getSherlockActivity());
			}
			return true;
		case R.id.menu_item_new_pokemon:
			Intent i = new Intent(getActivity(), NewPokemonActivity.class);
			
			Bundle b = new Bundle();
			b.putInt(GameListFragment.EXTRA_GAME_POSITION, gamePos);
			
			i.putExtras(b);
			startActivity(i);
			return true;
		default:
			return false;
		}
	}
	
	private class PokemonAdapter extends ArrayAdapter<EVPokemon>
	{
		public PokemonAdapter(ArrayList<EVPokemon> allPokemon)
		{
			super(getActivity(), 0, allPokemon);
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_ev_pokemon, null);
			}
			
			Resources resource = getResources();
			EVPokemon p = getItem(position);
			
			ImageView pokemonImage = (ImageView)convertView.findViewById(R.id.image_pokemon);
			
			if (p.getPokemonNumber() < 10)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p00" + p.getPokemonNumber(), null, null));
			else if (p.getPokemonNumber() < 100)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p0" + p.getPokemonNumber(), null, null));
			else
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p" + p.getPokemonNumber(), null, null));
			
			TextView pokemonName = (TextView)convertView.findViewById(R.id.name_pokemon);
			pokemonName.setText(p.getPokemonName());
			
			return convertView;
		}
	}
	
	public static PokemonListFragment newInstance(int position)
	{
		Bundle args = new Bundle();
		args.putInt(GameListFragment.EXTRA_GAME_POSITION, position);
		
		PokemonListFragment fragment = new PokemonListFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
