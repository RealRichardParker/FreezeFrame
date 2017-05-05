/**
 * MoveableManager.java
 * Manages all the AbstractMoveable objects on the GameScreen. Updates and
 * renders items and bullets and detects collisions
 *
 * @author Tiger Zhang
 * Period: 3
 * Date: 5/5/2017
 *
 */
package com.gmail.creativitry.freezeframe;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.bullet.AbstractBullet;

import java.util.Iterator;

public class MoveableManager implements Renderable
{
	private ObjectMap<Class<? extends AbstractBullet>, Array<AbstractBullet>> pool;
	private Array<? extends AbstractMoveable> moveables;
	private Player player;
	
	public MoveableManager(Player player)
	{
		this.player = player;
	}
	
	/**
	 * Renders, updates and checks collisions for all AbstractMoveable objects on the
	 * GameScreen
	 *
	 * @param batch Batch to render to
	 * @param delta time in seconds since last tick
	 */
	@Override
	public void render(SpriteBatch batch, float delta)
	{
		Iterator<? extends AbstractMoveable> iter = moveables.iterator();
		
		while (iter.hasNext())
		{
			AbstractMoveable moveable = iter.next();
			moveable.update(delta);
			if (moveable.isColliding(player.getPosition(), player.getRadius()))
			{
				moveable.onCollision(player);
				if (moveable instanceof AbstractBullet)
				{
					AbstractBullet tempBullet = (AbstractBullet) moveable;
					if (!pool.containsKey(tempBullet.getClass()))
					{
						pool.put(tempBullet.getClass(), new Array<AbstractBullet>());
					}
					pool.get(tempBullet.getClass()).add(tempBullet);
				}
			}
			else
				moveable.render(batch);
		}
	}
}
