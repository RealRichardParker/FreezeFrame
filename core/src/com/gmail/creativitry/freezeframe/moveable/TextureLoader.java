/**
 * TextureLoader.java
 * Loads textures for moveables from a json and png file
 *
 * @author Gahwon Lee, Tiger Zhang
 * Period: 3
 * Date: 5/9/2017
 */

package com.gmail.creativitry.freezeframe.moveable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.gmail.creativitry.freezeframe.moveable.bullet.BulletTemplate;
import com.gmail.creativitry.freezeframe.moveable.item.ItemSpawner;

public class TextureLoader
{
	/**
	 * No construction
	 */
	private TextureLoader()
	{
	}
	
	/**
	 * Loads a texture from the path
	 * @param path path to get the texture from
	 * @param assetManager manager to load to
	 * @return loaded texture
	 */
	public static MoveableTexture load(String path, AssetManager assetManager)
	{
		Json json = new Json();
		MoveableTexture texture = json.fromJson(MoveableTexture.class,
			Gdx.files.internal(path + ".json"));
		final String imageName = path + ".png";
		assetManager.load(imageName, Texture.class);
		assetManager.finishLoadingAsset(imageName);
		texture.setTexture(assetManager.get(imageName, Texture.class));
		return texture;
	}
}
