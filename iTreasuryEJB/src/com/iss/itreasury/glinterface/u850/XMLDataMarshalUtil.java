package com.iss.itreasury.glinterface.u850;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.apache.xerces.dom.DocumentImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.UtilOperation;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Config;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log4j;

/**
 * @author rongyang
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of
 * type comments go to Window>Preferences>Java>Code Generation.
 */

class XMLDataMarshalUtil
{
	private static Log4j logger = new Log4j(Constant.ModuleType.SETTLEMENT);
	/**
	 * Constructor for XMLDataMarshalUtil.
	 */
	public XMLDataMarshalUtil()
	{
		super();
	}
	/**
	 * ��XML DOM����ת��Ϊָ������
	 * 
	 * @param obj
	 * @param xmlDoc
	 * @throws Exception
	 */
	public void marshal(Object obj, Node xmlDoc) throws Exception
	{
		if (obj == null || xmlDoc == null)
		{
			throw new IllegalArgumentException("null Object or Node.");
		}
		if (obj instanceof XMLInfo)
		{
			XMLInfo info = (XMLInfo) obj;
			info.marshal(xmlDoc);
		}
	}
	
	
	/**
	 * ��ָ������ת��ΪXML DOM����
	 * 
	 * @param obj
	 * @return Node
	 * @throws Exception
	 */
	public Node unmarshal(Object obj) throws Exception
	{
		Node xmlDoc = null;
		if (obj == null)
		{
			throw new IllegalArgumentException("null Object.");
		}
		if (obj instanceof XMLInfo)
		{
			XMLInfo info = (XMLInfo) obj;
			xmlDoc = info.unmarshal(null);
		}
		return xmlDoc;
	}
	
	
	class XMLInfo
	{
		/**
		 * Constructor for XMLInfo.
		 */
		XMLInfo()
		{
			super();
		}
		protected void marshal(Node xmlDoc) throws Exception
		{
			//stub
		}
		protected Node unmarshal(Document doc) throws Exception
		{
			return null;
		}
		/**
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			StringBuffer sbResult = new StringBuffer(128);
			sbResult.append(this.getClass().getName() + " instance (hashCode=" + this.hashCode() + ")\r\n");
			sbResult.append("=========================================\r\n");
			//��õ�ǰ����ָ�����Ƶ�Field����
			java.lang.reflect.Field[] field = null;
			try
			{
				String strTemp = null;
				ArrayList alClass = new ArrayList(6);
				Class classTemp = this.getClass();
				while (!classTemp.getName().equals("java.lang.Object"))
				{
					alClass.add(classTemp);
					classTemp = classTemp.getSuperclass();
				}
				for (int i = alClass.size() - 1; i >= 0; i--)
				{
					classTemp = (Class) alClass.get(i);
					field = classTemp.getDeclaredFields();
					if (field != null)
					{
						for (int j = 0; j < field.length; j++)
						{
							field[j].setAccessible(true);
							strTemp = field[j].getName();
							if (!strTemp.startsWith("this"))
							{
								sbResult.append(field[j].getName() + " = ");
								sbResult.append(field[j].get(this) + ";\r\n");
							}
						}
						field = null;
					}
				}
			}
			catch (Exception exp)
			{
				exp.printStackTrace();
			}
			return sbResult.toString();
		}
	}
	class Attribute
	{
		protected String name = null;
		protected String value = null;
		/**
		 * Constructor for Attribute.
		 */
		public Attribute(String name, String value)
		{
			this.name = name;
			this.value = value;
		}
		protected boolean isValid()
		{
			boolean result = true;
			if (name == null || "".equals(name.trim()))
				result = false;
			if (value == null || "".equals(value.trim()))
				result = false;
			return result;
		}
	}
	class RootInfo extends XMLInfo
	{
		//<ufinterface roottag=���� billtype=���� docid=���� receiver=���� sender=����
		// proc=���� codeexchanged=���� exportneedexch=���� display=���� family=����/>
		protected final static String nodeName = "ufinterface";
		protected String roottag = null;
		protected String billtype = null;
		protected String docid = null;
		protected String receiver = null;
		protected String sender = null;
		protected String proc = null;
		protected String procmode = null;
		protected String codeexchanged = null;
		protected String exportneedexch = null;
		protected String display = null;
		protected String family = null;
		/**
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#marshal(org.w3c.dom.Node)
		 */
		protected void marshal(Node xmlDoc) throws Exception
		{
			super.marshal(xmlDoc);
		}
		/**
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
		 */
		protected Node unmarshal(Document doc) throws Exception
		{
			Attribute[] attrs = new Attribute[11];
			attrs[0] = new Attribute("roottag", this.roottag);
			attrs[1] = new Attribute("billtype", this.billtype);
			attrs[2] = new Attribute("docid", this.docid);
			attrs[3] = new Attribute("receiver", this.receiver);
			attrs[4] = new Attribute("sender", this.sender);
			attrs[5] = new Attribute("proc", this.proc);
			attrs[6] = new Attribute("codeexchanged", this.codeexchanged);
			attrs[7] = new Attribute("exportneedexch", this.exportneedexch);
			attrs[8] = new Attribute("display", this.display);
			attrs[9] = new Attribute("family", this.family);
			attrs[10] = new Attribute("procmode", this.procmode);
			Node root = XMLDataMarshalUtil.createNode(doc, null, RootInfo.nodeName, null, attrs, true);
			return root;
		}
		/**
		 * Returns the billtype.
		 * 
		 * @return String
		 */
		public String getBilltype()
		{
			return billtype;
		}
		/**
		 * Returns the codeexchanged.
		 * 
		 * @return String
		 */
		public String getCodeexchanged()
		{
			return codeexchanged;
		}
		/**
		 * Returns the display.
		 * 
		 * @return String
		 */
		public String getDisplay()
		{
			return display;
		}
		/**
		 * Returns the docid.
		 * 
		 * @return String
		 */
		public String getDocid()
		{
			return docid;
		}
		/**
		 * Returns the exportneedexch.
		 * 
		 * @return String
		 */
		public String getExportneedexch()
		{
			return exportneedexch;
		}
		/**
		 * Returns the family.
		 * 
		 * @return String
		 */
		public String getFamily()
		{
			return family;
		}
		/**
		 * Returns the proc.
		 * 
		 * @return String
		 */
		public String getProc()
		{
			return proc;
		}
		/**
		 * Returns the receiver.
		 * 
		 * @return String
		 */
		public String getReceiver()
		{
			return receiver;
		}
		/**
		 * Returns the roottag.
		 * 
		 * @return String
		 */
		public String getRoottag()
		{
			return roottag;
		}
		/**
		 * Returns the sender.
		 * 
		 * @return String
		 */
		public String getSender()
		{
			return sender;
		}
		/**
		 * Sets the billtype.
		 * 
		 * @param billtype
		 *            The billtype to set
		 */
		public void setBilltype(String billtype)
		{
			this.billtype = billtype;
		}
		/**
		 * Sets the codeexchanged.
		 * 
		 * @param codeexchanged
		 *            The codeexchanged to set
		 */
		public void setCodeexchanged(String codeexchanged)
		{
			this.codeexchanged = codeexchanged;
		}
		/**
		 * Sets the display.
		 * 
		 * @param display
		 *            The display to set
		 */
		public void setDisplay(String display)
		{
			this.display = display;
		}
		/**
		 * Sets the docid.
		 * 
		 * @param docid
		 *            The docid to set
		 */
		public void setDocid(String docid)
		{
			this.docid = docid;
		}
		/**
		 * Sets the exportneedexch.
		 * 
		 * @param exportneedexch
		 *            The exportneedexch to set
		 */
		public void setExportneedexch(String exportneedexch)
		{
			this.exportneedexch = exportneedexch;
		}
		/**
		 * Sets the family.
		 * 
		 * @param family
		 *            The family to set
		 */
		public void setFamily(String family)
		{
			this.family = family;
		}
		/**
		 * Sets the proc.
		 * 
		 * @param proc
		 *            The proc to set
		 */
		public void setProc(String proc)
		{
			this.proc = proc;
		}
		/**
		 * Sets the receiver.
		 * 
		 * @param receiver
		 *            The receiver to set
		 */
		public void setReceiver(String receiver)
		{
			this.receiver = receiver;
		}
		/**
		 * Sets the roottag.
		 * 
		 * @param roottag
		 *            The roottag to set
		 */
		public void setRoottag(String roottag)
		{
			this.roottag = roottag;
		}
		/**
		 * Sets the sender.
		 * 
		 * @param sender
		 *            The sender to set
		 */
		public void setSender(String sender)
		{
			this.sender = sender;
		}
		public String getProcmode() {
			return procmode;
		}
		public void setProcmode(String procmode) {
			this.procmode = procmode;
		}
	}
	class ResponseXMLInfo extends XMLInfo
	{
		protected RootInfo rootInfo = null;
		private ArrayList resultSet = new ArrayList(128); //��ѯ�õ��Ľ����
		class ItemInfo extends XMLInfo
		{
			protected final static String nodeName = "item";
			protected String accounting_period = null;
			protected String dsc = null; //��ʧ�ܵ�������
			protected String entry_id = null;
			protected String succeed = null; //���ɹ���ʶ��0���ɹ�����0��ʧ�ܣ�
			protected String u8accounting_period = null;
			protected String u8voucher_id = null;
			protected String voucher_id = null; //��Ϊһ����¼�Ĺؼ��֣�
			protected String voucher_type = null;
			
			/**
			 * Constructor for ItemInfo.
			 */
			public ItemInfo()
			{
				super();
			}
			protected void marshal(Node xmlDoc) throws Exception
			{
				traverse(xmlDoc);
			}
			private void traverse(Node node) throws Exception
			{
				short type = node.getNodeType();
				String strName = node.getNodeName();
				switch (type)
				{
					case Node.DOCUMENT_NODE :
						{
							Document document = (Document) node;
							traverse(document.getDocumentElement());
							break;
						}
					case Node.ELEMENT_NODE :
						{
							Node child = node.getFirstChild();
							while (child != null)
							{
								traverse(child);
								child = child.getNextSibling();
							}
							//������ǰ��������
							if (node.hasAttributes())
							{
								NamedNodeMap atts = node.getAttributes();
								for (int i = 0; i < atts.getLength(); i++)
								{
									XMLDataMarshalUtil.setValueFromNodeToObject(atts.item(i), this);
								}
							}
							break;
						}
					case Node.TEXT_NODE :
						{
							XMLDataMarshalUtil.setValueFromNodeToObject(node, this);
							break;
						}
				}
			}
		}
		/**
		 * Constructor for ResponseXMLInfo.
		 */
		public ResponseXMLInfo()
		{
			super();
		}
		protected void marshal(Node xmlDoc) throws Exception
		{
			if (xmlDoc == null)
			{
				throw new IllegalArgumentException("null DOM object.");
			}
			//���xmlDoc�ĸ�ʽ�ǲ��ǵ�ǰ����ĸ�ʽ
			//			boolean bFlag = false;
			//
			//			Document document = (Document) xmlDoc;
			//			Element root = document.getDocumentElement();
			//			if (RootInfo.nodeName.equals(root.getNodeName()))
			//			{
			//				//��ȡ�������Text���ͽ��ĵ�һ��ELEMENT_NODE���͵Ľ��
			//				Node text = root.getFirstChild();
			//				Node tempNode = text.getNextSibling();
			//
			//				if (tempNode != null &&
			// ItemInfo.nodeName.equals(tempNode.getNodeName()))
			//				{
			//					bFlag = true;
			//				}
			//			}
			//if (!bFlag)throw new Exception("invalue xml data.");
			traverse(xmlDoc);
		}
		private void traverse(Node node) throws Exception
		{
			short type = node.getNodeType();
			String strName = node.getNodeName();
			switch (type)
			{
				case Node.DOCUMENT_NODE :
					{
						Document document = (Document) node;
						traverse(document.getDocumentElement());
						break;
					}
				case Node.ELEMENT_NODE :
					{
						if (ItemInfo.nodeName.equals(strName))
						{
							ItemInfo queryResult = new ItemInfo();
							queryResult.marshal(node);
							this.resultSet.add(queryResult);
							break;
						}
						Node child = node.getFirstChild();
						while (child != null)
						{
							traverse(child);
							child = child.getNextSibling();
						}
						break;
					}
				case Node.TEXT_NODE :
					{
						XMLDataMarshalUtil.setValueFromNodeToObject(node, this);
						break;
					}
			}
		}
		public int getResultSetSize()
		{
			return resultSet.size();
		}
		public GLVoucherInfo getResult(int index) throws Exception
		{
			GLVoucherInfo result = null;
			ItemInfo temp = (ItemInfo) this.resultSet.get(index);
			if (temp != null)
			{
				result = new GLVoucherInfo();
				result.setVoucherNo(temp.voucher_id);
				result.setPostStatusID((Long.valueOf(temp.succeed).longValue() == 0) ? Constant.YesOrNo.YES : Constant.YesOrNo.NO);
			}
			return result;
		}
	}
	class VoucherListInfo extends XMLInfo
	{
		protected RootInfo rootInfo = new RootInfo();
		protected ArrayList voucherList = new ArrayList(6);
		class VoucherInfo extends XMLInfo
		{
			protected final static String nodeName = "voucher";
			protected String id = null; //voucher node attribute
			protected VoucherHeadInfo voucher_head = new VoucherHeadInfo();
			protected VoucherBodyInfo voucher_body = new VoucherBodyInfo();
			class VoucherHeadInfo extends XMLInfo
			{
				protected final static String nodeName = "voucher_head";
				protected String company = null; //��˾�����ƻ���,U8�û�������д����NC�û�ʹ��
				protected String voucher_type = null; //varchar(2) ƾ֤���
				protected String fiscal_year = null; //ƾ֤�����Ļ�����
				protected String accounting_period = null; //tinyint �����Ļ���ڼ�
				protected String voucher_id = null; //smallint ƾ֤��
				protected String attachment_number = null; //smallint �������ݵ�����
				protected String date = null; //datetime �Ƶ�����
				protected String enter = null; //varchar(20) �Ƶ�������
				protected String cashier = null; //varchar(20) ��������
				protected String signature = null; //bit �Ƿ���ǩ��
				protected String checker = null; //varchar(20) ���������
				protected String posting_date = null; //datetime ��������
				protected String posting_person = null; //varchar(20) ����������
				protected String voucher_making_system = null; //varchar(10)
				// �Ƶ�ϵͳ��ʾ,����ʱ������GL,������ϵͳ����
				protected String memo1 = null; //varchar (10) ��ע1
				protected String memo2 = null; //varchar (10) ��ע2
				protected String reserve1 = null; //varchar (20) ����(�ⲿƾ֤ҵ������)
				protected String reserve2 = null; //varchar (50) ����(�ⲿƾ֤ҵ���)
				protected String revokeflag = null; //����ƾ֤���ֶ���1������ƾ֤����д�ƾ֤��2
				/**
				 * Constructor for VoucherHeadInfo.
				 */
				public VoucherHeadInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtil.createNode(doc, null, VoucherHeadInfo.nodeName, this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "company", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "voucher_type", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "fiscal_year", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "accounting_period", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "voucher_id", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "attachment_number", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "date", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "enter", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "cashier", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "signature", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "checker", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "posting_date", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "posting_person", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "voucher_making_system", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "memo1", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "memo2", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "reserve1", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "reserve2", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "revokeflag", this, null, true);
					return root;
				}
			}
			class VoucherBodyInfo extends XMLInfo
			{
				protected final static String nodeName = "voucher_body";
				private ArrayList entryList = new ArrayList(4);
				/**
				 * Constructor for VoucherBodyInfo.
				 */
				public VoucherBodyInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtil.createNode(doc, null, VoucherBodyInfo.nodeName, this, null, true);
					EntryInfo entryInfo = null;
					
					for (int i = 0; i < entryList.size(); i++)
					{
						entryInfo = (EntryInfo) entryList.get(i);
						root.appendChild(entryInfo.unmarshal(doc));
					}
					
					return root;
				}
			}
			class EntryInfo extends XMLInfo
			{
				protected final static String nodeName = "entry";
				protected String entry_id = null; //smallint ��¼��
				protected String account_code = null; //varchar(15) ��Ŀ����
				protected String Abstract = null; //varchar(60) ժҪ
				protected String settlement = null; //varchar (3)
				// ���㷽ʽ,�ڿ�Ŀ�����л�����ʱ��������д����
				protected String document_id = null; //varchar (30)
				// Ʊ�ݺţ��ڿ�Ŀ�����л�����ʱ��������д����
				protected String document_date = null; //datetime
				// Ʊ�����ڣ��ڿ�Ŀ�����л�����ʱ��������д����
				protected String currency = null; //varchar (8) ����
				protected String unit_price = null; //����,�ڿ�Ŀ����������ʱ����д����
				protected String exchange_rate1 = null; //float ����1 �����Һ���ʱʹ��
				// �۸����� ԭ��*����1=����
				// NC�û�ʹ��
				protected String exchange_rate2 = null; //float ����2 �������� �۱�����
				// ����*����2=���ң�U8�û�ʹ��
				protected String debit_quantity = null; //float
				// �跽����,�ڿ�Ŀ����������ʱ����д����
				protected String primary_debit_amount = null; //money ԭ�ҽ跽������
				protected String secondary_debit_amount = null; //money
				// ���ҽ跽������
				protected String natural_debit_currency = null; //money
				// ���ҽ跽������
				protected String credit_quantity = null; //float
				// ��������,�ڿ�Ŀ����������ʱ����д����
				protected String primary_credit_amount = null; //money ԭ�Ҵ���������
				protected String secondary_credit_amount = null; //money
				// ���Ҵ���������
				protected String natural_credit_currency = null; //money
				// ���Ҵ���������
				protected String bill_type = null; //varchar (20)
				// ԭʼ�������ͣ�����ƾ֤ʱ���
				protected String bill_id = null; //varchar (30) ԭʼ���ݱ�ţ�����ƾ֤ʱ���
				protected String bill_date = null; //datetime ԭʼ�������ڣ�����ƾ֤ʱ���
				
				protected AuxiliaryInfo auxiliaryInfo = new AuxiliaryInfo(); //��������
				
				protected DetailInfo detailInfo = new DetailInfo(); //��ϸ
				/**
				 * Constructor for EntryInfo.
				 */
				public EntryInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtil.createNode(doc, null, EntryInfo.nodeName, this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "entry_id", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "account_code", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "abstract", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "settlement", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "document_id", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "document_date", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "currency", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "unit_price", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "exchange_rate1", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "exchange_rate2", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "debit_quantity", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "primary_debit_amount", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "secondary_debit_amount", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "natural_debit_currency", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "credit_quantity", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "primary_credit_amount", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "secondary_credit_amount", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "natural_credit_currency", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "bill_type", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "bill_id", this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "bill_date", this, null, true);
					
					root.appendChild(this.auxiliaryInfo.unmarshal(doc));
					
					root.appendChild(this.detailInfo.unmarshal(doc));
					return root;
				}
			}
			class AuxiliaryInfo extends XMLInfo
			{
				protected final static String nodeName = "auxiliary_accounting";
				protected String dept_id = null; //varchar (12) ����
				protected String cperson_id = null; //varchar (8) ��Ա
				protected String ccus_id = null; //varchar (20) �ͻ�
				protected String csup_id = null; //varchar (12) ��Ӧ��
				protected String item_class = null; //varchar(2) ��Ŀ����
				protected String citem_id = null; //varchar (20) ��Ŀ����
				protected String operator = null; //varchar (20) ҵ��Ա
				protected String define1 = null; //varchar 20 �Զ����ֶ�1
				protected String define2 = null; //varchar 20 �Զ����ֶ�2
				protected String define3 = null; //varchar 20 �Զ����ֶ�3
				protected String define4 = null; //datetime �Զ����ֶ�4
				protected String define5 = null; //smallint �Զ����ֶ�5
				protected String define6 = null; //datetime �Զ����ֶ�6
				protected String define7 = null; //float �Զ����ֶ�7
				protected String define8 = null; //varchar 4 �Զ����ֶ�8
				protected String define9 = null; //varchar 8 �Զ����ֶ�9
				protected String define10 = null; //varchar 60 �Զ����ֶ�10
				protected String define11 = null; //varchar 120 �Զ����ֶ�11
				protected String define12 = null; //varchar 120 �Զ����ֶ�12
				protected String define13 = null; //varchar 120 �Զ����ֶ�13
				protected String define14 = null; //varchar 120 �Զ����ֶ�14
				protected String define15 = null; //int �Զ����ֶ�15
				protected String define16 = null; //float �Զ����ֶ�16
				
				//protected String item_name = null;   //������������
				//protected String item_value = null;  //��������ֵ
				
				/**
				 * Constructor for AuxiliaryInfo.
				 */
				public AuxiliaryInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtil.createNode(doc, null, AuxiliaryInfo.nodeName, this, null, true);
										
                    //����������Ϣ
					//if(this.item_name.length() > 1)
					//{
					//	createItem(doc, root, this.item_value, this.item_name);
					//}
					
					createItem(doc, root, this.dept_id, "dept_id");
					createItem(doc, root, this.cperson_id, "personnel_id");
					createItem(doc, root, this.ccus_id, "cust_id");
					createItem(doc, root, this.csup_id, "supplier_id");
					createItem(doc, root, this.citem_id, "item_id");
					createItem(doc, root, this.item_class, "item_class");
					createItem(doc, root, this.operator, "operator");
					createItem(doc, root, this.define1, "self_define1");
					createItem(doc, root, this.define2, "self_define2");
					createItem(doc, root, this.define3, "self_define3");
					createItem(doc, root, this.define4, "self_define4");
					createItem(doc, root, this.define5, "self_define5");
					createItem(doc, root, this.define6, "self_define6");
					createItem(doc, root, this.define7, "self_define7");
					createItem(doc, root, this.define8, "self_define8");
					createItem(doc, root, this.define9, "self_define9");
					createItem(doc, root, this.define10, "self_define10");
					createItem(doc, root, this.define11, "self_define11");
					createItem(doc, root, this.define12, "self_define12");
					createItem(doc, root, this.define13, "self_define13");
					createItem(doc, root, this.define14, "self_define14");
					createItem(doc, root, this.define15, "self_define15");
					createItem(doc, root, this.define16, "self_define16");
					
					
//					XMLDataMarshalUtil.createNode(doc, root, "dept_id", this, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "cperson_id", this, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "ccus_id", this, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "csup_id", this, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "item_class", this, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "citem_id", this, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "operator", this, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define1", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define2", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define3", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define4", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define5", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define6", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define7", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define8", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define9", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define10", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define11", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define12", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define13", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define14", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define15", null, null, true);
//					XMLDataMarshalUtil.createNode(doc, root, "define16", null, null, true);

					return root;
				}
				private void createItem(Document doc, Node parentNode, String value, String nameAttributeValue)
				{
					Element element = doc.createElement("item");
					
					element.setAttribute("name", nameAttributeValue);
							
					if ((value != null && !"".equals(value.trim())))
					{
						Text content = doc.createTextNode(value);
						element.appendChild(content);
					}
					
					parentNode.appendChild(element);
				}
			}
			class DetailInfo extends XMLInfo
			{
				protected final static String nodeName = "detail";
				/**
				 * Constructor for DetailInfo.
				 */
				public DetailInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtil.createNode(doc, null, DetailInfo.nodeName, this, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "cash_flow_statement", null, null, true);
					XMLDataMarshalUtil.createNode(doc, root, "code_remark_statement", null, null, true);
					return root;
				}
			}
			/**
			 * Constructor for VoucherInfo.
			 */
			public VoucherInfo()
			{
				super();
			}
			/**
			 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
			 */
			protected Node unmarshal(Document doc) throws Exception
			{
				Node element = XMLDataMarshalUtil.createNode(doc, null, VoucherInfo.nodeName, this, new Attribute[] { new Attribute("id", this.id)}, true);
				
				//����voucher_head ƾ֤ͷ
				element.appendChild(this.voucher_head.unmarshal(doc));
				
				//����voucher_body ƾ֤��
				element.appendChild(this.voucher_body.unmarshal(doc));
				
				return element;
			}
			
			protected void setValues(GLVoucherInfo voucherInfo,long lOfficeID,long lCurrencyID) throws Exception
			{
				this.id = voucherInfo.getTransNo();
				
				//����ƾ֤ͷ��Ϣ
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+Env.getClientName());
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+Env.GL_VOUCHERTYPE);
				
				GlSettingInfo  glSettingInfo=new GlSettingInfo();
				glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
				
				String	glVoucherType=glSettingInfo.getGlVoucherType();
				String glUserName=glSettingInfo.getGlUserName();
		
				this.voucher_head.voucher_type = DataFormat.toChinese(glVoucherType);
				this.voucher_head.fiscal_year = ConvertDataUtil.convertDateToString(voucherInfo.getPostDate(), "yyyy");
				this.voucher_head.accounting_period = ConvertDataUtil.convertDateToString(voucherInfo.getPostDate(), "M");
				//this.voucher_head.voucher_id = ConvertDataUtil.convertVoucherIDToU850(voucherInfo.getTransNo());
				this.voucher_head.voucher_id = voucherInfo.getVoucherNo();
				//add by lixiren for �ַ�
				if(GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID).getNImportType()==Constant.GLImportType.hz)
				{
					this.voucher_head.attachment_number = "1";
				}
				else
				{
					this.voucher_head.attachment_number = String.valueOf(voucherInfo.getList().size());
				}
				//end by lixiren for �ַ�
				
				this.voucher_head.enter = glUserName; //���޸�
				this.voucher_head.posting_date = ConvertDataUtil.convertDateToString(voucherInfo.getPostDate(), "yyyy-MM-dd");
				this.voucher_head.date = this.voucher_head.posting_date;
				this.voucher_head.voucher_making_system = Constant.ModuleType.getName(voucherInfo.getModelID());
				
				//����ƾ֤����Ϣ
				GLEntryInfo glEntryInfo = null;
				EntryInfo entryInfo = null;
				for (int i = 0; i < voucherInfo.getList().size(); i++)
				{
					
					glEntryInfo = (GLEntryInfo) voucherInfo.getList().get(i);
					if(i==0)
					{
						this.voucher_head.voucher_type = GLU850Bean.getGlSettingInfo(glEntryInfo.getOfficeID(), glEntryInfo.getCurrencyID()).getGlVoucherType();
						this.voucher_head.enter = GLU850Bean.getGlSettingInfo(glEntryInfo.getOfficeID(), glEntryInfo.getCurrencyID()).getGlUserName();
					}
					
					entryInfo = new EntryInfo();
					entryInfo.entry_id = String.valueOf(glEntryInfo.getID());
					entryInfo.account_code = glEntryInfo.getSubject();
					////���ݲ�ͬ��ģ�鴦����ͬ��ժҪ����-----��ΪU850ϵͳ��ժҪ���Ȳ��ܳ���60���ַ���������Ҫ���⴦��
					String strAbstract = glEntryInfo.getAbstract();
					if(glEntryInfo.getAbstract()!=null && glEntryInfo.getAbstract().length()>40)
					{
						strAbstract = strAbstract.substring(0,30);
					}
					if(voucherInfo.getModelID()==Constant.ModuleType.SETTLEMENT)
					{
						if(glEntryInfo.getTransNo()!=null && glEntryInfo.getTransNo().trim().length()!=0)
						{
							//modified by mzh_fu 2007/12/03
							//entryInfo.Abstract = DataFormat.formatNullString(Constant.ModuleType.getName(voucherInfo.getModelID()))+"/" + DataFormat.formatNullString(strAbstract)+"/"+glEntryInfo.getTransNo().substring(12);
							entryInfo.Abstract = DataFormat.formatNullString(Constant.ModuleType.getName(voucherInfo.getModelID()))+"/" + DataFormat.formatNullString(strAbstract)+"/"+glEntryInfo.getTransNo();
						}
						else
						{
							entryInfo.Abstract = DataFormat.formatNullString(Constant.ModuleType.getName(voucherInfo.getModelID()))+"/" + DataFormat.formatNullString(strAbstract)+"/"+glEntryInfo.getTransNo();
						}
					}
					else if(voucherInfo.getModelID()==Constant.ModuleType.SECURITIES)
					{
						entryInfo.Abstract = DataFormat.formatNullString(Constant.ModuleType.getName(voucherInfo.getModelID()))+"/" + DataFormat.formatNullString(strAbstract)+"/"+glEntryInfo.getTransNo();
					}
					else
					{
						entryInfo.Abstract = DataFormat.formatNullString(Constant.ModuleType.getName(voucherInfo.getModelID()))+"/" + DataFormat.formatNullString(strAbstract);
					}
					//	�ж����ժҪ���ȣ���ش�
					/*int AbLength = Config.getInteger(Config.SETTLEMENT_U850_ABSTRACTLENGTH, 30);
					if(entryInfo.Abstract.trim().length()>AbLength){
				    	entryInfo.Abstract = entryInfo.Abstract.trim().substring(0, AbLength);
				    }*/
					//Modify by leiyang 2009-01-07
					//  �µĽ��ַ�������
					int AbLength = Config.getInteger(Config.SETTLEMENT_U850_ABSTRACTLENGTH, 60);
					if(entryInfo.Abstract.trim().getBytes().length>AbLength){
				    	entryInfo.Abstract = new String(entryInfo.Abstract.trim().getBytes(), 0, AbLength);
				    }
					//entryInfo.settlement =
					entryInfo.document_id = glEntryInfo.getBankChequeNo();
					//entryInfo.document_date =
					entryInfo.currency = ConvertDataUtil.couvertLongCodeToCurrencyName(glEntryInfo.getCurrencyID());
					if (glEntryInfo.getDirectionID() == SETTConstant.DebitOrCredit.CREDIT)
					{
						entryInfo.natural_credit_currency = DataFormat.formatAmount(glEntryInfo.getAmount());
						entryInfo.natural_debit_currency = "0";
					}
					else if (glEntryInfo.getDirectionID() == SETTConstant.DebitOrCredit.DEBIT)
					{
						entryInfo.natural_debit_currency = DataFormat.formatAmount(glEntryInfo.getAmount());
						entryInfo.natural_credit_currency = "0";
					}
					entryInfo.exchange_rate2 = "0";
					entryInfo.debit_quantity = "0";
					entryInfo.primary_debit_amount = "0";
					entryInfo.credit_quantity = "0";
					entryInfo.primary_credit_amount = "0";
					
					//��������
					entryInfo.auxiliaryInfo.dept_id = null; //varchar (12) ����
					entryInfo.auxiliaryInfo.cperson_id = null; //varchar (8) ��Ա
					
					//entryInfo.auxiliaryInfo.ccus_id = null; //varchar (20) �ͻ�
					entryInfo.auxiliaryInfo.ccus_id = DataFormat.formatNullString(glEntryInfo.getAssitantValue()); //varchar (20) �ͻ�
					
					entryInfo.auxiliaryInfo.csup_id = null; //varchar (12) ��Ӧ��
					entryInfo.auxiliaryInfo.item_class = null; //varchar(2) ��Ŀ����
					entryInfo.auxiliaryInfo.citem_id = null; //varchar (20) ��Ŀ����
					entryInfo.auxiliaryInfo.operator = null; //varchar (20) ҵ��Ա
					entryInfo.auxiliaryInfo.define1 = null; //varchar 20 �Զ����ֶ�1
					entryInfo.auxiliaryInfo.define2 = null; //varchar 20 �Զ����ֶ�2
					entryInfo.auxiliaryInfo.define3 = null; //varchar 20 �Զ����ֶ�3
					entryInfo.auxiliaryInfo.define4 = null; //datetime �Զ����ֶ�4
					entryInfo.auxiliaryInfo.define5 = null; //smallint �Զ����ֶ�5
					entryInfo.auxiliaryInfo.define6 = null; //datetime �Զ����ֶ�6
					entryInfo.auxiliaryInfo.define7 = "0"; //float �Զ����ֶ�7
					entryInfo.auxiliaryInfo.define8 = null; //varchar 4 �Զ����ֶ�8
					entryInfo.auxiliaryInfo.define9 = null; //varchar 8 �Զ����ֶ�9
					entryInfo.auxiliaryInfo.define10 = null; //varchar 60 �Զ����ֶ�10
					entryInfo.auxiliaryInfo.define11 = null; //varchar 120 �Զ����ֶ�11
					entryInfo.auxiliaryInfo.define12 = null; //varchar 120 �Զ����ֶ�12
					entryInfo.auxiliaryInfo.define13 = null; //varchar 120 �Զ����ֶ�13
					entryInfo.auxiliaryInfo.define14 = null; //varchar 120 �Զ����ֶ�14
					entryInfo.auxiliaryInfo.define15 = null; //int �Զ����ֶ�15
					entryInfo.auxiliaryInfo.define16 = null; //float �Զ����ֶ�16
					
					//����������Ϣ
					//entryInfo.auxiliaryInfo.item_name = DataFormat.formatNullString(glEntryInfo.getAssitantName());
					//entryInfo.auxiliaryInfo.item_value = DataFormat.formatNullString(glEntryInfo.getAssitantValue());
					
					//entryInfo.unit_price =
					//					entryInfo.exchange_rate1 =
					//					entryInfo.exchange_rate2 =
					//					entryInfo.debit_quantity =
					//					entryInfo.primary_debit_amount =
					//					entryInfo.secondary_debit_amount =
					//					entryInfo.natural_debit_currency =
					//					entryInfo.credit_quantity =
					//					entryInfo.primary_credit_amount =
					//					entryInfo.secondary_credit_amount =
					//					entryInfo.natural_credit_currency =
					//					entryInfo.bill_type =
					//					entryInfo.bill_id =
					//					entryInfo.bill_date =
					this.voucher_body.entryList.add(entryInfo);
					entryInfo = null;
				} //end for()
			}
		}
		
		/**
		 * Constructor for VoucherListInfo.
		 */
		public VoucherListInfo()
		{
			super();
		}
		
		public void addVoucher(GLVoucherInfo voucherInfo,long lOfficeID,long lCurrencyID) throws Exception
		{
			if (voucherInfo == null)
			{
				throw new IllegalArgumentException("offer voucherInfo is null.");
			}
			
			VoucherInfo info = new VoucherInfo();
			info.setValues(voucherInfo,lOfficeID,lCurrencyID);
			
			this.voucherList.add(info);
		}
		
		/**
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
		 */
		protected Node unmarshal(Document doc) throws Exception
		{
			Document newDoc = null;
			newDoc = new DocumentImpl();
			//��������
			Node root = rootInfo.unmarshal(newDoc);
			newDoc.appendChild(root);
			VoucherInfo voucherInfo = null;
			for (int i = 0; i < voucherList.size(); i++)
			{
				voucherInfo = (VoucherInfo) voucherList.get(i);
				root.appendChild(voucherInfo.unmarshal(newDoc));
			}
			return newDoc;
		}
	}
	class RequestQuerySubjectInfo extends XMLInfo
	{
		protected RootInfo rootInfo = new RootInfo();
		/**
		 * Constructor for QuerySubjectInfo.
		 */
		RequestQuerySubjectInfo()
		{
			super();
		}
		/**
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtil.XMLInfo#unmarshal(org.w3c.dom.Document)
		 */
		protected Node unmarshal(Document doc) throws Exception
		{
			Document newDoc = null;
			newDoc = new DocumentImpl();
			//��������
			Node root = rootInfo.unmarshal(newDoc);
			newDoc.appendChild(root);
			return newDoc;
		}
	}
	class ResponseQuerySubjectInfo extends XMLInfo
	{
		protected RootInfo rootInfo = null;
		private ArrayList resultSet = new ArrayList(128); //��ѯ�õ��Ľ����
		class SubjectInfo extends XMLInfo
		{
			protected final static String nodeName = "code";
			protected String id = null; //code child node value
			protected String type = null; //Varchar 14 ��Ŀ����(������ҵ���Ͷ����Ŀ����)
			protected String type_ename = null; //Varchar 50 ��Ŀ����Ӣ������
			protected String analysis_type = null; //Varchar 14 �����������
			protected String analysis_type_ename = null; //Varchar 50
			// �����������Ӣ������
			protected String code = null; //Varchar 15
			// ��Ŀ����(����Ŀ����ԭ����б���,<����������>)
			protected String name = null; //Varchar 40 ��Ŀ����
			protected String ename = null; //Varchar 100 ��ĿӢ������
			protected String grade = null; //Tinyint 1 ��Ŀ����(�������Ŀ������ƥ��)
			protected String prop = null; //Bit 1 ��Ŀ����(False����Դ�mTrue:ռ��)
			protected String acclist_style = null; //Varchar 10
			// ��ҳ��ʽ(���ʽ,�������ʽ,��ҽ��ʽ,�������ʽ)
			protected String acclist_style_ename = null; //Varchar 50 ��ҳ��ʽӢ������
			protected String ac_assist = null; //Varchar 6 ��Ŀ������
			protected String fc_name = null; //Varchar 8 �������(�������������)
			protected String measure_unit = null; //Varchar 6 ������λ
			protected String person_acc = null; //Bit 1 ������������(������������������ͬʱ����)
			protected String cust_acc = null; //Bit 1 �ͻ���������(���벿��,��Ŀ����ͬʱ����)
			protected String supplier_acc = null; //Bit 1
			// ��Ӧ����������(���벿��,��Ŀ����ͬʱ����)
			protected String dept_acc = null; //Bit 1 ���ź���(����ͻ�,��Ӧ��,��Ŀ����ͬʱ����)
			protected String item_acc = null; //Bit 1 ��Ŀ����(����ͻ�,��Ӧ��,���ź���ͬʱ����)
			protected String item_category = null; //Varchar 2
			// ��Ŀ����(����Ŀ��������_item����,��bitem=True)
			protected String date_acc = null; //Bit 1 �ռ���
			protected String bank_acc = null; //Bit 1 ������
			protected String sum_flag = null; //Varchar 15
			// �Ƿ���ܴ�ӡ(��ӡƾ֤)(Null_������,����Ϊ�������ϼ����ܿ�Ŀ)
			protected String end_item_flag = null; //Bit 1 �Ƿ�ĩ����Ŀ
			protected String exchange_flag = null; //Bit 1 �Ƿ�������������
			protected String cash_acc_flag = null; //Bit 1
			// �Ƿ����(�ֽ�)��Ŀ(��ָ���ϼ���Ŀ,�Զ����¼���Ŀ���ô�����)
			protected String bank_acc_flag = null; //Bit 1
			// �Ƿ����(����)��Ŀ(��ָ���ϼ���Ŀ,�Զ����¼���Ŀ���ô�����)
			protected String bacc_enable_flag = null; //Bit 1 �����˿�Ŀ�Ƿ�����
			protected String bacc_balance_way = null; //Bit 1 True �����˿�Ŀ���˷���
			// True:�跽, False:����
			protected String bacc_begin = null; //DateTime 8 �����˿�Ŀ����ʱ��
			protected String bacc_end = null; //DateTime 8 �����˿�Ŀ���˽�ֹ����
			protected String period_pl = null; //Tinyint 1
			// �ڼ�����:1_��������,���۳ɱ�:2_�����Ʒ/3_��������/4_���۳ɱ�,�������:5_���˿�Ŀ
			protected String acc_seal_flag = null; //Bit 1 ��Ŀ�Ƿ���(�ѷ���Ŀ�����Ƶ�)
			protected String ctrled_acc = null; //Varchar 10
			// �ܿؿ�Ŀ(��Ŀ������ϵͳ�Ŀ���,ϵͳID��,�ܿؿ�Ŀ�Ƿ���Ƶ������ײ�������)
			protected String other_use_flag = null; //Int 4
			// ����ϵͳ�Ƿ�ʹ��(������ϵͳ��д,0_δʹ��,��0_��ʹ��)
			protected String self_define1 = null; //Bit 1 �Զ����ֶ�1
			protected String self_define2 = null; //Bit 1 �Զ����ֶ�2
			protected String self_define3 = null; //Bit 1 �Զ����ֶ�3
			protected String self_define4 = null; //Bit 1 �Զ����ֶ�4
			protected String self_define5 = null; //Bit 1 �Զ����ֶ�5
			protected String self_define6 = null; //Bit 1 �Զ����ֶ�6
			protected String self_define7 = null; //Bit 1 �Զ����ֶ�7
			protected String self_define8 = null; //Bit 1 �Զ����ֶ�8
			protected String self_define9 = null; //Bit 1 �Զ����ֶ�9
			protected String self_define10 = null; //Bit 1 �Զ����ֶ�10
			protected String self_define11 = null; //Bit 1 �Զ����ֶ�11
			protected String self_define12 = null; //Bit 1 �Զ����ֶ�12
			protected String self_define13 = null; //Bit 1 �Զ����ֶ�13
			protected String self_define14 = null; //Bit 1 �Զ����ֶ�14
			protected String self_define15 = null; //Bit 1 �Զ����ֶ�15
			protected String self_define16 = null; //Bit 1 �Զ����ֶ�16
			protected String itemtype = null; //�ڽ�������Ŀ��Ŀ���� 0_���ڽ�������Ŀ��Ŀ 1_������Ŀ
			// 2_���ÿ�Ŀ >2_��֧��Ŀ
			protected String proj_balance = null; //�Ƿ񹤳̽����Ŀ GL_XMZCDetail
			protected String cashitem = null; //�Ƿ����ֽ�������Ŀ True_�Զ������Ӵ���
			/**
			 * Constructor for SubjectInfo.
			 */
			public SubjectInfo()
			{
				super();
			}
			protected void marshal(Node xmlDoc) throws Exception
			{
				//traverse(xmlDoc);
				//Modify by leiyang 2008/08/04
				//����XML�Ľ�����ʽ
				if(xmlDoc.getNodeType() == Node.ELEMENT_NODE){
					NodeList textList = xmlDoc.getChildNodes();
					for(int i=0; i<textList.getLength(); i++){
						Node textNode = textList.item(i);
						if(textNode.getNodeType() == Node.ELEMENT_NODE){
							if(textNode.getFirstChild() != null){
								XMLDataMarshalUtil.setValueFromNodeToObject(textNode.getFirstChild(), this);
							}
						}
					}
				}
			}
			
			/*private void traverse(Node node) throws Exception
			{
				short type = node.getNodeType();
				String strName = node.getNodeName();
				switch (type)
				{
					case Node.DOCUMENT_NODE :
						{
							Document document = (Document) node;
							traverse(document.getDocumentElement());
							break;
						}
					case Node.ELEMENT_NODE :
						{
							Node child = node.getFirstChild();
							while (child != null)
							{
								traverse(child);
								child = child.getNextSibling();
							}
							break;
						}
					case Node.TEXT_NODE :
						{
							//�˴����������ڴ���code����code���ͬ��������
							if ("code".equals(node.getParentNode().getParentNode().getNodeName()))
							{
								XMLDataMarshalUtil.setValueFromNodeToObject(node, this);
							}
							break;
						}
				}
			}*/
		}
		/**
		 * Constructor for ResponseQuerySubjectInfo.
		 */
		public ResponseQuerySubjectInfo()
		{
			super();
		}
		public int getResultSetSize()
		{
			return resultSet.size();
		}
		public GLSubjectDefinitionInfo getResult(int index) throws Exception
		{
			GLSubjectDefinitionInfo result = null;
			SubjectInfo temp = (SubjectInfo) this.resultSet.get(index);
			if (temp != null)
			{
				result = new GLSubjectDefinitionInfo();
				result.setID(Long.valueOf(temp.id).longValue());
				result.setSubjectType(ConvertDataUtil.convertU850Subject(temp.type_ename));
				result.setLeaf(Boolean.valueOf(temp.end_item_flag).booleanValue());
				result.setSegmentCode2(temp.code);
				result.setSegmentName2(temp.name);
			}
			return result;
		}
		protected void marshal(Node xmlDoc) throws Exception
		{
			if (xmlDoc == null)
			{
				throw new IllegalArgumentException("null DOM object.");
			}
			//���xmlDoc�ĸ�ʽ�ǲ��ǵ�ǰ����ĸ�ʽ
			//			boolean bFlag = false;
			//
			//			Document document = (Document) xmlDoc;
			//			Element root = document.getDocumentElement();
			//			if (RootInfo.nodeName.equals(root.getNodeName()))
			//			{
			//				//��ȡ������µĵ�һ��ELEMENT_NODE���͵Ľ��
			//				Node tempNode = root.getFirstChild();
			//				while (tempNode.getNodeType() != Node.ELEMENT_NODE)
			//				{
			//					tempNode = root.getNextSibling();
			//				}
			//
			//				if (SubjectInfo.nodeName.equals(tempNode.getNodeName()))
			//				{
			//					bFlag = true;
			//				}
			//			}
			//			if (!bFlag)
			//				throw new Exception("invalue xml data.");
			
			//Modify by leiyang 2008/08/04
			//����XML�Ľ�����ʽ
			if(xmlDoc.getNodeType() == Node.DOCUMENT_NODE){
				NodeList elementList = xmlDoc.getFirstChild().getChildNodes();
				for(int i=0; i<elementList.getLength(); i++){
					Node elementNode = elementList.item(i);
					if(elementNode.getNodeType() == Node.ELEMENT_NODE){
						SubjectInfo queryResult = new SubjectInfo();
						queryResult.marshal(elementNode);
						this.resultSet.add(queryResult);
					}
				}
			}
			
			//traverse(xmlDoc);
		}
		
		/*private void traverse(Node node) throws Exception
		{
			short type = node.getNodeType();
			String strName = node.getNodeName();
			switch (type)
			{
				case Node.DOCUMENT_NODE :
					{
						Document document = (Document) node;
						traverse(document.getDocumentElement());
						break;
					}
				case Node.ELEMENT_NODE :
					{
						if (SubjectInfo.nodeName.equals(strName))
						{
							SubjectInfo queryResult = new SubjectInfo();
							queryResult.marshal(node);
							this.resultSet.add(queryResult);
							break;
						}
						Node child = node.getFirstChild();
						while (child != null)
						{
							traverse(child);
							child = child.getNextSibling();
						}
						break;
					}
				case Node.TEXT_NODE :
					{
						XMLDataMarshalUtil.setValueFromNodeToObject(node, this);
						break;
					}
			}
		}*/
	}
	/**
	 * ����ָ���Ľ������(nodeName)����һ�������뵽ָ���ĸ����(parentNode)
	 * �С��´����Ľ���ֵΪvalue��������nodeNameͬ������ֵ��
	 * 
	 * ע��value�����ж�Ӧ�������ΪString����
	 * 
	 * �� ���� ��value���󲻴�����nodeNameͬ������()ʱ����forceCreateΪfalseʱ�����쳣
	 * ��value���󲻴�����nodeNameͬ������()ʱ����forceCreateΪtrueʱ�����ɿս��
	 * ��value�����ж�Ӧ������String����ʱ�����쳣
	 * ��value�����ж�Ӧ����ֵΪnullʱ�򡰡�ʱ����forceCreateΪfalseʱ�����ɿս�㣬���򲻲����κν��
	 * ��parentNode����Ϊnullʱ���������Ľ�㲻ִ�м��븸���Ĳ����������쳣����Ƕ����ʹ��
	 * 
	 * @param doc
	 *            ����Ϊnull
	 * @param parentNode
	 *            ����Ϊnull
	 * @param nodeName
	 *            ����Ϊnull
	 * @param valueObj
	 *            ��forceCreate=falseʱ����Ϊnull
	 * @param attributes
	 *            ����Ϊnull
	 * @param forceCreate
	 *            �Ƿ�ǿ�ƴ���
	 * @return Node �Żص�ǰ�����Ľ��
	 * @throws Exception
	 */
	private static Node createNode(Document doc, Node parentNode, String nodeName, Object valueObj, Attribute[] attributes, boolean forceCreate) throws Exception
	{
		//��ȡ��Ӧ������ֵ
		String value = null;
		if (valueObj != null)
		{
			Class classTemp = valueObj.getClass();
			Field field = null;
			String fieldName = null;
			//���⴦��
			if (nodeName.equalsIgnoreCase("abstract"))
			{
				fieldName = "Abstract";
			}
			else
			{
				fieldName = nodeName;
			}
			while (!classTemp.getName().equals("java.lang.Object"))
			{
				try
				{
					field = classTemp.getDeclaredField(fieldName);
					break;
				}
				catch (NoSuchFieldException e)
				{
					logger.debug("try to find field in super class of " + classTemp.getName());
					classTemp = classTemp.getSuperclass();
				}
			}
			if (field != null)
			{
				field.setAccessible(true);
				value = (String) field.get(valueObj);
			}
			else
			{
				if (!forceCreate)
					throw new Exception("offer value object did not contained the field[" + nodeName + "]");
			}
		}
		else
		{
			//��forceCreate=falseʱ,valueObj����Ϊnull
			if (!forceCreate)
				throw new Exception("offer value object is null.");
		}
		Element element = null;
		if (forceCreate || (value != null && !"".equals(value.trim())))
		{
			//����Node
			element = doc.createElement(nodeName);
			if (attributes != null && attributes.length > 0)
			{
				for (int i = 0; i < attributes.length; i++)
				{
					if (attributes[i].isValid())
					{
						element.setAttribute(attributes[i].name, attributes[i].value);
					}
				}
			}
			if ((value != null && !"".equals(value.trim())))
			{
				Text content = doc.createTextNode(value);
				element.appendChild(content);
			}
			//parentNode ����Ϊ��
			if (parentNode != null)
				parentNode.appendChild(element);
		}
		return element;
	}
	private static void setValueFromNodeToObject(Node node, Object obj) throws Exception
	{
		short type = node.getNodeType();
		String strName = node.getNodeName();
		if (strName == null || "".equals(strName))
			return;
		String strFieldName = null;
		String strFieldValue = null;
		switch (type)
		{
			case Node.ATTRIBUTE_NODE :
				{
					strFieldName = node.getNodeName().trim();
					strFieldValue = node.getNodeValue();
					break;
				}
			case Node.TEXT_NODE :
				{
					Node parent = node.getParentNode();
					strFieldName = parent.getNodeName().trim();
					strFieldValue = node.getNodeValue();
					break;
				}
		}
		//		logger.debug("Node name:" + strName);
		//		logger.debug("Node type:" + type);
		//		logger.debug("Field name:" + strFieldName);
		//		logger.debug("Field value:" + strFieldValue);
		//��õ�ǰ����ָ�����Ƶ�Field����
		Class classTemp = obj.getClass();
		Field field = null;
		while (!classTemp.getName().equals("java.lang.Object"))
		{
			try
			{
				field = classTemp.getDeclaredField(strFieldName);
				field.setAccessible(true);
				break;
			}
			catch (NoSuchFieldException e)
			{
				logger.debug("try to find field in super class of " + classTemp.getName());
				classTemp = classTemp.getSuperclass();
			}
		}
		if (field == null)
		{
			logger.info(obj.getClass().getName() + " has not field named \"" + strFieldName + "\"");
			return;
		}
		//��ֵ
		try
		{
			if (strFieldValue != null && !"".equals(strFieldValue))
				field.set(obj, strFieldValue);
		}
		catch (IllegalArgumentException exp)
		{
			throw new Exception("��ֵ����������Ч��ӦΪ" + field.getType().getName());
		}
		catch (IllegalAccessException exp)
		{
			throw new Exception(classTemp.getName() + "�ܰ�ȫԼ�����޷����ʣ�");
		}
		catch (SecurityException exp)
		{
			throw new Exception(classTemp.getName() + "�ܰ�ȫԼ�����޷����ʣ�");
		}
	}
	public static void main(String[] args)
	{
		//XMLDataMarshalUtil util = new XMLDataMarshalUtil();
		//XMLDataMarshalUtil.VoucherListInfo info = util.new VoucherListInfo();
		try
		{
			//			GLVoucherInfo glVoucher = createVoucher();
			//
			//			info.addVoucher(glVoucher);
			//			Node node1 = util.unmarshal(info);
			//
			//			XMLHelper.writeXMLString(System.out, node1, "gb2312");
			//XMLDataMarshalUtil.ResponseXMLInfo infoTemp = util.new ResponseXMLInfo();
			//util.marshal(infoTemp, getDocument("C:\\Documents and Settings\\rongyang\\����\\distribute\\Xml\\Samples\\result.xml"));
			String a = "01-601011";
			int i = a.indexOf("-");
			System.out.println(i);
			a = a.substring(i+1);
			System.out.println(a);
			System.out.println(a.length());
			if(a.length() >= 6)
				System.out.println(a.substring(0, 6));
			//System.out.println(DataFormat.formatInt(5, 5));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private static GLVoucherInfo createVoucher() throws Exception
	{
		GLVoucherInfo glVoucher = new GLVoucherInfo();
		glVoucher.setModelID(11111111);
		glVoucher.setPostDate(Env.getSystemDateTime());
		glVoucher.setPostStatusID(22222222);
		glVoucher.setTransNo("20040223153000");
		GLEntryInfo glEntry = new GLEntryInfo();
		glEntry.setAbstract("Abstract");
		glEntry.setAmount(100.20);
		glEntry.setBankChequeNo("00012");
		glEntry.setCurrencyID(2);
		glEntry.setDirectionID(1);
		glEntry.setExecute((Timestamp) ConvertDataUtil.convertStringToDate("2004-02-12", "yyyy-MM-dd"));
		glEntry.setID(5555);
		glEntry.setInterestStart((Timestamp) ConvertDataUtil.convertStringToDate("2004-02-10", "yyyy-MM-dd"));
		glEntry.setOfficeID(1);
		glEntry.setStatusID(2);
		glEntry.setSubject("2356-8945");
		glEntry.setTransNo("20040229124500");
		ArrayList alTemp = new ArrayList(1);
		alTemp.add(glEntry);
		glVoucher.setList(alTemp);
		return glVoucher;
	}
	private static Node getDocument(String fileName) throws Exception
	{
		FileInputStream input = new FileInputStream(fileName);
		Node node = XMLHelper.parse(input, "gb2312");
		input.close();
		return node;
	}
	private static String convertEncoding(String source, String sEncoding, String oEncoding) throws UnsupportedEncodingException
	{
		return new String(source.getBytes(sEncoding));
	}
	private static void displayCode(String string)
	{
		if (string == null)
			return;
		System.out.println("\"" + string + "\" code is:\r\n");
		char[] chars = string.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			System.out.print("[" + (int) chars[i] + "]");
		}
		System.out.print("\r\n");
	}
}