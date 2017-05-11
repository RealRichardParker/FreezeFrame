/**
 * MoveableManager.java
 * Manages all the AbstractMoveable objects on the GameScreen. Updates and
 * renders items and bullets and detects collisions
 *
 * @author Tiger Zhang
 * Period: 3
 * Date: 5/5/2017
 */
package com.gmail.creativitry.freezeframe.moveable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.gmail.creativitry.freezeframe.Player;
import com.gmail.creativitry.freezeframe.behaviors.Renderable;
import com.gmail.creativitry.freezeframe.moveable.bullet.BulletTemplate;
import com.gmail.creativitry.freezeframe.moveable.bullet.AbstractBullet;
import com.gmail.creativitry.freezeframe.moveable.item.AbstractItem;

import java.util.Iterator;

public class MoveableManager implements Renderable
{
	private ObjectMap<Class<? extends AbstractBullet>, Array<AbstractBullet>> pool;
	private Array<AbstractMoveable> moveables;
	private Player player;
	
	public MoveableManager(Player player)
	{
		this.player = player;
	}
	
	public void addBullet(BulletTemplate template, float x, float y, float angle)
	{
		Class<? extends AbstractBullet> clazz = template.getBulletClass();
		if (pool.containsKey(clazz) && pool.get(clazz).size != 0)
		{
			AbstractBullet bullet = pool.get(clazz).removeIndex(pool.get(clazz).size - 1);
			bullet.init(template);
			moveables.add(bullet);
		}
		else
		{
			AbstractBullet bullet = template.spawnBullet();
			bullet.init(template);
			moveables.add(bullet);
		}
	}
	
	public void addItem(AbstractItem item)
	{
		moveables.add(item);
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
