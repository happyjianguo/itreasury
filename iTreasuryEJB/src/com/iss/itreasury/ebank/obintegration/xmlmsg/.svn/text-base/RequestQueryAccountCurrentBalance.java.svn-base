/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.xmlmsg;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.settlement.query.paraminfo.QueryAccountWhereInfo;
import com.iss.itreasury.util.IException;

public class RequestQueryAccountCurrentBalance extends RequestXMLInfo{ 

    
	public RequestQueryAccountCurrentBalance() {
		super();
	}

	/**
	 * @param args
	 */
	private Vector vReqQuery = new Vector(); //查询指令集
	
    public Vector getVReqQuery() {
		return vReqQuery;
	}

	public void setVReqQuery(Vector reqQuery) {
		vReqQuery = reqQuery;
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
                    	ReqCurrentAccountBalanceQuery reqQuery = new ReqCurrentAccountBalanceQuery();

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
    
    public class ReqCurrentAccountBalanceQuery
    {
    	//<AccountCode>帐户号</AccountCode>
    	private String AccountCode =null;
 
    	public QueryAccountWhereInfo toQueryAccountAmountForm()
    	{
    		QueryAccountWhereInfo qQueryInfo = new QueryAccountWhereInfo();
    		qQueryInfo.setStartAccountNo(this.AccountCode );
    		qQueryInfo.setEndAccountNo(this.AccountCode );
    		return qQueryInfo;
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
    }
}
