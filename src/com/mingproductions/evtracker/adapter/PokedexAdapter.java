package com.mingproductions.evtracker.adapter;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingproductions.evtracker.R;
import com.mingproductions.evtracker.model.EVPokemon;

public class PokedexAdapter extends ArrayAdapter<EVPokemon> implements Filterable {

	private ArrayList<EVPokemon> original;
	private ArrayList<EVPokemon> filtered;
	private Activity mContext;

	public PokedexAdapter(ArrayList<EVPokemon> pokedex, Activity context)
	{
		super(context, 0, pokedex);
		original = new ArrayList<EVPokemon>(pokedex);
		filtered = new ArrayList<EVPokemon>(pokedex);
		mContext = context;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = mContext.getLayoutInflater().inflate(R.layout.list_item_pokedex, null);
		}

		Resources resource = mContext.getResources();
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
			firstEV.setTextColor(resource.getColor(R.color.Red));
			firstEVDetail.setTextColor(resource.getColor(R.color.Red));
		}
		if (p.getAtk() > 0)
		{
			if (firstEV.getVisibility() == View.INVISIBLE)
			{
				firstEV.setVisibility(View.VISIBLE);
				firstEVDetail.setVisibility(View.VISIBLE);

				firstEV.setText("Atk");
				firstEVDetail.setText("" + p.getAtk());
				firstEV.setTextColor(resource.getColor(R.color.Orange));
				firstEVDetail.setTextColor(resource.getColor(R.color.Orange));
			} else {
				secondEV.setVisibility(View.VISIBLE);
				secondEVDetail.setVisibility(View.VISIBLE);

				secondEV.setText("Atk");
				secondEVDetail.setText("" + p.getAtk());
				secondEV.setTextColor(resource.getColor(R.color.Orange));
				secondEVDetail.setTextColor(resource.getColor(R.color.Orange));
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
				firstEV.setTextColor(resource.getColor(R.color.Yellow));
				firstEVDetail.setTextColor(resource.getColor(R.color.Yellow));
			} else if (secondEV.getVisibility() == View.INVISIBLE){
				secondEV.setVisibility(View.VISIBLE);
				secondEVDetail.setVisibility(View.VISIBLE);

				secondEV.setText("Def");
				secondEVDetail.setText("" + p.getDef());
				secondEV.setTextColor(resource.getColor(R.color.Yellow));
				secondEVDetail.setTextColor(resource.getColor(R.color.Yellow));
			} else {
				thirdEV.setVisibility(View.VISIBLE);
				thirdEVDetail.setVisibility(View.VISIBLE);

				thirdEV.setText("Def");
				thirdEVDetail.setText("" + p.getDef());
				thirdEV.setTextColor(resource.getColor(R.color.Yellow));
				thirdEVDetail.setTextColor(resource.getColor(R.color.Yellow));
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
				firstEV.setTextColor(resource.getColor(R.color.Light_Blue));
				firstEVDetail.setTextColor(resource.getColor(R.color.Light_Blue));
			} else if (secondEV.getVisibility() == View.INVISIBLE){
				secondEV.setVisibility(View.VISIBLE);
				secondEVDetail.setVisibility(View.VISIBLE);

				secondEV.setText("SpAtk");
				secondEVDetail.setText("" + p.getSpAtk());
				secondEV.setTextColor(resource.getColor(R.color.Light_Blue));
				secondEVDetail.setTextColor(resource.getColor(R.color.Light_Blue));
			} else {
				thirdEV.setVisibility(View.VISIBLE);
				thirdEVDetail.setVisibility(View.VISIBLE);

				thirdEV.setText("SpAtk");
				thirdEVDetail.setText("" + p.getSpAtk());
				thirdEV.setTextColor(resource.getColor(R.color.Light_Blue));
				thirdEVDetail.setTextColor(resource.getColor(R.color.Light_Blue));
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
				firstEV.setTextColor(resource.getColor(R.color.Green));
				firstEVDetail.setTextColor(resource.getColor(R.color.Green));
			} else if (secondEV.getVisibility() == View.INVISIBLE){
				secondEV.setVisibility(View.VISIBLE);
				secondEVDetail.setVisibility(View.VISIBLE);

				secondEV.setText("SpDef");
				secondEVDetail.setText("" + p.getSpDef());
				secondEV.setTextColor(resource.getColor(R.color.Green));
				secondEVDetail.setTextColor(resource.getColor(R.color.Green));
			} else {
				thirdEV.setVisibility(View.VISIBLE);
				thirdEVDetail.setVisibility(View.VISIBLE);

				thirdEV.setText("SpDef");
				thirdEVDetail.setText("" + p.getSpDef());
				thirdEV.setTextColor(resource.getColor(R.color.Green));
				thirdEVDetail.setTextColor(resource.getColor(R.color.Green));
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
				firstEV.setTextColor(resource.getColor(R.color.Purple));
				firstEVDetail.setTextColor(resource.getColor(R.color.Purple));
			} else if (secondEV.getVisibility() == View.INVISIBLE){
				secondEV.setVisibility(View.VISIBLE);
				secondEVDetail.setVisibility(View.VISIBLE);

				secondEV.setText("Speed");
				secondEVDetail.setText("" + p.getSpeed());
				secondEV.setTextColor(resource.getColor(R.color.Purple));
				secondEVDetail.setTextColor(resource.getColor(R.color.Purple));
			} else {
				thirdEV.setVisibility(View.VISIBLE);
				thirdEVDetail.setVisibility(View.VISIBLE);

				thirdEV.setText("Speed");
				thirdEVDetail.setText("" + p.getSpeed());
				thirdEV.setTextColor(resource.getColor(R.color.Purple));
				thirdEVDetail.setTextColor(resource.getColor(R.color.Purple));
			}
		}

		return convertView;
	}

	Filter filter = new Filter() {

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			filtered = (ArrayList<EVPokemon>)results.values;
			notifyDataSetChanged();
			clear();
			for (EVPokemon p : filtered)
			{
				add(p);
				notifyDataSetInvalidated();
			}
		}

		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults filterResults = new FilterResults();
			if(constraint != null && filtered != null)
			{
				ArrayList<EVPokemon> tempList = new ArrayList<EVPokemon>();
				for (EVPokemon p : original)
				{
					if (p.getPokemonName().toLowerCase(Locale.US).contains(constraint.toString().toLowerCase(Locale.US)))
					{
						tempList.add(p);
					} 
					else if (Integer.toString(p.getPokemonNumber()).contains(constraint))
					{
						tempList.add(p);
					}
				}
				filterResults.values = tempList;
				filterResults.count = tempList.size();
			}
			else 
			{
				ArrayList<EVPokemon> tempList = new ArrayList<EVPokemon>(original);
				filterResults.values = tempList;
				filterResults.count = tempList.size();
			}

			return filterResults;
		}
	};

	@Override
	public Filter getFilter()
	{
		return filter;
	}

}
