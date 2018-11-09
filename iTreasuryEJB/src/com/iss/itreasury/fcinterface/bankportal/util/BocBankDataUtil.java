/**
 * Created on 2006-9-30
 */
package com.iss.itreasury.fcinterface.bankportal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author chengli
 */
public class BocBankDataUtil {

	private static Logger logger = new Logger(BocBankDataUtil.class);
	private static String BANKDATA_TABLE_NAME = "bs_BOCBranchCode";
	private StringBuffer sbBranch = null; // 存储所有分行数据
	private StringBuffer sbSupBranch = null; // 存储上级行数据

	public BocBankDataUtil()
	{
		super();
		sbBranch = new StringBuffer();
		sbSupBranch = new StringBuffer();
	}

	/**
     * 根据用户提供的地名，从数据库中读取满足条件的分行数据，并将数据保存在StringBuffer
     * 变量sbBranch和sbSupBranch中。StringBuffer中保存的字符串格式分别为： 例如：sbSupBranch:
     * supBranchGroup[1]=new Option("中国银行北京朝阳区支行","1")， sbBranch:
     * branchGroup[1][0]=new Option("中国银行北京朝阳区西坝河东里支行","40017-8026")， 注意
     * supBranchGroup的option的value必须与branchGroup数组的一维下标对应。
     * sbBranch的option的value为该支行的联行号和机构号，格式为:联行号+"-"+机构号
     * 
     * @param sState
     *            用户输入的地名 异常：
     * @return
     */
	public void setBranchData(String province) throws Exception
	{

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			logger.debug("****************province="+province);
			String sql="select count(distinct S_UPBANKEXCHAGECODE) from "
				+ BANKDATA_TABLE_NAME
				+ " where S_UPBRANCHNAME like '中国银行"
				+ province
				+ "%' or S_UPBRANCHNAME like '%总行'";
			logger.debug("sql1="+sql);
			conn = Database.getConnection();
			// 查询满足条件的上级行的记录数
			int supBranchCount = 0;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs!=null && rs.next())
			{
				supBranchCount = rs.getInt(1);
			}			
			logger.debug("supBranchCount="+supBranchCount);
			// 生成对上级行选择下拉框的javascript定义部分
			sbSupBranch.append("var supBranchSelect;" + "\n");
			sbSupBranch.append("var supBranchGroup = new Array("
					+ supBranchCount + ");" + "\n");

			// 生成对分行选择下拉框的javascript定义部分
			sbBranch.append("var branchSelect;" + "\n");
			sbBranch.append("var branchGroup=new Array(" + supBranchCount
					+ ");" + "\n");
			sbBranch.append("for (i = 0; i <= " + supBranchCount + "; i++)"
					+ "\n");
			sbBranch.append("{" + "\n");
			sbBranch.append("branchGroup[i]=new Array();" + "\n");
			sbBranch.append("}" + "\n");

			// 查询结果集，并对StringBuffer赋值
			 sql = "select * from "
					+ BANKDATA_TABLE_NAME
					+ " where S_UPBRANCHNAME like '中国银行"
					+ province
					+ "%' or S_UPBRANCHNAME like '%总行' order by S_UPBANKEXCHAGECODE";
			 logger.debug("sql2="+sql);
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			String temp = "";
			int m = 1;
			int n = 1;
			int k = 0;
			while (rs.next())
			{
				if(!temp.equals(rs.getString(5).trim()))
				{
					sbSupBranch.append("supBranchGroup[" + n
							+ "] = new Option(\"" + rs.getString(4) + "\","
							+ "\"" + n + "\");" + "\n");

					sbBranch.append("branchGroup[" + n + "][0] = new Option(\""
							+ rs.getString(4) + "\"," + "\"" + rs.getString(5)
							+ "-" + rs.getString(6) + "\");" + "\n");
					n++;
					m = 1;
				}
				k = n - 1;

				sbBranch.append("branchGroup[" + k + "][" + m
						+ "] = new Option(\"" + rs.getString(1) + "\"," + "\""
						+ rs.getString(2) + "-" + rs.getString(3) + "\");"
						+ "\n");

				temp = rs.getString(5).trim(); // 保存这次取到的银行联行号，与下次取到的值作比较

				m++;
			}
		}
		catch (SQLException e)
		{
			logger.error("从数据库读取分行数据发生错误", e);
		}
		finally
		{
			rs.close();
			ps.close();
			conn.close();
		}

	}

	/**
     * 根据用户提供的地名，生成的javascript下拉框所需要的分行option数据 异常：
     * 
     * @return String
     */
	public String getBranchData() throws Exception
	{
		return sbBranch.toString();
	}

	/**
     * 根据用户提供的地名，生成的javascript下拉框所需要的上级行option数据 异常：
     * 
     * @return String
     */
	public String getSupBranchData() throws Exception
	{
		return sbSupBranch.toString();
	}
}
