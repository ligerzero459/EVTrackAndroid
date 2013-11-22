package com.mingproductions.evtracker;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class GameListFragment extends ListFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		
	}
	
	private class GameAdapter extends ArrayAdapter<PokemonGame>
	{
		public GameAdapter(ArrayList<PokemonGame> allGames)
		{
			super(getActivity(), 0, allGames);
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_game, null);
			}
			
			PokemonGame game = getItem(position);
			
			ImageView gameImage = (ImageView)convertView.findViewById(R.id.game_image);
			
			
			return convertView;
		}
	}
}
