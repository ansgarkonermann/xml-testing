package de.lightful.testing.xml;

import org.testng.annotations.Test;

import static de.lightful.testing.xml.XmlAssert.assertThat;

@Test
public class XmlAssertTest {

  public void testIsEquivalentToSucceedsForEquivalentXml() {
    assertThat("<root><element attribOne=\"ABC\" attribTwo=\"DEF\" /></root>")
        .as("Test XML Snippet")
        .isEquivalentTo(" " +
                        " <root>   " +
                        " <element attribTwo='DEF' attribOne='ABC'>" +
                        "</element></root>");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void testIsEquivalentToFailsForMissingAttribute() {
    assertThat("<root><element attribOne=\"ABC\" attribTwoXX=\"DEF\" /></root>")
        .as("Test XML Snippet")
        .isEquivalentTo(" " +
                        " <root>   " +
                        " <element attribTwo='DEF' attribOne='ABC'>" +
                        "</element></root>");
  }
}
