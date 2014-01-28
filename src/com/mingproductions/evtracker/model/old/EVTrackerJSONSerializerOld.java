package com.mingproductions.evtracker.model.old;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class EVTrackerJSONSerializerOld {

	private Context mContext;
	private String mFilename;

	public EVTrackerJSONSerializerOld(Context c, String f)
	{
		mContext = c;
		mFilename = f;
	}

	public void saveGames(ArrayList<PokemonGameOld> games) throws JSONException, IOException {
		// Building array for all games in JSON
		JSONArray array = new JSONArray();
		for (PokemonGameOld g : games)
		{
			array.put(g.toJSON());
		}

		// Write the file to disk
		Writer writer = null;
		try
		{
			OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
			Log.d("Save Game", "Saved to storage");
		} 
		finally
		{
			if (writer != null)
			{
				writer.close();
			}
		}
	}
	
	public void savePokedex(ArrayList<EVPokemonOld> pokedex) throws JSONException, IOException
	{
		JSONArray array = new JSONArray();
		for (EVPokemonOld p : pokedex)
		{
			array.put(p.toJSON());
		}
		
		Writer writer = null;
		try
		{
			OutputStream out = mContext.openFileOutput("pokedex.json", Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		}
		finally {
			if (writer != null)
			{
				writer.close();
			}
		}
	}
	
	public void saveGameTable(ArrayList<PokemonGameOld> games) throws JSONException, IOException {
		// Building array for all games in JSON
		JSONArray array = new JSONArray();
		for (PokemonGameOld g : games)
		{
			array.put(g.toJSON());
		}

		// Write the file to disk
		Writer writer = null;
		try
		{
			OutputStream out = mContext.openFileOutput("gametable.json", Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} 
		finally
		{
			if (writer != null)
			{
				writer.close();
			}
		}
	}
	
	public ArrayList<EVPokemonOld> loadPokedex() throws IOException, JSONException
	{
		ArrayList<EVPokemonOld> pokedex = new ArrayList<EVPokemonOld>();
		
		BufferedReader reader = null;
		try
		{
			InputStream in = mContext.getAssets().open("pokedex.json");
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			
			for (int i = 0; i < array.length(); i++)
			{
				pokedex.add(new EVPokemonOld(array.getJSONObject(i)));
			}
			
		}
		catch (FileNotFoundException e)
		{
			// Ignore this one; it happens when starting fresh
		}
		finally
		{
			if (reader != null)
				reader.close();
		}
		
		return pokedex;
	}
	
	public ArrayList<PokemonGameOld> loadGames() throws IOException, JSONException
	{
		ArrayList<PokemonGameOld> games = new ArrayList<PokemonGameOld>();
		
		BufferedReader reader = null;
		try
		{
			// Open and read the file into a StringBuilder
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				// Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			
			// Parse the JSON using JSONTokener
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			
			// Build the array of crimes from JSONObjects
			for (int i = 0; i < array.length(); i++)
			{
				games.add(new PokemonGameOld(array.getJSONObject(i)));
			}
			
			Log.d("Load Game", "Loaded from storage");
		}
		catch (FileNotFoundException e)
		{
			// Ignore this one; it happens when starting fresh
		}
		finally
		{
			if (reader != null)
				reader.close();
		}
		
		return games;
	}
	
	public ArrayList<PokemonGameOld> loadGameTable() throws IOException, JSONException
	{
		ArrayList<PokemonGameOld> games = new ArrayList<PokemonGameOld>();
		
		BufferedReader reader = null;
		try
		{
			// Open and read the file into a StringBuilder
			InputStream in = mContext.getAssets().open("gametable.json");
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				// Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			
			// Parse the JSON using JSONTokener
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
			
			// Build the array of crimes from JSONObjects
			for (int i = 0; i < array.length(); i++)
			{
				games.add(new PokemonGameOld(array.getJSONObject(i)));
			}
			
		}
		catch (FileNotFoundException e)
		{
			// Ignore this one; it happens when starting fresh
		}
		finally
		{
			if (reader != null)
				reader.close();
		}
		
		return games;
	}

}
