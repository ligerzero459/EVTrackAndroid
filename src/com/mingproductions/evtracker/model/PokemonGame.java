package com.mingproductions.evtracker.model;

import java.util.ArrayList;

public class PokemonGame {
	private ArrayList<EVPokemon> mAllPokemon;
	private String mGameName;
	private int mImageId;
	
	public PokemonGame() {
		this("Default Game", 0x7f020000);
	}
	
	public PokemonGame(String gameName, int imageId) {
		this(gameName, imageId, new ArrayList<EVPokemon>());
	}
	
	public PokemonGame(String gameName, int imageId, ArrayList<EVPokemon> allPokemon) {
		setGameName(gameName);
		setImageId(imageId);
		setAllPokemon(allPokemon);
	}

	public ArrayList<EVPokemon> getAllPokemon() {
		return mAllPokemon;
	}

	public String getGameName() {
		return mGameName;
	}

	public int getImageId() {
		return mImageId;
	}

	public void setAllPokemon(ArrayList<EVPokemon> allPokemon) {
		mAllPokemon = allPokemon;
	}

	public void setGameName(String gameName) {
		mGameName = gameName;
	}

	public void setImageId(int imageId) {
		mImageId = imageId;
	}
	
}
