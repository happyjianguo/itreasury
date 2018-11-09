package com.iss.itreasury.loan.creditext.dataentity;



import java.util.Date;

import javax.servlet.ServletRequest;

import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.ITreasuryException;
//银行授信
public class BankCreditExtInfo {
	private long 	id = -1;					//ID
	private String	contractNo = "";			//授信合同号	
	private String	year = "";					//授信年度	
	private String 	bankName = "";				//授信银行	
	private String 	company = "";				//授信主体	
	private long		reuse = -1;					//是否可循环	    0-可循环1-不可循环
	private long		term = -1;					//授信期限	    0-有期限1-无期限
	private String	startDate = "";				//起始日期	
	private String	endDate = "";				//结束日期	
	private String	operationDate = "";			//办理日期	
	private long		status	= -1;				//授信状态		1-执行中2-已结束3-已取消
	private long     isAvlid = -1;				//是否有效		0表示无效；1表示有效
	private String	useLimit = "";				//使用范围	
	private String	remark = "";				//备注
	private String	guarangy = "";				//担保信息
	private String   accessory = "";				//显示文件名称，超级链接上传到服务器的地址
	private long     lastModifier = -1;			//最后修改人
	private String 	lastModifyDate = "";        //最后修改时间             /???????????
	private String  fileURL = "";
	private String  filename = ""; 
	
	private long nOfficeId=-1;//added by mzh_fu 2007/03/26 区分办事处

	public void  formatString()
	{
		startDate = DataFormat.formatString(startDate);
		endDate = DataFormat.formatString(endDate);
		useLimit = DataFormat.formatString(useLimit);
		remark = DataFormat.formatString(remark);
		guarangy = DataFormat.formatString(guarangy);
		accessory = DataFormat.formatString(accessory);
		
		if(!startDate.equals(""))
		{
			startDate = startDate.substring(0,10);		
		}
		if(!endDate.equals(""))
		{			
			endDate = endDate.substring(0,10);			
		}			
	}
	
	//取得附件的文件名
	public String getAccessoryName()
	{
		
		String fileName = "";
		if((accessory != null) && (!accessory.equals("")))
		{
			
			fileName = accessory.replaceAll(Env.UPLOAD_PATH + "loan/upload/","");
		}
		return fileName;
	}
	
    /**
	 * 继承父类的convertRequestToDataEntity方法，用以从Request中取出和AssureInfo绑定的参数，
	 * 并封装在dataentity中
	 */
    public void convertRequestToDataEntity(ServletRequest request)throws ITreasuryException{
    	
		String strTmp = "";
		strTmp = (String) request.getAttribute("id");
		if( strTmp != null && strTmp.length() > 0 )
		{
			id = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("contractNo");
		if( strTmp != null && strTmp.length() > 0 )
		{
			contractNo = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("year");
		if( strTmp != null && strTmp.length() > 0 )
		{
			year = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("bankName");
		if( strTmp != null && strTmp.length() > 0 )
		{
			bankName = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("company");
		if( strTmp != null && strTmp.length() > 0 )
		{
			company = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("reuse");
		if( strTmp != null && strTmp.length() > 0 )
		{
			reuse = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("term");
		if( strTmp != null && strTmp.length() > 0 )
		{
			term = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("startDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			startDate = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("endDate");
		if( strTmp != null && strTmp.length() > 0 )
		{
			endDate = strTmp.trim();
		}			
		strTmp = (String) request.getAttribute("status");
		if( strTmp != null && strTmp.length() > 0 )
		{
			
			status = (Long.valueOf(strTmp.trim())).longValue();
		}	
		strTmp = (String) request.getAttribute("useLimit");
		if( strTmp != null && strTmp.length() > 0 )
		{
			
			useLimit = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("remark");
		if( strTmp != null && strTmp.length() > 0 )
		{
			remark = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("guarangy");
		if( strTmp != null && strTmp.length() > 0 )
		{
			guarangy = strTmp.trim();
		}		
		strTmp = (String) request.getAttribute("accessory");
		if( strTmp != null && strTmp.length() > 0 )
		{
			accessory = strTmp.trim();
		}	
		strTmp = (String) request.getAttribute("lastModifier");
		if( strTmp != null && strTmp.length() > 0 )
		{
			
			lastModifier = (Long.valueOf(strTmp.trim())).longValue();
		}	

	}
    

	public String getFilename() {
		return filename;
	}

	public String getFileURL() {
		return fileURL;
	}

	public String getAccessory() {
		return accessory;
	}
	public void setAccessory(String accessory) {
		this.accessory = accessory;
		if( (accessory  != null) && (!accessory.equals("") ))
		{		
			this.fileURL = (accessory.split(","))[0];
			this.filename = (accessory.split(","))[1];
		}
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getGuarangy() {
		return guarangy;
	}
	public void setGuarangy(String guarangy) {
		this.guarangy = guarangy;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getReuse() {
		return reuse;
	}
	public void setReuse(long reuse) {
		this.reuse = reuse;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public long getTerm() {
		return term;
	}
	public void setTerm(long term) {
		this.term = term;
	}
	public String getUseLimit() {
		return useLimit;
	}
	public void setUseLimit(String useLimit) {
		this.useLimit = useLimit;
	}
	public String getYear() {
		return year;
	}
	public void setStartDate(Date year) {
		this.year = year.toString();
	}
    
	public void setEndDate(Date year) {
		this.year = year.toString();
	}
	public void setOperationDate(Date year) {
		this.year = year.toString();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public long getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(long lastModifier) {
		this.lastModifier = lastModifier;
	}
	
	public long getIsAvlid() {
		return isAvlid;
	}
	public void setIsAvlid(long isAvlid) {
		this.isAvlid = isAvlid;
	}
	public String getLastModifyDate() {
		return lastModifyDate;
	}
	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public long getNOfficeId() {
		return nOfficeId;
	}

	public void setNOfficeId(long officeId) {
		nOfficeId = officeId;
	}

    
	
	
	
}
