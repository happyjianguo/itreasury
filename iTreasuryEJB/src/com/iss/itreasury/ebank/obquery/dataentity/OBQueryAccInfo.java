package com.iss.itreasury.ebank.obquery.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

 

public class OBQueryAccInfo extends ITreasuryBaseDataEntity{

    private long   id=-1;//
	private String bankId="";// ����ID���м��á�,�������Ӻ�ƴ�ɵĴ�
	private String areaCenter="";//�������ģ��м��á�,�������Ӻ�ƴ�ɵĴ�
	private String currencyId="";//���֣��м��á�,�������Ӻ�ƴ�ɵĴ�
	private boolean ifUniteCurren=false;//�Ƿ�ϲ�������ʾ
	private long baseCurrency=-1;//��������
	private String countryId="";//���ң��м��á�,�������Ӻ�ƴ�ɵĴ�
	private String accountId="";//�˻�ID���м��á�,�������Ӻ�ƴ�ɵĴ���ѡ�����˻���
	private String accttypeid="";//�˻�����id �����л��ڲ�
	private String startDate = null;		//��ѯ��ʼ����
    private String endDate = null;		//��ѯ��������
    private String underClient="";//������λ���м��á�,�������Ӻ�ƴ�ɵĴ�
    private long includeSelf = -1;//�Ƿ�����ͻ������˻�
    private long accID=-1;//�˻�ID�������˻�ID��ѯ������ϸ
    private long clientid = -1; //�����Ĳ�ѯ��������ǰ��¼�ͻ�ID 
    private long userid	  =-1;	//������ѯ����,��ǰ��¼���û�id
    private long officeId = -1; //���´�ID
    private long n_countryId = -1;
   
    /*
     * ��ѯ����
     * querytype = 1 ���˻����ײ�ѯ
     * querytype = 2 ��������ҵ�˻����ײ�ѯ
     */
    private long querytype = -1;
    
    private long desc                 = -1;  // ����ʽ

    private long orderField           = -1;  // �����ֶ�
    
    
    
    /**
     * @return Returns the desc.
     */
    public long getDesc()
    {
        return desc;
    }
    /**
     * @param desc The desc to set.
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
     * @param orderField The orderField to set.
     */
    public void setOrderField(long orderField)
    {
        this.orderField = orderField;
    }
    /**
     * @return Returns the querytype.
     */
    public long getQuerytype()
    {
        return querytype;
    }
    /**
     * @param querytype The querytype to set.
     */
    public void setQuerytype(long querytype)
    {
        this.querytype = querytype;
    }
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getAreaCenter() {
		return areaCenter;
	}
	public void setAreaCenter(String areaCenter) {
		this.areaCenter = areaCenter;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public long getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(long baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public boolean getIfUniteCurren() {
		return ifUniteCurren;
	}
	public void setIfUniteCurren(boolean ifUniteCurren) {
		this.ifUniteCurren = ifUniteCurren;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getUnderClient() {
		return underClient;
	}
	public void setUnderClient(String underClient) {
		this.underClient = underClient;
	}
	/**
	 * @return Returns the accID.
	 */
	public long getAccID() {
		return accID;
	}
	/**
	 * @param accID The accID to set.
	 */
	public void setAccID(long accID) {
		this.accID = accID;
	}
	/**
	 * @return Returns the clientid.
	 */
	public long getClientid() {
		return clientid;
	}
	/**
	 * @param clientid The clientid to set.
	 */
	public void setClientid(long clientid) {
		this.clientid = clientid;
	}
	
	/**
	 * @return Returns the includeSelf.
	 */
	public long getIncludeSelf() {
		return includeSelf;
	}
	/**
	 * @param includeSelf The includeSelf to set.
	 */
	public void setIncludeSelf(long includeSelf) {
		this.includeSelf = includeSelf;
	}
   
    /**
     * @return Returns the id.
     */
    public long getId()
    {
        return id;
    }
    /**
     * @param id The id to set.
     */
    public void setId(long id)
    {
        this.id = id;
    }
	public long getOfficeId() {
		return officeId;
	}
	public void setOfficeId(long officeId) {
		this.officeId = officeId;
	}
	
    /**
     * @return Returns the userid.
     */
    public long getUserid()
    {
        return userid;
    }
    /**
     * @param userid The userid to set.
     */
    public void setUserid(long userid)
    {
        this.userid = userid;
    }
    
    /**
     * @return Returns the accttypeid.
     */
    public String getAccttypeid()
    {
        return accttypeid;
    }
    /**
     * @param accttypeid The accttypeid to set.
     */
    public void setAccttypeid(String accttypeid)
    {
        this.accttypeid = accttypeid;
    }
	/**
	 * @return Returns the n_countryId.
	 */
	public long getN_countryId() {
		return n_countryId;
	}
	/**
	 * @param id The n_countryId to set.
	 */
	public void setN_countryId(long id) {
		n_countryId = id;
	}
}