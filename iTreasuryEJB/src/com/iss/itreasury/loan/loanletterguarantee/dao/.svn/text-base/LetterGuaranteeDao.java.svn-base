package com.iss.itreasury.loan.loanletterguarantee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.bankloan.dataentity.BankLoanInfo;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.letterguarantee.dataentity.VarietyInfo;
import com.iss.itreasury.loan.loanletterguarantee.dataentity.LetterGuaranteeInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.system.dao.PageLoader;

public class LetterGuaranteeDao extends LoanDAO {
	
	public LetterGuaranteeDao(){
		super("loan_Letter_Guarantee");
	}
	
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	//  新增，将info里的信息insert到表loan_Letter_Guarantee里
	public long add(LetterGuaranteeInfo info) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer("insert into loan_Letter_Guarantee( ");
		strSQL.append("id,sOfficeCode,sInnerCode,nBankCreditExtId,sApplyCompanyCode,sAgreementNo,sProjectName,sLetterGuaranteeNo,");
		strSQL.append("sEBeneficiary,sCBeneficiary,sLinkMan,nContractCurrencyType,mContractAmount,nCurrencyType,mAmount,mExchangeRate,");
		strSQL.append("dStartDate,dEndDate,nVariety,nStatus,sLastModifier,dLastModifyDate,nIsImport,sRemark )");
		strSQL.append("values ((select nvl(max(id)+1,1) id from loan_Letter_Guarantee),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,to_date(?,'yyyy-mm-dd'),?,?)");
		
		System.out.println("将info里的信息insert到表loan_Letter_Guarantee里="+strSQL.toString());
		
		try{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL.toString());
			ps.setLong(1,info.getSOfficeCode());
			ps.setString(2,info.getSInnerCode());
			ps.setLong(3,info.getNBankCreditExtId());
			ps.setString(4,info.getSApplyCompanyCode());
			ps.setString(5,info.getSAgreementNo());
			ps.setString(6,info.getSProjectName());
			ps.setString(7,info.getSLetterGuaranteeNo());
			ps.setString(8,info.getSEBeneficiary());
			ps.setString(9,info.getSCBeneficiary());
			ps.setString(10,info.getSLinkMan());
			ps.setLong(11,info.getNContractCurrencyType());
			ps.setDouble(12,info.getMContractAmount());
			ps.setLong(13,info.getNCurrencyType());
			ps.setDouble(14,info.getMAmount());
			ps.setDouble(15,info.getMExchangeRate());
			ps.setString(16,info.getDStartDate());
			ps.setString(17,info.getDEndDate());
			ps.setString(18,info.getNVariety());
			ps.setLong(19,info.getNStatus());
			//ps.setLong(20,info.getNIsValid());
			ps.setLong(20,info.getSLastModifier());
			ps.setString(21,info.getDLastModifyDate());
			ps.setLong(22,info.getNIsImport());
			ps.setString(23,info.getSRemark());
			
			lret = ps.executeUpdate();
			
			//System.out.println("--------------ps.getMaxRows()----------------="+ps.getMaxRows());
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	
	//  取得表loan_Letter_Guarantee中最大的ID
	public long getMaxID() throws Exception
	{
		long lret = -1;
		String strSQL = "select max(id) id from loan_Letter_Guarantee";
		try{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if(rs.next()){
				lret = rs.getLong("id");
			}						
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	
	//	修改，将info里的信息update到表loan_Letter_Guarantee里
	public long modify(LetterGuaranteeInfo info) throws Exception
	{
		long lret = -1;
		StringBuffer strSQL = new StringBuffer("update loan_Letter_Guarantee set "); 
		strSQL.append("nBankCreditExtId=?,sApplyCompanyCode=?,sAgreementNo=?,sProjectName=?,sLetterGuaranteeNo=?,");
		strSQL.append("sEBeneficiary=?,sCBeneficiary=?,sLinkMan=?,nContractCurrencyType=?,mContractAmount=?,nCurrencyType=?,mAmount=?,mExchangeRate=?,");
		strSQL.append("dStartDate=to_date(?,'yyyy-mm-dd'),dEndDate=to_date(?,'yyyy-mm-dd'),nVariety=?,nStatus=?,sLastModifier=?,dLastModifyDate=to_date(?,'yyyy-mm-dd'),nIsImport=?,sRemark=?");
		strSQL.append(" where id=?");
System.out.println("修改，将info里的信息update到表loan_Letter_Guarantee里 sql ="+strSQL.toString());
		
//System.out.println("1111111="+info.getNBankCreditExtId());
//System.out.println("2222222="+info.getSApplyCompanyCode());
//System.out.println("3333333="+info.getSAgreementNo());
//System.out.println("4444444="+info.getSProjectName());
//System.out.println("5555555="+info.getSLetterGuaranteeNo());
//System.out.println("6666666="+info.getSEBeneficiary());
//System.out.println("7777777="+info.getSCBeneficiary());
//System.out.println("8888888="+info.getSLinkMan());
//System.out.println("9999999="+info.getNContractCurrencyType());
//System.out.println("10="+info.getMContractAmount());
//System.out.println("11="+info.getNCurrencyType());
//System.out.println("12="+info.getMAmount());
//System.out.println("13="+info.getMExchangeRate());
//System.out.println("14="+info.getDStartDate());
//System.out.println("15="+info.getDEndDate());
//System.out.println("16="+info.getNVariety());
//System.out.println("17="+info.getNStatus());
////System.out.println("18="+info.getNIsValid());
//System.out.println("18="+info.getSLastModifier());
//System.out.println("19="+info.getDLastModifyDate());
//System.out.println("20="+info.getNIsImport());
//System.out.println("21="+info.getSRemark());
//
//System.out.println("22="+info.getId());

   
		try{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL.toString());
			
			//ps.setLong(1,info.getSOfficeCode());
			//ps.setString(2,info.getSInnerCode());
			ps.setLong(1,info.getNBankCreditExtId());
			ps.setString(2,info.getSApplyCompanyCode());
			ps.setString(3,info.getSAgreementNo());
			ps.setString(4,info.getSProjectName());
			ps.setString(5,info.getSLetterGuaranteeNo());
			ps.setString(6,info.getSEBeneficiary());
			ps.setString(7,info.getSCBeneficiary());
			ps.setString(8,info.getSLinkMan());
			ps.setLong(9,info.getNContractCurrencyType());
			ps.setDouble(10,info.getMContractAmount());
			ps.setLong(11,info.getNCurrencyType());
			ps.setDouble(12,info.getMAmount());
			ps.setDouble(13,info.getMExchangeRate());
			ps.setString(14,info.getDStartDate());
			ps.setString(15,info.getDEndDate());
			ps.setString(16,info.getNVariety());
			ps.setLong(17,info.getNStatus());
			//ps.setLong(18,info.getNIsValid());
			ps.setLong(18,info.getSLastModifier());
			ps.setString(19,info.getDLastModifyDate());
			ps.setLong(20,info.getNIsImport());
			ps.setString(21,info.getSRemark());
			
			ps.setLong(22,info.getId());
			
			lret = ps.executeUpdate();
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	
	//	删除，将info里的信息update到表loan_Letter_Guarantee里
	public long delete(long id) throws Exception
	{
		long lret = -1;
		String strSQL = "UPDATE loan_Letter_Guarantee SET nStatus = 0 WHERE id=?";
		String strSQL1 = "delete Loan_Letter_Guarantee_Charge where guaranteeid="+id;
		try{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1,id);
			lret = ps.executeUpdate();
			ps.clearParameters();
			ps.close();
			
			ps = conn.prepareStatement(strSQL1);
			lret = ps.executeUpdate();
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return lret;
	}
	
    //根据ID查询
	public LetterGuaranteeInfo findByID(long id) throws Exception{
		LetterGuaranteeInfo info = null;
		String strSQL = "SELECT a.*,b.SCONTRACTNO,b.SBANKNAME,c.sName,d.sname as clientName from loan_Letter_Guarantee a , LOAN_BANK_CREDITEXT b , LOAN_LETTEROFINDEMNITY_VARIETY c, client d  where a.NBANKCREDITEXTID=b.id and a.nVariety=c.sCode and a.sApplyCompanyCode=d.sCode and a.id=?";
		try{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1,id);
			rs = ps.executeQuery();
			if(rs.next()){
				info = new LetterGuaranteeInfo();
				info.setId(rs.getLong("id"));
				info.setSOfficeCode(rs.getLong("sOfficeCode"));
				info.setSInnerCode(rs.getString("sInnerCode"));
				info.setNBankCreditExtId(rs.getLong("nBankCreditExtId"));
				info.setSApplyCompanyCode(rs.getString("sApplyCompanyCode"));
				info.setSAgreementNo(rs.getString("sAgreementNo"));
				info.setSProjectName(rs.getString("sProjectName"));
				info.setSLetterGuaranteeNo(rs.getString("sLetterGuaranteeNo"));
				info.setSEBeneficiary(rs.getString("sEBeneficiary"));
				info.setSCBeneficiary(rs.getString("sCBeneficiary"));
				info.setSLinkMan(rs.getString("sLinkMan"));
				info.setNContractCurrencyType(rs.getLong("nContractCurrencyType"));
				info.setMContractAmount(rs.getLong("mContractAmount"));
				info.setNCurrencyType(rs.getLong("nCurrencyType"));
				info.setMAmount(rs.getLong("mAmount"));
				info.setMExchangeRate(rs.getLong("mExchangeRate"));
				
				info.setOriginalCoverRMB(rs.getLong("mAmount")*rs.getLong("mExchangeRate"));
				
				info.setDStartDate(DataFormat.formatDate(rs.getDate("dStartDate"),1));
				info.setDEndDate(DataFormat.formatDate(rs.getDate("dEndDate"),1));
				info.setNVariety(rs.getString("nVariety"));
				info.setNStatus(rs.getLong("nStatus"));
				//info.setNIsValid(rs.getLong("nIsValid"));
				info.setSLastModifier(rs.getLong("sLastModifier"));
				info.setDLastModifyDate(DataFormat.formatDate(rs.getDate("dLastModifyDate"),1));
				info.setNIsImport(rs.getLong("nIsImport"));
				info.setSRemark(rs.getString("sRemark"));
				
				info.setSBankname(rs.getString("SBANKNAME"));
				info.setScontractNo(rs.getString("SCONTRACTNO"));
				info.setClientName(rs.getString("clientName"));
				info.setSName(rs.getString("sName"));
				
			}
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return info;
		
	}
    //获得所有的信息记录
	public Collection findAll() throws Exception{
		Collection cret = null;
		String strSQL = "SELECT a.*,b.SCONTRACTNO,b.SBANKNAME,c.sName,d.sname as clientName from loan_Letter_Guarantee a , LOAN_BANK_CREDITEXT b , LOAN_LETTEROFINDEMNITY_VARIETY c, client d where a.NBANKCREDITEXTID=b.id and a.nVariety=c.sCode and a.sApplyCompanyCode=d.sCode and a.nStatus > 0  ORDER BY a.id";
		try{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			cret = new ArrayList();
			while(rs.next()){
				LetterGuaranteeInfo info =  new LetterGuaranteeInfo();
				info.setId(rs.getLong("id"));
				info.setSOfficeCode(rs.getLong("sOfficeCode"));
				info.setSInnerCode(rs.getString("sInnerCode"));
				info.setNBankCreditExtId(rs.getLong("nBankCreditExtId"));
				info.setSApplyCompanyCode(rs.getString("sApplyCompanyCode"));
				info.setSAgreementNo(rs.getString("sAgreementNo"));
				info.setSProjectName(rs.getString("sProjectName"));
				info.setSLetterGuaranteeNo(rs.getString("sLetterGuaranteeNo"));
				info.setSEBeneficiary(rs.getString("sEBeneficiary"));
				info.setSCBeneficiary(rs.getString("sCBeneficiary"));
				info.setSLinkMan(rs.getString("sLinkMan"));
				info.setNContractCurrencyType(rs.getLong("nContractCurrencyType"));
				info.setMContractAmount(rs.getLong("mContractAmount"));
				info.setNCurrencyType(rs.getLong("nCurrencyType"));
				info.setMAmount(rs.getLong("mAmount"));
				info.setMExchangeRate(rs.getLong("mExchangeRate"));
				info.setDStartDate(DataFormat.formatDate(rs.getDate("dStartDate"),1));
				info.setDEndDate(DataFormat.formatDate(rs.getDate("dEndDate"),1));
				info.setNVariety(rs.getString("nVariety"));
				info.setNStatus(rs.getLong("nStatus"));
				//info.setNIsValid(rs.getLong("nIsValid"));
				info.setSLastModifier(rs.getLong("sLastModifier"));
				info.setDLastModifyDate(DataFormat.formatDate(rs.getDate("dLastModifyDate"),1));
				info.setNIsImport(rs.getLong("nIsImport"));
				info.setSRemark(rs.getString("sRemark"));
				
				cret.add(info);
			}
		}catch(Exception e){
			
			e.printStackTrace();
			throw e;
		}finally{
			if(rs != null){
				rs.close();
				rs = null;
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return cret;
	}

	//生成内部流水号
	public String getNextCode(String prefix) throws Exception {
		String strSQL = "select sInnerCode from loan_Letter_Guarantee where sInnerCode like ?";
		String strTemp = prefix;
		String newCode = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Env.getSystemDateTime());
		strTemp += calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH)+1);
		try{
			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setString(1,strTemp + "%");
			rs = ps.executeQuery();
			int iCode = 0;
			while(rs.next()){
				String scode = rs.getString("sInnerCode");
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
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
		}
		return newCode;
	}

	public PageLoader findWithPage(LetterGuaranteeInfo info) throws Exception{
		
		String[] sql = getSQL(info);
		
		PageLoader pageLoader = null;
		try{
		pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.loanletterguarantee.dataentity.LetterGuaranteeInfo", null);
		
		pageLoader.setOrderBy(" " + sql[3] + " ");	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return pageLoader;

	}
	
	public PageLoader findAll(long officeId) throws Exception{
		String[] sql = new String[4];
		sql[0] = " a.*,b.SCONTRACTNO,b.SBANKNAME,c.sName,d.sname as clientName ";
		sql[1] = " loan_Letter_Guarantee a , LOAN_BANK_CREDITEXT b , LOAN_LETTEROFINDEMNITY_VARIETY c, client d ";
		sql[2] = " a.NBANKCREDITEXTID=b.id and a.nVariety=c.sCode and a.sApplyCompanyCode=d.sCode and a.nStatus > 0 and a.sOfficeCode=" + officeId;
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" order by id" + strDesc);
		sql[3] = oBuf.toString();
		
		PageLoader pageLoader = null;
		try{
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
			pageLoader.initPageLoader(new AppContext(), sql[1], sql[0], sql[2], (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.loan.loanletterguarantee.dataentity.LetterGuaranteeInfo", null);
		
			pageLoader.setOrderBy(" " + sql[3] + " ");	
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}

		return pageLoader;
	}
	
	private String[] getSQL(LetterGuaranteeInfo info) throws Exception{
		
		String[] sql = new String[4];
		StringBuffer strSQL = new StringBuffer();
		String strTemp = null;
		long lTemp = -1;
		
		//select
		sql[0] =" a.*,b.SCONTRACTNO,b.SBANKNAME,c.sName,d.sname as clientName";
		//from
		sql[1] =" loan_Letter_Guarantee a , LOAN_BANK_CREDITEXT b , LOAN_LETTEROFINDEMNITY_VARIETY c, client d";
		//where
		sql[2] =" a.NBANKCREDITEXTID=b.id and a.nVariety=c.sCode and a.sApplyCompanyCode=d.sCode and a.nStatus > 0 and a.sOfficeCode=" + info.getSOfficeCode();

		strTemp = info.getSApplyCompanyCode();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and a.sApplyCompanyCode='" + strTemp + "'");
		}
		
		strTemp = info.getSAgreementNo();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and a.sAgreementNo='" + strTemp + "'" );
		}
		
		strTemp = info.getSLetterGuaranteeNo();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and a.sLetterGuaranteeNo='" + strTemp + "'");
		}
		
		lTemp = info.getNBankCreditExtId();
		if(lTemp > 0){
			strSQL.append(" and a.nBankCreditExtId=" + lTemp);
		}
		lTemp = info.getNCurrencyType();
		if(lTemp > 0){
			strSQL.append(" and a.nCurrencyType=" + lTemp);
		}
		strTemp = info.getNVariety();
		if(strTemp != null && !strTemp.equals("-1") && !strTemp.equals("")){
			strSQL.append(" and a.nVariety='" + strTemp + "'" );
		}
		lTemp = info.getNStatus();
		if(lTemp > 0){
			strSQL.append(" and a.nStatus=" + lTemp);
		}
		
		strTemp = info.getFromdStartDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and a.dStartDate>=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getEnddStartDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and a.dStartDate<=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getFromdEndDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and a.dEndDate>=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		strTemp = info.getEnddEndDate();
		if(strTemp != null && !strTemp.equals("")){
			strSQL.append(" and a.dEndDate<=to_date('" + strTemp + "','yyyy-mm-dd')");
		}
		
		sql[2] = sql[2] + strSQL.toString();
		
		
		//order
		String strDesc = " desc ";
		StringBuffer oBuf = new StringBuffer();
		oBuf.append(" order by a.id" + strDesc);
		sql[3] = oBuf.toString();
		
		System.out.println("**********SQL**********"+sql[0]+"  "+sql[1]+"   "+sql[2]+"   "+sql[3]);
		
		return sql;

	}
	
    /**
     *  获得所有的保函种类
     */
	public Collection getAllVariety() throws Exception 
	{
		Collection allVariety = null;
		StringBuffer strSQL = new StringBuffer();
		try
        {
            strSQL.append("select * from LOAN_LETTEROFINDEMNITY_VARIETY order by sCode");  	     	
            
            // 获得数据库连接，初使化PreparedStatement
            conn = Database.getConnection();
            ps = conn.prepareStatement(strSQL.toString());
            rs = ps.executeQuery();
            while(rs.next()) {
            	
            }
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("查询所有保函种类出错",e);
        }
        finally
        {
        	if(rs != null){
				rs.close();
				rs = null;
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
        }
        return allVariety;
	}
	
	/** 
	 *  根据保函种类sCode查询保函种类名称
	 */
	public String getVarietyByCode(String sCode) throws Exception
	{
		String sName = "";
		StringBuffer strSQL = new StringBuffer();
		try
        {	
			strSQL.append("select sname from LOAN_LETTEROFINDEMNITY_VARIETY where sCode=?" );  	
           
            //获得数据库连接，初使化PreparedStatement	
            conn = Database.getConnection();
            ps = conn.prepareStatement(strSQL.toString()); 
            ps.setString(1,sCode);
            rs = ps.executeQuery();
            //info = this.getDataEntityFromResultSet();
            if(rs.next())
            {
            	 sName = rs.getString("sname");
            }     	
            
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("根据保函种类编号查询保函种类名称出错",e);
        }
        finally
        {
        	if(rs != null){
				rs.close();
				rs = null;
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
        }
		return sName;	
	}
	
	/** 
	 *  根据授信ID查询授信银行名称
	 */
	public String getIDByName(long id) throws Exception
	{
		String sName = "";
		StringBuffer strSQL = new StringBuffer();
		try
        {	
			strSQL.append("select SBANKNAME from LOAN_BANK_CREDITEXT where id=?" );  	
            System.out.println("根据授信ID查询授信银行名称 ---sql--- = select SBANKNAME from LOAN_BANK_CREDITEXT where id=" + id );
            //获得数据库连接，初使化PreparedStatement	
            conn = Database.getConnection();
            ps = conn.prepareStatement(strSQL.toString()); 
            ps.setLong(1,id);
            rs = ps.executeQuery();
            //info = this.getDataEntityFromResultSet();
            if(rs.next())
            {
            	 sName = rs.getString("SBANKNAME");
            }     	
            
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("根据授信ID查询授信银行名称出错",e);
        }
        finally
        {
        	if(rs != null){
				rs.close();
				rs = null;
			}
			if(ps != null){
				ps.close();
				ps = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
        }
		return sName;	
	}
	

}
