/**
 * FreezeFrame.java
 * Description
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/4/2017
 */

package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.gmail.creativitry.freezeframe.screens.AbstractScreen;
import com.gmail.creativitry.freezeframe.screens.MainMenuScreen;

import java.lang.reflect.InvocationTargetException;

public class FreezeFrame extends Game
{
	private AssetManager assetManager;
	
	/**
	 * Called when the Application is first created.
	 */
	@Override
	public void create()
	{
		assetManager = new AssetManager();
		
		setScreen(new MainMenuScreen(this));
	}
	
	public AssetManager getAssetManager()
	{
		return assetManager;
	}
	
	public void setScreen(AbstractScreen screen)
	{
		screen.load(assetManager);
		assetManager.finishLoading();
		super.setScreen(screen);
	}
}
