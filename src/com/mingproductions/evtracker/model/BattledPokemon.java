package com.mingproductions.evtracker.model;

import org.json.JSONException;
import org.json.JSONObject;

public class BattledPokemon {
	/**
	 * Static Variables
	 */
	private static final String JSON_POKEMON_NUMBER = "number";
	private static final String JSON_POKEMON_NAME = "name";
	private static final String JSON_LEVEL = "level";
	private static final String JSON_HP = "hp";
	private static final String JSON_ATK = "atk";
	private static final String JSON_DEF = "def";
	private static final String JSON_SPATK = "spatk";
	private static final String JSON_SPDEF = "spdef";
	private static final String JSON_SPEED = "speed";
	private static final String JSON_BATTLED = "battled";
	
	/**
	 * Member Variables
	 */
	private int mPokemonNumber;
	private String mPokemonName;
	private int mLevel;
	private int mHp;
	private int mAtk;
	private int mDef;
	private int mSpAtk;
	private int mSpDef;
	private int mSpeed;
	private int mBattled;
	
	/**
	*  Constructors
	*/
	
	public BattledPokemon()
	{
		// Default Constructor
		this(1, "Pokemon");
	}
	
	public BattledPokemon(int pokemonNumber, String pokemonName)
	{
		/** Standard Constructor
		 	Used for adding Pokemon at runtime*/
		this(pokemonNumber, pokemonName, 1, 0, 0, 0, 0, 0, 0, 1);
	}
	
	public BattledPokemon(int pokemonNumber, String pokemonName,  int level, int hp, int atk, int def, int spAtk, int spDef, 
			int speed, int battled) {
		/** Specialized Constructor
		 	Only to be called through "Standard" or when adding entries
		 	To main PokeDex */
		setPokemonNumber(pokemonNumber);
		setPokemonName(pokemonName);
		setLevel(level);
		setHp(hp);
		setAtk(atk);
		setDef(def);
		setSpAtk(spAtk);
		setSpDef(spDef);
		setSpeed(speed);
		setBattled(battled);
	}
	
	public BattledPokemon(JSONObject json) throws JSONException {
		setPokemonNumber(json.getInt(JSON_POKEMON_NUMBER));
		setPokemonName(json.getString(JSON_POKEMON_NAME));
		setLevel(json.getInt(JSON_LEVEL));
		setHp(json.getInt(JSON_HP));
		setAtk(json.getInt(JSON_ATK));
		setDef(json.getInt(JSON_DEF));
		setSpAtk(json.getInt(JSON_SPATK));
		setSpDef(json.getInt(JSON_SPDEF));
		setSpeed(json.getInt(JSON_SPEED));
		setBattled(json.getInt(JSON_BATTLED));
	}
	
	public BattledPokemon(EVPokemon pokemonAtIndex) {
		setPokemonNumber(pokemonAtIndex.getPokemonNumber());
		setPokemonName(pokemonAtIndex.getPokemonName());
		setLevel(pokemonAtIndex.getLevel());
		setHp(pokemonAtIndex.getHp());
		setAtk(pokemonAtIndex.getAtk());
		setDef(pokemonAtIndex.getDef());
		setSpAtk(pokemonAtIndex.getSpAtk());
		setSpDef(pokemonAtIndex.getSpDef());
		setSpeed(pokemonAtIndex.getSpeed());
		setBattled(1);
	}
	
	/**
	* Getters/Setters
	*/
	
	public int getPokemonNumber() {
		return mPokemonNumber;
	}
	
	public String getPokemonName() {
		return mPokemonName;
	}
	
	public int getLevel() {
		return mLevel;
	}
	
	public int getHp() {
		return mHp;
	}
	
	public int getAtk() {
		return mAtk;
	}
	
	public int getDef() {
		return mDef;
	}
	
	public int getSpAtk() {
		return mSpAtk;
	}
	
	public int getSpDef() {
		return mSpDef;
	}
	
	public int getSpeed() {
		return mSpeed;
	}
	
	public int getBattled() {
		return mBattled;
	}
	
	public void setPokemonNumber(int pokemonNumber) {
		mPokemonNumber = pokemonNumber;
	}
	
	public void setPokemonName(String pokemonName) {
		mPokemonName = pokemonName;
	}
	
	public void setLevel(int level) {
		mLevel = level;
	}
	
	public void setHp(int hp) {
		mHp = hp;
	}
	
	public void setAtk(int atk) {
		mAtk = atk;
	}
	
	public void setDef(int def) {
		mDef = def;
	}
	
	public void setSpAtk(int spAtk) {
		mSpAtk = spAtk;
	}
	
	public void setSpDef(int spDef) {
		mSpDef = spDef;
	}
	
	public void setSpeed(int speed) {
		mSpeed = speed;
	}
	
	public void setBattled(int battled) {
		mBattled = battled;
	}
	
	public void incrementBattled()
	{
		mBattled++;
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		
		json.put(JSON_POKEMON_NUMBER, mPokemonNumber);
		json.put(JSON_POKEMON_NAME, mPokemonName);
		json.put(JSON_LEVEL, mLevel);
		json.put(JSON_HP, mHp);
		json.put(JSON_ATK, mAtk);
		json.put(JSON_DEF, mDef);
		json.put(JSON_SPATK, mSpAtk);
		json.put(JSON_SPDEF, mSpDef);
		json.put(JSON_SPEED, mSpeed);
		json.put(JSON_BATTLED, mBattled);
		
		return json;
	}

}
