/*
 * Created on 2004-10-12
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.settlement.setting.dataentity;
import java.sql.*;

/**
 * @author stsun
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
  

/**
 * ��������Ϣ��
 * �������ڣ�(2002-1-15 17:26:45)
 * @author��Administrator
 */
public class GeneralLedgerInfo implements java.io.Serializable
{
    /**
     * GeneralLedgerInfo ������ע�⡣
     */
    public GeneralLedgerInfo()
    {
        super();
    };
    
    public long     m_lID=-1;                      //�������¼ID
    public long     m_lCurrencyID=-1;              //���ֱ�ʶ
    public long     m_lOfficeID=-1;		//���´���ʶ
    public long     m_lTransactionNoID=-1;         //���׺�ID
    public String   m_strTransactionNo="";         //���׺�
    public long     m_lClientID=-1;                //�ͻ�ID
    public String   m_strClientNo="";              //�ͻ����
    public String   m_strClientName="";            //�ͻ�����
    public long     m_lAccountID=-1;               //�˻�ID
    public String   m_strAccountNo="";             //�˻����
    public String   m_strAcccoutName="";           //�˻����� 
    public long     m_lDirection=1;               //�������
    public double   m_dAmount=0.0;                  //���                          
    public long     m_lGenLedgerIDOne=-1;          //��������ID1
    public String   m_strGeneralLedgerCodeOne="";  //������ҵ�����ͱ���1
    public String   m_strGeneralLedgerNameOne="";  //������ҵ����������1
    public long     m_lDirOne=1;                  //����1
    public double   m_dOne=0.0;                     //���1
    public long     m_lGenLedgerIDTwo=-1;          //��������ID2
    public String   m_strGeneralLedgerCodeTwo="";  //������ҵ�����ͱ���2
    public String   m_strGeneralLedgerNameTwo="";  //������ҵ����������2
    public long     m_lDirTwo=1;                  //����2
    public double   m_dTwo=0.0;                     //���2
    public long     m_lGenLedgerIDThree=-1;        //��������ID3
    public String   m_strGeneralLedgerCodeThree="";//������ҵ�����ͱ���3
    public String   m_strGeneralLedgerNameThree="";//������ҵ����������3
    public long     m_lDirThree=1;                //����3
    public double   m_dThree=0.0;                   //���3
    public long     m_lVoucherID=-1;               //ί��ƾ֤ID
    public String   m_strVoucherNo="";               //ί��ƾ֤No
    public String   m_strVoucherPassword="";       //ί��ƾ֤����
    public String   m_strAbstract="";              //ժҪ
    public Timestamp    m_tsInterestStart=null;      //��Ϣ��
    public Timestamp    m_tsExecute=null;            //ִ����
    public long     m_lStatus=-1;                  //״̬
    public long     m_lInputUserID=-1;             //¼����
    public String   m_strInputUserName="";
    public Timestamp    m_tsInput=null;              //¼������
    public long     m_lModifyUserID=-1;            //�޸���
    public String   m_strModifyUserName="";        
    public Timestamp    m_tsModify=null;             //�޸�����
    public long     m_lCheckUserID=-1;             //�����    
    public String m_strCheckUserName="";
    public Timestamp    m_tsCheck=null;              //�������
    public String   m_strCheckNote="";             //��˱�ע
    public long     m_lPageCount;                   //��ҳ��
    public String m_strGeneralLedgerCode = "";
    public String m_strName="";
    public String m_strSubjectCode = "";
    public long m_nStatusID = 1;
	/**
	 * @return Returns the m_dAmount.
	 */
	public double getM_dAmount() {
		return m_dAmount;
	}
	/**
	 * @param amount The m_dAmount to set.
	 */
	public void setM_dAmount(double amount) {
		m_dAmount = amount;
	}
	/**
	 * @return Returns the m_dOne.
	 */
	public double getM_dOne() {
		return m_dOne;
	}
	/**
	 * @param one The m_dOne to set.
	 */
	public void setM_dOne(double one) {
		m_dOne = one;
	}
	/**
	 * @return Returns the m_dThree.
	 */
	public double getM_dThree() {
		return m_dThree;
	}
	/**
	 * @param three The m_dThree to set.
	 */
	public void setM_dThree(double three) {
		m_dThree = three;
	}
	/**
	 * @return Returns the m_dTwo.
	 */
	public double getM_dTwo() {
		return m_dTwo;
	}
	/**
	 * @param two The m_dTwo to set.
	 */
	public void setM_dTwo(double two) {
		m_dTwo = two;
	}
	/**
	 * @return Returns the m_lAccountID.
	 */
	public long getM_lAccountID() {
		return m_lAccountID;
	}
	/**
	 * @param accountID The m_lAccountID to set.
	 */
	public void setM_lAccountID(long accountID) {
		m_lAccountID = accountID;
	}
	/**
	 * @return Returns the m_lCheckUserID.
	 */
	public long getM_lCheckUserID() {
		return m_lCheckUserID;
	}
	/**
	 * @param checkUserID The m_lCheckUserID to set.
	 */
	public void setM_lCheckUserID(long checkUserID) {
		m_lCheckUserID = checkUserID;
	}
	/**
	 * @return Returns the m_lClientID.
	 */
	public long getM_lClientID() {
		return m_lClientID;
	}
	/**
	 * @param clientID The m_lClientID to set.
	 */
	public void setM_lClientID(long clientID) {
		m_lClientID = clientID;
	}
	/**
	 * @return Returns the m_lCurrencyID.
	 */
	public long getM_lCurrencyID() {
		return m_lCurrencyID;
	}
	/**
	 * @param currencyID The m_lCurrencyID to set.
	 */
	public void setM_lCurrencyID(long currencyID) {
		m_lCurrencyID = currencyID;
	}
	/**
	 * @return Returns the m_lDirection.
	 */
	public long getM_lDirection() {
		return m_lDirection;
	}
	/**
	 * @param direction The m_lDirection to set.
	 */
	public void setM_lDirection(long direction) {
		m_lDirection = direction;
	}
	/**
	 * @return Returns the m_lDirOne.
	 */
	public long getM_lDirOne() {
		return m_lDirOne;
	}
	/**
	 * @param dirOne The m_lDirOne to set.
	 */
	public void setM_lDirOne(long dirOne) {
		m_lDirOne = dirOne;
	}
	/**
	 * @return Returns the m_lDirThree.
	 */
	public long getM_lDirThree() {
		return m_lDirThree;
	}
	/**
	 * @param dirThree The m_lDirThree to set.
	 */
	public void setM_lDirThree(long dirThree) {
		m_lDirThree = dirThree;
	}
	/**
	 * @return Returns the m_lDirTwo.
	 */
	public long getM_lDirTwo() {
		return m_lDirTwo;
	}
	/**
	 * @param dirTwo The m_lDirTwo to set.
	 */
	public void setM_lDirTwo(long dirTwo) {
		m_lDirTwo = dirTwo;
	}
	/**
	 * @return Returns the m_lGenLedgerIDOne.
	 */
	public long getM_lGenLedgerIDOne() {
		return m_lGenLedgerIDOne;
	}
	/**
	 * @param genLedgerIDOne The m_lGenLedgerIDOne to set.
	 */
	public void setM_lGenLedgerIDOne(long genLedgerIDOne) {
		m_lGenLedgerIDOne = genLedgerIDOne;
	}
	/**
	 * @return Returns the m_lGenLedgerIDThree.
	 */
	public long getM_lGenLedgerIDThree() {
		return m_lGenLedgerIDThree;
	}
	/**
	 * @param genLedgerIDThree The m_lGenLedgerIDThree to set.
	 */
	public void setM_lGenLedgerIDThree(long genLedgerIDThree) {
		m_lGenLedgerIDThree = genLedgerIDThree;
	}
	/**
	 * @return Returns the m_lGenLedgerIDTwo.
	 */
	public long getM_lGenLedgerIDTwo() {
		return m_lGenLedgerIDTwo;
	}
	/**
	 * @param genLedgerIDTwo The m_lGenLedgerIDTwo to set.
	 */
	public void setM_lGenLedgerIDTwo(long genLedgerIDTwo) {
		m_lGenLedgerIDTwo = genLedgerIDTwo;
	}
	/**
	 * @return Returns the m_lID.
	 */
	public long getM_lID() {
		return m_lID;
	}
	/**
	 * @param m_lid The m_lID to set.
	 */
	public void setM_lID(long m_lid) {
		m_lID = m_lid;
	}
	/**
	 * @return Returns the m_lInputUserID.
	 */
	public long getM_lInputUserID() {
		return m_lInputUserID;
	}
	/**
	 * @param inputUserID The m_lInputUserID to set.
	 */
	public void setM_lInputUserID(long inputUserID) {
		m_lInputUserID = inputUserID;
	}
	/**
	 * @return Returns the m_lModifyUserID.
	 */
	public long getM_lModifyUserID() {
		return m_lModifyUserID;
	}
	/**
	 * @param modifyUserID The m_lModifyUserID to set.
	 */
	public void setM_lModifyUserID(long modifyUserID) {
		m_lModifyUserID = modifyUserID;
	}
	/**
	 * @return Returns the m_lOfficeID.
	 */
	public long getM_lOfficeID() {
		return m_lOfficeID;
	}
	/**
	 * @param officeID The m_lOfficeID to set.
	 */
	public void setM_lOfficeID(long officeID) {
		m_lOfficeID = officeID;
	}
	/**
	 * @return Returns the m_lPageCount.
	 */
	public long getM_lPageCount() {
		return m_lPageCount;
	}
	/**
	 * @param pageCount The m_lPageCount to set.
	 */
	public void setM_lPageCount(long pageCount) {
		m_lPageCount = pageCount;
	}
	/**
	 * @return Returns the m_lStatus.
	 */
	public long getM_lStatus() {
		return m_lStatus;
	}
	/**
	 * @param status The m_lStatus to set.
	 */
	public void setM_lStatus(long status) {
		m_lStatus = status;
	}
	/**
	 * @return Returns the m_lTransactionNoID.
	 */
	public long getM_lTransactionNoID() {
		return m_lTransactionNoID;
	}
	/**
	 * @param transactionNoID The m_lTransactionNoID to set.
	 */
	public void setM_lTransactionNoID(long transactionNoID) {
		m_lTransactionNoID = transactionNoID;
	}
	/**
	 * @return Returns the m_lVoucherID.
	 */
	public long getM_lVoucherID() {
		return m_lVoucherID;
	}
	/**
	 * @param voucherID The m_lVoucherID to set.
	 */
	public void setM_lVoucherID(long voucherID) {
		m_lVoucherID = voucherID;
	}
	/**
	 * @return Returns the m_nStatusID.
	 */
	public long getM_nStatusID() {
		return m_nStatusID;
	}
	/**
	 * @param statusID The m_nStatusID to set.
	 */
	public void setM_nStatusID(long statusID) {
		m_nStatusID = statusID;
	}
	/**
	 * @return Returns the m_strAbstract.
	 */
	public String getM_strAbstract() {
		return m_strAbstract;
	}
	/**
	 * @param abstract1 The m_strAbstract to set.
	 */
	public void setM_strAbstract(String abstract1) {
		m_strAbstract = abstract1;
	}
	/**
	 * @return Returns the m_strAcccoutName.
	 */
	public String getM_strAcccoutName() {
		return m_strAcccoutName;
	}
	/**
	 * @param acccoutName The m_strAcccoutName to set.
	 */
	public void setM_strAcccoutName(String acccoutName) {
		m_strAcccoutName = acccoutName;
	}
	/**
	 * @return Returns the m_strAccountNo.
	 */
	public String getM_strAccountNo() {
		return m_strAccountNo;
	}
	/**
	 * @param accountNo The m_strAccountNo to set.
	 */
	public void setM_strAccountNo(String accountNo) {
		m_strAccountNo = accountNo;
	}
	/**
	 * @return Returns the m_strCheckNote.
	 */
	public String getM_strCheckNote() {
		return m_strCheckNote;
	}
	/**
	 * @param checkNote The m_strCheckNote to set.
	 */
	public void setM_strCheckNote(String checkNote) {
		m_strCheckNote = checkNote;
	}
	/**
	 * @return Returns the m_strCheckUserName.
	 */
	public String getM_strCheckUserName() {
		return m_strCheckUserName;
	}
	/**
	 * @param checkUserName The m_strCheckUserName to set.
	 */
	public void setM_strCheckUserName(String checkUserName) {
		m_strCheckUserName = checkUserName;
	}
	/**
	 * @return Returns the m_strClientName.
	 */
	public String getM_strClientName() {
		return m_strClientName;
	}
	/**
	 * @param clientName The m_strClientName to set.
	 */
	public void setM_strClientName(String clientName) {
		m_strClientName = clientName;
	}
	/**
	 * @return Returns the m_strClientNo.
	 */
	public String getM_strClientNo() {
		return m_strClientNo;
	}
	/**
	 * @param clientNo The m_strClientNo to set.
	 */
	public void setM_strClientNo(String clientNo) {
		m_strClientNo = clientNo;
	}
	/**
	 * @return Returns the m_strGeneralLedgerCode.
	 */
	public String getM_strGeneralLedgerCode() {
		return m_strGeneralLedgerCode;
	}
	/**
	 * @param generalLedgerCode The m_strGeneralLedgerCode to set.
	 */
	public void setM_strGeneralLedgerCode(String generalLedgerCode) {
		m_strGeneralLedgerCode = generalLedgerCode;
	}
	/**
	 * @return Returns the m_strGeneralLedgerCodeOne.
	 */
	public String getM_strGeneralLedgerCodeOne() {
		return m_strGeneralLedgerCodeOne;
	}
	/**
	 * @param generalLedgerCodeOne The m_strGeneralLedgerCodeOne to set.
	 */
	public void setM_strGeneralLedgerCodeOne(String generalLedgerCodeOne) {
		m_strGeneralLedgerCodeOne = generalLedgerCodeOne;
	}
	/**
	 * @return Returns the m_strGeneralLedgerCodeThree.
	 */
	public String getM_strGeneralLedgerCodeThree() {
		return m_strGeneralLedgerCodeThree;
	}
	/**
	 * @param generalLedgerCodeThree The m_strGeneralLedgerCodeThree to set.
	 */
	public void setM_strGeneralLedgerCodeThree(String generalLedgerCodeThree) {
		m_strGeneralLedgerCodeThree = generalLedgerCodeThree;
	}
	/**
	 * @return Returns the m_strGeneralLedgerCodeTwo.
	 */
	public String getM_strGeneralLedgerCodeTwo() {
		return m_strGeneralLedgerCodeTwo;
	}
	/**
	 * @param generalLedgerCodeTwo The m_strGeneralLedgerCodeTwo to set.
	 */
	public void setM_strGeneralLedgerCodeTwo(String generalLedgerCodeTwo) {
		m_strGeneralLedgerCodeTwo = generalLedgerCodeTwo;
	}
	/**
	 * @return Returns the m_strGeneralLedgerNameOne.
	 */
	public String getM_strGeneralLedgerNameOne() {
		return m_strGeneralLedgerNameOne;
	}
	/**
	 * @param generalLedgerNameOne The m_strGeneralLedgerNameOne to set.
	 */
	public void setM_strGeneralLedgerNameOne(String generalLedgerNameOne) {
		m_strGeneralLedgerNameOne = generalLedgerNameOne;
	}
	/**
	 * @return Returns the m_strGeneralLedgerNameThree.
	 */
	public String getM_strGeneralLedgerNameThree() {
		return m_strGeneralLedgerNameThree;
	}
	/**
	 * @param generalLedgerNameThree The m_strGeneralLedgerNameThree to set.
	 */
	public void setM_strGeneralLedgerNameThree(String generalLedgerNameThree) {
		m_strGeneralLedgerNameThree = generalLedgerNameThree;
	}
	/**
	 * @return Returns the m_strGeneralLedgerNameTwo.
	 */
	public String getM_strGeneralLedgerNameTwo() {
		return m_strGeneralLedgerNameTwo;
	}
	/**
	 * @param generalLedgerNameTwo The m_strGeneralLedgerNameTwo to set.
	 */
	public void setM_strGeneralLedgerNameTwo(String generalLedgerNameTwo) {
		m_strGeneralLedgerNameTwo = generalLedgerNameTwo;
	}
	/**
	 * @return Returns the m_strInputUserName.
	 */
	public String getM_strInputUserName() {
		return m_strInputUserName;
	}
	/**
	 * @param inputUserName The m_strInputUserName to set.
	 */
	public void setM_strInputUserName(String inputUserName) {
		m_strInputUserName = inputUserName;
	}
	/**
	 * @return Returns the m_strModifyUserName.
	 */
	public String getM_strModifyUserName() {
		return m_strModifyUserName;
	}
	/**
	 * @param modifyUserName The m_strModifyUserName to set.
	 */
	public void setM_strModifyUserName(String modifyUserName) {
		m_strModifyUserName = modifyUserName;
	}
	/**
	 * @return Returns the m_strName.
	 */
	public String getM_strName() {
		return m_strName;
	}
	/**
	 * @param name The m_strName to set.
	 */
	public void setM_strName(String name) {
		m_strName = name;
	}
	/**
	 * @return Returns the m_strTransactionNo.
	 */
	public String getM_strTransactionNo() {
		return m_strTransactionNo;
	}
	/**
	 * @param transactionNo The m_strTransactionNo to set.
	 */
	public void setM_strTransactionNo(String transactionNo) {
		m_strTransactionNo = transactionNo;
	}
	/**
	 * @return Returns the m_strVoucherNo.
	 */
	public String getM_strVoucherNo() {
		return m_strVoucherNo;
	}
	/**
	 * @param voucherNo The m_strVoucherNo to set.
	 */
	public void setM_strVoucherNo(String voucherNo) {
		m_strVoucherNo = voucherNo;
	}
	/**
	 * @return Returns the m_strVoucherPassword.
	 */
	public String getM_strVoucherPassword() {
		return m_strVoucherPassword;
	}
	/**
	 * @param voucherPassword The m_strVoucherPassword to set.
	 */
	public void setM_strVoucherPassword(String voucherPassword) {
		m_strVoucherPassword = voucherPassword;
	}
	/**
	 * @return Returns the m_tsCheck.
	 */
	public Timestamp getM_tsCheck() {
		return m_tsCheck;
	}
	/**
	 * @param check The m_tsCheck to set.
	 */
	public void setM_tsCheck(Timestamp check) {
		m_tsCheck = check;
	}
	/**
	 * @return Returns the m_tsExecute.
	 */
	public Timestamp getM_tsExecute() {
		return m_tsExecute;
	}
	/**
	 * @param execute The m_tsExecute to set.
	 */
	public void setM_tsExecute(Timestamp execute) {
		m_tsExecute = execute;
	}
	/**
	 * @return Returns the m_tsInput.
	 */
	public Timestamp getM_tsInput() {
		return m_tsInput;
	}
	/**
	 * @param input The m_tsInput to set.
	 */
	public void setM_tsInput(Timestamp input) {
		m_tsInput = input;
	}
	/**
	 * @return Returns the m_tsInterestStart.
	 */
	public Timestamp getM_tsInterestStart() {
		return m_tsInterestStart;
	}
	/**
	 * @param interestStart The m_tsInterestStart to set.
	 */
	public void setM_tsInterestStart(Timestamp interestStart) {
		m_tsInterestStart = interestStart;
	}
	/**
	 * @return Returns the m_tsModify.
	 */
	public Timestamp getM_tsModify() {
		return m_tsModify;
	}
	/**
	 * @param modify The m_tsModify to set.
	 */
	public void setM_tsModify(Timestamp modify) {
		m_tsModify = modify;
	}
}
