package com.iss.itreasury.ebank.system.dataentity;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import com.iss.itreasury.ebank.util.SessionOB;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author gqfang
 * 
 * TODO To change the template for this generated type comment go to Window - Preferences - Java - Code Style - Code Templates
 */
public class QueryBillPrintParam extends ITreasuryBaseDataEntity
{
    private long id                   = -1;  //Ψһ��ʶ

    private long clientIdFrom         = -1;  //�ͻ���ʶ ��

    private long clientIdTo           = -1;  //�ͻ���ʶ ��

    private long countryId            = -1;  //����Id

    private long oppContryId          = -1;  //�Է����������ڹ��ұ�ʶ

    private String bankType           = "";  //��������
    
    private long bankId               = -1;  //����ID

    private long currencyType         = -1;  //����

    private long ownerType            = -1;  //�˻�����������

    private long inputOrOutput        = -1;  //��֧����

    private long isDirectLink         = -1;  //�Ƿ���ֱ���˻� 1���ǣ�2������

    private long accountId            = -1;  //�����˻�Id

    private long accountPropertyOne   = -1;  //�˻�����һ

    private long accountPropertyTwo   = -1;  //�˻����Զ�

    private long accountPropertyThree = -1;  //�˻�������

    private Date transactionStartDate = null; //��ѯ������

    private Date transactionEndDate   = null; //��ѯ����ֹ

    private long desc                 = -1;  //����ʽ

    private long orderField           = -1;  //�����ֶ�
    
    private long officeID           = -1;  //���´�ID
    
    //add by zcwang 2008-4-30
    private long clientId = -1; 			//����λ
    
    private long subclientId = -1;          //�¼��ͻ�ID
    
    private long subaccountId = -1;  		//�¼��˻�ID
    
    //add by xwhe 2008-11-13
    private String  allbankId   ="";  //����ID

    public String getAllbankId() {
		return allbankId;
	}

	public void setAllbankId(String allbankId) {
		this.allbankId = allbankId;
	}

	public long getClientId() {
		return clientId;
	}

	public void setClientId(long clientId) {
		this.clientId = clientId;
	}

	public long getSubaccountId() {
		return subaccountId;
	}

	public void setSubaccountId(long subaccountId) {
		this.subaccountId = subaccountId;
	}

	public long getSubclientId() {
		return subclientId;
	}

	public void setSubclientId(long subclientId) {
		this.subclientId = subclientId;
	}
    
    public long getOfficeID() {
		return officeID;
	}

	public void setOfficeID(long officeID) {
		this.officeID = officeID;
	}
    
	/**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
    }

    /**
     * @return Returns the accountId.
     */
    public long getAccountId()
    {
        return accountId;
    }

    /**
     * @param accountId
     *            The accountId to set.
     */
    public void setAccountId(long accountId)
    {
        this.accountId = accountId;
    }

    /**
     * @return Returns the accountPropertyOne.
     */
    public long getAccountPropertyOne()
    {
        return accountPropertyOne;
    }

    /**
     * @param accountPropertyOne
     *            The accountPropertyOne to set.
     */
    public void setAccountPropertyOne(long accountPropertyOne)
    {
        this.accountPropertyOne = accountPropertyOne;
    }

    /**
     * @return Returns the accountPropertyThree.
     */
    public long getAccountPropertyThree()
    {
        return accountPropertyThree;
    }

    /**
     * @param accountPropertyThree
     *            The accountPropertyThree to set.
     */
    public void setAccountPropertyThree(long accountPropertyThree)
    {
        this.accountPropertyThree = accountPropertyThree;
    }

    /**
     * @return Returns the accountPropertyTwo.
     */
    public long getAccountPropertyTwo()
    {
        return accountPropertyTwo;
    }

    /**
     * @param accountPropertyTwo
     *            The accountPropertyTwo to set.
     */
    public void setAccountPropertyTwo(long accountPropertyTwo)
    {
        this.accountPropertyTwo = accountPropertyTwo;
    }

    /**
     * @return Returns the bankType.
     */
    public String getBankType()
    {
        return bankType;
    }

    /**
     * @param bankType
     *            The bankType to set.
     */
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }

    /**
     * @return Returns the clientIdFrom.
     */
    public long getClientIdFrom()
    {
        return clientIdFrom;
    }

    /**
     * @param clientIdFrom
     *            The clientIdFrom to set.
     */
    public void setClientIdFrom(long clientIdFrom)
    {
        this.clientIdFrom = clientIdFrom;
    }

    /**
     * @return Returns the clientIdTo.
     */
    public long getClientIdTo()
    {
        return clientIdTo;
    }

    /**
     * @param clientIdTo
     *            The clientIdTo to set.
     */
    public void setClientIdTo(long clientIdTo)
    {
        this.clientIdTo = clientIdTo;
    }

    /**
     * @return Returns the currencyType.
     */
    public long getCurrencyType()
    {
        return currencyType;
    }

    /**
     * @param currencyType
     *            The currencyType to set.
     */
    public void setCurrencyType(long currencyType)
    {
        this.currencyType = currencyType;
    }

    /**
     * @return Returns the inputOrOutput.
     */
    public long getInputOrOutput()
    {
        return inputOrOutput;
    }

    /**
     * @param inputOrOutput
     *            The inputOrOutput to set.
     */
    public void setInputOrOutput(long inputOrOutput)
    {
        this.inputOrOutput = inputOrOutput;
    }

    /**
     * @return Returns the isDirectLink.
     */
    public long getIsDirectLink()
    {
        return isDirectLink;
    }

    /**
     * @param isDirectLink
     *            The isDirectLink to set.
     */
    public void setIsDirectLink(long isDirectLink)
    {
        this.isDirectLink = isDirectLink;
    }

    /**
     * @return Returns the oppContryId.
     */
    public long getOppContryId()
    {
        return oppContryId;
    }

    /**
     * @param oppContryId
     *            The oppContryId to set.
     */
    public void setOppContryId(long oppContryId)
    {
        this.oppContryId = oppContryId;
    }

    /**
     * @return Returns the ownerType.
     */
    public long getOwnerType()
    {
        return ownerType;
    }

    /**
     * @param ownerType
     *            The ownerType to set.
     */
    public void setOwnerType(long ownerType)
    {
        this.ownerType = ownerType;
    }

    /**
     * @return Returns the desc.
     */
    public long getDesc()
    {
        return desc;
    }

    /**
     * @param desc
     *            The desc to set.
     */
    public void setDesc(long desc)
    {
        this.desc = desc;
    }

    /**
     * @return Returns the orderField.
     */
    public long getOrderField()
    {
        return orderField;
    }

    /**
     * @param orderField
     *            The orderField to set.
     */
    public void setOrderField(long orderField)
    {
        this.orderField = orderField;
    }

    /**
     * @return Returns the transactionEndDate.
     */
    public Date getTransactionEndDate()
    {
        return transactionEndDate;
    }

    /**
     * @param transactionEndDate
     *            The transactionEndDate to set.
     */
    public void setTransactionEndDate(Date transactionEndDate)
    {
        this.transactionEndDate = transactionEndDate;
    }

    /**
     * @return Returns the accountByOpenDate.
     */
    public String getTransactionEndDateString()
    {
        return "";
    }

    /**
     * @param accountByOpenDate
     *            The accountByOpenDate to set.
     */
    public void setTransactionEndDateString(String transactionEndDateString)
    {
        try
        {
            setTransactionEndDate(DataFormat.parseDate(
                    transactionEndDateString, DataFormat.FMT_DATE_YYYYMMDD));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return Returns the transactionStartDate.
     */
    public Date getTransactionStartDate()
    {
        return transactionStartDate;
    }

    /**
     * @param transactionStartDate
     *            The transactionStartDate to set.
     */
    public void setTransactionStartDate(Date transactionStartDate)
    {
        this.transactionStartDate = transactionStartDate;
    }

    /**
     * @return Returns the accountByOpenDate.
     */
    public String getTransactionStartDateString()
    {
        return "";
    }

    /**
     * @param accountByOpenDate
     *            The accountByOpenDate to set.
     */
    public void setTransactionStartDateString(String transactionStartDateString)
    {
        try
        {
            setTransactionStartDate(DataFormat.parseDate(
                    transactionStartDateString, DataFormat.FMT_DATE_YYYYMMDD));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return Returns the countryId.
     */
    public long getCountryId()
    {
        return countryId;
    }

    /**
     * @param countryId
     *            The countryId to set.
     */
    public void setCountryId(long countryId)
    {
        this.countryId = countryId;
    }
    
    private SessionOB sessionMng = null;
    
	public SessionOB getSessionMng()
	{
		return sessionMng;
	}

	public void setSessionMng(SessionOB sessionMng)
	{
		this.sessionMng = sessionMng;
	}
    
	/**
	 * ��request�еõ�������dataentity
	 * ֧�ֶ����Ƕ��
	 */
	public void convertRequestToDataEntity(ServletRequest request)
	{
		//Build a list of relevant request parameters from this request
		HashMap properties = new HashMap();
		// Iterator of parameter names
		Enumeration names = null;
		Boolean isChinese = (Boolean)request.getAttribute("_isChinese");
		//System.out.println("isChinese value:"+(isChinese==null?"null":""+isChinese.booleanValue()));
		names = request.getParameterNames();
		//System.out.println("Reqest�е���ֵ�ԣ�");
		while (names.hasMoreElements())
		{
			String name = (String) names.nextElement();
			//System.out.println("name:\""+name+"\"");
			String[] values = request.getParameterValues(name);
			for(int i = 0; i < values.length; i++)
			{
				if(values[i] == null || values[i].trim().length() <= 0)
				{
					values[i] = null;
				}
				else
				{
					String str = values[i].trim();
					if(isChinese == null || !isChinese.booleanValue())
					{
						values[i] = DataFormat.toChinese(str);
					}
				}
				//System.out.println("    value:\""+values[i]+"\"");
			}
			//System.out.println("Parameter : " + name + " = " + request.getParameter(name));
			properties.put(name, values);
		}
		request.setAttribute("_isChinese", new Boolean(true));
		// Set the corresponding properties of our helper bean
		try
		{
			BeanUtils.populate(this, properties);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("��requestӳ�䵽dataEntityʱ��������ԭ��"+e.getMessage());
		}
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		SessionOB sessionMng = (SessionOB) session.getAttribute("sessionMng");
		this.setSessionMng(sessionMng);
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
}