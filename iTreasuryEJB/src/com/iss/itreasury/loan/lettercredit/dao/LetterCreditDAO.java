package com.iss.itreasury.loan.lettercredit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.lettercredit.dataentity.LetterCreditInfo;
import com.iss.itreasury.loan.lettercredit.dataentity.LetterCreditPaymentInfo;
import com.iss.itreasury.loan.lettercredit.dataentity.LetterCreditQueryInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;

public class LetterCreditDAO extends LoanDAO{
	
	public LetterCreditDAO(){
		super("loan_letter_credit");
	}
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	// 查询信用证使用情况
	public PageLoader getLetterCreditInfo(LetterCreditQueryInfo info) throws Exception {
		
		String[] sql = getSQL(info);
		
		PageLoader pageLoader = null;
		try{
		pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.lettercredit.dataentity.LetterCreditResultInfo", null);
		
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return pageLoader;
	}
	
	public PageLoader getAllLetterCredit(long officeId) throws Exception{
		String[] sql = new String[4];
		sql[0] =" a.*,b.scontractno contractno,b.sbankname bankname ";
		sql[1] =" (select m.*,n.paymentamount from (select t.sinnercode innercode,t.nofficeid officeid," +
				"t.slettercreditno lettercreditno,t.dstartdate startdate,t.ncurrencytype currencytype," +
				"t.mamount applyamount,t.nbankcreditextid bankcreditextid,p.name applycompanyname," +
				"t.npaymentmode paymentmode,t.ntype type,t.nstatus status,t.sapplycompanycode applycompanycode " +
				"from loan_letter_credit t left join client_clientinfo p on t.sapplycompanycode=p.code) m " +
				"left join (select u.sinnercode,sum(v.mpaymentamount) paymentamount from loan_letter_credit u " +
				"left join loan_letter_credit_payment v on u.sinnercode=v.sinnercode group by u.sinnercode) n " +
				"on m.innercode=n.sinnercode) a left join loan_bank_creditext b on a.bankcreditextid= b.id  ";
		sql[2] = " status > 0 and officeId=" + officeId;
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" order by innercode" + strDesc);
		sql[3] = oBuf.toString();
		
		PageLoader pageLoader = null;
		try{
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
			pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.lettercredit.dataentity.LetterCreditResultInfo", null);
		
			pageLoader.setOrderBy(" " + sql[3] + " ");	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return pageLoader;
		
	}
	
	public String[] getSQL(LetterCreditQueryInfo info) throws Exception{
		
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		String strTemp = null;
		long lTemp = -1;
		
//		select
		sql[0] =" a.*,b.scontractno contractno,b.sbankname bankname ";
		//from
		sql[1] =" (select m.*,n.paymentamount from (select t.sinnercode innercode,t.nofficeid officeid," +
				"t.slettercreditno lettercreditno,t.dstartdate startdate,t.ncurrencytype currencytype," +
				"t.mamount applyamount,t.nbankcreditextid bankcreditextid,p.name applycompanyname," +
				"t.npaymentmode paymentmode,t.ntype type,t.nstatus status,t.sapplycompanycode applycompanycode " +
				"from loan_letter_credit t left join client_clientinfo p on t.sapplycompanycode=p.code) m " +
				"left join (select u.sinnercode,sum(v.mpaymentamount) paymentamount from loan_letter_credit u " +
				"left join loan_letter_credit_payment v on u.sinnercode=v.sinnercode group by u.sinnercode) n " +
				"on m.innercode=n.sinnercode) a left join loan_bank_creditext b on a.bankcreditextid= b.id  ";
		//where
		sql[2] =" status > 0 and officeId=" + info.getOfficeId();
		
		strTemp = info.getApplyCompanyCode();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and applyCompanyCode = '" + strTemp + "' " );
		}
		
		lTemp = info.getBankCreditextID();
		if(lTemp > 0){
			strSQL.append(" and bankCreditextID = " + lTemp);
		}
		
		strTemp = info.getAgreementNo();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and agreementNo = '" + strTemp + "' " );
		}
		
		strTemp = info.getLetterCreditNo();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and letterCreditNo = '" + strTemp + "' " );
		}
		
		strTemp = info.getBankName();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and sbankName = '" + strTemp + "' " );
		}
		
		strTemp = info.getFromStartDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and startDate >= to_date('" + strTemp + "','yyyy-mm-dd') " );
		}
		
		strTemp = info.getEndStartDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and startDate <= to_date('" + strTemp + "','yyyy-mm-dd') " );
		}
		
		lTemp = info.getPaymentMode();
		if(lTemp > 0){
			strSQL.append(" and paymentMode = " + lTemp);
		}
		
		lTemp = info.getType();
		if(lTemp > 0){
			strSQL.append(" and type = " + lTemp);
		}
		
		lTemp = info.getStatus();
		if(lTemp > 0){
			strSQL.append(" and status = " + lTemp);
		}
		
		sql[2] = sql[2] + strSQL.toString();
		
		
		//order
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" order by innercode" + strDesc);
		sql[3] = oBuf.toString();
		
		System.out.println("**********SQL**********"+sql[0]+"  "+sql[1]+"   "+sql[2]+"   "+sql[3]);
		
		return sql;

		
	}
	
	// 查询信用证信息
	public LetterCreditInfo getLetterCreditInfo(String innerCode) throws Exception{
		LetterCreditInfo info = null;
		StringBuffer strSQL = new StringBuffer();
		strSQL.append("select a.*,b.scontractno contractno,b.sbankname bankname from ");
		strSQL.append("(select m.*,n.paymentamount from (select t.sinnercode innercode,t.nofficeid officeid,t.sagreementno agreementno,");
		strSQL.append("t.sprojectname projectname,t.npaymentmode paymentmode,t.ntype type,t.nstatus status,t.slettercreditno ");
		strSQL.append("lettercreditno,t.sbeneficiary beneficiary,t.denableddate enableddate,t.sremark remark,t.mhandlingcharge ");
		strSQL.append("handlingcharge,t.dshippingdate shippingdate,t.dstartdate startdate,t.ncurrencytype currencytype,");
		strSQL.append("t.mamount applyamount,t.dnegotationdate negotationdate,t.sdisabledplace disabledplace,t.mexchangerate ");
		strSQL.append("exchangerate,t.nbankcreditextid bankcreditextid,p.name applycompanyname,t.sapplycompanycode applycompanycode ");
		strSQL.append("from loan_letter_credit t left join client_clientinfo p on t.sapplycompanycode=p.code) m ");
		strSQL.append("left join (select u.sinnercode,sum(v.mpaymentamount) paymentamount from loan_letter_credit u left join ");
		strSQL.append("loan_letter_credit_payment v on u.sinnercode=v.sinnercode group by u.sinnercode) n on m.innercode=n.sinnercode) ");
		strSQL.append("a left join loan_bank_creditext b on a.bankcreditextid= b.id where innercode=?");
		
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL.toString());
			System.out.println(strSQL.toString());
			pstmt.setString(1,innerCode);
			rs = pstmt.executeQuery();
			if(rs.next()){
				info = new LetterCreditInfo();
				info.setInnerCode(rs.getString("innercode"));
				info.setApplyCompanyCode(rs.getString("applyCompanyCode"));
				info.setApplyCompanyName(rs.getString("applyCompanyName"));
				info.setAgreementNo(rs.getString("agreementNo"));
				info.setProjectName(rs.getString("projectName"));
				info.setLetterCreditNo(rs.getString("letterCreditNo"));
				info.setBankCreditExtId(rs.getLong("bankCreditExtId"));
				info.setContractNo(rs.getString("contractNo"));
				info.setStartDate(DataFormat.formatDate(rs.getDate("startDate"),1));
				info.setBeneficiary(rs.getString("beneficiary"));
				info.setBankName(rs.getString("bankName"));
				info.setEnabledDate(DataFormat.formatDate(rs.getDate("enabledDate"),1));
				info.setShippingDate(DataFormat.formatDate(rs.getDate("shippingDate"),1));
				info.setNegotationDate(DataFormat.formatDate(rs.getDate("negotationDate"),1));
				info.setDisabledPlace(rs.getString("disabledPlace"));
				info.setCurrencyType(rs.getInt("currencyType"));
				info.setApplyAmount(rs.getDouble("applyAmount"));
				info.setPaymentAmount(rs.getDouble("paymentAmount"));
				info.setExchangeRate(rs.getDouble("exchangeRate"));
				info.setPaymentMode(rs.getInt("paymentMode"));
				info.setType(rs.getInt("type"));
				info.setStatus(rs.getInt("status"));
				info.setHandlingCharge(rs.getDouble("handlingCharge"));
				info.setRemark(rs.getString("remark"));
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		
		return info;
	}
	
	// 查询信用证实际付款情况
	public Collection getLetterCreditPaymentInfo(String innerCode) throws Exception{
		Collection list = null;
		String strSQL = "SELECT * FROM loan_letter_credit_payment WHERE sinnercode=?";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,innerCode);
			rs = pstmt.executeQuery();
			list = new ArrayList();
			while(rs.next()){
				LetterCreditPaymentInfo info = new LetterCreditPaymentInfo();
				info.setId(rs.getInt("id"));
				info.setPaymentAmout(rs.getDouble("mPaymentAmount"));
				info.setPaymentDate(DataFormat.formatDate(rs.getDate("dPaymentDate"),1));
				list.add(info);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return list;
	}
	
	// 新增信用证信息
	public boolean insert(LetterCreditInfo info) throws Exception{
		boolean isInserted = false;
		String strSQL = "INSERT INTO loan_letter_credit values(?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),?,?,to_date(?,'yyyy-mm-dd'),?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,?,sysdate,?,1)";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,info.getInnerCode());
			pstmt.setLong(2,info.getBankCreditExtId());
			pstmt.setLong(3,info.getOfficeId());
			pstmt.setString(4,info.getApplyCompanyCode());
			pstmt.setString(5,info.getAgreementNo());
			pstmt.setString(6,info.getProjectName());
			pstmt.setString(7,info.getLetterCreditNo());
			pstmt.setString(8,info.getStartDate());
			pstmt.setString(9,info.getBeneficiary());
			pstmt.setInt(10,info.getPaymentMode());
			pstmt.setString(11,info.getEnabledDate());
			pstmt.setString(12,info.getDisabledPlace());
			pstmt.setString(13,info.getShippingDate());
			pstmt.setString(14,info.getNegotationDate());
			pstmt.setInt(15,info.getCurrencyType());
			pstmt.setDouble(16,info.getApplyAmount());
			pstmt.setDouble(17,info.getExchangeRate());
			pstmt.setInt(18,info.getType());
			pstmt.setInt(19,info.getStatus());
			pstmt.setDouble(20,info.getHandlingCharge());
			pstmt.setString(21,info.getLastModifier());
			pstmt.setString(22,info.getRemark());
			pstmt.executeUpdate();
			isInserted = true;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return isInserted;
	}
	
	// 新增信用证实际付款信息
	public boolean insert(LetterCreditPaymentInfo info) throws Exception{
		boolean isInserted = false;
		String strSQL = "INSERT INTO loan_letter_credit_payment values((SELECT NVL(MAX(id)+1,1) id from loan_letter_credit_payment),?,to_date(?,'yyyy-mm-dd'),?)";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,info.getInnerCode());
			pstmt.setString(2,info.getPaymentDate());
			pstmt.setDouble(3,info.getPaymentAmout());
			pstmt.executeUpdate();
			isInserted = true;
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return isInserted;
	}
	
	// 修改信用证信息
	public boolean modify(LetterCreditInfo info) throws Exception{
		boolean isModified = false;
		StringBuffer strSQL = new StringBuffer("UPDATE loan_letter_credit SET nBankCreditExtID=?,sApplyCompanyCode=?,");
		strSQL.append("sAgreementNo=?,sProjectName=?,sLetterCreditNo=?,dStartDate=to_date(?,'yyyy-mm-dd'),");
		strSQL.append("sBeneficiary=?,nPaymentMode=?,dEnabledDate=to_date(?,'yyyy-mm-dd'),sDisabledPlace=?,");
		strSQL.append("dShippingDate=to_date(?,'yyyy-mm-dd'),dNegotationDate=to_date(?,'yyyy-mm-dd'),");
		strSQL.append("nCurrencyType=?,mAmount=?,mExchangeRate=?,nType=?,nStatus=?,mHandlingCharge=?,");
		strSQL.append("sLastModifier=?,dLastModifyDate=sysdate,sRemark=? where sinnercode=?");
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL.toString());
			
			pstmt.setLong(1,info.getBankCreditExtId());
			pstmt.setString(2,info.getApplyCompanyCode());
			pstmt.setString(3,info.getAgreementNo());
			pstmt.setString(4,info.getProjectName());
			pstmt.setString(5,info.getLetterCreditNo());
			pstmt.setString(6,info.getStartDate());
			pstmt.setString(7,info.getBeneficiary());
			pstmt.setInt(8,info.getPaymentMode());
			pstmt.setString(9,info.getEnabledDate());
			pstmt.setString(10,info.getDisabledPlace());
			pstmt.setString(11,info.getShippingDate());
			pstmt.setString(12,info.getNegotationDate());
			pstmt.setInt(13,info.getCurrencyType());
			pstmt.setDouble(14,info.getApplyAmount());
			pstmt.setDouble(15,info.getExchangeRate());
			pstmt.setInt(16,info.getType());
			pstmt.setInt(17,info.getStatus());
			pstmt.setDouble(18,info.getHandlingCharge());
			pstmt.setString(19,info.getLastModifier());
			pstmt.setString(20,info.getRemark());
			pstmt.setString(21,info.getInnerCode());
			pstmt.executeUpdate();
			isModified = true;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		
		return isModified;
	}
	
	// 删除信用证信息
	public boolean delete(String innerCode) throws Exception{
		boolean isDeleted = false;
		String strSQL = "UPDATE loan_letter_credit SET nstatus = 0 WHERE sinnercode=?";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,innerCode);
			pstmt.executeUpdate();
			isDeleted = true;
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return isDeleted;
	}
	
	public boolean deletePayment(String innerCode) throws Exception{
		boolean isDeleted = false;
		String strSQL = "DELETE FROM loan_letter_credit_payment WHERE sinnercode=?";
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,innerCode);
			pstmt.executeUpdate();
			isDeleted = true;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		
		return isDeleted;	
	}
	
	public boolean deletePayment(String[] id) throws Exception{
		boolean isDeleted = false;
		StringBuffer strSQL = new StringBuffer("DELETE FROM loan_letter_credit_payment WHERE id IN(");
		if(id != null && id.length >0){
			for(int i=0;i<id.length;i++){
				strSQL.append(id[i]+ ",");
			}
			strSQL = strSQL.deleteCharAt(strSQL.length()-1);
		}
		strSQL.append(")");
		System.out.println(strSQL.toString());
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL.toString());
			pstmt.executeUpdate();
			isDeleted = true;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		
		return isDeleted;
	}
	
	public String getNextCode(String prefix) throws Exception {
		String strSQL = "select sinnercode from loan_letter_credit where sinnercode like ?";
		String strTemp = prefix;
		String newCode = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Env.getSystemDateTime());
		strTemp += calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1);
		try{
			conn = Database.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			pstmt.setString(1,strTemp + "%");
			rs = pstmt.executeQuery();
			int iCode = 0;
			while(rs.next()){
				String scode = rs.getString("sinnercode");
				scode = scode.substring(strTemp.length(),scode.length());
				if(scode != null && !scode.equals("")){
					int iTemp = Integer.parseInt(scode);
					if(iCode < iTemp){
						iCode = iTemp;
					}
				}
			}
			iCode += 1;
			newCode = "" +iCode;
			while(newCode.length() < 4){
				newCode = "0" + newCode;
			}
			newCode = strTemp + newCode;
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return newCode;
	}
	
}
