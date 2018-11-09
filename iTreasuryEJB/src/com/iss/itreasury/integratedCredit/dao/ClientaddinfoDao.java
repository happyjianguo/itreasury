package com.iss.itreasury.integratedCredit.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import com.iss.itreasury.integratedCredit.dataentity.Clientaddinfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;

public class ClientaddinfoDao {
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	public Collection findbyClientId(long clientid)throws Exception
	{
		Collection c = null;
		String strSQL = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			con = Database.getConnection();
			
			strSQL ="select * from clientaddinfo where clientid=?";
			
			log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			ps.setLong(1, clientid);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				Clientaddinfo clientadd = new Clientaddinfo();
				clientadd.setId(rs.getLong("id"));
				clientadd.setClientid(rs.getLong("clientid"));
				clientadd.setEschargesector(rs.getString("eschargesector"));
				clientadd.setNauphone(rs.getString("nauphone"));
				clientadd.setNcompanyNature(rs.getLong("ncompanyNature"));
				clientadd.setNCompanyRegion(rs.getLong("ncompanyRegion"));
				clientadd.setNCompanySize(rs.getLong("ncompanySize"));
				clientadd.setNCorporateLevel(rs.getLong("ncorporateLevel"));
				clientadd.setNcorporatenature(rs.getLong("ncorporatenature"));
				clientadd.setNequityCompany(rs.getLong("nequityCompany"));
				clientadd.setNholdingCompany(rs.getLong("nholdingCompany"));
				clientadd.setNmainBusiness(rs.getLong("nmainBusiness"));
				clientadd.setNPaidCapital(rs.getLong("npaidCapital"));
				clientadd.setNsubsidiary(rs.getLong("nsubsidiary"));
				clientadd.setNsubsidiaryPlant(rs.getLong("nsubsidiaryPlant"));
				clientadd.setNtreasurerPhone(rs.getString("ntreasurerPhone"));
				clientadd.setNupportthedevelopment(rs.getLong("nupportthedevelopment"));
				clientadd.setSauotherDuties(rs.getString("sauotherDuties"));
				clientadd.setSauthorizedagentName(rs.getString("sauthorizedagentName"));
				clientadd.setNCategory1(rs.getLong("ncategory1"));
				clientadd.setNCategory2(rs.getLong("ncategory2"));
				clientadd.setNCategory3(rs.getLong("ncategory3"));
				clientadd.setScorporateCulture(rs.getString("scorporateCulture"));
				clientadd.setScorporategender(rs.getLong("scorporategender"));
				clientadd.setScorporateOrigin(rs.getString("scorporateOrigin"));
				clientadd.setScorporateQualifications(rs.getString("scorporateQualifications"));
				clientadd.setScorWorkExperience(rs.getString("scorWorkExperience"));
				clientadd.setSmainBusiness(rs.getString("smainBusiness"));
				clientadd.setSmainDepositaryBank(rs.getString("smainDepositaryBank"));
				clientadd.setSorganizationAlstructure(rs.getString("sorganizationAlstructure"));
				clientadd.setSotherBusiness(rs.getString("sotherBusiness"));
				clientadd.setSqenterpriseHistory(rs.getString("sqenterpriseHistory"));
				clientadd.setStatutoryName(rs.getString("statutoryName"));
				clientadd.setStatutoryotherduties(rs.getString("statutoryotherduties"));
				clientadd.setStatutorySphone(rs.getString("statutorySphone"));
				clientadd.setStreasurerDuty(rs.getString("streasurerDuty"));
				clientadd.setStreasurerName(rs.getString("streasurerName"));
	            clientadd.setNregistercapital(rs.getLong("nregistercapital"));
	            clientadd.setNcorporateage(rs.getLong("ncorporateage"));
	            clientadd.setScorporatename(rs.getString("scorporatename"));
	            clientadd.setNGuquanjiegou(rs.getLong("NGUQUANJIEGOU"));	            
				
				c.add(clientadd);
			}
			if(rs !=null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
			
		}catch(Exception e)
		{
			e.printStackTrace();
			if(rs !=null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
		}finally
        {
            try
            {
            	if(rs !=null)
    			{
    				rs.close();
    				rs = null;
    			}
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (Exception e)
            {
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }
		return c;
	}
	public long InsertClientaddinfo(Clientaddinfo clientadd)throws IException,Exception
	{
		long lResult=-1;
        String strSQL = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
        	con = Database.getConnection();
        	strSQL="select max(id) as id from clientaddinfo";
        	log4j.info("SQL=" + strSQL);
			ps = con.prepareStatement(strSQL);
			System.out.println("查最大值strSQL="+strSQL);
			rs = ps.executeQuery();
			if (rs != null && rs.next())
			{
				clientadd.setId(rs.getLong("id")+1);
			}else
				clientadd.setId(1);
			if(rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
            {
                ps.close();
                ps = null;
            }

        	strSQL=" insert into clientaddinfo(id,clientid,SMAINBUSINESS,SOTHERBUSINESS,NUPPORTTHEDEVELOPMENT,"+
        			"STATUTORYNAME,STATUTORYOTHERDUTIES,STATUTORYSPHONE,SAUTHORIZEDAGENTNAME,SAUOTHERDUTIES,"+
        			"NAUPHONE,STREASURERNAME,STREASURERDUTY,NTREASURERPHONE,SCORPORATEGENDER,SCORPORATEQUALIFICATIONS,"+
        			"SCORPORATEORIGIN,SCORWORKEXPERIENCE,NCORPORATENATURE,NCOMPANYNATURE,nCompanySize,"+
        			"nCorporateLevel,nCompanyRegion,nmainBusiness,nCategory1,nCategory2,nCategory3,ESCHARGESECTOR,"+
        			"NSUBSIDIARY,NHOLDINGCOMPANY,nEQUITYCOMPANY,nSUBSIDIARYPLANT,sORGANIZATIONALSTRUCTURE,"+
        			"sQENTERPRISEHISTORY,sMAINDEPOSITARYBANK,sCORPORATECULTURE,nPaidCapital,nregistercapital," +
        			"ncorporateage,scorporatename,NGUQUANJIEGOU) "
        		   +"values(?,?,?,?,?,?,?,?,?,?,"+"?,?,?,?,?,?,?,?,?,?,"+"?,?,?,?,?,?,?,?,?,?,"+"?,?,?,?,?,?,?,?,?,?,?)";
        	log4j.info(" 客户新增表=" + strSQL);

            ps = con.prepareStatement(strSQL);
            ps.setLong(1, clientadd.getId()); 
            ps.setLong(2, clientadd.getClientid());
            ps.setString(3,clientadd.getSmainBusiness()); 
            ps.setString(4,clientadd.getSotherBusiness()); 
            ps.setLong(5,clientadd.getNupportthedevelopment());
            ps.setString(6,clientadd.getStatutoryName()); 
            ps.setString(7,clientadd.getStatutoryotherduties());
            ps.setString(8,clientadd.getStatutorySphone()); 
            ps.setString(9,clientadd.getSauthorizedagentName());
            ps.setString(10,clientadd.getSauotherDuties()); 
            ps.setString(11, clientadd.getNauphone()); 
            ps.setString(12,clientadd.getStreasurerName()); 
            ps.setString(13,clientadd.getStreasurerDuty()); 
            ps.setString(14,clientadd.getNtreasurerPhone());
            ps.setLong(15,clientadd.getScorporategender());
            ps.setString(16,clientadd.getScorporateQualifications()); 
            ps.setString(17,clientadd.getScorporateOrigin()); 
            ps.setString(18,clientadd.getScorWorkExperience());
            ps.setLong(19, clientadd.getNcorporatenature()); 
            ps.setLong(20, clientadd.getNcompanyNature());
            ps.setLong(21, clientadd.getNCompanySize()); 
            ps.setLong(22, clientadd.getNCorporateLevel()); 
            ps.setLong(23, clientadd.getNCompanyRegion()); 
            ps.setLong(24,clientadd.getNmainBusiness());
            ps.setLong(25,clientadd.getNCategory1()); 
            ps.setLong(26,clientadd.getNCategory2()); 
            ps.setLong(27,clientadd.getNCategory3());
            ps.setString(28, clientadd.getEschargesector());  
            ps.setLong(29,clientadd.getNsubsidiary());
            ps.setLong(30,clientadd.getNholdingCompany()); 
            ps.setLong(31,clientadd.getNequityCompany());  
            ps.setLong(32,clientadd.getNsubsidiaryPlant());
            ps.setString(33,clientadd.getSorganizationAlstructure()); 
            ps.setString(34,clientadd.getSqenterpriseHistory() ); 
            ps.setString(35,clientadd.getSmainDepositaryBank()); 
            ps.setString(36,clientadd.getScorporateCulture());
            ps.setLong(37,clientadd.getNPaidCapital());  
            ps.setLong(38, clientadd.getNregistercapital());
            ps.setLong(39,clientadd.getNcorporateage());
            ps.setString(40,clientadd.getScorporatename());
            ps.setLong(41,clientadd.getNGuquanjiegou());

            lResult = ps.executeUpdate();
            
            if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (lResult < 0)
            {
                lResult = -1;
                return lResult;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        }catch(Exception e)
        {
        	e.printStackTrace();
        	if(rs != null)
 			{
 				rs.close();
 				rs = null;
 			}
             if (ps != null)
             {
                 ps.close();
                 ps = null;
             }
             if (con != null)
             {
                 con.close();
                 con = null;
             }
             throw new IException("Gen_E001");
        }finally
        {
            try
            {
            	if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (Exception e)
            {
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }
        
        return lResult;
	}
	public long FindClientaddinfo(Clientaddinfo clientadd)throws IException,Exception
	{
		long lResult=-1;
        String strSQL = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		try{
        	con = Database.getConnection();
        	strSQL="select * from clientaddinfo where clientid ="+clientadd.getClientid();
        	ps = con.prepareStatement(strSQL);
        	System.out.println("1查strSQL="+strSQL);
        	rs = ps.executeQuery();
        	if(rs != null && rs.next()){
        		lResult = rs.getLong("clientid");
        	}
        	if(rs != null && rs.next())
        		return -1;
        	if(rs != null)
			{
				rs.close();
				rs = null;
			}
			if (ps != null)
            {
                ps.close();
                ps = null;
            }
			if (con != null)
            {
                con.close();
                con = null;
            }
        	
        }catch(Exception e)
        {
        	 e.printStackTrace();
        	 if(rs != null)
 			{
 				rs.close();
 				rs = null;
 			}
             if (ps != null)
             {
                 ps.close();
                 ps = null;
             }
             if (con != null)
             {
                 con.close();
                 con = null;
             }
             throw new IException("Gen_E001");
        }finally
        {
            try
            {
            	if(rs != null)
    			{
    				rs.close();
    				rs = null;
    			}
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (Exception e)
            {
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }
        return lResult;
	}
	public long SaveClientaddinfo(Clientaddinfo clientadd)throws IException,Exception
	{
		long lResult=-1;
        
        try{
        	lResult = FindClientaddinfo(clientadd);
        	if(lResult == clientadd.getClientid())
        	{
        		lResult = UpdateClientaddinfo(clientadd);
        	}else
        	{	lResult = InsertClientaddinfo(clientadd);}
        	
        }catch(Exception e)
        {
        	 e.printStackTrace();
             throw new IException("Gen_E001");
        }
		
		return lResult;
	}
	public long UpdateClientaddinfo(Clientaddinfo clientadd)throws IException,Exception
	{
		long lResult=-1;
        String strSQL = null;
        Connection con = null;
        PreparedStatement ps = null;
        try{
        	System.out.println("###########################################################");
        	con = Database.getConnection();
        	strSQL=" update clientaddinfo set SMAINBUSINESS=?,SOTHERBUSINESS=?,NUPPORTTHEDEVELOPMENT=?,"+
			"STATUTORYNAME=?,STATUTORYOTHERDUTIES=?,STATUTORYSPHONE=?,SAUTHORIZEDAGENTNAME=?,SAUOTHERDUTIES=?,"+
			"NAUPHONE=?,STREASURERNAME=?,STREASURERDUTY=?,NTREASURERPHONE=?,SCORPORATEGENDER=?,SCORPORATEQUALIFICATIONS=?,"+
			"SCORPORATEORIGIN=?,SCORWORKEXPERIENCE=?,NCORPORATENATURE=?,NCOMPANYNATURE=?,nCompanySize=?,"+
			"nCorporateLevel=?,nCompanyRegion=?,nmainBusiness=?,nCategory1=?,nCategory2=?,nCategory3=?,ESCHARGESECTOR=?,"+
			"NSUBSIDIARY=?,NHOLDINGCOMPANY=?,nEQUITYCOMPANY=?,nSUBSIDIARYPLANT=?,sORGANIZATIONALSTRUCTURE=?,"+
			"sQENTERPRISEHISTORY=?,sMAINDEPOSITARYBANK=?,sCORPORATECULTURE=?,nPaidCapital=?,nregistercapital=?," +
			"ncorporateage=?,scorporatename=?,NGUQUANJIEGOU=? where clientid=?";
        	log4j.info(" 客户新增表=" + strSQL);
        	ps = con.prepareStatement(strSQL);       
        	ps.setString(1, clientadd.getSmainBusiness());  
        	ps.setString(2, clientadd.getSotherBusiness());       
        	ps.setLong(3, clientadd.getNupportthedevelopment());  
        	ps.setString(4, clientadd.getStatutoryName()); 
        	ps.setString(5, clientadd.getStatutoryotherduties());
        	ps.setString(6, clientadd.getStatutorySphone());    
        	ps.setString(7, clientadd.getSauthorizedagentName()); 
        	ps.setString(8, clientadd.getSauotherDuties()); 
        	ps.setString(9,clientadd.getNauphone());  
        	ps.setString(10,clientadd.getStreasurerName()); 
        	ps.setString(11,clientadd.getStreasurerDuty());  
        	ps.setString(12,clientadd.getNtreasurerPhone());  
        	ps.setLong(13,clientadd.getScorporategender()); 
        	ps.setString(14,clientadd.getScorporateQualifications()); 
        	ps.setString(15,clientadd.getScorporateOrigin());   
        	ps.setString(16,clientadd.getScorWorkExperience());
        	ps.setLong(17,clientadd.getNcorporatenature());     
        	ps.setLong(18,clientadd.getNcompanyNature()); 
        	ps.setLong(19,clientadd.getNCompanySize()); 
        	ps.setLong(20,clientadd.getNCorporateLevel()); 
        	ps.setLong(21,clientadd.getNCompanyRegion()); 
        	ps.setLong(22,clientadd.getNmainBusiness());
        	ps.setLong(23,clientadd.getNCategory1());
        	ps.setLong(24,clientadd.getNCategory2()); 
        	ps.setLong(25,clientadd.getNCategory3()); 
        	ps.setString(26,clientadd.getEschargesector()); 
        	ps.setLong(27,clientadd.getNsubsidiary()); 
        	ps.setLong(28,clientadd.getNholdingCompany()); 
        	ps.setLong(29,clientadd.getNequityCompany()); 
        	ps.setLong(30,clientadd.getNsubsidiaryPlant()); 
        	ps.setString(31,clientadd.getSorganizationAlstructure()); 
        	ps.setString(32,clientadd.getSqenterpriseHistory()); 
        	ps.setString(33,clientadd.getSmainDepositaryBank()); 
        	ps.setString(34,clientadd.getScorporateCulture()); 
        	ps.setLong(35,clientadd.getNPaidCapital()); 
        	ps.setLong(36,clientadd.getNregistercapital()); 
        	ps.setLong(37,clientadd.getNcorporateage()); 
        	ps.setString(38,clientadd.getScorporatename()); 
        	ps.setLong(39,clientadd.getNGuquanjiegou()); 
        	ps.setLong(40,clientadd.getClientid()); 

        	lResult = ps.executeUpdate();
        	if (ps != null)
            {
                ps.close();
                ps = null;
            }
            if (con != null)
            {
                con.close();
                con = null;
            }
        	if (lResult < 0)
            {
                lResult = -1;
                return lResult;
            }
        }catch(Exception e)
        {
        	 e.printStackTrace();
             if (ps != null)
             {
                 ps.close();
                 ps = null;
             }
             if (con != null)
             {
                 con.close();
                 con = null;
             }
             throw new IException("Gen_E001");
        }finally
        {
            try
            {
                if (ps != null)
                {
                    ps.close();
                    ps = null;
                }
                if (con != null)
                {
                    con.close();
                    con = null;
                }
            }
            catch (Exception e)
            {
                log4j.error(e.toString());
                throw new IException("Gen_E001");
            }
        }
		return lResult;
	}

}
