/**
 * Scores.java
 * Holds the top ten scores in a file
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/13/2017
 */

package com.gmail.creativitry.freezeframe.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.gmail.creativitry.freezeframe.screens.ScoreScreen;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeSet;

public class Scores
{
	private static final FileHandle FILE_PATH = Gdx.files.local("scores.dat");
	
	private TreeSet<ScoreData> data;
	
	/**
	 * Constructs new list of scores by loading the file, adding the new score,
	 * and saving
	 *
	 * @param scoreData new score to add to the list
	 */
	public Scores(ScoreData scoreData)
	{
		load();
		
		data.add(scoreData);
		while (data.size() > ScoreScreen.HIGHSCORES_AMOUNT)
			data.pollLast();
		
		save();
	}
	
	/**
	 * Attempts to load from the file or generates a new highscore list
	 */
	@SuppressWarnings("unchecked")
	private void load()
	{
		if (FILE_PATH.exists())
		{
			try
			{
				ObjectInputStream in = new ObjectInputStream(FILE_PATH.read());
				data = (TreeSet<ScoreData>) in.readObject();
				in.close();
			}
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
				
				data = new TreeSet<>();
			}
		}
		else
		{
			data = new TreeSet<>();
		}
		
		
	}
	
	/**
	 * Attempts to save the highscores to the file
	 */
	private void save()
	{
		
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(FILE_PATH.write(false));
			out.writeObject(data);
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the highscores
	 *
	 * @return highscores
	 */
	public TreeSet<ScoreData> getData()
	{
		return data;
	}
}
