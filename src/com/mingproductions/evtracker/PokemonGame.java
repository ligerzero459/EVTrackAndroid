package com.mingproductions.evtracker;

import java.util.ArrayList;

public class PokemonGame {
	private ArrayList<EVPokemon> mAllPokemon;
	private String mGameName;
	private String mImageName;
	
	public PokemonGame() {
		this("Default Game", "");
	}
	
	public PokemonGame(String gameName, String imageName) {
		this(gameName, imageName, new ArrayList<EVPokemon>());
	}
	
	public PokemonGame(String gameName, String imageName, ArrayList<EVPokemon> allPokemon) {
		
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

	public void setAllPokemon(ArrayList<EVPokemon> allPokemon) {
		mAllPokemon = allPokemon;
	}

	public void setGameName(String gameName) {
		mGameName = gameName;
	}

	public void setImageName(String imageName) {
		mImageName = imageName;
	}
	
}
