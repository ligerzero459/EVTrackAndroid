package com.mingproductions.evtracker.model;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class GameStore {
	private static final String FILENAME = "games.json";
	
	private ArrayList<PokemonGame> allGames;
	private EVTrackerJSONSerializer mSerializer;
	
	private static GameStore sharedStore;
	private Context mAppContext;
	
	/**
	 * GameStore
	 * @param appContext - Android application context
	 * Initializes the GameStore singleton
	 */
	
	private GameStore(Context appContext)
	{
		mAppContext = appContext;
		mSerializer = new EVTrackerJSONSerializer(mAppContext, FILENAME);
		
		try
		{
			allGames = mSerializer.loadGames();
		}
		catch (Exception ex)
		{
			allGames = new ArrayList<PokemonGame>();
		}
		
	}
	
	/**
	 * sharedStore
	 * @param c - Activity Context
	 * @return returns the sharedStore singleton
	 * Checks if a sharedStore already exists. If so, it returns the
	 * existing store. If not, a new store is created and returned
	 */
	
	public static GameStore sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new GameStore(c.getApplicationContext());
		}
		
		return sharedStore;
	}
	
	/**
	 * allGames
	 * @return returns allGames array
	 */
	
	public ArrayList<PokemonGame> allGames()
	{
		return allGames;
	}
	
	/**
	 * addGame
	 * @param game
	 * Takes new PokemonGame and inserts it into the store
	 */
	
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
	
	public void saveGames()
	{
		try
		{
			mSerializer.saveGames(allGames);
		}
		catch (Exception ex)
		{
			Log.e("GameStore", "Error Saving Games", ex);
		}
	}
}
