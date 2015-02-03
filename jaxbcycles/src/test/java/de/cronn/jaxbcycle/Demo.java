
package de.cronn.jaxbcycle;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class Demo
{
	@Test
	public void testCycles() throws JAXBException
	{
		final Node parent = new Node("Parent");
		final Node child1 = new Node("Child1");
		final Node child2 = new Node("Child2");
		final Node child11 = new Node("Child11");

		addChild(parent, child1);
		addChild(parent, child2);
		addChild(child1, child11);

		assertSameAfterMarshalling(parent);
		assertSameAfterMarshalling(child1);
		assertSameAfterMarshalling(child2);
		assertSameAfterMarshalling(child11);
	}

	public void assertSameAfterMarshalling(Node node) throws JAXBException
	{
		final JAXBContext context = JAXBContext.newInstance(Node.class);

		final Marshaller marshaller = context.createMarshaller();
		final StringWriter writer = new StringWriter();
		marshaller.marshal(node, writer);

		final Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setListener(new CycleRestoreListener());
		final Node nodeAfterUnmarshalling = (Node) unmarshaller.unmarshal(new StringReader(
				writer.toString()));

		assertEquals(node, nodeAfterUnmarshalling);
	}

	public void addChild(Node parent, Node child)
	{
		parent.getChildren().add(child);
		child.setParent(parent);
	}
}
