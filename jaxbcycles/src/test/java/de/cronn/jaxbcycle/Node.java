
package de.cronn.jaxbcycle;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(CycleAdapter.class)
@XmlRootElement
public class Node extends CycleRecoverableEx
{
	private String name;
	private List<Node> children = new ArrayList<Node>();
	private Node parent;

	public Node()
	{
	}

	public Node(String name)
	{
		this.name = name;
	}

	@XmlAttribute
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@XmlElementWrapper(name = "children")
	@XmlElement(name = "node")
	public List<Node> getChildren()
	{
		return children;
	}

	public void setChildren(List<Node> children)
	{
		this.children = children;
	}

	public Node getParent()
	{
		return parent;
	}

	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = ((children == null) ? 0 : children.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime
				* result
				+ ((parent == null || parent.getName() == null) ? 0
						: parent.getName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Node other = (Node) obj;
		if (children == null)
		{
			if (other.children != null)
			{
				return false;
			}
		}
		else if (!children.equals(other.children))
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		if (parent == null)
		{
			if (other.parent != null)
			{
				return false;
			}
		}
		else if (parent.getName() == null)
		{
			if (other.parent.getName() != null)
			{
				return false;
			}
		}
		else if (other.parent == null
		        || !parent.getName().equals(other.parent.getName()))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Node [name=" + name + ", children=" + children + ", parent="
				+ (parent == null ? "" : parent.getName()) + "]";
	}

}
