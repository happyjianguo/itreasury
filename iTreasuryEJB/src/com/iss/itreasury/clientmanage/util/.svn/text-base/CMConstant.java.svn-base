/*
 * Created on 2005-11-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.clientmanage.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.clientmanage.client.bizlogic.ClientCmd;
import com.iss.itreasury.clientmanage.client.bizlogic.Clientmanage;
import com.iss.itreasury.clientmanage.client.dao.ClientDAO;
import com.iss.itreasury.clientmanage.client.dataentity.ClientAreaInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EconomicComponentInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EconomicDepartmentInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EconomicIndustryInfo;
import com.iss.itreasury.clientmanage.client.dataentity.EnterpriseSizeInfo;
import com.iss.itreasury.clientmanage.systemset.clientattribute.bizlogic.ClientAttributeCmd;
import com.iss.itreasury.clientmanage.systemset.clientattribute.dataentity.ClientAttributeInfo;
import com.iss.itreasury.clientmanage.systemset.customfield.bizlogic.CustomFieldCmd;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Log;
/**
 * @author gdzhao
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CMConstant extends com.iss.itreasury.util.Constant{
	
    public static final long CLIENPRO1 = 1;//客户属性1
    public static final long CLIENPRO2 = 2;//客户属性2
    public static final long CLIENPRO3 = 3;//客户属性3
    public static final long CLIENPRO4 = 4;//客户属性4
    public static final long CLIENPRO5 = 5;//客户属性5
    public static final long CLIENPRO6 = 6;//客户属性6
    
    public static final long CLIENTEXTENDPRO1 = 11;//客户属性11
    public static final long CLIENTEXTENDPRO2 = 12;//客户属性12
    public static final long CLIENTEXTENDPRO3 = 13;//客户属性13
    public static final long CLIENTEXTENDPRO4 = 14;//客户属性14
    public static final long CLIENTEXTENDPRO5 = 15;//客户属性15
    public static final long CLIENTEXTENDPRO6 = 16;//客户属性16
    public static final long CLIENTEXTENDPRO7 = 17;//客户属性17
    public static final long CLIENTEXTENDPRO8 = 18;//客户属性18
    
    public static final long LEGALPERSON1 = 21;//法人客户扩展信息1
    public static final long LEGALPERSON2 = 22;//法人客户扩展信息2
    public static final long LEGALPERSON3 = 23;//法人客户扩展信息3
    public static final long LEGALPERSON4 = 24;//法人客户扩展信息4
    public static final long LEGALPERSON5 = 25;//法人客户扩展信息5
    public static final long LEGALPERSON6 = 26;//法人客户扩展信息6
    public static final long LEGALPERSON7 = 27;//法人客户扩展信息7
    public static final long LEGALPERSON8 = 28;//法人客户扩展信息8
    
    public static final long NATURALPERSON1 = 31;//法人客户扩展信息1
    public static final long NATURALPERSON2 = 32;//法人客户扩展信息2
    public static final long NATURALPERSON3 = 33;//法人客户扩展信息3
    public static final long NATURALPERSON4 = 34;//法人客户扩展信息4
    public static final long NATURALPERSON5 = 35;//法人客户扩展信息5
    public static final long NATURALPERSON6 = 36;//法人客户扩展信息6
    public static final long NATURALPERSON7 = 37;//法人客户扩展信息7
    public static final long NATURALPERSON8 = 38;//法人客户扩展信息8
    
   
    
	public static String  FieldID(long FieldID)
	{
		String str = "";
		switch((int) FieldID)
		{
			case (int) 1:
				str = "客户属性一";
			break;
			case (int) 2:
				str = "客户属性二";
			break;
			case (int) 3:
				str = "客户属性三";
			break;
			case (int) 4:
				str = "客户属性四";
			break;
			case (int) 5:
				str = "客户属性五";
			break;
			case (int) 6:
				str = "客户属性六";
			break;
			case (int) 11:
				str = "客户扩展属性1";
			break;
			case (int) 12:
				str = "客户扩展属性2";
			break;
			case (int) 13:
				str = "客户扩展属性3";
			break;
			case (int) 14:
				str = "客户扩展属性4";
			break;
			case (int) 15:
				str = "客户扩展属性5";
			break;
			case (int) 16:
				str = "客户扩展属性6";
			break;
			case (int) 17:
				str = "客户扩展属性7";
			break;
			case (int) 18:
				str = "客户扩展属性8";
			break;			
			case (int) 21:
				str = "法人客户扩展信息1";
			break;
			case (int) 22:
				str = "法人客户扩展信息2";
			break;
			case (int) 23:
				str = "法人客户扩展信息3";
			break;
			case (int) 24:
				str = "法人客户扩展信息4";
			break;
			case (int) 25:
				str = "法人客户扩展信息5";
			break;
			case (int) 26:
				str = "法人客户扩展信息6";
			break;
			case (int) 27:
				str = "法人客户扩展信息7";
			break;
			case (int) 28:
				str = "法人客户扩展信息8";
			break;
			case (int) 31:
				str = "自然人客户扩展信息1";
			break;
			case (int) 32:
				str = "自然人客户扩展信息2";
			break;
			case (int) 33:
				str = "自然人客户扩展信息3";
			break;
			case (int) 34:
				str = "自然人客户扩展信息4";
			break;
			case (int) 35:
				str = "自然人客户扩展信息5";
			break;
			case (int) 36:
				str = "自然人客户扩展信息6";
			break;
			case (int) 37:
				str = "自然人客户扩展信息7";
			break;
			case (int) 38:
				str = "自然人客户扩展信息8";
			break;
		}
		
		return (str);
	}
	
	public static String  getFieldName(long FieldID) throws Exception
	{
		String str = "";
		switch((int) FieldID)
		{
			case (int) 1:
				str = new CustomFieldCmd().getFieldname(1);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户属性一";
				}
			break;
			case (int) 2:
				str = new CustomFieldCmd().getFieldname(2);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户属性二";
				}
			break;
			case (int) 3:
				str = new CustomFieldCmd().getFieldname(3);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户属性三";
				}
			break;
			case (int) 4:
				str = new CustomFieldCmd().getFieldname(4);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户属性四";
				}				
			break;
			case (int) 5:
				str = new CustomFieldCmd().getFieldname(5);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户属性五";
				}				
			break;
			case (int) 6:
				str = new CustomFieldCmd().getFieldname(6);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户属性六";
				}				
			break;
			case (int) 11:
				str = new CustomFieldCmd().getFieldname(11);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户扩展属性1";
				}				
			break;
			case (int) 12:
				str = new CustomFieldCmd().getFieldname(12);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户扩展属性2";
				}				
			break;
			case (int) 13:
				str = new CustomFieldCmd().getFieldname(13);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户扩展属性3";
				}				
			break;
			case (int) 14:
				str = new CustomFieldCmd().getFieldname(14);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户扩展属性4";
				}				
			break;
			case (int) 15:
				str = new CustomFieldCmd().getFieldname(15);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户扩展属性5";
				}				
			break;
			case (int) 16:
				str = new CustomFieldCmd().getFieldname(16);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户扩展属性6";
				}				
			break;
			case (int) 17:
				str = new CustomFieldCmd().getFieldname(17);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户扩展属性7";
				}				
			break;
			case (int) 18:
				str = new CustomFieldCmd().getFieldname(18);
				if((str == null) || str.trim().length()==0)
				{
					str = "客户扩展属性8";
				}					
			break;			
			case (int) 21:
				str = new CustomFieldCmd().getFieldname(21);
				if((str == null) || str.trim().length()==0)
				{
					str = "法人客户扩展信息1";
				}				
			break;
			case (int) 22:
				str = new CustomFieldCmd().getFieldname(22);
				if((str == null) || str.trim().length()==0)
				{
					str = "法人客户扩展信息2";
				}				
			break;
			case (int) 23:
				str = new CustomFieldCmd().getFieldname(23);
				if((str == null) || str.trim().length()==0)
				{
					str = "法人客户扩展信息3";
				}				
			break;
			case (int) 24:
				str = new CustomFieldCmd().getFieldname(24);
				if((str == null) || str.trim().length()==0)
				{
					str = "法人客户扩展信息4";
				}				
			break;
			case (int) 25:
				str = new CustomFieldCmd().getFieldname(25);
				if((str == null) || str.trim().length()==0)
				{
					str = "法人客户扩展信息5";
				}				
			break;
			case (int) 26:
				str = new CustomFieldCmd().getFieldname(26);
				if((str == null) || str.trim().length()==0)
				{
					str = "法人客户扩展信息6";
				}				
			break;
			case (int) 27:
				str = new CustomFieldCmd().getFieldname(27);
				if((str == null) || str.trim().length()==0)
				{
					str = "法人客户扩展信息7";
				}				
			break;
			case (int) 28:
				str = new CustomFieldCmd().getFieldname(28);
				if((str == null) || str.trim().length()==0)
				{
					str = "法人客户扩展信息8";
				}				
			break;
			case (int) 31:
				str = new CustomFieldCmd().getFieldname(31);
				if((str == null) || str.trim().length()==0)
				{
					str = "自然人客户扩展信息1";
				}				
			break;
			case (int) 32:
				str = new CustomFieldCmd().getFieldname(32);
				if((str == null) || str.trim().length()==0)
				{
					str = "自然人客户扩展信息2";
				}				
			break;
			case (int) 33:
				str = new CustomFieldCmd().getFieldname(33);
				if((str == null) || str.trim().length()==0)
				{
					str = "自然人客户扩展信息3";
				}				
			break;
			case (int) 34:
				str = new CustomFieldCmd().getFieldname(34);
				if((str == null) || str.trim().length()==0)
				{
					str = "自然人客户扩展信息4";
				}				
			break;
			case (int) 35:
				str = new CustomFieldCmd().getFieldname(35);
				if((str == null) || str.trim().length()==0)
				{
					str = "自然人客户扩展信息5";
				}				
			break;
			case (int) 36:
				str = new CustomFieldCmd().getFieldname(36);
				if((str == null) || str.trim().length()==0)
				{
					str = "自然人客户扩展信息6";
				}				
			break;
			case (int) 37:
				str = new CustomFieldCmd().getFieldname(37);
				if((str == null) || str.trim().length()==0)
				{
					str = "自然人客户扩展信息7";
				}				
			break;
			case (int) 38:
				str = new CustomFieldCmd().getFieldname(38);
				if(str == null  || (str.trim().length()==0) )
				{
					str = "自然人客户扩展信息8";
				}				
			break;
		}		
		return (str);
	}
	
	public static String  getName(long ID) throws Exception
	{
		String str = "";
		
		if(ID>0)
		{
			str = new ClientDAO(Database.getConnection()).findname(ID);
		}
	
		return (str);
	}
	
	public static String formatLong(long id) throws Exception
	{
		String str = "";
		if(id != -1)
		{
			str = String.valueOf(id);
		}
		return str;
	}
	
	public static String getAttributename(long id)
	{
		String s = null;
		try {
			
			s = new ClientAttributeCmd().getAttributename(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public static String  getManageName(long ID) throws Exception
	{
		String str = "";
		Connection conn = Database.getConnection();
		 
		if(ID>0)
		{
			str = new ClientDAO(conn).findmanagename(ID);
		}
	    if(conn != null){
	    	conn.close();
	    	conn = null;
	    }
		return (str);
	}
	/**
	 * 获得母公司信息
	 * @author chuanliu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static String parentName(long id)
	{
		String s = null;
		try {
			
			s = new ClientCmd().parentName(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	public static class AttachParentType
	{
		//文档父类型
		public static final long SEAL =1; //印鉴
		public static final long LICENSE =11; //执照
		public static final long CONFIG =3; //结构图
		
	}
	
	public static class ClientBaseType
	{
		public static final String CORPORATION = "1";
		public static final String NATURE = "2";
	}
	
	public static void main(String[] args)
	{
		long id = 5;
		System.out.println(CMConstant.getAttributename(id));
	}

	public static String  getFieldName(long FieldID, long officeID,long currencyID) throws Exception
	{
		String str = "";
		str = new CustomFieldCmd().getFieldname(FieldID,officeID,currencyID);
		if((str == null) || str.trim().length()==0)
		{
			str = "empty";
		}
		return (str);
	}
	
	/**
	 * 增加“高管人员类别”的常量和下拉列表操作 2008-11-6增加 
	 * @author kaishao
	 *
	 */
	public static class ManagementType
	{
		//定义高层管理人员类别
		public static final long LEGALREPRESENTATIVE = 1;	//法定代表人
		public static final long GENERAL = 2;	//总经理
		public static final long FINANCIALDEPARTMENT = 3;	//财务负责人
		
		private long ManagementTypeCode;
		private String ManagementTypeName;
		public long getManagementTypeCode() {
			return ManagementTypeCode;
		}

		public void setManagementTypeCode(long managementTypeCode) {
			ManagementTypeCode = managementTypeCode;
		}

		public String getManagementTypeName() {
			return ManagementTypeName;
		}

		public void setManagementTypeName(String managementTypeName) {
			ManagementTypeName = managementTypeName;
		}
		
		/**
		 * 获取高管人员类别名称
		 * @param lCode
		 * @return
		 */
		public static final String getManagementType(long lCode) {
			String strReturn = ""; // 初始化返回值
			switch ((int) lCode) {
			case (int) LEGALREPRESENTATIVE:
				strReturn = "法定代表人";
				break;
			case (int) GENERAL:
				strReturn = "总经理";
				break;
			case (int) FINANCIALDEPARTMENT:
				strReturn = "财务负责人";
				break;
			}
			return strReturn;
		}
		
		/**
		 * @return
		 */
		public static final long[] getAllManagementType() {
			long[] lTemp = { LEGALREPRESENTATIVE, GENERAL, FINANCIALDEPARTMENT };
			return lTemp;
		}

		/**
		 * @param officeID
		 * @param currencyID
		 * @return
		 */
		public static final long[] getAllManagementType(long officeID, long currencyID) {
			return Constant
					.getAllCode(
							"com.iss.itreasury.clientmanage.util.CMConstant$ManagementType",
							officeID, currencyID);
		}
		
		/**
		 * 高管人员下拉列表使用方法
		 * @return
		 */
		public static Vector getManagementType(){
			ManagementType managementtype=null;
			Vector v = new Vector();
			long[] managementtypecode = getAllManagementType();
			for (int i = 0; i < managementtypecode.length; i++) {
				String managementtypename = getManagementType(managementtypecode[i]);
				long managementTypeCode = managementtypecode[i];
				managementtype=new ManagementType();
				managementtype.setManagementTypeCode(managementTypeCode);
				managementtype.setManagementTypeName(managementtypename);
				v.add(managementtype);	
			}
			return v;
		}
		//关于高管类别的增加结束
		
	}
	
    /**
     * 显示下拉列表(贷款查询用)
     * 
     * @param out
     * @param strControlName,
     *            控件名称
     * @param lAttributeID                 
     * @param lSelectValue
     * @param isNeedAll，是否需要”全部项“
     * @param isNeedBlank
     *            是否需要空白行
     * @param strProperty
     * @param lOfficeID
     * @param lCurrencyID
     */
    public static void showList(JspWriter out, String strControlName,long lAttributeID, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
    {
        long[] lArrayID = null;
        String[] strArrayName = null;
        try
        {                
        	ClientAttributeCmd clientAttributeCmd = new ClientAttributeCmd();
        	Vector clientAttributeCmdVector = clientAttributeCmd.findByAttributeID(lAttributeID,lOfficeID,lCurrencyID);
        	if(clientAttributeCmdVector!=null)
        	{
        		lArrayID = new long[clientAttributeCmdVector.size()];
        		strArrayName = new String[clientAttributeCmdVector.size()];
				for(int i=0;i<clientAttributeCmdVector.size();i++)
				{
					ClientAttributeInfo clientAttributeInfo = (ClientAttributeInfo)clientAttributeCmdVector.get(i);
					lArrayID[i] = clientAttributeInfo.getID();
					strArrayName[i] = clientAttributeInfo.getName();
				}
        	}
        	
            showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
        }
        catch (Exception ex)
        {
            Log.print(ex.toString());
        }
    }
    
    //是否控股人
    public static class StockHolderType
    {
    	public static final long isStockHolder = 1; //是
    	public static final long notStockHolder = 2; //否
    	
    	public static final String getStockHolderType(long sign)
    	{
    		String StockHolderType = "";
    		switch((int)sign){
    			case(int)isStockHolder:
    				StockHolderType = "是";
    				break;
    			case(int)notStockHolder:
    				StockHolderType = "否";
				break;
    		}
    		
    		return StockHolderType;
    		
    	}
    	//是否控股人下拉菜单
    	public static final void showStockHolderType(JspWriter out,String strControlName,long lSelectValue,String strProperty)
    	{
    		long[] lArrayID = null;
    		String[] strArrayName = null;
    		try
    		{
    			lArrayID = new long[]{isStockHolder,notStockHolder};
    			strArrayName = new String[lArrayID.length];
    			for(int i=0;i<lArrayID.length;i++)
    			{
    				strArrayName[i] = getStockHolderType(lArrayID[i]);
    			}
    			out.print("<select class='box' name='"+strControlName+"'"+strProperty+">");
    			for(int i=0;i<lArrayID.length;i++)
    			{
    				if(lArrayID[i]==lSelectValue)
    				{
    					out.print("<option value='"+lArrayID[i]+"' selected>"+strArrayName[i]+"</option>");
    					
    				}
    				else
    				{
    					out.print("<option value='"+lArrayID[i]+"'>"+strArrayName[i]+"</option>");
    				}
    				
    			}
    			out.print("</select>");
    			
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    			System.out.println(e.toString());
    		}
    	}

    	
    }
    
    //报表类型
    public static class ClientReportType
    {
    	public static final long MONTH = 1; //月报
    	public static final long QUARTER = 2; //季报
    	public static final long HALFYEAR = 3; //半年报
    	public static final long YEAR = 4; //年报
    	
    	public static final String getClientReportType(long sign)
    	{
    		String clientReportType = "";
    		switch((int)sign)
    		{
    			case(int)MONTH:
    				clientReportType = "月报";
    				break;
    			case(int)QUARTER:
    				clientReportType = "季报";
    				break;
    			case(int)HALFYEAR:
    				clientReportType = "半年报";
    				break;
    			case(int)YEAR:
    				clientReportType = "年报";
    				break;
    			
    		}
    		return clientReportType;
    		
    	}
    
    	//报表类型下拉控件
    	public static final void showReportTypeList(JspWriter out,String strControlName,long lSelectValue,String strProperty)
    	{
    		long[] lArrayID = null;
    		String[] strArrayName = null;
    		try
    		{
    			lArrayID = new long[]{MONTH,QUARTER,HALFYEAR,YEAR};
    			strArrayName = new String[lArrayID.length];
    			for(int i=0;i<strArrayName.length;i++)
    			{
    				strArrayName[i] = getClientReportType(lArrayID[i]);
    			}
    			out.println("<select class='select' name='"+strControlName+"'"+strProperty+">");
    	
    			for(int i=0;i<lArrayID.length;i++)
    			{
    				if(lArrayID[i]==lSelectValue)
    				{
    					out.println("<option value='"+lArrayID[i]+"' selected>"+strArrayName[i]+"</option>");
    				}
    				else{
    					out.println("<option value='"+lArrayID[i]+"'>"+strArrayName[i]+"</option>");
    				}
    			}
    		
    			out.println("</select>");
    			
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    			System.out.println(e.toString());
    		}
    	}
    }
    
    public static class DepositAndLoanMessage
    {
    	public static final void showEconomicDepartment(JspWriter out,String controlName,String isSelectValue,String strProperty,boolean isNeedBlank) throws Exception
    	{
    		try
    		{
	    		Clientmanage biz = new Clientmanage();
	    		ArrayList list = new ArrayList();
	    		ArrayList codeList = new ArrayList();
	    		ArrayList nameList = new ArrayList();
	    		list = biz.getEconomicDepartmentList();
	    		if(list!=null)
	    		{
	    			EconomicDepartmentInfo info = null;
	    			Iterator it = list.iterator();
	    			while(it.hasNext())
	    			{
	    				info = (EconomicDepartmentInfo)it.next();
	    				codeList.add(info.getDepartmentCode());
	    				nameList.add(info.getDepartmentName());
	    			}
	    		}
	    		String[] code = (String[])codeList.toArray(new String[]{});
	    		String[] name = (String[])nameList.toArray(new String[]{});
	    		showSelectList(out,controlName,code,name,isSelectValue,strProperty,isNeedBlank);
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    			throw new Exception("获取国民经济部门下拉列表出错!",e);
    		}
    	}
    	
    	public static final void showEconomicComponent(JspWriter out,String controlName,String isSelectValue,String strProperty,boolean isNeedBlank) throws Exception   	
    	{
    		try
    		{
	    		Clientmanage biz = new Clientmanage();
	    		ArrayList list = new ArrayList();
	    		ArrayList codeList = new ArrayList();
	    		ArrayList nameList = new ArrayList();    	
	    		list = biz.getEconomicComponentList();
	    		if(list!=null)
	    		{
	    			EconomicComponentInfo info = null;
	    			Iterator it = list.iterator();
	    			while(it.hasNext())
	    			{
	    				info = (EconomicComponentInfo)it.next();
	    				codeList.add(info.getComponentCode());
	    				nameList.add(info.getComponentTypeName());
	    			}
	    		}
	    		String[] code = (String[])codeList.toArray(new String[]{});
	    		String[] name = (String[])nameList.toArray(new String[]{});	    
	    		showSelectList(out,controlName,code,name,isSelectValue,strProperty,isNeedBlank);
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    			throw new Exception("获取经济成分下拉列表出错!",e);
    		}
    	}
    	
    	public static final void showEnterpriseSize(JspWriter out,String controlName,String isSelectValue,String strProperty,boolean isNeedBlank) throws Exception   	
    	{
    		try
    		{
	    		Clientmanage biz = new Clientmanage();
	    		ArrayList list = new ArrayList();
	    		ArrayList codeList = new ArrayList();
	    		ArrayList sizeList = new ArrayList();    
	    		list = biz.getEnterpriseSizeList();
	    		if(list!=null)
	    		{
	    			EnterpriseSizeInfo info = null;
	    			Iterator it = list.iterator();
	    			while(it.hasNext())
	    			{
	    				info = (EnterpriseSizeInfo)it.next();
	    				codeList.add(info.getCode());
	    				sizeList.add(info.getSizeName());
	    			}
	    		}
	    		String[] code = (String[])codeList.toArray(new String[]{});
	    		String[] size = (String[])sizeList.toArray(new String[]{});	     
	    		showSelectList(out,controlName,code,size,isSelectValue,strProperty,isNeedBlank);
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    			throw new Exception("获取企业规模下拉列表出错!",e);
    		}
    	}
    	
    	
    	private static final void showSelectList(JspWriter out,String controlName,String[] value,String[] name,String isSelectValue,String strProperty,boolean isNeedBlank) throws Exception
    	{
    		try
    		{
    			out.println("<select id='"+controlName+"' name='"+controlName+"' "+strProperty+" class='select'>");
    			if(isNeedBlank)
    			{
    				out.println("<option value=''></option>");
    			}
    			if(name!=null&&value!=null)
    			{
	    			for(int i=0;i<value.length;i++)
	    			{
	    				if(isSelectValue!=null&&value[i].equals(isSelectValue))
	    				{
	    					out.println("<option value='"+value[i]+"' selected>"+name[i]+"</option>");
	    				}
	    				else
	    				{
	    					out.println("<option value='"+value[i]+"'>"+name[i]+"</option>");
	    				}
	    			}
    			}
    			out.println("</select>");
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    			throw new Exception("获取下拉列表出错!",e);
    		}
    	}
        public static class Area
    	{
        	public static final long PROVINCE = 1;  //省
        	public static final long CITY = 2;  //市
        	public static final long COUNTY = 3;  //  区/县
        	public static final void showProvince(JspWriter out,String controlName,String isSelectValue,String strProperty,boolean isNeedBlank)throws Exception   	
        	{
        		try
        		{
    	    		Clientmanage biz = new Clientmanage();
    	    		ArrayList list = new ArrayList();
    	    		list = biz.getProvinceList();
    	    		showArea(list,out,controlName,isSelectValue,strProperty,isNeedBlank);
        		}catch(Exception e)
        		{
        			e.printStackTrace();
        			throw new Exception("获取地区信息下拉列表出错!",e);
        		}        		
        	}
        	
        	public static final void showCity(JspWriter out,String controlName,String isSelectValue,String provinceCode,String strProperty,boolean isNeedBlank)throws Exception 
        	{
        		if(provinceCode==null||provinceCode.equals(""))
        		{
        			showSelectList(out,controlName,null,null,isSelectValue,strProperty,isNeedBlank);
        		}
        		else
        		{
    	    		Clientmanage biz = new Clientmanage();
    	    		ArrayList list = new ArrayList();  
    	    		list = biz.getCityList(provinceCode);
    	    		showArea(list,out,controlName,isSelectValue,strProperty,isNeedBlank);
        		}
        	}
        	
        	public static final void showCounty(JspWriter out,String controlName,String isSelectValue,String cityCode,String strProperty,boolean isNeedBlank)throws Exception 
        	{
        		if(cityCode==null||cityCode.equals(""))
        		{
        			showSelectList(out,controlName,null,null,isSelectValue,strProperty,isNeedBlank);
        		}
        		else
        		{
    	    		Clientmanage biz = new Clientmanage();
    	    		ArrayList list = new ArrayList();  
    	    		list = biz.getCountyList(cityCode);
    	    		showArea(list,out,controlName,isSelectValue,strProperty,isNeedBlank);
        		}
        	}
        	
        	
        	
        	private static final void showArea(ArrayList showList,JspWriter out,String controlName,String isSelectValue,String strProperty,boolean isNeedBlank) throws Exception
        	{
	    		ArrayList codeList = new ArrayList();
	    		ArrayList nameList = new ArrayList();   
	    		if(showList!=null)
	    		{
	    			ClientAreaInfo info = null;
	    			Iterator it = showList.iterator();
	    			while(it.hasNext())
	    			{
	    				info = (ClientAreaInfo)it.next();
	    				codeList.add(info.getAreaCode());
	    				nameList.add(info.getAreaName());
	    			}
	    		}
	    		String[] code = (String[])codeList.toArray(new String[]{});
	    		String[] size = (String[])nameList.toArray(new String[]{});	     
	    		showSelectList(out,controlName,code,size,isSelectValue,strProperty,isNeedBlank);	    		
        	}
        	
    	}
        
        public static class Industry
        {
        	public static final long CATEGORY = 1;  //门类
        	public static final long BIGCATEGORY = 2;  //大类
        	public static final long MEDIUMCATEGORY = 3;  //中类
        	public static final long SMALLCATEGORY = 4;  //小类
        	
        	public static final void showCategory(JspWriter out,String controlName,String isSelectValue,String strProperty,boolean isNeedBlank)throws Exception 
        	{
        		try
        		{
    	    		Clientmanage biz = new Clientmanage();
    	    		ArrayList list = new ArrayList();
    	    		list = biz.getCategoryList();
    	    		showIndustryList(list,out,controlName,isSelectValue,strProperty,isNeedBlank);
        		}catch(Exception e)
        		{
        			e.printStackTrace();
        			throw new Exception("获取行业信息下拉列表出错!",e);        			
        		}
        	}
        	
        	public static final void showBigCategory(JspWriter out,String controlName,String isSelectValue,String categoryValue,String strProperty,boolean isNeedBlank)throws Exception 
        	{
        		if(categoryValue==null||categoryValue.equals(""))
        		{
        			showSelectList(out,controlName,null,null,isSelectValue,strProperty,isNeedBlank);
        		}
        		else
        		{
    	    		Clientmanage biz = new Clientmanage();
    	    		ArrayList list = new ArrayList();     
    	    		list = biz.getBigCategoryList(categoryValue);
    	    		showIndustryList(list,out,controlName,isSelectValue,strProperty,isNeedBlank);
        		}
        	}
        	
        	private static final void showIndustryList(ArrayList showList,JspWriter out,String controlName,String isSelectValue,String strProperty,boolean isNeedBlank) throws Exception
        	{
	    		ArrayList codeList = new ArrayList();
	    		ArrayList typeList = new ArrayList();       
	    		if(showList!=null)
	    		{
	    			EconomicIndustryInfo info = null;
	    			Iterator it = showList.iterator();
	    			while(it.hasNext())
	    			{
	    				info = (EconomicIndustryInfo)it.next();
	    				codeList.add(info.getIndustryCode());
	    				typeList.add(info.getIndustryType());
	    			}
	    		}  
	    		String[] code = (String[])codeList.toArray(new String[]{});
	    		String[] type = (String[])typeList.toArray(new String[]{});	    	    		
	    		showSelectList(out,controlName,code,type,isSelectValue,strProperty,isNeedBlank);        		
        	}
        	
        }

    }

  
    
}
