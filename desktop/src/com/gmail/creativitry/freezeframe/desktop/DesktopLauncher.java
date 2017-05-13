package com.gmail.creativitry.freezeframe.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gmail.creativitry.freezeframe.FreezeFrame;

public class DesktopLauncher
{
	public static void main(String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1600;
		config.height = 900;
		config.title = "FreezeFrame";
		config.addIcon("bullet/Ball.png", Files.FileType.Internal);
		new LwjglApplication(new FreezeFrame(), config);
	}
}