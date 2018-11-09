package com.iss.itreasury.fcinterface.bankportal.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.jsp.JspWriter;


/**
 * 建行网银版关于联行号和机构号的类
 * @author yqzhang
 *
 */
public class CcbNetBankDataUtil {
	
	private static Logger logger = new Logger(CcbNetBankDataUtil.class);
	
	private StringBuffer sbFinalBranch = null; // 存储一级分行数据
	private StringBuffer sbSecondBranch = null; // 存储二级分行数据
	private StringBuffer sbThirdBranch = null; // 存储会计柜员数据

	public CcbNetBankDataUtil()
	{
		super();
		sbFinalBranch = new StringBuffer();
		sbSecondBranch = new StringBuffer();
		sbThirdBranch = new StringBuffer();
	}
	/**
	 * 从数据库获取建行网银版银行数据
	 * @param strType 信息类型 1 一级分行信息 2 二级分行信息 3 柜员机构信息
	 * @return
	 */
	public static final HashMap getCcbNetDataFromDb(String strType)
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap hm = new HashMap();
		StringBuffer buffer = new StringBuffer();
		try
		{
			conn = Database.getConnection();
			buffer.append(" SELECT N_ID,S_NAME,N_UPID,S_COUNTERNO,S_UBANKNO FROM CCBNETBRANCH ");
			buffer.append("\n WHERE N_GRADE = "+Long.parseLong(strType)+" ORDER BY N_ID DESC ");
			String strSQL = buffer.toString();
			ps = conn.prepareStatement(strSQL);
			System.out.println(strSQL);
			rs = ps.executeQuery();
			while(rs.next())
			{
				if(strType.equals("1"))
					hm.put(rs.getString("N_ID"),rs.getString("S_NAME"));
				else if (strType.equals("3"))
					hm.put(rs.getString("N_ID"),rs.getString("N_UPID")+"_"+rs.getString("S_NAME")+"_"+
							rs.getString("S_COUNTERNO")+"_"+rs.getString("S_UBANKNO"));
				else if (strType.equals("2"))
					hm.put(rs.getString("N_ID"),rs.getString("N_UPID")+"_"+rs.getString("S_NAME"));
			}
			
		} catch (Exception ex) {
			logger.error(ex.toString());
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				logger.error(e.toString());
			}
		}
		return hm;
	}
	/**
     * @return
     */
	public void setBranchData(String province) throws Exception
	{
		try
		{
			HashMap hmsecond = getCcbNetDataFromDb("2");
			sbSecondBranch.append("var SecondBranch = new Array("
					+ hmsecond.size() + ");" + "\n");
			sbSecondBranch.append("for (i = 0; i <= " + (hmsecond.size() -1 ) + "; i++)"
					+ "\n");
			sbSecondBranch.append("{" + "\n");
			sbSecondBranch.append("SecondBranch[i]=new Array(3);" + "\n");
			sbSecondBranch.append("}" + "\n");
			Iterator itsecond = hmsecond.keySet().iterator();
			int j=0;
			while (itsecond.hasNext())
			{
			   String idsecond = (String)itsecond.next();
			   String upidname =(String)hmsecond.get(idsecond);    
			   String[] str= DataFormat.splitString(upidname,"_");
			   String upid = str[0];
			   String name = str[1];
			   sbSecondBranch.append("SecondBranch["+j+"]=new Array('"+idsecond+"','"+upid+"','"+name+"')\n" );
			   j++;
			}		
			HashMap hmthird = getCcbNetDataFromDb("3");
			sbThirdBranch.append("var ThirdBranch = new Array("
					+ hmthird.size() + ");" + "\n");
			sbThirdBranch.append("for (i = 0; i <= " + (hmthird.size() -1 ) + "; i++)"
					+ "\n");
			sbThirdBranch.append("{" + "\n");
			sbThirdBranch.append("ThirdBranch[i]=new Array(3);" + "\n");
			sbThirdBranch.append("}" + "\n");
			Iterator itthird = hmthird.keySet().iterator();
			j=0;
			while (itthird.hasNext())
			{
			   String idsecond = (String)itthird.next();
			   String upidname =(String)hmthird.get(idsecond);    
			   String[] str= DataFormat.splitString(upidname,"_");
			   String upid = str[0];
			   String name = str[1];
			   String scountername = str[2];
			   String subankno = str[3];
			   sbThirdBranch.append("ThirdBranch["+j+"]=new Array('"+idsecond+"','"+upid+"','"+name+"','"+scountername+"','"+subankno+"')\n" );
			   j++;
			}		
		
		}
		catch (Exception e)
		{
			logger.error("生成数组出现异常", e);
		}
	}
	
	
	/**
	 * 显示建设银行信息下拉列表
	 *
	 * @param out
	 * @param strControlName,
	 *            控件名称
	 * @param nType，控件类型（0，显示全部；）
	 * @param lSelectValue
	 * @param isNeedAll，是否需要”全部项“
	 * @param isNeedBlank
	 *            是否需要空白行
	 * @param strProperty
	 * @param strType 列表信息类型 1 一级分行信息 2 二级分行信息 3 柜员机构信息
	 */
	public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,String strType)
	{
		HashMap hm = null;
		long[] lArrayID = null;
		String[] strArrayName = null;
		//获取银行信息到HashMap中
		hm = getCcbNetDataFromDb(strType);
		try
		{
			lArrayID = new long[hm.size()];
			strArrayName = new String[hm.size()];
			Iterator it = hm.keySet().iterator();
			int i=0;
		    while (it.hasNext())
		    {
		        String id = (String)it.next();
		        System.out.println("id:"+id);
		        String name =(String)hm.get(id);
		        System.out.println("name:"+name);
		        lArrayID[i] = Long.parseLong(id);
		        strArrayName[i] = name;
		        i++;
		    }
		    HTMLHelper.showCommonSelectListControlSel(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
		}
		catch (Exception ex)
		{
			logger.error(ex.toString());
		}
	}
	
	public String getSecondBranchData() throws Exception
	{
		return sbSecondBranch.toString();
	}
	
	public String getThirdBranchData() throws Exception
	{
		return sbThirdBranch.toString();
	}
	
	public String getFinalBranchData() throws Exception
	{
		return sbFinalBranch.toString();
	}
	
	public static void main(String[] args)
	{
//		JspWriter w = null;
//		showList(w,"",1,1,false,false,"","1");
		CcbNetBankDataUtil cn = new CcbNetBankDataUtil();
		try
		{
			cn.setBranchData("");
		}
		catch(Exception e)
		{
			
		}
		System.out.println(cn.sbSecondBranch.toString());
		System.out.println(cn.sbThirdBranch.toString());
		
	}


}
