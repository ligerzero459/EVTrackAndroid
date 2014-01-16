package com.mingproductions.evtracker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.MenuItem;
import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.FragmentStorage;
import com.mingproductions.evtracker.model.GameStore;

public class EVFixFragment extends SherlockFragment {
	
	private EVPokemon mPokemon;
	private int mGamePos;
	private int mPokemonPos;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
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

		getSherlockActivity().getSupportActionBar().setTitle(mPokemon.getPokemonName());
		FragmentStorage.sharedStore(getActivity()).addFragmentToList(this);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		GameStore.sharedStore(getActivity()).saveGames();
		getSherlockActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView hp = (TextView)getView().findViewById(R.id.hp_evs);
		hp.setText(mPokemon.getHp() + "/255");
		
		TextView atk = (TextView)getView().findViewById(R.id.atk_evs);
		atk.setText(mPokemon.getAtk() + "/255");
		
		TextView def = (TextView)getView().findViewById(R.id.def_evs);
		def.setText(mPokemon.getDef() + "/255");
		
		TextView spatk = (TextView)getView().findViewById(R.id.sp_atk_evs);
		spatk.setText(mPokemon.getSpAtk() + "/255");
		
		TextView spdef = (TextView)getView().findViewById(R.id.sp_def_evs);
		spdef.setText(mPokemon.getSpDef() + "/255");
		
		TextView speed = (TextView)getView().findViewById(R.id.speed_evs);
		speed.setText(mPokemon.getSpeed() + "/255");
		
		TextView total = (TextView)getView().findViewById(R.id.total_evs);
		total.setText(mPokemon.getTotal() + "/510");

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

		getSherlockActivity().getSupportActionBar().setTitle(mPokemon.getPokemonName());
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		GameStore.sharedStore(getActivity()).saveGames();
	}
	
	private void populateLabels()
	{
		TextView hp = (TextView)getView().findViewById(R.id.hp_evs);
		hp.setText(mPokemon.getHp() + "/255");
		
		TextView atk = (TextView)getView().findViewById(R.id.atk_evs);
		atk.setText(mPokemon.getAtk() + "/255");
		
		TextView def = (TextView)getView().findViewById(R.id.def_evs);
		def.setText(mPokemon.getDef() + "/255");
		
		TextView spatk = (TextView)getView().findViewById(R.id.sp_atk_evs);
		spatk.setText(mPokemon.getSpAtk() + "/255");
		
		TextView spdef = (TextView)getView().findViewById(R.id.sp_def_evs);
		spdef.setText(mPokemon.getSpDef() + "/255");
		
		TextView speed = (TextView)getView().findViewById(R.id.speed_evs);
		speed.setText(mPokemon.getSpeed() + "/255");
		
		TextView total = (TextView)getView().findViewById(R.id.total_evs);
		total.setText(mPokemon.getTotal() + "/510");
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_fix_evs, parent, false);
		
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
		
		Button renameButton = (Button)v.findViewById(R.id.renameButton);
		renameButton.setText(mPokemon.getPokemonName());
		
		Drawable image = null;
		
		if (mPokemon.getPokemonNumber() < 10)
			image = getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p00" + mPokemon.getPokemonNumber(), null, null));
		else if (mPokemon.getPokemonNumber() < 100)
			image = getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p0" + mPokemon.getPokemonNumber(), null, null));
		else
			image = getResources().getDrawable(getResources()
					.getIdentifier("com.mingproductions.evtracker:drawable/p" + mPokemon.getPokemonNumber(), null, null));
		
		renameButton.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null);
		
		TextView hp = (TextView)v.findViewById(R.id.hp_evs);
		hp.setText(mPokemon.getHp() + "/255");
		
		TextView atk = (TextView)v.findViewById(R.id.atk_evs);
		atk.setText(mPokemon.getAtk() + "/255");
		
		TextView def = (TextView)v.findViewById(R.id.def_evs);
		def.setText(mPokemon.getDef() + "/255");
		
		TextView spatk = (TextView)v.findViewById(R.id.sp_atk_evs);
		spatk.setText(mPokemon.getSpAtk() + "/255");
		
		TextView spdef = (TextView)v.findViewById(R.id.sp_def_evs);
		spdef.setText(mPokemon.getSpDef() + "/255");
		
		TextView speed = (TextView)v.findViewById(R.id.speed_evs);
		speed.setText(mPokemon.getSpeed() + "/255");
		
		TextView total = (TextView)v.findViewById(R.id.total_evs);
		total.setText(mPokemon.getTotal() + "/510");
		
		Button hpMinus = (Button)v.findViewById(R.id.minus_hp);
		hpMinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getHp() > 0)
				{
					mPokemon.addHP(-1);
					populateLabels();
				}
			}
		});
		Button atkMinus = (Button)v.findViewById(R.id.minus_atk);
		atkMinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getAtk() > 0)
				{
					mPokemon.addAttack(-1);
					populateLabels();
				}
			}
		});
		Button defMinus = (Button)v.findViewById(R.id.minus_def);
		defMinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getDef() > 0)
				{
					mPokemon.addDefense(-1);
					populateLabels();
				}
			}
		});
		Button spAtkMinus = (Button)v.findViewById(R.id.minus_sp_atk);
		spAtkMinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getSpAtk() > 0)
				{
					mPokemon.addSpAttack(-1);
					populateLabels();
				}
			}
		});
		Button spDefMinus = (Button)v.findViewById(R.id.minus_sp_def);
		spDefMinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getSpDef() > 0)
				{
					mPokemon.addSpDefense(-1);
					populateLabels();
				}
			}
		});
		Button speedMinus = (Button)v.findViewById(R.id.minus_speed);
		speedMinus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getSpeed() > 0)
				{
					mPokemon.addSpeed(-1);
					populateLabels();
				}
			}
		});
		Button hpPlus = (Button)v.findViewById(R.id.plus_hp);
		hpPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getHp() < 255)
				{
					mPokemon.addHP(1);
					populateLabels();
				}
			}
		});
		Button atkPlus = (Button)v.findViewById(R.id.plus_atk);
		atkPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getAtk() < 255)
				{
					mPokemon.addAttack(1);
					populateLabels();
				}
			}
		});
		Button defPlus = (Button)v.findViewById(R.id.plus_def);
		defPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getDef() < 255)
				{
					mPokemon.addDefense(1);
					populateLabels();
				}
			}
		});
		Button spAtkPlus = (Button)v.findViewById(R.id.plus_sp_atk);
		spAtkPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getSpAtk() < 255)
				{
					mPokemon.addSpAttack(1);
					populateLabels();
				}
			}
		});
		Button spDefPlus = (Button)v.findViewById(R.id.plus_sp_def);
		spDefPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getSpDef() < 255)
				{
					mPokemon.addSpDefense(1);
					populateLabels();
				}
			}
		});
		Button speedPlus = (Button)v.findViewById(R.id.plus_speed);
		speedPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mPokemon.getSpeed() < 255)
				{
					mPokemon.addSpeed(1);
					populateLabels();
				}
			}
		});
		
		return v;
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
	
	public static EVFixFragment newInstance(int position, int game)
	{
		Bundle args = new Bundle();
		args.putInt("mPokemon", position);
		args.putInt("game", game);
		
		EVFixFragment fragment = new EVFixFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
}
