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
import com.badlogic.gdx.utils.Json;
import com.gmail.creativitry.freezeframe.screens.ScoreScreen;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class Scores
{
	public static final int BUFFER_SIZE = 8192;
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
		
		saveToFile();
	}
	
	
	private void load()
	{
		try
		{
			URL url = new URL("https://script.google.com/macros/s/AKfycbxZnL760fN1YtoYAzelYQV51F_2tTrlX-f-KElTejyCj3hUnAyM/exec");
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			
			BufferedReader in = new BufferedReader(
				new InputStreamReader(conn.getInputStream(), "UTF-8"));
			
			StringBuilder sb = new StringBuilder("{\"scores\":");
			while (true)
			{
				String line = in.readLine();
				if (line == null)
					break;
				sb.append(line).append('\n');
			}
			sb.append('}');
			
			Json json = new Json();
			OnlineScores onlineScores = json.fromJson(OnlineScores.class, sb.toString());
			data = onlineScores.toScores();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			loadFromFile();
		}
	}
	
	/**
	 * Attempts to load from the file or generates a new highscore list
	 */
	@SuppressWarnings("unchecked")
	private void loadFromFile()
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
	
	private byte[] encodeScore(ScoreData score) throws UnsupportedEncodingException
	{
		HashMap<String, String> params = new HashMap<>();
		params.put("name", score.getName());
		params.put("seed", score.getSeed());
		params.put("score", "" + score.getScore());
		
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> param : params.entrySet())
		{
			if (sb.length() != 0)
				sb.append("&");
			sb.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			sb.append("=");
			sb.append(URLEncoder.encode(param.getValue(), "UTF-8"));
		}
		System.out.println(sb);
		return sb.toString()/*"name=GahwonLee&seed=4N1jwodfO2LD853&score=16"*/.getBytes(StandardCharsets.UTF_8);
	}
	
	public void saveOnline(ScoreData scoreData)
	{
		System.out.println("saving online");
		
		HttpURLConnection conn = null;
		try
		{
			byte[] postData = encodeScore(scoreData);
			URL url = new URL("https://script.google.com/macros/s/AKfycbxZnL760fN1YtoYAzelYQV51F_2tTrlX-f-KElTejyCj3hUnAyM/exec");
			
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setUseCaches(false);
			conn.getOutputStream().write(postData);
			conn.getOutputStream().flush();
			
			byte[] buffer = new byte[8192];
			int read = conn.getInputStream().read(buffer);
			if (read != -1)
				System.out.println(new String(Arrays.copyOf(buffer, read)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (conn != null)
			{
				conn.disconnect();
			}
		}
	}
	
	/**
	 * Attempts to save the highscores to the file
	 */
	
	private void saveToFile()
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
