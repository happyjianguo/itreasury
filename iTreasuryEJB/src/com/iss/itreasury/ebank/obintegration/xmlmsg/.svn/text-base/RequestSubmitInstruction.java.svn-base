/**
 * created on Mar 12, 2008
 */
package com.iss.itreasury.ebank.obintegration.xmlmsg;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.iss.itreasury.ebank.obintegration.util.XMLHelper;
import com.iss.itreasury.util.IException;


/**
 * @author xintan
 * 
 * 指令提交申请请求报文
 */
public class RequestSubmitInstruction extends RequestXMLInfo 
{
	 private Vector vReqInstr = new Vector(); //申请指令集
     
	/**
	 * 
	 */
	public RequestSubmitInstruction() 
	{
		super();
	}
	
	/**
	 * @return Returns the reqInstr.
	 */
	public Vector getReqInstr() 
	{
		return vReqInstr;
	}
    
	/**
     * @param reqInstr The vReqInstr to set.
     */
    public void setReqInstr(Vector reqInstr) {
        this.vReqInstr = reqInstr;
    }

    /**
	 * 
	 * @return int
	 */
    public int getReqInstrSize()
    {
        return vReqInstr.size();
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
                    if ("InstrContent".equals(strName)
                        && "InstrReq".equals(
                            node.getParentNode().getNodeName()))
                    {
                    	ReqInstr reqInstr = new ReqInstr();

                    	reqInstr.marshal(node);

                        this.vReqInstr.add(reqInstr);

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
	
    public class ReqInstr 
    {
		//<ApplyCode>业务申请编号</ApplyCode>
    	private String ApplyCode = null;
		//<TransType>交易类型</TransType>  
    	private String TransType = null;
		//<Amount>金额</Amount>
    	private String Amount = null;
		//<ExcuteDate>执行日</ExcuteDate>
    	private String ExcuteDate = null;
		//<ClientCode>客户编号</ClientCode>
    	private String ClientCode = null;
		//<PayerAcctNo>付款方账号</PayerAcctNo>
    	private String PayerAcctNo = null;
		//<PayeeAcctNo>收款方账号</PayeeAcctNo> 
    	private String PayeeAcctNo = null;
		//<PayeeAcctName>收款方账户名称</PayeeAcctName>
    	private String PayeeAcctName = null;
		//<RemitBankName>汇入行名称</RemitBankName>
    	private String RemitBankName = null;
		//<RemitProvince>汇入省</RemitProvince>
    	private String RemitProvince = null;
		//<RemitCity>汇入市</RemitCity>
    	private String RemitCity = null;
		//<ComfirmUser>提交人</ComfirmUser>  
    	private String ConfirmUser = null;
		//<ComfirmTime>提交时间</ComfirmTime>
    	private String ConfirmTime = null;
		//<Note>摘要（备注）</Note>
    	private String Note = null;    	
    	//<RemitBankCode>汇入行编码</RemitBankCode>
    	private String RemitBankCode = null;

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
		 * @return Returns the amount.
		 */
		public String getAmount() {
			return Amount;
		}
		/**
		 * @param amount The amount to set.
		 */
		public void setAmount(String amount) {
			Amount = amount;
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
		 * @return Returns the clientCode.
		 */
		public String getClientCode() {
			return ClientCode;
		}
		/**
		 * @param clientCode The clientCode to set.
		 */
		public void setClientCode(String clientCode) {
			ClientCode = clientCode;
		}
		/**
		 * @return Returns the comfirmTime.
		 */
		public String getConfirmTime() {
			return ConfirmTime;
		}
		/**
		 * @param comfirmTime The comfirmTime to set.
		 */
		public void setConfirmTime(String confirmTime) {
			ConfirmTime = confirmTime;
		}
		/**
		 * @return Returns the comfirmUser.
		 */
		public String getConfirmUser() {
			return ConfirmUser;
		}
		/**
		 * @param comfirmUser The comfirmUser to set.
		 */
		public void setConfirmUser(String confirmUser) {
			ConfirmUser = confirmUser;
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
		/**
		 * @return Returns the note.
		 */
		public String getNote() {
			return Note;
		}
		/**
		 * @param note The note to set.
		 */
		public void setNote(String note) {
			Note = note;
		}
		/**
		 * @return Returns the payeeAcctName.
		 */
		public String getPayeeAcctName() {
			return PayeeAcctName;
		}
		/**
		 * @param payeeAcctName The payeeAcctName to set.
		 */
		public void setPayeeAcctName(String payeeAcctName) {
			PayeeAcctName = payeeAcctName;
		}
		/**
		 * @return Returns the payeeAcctNo.
		 */
		public String getPayeeAcctNo() {
			return PayeeAcctNo;
		}
		/**
		 * @param payeeAcctNo The payeeAcctNo to set.
		 */
		public void setPayeeAcctNo(String payeeAcctNo) {
			PayeeAcctNo = payeeAcctNo;
		}
		/**
		 * @return Returns the payerAcctNo.
		 */
		public String getPayerAcctNo() {
			return PayerAcctNo;
		}
		/**
		 * @param payerAcctNo The payerAcctNo to set.
		 */
		public void setPayerAcctNo(String payerAcctNo) {
			PayerAcctNo = payerAcctNo;
		}
		/**
		 * @return Returns the remitBankName.
		 */
		public String getRemitBankName() {
			return RemitBankName;
		}
		/**
		 * @param remitBankName The remitBankName to set.
		 */
		public void setRemitBankName(String remitBankName) {
			RemitBankName = remitBankName;
		}
		/**
		 * @return Returns the remitCity.
		 */
		public String getRemitCity() {
			return RemitCity;
		}
		/**
		 * @param remitCity The remitCity to set.
		 */
		public void setRemitCity(String remitCity) {
			RemitCity = remitCity;
		}
		/**
		 * @return Returns the remitProvince.
		 */
		public String getRemitProvince() {
			return RemitProvince;
		}
		/**
		 * @param remitProvince The remitProvince to set.
		 */
		public void setRemitProvince(String remitProvince) {
			RemitProvince = remitProvince;
		}
		/**
		 * @return Returns the transType.
		 */
		public String getTransType() {
			return TransType;
		}
		/**
		 * @param transType The transType to set.
		 */
		public void setTransType(String transType) {
			TransType = transType;
		}

		public String getRemitBankCode() {
			return RemitBankCode;
		}

		public void setRemitBankCode(String remitBankCode) {
			RemitBankCode = remitBankCode;
		}
    }

}
