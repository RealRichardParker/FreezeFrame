/**
 * TextureLoader.java
 * Description
 *
 * @author Tiger
 * Date: 5/9/2017
 */
package com.gmail.creativitry.freezeframe.moveable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;

public class TextureLoader
{
	private TextureLoader()
	{
	}
	
	public static MoveableTexture load(String path, AssetManager assetManager)
	{
		Json json = new Json();
		MoveableTexture texture = json.fromJson(MoveableTexture.class, Gdx.files.internal(path + ".json"));
		assetManager.load(path + ".png", Texture.class);
		assetManager.finishLoading();
		texture.setTexture(assetManager.get(path + ".png", Texture.class));
		return texture;
	}
}
