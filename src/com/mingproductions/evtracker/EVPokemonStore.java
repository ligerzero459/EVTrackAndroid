package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.content.Context;

public class EVPokemonStore {
	private ArrayList<EVPokemon> allPokemon;
	
	private static EVPokemonStore sharedStore;
	private Context mAppContext;
	
	private EVPokemonStore(Context appContext)
	{
		mAppContext = appContext;
		allPokemon = new ArrayList<EVPokemon>();
	}
	
	public static EVPokemonStore sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new EVPokemonStore(c.getApplicationContext());
		}
		
		return sharedStore;
	}
	
	public void addPokemon(EVPokemon pokemon) {
		allPokemon.add(pokemon);
	}
	
	public void addPokemon(int pokemonNumber, String pokemonName) {
		EVPokemon pokemon = new EVPokemon(pokemonNumber, pokemonName);
		
		allPokemon.add(pokemon);
	}
	
	public void removePokemon(EVPokemon pokemon)
	{
		allPokemon.remove(pokemon);
	}
	
	public ArrayList<EVPokemon> getAllPokemon() {
		return allPokemon;
	}
}
