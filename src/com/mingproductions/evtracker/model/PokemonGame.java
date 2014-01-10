package com.mingproductions.evtracker.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokemonGame {
	
	private static final String JSON_NAME = "name";
	private static final String JSON_IMAGE = "image";
	private static final String JSON_POKEMON = "pokemon";
	
	private ArrayList<EVPokemon> mAllPokemon;
	private String mGameName;
	private String mImageName;
	
	public PokemonGame() {
		this("Default Game", "default_game");
	}
	
	public PokemonGame(String gameName, String imageName) {
		this(gameName, imageName, new ArrayList<EVPokemon>());
	}
	
	public PokemonGame(String gameName, String imageName, ArrayList<EVPokemon> allPokemon) {
		setGameName(gameName);
		setImageName(imageName);
		setAllPokemon(allPokemon);
	}

	public PokemonGame(JSONObject json) throws JSONException {
		setGameName(json.getString(JSON_NAME));
		setImageName(json.getString(JSON_IMAGE));
		setAllPokemon(new ArrayList<EVPokemon>());
		
		JSONArray array = json.getJSONArray(JSON_POKEMON);
		for (int i = 0; i < array.length(); i++) {
			mAllPokemon.add(new EVPokemon(array.getJSONObject(i)));
		}
	}

	public ArrayList<EVPokemon> getAllPokemon() {
		return mAllPokemon;
	}

	public String getGameName() {
		return mGameName;
	}

	public String getImageName() {
		return mImageName;
	}

	public void setImageName(String imageName) {
		mImageName = imageName;
	}

	public void setAllPokemon(ArrayList<EVPokemon> allPokemon) {
		mAllPokemon = allPokemon;
	}

	public void setGameName(String gameName) {
		mGameName = gameName;
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		json.put(JSON_NAME, mGameName);
		json.put(JSON_IMAGE, mImageName);
		
		JSONArray pokemonArray = new JSONArray();
		for (EVPokemon p : mAllPokemon)
		{
			pokemonArray.put(p.toJSON());
		}
		
		json.put(JSON_POKEMON, pokemonArray);
		
		return json;
	}
	
}
