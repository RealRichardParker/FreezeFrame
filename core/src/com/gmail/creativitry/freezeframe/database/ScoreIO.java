/**
 * ScoreIO.java
 * //TODO Description
 *
 * @author creativitRy
 * Period: 3
 * Date: 5/13/2017
 */
package com.gmail.creativitry.freezeframe.database;

import java.io.*;
import java.util.TreeSet;

public class ScoreIO
{
	private TreeSet<ScoreData> data;
	private String fileName;
	
	public ScoreIO()
	{
		fileName = "scores.dat";
	}
	
	@SuppressWarnings("unchecked")
	private void load()
	{
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			data = (TreeSet<ScoreData>) in.readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			System.err.print(e);
			
			data = new TreeSet<>();
		}
	}
	
	private void save()
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
