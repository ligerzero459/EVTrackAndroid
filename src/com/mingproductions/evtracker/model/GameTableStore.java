package com.mingproductions.evtracker.model;

import java.util.ArrayList;

import android.content.Context;

public class GameTableStore {
	private ArrayList<PokemonGame> allGames;
	
	private static GameTableStore sharedStore;
	private Context mAppContext;

	private GameTableStore(Context appContext)
	{
		mAppContext = appContext;
		allGames = new ArrayList<PokemonGame>();
	}
	
	public static GameTableStore sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new GameTableStore(c.getApplicationContext());
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
}
