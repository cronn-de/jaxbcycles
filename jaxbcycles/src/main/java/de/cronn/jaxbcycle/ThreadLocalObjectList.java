
package de.cronn.jaxbcycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Thread local object list for storing already loaded cycle recoverable objects
 * with unique ID
 *
 * @author Hanno Fellmann, cronn GmbH
 */
class ThreadLocalObjectList
{
	private final ArrayList<CycleRecoverableEx> newObjects = new ArrayList<CycleRecoverableEx>();
	private final Map<String, CycleRecoverableEx> objectMap = new HashMap<String, CycleRecoverableEx>();

	private static final ThreadLocal<ThreadLocalObjectList> threadId = new ThreadLocal<ThreadLocalObjectList>() {
		@Override
		protected ThreadLocalObjectList initialValue()
		{
			return new ThreadLocalObjectList();
		}
	};

	public static ThreadLocalObjectList getInstance()
	{
		return threadId.get();
	}

	public void put(CycleRecoverableEx object)
	{
		newObjects.add(object);
	}

	public CycleRecoverableEx get(CycleRecoverableEx object)
	{
		updateObjectMap();
		return objectMap.get(object.cycId);
	}

	private void updateObjectMap()
	{
		final Iterator<CycleRecoverableEx> it = newObjects.iterator();
		while (it.hasNext())
		{
			final CycleRecoverableEx c = it.next();
			if (c.cycId != null)
			{
				if (!objectMap.containsKey(c.cycId))
				{
					objectMap.put(c.cycId, c);
				}
				it.remove();
			}
		}
	}

	public static void removeInstance()
	{
		threadId.remove();
	}
}
