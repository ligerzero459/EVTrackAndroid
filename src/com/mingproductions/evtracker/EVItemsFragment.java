package com.mingproductions.evtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;

public class EVItemsFragment extends SherlockFragment {

	private EVPokemon mPokemon;
	private int mGamePos;
	private int mPokemonPos;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		mGamePos = getArguments().getInt("game");
		mPokemonPos = getArguments().getInt("mPokemon");

		/**
		 * Breakdown: retrieves GameStore, looks for the game at mGamePos and then
		 * finds a specific Pokemon in that game
		 */
		mPokemon = GameStore.sharedStore(getActivity()).gameAtIndex(mGamePos).pokemonAtIndex(mPokemonPos);

		// Set logo in title bar
		if (mPokemon.getPokemonNumber() < 10)
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p00" + mPokemon.getPokemonNumber(), null, null)));
		else if (mPokemon.getPokemonNumber() < 100)
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p0" + mPokemon.getPokemonNumber(), null, null)));
		else
			getSherlockActivity().getSupportActionBar().setLogo(getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p" + mPokemon.getPokemonNumber(), null, null)));
		
		// Set title in bar
		getSherlockActivity().getSupportActionBar().setTitle("EV Items");
		FragmentStorage.sharedStore(getActivity()).addFragmentToList(this);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_ev_items, parent, false);
		
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

		final ToggleButton PKRS = (ToggleButton)v.findViewById(R.id.pkrs_button);
		PKRS.setChecked(mPokemon.isPKRS());
		final ToggleButton MachoBrace = (ToggleButton)v.findViewById(R.id.macho_button);
		MachoBrace.setChecked(mPokemon.isMachoBrace());
		final ToggleButton PowerWeight = (ToggleButton)v.findViewById(R.id.power_weight_button);
		PowerWeight.setChecked(mPokemon.isPowerWeight());
		final ToggleButton PowerBracer = (ToggleButton)v.findViewById(R.id.power_bracer_button);
		PowerBracer.setChecked(mPokemon.isPowerBracer());
		final ToggleButton PowerBelt = (ToggleButton)v.findViewById(R.id.power_belt_button);
		PowerBelt.setChecked(mPokemon.isPowerBelt());
		final ToggleButton PowerLens = (ToggleButton)v.findViewById(R.id.power_lens_button);
		PowerLens.setChecked(mPokemon.isPowerLens());
		final ToggleButton PowerBand = (ToggleButton)v.findViewById(R.id.power_band_button);
		PowerBand.setChecked(mPokemon.isPowerBand());
		final ToggleButton PowerAnklet = (ToggleButton)v.findViewById(R.id.power_anklet_button);
		PowerAnklet.setChecked(mPokemon.isPowerAnklet());

		PKRS.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mPokemon.setPKRS(isChecked);
			}
		});
		MachoBrace.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MachoBrace.setChecked(isChecked);
					PowerWeight.setChecked(false);
					PowerBracer.setChecked(false);
					PowerBelt.setChecked(false);
					PowerLens.setChecked(false);
					PowerBand.setChecked(false);
					PowerAnklet.setChecked(false);


					mPokemon.setMachoBrace(isChecked);
					mPokemon.setPowerWeight(false);
					mPokemon.setPowerBracer(false);
					mPokemon.setPowerBelt(false);
					mPokemon.setPowerLens(false);
					mPokemon.setPowerBand(false);
					mPokemon.setPowerAnklet(false);
				}
				else
					mPokemon.setMachoBrace(false);
			}
		});
		PowerWeight.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MachoBrace.setChecked(false);
					PowerWeight.setChecked(isChecked);
					PowerBracer.setChecked(false);
					PowerBelt.setChecked(false);
					PowerLens.setChecked(false);
					PowerBand.setChecked(false);
					PowerAnklet.setChecked(false);

					mPokemon.setMachoBrace(false);
					mPokemon.setPowerWeight(isChecked);
					mPokemon.setPowerBracer(false);
					mPokemon.setPowerBelt(false);
					mPokemon.setPowerLens(false);
					mPokemon.setPowerBand(false);
					mPokemon.setPowerAnklet(false);
				}
				else
					mPokemon.setPowerWeight(false);
			}
		});
		PowerBracer.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MachoBrace.setChecked(false);
					PowerWeight.setChecked(false);
					PowerBracer.setChecked(isChecked);
					PowerBelt.setChecked(false);
					PowerLens.setChecked(false);
					PowerBand.setChecked(false);
					PowerAnklet.setChecked(false);

					mPokemon.setMachoBrace(false);
					mPokemon.setPowerWeight(false);
					mPokemon.setPowerBracer(isChecked);
					mPokemon.setPowerBelt(false);
					mPokemon.setPowerLens(false);
					mPokemon.setPowerBand(false);
					mPokemon.setPowerAnklet(false);
				}
				else
					mPokemon.setPowerBracer(false);
			}
		});
		PowerBelt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MachoBrace.setChecked(false);
					PowerWeight.setChecked(false);
					PowerBracer.setChecked(false);
					PowerBelt.setChecked(isChecked);
					PowerLens.setChecked(false);
					PowerBand.setChecked(false);
					PowerAnklet.setChecked(false);

					mPokemon.setMachoBrace(false);
					mPokemon.setPowerWeight(false);
					mPokemon.setPowerBracer(false);
					mPokemon.setPowerBelt(isChecked);
					mPokemon.setPowerLens(false);
					mPokemon.setPowerBand(false);
					mPokemon.setPowerAnklet(false);
				}
				else
					mPokemon.setPowerBelt(false);
			}
		});
		PowerBand.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MachoBrace.setChecked(false);
					PowerWeight.setChecked(false);
					PowerBracer.setChecked(false);
					PowerBelt.setChecked(isChecked);
					PowerLens.setChecked(false);
					PowerBand.setChecked(false);
					PowerAnklet.setChecked(false);

					mPokemon.setMachoBrace(false);
					mPokemon.setPowerWeight(false);
					mPokemon.setPowerBracer(false);
					mPokemon.setPowerBelt(false);
					mPokemon.setPowerLens(false);
					mPokemon.setPowerBand(isChecked);
					mPokemon.setPowerAnklet(false);
				}
				else
					mPokemon.setPowerBand(false);
			}
		});
		PowerLens.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MachoBrace.setChecked(false);
					PowerWeight.setChecked(false);
					PowerBracer.setChecked(false);
					PowerBelt.setChecked(false);
					PowerLens.setChecked(isChecked);
					PowerBand.setChecked(false);
					PowerAnklet.setChecked(false);

					mPokemon.setMachoBrace(false);
					mPokemon.setPowerWeight(false);
					mPokemon.setPowerBracer(false);
					mPokemon.setPowerBelt(false);
					mPokemon.setPowerLens(isChecked);
					mPokemon.setPowerBand(false);
					mPokemon.setPowerAnklet(false);
				}
				else
					mPokemon.setPowerLens(false);
			}
		});
		PowerAnklet.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					MachoBrace.setChecked(false);
					PowerWeight.setChecked(false);
					PowerBracer.setChecked(false);
					PowerBelt.setChecked(false);
					PowerLens.setChecked(false);
					PowerBand.setChecked(false);
					PowerAnklet.setChecked(isChecked);

					mPokemon.setMachoBrace(false);
					mPokemon.setPowerWeight(false);
					mPokemon.setPowerBracer(false);
					mPokemon.setPowerBelt(false);
					mPokemon.setPowerLens(false);
					mPokemon.setPowerBand(false);
					mPokemon.setPowerAnklet(isChecked);
				}
				else
					mPokemon.setPowerAnklet(false);
			}
		});

		Button HPUp = (Button)v.findViewById(R.id.hpup_button);
		HPUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPokemon.addHP(10);
			}
		});
		
		Button Protein = (Button)v.findViewById(R.id.protein_button);	
		Protein.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPokemon.addAttack(10);
			}
		});
		
		Button Iron = (Button)v.findViewById(R.id.iron_button);
		Iron.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPokemon.addDefense(10);
			}
		});
		
		Button Calcium = (Button)v.findViewById(R.id.calcium_button);
		Calcium.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPokemon.addSpAttack(10);
			}
		});
		
		Button Zinc = (Button)v.findViewById(R.id.zinc_button);
		Zinc.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPokemon.addSpDefense(10);
			}
		});
		
		Button Carbos = (Button)v.findViewById(R.id.carbos_button);
		Carbos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPokemon.addSpeed(10);
			}
		});

		return v;
	}

	@Override
	public void onPause()
	{
		super.onPause();
		GameStore.sharedStore(getActivity()).saveGames();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case android.R.id.home:
			FragmentStorage.sharedStore(getActivity()).removeFragmentFromList(this);
			getFragmentManager().popBackStackImmediate();
			return true;
		default:
			return false;
		}
	}

	public static EVItemsFragment newInstance(int position, int game)
	{
		Bundle args = new Bundle();
		args.putInt("mPokemon", position);
		args.putInt("game", game);

		EVItemsFragment fragment = new EVItemsFragment();
		fragment.setArguments(args);

		return fragment;
	}

}
