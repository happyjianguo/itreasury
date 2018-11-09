/**
 * AutoFileInfo.java
 * @author  
 * @version
 */

package com.iss.itreasury.dataentity;

import java.sql.*;

public class AutoFileInfo implements java.io.Serializable{
 
    private long m_lID = -1 ;                          //ID
    private String m_strFileType = "" ;                //文件类型
    private String m_strServerFileName = "" ;          //服务器端文件名称
    private String m_strClientFileName = "" ;          //客户端文件名称
    private String m_strServerPath = "" ;              //服务器端路径
    private String m_strClientPath = "" ;              //客户端路径
    private long m_lStatus = -1 ;                      //状态
    private long  m_lInputUserID = -1 ;                //录入人
    private Timestamp m_tsInput = null ;               //录入时间
    private long  m_lModifiedUserID = -1 ;             //更改人
    private Timestamp m_tsModified = null ;            //更改时间
    private String m_strFileContentType = "" ;         //文件ContenType
    private String m_strFileMimeType = "" ;            //文件MimeType
    private boolean m_bFileSucc = false ;              //文件操作是否成功
    private String m_strFileDeniedExt = "" ;           //不允许的文件后缀名，可以写作"exe,jsp"这样的格式，多个后缀用逗号隔开
    private String m_strFileAllowedExt = "" ;          //允许的文件后缀名，格式同上
    private long m_lMaxFileSize = -1 ;                 //允许的文件大小
	private long ParentID = -1;                        //所属对象的ID
	private long ParentIDType = -1;                    //所属对象的类型ID
    private long ModuleID = -1;						   //所属模块ID
    private long TransTypeID = -1;					   //所属交易类型ID
    private long TransSubTypeID = -1;				   //所属交易子类型ID
    private long CurrencyID = -1;				   	   //币种ID
    private long OfficeID = -1;				   		   //办事处ID 
    private String transCode = "";				   		   //交易号
    
    private long ClientID = -1;  //clientcenter 使用
    private long TypeID = -1;    //clientcenter 属于哪种业务类型的文件
    
    
    private long ContractID=-1; //合同ID
    private long PayFormID=-1; //放款单ID
    
    private long ArchivesTypeID=-1;   //档案类型
    
    public long getArchivesTypeID() {
		return ArchivesTypeID;
	}
	public void setArchivesTypeID(long archivesTypeID) {
		ArchivesTypeID = archivesTypeID;
	}
	public long getID() 
    {
        return m_lID ;
    }
    public void setID( long lID ) 
    {
        this.m_lID = lID ;
    }
    
    public String getFileType() 
    {
        return m_strFileType ;
    }
    public void setFileType( String strFileType ) 
    {
        this.m_strFileType = strFileType ;
    }
    
    public String getServerFileName() 
    {
        return m_strServerFileName ;
    }
    public void setServerFileName( String strServerFileName ) 
    {
        this.m_strServerFileName = strServerFileName ;
    }
    
    public String getClientFileName() 
    {
        return m_strClientFileName ;
    }
    public void setClientFileName( String strClientFileName ) 
    {
        this.m_strClientFileName = strClientFileName ;
    }
    
    public String getServerPath() 
    {
        return m_strServerPath ;
    }
    public void setServerPath( String strServerPath ) 
    {
        this.m_strServerPath = strServerPath ;
    }
    
    public String getClientPath() 
    {
        return m_strClientPath ;
    }
    public void setClientPath( String strClientPath ) 
    {
        this.m_strClientPath = strClientPath ;
    }
    
    public long getStatus() 
    {
        return m_lStatus ;
    }
    public void setStatus( long lStatus ) 
    {
        this.m_lStatus = lStatus ;
    }
    
    public long getInputUserID() 
    {
        return m_lInputUserID ;
    }
    public void setInputUserID( long lInputUserID ) 
    {
        this.m_lInputUserID = lInputUserID ;
    }
    
    public Timestamp getInputTime() 
    {
        return m_tsInput ;
    }
    public void setInputTime( Timestamp tsInput ) 
    {
        this.m_tsInput = tsInput ;
    }
    
    public long getModifiedUserID() 
    {
        return m_lModifiedUserID ;
    }
    public void setModifiedUserID( long lModifiedUserID ) 
    {
        this.m_lModifiedUserID = lModifiedUserID ;
    }
    
    public Timestamp getModifiedTime() 
    {
        return m_tsModified ;
    }
    public void setModifiedTime( Timestamp tsModified ) 
    {
        this.m_tsModified = tsModified ;
    }
    
    public String getFileContentType() 
    {
        return m_strFileContentType ;
    }
    public void setFileContentType( String strFileContentType ) 
    {
        this.m_strFileContentType = strFileContentType ;
    }
    
    public String getFileMimeType() 
    {
        return m_strFileMimeType ;
    }
    public void setFileMimeType( String strFileMimeType ) 
    {
        this.m_strFileMimeType = strFileMimeType ;
    }
    
    public boolean getFileSucc() 
    {
        return m_bFileSucc ;
    }
    public void setFileSucc( boolean bFileSucc ) 
    {
        this.m_bFileSucc = bFileSucc ;
    }
    
    public String getFileDeniedExt() 
    {
        return m_strFileDeniedExt ;
    }
    public void setFileDeniedExt( String strFileDeniedExt ) 
    {
        this.m_strFileDeniedExt = strFileDeniedExt ;
    }
    
    public String getFileAllowedExt() 
    {
        return m_strFileAllowedExt ;
    }
    public void setFileAllowedExt( String m_strFileAllowedExt ) 
    {
        this.m_strFileAllowedExt = m_strFileAllowedExt ;
    }
    
    public long getMaxFileSize() 
    {
        return m_lMaxFileSize ;
    }
    public void setMaxFileSize( long lMaxFileSize ) 
    {
        this.m_lMaxFileSize = lMaxFileSize ;
    }

	/**
	 * @return
	 */
	public boolean isM_bFileSucc()
	{
		return m_bFileSucc;
	}

	/**
	 * @return
	 */
	public long getM_lID()
	{
		return m_lID;
	}

	/**
	 * @return
	 */
	public long getM_lInputUserID()
	{
		return m_lInputUserID;
	}

	/**
	 * @return
	 */
	public long getM_lMaxFileSize()
	{
		return m_lMaxFileSize;
	}

	/**
	 * @return
	 */
	public long getM_lModifiedUserID()
	{
		return m_lModifiedUserID;
	}

	/**
	 * @return
	 */
	public long getM_lStatus()
	{
		return m_lStatus;
	}

	/**
	 * @return
	 */
	public String getM_strClientFileName()
	{
		return m_strClientFileName;
	}

	/**
	 * @return
	 */
	public String getM_strClientPath()
	{
		return m_strClientPath;
	}

	/**
	 * @return
	 */
	public String getM_strFileAllowedExt()
	{
		return m_strFileAllowedExt;
	}

	/**
	 * @return
	 */
	public String getM_strFileContentType()
	{
		return m_strFileContentType;
	}

	/**
	 * @return
	 */
	public String getM_strFileDeniedExt()
	{
		return m_strFileDeniedExt;
	}

	/**
	 * @return
	 */
	public String getM_strFileMimeType()
	{
		return m_strFileMimeType;
	}

	/**
	 * @return
	 */
	public String getM_strFileType()
	{
		return m_strFileType;
	}

	/**
	 * @return
	 */
	public String getM_strServerFileName()
	{
		return m_strServerFileName;
	}

	/**
	 * @return
	 */
	public String getM_strServerPath()
	{
		return m_strServerPath;
	}

	/**
	 * @return
	 */
	public Timestamp getM_tsInput()
	{
		return m_tsInput;
	}

	/**
	 * @return
	 */
	public Timestamp getM_tsModified()
	{
		return m_tsModified;
	}

	/**
	 * @return
	 */
	public long getParentID()
	{
		return ParentID;
	}

	/**
	 * @param b
	 */
	public void setM_bFileSucc(boolean b)
	{
		m_bFileSucc = b;
	}

	/**
	 * @param l
	 */
	public void setM_lID(long l)
	{
		m_lID = l;
	}

	/**
	 * @param l
	 */
	public void setM_lInputUserID(long l)
	{
		m_lInputUserID = l;
	}

	/**
	 * @param l
	 */
	public void setM_lMaxFileSize(long l)
	{
		m_lMaxFileSize = l;
	}

	/**
	 * @param l
	 */
	public void setM_lModifiedUserID(long l)
	{
		m_lModifiedUserID = l;
	}

	/**
	 * @param l
	 */
	public void setM_lStatus(long l)
	{
		m_lStatus = l;
	}

	/**
	 * @param string
	 */
	public void setM_strClientFileName(String string)
	{
		m_strClientFileName = string;
	}

	/**
	 * @param string
	 */
	public void setM_strClientPath(String string)
	{
		m_strClientPath = string;
	}

	/**
	 * @param string
	 */
	public void setM_strFileAllowedExt(String string)
	{
		m_strFileAllowedExt = string;
	}

	/**
	 * @param string
	 */
	public void setM_strFileContentType(String string)
	{
		m_strFileContentType = string;
	}

	/**
	 * @param string
	 */
	public void setM_strFileDeniedExt(String string)
	{
		m_strFileDeniedExt = string;
	}

	/**
	 * @param string
	 */
	public void setM_strFileMimeType(String string)
	{
		m_strFileMimeType = string;
	}

	/**
	 * @param string
	 */
	public void setM_strFileType(String string)
	{
		m_strFileType = string;
	}

	/**
	 * @param string
	 */
	public void setM_strServerFileName(String string)
	{
		m_strServerFileName = string;
	}

	/**
	 * @param string
	 */
	public void setM_strServerPath(String string)
	{
		m_strServerPath = string;
	}

	/**
	 * @param timestamp
	 */
	public void setM_tsInput(Timestamp timestamp)
	{
		m_tsInput = timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setM_tsModified(Timestamp timestamp)
	{
		m_tsModified = timestamp;
	}

	/**
	 * @param l
	 */
	public void setParentID(long l)
	{
		ParentID = l;
	}

	/**
	 * @return
	 */
	public long getParentIDType()
	{
		return ParentIDType;
	}

	/**
	 * @param l
	 */
	public void setParentIDType(long l)
	{
		ParentIDType = l;
	}

	/**
	 * @return
	 */
	public long getClientID()
	{
		return ClientID;
	}

	/**
	 * @param l
	 */
	public void setClientID(long l)
	{
		ClientID = l;
	}

	/**
	 * @return
	 */
	public long getTypeID()
	{
		return TypeID;
	}

	/**
	 * @param l
	 */
	public void setTypeID(long l)
	{
		TypeID = l;
	}
	public long getCurrencyID() {
		return CurrencyID;
	}
	public long getModuleID() {
		return ModuleID;
	}
	public long getOfficeID() {
		return OfficeID;
	}
	public long getTransSubTypeID() {
		return TransSubTypeID;
	}
	public long getTransTypeID() {
		return TransTypeID;
	}
	public void setCurrencyID(long currencyID) {
		CurrencyID = currencyID;
	}
	public void setModuleID(long moduleID) {
		ModuleID = moduleID;
	}
	public void setOfficeID(long officeID) {
		OfficeID = officeID;
	}
	public void setTransSubTypeID(long transSubTypeID) {
		TransSubTypeID = transSubTypeID;
	}
	public void setTransTypeID(long transTypeID) {
		TransTypeID = transTypeID;
	}
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public long getContractID() {
		return ContractID;
	}
	public void setContractID(long contractID) {
		ContractID = contractID;
	}
	public long getPayFormID() {
		return PayFormID;
	}
	public void setPayFormID(long payFormID) {
		PayFormID = payFormID;
	}


}
