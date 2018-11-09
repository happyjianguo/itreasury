package com.iss.itreasury.loan.letterofindemnity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.base.LoanDAO;
import com.iss.itreasury.loan.base.LoanDAOException;
import com.iss.itreasury.loan.letterofindemnity.dataentity.VarietyInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log4j;
/**
 * @author fxzhang
 * 
 */
public class VarietyDAO extends LoanDAO{
	
	private Log4j log4j = new Log4j(Constant.ModuleType.LOAN, this);
	
	private Connection conn = null;
	private PreparedStatement pstm = null;
	private ResultSet rs = null;
	
	public VarietyDAO() 
	{
		super("LOAN_LETTEROFINDEMNITY_VARIETY");   //LOAN_LETTEROFINDEMNITY_VARIETYΪ��Ӧ�����ݿ����
	}
	public VarietyDAO(Connection conn) 
	{
		super("LOAN_LETTEROFINDEMNITY_VARIETY",conn);   //LOAN_LETTEROFINDEMNITY_VARIETYΪ��Ӧ�����ݿ����
	}
    /**
     *  ������еı�������
     */
	public Collection getAllVariety() throws Exception 
	{
		Collection allVariety = null;
		StringBuffer strSQL = new StringBuffer();
		try
        {
            strSQL.append("select * from LOAN_LETTEROFINDEMNITY_VARIETY order by sCode");  	     	
            log4j.print( strSQL.toString() );	
            // ������ݿ����ӣ���ʹ��PreparedStatement
            conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());
            rs = pstm.executeQuery();
            allVariety = this.getDataEntitiesFromResultSet();
            
            finalize();
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("��ѯ���б����������",e);
        }
        finally
        {
           finalize();
        }
        return allVariety;
	}
	
	
	/** 
	 *  ���ݱ��������Ų�ѯ����������Ϣ
	 */
	public VarietyInfo getVarietyByCode(String code) throws Exception
	{
		VarietyInfo info = null;
		StringBuffer strSQL = new StringBuffer();
		try
        {	
			strSQL.append("select * from LOAN_LETTEROFINDEMNITY_VARIETY where sCode=?" );  	
            log4j.print( strSQL.toString());	
            //������ݿ����ӣ���ʹ��PreparedStatement	
            conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString()); 
            pstm.setString(1,code);
            rs = pstm.executeQuery();
            info = this.getDataEntityFromResultSet();
            if(info.getSCode() != null)
            {
            	info.nullToString(); 
            }     	
            else
            {
            	info = null;
            } 	
            finalize();
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("���ݱ��������Ų�ѯ����������Ϣ����",e);
        }
        finally
        {
           finalize();
        }
		return info;	
	}
	
	
	/**
	 *  ���汣�������޸���Ϣ
	 */ 
	public boolean modify(VarietyInfo info) throws Exception
	{
		int effected = -1;
		StringBuffer strSQL = new StringBuffer();
		try
        {
            strSQL.append("update LOAN_LETTEROFINDEMNITY_VARIETY set sName=?,sRemark=?,Npredefined=1");
            strSQL.append(" where sCode=?");         
            log4j.print( strSQL.toString());	
            //������ݿ����ӣ���ʹ��PreparedStatement
            conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setString(1,info.getSName());
            pstm.setString(2,info.getSRemark());
            pstm.setString(3,info.getSCode());
            effected = pstm.executeUpdate();	
            log4j.debug(effected + " lines effected!");
            
            finalize();
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("�޸ı����������",e);
        }
        finally
        {
           finalize();
        }
		return (effected > 0 ? true : false);		
	}
	
	
	/**
	 *  �жϸñ���������û����ʹ��
	 *  
	 */ 
	public boolean isUsed(String code) throws Exception
	{
		return false;
	}
	
	
	/**
	 *  ɾ����������
	 */ 
	public boolean delete(String code) throws Exception
	{
		int effected = -1;
		StringBuffer strSQL = new StringBuffer();
		try
        {
            strSQL.append("delete from LOAN_LETTEROFINDEMNITY_VARIETY where sCode=?");     
            log4j.print( strSQL.toString());	
            //������ݿ����ӣ���ʹ��PreparedStatement
            conn = Database.getConnection();
            pstm = conn.prepareStatement(strSQL.toString());
            pstm.setString(1,code);
            effected = pstm.executeUpdate();         
            log4j.debug(effected + " lines effected!");
            
            finalize();
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("ɾ�������������",e);
        }
        finally
        {
           finalize();
        }
        return (effected > 0 ? true : false);	
	}
	
	
	/**
	 *  ȡ����һ��������������ı��
	 */ 
	public String getNextCode() throws Exception
	{
		String code = "";
		StringBuffer strSQL = new StringBuffer();
		try
        {
           
            strSQL.append("select sCode  from LOAN_LETTEROFINDEMNITY_VARIETY order by sCode DESC") ;  	       
            log4j.print( strSQL.toString());	
            //������ݿ����ӣ���ʹ��PreparedStatement
            conn = Database.getConnection();
            pstm  = conn.prepareStatement(strSQL.toString());
            rs = pstm.executeQuery();         
            if(rs.next())
            {
                if(rs.getString("sCode") != null)
                {
                	code =  String.valueOf(Integer.parseInt(rs.getString("sCode"))+1);
                	if(code.length() == 1)
                	{
                		code = "0" + code;
                	}
                }     
            } 
            finalize();
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("��ѯ����ų���",e);
        }         
        finally
        {
        	finalize();
        }
		return code;		
	}
	
	
	/**
	 * ������������
	 */ 
	public boolean insert(VarietyInfo info) throws Exception
	{
		int effected = -1;
		StringBuffer strSQL = new StringBuffer();	
		try
        {
            strSQL.append("insert into LOAN_LETTEROFINDEMNITY_VARIETY(sCode,sName,sRemark,nPredefined)");
            strSQL.append(" values (?,?,?,1)" );
            log4j.print( strSQL.toString());	
            //������ݿ����ӣ���ʹ��PreparedStatement	
            conn = Database.getConnection();
            pstm  = conn.prepareStatement(strSQL.toString());
            pstm.setString(1,info.getSCode());
            pstm.setString(2,info.getSName());
            pstm.setString(3,info.getSRemark());
            effected = pstm.executeUpdate();    
            log4j.debug(effected + " lines effected!");
            
            finalize();
        }
        catch(Exception e)
        {
            throw new ITreasuryDAOException("���������������",e);
        }        
        finally
        {
        	finalize();
        }
        return (effected > 0 ? true : false);	
	}
	
	/**
	 * �ر����ݿ���Դ
	 */
	public void finalize() throws ITreasuryDAOException
	{		
		try 
         {
 			if (rs != null) 
 			{
 				rs.close();
 				rs = null;
 			}

 			if (pstm != null) 
 			{
 				pstm.close();
 				pstm = null;
 			}

 			if (conn != null) 
 			{
 				conn.close();
 				conn = null;
 			}
 		} catch (SQLException e) 
 		{
 			throw new ITreasuryDAOException("���ݿ�ر��쳣����",e);			
 	    }
	}

	/**
	 *  �����ת��Ϊһ��DataEntity
	 * @throws SQLException 
	 */
	public VarietyInfo getDataEntityFromResultSet() throws SQLException
	{
		VarietyInfo info = new VarietyInfo();
		if(rs.next())
		{
			info.setSCode(rs.getString("sCode"));
			info.setSName(rs.getString("sName"));
			info.setNPredefined(rs.getLong("nPredefined"));
			info.setSRemark(rs.getString("sRemark"));
		}
		return info;
	}
	
	/**
	 *  ����ѯ���ת��ΪCollection��������ÿ��Ԫ�ض���һ��DataEntity
	 * @throws SQLException 
	 */
	public Collection getDataEntitiesFromResultSet() throws SQLException
	{
		Collection result = new ArrayList();
		while(rs.next())
		{
			VarietyInfo info = new VarietyInfo();
			info.setSCode(rs.getString("sCode"));
			info.setSName(rs.getString("sName"));
			info.setNPredefined(rs.getLong("nPredefined"));
			info.setSRemark(rs.getString("sRemark"));
			result.add(info);
		}
		return result;
	}
}
