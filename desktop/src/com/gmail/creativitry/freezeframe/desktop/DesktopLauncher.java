/**
 * DesktopLauncher.java
 * Launches the game for a computer
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/2/2017
 */

package com.gmail.creativitry.freezeframe.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gmail.creativitry.freezeframe.FreezeFrame;

public class DesktopLauncher
{
	
	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;
	
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WIDTH;
		config.height = HEIGHT;
		config.title = "FreezeFrame";
		config.addIcon("icon.png", Files.FileType.Internal);
		
		new LwjglApplication(new FreezeFrame(), config);
	}
}