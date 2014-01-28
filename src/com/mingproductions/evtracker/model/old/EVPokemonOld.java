package com.mingproductions.evtracker.model.old;

import org.json.JSONException;
import org.json.JSONObject;

public class EVPokemonOld {
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
	private static final String JSON_PKRS = "pkrs";
	private static final String JSON_MACHO_BRACE = "macho";
	private static final String JSON_POWER_WEIGHT = "weight";
	private static final String JSON_POWER_BRACER = "bracer";
	private static final String JSON_POWER_BELT = "belt";
	private static final String JSON_POWER_LENS = "lens";
	private static final String JSON_POWER_BAND = "band";
	private static final String JSON_POWER_ANKLET = "anklet";
	
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
	private Boolean mPKRS;
	private Boolean mMachoBrace;
	private Boolean mPowerWeight;
	private Boolean mPowerBracer;
	private Boolean mPowerBelt;
	private Boolean mPowerLens;
	private Boolean mPowerBand;
	private Boolean mPowerAnklet;
	
	/**
	*  Constructors
	*/
	
	public EVPokemonOld()
	{
		// Default Constructor
		this(1, "Pokemon");
	}
	
	public EVPokemonOld(int pokemonNumber, String pokemonName)
	{
		/** Standard Constructor
		 	Used for adding Pokemon at runtime*/
		this(pokemonNumber, pokemonName, 1, 0, 0, 0, 0, 0, 0);
	}
	
	public EVPokemonOld(int pokemonNumber, String pokemonName,  int level, int hp, int atk, int def, int spAtk, int spDef, 
			int speed) {
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
		setPKRS(false);
		setMachoBrace(false);
		setPowerWeight(false);
		setPowerBracer(false);
		setPowerBelt(false);
		setPowerLens(false);
		setPowerBand(false);
		setPowerAnklet(false);
	}
	
	public EVPokemonOld(JSONObject json) throws JSONException {
		setPokemonNumber(json.getInt(JSON_POKEMON_NUMBER));
		setPokemonName(json.getString(JSON_POKEMON_NAME));
		setLevel(json.getInt(JSON_LEVEL));
		setHp(json.getInt(JSON_HP));
		setAtk(json.getInt(JSON_ATK));
		setDef(json.getInt(JSON_DEF));
		setSpAtk(json.getInt(JSON_SPATK));
		setSpDef(json.getInt(JSON_SPDEF));
		setSpeed(json.getInt(JSON_SPEED));
		setPKRS(json.getBoolean(JSON_PKRS));
		setMachoBrace(json.getBoolean(JSON_MACHO_BRACE));
		setPowerWeight(json.getBoolean(JSON_POWER_WEIGHT));
		setPowerBracer(json.getBoolean(JSON_POWER_BRACER));
		setPowerBelt(json.getBoolean(JSON_POWER_BELT));
		setPowerLens(json.getBoolean(JSON_POWER_LENS));
		setPowerBand(json.getBoolean(JSON_POWER_BAND));
		setPowerAnklet(json.getBoolean(JSON_POWER_ANKLET));
	}
	
	public EVPokemonOld(EVPokemonOld pokemonAtIndex) {
		setPokemonNumber(pokemonAtIndex.getPokemonNumber());
		setPokemonName(pokemonAtIndex.getPokemonName());
		setLevel(pokemonAtIndex.getLevel());
		setHp(pokemonAtIndex.getHp());
		setAtk(pokemonAtIndex.getAtk());
		setDef(pokemonAtIndex.getDef());
		setSpAtk(pokemonAtIndex.getSpAtk());
		setSpDef(pokemonAtIndex.getSpDef());
		setSpeed(pokemonAtIndex.getSpeed());
		setPKRS(pokemonAtIndex.isPKRS());
		setMachoBrace(pokemonAtIndex.isMachoBrace());
		setPowerWeight(pokemonAtIndex.isPowerWeight());
		setPowerBracer(pokemonAtIndex.isPowerBracer());
		setPowerBelt(pokemonAtIndex.isPowerBelt());
		setPowerLens(pokemonAtIndex.isPowerLens());
		setPowerBand(pokemonAtIndex.isPowerBand());
		setPowerAnklet(pokemonAtIndex.isPowerAnklet());
	}

	/**
	* Adding EV Values
	*/
	public void addHP(int value)
	{
		mHp += value;
	}
	
	public void addAttack(int value)
	{
		mAtk += value;
	}
	
	public void addDefense(int value)
	{
		mDef += value;
	}
	
	public void addSpAttack(int value)
	{
		mSpAtk += value;
	}
	
	public void addSpDefense(int value)
	{
		mSpDef += value;
	}
	
	public void addSpeed(int value)
	{
		mSpeed += value;
	}
	
	public void addPokemon(EVPokemonOld p)
	{
		// Set multiplier based on if pokemon has PKRS and is
		// holding Macho Brace
		int multiplier = 1;
		
		if (mPKRS && mMachoBrace)
		{
			multiplier = 4;
		}
		else if (mMachoBrace || mPKRS)
		{
			multiplier = 2;
		}
		else
		{
			multiplier = 1;
		}
		
		if (mPowerWeight) {
			addHP((p.mHp + 4) * multiplier);
		} else {
			addHP((p.mHp) * multiplier);
		}
		
		if (mPowerBracer) {
			addAttack((p.mAtk + 4) * multiplier);
		} else {
			addAttack((p.mAtk) * multiplier);
		}
		
		if (mPowerBelt) {
			addDefense((p.mDef + 4) * multiplier);
		} else {
			addDefense((p.mDef) * multiplier);
		}
		
		if (mPowerLens) {
			addSpAttack((p.mSpAtk + 4) * multiplier);
		} else {
			addSpAttack((p.mSpAtk) * multiplier);
		}
		
		if (mPowerBand) {
			addSpDefense((p.mSpDef + 4) * multiplier);
		} else {
			addSpDefense((p.mSpDef) * multiplier);
		}
		
		if (mPowerAnklet) {
			addSpeed((p.mSpeed + 4) * multiplier);
		} else {
			addSpeed((p.mSpeed) * multiplier);
		}
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
	
	public int getTotal()
	{
		return (mHp + mAtk + mDef + mSpAtk + mSpDef + mSpeed);
	}
	
	public Boolean isPKRS() {
		return mPKRS;
	}
	
	public Boolean isMachoBrace() {
		return mMachoBrace;
	}
	
	public Boolean isPowerWeight() {
		return mPowerWeight;
	}
	
	public Boolean isPowerBracer() {
		return mPowerBracer;
	}
	
	public Boolean isPowerBelt() {
		return mPowerBelt;
	}
	
	public Boolean isPowerLens() {
		return mPowerLens;
	}
	
	public Boolean isPowerBand() {
		return mPowerBand;
	}
	
	public Boolean isPowerAnklet() {
		return mPowerAnklet;
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
	
	public void setPKRS(Boolean pKRS) {
		mPKRS = pKRS;
	}
	
	public void setMachoBrace(Boolean machoBrace) {
		mMachoBrace = machoBrace;
	}
	
	public void setPowerWeight(Boolean powerWeight) {
		mPowerWeight = powerWeight;
	}
	
	public void setPowerBracer(Boolean powerBracer) {
		mPowerBracer = powerBracer;
	}
	
	public void setPowerBelt(Boolean powerBelt) {
		mPowerBelt = powerBelt;
	}
	
	public void setPowerLens(Boolean powerLens) {
		mPowerLens = powerLens;
	}
	
	public void setPowerBand(Boolean powerBand) {
		mPowerBand = powerBand;
	}
	
	public void setPowerAnklet(Boolean powerAnklet) {
		mPowerAnklet = powerAnklet;
	}
	
	@Override
	public String toString() {
		return "[National Number=" + mPokemonNumber
				+ ", Pokemon Name=" + mPokemonName + ", HP=" + mHp + ", Atk="
				+ mAtk + ", Def=" + mDef + ", SpAtk=" + mSpAtk + ", SpDef="
				+ mSpDef + ", Speed=" + mSpeed + "]";
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
		json.put(JSON_PKRS, mPKRS);
		json.put(JSON_MACHO_BRACE, mMachoBrace);
		json.put(JSON_POWER_WEIGHT, mPowerWeight);
		json.put(JSON_POWER_BRACER, mPowerBracer);
		json.put(JSON_POWER_BELT, mPowerBelt);
		json.put(JSON_POWER_BAND, mPowerBand);
		json.put(JSON_POWER_LENS, mPowerLens);
		json.put(JSON_POWER_ANKLET, mPowerAnklet);
		
		return json;
	}
	
}