/*
 * Created on 2003-10-22
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.loan.attach.dao;

import java.util.*;
import java.sql.*;

import com.iss.itreasury.util.*;
import com.iss.itreasury.archivesmanagement.dataentity.ArchivesSearchInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.loan.attach.dataentity.*;
import com.iss.system.dao.PageLoader;

/**
 * @author hyzeng 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AttachDao
{
	private static Log4j log4j = null;

	public AttachDao()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}

	/**
	* 得到文件信息
	* Create Date: 2003-10-15
	* @param lAttachTypeID 上传的文档类型
	* @param lParentID 文档所依附的主体的ID
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection query(long lAttachTypeID, long lParentID) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.id as docID,b.* ");
			sbSQL.append(" FROM loan_docInfo a,fileInfo b");
			sbSQL.append(" WHERE a.nFileID = b.id ");
			sbSQL.append(" AND a.nStatusID = " + Constant.RecordStatus.VALID);
		//	sbSQL.append(" AND a.archivestypeid = ?");
			sbSQL.append(" AND a.nParentTypeID = ?");
			sbSQL.append(" AND a.nParentID = ?");
		//	sbSQL.append(" AND a.clientid = ?");
			
			
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAttachTypeID);
			ps.setLong(2, lParentID);
			rs = ps.executeQuery();

			while (rs.next())
			{
				AttachInfo info = new AttachInfo();
				info.setID(rs.getLong("docID")); //文件ID(表loan_docInfo的ID)
				info.setFileID(rs.getLong("id")); //文件ID（表fileinfo的ID）
				info.setShowName(rs.getString("sClientFileName")); //在客户端的名称
				info.setRealName(rs.getString("sClientPath")); //在客户端的路径
				info.setMime(rs.getString("sFileMimeType")); //文件内容类型
				info.setContentType(rs.getString("sFileContentType")); //文件类型
				info.setServerPath(rs.getString("sServerPath")); //在服务器上的路径
				info.setParentID(lParentID);//所属对象的ID
                info.setInputUser(rs.getLong("ninputuserid"));
                info.setInputTime(rs.getTimestamp("dtinput"));
				vResult.add(info);
			}

			if (rs != null)
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
		finally
		{
			try
			{
				if (rs != null)
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
		return vResult.size() > 0 ? vResult : null;

	}
	
	/**
	* 得到文件信息
	* 修改档案查询的返回值类型为PageLoader 2008-11-26No.201增加 kaishao
	* @param lAttachTypeID 上传的文档类型
	* @param lParentID 文档所依附的主体的ID
	* @return PageLoader 文件信息
	* @exception Exception
	*/
	public PageLoader queryBySearchPage(ArchivesSearchInfo archivesSearchInfo) throws Exception
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
            con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.id as ID,a.clientid as ClientID,a.contractid as ContractID,a.payformid as PayFormID,a.archivestypeid as ArchivesTyepID,b.DTINPUT as inputTime,b.NINPUTUSERID as inputUser,b.SCLIENTFILENAME as ShowName,b.SCLIENTPATH as RealName,b.SSERVERPATH as ServerPath,a.NFILEID as FileID,c.scode as code");
			sbSQL.append(" FROM loan_docInfo a,fileInfo b,client c");
			sbSQL.append(" WHERE a.nFileID = b.id ");
			sbSQL.append(" AND a.officeid =  "+archivesSearchInfo.getOfficeID());
			sbSQL.append(" AND a.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" and a.clientid = c.id(+) ");
			if(archivesSearchInfo.getLClientIDStart()!=-1 ){
			sbSQL.append(" AND a.clientid = "+archivesSearchInfo.getLClientIDStart());
			}
			if(archivesSearchInfo.getLarchivesType()!=0 ){
				sbSQL.append(" AND a.archivestypeid = "+archivesSearchInfo.getLarchivesType());
				}
			if(archivesSearchInfo.getStrStartContractCode()!=-1 ){
				sbSQL.append(" AND a.contractid = "+archivesSearchInfo.getStrStartContractCode());
				}
			if(archivesSearchInfo.getStrStartLoanPayCode()!=-1 ){
				sbSQL.append(" AND a.payformid = "+archivesSearchInfo.getStrStartLoanPayCode());
				}			
			if(archivesSearchInfo.getInputTime()!=null ){
				sbSQL.append(" AND b.dtinput = to_timestamp('"+archivesSearchInfo.getInputTime()+"','yyyy-mm-dd hh24:mi:ssxff')");
				}			
			if(archivesSearchInfo.getRemark()!=null && ! archivesSearchInfo.getRemark().equals("")){ 
				sbSQL.append(" AND sClientFileName like '%"+archivesSearchInfo.getRemark()+"%'");
				}	
			if(archivesSearchInfo.getInputUserID()!=-1 ){
				sbSQL.append(" AND b.ninputuserid = "+archivesSearchInfo.getInputUserID());
				}	
			
			  
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
//			if(archivesSearchInfo.getLClientIDStart()!=-1 ){
//				ps.setLong(++index, archivesSearchInfo.getLClientIDStart());
//			}
//			if(archivesSearchInfo.getLarchivesType()!=0 ){
//				ps.setLong(++index, archivesSearchInfo.getLarchivesType());
//				}
//			if(archivesSearchInfo.getStrStartContractCode()!=-1 ){
//				ps.setLong(++index, archivesSearchInfo.getStrStartContractCode());
//				} 
//			if(archivesSearchInfo.getStrStartLoanPayCode()!=-1 ){
//				ps.setLong(++index, archivesSearchInfo.getStrStartLoanPayCode());
//				}
//			if(archivesSearchInfo.getInputTime()!=null ){ 
//				ps.setTimestamp(++index, archivesSearchInfo.getInputTime());
//			}
//			if(archivesSearchInfo.getInputUserID()!=-1 ){
//				ps.setLong(++index, archivesSearchInfo.getInputUserID());
//			}
			
			rs = ps.executeQuery();

			try{
				PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
				pageLoader.initPageLoader(new AppContext(), "("+sbSQL.toString()+")","*", "1=1", (int) Constant.PageControl.CODE_PAGELINECOUNT,
						"com.iss.itreasury.loan.attach.dataentity.AttachInfo", null);
				if (rs != null)
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
				return pageLoader;
			}catch(Exception ex)
			{
				throw new ITreasuryDAOException(ex.getMessage(), ex);
			}			
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
		{
			try
			{
				if (rs != null)
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
	}
	
	/**
	* 得到文件信息
	* Create Date: 2003-10-15
	* @param lAttachTypeID 上传的文档类型
	* @param lParentID 文档所依附的主体的ID
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection queryBySearch(ArchivesSearchInfo archivesSearchInfo) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
            int index=0;
			con = Database.getConnection();

			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.id as docID,a.clientid as aClientID,a.contractid as aContractID,a.payformid as aPayformID,a.archivestypeid as aArchivestypeID,b.* ");
			sbSQL.append(" FROM loan_docInfo a,fileInfo b");
			sbSQL.append(" WHERE a.nFileID = b.id ");
			sbSQL.append(" AND a.officeid =  "+archivesSearchInfo.getOfficeID());
			sbSQL.append(" AND a.nStatusID = " + Constant.RecordStatus.VALID);
			if(archivesSearchInfo.getLClientIDStart()!=-1 ){
			sbSQL.append(" AND a.clientid = ?");
			}
			if(archivesSearchInfo.getStrStartContractCode()!=-1 ){
				sbSQL.append(" AND a.contractid = ?");
				}
			if(archivesSearchInfo.getStrStartLoanPayCode()!=-1 ){
				sbSQL.append(" AND a.payformid = ?");
				}			
			if(archivesSearchInfo.getInputTime()!=null ){
				sbSQL.append(" AND dtinput = ?");
				}			
			if(archivesSearchInfo.getRemark()!=null && ! archivesSearchInfo.getRemark().equals("")){ 
				sbSQL.append(" AND sClientFileName like '%"+archivesSearchInfo.getRemark()+"%'");
				}	
			if(archivesSearchInfo.getInputUserID()!=-1 ){
				sbSQL.append(" AND ninputuserid = ?");
				}	
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			if(archivesSearchInfo.getLClientIDStart()!=-1 ){
			ps.setLong(++index, archivesSearchInfo.getLClientIDStart());
			}
		//	if(archivesSearchInfo.getLarchivesType()!=-1 ){
	//			ps.setLong(++index, archivesSearchInfo.getLarchivesType());
		//		}
			if(archivesSearchInfo.getStrStartContractCode()!=-1 ){
				ps.setLong(++index, archivesSearchInfo.getStrStartContractCode());
				} 
			if(archivesSearchInfo.getStrStartLoanPayCode()!=-1 ){
				ps.setLong(++index, archivesSearchInfo.getStrStartLoanPayCode());
				}
			if(archivesSearchInfo.getInputTime()!=null ){ 
				ps.setTimestamp(++index, archivesSearchInfo.getInputTime());
			}
			if(archivesSearchInfo.getInputUserID()!=-1 ){
				ps.setLong(++index, archivesSearchInfo.getInputUserID());
			}
			rs = ps.executeQuery();
			while (rs.next())
			{
				AttachInfo info = new AttachInfo();
				info.setID(rs.getLong("docID")); //文件ID(表loan_docInfo的ID)
				info.setFileID(rs.getLong("id")); //文件ID（表fileinfo的ID）
				info.setShowName(rs.getString("sClientFileName")); //在客户端的名称
				info.setRealName(rs.getString("sClientPath")); //在客户端的路径
				info.setMime(rs.getString("sFileMimeType")); //文件内容类型
				info.setContentType(rs.getString("sFileContentType")); //文件类型
				info.setServerPath(rs.getString("sServerPath")); //在服务器上的路径
				
				info.setClientID(rs.getLong("aClientID"));
				info.setArchivesTyepID(rs.getLong("aArchivestypeID"));
				info.setContractID(rs.getLong("aContractID"));
				info.setPayFormID(rs.getLong("aPayformID"));
				//info.setParentID(ClientID);//所属对象的ID
				
				info.setInputTime(rs.getTimestamp("dtinput"));
				info.setInputUser(rs.getLong("ninputuserid"));
                 
				vResult.add(info);
			}

			if (rs != null)
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
		finally
		{
			try
			{
				if (rs != null)
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
		return vResult.size() > 0 ? vResult : null;

	}
	/**
	* 得到文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param lAttachTypeID 上传的文档类型
	* @param lClientID 文档所依附的主体的ID
	* @return Collection 文件信息
	* @exception Exception
	*/
	public Collection queryclientcenter(long lAttachTypeID, long lClientID) throws Exception
	{
		Vector vResult = new Vector();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" SELECT a.id as docID,b.* ");
			sbSQL.append(" FROM CENTER_DOCINFO a,fileInfo b");
			sbSQL.append(" WHERE a.nFileID = b.id ");
			sbSQL.append(" AND a.nStatusID = " + Constant.RecordStatus.VALID);
			sbSQL.append(" AND a.nParentTypeID = ?");
			sbSQL.append(" AND a.nCLIENTID = ?");
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lAttachTypeID);
			ps.setLong(2, lClientID);
			rs = ps.executeQuery();
			while (rs.next())
			{
				AttachInfo info = new AttachInfo();
				info.setID(rs.getLong("docID")); //文件ID(表center_docInfo的ID)
				info.setFileID(rs.getLong("id")); //文件ID（表fileinfo的ID）
				info.setShowName(rs.getString("sClientFileName")); //在客户端的名称
				info.setRealName(rs.getString("sClientPath")); //在客户端的路径
				info.setMime(rs.getString("sFileMimeType")); //文件内容类型
				info.setContentType(rs.getString("sFileContentType")); //文件类型
				info.setServerPath(rs.getString("sServerPath")); //在服务器上的路径
				info.setParentID(lClientID);//所属对象的ID
				vResult.add(info);
			}
			if (rs != null)
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
		finally
		{
			try
			{
				if (rs != null)
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
		return vResult.size() > 0 ? vResult : null;
	}
	/**
	* 逻辑删除文件信息
	* Create Date: 2003-10-15
	* @param lID 文档ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long delete(long lID) throws Exception
	{
		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;
		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE loan_docInfo ");
			sbSQL.append(" SET nStatusID = " + Constant.RecordStatus.INVALID);
			sbSQL.append(" WHERE id = ? ");
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
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
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
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
	/**
	* 逻辑删除客户中心文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param lID 文档ID
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long deleteclientcenter(long lID) throws Exception
	{

		PreparedStatement ps = null;
		Connection con = null;
		long lResult = -1;

		try
		{
			con = Database.getConnection();
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append(" UPDATE center_docInfo ");
			sbSQL.append(" SET nStatusID = " + Constant.RecordStatus.INVALID);
			sbSQL.append(" WHERE id = ? ");
			log4j.info(sbSQL.toString());
			ps = con.prepareStatement(sbSQL.toString());
			ps.setLong(1, lID);
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
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
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
	/**
	* 增加文件信息
	* Create Date: 2003-10-15
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public void insert(AttachInfo info) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lID = -1;
		long lResult = -1;
		try
		{
			if (info != null)
			{
				con = Database.getConnection();
				StringBuffer sbSQL = new StringBuffer();
				sbSQL.append(" SELECT nvl(max(ID)+1,1) AS lID");
				sbSQL.append(" FROM loan_docInfo");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if ( rs.next() )
				{
					lID = rs.getLong("lID");
				}						
				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO loan_docInfo ");
				sbSQL.append(" (ID,nFileID,");
				sbSQL.append(" nTypeID,nParentTypeID,");
				sbSQL.append(" nParentID,nStatusID,ContractID,ClientID,PayformID,ArchivesTypeID,OFFICEID)");
				sbSQL.append(" VALUES ");
				sbSQL.append(" (?,?,?,?,?,?,?,?,?,?,?)"); 
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1,lID);
				ps.setLong(2,info.getFileID());
				ps.setLong(3,info.getType());
				ps.setLong(4,info.getParentType());
				ps.setLong(5,info.getParentID());
				ps.setLong(6,info.getStatus());		
				ps.setLong(7,info.getContractID());
				ps.setLong(8,info.getClientID()); 
				ps.setLong(9,info.getPayFormID());
				ps.setLong(10,info.getArchivesTyepID());
				ps.setLong(11,info.getOfficeID());
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
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
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
	}
	/**
	* 增加文件信息
	* Create Date: 2005-06-30 by weihuang
	* @param AttachInfo 文档信息
	* @return long 大于0表示成功，小于等于0表示失败
	* @exception Exception
	*/
	public long insertclientcenter(AttachInfo info) throws Exception
	{
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = null;
		long lID = -1;
		long lResult = -1;
		try
		{
			if (info != null)
			{
				con = Database.getConnection();

				StringBuffer sbSQL = new StringBuffer();
				
				sbSQL.append(" SELECT MAX(NVL(id,0)+1) AS lID");
				sbSQL.append(" FROM center_docInfo");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				rs = ps.executeQuery();
				if ( rs.next() )
				{
					lID = rs.getLong("lID");
				}						
				sbSQL.setLength(0);
				sbSQL.append(" INSERT INTO center_docInfo ");
				sbSQL.append(" (ID,nFileID,");
				sbSQL.append(" NFILETYPEID,nParentTypeID,");
				sbSQL.append(" NCLIENTID,nStatusID)");
				sbSQL.append(" VALUES ");
				sbSQL.append(" (?,?,?,?,?,?)");
				log4j.info(sbSQL.toString());
				ps = con.prepareStatement(sbSQL.toString());
				ps.setLong(1,lID);
				ps.setLong(2,info.getFileID());
				ps.setLong(3,info.getType());
				ps.setLong(4,info.getParentType());
				ps.setLong(5,info.getParentID());
				ps.setLong(6,info.getStatus());		
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
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		finally
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
