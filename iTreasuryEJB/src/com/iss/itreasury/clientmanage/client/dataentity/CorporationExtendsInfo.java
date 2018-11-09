/**
 * 
 */
package com.iss.itreasury.clientmanage.client.dataentity;

import com.iss.itreasury.util.ITreasuryBaseDataEntity;

/**
 * @author weihuang
 *
 */
public class CorporationExtendsInfo extends ITreasuryBaseDataEntity{
    
	private long lClientID=-1;  //�ͻ�id
	private String lClientNo = "";  //�ͻ����
	private String lClientName = ""; //�ͻ�����
	private String lMarketSpace1 = ""; // ���еص�1
	private String lStockNo1 = ""; //��Ʊ����1
	private String lMarketSpace2 = ""; // ���еص�2
	private String lStockNo2 = ""; //��Ʊ����2
	private String lMarketSpace3 = ""; // ���еص�3
	private String lStockNo3 = ""; //��Ʊ����3
	private String lMarketSpace4 = ""; // ���еص�4
	private String lStockNo4 = ""; //��Ʊ����4
	private String lMarketSpace5 = ""; // ���еص�5
	private String lStockNo5 = ""; //��Ʊ����5
	private long isMarkCompany =-1; //�Ƿ����й�˾
	
	
	public long getIsMarkCompany() {
		return isMarkCompany;
	}
	public void setIsMarkCompany(long isMarkCompany) {
		this.isMarkCompany = isMarkCompany;
		putUsedField("isMarkCompany", isMarkCompany);
	}
	public String getLMarketSpace2() {
		return lMarketSpace2;
	}
	public void setLMarketSpace2(String lmarketSpace2) {
		lMarketSpace2 = lmarketSpace2;
		putUsedField("lmarketSpace2", lmarketSpace2);
	}
	public String getLStockNo2() {
		return lStockNo2;
	}
	public void setLStockNo2(String lstockNo2) {
		lStockNo2 = lstockNo2;
		putUsedField("lstockNo2", lstockNo2);
	}
	public String getLMarketSpace3() {
		return lMarketSpace3;
	}
	public void setLMarketSpace3(String lmarketSpace3) {
		lMarketSpace3 = lmarketSpace3;
		putUsedField("lmarketSpace3", lmarketSpace3);
	}
	public String getLStockNo3() {
		return lStockNo3;
	}
	public void setLStockNo3(String lstockNo3) {
		lStockNo3 = lstockNo3;
		putUsedField("lstockNo3", lstockNo3);
	}
	public String getLMarketSpace4() {
		return lMarketSpace4;
	}
	public void setLMarketSpace4(String lmarketSpace4) {
		lMarketSpace4 = lmarketSpace4;
		putUsedField("lmarketSpace4", lmarketSpace4);
	}
	public String getLStockNo4() {
		return lStockNo4;
	}
	public void setLStockNo4(String lstockNo4) {
		lStockNo4 = lstockNo4;
		putUsedField("lstockNo4", lstockNo4);
	}
	public String getLMarketSpace5() {
		return lMarketSpace5;
	}
	public void setLMarketSpace5(String lmarketSpace5) {
		lMarketSpace5 = lmarketSpace5;
		putUsedField("lmarketSpace5", lmarketSpace5);
	}
	public String getLStockNo5() {
		return lStockNo5;
	}
	public void setLStockNo5(String lstockNo5) {
		lStockNo5 = lstockNo5;
		putUsedField("lstockNo5", lstockNo5);
	}
	public String getLClientNo() {
		return lClientNo;
	}
	public void setLClientNo(String lClientNo) {
		this.lClientNo = lClientNo;
		putUsedField("lClientNo", lClientNo);
	}
	public String getLClientName() {
		return lClientName;
	}
	public void setLClientName(String lClientName) {
		this.lClientName = lClientName;
		putUsedField("lClientName", lClientName);
	}
	public String getLMarketSpace1() {
		return lMarketSpace1;
	}
	public void setLMarketSpace1(String lMarketSpace1) {
		this.lMarketSpace1 = lMarketSpace1;
		putUsedField("lMarketSpace1", lMarketSpace1);
	}
	public String getLStockNo1() {
		return lStockNo1;
	}
	public void setLStockNo1(String lStockNo1) {
		this.lStockNo1 = lStockNo1;
		putUsedField("lStockNo1", lStockNo1);
	}
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}
	public long getLClientID() {
		return lClientID;
	}
	public void setLClientID(long lClientID) {
		this.lClientID = lClientID;
		putUsedField("lClientID", lClientID);
	}
	
	
	
	
	
}
