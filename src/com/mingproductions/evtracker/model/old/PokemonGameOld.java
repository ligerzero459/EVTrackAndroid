package com.mingproductions.evtracker.model.old;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokemonGameOld {
	
	private static final String JSON_NAME = "name";
	private static final String JSON_IMAGE = "image";
	private static final String JSON_POKEMON = "pokemon";
	
	private ArrayList<EVPokemonOld> mAllPokemon;
	private String mGameName;
	private String mImageName;
	
	public PokemonGameOld() {
		this("Default Game", "default_game");
	}
	
	public PokemonGameOld(String gameName, String imageName) {
		this(gameName, imageName, new ArrayList<EVPokemonOld>());
	}
	
	public PokemonGameOld(String gameName, String imageName, ArrayList<EVPokemonOld> allPokemon) {
		setGameName(gameName);
		setImageName(imageName);
		setAllPokemon(allPokemon);
	}

	public PokemonGameOld(JSONObject json) throws JSONException {
		setGameName(json.getString(JSON_NAME));
		setImageName(json.getString(JSON_IMAGE));
		setAllPokemon(new ArrayList<EVPokemonOld>());
		
		JSONArray array = json.getJSONArray(JSON_POKEMON);
		for (int i = 0; i < array.length(); i++) {
			mAllPokemon.add(new EVPokemonOld(array.getJSONObject(i)));
		}
	}

	public ArrayList<EVPokemonOld> getAllPokemon() {
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

	public void setAllPokemon(ArrayList<EVPokemonOld> allPokemon) {
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
		for (EVPokemonOld p : mAllPokemon)
		{
			pokemonArray.put(p.toJSON());
		}
		
		json.put(JSON_POKEMON, pokemonArray);
		
		return json;
	}
	
	public void addPokemon(EVPokemonOld p)
	{
		mAllPokemon.add(p);
	}
	
	public void removePokemon(EVPokemonOld p)
	{
		mAllPokemon.remove(p);
	}
	
	public EVPokemonOld pokemonAtIndex(int position)
	{
		return mAllPokemon.get(position);
	}
	
	public void replacePokemon(int position, EVPokemonOld p)
	{
		mAllPokemon.set(position, p);
	}
	
}
