package com.mingproductions.evtracker;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.PokedexStore;

public class PListParsing {

	public static void PopulatePokedex(AssetManager mgr, Context c) throws IOException, NullPointerException
	{
		try
		{
			String[] paths = mgr.list("");
			InputStream is = mgr.open(paths[0]);
			NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(is);
			NSObject[] tempPokemon = ((NSArray)rootDict.objectForKey("Pokemon")).getArray();

			for(NSObject param:tempPokemon)
			{
				NSDictionary pokemonDict = (NSDictionary)param;
				
				int pokemonNumber = ((NSNumber)pokemonDict.objectForKey("Num")).intValue();
				String pokemonName = ((NSString)pokemonDict.objectForKey("Name")).toString();
				int imageResource = ((NSNumber)pokemonDict.objectForKey("ImageNumber")).intValue();
				int hp = ((NSNumber)pokemonDict.objectForKey("HP")).intValue();
				int atk = ((NSNumber)pokemonDict.objectForKey("Atk")).intValue();
				int def = ((NSNumber)pokemonDict.objectForKey("Def")).intValue();
				int spAtk = ((NSNumber)pokemonDict.objectForKey("SpAtk")).intValue();
				int spDef = ((NSNumber)pokemonDict.objectForKey("SpDef")).intValue();
				int speed = ((NSNumber)pokemonDict.objectForKey("Speed")).intValue();
				
				EVPokemon newPokemon = new EVPokemon(pokemonNumber, pokemonName, imageResource, 0, hp, atk, def, spAtk, spDef, speed);
				
				PokedexStore.sharedStore(c).addPokemon(newPokemon);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
