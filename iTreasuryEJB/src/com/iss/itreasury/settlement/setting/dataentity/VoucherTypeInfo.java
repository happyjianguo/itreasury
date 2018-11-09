package com.iss.itreasury.settlement.setting.dataentity;

import java.sql.Timestamp;

import com.iss.itreasury.settlement.base.SettlementBaseDataEntity;

public class VoucherTypeInfo extends SettlementBaseDataEntity
{
	 
	private String ID = "" ;
	private String Voucher_Type_Code = "" ;          // 凭证类型编号
	private String Voucher_Type_Name = "" ;          // 凭证类型名称
	private String Pdf_Participant_BankNoId = "" ;   // 凭证发行银行
	private double Procedure_fee  = 0.0 ;            // 手续费
	private double Expenses_fees  = 0.0 ;            // 工本费 
	private double Buy_Unit_Price = 0.0 ;            // 买入单价
	private double Sell_Unit_Price= 0.0 ;            // 卖出单价
	private String Is_Support_Renew = "" ;           // 是否支持换发
	private String Vouching_Status  = "" ;           // 审批状态
	private String ModifierId = "" ;                 // 操作人ID
	private Timestamp ModTime  = null ;              // 操作时间
	private String Status = "" ;                     // 逻辑删除标志
	private String Extension_Field1 = "" ;           // 备用字段1
	private String Extension_Field2 = "" ;           // 备用字段2
	private String Extension_Field3 = "" ;           // 备用字段3
	private String Extension_Field4 = "" ;           // 备用字段4
	private String Extension_Field5 = "" ;           // 备用字段5
	
	
	
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getVoucher_Type_Name() {
		return Voucher_Type_Name;
	}
	public void setVoucher_Type_Name(String voucher_Type_Name) {
		Voucher_Type_Name = voucher_Type_Name;
	}
	public String getPdf_Participant_BankNoId() {
		return Pdf_Participant_BankNoId;
	}
	public void setPdf_Participant_BankNoId(String pdf_Participant_BankNoId) {
		Pdf_Participant_BankNoId = pdf_Participant_BankNoId;
	}
	public double getProcedure_fee() {
		return Procedure_fee;
	}
	public void setProcedure_fee(double procedure_fee) {
		Procedure_fee = procedure_fee;
	}
	public double getExpenses_fees() {
		return Expenses_fees;
	}
	public void setExpenses_fees(double expenses_fees) {
		Expenses_fees = expenses_fees;
	}
	public double getBuy_Unit_Price() {
		return Buy_Unit_Price;
	}
	public void setBuy_Unit_Price(double buy_Unit_Price) {
		Buy_Unit_Price = buy_Unit_Price;
	}
	public double getSell_Unit_Price() {
		return Sell_Unit_Price;
	}
	public void setSell_Unit_Price(double sell_Unit_Price) {
		Sell_Unit_Price = sell_Unit_Price;
	}
	public String getIs_Support_Renew() {
		return Is_Support_Renew;
	}
	public void setIs_Support_Renew(String is_Support_Renew) {
		Is_Support_Renew = is_Support_Renew;
	}
	public String getVouching_Status() {
		return Vouching_Status;
	}
	public void setVouching_Status(String vouching_Status) {
		Vouching_Status = vouching_Status;
	}
	public String getModifierId() {
		return ModifierId;
	}
	public void setModifierId(String modifierId) {
		ModifierId = modifierId;
	}
	public Timestamp getModTime() {
		return ModTime;
	}
	public void setModTime(Timestamp modTime) {
		ModTime = modTime;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getExtension_Field1() {
		return Extension_Field1;
	}
	public void setExtension_Field1(String extension_Field1) {
		Extension_Field1 = extension_Field1;
	}
	public String getExtension_Field2() {
		return Extension_Field2;
	}
	public void setExtension_Field2(String extension_Field2) {
		Extension_Field2 = extension_Field2;
	}
	public String getExtension_Field3() {
		return Extension_Field3;
	}
	public void setExtension_Field3(String extension_Field3) {
		Extension_Field3 = extension_Field3;
	}
	public String getExtension_Field4() {
		return Extension_Field4;
	}
	public void setExtension_Field4(String extension_Field4) {
		Extension_Field4 = extension_Field4;
	}
	public String getExtension_Field5() {
		return Extension_Field5;
	}
	public void setExtension_Field5(String extension_Field5) {
		Extension_Field5 = extension_Field5;
	}
	public String getVoucher_Type_Code() {
		return Voucher_Type_Code;
	}
	public void setVoucher_Type_Code(String voucher_Type_Code) {
		Voucher_Type_Code = voucher_Type_Code;
	}
	
	
	
	
}
