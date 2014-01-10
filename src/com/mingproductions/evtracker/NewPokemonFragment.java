package com.mingproductions.evtracker;

import java.util.ArrayList;

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
import com.mingproductions.evtracker.model.PokedexStore;

public class NewPokemonFragment extends SherlockListFragment {
	// TODO: Implement search bar
	
	private ArrayList<EVPokemon> allPokemon;
	private int gamePos;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		gamePos = (int)getArguments().getInt(GameListFragment.EXTRA_GAME_POSITION);
		allPokemon = PokedexStore.sharedStore(getActivity()).allPokemon();
		
		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		PokemonAdapter adapter = new PokemonAdapter(allPokemon);
		setListAdapter(adapter);
		
		setRetainInstance(true);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		EVPokemon newPokemon = new EVPokemon(allPokemon.get(position).getPokemonNumber(), 
				allPokemon.get(position).getPokemonName());
		
		/**
		 * This super long line gets the GameStore singleton, gets all the 
		 * games from the GameStore, get the selected game, then get a
		 * reference to the allPokemon array and then add the newPokemon
		 * to the game array
		 **/
		GameStore.sharedStore(getActivity()).allGames().get(gamePos).getAllPokemon().add(newPokemon);
		GameStore.sharedStore(getActivity()).saveGames();
		
		getActivity().finish();
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
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getSherlockActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
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
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_pokemon, null);
			}
			
			Resources resource = getResources();
			EVPokemon p = getItem(position);
			
			ImageView pokemonImage = (ImageView)convertView.findViewById(R.id.pokemon_image);
			if (p.getPokemonNumber() < 10)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p00" + p.getPokemonNumber(), null, null));
			else if (p.getPokemonNumber() < 100)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p0" + p.getPokemonNumber(), null, null));
			else
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p" + p.getPokemonNumber(), null, null));
			
			TextView pokemonName = (TextView)convertView.findViewById(R.id.pokemon_name);
			pokemonName.setText("#" + p.getPokemonNumber() + " " + p.getPokemonName());
			
			return convertView;
		}
	}
	
	public static NewPokemonFragment newInstance(int position)
	{
		Bundle args = new Bundle();
		args.putInt(GameListFragment.EXTRA_GAME_POSITION, position);
		
		NewPokemonFragment fragment = new NewPokemonFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
