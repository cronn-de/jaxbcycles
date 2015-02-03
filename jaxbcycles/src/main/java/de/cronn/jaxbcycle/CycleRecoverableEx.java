
package de.cronn.jaxbcycle;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import com.sun.xml.bind.CycleRecoverable;

/**
 * Base type for classes containing cyclic dependencies. Adds a unique ID to the
 * class and implements {@link CycleRecoverable} as cyle breaker.
 *
 * @author Hanno Fellmann, cronn GmbH
 */
@XmlTransient
public abstract class CycleRecoverableEx implements CycleRecoverable
{
	@XmlAttribute
	String cycId = UUID.randomUUID().toString();

	@Override
	public Object onCycleDetected(Context context)
	{
		try
		{
			final CycleRecoverableEx newObj = getClass().getConstructor().newInstance();
			newObj.cycId = cycId;
			return newObj;
		}
		catch (final Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
