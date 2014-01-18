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

public class PokemonAdapter extends ArrayAdapter<EVPokemon> implements Filterable {

	private ArrayList<EVPokemon> original;
	private ArrayList<EVPokemon> filtered;
	private Activity mContext;

	public PokemonAdapter(ArrayList<EVPokemon> allPokemon, Activity context)
	{			
		super(context, 0, allPokemon);
		original = new ArrayList<EVPokemon>(allPokemon);
		filtered = new ArrayList<EVPokemon>(allPokemon);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = mContext.getLayoutInflater().inflate(R.layout.list_item_pokemon, null);
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
