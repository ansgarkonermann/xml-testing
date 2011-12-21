package de.lightful.testing.xml;

import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.fest.assertions.Fail;
import org.fest.assertions.GenericAssert;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmlAssert extends GenericAssert<XmlAssert, String> {

  public static XmlAssert assertThat(String actual) {
    return new XmlAssert(actual);
  }

  public XmlAssert(String actual) {
    super(XmlAssert.class, actual);
  }

  public XmlAssert isEquivalentTo(String expectedXml) {
    assertIsEquivalentTo(expectedXml);
    return this;
  }

  private void assertIsEquivalentTo(String expectedXml) {
    XMLUnit.setIgnoreWhitespace(true);
    try {
      Diff xmlDiff = new Diff(expectedXml, actual);
      failIfNotEquivalent(actual, expectedXml, xmlDiff);
    }
    catch (IOException e) {
      failForException(e, actual, expectedXml);
    }
    catch (SAXException e) {
      failForException(e, actual, expectedXml);
    }
    catch (ParserConfigurationException e) {
      failForException(e, actual, expectedXml);
    }
  }

  private void failForException(Exception e, String actual, String expectedXml) {
    Fail.fail("[" + formatDescription() + "]" + " threw exception while computing XML difference.", e);
  }

  private void failIfNotEquivalent(String actual, String expectedXml, Diff xmlDiff) {
    if (xmlDiff.similar()) {
      return;
    }

    StringBuffer sb = new StringBuffer(250);
    appendDescription(sb);
    appendFailureReason(sb);
    appendXmlDifference(sb, xmlDiff);
    appendXmlSnippet(sb, "Actual", actual);
    appendXmlSnippet(sb, "Expected", expectedXml);
    sb.append("\n\n");

    throw Fail.fail(sb.toString());
  }

  private void appendXmlSnippet(StringBuffer sb, String s, String xmlSnippet) {
    sb.append("\n\n");
    sb.append("=====[ ");
    sb.append(s);
    sb.append(" XML ]=====");
    sb.append('\n');
    sb.append(xmlSnippet);
  }

  private void appendXmlDifference(StringBuffer sb, Diff xmlDiff) {
    sb.append("\n");
    sb.append("Difference: ");
    xmlDiff.appendMessage(sb);
  }

  private void appendFailureReason(StringBuffer sb) {
    sb.append(" expected actual XML document to be equivalent to expected one, but it was not.");
  }

  private void appendDescription(StringBuffer sb) {
    sb.append("[");
    if (null == description()) {
      sb.append("");
    }
    else {
      sb.append(description());
    }
    sb.append("]");
  }

  private String formatDescription() {
    if (null == description()) {
      return "";
    }
    else {
      return description();
    }
  }
}
