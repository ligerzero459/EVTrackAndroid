package com.mingproductions.evtracker;

import java.util.ArrayList;
import java.util.Locale;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.PokedexStore;
import com.mingproductions.evtracker.model.PokemonGame;

public class NewPokemonFragment extends SherlockListFragment {
	// TODO: Implement search bar

	private ArrayList<EVPokemon> mAllPokemon;
	private int mGamePos;
	
	private PokemonAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);

		mGamePos = (int)getArguments().getInt(ListGameFragment.EXTRA_GAME_POSITION);
		mAllPokemon = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getActivity()).allPokemon());

		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}

		adapter = new PokemonAdapter(mAllPokemon);
		setListAdapter(adapter);

		setRetainInstance(true);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		mAllPokemon = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getActivity()).allPokemon());
		adapter.notifyDataSetChanged();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_search_list, parent, false);
		
		EditText searchBox = (EditText)v.findViewById(R.id.inputSearch);
		searchBox.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Required, but not used				
			}

			@Override
			public void afterTextChanged(Editable s) {
				// Required, but not used
			}
		});

		return v;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		int selectedIndex = mAllPokemon.indexOf(l.getItemAtPosition(position));
		EVPokemon newPokemon = new EVPokemon(mAllPokemon.get(selectedIndex).getPokemonNumber(), 
				mAllPokemon.get(selectedIndex).getPokemonName());

		/**
		 * This super long line gets the GameStore singleton, gets all the 
		 * games from the GameStore, get the selected game, then get a
		 * reference to the mAllPokemon array and then add the newPokemon
		 * to the game array
		 **/
		GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).addPokemon(newPokemon);
		GameStore.sharedStore(getActivity()).saveGames();

		getActivity().finish();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getSherlockActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class PokemonAdapter extends ArrayAdapter<EVPokemon> implements Filterable
	{
		public ArrayList<EVPokemon> original;
		public ArrayList<EVPokemon> filtered;

		public PokemonAdapter(ArrayList<EVPokemon> allPokemon)
		{			
			super(getActivity(), 0, allPokemon);
			original = new ArrayList<EVPokemon>(allPokemon);
			filtered = new ArrayList<EVPokemon>(allPokemon);
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_pokemon, null);
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

	public static NewPokemonFragment newInstance(int position)
	{
		Bundle args = new Bundle();
		args.putInt(ListGameFragment.EXTRA_GAME_POSITION, position);

		NewPokemonFragment fragment = new NewPokemonFragment();
		fragment.setArguments(args);

		return fragment;
	}
}
