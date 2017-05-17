/**
 * Scores.java
 * Description
 *
 * @author creativitRy
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
	public static final String FILE_PATH = "scores.dat";
	private TreeSet<ScoreData> data;
	
	public Scores(ScoreData scoreData)
	{
		load();
		
		data.add(scoreData);
		while (data.size() > ScoreScreen.HIGHSCORES_AMOUNT)
			data.pollLast();
		
		save();
	}
	
	@SuppressWarnings("unchecked")
	private void load()
	{
		FileHandle file = Gdx.files.local(FILE_PATH);
		if (file.exists())
		{
			try
			{
				ObjectInputStream in = new ObjectInputStream(file.read());
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
	
	private void save()
	{
		FileHandle file = Gdx.files.local(FILE_PATH);
		
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(file.write(false));
			out.writeObject(data);
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public TreeSet<ScoreData> getData()
	{
		return data;
	}
}
