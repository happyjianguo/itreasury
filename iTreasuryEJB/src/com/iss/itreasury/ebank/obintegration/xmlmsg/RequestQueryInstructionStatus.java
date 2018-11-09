/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.xmlmsg;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.ebank.obfinanceinstr.dataentity.QueryCapForm;
import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.util.IException;

/**
 * @author xintan
 *
 */
public class RequestQueryInstructionStatus extends RequestXMLInfo
{
	/**
	 * 
	 */
	public RequestQueryInstructionStatus()
	{
		super();
	}
	 private Vector vReqQuery = new Vector(); //申请指令集
	
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
	                    	ReqQuery reqQuery = new ReqQuery();

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
		
	    public class ReqQuery 
	    {
			//<ApplyCode>业务申请编号</ApplyCode>
	    	private String ApplyCode = null;		
			//<ExcuteDate>执行日</ExcuteDate>
	    	private String ExcuteDate = null;

	    	public QueryCapForm toQueryCapForm()
			{
	    		QueryCapForm queryCapForm = new QueryCapForm();
	    		queryCapForm.setApplyCodeFrom(this.ApplyCode);
	    		queryCapForm.setApplyCodeTo(this.ApplyCode);
	    		queryCapForm.setStartExe(this.ExcuteDate);
	    		queryCapForm.setEndExe(this.ExcuteDate);
	    		return queryCapForm;
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
			/**
			 * @return Returns the applyCode.
			 */
			public String getApplyCode() {
				return ApplyCode;
			}
			/**
			 * @param applyCode The applyCode to set.
			 */
			public void setApplyCode(String applyCode) {
				ApplyCode = applyCode;
			}

			/**
			 * @return Returns the excuteDate.
			 */
			public String getExcuteDate() {
				return ExcuteDate;
			}
			/**
			 * @param excuteDate The excuteDate to set.
			 */
			public void setExcuteDate(String excuteDate) {
				ExcuteDate = excuteDate;
			}

	    }
}
