package com.iss.itreasury.settlement.query.resultinfo;
import java.sql.Timestamp;
/**
 * 此处插入类型说明。
 * 创建日期：(2002-1-15 18:25:33)
 * @author：Administrator
 */
public class SubjectInfo implements java.io.Serializable 
{
		public long   m_lID;
        public String m_strName;
        public long   m_lCreditorTypeID;
        public String m_strCreditorSubjectCode;
        public long   m_lDebtorTypeID;
        public String m_strDebtorSubjectCode;
        public long   m_lPageCount;
        
        //ADD BY XRZHANG; 通过关联得到OracleSubject种的Descript2描述子段，用于打印凭证时科目名称的显示
        //其中一个为借方科目名称，一个为贷方科目名称
        //贷方科目名称
        public String m_strCreditDescrip2 = "";
        //借方科目名称
        public String m_strDebtDescrip2 = "";
        ////add by 
        public Timestamp m_tsDate = null;
        public double m_dBalance = 0.00;
        public String m_strSubject = "";
        public long m_lOfficeID = -1;
        public long m_lCurrencyID = -1;
        public long m_lRecordCount = -1;
        public double m_dSumBalance = 0.00;
}
