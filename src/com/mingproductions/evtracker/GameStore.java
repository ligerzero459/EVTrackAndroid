package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.content.Context;

public class GameStore {
	private ArrayList<PokemonGame> allGames;
	
	private static GameStore sharedStore;
	private Context mAppContext;
	
	private GameStore(Context appContext)
	{
		mAppContext = appContext;
		allGames = new ArrayList<PokemonGame>();
	}
	
	public static GameStore sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new GameStore(c.getApplicationContext());
		}
		
		return sharedStore;
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
