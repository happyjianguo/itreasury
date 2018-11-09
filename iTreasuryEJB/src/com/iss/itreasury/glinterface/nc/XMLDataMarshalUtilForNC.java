package com.iss.itreasury.glinterface.nc;
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
import org.w3c.dom.Text;
import com.iss.itreasury.glinterface.dataentity.GLEntryInfo;
import com.iss.itreasury.glinterface.dataentity.GLVoucherInfo;
import com.iss.itreasury.glinterface.nc.NCBean.RequestDataPackage;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.settlement.generalledger.dataentity.GLBalanceInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Log4j;

/**
 * @author rongyang
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of
 * type comments go to Window>Preferences>Java>Code Generation.
 */

class XMLDataMarshalUtilForNC
{
	private static Log4j logger = new Log4j(Constant.ModuleType.SETTLEMENT);
	/**
	 * Constructor for XMLDataMarshalUtilForNC.
	 */
	public XMLDataMarshalUtilForNC()
	{
		super();
	}
	/**
	 * 将XML DOM对象转换为指定对象
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
	 * 将指定对象转换为XML DOM对象
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
			//获得当前对象指定名称的Field对象
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
		/*
		billtype="bdaccsubjplugin" 
		filename="示例.xml" 
		isexchange="Y" 
		proc="query" 
		receiver="Q88@Q88-0001" 
		replace="Y" 
		roottag="voucher" 
		sender="205"
		*/
		//<ufinterface roottag=’’ billtype=’’ docid=’’ receiver=’’ sender=’’
		// proc=’’ codeexchanged=’’ exportneedexch=’’ display=‘’ family=‘’/>
		protected final static String nodeName = "ufinterface";
		protected String roottag = null;
		protected String billtype = null;
		protected String docid = null;
		protected String receiver = null;
		protected String sender = null;
		protected String proc = null;
		protected String codeexchanged = null;
		protected String exportneedexch = null;
		protected String display = null;
		protected String family = null;
		
		protected String filename = null;
		protected String isexchange = null;
		protected String replace = null;
		
		
		/**
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#marshal(org.w3c.dom.Node)
		 */
		protected void marshal(Node xmlDoc) throws Exception
		{
			super.marshal(xmlDoc);
		}
		/**
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
		 */
		protected Node unmarshal(Document doc) throws Exception
		{
			Attribute[] attrs = new Attribute[13];
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
			
			attrs[10] = new Attribute("filename", this.filename);
			attrs[11] = new Attribute("isexchange", this.isexchange);
			attrs[12] = new Attribute("replace", this.replace);
			
			Node root = XMLDataMarshalUtilForNC.createNode(doc, null, RootInfo.nodeName, null, attrs, true);
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
	
		/**
		 * @return Returns the filename.
		 */
		public String getFilename() {
			return filename;
		}
		/**
		 * @param filename The filename to set.
		 */
		public void setFilename(String filename) {
			this.filename = filename;
		}
		/**
		 * @return Returns the isexchange.
		 */
		public String getIsexchange() {
			return isexchange;
		}
		/**
		 * @param isexchange The isexchange to set.
		 */
		public void setIsexchange(String isexchange) {
			this.isexchange = isexchange;
		}
		/**
		 * @return Returns the replace.
		 */
		public String getReplace() {
			return replace;
		}
		/**
		 * @param replace The replace to set.
		 */
		public void setReplace(String replace) {
			this.replace = replace;
		}
	}
	class ResponseXMLInfo extends XMLInfo
	{
		protected RootInfo rootInfo = null;
		private ArrayList resultSet = new ArrayList(128); //查询得到的结果集
		class ItemInfo extends XMLInfo
		{
			protected final static String nodeName = "item";
			protected String voucher_id = null; //：为一条记录的关键字；
			protected String succeed = null; //：成功标识：0：成功；非0：失败；
			protected String dsc = null; //：失败的描述；
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
							//处理当前结点的属性
							if (node.hasAttributes())
							{
								NamedNodeMap atts = node.getAttributes();
								for (int i = 0; i < atts.getLength(); i++)
								{
									XMLDataMarshalUtilForNC.setValueFromNodeToObject(atts.item(i), this);
								}
							}
							break;
						}
					case Node.TEXT_NODE :
						{
						XMLDataMarshalUtilForNC.setValueFromNodeToObject(node, this);
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
			//检查xmlDoc的格式是不是当前对象的格式
			//			boolean bFlag = false;
			//
			//			Document document = (Document) xmlDoc;
			//			Element root = document.getDocumentElement();
			//			if (RootInfo.nodeName.equals(root.getNodeName()))
			//			{
			//				//获取根结点下Text类型结点的第一个ELEMENT_NODE类型的结点
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
					XMLDataMarshalUtilForNC.setValueFromNodeToObject(node, this);
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
				protected String company = null; //公司的名称或编号,U8用户不用填写，给NC用户使用
				protected String voucher_type = null; //varchar(2) 凭证类别
				protected String fiscal_year = null; //凭证所属的会计年度
				protected String accounting_period = null; //tinyint 所属的会计期间
				protected String voucher_id = null; //smallint 凭证号
				protected String attachment_number = null; //smallint 所附单据的数量
				protected String date = null; //datetime 制单日期
				protected String enter = null; //varchar(20) 制单人名称
				protected String cashier = null; //varchar(20) 出纳名称
				protected String signature = null; //bit 是否已签字
				protected String checker = null; //varchar(20) 审核人名称
				protected String posting_date = null; //datetime 记账日期
				protected String posting_person = null; //varchar(20) 记账人名称
				protected String voucher_making_system = null; //varchar(10)
				// 制单系统标示,导入时必须填GL,即总帐系统单据
				protected String memo1 = null; //varchar (10) 备注1
				protected String memo2 = null; //varchar (10) 备注2
				protected String reserve1 = null; //varchar (20) 保留(外部凭证业务类型)
				protected String reserve2 = null; //varchar (50) 保留(外部凭证业务号)
				protected String revokeflag = null; //作废凭证此字段填1，正常凭证不填，有错凭证填2
				/**
				 * Constructor for VoucherHeadInfo.
				 */
				public VoucherHeadInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, VoucherHeadInfo.nodeName, this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "company", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "voucher_type", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "fiscal_year", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "accounting_period", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "voucher_id", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "attachment_number", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "date", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "enter", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "cashier", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "signature", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "checker", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "posting_date", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "posting_person", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "voucher_making_system", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "memo1", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "memo2", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "reserve1", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "reserve2", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "revokeflag", this, null, true);
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
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, VoucherBodyInfo.nodeName, this, null, true);
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
				protected String entry_id = null; //smallint 分录号
				protected String account_code = null; //varchar(15) 科目编码
				protected String Abstract = null; //varchar(60) 摘要
				protected String settlement = null; //varchar (3)
				// 结算方式,在科目是银行或往来时，可以填写此项
				protected String document_id = null; //varchar (30)
				// 票据号，在科目是银行或往来时，可以填写此项
				protected String document_date = null; //datetime
				// 票据日期，在科目是银行或往来时，可以填写此项
				protected String currency = null; //varchar (8) 币种
				protected String unit_price = null; //单价,在科目有数量核算时，填写此项
				protected String exchange_rate1 = null; //float 汇率1 主辅币核算时使用
				// 折辅汇率 原币*汇率1=辅币
				// NC用户使用
				protected String exchange_rate2 = null; //float 汇率2 折主汇率 折本汇率
				// 本币*汇率2=主币，U8用户使用
				protected String debit_quantity = null; //float
				// 借方数量,在科目有数量核算时，填写此项
				protected String primary_debit_amount = null; //money 原币借方发生额
				protected String secondary_debit_amount = null; //money
				// 辅币借方发生额
				protected String natural_debit_currency = null; //money
				// 本币借方发生额
				protected String credit_quantity = null; //float
				// 贷方数量,在科目有数量核算时，填写此项
				protected String primary_credit_amount = null; //money 原币贷方发生额
				protected String secondary_credit_amount = null; //money
				// 辅币贷方发生额
				protected String natural_credit_currency = null; //money
				// 本币贷方发生额
				protected String bill_type = null; //varchar (20)
				// 原始单据类型，导入凭证时填空
				protected String bill_id = null; //varchar (30) 原始单据编号，导入凭证时填空
				protected String bill_date = null; //datetime 原始单据日期，导入凭证时填空
				protected AuxiliaryInfo auxiliaryInfo = new AuxiliaryInfo(); //辅助核算
				protected DetailInfo detailInfo = new DetailInfo(); //明细
				/**
				 * Constructor for EntryInfo.
				 */
				public EntryInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, EntryInfo.nodeName, this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "entry_id", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "account_code", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "abstract", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "settlement", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "document_id", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "document_date", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "currency", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "unit_price", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "exchange_rate1", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "exchange_rate2", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "debit_quantity", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "primary_debit_amount", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "secondary_debit_amount", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "natural_debit_currency", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "credit_quantity", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "primary_credit_amount", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "secondary_credit_amount", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "natural_credit_currency", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "bill_type", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "bill_id", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "bill_date", this, null, true);
					root.appendChild(this.auxiliaryInfo.unmarshal(doc));
					root.appendChild(this.detailInfo.unmarshal(doc));
					return root;
				}
			}
			class AuxiliaryInfo extends XMLInfo
			{
				protected final static String nodeName = "auxiliary_accounting";
				protected String dept_id = null; //varchar (12) 部门
				protected String cperson_id = null; //varchar (8) 人员
				protected String ccus_id = null; //varchar (20) 客户
				protected String csup_id = null; //varchar (12) 供应商
				protected String item_class = null; //varchar(2) 项目大类
				protected String citem_id = null; //varchar (20) 项目档案
				protected String operator = null; //varchar (20) 业务员
				protected String define1 = null; //varchar 20 自定义字段1
				protected String define2 = null; //varchar 20 自定义字段2
				protected String define3 = null; //varchar 20 自定义字段3
				protected String define4 = null; //datetime 自定义字段4
				protected String define5 = null; //smallint 自定义字段5
				protected String define6 = null; //datetime 自定义字段6
				protected String define7 = null; //float 自定义字段7
				protected String define8 = null; //varchar 4 自定义字段8
				protected String define9 = null; //varchar 8 自定义字段9
				protected String define10 = null; //varchar 60 自定义字段10
				protected String define11 = null; //varchar 120 自定义字段11
				protected String define12 = null; //varchar 120 自定义字段12
				protected String define13 = null; //varchar 120 自定义字段13
				protected String define14 = null; //varchar 120 自定义字段14
				protected String define15 = null; //int 自定义字段15
				protected String define16 = null; //float 自定义字段16
				/**
				 * Constructor for AuxiliaryInfo.
				 */
				public AuxiliaryInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, AuxiliaryInfo.nodeName, this, null, true);
										
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
					
					
//					XMLDataMarshalUtilForNC.createNode(doc, root, "dept_id", this, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "cperson_id", this, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "ccus_id", this, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "csup_id", this, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "item_class", this, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "citem_id", this, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "operator", this, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define1", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define2", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define3", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define4", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define5", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define6", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define7", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define8", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define9", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define10", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define11", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define12", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define13", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define14", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define15", null, null, true);
//					XMLDataMarshalUtilForNC.createNode(doc, root, "define16", null, null, true);

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
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, DetailInfo.nodeName, this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "cash_flow_statement", null, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "code_remark_statement", null, null, true);
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
			 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
			 */
			protected Node unmarshal(Document doc) throws Exception
			{
				Node element = XMLDataMarshalUtilForNC.createNode(doc, null, VoucherInfo.nodeName, this, new Attribute[] { new Attribute("id", this.id)}, true);
				//生成voucher_head 凭证头
				element.appendChild(this.voucher_head.unmarshal(doc));
				//生成voucher_body 凭证体
				element.appendChild(this.voucher_body.unmarshal(doc));
				return element;
			}
			protected void setValues(GLVoucherInfo voucherInfo,long lOfficeID,long lCurrencyID) throws Exception
			{
				this.id = voucherInfo.getTransNo();
				
				//设置凭证头信息
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+Env.getClientName());
				//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+Env.GL_VOUCHERTYPE);
				
				GlSettingInfo  glSettingInfo=new GlSettingInfo();
				glSettingInfo=NCBean.getGlSettingInfo(lOfficeID,lCurrencyID);
				
				String	glVoucherType=glSettingInfo.getGlVoucherType();
				String glUserName=glSettingInfo.getGlUserName();
		
				this.voucher_head.voucher_type = DataFormat.toChinese(glVoucherType);
				this.voucher_head.fiscal_year = ConvertDataUtil.convertDateToString(voucherInfo.getPostDate(), "yyyy");
				this.voucher_head.accounting_period = ConvertDataUtil.convertDateToString(voucherInfo.getPostDate(), "M");
				//this.voucher_head.voucher_id = ConvertDataUtil.convertVoucherIDToU850(voucherInfo.getTransNo());
				this.voucher_head.voucher_id = voucherInfo.getVoucherNo();
				this.voucher_head.attachment_number = String.valueOf(voucherInfo.getList().size());
				this.voucher_head.enter = glUserName; //待修改
				this.voucher_head.posting_date = ConvertDataUtil.convertDateToString(voucherInfo.getPostDate(), "yyyy-MM-dd");
				this.voucher_head.date = this.voucher_head.posting_date;
				this.voucher_head.voucher_making_system = Constant.ModuleType.getName(voucherInfo.getModelID());
				//设置凭证体信息
				GLEntryInfo glEntryInfo = null;
				EntryInfo entryInfo = null;
				for (int i = 0; i < voucherInfo.getList().size(); i++)
				{
					
					glEntryInfo = (GLEntryInfo) voucherInfo.getList().get(i);
						if(i==0)
						{
							this.voucher_head.voucher_type = NCBean.getGlSettingInfo(glEntryInfo.getOfficeID(), glEntryInfo.getCurrencyID()).getGlVoucherType();
							this.voucher_head.enter = NCBean.getGlSettingInfo(glEntryInfo.getOfficeID(), glEntryInfo.getCurrencyID()).getGlUserName();
						}
					
					entryInfo = new EntryInfo();
					entryInfo.entry_id = String.valueOf(glEntryInfo.getID());
					entryInfo.account_code = glEntryInfo.getSubject();
					////根据不同的模块处理不同的摘要问题-----因为U850系统的摘要长度不能超过60个字符，所以需要特殊处理
					String strAbstract = glEntryInfo.getAbstract();
					if(glEntryInfo.getAbstract()!=null && glEntryInfo.getAbstract().length()>40)
					{
						strAbstract = strAbstract.substring(0,30);
					}
					if(voucherInfo.getModelID()==Constant.ModuleType.SETTLEMENT)
					{
						if(glEntryInfo.getTransNo()!=null && glEntryInfo.getTransNo().trim().length()!=0)
						{
							entryInfo.Abstract = DataFormat.formatNullString(Constant.ModuleType.getName(voucherInfo.getModelID()))+"/" + DataFormat.formatNullString(strAbstract)+"/"+glEntryInfo.getTransNo().substring(12);
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
					entryInfo.auxiliaryInfo.dept_id = null; //varchar (12) 部门
					entryInfo.auxiliaryInfo.cperson_id = null; //varchar (8) 人员
					entryInfo.auxiliaryInfo.ccus_id = null; //varchar (20) 客户
					entryInfo.auxiliaryInfo.csup_id = null; //varchar (12) 供应商
					entryInfo.auxiliaryInfo.item_class = null; //varchar(2) 项目大类
					entryInfo.auxiliaryInfo.citem_id = null; //varchar (20) 项目档案
					entryInfo.auxiliaryInfo.operator = null; //varchar (20) 业务员
					entryInfo.auxiliaryInfo.define1 = null; //varchar 20 自定义字段1
					entryInfo.auxiliaryInfo.define2 = null; //varchar 20 自定义字段2
					entryInfo.auxiliaryInfo.define3 = null; //varchar 20 自定义字段3
					entryInfo.auxiliaryInfo.define4 = null; //datetime 自定义字段4
					entryInfo.auxiliaryInfo.define5 = null; //smallint 自定义字段5
					entryInfo.auxiliaryInfo.define6 = null; //datetime 自定义字段6
					entryInfo.auxiliaryInfo.define7 = "0"; //float 自定义字段7
					entryInfo.auxiliaryInfo.define8 = null; //varchar 4 自定义字段8
					entryInfo.auxiliaryInfo.define9 = null; //varchar 8 自定义字段9
					entryInfo.auxiliaryInfo.define10 = null; //varchar 60 自定义字段10
					entryInfo.auxiliaryInfo.define11 = null; //varchar 120 自定义字段11
					entryInfo.auxiliaryInfo.define12 = null; //varchar 120 自定义字段12
					entryInfo.auxiliaryInfo.define13 = null; //varchar 120 自定义字段13
					entryInfo.auxiliaryInfo.define14 = null; //varchar 120 自定义字段14
					entryInfo.auxiliaryInfo.define15 = null; //int 自定义字段15
					entryInfo.auxiliaryInfo.define16 = null; //float 自定义字段16
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
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
		 */
		protected Node unmarshal(Document doc) throws Exception
		{
			Document newDoc = null;
			newDoc = new DocumentImpl();
			//加入根结点
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
		protected ArrayList billList = new ArrayList(1);	//目前不懂
		
		class BillInfo extends XMLInfo{
			/**
			 * Constructor for VoucherInfo.
			 */
			public BillInfo()
			{
				super();
			}
			protected final static String nodeName = "bill";
			protected BillHeadInfo bill_head = new BillHeadInfo();
			protected BillBodyInfo bill_body = new BillBodyInfo();
			class BillHeadInfo extends XMLInfo{		//head
				protected final static String nodeName = "billhead";
				protected String pk_corp = null; //不明白
				
				/**
				 * Constructor for VoucherHeadInfo.
				 */
				public BillHeadInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, BillHeadInfo.nodeName, this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "pk_corp", this, null, true);
					return root;
				}
				
			}
			class BillBodyInfo extends XMLInfo{		//body
				protected final static String nodeName = "billbody";
				protected String entry = null; //不明白
				/**
				 * Constructor for VoucherHeadInfo.
				 */
				public BillBodyInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, BillBodyInfo.nodeName, this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "entry", this, null, true);
					return root;
				}
				
			
				
			}
			
			/**
			 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
			 */
			protected Node unmarshal(Document doc) throws Exception
			{
				Node element = XMLDataMarshalUtilForNC.createNode(doc, null, BillInfo.nodeName, this, null, true);
				//生成voucher_head 凭证头
				element.appendChild(this.bill_head.unmarshal(doc));
				//生成voucher_body 凭证体
				element.appendChild(this.bill_body.unmarshal(doc));
				return element;
			}
			//设置值
			protected void setValues(BillInfo billInfo,long lOfficeID,long lCurrencyID) throws Exception{
				GlSettingInfo glInfo = NCBean.getGlSettingInfo(lOfficeID,lCurrencyID);
				this.bill_head.pk_corp=glInfo.getPk_corp();//1341
				
			}
		}
		
		/**
		 * Constructor for QuerySubjectInfo.
		 */
		RequestQuerySubjectInfo()
		{
			super();
		}
		/**
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
		 */
		public void addBill(BillInfo billInfo,long lOfficeID,long lCurrencyID) throws Exception
		{
			/*
			if (billInfo == null)
			{
				throw new IllegalArgumentException("offer voucherInfo is null.");
			}
			*/
			BillInfo info = new BillInfo();
			info.setValues(info,lOfficeID,lCurrencyID);
			this.billList.add(info);
		}
		protected Node unmarshal(Document doc) throws Exception
		{
			Document newDoc = null;
			newDoc = new DocumentImpl();
			//加入根结点
			Node root = rootInfo.unmarshal(newDoc);
			newDoc.appendChild(root);
			
			
			BillInfo billInfo = null;
			for (int i = 0; i < billList.size(); i++)
			{
				billInfo = (BillInfo) billList.get(i);
				root.appendChild(billInfo.unmarshal(newDoc));
			}
			return newDoc;
		}
	}
	
	//获取科目余额信息  请求类(发送XML)
	class RequestQuerySubjectBalanceInfo extends XMLInfo
	{
		protected RootInfo rootInfo = new RootInfo();
		protected ArrayList billList = new ArrayList(1);	//目前不懂
		class BillInfo extends XMLInfo{
			/**
			 * Constructor for VoucherInfo.
			 */
			public BillInfo()
			{
				super();
			}
			protected final static String nodeName = "bill";
			protected BillHeadInfo bill_head = new BillHeadInfo();
			protected BillBodyInfo bill_body = new BillBodyInfo();
			class BillHeadInfo extends XMLInfo{		//head
				protected final static String nodeName = "billhead";
				//update by dwj 20090927
				//protected String pk_corp = null; //不明白
				//<!--按会计期间或会计日期查询, P代表按会计期间，D代表按会计日期查询，不能空-->
				//<periodordate>D</periodordate >
				//<!--公司编码，可以是外部系统的编码，但是需要和NC系统的公司编码对照，不能空-->
				//<pk_corp>1002</pk_corp>
				//<!--会计年份，不能空-->
			    //<year>2009</year>
				//<!--会计期间，不能空-->
			    //<period>01</period >
				//<!--会计日期, 日期必须是 YYYY-MM-DD格式，如果periodordate=D不能空-->
				//<date>2009-01-20</date>
				//<!--是否需要期初数据, Y代表需要，N代表不需要，不能空-->
				//<ifneedbegin>Y</ifneedbegin >
				//<!--主体账簿（会计账簿）编码，此数据NC系统提供-->
				//<pk_glorgbook>0101-0002</pk_glorgbook>
				//<!--是否查询所有科目，Y代表是，N代表否，如果是Y，条件体内不需要再指定科目编码-->
				//<ifallaccsubj >N</ifallaccsubj >
				//<!--是否包含未记账凭证，Y代表是，N代表否，如果是Y-->
			    //<ifincludeuntally>Y</ifincludeuntally>
				protected String periodordate = null;
				protected String pk_corp = null;
				protected String year = null;
				protected String period = null;
				protected String date = null;
				protected String ifneedbegin = null;
				protected String pk_glorgbook = null;
				protected String ifallaccsubj = null;
				protected String ifincludeuntally = null;
				
				
				//end update by dwj 20090927
				/**
				 * Constructor for VoucherHeadInfo.
				 */
				public BillHeadInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, BillHeadInfo.nodeName, this, null, true);
					//XMLDataMarshalUtilForNC.createNode(doc, root, "pk_corp", this, null, true);
					//add by dwj 20090927
					XMLDataMarshalUtilForNC.createNode(doc, root, "periodordate", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "pk_corp", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "year", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "period", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "date", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "ifneedbegin", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "pk_glorgbook", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "ifallaccsubj", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "ifincludeuntally", this, null, true);
					//end add by dwj 20090927
					return root;
				}
				
			}

			class BillBodyInfo extends XMLInfo{		//body
				protected final static String nodeName = "billbody";
				private ArrayList entryList = new ArrayList(10);
				//protected String entry = null; //不明白
				
				//protected String pk_corp = null; //公司主键
				//protected String period = null;//会计期间
				//protected String year = null;//会计年度
				//protected String pk_accsubj = null;//科目编码
				//protected String pk_glorgbook = null;//主体账簿（会计账簿）
				
				/**
				 * Constructor for VoucherHeadInfo.
				 */
				
				public BillBodyInfo()
				{
					super();
				}
				
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, BillBodyInfo.nodeName, this, null, true);

					
					BillEntryInfo entryInfo = null;
					for (int i = 0; i < entryList.size(); i++)
					{
						entryInfo = (BillEntryInfo) entryList.get(i);
						root.appendChild(entryInfo.unmarshal(doc));
					}
					
					return root;
				}
				
			
				
			}
			
			/*
			 * 
			 	<!--公司主键-->
				<pk_corp>Q88</pk_corp>
				<!--会计期间-->
				<period>01</period>
				<!--会计年度-->
				<year>2008</year>
				<!--科目编码-->
				<pk_accsubj>1003</pk_accsubj>
				<!--主体账簿（会计账簿）-->
				<pk_glorgbook>Q88-0001</pk_glorgbook>
			 * 
			 */
			class BillEntryInfo extends XMLInfo{
				protected final static String nodeName = "entry";
				
				//update by dwj 20090927
				/*
				protected String pk_corp = null; //公司主键
				protected String period = null;//会计期间
				protected String year = null;//会计年度
				protected String pk_accsubj = null;//科目编码
				protected String pk_glorgbook = null;//主体账簿（会计账簿）
				*/
				protected String pk_accsubj = null;//需要查询科目编码
				protected String pk_currtype = null;//币种编码
				protected String localdebitamount = null;//科目本期本币借方发生额
				protected String debitamount = null;//科目本期原币借方发生额
				protected String localcreditamount = null;//科目本期本币贷方发生额
				protected String creditamount = null;//科目本期原币贷方发生额
				protected String localamountbegin = null;//科目本期本币期初余额
				protected String amountbegin = null;//科目本期原币期初余额
				protected String localamountend = null;//科目本期本币期末余额
				protected String amountend = null;//科目本期原币期末余额
				protected String balanorient = null;//科目方向
				//end update by dwj 20090927
				
				/**
				 * Constructor for VoucherHeadInfo.
				 */
				public BillEntryInfo()
				{
					super();
				}
				/**
				 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
				 */
				protected Node unmarshal(Document doc) throws Exception
				{
					Node root = XMLDataMarshalUtilForNC.createNode(doc, null, BillEntryInfo.nodeName, this, null, true);
					
					//update by dwj 20090927
					/*
					XMLDataMarshalUtilForNC.createNode(doc, root, "pk_corp", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "period", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "year", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "pk_accsubj", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "pk_glorgbook", this, null, true);
					*/
					XMLDataMarshalUtilForNC.createNode(doc, root, "pk_accsubj", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "pk_currtype", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "localdebitamount", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "debitamount", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "localcreditamount", this, null, true);
					
					XMLDataMarshalUtilForNC.createNode(doc, root, "creditamount", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "localamountbegin", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "amountbegin", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "localamountend", this, null, true);
					XMLDataMarshalUtilForNC.createNode(doc, root, "amountend", this, null, true);
					
					XMLDataMarshalUtilForNC.createNode(doc, root, "balanorient", this, null, true);
					//end update by dwj 20090927
					
					return root;
				}
				
			
				
			}
			
			/**
			 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
			 */
			protected Node unmarshal(Document doc) throws Exception
			{
				Node element = XMLDataMarshalUtilForNC.createNode(doc, null, BillInfo.nodeName, this, null, true);
				//生成voucher_head 凭证头
				element.appendChild(this.bill_head.unmarshal(doc));
				//生成voucher_body 凭证体
				element.appendChild(this.bill_body.unmarshal(doc));
				return element;
			}
			//设置值
			/*protected void setValues(BillInfo billInfo,long lOfficeID,long lCurrencyID) throws Exception{
				//this.bill_head.pk_corp="0001";			
			}*/
			
			protected void setValues(ArrayList arrayList,long lOfficeID,long lCurrencyID) throws Exception{
				//this.bill_head.pk_corp="0001";
				
				this.bill_body.entryList = arrayList;
			}
		}
		
		/**
		 * Constructor for QuerySubjectInfo.
		 */
		RequestQuerySubjectBalanceInfo()
		{
			super();
		}
		/**
		 * @see com.iss.itreasury.closesystem.XMLDataMarshalUtilForNC.XMLInfo#unmarshal(org.w3c.dom.Document)
		 */
		//public void addBill(BillInfo billInfo,long lOfficeID,long lCurrencyID) throws Exception 原
		//public void addBill(ArrayList arrayList,long lOfficeID,long lCurrencyID) throws Exception //update by dwj 20090927
		public void addBill(ArrayList arrayList,long lOfficeID,long lCurrencyID,BillInfo billInfo) throws Exception
		{
			/*
			if (billInfo == null)
			{
				throw new IllegalArgumentException("offer voucherInfo is null.");
			}
			*/
			BillInfo info = new BillInfo();
			/*info.setValues(info,lOfficeID,lCurrencyID);*/
			info.setValues(arrayList, lOfficeID, lCurrencyID);
			//add by dwj 20090928 在头中添加节点值
			info.bill_head = billInfo.bill_head;
			//end add by dwj 20090928 
			this.billList.add(info);
		}
		protected Node unmarshal(Document doc) throws Exception
		{
			Document newDoc = null;
			newDoc = new DocumentImpl();
			//加入根结点
			Node root = rootInfo.unmarshal(newDoc);
			newDoc.appendChild(root);
			
			
			BillInfo billInfo = null;
			for (int i = 0; i < billList.size(); i++)
			{
				billInfo = (BillInfo) billList.get(i);
				root.appendChild(billInfo.unmarshal(newDoc));
			}
			return newDoc;
		}
	}
	
	//得到的结果Info
	class ResponseQuerySubjectInfo extends XMLInfo
	{
		protected RootInfo rootInfo = null;
		private ArrayList resultSet = new ArrayList(128); //查询得到的结果集
		class SubjectInfo extends XMLInfo
		{
			protected final static String nodeName = "entry";
			protected String subjname = null; //Varchar 40 科目名称
			protected String subjcode = null; //Varchar 40 科目名称
			protected String pk_subjtype = null; //Varchar 14 科目类型(根据企业类型定义科目分类)
			protected String endflag = null; //Bit 1 是否末级科目
			protected String balanorient = null; //Bit 1 余额方向
			protected String sealflag = null; //Bit 1 是否封存，软通不用。
	
			/*
			protected final static String nodeName = "code";
			protected String id = null; //code child node value
			protected String type = null; //Varchar 14 科目类型(根据企业类型定义科目分类)
			protected String type_ename = null; //Varchar 50 科目类型英文名称
			protected String analysis_type = null; //Varchar 14 财务分析类型
			protected String analysis_type_ename = null; //Varchar 50
			// 财务分析类型英文名称
			protected String code = null; //Varchar 15
			// 科目编码(按科目编码原则进行编码,<主表关联项>)
			protected String name = null; //Varchar 40 科目名称
			protected String ename = null; //Varchar 100 科目英文名称
			protected String grade = null; //Tinyint 1 科目级次(必须与科目编码相匹配)
			protected String prop = null; //Bit 1 科目性质(False：来源True:占用)
			protected String acclist_style = null; //Varchar 10
			// 帐页格式(金额式,数量金额式,外币金额式,数量外币式)
			protected String acclist_style_ename = null; //Varchar 50 帐页格式英文名称
			protected String ac_assist = null; //Varchar 6 科目助记码
			protected String fc_name = null; //Varchar 8 外币名称(与外币主表关联)
			protected String measure_unit = null; //Varchar 6 计量单位
			protected String person_acc = null; //Bit 1 个人往来核算(不能与其他辅助核算同时设置)
			protected String cust_acc = null; //Bit 1 客户往来核算(可与部门,项目核算同时设置)
			protected String supplier_acc = null; //Bit 1
			// 供应商往来核算(可与部门,项目核算同时设置)
			protected String dept_acc = null; //Bit 1 部门核算(可与客户,供应商,项目核算同时设置)
			protected String item_acc = null; //Bit 1 项目核算(可与客户,供应商,部门核算同时设置)
			protected String item_category = null; //Varchar 2
			// 项目大类(与项目大类主表_item关联,当bitem=True)
			protected String date_acc = null; //Bit 1 日记帐
			protected String bank_acc = null; //Bit 1 银行帐
			protected String sum_flag = null; //Varchar 15
			// 是否汇总打印(打印凭证)(Null_不汇总,其它为本级或上级汇总科目)
			protected String end_item_flag = null; //Bit 1 是否末级科目
			protected String exchange_flag = null; //Bit 1 是否参与汇兑损益计算
			protected String cash_acc_flag = null; //Bit 1
			// 是否出纳(现金)科目(可指定上级科目,自动对下级科目设置此属性)
			protected String bank_acc_flag = null; //Bit 1
			// 是否出纳(银行)科目(可指定上级科目,自动对下级科目设置此属性)
			protected String bacc_enable_flag = null; //Bit 1 银行帐科目是否启用
			protected String bacc_balance_way = null; //Bit 1 True 银行帐科目对帐方向
			// True:借方, False:贷方
			protected String bacc_begin = null; //DateTime 8 银行帐科目启用时间
			protected String bacc_end = null; //DateTime 8 银行帐科目对帐截止日期
			protected String period_pl = null; //Tinyint 1
			// 期间损益:1_本年利润,销售成本:2_库存商品/3_销售收入/4_销售成本,汇兑损益:5_入帐科目
			protected String acc_seal_flag = null; //Bit 1 科目是否封存(已封存科目不能制单)
			protected String ctrled_acc = null; //Varchar 10
			// 受控科目(科目受其他系统的控制,系统ID名,受控科目是否可制单由帐套参数决定)
			protected String other_use_flag = null; //Int 4
			// 其它系统是否使用(由其它系统填写,0_未使用,非0_已使用)
			protected String self_define1 = null; //Bit 1 自定义字段1
			protected String self_define2 = null; //Bit 1 自定义字段2
			protected String self_define3 = null; //Bit 1 自定义字段3
			protected String self_define4 = null; //Bit 1 自定义字段4
			protected String self_define5 = null; //Bit 1 自定义字段5
			protected String self_define6 = null; //Bit 1 自定义字段6
			protected String self_define7 = null; //Bit 1 自定义字段7
			protected String self_define8 = null; //Bit 1 自定义字段8
			protected String self_define9 = null; //Bit 1 自定义字段9
			protected String self_define10 = null; //Bit 1 自定义字段10
			protected String itemtype = null; //在建工程项目科目类型 0_非在建工程项目科目 1_其他科目
			// 2_费用科目 >2_开支科目
			protected String proj_balance = null; //是否工程结算科目 GL_XMZCDetail
			protected String cashitem = null; //是否常用现金流量科目 True_自动单出子窗体
			*/
			
			/**
			 * Constructor for SubjectInfo.
			 */
			public SubjectInfo()
			{
				super();
			}
			protected void marshal(Node xmlDoc) throws Exception
			{
				System.out.println("==============开始10===============");			
				traverse(xmlDoc);
			}
			private void traverse(Node node) throws Exception
			{
				System.out.println("==============开始11===============");	
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
							//此处的条件用于处理code和子code结点同名的问题
							if ("entry".equals(node.getParentNode().getParentNode().getNodeName()))
							{
								System.out.println("==============开始12===============");	
								XMLDataMarshalUtilForNC.setValueFromNodeToObject(node, this);
							}
							break;
						}
				}
			}
		}
		//add by feiye 20080910   资产类型
		private long SToLForSubjectType(String sSubType){
			long lSubType=-1;
			if(sSubType.equals("资产")){
				lSubType=1;
			}else if(sSubType.equals("负债")){
				lSubType=2;
			}else if(sSubType.equals("所有者权益")){
				lSubType=3;
			}else if(sSubType.equals("成本")){
				lSubType=4;
			}else if(sSubType.equals("损益")){
				lSubType=4;
			}else if(sSubType.equals("表外")){
				lSubType=6;
			}
			return lSubType;
		}
		//add by feiye 20080910   借贷方向
		private long SToLForSubjectDirctor(String sSubDirctor){
			long lSubDirctor=-1;
			if(sSubDirctor.equals("借")){
				lSubDirctor=1;
			}else if(sSubDirctor.equals("贷")){
				lSubDirctor=2;
			}
			return lSubDirctor;
		}
		
//		add by feiye 20080910   是否未级
		private boolean SToLForSubjectEnd(String sSubEnd){
			boolean bSubEnd=false;
			if(sSubEnd.equals("Y")){
				bSubEnd=true;
			}else if(sSubEnd.equals("N")){
				bSubEnd=false;
			}
			return bSubEnd;
		}
		/**
		 * Constructor for ResponseQuerySubjectInfo.
		 */
		public ResponseQuerySubjectInfo()
		{
			super();
		}
		
		//获得大小
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
				
				System.out.println("余额方向: " + temp.balanorient);////Bit 1 余额方向
				System.out.println("是否末级科目: " + temp.endflag);//是否末级科目
				System.out.println("科目类型: " + temp.pk_subjtype);//Varchar 14 科目类型(根据企业类型定义科目分类)
				System.out.println("科目编号: " + temp.subjcode);//Varchar 40 科目编号
				System.out.println("科目名称：" + temp.subjname);//科目名称	
				System.out.println();
				
				
				result = new GLSubjectDefinitionInfo();
				result.setBalanceDirection(this.SToLForSubjectType(temp.balanorient));//余额方向
				result.setSubjectType(this.SToLForSubjectType(temp.pk_subjtype.trim()));//科目类型
				result.setLeaf(this.SToLForSubjectEnd(temp.endflag));//是否末级科目
				result.setSegmentCode2(temp.subjcode.trim());//科目编号
				result.setSegmentName2(temp.subjname.trim());//科目名称	
				/*result.setID(Long.valueOf(temp.id).longValue());
				result.setSubjectType(ConvertDataUtil.convertU850Subject(temp.type_ename));
				result.setLeaf(Boolean.valueOf(temp.end_item_flag).booleanValue());
				result.setSegmentCode2(temp.code);
				result.setSegmentName2(temp.name);*/
			}
			return result;
		}
		protected void marshal(Node xmlDoc) throws Exception
		{
			if (xmlDoc == null)
			{
				throw new IllegalArgumentException("null DOM object.");
			}
			//检查xmlDoc的格式是不是当前对象的格式
			//			boolean bFlag = false;
			//
			//			Document document = (Document) xmlDoc;
			//			Element root = document.getDocumentElement();
			//			if (RootInfo.nodeName.equals(root.getNodeName()))
			//			{
			//				//获取根结点下的第一个ELEMENT_NODE类型的结点
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
						XMLDataMarshalUtilForNC.setValueFromNodeToObject(node, this);
						break;
					}
			}
		}
	}
	
	//科目余额 （返回的info）把XML解析成info类
	class ResponseQuerySubjectBalanceInfo extends XMLInfo
	{

		protected RootInfo rootInfo = null;
		private ArrayList resultSet = new ArrayList(128); //查询得到的结果集
		class SubjectInfo extends XMLInfo
		{
			protected final static String nodeName = "entry";
			
			/*<pk_corp>Q88</pk_corp>
            <period>01</period>
            <year>2008</year>
            <subjcode>1003</subjcode>
            <glorgbookcode>Q88-0001</glorgbookcode>
            <localcreditamount>0</localcreditamount>
            <localdebitamount>101</localdebitamount>
            <balanorient>借</balanorient>*/
			//update by dwj 20090927
			/*
			 <entry>
	      <!--需要查询科目编码-->
	      <pk_accsubj>100101</pk_accsubj>
          <!--币种编码-->
	      <pk_currtype>CNY</pk_currtype>
           <!--科目本期本币借方发生额-->
	      <localdebitamount>100</localdebitamount >
          <!--科目本期原币借方发生额-->
	      <debitamount>101</debitamount >
           <!--科目本期本币贷方发生额-->
	      <localcreditamount>200</localcreditamount >
           <!--科目本期原币贷方发生额-->
	      <creditamount>201</creditamount >
          <!--科目本期本币期初余额-->
	      <localamountbegin>500</localamountbegin >
          <!--科目本期原币期初余额-->
	      <amountbegin>501</amountbegin>
           <!--科目本期本币期末余额-->
	      <localamountbegin>600</localamountbegin >
           <!--科目本期原币期末余额-->
	      <amountbegin>601</amountbegin >
          <!--科目方向-->
	       <balanorient>借</balanorient>
	 </entry>
			 */
			/*
			protected String pk_corp = null; //公司主键
			protected String period = null;//会计期间
			protected String year = null;//会计年度
			protected String subjcode = null;//科目编号(用)
			protected String glorgbookcode = null;//Q88-0001主体账簿（会计账簿）
			protected String localcreditamount = null;//贷方余额()
			protected String localdebitamount = null;//借方余额()
			protected String balanorient = null;//余额方向()
			*/
			//end update by dwj 20090927
			
			protected String pk_accsubj = null;//需要查询科目编码
			protected String pk_currtype = null;//币种编码
			protected String localdebitamount = null;//科目本期本币借方发生额
			protected String debitamount = null;//科目本期原币借方发生额
			protected String localcreditamount = null;//科目本期本币贷方发生额
			protected String creditamount = null;//科目本期原币贷方发生额
			protected String localamountbegin = null;//科目本期本币期初余额
			protected String amountbegin = null;//科目本期原币期初余额
			protected String localamountend = null;//科目本期本币期末余额
			protected String amountend = null;//科目本期原币期末余额
			protected String balanorient = null;//科目方向
			
			
			
			/**
			 * Constructor for SubjectInfo.
			 */
			public SubjectInfo()
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
							break;
						}
					case Node.TEXT_NODE :
						{
							//此处的条件用于处理code和子code结点同名的问题
							if ("entry".equals(node.getParentNode().getParentNode().getNodeName()))
							{
								XMLDataMarshalUtilForNC.setValueFromNodeToObject(node, this);
							}
							break;
						}
				}
			}
		}
		/**
		 * Constructor for ResponseQuerySubjectBalanceInfo.
		 */
		public ResponseQuerySubjectBalanceInfo()
		{
			super();
		}
		
		//获得大小
		public int getResultSetSize()
		{
			return resultSet.size();
		}
		
//		add by feiye 20080910   借贷方向
		private long SToLForSubjectDirctor(String sSubDirctor){
			long lSubDirctor=-1;
			/*
			if(sSubDirctor.equals("借")){
				lSubDirctor=1;
			}else if(sSubDirctor.equals("贷")){
				lSubDirctor=2;
			}
			*/
			if(sSubDirctor.equals("1")){//1借
				lSubDirctor=1;
			}else if(sSubDirctor.equals("2")){//2贷
				lSubDirctor=2;
			}
			return lSubDirctor;
		}
//		add by duwjun 20080910   字符串格式成数值
		private double SToDouble(String str)
		{
			Double d = new Double(0.0);
			
			try
			{
				d = Double.valueOf(str);
			}catch (Exception e) {
				
			}
			return d.doubleValue();
		}
		
		//public GLSubjectDefinitionInfo getResult(int index) throws Exception(原型)
		public GLBalanceInfo getResult(int index) throws Exception
		{
			GLBalanceInfo result = null;//GLSubjectDefinitionInfo
				
			SubjectInfo temp = (SubjectInfo) this.resultSet.get(index);
			if (temp != null)
			{
				result = new GLBalanceInfo();//GLSubjectDefinitionInfo()
				System.out.println("第" + index + "条数据");
				
				result.setBalanceDirection(this.SToLForSubjectDirctor(temp.balanorient));//余额方向
				System.out.println("余额方向: " + result.getBalanceDirection()+"******");
				
				//update by dwj 20090927
				//result.setGLSubjectCode(temp.subjcode);//科目编号
				result.setGLSubjectCode(temp.pk_accsubj);//科目编号
				//end update by dwj 20090927
				System.out.println("科目编号: " + result.getGLSubjectCode()+"******");
				
				//update by dwj 20090927 因为期末余额就是科目余额
				if(result.getBalanceDirection()==1)
				{
					//result.setDebitBalance(SToDouble(temp.localdebitamount));//借方余额
					result.setDebitBalance(SToDouble(temp.localamountend));//借方余额
					System.out.println("借方余额: " + result.getDebitBalance()+"******");
				}
				else
				{
					//result.setCreditBalance(-1*SToDouble(temp.localcreditamount));// 代方余额 balanorient
					result.setCreditBalance(SToDouble(temp.localamountend));// 代方余额 balanorient
					System.out.println("代方余额: " + result.getCreditBalance()+"******");
				}
				//end update by dwj 20090927
				
				//end update dwj 20080910
				result.setDebitAmount(SToDouble(temp.localdebitamount));//借方发生额
				System.out.println("借方发生额******" + result.getDebitAmount());
				result.setCreditAmount(SToDouble(temp.localcreditamount));//贷方发生额
				System.out.println("贷方发生额******" + result.getCreditAmount());
				//add by dwj 20090928
				
				//end add by dwj 20090928
			}
			return result;
		}
		protected void marshal(Node xmlDoc) throws Exception
		{
			if (xmlDoc == null)
			{
				throw new IllegalArgumentException("null DOM object.");
			}
			//检查xmlDoc的格式是不是当前对象的格式
			//			boolean bFlag = false;
			//
			//			Document document = (Document) xmlDoc;
			//			Element root = document.getDocumentElement();
			//			if (RootInfo.nodeName.equals(root.getNodeName()))
			//			{
			//				//获取根结点下的第一个ELEMENT_NODE类型的结点
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
						XMLDataMarshalUtilForNC.setValueFromNodeToObject(node, this);
						break;
					}
			}
		}
	}

	/**
	 * 根据指定的结点名称(nodeName)创建一个结点加入到指定的父结点(parentNode)
	 * 中。新创建的结点的值为value对象中与nodeName同名的域值。
	 * 
	 * 注：value对象中对应的域必须为String类型
	 * 
	 * 异 常： 当value对象不存在与nodeName同名的域()时，且forceCreate为false时，报异常
	 * 当value对象不存在与nodeName同名的域()时，且forceCreate为true时，生成空结点
	 * 当value对象中对应的域不是String类型时，报异常
	 * 当value对象中对应的域值为null时或“”时，且forceCreate为false时，生成空结点，否则不产生任何结点
	 * 当parentNode对象为null时，创建出的结点不执行加入父结点的操作，不报异常，供嵌套类使用
	 * 
	 * @param doc
	 *            不能为null
	 * @param parentNode
	 *            可以为null
	 * @param nodeName
	 *            不能为null
	 * @param valueObj
	 *            当forceCreate=false时不能为null
	 * @param attributes
	 *            可以为null
	 * @param forceCreate
	 *            是否强制创建
	 * @return Node 放回当前创建的结点
	 * @throws Exception
	 */
	private static Node createNode(Document doc, Node parentNode, String nodeName, Object valueObj, Attribute[] attributes, boolean forceCreate) throws Exception
	{
		//获取对应的属性值
		String value = null;
		if (valueObj != null)
		{
			Class classTemp = valueObj.getClass();
			Field field = null;
			String fieldName = null;
			//特殊处理
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
			//当forceCreate=false时,valueObj不能为null
			if (!forceCreate)
				throw new Exception("offer value object is null.");
		}
		Element element = null;
		if (forceCreate || (value != null && !"".equals(value.trim())))
		{
			//构造Node
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
			//parentNode 可以为空
			if (parentNode != null)
				parentNode.appendChild(element);
		}
		return element;
	}
	private static void setValueFromNodeToObject(Node node, Object obj) throws Exception
	{
		System.out.println("==============开始13===============");	
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
		//获得当前对象指定名称的Field对象
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
		//赋值
		try
		{
			if (strFieldValue != null && !"".equals(strFieldValue))
				field.set(obj, strFieldValue);
		}
		catch (IllegalArgumentException exp)
		{
			throw new Exception("赋值对象类型无效！应为" + field.getType().getName());
		}
		catch (IllegalAccessException exp)
		{
			throw new Exception(classTemp.getName() + "受安全约束，无法访问！");
		}
		catch (SecurityException exp)
		{
			throw new Exception(classTemp.getName() + "受安全约束，无法访问！");
		}
		System.out.println("==============开始14===============");	
	}
	public static void main(String[] args)
	{
		/*XMLDataMarshalUtilForNC util = new XMLDataMarshalUtilForNC();
		XMLDataMarshalUtilForNC.VoucherListInfo info = util.new VoucherListInfo();
		try
		{
			//			GLVoucherInfo glVoucher = createVoucher();
			//
			//			info.addVoucher(glVoucher);
			//			Node node1 = util.unmarshal(info);
			//
			//			XMLHelper.writeXMLString(System.out, node1, "gb2312");
			XMLDataMarshalUtilForNC.ResponseXMLInfo infoTemp = util.new ResponseXMLInfo();
			util.marshal(infoTemp, getDocument("C:\\Documents and Settings\\rongyang\\桌面\\distribute\\Xml\\Samples\\result.xml"));
			System.out.println(infoTemp);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/
		
		/*
		 * 说明：解析XML文件 
		 * 
		 * 科目解析
		 * 
		 */
		/*try
		{
			XMLDataMarshalUtilForNC util = new XMLDataMarshalUtilForNC();
			XMLDataMarshalUtilForNC.ResponseQuerySubjectInfo respXml = util.new ResponseQuerySubjectInfo();
			util.marshal(respXml, getDocument("E:\\xml\\a.xml"));//将XML文件解析成类
			System.out.println(respXml);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}*/
		
		/*
		 * 说明：解析XML文件
		 * 
		 * 科目余额解析
		 *
		 */
		
		try
		{
			XMLDataMarshalUtilForNC util = new XMLDataMarshalUtilForNC();
			XMLDataMarshalUtilForNC.ResponseQuerySubjectBalanceInfo respXml = util.new ResponseQuerySubjectBalanceInfo();
			util.marshal(respXml, getDocument("E:\\xml\\aa.xml"));//将XML文件解析成类
			System.out.println("得到的xml-----------开始---------");
			System.out.println(respXml);
			System.out.println("得到的xml-----------结束---------");
			System.out.println(respXml.getResultSetSize());
			for (int i = 0; i < respXml.getResultSetSize(); i++)
			{
				//update dwj 20080910
				//GLSubjectDefinitionInfo infoTemp = responseInfo.getResult(i);
				GLBalanceInfo infoTemp = respXml.getResult(i);
				//end update dwj 20080910
				//infoTemp.setGLDate(tsDate);
				//infoTemp.setCurrencyID(lCurrencyID);	//办事处
				//infoTemp.setOfficeID(lOfficeID);		//币种
				//collGlSubjectBalance.add(infoTemp);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		/*
		 * 说明：拼写XML文件(请求XML) 
		 */
		/*
		GLU850Bean temp = new GLU850Bean();
		RequestDataPackage requestData = temp.new RequestDataPackage();
		//创建XML解析工具类
		XMLDataMarshalUtilForNC utilTemp = new XMLDataMarshalUtilForNC();
		ArrayList arrayList = new ArrayList();
		XMLDataMarshalUtilForNC.RequestQuerySubjectBalanceInfo info = utilTemp.new RequestQuerySubjectBalanceInfo();
		XMLDataMarshalUtilForNC.RequestQuerySubjectBalanceInfo.BillInfo billInfo = info.new BillInfo();
		XMLDataMarshalUtilForNC.RequestQuerySubjectBalanceInfo.BillInfo.BillEntryInfo entryInfo = billInfo.new BillEntryInfo();
		
		info.rootInfo.billtype = "accsubj";
		info.rootInfo.filename = "55.xml";
		info.rootInfo.isexchange = "Y";
		info.rootInfo.proc = "query";
		info.rootInfo.receiver = "Q88@Q88-0001";
		info.rootInfo.replace = "Y";
		info.rootInfo.roottag = "bill";
		info.rootInfo.sender = "204";
		
		//<!--公司主键-->
		//<pk_corp>Q88</pk_corp>
		//<!--会计期间-->
		//<period>01</period>
		//<!--会计年度-->
		//<year>2008</year>
		//<!--科目编码-->
		//<pk_accsubj>1003</pk_accsubj>
		//<!--主体账簿（会计账簿）-->
		//<pk_glorgbook>Q88-0001</pk_glorgbook>
		entryInfo.pk_accsubj = "1003";//科目号
		entryInfo.pk_currtype = "CNY";//币种
		arrayList.add(entryInfo);
		try {
			//info.addBill(arrayList, 1, 1);
			Node xmlDom = utilTemp.unmarshal(info);
			requestData.setDataSegment(xmlDom);
			//System.out.println(xmlDom);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*
//		创建XML解析工具类
		XMLDataMarshalUtilForNC utilTemp = new XMLDataMarshalUtilForNC();
		//请求XML数据对象
		XMLDataMarshalUtilForNC.RequestQuerySubjectInfo requestInfo = utilTemp.new RequestQuerySubjectInfo();
		//结果XML数据对象		
		//XMLDataMarshalUtilForNC.ResponseQuerySubjectInfo responseInfo = utilTemp.new ResponseQuerySubjectInfo();

		
		//设置请求参数
		requestInfo.rootInfo.billtype = "bdaccsubjplugin";
		requestInfo.rootInfo.filename = "示例.xml";
		requestInfo.rootInfo.isexchange = "Y";
		requestInfo.rootInfo.proc = "query";
		requestInfo.rootInfo.receiver = "Q88@Q88-0001";
		requestInfo.rootInfo.replace = "Y";
		requestInfo.rootInfo.roottag = "voucher";
		requestInfo.rootInfo.sender = "205";
		
		try {
			requestInfo.addBill(null,1,1);
			GLU850Bean temp = new GLU850Bean();
			
			XMLDataMarshalUtilForNC util = new XMLDataMarshalUtilForNC();
//			准备请求Package数据
			RequestDataPackage requestData = temp.new RequestDataPackage();
			
			Node xmlDom = util.unmarshal(requestInfo);
			System.out.println("*******");
			System.out.println(xmlDom);
			System.out.println("*******");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("********");
			//System.out.println();
			System.out.println("********");
			requestData.setDataSegment(xmlDom);
			//XMLHelper
			//requestData.getDataSegment();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}
	private static GLVoucherInfo createVoucher() throws Exception
	{
		GLVoucherInfo glVoucher = new GLVoucherInfo();
		glVoucher.setModelID(11111111);
		glVoucher.setPostDate(new Timestamp(System.currentTimeMillis()));
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
