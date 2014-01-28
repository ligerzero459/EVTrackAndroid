package com.mingproductions.evtracker.model.old;

import java.util.ArrayList;

import android.content.Context;

import com.mingproductions.evtracker.model.BattledPokemon;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.PokemonGame;

public class DataConverter {
	private Context mAppContext;
	private static DataConverter converter;
	
	private DataConverter(Context context)
	{
		mAppContext = context;
	}
	
	public static DataConverter converter(Context c)
	{
		if (converter == null)
		{
			converter = new DataConverter(c.getApplicationContext());
		}
		
		return converter;
	}
	
	public ArrayList<PokemonGame> convertOldGames(ArrayList<PokemonGameOld> oldGames)
	{
		ArrayList<PokemonGame> newGames = new ArrayList<PokemonGame>();
		
		for (PokemonGameOld og : oldGames)
		{
			newGames.add(convertOldGame(og));
		}
		
		return newGames;
	}
	
	public ArrayList<EVPokemon> convertOldPokemons(ArrayList<EVPokemonOld> oldPokemons)
	{
		ArrayList<EVPokemon> newPokemons = new ArrayList<EVPokemon>();
		
		for (EVPokemonOld op : oldPokemons)
		{
			newPokemons.add(convertOldPokemon(op));
		}
		
		return newPokemons;
	}
	
	public PokemonGame convertOldGame(PokemonGameOld oldGame)
	{
		PokemonGame newGame = new PokemonGame();
		
		newGame.setGameName(oldGame.getGameName());
		newGame.setImageName(oldGame.getImageName());
		newGame.setAllPokemon(new ArrayList<EVPokemon>());
		
		for (EVPokemonOld p : oldGame.getAllPokemon())
		{
			newGame.addPokemon(convertOldPokemon(p));
		}
		
		return newGame;
	}
	
	public EVPokemon convertOldPokemon(EVPokemonOld oldPokemon)
	{
		EVPokemon newPokemon = new EVPokemon();
		
		newPokemon.setPokemonName(oldPokemon.getPokemonName());
		newPokemon.setPokemonNumber(oldPokemon.getPokemonNumber());
		newPokemon.setHp(oldPokemon.getHp());
		newPokemon.setAtk(oldPokemon.getAtk());
		newPokemon.setDef(oldPokemon.getDef());
		newPokemon.setSpAtk(oldPokemon.getSpAtk());
		newPokemon.setSpDef(oldPokemon.getSpDef());
		newPokemon.setSpeed(oldPokemon.getSpeed());
		newPokemon.setPKRS(oldPokemon.isPKRS());
		newPokemon.setMachoBrace(oldPokemon.isMachoBrace());
		newPokemon.setPowerAnklet(oldPokemon.isPowerAnklet());
		newPokemon.setPowerBand(oldPokemon.isPowerBand());
		newPokemon.setPowerBelt(oldPokemon.isPowerBelt());
		newPokemon.setPowerBracer(oldPokemon.isPowerBracer());
		newPokemon.setPowerLens(oldPokemon.isPowerLens());
		newPokemon.setPowerWeight(oldPokemon.isPowerWeight());
		newPokemon.setRecentBattled(new ArrayList<BattledPokemon>());
		
		return newPokemon;
	}

}
