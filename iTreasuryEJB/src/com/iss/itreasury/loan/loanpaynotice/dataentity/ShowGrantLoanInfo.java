package com.iss.itreasury.loan.loanpaynotice.dataentity ;

/**
 * <p>Title: iTreasury</p>
 * <p>Description: 财务</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: iss</p>
 * @author 方远明
 * @version 1.0
 */
/**
 * @author ruixie
 * 贷款发放info类
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ShowGrantLoanInfo
{
        public ShowGrantLoanInfo()
        {
                super();
                // TODO Auto-generated constructor stub
        }
        public String Year = ""; //年
        public String Month = ""; //月
        public String Day = ""; //日
        public String Amount = ""; //本金金额
        public String CurrencyName = ""; //币种
        public String TransNo = ""; //交易号
        public String ChineseAmount = ""; //本金金额（大写）
        public String InputUserName = ""; //录入人
        public String CheckUserName = ""; //复核人

        public String ManagerName = ""; //部门经理
        public String ManagerLeaderName = ""; //主管总经理
        public String ManagerDepartLeader ="";//分管副经理

        public String Abstract = ""; //摘要
        public String Note = ""; //备注
        public String ContractRate = ""; //合同利率
        public String ChargeRate = ""; //手续费率
        public String LoanType = ""; //贷款种类
        public String LoanUnit = ""; //借款单位
        public String OpenBankName = ""; //开户行名称
        public String AccountNo = ""; //账号
        public String ContractCode = ""; //合同编号
        public String BillCode = ""; //放款单号
        public String ConsignUnit = ""; //委托单位
        public String LoanInterval = ""; //贷款期限
        public String LoanAccountNo = ""; //贷款账户号
        
        public String OverDueInterestRate="";//逾期利率
        /**
         * @return
         */
        public String getAbstract()
        {
                return Abstract;
        }
        /**
         * @return
         */
        public String getAccountNo()
        {
                return AccountNo;
        }
        /**
         * @return
         */
        public String getAmount()
        {
                return Amount;
        }
        /**
         * @return
         */
        public String getBillCode()
        {
                return BillCode;
        }
        /**
         * @return
         */
        public String getChargeRate()
        {
                return ChargeRate;
        }
        /**
         * @return
         */
        public String getCheckUserName()
        {
                return CheckUserName;
        }

        /**
         * @return
         */
        public String getManagerName()
        {
            return ManagerName ;
        }

        /**
         * @return
         */
        public String getManagerLeaderName()
        {
            return ManagerLeaderName ;
        }

        /**
         * @return
         */
        public String getChineseAmount()
        {
                return ChineseAmount;
        }
        /**
         * @return
         */
        public String getConsignUnit()
        {
                return ConsignUnit;
        }
        /**
         * @return
         */
        public String getContractCode()
        {
                return ContractCode;
        }
        /**
         * @return
         */
        public String getContractRate()
        {
                return ContractRate;
        }
        /**
         * @return
         */
        public String getCurrencyName()
        {
                return CurrencyName;
        }
        /**
         * @return
         */
        public String getDay()
        {
                return Day;
        }
        /**
         * @return
         */
        public String getInputUserName()
        {
                return InputUserName;
        }
        /**
         * @return
         */
        public String getLoanInterval()
        {
                return LoanInterval;
        }
        /**
         * @return
         */
        public String getLoanType()
        {
                return LoanType;
        }
        /**
         * @return
         */
        public String getLoanUnit()
        {
                return LoanUnit;
        }
        /**
         * @return
         */
        public String getMonth()
        {
                return Month;
        }
        /**
         * @return
         */
        public String getNote()
        {
                return Note;
        }
        /**
         * @return
         */
        public String getOpenBankName()
        {
                return OpenBankName;
        }
        /**
         * @return
         */
        public String getTransNo()
        {
                return TransNo;
        }
        /**
         * @return
         */
        public String getYear()
        {
                return Year;
        }
        /**
         * @param string
         */
        public void setAbstract(String string)
        {
                Abstract = string;
        }
        /**
         * @param string
         */
        public void setAccountNo(String string)
        {
                AccountNo = string;
        }
        /**
         * @param string
         */
        public void setAmount(String string)
        {
                Amount = string;
        }
        /**
         * @param string
         */
        public void setBillCode(String string)
        {
                BillCode = string;
        }
        /**
         * @param string
         */
        public void setChargeRate(String string)
        {
                ChargeRate = string;
        }
        /**
         * @param string
         */
        public void setCheckUserName(String string)
        {
                CheckUserName = string;
        }
        /**
         * @param string
         */
        public void setChineseAmount(String string)
        {
                ChineseAmount = string;
        }
        /**
         * @param string
         */
        public void setConsignUnit(String string)
        {
                ConsignUnit = string;
        }
        /**
         * @param string
         */
        public void setContractCode(String string)
        {
                ContractCode = string;
        }
        /**
         * @param string
         */
        public void setContractRate(String string)
        {
                ContractRate = string;
        }
        /**
         * @param string
         */
        public void setCurrencyName(String string)
        {
                CurrencyName = string;
        }
        /**
         * @param string
         */
        public void setDay(String string)
        {
                Day = string;
        }
        /**
         * @param string
         */
        public void setInputUserName(String string)
        {
                InputUserName = string;
        }
        /**
         * @param string
         */
        public void setManagerName(String string)
        {
                ManagerName = string;
        }
        /**
         * @param string
         */
        public void setManagerLeaderName(String string)
        {
                ManagerLeaderName = string;
        }

        /**
         * @param string
         */
        public void setLoanInterval(String string)
        {
                LoanInterval = string;
        }
        /**
         * @param string
         */
        public void setLoanType(String string)
        {
                LoanType = string;
        }
        /**
         * @param string
         */
        public void setLoanUnit(String string)
        {
                LoanUnit = string;
        }
        /**
         * @param string
         */
        public void setMonth(String string)
        {
                Month = string;
        }
        /**
         * @param string
         */
        public void setNote(String string)
        {
                Note = string;
        }
        /**
         * @param string
         */
        public void setOpenBankName(String string)
        {
                OpenBankName = string;
        }
        /**
         * @param string
         */
        public void setTransNo(String string)
        {
                TransNo = string;
        }
        /**
         * @param string
         */
        public void setYear(String string)
        {
                Year = string;
        }
        /**
         * @return
         */
        public String getLoanAccountNo()
        {
                return LoanAccountNo;
        }
        /**
         * @param string
         */
        public void setLoanAccountNo(String string)
        {
                LoanAccountNo = string;
        }
		public String getManagerDepartLeader() {
			return ManagerDepartLeader;
		}
		public void setManagerDepartLeader(String managerDepartLeader) {
			ManagerDepartLeader = managerDepartLeader;
		}
		
		public String getOverDueInterestRate() {
			return OverDueInterestRate;
		}
		public void setOverDueInterestRate(String overDueInterestRate) {
			OverDueInterestRate = overDueInterestRate;
		}
		
		
}
