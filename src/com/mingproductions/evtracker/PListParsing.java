package com.mingproductions.evtracker;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameTableStore;
import com.mingproductions.evtracker.model.PokedexStore;
import com.mingproductions.evtracker.model.PokemonGame;

public class PListParsing {

	public static void PopulatePokedex(AssetManager mgr, Context c) throws IOException, NullPointerException
	{
		InputStream is = null;
		int pokemonNumber = 0;
		try
		{
			is = mgr.open("PokemonList.plist");
			NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(is);
			NSObject[] tempPokemon = ((NSArray)rootDict.objectForKey("Pokemon")).getArray();

			for(NSObject param:tempPokemon)
			{
				NSDictionary pokemonDict = (NSDictionary)param;
				
				pokemonNumber = ((NSNumber)pokemonDict.objectForKey("Num")).intValue();
				String pokemonName = ((NSString)pokemonDict.objectForKey("Name")).toString();
				int hp = ((NSNumber)pokemonDict.objectForKey("HP")).intValue();
				int atk = ((NSNumber)pokemonDict.objectForKey("Atk")).intValue();
				int def = ((NSNumber)pokemonDict.objectForKey("Def")).intValue();
				int spAtk = ((NSNumber)pokemonDict.objectForKey("SpAtk")).intValue();
				int spDef = ((NSNumber)pokemonDict.objectForKey("SpDef")).intValue();
				int speed = ((NSNumber)pokemonDict.objectForKey("Speed")).intValue();
				
				EVPokemon newPokemon = new EVPokemon(pokemonNumber, pokemonName, 0, hp, atk, def, spAtk, spDef, speed);
				
				PokedexStore.sharedStore(c).addPokemon(newPokemon);
			}
		}
		catch(Exception ex)
		{
			Log.e("PopulatePokedex", "Unable to read object: " + Integer.toString(pokemonNumber));
			Log.e("PopulatePokedex", "Unable to populate pokedex", ex);
		}
		finally
		{
			is.close();
		}
	}
	
	public static void PopulateGameList(AssetManager mgr, Context c) throws IOException, NullPointerException
	{
		InputStream is = null;
		try
		{
			is = mgr.open("GameList.plist");
			NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(is);
			NSObject[] tempGame = ((NSArray)rootDict.objectForKey("Game")).getArray();
			
			for (NSObject param: tempGame)
			{
				NSDictionary gameDict = (NSDictionary) param;
				
				PokemonGame newGame = new PokemonGame();
				
				newGame.setGameName(((NSString)gameDict.objectForKey("gameName")).toString());
				newGame.setImageName(((NSString)gameDict.objectForKey("imageName")).toString());
				
				GameTableStore.sharedStore(c).addGame(newGame);
			}
		}
		catch (Exception ex)
		{
			Log.e("PopulateGameList", "Unable to populate game list", ex);
		}
		finally
		{
			is.close();
		}
	}
}
