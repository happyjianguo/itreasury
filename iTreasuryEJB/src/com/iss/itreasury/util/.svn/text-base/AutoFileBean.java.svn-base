package com.iss.itreasury.util;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dataentity.AutoFileInfo;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AutoFileBean
{
	private static Log4j logger = new Log4j(Constant.ModuleType.ARCHIVESMANAGEMENT);
	/**
	 * find the file by id
	 * @author  
	 * @date 
	 * @param pagecontext
	 * @param downloadfileinfo
	 * @return AutoFileInfo
	 * @throws Exception
	 */

	public static AutoFileInfo getFileByID(long lFileID) throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		StringBuffer sbSQL = null;
		long lMaxNo = -1;
		AutoFileInfo fileInfo = new AutoFileInfo();

		try
		{
			sbSQL = new StringBuffer("");
			sbSQL.append(" SELECT * ");
			sbSQL.append(" FROM fileinfo ");
			sbSQL.append(" WHERE ID = ? ");
			strSQL = sbSQL.toString();

			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lFileID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				fileInfo.setID(rs.getLong("ID"));
				fileInfo.setFileType(rs.getString("sFileType"));
				fileInfo.setServerPath(rs.getString("sServerPath"));
				fileInfo.setClientPath(rs.getString("sClientPath"));
				fileInfo.setServerFileName(rs.getString("sServerFileName"));
				fileInfo.setClientFileName(rs.getString("sClientFileName"));
				fileInfo.setStatus(rs.getLong("nStatus"));
				fileInfo.setInputUserID(rs.getLong("nInputUserID"));
				fileInfo.setInputTime(rs.getTimestamp("dtInput"));
				fileInfo.setFileContentType(rs.getString("sFileContentType"));
				fileInfo.setFileMimeType(rs.getString("sFileMimeType"));
				fileInfo.setFileSucc(rs.getBoolean("bFileSucc"));
				fileInfo.setFileDeniedExt(rs.getString("sFileDeniedExt"));
				fileInfo.setFileAllowedExt(rs.getString("sFileAllowedExt"));
				fileInfo.setMaxFileSize(rs.getLong("nMaxFileSize"));
			}
		}
		catch (Exception exp)
		{
			System.out.println(exp.toString());
		}
		finally
		{
			try
			{
				sqlRelease(conn, ps, rs);
			}
			catch (Exception exp)
			{

			}
		}
		return fileInfo;
	}

	/**
	 * insert the fileinfo into Table OB_FileInfo
	 * @author  
	 * @date 
	 * @param conn
	 * @param fileinfo
	 * @return long
	 * @throws Exception
	 */
	public static AutoFileInfo InsertFileInfoIntoDB(AutoFileInfo fileInfo) throws ITreasuryDAOException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		StringBuffer sbSQL = null;
		long lResult = -1;
		
		try
		{
			sbSQL = new StringBuffer("");
			sbSQL.append(" insert into fileinfo ( ID, sFileType, sServerPath, sClientPath, ");
			sbSQL.append(" sServerFileName, sClientFileName, nStatus, nInputUserID, dtInput, ");
			sbSQL.append(" sFileMimeType, sFileContentType, bFileSucc, sFileDeniedExt, sFileAllowedExt, nMaxFileSize ) ");
			sbSQL.append(" values( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) ");
			strSQL = sbSQL.toString();

			long lID = getMaxFileID();
			fileInfo.setID(lID);

			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			ps.setLong(1, lID);
			ps.setString(2, fileInfo.getFileType());
			ps.setString(3, fileInfo.getServerPath());
			ps.setString(4, fileInfo.getClientPath());
			ps.setString(5, fileInfo.getServerFileName());
			ps.setString(6, fileInfo.getClientFileName());
			ps.setLong(7, fileInfo.getStatus());
			ps.setLong(8, fileInfo.getInputUserID());
			ps.setTimestamp(9, fileInfo.getInputTime());
			ps.setString(10, fileInfo.getFileMimeType());
			ps.setString(11, fileInfo.getFileContentType());
			ps.setBoolean(12, fileInfo.getFileSucc());
			ps.setString(13, fileInfo.getFileDeniedExt());
			ps.setString(14, fileInfo.getFileAllowedExt());
			ps.setLong(15, fileInfo.getMaxFileSize());

			lResult = ps.executeUpdate();
		}
		catch (Exception exp)
		{
			exp.printStackTrace();
			throw new ITreasuryDAOException("保存上传文件记录失败", exp);
		}
		finally
		{
			try
			{
				sqlRelease(conn, ps, rs);
			}
			catch (Exception exp)
			{

			}
			
			if (lResult <= 0)
			{
				fileInfo.setID(-1);
			}
		}

		return fileInfo;
	}

	/**
	 * get the destination path by the specific type value in Upload module
	 * @author  
	 * @date 
	 * @param lDesType
	 * @return String
	 * @throws Exception
	 */
	public static String getDestPath(long lDocType)
	{
		String strReturn = "";

		switch ((int) lDocType)
		{
			case (int) Constant.DocType.LOANCONTRACTCONTENT :
				strReturn = Env.UPLOAD_PATH + "loan/contractcontent/";
				break;
			case (int) Constant.DocType.LOANCONTRACTTEMPLATE :
				strReturn = Env.UPLOAD_PATH + "loan/template/";
				break;
			case (int) Constant.DocType.LOANUPLOAD :
				strReturn = Env.UPLOAD_PATH + "loan/upload/";
				break;
			case (int) Constant.DocType.EBANKUPLOAD :
				strReturn = Env.UPLOAD_PATH + "ebank/upload/";
				break;
			case (int) Constant.DocType.CLIENTCENTER :
				strReturn = Env.UPLOAD_PATH + "clientcenter/upload/";
				break;
			case (int) Constant.DocType.CLIENTMANAGE :
				strReturn = Env.UPLOAD_PATH + "clientmanage/upload/";
				break;
			case (int) Constant.DocType.SECURITIESUPLOAD :
				strReturn = Env.UPLOAD_PATH + "securities/upload/";
				break;
			case (int) Constant.DocType.SETTLEMENTUPLOAD :
				strReturn = Env.UPLOAD_PATH + "settlement/upload/";
				break;
			case (int) Constant.DocType.CRERTUPLOAD :
				strReturn = Env.UPLOAD_PATH + "creditrating/upload/";
				break;
			case (int) Constant.DocType.MODELUPLOAD :
				strReturn = Env.UPLOAD_PATH + "filemanager/upload/";	
				
		}
		if (strReturn.length() == 0)
		{

		}
		return strReturn;
	}

	/**
	 * get the source path by the specific type value in Download module
	 * @author  
	 * @date 
	 * @param lFileID
	 * @return String
	 * @throws Exception
	 */
	public static String getSourcePath(long lFileID)
	{
		String strReturn = "";

		return strReturn;
	}

	/**
	 * get the directory of Uploading file by its established time
	 * @author  
	 * @date 
	 * @param strPreviousPath
	 * @return String
	 * @throws Exception
	 */
	public static String getPathByTime(String strPreviousPath)
	{
		String strLaterPath = "";

		Calendar myCalendar = Calendar.getInstance();
		myCalendar.setTime(Env.getSystemDateTime());
		strLaterPath += strPreviousPath;

		if (!strLaterPath.endsWith("/"))
		{
			strLaterPath += "/";
		}

		strLaterPath += myCalendar.get(Calendar.YEAR);

		return strLaterPath;
	}

	/**
	 * 根据当前时间，生成一个内部文件名
	 * @author  
	 * @date 
	 * @param AutoFileInfo fileInfo 欲生成文件名的文件对象
	 * @return String
	 * @throws Exception
	 */
	public static String getFileName(AutoFileInfo fileInfo)
	{
		String strFileName;
		
		strFileName = Long.toString(Env.getSystemDateTime().getTime()) + "." + fileInfo.getFileType();
		fileInfo.setServerFileName(strFileName);
		return strFileName;
	}
	/**
	 * find the Max ID from the table ob_fileInfo
	 * @author  
	 * @date 
	 * @return long
	 * @throws Exception
	 */
	private static long getMaxFileID() throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String strSQL = null;
		StringBuffer sbSQL = null;
		long lMaxNo = -1;

		try
		{
			sbSQL = new StringBuffer("");
			sbSQL.append(" select nvl( max( ID ), 0 ) + 1 as maxno ");
			sbSQL.append(" from fileinfo");
			strSQL = sbSQL.toString();

			conn = Database.getConnection();
			ps = conn.prepareStatement(strSQL);
			rs = ps.executeQuery();
			if (rs.next())
			{
				lMaxNo = rs.getLong("maxno");
			}
		}
		catch (Exception exp)
		{

		}
		finally
		{
			try
			{
				sqlRelease(conn, ps, rs);
			}
			catch (Exception exp)
			{

			}
		}
		return lMaxNo;
	}

	/**
	 * release the reference of Connection,PreparedStatment and ResultSet
	 * @author  
	 * @date 
	 * @param Connection
	 * @param PreparedStatement
	 * @param ResultSet
	 * @throws Exception
	 */
	private static void sqlRelease(Connection conn, PreparedStatement ps, ResultSet rs) throws Exception
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
			if (conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch (Exception exp)
		{

		}
	}

}
