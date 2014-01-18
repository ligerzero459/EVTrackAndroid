package com.mingproductions.evtracker.adapter;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingproductions.evtracker.R;
import com.mingproductions.evtracker.model.PokemonGame;

public class GameAdapter extends ArrayAdapter<PokemonGame>
{
	private ArrayList<PokemonGame> original;
	private ArrayList<PokemonGame> filtered;
	private Activity mContext;

	public GameAdapter(ArrayList<PokemonGame> allGames, Activity context)
	{
		super(context, 0, allGames);
		original = new ArrayList<PokemonGame>(allGames);
		filtered = new ArrayList<PokemonGame>(allGames);
		mContext = context;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = mContext.getLayoutInflater().inflate(R.layout.list_item_game, null);
		}

		PokemonGame game = getItem(position);
		Resources resource = mContext.getResources();

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
