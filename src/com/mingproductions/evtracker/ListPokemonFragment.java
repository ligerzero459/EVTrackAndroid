package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;

public class ListPokemonFragment extends SherlockListFragment {
	
	private ArrayList<EVPokemon> mAllPokemon;
	private int mGamePos;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		mGamePos = (int)getArguments().getInt(ListGameFragment.EXTRA_GAME_POSITION);
		mAllPokemon = GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).getAllPokemon();

		PokemonAdapter adapter = new PokemonAdapter(mAllPokemon);
		setListAdapter(adapter);
		
		setHasOptionsMenu(true);
		setRetainInstance(true);

		// Set the title & image to the correct game
		getActivity().setTitle(GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).getGameName());

		Resources resource = getResources();

		int imageId = resource.getIdentifier("com.mingproductions.evtracker:drawable/" 
				+ GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).getImageName()
				, null, null);
		((ListPokemonActivity)getActivity()).getSupportActionBar().setLogo(imageId);
		if (NavUtils.getParentActivityName(getActivity()) != null)
		{
			getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		((PokemonAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_pokemon_list, parent, false);
		
		Button newPokemonButton = (Button)v.findViewById(R.id.new_pokemon_button);
		newPokemonButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), NewPokemonActivity.class);
				
				Bundle b = new Bundle();
				b.putInt(ListGameFragment.EXTRA_GAME_POSITION, mGamePos);
				
				i.putExtras(b);
				startActivity(i);
			}
		});
		
		ListView listView = (ListView)v.findViewById(android.R.id.list);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
		{
			registerForContextMenu(listView);
		} 
		else 
		{
			// Use contextual action bar on Honeycomb and higher
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

				@Override
				public boolean onPrepareActionMode(ActionMode mode, android.view.Menu menu) {
					// Required, but not used in this implementation
					return false;
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// Required, but not used in this implementation

				}

				@Override
				public boolean onCreateActionMode(ActionMode mode, android.view.Menu menu) {
					android.view.MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.fragment_pokemon_delete, menu);
					return true;
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode, android.view.MenuItem item) {
					switch (item.getItemId())
					{
					case R.id.menu_item_delete_pokemon:
					{
						PokemonAdapter adapter = (PokemonAdapter)getListAdapter();

						for (int i = adapter.getCount() - 1; i >= 0; i--)
						{
							if (getListView().isItemChecked(i))
							{
								GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).removePokemon(adapter.getItem(i));
							}
						}
						mode.finish();
						GameStore.sharedStore(getActivity()).saveGames();
						adapter.notifyDataSetChanged();
						return true;
					}
					default:
						return false;
					}
				}

				@Override
				public void onItemCheckedStateChanged(ActionMode mode, int position,
						long id, boolean checked) {
					// Required, but not used in this implementation

				}
			});
		}

		return v;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		getActivity().getMenuInflater().inflate(R.menu.fragment_pokemon_delete, menu);
	}
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;
		PokemonAdapter adapter = (PokemonAdapter)getListAdapter();
		EVPokemon pokemon = adapter.getItem(position);
		
		switch(item.getItemId())
		{
		case R.id.menu_item_delete_pokemon:
		{
			GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).removePokemon(pokemon);
			adapter.notifyDataSetChanged();
			return true;
		}
		default:
			return false;
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Intent i = new Intent(getActivity(), EVDetailActivity.class);
		
		Bundle b = new Bundle();
		b.putInt("pokemon", position);
		b.putInt("game", mGamePos);
		i.putExtras(b);
		
		startActivity(i);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_pokemon_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getSherlockActivity()) != null)
			{
				NavUtils.navigateUpFromSameTask(getSherlockActivity());
			}
			return true;
		case R.id.menu_item_new_pokemon:
		{
			Intent i = new Intent(getActivity(), NewPokemonActivity.class);
			
			Bundle b = new Bundle();
			b.putInt(ListGameFragment.EXTRA_GAME_POSITION, mGamePos);
			
			i.putExtras(b);
			startActivity(i);
			return true;
		}
		case R.id.menu_item_pokedex:
		{
			Intent i = new Intent(getActivity(), ListPokedexActivity.class);
			startActivity(i);
		}
		default:
			return false;
		}
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
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_ev_pokemon, null);
			}
			
			Resources resource = getResources();
			EVPokemon p = getItem(position);
			
			ImageView pokemonImage = (ImageView)convertView.findViewById(R.id.image_pokemon);
			
			if (p.getPokemonNumber() < 10)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p00" + p.getPokemonNumber(), null, null));
			else if (p.getPokemonNumber() < 100)
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p0" + p.getPokemonNumber(), null, null));
			else
				pokemonImage.setImageResource(resource.getIdentifier("com.mingproductions.evtracker:drawable/p" + p.getPokemonNumber(), null, null));
			
			TextView pokemonName = (TextView)convertView.findViewById(R.id.name_pokemon);
			pokemonName.setText(p.getPokemonName());
			
			TextView hp = (TextView)convertView.findViewById(R.id.hp);
			hp.setText("" + p.getHp());
			TextView atk = (TextView)convertView.findViewById(R.id.atk);
			atk.setText("" + p.getAtk());
			TextView def = (TextView)convertView.findViewById(R.id.def);
			def.setText("" + p.getDef());
			TextView spAtk = (TextView)convertView.findViewById(R.id.spatk);
			spAtk.setText("" + p.getSpAtk());
			TextView spDef = (TextView)convertView.findViewById(R.id.spdef);
			spDef.setText("" + p.getSpDef());
			TextView speed = (TextView)convertView.findViewById(R.id.speed);
			speed.setText("" + p.getSpeed());
			
			return convertView;
		}
	}
	
	public static ListPokemonFragment newInstance(int position)
	{
		Bundle args = new Bundle();
		args.putInt(ListGameFragment.EXTRA_GAME_POSITION, position);
		
		ListPokemonFragment fragment = new ListPokemonFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
