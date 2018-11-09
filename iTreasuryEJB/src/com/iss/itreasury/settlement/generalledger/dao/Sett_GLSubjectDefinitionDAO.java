/*
 * Created on 2003-9-12
 *
 */
package com.iss.itreasury.settlement.generalledger.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.iss.itreasury.dataentity.UpLoanReturnInfo;
import com.iss.itreasury.settlement.generalledger.dataentity.GLSubjectDefinitionInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.Log;

/**
 * 
 * ��Ŀ�ܱ�DAO
 * @author yqwu
 *
 */
public class Sett_GLSubjectDefinitionDAO extends AbstractGeneralLedgerDAO
{
    private static final String TABLE_NAME = "sett_GLSubjectDefinition";
    private static Logger logger =
        Logger.getLogger(Sett_GLSubjectDefinitionDAO.class);

    public Sett_GLSubjectDefinitionDAO()
    {
        this.tableName = TABLE_NAME;
    }
    
	public Sett_GLSubjectDefinitionDAO(Connection conn)
	 {
	 	super(conn);
		 this.tableName = TABLE_NAME;
	 }    

    /**
     * ������Ŀ�ܱ��¼
     * @param info Sett_GLSubjectDefinition
     * @return ��Ŀ�ܱ��¼ID
     * @throws SQLException
     */
    public long add(GLSubjectDefinitionInfo info) throws SQLException
    {

        long lReturn = -1;
        long id = this.getNextID();
        info.setID(id);

        Connection conn = null;
        PreparedStatement pstmt = null;
        StringBuffer buffer = new StringBuffer(64);
        buffer.append("insert into sett_GLSubjectDefinition \n");
        buffer.append("(ID,\n");
        buffer.append("nOfficeID,\n");
        buffer.append("nCurrencyID,\n");
        buffer.append("sSegmentCode1,\n");
        buffer.append("sSegmentCode2,\n");
        buffer.append("sSegmentCode3,\n");
        buffer.append("sSegmentCode4,\n");
        buffer.append("sSegmentCode5,\n");
		buffer.append("sSegmentCode6,\n");
		buffer.append("sSegmentCode7,\n");
        buffer.append("sSegmentName1,\n");
        //buffer.append("sSubjectName,\n");
        buffer.append("sSegmentName2,\n");
        buffer.append("sSegmentName3,\n");
        buffer.append("sSegmentName4,\n");
        buffer.append("nSubjectType,\n");
        buffer.append("nIsleaf,\n");
        buffer.append("nIsRoot,\n");
        buffer.append("nParentSubjectID,\n");
        buffer.append("nBalanceDirection,\n");
        buffer.append("nAmountDirection,\n");
        buffer.append("nStatus,\n");
        buffer.append("nLederType,\n");
        buffer.append("nSecurityLevel,\n");
        buffer.append("nUseScope,\n");
        buffer.append("nFlag,\n");
        buffer.append("dtValidDate) \n");
        buffer.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

        try
        {
            conn = this.getConnection();

            int nIndex = 1;

            pstmt = conn.prepareStatement(buffer.toString());

            pstmt.setLong(nIndex++, info.getID());
            pstmt.setLong(nIndex++, info.getOfficeID());
            pstmt.setLong(nIndex++, info.getCurrencyID());
            pstmt.setString(nIndex++, info.getSegmentCode1());
            pstmt.setString(nIndex++, info.getSegmentCode2());
            pstmt.setString(nIndex++, info.getSegmentCode3());
            pstmt.setString(nIndex++, info.getSegmentCode4());
            pstmt.setString(nIndex++, info.getSegmentCode5());//add by xuteng
			pstmt.setString(nIndex++, info.getSegmentCode6());
			pstmt.setString(nIndex++, info.getSegmentCode7());
            pstmt.setString(nIndex++, info.getSegmentName1());
            //pstmt.setString(nIndex++, info.getSubjectName());
            pstmt.setString(nIndex++, info.getSegmentName2());
            pstmt.setString(nIndex++, info.getSegmentName3());
            pstmt.setString(nIndex++, info.getSegmentName4());
            pstmt.setLong(nIndex++, info.getSubjectType());
            pstmt.setLong(nIndex++, info.isLeaf() ? 1 : 0);
            pstmt.setLong(nIndex++, info.isRoot() ? 1 : 0);
            pstmt.setLong(nIndex++, info.getParentSubjectID());
            pstmt.setLong(nIndex++, info.getBalanceDirection());
            pstmt.setLong(nIndex++, info.getAmountDirection());
            pstmt.setLong(nIndex++, info.getStatusID());
            pstmt.setLong(nIndex++, info.getLederType());
            pstmt.setLong(nIndex++, info.getSecurityLevel());
            pstmt.setLong(nIndex++, info.getUseScope());
            pstmt.setLong(nIndex++, info.getFlag());
            pstmt.setTimestamp(nIndex++, info.getValidDate());

            pstmt.execute();

            lReturn = id;

        }
        catch (SQLException sqle)
        {
            logger.error("������Ŀ�ܱ��¼ʱSQL�쳣", sqle);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {

                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (SQLException e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
                //e.printStackTrace();throw new SQLException("Gen _E001");
                throw e;
            }
        }

        return lReturn;
    }

    /**
     * �޸Ŀ�Ŀ�ܱ��¼
     * @param info GLSubjectDefineInfo
     * @return ��Ŀ�ܱ��¼ID
     * @throws SQLException
     */
    public long update(GLSubjectDefinitionInfo info) throws SQLException
    {

        long lReturn = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append("update SETT_GLSUBJECTDEFINITION set \n");
        buffer.append("nOfficeID=?,\n");
        buffer.append("nCurrencyID=?,\n");
        buffer.append("sSegmentCode1=?,\n");
        buffer.append("sSegmentCode2=?,\n");
        buffer.append("sSegmentCode3=?,\n");
        buffer.append("sSegmentCode4=?,\n");
        buffer.append("sSegmentCode5=?,\n");//add by xuteng
		buffer.append("sSegmentCode6=?,\n");
		buffer.append("sSegmentCode7=?,\n");
        buffer.append("sSegmentName1=?,\n");
        //buffer.append("sSubjectName=?,\n");
        buffer.append("sSegmentName2=?,\n");
        buffer.append("sSegmentName3=?,\n");
        buffer.append("sSegmentName4=?,\n");
        buffer.append("nSubjectType=?,\n");
        buffer.append("nIsleaf=?,\n");
        buffer.append("nIsRoot=?,\n");
        buffer.append("nParentSubjectID=?,\n");
        buffer.append("nBalanceDirection=?,\n");
        buffer.append("nAmountDirection=?,\n");
        buffer.append("nStatus=?,\n");
        buffer.append("nLederType=?,\n");
        buffer.append("nSecurityLevel=?,\n");
        buffer.append("nUseScope=?,\n");
        buffer.append("nFlag=?,\n");
        buffer.append("dtValidDate=? \n");
        buffer.append("where id=?");

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
            int nIndex = 1;

            pstmt.setLong(nIndex++, info.getOfficeID());
            pstmt.setLong(nIndex++, info.getCurrencyID());
            pstmt.setString(nIndex++, info.getSegmentCode1());
            pstmt.setString(nIndex++, info.getSegmentCode2());
            pstmt.setString(nIndex++, info.getSegmentCode3());
            pstmt.setString(nIndex++, info.getSegmentCode4());
            pstmt.setString(nIndex++, info.getSegmentCode5());
			pstmt.setString(nIndex++, info.getSegmentCode6());
			pstmt.setString(nIndex++, info.getSegmentCode7());//add by xuteng
            pstmt.setString(nIndex++, info.getSegmentName1());
            //            pstmt.setString(nIndex++, info.getSubjectName());
            pstmt.setString(nIndex++, info.getSegmentName2());
            pstmt.setString(nIndex++, info.getSegmentName3());
            pstmt.setString(nIndex++, info.getSegmentName4());
            pstmt.setLong(nIndex++, info.getSubjectType());
            pstmt.setLong(nIndex++, info.isLeaf() ? 1 : 0);
            pstmt.setLong(nIndex++, info.isRoot() ? 1 : 0);
            pstmt.setLong(nIndex++, info.getParentSubjectID());
            pstmt.setLong(nIndex++, info.getBalanceDirection());
            pstmt.setLong(nIndex++, info.getAmountDirection());
            pstmt.setLong(nIndex++, info.getStatusID());
            pstmt.setLong(nIndex++, info.getLederType());
            pstmt.setLong(nIndex++, info.getSecurityLevel());
            pstmt.setLong(nIndex++, info.getUseScope());
            pstmt.setLong(nIndex++, info.getFlag());
            pstmt.setTimestamp(nIndex++, info.getValidDate());
            pstmt.setLong(nIndex++, info.getID());

            pstmt.execute();

            lReturn = info.getID();

        }
        catch (SQLException sqle)
        {
            logger.error("�޸Ŀ�Ŀ�ܱ��¼ʱSQL�쳣", sqle);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {

                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (SQLException e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
                //e.printStackTrace();throw new SQLException("Gen _E001");
                throw e;
            }
        }

        return lReturn;
    }

    /**
     * ͨ����ʶ��ѯ
     * @param id long
     * @return ��Ŀ�ܱ��¼
     * @throws SQLException
     */
    public GLSubjectDefinitionInfo findByID(long id) throws SQLException
    {
        GLSubjectDefinitionInfo info = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append("select \n");
        buffer.append("nOfficeID,\n");
        buffer.append("nCurrencyID,\n");
        buffer.append("sSegmentCode1,\n");
        buffer.append("sSegmentCode2,\n");
        buffer.append("sSegmentCode3,\n");
        buffer.append("sSegmentCode4,\n");
        buffer.append("sSegmentCode5,\n");
		buffer.append("sSegmentCode6,\n");
		buffer.append("sSegmentCode7,\n");
        buffer.append("sSegmentName1,\n");
        //buffer.append("sSubjectName,\n");
        buffer.append("sSegmentName2,\n");
        buffer.append("sSegmentName3,\n");
        buffer.append("sSegmentName4,\n");
        buffer.append("nSubjectType,\n");
        buffer.append("nIsleaf,\n");
        buffer.append("nIsRoot,\n");
        buffer.append("nParentSubjectID,\n");
        buffer.append("nBalanceDirection,\n");
        buffer.append("nAmountDirection,\n");
        buffer.append("nStatus,\n");
        buffer.append("nLederType,\n");
        buffer.append("nSecurityLevel,\n");
        buffer.append("nUseScope,\n");
        buffer.append("nFlag,\n");
        buffer.append("dtValidDate \n");
        buffer.append("from SETT_VGLSUBJECTDEFINITION where id=?");

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
            pstmt.setLong(1, id);
            rset = pstmt.executeQuery();

            boolean hasResult = rset.next();

            if (hasResult)
            {
                info = new GLSubjectDefinitionInfo();
                info.setID(id);
                info.setOfficeID(rset.getLong("nOfficeID"));
                info.setCurrencyID(rset.getLong("nCurrencyID"));
                info.setSegmentCode1(rset.getString("sSegmentCode1"));
                info.setSegmentCode2(rset.getString("sSegmentCode2"));
                info.setSegmentCode3(rset.getString("sSegmentCode3"));
                info.setSegmentCode4(rset.getString("sSegmentCode4"));
                info.setSegmentCode5(rset.getString("sSegmentCode5"));
				info.setSegmentCode6(rset.getString("sSegmentCode6"));
				info.setSegmentCode7(rset.getString("sSegmentCode7"));
                info.setSegmentName1(rset.getString("sSegmentName1"));
                //info.setSubjectName(rset.getString("sSubjectName"));
                info.setSegmentName2(rset.getString("sSegmentName2"));
                info.setSegmentName3(rset.getString("sSegmentName3"));
                info.setSegmentName4(rset.getString("sSegmentName4"));
                info.setSubjectType(rset.getLong("nSubjectType"));
                info.setLeaf(rset.getLong("nIsleaf") == 1 ? true : false);
                info.setRoot(rset.getLong("nIsRoot") == 1 ? true : false);
                info.setParentSubjectID(rset.getLong("nParentSubjectID"));
                info.setBalanceDirection(rset.getLong("nBalanceDirection"));
                info.setAmountDirection(rset.getLong("nAmountDirection"));
                info.setStatusID(rset.getLong("nStatus"));
                info.setLederType(rset.getLong("nLederType"));
                info.setSecurityLevel(rset.getLong("nSecurityLevel"));
                info.setUseScope(rset.getLong("nUseScope"));
                info.setFlag(rset.getLong("nFlag"));
                info.setValidDate(rset.getTimestamp("dtValidDate"));
            }
        }
        catch (SQLException sqle)
        {
            logger.error("ͨ����ʶ��ѯʱSQL�쳣", sqle);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {

                this.cleanup(rset);
                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (SQLException e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
                //e.printStackTrace();throw new SQLException("Gen _E001");
                throw e;
            }
        }

        return info;
    }

    /**
     * ͨ�������ѯ
     * @param subjectCode String
     * @return GLSubjectDefinitionInfo
     * @throws SQLException
     */
    public GLSubjectDefinitionInfo findByOldCode(String subjectCode)
        throws SQLException
    {
        GLSubjectDefinitionInfo info = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append("select \n");
        buffer.append("ID,\n");
        buffer.append("nOfficeID,\n");
        buffer.append("nCurrencyID,\n");
        buffer.append("sSegmentCode1,\n");
        buffer.append("sSegmentCode2,\n");
        buffer.append("sSegmentCode3,\n");
        buffer.append("sSegmentCode4,\n");
        buffer.append("sSegmentName1,\n");
        buffer.append("sSegmentName2,\n");
        buffer.append("sSegmentName3,\n");
        buffer.append("sSegmentName4,\n");
        buffer.append("nSubjectType,\n");
        buffer.append("nIsleaf,\n");
        buffer.append("nIsRoot,\n");
        buffer.append("nParentSubjectID,\n");
        buffer.append("nBalanceDirection,\n");
        buffer.append("nAmountDirection,\n");
        buffer.append("nStatus,\n");
        buffer.append("nLederType,\n");
        buffer.append("nSecurityLevel,\n");
        buffer.append("nUseScope,\n");
        buffer.append("nFlag,\n");
        buffer.append("dtValidDate \n");
        buffer.append(
            "from SETT_VGLSUBJECTDEFINITION\n");
		buffer.append(" where SSUBJECTCODE=? AND nStatus = 1");

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
            pstmt.setString(1, subjectCode);
            rset = pstmt.executeQuery();

            boolean hasResult = rset.next();

            if (hasResult)
            {
                info = new GLSubjectDefinitionInfo();
                info.setID(rset.getLong("ID"));
                info.setOfficeID(rset.getLong("nOfficeID"));
                info.setCurrencyID(rset.getLong("nCurrencyID"));
                info.setSegmentCode1(rset.getString("sSegmentCode1"));
                info.setSegmentCode2(rset.getString("sSegmentCode2"));
                info.setSegmentCode3(rset.getString("sSegmentCode3"));
                info.setSegmentCode4(rset.getString("sSegmentCode4"));
                info.setSegmentName1(rset.getString("sSegmentName1"));
                info.setSegmentName2(rset.getString("sSegmentName2"));
                info.setSegmentName3(rset.getString("sSegmentName3"));
                info.setSegmentName4(rset.getString("sSegmentName4"));
                info.setSubjectType(rset.getLong("nSubjectType"));
                info.setLeaf(rset.getLong("nIsleaf") == 1 ? true : false);
                info.setRoot(rset.getLong("nIsRoot") == 1 ? true : false);
                info.setParentSubjectID(rset.getLong("nParentSubjectID"));
                info.setBalanceDirection(rset.getLong("nBalanceDirection"));
                info.setAmountDirection(rset.getLong("nAmountDirection"));
                info.setStatusID(rset.getLong("nStatus"));
                info.setLederType(rset.getLong("nLederType"));
                info.setSecurityLevel(rset.getLong("nSecurityLevel"));
                info.setUseScope(rset.getLong("nUseScope"));
                info.setFlag(rset.getLong("nFlag"));
                info.setValidDate(rset.getTimestamp("dtValidDate"));
            }
        }
        catch (SQLException sqle)
        {
            logger.error("ͨ�������ѯʱSQL�쳣", sqle);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {

                this.cleanup(rset);
                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (SQLException e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
                //e.printStackTrace();throw new SQLException("Gen _E001");
                throw e;
            }
        }

        return info;
    }

    /**
     * ͨ�������ѯ
     * @param subjectCode String
     * @return GLSubjectDefinitionInfo
     * @throws SQLException
     */
    public GLSubjectDefinitionInfo findByCode(String subjectCode)
        throws SQLException
    {
        GLSubjectDefinitionInfo info = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append("select \n");
        buffer.append("ID,\n");
        buffer.append("nOfficeID,\n");
        buffer.append("nCurrencyID,\n");
        buffer.append("sSegmentCode1,\n");
        buffer.append("sSegmentCode2,\n");
        buffer.append("sSegmentCode3,\n");
        buffer.append("sSegmentCode4,\n");
        buffer.append("sSegmentName1,\n");
        buffer.append("sSegmentName2,\n");
        buffer.append("sSegmentName3,\n");
        buffer.append("sSegmentName4,\n");
        buffer.append("nSubjectType,\n");
        buffer.append("nIsleaf,\n");
        buffer.append("nIsRoot,\n");
        buffer.append("nParentSubjectID,\n");
        buffer.append("nBalanceDirection,\n");
        buffer.append("nAmountDirection,\n");
        buffer.append("nStatus,\n");
        buffer.append("nLederType,\n");
        buffer.append("nSecurityLevel,\n");
        buffer.append("nUseScope,\n");
        buffer.append("nFlag,\n");
        buffer.append("dtValidDate \n");
        
        buffer.append("SROOTSUBJECT,\n");
        buffer.append("SROOTSUBJECTNAME,\n");
        buffer.append("SSUBJECTCODE,\n");
        buffer.append("SSUBJECTNAME \n");
        
        buffer.append("from SETT_VGLSUBJECTDEFINITION where SSUBJECTCODE=?");

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());
            pstmt.setString(1, subjectCode);
            rset = pstmt.executeQuery();

            boolean hasResult = rset.next();

            if (hasResult)
            {
                info = new GLSubjectDefinitionInfo();
                info.setID(rset.getLong("ID"));
                info.setOfficeID(rset.getLong("nOfficeID"));
                info.setCurrencyID(rset.getLong("nCurrencyID"));
                info.setSegmentCode1(rset.getString("sSegmentCode1"));
                info.setSegmentCode2(rset.getString("sSegmentCode2"));
                info.setSegmentCode3(rset.getString("sSegmentCode3"));
                info.setSegmentCode4(rset.getString("sSegmentCode4"));
                info.setSegmentName1(rset.getString("sSegmentName1"));
                info.setSegmentName2(rset.getString("sSegmentName2"));
                info.setSegmentName3(rset.getString("sSegmentName3"));
                info.setSegmentName4(rset.getString("sSegmentName4"));
                info.setSubjectType(rset.getLong("nSubjectType"));
                info.setLeaf(rset.getLong("nIsleaf") == 1 ? true : false);
                info.setRoot(rset.getLong("nIsRoot") == 1 ? true : false);
                info.setParentSubjectID(rset.getLong("nParentSubjectID"));
                info.setBalanceDirection(rset.getLong("nBalanceDirection"));
                info.setAmountDirection(rset.getLong("nAmountDirection"));
                info.setStatusID(rset.getLong("nStatus"));
                info.setLederType(rset.getLong("nLederType"));
                info.setSecurityLevel(rset.getLong("nSecurityLevel"));
                info.setUseScope(rset.getLong("nUseScope"));
                info.setFlag(rset.getLong("nFlag"));
                
                //info.setValidDate(rset.getTimestamp("dtValidDate"));
                
                info.setSrootSubJect(rset.getString("SROOTSUBJECT"));
                info.setSrootSubJectName(rset.getString("SROOTSUBJECTNAME"));
                info.setSsubjectCode(rset.getString("SSUBJECTCODE"));
                info.setSsubjectName(rset.getString("SSUBJECTNAME"));
            }
        }
        catch (SQLException sqle)
        {
            logger.error("ͨ�������ѯʱSQL�쳣", sqle);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {

                this.cleanup(rset);
                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (SQLException e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
                //e.printStackTrace();throw new SQLException("Gen _E001");
                throw e;
            }
        }

        return info;
    }
    //add by xuteng
    /**
	 * ����������ѯ��Ŀ������Ϣ
	 * 
	 * @param condition
	 * @return
	 * @throws SQLException
	 */
	public Collection findByCondition(GLSubjectDefinitionInfo condition)
			throws SQLException {
		Collection coll = new ArrayList();
		GLSubjectDefinitionInfo info = null;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		StringBuffer buffer = new StringBuffer(64);
		buffer.append("select \n");
		buffer.append("ID,\n");
		buffer.append("nOfficeID,\n");
		buffer.append("nCurrencyID,\n");
		buffer.append("sSegmentCode1,\n");
		buffer.append("sSegmentCode2,\n");
		buffer.append("sSegmentCode3,\n");
		buffer.append("sSegmentCode4,\n");
		buffer.append("sSegmentCode5,\n");
		buffer.append("sSegmentCode6,\n");
		buffer.append("sSegmentCode7,\n");
		buffer.append("sSegmentName1,\n");
		buffer.append("sSegmentName2,\n");
		buffer.append("sSegmentName3,\n");
		buffer.append("sSegmentName4,\n");
		buffer.append("nSubjectType,\n");
		buffer.append("nIsleaf,\n");
		buffer.append("nIsRoot,\n");
		buffer.append("nParentSubjectID,\n");
		buffer.append("nBalanceDirection,\n");
		buffer.append("nAmountDirection,\n");
		buffer.append("nStatus,\n");
		buffer.append("nLederType,\n");
		buffer.append("nSecurityLevel,\n");
		buffer.append("nUseScope,\n");
		buffer.append("nFlag,\n");
		buffer.append("dtValidDate \n");
		buffer.append("from SETT_VGLSUBJECTDEFINITION where 1=1");

		
		
		
		// ���´�
		if (condition.getOfficeID() > 0) {
			buffer.append(" and nOfficeID = ?");
		}
		// ����
		if (condition.getCurrencyID() > 0) {
			buffer.append(" and nCurrencyID = ?");
		}

//		// ��Ŀ����
//		if (condition.getSegmentCode2() != null
//				&& !condition.getSegmentCode2().equals("")) {
//			buffer.append(" and SSUBJECTCODE like ?");
//		}
		
		//��Ŀ���� ��һ��
		if (condition.getSegmentCode1() != null
				&& !condition.getSegmentCode1().equals("")) {
			buffer.append(" and SSEGMENTCODE1 like ?");
		}
		
		//��Ŀ���� �ڶ���
		if (condition.getSegmentCode2() != null
				&& !condition.getSegmentCode2().equals("")) {
			buffer.append(" and SSEGMENTCODE2 like ?");
		}

		//��Ŀ���� ������
		if (condition.getSegmentCode3() != null
				&& !condition.getSegmentCode3().equals("")) {
			buffer.append(" and SSEGMENTCODE3 like ?");
		}

		//��Ŀ���� ���Ķ�
		if (condition.getSegmentCode4() != null
				&& !condition.getSegmentCode4().equals("")) {
			buffer.append(" and SSEGMENTCODE4 like ?");
		}

		//��Ŀ���� �����
		if (condition.getSegmentCode5() != null
				&& !condition.getSegmentCode5().equals("")) {
			buffer.append(" and SSEGMENTCODE5 like ?");
		}

		//��Ŀ���� ������
		if (condition.getSegmentCode6() != null
				&& !condition.getSegmentCode6().equals("")) {
			buffer.append(" and SSEGMENTCODE6 like ?");
		}

		//��Ŀ���� ���߶�
		if (condition.getSegmentCode7() != null
				&& !condition.getSegmentCode7().equals("")) {
			buffer.append(" and SSEGMENTCODE7 like ?");
		}

		// ��Ŀ����
		if (condition.getSegmentName2() != null
				&& !condition.getSegmentName2().equals("")) {
			buffer.append(" and sSubjectName like ?");
		}

		// ��Ŀ����ID
		if (condition.getSubjectType() > 0) {
			buffer.append(" and nSubjectType=?");
		}

		// ����
		if (condition.getBalanceDirection() > 0) {
			buffer.append(" and NBALANCEDIRECTION=?");
		}

		// �������
		if (condition.getAmountDirection() > 0) {
			buffer.append(" and NAMOUNTDIRECTION=?");
		}

		switch ((int) condition.getOrderBy()) {
		case 1:
			if(condition.getDesc() == 1){
				buffer.append(" order by sSegmentCode1 desc,sSegmentCode2 desc,sSegmentCode3 desc,sSegmentCode4 desc,sSegmentCode5 desc,sSegmentCode6 desc,sSegmentCode7 desc");
			}else{
				buffer.append(" order by sSegmentCode1,sSegmentCode2,sSegmentCode3,sSegmentCode4,sSegmentCode5,sSegmentCode6,sSegmentCode7");
			}
			
			break;
		case 2:
			//����
			if (condition.getDesc() == 1) {
				buffer.append(" order by sSegmentName2 desc");
			}else{
				buffer.append(" order by sSegmentName2");
			}
			break;
		case 3:
			//����
			if (condition.getDesc() == 1) {
				buffer.append(" order by nSubjectType desc");
			}else{
				buffer.append(" order by nSubjectType");
			}
			break;
		case 4:
			//����
			if (condition.getDesc() == 1) {
				buffer.append(" order by nBalanceDirection desc");
			}else{
				buffer.append(" order by nBalanceDirection");
			}
			break;
		case 5:
			//����
			if (condition.getDesc() == 1) {
				buffer.append(" order by nAmountDirection desc");
			}else{
				buffer.append(" order by nAmountDirection");
			}
			
			break;
		default:
			if(condition.getDesc() == 1){
				buffer.append(" order by sSegmentCode1 desc,sSegmentCode2 desc,sSegmentCode3 desc,sSegmentCode4 desc,sSegmentCode5 desc,sSegmentCode6 desc,sSegmentCode7 desc");
			}else{
				buffer.append(" order by sSegmentCode1,sSegmentCode2,sSegmentCode3,sSegmentCode4,sSegmentCode5,sSegmentCode6,sSegmentCode7");
			}
			break;
		}

		
		System.out.println("����������ѯ��Ŀ������Ϣ: " + buffer.toString());
		try {
			int indexId = 1;
			conn = this.getConnection();
			pstmt = conn.prepareStatement(buffer.toString());

			// ���´�
			if (condition.getOfficeID() > 0) {
				pstmt.setLong(indexId++, condition.getOfficeID());
			}
			// ����
			if (condition.getCurrencyID() > 0) {
				pstmt.setLong(indexId++, condition.getCurrencyID());
			}

//			// ��Ŀ����
//			if (condition.getSegmentCode2() != null
//					&& !condition.getSegmentCode2().equals("")) {
//				pstmt.setString(indexId++, "%" + condition.getSegmentCode2()
//						+ "%");
//			}

			//��Ŀ���� ��һ��
			if (condition.getSegmentCode1() != null
					&& !condition.getSegmentCode1().equals("")) {
				pstmt.setString(indexId++, "%" + condition.getSegmentCode1()
						+ "%");
			}
			
			//��Ŀ���� �ڶ���
			if (condition.getSegmentCode2() != null
					&& !condition.getSegmentCode2().equals("")) {
				pstmt.setString(indexId++, "%" + condition.getSegmentCode2()
						+ "%");
			}
			
			//��Ŀ���� ������
			if (condition.getSegmentCode3() != null
					&& !condition.getSegmentCode3().equals("")) {
				pstmt.setString(indexId++, "%" + condition.getSegmentCode3()
						+ "%");
			}
			
			//��Ŀ���� ���Ķ�
			if (condition.getSegmentCode4() != null
					&& !condition.getSegmentCode4().equals("")) {
				pstmt.setString(indexId++, "%" + condition.getSegmentCode4()
						+ "%");
			}
			
			//��Ŀ���� �����
			if (condition.getSegmentCode5() != null
					&& !condition.getSegmentCode5().equals("")) {
				pstmt.setString(indexId++, "%" + condition.getSegmentCode5()
						+ "%");
			}
			
			//��Ŀ���� ������
			if (condition.getSegmentCode6() != null
					&& !condition.getSegmentCode6().equals("")) {
				pstmt.setString(indexId++, "%" + condition.getSegmentCode6()
						+ "%");
			}
			
			//��Ŀ���� ���߶�
			if (condition.getSegmentCode7() != null
					&& !condition.getSegmentCode7().equals("")) {
				pstmt.setString(indexId++, "%" + condition.getSegmentCode7()
						+ "%");
			}
			
			// ��Ŀ����
			if (condition.getSegmentName2() != null
					&& !condition.getSegmentName2().equals("")) {
				pstmt.setString(indexId++, "%" + condition.getSegmentName2()
						+ "%");
			}

			// ��Ŀ����ID
			if (condition.getSubjectType() > 0) {
				pstmt.setLong(indexId++, condition.getSubjectType());
			}

			// ����
			if (condition.getBalanceDirection() > 0) {
				pstmt.setLong(indexId++, condition.getBalanceDirection());
			}

			// �������
			if (condition.getAmountDirection() > 0) {
				pstmt.setLong(indexId++, condition.getAmountDirection());
			}

			rset = pstmt.executeQuery();

			while (rset.next()) {
				info = new GLSubjectDefinitionInfo();
				info.setID(rset.getLong("ID"));
				info.setOfficeID(rset.getLong("nOfficeID"));
				info.setCurrencyID(rset.getLong("nCurrencyID"));
				info.setSegmentCode1(rset.getString("sSegmentCode1"));
				info.setSegmentCode2(rset.getString("sSegmentCode2"));
				info.setSegmentCode3(rset.getString("sSegmentCode3"));
				info.setSegmentCode4(rset.getString("sSegmentCode4"));
				info.setSegmentCode5(rset.getString("sSegmentCode5"));
				info.setSegmentCode6(rset.getString("sSegmentCode6"));
				info.setSegmentCode7(rset.getString("sSegmentCode7"));
				info.setSegmentName1(rset.getString("sSegmentName1"));
				info.setSegmentName2(rset.getString("sSegmentName2"));
				info.setSegmentName3(rset.getString("sSegmentName3"));
				info.setSegmentName4(rset.getString("sSegmentName4"));
				info.setSubjectType(rset.getLong("nSubjectType"));
				info.setLeaf(rset.getLong("nIsleaf") == 1 ? true : false);
				info.setRoot(rset.getLong("nIsRoot") == 1 ? true : false);
				info.setParentSubjectID(rset.getLong("nParentSubjectID"));
				info.setBalanceDirection(rset.getLong("nBalanceDirection"));
				info.setAmountDirection(rset.getLong("nAmountDirection"));
				info.setStatusID(rset.getLong("nStatus"));
				info.setLederType(rset.getLong("nLederType"));
				info.setSecurityLevel(rset.getLong("nSecurityLevel"));
				info.setUseScope(rset.getLong("nUseScope"));
				info.setFlag(rset.getLong("nFlag"));
				info.setValidDate(rset.getTimestamp("dtValidDate"));
				coll.add(info);
			}
		} catch (SQLException sqle) {
			logger.error("ͨ�������ѯʱSQL�쳣", sqle);
			// throw new SQLException("Gen _E001");
			throw sqle;
		} finally {
			try {

				this.cleanup(rset);
				this.cleanup(pstmt);
				this.cleanup(conn);
			} catch (SQLException e) {
				logger.error("�ر����ݿ�����ʱSQL�쳣", e);
				// throw new SQLException("Gen _E001");
				throw e;
			}
		}

		return coll;
	}
    /**
     * ��ѯ���п�Ŀ�ܱ��¼
     * @param includeDisable true-������Ч��false-��������Ч
     * @param officeID ����ID �����-1���Դ�����
     * @param CurrencyID ���� �����-1���Դ�����
     * @param subjectType ��Ŀ���� �����-1���Դ�����
     * @return ���п�Ŀ�ܱ��¼����
     * @throws SQLException
     */
    public Collection findAll(
        boolean includeDisable,
        long officeID,
        long CurrencyID,
        long subjectType)
        throws SQLException
    {
        Collection collection = new ArrayList(64);
        GLSubjectDefinitionInfo info = null;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        StringBuffer buffer = new StringBuffer(64);
        buffer.append("select \n");
        buffer.append("nOfficeID,\n");
        buffer.append("nCurrencyID,\n");
        buffer.append("sSegmentCode1,\n");
        buffer.append("sSegmentCode2,\n");
        buffer.append("sSegmentCode3,\n");
        buffer.append("sSegmentCode4,\n");
        buffer.append("sSegmentName1,\n");
        //buffer.append("sSubjectName,\n");
        buffer.append("sSegmentName2,\n");
        buffer.append("sSegmentName3,\n");
        buffer.append("sSegmentName4,\n");
        buffer.append("nSubjectType,\n");
        buffer.append("nIsleaf,\n");
        buffer.append("nIsRoot,\n");
        buffer.append("nParentSubjectID,\n");
        buffer.append("nBalanceDirection,\n");
        buffer.append("nAmountDirection,\n");
        buffer.append("nStatus,\n");
        buffer.append("nLederType,\n");
        buffer.append("nSecurityLevel,\n");
        buffer.append("nUseScope,\n");
        buffer.append("nFlag,\n");
        buffer.append("dtValidDate,\n");
        buffer.append("id \n");
        buffer.append(" from SETT_VGLSUBJECTDEFINITION \n");

        boolean itemNotEmpty = false;

        if (!includeDisable)
        {
            itemNotEmpty = true;
            buffer.append("where nStatus=1 ");
        }

        if (officeID != -1)
        {
            if (itemNotEmpty)
            {
                buffer.append(" and ");
            }
            else
            {
                itemNotEmpty = true;
                buffer.append(" where ");
            }

            buffer.append(" nOfficeID=").append(officeID);
        }

        if (CurrencyID != -1)
        {
            if (itemNotEmpty)
            {
                buffer.append(" and ");
            }
            else
            {
                itemNotEmpty = true;
                buffer.append(" where ");
            }

            buffer.append(" nCurrencyID=").append(CurrencyID);
        }

        if (subjectType != -1)
        {
            if (itemNotEmpty)
            {
                buffer.append(" and ");
            }
            else
            {
                itemNotEmpty = true;
                buffer.append(" where ");
            }

            buffer.append(" NSUBJECTTYPE=").append(subjectType);
        }

        buffer.append(" order by sSegmentCode2");

        //sql�ϸ��ӣ��ɴ�ӡ��log�е���
        if (logger.isDebugEnabled())
        {
            logger.debug("\n��ѯ���з�¼���ö�����¼SQL���:\n" + buffer.toString());
        }

        try
        {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(buffer.toString());

            rset = pstmt.executeQuery();

            while (rset.next())
            {
                info = new GLSubjectDefinitionInfo();

                info.setOfficeID(rset.getLong("nOfficeID"));
                info.setCurrencyID(rset.getLong("nCurrencyID"));
                info.setSegmentCode1(rset.getString("sSegmentCode1"));
                info.setSegmentCode2(rset.getString("sSegmentCode2"));
                info.setSegmentCode3(rset.getString("sSegmentCode3"));
                info.setSegmentCode4(rset.getString("sSegmentCode4"));
                info.setSegmentName1(rset.getString("sSegmentName1"));
                //info.setSubjectName(rset.getString("sSubjectName"));
                info.setSegmentName2(rset.getString("sSegmentName2"));
                info.setSegmentName3(rset.getString("sSegmentName3"));
                info.setSegmentName4(rset.getString("sSegmentName4"));
                info.setSubjectType(rset.getLong("nSubjectType"));
                info.setLeaf(rset.getLong("nIsleaf") == 1 ? true : false);
                info.setRoot(rset.getLong("nIsRoot") == 1 ? true : false);
                info.setParentSubjectID(rset.getLong("nParentSubjectID"));
                info.setBalanceDirection(rset.getLong("nBalanceDirection"));
                info.setAmountDirection(rset.getLong("nAmountDirection"));
                info.setStatusID(rset.getLong("nStatus"));
                info.setLederType(rset.getLong("nLederType"));
                info.setSecurityLevel(rset.getLong("nSecurityLevel"));
                info.setUseScope(rset.getLong("nUseScope"));
                info.setFlag(rset.getLong("nFlag"));
                info.setValidDate(rset.getTimestamp("dtValidDate"));
                info.setID(rset.getLong("id"));

                collection.add(info);
            }
        }
        catch (SQLException sqle)
        {
            logger.error("��ѯ���п�Ŀ�ܱ��¼ʱSQL�쳣", sqle);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {
                this.cleanup(rset);
                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (SQLException e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
                //e.printStackTrace();throw new SQLException("Gen _E001");
                throw e;
            }
        }

        return collection;
    }

    /**
     * �޸Ŀ�Ŀ�ܱ��¼״̬
     * @param id long
     * @param status long
     * @return ��Ŀ�ܱ��¼id
     * @throws SQLException
     */
    public long updateStatus(long id, long status) throws SQLException
    {
        long lReturn = -1;

        Connection conn = null;
        PreparedStatement pstmt = null;

        String sqlString =
            "update SETT_GLSUBJECTDEFINITION set NSTATUS=? where id=?";

        try
        {
            conn = this.getConnection();
            int nIndex = 1;

            pstmt = conn.prepareStatement(sqlString);
            pstmt.setLong(nIndex++, status);
            pstmt.setLong(nIndex++, id);

            pstmt.execute();

            lReturn = id;
        }

        finally
        {
            this.cleanup(pstmt);
            this.cleanup(conn);

        }
        return lReturn;
    }

    /**
     * �жϿ�Ŀ�����Ƿ����
     * @param ��Ŀ
     * @return ��Ŀ�����Ƿ����
     * @param SQLException
     */
    public boolean SegmentCode2IsExist(GLSubjectDefinitionInfo info)
        throws SQLException
    {
        boolean isExist = false;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try
        {
            int nIndex = 1;
            conn = this.getConnection();
            pstmt =
                conn.prepareStatement(
                    "SELECT ID FROM sett_VGLSubjectDefinition WHERE nstatus=1 and sSegmentCode2=? and nOfficeID=?");

            pstmt.setString(nIndex++, info.getSegmentCode2());
            pstmt.setLong(nIndex++, info.getOfficeID());
            rset = pstmt.executeQuery();

            if (rset.next() && rset.getLong(1) != info.getID())
            {
                isExist = true;
            }

        }
        catch (SQLException e)
        {
            logger.error("�жϿ�Ŀ�����Ƿ����ʱSQL�쳣", e);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw e;
        }
        finally
        {
            try
            {
                this.cleanup(rset);
                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (Exception e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
            }

        }
        return isExist;
    }

    /**
     * �жϿ�Ŀ�����Ƿ����
     * @param  ��Ŀ
     * @return ��Ŀ�����Ƿ����
     * @throws Exception
     */
    public boolean subjectNameIsExist(GLSubjectDefinitionInfo info)
        throws Exception
    {
        boolean isExist = false;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try
        {
            int nIndex = 1;
            conn = this.getConnection();
            pstmt =
                conn.prepareStatement(
                    "SELECT ID FROM sett_VGLSubjectDefinition WHERE nstatus=1 and sSegmentName2=? and nOfficeID=?");
            pstmt.setString(nIndex++, info.getSegmentName2());
            pstmt.setLong(nIndex++, info.getOfficeID());
            rset = pstmt.executeQuery();

            if (rset.next() && rset.getLong(1) != info.getID())
            {
                isExist = true;
            }

        }
        catch (SQLException e)
        {
            logger.error("�жϿ�Ŀ�����Ƿ񲻴���ʱSQL�쳣", e);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw e;
        }
        finally
        {
            try
            {
                this.cleanup(rset);
                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (Exception e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
            }

        }

        return isExist;
    }

    /**
     * У���йغ��ӿ�Ŀ�Ĺ�ϵ
     * @param id long
     * @throws Exception
     */
    public boolean hasSonSubject(long id) throws Exception
    {
        boolean hasSonSubject = false;

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try
        {
            conn = this.getConnection();
            pstmt =
                conn.prepareStatement(
                    "select id from SETT_VGLSUBJECTDEFINITION where NPARENTSUBJECTID=? and NSTATUS=1");
            pstmt.setLong(1, id);
            rset = pstmt.executeQuery();

            if (rset.next())
            {
                hasSonSubject = true;
            }

        }
        catch (SQLException sqle)
        {
            logger.error("�жϿ�Ŀ�����Ƿ񲻴���ʱSQL�쳣", sqle);
            //e.printStackTrace();throw new SQLException("Gen _E001");
            throw sqle;
        }
        finally
        {
            try
            {
                this.cleanup(rset);
                this.cleanup(pstmt);
                this.cleanup(conn);
            }
            catch (Exception e)
            {
                logger.error("�ر����ݿ�����ʱSQL�쳣", e);
            }
        }

        return hasSonSubject;

    }

	public boolean isExistSubeject(String subjectCode) throws SQLException{
		boolean isExist = false;

		 Connection conn = null;
		 PreparedStatement pstmt = null;
		 ResultSet rset = null;

		 try
		 {
			 conn = this.getConnection();
			 pstmt =
				 conn.prepareStatement(
					 "SELECT ID FROM sett_VGLSubjectDefinition WHERE SSUBJECTCODE=?");
			 pstmt.setString(1, subjectCode);
			 rset = pstmt.executeQuery();

			 if (rset.next() && rset.getLong(1) > 0)
			 {
				 isExist = true;
			 }

		 }
		 finally
		 {
			 this.cleanup(rset);
			 this.cleanup(pstmt);
			 this.cleanup(conn);
		 }

		 return isExist;		
	}
	// add by xuteng
	public boolean isExistSubeject(GLSubjectDefinitionInfo info) throws SQLException {
		boolean isExist = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = this.getConnection();
			pstmt = conn
					.prepareStatement("SELECT ID FROM sett_VGLSubjectDefinition WHERE SSEGMENTCODE1=? and SSEGMENTCODE2=? and SSEGMENTCODE3=? and SSEGMENTCODE4=? and SSEGMENTCODE5=? and SSEGMENTCODE6=? and SSEGMENTCODE7=?");
			pstmt.setString(1, info.getSegmentCode1());
			pstmt.setString(2, info.getSegmentCode2());
			pstmt.setString(3, info.getSegmentCode3());
			pstmt.setString(4, info.getSegmentCode4());
			pstmt.setString(5, info.getSegmentCode5());
			pstmt.setString(6, info.getSegmentCode6());
			pstmt.setString(7, info.getSegmentCode7());
			
			rset = pstmt.executeQuery();

			if (rset.next() && rset.getLong(1) > 0) {
				isExist = true;
			}

		} finally {
			this.cleanup(rset);
			this.cleanup(pstmt);
			this.cleanup(conn);
		}

		return isExist;
	}
	public Vector saveUploadFileToDateBase(PageContext pageContext,
			com.jspsmart.upload.SmartUpload mySmartUpload, long lCurrencyID,
			long lOfficeID) throws Exception {
		long lResult = 0;
		long strResult = -1; //�������

		String strFileContent = "";
		String sbExecuteSQL = "";
		String strSQLGetOtherInfo = "";
		Vector returnVector = new Vector();
		Vector conditionVector = new Vector();
		String strAdd = "";// ÿ�δ��ϴ��ļ��ж�����һ����Ԫ��
		int index = 0;
		int toIndex = 0;
		long lNumberRow = 0;
		int fromIndex = 0;

		boolean bIsValid = true; // ��ǰ���Ƿ���Ч

		String strAccuntID = "";
		String strClientID = "";
		String strGetUpClientID = "";
		String strClientCode = "";
		String strTempClientCode = "";
		long lUpClientID = 0;

		long lIsertRowSum = 0;

		long lMaxID = 0;
		long lMaxNO = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int iTableKeyPositionFive = 0;
		boolean bIsEnd = true;
		// Retreive the current file
		com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0);
		myFile.saveAs(Env.UPLOAD_PATH + myFile.getFileName());
		String path = pageContext.getServletContext().getRealPath(Env.UPLOAD_PATH + myFile.getFileName());


		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path));
			HSSFWorkbook wb = null;
			HSSFSheet sheet = null;
			HSSFRow row = null;
			HSSFCell cell = null;
			if (fs != null) {
				wb = new HSSFWorkbook(fs);
			}
			if (wb != null) {
				sheet = wb.getSheetAt(0);
			}
			if (sheet == null) {
				UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
				returnVector.addElement(upLoanReturnInfo);
				upLoanReturnInfo.setIsOk(true);
				upLoanReturnInfo.setPositionRow(0);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason("���ܵ���յ�Excel�ļ���");
				Log.print("����3");
			}

			row = sheet.getRow(0);
			for (int i = 0; row != null; i++, row = sheet.getRow(i)) {
				Log.print("���ѭ��****" + i);
				// i=0��ʱ������Ǳ����У�Ӧ������
				if (i == 0) {
					continue;
				}
				bIsValid = true; //�ж��Ƿ���֤ͨ��
				long num = -1; //
				GLSubjectDefinitionInfo subjectInfo = new GLSubjectDefinitionInfo();

				for (index = 1; index <= 8; index++) {
					Log.print("�ڲ�ѭ��****" + index);
					strAdd = "";
					Log.print("for  ѭ����ʼ" + index);
					cell = row.getCell((short) (index - 1));
					if (cell != null) {
						// ��Excel�е��������ı�������
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
							strAdd = cell.getStringCellValue();
						}
						// ��Excel�е���������ֵ
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
							strAdd = String.valueOf(cell.getNumericCellValue());
						}
					}
					if (strAdd == null) {
						strAdd = "";
					}

					Log.print("��ȡ����strAdd==" + strAdd + "==");
					

					// 1-������ID
					if (index == 1) {
						strAdd = strAdd.trim();
						if (strAdd.indexOf(".")>0) //����excel������ת��Ϊ�����͵�����
							strAdd = strAdd.substring(0,strAdd.indexOf("."));
						Log.print("ȡ��1������ = " + strAdd);
						
						if(strAdd == null || strAdd.equals("")){
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							returnVector.addElement(upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("�����Ų���Ϊ�գ�::"
									+ strAdd);
							bIsValid = false;
						}else{
							try {
								subjectInfo.setOfficeID(Long.parseLong(strAdd));
							} catch (Exception e) {
								Log.print("ȡ��1������!��ʽ������!");
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
								returnVector.addElement(upLoanReturnInfo);
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionRow(i);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���֣�::"
										+ strAdd);
								bIsValid = false;
							}
						}
					}

					// 2-���ֺ�
					if (index == 2) {
						strAdd = strAdd.trim();
						Log.print("ȡ��2������ = " + strAdd);
						if (strAdd.indexOf(".")>0)  //����excel������ת��Ϊ�����͵�����
							strAdd = strAdd.substring(0,strAdd.indexOf("."));
						
						if(strAdd == null || strAdd.equals("")){
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							returnVector.addElement(upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("���ֺŲ���Ϊ�գ�::"
									+ strAdd);
							bIsValid = false;
						}else{
							try {
								subjectInfo.setCurrencyID(Long.parseLong(strAdd));
							} catch (Exception e) {
								Log.print("ȡ��2������!��ʽ������!");
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
								returnVector.addElement(upLoanReturnInfo);
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionRow(i);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setReason("��������ݲ���ת��Ϊ���֣�::"
										+ strAdd);
								bIsValid = false;
							}
						}
					}

					// 3-��Ŀ����
					if (index == 3) {
						strAdd = strAdd.trim();
						Log.print("ȡ��3������ = " + strAdd);
//						if (strAdd.indexOf(".")>0)  //����excel������ת��Ϊ�����͵�����
//							strAdd = strAdd.substring(0,strAdd.indexOf("."));
						
						if(strAdd == null || strAdd.equals("")){
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							returnVector.addElement(upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("��Ŀ���벻��Ϊ�գ�::"
									+ strAdd);
							bIsValid = false;
						}else{
							String[] sub = strAdd.split("\\.");
							if(sub.length != 7){
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
								returnVector.addElement(upLoanReturnInfo);
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionRow(i);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setReason("��Ŀ���벻���Ϲ淶-ֻ����7�Σ�::"
										+ strAdd);
								bIsValid = false;
							}else{
								for(int j = 0; j < sub.length; j++){
									if(!this.isNumberOrLetter(sub[j])){
										UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
										returnVector.addElement(upLoanReturnInfo);
										upLoanReturnInfo.setIsOk(true);
										upLoanReturnInfo.setPositionRow(i);
										upLoanReturnInfo.setPositionCol(index);
										upLoanReturnInfo.setReason("��Ŀ���벻���Ϲ淶-ÿ��ֻ�������ֻ�Ӣ����ĸ��ɣ�::"
												+ strAdd);
										bIsValid = false;
										break;
									}
									if(sub[j].length() > 10){
										UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
										returnVector.addElement(upLoanReturnInfo);
										upLoanReturnInfo.setIsOk(true);
										upLoanReturnInfo.setPositionRow(i);
										upLoanReturnInfo.setPositionCol(index);
										upLoanReturnInfo.setReason("��Ŀ���벻���Ϲ淶-ÿ�γ��Ȳ��ܳ���10���ַ���::"
												+ strAdd);
										bIsValid = false;
										break;
									}
								}
								subjectInfo.setSubcode(strAdd);
							}				
						}								
					}

					// 4-��Ŀ����
					if (index == 4) {
						strAdd = strAdd.trim();
						Log.print("ȡ��4������ = " + strAdd);
						if(strAdd == null || strAdd.equals("")){
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							returnVector.addElement(upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("��Ŀ���Ʋ���Ϊ�գ�::"
									+ strAdd);
							bIsValid = false;
						}else{
							if(strAdd.length() > 50){
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
								returnVector.addElement(upLoanReturnInfo);
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionRow(i);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setReason("��Ŀ���Ƴ��Ȳ��ܳ���50���ַ���::"
										+ strAdd);
								bIsValid = false;
							}else{
								subjectInfo.setSegmentName2(strAdd);
							}	
						}
					}

					// 5-��Ŀ����
					if (index == 5) {
						strAdd = strAdd.trim();
						Log.print("ȡ��5������ = " + strAdd);
						num = SETTConstant.SubjectAttribute.getCode(strAdd);
						if(num == 0){
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							returnVector.addElement(upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("����Ŀ�Ŀ������ϵͳ�в����ڣ�::"
									+ strAdd);
							bIsValid = false;
						}else{
							subjectInfo.setSubjectType(num);
						}	
					}

					// 6-�Ƿ�ĩ����Ŀ
					if (index == 6) {
						strAdd = strAdd.trim();
						Log.print("ȡ��6������ = " + strAdd);
						if(strAdd == null || strAdd.equals("")){
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							returnVector.addElement(upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("�Ƿ�ĩ����Ŀ����Ϊ�գ�::"
									+ strAdd);
							bIsValid = false;
						}else{
							if(strAdd.equals("��")){
								subjectInfo.setLeaf(true);
							}else if(strAdd.equals("��")){
								subjectInfo.setLeaf(false);
							}else{
								UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
								returnVector.addElement(upLoanReturnInfo);
								upLoanReturnInfo.setIsOk(true);
								upLoanReturnInfo.setPositionRow(i);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setReason("���ֶε�ֵ����Ϊ'��'��'��'��::"
										+ strAdd);
								bIsValid = false;
							}
						}						
						
					}
					
					// 7-����
					if (index == 7) {
						strAdd = strAdd.trim();
						Log.print("ȡ��7������ = " + strAdd);
						num = SETTConstant.ControlDirection.getCode(strAdd);
						if(num == 0){
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							returnVector.addElement(upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("�����������ϵͳ�в����ڣ�::"
									+ strAdd);
							bIsValid = false;
						}else{
							subjectInfo.setBalanceDirection(num);
						}	
					}
					
					// 8-�������
					if (index == 8) {
						strAdd = strAdd.trim();
						Log.print("ȡ��8������ = " + strAdd);
						num = SETTConstant.ControlDirection.getCode(strAdd);
						if(num == 0){
							UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
							returnVector.addElement(upLoanReturnInfo);
							upLoanReturnInfo.setIsOk(true);
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("����ķ��������ϵͳ�в����ڣ�::"
									+ strAdd);
							bIsValid = false;
						}else{
							subjectInfo.setAmountDirection(num);
						}	
					}
				}
				//��֤ͨ������뼯��
				if (bIsValid) {
					subjectInfo.setStatusID(1); //��Ŀ״̬������
					conditionVector.addElement(subjectInfo);
				}
			}

			if (conditionVector.size() > 0) {
				Iterator it = conditionVector.iterator();

				Log.print("��ʼ��������");

				 Log.print("ɾ���������е�����");
				 con = this.getConnection();
				
				 ps = con.prepareStatement (" delete from sett_glsubjectdefinition ");
				
				 ps.executeQuery ();
				 Log.print("�ɹ�ɾ���������е�����");
				 if (ps!=null)
				 {
				 ps.close();
				 ps=null;
				 }

				lIsertRowSum = 0;

				Connection conn = null;
				PreparedStatement pstmt = null;
				
				while (it.hasNext()) {
					Log.print("aa");

					GLSubjectDefinitionInfo info = (GLSubjectDefinitionInfo) it.next();

					lIsertRowSum++;
					
					if(this.isExistSubeject(info)){
						UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
						returnVector.addElement(upLoanReturnInfo);
						upLoanReturnInfo.setIsOk(false);

						upLoanReturnInfo.setPositionRow(lIsertRowSum);
						upLoanReturnInfo.setPositionCol(0);

						upLoanReturnInfo.setReason("����д�����ݿⷢ���쳣��������ͬ�Ŀ�Ŀ�ţ�" + info.getSubcode());
						continue;
					}
					long id = this.getNextID();
					info.setID(id);

					
					StringBuffer buffer = new StringBuffer(64);
					buffer.append("insert into sett_GLSubjectDefinition \n");
					buffer.append("(ID,\n");
					buffer.append("nOfficeID,\n");
					buffer.append("nCurrencyID,\n");
					buffer.append("sSegmentCode1,\n");
					buffer.append("sSegmentCode2,\n");
					buffer.append("sSegmentCode3,\n");
					buffer.append("sSegmentCode4,\n");
					buffer.append("sSegmentCode5,\n");
					buffer.append("sSegmentCode6,\n");
					buffer.append("sSegmentCode7,\n");
					buffer.append("sSegmentName1,\n");
					// buffer.append("sSubjectName,\n");
					buffer.append("sSegmentName2,\n");
					buffer.append("sSegmentName3,\n");
					buffer.append("sSegmentName4,\n");
					buffer.append("nSubjectType,\n");
					buffer.append("nIsleaf,\n");
					buffer.append("nIsRoot,\n");
					buffer.append("nParentSubjectID,\n");
					buffer.append("nBalanceDirection,\n");
					buffer.append("nAmountDirection,\n");
					buffer.append("nStatus,\n");
					buffer.append("nLederType,\n");
					buffer.append("nSecurityLevel,\n");
					buffer.append("nUseScope,\n");
					buffer.append("nFlag,\n");
					buffer.append("dtValidDate) \n");
					buffer
							.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

					try {
						conn = this.getConnection();

						int nIndex = 1;

						pstmt = conn.prepareStatement(buffer.toString());

						pstmt.setLong(nIndex++, info.getID());
						pstmt.setLong(nIndex++, info.getOfficeID());
						pstmt.setLong(nIndex++, info.getCurrencyID());
						pstmt.setString(nIndex++, info.getSegmentCode1());
						pstmt.setString(nIndex++, info.getSegmentCode2());
						pstmt.setString(nIndex++, info.getSegmentCode3());
						pstmt.setString(nIndex++, info.getSegmentCode4());
						pstmt.setString(nIndex++, info.getSegmentCode5());
						pstmt.setString(nIndex++, info.getSegmentCode6());
						pstmt.setString(nIndex++, info.getSegmentCode7());
						pstmt.setString(nIndex++, info.getSegmentName1());
						// pstmt.setString(nIndex++, info.getSubjectName());
						pstmt.setString(nIndex++, info.getSegmentName2());
						pstmt.setString(nIndex++, info.getSegmentName3());
						pstmt.setString(nIndex++, info.getSegmentName4());
						pstmt.setLong(nIndex++, info.getSubjectType());
						pstmt.setLong(nIndex++, info.isLeaf() ? 1 : 0);
						pstmt.setLong(nIndex++, info.isRoot() ? 1 : 0);
						pstmt.setLong(nIndex++, info.getParentSubjectID());
						pstmt.setLong(nIndex++, info.getBalanceDirection());
						pstmt.setLong(nIndex++, info.getAmountDirection());
						pstmt.setLong(nIndex++, info.getStatusID());
						pstmt.setLong(nIndex++, info.getLederType());
						pstmt.setLong(nIndex++, info.getSecurityLevel());
						pstmt.setLong(nIndex++, info.getUseScope());
						pstmt.setLong(nIndex++, info.getFlag());
						pstmt.setTimestamp(nIndex++, info.getValidDate());

						pstmt.execute();
						
						Log.print("�ɹ������" + lIsertRowSum + "����¼��");
						UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
						returnVector.addElement(upLoanReturnInfo);
						upLoanReturnInfo.setIsOk(true);
						upLoanReturnInfo.setPositionRow(lIsertRowSum);
						upLoanReturnInfo.setPositionCol(index);
						upLoanReturnInfo.setReason("<font color=green>�ɹ������Ŀ��Ϊ��" + info.getSubcode() + " �Ŀ�Ŀ��Ϣ��</font>");

					} catch (SQLException sqle) {
						logger.error("������Ŀ�ܱ��¼ʱSQL�쳣", sqle);
						UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
						returnVector.addElement(upLoanReturnInfo);
						upLoanReturnInfo.setIsOk(false);

						upLoanReturnInfo.setPositionRow(lIsertRowSum);
						upLoanReturnInfo.setPositionCol(0);

						upLoanReturnInfo.setReason("����д�����ݿⷢ���쳣����Ŀ��Ϊ��" + info.getSubcode() + "   " + sqle.getMessage());
						continue;
					} finally {
						try {

							this.cleanup(pstmt);
							this.cleanup(conn);
						} catch (SQLException e) {
							logger.error("�ر����ݿ�����ʱSQL�쳣", e);
							// throw new SQLException("Gen _E001");
							throw e;
						}
					}


				}

				Log.print("===�ɹ���������===");
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			}

			Log.print("===�ɹ��ر�����===");

		} catch (SQLException sqle) {
			Log.print(sqle.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

			UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
			returnVector.addElement(upLoanReturnInfo);
			upLoanReturnInfo.setIsOk(false);
			upLoanReturnInfo.setPositionRow(lIsertRowSum);
			upLoanReturnInfo.setPositionCol(0);
			upLoanReturnInfo.setReason("д����߶�ȡ���ݿⷢ���쳣����ȫ�����µ��룡");
		} catch (Exception e) {
			e.printStackTrace();
			Log.print(e.toString());
			cleanup(rs);
			cleanup(ps);
			cleanup(con);

			UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
			returnVector.addElement(upLoanReturnInfo);
			upLoanReturnInfo.setIsOk(false);
			upLoanReturnInfo.setPositionRow(lIsertRowSum);
			upLoanReturnInfo.setPositionCol(0);
			upLoanReturnInfo.setReason(e.toString() + "��");
		} finally {
			Log.print("===finally===");
			try {
				if (myFile != null)
					myFile = null;
				cleanup(rs);
				cleanup(ps);
				cleanup(con);
			} catch (Exception e) {
				Log.print(e.toString());
				UpLoanReturnInfo upLoanReturnInfo = new UpLoanReturnInfo();
				returnVector.addElement(upLoanReturnInfo);
				upLoanReturnInfo.setIsOk(false);
				upLoanReturnInfo.setPositionRow(lIsertRowSum);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason(e.toString() + "��");
			}
		}

		Log.print("===finally=ok==");
		return (returnVector.size() > 0 ? returnVector : null);
	}
	
	/**
	 * �ж��ַ����Ƿ�ֻ�������ֻ�Ӣ����ĸ�����Ϊ��Ҳ����false
	 * @param str
	 * @return
	 */
	public static boolean isNumberOrLetter(String str){
		Pattern pattern = Pattern.compile("[0-9|A-Za-z]*");
		Matcher result = null;
		if(str != null && !str.equals("")){
			result = pattern.matcher(str);
			return result.matches();
		}else{
			return false;
		}		
	}
	public static void main(String[] ss){
		Sett_GLSubjectDefinitionDAO ssd = new Sett_GLSubjectDefinitionDAO();
		String sss = "&201.210000.1001001.000000.000000000.000000000.0000";
		String[] dd = sss.split("\\.");
		Pattern pattern = Pattern.compile("[0-9|A-Za-z]*");
		for(int i = 0; i < dd.length; i++){
			if(dd[i] != null && dd[i].equals("")){
				System.out.println("yes");
			}else{
				Matcher isNum = pattern.matcher(dd[i]);
				System.out.println(dd[i] + ": " + isNum.matches() );
			}	
		}
	}
    //id,nOfficeID,nCurrencyID,sSegmentCode1,sSegmentCode2,sSegmentCode3,sSegmentCode4,sSegmentName1,sSubjectName,sSegmentName2,sSegmentName3,segmentName4,nSubjectType,nIsleaf,nIsRoot,nParentSubjectID,nBalanceDirection,nAmountDirection,nStatus,nLederType,nSecurityLevel,nUseScope,nFlag,dtValidDate
}
