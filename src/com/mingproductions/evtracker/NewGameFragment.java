package com.mingproductions.evtracker;

import java.util.ArrayList;
import java.util.Locale;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;
import com.mingproductions.evtracker.model.GameTableStore;
import com.mingproductions.evtracker.model.PokemonGame;

public class NewGameFragment extends SherlockListFragment {

	private ArrayList<PokemonGame> mAllGames;
	private GameAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);

		mAllGames = new ArrayList<PokemonGame>(GameTableStore.sharedStore(getActivity()).allGames());

		adapter = new GameAdapter(mAllGames);
		setListAdapter(adapter);
		
		setHasOptionsMenu(true);
		
		FragmentStorage.sharedStore(getActivity()).addFragmentToList(this);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		getSherlockActivity().getSupportActionBar().setTitle("New Game");
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		adapter.notifyDataSetChanged();
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_search_list, parent, false);
		
		final Fragment myself = this;
		OnKeyListener pressed = new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if (keyCode == KeyEvent.KEYCODE_BACK)
				{
					FragmentStorage.sharedStore(getActivity()).removeFragmentFromList(myself);
					return true;
				}
				return false;
			}
		};
		v.setOnKeyListener(pressed);

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
		PokemonGame newGame = (PokemonGame)l.getItemAtPosition(position);

		GameStore.sharedStore(getActivity()).addGame(newGame);
		GameStore.sharedStore(getActivity()).saveGames();

		getFragmentManager().popBackStackImmediate();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case android.R.id.home:
			FragmentStorage.sharedStore(getActivity()).removeFragmentFromList(this);
			getFragmentManager().popBackStackImmediate();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class GameAdapter extends ArrayAdapter<PokemonGame>
	{
		public ArrayList<PokemonGame> original;
		public ArrayList<PokemonGame> filtered;

		public GameAdapter(ArrayList<PokemonGame> allGames)
		{
			super(getActivity(), 0, allGames);
			original = new ArrayList<PokemonGame>(allGames);
			filtered = new ArrayList<PokemonGame>(allGames);
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_game, null);
			}

			PokemonGame game = getItem(position);
			Resources resource = getResources();

			ImageView gameImage = (ImageView)convertView.findViewById(R.id.game_image);
			gameImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/" 
					+ game.getImageName(), null, null));

			TextView gameNameText = (TextView)convertView.findViewById(R.id.game_name);
			gameNameText.setText(game.getGameName());

			return convertView;
		}
		
		Filter filter = new Filter() {

			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				filtered = (ArrayList<PokemonGame>)results.values;
				notifyDataSetChanged();
				clear();
				for (PokemonGame g : filtered)
				{
					add(g);
					notifyDataSetInvalidated();
				}
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults filterResults = new FilterResults();
				if(constraint != null && filtered != null)
				{
					ArrayList<PokemonGame> tempList = new ArrayList<PokemonGame>();
					for (PokemonGame g : original)
					{
						if (g.getGameName().toLowerCase(Locale.US).contains(constraint.toString().toLowerCase(Locale.US)))
						{
							tempList.add(g);
						}
					}
					filterResults.values = tempList;
					filterResults.count = tempList.size();
				}
				else 
				{
					ArrayList<PokemonGame> tempList = new ArrayList<PokemonGame>(original);
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
