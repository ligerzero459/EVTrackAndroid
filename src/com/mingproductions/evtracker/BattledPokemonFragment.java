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
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.PokedexStore;

public class BattledPokemonFragment extends SherlockListFragment {
	// TODO: Implement search bar
	
	private ArrayList<EVPokemon> pokedex;
	private EVPokemon pokemon;
	private int gamePos;
	private int pokemonPos;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		gamePos = getArguments().getInt("game");
		pokemonPos = getArguments().getInt("pokemon");
		
		pokedex = PokedexStore.sharedStore(getActivity()).allPokemon();
		pokemon = GameStore.sharedStore(getActivity()).allGames().get(gamePos).getAllPokemon().get(pokemonPos);
		
		BattledAdapter adapter = new BattledAdapter(pokedex);
		setListAdapter(adapter);
		
		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			((BattledPokemonActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		EVPokemon selectedP = pokedex.get(position);
		
		// FIXME: Logic error involving adding EVs. Fix inside EVPokemon class
		
		pokemon.addPokemon(selectedP);
		pokemon.checkForItems();
		
		GameStore.sharedStore(getActivity()).allGames().get(gamePos).getAllPokemon().set(pokemonPos, pokemon);
		
		getActivity().finish();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public static BattledPokemonFragment newInstance(int pokemonPos, int gamePos)
	{
		Bundle args = new Bundle();
		args.putInt("pokemon", pokemonPos);
		args.putInt("game", gamePos);
		
		BattledPokemonFragment fragment = new BattledPokemonFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	private class BattledAdapter extends ArrayAdapter<EVPokemon>
	{
		public BattledAdapter(ArrayList<EVPokemon> pokedex)
		{
			super(getActivity(), 0, pokedex);
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_pokemon, null);
			}
			
			Resources resource = getResources();
			EVPokemon pokemon = getItem(position);
			
			ImageView pokemonImage = (ImageView)convertView.findViewById(R.id.pokemon_image);
			if (pokemon.getPokemonNumber() < 10)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p00" + pokemon.getPokemonNumber(), null, null));
			else if (pokemon.getPokemonNumber() < 100)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p0" + pokemon.getPokemonNumber(), null, null));
			else
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p" + pokemon.getPokemonNumber(), null, null));
			
			
			TextView pokemonName = (TextView)convertView.findViewById(R.id.pokemon_name);
			pokemonName.setText("#" + pokemon.getPokemonNumber() + " " + pokemon.getPokemonName());
			
			return convertView;
		}
	}
}
