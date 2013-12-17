package com.mingproductions.evtracker.model;

import java.io.IOException;
import java.util.ArrayList;

import com.mingproductions.evtracker.PListParsing;

import android.content.Context;
import android.content.res.AssetManager;

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
			try
			{
				AssetManager mgr = c.getAssets();
				PListParsing.PopulatePokedex(mgr, c);
			}
			catch(IOException ex)
			{
				ex.printStackTrace();
			}
			catch(NullPointerException ex)
			{
				ex.printStackTrace();
			}
		}
		
		return sharedStore;
	}
	
	public void addPokemon(EVPokemon pokemon) {
		allPokemon.add(pokemon);
	}
	
	public void addPokemon(int pokemonNumber, String pokemonName, int imageResource, int level, int hp, int atk, int def, int spAtk, int spDef, 
			int speed) {
		EVPokemon pokemon = new EVPokemon(pokemonNumber, pokemonName, imageResource, level, hp, atk, def, spAtk, spDef, speed);
		
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
}
