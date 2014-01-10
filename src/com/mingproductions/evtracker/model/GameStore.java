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
