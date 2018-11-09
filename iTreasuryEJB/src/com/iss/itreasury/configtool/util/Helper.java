package com.iss.itreasury.configtool.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.security.MessageDigest;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;

/**
 *
 * @author
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class Helper {
	/** Default canonical output (false). */
	private static final boolean DEFAULT_CANONICAL = false;

	public static final String DEFAULT_ENCODING = "utf-8";

	private static final String DEFAULT_INDENT = "    ";

	private static final String LRCF = "\r\n";

	//
	// Data
	//

	/** Print writer. */
	private PrintWriter fOut;

	/** Canonical output. */
	private boolean fCanonical;

	private String encoding = null;

	/**
	 *
	 * @see java.lang.Object#Object()
	 */
	private Helper() {
	}

	/**
	 *
	 * @param canonical
	 */
	private void setCanonical(boolean canonical) {
		fCanonical = canonical;
	}

	public static String byteTohex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	public static String getDigest(byte[] msg) {
		String result = null;
		try {

			MessageDigest alga = MessageDigest.getInstance("MD5");
			alga.update(msg);
			byte[] digesta = alga.digest();
			result = byteTohex(digesta);

		} catch (Exception e) {
			Log.print("非法摘要算法:" + e.toString());
		}

		return result;
	}

	/**
	 * 加载指定文件
	 *
	 * @param configFileName
	 * @throws Exception
	 * @return FileInputStream
	 */
	public static FileInputStream loadConfig(String configFileName) throws Exception {


		File configFile = null;
		FileInputStream input = null;
		try {
//			检测指定文件名的有效性
			if (configFileName == null || configFileName.trim().length() <= 0) {
				Log.print("configFileName is null");
				throw new IException("Gen_E001");
			}

			configFile = new File(configFileName);

			//Log.print("load bs config file:" + configFile.getAbsolutePath());

			input = new FileInputStream(configFile);

		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}

		return input;
	}

	/**
	 *
	 * @param stream
	 * @param encoding
	 * @throws UnsupportedEncodingException
	 */
	private void setOutput(OutputStream stream, String encoding)
			throws UnsupportedEncodingException {
		java.io.Writer writer = null;

		if (encoding == null || "".equals(encoding.trim())) {
			this.encoding = DEFAULT_ENCODING;

			writer = new OutputStreamWriter(stream);
		} else {
			this.encoding = encoding;

			writer = new OutputStreamWriter(stream, this.encoding);
		}

		fOut = new PrintWriter(writer);

	}

	/**
	 *
	 * @param writer
	 */
	private void setOutput(java.io.Writer writer) {

		fOut = writer instanceof PrintWriter ? (PrintWriter) writer : new PrintWriter(writer);

	}

	/**
	 * Writes the specified node, recursively.
	 *
	 * @param node
	 * @param isInsertCRCF
	 * @param indentLength
	 */
	private boolean write(Node node, int indentLength) {

		// is there anything to do?
		if (node == null) {
			return false;
		}

		if (indentLength < 0)
			indentLength = 0;

		boolean isInsertCRLFBeforeEndTag = false;
		boolean isElement = false;

		short type = node.getNodeType();
		switch (type) {
			case Node.DOCUMENT_NODE : {
				Document document = (Document) node;
				if (!fCanonical) {
					fOut.println("<?xml version=\"1.0\" encoding=\"" + this.encoding + "\"?>");
					fOut.flush();
					write(document.getDoctype(), -1);
				}
				write(document.getDocumentElement(), -1);
				break;
			}

			case Node.DOCUMENT_TYPE_NODE : {
				DocumentType doctype = (DocumentType) node;
				fOut.print("<!DOCTYPE ");
				fOut.print(doctype.getName());
				String publicId = doctype.getPublicId();
				String systemId = doctype.getSystemId();
				if (publicId != null) {
					fOut.print(" PUBLIC '");
					fOut.print(publicId);
					fOut.print("' '");
					fOut.print(systemId);
					fOut.print('\'');
				} else {
					fOut.print(" SYSTEM '");
					fOut.print(systemId);
					fOut.print('\'');
				}
				String internalSubset = doctype.getInternalSubset();
				if (internalSubset != null) {
					fOut.println(" [");
					fOut.print(internalSubset);
					fOut.print(']');
				}
				fOut.println('>');
				break;
			}

			case Node.ELEMENT_NODE : {
				isElement = true;

				if (indentLength > 0)
					fOut.println();
				int nTemp = indentLength;
				while (nTemp > 0) {
					fOut.print(DEFAULT_INDENT);
					nTemp--;
				}

				fOut.print('<');
				fOut.print(node.getNodeName());
				Attr attrs[] = sortAttributes(node.getAttributes());
				for (int i = 0; i < attrs.length; i++) {
					Attr attr = attrs[i];
					fOut.print(' ');
					fOut.print(attr.getNodeName());
					fOut.print("=\"");
					normalizeAndPrint(attr.getNodeValue());
					fOut.print('"');
				}
				fOut.print('>');
				fOut.flush();

				Node child = node.getFirstChild();
				while (child != null) {
					if (write(child, indentLength + 1)) {
						isInsertCRLFBeforeEndTag = true;
					}
					child = child.getNextSibling();
				}
				break;
			}

			case Node.ENTITY_REFERENCE_NODE : {
				if (fCanonical) {
					Node child = node.getFirstChild();
					while (child != null) {
						write(child, indentLength + 1);
						child = child.getNextSibling();
					}
				} else {
					fOut.print('&');
					fOut.print(node.getNodeName());
					fOut.print(';');
					fOut.flush();
				}
				break;
			}

			case Node.CDATA_SECTION_NODE : {
				if (fCanonical) {
					normalizeAndPrint(node.getNodeValue());
				} else {
					fOut.print("<![CDATA[");
					fOut.print(node.getNodeValue());
					fOut.print("]]>");
				}
				fOut.flush();
				break;
			}

			case Node.TEXT_NODE : {
				normalizeAndPrint(node.getNodeValue());
				fOut.flush();
				break;
			}

			case Node.PROCESSING_INSTRUCTION_NODE : {
				fOut.print("<?");
				fOut.print(node.getNodeName());
				String data = node.getNodeValue();
				if (data != null && data.length() > 0) {
					fOut.print(' ');
					fOut.print(data);
				}
				fOut.println("?>");
				fOut.flush();
				break;
			}
		}

		if (type == Node.ELEMENT_NODE) {
			if (isInsertCRLFBeforeEndTag) {
				fOut.println();
				int nTemp = indentLength;
				while (nTemp > 0) {
					fOut.print(DEFAULT_INDENT);
					nTemp--;
				}
			}
			fOut.print("</");
			fOut.print(node.getNodeName());
			fOut.print('>');
			fOut.flush();
		}

		return isElement;

	}

	/**
	 * Returns a sorted list of attributes.
	 *
	 * @param attrs
	 * @return Attr[]
	 */
	private Attr[] sortAttributes(NamedNodeMap attrs) {

		int len = (attrs != null) ? attrs.getLength() : 0;
		Attr array[] = new Attr[len];
		for (int i = 0; i < len; i++) {
			array[i] = (Attr) attrs.item(i);
		}
		for (int i = 0; i < len - 1; i++) {
			String name = array[i].getNodeName();
			int index = i;
			for (int j = i + 1; j < len; j++) {
				String curName = array[j].getNodeName();
				if (curName.compareTo(name) < 0) {
					name = curName;
					index = j;
				}
			}
			if (index != i) {
				Attr temp = array[i];
				array[i] = array[index];
				array[index] = temp;
			}
		}

		return array;

	}

	/**
	 * Normalizes and prints the given string.
	 *
	 * @param s
	 */
	private void normalizeAndPrint(String s) {

		int len = (s != null) ? s.length() : 0;
		for (int i = 0; i < len; i++) {
			char c = s.charAt(i);
			normalizeAndPrint(c);
		}

	}

	/**
	 * Normalizes and print the given character.
	 *
	 * @param c
	 */
	private void normalizeAndPrint(char c) {

		switch (c) {
			case '<' : {
				fOut.print("&lt;");
				break;
			}
			case '>' : {
				fOut.print("&gt;");
				break;
			}
			case '&' : {
				fOut.print("&amp;");
				break;
			}
			case '"' : {
				fOut.print("&quot;");
				break;
			}
			case '\'' : {
				fOut.print("&apos;");
				break;
			}
			case '\r' :
			case '\n' : {
				if (fCanonical) {
					fOut.print("&#");
					fOut.print(Integer.toString(c));
					fOut.print(';');
					//break;
				}
				break;
				// else, default print char
			}
			case ' ' :
			case '\t' : {
				break;
			}
			default : {
				fOut.print(c);
			}
		}

	}

	public static String getXMLString(Node node, boolean canonical, String encoding)
			throws Exception {
		OutputStream stream = new ByteArrayOutputStream(256);
		writeXMLString(stream, node, canonical, encoding);
		stream.close();
		return stream.toString();
	}

	public static String getXMLString(Node node, String encoding) throws Exception {
		return getXMLString(node, DEFAULT_CANONICAL, encoding);
	}

	public static String getXMLString(Node node) throws Exception {
		return getXMLString(node, DEFAULT_CANONICAL, DEFAULT_ENCODING);
	}

	/**
	 * 向指定流写入XML 字符串。 建议调试输出，写文件等操作，优先使用该方法，效率较优
	 *
	 * @param stream
	 *            待写入的流
	 * @param node
	 *            XML node,不作空值检查
	 * @param canonical
	 *            是否按标准输出
	 * @param encoding
	 *            字符编码
	 * @throws Exception
	 */
	public static void writeXMLString(OutputStream stream, Node node, boolean canonical,
			String encoding) throws Exception {
		if (stream == null) {
			Log.print("OutputStream stream is null!");
			throw new IException("Gen_E001");
		}
		if (node != null) {
			Helper writer = new Helper();

			writer.setOutput(stream, encoding);

			writer.setCanonical(canonical);

			writer.write(node, -1);
		} else {
			stream.write(new byte[]{'N', 'u', 'l', 'l', '.'});
		}
	}

	public static void writeXMLString(OutputStream stream, Node node, String encoding)
			throws Exception {
		writeXMLString(stream, node, DEFAULT_CANONICAL, encoding);
	}

	public static void writeXMLString(OutputStream stream, Node node) throws Exception {
		writeXMLString(stream, node, DEFAULT_CANONICAL, null);
	}

	/**
	 * 将xml数据流转换为DOM模型对象
	 *
	 * @param input
	 * @return Document
	 * @throws Exception
	 */
	public static Document parse(InputStream input, String encoding) throws Exception {
		DOMParser parser = new DOMParser();

		try {
			if (input == null) {
				Log.print("null InputStream");
				throw new IException("Gen_E001");
			}

			InputSource source = new InputSource(input);

			if (encoding != null && !"".equals(encoding))
				source.setEncoding(encoding);
			parser.parse(source);

		}
		catch(IException ie)
		{
			throw ie;
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			e.printStackTrace();
			throw new IException("Gen_E001");
		}

		return parser.getDocument();
	}

	public static Document parse(InputStream input) throws Exception {
		return parse(input, null);
	}

	public static Document parse(String input, String encoding) throws Exception {
		if (input == null) {
			Log.print("\n null InputStream");
			throw new IException("Gen_E001");
		}

		ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());

		InputSource source = new InputSource(inputStream);

		if (encoding != null && !"".equals(encoding))
			source.setEncoding(encoding);

		DOMParser parser = new DOMParser();
		parser.parse(source);

		return parser.getDocument();
	}

	public static Document parse(String input) throws Exception {
		return parse(input, null);
	}

	public static void outputToFile1(String configFileName, Document node) throws Exception {
		try {
			FileOutputStream outStream = new FileOutputStream(configFileName);

			Writer writer = new BufferedWriter(new OutputStreamWriter(outStream));

			OutputFormat outputFormat = new OutputFormat(node, "GB2312", true);

			XMLSerializer serializer = new XMLSerializer(writer, outputFormat);

			serializer.serialize(node);

			writer.close();
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}

	public static void outputToFile(String configFileName, Document node) throws Exception {
		DOMSource doms = new DOMSource(node);
		File f = new File (configFileName);
		StreamResult sr = new StreamResult (f);

		try{
			TransformerFactory tf=TransformerFactory.newInstance();
			Transformer t=tf.newTransformer ();

			Properties properties = t.getOutputProperties();

			//设置新的输出属性:输出字符编码为GB2312,这样可以支持中文字符,XSLT引擎所输出　　
			//的XML文档如果包含了中文字符,可以正常显示,不会出现所谓的"汉字问题"。　　
			//请留意OutputKeys类的字符串常数OutputKeys.ENCODING。　　
			properties.setProperty(OutputKeys.ENCODING,"GB2312");
			properties.setProperty(OutputKeys.INDENT,"yes");
			properties.setProperty(OutputKeys.STANDALONE,"yes");
			//更新XSLT引擎的输出属性。　　
			t.setOutputProperties(properties);

			//关键的一步, 调用Transformer对象 (XSLT引擎)的transform()方法,该方法的第一　　
			//个参数是DOMSource对象,第二个参数是StreamResult对象。
			t.transform(doms,sr);
		}
		catch(Exception e)
		{
			Log.print(e.toString());
			throw new IException("Gen_E001");
		}
	}

	/**
	 * test
	 *
	 * @param argv
	 */
	public static void main(String argv[]) {

		try {
			System.out.print(getXMLString(null, true, ""));

			//System.out.print('');
		} catch (Exception e) {
			e.printStackTrace();
		}

	} // main(String[])

}
