/*
 * Created on 2003-9-26
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transcompatibility.dataentity;
import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.util.DataFormat;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import javax.servlet.ServletRequest;
/**
 *
 * <p>Title: SpecialOperationInfo Class </p>
 * <p>Description: 兼容业务业明细务实体类，对应db数据库中Sett_ TransCompatibilityDetail表
 * </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: isoftstone</p>
 * @author gqzhang
 * @version 1.0
 */
public class TransCompatibilityDetailInfo extends SettlementBaseDataEntity
{
	private long Id = -1; //Number	ID	
	private long CompatibilityID = -1; //	Number	记录ID	外键：关联Sett_ TransCompatibility
	private long SerialNo = -1; //	Number	序列号	
	private long ClientID = -1; //	Number	客户号	
	private long AccountID = -1; //	Number	账户号	
	private long ContractID = -1; //	Number	合同号	
	private long DueBillID = -1; //	Number	借据号	
	private String DepositForm = ""; //	Code	存单号	
	private long BillID = -1; //	Number	票据号ID	
	private long GLID = -1; //	Number	总账ID	
	private long BankID = -1; //	Number	开户行ID	
	private long SingleTypeID = -1; //	Number	单边账类型ID	取值于常量定义
	private long CashFlowID = -1; //	Number	现金流向ID	
	private long TransDirectionID = -1; //	Number	交易方向	
	private double Amount = 0.0; //	Money	金额	
	private String Voucher = ""; //	Code	凭证号	
	private String Password = ""; //	Code	密码	
	private String BankAccount = ""; //	Abstract	银行账号	
	private String BankClient = ""; //	Abstract	银行客户	
	private String BankCheckNo = ""; //	Abstract	银行支票	
	private String DeclarationNo = ""; //	Abstract	银行保单号	
	private String RemitProvince = ""; //	Abstract	省	
	private String RemitCity = ""; //	Abstract	市	
	private String RemitBank = ""; //	Abstract	银行
	private long SubAccountID = -1;//子账户id	
	private final String[] dataType = { "double", "long", "java.lang.String", "java.sql.Timestamp" }; //支持的数据类型
	/**
	 * Method convertRequestToDataEntity.
	 *  将  request中的数组转换为明细的组合
	 * @param request
	 * @param lDetailNum
	 * @throws SettlementException
	 */
	public void convertRequestToDataEntity(ServletRequest request, int lDetailNum) throws SettlementException
	{
		String strTemp = null;
		BeanInfo beanInfo = null;
		try
		{
			beanInfo = Introspector.getBeanInfo(this.getClass());
		}
		catch (IntrospectionException e)
		{
			throw new SettlementException("Java Bean.内省异常发生", e);
		}
		PropertyDescriptor[] p = beanInfo.getPropertyDescriptors();
		for (int n = 0; n < p.length; n++)
		{
			if (p[n].getName().compareToIgnoreCase("class") == 0)
				continue;
			System.out.println("====p[n].getName():" + p[n].getName());
			strTemp = (String) request.getAttribute(p[n].getName() + lDetailNum);
			System.out.println("====strTemp:" + strTemp);
			if (strTemp != null && strTemp.trim().length() > 0)
			{
				System.out.println("====进入if====");
				Object[] oParam = new Object[] {
				};
				Method m = p[n].getWriteMethod();
				if (m != null)
				{
					if (m.getParameterTypes()[0].getName().equals(dataType[0]))
					{ //parameter type is double
						if (strTemp.trim().equals(""))
						{
							strTemp = "0.0";
						}
						System.out.println("====浮点型:" + strTemp);
						oParam = new Double[] { new Double(DataFormat.parseNumber(strTemp.trim()))};
					}
					else
						if (m.getParameterTypes()[0].getName().equals(dataType[1]))
						{ //parameter type is long
							/**
							 * 针对ID做的特殊处理,如果对象参数的长度为0,说明更改了,赋予参数初始值
							 */
							System.out.println("====进入长整型====");
							if (strTemp.trim().equals(""))
							{
								strTemp = "-1";
							}
							System.out.println("====数值型:" + strTemp);
							oParam = new Long[] { new Long(strTemp.trim())};
						}
						else
							if (m.getParameterTypes()[0].getName().equals(dataType[2]))
							{ //parameter type is String
								oParam = new String[] { strTemp.trim()};
								System.out.println("====字符串:" + strTemp);
							}
							else
								if (m.getParameterTypes()[0].getName().equals(dataType[3]))
								{ //parameter type is Timestamp
									oParam = new Timestamp[] { DataFormat.getDateTime(strTemp.trim())};
									System.out.println("====日期型:" + strTemp);
								}
					try
					{
						System.out.println("====开始调用===");
						m.invoke(this, oParam); //set parameters to dataentity
						System.out.println("====调用结束===");
					}
					catch (IllegalArgumentException e)
					{
						// TODO Auto-generated catch block
						throw new SettlementException("从request中获得dataentity错误", e);
					}
					catch (IllegalAccessException e)
					{
						// TODO Auto-generated catch block
						throw new SettlementException("从request中获得dataentity错误", e);
					}
					catch (InvocationTargetException e)
					{
						throw new SettlementException("从request中获得dataentity错误", e);
					}
				}
			}
		}
		return;
	}
	/**
	 * Method convertDataEntityToRequest.
	 * 将明细的组合转换为request
	 * @param request
	 * @param lDetailNum
	 * @throws SettlementException
	 */
	public void convertDataEntityToRequest(ServletRequest request, int lDetailNum) throws SettlementException
	{
		BeanInfo info = null;
		try
		{
			info = Introspector.getBeanInfo(this.getClass());
		}
		catch (IntrospectionException e)
		{
			throw new SettlementException("Java Bean.内省异常发生", e);
		}
		//Log.print("----------从dataentity转化到request----------");
		PropertyDescriptor[] p = info.getPropertyDescriptors();
		for (int n = 0; n < p.length; n++)
		{
			if (p[n].getName().compareToIgnoreCase("class") == 0)
				continue;
			try
			{
				if (p[n].getReadMethod() != null)
				{
					//Log.print("key:" + p[n].getName() + "// value:" + p[n].getReadMethod().invoke(this,null));
					String strValue = (p[n].getReadMethod().invoke(this, new Object[]{}) == null) ? "" : String.valueOf(p[n].getReadMethod().invoke(this, new Object[]{}));
					String strReturnType = p[n].getReadMethod().getReturnType().getName();
					if (strReturnType.equals(dataType[0]) && Double.parseDouble(strValue) == 0.0)
					{ //parameter type is double
						strValue = null;
					}
					else
						if (p[n].getReadMethod().getReturnType().getName().equals(dataType[1]) && Long.parseLong(strValue) == -1)
						{ //parameter type is long
							strValue = null;
						}
						else
							if (p[n].getReadMethod().getReturnType().getName().equals(dataType[2]) && strValue.equals(""))
							{ //parameter type is String
								strValue = null;
							}
					if (strValue != null)
						request.setAttribute(p[n].getName() + lDetailNum, strValue); //如果返回值不是null则置入request
				}
			}
			catch (IllegalAccessException e)
			{
				// TODO Auto-generated catch block
				throw new SettlementException("把dataentity置入request中出现错误", e);
			}
			catch (InvocationTargetException e)
			{
				throw new SettlementException("把dataentity置入request中出现错误", e);
			}
		}
	}
	public static void main(java.lang.String[] args) throws Exception
	{
		//在此处插入用来启动应用程序的代码。
		try
		{
			TransCompatibilityDetailInfo detailInfo = new TransCompatibilityDetailInfo();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Returns the accountID.
	 * @return long
	 */
	public long getAccountID()
	{
		return AccountID;
	}
	/**
	 * Returns the amount.
	 * @return double
	 */
	public double getAmount()
	{
		return Amount;
	}
	/**
	 * Returns the bankAccount.
	 * @return String
	 */
	public String getBankAccount()
	{
		return BankAccount;
	}
	/**
	 * Returns the bankCheckNo.
	 * @return String
	 */
	public String getBankCheckNo()
	{
		return BankCheckNo;
	}
	/**
	 * Returns the bankClient.
	 * @return String
	 */
	public String getBankClient()
	{
		return BankClient;
	}
	/**
	 * Returns the bankID.
	 * @return long
	 */
	public long getBankID()
	{
		return BankID;
	}
	/**
	 * Returns the billID.
	 * @return long
	 */
	public long getBillID()
	{
		return BillID;
	}
	/**
	 * Returns the cashFlowID.
	 * @return long
	 */
	public long getCashFlowID()
	{
		return CashFlowID;
	}
	/**
	 * Returns the clientID.
	 * @return long
	 */
	public long getClientID()
	{
		return ClientID;
	}
	/**
	 * Returns the compatibilityID.
	 * @return long
	 */
	public long getCompatibilityID()
	{
		return CompatibilityID;
	}
	/**
	 * Returns the contractID.
	 * @return long
	 */
	public long getContractID()
	{
		return ContractID;
	}
	/**
	 * Returns the declarationNo.
	 * @return String
	 */
	public String getDeclarationNo()
	{
		return DeclarationNo;
	}
	/**
	 * Returns the depositForm.
	 * @return String
	 */
	public String getDepositForm()
	{
		return DepositForm;
	}
	/**
	 * Returns the dueBillID.
	 * @return long
	 */
	public long getDueBillID()
	{
		return DueBillID;
	}
	/**
	 * Returns the gLID.
	 * @return long
	 */
	public long getGLID()
	{
		return GLID;
	}
	/**
	 * Returns the iD.
	 * @return long
	 */
	public long getId()
	{
		return Id;
	}
	/**
	 * Returns the password.
	 * @return String
	 */
	public String getPassword()
	{
		return Password;
	}
	/**
	 * Returns the remitBank.
	 * @return String
	 */
	public String getRemitBank()
	{
		return RemitBank;
	}
	/**
	 * Returns the remitCity.
	 * @return String
	 */
	public String getRemitCity()
	{
		return RemitCity;
	}
	/**
	 * Returns the remitProvince.
	 * @return String
	 */
	public String getRemitProvince()
	{
		return RemitProvince;
	}
	/**
	 * Returns the serialNo.
	 * @return long
	 */
	public long getSerialNo()
	{
		return SerialNo;
	}
	/**
	 * Returns the singleTypeID.
	 * @return long
	 */
	public long getSingleTypeID()
	{
		return SingleTypeID;
	}
	/**
	 * Returns the transDirectionID.
	 * @return long
	 */
	public long getTransDirectionID()
	{
		return TransDirectionID;
	}
	/**
	 * Returns the voucher.
	 * @return String
	 */
	public String getVoucher()
	{
		return Voucher;
	}
	/**
	 * Sets the accountID.
	 * @param accountID The accountID to set
	 */
	public void setAccountID(long accountID)
	{
		AccountID = accountID;
		putUsedField("AccountID", AccountID);
	}
	/**
	 * Sets the amount.
	 * @param amount The amount to set
	 */
	public void setAmount(double amount)
	{
		Amount = amount;
		putUsedField("Amount", Amount);
	}
	/**
	 * Sets the bankAccount.
	 * @param bankAccount The bankAccount to set
	 */
	public void setBankAccount(String bankAccount)
	{
		BankAccount = bankAccount;
		putUsedField("BankAccount", BankAccount);
	}
	/**
	 * Sets the bankCheckNo.
	 * @param bankCheckNo The bankCheckNo to set
	 */
	public void setBankCheckNo(String bankCheckNo)
	{
		BankCheckNo = bankCheckNo;
		putUsedField("BankCheckNo", BankCheckNo);
	}
	/**
	 * Sets the bankClient.
	 * @param bankClient The bankClient to set
	 */
	public void setBankClient(String bankClient)
	{
		BankClient = bankClient;
		putUsedField("BankClient", BankClient);
	}
	/**
	 * Sets the bankID.
	 * @param bankID The bankID to set
	 */
	public void setBankID(long bankID)
	{
		BankID = bankID;
		putUsedField("BankID", BankID);
	}
	/**
	 * Sets the billID.
	 * @param billID The billID to set
	 */
	public void setBillID(long billID)
	{
		BillID = billID;
		putUsedField("BillID", BillID);
	}
	/**
	 * Sets the cashFlowID.
	 * @param cashFlowID The cashFlowID to set
	 */
	public void setCashFlowID(long cashFlowID)
	{
		CashFlowID = cashFlowID;
		putUsedField("CashFlowID", CashFlowID);
	}
	/**
	 * Sets the clientID.
	 * @param clientID The clientID to set
	 */
	public void setClientID(long clientID)
	{
		ClientID = clientID;
		putUsedField("ClientID", ClientID);
	}
	/**
	 * Sets the compatibilityID.
	 * @param compatibilityID The compatibilityID to set
	 */
	public void setCompatibilityID(long compatibilityID)
	{
		CompatibilityID = compatibilityID;
		putUsedField("CompatibilityID", CompatibilityID);
	}
	/**
	 * Sets the contractID.
	 * @param contractID The contractID to set
	 */
	public void setContractID(long contractID)
	{
		ContractID = contractID;
		putUsedField("ContractID", ContractID);
	}
	/**
	 * Sets the declarationNo.
	 * @param declarationNo The declarationNo to set
	 */
	public void setDeclarationNo(String declarationNo)
	{
		DeclarationNo = declarationNo;
		putUsedField("DeclarationNo", DeclarationNo);
	}
	/**
	 * Sets the depositForm.
	 * @param depositForm The depositForm to set
	 */
	public void setDepositForm(String depositForm)
	{
		DepositForm = depositForm;
		putUsedField("DepositForm", DepositForm);
	}
	/**
	 * Sets the dueBillID.
	 * @param dueBillID The dueBillID to set
	 */
	public void setDueBillID(long dueBillID)
	{
		DueBillID = dueBillID;
		putUsedField("DueBillID", DueBillID);
	}
	/**
	 * Sets the gLID.
	 * @param gLID The gLID to set
	 */
	public void setGLID(long gLID)
	{
		GLID = gLID;
		putUsedField("GLID", GLID);
	}
	/**
	 * Sets the iD.
	 * @param iD The iD to set
	 */
	public void setId(long iD)
	{
		Id = iD;
		putUsedField("Id", Id);
	}
	/**
	 * Sets the password.
	 * @param password The password to set
	 */
	public void setPassword(String password)
	{
		Password = password;
		putUsedField("Password", Password);
	}
	/**
	 * Sets the remitBank.
	 * @param remitBank The remitBank to set
	 */
	public void setRemitBank(String remitBank)
	{
		RemitBank = remitBank;
		putUsedField("RemitBank", RemitBank);
	}
	/**
	 * Sets the remitCity.
	 * @param remitCity The remitCity to set
	 */
	public void setRemitCity(String remitCity)
	{
		RemitCity = remitCity;
		putUsedField("RemitCity", RemitCity);
	}
	/**
	 * Sets the remitProvince.
	 * @param remitProvince The remitProvince to set
	 */
	public void setRemitProvince(String remitProvince)
	{
		RemitProvince = remitProvince;
		putUsedField("RemitProvince", RemitProvince);
	}
	/**
	 * Sets the serialNo.
	 * @param serialNo The serialNo to set
	 */
	public void setSerialNo(long serialNo)
	{
		SerialNo = serialNo;
		putUsedField("SerialNo", SerialNo);
	}
	/**
	 * Sets the singleTypeID.
	 * @param singleTypeID The singleTypeID to set
	 */
	public void setSingleTypeID(long singleTypeID)
	{
		SingleTypeID = singleTypeID;
		putUsedField("SingleTypeID", SingleTypeID);
	}
	/**
	 * Sets the transDirectionID.
	 * @param transDirectionID The transDirectionID to set
	 */
	public void setTransDirectionID(long transDirectionID)
	{
		TransDirectionID = transDirectionID;
		putUsedField("TransDirectionID", TransDirectionID);
	}
	/**
	 * Sets the voucher.
	 * @param voucher The voucher to set
	 */
	public void setVoucher(String voucher)
	{
		Voucher = voucher;
		putUsedField("Voucher", Voucher);
	}
	/**
	 * Returns the subAccountID.
	 * @return long
	 */
	public long getSubAccountID()
	{
		return SubAccountID;
	}

	/**
	 * Sets the subAccountID.
	 * @param subAccountID The subAccountID to set
	 */
	public void setSubAccountID(long subAccountID)
	{
		SubAccountID = subAccountID;
		putUsedField("SubAccountID", subAccountID);
	}

}
