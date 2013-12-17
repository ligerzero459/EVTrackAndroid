package com.mingproductions.evtracker.model;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;

public class GameStore {
	private ArrayList<PokemonGame> allGames;
	
	private static GameStore sharedStore;
	private Context mAppContext;
	
	private GameStore(Context appContext)
	{
		mAppContext = appContext;
		allGames = new ArrayList<PokemonGame>();
		
		addGame(new PokemonGame());
		addGame(new PokemonGame("Pokemon Black", 0x7f0202cf));
		
	}
	
	public static GameStore sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new GameStore(c.getApplicationContext());
		}
		
		return sharedStore;
	}
	
	public ArrayList<PokemonGame> allGames()
	{
		return allGames;
	}
	
	public void addGame(PokemonGame game)
	{
		allGames.add(game);
	}
	
	public void removeGame(PokemonGame game)
	{
		allGames.remove(game);
	}
	
	public PokemonGame gameAtIndex(int index)
	{
		return (PokemonGame)allGames.get(index);
	}
}
