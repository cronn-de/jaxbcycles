
package de.cronn.jaxbcycle;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Java Type adapter for classes containing cyclic dependencies.
 *
 * @author Hanno Fellmann, cronn GmbH
 */
public class CycleAdapter extends XmlAdapter<Object, Object>
{
	@Override
	public Object unmarshal(Object v) throws Exception
	{
		if (v instanceof CycleRecoverableEx)
		{
			final CycleRecoverableEx replacement = ThreadLocalObjectList.getInstance().get((CycleRecoverableEx) v);
			if (replacement != null)
			{
				return replacement;
			}
		}
		return v;
	}

	@Override
	public Object marshal(Object v) throws Exception
	{
		return v;
	}
}
