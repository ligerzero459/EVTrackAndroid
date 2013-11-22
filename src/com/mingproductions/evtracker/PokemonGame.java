package com.mingproductions.evtracker;

import java.util.ArrayList;

public class PokemonGame {
	private ArrayList<EVPokemon> mAllPokemon;
	private String mGameName;
	private R.drawable mImageDrawable;
	
	public PokemonGame() {
		this("Default Game", null);
	}
	
	public PokemonGame(String gameName, R.drawable imageDrawable) {
		this(gameName, imageDrawable, new ArrayList<EVPokemon>());
	}
	
	public PokemonGame(String gameName, R.drawable imageDrawable, ArrayList<EVPokemon> allPokemon) {
		
	}

	public ArrayList<EVPokemon> getAllPokemon() {
		return mAllPokemon;
	}

	public String getGameName() {
		return mGameName;
	}

	public R.drawable getImageDrawable() {
		return mImageDrawable;
	}

	public void setAllPokemon(ArrayList<EVPokemon> allPokemon) {
		mAllPokemon = allPokemon;
	}

	public void setGameName(String gameName) {
		mGameName = gameName;
	}

	public void setImageDrawable(R.drawable imageDrawable) {
		mImageDrawable = imageDrawable;
	}
	
}
