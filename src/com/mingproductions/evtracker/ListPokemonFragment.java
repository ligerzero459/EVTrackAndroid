package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
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
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;

public class ListPokemonFragment extends SherlockListFragment {
	
	private ArrayList<EVPokemon> mAllPokemon;
	private int mGamePos;
	private PokemonAdapter adapter;
	
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		try
		{
			super.onCreate(savedInstanceBundle);
			mGamePos = (int)getArguments().getInt(ListGameFragment.EXTRA_GAME_POSITION);
			mAllPokemon = GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).getAllPokemon();

			adapter = new PokemonAdapter(mAllPokemon);
			setListAdapter(adapter);

			setHasOptionsMenu(true);

			// Set the title & image to the correct game
			getSherlockActivity().setTitle(GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).getGameName());

			Resources resource = getResources();

			int imageId = resource.getIdentifier("com.mingproductions.evtracker:drawable/" 
					+ GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).getImageName()
					, null, null);
			getSherlockActivity().getSupportActionBar().setLogo(imageId);

			FragmentStorage.sharedStore(getSherlockActivity()).addFragmentToList(this);
		}
		catch (Exception ex)
		{
			FragmentStorage.sharedStore(getSherlockActivity()).clearFragmentList();
			getSherlockActivity().getSupportActionBar().selectTab(getSherlockActivity().getSupportActionBar().getTabAt(0));
		}
	}
	
	@Override
	public void onResume()
	{
		try
		{
			super.onResume();
			// Set the title & image to the correct game
			getSherlockActivity().getSupportActionBar().setTitle(GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).getGameName());

			Resources resource = getResources();

			int imageId = resource.getIdentifier("com.mingproductions.evtracker:drawable/" 
					+ GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).getImageName()
					, null, null);
			getSherlockActivity().getSupportActionBar().setLogo(imageId);	
			// Enable Up button
			getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			((PokemonAdapter)getListAdapter()).notifyDataSetChanged();
		}
		catch (Exception ex)
		{
			FragmentStorage.sharedStore(getSherlockActivity()).clearFragmentList();
			getSherlockActivity().getSupportActionBar().selectTab(getSherlockActivity().getSupportActionBar().getTabAt(0));
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_pokemon_list, parent, false);
		
		final Fragment myself = this;
		v.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				if (keyCode == KeyEvent.KEYCODE_BACK)
				{
					FragmentStorage.sharedStore(getSherlockActivity()).removeFragmentFromList(myself);
					return true;
				}
				return false;
			}
		});
		
		Button newPokemonButton = (Button)v.findViewById(R.id.new_pokemon_button);
		newPokemonButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getSherlockActivity().getSupportFragmentManager().beginTransaction().replace(R.id.host_view, NewPokemonFragment.newInstance(mGamePos))
				.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
			}
		});
		
		ListView listView = (ListView)v.findViewById(android.R.id.list);
		//listView.setAdapter(adapter);
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
				public boolean onActionItemClicked(final ActionMode mode, android.view.MenuItem item) {
					switch (item.getItemId())
					{
					case R.id.menu_item_delete_pokemon:
					{
						final PokemonAdapter adapter = (PokemonAdapter)getListAdapter();
						
						Builder dialog = new AlertDialog.Builder(getSherlockActivity()).setTitle("Delete Pokemon")
								.setMessage("Are you sure you want to delete these pokemon?");
						dialog.setNegativeButton("No", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								mode.finish();
							}
						});
						dialog.setPositiveButton("Delete", new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								for (int i = adapter.getCount() - 1; i >= 0; i--)
								{
									if (getListView().isItemChecked(i))
									{
										GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).removePokemon(adapter.getItem(i));
									}
								}
								GameStore.sharedStore(getSherlockActivity()).saveGames();
								adapter.notifyDataSetInvalidated();
								adapter.notifyDataSetChanged();
								mode.finish();
							}
						});
						dialog.create().show();
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
		getSherlockActivity().getMenuInflater().inflate(R.menu.fragment_pokemon_delete, menu);
	}
	
	@Override
	public boolean onContextItemSelected(android.view.MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int position = info.position;
		final PokemonAdapter adapter = (PokemonAdapter)getListAdapter();
		final EVPokemon pokemon = adapter.getItem(position);
		
		switch(item.getItemId())
		{
		case R.id.menu_item_delete_pokemon:
		{
			Builder dialog = new AlertDialog.Builder(getSherlockActivity()).setTitle("Delete Pokemon")
					.setMessage("Are you sure you want to delete " + pokemon.getPokemonName() + "?");
			dialog.setNegativeButton("No", null);
			dialog.setPositiveButton("Delete", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					GameStore.sharedStore(getSherlockActivity()).gameAtIndex(mGamePos).removePokemon(pokemon);
					adapter.notifyDataSetInvalidated();
					adapter.notifyDataSetChanged();
				}
			});
			dialog.create().show();
			return true;
		}
		default:
			return false;
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		Fragment evDetailFragment = EVDetailFragment.newInstance(position, mGamePos);
		getSherlockActivity().getSupportFragmentManager().beginTransaction().replace(R.id.host_view, evDetailFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
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
			FragmentStorage.sharedStore(getSherlockActivity()).removeFragmentFromList(this);
			getSherlockActivity().getSupportFragmentManager().popBackStackImmediate();
			return true;
		case R.id.menu_item_new_pokemon:
		{
			getSherlockActivity().getSupportFragmentManager().beginTransaction().replace(R.id.host_view, NewPokemonFragment.newInstance(mGamePos))
								.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
			return true;
		}
		default:
			return false;
		}
	}
	
	private class PokemonAdapter extends ArrayAdapter<EVPokemon>
    {
            public PokemonAdapter(ArrayList<EVPokemon> allPokemon)
            {
                    super(getSherlockActivity(), 0, allPokemon);
            }
            
            public View getView(int position, View convertView, ViewGroup parent)
            {
                    if (convertView == null)
                    {
                            convertView = getSherlockActivity().getLayoutInflater().inflate(R.layout.list_item_ev_pokemon, null);
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
