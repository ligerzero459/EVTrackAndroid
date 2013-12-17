package com.mingproductions.evtracker;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mingproductions.evtracker.model.EVPokemon;
import com.mingproductions.evtracker.model.GameStore;

public class EVDetailFragment extends Fragment {
	
	private EVPokemon pokemon;
	private int gamePos;
	private int pokemonPos;
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		
		gamePos = getArguments().getInt("game");
		pokemonPos = getArguments().getInt("pokemon");
		
		/**
		 * Breakdown: retrieves all games from the GameStore, grabs the correct game,
		 * finds all the Pokemon in that game, and finds the right Pokemon
		 */
		pokemon = GameStore.sharedStore(getActivity()).allGames().get(gamePos).getAllPokemon().get(pokemonPos);
	}
	
	public void onResume()
	{
		super.onResume();
	}
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_ev_details, parent, false);
		
		Button renameButton = (Button)v.findViewById(R.id.renameButton);
		renameButton.setText(pokemon.getPokemonName());
		
		Drawable image = getResources().getDrawable(pokemon.getImageResource());
		renameButton.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null);
		
		TextView hp = (TextView)v.findViewById(R.id.hp_evs);
		hp.setText(pokemon.getHp() + "/255");
		
		TextView atk = (TextView)v.findViewById(R.id.atk_evs);
		atk.setText(pokemon.getAtk() + "/255");
		
		TextView def = (TextView)v.findViewById(R.id.def_evs);
		def.setText(pokemon.getDef() + "/255");
		
		TextView spatk = (TextView)v.findViewById(R.id.sp_atk_evs);
		spatk.setText(pokemon.getSpAtk() + "/255");
		
		TextView spdef = (TextView)v.findViewById(R.id.sp_def_evs);
		spdef.setText(pokemon.getSpDef() + "/255");
		
		TextView speed = (TextView)v.findViewById(R.id.speed_evs);
		speed.setText(pokemon.getSpeed() + "/255");
		
		Button battled = (Button)v.findViewById(R.id.battled_pokemon_button);
		battled.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), BattledPokemonActivity.class);
				
				Bundle b = new Bundle();
				b.putInt("pokemon", pokemonPos);
				b.putInt("game", gamePos);
				i.putExtras(b);
				
				startActivity(i);
			}
		});
		
		return v;
	}
	
	public static EVDetailFragment newInstance(int position, int game)
	{
		Bundle args = new Bundle();
		args.putInt("pokemon", position);
		args.putInt("game", game);
		
		EVDetailFragment fragment = new EVDetailFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
