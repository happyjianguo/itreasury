/*
 * Created on 2004-06-23
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.securities.market.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.securities.util.SECBaseDataEntity;

/**
 * @author kewen hu 2004-06-23 
 * 
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class MarketInfo extends SECBaseDataEntity implements java.io.Serializable {
	private long id = -1;				//Id
    //SECURITIESCODE	NOT NULL	VARCHAR2(100)
    private String SecuritiesCode = "";	// ֤ȯ����
    //CLOSEDATE	NOT NULL	DATE
    private Timestamp CloseDate = null;	// ������
    //SECURITIESNAME		VARCHAR2(100)
    private String SecuritiesName = "";	// ֤ȯ����
    //NETCLOSEPRICE		NUMBER(23,8)
    private double NetClosePrice = 0.0;	// ����(ֻ��ծȯ)
    //CLOSEPRICE		NUMBER(23,8)
    private double ClosePrice = 0.0;	// ���̼�(ծȯΪȫ��)
    //STATUSID		NUMBER
    private long StatusID = -1;			// ״̬
    //INPUTUSERID		NUMBER
    private long InputUserID = -1;		// ¼����ID
    //INPUTDATE		DATE
    private Timestamp InputDate = null;	// ¼��ʱ��
    //UPDATEUSERID		NUMBER
    private long UpdateUserID = -1;		// �޸���ID
    //UPDATEDATE		DATE
    private Timestamp UpdateDate = null;// �޸�ʱ��
    //COUNTERPARTID		NUMBER
    private long CounterpartID = -1;	// �������˾ID
    //TYPEID		NUMBER
    private long SecuritiesTypeID = -1;	// ֤ȯ���

    private long TypeID = -1;			// ֤ȯ���
    private String CounterpartName = "";// �������˾����
    private long SecuritiesID = -1;		// ֤ȯID
    private String InputUserName = "";	// ¼����
    private String UpdateUserName = "";	// �޸���
	/**
	 * @return Returns the closeDate.
	 */
	public Timestamp getCloseDate() {
		return CloseDate;
	}
	/**
	 * @param closeDate The closeDate to set.
	 */
	public void setCloseDate(Timestamp closeDate) {
		CloseDate = closeDate;
	}
	/**
	 * @return Returns the closePrice.
	 */
	public double getClosePrice() {
		return ClosePrice;
	}
	/**
	 * @param closePrice The closePrice to set.
	 */
	public void setClosePrice(double closePrice) {
		ClosePrice = closePrice;
	}
	/**
	 * @return Returns the counterpartID.
	 */
	public long getCounterpartID() {
		return CounterpartID;
	}
	/**
	 * @param counterpartID The counterpartID to set.
	 */
	public void setCounterpartID(long counterpartID) {
		CounterpartID = counterpartID;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate() {
		return InputDate;
	}
	/**
	 * @param inputDate The inputDate to set.
	 */
	public void setInputDate(Timestamp inputDate) {
		InputDate = inputDate;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID() {
		return InputUserID;
	}
	/**
	 * @param inputUserID The inputUserID to set.
	 */
	public void setInputUserID(long inputUserID) {
		InputUserID = inputUserID;
	}
	/**
	 * @return Returns the inputUserName.
	 */
	public String getInputUserName() {
		return InputUserName;
	}
	/**
	 * @param inputUserName The inputUserName to set.
	 */
	public void setInputUserName(String inputUserName) {
		InputUserName = inputUserName;
	}
	/**
	 * @return Returns the netClosePrice.
	 */
	public double getNetClosePrice() {
		return NetClosePrice;
	}
	/**
	 * @param netClosePrice The netClosePrice to set.
	 */
	public void setNetClosePrice(double netClosePrice) {
		NetClosePrice = netClosePrice;
	}
	/**
	 * @return Returns the securitiesCode.
	 */
	public String getSecuritiesCode() {
		return SecuritiesCode;
	}
	/**
	 * @param securitiesCode The securitiesCode to set.
	 */
	public void setSecuritiesCode(String securitiesCode) {
		SecuritiesCode = securitiesCode;
	}
	/**
	 * @return Returns the securitiesName.
	 */
	public String getSecuritiesName() {
		return SecuritiesName;
	}
	/**
	 * @param securitiesName The securitiesName to set.
	 */
	public void setSecuritiesName(String securitiesName) {
		SecuritiesName = securitiesName;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID() {
		return StatusID;
	}
	/**
	 * @param statusID The statusID to set.
	 */
	public void setStatusID(long statusID) {
		StatusID = statusID;
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate() {
		return UpdateDate;
	}
	/**
	 * @param updateDate The updateDate to set.
	 */
	public void setUpdateDate(Timestamp updateDate) {
		UpdateDate = updateDate;
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserID() {
		return UpdateUserID;
	}
	/**
	 * @param updateUserID The updateUserID to set.
	 */
	public void setUpdateUserID(long updateUserID) {
		UpdateUserID = updateUserID;
	}
	/**
	 * @return Returns the updateUserName.
	 */
	public String getUpdateUserName() {
		return UpdateUserName;
	}
	/**
	 * @param updateUserName The updateUserName to set.
	 */
	public void setUpdateUserName(String updateUserName) {
		UpdateUserName = updateUserName;
	}
	/**
	 * @return Returns the securitiesTypeID.
	 */
	public long getSecuritiesTypeID() {
		return SecuritiesTypeID;
	}
	/**
	 * @param securitiesTypeID The securitiesTypeID to set.
	 */
	public void setSecuritiesTypeID(long securitiesTypeID) {
		SecuritiesTypeID = securitiesTypeID;
	}
	/**
	 * @return Returns the securitiesID.
	 */
	public long getSecuritiesID() {
		return SecuritiesID;
	}
	/**
	 * @param securitiesID The securitiesID to set.
	 */
	public void setSecuritiesID(long securitiesID) {
		SecuritiesID = securitiesID;
	}
	/**
	 * @return Returns the counterpartName.
	 */
	public String getCounterpartName() {
		return CounterpartName;
	}
	/**
	 * @param counterpartName The counterpartName to set.
	 */
	public void setCounterpartName(String counterpartName) {
		CounterpartName = counterpartName;
	}
	/**
	 * @return Returns the typeID.
	 */
	public long getTypeID() {
		return TypeID;
	}
	/**
	 * @param typeID The typeID to set.
	 */
	public void setTypeID(long typeID) {
		TypeID = typeID;
	}
	/**
	 * @return Returns the id.
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(long id) {
		this.id = id;
	}
}