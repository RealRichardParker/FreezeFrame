/**
 * The FreezeFrame.java class loads the skin and switches between screens of the game
 *
 * @author Gahwon Lee
 * Period: 3
 * Date: 5/4/2017
 */

package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gmail.creativitry.freezeframe.screens.AbstractScreen;
import com.gmail.creativitry.freezeframe.screens.MainMenuScreen;

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
		
		assetManager.load("ui/neon-ui.json", Skin.class);
		
		setScreen(new MainMenuScreen(this));
	}
	
	/**
	* Calls the assetManager to be used by something else
	* @return the assetManager
	*/
	public AssetManager getAssetManager()
	{
		return assetManager;
	}
	
	public void setScreen(AbstractScreen screen)
	{
		screen.load(assetManager);
		assetManager.finishLoading();
		
		if (getScreen() == null)
		{
			super.setScreen(screen);
		}
		else
		{
			AbstractScreen oldScreen = (AbstractScreen) getScreen();
			oldScreen.dispose(assetManager);
			super.setScreen(screen);
		}
		
		
	}
}
