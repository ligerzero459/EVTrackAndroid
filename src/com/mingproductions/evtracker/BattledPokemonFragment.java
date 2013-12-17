package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.PokedexStore;

public class BattledPokemonFragment extends ListFragment {
	
	private ArrayList<EVPokemon> pokedex;
	private EVPokemon pokemon;
	private int gamePos;
	private int pokemonPos;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		pokedex = PokedexStore.sharedStore(getActivity()).allPokemon();
		pokemon = GameStore.sharedStore(getActivity()).allGames().get(gamePos).getAllPokemon().get(pokemonPos);
		
		BattledAdapter adapter = new BattledAdapter(pokedex);
		setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		EVPokemon selectedP = pokedex.get(position);
		
		pokemon.addPokemon(selectedP);
		pokemon.checkForItems();
		
		GameStore.sharedStore(getActivity()).allGames().get(gamePos).getAllPokemon().set(pokemonPos, pokemon);
		
		getActivity().finish();
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
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_pokedex, null);
			}
			
			EVPokemon pokemon = getItem(position);
			
			ImageView pokemonImage = (ImageView)convertView.findViewById(R.id.pokemon_image);
			pokemonImage.setImageResource(pokemon.getImageResource());
			
			TextView pokemonName = (TextView)convertView.findViewById(R.id.pokemon_name);
			pokemonName.setText("#" + pokemon.getPokemonNumber() + " " + pokemon.getPokemonName());
			
			return convertView;
		}
	}

}
