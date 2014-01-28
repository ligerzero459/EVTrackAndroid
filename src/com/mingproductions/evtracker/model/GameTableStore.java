package com.mingproductions.evtracker.model;

import java.util.ArrayList;

import com.mingproductions.evtracker.model.old.DataConverter;
import com.mingproductions.evtracker.model.old.EVTrackerJSONSerializerOld;
import com.mingproductions.evtracker.model.old.PokemonGameOld;

import android.content.Context;
import android.util.Log;

public class GameTableStore {
	private ArrayList<PokemonGame> allGames;
	private Context mAppContext;
	
	private static GameTableStore sharedStore;
	private EVTrackerJSONSerializer mSerializer;
	private EVTrackerJSONSerializerOld mSerializerOld;

	private GameTableStore(Context appContext)
	{
		mAppContext = appContext;
		mSerializer = new EVTrackerJSONSerializer(mAppContext, "gametable.json");
		try
		{
			allGames = mSerializer.loadGameTable();
		}
		catch (Exception ex)
		{
			Log.e("GameTableStore", "Unable to load game table");
		}
	}
	
	private GameTableStore(Context appContext, boolean migrate)
	{
		mAppContext = appContext;
		mSerializer = new EVTrackerJSONSerializer(mAppContext, "gametable.json");
		mSerializerOld = new EVTrackerJSONSerializerOld(mAppContext, "gametable.json");
		try
		{
			ArrayList<PokemonGameOld> allGamesOld = mSerializerOld.loadGameTable();
			allGames = DataConverter.converter(mAppContext).convertOldGames(allGamesOld);
			mSerializer.saveGameTable(allGames);
		}
		catch (Exception ex)
		{
			Log.e("GameTableStore", "Unable to load game table");
		}
	}
	
	public static GameTableStore sharedStore(Context c)
	{
		if (sharedStore == null)
		{
			sharedStore = new GameTableStore(c.getApplicationContext());
		}
		
		return sharedStore;
	}
	
	public static GameTableStore sharedStore(Context c, boolean migrate)
	{
		if (sharedStore == null)
		{
			sharedStore = new GameTableStore(c.getApplicationContext(), migrate);
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
	
	public void saveGameTable()
	{
		try
		{
			mSerializer.saveGameTable(GameTableStore.sharedStore(mAppContext).allGames());
		}
		catch (Exception ex)
		{
			Log.e("GameStore", "Error Saving Game Table", ex);
		}
	}
}
