package com.mingproductions.evtracker;

import java.util.ArrayList;
import java.util.Locale;

import android.content.Intent;
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

public class EVBattledPokemonFragment extends SherlockListFragment {

	private ArrayList<EVPokemon> mPokedex;
	private EVPokemon mPokemon;
	private int mGamePos;
	private int mPokemonPos;

	private BattledAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);

		mGamePos = getArguments().getInt("game");
		mPokemonPos = getArguments().getInt("mPokemon");

		mPokedex = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getActivity()).allPokemon());
		mPokemon = GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).pokemonAtIndex(mPokemonPos);

		adapter = new BattledAdapter(mPokedex);
		setListAdapter(adapter);

		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			((EVBattledPokemonActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		mPokedex = new ArrayList<EVPokemon>(PokedexStore.sharedStore(getActivity()).allPokemon());
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

	public void onListItemClick(ListView l, View v, int position, long id)
	{
		EVPokemon selectedP = (EVPokemon)l.getItemAtPosition(position);

		mPokemon.addPokemon(selectedP);

		GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).replacePokemon(mPokemonPos, mPokemon);

		getActivity().finish();
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_default_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		case R.id.menu_item_pokedex:
		{
			Intent i = new Intent(getActivity(), ListPokedexActivity.class);
			startActivity(i);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static EVBattledPokemonFragment newInstance(int pokemonPos, int gamePos)
	{
		Bundle args = new Bundle();
		args.putInt("mPokemon", pokemonPos);
		args.putInt("game", gamePos);

		EVBattledPokemonFragment fragment = new EVBattledPokemonFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private class BattledAdapter extends ArrayAdapter<EVPokemon>
	{
		public ArrayList<EVPokemon> original;
		public ArrayList<EVPokemon> filtered;

		public BattledAdapter(ArrayList<EVPokemon> pokedex)
		{
			super(getActivity(), 0, pokedex);
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
}
