/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.xmlmsg;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.IException;

/**
 * @author xintan
 *
 */
public class RequestQueryAccountDailyBalance extends RequestXMLInfo{

	public RequestQueryAccountDailyBalance() {
		super();
	}

	 private Vector vReqQuery = new Vector(); //查询指令集
		
		/**
		 * @return Returns the reqInstr.
		 */
		public Vector getReqQuery() 
		{
			return vReqQuery;
		}
		/**
		 * 
		 * @return int
		 */
	    public int getReqQuerySize()
	    {
	        return vReqQuery.size();
	    }
		
	    public void marshal(Node xmlDoc) throws Exception
	    {
	        if (xmlDoc == null)
	        {
	            throw new IException("null DOM object.");
	        }

	        traverse(xmlDoc);
	    }

	    private void traverse(Node node) throws Exception
	    {
	        short type = node.getNodeType();
	        String strName = node.getNodeName();
	        //logger.debug("Node name:" + strName);
	        //logger.debug("Node type:" + type);
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
	                    if ("QueryContent".equals(strName)
	                        && "QueryReq".equals(
	                            node.getParentNode().getNodeName()))
	                    {
	                    	ReqAccBalanceQuery reqQuery = new ReqAccBalanceQuery();

	                    	reqQuery.marshal(node);

	                        this.vReqQuery.add(reqQuery);

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
	            		XMLHelper.setValueFromNodeToObject(node, this);
	                    break;
	                }
	        }
	    }
		
	    public class ReqAccBalanceQuery 
	    {
	    	private String AccountCode = null ; //<AccountCode>帐户指令号</AccountCode>
	    	private String ExcuteStart =null;  //<ExcuteStart>查询起始日期</ExcuteStart>
	    	private String ExcuteEnd =null; //<ExcuteEnd>查询结束日期</ExcuteEnd>

	    	public QueryAccountWhereInfo toQueryBalanceForm()
			{
	    		QueryAccountWhereInfo queryAccBalanceForm = new QueryAccountWhereInfo();
	    		 queryAccBalanceForm.setStartAccountNo(this.AccountCode);
	    		 queryAccBalanceForm.setEndAccountNo(this.AccountCode);
	    		 queryAccBalanceForm.setStartQueryDate(DataFormat.getDateTime(this.ExcuteStart));
	    		 queryAccBalanceForm.setEndQueryDate(DataFormat.getDateTime(this.ExcuteEnd));
	    		return queryAccBalanceForm;
	    	}
            
	        public void marshal(Node xmlDoc) throws Exception
	        {
	            if (xmlDoc == null)
	            {
	                throw new IException("null DOM object.");
	            }

	            traverse(xmlDoc);
	        }

	        private void traverse(Node node) throws Exception
	        {
	            short type = node.getNodeType();
	            String strName = node.getNodeName();
	            //logger.debug("Node name:" + strName);
	            //logger.debug("Node type:" + type);
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
	            		XMLHelper.setValueFromNodeToObject(node, this);
	                    break;
	                }
	            }
	        }
			public String getAccountCode() {
				return AccountCode;
			}
			public void setAccountCode(String accountCode) {
				AccountCode = accountCode;
			}
			public String getExcuteEnd() {
				return ExcuteEnd;
			}
			public void setExcuteEnd(String excuteEnd) {
				ExcuteEnd = excuteEnd;
			}
			public String getExcuteStart() {
				return ExcuteStart;
			}
			public void setExcuteStart(String excuteStart) {
				ExcuteStart = excuteStart;
			}
	
	    }
}
