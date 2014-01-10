package com.mingproductions.evtracker.model;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;

import com.mingproductions.evtracker.PListParsing;

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
			try
			{
				AssetManager mgr = c.getAssets();
				PListParsing.PopulateGameList(mgr, c);
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
	
	public ArrayList<PokemonGame> allGames()
	{
		return allGames;
	}
	
	public void addGame(PokemonGame game)
	{
		allGames.add(game);
	}
}
