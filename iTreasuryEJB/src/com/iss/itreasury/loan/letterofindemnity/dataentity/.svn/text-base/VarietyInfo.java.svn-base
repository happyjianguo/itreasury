package com.iss.itreasury.loan.letterofindemnity.dataentity;

import javax.servlet.ServletRequest;

import com.iss.itreasury.loan.base.LoanBaseDataEntity;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
import com.iss.itreasury.util.ITreasuryException;
/**
 * 
 * @author fxzhang
 *
 */
public class VarietyInfo extends  LoanBaseDataEntity {
	private String sCode;       //保函种类编号
	private String sName;		//保函种类名称
	private long nPredefined;	//是否预定义 :0-预定义1-自定义
	private String sRemark;		//备注
	/**
	 * 继承父类的convertRequestToDataEntity方法，用以从Request中取出和VarietyInfo绑定的参数，
	 * 并封装在dataentity中
	 */
	public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
		//super.convertRequestToDataEntity(request);
		String strTmp = "";
		strTmp = (String) request.getAttribute("sCode");
		if( strTmp != null && strTmp.length() > 0 )
		{
			sCode = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("sName");
		if( strTmp != null && strTmp.length() > 0 )
		{
			sName = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("sRemark");
		if( strTmp != null && strTmp.length() > 0 )
		{
			sRemark = strTmp.trim();
		}	
		this.nullToString();
	}

	//将dataentity中为空的属性值转换为空字符串
	public void nullToString()
	{
		if(sCode == null)
		{
			sCode = "";
		}
		if(sName == null)
		{
			sName = "";
		}
		if(sRemark == null)
		{
			sRemark = "";
		}
	}
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setId(long id) {
		// TODO Auto-generated method stub
		
	}

	

	public long getNPredefined() {
		return nPredefined;
	}

	public void setNPredefined(long predefined) {
		nPredefined = predefined;
	}

	public String getSCode() {
		return sCode;
	}

	public void setSCode(String code) {
		sCode = code;
	}

	public String getSName() {
		return sName;
	}

	public void setSName(String name) {
		sName = name;
	}

	public String getSRemark() {
		return sRemark;
	}

	public void setSRemark(String remark) {
		sRemark = remark;
	}
	
}
