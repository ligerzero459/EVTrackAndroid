package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.content.Context;

public class PokedexStore {
	private ArrayList<EVPokemon> allPokemon;
	
	private static PokedexStore sharedStore;
	private Context mAppContext;
	
	private PokedexStore(Context appContext)
	{
		mAppContext = appContext;
		allPokemon = new ArrayList<EVPokemon>();
	}
	
	public static PokedexStore sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new PokedexStore(c.getApplicationContext());
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
}
