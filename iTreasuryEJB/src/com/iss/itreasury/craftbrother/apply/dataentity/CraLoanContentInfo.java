/**
 * 
 */
package com.iss.itreasury.craftbrother.apply.dataentity;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import com.iss.itreasury.securities.util.SECBaseDataEntity;
import com.iss.itreasury.util.IException;

/**
 * @author sunjing
 * 2011-04-15
 *
 */
public class CraLoanContentInfo extends SECBaseDataEntity {
	
	private long    id = -1;            
	private String  sCode = null;         	//编号
	private String  sName = null;       	//借款人
	private String  contractCode = null;	//借款合同编号
	private String  applyCode = null;		//申请编号
	private double  contractAmount = 0.0;	//合同原始金额
	private double  transferAmount = 0.0;	//转让金额
	private Timestamp  dtStart = null;		//贷款起始日
	private Timestamp  dtEnd = null;		//贷款结束日
	private double  rate = 0.0;				//利率
	private String  assureStatus = null;	//担保状况
	private long applyId = -1;				//申请ID
	/**
	 * @return the applyId
	 */
	public long getApplyId() {
		return applyId;
	}
	/**
	 * @param applyId the applyId to set
	 */
	public void setApplyId(long applyId) {
		this.applyId = applyId;
		putUsedField("applyId", applyId);
	}
	private long  statusId = -1;			//状态
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @return the sCode
	 */
	public String getsCode() {
		return sCode;
	}
	/**
	 * @return the sName
	 */
	public String getsName() {
		return sName;
	}
	/**
	 * @return the contractCode
	 */
	public String getContractCode() {
		return contractCode;
	}
	/**
	 * @return the applyCode
	 */
	public String getApplyCode() {
		return applyCode;
	}
	public double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(double contractAmount) {
		this.contractAmount = contractAmount;
		putUsedField("contractAmount", contractAmount);
	}
	/**
	 * @return the transferAmount
	 */
	public double getTransferAmount() {
		return transferAmount;
	}
	/**
	 * @return the dtStart
	 */
	public Timestamp getDtStart() {
		return dtStart;
	}
	/**
	 * @return the dtEnd
	 */
	public Timestamp getDtEnd() {
		return dtEnd;
	}
	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * @return the assureStatus
	 */
	public String getAssureStatus() {
		return assureStatus;
	}
	/**
	 * @return the statusId
	 */
	public long getStatusId() {
		return statusId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
		putUsedField("id", id);
	}
	/**
	 * @param sCode the sCode to set
	 */
	public void setsCode(String sCode) {
		this.sCode = sCode;
		putUsedField("sCode", sCode);
	}
	/**
	 * @param sName the sName to set
	 */
	public void setsName(String sName) {
		this.sName = sName;
		putUsedField("sName", sName);
	}
	/**
	 * @param contractCode the contractCode to set
	 */
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
		putUsedField("contractCode", contractCode);
	}
	/**
	 * @param applyCode the applyCode to set
	 */
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
		putUsedField("applyCode", applyCode);
	}
	/**
	 * @param transferAmount the transferAmount to set
	 */
	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
		putUsedField("transferAmount", transferAmount);
	}
	/**
	 * @param dtStart the dtStart to set
	 */
	public void setDtStart(Timestamp dtStart) {
		this.dtStart = dtStart;
		putUsedField("dtStart", dtStart);
	}
	/**
	 * @param dtEnd the dtEnd to set
	 */
	public void setDtEnd(Timestamp dtEnd) {
		this.dtEnd = dtEnd;
		putUsedField("dtEnd", dtEnd);
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
		putUsedField("rate", rate);
	}
	/**
	 * @param assureStatus the assureStatus to set
	 */
	public void setAssureStatus(String assureStatus) {
		this.assureStatus = assureStatus;
		putUsedField("assureStatus", assureStatus);
	}
	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(long statusId) {
		this.statusId = statusId;
		putUsedField("statusId", statusId);
	}

  	public static final List json2Infos(String jsonDate) throws IException{
		ObjectMapper jsonMapper = new ObjectMapper();
		List craInfos = null;
		try{
			craInfos = (List)jsonMapper.readValue(jsonDate, TypeFactory.collectionType(ArrayList.class, CraLoanContentInfo.class)); 
			return craInfos;
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("Gen_001");
		}
	}	
	
}
