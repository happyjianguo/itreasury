/*
 * Created on 2004-7-5
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.system.setting.dataentity ;
import java.io.Serializable;
import java.sql.Timestamp;
import com.iss.itreasury.util.ITreasuryBaseDataEntity;
/**
 * @author yiwang
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class DepartmentInfo extends ITreasuryBaseDataEntity implements Serializable
{
	private long		Id						= -1 ;
	private long		officeID				= -1 ;
	private long		currencyID				= -1 ;
	private String		departmentCode			= "" ;
	private String		departmentName			= "" ;
	private String		departmentshortName		= "" ;
	private long		superiorDepartmentID	= -1 ;
	private Timestamp	foundDate				= null ;
	private long		managerUserID			= -1 ;
	private String		responsibility			= "" ;
	private String		address					= "" ;
	private String		telephoneNumber			= "" ;
	private long		contactUserID			= -1 ;
	private long		statusID				= -1 ;
	private long		inputUserID				= -1 ;
	private Timestamp	inputDate				= null ;
	private long		updateUserID			= -1 ;
	private Timestamp	updateDate				= null ;
	private long		departmentFlag			= -1 ;
	private long		isCompanyLevel			= 0 ;
	/**
	 * @return Returns the isCompanyLevel.
	 */
	public long getIsCompanyLevel ( )
	{
		return isCompanyLevel ;
	}
	/**
	 * @param isCompanyLevel
	 *            The isCompanyLevel to set.
	 */
	public void setIsCompanyLevel ( long isCompanyLevel )
	{
		this.isCompanyLevel = isCompanyLevel ;
		putUsedField ( "isCompanyLevel" , isCompanyLevel ) ;
	}
	/**
	 * @return Returns the address.
	 */
	public String getAddress ( )
	{
		return address ;
	}
	/**
	 * @param address
	 *            The address to set.
	 */
	public void setAddress ( String address )
	{
		this.address = address ;
		putUsedField ( "address" , address ) ;
	}
	/**
	 * @return Returns the contactUserID.
	 */
	public long getContactUserID ( )
	{
		return contactUserID ;
	}
	/**
	 * @param contactUserID
	 *            The contactUserID to set.
	 */
	public void setContactUserID ( long contactUserID )
	{
		this.contactUserID = contactUserID ;
		putUsedField ( "contactUserID" , contactUserID ) ;
	}
	/**
	 * @return Returns the departmentCode.
	 */
	public String getDepartmentCode ( )
	{
		return departmentCode ;
	}
	/**
	 * @param departmentCode
	 *            The departmentCode to set.
	 */
	public void setDepartmentCode ( String departmentCode )
	{
		this.departmentCode = departmentCode ;
		putUsedField ( "departmentCode" , departmentCode ) ;
	}
	/**
	 * @return Returns the departmentName.
	 */
	public String getDepartmentName ( )
	{
		return departmentName ;
	}
	/**
	 * @param departmentName
	 *            The departmentName to set.
	 */
	public void setDepartmentName ( String departmentName )
	{
		this.departmentName = departmentName ;
		putUsedField ( "departmentName" , departmentName ) ;
	}
	/**
	 * @return Returns the departmentshortName.
	 */
	public String getDepartmentshortName ( )
	{
		return departmentshortName ;
	}
	/**
	 * @param departmentshortName
	 *            The departmentshortName to set.
	 */
	public void setDepartmentshortName ( String departmentshortName )
	{
		this.departmentshortName = departmentshortName ;
		putUsedField ( "departmentshortName" , departmentshortName ) ;
	}
	/**
	 * @return Returns the foundDate.
	 */
	public Timestamp getFoundDate ( )
	{
		return foundDate ;
	}
	/**
	 * @param timestamp
	 *            The foundDate to set.
	 */
	public void setFoundDate ( Timestamp foundDate )
	{
		this.foundDate = foundDate ;
		putUsedField ( "foundDate" , foundDate ) ;
	}
	/**
	 * @return Returns the inputDate.
	 */
	public Timestamp getInputDate ( )
	{
		return inputDate ;
	}
	/**
	 * @param inputDate
	 *            The inputDate to set.
	 */
	public void setInputDate ( Timestamp inputDate )
	{
		this.inputDate = inputDate ;
		putUsedField ( "inputDate" , inputDate ) ;
	}
	/**
	 * @return Returns the inputUserID.
	 */
	public long getInputUserID ( )
	{
		return inputUserID ;
	}
	/**
	 * @param inputUserID
	 *            The inputUserID to set.
	 */
	public void setInputUserID ( long inputUserID )
	{
		this.inputUserID = inputUserID ;
		putUsedField ( "inputUserID" , inputUserID ) ;
	}
	/**
	 * @return Returns the managerUserID.
	 */
	public long getManagerUserID ( )
	{
		return managerUserID ;
	}
	/**
	 * @param managerUserID
	 *            The managerUserID to set.
	 */
	public void setManagerUserID ( long managerUserID )
	{
		this.managerUserID = managerUserID ;
		putUsedField ( "managerUserID" , managerUserID ) ;
	}
	/**
	 * @return Returns the responsibility.
	 */
	public String getResponsibility ( )
	{
		return responsibility ;
	}
	/**
	 * @param responsibility
	 *            The responsibility to set.
	 */
	public void setResponsibility ( String responsibility )
	{
		this.responsibility = responsibility ;
		putUsedField ( "responsibility" , responsibility ) ;
	}
	/**
	 * @return Returns the statusID.
	 */
	public long getStatusID ( )
	{
		return statusID ;
	}
	/**
	 * @param statusID
	 *            The statusID to set.
	 */
	public void setStatusID ( long statusID )
	{
		this.statusID = statusID ;
		putUsedField ( "statusID" , statusID ) ;
	}
	/**
	 * @return Returns the superiorDepartmentID.
	 */
	public long getSuperiorDepartmentID ( )
	{
		return superiorDepartmentID ;
	}
	/**
	 * @param superiorDepartmentID
	 *            The superiorDepartmentID to set.
	 */
	public void setSuperiorDepartmentID ( long superiorDepartmentID )
	{
		this.superiorDepartmentID = superiorDepartmentID ;
		putUsedField ( "superiorDepartmentID" , superiorDepartmentID ) ;
	}
	/**
	 * @return Returns the telephoneNumber.
	 */
	public String getTelephoneNumber ( )
	{
		return telephoneNumber ;
	}
	/**
	 * @param telephoneNumber
	 *            The telephoneNumber to set.
	 */
	public void setTelephoneNumber ( String telephoneNumber )
	{
		this.telephoneNumber = telephoneNumber ;
		putUsedField ( "telephoneNumber" , telephoneNumber ) ;
	}
	/**
	 * @return Returns the updateDate.
	 */
	public Timestamp getUpdateDate ( )
	{
		return updateDate ;
	}
	/**
	 * @param updateDate
	 *            The updateDate to set.
	 */
	public void setUpdateDate ( Timestamp updateDate )
	{
		this.updateDate = updateDate ;
		putUsedField ( "updateDate" , updateDate ) ;
	}
	/**
	 * @return Returns the updateUserID.
	 */
	public long getUpdateUserID ( )
	{
		return updateUserID ;
	}
	/**
	 * @param updateUserID
	 *            The updateUserID to set.
	 */
	public void setUpdateUserID ( long updateUserID )
	{
		this.updateUserID = updateUserID ;
		putUsedField ( "updateUserID" , updateUserID ) ;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#getId()
	 */
	public long getId ( )
	{
		// TODO Auto-generated method stub
		return Id ;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iss.itreasury.util.ITreasuryBaseDataEntity#setId(long)
	 */
	public void setId ( long id )
	{
		// TODO Auto-generated method stub
		this.Id = id ;
		putUsedField ( "Id" , id ) ;
	}
	/**
	 * @return Returns the officeID.
	 */
	public long getOfficeID ( )
	{
		return officeID ;
	}
	/**
	 * @param officeID
	 *            The officeID to set.
	 */
	public void setOfficeID ( long officeID )
	{
		this.officeID = officeID ;
		putUsedField ( "officeID" , officeID ) ;
	}
	/**
	 * @return Returns the departmentFlag.
	 */
	public long getDepartmentFlag ( )
	{
		return departmentFlag ;
	}
	/**
	 * @param departmentFlag
	 *            The departmentFlag to set.
	 */
	public void setDepartmentFlag ( long departmentFlag )
	{
		this.departmentFlag = departmentFlag ;
		putUsedField ( "departmentFlag" , departmentFlag ) ;
	}
    /**
     * @return Returns the currencyID.
     */
    public long getCurrencyID()
    {
        return currencyID;
    }
    /**
     * @param currencyID The currencyID to set.
     */
    public void setCurrencyID(long currencyID)
    {
        this.currencyID = currencyID;
        putUsedField("currencyID", currencyID);
    }
}