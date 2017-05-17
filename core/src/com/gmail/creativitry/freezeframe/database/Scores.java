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
	//todo: at school
	private static final FileHandle SCHOOL_FILE_PATH = Gdx.files.absolute("S:/Templates/CS/Gahwon/scores.dat");
	private static final FileHandle FILE_PATH = Gdx.files.local("scores.dat");
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
		FileHandle fileHandle;
		if (SCHOOL_FILE_PATH.exists())
		{
			fileHandle = SCHOOL_FILE_PATH;
		}
		else if (FILE_PATH.exists())
		{
			fileHandle = FILE_PATH;
		}
		else
		{
			data = new TreeSet<>();
			return;
		}
		
		try
		{
			ObjectInputStream in = new ObjectInputStream(fileHandle.read());
			data = (TreeSet<ScoreData>) in.readObject();
			in.close();
		}
		catch (IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
			
			data = new TreeSet<>();
		}
		
	}
	
	private void save()
	{
		FileHandle fileHandle;
		if (SCHOOL_FILE_PATH.exists())
		{
			fileHandle = SCHOOL_FILE_PATH;
		}
		else
		{
			fileHandle = FILE_PATH;
		}
		
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(fileHandle.write(false));
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
