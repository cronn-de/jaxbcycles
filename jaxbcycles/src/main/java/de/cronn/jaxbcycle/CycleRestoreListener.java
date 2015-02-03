
package de.cronn.jaxbcycle;

import javax.xml.bind.Unmarshaller.Listener;

import com.sun.xml.bind.CycleRecoverable;

/**
 * Base type for classes containing cyclic dependencies. Adds a unique ID to the
 * class and implements {@link CycleRecoverable} as cyle breaker.
 *
 * @author Hanno Fellmann, cronn GmbH
 */
public class CycleRestoreListener extends Listener
{
	@Override
	public void afterUnmarshal(Object target, Object parent)
	{
		if (parent == null)
		{
			ThreadLocalObjectList.removeInstance();
		}
	}

	@Override
	public void beforeUnmarshal(Object target, Object parent)
	{
		if (target instanceof CycleRecoverableEx)
		{
			ThreadLocalObjectList.getInstance().put((CycleRecoverableEx) target);
		}
	}
}
