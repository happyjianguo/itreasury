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
	private StringBuffer sbBranch = null; // �洢���з�������
	private StringBuffer sbSupBranch = null; // �洢�ϼ�������

	public BocBankDataUtil()
	{
		super();
		sbBranch = new StringBuffer();
		sbSupBranch = new StringBuffer();
	}

	/**
     * �����û��ṩ�ĵ����������ݿ��ж�ȡ���������ķ������ݣ��������ݱ�����StringBuffer
     * ����sbBranch��sbSupBranch�С�StringBuffer�б�����ַ�����ʽ�ֱ�Ϊ�� ���磺sbSupBranch:
     * supBranchGroup[1]=new Option("�й����б���������֧��","1")�� sbBranch:
     * branchGroup[1][0]=new Option("�й����б������������ӺӶ���֧��","40017-8026")�� ע��
     * supBranchGroup��option��value������branchGroup�����һά�±��Ӧ��
     * sbBranch��option��valueΪ��֧�е����кźͻ����ţ���ʽΪ:���к�+"-"+������
     * 
     * @param sState
     *            �û�����ĵ��� �쳣��
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
				+ " where S_UPBRANCHNAME like '�й�����"
				+ province
				+ "%' or S_UPBRANCHNAME like '%����'";
			logger.debug("sql1="+sql);
			conn = Database.getConnection();
			// ��ѯ�����������ϼ��еļ�¼��
			int supBranchCount = 0;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs!=null && rs.next())
			{
				supBranchCount = rs.getInt(1);
			}			
			logger.debug("supBranchCount="+supBranchCount);
			// ���ɶ��ϼ���ѡ���������javascript���岿��
			sbSupBranch.append("var supBranchSelect;" + "\n");
			sbSupBranch.append("var supBranchGroup = new Array("
					+ supBranchCount + ");" + "\n");

			// ���ɶԷ���ѡ���������javascript���岿��
			sbBranch.append("var branchSelect;" + "\n");
			sbBranch.append("var branchGroup=new Array(" + supBranchCount
					+ ");" + "\n");
			sbBranch.append("for (i = 0; i <= " + supBranchCount + "; i++)"
					+ "\n");
			sbBranch.append("{" + "\n");
			sbBranch.append("branchGroup[i]=new Array();" + "\n");
			sbBranch.append("}" + "\n");

			// ��ѯ�����������StringBuffer��ֵ
			 sql = "select * from "
					+ BANKDATA_TABLE_NAME
					+ " where S_UPBRANCHNAME like '�й�����"
					+ province
					+ "%' or S_UPBRANCHNAME like '%����' order by S_UPBANKEXCHAGECODE";
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

				temp = rs.getString(5).trim(); // �������ȡ�����������кţ����´�ȡ����ֵ���Ƚ�

				m++;
			}
		}
		catch (SQLException e)
		{
			logger.error("�����ݿ��ȡ�������ݷ�������", e);
		}
		finally
		{
			rs.close();
			ps.close();
			conn.close();
		}

	}

	/**
     * �����û��ṩ�ĵ��������ɵ�javascript����������Ҫ�ķ���option���� �쳣��
     * 
     * @return String
     */
	public String getBranchData() throws Exception
	{
		return sbBranch.toString();
	}

	/**
     * �����û��ṩ�ĵ��������ɵ�javascript����������Ҫ���ϼ���option���� �쳣��
     * 
     * @return String
     */
	public String getSupBranchData() throws Exception
	{
		return sbSupBranch.toString();
	}
}
