package com.mingproductions.evtracker;

public class EVPokemon {
	/**
	 * Static Variables
	 */
	
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
	
	public EVPokemon()
	{
		// Default Constructor
		this(1, "Pokemon");
	}
	
	public EVPokemon(int pokemonNumber, String pokemonName)
	{
		/** Standard Constructor
		 	Used for adding Pokemon at runtime*/
		this(pokemonNumber, pokemonName, 1, 0, 0, 0, 0, 0, 0);
	}
	
	public EVPokemon(int pokemonNumber, String pokemonName, int level, int hp, int atk, int def, int spAtk, int spDef, 
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
	
}