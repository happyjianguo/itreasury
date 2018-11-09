package com.iss.itreasury.settlement.assistant.dao;

import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.settlement.assistant.dataentity.AssistantInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLEntryInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;

import java.sql.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: aaa
 * Date: 2006-12-20
 * Time: 13:26:01
 */
public class AssistantDao extends SettlementDAO {
	StringBuffer m_sbSelect = null;
	StringBuffer m_sbFrom = null;
	StringBuffer m_sbWhere = null;
	StringBuffer m_sbOrderBy = null;

	public AssistantDao (){
		super();
	}
	public AssistantDao(Connection conn)
	{
		super(conn);
		this.strTableName = "GL_Assistant";
	}

	//新增保存
	public long add(AssistantInfo assistantInfo) throws Exception
	{
		long lReturn = -1;
		long assistantId = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			//效验分录ID是否在辅助核算表中存在
			assistantId = this.validateAssistant(assistantInfo.getGlentryID());
			
			if(assistantId > 0)
			{
				assistantInfo.setId(assistantId);
				lReturn = this.update(assistantInfo);
			}
			else
			{
				conn = getConnection();
				sbSQL = new StringBuffer();
				sbSQL.append(" insert into GL_Assistant values(?, ?, ?, ?, sysdate, ?, ?) ");
				ps = conn.prepareStatement(sbSQL.toString());
				int nIndex = 1;
				
				//ps.setLong(nIndex++, getMaxID());
				ps.setLong(nIndex++, getAssitantID());
				
				ps.setLong(nIndex++, assistantInfo.getGlentryID());
				ps.setString(nIndex++, assistantInfo.getAssitantName());
				ps.setString(nIndex++, assistantInfo.getAssitantValue());
				//ps.setTimestamp(nIndex++, assistantInfo.getModityDate());
				ps.setLong(nIndex++, assistantInfo.getModifyUserID());
				ps.setLong(nIndex++, 1);
				lReturn = ps.executeUpdate();
			}
			
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lReturn;
	}

	//删除辅助核算信息
	public long delete(long id){
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		
		sb.append(" update GL_Assistant set statusid = 0 where GlentryID = " + id);
		
		PreparedStatement ps = null;
		long lReturn = -1;
		
		try {
			 ps = conn.prepareStatement(sb.toString());
			 ps.executeQuery();
			 lReturn = 1;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				cleanup(ps);
				cleanup(conn);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return lReturn;
	}

	//修改保存
	public long update(AssistantInfo assistantInfo) {
		Connection conn = getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append(" update GL_Assistant set AssitantName  = '"+ assistantInfo.getAssitantName() +"', AssitantValue ='"+ assistantInfo.getAssitantValue() +"', ModityDate = sysdate, ModifyUserID = "+ assistantInfo.getModifyUserID() +"");
		sql.append(" where GlentryID = "+ assistantInfo.getId() +"");
		Statement ps = null;
		long lReturn = -1;
		try {
			ps = conn.createStatement();
			System.out.println(sql.toString());
			ps.execute(sql.toString());
			lReturn = 1;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				cleanup(ps);
				cleanup(conn);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return lReturn;
	}

	public AssistantInfo findAssistantByID(long id) {
		AssistantInfo assistantInfo = new AssistantInfo();
		Connection conn = getConnection();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from SETT_Glentry g where g.nstatusid > 0 and g.id = " + id);
		
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				assistantInfo.setGlentryID(rs.getLong("ID"));
				//assistantInfo.setAssitantName(rs.getString("AssitantName"));
				//assistantInfo.setAssitantValue(rs.getString("AssitantValue"));
				//assistantInfo.setModityDate(rs.getTimestamp("ModityDate"));
				//assistantInfo.setModifyUserID(rs.getLong("ModifyUserID"));
				assistantInfo.setTransNo(rs.getString("StransNO"));
				assistantInfo.setSubjectCode(rs.getString("SSUBJECTCODE"));
				assistantInfo.setTransActionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
				assistantInfo.setTransDircetion(rs.getLong("NTRANSDIRECTION"));
				assistantInfo.setIntertStart(rs.getTimestamp("DTINTERESTSTART"));
				assistantInfo.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
				assistantInfo.setAbstracts(rs.getString("SABSTRACT"));
				assistantInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				assistantInfo.setCheckUserID(rs.getLong("NCHECKUSERID"));
				assistantInfo.setAmount(rs.getDouble("MAMOUNT"));
				assistantInfo.setnPostStatusID(rs.getLong("nPostStatusID"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				cleanup(ps);
				cleanup(conn);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return assistantInfo;
	}
	
	public AssistantInfo findAssistantByID1(long id) {
		AssistantInfo assistantInfo = new AssistantInfo();
		Connection conn = getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from GL_Assistant a ,SETT_Glentry g where g.id = " + id);
		sql.append(" and a.GlentryID = g.id and a.statusid > 0 and g.nstatusid > 0 ");
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				assistantInfo.setGlentryID(rs.getLong("GlentryID"));
				assistantInfo.setAssitantName(rs.getString("AssitantName"));
				assistantInfo.setAssitantValue(rs.getString("AssitantValue"));
				//assistantInfo.setModityDate(rs.getTimestamp("ModityDate"));
				//assistantInfo.setModifyUserID(rs.getLong("ModifyUserID"));
				assistantInfo.setTransNo(rs.getString("StransNO"));
				assistantInfo.setSubjectCode(rs.getString("SSUBJECTCODE"));
				assistantInfo.setTransActionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
				assistantInfo.setTransDircetion(rs.getLong("NTRANSDIRECTION"));
				assistantInfo.setIntertStart(rs.getTimestamp("DTINTERESTSTART"));
				assistantInfo.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
				assistantInfo.setAbstracts(rs.getString("SABSTRACT"));
				assistantInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				assistantInfo.setCheckUserID(rs.getLong("NCHECKUSERID"));
				assistantInfo.setAmount(rs.getDouble("MAMOUNT"));
				assistantInfo.setnPostStatusID(rs.getLong("nPostStatusID"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				cleanup(ps);
				cleanup(conn);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return assistantInfo;
	}	

	public List findByProp(AssistantInfo queryProp){
		Connection conn = getConnection();
		List assistantInfos = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from  GL_Assistant a ,SETT_Glentry g where ");
		if(queryProp.getTransActionTypeID()!=-1)
			sql.append(" g.NTRANSACTIONTYPEID = ").append(queryProp.getTransActionTypeID()).append(" and ");
		if(queryProp.getExecuteDate()!=null&&queryProp.getExecuteDate().toString().length()>0)
			sql.append("g.dtexecute = to_date('").append(DataFormat.getDateString(queryProp.getExecuteDate())).append("','yyyy-mm-dd')").append(" and ");
		if(queryProp.getTransNo()!=null&&queryProp.getTransNo().length()>0)
			sql.append(" g.STRANSNO ='").append(queryProp.getTransNo()).append("' and ");
		if(queryProp.getSubjectCode()!=null&&queryProp.getSubjectCode().length()>0)
			sql.append("g.SSUBJECTCODE ='").append(queryProp.getSubjectCode()).append("' and");
		sql.append(" a.GlentryID = g.id");
		PreparedStatement ps = null;
		try{
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())	{
				AssistantInfo assistantInfo = new AssistantInfo();
				assistantInfo.setId(rs.getLong("ID"));
				assistantInfo.setGlentryID(rs.getLong("GlentryID"));
				assistantInfo.setAssitantName(rs.getString("AssitantName"));
				assistantInfo.setAssitantValue(rs.getString("AssitantValue"));
//				assistantInfo.setModityDate(rs.getTimestamp("ModityDate"));
//				assistantInfo.setModifyUserID(rs.getLong("ModifyUserID"));
				assistantInfo.setTransNo(rs.getString("StransNO"));
				assistantInfo.setSubjectCode(rs.getString("SSUBJECTCODE"));
				assistantInfo.setTransActionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
				assistantInfo.setTransDircetion(rs.getLong("NTRANSDIRECTION"));
				assistantInfo.setIntertStart(rs.getTimestamp("DTINTERESTSTART"));
				assistantInfo.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
				assistantInfo.setAbstracts(rs.getString("SABSTRACT"));
				assistantInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				assistantInfo.setCheckUserID(rs.getLong("NCHECKUSERID"));
				assistantInfo.setAmount(rs.getDouble("MAMOUNT"));
				assistantInfo.setnPostStatusID(rs.getLong("nPostStatusID"));
				assistantInfos.add(assistantInfo);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
			}
		}
		return assistantInfos;
	}

	public List findGlentryByProp(AssistantInfo queryProp){
		Connection conn = getConnection();
		List assistantInfos = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from  SETT_Glentry g where ");
		if(queryProp.getTransActionTypeID()!=-1)
			sql.append(" g.NTRANSACTIONTYPEID = ").append(queryProp.getTransActionTypeID()).append(" and ");
		if(queryProp.getExecuteDate()!=null&&queryProp.getExecuteDate().toString().length()>0)
			sql.append("g.dtexecute = to_date('").append(DataFormat.getDateString(queryProp.getExecuteDate())).append("','yyyy-mm-dd')").append(" and ");
		if(queryProp.getTransNo()!=null&&queryProp.getTransNo().length()>0)
			sql.append(" g.STRANSNO ='").append(queryProp.getTransNo()).append("' and ");
		if(queryProp.getSubjectCode()!=null&&queryProp.getSubjectCode().length()>0)
			sql.append("g.SSUBJECTCODE ='").append(queryProp.getSubjectCode()).append(" and ");
		if(queryProp.getGlentryID()!=-1)
			sql.append(" g.id = ").append(queryProp.getGlentryID()).append(" and  g.npoststatusid >=1").append(" and ");
		sql.append(" g.id not in (select a.id from GL_Assistant a ) ");
		PreparedStatement ps = null;
		try{
			System.out.println(sql.toString());
			ps = conn.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())	{
				AssistantInfo assistantInfo = new AssistantInfo();
				assistantInfo.setGlentryID(rs.getLong("ID"));
				assistantInfo.setTransNo(rs.getString("STRANSNO"));
				assistantInfo.setSubjectCode(rs.getString("SSUBJECTCODE"));
				assistantInfo.setTransActionTypeID(rs.getLong("NTRANSACTIONTYPEID"));
				assistantInfo.setTransDircetion(rs.getLong("NTRANSDIRECTION"));
				assistantInfo.setIntertStart(rs.getTimestamp("DTINTERESTSTART"));
				assistantInfo.setExecuteDate(rs.getTimestamp("DTEXECUTE"));
				assistantInfo.setAbstracts(rs.getString("SABSTRACT"));
				assistantInfo.setInputUserID(rs.getLong("NINPUTUSERID"));
				assistantInfo.setCheckUserID(rs.getLong("NCHECKUSERID"));
				assistantInfo.setAmount(rs.getDouble("MAMOUNT"));
				assistantInfo.setnPostStatusID(rs.getLong("nPostStatusID"));
				assistantInfos.add(assistantInfo);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
			}
		}
		return assistantInfos;
	}


	public List findAllAssistant(){
		List assistants = new ArrayList();
		Connection conn = getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from  GL_Assistant ");
		PreparedStatement ps = null;
		try{
			ResultSet rs = ps.executeQuery();
			while (rs.next())	{
				AssistantInfo assistantInfo = new AssistantInfo();
				assistantInfo.setGlentryID(rs.getLong("GlentryID"));
				assistantInfo.setAssitantName(rs.getString("AssitantName"));
				assistantInfo.setAssitantValue(rs.getString("AssitantValue"));
				assistantInfo.setModityDate(rs.getTimestamp("ModityDate"));
				assistantInfo.setModifyUserID(rs.getLong("ModifyUserID"));
				assistants.add(assistantInfo);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				cleanup(ps);
				cleanup(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return assistants;
	}

	private long getMaxID() throws Exception {
		long id = -1;
		Connection conn = getConnection();
		StringBuffer sb = new StringBuffer();
		sb.append("select max(ID)from GL_Assistant");
		PreparedStatement ps = conn.prepareStatement(sb.toString());
		ResultSet rs = ps.executeQuery();
		if (rs.next())	{
			id = rs.getLong("max(ID)") + 1;
		}
		else id = 1;
		cleanup(rs);
		cleanup(ps);
		cleanup(conn);
		return id;
	}

	public void getTransactionSQL(AssistantInfo info)
	{
		m_sbSelect = new StringBuffer();
		m_sbFrom = new StringBuffer();
		m_sbWhere = new StringBuffer();
		m_sbOrderBy = new StringBuffer();
		
		//查询条件为"修改"
		if(info.getQueryType().equals("update")) {
			m_sbFrom.append(" GL_Assistant a ,SETT_Glentry g ");
			m_sbSelect.append(" a.AssitantName,a.AssitantValue,");
			m_sbSelect.append(" g.id id,g.nPostStatusID nPostStatusID,g.StransNO TransNo,g.NTRANSACTIONTYPEID transActionTypeID ,g.SSUBJECTCODE subjectCode,g.NTRANSDIRECTION transDircetion,");
			m_sbSelect.append(" g.DTINTERESTSTART intertStart,g.DTEXECUTE executeDate,g.SABSTRACT abstracts,g.NINPUTUSERID inputUserID,g.NCHECKUSERID checkUserID,g.MAMOUNT amount");
			
			m_sbWhere.append("  g.NSTATUSID > 0 ");
			
			if(info.getTransActionTypeID() != -1) {
				m_sbWhere.append(" and g.NTRANSACTIONTYPEID = "+ info.getTransActionTypeID() +" ");
			}
			
			if(info.getExecuteDate() != null && info.getExecuteDate().toString().length() > 0) {
				m_sbWhere.append(" and g.dtexecute =  to_date('"+ DataFormat.getDateString( info.getExecuteDate() ) +"', 'yyyy-mm-dd') ");
			}
			
			if(info.getTransNo() != null && info.getTransNo().length() > 0) {
				m_sbWhere.append(" and g.STRANSNO ='"+ info.getTransNo() +"' ");
			}
			
			if(info.getSubjectCode() != null && info.getSubjectCode().length() > 0) {
				m_sbWhere.append(" and g.SSUBJECTCODE ='"+ info.getSubjectCode() +"' ");
			}
			
			m_sbWhere.append(" and a.STATUSID > 0 ");
			m_sbWhere.append(" and a.GlentryID = g.id ");
			
			m_sbOrderBy.append(" order by g.StransNO ");
			
			System.out.println("SQL== select"+ m_sbSelect.toString() +" from "+ m_sbFrom.toString() +" where "+ m_sbWhere.toString() +"");
		}
		
		//查询条件为"新增"
		if(info.getQueryType().equals("add")) {
			m_sbFrom.append(" SETT_Glentry g ");
			m_sbSelect.append(" g.id id, g.nPostStatusID nPostStatusID, g.StransNO TransNo, g.NTRANSACTIONTYPEID transActionTypeID, g.SSUBJECTCODE subjectCode, g.NTRANSDIRECTION transDircetion ");
			m_sbSelect.append(" , g.DTINTERESTSTART intertStart, g.DTEXECUTE executeDate, g.SABSTRACT abstracts, g.NINPUTUSERID inputUserID, g.NCHECKUSERID checkUserID, g.MAMOUNT amount ");
			
			m_sbWhere.append(" g.NSTATUSID > 0 ");
			
			if(info.getTransActionTypeID() != -1) {
				m_sbWhere.append(" and g.NTRANSACTIONTYPEID = "+ info.getTransActionTypeID() +" ");
			}
				
			if(info.getExecuteDate() != null && info.getExecuteDate().toString().length() > 0) {
				m_sbWhere.append(" and g.dtexecute = to_date('"+ DataFormat.getDateString(info.getExecuteDate()) +"', 'yyyy-mm-dd') ");
			}
				
			if(info.getTransNo() != null && info.getTransNo().length() > 0) {
				m_sbWhere.append(" and g.STRANSNO ='"+ info.getTransNo()+"' ");
			}
			
			if(info.getSubjectCode() != null && info.getSubjectCode().length() > 0) {
				m_sbWhere.append(" and g.SSUBJECTCODE ='"+ info.getSubjectCode() +"' ");
			}
			
			m_sbWhere.append(" and NOT EXISTS (select ga.GLENTRYID from GL_Assistant ga where g.id = ga.glentryid and ga.statusid > 0) ");
			
			m_sbWhere.append(" and NOT EXISTS (select sb.ssubjectcode subjectcode from SETT_BRANCH sb where sb.ssubjectcode = g.SSUBJECTCODE and sb.nstatusid > 0) ");
			
			m_sbOrderBy.append(" order by g.StransNO ");
			
			System.out.println("SQL == select"+ m_sbSelect.toString() +" from "+ m_sbFrom.toString() +" where "+ m_sbWhere.toString() +"");
		}
	}
	
	public PageLoader queryTransactionInfo(AssistantInfo info) throws Exception
	{
		getTransactionSQL(info);
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		pageLoader.initPageLoader(
				new AppContext(),
				m_sbFrom.toString(),
				m_sbSelect.toString(),
				m_sbWhere.toString(),
				(int) Constant.PageControl.CODE_PAGELINECOUNT,
				"com.iss.itreasury.settlement.assistant.dataentity.AssistantInfo",
				null);
		pageLoader.setOrderBy(" " + m_sbOrderBy.toString() + " ");
		return pageLoader;
	}
	
	//保存数据前的效验
	public long validateAssistant(long glentryId) throws Exception
	{
		long lReturnId = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		StringBuffer sbSQL = null;
		try
		{
			conn = getConnection();
			sbSQL = new StringBuffer();
			
			sbSQL.append(" select id from GL_Assistant where statusid = 1 and glentryid = " + glentryId);
			
			ps = conn.prepareStatement(sbSQL.toString());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				lReturnId = rs.getLong("id");
			}
			
			cleanup(ps);
			cleanup(conn);
		}
		catch (Exception exp)
		{
			throw exp;
		}
		finally
		{
			cleanup(ps);
			cleanup(conn);
		}
		
		return lReturnId;
	}
	
	//删除辅助核算信息
	public long cancelCheckDelete(Collection coll) throws SQLException {
		GLEntryInfo glEntryInfo = null;
		long lReturn = -1; 
		
		if(coll != null && coll.size() > 0)
		{
			Iterator it = coll.iterator();
			while(it.hasNext()) {
				glEntryInfo = (GLEntryInfo)it.next();
				lReturn = this.delete(glEntryInfo.getID());
			}
		}
		
		return lReturn;
	}
}
