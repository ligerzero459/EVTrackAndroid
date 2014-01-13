package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.PokedexStore;

public class ListPokedexFragment extends SherlockListFragment {
	
	private ArrayList<EVPokemon> mAllPokemon;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		mAllPokemon = PokedexStore.sharedStore(getActivity()).allPokemon();
		
		PokemonAdapter adapter = new PokemonAdapter(mAllPokemon);
		setListAdapter(adapter);
		
		setRetainInstance(true);
	}
	
	private class PokemonAdapter extends ArrayAdapter<EVPokemon>
	{
		public PokemonAdapter(ArrayList<EVPokemon> allPokemon)
		{
			super(getActivity(), 0, allPokemon);
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_pokedex, null);
			}
			
			Resources resource = getResources();
			EVPokemon p = getItem(position);
			
			ImageView pokemonImage = (ImageView)convertView.findViewById(R.id.pokemon_image);
			if (p.getPokemonNumber() < 10)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p00" + p.getPokemonNumber(), null, null));
			else if (p.getPokemonNumber() < 100)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p0" + p.getPokemonNumber(), null, null));
			else
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p" + p.getPokemonNumber(), null, null));
			
			TextView pokemonName = (TextView)convertView.findViewById(R.id.pokemon_name);
			pokemonName.setText("#" + p.getPokemonNumber() + " " + p.getPokemonName());
			
			TextView firstEV = (TextView)convertView.findViewById(R.id.ev1_label);
			TextView firstEVDetail = (TextView)convertView.findViewById(R.id.ev1);
			TextView secondEV = (TextView)convertView.findViewById(R.id.ev2_label);
			TextView secondEVDetail = (TextView)convertView.findViewById(R.id.ev2);
			TextView thirdEV = (TextView)convertView.findViewById(R.id.ev3_label);
			TextView thirdEVDetail = (TextView)convertView.findViewById(R.id.ev3);
			
			firstEV.setVisibility(View.INVISIBLE);
			firstEVDetail.setVisibility(View.INVISIBLE);
			secondEV.setVisibility(View.INVISIBLE);
			secondEVDetail.setVisibility(View.INVISIBLE);
			thirdEV.setVisibility(View.INVISIBLE);
			thirdEVDetail.setVisibility(View.INVISIBLE);
			
			if (p.getHp() > 0)
			{
				firstEV.setVisibility(View.VISIBLE);
				firstEVDetail.setVisibility(View.VISIBLE);
				
				firstEV.setText("HP");
				firstEVDetail.setText("" + p.getHp());
				firstEV.setTextColor(getResources().getColor(R.color.Red));
				firstEVDetail.setTextColor(getResources().getColor(R.color.Red));
			}
			if (p.getAtk() > 0)
			{
				if (firstEV.getVisibility() == View.INVISIBLE)
				{
					firstEV.setVisibility(View.VISIBLE);
					firstEVDetail.setVisibility(View.VISIBLE);
					
					firstEV.setText("Atk");
					firstEVDetail.setText("" + p.getAtk());
					firstEV.setTextColor(getResources().getColor(R.color.Orange));
					firstEVDetail.setTextColor(getResources().getColor(R.color.Orange));
				} else {
					secondEV.setVisibility(View.VISIBLE);
					secondEVDetail.setVisibility(View.VISIBLE);
					
					secondEV.setText("Atk");
					secondEVDetail.setText("" + p.getAtk());
					secondEV.setTextColor(getResources().getColor(R.color.Orange));
					secondEVDetail.setTextColor(getResources().getColor(R.color.Orange));
				}
			}
			if (p.getDef() > 0)
			{
				if (firstEV.getVisibility() == View.INVISIBLE)
				{
					firstEV.setVisibility(View.VISIBLE);
					firstEVDetail.setVisibility(View.VISIBLE);
					
					firstEV.setText("Def");
					firstEVDetail.setText("" + p.getDef());
					firstEV.setTextColor(getResources().getColor(R.color.Yellow));
					firstEVDetail.setTextColor(getResources().getColor(R.color.Yellow));
				} else if (secondEV.getVisibility() == View.INVISIBLE){
					secondEV.setVisibility(View.VISIBLE);
					secondEVDetail.setVisibility(View.VISIBLE);
					
					secondEV.setText("Def");
					secondEVDetail.setText("" + p.getDef());
					secondEV.setTextColor(getResources().getColor(R.color.Yellow));
					secondEVDetail.setTextColor(getResources().getColor(R.color.Yellow));
				} else {
					thirdEV.setVisibility(View.VISIBLE);
					thirdEVDetail.setVisibility(View.VISIBLE);
					
					thirdEV.setText("Def");
					thirdEVDetail.setText("" + p.getDef());
					thirdEV.setTextColor(getResources().getColor(R.color.Yellow));
					thirdEVDetail.setTextColor(getResources().getColor(R.color.Yellow));
				}
			}
			if (p.getSpAtk() > 0)
			{
				if (firstEV.getVisibility() == View.INVISIBLE)
				{
					firstEV.setVisibility(View.VISIBLE);
					firstEVDetail.setVisibility(View.VISIBLE);
					
					firstEV.setText("SpAtk");
					firstEVDetail.setText("" + p.getSpAtk());
					firstEV.setTextColor(getResources().getColor(R.color.Light_Blue));
					firstEVDetail.setTextColor(getResources().getColor(R.color.Light_Blue));
				} else if (secondEV.getVisibility() == View.INVISIBLE){
					secondEV.setVisibility(View.VISIBLE);
					secondEVDetail.setVisibility(View.VISIBLE);
					
					secondEV.setText("SpAtk");
					secondEVDetail.setText("" + p.getSpAtk());
					secondEV.setTextColor(getResources().getColor(R.color.Light_Blue));
					secondEVDetail.setTextColor(getResources().getColor(R.color.Light_Blue));
				} else {
					thirdEV.setVisibility(View.VISIBLE);
					thirdEVDetail.setVisibility(View.VISIBLE);
					
					thirdEV.setText("SpAtk");
					thirdEVDetail.setText("" + p.getSpAtk());
					thirdEV.setTextColor(getResources().getColor(R.color.Light_Blue));
					thirdEVDetail.setTextColor(getResources().getColor(R.color.Light_Blue));
				}
			}
			if (p.getSpDef() > 0)
			{
				if (firstEV.getVisibility() == View.INVISIBLE)
				{
					firstEV.setVisibility(View.VISIBLE);
					firstEVDetail.setVisibility(View.VISIBLE);
					
					firstEV.setText("SpDef");
					firstEVDetail.setText("" + p.getSpDef());
					firstEV.setTextColor(getResources().getColor(R.color.Green));
					firstEVDetail.setTextColor(getResources().getColor(R.color.Green));
				} else if (secondEV.getVisibility() == View.INVISIBLE){
					secondEV.setVisibility(View.VISIBLE);
					secondEVDetail.setVisibility(View.VISIBLE);
					
					secondEV.setText("SpDef");
					secondEVDetail.setText("" + p.getSpDef());
					secondEV.setTextColor(getResources().getColor(R.color.Green));
					secondEVDetail.setTextColor(getResources().getColor(R.color.Green));
				} else {
					thirdEV.setVisibility(View.VISIBLE);
					thirdEVDetail.setVisibility(View.VISIBLE);
					
					thirdEV.setText("SpDef");
					thirdEVDetail.setText("" + p.getSpDef());
					thirdEV.setTextColor(getResources().getColor(R.color.Green));
					thirdEVDetail.setTextColor(getResources().getColor(R.color.Green));
				}
			}
			if (p.getSpeed() > 0)
			{
				if (firstEV.getVisibility() == View.INVISIBLE)
				{
					firstEV.setVisibility(View.VISIBLE);
					firstEVDetail.setVisibility(View.VISIBLE);
					
					firstEV.setText("Speed");
					firstEVDetail.setText("" + p.getSpeed());
					firstEV.setTextColor(getResources().getColor(R.color.Purple));
					firstEVDetail.setTextColor(getResources().getColor(R.color.Purple));
				} else if (secondEV.getVisibility() == View.INVISIBLE){
					secondEV.setVisibility(View.VISIBLE);
					secondEVDetail.setVisibility(View.VISIBLE);
					
					secondEV.setText("Speed");
					secondEVDetail.setText("" + p.getSpeed());
					secondEV.setTextColor(getResources().getColor(R.color.Purple));
					secondEVDetail.setTextColor(getResources().getColor(R.color.Purple));
				} else {
					thirdEV.setVisibility(View.VISIBLE);
					thirdEVDetail.setVisibility(View.VISIBLE);
					
					thirdEV.setText("Speed");
					thirdEVDetail.setText("" + p.getSpeed());
					thirdEV.setTextColor(getResources().getColor(R.color.Purple));
					thirdEVDetail.setTextColor(getResources().getColor(R.color.Purple));
				}
			}
			
			return convertView;
		}
	}

}
