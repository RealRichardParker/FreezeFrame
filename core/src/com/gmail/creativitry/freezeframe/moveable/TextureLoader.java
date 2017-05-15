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
		final String imageName = path + ".png";
		assetManager.load(imageName, Texture.class);
		assetManager.finishLoadingAsset(imageName);
		texture.setTexture(assetManager.get(imageName, Texture.class));
		return texture;
	}
}
