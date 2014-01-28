package com.mingproductions.evtracker.model;

import java.util.ArrayList;

import com.mingproductions.evtracker.model.old.DataConverter;
import com.mingproductions.evtracker.model.old.EVPokemonOld;
import com.mingproductions.evtracker.model.old.EVTrackerJSONSerializerOld;

import android.content.Context;
import android.util.Log;

public class PokedexStore {
	private ArrayList<EVPokemon> allPokemon;
	private Context mAppContext;
	
	private static PokedexStore sharedStore;
	private EVTrackerJSONSerializer mSerializer;
	private EVTrackerJSONSerializerOld mSerializerOld;
	
	private PokedexStore(Context appContext)
	{
		mAppContext = appContext;
		mSerializer = new EVTrackerJSONSerializer(mAppContext, "pokedex.json");
		try
		{
			allPokemon = mSerializer.loadPokedex();
		}
		catch (Exception ex)
		{
			Log.e("PokedexStore", "Unable to load pokedex", ex);
		}
	}
	
	private PokedexStore(Context appContext, boolean migrate)
	{
		mAppContext = appContext;
		mSerializer = new EVTrackerJSONSerializer(mAppContext, "pokedex.json");
		mSerializerOld = new EVTrackerJSONSerializerOld(mAppContext, "pokedex.json");
		try
		{
			ArrayList<EVPokemonOld> allPokemonOld = mSerializerOld.loadPokedex();
			allPokemon = DataConverter.converter(mAppContext).convertOldPokemons(allPokemonOld);
			mSerializer.savePokedex(allPokemon);
		}
		catch (Exception ex)
		{
			Log.e("PokedexStore", "Unable to load pokedex", ex);
		}
	}
	
	public static PokedexStore sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new PokedexStore(c.getApplicationContext());
		}
		
		return sharedStore;
	}
	
	public static PokedexStore sharedStore(Context c, boolean migrate)
	{
		if (sharedStore == null)
		{
			sharedStore = new PokedexStore(c.getApplicationContext(), migrate);
		}
		
		return sharedStore;
	}
	
	public void addPokemon(EVPokemon pokemon) {
		allPokemon.add(pokemon);
	}
	
	public void addPokemon(int pokemonNumber, String pokemonName, int level, int hp, int atk, int def, int spAtk, int spDef, 
			int speed) {
		EVPokemon pokemon = new EVPokemon(pokemonNumber, pokemonName, level, hp, atk, def, spAtk, spDef, speed);
		
		allPokemon.add(pokemon);
	}

	public ArrayList<EVPokemon> allPokemon() {
		return allPokemon;
	}

	@Override
	public String toString() {
		String toString = "{";
		
		for (EVPokemon p:allPokemon)
		{
			toString.concat(p.toString() + ", ");
		}
		
		toString.concat("}");
		
		return toString;
	}
	
	public void savePokedex()
	{
		try
		{
			mSerializer.savePokedex(PokedexStore.sharedStore(mAppContext).allPokemon());
		}
		catch (Exception ex)
		{
			Log.e("GameStore", "Error Saving Pokedex", ex);
		}
	}
}
