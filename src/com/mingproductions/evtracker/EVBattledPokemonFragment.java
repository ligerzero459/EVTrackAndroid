package com.mingproductions.evtracker;

import java.util.ArrayList;

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
import com.mingproductions.evtracker.model.PokedexStore;

public class EVBattledPokemonFragment extends SherlockListFragment {
	// TODO: Implement search bar
	
	private ArrayList<EVPokemon> mPokedex;
	private EVPokemon mPokemon;
	private int mGamePos;
	private int mPokemonPos;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		mGamePos = getArguments().getInt("game");
		mPokemonPos = getArguments().getInt("mPokemon");
		
		mPokedex = PokedexStore.sharedStore(getActivity()).allPokemon();
		mPokemon = GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).pokemonAtIndex(mPokemonPos);
		
		BattledAdapter adapter = new BattledAdapter(mPokedex);
		setListAdapter(adapter);
		
		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			((EVBattledPokemonActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		EVPokemon selectedP = mPokedex.get(position);
		
		mPokemon.addPokemon(selectedP);
		
		GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).replacePokemon(mPokemonPos, mPokemon);
		
		getActivity().finish();
	}
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_default_menu, menu);
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
		case R.id.menu_item_pokedex:
		{
			Intent i = new Intent(getActivity(), ListPokedexActivity.class);
			startActivity(i);
			return true;
		}
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
