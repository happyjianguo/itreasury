/*
 * Created on 2003-12-24
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.settlement.query.paraminfo;

import java.io.Serializable;
import java.sql.Timestamp;
/**
 * @author ruixie
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryDepositLoanWhereInfo
{
	public QueryDepositLoanWhereInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	private long OfficeID = -1;
	private long CurrencyID = -1;
	
	private Timestamp Date = null;//��ѯ����
	private long EnterpriseTypeID = -1;//��ҵ����
	private long EnterpriseTypeID1 = -1;//����1
	private long EnterpriseTypeID2 = -1;//����2
	private long EnterpriseTypeID3 = -1;//����3
	private long EnterpriseTypeID4 = -1;//����4
	private long EnterpriseTypeID5 = -1;//����5
	private long EnterpriseTypeID6 = -1;//����6
	private long DepositAccountTypeID = -1;//������ ���� ���� ֪ͨ
	private long ClientManager = -1;//�ͻ�����id
    private long LoanTypeID = -1; // ��������
    private long AccountTypeID = -1; // �˻�����
    private long CosignClientID = -1; // ί�з�
    private long LoanTerm = -1; // ��������
    private long LoanYear = -1; // ��������
    private long ExtendAttribute1 = -1;//��չ����1
    private long ExtendAttribute2 = -1;//��չ����2
    private long ExtendAttribute3 = -1;//��չ����3
    private long ExtendAttribute4 = -1;//��չ����4
    
    private long DepositLoanSearchID = -1;//���ñ��е�ID
	/**
	 * @return
	 */
	public Timestamp getDate()
	{
		return Date;
	}

	/**
	 * @return
	 */
	public long getEnterpriseTypeID()
	{
		return EnterpriseTypeID;
	}

	/**
	 * @param timestamp
	 */
	public void setDate(Timestamp timestamp)
	{
		Date = timestamp;
	}

	/**
	 * @param l
	 */
	public void setEnterpriseTypeID(long l)
	{
		EnterpriseTypeID = l;
	}

	/**
	 * @return
	 */
	public long getCurrencyID()
	{
		return CurrencyID;
	}

	/**
	 * @return Returns the clientManager.
	 */
	public long getClientManager() {
		return ClientManager;
	}
	/**
	 * @param clientManager The clientManager to set.
	 */
	public void setClientManager(long clientManager) {
		ClientManager = clientManager;
	}
	/**
	 * @return
	 */
	
	public long getOfficeID()
	{
		return OfficeID;
	}

	/**
	 * @param l
	 */
	public void setCurrencyID(long l)
	{
		CurrencyID = l;
	}

	/**
	 * @param l
	 */
	public void setOfficeID(long l)
	{
		OfficeID = l;
	}

	/**
	 * @return
	 */
	public long getAccountTypeID()
	{
		return AccountTypeID;
	}

	/**
	 * @param accountTypeID
	 */
	public void setAccountTypeID(long accountTypeID)
	{
		AccountTypeID = accountTypeID;
	}

	/**
	 * @return
	 */
	public long getCosignClientID()
	{
		return CosignClientID;
	}

	/**
	 * @param cosignClientID
	 */
	public void setCosignClientID(long cosignClientID)
	{
		CosignClientID = cosignClientID;
	}

	/**
	 * @return
	 */
	public long getLoanTerm()
	{
		return LoanTerm;
	}

	/**
	 * @param loanTerm
	 */
	public void setLoanTerm(long loanTerm)
	{
		LoanTerm = loanTerm;
	}

	/**
	 * @return
	 */
	public long getLoanTypeID()
	{
		return LoanTypeID;
	}

	/**
	 * @param loanTypeID
	 */
	public void setLoanTypeID(long loanTypeID)
	{
		LoanTypeID = loanTypeID;
	}

	/**
	 * @return
	 */
	public long getLoanYear()
	{
		return LoanYear;
	}

	/**
	 * @param loanYear
	 */
	public void setLoanYear(long loanYear)
	{
		LoanYear = loanYear;
	}

	/**
	 * @return
	 */
	public long getDepositAccountTypeID()
	{
		return DepositAccountTypeID;
	}

	/**
	 * @param l
	 */
	public void setDepositAccountTypeID(long l)
	{
		DepositAccountTypeID = l;
	}

	/**
	 * @return
	 */
	public long getDepositLoanSearchID()
	{
		return DepositLoanSearchID;
	}

	/**
	 * @param l
	 */
	public void setDepositLoanSearchID(long l)
	{
		DepositLoanSearchID = l;
	}

	/**
	 * @return Returns the extendAttribute1.
	 */
	public long getExtendAttribute1()
	{
		return ExtendAttribute1;
	}
	/**
	 * @param extendAttribute1 The extendAttribute1 to set.
	 */
	public void setExtendAttribute1(long extendAttribute1)
	{
		ExtendAttribute1 = extendAttribute1;
	}
	/**
	 * @return Returns the extendAttribute2.
	 */
	public long getExtendAttribute2()
	{
		return ExtendAttribute2;
	}
	/**
	 * @param extendAttribute2 The extendAttribute2 to set.
	 */
	public void setExtendAttribute2(long extendAttribute2)
	{
		ExtendAttribute2 = extendAttribute2;
	}
	/**
	 * @return Returns the extendAttribute3.
	 */
	public long getExtendAttribute3()
	{
		return ExtendAttribute3;
	}
	/**
	 * @param extendAttribute3 The extendAttribute3 to set.
	 */
	public void setExtendAttribute3(long extendAttribute3)
	{
		ExtendAttribute3 = extendAttribute3;
	}
	/**
	 * @return Returns the extendAttribute4.
	 */
	public long getExtendAttribute4()
	{
		return ExtendAttribute4;
	}
	/**
	 * @param extendAttribute4 The extendAttribute4 to set.
	 */
	public void setExtendAttribute4(long extendAttribute4)
	{
		ExtendAttribute4 = extendAttribute4;
	}
	/**
	 * @return Returns the enterpriseTypeID1.
	 */
	public long getEnterpriseTypeID1() {
		return EnterpriseTypeID1;
	}
	/**
	 * @param enterpriseTypeID1 The enterpriseTypeID1 to set.
	 */
	public void setEnterpriseTypeID1(long enterpriseTypeID1) {
		EnterpriseTypeID1 = enterpriseTypeID1;
	}
	/**
	 * @return Returns the enterpriseTypeID2.
	 */
	public long getEnterpriseTypeID2() {
		return EnterpriseTypeID2;
	}
	/**
	 * @param enterpriseTypeID2 The enterpriseTypeID2 to set.
	 */
	public void setEnterpriseTypeID2(long enterpriseTypeID2) {
		EnterpriseTypeID2 = enterpriseTypeID2;
	}
	/**
	 * @return Returns the enterpriseTypeID3.
	 */
	public long getEnterpriseTypeID3() {
		return EnterpriseTypeID3;
	}
	/**
	 * @param enterpriseTypeID3 The enterpriseTypeID3 to set.
	 */
	public void setEnterpriseTypeID3(long enterpriseTypeID3) {
		EnterpriseTypeID3 = enterpriseTypeID3;
	}
	/**
	 * @return Returns the enterpriseTypeID4.
	 */
	public long getEnterpriseTypeID4() {
		return EnterpriseTypeID4;
	}
	/**
	 * @param enterpriseTypeID4 The enterpriseTypeID4 to set.
	 */
	public void setEnterpriseTypeID4(long enterpriseTypeID4) {
		EnterpriseTypeID4 = enterpriseTypeID4;
	}
	/**
	 * @return Returns the enterpriseTypeID5.
	 */
	public long getEnterpriseTypeID5() {
		return EnterpriseTypeID5;
	}
	/**
	 * @param enterpriseTypeID5 The enterpriseTypeID5 to set.
	 */
	public void setEnterpriseTypeID5(long enterpriseTypeID5) {
		EnterpriseTypeID5 = enterpriseTypeID5;
	}
	/**
	 * @return Returns the enterpriseTypeID6.
	 */
	public long getEnterpriseTypeID6() {
		return EnterpriseTypeID6;
	}
	/**
	 * @param enterpriseTypeID6 The enterpriseTypeID6 to set.
	 */
	public void setEnterpriseTypeID6(long enterpriseTypeID6) {
		EnterpriseTypeID6 = enterpriseTypeID6;
	}
}
