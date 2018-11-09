/*
 * Created on 2003-9-3
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.account.dataentity;

import java.io.Serializable;

/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QueryClientConditionInfo implements Serializable
{
	private long OfficeID = -1; // ���´�ID
	private String StartClientCode = ""; // ��ѯ�ɿͻ����
	private String EndClientCode = ""; // ��ѯ���ͻ����
        private String LegalPerson="";//���˴���
        private String  ClientName="";//�ͻ�����
        private long  StartClientID=-1;
        private long EndClientID=-1;
        private long  Order = 0;
        private long Desc =-1;
        private long PageCurrent = 1;
	/**
	 * Returns the endClientCode.
	 * @return String
	 */
	public String getEndClientCode()
	{
		return EndClientCode;
	}

	/**
	 * Returns the officeID.
	 * @return long
	 */
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * Returns the startClientCode.
	 * @return String
	 */
	public String getStartClientCode()
	{
		return StartClientCode;
	}

        public String getLegalPerson()
        {
                return LegalPerson;
        }


        public String getClientName()
        {
                return ClientName;
        }


	/**
	 * Sets the endClientCode.
	 * @param endClientCode The endClientCode to set
	 */
	public void setEndClientCode(String endClientCode)
	{
		EndClientCode = endClientCode;
	}

	/**
	 * Sets the officeID.
	 * @param officeID The officeID to set
	 */
	public void setOfficeID(long officeID)
	{
		OfficeID = officeID;
	}

	/**
	 * Sets the startClientCode.
	 * @param startClientCode The startClientCode to set
	 */
	public void setStartClientCode(String startClientCode)
	{
		StartClientCode = startClientCode;
	}

        public void setLegalPerson(String legalPerson)
        {
                LegalPerson = legalPerson;
        }


        public void setClientName(String clientName)
        {
                ClientName = clientName;
        }

        public void setOrder(long order)
        {
                Order = order;
        }


        public void setDesc(long desc)
        {
                Desc = desc;
         }

         public void setPageCurrent(long pageCurrent)
        {
                PageCurrent = pageCurrent;
        }

        public long getOrder()
        {
                return Order;
        }

        public long getDesc()
        {
                return Desc;
        }

        public void setStartClientID(long startClientID)
        {
                StartClientID = startClientID;
        }

        public void setEndClientID(long endClientID)
        {
                EndClientID = endClientID;
        }

        public long getPageCurrent()
        {
                return PageCurrent;
        }

        public long getStartClientID()
        {
                return StartClientID;
        }

        public long getEndClientID()
        {
                return EndClientID;
        }



}
