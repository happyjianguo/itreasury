/**
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.iss.itreasury.settlement.setting.bizlogic.OfficeBiz;
import com.iss.itreasury.system.privilege.dataentity.QueryOfficeInfo;
import com.iss.itreasury.system.privilege.dataentity.Sys_UserAuthorityInfo;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.dataentity.CurrencyInfo;
import com.iss.itreasury.dataentity.OfficeInfo;

/**
 * 包含公共的常量 
 * 
 * @author Forest Ming
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Constant
{
	public static final long SUCCESSFUL = 1; //操作成功
	
	public static final long FAIL = 0; //操作失败 
	
    public static final long TRUE = 1; //是

    public static final long FALSE = 0; //否

    public static final long PASSWORD_MAX_LENGTH = 20;		//默认密码最大长度
    
    public static final long PASSWORD_MIN_LENGTH = 6;		//默认密码最小长度
    
    public static final long EBANK_USER = 1;					//网银用户
    
    public static final long SETT_USER = 0;					//财务用户
    
    public static final String FINEREPORT_URL = "/NASApp/ReportServer?";//add by xfma
	
    private static AbstractConstantManager constantManager = AbstractConstantManager.createConstantManager();
    
    public static final int NETBANK = 2;                    //网银用户 //jzw 2010-05-21 
    public static final int SETTUSER = 1;                    //结算用户
    public static String SDCURL ="#";
    
    /**
     * 显示通用下拉列表
     * 
     * @param out
     * @param strControlName
     * @param lArrayID
     * @param strArrayName
     * @param lSelectValue
     * @param isNeedAll
     * @param strProperty
     */
    public static void showCommonList(JspWriter out, String strControlName, long[] lArrayID, String[] strArrayName, long lSelectValue, boolean isNeedAll,
            String strProperty)
    {
        showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, true,false);
    }

    /**
     * 下拉列表排序, zwsun, 2007/7/15
     *  <code>       	
     *  	SelectSorter selecSorter=new SelectSorter(lArrayID, strArrayName);
     *   	selecSorter.sort();
     *   	lArrayID=selecSorter.getLArrayID();
     *   	strArrayName=selecSorter.getStrArrayName();
     * </code>
     */
    public static class SelectSorter{
    	private class SelectArray{
        	long lArrayID;
        	String strArrayName;
        	public SelectArray(long lArrayID, String strArrayName) {
				this.lArrayID=lArrayID;
				this.strArrayName=strArrayName;
			}
    	}
    	private class selectComparater implements Comparator{
    		Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
    		public int compare(Object o1, Object o2) {
    			SelectArray s1=(SelectArray)o1;
    			SelectArray s2=(SelectArray)o2; 
    			return cmp.compare(s1.strArrayName, s2.strArrayName);
    		}
    	}
    	private ArrayList selectArray;
    	public SelectSorter(long[] lArrayID, String[] strArrayName) throws Exception{
    		if(lArrayID==null ||strArrayName==null || lArrayID.length!=strArrayName.length){
    			throw new Exception("初始化出错");
    		}else{   			
    			selectArray=new ArrayList();
    			for(int i=0;i<lArrayID.length;i++){
    				selectArray.add(new SelectArray(lArrayID[i],strArrayName[i]));
    			}
    		}
    	}
    	public long[] getLArrayID(){
    		long[] lArrayID=new long[selectArray.size()];
    		for(int i=0;i<lArrayID.length;i++){
    			lArrayID[i]=((SelectArray)selectArray.get(i)).lArrayID;
    		}
    		return lArrayID;
    	}
    	public String[] getStrArrayName(){
    		String[] strArrayName=new String[selectArray.size()];
    		for(int i=0;i<strArrayName.length;i++){
    			strArrayName[i]=((SelectArray)selectArray.get(i)).strArrayName;
    		}
    		return strArrayName;
    	}
    	public void sort(){
			Collections.sort(selectArray, new selectComparater());    		
    	}
    }
    
   
    
    /**
     * 显示通用下拉列表
     * 
     * @param out
     * @param strControlName
     * @param lArrayID
     * @param strArrayName
     * @param lSelectValue
     * @param isNeedAll
     * @param strProperty
     * @param isNeedBlank
     */
    public static void showCommonList(JspWriter out, String strControlName, long[] lArrayID, String[] strArrayName, long lSelectValue, boolean isNeedAll,
            String strProperty, boolean isNeedBlank)
    {    	   	
    	try
        {
            //增加排序， zwsun , 2007/7/15
        	SelectSorter selecSorter=new SelectSorter(lArrayID, strArrayName);
        	selecSorter.sort();
        	lArrayID=selecSorter.getLArrayID();
        	strArrayName=selecSorter.getStrArrayName();
        	
            out.println("<select class=\"select\" id=\"" + strControlName + "\" name=\"" + strControlName + "\" " + strProperty + ">");
            if (isNeedBlank == true)
            {
                if (lSelectValue == -1)
                {
                    out.println("<option value='-1' selected>&nbsp;</option>");
                }
                else
                {
                    out.println("<option value='-1'>&nbsp;</option>");
                }
            }
            if (isNeedAll == true) 
            {
            	if (lSelectValue == 0)
            	{
            		out.println("<option value='0' selected>全部</option>");
            	}
            	else
            	{
            		out.println("<option value='0'>全部</option>");
            	}
            }
            for (int i = 0; i < lArrayID.length; i++)
            {
                Log.print("lArrayID[i] = " + lArrayID[i]);
                Log.print("lSelectValue = " + lSelectValue);
                if (lArrayID[i] == lSelectValue)
                {
                    out.println("<option value='" + lArrayID[i] + "' selected >" + strArrayName[i] + "</option>");
                }
                else
                {
                    out.println("<option value='" + lArrayID[i] + "'>" + strArrayName[i] + "</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception ex)
        {
            Log.print("显示下拉列表出现异常：" + ex.toString());
        }
    }

    //Boxu Add 2008年12月16日 不需要排序
    public static void showCommonList(JspWriter out, String strControlName, long[] lArrayID, String[] strArrayName, long lSelectValue, boolean isNeedAll,
            String strProperty, boolean isNeedBlank, boolean blOrder)
    {    	   	
    	try
        {
            out.println("<select class=\"select\" id=\"" + strControlName + "\" name=\"" + strControlName + "\" " + strProperty + ">");
            if (isNeedBlank == true)
            {
                if (lSelectValue == -1)
                {
                    out.println("<option value='-1' selected>&nbsp;</option>");
                }
                else
                {
                    out.println("<option value='-1'>&nbsp;</option>");
                }
            }
            if (isNeedAll == true) 
            {
            	if (lSelectValue == 0)
            	{
            		out.println("<option value='0' selected>全部</option>");
            	}
            	else
            	{
            		out.println("<option value='0'>全部</option>");
            	}
            }
            for (int i = 0; i < lArrayID.length; i++)
            {
                Log.print("lArrayID[i] = " + lArrayID[i]);
                Log.print("lSelectValue = " + lSelectValue);
                if (lArrayID[i] == lSelectValue)
                {
                    out.println("<option value='" + lArrayID[i] + "' selected >" + strArrayName[i] + "</option>");
                }
                else
                {
                    out.println("<option value='" + lArrayID[i] + "'>" + strArrayName[i] + "</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception ex)
        {
            Log.print("显示下拉列表出现异常：" + ex.toString());
        }
    }
    
    
    public static void showCommonList_BankPay(JspWriter out, String strControlName, long[] lArrayID, String[] strArrayName, long lSelectValue, boolean isNeedAll,
            String strProperty, boolean isNeedBlank, boolean blOrder)
    {    	   	
    	try
        {
            out.println("<select class=\"select\" id=\"" + strControlName + "\" name=\"" + strControlName + "\" " + strProperty + ">");
            if (isNeedBlank == true)
            {
                if (lSelectValue == -1)
                {
                    out.println("<option value='-1' selected>&nbsp;</option>");
                }
                else
                {
                    out.println("<option value='-1'>&nbsp;</option>");
                }
            }
           
            for (int i = 0; i < lArrayID.length; i++)
            {
                Log.print("lArrayID[i] = " + lArrayID[i]);
                Log.print("lSelectValue = " + lSelectValue);
                if (lArrayID[i] == lSelectValue)
                {
                    out.println("<option value='" + lArrayID[i] + "' selected >" + strArrayName[i] + "</option>");
                }
                else
                {
                    out.println("<option value='" + lArrayID[i] + "'>" + strArrayName[i] + "</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception ex)
        {
            Log.print("显示下拉列表出现异常：" + ex.toString());
        }
    }
    
    /**
     * 显示通用下拉列表(值为字符串，证券使用) strSelectValue 为null或空字符串，或字符串“-1”，代表未选中
     * strSelectValue 字符串“0”，代表未选中
     * 
     * @param out
     * @param strControlName
     * @param strArrayValue
     * @param strArrayName
     * @param strSelectValue
     * @param isNeedAll
     * @param strProperty
     * @param isNeedBlank
     */
    public static void showCommonList(JspWriter out, String strControlName, String[] strArrayValue, String[] strArrayName, String strSelectValue,
            boolean isNeedAll, String strProperty, boolean isNeedBlank)
    {
        try
        {
            out.println("<select class=\"select\" id=\"" + strControlName + "\" name=\"" + strControlName + "\" " + strProperty + ">");
            if (isNeedBlank == true)
            {
                if (strSelectValue == null || strSelectValue.equals("-1"))
                {
                    out.println("<option value='-1' selected>&nbsp;</option>");
                }
                else
                {
                    out.println("<option value='-1'>&nbsp;</option>");
                }
            }
            for (int i = 0; i < strArrayValue.length; i++)
            {
                Log.print("lArrayID[i] = " + strArrayValue[i]);
                Log.print("lSelectValue = " + strSelectValue);
                if (strArrayValue[i].equals(strSelectValue))
                {
                    out.println("<option value='" + strArrayValue[i] + "' selected >" + strArrayName[i] + "</option>");
                }
                else
                {
                    out.println("<option value='" + strArrayValue[i] + "'>" + strArrayName[i] + "</option>");
                }
            }
            if (isNeedAll == true)
            {
                if (strSelectValue != null && strSelectValue.equals("0"))
                {
                    out.println("<option value='0' selected>全部</option>");
                }
                else
                {
                    out.println("<option value='0'>全部</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception ex)
        {
            Log.print("显示下拉列表出现异常：" + ex.toString());
        }
    }

    public static void main(String[] args)
    {
    	String a = Constant.CurrencyType.getName(1);
    	System.out.println("============="+a);
    	
    	System.out.println("============="+LoggerOfOperationType.isNeedLog(30));
    }

    public static final long CODE_OB_VIRTUAL_ID = -666; //网银虚拟ID

    public static final long CODE_RECORD_ALL = 0; //全部

    public static final int CYC_NUM = 50; //循环次数

    public static class ShowMenu
    {
        //是否显示菜单
        public static final long YES = 1; //显示菜单

        public static final long NO = 2; //隐藏菜单
    }
    
    //Added by zwsun, 2007-06-13, 权限分离
    public static class PurviewType
    {
    	//权限类型
    	public static final long SysModuleType=1;//系统模块权限
    	public static final long BizModuleType=-1;//业务模块权限
    }
    
    //Added by jiangqi, 2011-01-24, 权限分离,是否是指纹管理员
    public static class FingerPrintType
    {
    	//权限类型
    	public static final long IsFingerPrintType=1;//是指纹管理员
    	public static final long NotFingerPrintType=-1;//不是指纹管理员
    }
    
    public static class ForeignCurreny
    {
        //外汇币种
        public static final long USD = 2; //美元

        public static final long UKP = 3; //英镑

        public static final long ED = 4; //欧元
    }

    public static class UserGruopLever
    {
        //用户组权限
        public static final long HIGH = 1; //高

        public static final long MIDDLE = 2; //中

        public static final long LOW = 3; //低

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) HIGH:
                    strReturn = "高";
                    break;
                case (int) MIDDLE:
                    strReturn = "中";
                    break;
                case (int) LOW:
                    strReturn = "低";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { HIGH, MIDDLE };
            return lTemp;
        }
        public static final long[] getAllCode(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.util.Constant$UserGruopLever",officeID,currencyID);
        }        
    }

    public static class ILogType
    {
        //日志类型
        public static final long INSERT = 1; //增加

        public static final long UPDATE = 2; //修改

        public static final long DELETE = 3; //删除
        
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) INSERT:
                    strReturn = "增加";
                    break;
                case (int) UPDATE:
                    strReturn = "修改";
                    break;
                case (int) DELETE:
                    strReturn = "删除";
                    break;
            }
            return strReturn;
        }
    }
   
    public static class RecordStatus
    { 
        //表中记录的nStatus
        public static final long VALID = 1; //有效

        public static final long INVALID = 0; //无效(删除)
        
        public static final long STASIS =2; //停滞

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) VALID:
                    strReturn = "有效";
                    break;
                case (int) INVALID:
                    strReturn = "无效";
                	break;
                case (int) STASIS:
                	strReturn = "停滞";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { VALID, INVALID,STASIS };
            return lTemp;
        }
        public static final long[] getAllCode(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.util.Constant$RecordStatus",officeID,currencyID);
        }                
    }

  

    
    
    public static class YesOrNo
    {
        //是否
        public static final long YES = 1; //是

        public static final long NO = 2; //否

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) YES:
                    strReturn = "是";
                    break;
                case (int) NO:
                    strReturn = "否";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { YES, NO };
            return lTemp;
        }
        public static final long[] getAllCode(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.util.Constant$YesOrNo",officeID,currencyID);
        }                
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
                String strProperty)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                Log.print(ex.toString());
            }
        }
    }
    public static class OperateLog
    {
        //是否
        public static final long Corp = 1; //是

        public static final long Other = 2; //否

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) Corp:
                    strReturn = "客户信息修改";
                    break;
                case (int) Other: 
                    strReturn = "其他信息修改";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { Corp, Other };
            return lTemp;
        }
/*        public static final long[] getAllCode(long officeID,long currencyID)
        {
       	return Constant.getAllCode("com.iss.itreasury.util.Constant$YesOrNo",officeID,currencyID);
        }*/                
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
                String strProperty)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                Log.print(ex.toString());
            }
        }
    }

    /**
     * 币种类型
     * 
     * @author wlming
     * 
     * To change the template for this generated type comment go to
     * Window>Preferences>Java>Code Generation>Code and Comments
     */
    public static class CurrencyType
    {
        //币种：来自于中油一期----//haier 最新币种
        public static final long RMB = 1; //人民币---------------/不统一/ CNY
        public static final long USD = 2; //美元
        public static final long ED = 3; //欧元---------------/不统一/ EUR
        public static final long ASF = 4; //记账瑞士法郎
        public static final long ATS = 5; //奥地利先令
        public static final long AUD = 6; //澳大利亚元
        public static final long BEF = 7; //比利时法郎
        public static final long BRL = 8; //巴西里亚尔
        public static final long CAD = 9; //加拿大元
        public static final long CHF = 10; //瑞士法郎
        public static final long DEM = 11; //德国马克
        public static final long DKK = 12; //丹麦克朗
        public static final long ECU = 13; //欧洲货币单位
        public static final long ESP = 14; //西班牙比赛塔
        public static final long FIM = 15; //芬兰马克
        public static final long FRF = 16; //法国法郎
        public static final long UKP = 17; //英国英镑---------------/不统一/ GBP
        public static final long HKD = 18; //港元
        public static final long IDR = 19; //印尼盾
        public static final long INR = 20; //印度卢比
        public static final long IQD = 21; //伊拉克第纳尔
        public static final long IRR = 22; //伊朗里亚尔
        public static final long ITL = 23; //意大利里拉
        public static final long JOD = 24; //约旦第纳尔
        public static final long JP = 25; //日本元---------------/不统一/	JPY
        public static final long KRW = 26; //韩国圆
        public static final long KWD = 27; //科威特第纳尔
        public static final long MOP = 28; //澳门元
        public static final long MXP = 29; //墨西哥比索
        public static final long MYR = 30; //马来西亚林吉特
        public static final long NLG = 31; //荷兰盾
        public static final long NOK = 32; //挪威克朗
        public static final long NPR = 33; //尼泊尔卢比
        public static final long NZD = 34; //新西兰元
        public static final long PHP = 35; //菲律宾比索
        public static final long PKR = 36; //巴基斯坦卢比
        public static final long RUB = 37; //俄国卢布
        public static final long SEK = 38; //瑞典克朗
        public static final long SP = 39; //新加坡元 ---------------/不统一/ SGD      
        public static final long SLL = 40; //赛拉利昂利昂
        public static final long THB = 41; //泰国铢
        public static final long TWD = 42; //台湾元
        public static final long TZS = 43; //坦桑尼亚先令
        public static final long XDR = 44; //特别提款权

        /**
         * 获得币种的描述
         * 
         * @param lCurrencyID
         * @return String
         */
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            try
            {
	            CurrencyInfo info = (CurrencyInfo)Env.getCurrencyHash().get(String.valueOf(lCode));
	            if(info != null)
	            {
	                strReturn = info.getName();
	            }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return strReturn;
        }
        
        /**
         * 获得币种的code
         * 
         * @param lCurrencyID
         * @return String
         */
        public static final String getCode(long lCode)
        {
            String strReturn = ""; //初始化返回值
            try
            {
	            CurrencyInfo info = (CurrencyInfo)Env.getCurrencyHash().get(String.valueOf(lCode));
	            if(info != null)
	            {
	                strReturn = info.getCode();
	            }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return strReturn;
        }

        /**
         * 获得币种的符号 
         * 
         * @param lCurrencyID
         * @return String
         */
        public static final String getSymbol(long lCode)
        {
            String strReturn = ""; //初始化返回值
            try
            {
        		CurrencyInfo info = (CurrencyInfo)Env.getCurrencyHash().get(String.valueOf(lCode));
 	            if(info != null)
 	            {
 	                strReturn = info.getSymbol();
 	            }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return strReturn;
        }

        /**
         * 将银企接口中的币种常量对象转换为当前编码值
         * 
         * @param c
         * @return long
         */
        /*
        public static final long convertFromCurrencyObjectOfBS(com.iss.itreasury.bs.constant.CurrencyType c)
        {
            long result = -1;
            if (c.equals(com.iss.itreasury.bs.constant.CurrencyType.RMB))
            {
                result = RMB;
            }
            else if (c.equals(com.iss.itreasury.bs.constant.CurrencyType.USD))
            {
                result = USD;
            }
            else if (c.equals(com.iss.itreasury.bs.constant.CurrencyType.UKP))
            {
                result = UKP;
            }
            else if (c.equals(com.iss.itreasury.bs.constant.CurrencyType.ED))
            {
                result = ED;
            }
            else if (c.equals(com.iss.itreasury.bs.constant.CurrencyType.JP))
            {
                result = JP;
            }
            else if (c.equals(com.iss.itreasury.bs.constant.CurrencyType.SP))
            {
                result = SP;
            }
            return result;
        }
*/
        /**
         * 将当前编码值转换为银企接口中的币种常量对象
         * 
         * @param c
         * @return long
         */
        /*
        public static final com.iss.itreasury.bs.constant.CurrencyType convertToCurrencyObjectOfBS(long c)
        {
            com.iss.itreasury.bs.constant.CurrencyType result = null;
            if (c == RMB)
            {
                result = com.iss.itreasury.bs.constant.CurrencyType.RMB;
            }
            else if (c == USD)
            {
                result = com.iss.itreasury.bs.constant.CurrencyType.USD;
            }
            else if (c == UKP)
            {
                result = com.iss.itreasury.bs.constant.CurrencyType.UKP;
            }
            else if (c == ED)
            {
                result = com.iss.itreasury.bs.constant.CurrencyType.ED;
            }
            else if (c == JP)
            {
                result = com.iss.itreasury.bs.constant.CurrencyType.JP;
            }
            else if (c == SP)
            {
                result = com.iss.itreasury.bs.constant.CurrencyType.SP;
            }
            return result;
        }
*/
        /**
         * 得到所有的币种(从Env中取得币种数值)
         * 
         * @return long[]
         */
        public static final long[] getAllCode()
        {
        	
        	for(int i=0;i<10;i++)
        		System.out.println("-----------------");
            long[] lTemp = null;
            if(Env.getCurrencyHash()!=null && Env.getCurrencyHash().size()>0)
            {                
	            Iterator it = Env.getCurrencyHash().values().iterator(); 
	            int i = 0;
	            lTemp = new long[Env.getCurrencyHash().size()];
	            while(it.hasNext())
	            {
	            	CurrencyInfo info = (CurrencyInfo)it.next();
	            	lTemp[i] = info.getID();
	            	i++;
	            } 
            }		
            //排序
            java.util.Arrays.sort(lTemp);
            return lTemp;
        }
        
        //modify by leiyang 20081114 配置管理模块走配置
		public static final long[] getAllCode(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.util.Constant$CurrencyType",officeID,currencyID);
        }

        /**
         * 得到最大的值（变量的个数）
         */
        public static final long getLength()
        {
            return Env.getCurrencyHash().size();
        }

        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
                String strProperty)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                    case 1:
                        lArrayID = new long[]
                        { USD, UKP, ED, JP, SP };
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                Log.print(ex.toString());
            }
        }
        
		public static final void showList(JspWriter out, String strControlName, long type, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty, long lOfficeID, long lCurrencyID) {
			long[] lArrayID = null;
			String[] strArrayName = null;
			try {
//				lArrayID = getAllCode(lOfficeID, lCurrencyID);
				/////////////////////////////////////////////////////////jzw 2010-05-06 修改总账接口设置，办事处和币种关联
	            OfficeBiz biz=new OfficeBiz();             
	            lArrayID = biz.findCurrencyIDsByOfficeID(lOfficeID); 
	            /////////////////////////////////////////////////////////
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
			} 
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
    }
    
	/**
	 * 控制页面元素是否必输，是否显示
	 * @author zwsun
	 *
	 */
	public static class PageElementControl{
		//显示
		public static final long DISPLAY=1;
		public static final long NOTDISPLAY=0;
		//输入
		public static final long NEEDINPUT=1;
		public static final long NOTNEEDINPUT=0;
	}
	
    /**
     * 模块类型
     * 
     * @author yzhang
     * 
     * To change the template for this generated type comment go to
     * Window>Preferences>Java>Code Generation>Code and Comments
     */
    public static class ModuleType
    {
        //模块类型
        public static final long SETTLEMENT = 1; //结算

        public static final long LOAN = 2; //贷款

        public static final long SYSTEM = 3; //系统管理

        public static final long FOREIGN = 4; //外汇

     // public static final long SECURITIES = 5; //证券

        public static final long EBANK = 6; //网上银行

        public static final long PLAN = 7; //资金计划(旧) 2008-11-25No.196标记 kaishao

        public static final long CLIENTCENTER = 8; // 客户中心

        public static final long GENERALLEDGER = 9; // 总账接口
        
        public static final long BILL = 10;//票据管理
        
        public static final long BUDGET = 11;//资金预算
        
        public static final long BANKPORTAL = 12;//银企接口

		public static final long MANAGER = 13;//经理查询
		
		public static final long CLIENTMANAGE = 14;//客户信息管理
		
		public static final long CRAFTBROTHER = 15;//同业往来
		
		public static final long EVOUCHER = 16;//电子单据柜

        public static final long TREASURYMONITOR = 17; //资金监控
        
        public static final long REPORT = 18; //报表平台 add by xfma 2008-10-8
        
        public static final long MANAGERQUERY = 19;//领导查询 add by xfma 2008-10-8
        
        public static final long ARCHIVESMANAGEMENT = 20;//档案管理 add by qianggao 
        
        public static final long AUDIT = 21 ; //监审稽核
        
        public static final long FUNDPLAN =22 ;//资金计划(新) 2008-11-25No.196增加 kaishao
        
        public static final long CREDITRATING = 23;//信用评级  add by zcwang 2009-03-03
        
        public static final long SECURITIES = 35;//证券投资 add by xwhe 2010-03-12
        
		public static final long BI1=101;//资金资源系统 add by xiang 2011-07-30
		
		public static final long BI2=102;//资金分析系统 add by xiang 2011-07-30
        /**
         * 得到所有的模块
         * 增加新的“资金计划”模块 2008-11-25No.196增加 kaishao
         * 
         * @return long[]
         */
        public static final long[] getAllCode()
        {
            long[] allCode = null;
                long[] lTemp =
                	//增加“NEWPLAN” 2008-11-25No.196增加 kaishao
                { SETTLEMENT, LOAN,FOREIGN, SYSTEM, SECURITIES, PLAN, FUNDPLAN,
                        CLIENTCENTER,BILL, BANKPORTAL, MANAGER,
                        CRAFTBROTHER,EVOUCHER,TREASURYMONITOR,REPORT,MANAGERQUERY, ARCHIVESMANAGEMENT,AUDIT,CREDITRATING,
                        BI1,BI2};
                //No.196结束
                allCode = lTemp;

                return allCode;
        }
        public static final long[] getAllCode(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.util.Constant$ModuleType",officeID,currencyID);
        }
        
        
       
        /**
         * 
         * @param lCode
         * @return @throws
         *         Exception
         */
        public static final String getName(long lCode) throws Exception
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) SETTLEMENT:
                    strReturn = "结算";
                    break;
                case (int) LOAN:
                    strReturn = "信贷";
                    break;
                case (int) EBANK:
                    strReturn = "网上银行";
                    break;
                case (int) SECURITIES:
                    strReturn = "证券";
                    break;
                case (int) SYSTEM:
                    strReturn = "系统管理";
                    break;
                case (int) FOREIGN:
                    strReturn = "外汇";
                    break;
                case (int) PLAN:
                    strReturn = "资金计划(旧)";
                    break;
                //增加“资金计划”菜单 2008-11-25No.196增加 kaishao    
                case (int) FUNDPLAN:
                    strReturn = "资金计划";
                    break;
                //No.196结束    
                case (int) CLIENTCENTER:
                    strReturn = "客户信息中心";
                    break;
                case (int) GENERALLEDGER:
                    strReturn = "总账接口";
                	break;
                case (int) BILL:
					strReturn = "票据管理";
					break;
                case (int) BUDGET:
					strReturn = "资金预算";
					break;
                case (int) BANKPORTAL:
					strReturn = "银企接口";
					break;
				case (int) MANAGER:
					strReturn = "经理查询";
					break;
				case (int) CLIENTMANAGE:
					strReturn = "客户信息管理";
					break;
				case (int) CRAFTBROTHER:
					strReturn = "同业往来";
					break;
				case (int) EVOUCHER:
					strReturn = "电子单据柜";
					break;
                case (int) TREASURYMONITOR:
                    strReturn = "资金监控";
                    break;
                case (int) REPORT://add by xfma 2008-10-6
                    strReturn = "报表平台";
                    break;
                case (int) MANAGERQUERY://add by xfma 2008-10-8
                    strReturn = "领导查询";
                break;  
                case (int) ARCHIVESMANAGEMENT://add by xfma 2008-10-8
                    strReturn = "档案管理";
                    break;
                case (int) AUDIT://add by xfma 2008-10-8
                    strReturn = "监审稽核";
                break; 
                case (int) CREDITRATING://add by xfma 2008-10-8
                    strReturn = "信用评级";
                break; 
                case (int) BI1:
					strReturn = "资金资源业务系统";
				break;
				case (int) BI2:
					strReturn = "资金资源分析系统";
				break;
            }
            return strReturn;
        }
        
        public static final void showList(JspWriter out, String strControlName,int nType,
				long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
				String strProperty, long[] lArrayID)
		{
			String[] strArrayName = null;
			try 
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
					break;
					case 1 :
						lArrayID = new long[]{SETTLEMENT, LOAN};
					break;
					case 2 :
						//lArrayID = new long[]{SETTLEMENT, LOAN,CREDITRATING,CRAFTBROTHER,EVOUCHER};
						lArrayID = new long[]{SETTLEMENT, LOAN,CREDITRATING,CRAFTBROTHER,EVOUCHER,BILL};//update by dwj 20111214 添加票据模块
					break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++) {
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName,
						lSelectValue, isNeedAll, strProperty, isNeedBlank);
			}
			catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
    }

    
    public static class CustomModule
    {
    	public static final long CUSTOMMODULE = 101; //自定义模块
    }
    /**
     * 分页控制常量
     * 
     * @author wlming
     * 
     * To change the template for this generated type comment go to
     * Window>Preferences>Java>Code Generation>Code and Comments
     */
    public static class PageControl
    {
        //升序、降序
        public static final long CODE_ASCORDESC_ASC = 1; //升序

        public static final long CODE_ASCORDESC_DESC = 2; //降序

        //每页行数
        public static final long CODE_PAGELINECOUNT = 10; //每页行数
        public static final long CODE_PAGELINECOUNT1 = 20; //每页行数

        //分页控制操作
        public static final int FIRSTPAGE = 100; //第一页

        public static final int LASTPAGE = 101; //最后一页

        public static final int NEXTPAGE = 102; //下一页

        public static final int PREVIOUSPAGE = 103; //前一页

        public static final int GOTOPAGE = 104; //跳转到指定页面

        public static final int LISTALL = 105; //查询所有数据

        public static final String SearchResults = "_SearchResults";
        
        public static final String SearchResults1 = "_SearchResults1";
        
        //查询结果的参数名称
    }

    //审批设置-模块类型（同模块类型）
    //审批设置-业务类型
    public static final class ApprovalLoanType
    {
        //业务类型
        public static final long ZYDQ = 1; //自营短期贷款

        public static final long ZYZCQ = 2; //自营中长期贷款

        public static final long WT = 3; //委托贷款

        public static final long WTTJTH = 4; //委托贷款—统借统还

        public static final long TX = 5; //贴现

        public static final long ZGXEDQ = 6; //最高限额短期

        public static final long ZGXEZCQ = 7; //最高限额中长期

        public static final long YTDQ = 8; //银团短期贷款

        public static final long YTZCQ = 10; //银团中长期贷款

        public static final long OTHER = 9; //其他(针对利率调整、合同执行计划更改等与贷款类型无关的业务)

        public static final long ZTX = 11; //转贴现

		public static final long MFXD = 12; //买方信贷
		
		public static final long DB = 13; //担保

        public static final long CAPITAL_LANDING = 20; //资金拆借业务

        public static final long STOCK = 21; //股票

        public static final long CENTRAL_BANK_NOTE = 22; //央行票据

        public static final long BANK_BOND_REPURCHASE = 23; //银行间债券回购

        public static final long EXCHANGECENTER_BOND_REPURCHASE = 24; //交易所债券回购

        public static final long BANK_NATIONAL_BOND = 25; //银行间国债

        public static final long EXCHANGECENTER_NATIONAL_BOND = 26; //交易所国债

        public static final long FINANCIAL_BOND = 27; //金融债

        public static final long POLICY_RELATED_FINANCIAL_BOND = 28; //政策性金融债

        public static final long ENTERPRISE_BOND = 29; //企业债

        public static final long TRANSFORMABLE_BOND = 30; //可转债

        public static final long FUND = 31; //基金

        public static final long CAPITAL_IN_CREDIT_EXTENSION = 32; //资金拆入授信

        public static final long CAPITAL_OUT_CREDIT_EXTENSION = 33; //资金拆出授信

        public static final long CAPITAL_TRANSFER = 34; //资金划拨

        public static final long CAPITAL_REPURCHASE = 35; //资产回购

        public static final long ENTRUST_FINANCING = 36; //委托理财

        public static final long ENTRUSTED_FINANCING = 37; //受托理财

        public static final long FOREIGN_CURRENCY_INVESTMENT = 38; //结构性投资

        public static final long STOCK_INVESTMENT = 39; //股权投资

        public static final long BOND_UNDERWRITING = 40; //债券承销

        public static final long INSURANCE = 41; //保险代理

        public static final long PLAN = 42; //资金计划编制

		public static final long RZZL = 43;//融资租赁
		
		public static final long CREDIT =44;//授信设置

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) ZYDQ:
                    strReturn = "自营短期贷款";
                    break;
                case (int) ZYZCQ:
                    strReturn = "自营中长期贷款";
                    break;
                case (int) WT:
                    strReturn = "委托贷款";
                    break;
                case (int) WTTJTH:
                    strReturn = "委托贷款-统借统还";
                    break;
                case (int) TX:
                    strReturn = "贴现";
                    break;
                case (int) ZTX:
                    strReturn = "转贴现";
                    break;
                case (int) ZGXEDQ:
                    strReturn = "最高限额短期";
                    break;
                case (int) ZGXEZCQ:
                    strReturn = "最高限额中长期";
                    break;
                case (int) YTDQ:
                    strReturn = "银团短期贷款";
                    break;
                case (int) YTZCQ:
                    strReturn = "银团中长期贷款";
                    break;
                case (int) OTHER:
                    strReturn = "其他";
                    break;
				case (int) MFXD:
					strReturn = "买方信贷";
					break;
				case (int) DB:
					strReturn = "担保";
					break;
                case (int) CAPITAL_LANDING:
                    strReturn = "资金拆借业务";
                    break;
                case (int) STOCK:
                    strReturn = "股票";
                    break;
                case (int) CENTRAL_BANK_NOTE:
                    strReturn = "央行票据";
                    break;
                case (int) BANK_BOND_REPURCHASE:
                    strReturn = "银行间债券回购";
                    break;
                case (int) EXCHANGECENTER_BOND_REPURCHASE:
                    strReturn = "交易所债券回购";
                    break;
                case (int) BANK_NATIONAL_BOND:
                    strReturn = "银行间国债";
                    break;
                case (int) EXCHANGECENTER_NATIONAL_BOND:
                    strReturn = "交易所国债";
                    break;
                case (int) FINANCIAL_BOND:
                    strReturn = "金融债";
                    break;
                case (int) POLICY_RELATED_FINANCIAL_BOND:
                    strReturn = "政策性金融债";
                    break;
                case (int) ENTERPRISE_BOND:
                    strReturn = "企业债";
                    break;
                case (int) TRANSFORMABLE_BOND:
                    strReturn = "可转债";
                    break;
                case (int) FUND:
                    strReturn = "基金";
                    break;
                case (int) CAPITAL_IN_CREDIT_EXTENSION:
                    strReturn = "资金拆入授信";
                    break;
                case (int) CAPITAL_OUT_CREDIT_EXTENSION:
                    strReturn = "资金拆出授信";
                    break;
                case (int) CAPITAL_TRANSFER:
                    strReturn = "资金划拨";
                    break;
                case (int) CAPITAL_REPURCHASE:
                    strReturn = "资产回购";
                    break;
                case (int) ENTRUST_FINANCING:
                    strReturn = "委托理财";
                    break;
                case (int) ENTRUSTED_FINANCING:
                    strReturn = "受托理财";
                    break;
                case (int) FOREIGN_CURRENCY_INVESTMENT:
                    strReturn = "结构性投资";
                    break;
                case (int) STOCK_INVESTMENT:
                    strReturn = "股权投资";
                    break;
                case (int) BOND_UNDERWRITING:
                    strReturn = "债券承销";
                    break;
                case (int) INSURANCE:
                    strReturn = "保险代理";
                    break;
                case (int) PLAN:
                    strReturn = "资金计划编制";
                    break;
				case (int) RZZL:
                    strReturn = "融资租赁";
                    break;
				case (int) CREDIT:
		            strReturn = "授信设置";
		            break;  }
            return strReturn;
        }
    }

    //审批设置-操作类型
    public static final class ApprovalAction
    {
        public static final long LOAN_APPLY = 1;  //贷款申请

        public static final long LOAN_CONTRACT = 2; //贷款合同

        public static final long EXTEND_APPLY = 3;

        public static final long EXTEND_CONTRACT = 4;

        public static final long FREE_APPLY = 5;

        public static final long LOANPAY_NOTICE = 6;

        public static final long AHEADREPAY_NOTICE = 7;

        public static final long INTEREST_ADJUST = 8;
        
        public static final long INTEREST_ADJUST_BATCH = 80;

        public static final long CONTRACT_PLAN = 9;

        public static final long CONTRACT_STATUS = 10;

        public static final long CONTRACT_RISKLEVEL = 11;

        public static final long OVERDUE_APPLY = 12;

        public static final long DISCOUNT_CREDENCE = 13;

        public static final long LOANDRAW_NOTICE = 14;

        public static final long ATTORNMENT_APPLY = 15;

        public static final long SECURITIES_CONTRACT_PLAN = 16; //证券合同执行计划更改

        public static final long TRANSDISCOUNT_CREDENCE = 17; //转贴现凭证

        public static final long TRANSDISCOUNT_REPURCHASECREDENCE = 18; //转贴现回购凭证
        
        public static final long ASSURE_CHARGE_NOTICE = 19; //担保收款通知单
        
        public static final long ASSURE_MANAGEMENT_NOTICE = 20; //保后处理通知单
        
        public static final long ASSURE_ADJUST = 21;			//保证金调整
        
        public static final long LEASEHOLDPAY_NOTICE = 22;		//融资租赁收款通知单
        
        public static final long LEASEHOLDREPAY_NOTICE = 23;	//融资租赁还款通知单
        
        public static final long LEASEHOLDREPAY_ADJUSTNOTICE = 24;	//融资租赁调整通知单
        
        public static final long LOANCONTRACT_APPLY = 25;	//贷款合同转让申请
        
        public static final long RECOG_NOTIC        = 26;   //融资租赁保证金保后处理通知单
        
        public static final long DISCOUNT_PAYFORM = 27; //贷款贴现放款通知单

        public static final long CAPITAL_IN_APPLY = 2001; //资金拆入申请

        public static final long CAPITAL_OUT_APPLY = 2002; //资金拆出申请

        public static final long CAPITAL_LANDING_NOTICE = 2003; //资金拆借业务通知单

        public static final long STOCK_BUYIN_APPLY = 2101; //股票购入类申请

        public static final long STOCK_SELL_APPLY = 2102; //股票卖出类申请

        public static final long STOCK_TRANSACTION_NOTICE = 2103; //股票业务通知单

        public static final long NOTE_BUYIN_APPLY = 2201; //央行票据购入类申请

        public static final long NOTE_SELL_APPLY = 2202; //央行票据卖出类申请

        public static final long CENTRAL_BANK_NOTE_TRANSACTION_NOTICE = 2203;

        //央行票据业务通知单
        public static final long BANK_FUND_REPURCHASE_APPLY = 2301;

        //银行间债券正回购申请
        public static final long BANK_BOND_REPURCHASE_APPLY = 2302;

        //银行间债券逆回购申请
        public static final long BANK_BOND_REPURCHASE_NOTICE = 2303;

        //银行间债券回购业务通知单
        public static final long EXCHANGECENTER_FUND_REPURCHASE_APPLY = 2401;

        //交易所债券融资回购申请
        public static final long EXCHANGECENTER_BOND_REPURCHASE_APPLY = 2402;

        //交易所债券融券回购申请
        public static final long EXCHANGECENTER_BOND_REPURCHASE_NOTICE = 2403;

        //交易所债券回购业务通知单
        public static final long BANK_NATIONAL_BOND_BUYIN_APPLY = 2501;

        //银行间国债购入类申请
        public static final long BANK_NATIONAL_BOND_SELL_APPLY = 2502;

        //银行间国债卖出类申请
        public static final long BANK_NATIONAL_BOND_TRANSACTION_NOTICE = 2503;

        //银行间国债业务通知单
        public static final long EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY = 2601;

        //交易所国债购入类申请
        public static final long EXCHANGECENTER_NATIONAL_BOND_SELL_APPLY = 2602;

        //交易所国债卖出类申请
        public static final long EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE = 2603;

        //交易所国债业务通知单
        public static final long FINANCIAL_BOND_BUYIN_APPLY = 2701; //金融债购入类申请

        public static final long FINANCIAL_BOND_SELL_APPLY = 2702; //金融债卖出类申请

        public static final long FINANCIAL_BOND_TRANSACTION_NOTICE = 2703;

        //金融债业务通知单
        public static final long POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY = 2801;

        //政策性金融债购入类申请
        public static final long POLICY_RELATED_FINANCIAL_BOND_SELL_APPLY = 2802;

        //政策性金融债卖出类申请
        public static final long POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE = 2803;

        //政策性金融债业务通知单
        public static final long ENTERPRISE_BOND_BUYIN_APPLY = 2901; //企业债购入类申请

        public static final long ENTERPRISE_BOND_SELL_APPLY = 2902; //企业债卖出类申请

        public static final long ENTERPRISE_BOND_TRANSACTION_NOTICE = 2903;

        //企业债业务通知单
        public static final long TRANSFORMABLE_BOND_BUYIN_APPLY = 3001;

        //可转债购入类申请
        public static final long TRANSFORMABLE_BOND_SELL_APPLY = 3002;

        //可转债卖出类申请
        public static final long DEBT_TO_EQUITY = 3003; //债转股申请

        public static final long TRANSFORMABLE_BOND_TRANSACTION_NOTICE = 3004;

        //可转债业务通知单
        public static final long FUND_BUYIN_APPLY = 3101; //基金购入类申请

        public static final long FUND_SELL_APPLY = 3102; //基金卖出类申请

        public static final long FUND_TRANSACTION_NOTICE = 3103; //基金业务通知单

        public static final long CAPITAL_IN_CREDIT_EXTENSION_APPLY = 3201;

        //资金拆入授信申请
        public static final long CAPITAL_OUT_CREDIT_EXTENSION_APPLY = 3301;

        //资金拆出授信申请
        public static final long CAPITAL_TRANSFER_NOTICE = 3401; //资金划拨业务通知单

        public static final long CAPITAL_REPURCHASE_APPLY = 3501; //资产回购申请书处理

        public static final long CAPITAL_REPURCHASE_CONTRACT = 3502; //资产回购合同处理

        public static final long CAPITAL_REPURCHASE_NOTICE = 3503; //资产回购业务通知单

        public static final long ENTRUST_FINANCING_APPLY = 3601; //委托理财申请书处理

        public static final long ENTRUST_FINANCING_CONTRACT = 3602; //委托理财合同处理

        public static final long ENTRUST_FINANCING_NOTICE = 3603; //委托理财业务通知单

        public static final long ENTRUST_FINANCING_PLAN = 3604; //委托理财执行计划修改

        public static final long ENTRUSTED_FINANCING_APPLY = 3701; //受托理财申请书处理

        public static final long ENTRUSTED_FINANCING_CONTRACT = 3702;

        //受托理财合同处理
        public static final long ENTRUSTED_FINANCING_NOTICE = 3703; //受托理财业务通知单

        public static final long FOREIGN_CURRENCY_INVESTMENT_APPLY = 3801;

        //结构性投资申请书处理
        public static final long FOREIGN_CURRENCY_INVESTMENT_CONTRACT = 3802;

        //结构性投资合同处理
        public static final long FOREIGN_CURRENCY_INVESTMENT_NOTICE = 3803;

        //结构性投资业务通知单
        public static final long FOREIGN_CURRENCY_INVESTMENT_PLAN = 3804;

        //结构性投资执行计划修改
        public static final long STOCK_INVESTMENT_APPLY = 3901; //股权投资申请书处理

        public static final long STOCK_INVESTMENT_CONTRACT = 3902; //股权投资合同处理

        public static final long STOCK_INVESTMENT_NOTICE = 3903; //股权投资业务通知单

        public static final long BOND_UNDERWRITING_APPLY = 4001; //债券承销申请书处理

        public static final long BOND_UNDERWRITING_CONTRACT = 4002; //债券承销合同处理

        public static final long BOND_UNDERWRITING_NOTICE = 4003; //债券承销业务通知单

        public static final long INSURANCE_NOTICE = 4101; //保险代理业务通知单
        
        //预算模块操作类型
        public static final long BUDGET_CURRENT = 5001; //本单位审批

        public static final long BUDGET_TOTAL = 5002; //下级单位汇总审批
        
        public static final long BATCHREPAY_NOTICE = 1153; //批量还款通知单
        
        //信用评级模块操作类型
        public static final long CRERT_CREDITRATING = 6001; //信用评级
        
        public static final long CRERT_CREDITEVALUTION = 6002; //信用重估
        
        public static final long CRERT_BECOMINGINVALID = 6003;//作废
        
        //信贷资产转让模块操作类型
        public static final long CRA_TRANSLOANAPPLY = 8001;//转让申请
        
        public static final long CRA_LOANCONTRACT_APPLY = 8004;	//贷款合同转让
        
        public static final long CRA_TRANSLOANCONTRACT = 8002;//转让合同
        
        public static final long CRA_TRANSLOANNOTICE = 8003;//转让通知单
        
        
        
        //担保品管理
        public static final long DBPGL_1 = 61; //担保品管理-权证入库
        public static final long DBPGL_2 = 62; //担保品管理-权证出库
        public static final long DBPGL_3 = 63; //担保品管理-权证出借
        public static final long DBPGL_4 = 64; //担保品管理-权证归还
        
        //贷后管理
        public static final long DH_1 = 71; //贷后管理—贷款检查报告
        public static final long DH_2 = 72; //贷后管理—贷后五级分类
        public static final long DH_3 = 73; //贷后管理—客户风险信号预警
        
        
        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final String getName(long lCode) throws Exception
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) LOAN_APPLY:
                    strReturn = "贷款申请";
                    break;
                case (int) LOAN_CONTRACT:
                    strReturn = "贷款合同";
                    break;
                case (int) EXTEND_APPLY:
                    strReturn = "展期申请";
                    break;
                case (int) EXTEND_CONTRACT:
                    strReturn = "展期合同";
                    break;
                case (int) FREE_APPLY:
                    strReturn = "免还申请";
                    break;
                case (int) LOANPAY_NOTICE:
                    strReturn = "放款通知单";
                    break;
                case (int) AHEADREPAY_NOTICE:
                    strReturn = "还款通知单";
                    break;
                case (int) INTEREST_ADJUST:
                    strReturn = "利率单笔调整";
                    break;
                case (int) INTEREST_ADJUST_BATCH:
                    strReturn = "利率批量调整";
                    break;
                case (int) CONTRACT_PLAN:
                    strReturn = "合同执行计划更改";
                    break;
                case (int) ATTORNMENT_APPLY:
                    strReturn = "贷款转让";
                    break;
                case (int) SECURITIES_CONTRACT_PLAN:
                    strReturn = "证券合同执行计划更改";
                    break;
                case (int) CONTRACT_STATUS:
                    strReturn = "合同状态变更";
                    break;
                case (int) CONTRACT_RISKLEVEL:
                    strReturn = "合同风险状态变更";
                    break;
                case (int) OVERDUE_APPLY:
                    strReturn = "逾期申请";
                    break;
                case (int) DISCOUNT_CREDENCE:
                    strReturn = "贴现凭证";
                    break;
                case (int) LOANDRAW_NOTICE:
                    strReturn = "提款通知单";
                    break;
                case (int) TRANSDISCOUNT_CREDENCE:
                    strReturn = "转贴现凭证";
                    break;
                case (int) TRANSDISCOUNT_REPURCHASECREDENCE:
                    strReturn = "转贴现回购凭证";
                    break;
                case (int) ASSURE_CHARGE_NOTICE:
                    strReturn = "担保收款通知单";
                    break;
                case (int) ASSURE_MANAGEMENT_NOTICE:
                    strReturn = "保后处理通知单";
                    break;
                case (int) RECOG_NOTIC:
                    strReturn = "融资租赁保后处理";
                    break;
                case (int) ASSURE_ADJUST:
                	strReturn = "保证金调整";
                	break;                	
                case (int) LEASEHOLDPAY_NOTICE:
                	strReturn = "融资租赁收款通知单";
                	break;
                case (int) LEASEHOLDREPAY_NOTICE:
                	strReturn = "融资租赁还款通知单";
                	break;
                case (int)LEASEHOLDREPAY_ADJUSTNOTICE:
                	strReturn = "融资租赁调整通知单";
                	break;
                case (int)LOANCONTRACT_APPLY:
                	strReturn = "贷款合同转让申请";
                	break;
                	
                /////////////////////////////
                case (int) CAPITAL_IN_APPLY:
                    strReturn = "资金拆入申请";
                    break;
                case (int) CAPITAL_OUT_APPLY:
                    strReturn = "资金拆出申请";
                    break;
                case (int) CAPITAL_LANDING_NOTICE:
                    strReturn = "资金拆借业务通知单";
                    break;
                case (int) STOCK_BUYIN_APPLY:
                    strReturn = "股票购入类申请";
                    break;
                case (int) STOCK_SELL_APPLY:
                    strReturn = "股票卖出类申请";
                    break;
                case (int) STOCK_TRANSACTION_NOTICE:
                    strReturn = "股票业务通知单";
                    break;
                case (int) NOTE_BUYIN_APPLY:
                    strReturn = "央行票据购入类申请";
                    break;
                case (int) NOTE_SELL_APPLY:
                    strReturn = "央行票据卖出类申请";
                    break;
                case (int) CENTRAL_BANK_NOTE_TRANSACTION_NOTICE:
                    strReturn = "央行票据业务通知单";
                    break;
                case (int) BANK_FUND_REPURCHASE_APPLY:
                    strReturn = "银行间债券正回购申请";
                    break;
                case (int) BANK_BOND_REPURCHASE_APPLY:
                    strReturn = "银行间债券逆回购申请";
                    break;
                case (int) BANK_BOND_REPURCHASE_NOTICE:
                    strReturn = "银行间债券回购业务通知单";
                    break;
                case (int) EXCHANGECENTER_FUND_REPURCHASE_APPLY:
                    strReturn = "交易所债券融资回购申请";
                    break;
                case (int) EXCHANGECENTER_BOND_REPURCHASE_APPLY:
                    strReturn = "交易所债券融券回购申请";
                    break;
                case (int) EXCHANGECENTER_BOND_REPURCHASE_NOTICE:
                    strReturn = "交易所债券回购业务通知单";
                    break;
                case (int) BANK_NATIONAL_BOND_BUYIN_APPLY:
                    strReturn = "银行间国债购入类申请";
                    break;
                case (int) BANK_NATIONAL_BOND_SELL_APPLY:
                    strReturn = "银行间国债卖出类申请";
                    break;
                case (int) BANK_NATIONAL_BOND_TRANSACTION_NOTICE:
                    strReturn = "银行间国债业务通知单";
                    break;
                case (int) EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY:
                    strReturn = "交易所国债购入类申请";
                    break;
                case (int) EXCHANGECENTER_NATIONAL_BOND_SELL_APPLY:
                    strReturn = "交易所国债卖出类申请";
                    break;
                case (int) EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE:
                    strReturn = "交易所国债业务通知单";
                    break;
                case (int) FINANCIAL_BOND_BUYIN_APPLY:
                    strReturn = "金融债购入类申请";
                    break;
                case (int) FINANCIAL_BOND_SELL_APPLY:
                    strReturn = "金融债卖出类申请";
                    break;
                case (int) FINANCIAL_BOND_TRANSACTION_NOTICE:
                    strReturn = "金融债业务通知单";
                    break;
                case (int) POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY:
                    strReturn = "政策性金融债购入类申请";
                    break;
                case (int) POLICY_RELATED_FINANCIAL_BOND_SELL_APPLY:
                    strReturn = "政策性金融债卖出类申请";
                    break;
                case (int) POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE:
                    strReturn = "政策性金融债业务通知单";
                    break;
                case (int) ENTERPRISE_BOND_BUYIN_APPLY:
                    strReturn = "企业债购入类申请";
                    break;
                case (int) ENTERPRISE_BOND_SELL_APPLY:
                    strReturn = "企业债卖出类申请";
                    break;
                case (int) ENTERPRISE_BOND_TRANSACTION_NOTICE:
                    strReturn = "企业债业务通知单";
                    break;
                case (int) TRANSFORMABLE_BOND_BUYIN_APPLY:
                    strReturn = "可转债购入类申请";
                    break;
                case (int) TRANSFORMABLE_BOND_SELL_APPLY:
                    strReturn = "可转债卖出类申请";
                    break;
                case (int) DEBT_TO_EQUITY:
                    strReturn = "债转股申请";
                    break;
                case (int) TRANSFORMABLE_BOND_TRANSACTION_NOTICE:
                    strReturn = "可转债业务通知单";
                    break;
                case (int) FUND_BUYIN_APPLY:
                    strReturn = "基金购入类申请";
                    break;
                case (int) FUND_SELL_APPLY:
                    strReturn = "基金卖出类申请";
                    break;
                case (int) FUND_TRANSACTION_NOTICE:
                    strReturn = "基金业务通知单";
                    break;
                case (int) CAPITAL_IN_CREDIT_EXTENSION_APPLY:
                    strReturn = "资金拆入授信申请";
                    break;
                case (int) CAPITAL_OUT_CREDIT_EXTENSION_APPLY:
                    strReturn = "资金拆出授信申请";
                    break;
                case (int) CAPITAL_TRANSFER_NOTICE:
                    strReturn = "资金划拨业务通知单";
                    break;
                case (int) CAPITAL_REPURCHASE_APPLY:
                    strReturn = "资产回购申请书处理";
                    break;
                case (int) CAPITAL_REPURCHASE_CONTRACT:
                    strReturn = "资产回购合同处理";
                    break;
                case (int) CAPITAL_REPURCHASE_NOTICE:
                    strReturn = "资产回购业务通知单";
                    break;
                case (int) ENTRUST_FINANCING_APPLY:
                    strReturn = "委托理财申请书处理";
                    break;
                case (int) ENTRUST_FINANCING_CONTRACT:
                    strReturn = "委托理财合同处理";
                    break;
                case (int) ENTRUST_FINANCING_NOTICE:
                    strReturn = "委托理财业务通知单";
                    break;
                case (int) ENTRUST_FINANCING_PLAN:
                    strReturn = "委托理财执行计划修改";
                    break;
                case (int) ENTRUSTED_FINANCING_APPLY:
                    strReturn = "受托理财申请书处理";
                    break;
                case (int) ENTRUSTED_FINANCING_CONTRACT:
                    strReturn = "受托理财合同处理";
                    break;
                case (int) ENTRUSTED_FINANCING_NOTICE:
                    strReturn = "受托理财业务通知单";
                    break;
                case (int) FOREIGN_CURRENCY_INVESTMENT_APPLY:
                    strReturn = "结构性投资申请书处理";
                    break;
                case (int) FOREIGN_CURRENCY_INVESTMENT_CONTRACT:
                    strReturn = "结构性投资合同处理";
                    break;
                case (int) FOREIGN_CURRENCY_INVESTMENT_NOTICE:
                    strReturn = "结构性投资业务通知单";
                    break;
                case (int) FOREIGN_CURRENCY_INVESTMENT_PLAN:
                    strReturn = "结构性投资执行计划修改";
                    break;
                case (int) STOCK_INVESTMENT_APPLY:
                    strReturn = "股权投资申请书处理";
                    break;
                case (int) STOCK_INVESTMENT_CONTRACT:
                    strReturn = "股权投资合同处理";
                    break;
                case (int) STOCK_INVESTMENT_NOTICE:
                    strReturn = "股权投资业务通知单";
                    break;
                case (int) BOND_UNDERWRITING_APPLY:
                    strReturn = "债券承销申请书处理";
                    break;
                case (int) BOND_UNDERWRITING_CONTRACT:
                    strReturn = "债券承销合同处理";
                    break;
                case (int) BOND_UNDERWRITING_NOTICE:
                    strReturn = "债券承销业务通知单";
                    break;
                case (int) INSURANCE_NOTICE:
                    strReturn = "保险代理业务通知单";
                    break;
                case (int) BUDGET_CURRENT:
                    strReturn = "本单位审批";
                    break;
                case (int) BUDGET_TOTAL:
                    strReturn = "下级单位汇总审批";
                    break;
                case (int) BATCHREPAY_NOTICE:
                    strReturn = "批量还款通知单";
                    break;
                //信用评级专用 start
                case (int) CRERT_CREDITRATING:
                    strReturn = "信用评级";
                    break;
                case (int) CRERT_CREDITEVALUTION:
                    strReturn = "信用重估";
                    break;
                case (int) CRERT_BECOMINGINVALID:
                    strReturn = "作废";
                    break;                      
                // end 
                //信贷资产转让
                case (int) CRA_TRANSLOANAPPLY:
                    strReturn = "转让申请";
                    break;
                case (int) CRA_TRANSLOANCONTRACT:
                    strReturn = "转让合同";
                    break;
                case (int) CRA_TRANSLOANNOTICE:
                    strReturn = "转让通知单";
                    break; 
                case (int) CRA_LOANCONTRACT_APPLY:
                    strReturn = "贷款合同转让";
                    break;
                case (int) DH_1:
                    strReturn = "贷后调查报告";
                    break;
                case (int) DISCOUNT_PAYFORM:
                	strReturn = "贴现放款通知单";
                	break;
            }
            return strReturn;
        }
        
        public static final long[] getAllCode(long lModuleType)
        {
        	long[] lTemp = null;
        	switch ((int) lModuleType)
        	{
        		case (int) ModuleType.LOAN:
        				lTemp = new long[]{ 
                        		LOAN_APPLY,
                        		LOAN_CONTRACT,
                        		EXTEND_APPLY,
                        		EXTEND_CONTRACT,
                        		FREE_APPLY,
                        		LOANPAY_NOTICE,
                        		AHEADREPAY_NOTICE,
                        		BATCHREPAY_NOTICE,
                        		INTEREST_ADJUST,
                        		INTEREST_ADJUST_BATCH,
                        		CONTRACT_PLAN,
                        		CONTRACT_STATUS,
                        		CONTRACT_RISKLEVEL,
                        		OVERDUE_APPLY,
                        		DISCOUNT_CREDENCE,
                        		LOANDRAW_NOTICE,
                        		ATTORNMENT_APPLY,
                        		TRANSDISCOUNT_CREDENCE,
                        		TRANSDISCOUNT_REPURCHASECREDENCE,
                        		ASSURE_CHARGE_NOTICE,
                        		ASSURE_MANAGEMENT_NOTICE,
                        		ASSURE_ADJUST,
                        		RECOG_NOTIC,
                        		LEASEHOLDPAY_NOTICE,
                        		LEASEHOLDREPAY_NOTICE,
                        		LOANCONTRACT_APPLY,
                        		DH_1,
                        		DISCOUNT_PAYFORM
                        	};
        			break;
    			case(int)ModuleType.BUDGET:
    			    lTemp = new long[]{
    			        BUDGET_CURRENT,BUDGET_TOTAL
    			};
        	}            
            return lTemp;
        }        
    }

    //审批操作类型
    public static class ApprovalActionType
    {
        public static final long CONSTANT = 1; //常量

        public static final long DEPARTMENT = 2; //部门表
    }

    //审批设置-状态
    public static final class ApprovalStatus
    {
        public static final long INVALID = 0;

        public static final long SAVE = 1;

        public static final long SUBMIT = 2;

        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final String getName(long lCode) throws Exception
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) INVALID:
                    strReturn = "无效";
                    break;
                case (int) SAVE:
                    strReturn = "设置中";
                    break;
                case (int) SUBMIT:
                    strReturn = "设置完成";
                    break;
            }
            return strReturn;
        }
        
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName,
         *            控件名称
         * @param nType，控件类型（0，显示全部；1，新增复核的状态
         *            2,只有“未复核”状态）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {                
                lArrayID = getAllCode();                        
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                Log.print(ex.toString());
            }
        }
        public static final long[] getAllCode()
        {
            long[] lTemp = { SAVE, SUBMIT, INVALID };
            return lTemp;
        }
    }

    //审核决定
    public static final class ApprovalDecision
    {
        //审核决定
        public static final long PASS = 1; //审核通过

        public static final long REFUSE = 2; //审核拒绝

        public static final long RETURN = 3; //审核返回

        public static final long FINISH = 4; //审核完成

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) PASS:
                    strReturn = "审核通过";
                    break;
                case (int) REFUSE:
                    strReturn = "审核拒绝";
                    break;
                case (int) RETURN:
                    strReturn = "审核返回";
                    break;
                case (int) FINISH:
                    strReturn = "审核完成";
                    break;
            }
            return strReturn;
        }
    }

    //项目名称
    public static final class ProjectName
    {
        public static final String PROD = "prod";
        public static final String CPF = "cpf";
        public static final String HN = "hn";
        public static final String MBEC = "dqj";
        public static final String CNMEF = "cnmef";
        public static final String CEC = "cec";
        public static final String HAIER = "haier";
        public static final String ICBC = "icbc";
        public static final String SEFC = "sefc";
        
		public static final String GD = "GUODIAN";  //中国国电
		public static final String JN = "JINGNENG";  //京能集团
    }

	//项目 属性值
	public static final class ProjectType
	{
		public static final long CPF = 1;

		public static final long HN = 2;

		public static final long MBEC = 3;

		public static final long CNMEF = 4;

		public static final long CEC = 5;

		public static final long HAIER = 6;

		public static final long ICBC = 7;
		
		public static final long SEFC = 8;
	}
    //操作结果
    public static final class ActionResult
    {
        public static final String FAIL = "fail";

        public static final String SUCCESS = "success";
    }

    //系统涉及文档类型
    public static final class DocType
    {
        public static final long LOANCONTRACTTEMPLATE = 1; //贷款模块合同模版

        public static final long LOANCONTRACTCONTENT = 2; //贷款模块合同内容

        public static final long LOANUPLOAD = 3; //贷款模块上传文件

        public static final long EBANKCONSIGNUPSAVESETTING = 4;

        //网银模块上存资金账户设置文件
        public static final long EBANKUPLOAD = 5; //网银模块上传文件

        public static final long CLIENTCENTER = 6; //客户中心上传文件

        public static final long SECURITIESUPLOAD = 7; //证券模块上传文件
        
        public static final long CLIENTMANAGE = 14; //客户管理模块上传文件
        
        public static final long MODELUPLOAD = 15; //文件管理上传
        
        public static final long EVOUCHERBILLTEMPLATE = 16;//电子回单单据模版
        
        public static final long SETTLEMENTUPLOAD = 18;//结算模块上传文件
        
        public static final long CRERTUPLOAD  = 19;//信用评级模块上传文件
        
        
    }

    //	关机状态
    public static final class ShutDownStatus
    {
        //		关机状态
        public static final long SUCCESS = 1;

        public static final long FAIL = 2;

        public static final long OVERTIME = 3;

        public static final long DOING = 4;

        public static final long REQUEST = 5; //请求关机

        public static final long AFFIRM = 6; //关机待确认

        public static final long NORMAL = 7; //正常状态

        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) SUCCESS:
                    strReturn = "成功";
                    break;
                case (int) FAIL:
                    strReturn = "失败";
                    break;
                case (int) OVERTIME:
                    strReturn = "超时";
                    break;
                case (int) DOING:
                    strReturn = "正在运行";
                    break;
                case (int) REQUEST:
                    strReturn = "关机排队";
                    break;
                    
            }
            return strReturn;
        }
    }

    //	关机操作（开机、关机）
    public static final class ShutDownAction
    {
        public static final long OPEN = 1;

        public static final long CLOSE = 2;

        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) OPEN:
                    strReturn = "开机";
                    break;
                case (int) CLOSE:
                    strReturn = "关机";
                    break;
            }
            return strReturn;
        }
    }

    public static class SystemStatus
    {
        //系统状态
        public static final long OPEN = 1; //开机

        public static final long CLOSE = 2; //关机

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) OPEN:
                    strReturn = "Opened";
                    break;
                case (int) CLOSE:
                    strReturn = "Closed";
                    break;
            }
            return strReturn;
        }

        public static final String getChineseName(long lCode){
        	 String strReturn = ""; //初始化返回值
             switch ((int) lCode)
             {
                 case (int) OPEN:
                     strReturn = "开机";
                     break;
                 case (int) CLOSE:
                     strReturn = "关机";
                     break;
             }
             return strReturn;
        }
        public static final long getID(String strCode)
        {
            long lStatusID = -1; //初始化返回值
            if (strCode != null && strCode.equals("Closed"))
            {
                lStatusID = CLOSE;
            }
            else
            {
                lStatusID = OPEN;
            }
            return lStatusID;
        }

        public static final long[] getAllCode()
        {
            long[] lTemp =
            { OPEN, CLOSE };
            return lTemp;
        }
        public static final long[] getAllCode(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.util.Constant$SystemStatus",officeID,currencyID);
        }                
    }

    //利率费率类型
    public static class RateType
    {
        //利率费率类型
        public static final long INTEREST = 1; //贷款利率

        public static final long CHARGE = 2; //委托手续费率

        public static final long FINE = 3; //罚息利率

        public static final long ASSURE = 4; //担保费率

        public static final long AGENT = 5; //代理费率

        public static final long getRateStyle(long lCode)
        {
            long lReturn = RateStyle.YEAR; //初始化返回值
                switch ((int) lCode)
                {
                    case (int) INTEREST:
                        lReturn = RateStyle.YEAR;
                        break;
                    case (int) CHARGE:
                        lReturn = RateStyle.YEAR;
                        break;
                    case (int) FINE:
                        lReturn = RateStyle.YEAR;
                        break;
                    case (int) ASSURE:
                        lReturn = RateStyle.YEAR;
                        break;
                    case (int) AGENT:
                        lReturn = RateStyle.MONTH;
                        break;
                }
            return lReturn;
        }
    }

    //计息方式
    public static class RateStyle
    {
        //计息方式
        public static final long YEAR = 1; //年息

        public static final long MONTH = 2; //月息

        public static final long DAY = 3; //日息
    }

    /**
     * 登录验证状态
     * 
     * @author yiwang
     * 
     * To change the template for this generated type comment go to Window -
     * Preferences - Java - Code Generation - Code and Comments
     */
    public static class LoginStatus
    {
        //系统状态
        public static final long PASS = 1; //登录验证通过

        public static final long REJECT = 2; //无权限

        public static final long CLOSING = 3; // 正在关机

        public static final long CLOSED = 4; // 已关机
    }

 
    /**
     * 客户中心上传文件类型
     * 
     * @author rxie
     */
    public static class ClientCenterFileType
    {
        //客户中心上传文件类型
        public static final long PROD = 1; //生成经营信息

        public static final long FIN = 2; //财务信息

        public static final long HR = 3; //人员组织结构图

        public static final long LEADER = 4; //领导工作分管图

        public static final long PRECALCULATE = 5; //资金预算执行情况表

        public static final long OTHER = 6; //其他

        public static final long AWARD = 7; //授信评级信息
    }

    //	关机状态
    public static final class ExecuteStatus
    {
        //		关机状态
        public static final long SUCCESS = 1;

        public static final long FAIL = 2;

        public static final long NOTHING = 3;

        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final boolean isPassed(long lCode)
        {
            boolean strReturn = true; //初始化返回值
            switch ((int) lCode)
            {
                case (int) SUCCESS:
                    strReturn = true;
                    break;
                case (int) FAIL:
                    strReturn = false;
                    break;
                case (int) NOTHING:
                    strReturn = true;
                    break;
            }
            return strReturn;
        }
    }

    public static class GLPostStatus
    {
        //是否
        public static final long SUCCESS = 1; //成功

        public static final long FAILED = 2; //失败

        public static final long DOING = 3; //正在生成

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) SUCCESS:
                    strReturn = "成功";
                    break;
                case (int) FAILED:
                    strReturn = "失败";
                    break;
                case (int) DOING:
                    strReturn = "正在生成";
                    break;
            }
            return strReturn;
        }

        //能过账的状态
        public static final boolean isPosting(long lCode)
        {
            boolean strReturn = true; //初始化返回值
            switch ((int) lCode)
            {
                case (int) SUCCESS:
                    strReturn = false;
                    break;
                case (int) DOING:
                    strReturn = false;
                    break;
                default:
                    strReturn = true;
                    break;
            }
            return strReturn;
        }
    }

    public static class MachineUser
    {

        //是否
        public static final long InputUser = -100; //机制

        public static final long CheckUser = -101; //机核

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) InputUser:
                    strReturn = "机制";
                    break;
                case (int) CheckUser:
                    strReturn = "机核";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] arrayReturn = null;

            long[] lTemp = { InputUser, CheckUser };
            arrayReturn = lTemp;

            return arrayReturn;
        }
        public static final long[] getAllCode(long officeID,long currencyID)
        {
        	return Constant.getAllCode("com.iss.itreasury.util.Constant$MachineUser",officeID,currencyID);
        }                
    }
    /*
    长度控制，定义编码、摘要的长度
    */
    public static class LengthControl
    {
       
        public static final long Code = 20; //编码长度

        public static final long Abstract = 40; //摘要长度
        
        public static final long Content = 500; //内容、说明

    }
	/**
	 * 
	 * 页面操作定义
	 * 
	 * @author rongyang
	 */
	public static class Actions
	{
		public static final int CREATETEMPSAVE = 1; //新增临时保存
		public static final int MODIFYTEMPSAVE = 2; //修改临时保存
		public static final int CREATESAVE = 3; //创建保存
		public static final int MODIFYSAVE = 4; //修改保存
		public static final int DELETE = 5; //删除
		public static final int LINKSEARCH = 6; //链接查找
		public static final int MATCHSEARCH = 7; //匹配查找
		public static final int CHECK = 8; //复核/审核
		public static final int CANCELCHECK = 9; //取消复核
		public static final int NEXTSTEP = 10; //下一步
		public static final int TODETAIL = 11; //点交易号到详细页面
		public static final int MODIFYNEXTSTEP = 12; //更改下一步
		public static final int REJECT = 13; //拒绝
		public static final int RETURN = 14; //返回修改
		public static final int CHECKOVER = 15; //审核完成
		public static final int MASSCHECK = 16; //批量复核
		public static final int MASSCANCELCHECK = 17; //批量取消复核
		public static final int UPDATESEARCH = 18; //修改查找
		public static final int CHECKSEARCH = 19; //审核查找
		public static final int COMMIT = 20; //提交
		public static final int SUM = 21; //汇总
		public static final int UPSTEP = 22;//上一步
		public static final int UPLOAD = 23;//上传
		public static final int DOWNLOAD = 24;//下载
		public static final int CANCELAPPROVED= 25;//取消审批
		

		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case CREATETEMPSAVE :
					strReturn = "临时保存";
					break;
				case MODIFYTEMPSAVE :
					strReturn = "临时保存";
					break;
				case CREATESAVE :
					strReturn = "创建保存";
					break;
				case MODIFYSAVE :
					strReturn = "修改保存";
					break;
				case DELETE :
					strReturn = "删除";
					break;
				case LINKSEARCH :
					strReturn = "链接查找";
					break;
				case MATCHSEARCH :
					strReturn = "匹配查找";
					break;
				case CHECK :
					strReturn = "复核";
					break;
				case CANCELCHECK :
					strReturn = "取消复核";
					break;
				case NEXTSTEP :
					strReturn = "下一步";
					break;
				case TODETAIL :
					strReturn = "察看明细";
					break;
				case REJECT :
					strReturn = "拒绝";
					break;
				case RETURN :
					strReturn = "返回修改";
					break;
				case CHECKOVER :
					strReturn = "审核完成";
					break;
				case MASSCHECK :
					strReturn = "批量复核";
					break;
				case MASSCANCELCHECK :
					strReturn = "批量取消复核";
					break;
				case UPDATESEARCH :
					strReturn = "修改查找";
					break;
				case CHECKSEARCH :
					strReturn = "审核查找";
					break;
				case COMMIT :
					strReturn = "提交";
					break;
				case SUM :
					strReturn = "汇总";
					break;
				case UPSTEP :
					strReturn = "上一步";	
					break;
				case UPLOAD :
					strReturn = "上传";	
					break;
				case DOWNLOAD :
					strReturn = "下载";	
					break;
				case CANCELAPPROVED :
					strReturn = "取消审批";	
					break;

			}
			return strReturn;
		}
	}

	public static class UserRole
	{
		
		public static final int SUPER=1;
		public static final int ADMIN=2;
		public static final int USER=3;
		
		public static final String getName(long lCode)
		{
			String strReturn = "";
			switch ((int) lCode)
			{
				case SUPER :
					strReturn = "超级管理员";
					break;
				case ADMIN:
					strReturn = "系统管理员";
					break;
				case USER:
					strReturn = "系统用户";
					break;
			}
			return strReturn;
		}	
	}
	
	public static long[] getAllCode(String classname,long officeid,long currencyid)
	{
		if (officeid<0)	officeid=1;
		if (currencyid<0) currencyid=1;
		if ((classname==null)||(classname.length()==0))
		{

                    
			return null;
		}
		
		return constantManager.getFieldsByClassName(classname,officeid,currencyid );
	}
	
	
	/*
     * 总账导出方式
     * 
     * 
     */
    public static class GLImportType
    {
        //系统状态
        public static final long fb = 1; //分笔

        public static final long hz = 2; //汇总
        
        /**
         * 获得总账接口类型的描述
         * 
         * @param lCurrencyID
         * @return String
         */
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            lCode=lCode-1;
            try
            {
            	if( (lCode<0) || (lCode>1) )
            		lCode=0;	//默认是分笔
            	String strglName[]={"分笔","汇总"};
	            
            	strReturn=strglName[(int)lCode];
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return strReturn;
        }
        
        /**
         * 得到所有的币种(从Env中取得币种数值)
         * 
         * @return long[]
         */
        public static final long[] getAllID()
        {
        	long lTemp[]={1,2};
            return lTemp;
        }
    }
  
    /*
     * 总账接口类型
     * 
     */
    public static class GLType
    {
        //系统状态
        public static final long ORACLE = 1; //Oracle

        public static final long U850 = 2; //用友U850
        
        public static final long GENERSOFT = 3; //浪潮财务
        
        public static final long ISOFTSTONE = 4; //内部财务
        
        public static final long ORACLE_CPF = 5; //ORACLE_CPF(oracle中油)
        
        public static final long KINGDEE = 6;//金蝶财务

        public static final long ANYI = 7;//安易财务
        
        public static final long ISSACNT = 8; //内部财务核算
        
        public static final long NC = 9; //用友NC
        
        public static final long JOINCHEER = 10; //久其JOINCHEER

        /**
         * 获得总账接口类型的描述
         * 
         * @param lCurrencyID
         * @return String
         */
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            lCode=lCode-1;
            try
            {
            	//if( (lCode<0) || (lCode>8) )
            	if(lCode<0)
            	{
            		lCode=4;  //默认是ISOFTSTONE
            	}
            	String strglName[]={"ORACLE","U850","GENERSOFT","ISOFTSTONE","ORACLE_CPF","KINGDEE","ANYI","ISSACNT","NC","JOINCHEER"};
	            
            	strReturn=strglName[(int)lCode];
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return strReturn;
        }
        
        /**
         * 得到所有的币种(从Env中取得币种数值)
         * 
         * @return long[]
         */
        public static final long[] getAllID()
        {
        	
        	//long lTemp[]={1,2,3,4,5,6,7,8};
        	long lTemp[]={ORACLE,U850,GENERSOFT,ISOFTSTONE,ORACLE_CPF,KINGDEE,ANYI,ISSACNT,NC,JOINCHEER};
            return lTemp;
        }
        
        /*
         * 得到名字对应的ID值
         * 
         * @return long
         * 
         */
        public static final long getID(String name)
        {
        	if(name.equalsIgnoreCase("ORACLE"))
        	{
        		return ORACLE;
        	}
        	else if(name.equalsIgnoreCase("U850"))
        	{
        		return U850;
        	}
        	else if(name.equalsIgnoreCase("GENERSOFT"))
        	{
        		return GENERSOFT;
        	}
        	else if(name.equalsIgnoreCase("ISOFTSTONE"))
        	{
        		return ISOFTSTONE;
        	}
        	else if(name.equalsIgnoreCase("ORACLE_CPF"))
        	{
        		return ORACLE_CPF;
        	}
        	else if(name.equalsIgnoreCase("KINGDEE"))
        	{
        		return KINGDEE;
        	}
        	else if(name.equalsIgnoreCase("ANYI"))
        	{
        		return ANYI;
        	}
        	else if(name.equalsIgnoreCase("ISSACNT"))
        	{
        		return ISSACNT;
        	}
        	else if(name.equalsIgnoreCase("NC"))
        	{
        		return NC;
        	}
        	else if(name.equalsIgnoreCase("JOINCHEER"))
        	{
        		return JOINCHEER;
        	}
        	else
        	{	//默认是内部财务
        		return ISOFTSTONE;
        	}
        }
        
    }
    
    /*
     * 总账导入导出类型
     * 
     */
    public static class GLOperationType
    {
        //总账导入导出状态
    	public static final long NoOperator = 1; //不导入导出

        public static final long ImportOnly = 2; //只导入
        
        public static final long ExportOnly = 3; //只导出
        
        public static final long ImportAndExport = 4; //导入导出

        /**
         * 获得总账导入导出的描述
         * 
         * @param lCurrencyID
         * @return String
         */
        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            lCode=lCode-1;
            try
            {
            	if( (lCode<0) || (lCode>3) )
            		lCode=1;  //默认是不导入导出
            	String strglName[]={"NoOperator","ImportOnly","ExportOnly","ImportAndExport"};
	            
            	strReturn=strglName[(int)lCode];
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return strReturn;
        }
        
        /**
         * 获得总账导入导出的中文描述
         * 
         * @param lCurrencyID
         * @return String
         */
        public static final String getChiName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            lCode=lCode-1;
            try
            {
            	if( (lCode<0) || (lCode>3) )
            		lCode=1;  //默认是不导入导出
            	String strglName[]={"不导入导出","只导入","只导出","导入导出"};
	            
            	strReturn=strglName[(int)lCode];
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return strReturn;
        }
        
        /**
         * 得到所有的币种(从Env中取得币种数值)
         * 
         * @return long[]
         */
        public static final long[] getAllID()
        {
        	long lTemp[]={1,2,3,4};
            return lTemp;
        }
        
        /*
         * 得到名字对应的ID值
         * 
         * @return long
         * 
         */
        public static final long getID(String name)
        {
        	if(name.equalsIgnoreCase("NoOperator"))
        	{
        		return NoOperator;	//不导入导出
        	}
        	else if(name.equalsIgnoreCase("ImportOnly"))
        	{
        		return ImportOnly;	//只导入
        	}
        	else if(name.equalsIgnoreCase("ExportOnly"))
        	{
        		return ExportOnly;
        	}
        	else if(name.equalsIgnoreCase("ImportAndExport"))
        	{
        		return ImportAndExport;
        	}
        	else
        	{	//默认是不导入导出
        		return NoOperator;
        	}
        }
        
        public static final long[] getAllCode()
        {
            long[] lTemp =
            { NoOperator, ImportOnly, ExportOnly,ImportAndExport};
            return lTemp;
        }
        /**
         * 显示下拉列表
         * 
         * @param out
         * @param strControlName, *
         *            控件名称
         * @param nType，控件类型（0，显示全部；）
         * @param lSelectValue
         * @param isNeedAll，是否需要”全部项“
         * @param isNeedBlank
         *            是否需要空白行
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank,
                String strProperty)
        {
            long[] lArrayID = null;
            String[] strArrayName = null;
            try
            {
                switch (nType)
                {
                    case 0:
                        lArrayID = getAllCode();
                        break;
                }
                strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getChiName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
            }
            catch (Exception ex)
            {
                Log.print(ex.toString());
            }
        }
    }
    
    public static class GlobalTroyName {
		public static String NetSign = "netSign";

		public static String ITrus = "iTrus";
		
		public static String CFCA = "CFCA";
		
		public static String SDEAN = "SDEAN";

		public static String NotUseCertificate = "isoftstone";
	}
    
    public static class GlobalTroyType{
    	public static String SOFT = "soft";
    	public static String HARD = "hard";
    }
    
    public static class OBBudgetStatus
	{
		//
		public static final long DELETE = 0; //已删除
		public static final long SAVE = 1; //未复核
		public static final long CHECK = 2; //已复核
		public static final long AUTHED = 3; //已审批
		public static final long REFUSE = 4; //已拒绝
		public static final long USING = 5; //使用中
		public static final long OVER = 6; //已过期
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) DELETE :
					strReturn = "已删除";
					break;
				case (int) SAVE :
					strReturn = "已提交";
					break;
				case (int) CHECK :
					strReturn = "已复核";
					break;
				case (int) AUTHED :
					strReturn = "已审批";
					break;
				case (int) REFUSE :
					strReturn = "已拒绝";
					break;
				case (int) USING :
					strReturn = "使用中";
					break;
				case (int) OVER :
					strReturn = "已过期";
					break;
			}
			return strReturn;
		}
	}
 public  static final class CodingruleAction 
 {

     public static final long LOAN_APPLY = Constant.ApprovalAction.LOAN_APPLY;  //贷款申请

     public static final long LOAN_CONTRACT = Constant.ApprovalAction.LOAN_CONTRACT; //贷款合同

     public static final long EXTEND_APPLY = Constant.ApprovalAction.EXTEND_APPLY;

     public static final long EXTEND_CONTRACT = Constant.ApprovalAction.EXTEND_CONTRACT;

     public static final long FREE_APPLY = Constant.ApprovalAction.FREE_APPLY;

     public static final long LOANPAY_NOTICE = Constant.ApprovalAction.LOANPAY_NOTICE;

     public static final long AHEADREPAY_NOTICE = Constant.ApprovalAction.AHEADREPAY_NOTICE;

     public static final long INTEREST_ADJUST = Constant.ApprovalAction.INTEREST_ADJUST;

     public static final long CONTRACT_PLAN = Constant.ApprovalAction.CONTRACT_PLAN;

     public static final long CONTRACT_STATUS = Constant.ApprovalAction.CONTRACT_STATUS;

     public static final long CONTRACT_RISKLEVEL = Constant.ApprovalAction.CONTRACT_RISKLEVEL;

     public static final long OVERDUE_APPLY = Constant.ApprovalAction.OVERDUE_APPLY;

     public static final long DISCOUNT_CREDENCE = Constant.ApprovalAction.DISCOUNT_CREDENCE;

     public static final long LOANDRAW_NOTICE = Constant.ApprovalAction.LOANDRAW_NOTICE;

     public static final long ATTORNMENT_APPLY = Constant.ApprovalAction.ATTORNMENT_APPLY;

     public static final long SECURITIES_CONTRACT_PLAN = Constant.ApprovalAction.SECURITIES_CONTRACT_PLAN; //证券合同执行计划更改

     public static final long TRANSDISCOUNT_CREDENCE = Constant.ApprovalAction.TRANSDISCOUNT_CREDENCE; //转贴现凭证

     public static final long TRANSDISCOUNT_REPURCHASECREDENCE = Constant.ApprovalAction.TRANSDISCOUNT_REPURCHASECREDENCE; //转贴现回购凭证
     
     public static final long ASSURE_CHARGE_NOTICE = Constant.ApprovalAction.ASSURE_CHARGE_NOTICE; //担保收款通知单
     
     public static final long ASSURE_MANAGEMENT_NOTICE = Constant.ApprovalAction.ASSURE_MANAGEMENT_NOTICE; //保后处理通知单
     
     public static final long ASSURE_ADJUST = Constant.ApprovalAction.ASSURE_ADJUST;			//保证金调整
     
     public static final long LEASEHOLDPAY_NOTICE = Constant.ApprovalAction.LEASEHOLDPAY_NOTICE;		//融资租赁收款通知单
     
     public static final long LEASEHOLDREPAY_NOTICE = Constant.ApprovalAction.LEASEHOLDREPAY_NOTICE;	//融资租赁还款通知单
     
     public static final long LEASEHOLDREPAY_ADJUSTNOTICE = Constant.ApprovalAction.LEASEHOLDREPAY_ADJUSTNOTICE;	//融资租赁调整通知单
     

     public static final long CAPITAL_IN_APPLY = Constant.ApprovalAction.CAPITAL_IN_APPLY; //资金拆入申请

     public static final long CAPITAL_OUT_APPLY = Constant.ApprovalAction.CAPITAL_OUT_APPLY; //资金拆出申请

     public static final long CAPITAL_LANDING_NOTICE = Constant.ApprovalAction.CAPITAL_LANDING_NOTICE; //资金拆借业务通知单

     public static final long STOCK_BUYIN_APPLY = Constant.ApprovalAction.STOCK_BUYIN_APPLY; //股票购入类申请

     public static final long STOCK_SELL_APPLY = Constant.ApprovalAction.STOCK_SELL_APPLY; //股票卖出类申请

     public static final long STOCK_TRANSACTION_NOTICE = Constant.ApprovalAction.STOCK_TRANSACTION_NOTICE; //股票业务通知单

     public static final long NOTE_BUYIN_APPLY = Constant.ApprovalAction.NOTE_BUYIN_APPLY; //央行票据购入类申请

     public static final long NOTE_SELL_APPLY = Constant.ApprovalAction.NOTE_SELL_APPLY; //央行票据卖出类申请

     public static final long CENTRAL_BANK_NOTE_TRANSACTION_NOTICE = Constant.ApprovalAction.CENTRAL_BANK_NOTE_TRANSACTION_NOTICE;

     //央行票据业务通知单
     public static final long BANK_FUND_REPURCHASE_APPLY = Constant.ApprovalAction.BANK_FUND_REPURCHASE_APPLY;

     //银行间债券正回购申请
     public static final long BANK_BOND_REPURCHASE_APPLY = Constant.ApprovalAction.BANK_BOND_REPURCHASE_APPLY;

     //银行间债券逆回购申请
     public static final long BANK_BOND_REPURCHASE_NOTICE = Constant.ApprovalAction.BANK_BOND_REPURCHASE_NOTICE;

     //银行间债券回购业务通知单
     public static final long EXCHANGECENTER_FUND_REPURCHASE_APPLY = Constant.ApprovalAction.EXCHANGECENTER_FUND_REPURCHASE_APPLY;

     //交易所债券融资回购申请
     public static final long EXCHANGECENTER_BOND_REPURCHASE_APPLY = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_APPLY;

     //交易所债券融券回购申请
     public static final long EXCHANGECENTER_BOND_REPURCHASE_NOTICE = Constant.ApprovalAction.EXCHANGECENTER_BOND_REPURCHASE_NOTICE;

     //交易所债券回购业务通知单
     public static final long BANK_NATIONAL_BOND_BUYIN_APPLY = Constant.ApprovalAction.BANK_NATIONAL_BOND_BUYIN_APPLY;

     //银行间国债购入类申请
     public static final long BANK_NATIONAL_BOND_SELL_APPLY = Constant.ApprovalAction.BANK_NATIONAL_BOND_SELL_APPLY;

     //银行间国债卖出类申请
     public static final long BANK_NATIONAL_BOND_TRANSACTION_NOTICE = Constant.ApprovalAction.BANK_NATIONAL_BOND_TRANSACTION_NOTICE;

     //银行间国债业务通知单
     public static final long EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY;

     //交易所国债购入类申请
     public static final long EXCHANGECENTER_NATIONAL_BOND_SELL_APPLY = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_SELL_APPLY;

     //交易所国债卖出类申请
     public static final long EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE = Constant.ApprovalAction.EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE;

     //交易所国债业务通知单
     public static final long FINANCIAL_BOND_BUYIN_APPLY = Constant.ApprovalAction.FINANCIAL_BOND_BUYIN_APPLY; //金融债购入类申请

     public static final long FINANCIAL_BOND_SELL_APPLY = Constant.ApprovalAction.FINANCIAL_BOND_SELL_APPLY; //金融债卖出类申请

     public static final long FINANCIAL_BOND_TRANSACTION_NOTICE = Constant.ApprovalAction.FINANCIAL_BOND_TRANSACTION_NOTICE;

     //金融债业务通知单
     public static final long POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY;

     //政策性金融债购入类申请
     public static final long POLICY_RELATED_FINANCIAL_BOND_SELL_APPLY = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_SELL_APPLY;

     //政策性金融债卖出类申请
     public static final long POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE = Constant.ApprovalAction.POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE;

     //政策性金融债业务通知单
     public static final long ENTERPRISE_BOND_BUYIN_APPLY = Constant.ApprovalAction.ENTERPRISE_BOND_BUYIN_APPLY; //企业债购入类申请

     public static final long ENTERPRISE_BOND_SELL_APPLY = Constant.ApprovalAction.ENTERPRISE_BOND_SELL_APPLY; //企业债卖出类申请

     public static final long ENTERPRISE_BOND_TRANSACTION_NOTICE = Constant.ApprovalAction.ENTERPRISE_BOND_TRANSACTION_NOTICE;

     //企业债业务通知单
     public static final long TRANSFORMABLE_BOND_BUYIN_APPLY = Constant.ApprovalAction.TRANSFORMABLE_BOND_BUYIN_APPLY;

     //可转债购入类申请
     public static final long TRANSFORMABLE_BOND_SELL_APPLY = Constant.ApprovalAction.TRANSFORMABLE_BOND_SELL_APPLY;

     //可转债卖出类申请
     public static final long DEBT_TO_EQUITY = Constant.ApprovalAction.DEBT_TO_EQUITY; //债转股申请

     public static final long TRANSFORMABLE_BOND_TRANSACTION_NOTICE = Constant.ApprovalAction.TRANSFORMABLE_BOND_TRANSACTION_NOTICE;

     //可转债业务通知单
     public static final long FUND_BUYIN_APPLY = Constant.ApprovalAction.FUND_BUYIN_APPLY; //基金购入类申请

     public static final long FUND_SELL_APPLY = Constant.ApprovalAction.FUND_SELL_APPLY; //基金卖出类申请

     public static final long FUND_TRANSACTION_NOTICE = Constant.ApprovalAction.FUND_TRANSACTION_NOTICE; //基金业务通知单

     public static final long CAPITAL_IN_CREDIT_EXTENSION_APPLY = Constant.ApprovalAction.CAPITAL_IN_CREDIT_EXTENSION_APPLY;

     //资金拆入授信申请
     public static final long CAPITAL_OUT_CREDIT_EXTENSION_APPLY = Constant.ApprovalAction.CAPITAL_OUT_CREDIT_EXTENSION_APPLY;

     //资金拆出授信申请
     public static final long CAPITAL_TRANSFER_NOTICE = Constant.ApprovalAction.CAPITAL_TRANSFER_NOTICE; //资金划拨业务通知单

     public static final long CAPITAL_REPURCHASE_APPLY = Constant.ApprovalAction.CAPITAL_REPURCHASE_APPLY; //资产回购申请书处理

     public static final long CAPITAL_REPURCHASE_CONTRACT = Constant.ApprovalAction.CAPITAL_REPURCHASE_CONTRACT; //资产回购合同处理

     public static final long CAPITAL_REPURCHASE_NOTICE = Constant.ApprovalAction.CAPITAL_REPURCHASE_NOTICE; //资产回购业务通知单

     public static final long ENTRUST_FINANCING_APPLY = Constant.ApprovalAction.ENTRUST_FINANCING_APPLY; //委托理财申请书处理

     public static final long ENTRUST_FINANCING_CONTRACT = Constant.ApprovalAction.ENTRUST_FINANCING_CONTRACT; //委托理财合同处理

     public static final long ENTRUST_FINANCING_NOTICE = Constant.ApprovalAction.ENTRUST_FINANCING_NOTICE; //委托理财业务通知单

     public static final long ENTRUST_FINANCING_PLAN = Constant.ApprovalAction.ENTRUST_FINANCING_PLAN; //委托理财执行计划修改

     public static final long ENTRUSTED_FINANCING_APPLY = Constant.ApprovalAction.ENTRUSTED_FINANCING_APPLY; //受托理财申请书处理

     public static final long ENTRUSTED_FINANCING_CONTRACT = Constant.ApprovalAction.ENTRUSTED_FINANCING_CONTRACT;

     //受托理财合同处理
     public static final long ENTRUSTED_FINANCING_NOTICE = Constant.ApprovalAction.ENTRUSTED_FINANCING_NOTICE; //受托理财业务通知单

     public static final long FOREIGN_CURRENCY_INVESTMENT_APPLY = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_APPLY;

     //结构性投资申请书处理
     public static final long FOREIGN_CURRENCY_INVESTMENT_CONTRACT = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_CONTRACT;

     //结构性投资合同处理
     public static final long FOREIGN_CURRENCY_INVESTMENT_NOTICE = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_NOTICE;

     //结构性投资业务通知单
     public static final long FOREIGN_CURRENCY_INVESTMENT_PLAN = Constant.ApprovalAction.FOREIGN_CURRENCY_INVESTMENT_PLAN;

     //结构性投资执行计划修改
     public static final long STOCK_INVESTMENT_APPLY = Constant.ApprovalAction.STOCK_INVESTMENT_APPLY; //股权投资申请书处理

     public static final long STOCK_INVESTMENT_CONTRACT = Constant.ApprovalAction.STOCK_INVESTMENT_CONTRACT; //股权投资合同处理

     public static final long STOCK_INVESTMENT_NOTICE = Constant.ApprovalAction.STOCK_INVESTMENT_NOTICE; //股权投资业务通知单

     public static final long BOND_UNDERWRITING_APPLY = Constant.ApprovalAction.BOND_UNDERWRITING_APPLY; //债券承销申请书处理

     public static final long BOND_UNDERWRITING_CONTRACT = Constant.ApprovalAction.BOND_UNDERWRITING_CONTRACT; //债券承销合同处理

     public static final long BOND_UNDERWRITING_NOTICE = Constant.ApprovalAction.BOND_UNDERWRITING_NOTICE; //债券承销业务通知单

     public static final long INSURANCE_NOTICE = Constant.ApprovalAction.INSURANCE_NOTICE; //保险代理业务通知单
     
     //预算模块操作类型
     public static final long BUDGET_CURRENT = Constant.ApprovalAction.BUDGET_CURRENT; //本单位审批

     public static final long BUDGET_TOTAL = Constant.ApprovalAction.BUDGET_TOTAL; //下级单位汇总审批
     
     //信用评级专用
     public static final long CRERT_CREDITRATING = Constant.ApprovalAction.CRERT_CREDITRATING; //信用评级
     
     public static final long CRERT_CREDITEVALUTION = Constant.ApprovalAction.CRERT_CREDITEVALUTION; //信用重估
     
     public static final long CRERT_BECOMINGINVALID = Constant.ApprovalAction.CRERT_BECOMINGINVALID;//作废
     //信贷资产转让
     public static final long CRA_TRANSLOANAPPLY = Constant.ApprovalAction.CRA_TRANSLOANAPPLY; //转让申请
     
     public static final long CRA_TRANSLOANCONTRACT = Constant.ApprovalAction.CRA_TRANSLOANCONTRACT; //转让合同
     
     public static final long CRA_TRANSLOANNOTICE = Constant.ApprovalAction.CRA_TRANSLOANNOTICE;//转让通知单
     
     public static final long CRA_LOANCONTRACT_APPLY = Constant.ApprovalAction.CRA_LOANCONTRACT_APPLY;//贷款合同转让
     
     //转贴现
     public static final long ZTX_APPLY = 9001; //转贴现申请
     
     public static final long ZTX_CONTRACT = 9002; //转贴现合同
     
     public static final long ZTX_REPURCHASECREDENCE = 9003; //回购凭证
     
     //资金拆借
     public static final long FUNDBORROWING_APPLY = 10001; //资金拆借申请
     
     public static final long FUNDBORROWING_DELIVER = 10002; //资金拆借交割单
     
     public static final long FUNDBORROWING_NOTICE = 10003;  //资金拆借业务通知单
     
     
     
     /**
      * 用代码值得到代码名称
      * 
      * @param lCodeType
      *            代码类型
      * @param lCode
      *            代码
      */
     public static final String getName(long lCode) throws Exception
     {
         String strReturn = ""; //初始化返回值
         switch ((int) lCode)
         {
             case (int) LOAN_APPLY:
                 strReturn = "贷款申请";
                 break;
             case (int) LOAN_CONTRACT:
                 strReturn = "贷款合同";
                 break;
             case (int) EXTEND_APPLY:
                 strReturn = "展期申请";
                 break;
             case (int) EXTEND_CONTRACT:
                 strReturn = "展期合同";
                 break;
             case (int) FREE_APPLY:
                 strReturn = "免还申请";
                 break;
             case (int) LOANPAY_NOTICE:
                 strReturn = "放款通知单";
                 break;
             case (int) AHEADREPAY_NOTICE:
                 strReturn = "还款通知单";
                 break;
             case (int) INTEREST_ADJUST:
                 strReturn = "利率调整";
                 break;
             case (int) CONTRACT_PLAN:
                 strReturn = "合同执行计划更改";
                 break;
             case (int) ATTORNMENT_APPLY:
                 strReturn = "贷款转让";
                 break;
             case (int) SECURITIES_CONTRACT_PLAN:
                 strReturn = "证券合同执行计划更改";
                 break;
             case (int) CONTRACT_STATUS:
                 strReturn = "合同状态变更";
                 break;
             case (int) CONTRACT_RISKLEVEL:
                 strReturn = "合同风险状态变更";
                 break;
             case (int) OVERDUE_APPLY:
                 strReturn = "逾期申请";
                 break;
             case (int) DISCOUNT_CREDENCE:
                 strReturn = "贴现凭证";
                 break;
             case (int) LOANDRAW_NOTICE:
                 strReturn = "提款通知单";
                 break;
             case (int) TRANSDISCOUNT_CREDENCE:
                 strReturn = "转贴现凭证";
                 break;
             case (int) TRANSDISCOUNT_REPURCHASECREDENCE:
                 strReturn = "转贴现回购凭证";
                 break;
             case (int) ASSURE_CHARGE_NOTICE:
                 strReturn = "担保收款通知单";
                 break;
             case (int) ASSURE_MANAGEMENT_NOTICE:
                 strReturn = "保后处理通知单";
                 break;
             case (int) ASSURE_ADJUST:
             	strReturn = "保证金调整";
             	break;                	
             case (int) LEASEHOLDPAY_NOTICE:
             	strReturn = "融资租赁收款通知单";
             	break;
             case (int) LEASEHOLDREPAY_NOTICE:
             	strReturn = "融资租赁还款通知单";
             	break;
             case (int)LEASEHOLDREPAY_ADJUSTNOTICE:
             	strReturn = "融资租赁调整通知单";
             	break;
             /////////////////////////////
             case (int) CAPITAL_IN_APPLY:
                 strReturn = "资金拆入申请";
                 break;
             case (int) CAPITAL_OUT_APPLY:
                 strReturn = "资金拆出申请";
                 break;
             case (int) CAPITAL_LANDING_NOTICE:
                 strReturn = "资金拆借业务通知单";
                 break;
             case (int) STOCK_BUYIN_APPLY:
                 strReturn = "股票购入类申请";
                 break;
             case (int) STOCK_SELL_APPLY:
                 strReturn = "股票卖出类申请";
                 break;
             case (int) STOCK_TRANSACTION_NOTICE:
                 strReturn = "股票业务通知单";
                 break;
             case (int) NOTE_BUYIN_APPLY:
                 strReturn = "央行票据购入类申请";
                 break;
             case (int) NOTE_SELL_APPLY:
                 strReturn = "央行票据卖出类申请";
                 break;
             case (int) CENTRAL_BANK_NOTE_TRANSACTION_NOTICE:
                 strReturn = "央行票据业务通知单";
                 break;
             case (int) BANK_FUND_REPURCHASE_APPLY:
                 strReturn = "银行间债券正回购申请";
                 break;
             case (int) BANK_BOND_REPURCHASE_APPLY:
                 strReturn = "银行间债券逆回购申请";
                 break;
             case (int) BANK_BOND_REPURCHASE_NOTICE:
                 strReturn = "银行间债券回购业务通知单";
                 break;
             case (int) EXCHANGECENTER_FUND_REPURCHASE_APPLY:
                 strReturn = "交易所债券融资回购申请";
                 break;
             case (int) EXCHANGECENTER_BOND_REPURCHASE_APPLY:
                 strReturn = "交易所债券融券回购申请";
                 break;
             case (int) EXCHANGECENTER_BOND_REPURCHASE_NOTICE:
                 strReturn = "交易所债券回购业务通知单";
                 break;
             case (int) BANK_NATIONAL_BOND_BUYIN_APPLY:
                 strReturn = "银行间国债购入类申请";
                 break;
             case (int) BANK_NATIONAL_BOND_SELL_APPLY:
                 strReturn = "银行间国债卖出类申请";
                 break;
             case (int) BANK_NATIONAL_BOND_TRANSACTION_NOTICE:
                 strReturn = "银行间国债业务通知单";
                 break;
             case (int) EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY:
                 strReturn = "交易所国债购入类申请";
                 break;
             case (int) EXCHANGECENTER_NATIONAL_BOND_SELL_APPLY:
                 strReturn = "交易所国债卖出类申请";
                 break;
             case (int) EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE:
                 strReturn = "交易所国债业务通知单";
                 break;
             case (int) FINANCIAL_BOND_BUYIN_APPLY:
                 strReturn = "金融债购入类申请";
                 break;
             case (int) FINANCIAL_BOND_SELL_APPLY:
                 strReturn = "金融债卖出类申请";
                 break;
             case (int) FINANCIAL_BOND_TRANSACTION_NOTICE:
                 strReturn = "金融债业务通知单";
                 break;
             case (int) POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY:
                 strReturn = "政策性金融债购入类申请";
                 break;
             case (int) POLICY_RELATED_FINANCIAL_BOND_SELL_APPLY:
                 strReturn = "政策性金融债卖出类申请";
                 break;
             case (int) POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE:
                 strReturn = "政策性金融债业务通知单";
                 break;
             case (int) ENTERPRISE_BOND_BUYIN_APPLY:
                 strReturn = "企业债购入类申请";
                 break;
             case (int) ENTERPRISE_BOND_SELL_APPLY:
                 strReturn = "企业债卖出类申请";
                 break;
             case (int) ENTERPRISE_BOND_TRANSACTION_NOTICE:
                 strReturn = "企业债业务通知单";
                 break;
             case (int) TRANSFORMABLE_BOND_BUYIN_APPLY:
                 strReturn = "可转债购入类申请";
                 break;
             case (int) TRANSFORMABLE_BOND_SELL_APPLY:
                 strReturn = "可转债卖出类申请";
                 break;
             case (int) DEBT_TO_EQUITY:
                 strReturn = "债转股申请";
                 break;
             case (int) TRANSFORMABLE_BOND_TRANSACTION_NOTICE:
                 strReturn = "可转债业务通知单";
                 break;
             case (int) FUND_BUYIN_APPLY:
                 strReturn = "基金购入类申请";
                 break;
             case (int) FUND_SELL_APPLY:
                 strReturn = "基金卖出类申请";
                 break;
             case (int) FUND_TRANSACTION_NOTICE:
                 strReturn = "基金业务通知单";
                 break;
             case (int) CAPITAL_IN_CREDIT_EXTENSION_APPLY:
                 strReturn = "资金拆入授信申请";
                 break;
             case (int) CAPITAL_OUT_CREDIT_EXTENSION_APPLY:
                 strReturn = "资金拆出授信申请";
                 break;
             case (int) CAPITAL_TRANSFER_NOTICE:
                 strReturn = "资金划拨业务通知单";
                 break;
             case (int) CAPITAL_REPURCHASE_APPLY:
                 strReturn = "资产回购申请书处理";
                 break;
             case (int) CAPITAL_REPURCHASE_CONTRACT:
                 strReturn = "资产回购合同处理";
                 break;
             case (int) CAPITAL_REPURCHASE_NOTICE:
                 strReturn = "资产回购业务通知单";
                 break;
             case (int) ENTRUST_FINANCING_APPLY:
                 strReturn = "委托理财申请书处理";
                 break;
             case (int) ENTRUST_FINANCING_CONTRACT:
                 strReturn = "委托理财合同处理";
                 break;
             case (int) ENTRUST_FINANCING_NOTICE:
                 strReturn = "委托理财业务通知单";
                 break;
             case (int) ENTRUST_FINANCING_PLAN:
                 strReturn = "委托理财执行计划修改";
                 break;
             case (int) ENTRUSTED_FINANCING_APPLY:
                 strReturn = "受托理财申请书处理";
                 break;
             case (int) ENTRUSTED_FINANCING_CONTRACT:
                 strReturn = "受托理财合同处理";
                 break;
             case (int) ENTRUSTED_FINANCING_NOTICE:
                 strReturn = "受托理财业务通知单";
                 break;
             case (int) FOREIGN_CURRENCY_INVESTMENT_APPLY:
                 strReturn = "结构性投资申请书处理";
                 break;
             case (int) FOREIGN_CURRENCY_INVESTMENT_CONTRACT:
                 strReturn = "结构性投资合同处理";
                 break;
             case (int) FOREIGN_CURRENCY_INVESTMENT_NOTICE:
                 strReturn = "结构性投资业务通知单";
                 break;
             case (int) FOREIGN_CURRENCY_INVESTMENT_PLAN:
                 strReturn = "结构性投资执行计划修改";
                 break;
             case (int) STOCK_INVESTMENT_APPLY:
                 strReturn = "股权投资申请书处理";
                 break;
             case (int) STOCK_INVESTMENT_CONTRACT:
                 strReturn = "股权投资合同处理";
                 break;
             case (int) STOCK_INVESTMENT_NOTICE:
                 strReturn = "股权投资业务通知单";
                 break;
             case (int) BOND_UNDERWRITING_APPLY:
                 strReturn = "债券承销申请书处理";
                 break;
             case (int) BOND_UNDERWRITING_CONTRACT:
                 strReturn = "债券承销合同处理";
                 break;
             case (int) BOND_UNDERWRITING_NOTICE:
                 strReturn = "债券承销业务通知单";
                 break;
             case (int) INSURANCE_NOTICE:
                 strReturn = "保险代理业务通知单";
                 break;
             case (int) BUDGET_CURRENT:
                 strReturn = "本单位审批";
                 break;
             case (int) BUDGET_TOTAL:
                 strReturn = "下级单位汇总审批";
                 break;
            //信用评级专用 start
             case (int) CRERT_CREDITRATING:
                 strReturn = "信用评级";
                 break;
             case (int) CRERT_CREDITEVALUTION:
                 strReturn = "信用重估";
                 break;
             case (int) CRERT_BECOMINGINVALID:
                 strReturn = "作废";
                 break;                   
             // end 
             //信贷资产转让
             case (int) CRA_TRANSLOANAPPLY:
                 strReturn = "资产转让申请";
                 break;
             case (int) CRA_TRANSLOANCONTRACT:
                 strReturn = "资产转让合同";
                 break;
             case (int) CRA_TRANSLOANNOTICE:
                 strReturn = "资产转让通知单";
                 break;
             case (int) CRA_LOANCONTRACT_APPLY:
                 strReturn = "资产贷款合同转让";
                 break;
             case (int) ZTX_APPLY:
            	 strReturn = "转贴现申请";
             	 break;
             case (int) ZTX_CONTRACT:
            	 strReturn = "转贴现合同";
             	 break;
             case (int) ZTX_REPURCHASECREDENCE:
            	 strReturn = "转贴现回购凭证";
             	 break;
             case (int) FUNDBORROWING_APPLY:
            	 strReturn = "资金拆借申请";
             	 break;
             case (int) FUNDBORROWING_DELIVER:
            	 strReturn = "资金拆借交割单";
             	 break;
             case (int) FUNDBORROWING_NOTICE:
            	 strReturn = "资金拆借业务通知单";
             	 break;
             default:
            	 strReturn = "";
             	 break;
            	 
                 
         }
         return strReturn;
     }
     public static final long[] getAllCode(long lModuleType)
     {
     	long[] lTemp = null;
     	switch ((int) lModuleType)
     	{
     		case (int) ModuleType.LOAN:
     				lTemp = new long[]{ 
                     		LOAN_APPLY,
                     		LOAN_CONTRACT,
                     		EXTEND_APPLY,
                     		EXTEND_CONTRACT,
                     		FREE_APPLY,
                     		LOANPAY_NOTICE,
                     		AHEADREPAY_NOTICE,
                     		INTEREST_ADJUST,
                     		CONTRACT_PLAN,
                     		CONTRACT_STATUS,
                     		CONTRACT_RISKLEVEL,
                     		OVERDUE_APPLY,
                     		DISCOUNT_CREDENCE,
                     		LOANDRAW_NOTICE,
                     		ATTORNMENT_APPLY,
                     		TRANSDISCOUNT_CREDENCE,
                     		TRANSDISCOUNT_REPURCHASECREDENCE,
                     		ASSURE_CHARGE_NOTICE,
                     		ASSURE_MANAGEMENT_NOTICE,
                     		ASSURE_ADJUST,
                     		LEASEHOLDPAY_NOTICE,
                     		LEASEHOLDREPAY_NOTICE
                     	};
     			break;
     		case (int) ModuleType.SECURITIES:
     			lTemp = new long[]{
     				SECURITIES_CONTRACT_PLAN,
             		CAPITAL_IN_APPLY,
             		CAPITAL_OUT_APPLY,
             		CAPITAL_LANDING_NOTICE,
             		STOCK_BUYIN_APPLY,
             		STOCK_SELL_APPLY,
             		STOCK_TRANSACTION_NOTICE,
             		NOTE_BUYIN_APPLY,
             		NOTE_SELL_APPLY,
             		CENTRAL_BANK_NOTE_TRANSACTION_NOTICE,
             		BANK_FUND_REPURCHASE_APPLY,
             		BANK_BOND_REPURCHASE_APPLY,
             		BANK_BOND_REPURCHASE_NOTICE,
             		EXCHANGECENTER_FUND_REPURCHASE_APPLY, 
             		EXCHANGECENTER_BOND_REPURCHASE_APPLY, 
             		EXCHANGECENTER_BOND_REPURCHASE_NOTICE,
             		BANK_NATIONAL_BOND_BUYIN_APPLY,
             		BANK_NATIONAL_BOND_SELL_APPLY,
             		BANK_NATIONAL_BOND_TRANSACTION_NOTICE,
             		EXCHANGECENTER_NATIONAL_BOND_BUYIN_APPLY,
             		EXCHANGECENTER_NATIONAL_BOND_SELL_APPLY,
             		EXCHANGECENTER_NATIONAL_BOND_TRANSACTION_NOTICE,
             		FINANCIAL_BOND_BUYIN_APPLY,
             		FINANCIAL_BOND_SELL_APPLY,
             		FINANCIAL_BOND_TRANSACTION_NOTICE,
             		POLICY_RELATED_FINANCIAL_BOND_BUYIN_APPLY,
             		POLICY_RELATED_FINANCIAL_BOND_SELL_APPLY,
             		POLICY_RELATED_FINANCIAL_BOND_TRANSACTION_NOTICE,
             		ENTERPRISE_BOND_BUYIN_APPLY,
             		ENTERPRISE_BOND_SELL_APPLY,
             		ENTERPRISE_BOND_TRANSACTION_NOTICE,
             		TRANSFORMABLE_BOND_BUYIN_APPLY,
             		TRANSFORMABLE_BOND_SELL_APPLY,
             		DEBT_TO_EQUITY,
             		TRANSFORMABLE_BOND_TRANSACTION_NOTICE,
             		FUND_BUYIN_APPLY,
             		FUND_SELL_APPLY,
             		FUND_TRANSACTION_NOTICE,
             		CAPITAL_IN_CREDIT_EXTENSION_APPLY,
             		CAPITAL_OUT_CREDIT_EXTENSION_APPLY,
             		CAPITAL_TRANSFER_NOTICE,
             		CAPITAL_REPURCHASE_APPLY,
             		CAPITAL_REPURCHASE_CONTRACT,
             		CAPITAL_REPURCHASE_NOTICE,
             		ENTRUST_FINANCING_APPLY,
             		ENTRUST_FINANCING_CONTRACT,
             		ENTRUST_FINANCING_NOTICE,
             		ENTRUST_FINANCING_PLAN,
             		ENTRUSTED_FINANCING_APPLY,
             		ENTRUSTED_FINANCING_CONTRACT,
             		ENTRUSTED_FINANCING_NOTICE,
             		FOREIGN_CURRENCY_INVESTMENT_APPLY,
             		FOREIGN_CURRENCY_INVESTMENT_CONTRACT,
             		FOREIGN_CURRENCY_INVESTMENT_NOTICE,
             		FOREIGN_CURRENCY_INVESTMENT_PLAN,
             		STOCK_INVESTMENT_APPLY,
             		STOCK_INVESTMENT_CONTRACT,
             		STOCK_INVESTMENT_NOTICE,
             		BOND_UNDERWRITING_APPLY,
             		BOND_UNDERWRITING_CONTRACT,
             		BOND_UNDERWRITING_NOTICE,
             		INSURANCE_NOTICE
             	};
     			break;
     			case(int)ModuleType.CREDITRATING:
 			    lTemp = new long[]{
     					CRERT_CREDITRATING,
     					CRERT_CREDITEVALUTION,
     					CRERT_BECOMINGINVALID
     				}; 
     			break;
     			case(int)ModuleType.CRAFTBROTHER:
     			    lTemp = new long[]{
     					ZTX_APPLY,
     					ZTX_CONTRACT,
     					ZTX_REPURCHASECREDENCE,
     					FUNDBORROWING_APPLY,
     					FUNDBORROWING_DELIVER,
     					FUNDBORROWING_NOTICE,
     					CRA_TRANSLOANAPPLY,
     					CRA_LOANCONTRACT_APPLY,
     					CRA_TRANSLOANCONTRACT,
     					CRA_TRANSLOANNOTICE
         				}; 
         			break;
     			case(int)ModuleType.BUDGET:
     			    lTemp = new long[]{
     			        BUDGET_CURRENT,BUDGET_TOTAL
     			};
     	}            
         return lTemp;
     }        
 
 }
 
 //配合ShowComfirmMessage.jsp使用
 public static class ComfirmMessageValidateName
 {
	 public static final String Message1 = "isValidateComfirmMessage1";
	 public static final String Message2 = "isValidateComfirmMessage2";
	 public static final String Message3 = "isValidateComfirmMessage3";
	 
	 
 }
 
 	//电子回单打印时间间隔
 	public static class EvoucherPrint
	{
 		//没有配置项从此获得打印间隔时间,4000为4秒,建议根据打印机和网络的实际情况调整
		public static final int ITIME = 4000;
	}
 	
//风险-状态
    public static final class RiskUpAndDown
    {
       

        public static final long DOWN = 1;

        public static final long UP = 2;

        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final String getName(long lCode) throws Exception
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) DOWN:
                    strReturn = "≥";
                    break;
                case (int) UP:
                    strReturn = "≤";
                    break;
            }
            return strReturn;
        }
        
    }
//  风险-级别
    public static final class RiskLelvel
    {
       

        public static final long one = 1;

        public static final long two = 2;
        
        public static final long three = 3;

        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final String getName(long lCode) throws Exception
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) one:
                    strReturn = "Ⅰ";
                    break;
                case (int) two:
                    strReturn = "Ⅱ";
                    break;
                case (int) three:
                    strReturn = "Ⅲ";
                    break;
            }
            return strReturn;
        }
        
    }
//  风险-状态2
    public static final class UpAndDown
    {
       

        public static final long DOWN = 1;

        public static final long UP = 2;

        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final String getName(long lCode) throws Exception
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) DOWN:
                    strReturn = "以下";
                    break;
                case (int) UP:
                    strReturn = "以上";
                    break;
            }
            return strReturn;
        }
        
    }
    
    
    public static final class TransLogActionType
    {
       

        public static final long insert = 1;//新增

        public static final long delete = 2;//删除
        
        public static final long modify = 3;//修改
        
        public static final long search = 4;//查询
        
        public static final long export = 5;//导出查询结果
        
        public static final long print = 6;//打印
        
        public static final long initApproval = 7;//保存并提交审批
        
        public static final long approval = 8; //审批
        
        public static final long cancelApproval = 9; //取消审批
        
        public static final long check = 10;//复核
        
        public static final long cancelCheck = 11;//取消复核
        
        public static final long inport = 12;//导入
        
        public static final long sign = 13;//签认
        
        public static final long cancelSign = 14;//取消签认

        /**
         * 用代码值得到代码名称
         * 
         * @param lCodeType
         *            代码类型
         * @param lCode
         *            代码
         */
        public static final String getName(long lCode) throws Exception
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) insert:
                    strReturn = "新增";
                    break;
                case (int) delete:
                    strReturn = "删除";
                    break;
                case (int) modify:
                    strReturn = "修改";
                    break;
                case (int) search:
                    strReturn = "导出查询结果";
                    break;
                case (int) export:
                    strReturn = "打印";
                    break;
                case (int) initApproval:
                    strReturn = "保存并提交审批";
                    break;
                case (int) approval:
                    strReturn = "审批";
                    break;
                case (int) cancelApproval:
                    strReturn = "取消审批";
                    break;
                case (int) check:
                    strReturn = "复核";
                    break;
                case (int) cancelCheck:
                    strReturn = "取消复核";
                    break;
                case (int) print:
                    strReturn = "查询";
                    break;
                case (int) inport:
                    strReturn = "导入";
                    break;
                case (int) sign:
                    strReturn = "签认";
                    break;
                case (int) cancelSign:
                    strReturn = "取消签认";
                    break;
            }
            return strReturn;
        }
        
    }
    
    public static final class SystemLogType
    {
        public static final long loginlog = 1;//系统登录日志

        public static final long menulog = 2;//菜单日志
        
        public static final long translog = 3;//业务日志
        
        public static final long all = 0;//业务日志
        
        public static final String getName(long lCode) throws Exception
        {
        	 String strReturn = ""; //初始化返回值
             switch ((int) lCode)
             {
                 case (int) loginlog:
                     strReturn = "登录日志";
                     break;
                 case (int) menulog:
                     strReturn = "菜单日志";
                     break;
                 case (int) translog:
                     strReturn = "操作日志";
                     break;
                 case (int) all:
                     strReturn = "全部";
                     break;
             }
             return strReturn;
        	
        }
        
        public static final long[] getAllCode()
		{
			long[] lTemp = { all,loginlog, menulog, translog};
			return lTemp;
		}
        
        public static final long[] getOtherCode()
		{
			long[] lTemp = { all,loginlog, menulog};
			return lTemp;
		}
        
        /**
		  * 显示下拉列表
		  * 
		  * @param out
		  * @param strControlName,
		  *            控件名称
		  * @param nType，控件类型（0，显示全部；1,没有清户选项）
		  * @param lSelectValue
		  * @param isNeedAll，是否需要”全部项“
		  * @param isNeedBlank
		  *            是否需要空白行
		  * @param strProperty
		  */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
					case 1 :
						lArrayID = getOtherCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank,false);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
    }
    
    public static final class LogActionResult
    {
        public static final long success = 1;//操作成功

        public static final long fail = 0;//操作失败
        
        public static final long all = 2;//全部
        
        public static final String getName(long lCode) throws Exception
        {
        	 String strReturn = ""; //初始化返回值
             switch ((int) lCode)
             {
                 case (int) success:
                     strReturn = "成功";
                     break;
                 case (int) fail:
                     strReturn = "失败";
                     break;
                 case (int) all:
                     strReturn = "全部";
                     break;
                     
             }
             return strReturn;
        	
        }
        
        public static final long[] getAllCode()
		{
			long[] lTemp = {all, success, fail };
			return lTemp;
		}
        
        /**
		  * 显示下拉列表
		  * 
		  * @param out
		  * @param strControlName,
		  *            控件名称
		  * @param nType，控件类型（0，显示全部；1,没有清户选项）
		  * @param lSelectValue
		  * @param isNeedAll，是否需要”全部项“
		  * @param isNeedBlank
		  *            是否需要空白行
		  * @param strProperty
		  */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 0 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank,false);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
    }
    
    /**
     * add by zhouxiang 2010-9-30
     * 通用文本域控件
     * 
     */
    public static final class CommonTextarea{
    	
    	public static final void show(
    			
    			JspWriter out,
    			String strTitle,		//显示名称
    			String strFirstTD,		//显示名称TD属性
    			String strMainName,		//控件名称
    			String strSecondTD, 	//控件TD属性
    			boolean isNeedInput,	//是否为必输项
    			long lRows,				//行数
    			long lCols,				//列数
    			String initValue,		//默认值
    			long maxLength,			//可输入的最大字节数
    			String strNextControls, //下一焦点
    			String strProperty		//控件其他属性
    			)
    	throws Exception
    	{
    		long initLen = 0;
    		long charLen = 0;
    		int initValueLen = initValue.length();
			for(int i = 0; i < initValueLen; i++){
			     if(initValue.charAt(i)>255){
			    	 charLen+=2;
			     }else{
			         charLen+=1;
			     }  
			}
			initLen = maxLength - charLen;
    		String strMainProperty = " onpropertychange='checkStr("+maxLength+",\""+strMainName+"\")' onkeydown='checkStrEnter("+maxLength+",\""+strMainName+"\")' onblur ='checkStrMessage("+maxLength+",\""+strMainName+"\")' ";
    		if (strNextControls != null && strNextControls.length() > 0)
    		{
    			strMainProperty += " onfocus=\"nextfield='" + strNextControls + "'\"";
    		}
    		if (strProperty != null && strProperty.length() > 0)
    		{
    			strMainProperty += " "+strProperty;
    		}
    		if(isNeedInput) strTitle = "<font color='red'>*</font>&nbsp;" + strTitle;
    		out.println(
    					"<td "+strFirstTD+" >"
    					+ strTitle
    					+ "</td>"
    					+"<td "
						+ strSecondTD
						+ " ><textarea name='"
						+ strMainName
						+ "' id='" + strMainName+ "' class=\"box\" rows="+lRows+" cols="+lCols +" value=\""+initValue+"\""
						+ strMainProperty+">"
						+ initValue
						+ "</textarea></br>"
						+ "<span id='"+strMainName+"textAreaShow'>最多<b>"+maxLength+"</b>个字符（一个汉字2个字符），还可以输入 <b>"+initLen+"</b> 个字符</span></td>");
    		
    	}
    	
    }
    /**常量表设置           
     * 
     */ 
    public static final class SystemConstantTable{
    	
        public static final String ANALYSIS = "Analysis";                // 差错分析
        public static final String RECEIPT  = "Receipt" ;                // 单据类型
        public static final String VOUCHERINIT  = "VoucherInit" ;        // 回单属性是否初始化  0 已经初始化   1 还未初始化
        public static final String VOUCHERACCOUNT = "VoucherAccount" ;   // 回单属性 1 普通     2 归口        3归口子账户
        public static final String VOUCHERPLACE = "VoucherPlace" ;       // 网点属性 1 厂区内   2 厂区外
        public static final String TRANSSOURCE = "TransSource" ;         // 交易数据来源 1 柜台业务     2 网银指令
        public static final String SELECTMONTH = "SelectMonth" ;         // 下拉月份
        public static final String SELECTYEAR = "SelectYear" ;           // 下拉年
        public static final String RELATIONTYPE = "RelationType";        // 体系类型:  1上划    2下拨    3代理付款
        public static final String BANKTYPE = "BankType";                // 银行类型:  1工行    2建行    3财务公司
        
       
        /**常量表设置              lijunli  2010.12.7
         * 
         * @param lCode
         * @return @throws
         *         Exception
         */  
      public static final String getName(String sCode) throws Exception {
    	  String  strReturn = "";
 
    	  if (sCode.equals(ANALYSIS) )
    	  {
    		  strReturn = "差错分析";
    	  }
    	  if (sCode.equals(RECEIPT))
    	  {
    		  strReturn = "单据类型";
    	  }
    	  if (sCode.equals(VOUCHERINIT))
    	  {
    		  strReturn = "回单属性是否初始化";
    	  }
    	  if (sCode.equals(VOUCHERACCOUNT))
    	  {
    		  strReturn = "回单属性";
    	  }
    	  
    	  if (sCode.equals(VOUCHERPLACE))
    	  {
    		  strReturn = "网点属性";
    	  }
    	  if (TRANSSOURCE.equals(sCode))
    	  {
    		  strReturn = "交易数据来源";
    	  } 	  
    	  if (sCode.equals(SELECTYEAR) )
    	  {
    		  strReturn = "年";
    	  }
    	  if (sCode.equals(SELECTMONTH) )
    	  {
    		  strReturn = "月";
    	  }
    	  if (RELATIONTYPE.equals(sCode))
    	  {
    		  strReturn = "体系类型";
    	  } 
    	  if (BANKTYPE.equals(sCode))
    	  {
    		  strReturn = "银行类型";
    	  } 
		return strReturn;
    	  
    	  
    		
    	  
      }  
      
      public static final String[] getAllCode()
		{
			
    	  String[] lTemp = {ANALYSIS,RECEIPT,VOUCHERINIT,VOUCHERACCOUNT,VOUCHERPLACE,TRANSSOURCE};
			
			return lTemp;
		}
        
      /**lijunli  2010.12.7
       * 
       * @param whereSQL
       * @param out
       * @param strControlName
       * @param nType
       * @param lSelectValue
       * @param isNeedAll
       * @param isNeedBlank
       * @param strProperty
       * @throws Exception
       */
      
      public static final void showList(JspWriter out, String strControlName,  String strSelectValue, boolean isNeedAll, String strProperty, boolean isNeedBlank)throws Exception{
			
		 String[] strArrayName = null;
      	 String[] strArrayValue = null ;
          
          strArrayValue = getAllCode() ;
          strArrayName = new String[strArrayValue.length];
          for(int i=0;i<strArrayValue.length;i++){
        	  strArrayName[i] = getName(strArrayValue[i]);
          }

          showCommonList(out, strControlName, strArrayValue,  strArrayName,  strSelectValue, isNeedAll, strProperty,  isNeedBlank);
      	
      }
      
      
        
        /**  @author 
         * 
         * @param whereSQL
         * @param out
         * @param strControlName
         * @param nType
         * @param lSelectValue
         * @param isNeedAll
         * @param isNeedBlank
         * @param strProperty
         * @throws Exception
         */
        public static final void showList(String whereSQL,JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)throws Exception{
			
        	
        	long[] lArrayID = null;
			String[] strArrayName = null;
			
			int isize = 0 ;
			
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection conn = null;
            String strSQL1 = "select count(id) num                  from sys_enumerate " + whereSQL + " order by propindex  "; 
            String strSQL2 = "select propname, propvalue, propstate from sys_enumerate " + whereSQL + " order by propindex  "; 
            
            //System.out.println("SQL1: " + strSQL1);
            //System.out.println("SQL2: " + strSQL2);
            

            try{
                conn = Database.getConnection();
                ps = conn.prepareStatement(strSQL1);
                rs = ps.executeQuery();

                while (rs.next()){
                	isize = rs.getInt("num");
                	lArrayID = new long[isize];
                	strArrayName = new String[isize];
                 }
                
                ps = conn.prepareStatement(strSQL2);
                rs = ps.executeQuery(); 
                
                int i = 0 ;
                while (rs.next() && isize >0){
         
                	lArrayID[i] = Long.parseLong(rs.getString("propvalue"));
                	strArrayName[i] =  rs.getString("propname");
                	i++;

                }
            }catch (SQLException e){
                e.printStackTrace();
                throw new Exception("发生数据库错误！");
            }finally{
                try{
                    if (rs != null){
                        rs.close();
                        rs = null;
                    }
                    if (ps != null){
                        ps.close();
                        ps = null;
                    }
                    if (conn != null){
                        conn.close();
                        conn = null;
                    }
                }catch (Exception _ex){
                    System.out.println("关闭数据库连接时发生数据库错误！");
                }
            }
			
        	showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank,false);
        	
        }
        
        
        /**
         * 根据
         * @param sFunction
         * @param lpropvalue
         * @return
         * @throws Exception
         */
        public static final String getSystemConstantName(String sFunction,long lpropvalue)throws Exception{
        	String sName = "" ;
        	
            PreparedStatement ps = null;
            ResultSet rs = null;
            Connection conn = null;
            
            String strSQL = " select propname from sys_enumerate where function = '"+sFunction+"' and propvalue = " + lpropvalue ; 


            try{
                conn = Database.getConnection();
                ps = conn.prepareStatement(strSQL);
                rs = ps.executeQuery();

                while (rs.next()){
                	sName = rs.getString("propname");
                 }

            }catch (SQLException e){
                e.printStackTrace();
                throw new Exception("发生数据库错误！");
            }finally{
                try{
                    if (rs != null){
                        rs.close();
                        rs = null;
                    }
                    if (ps != null){
                        ps.close();
                        ps = null;
                    }
                    if (conn != null){
                        conn.close();
                        conn = null;
                    }
                }catch (Exception _ex){
                    System.out.println("关闭数据库连接时发生数据库错误！");
                }
            }
        	
        	return sName ;
        }
        
    }
    
    //Boxu Add 2010-12-01 增加汇款区域"本地/异地"
	public static final class remitAreaType
    {
        public static final long NATIVE = 1;		//本地

        public static final long DEVIATIONISM = 2;	//异地
        
        public static final String getName(long lCode) throws Exception
        {
        	 String strReturn = ""; //初始化返回值
             switch ((int) lCode)
             {
                 case (int) NATIVE:
                     strReturn = "本地";
                     break;
                 case (int) DEVIATIONISM:
                     strReturn = "异地";
                     break;
             }
             return strReturn;
        	
        }
        
        public static final long[] getAllCode()
		{
			long[] lTemp = { NATIVE, DEVIATIONISM };
			return lTemp;
		}
        
        /**
		  * 显示下拉列表
		  * 
		  * @param out
		  * @param strControlName,
		  *            控件名称
		  * @param nType，控件类型（0，显示全部；1,没有清户选项）
		  * @param lSelectValue
		  * @param isNeedAll，是否需要”全部项“
		  * @param isNeedBlank
		  *            是否需要空白行
		  * @param strProperty
		  */
        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty)
		{
			long[] lArrayID = null;
			String[] strArrayName = null;
			try
			{
				switch (nType)
				{
					case 1 :
						lArrayID = getAllCode();
						break;
				}
				strArrayName = new String[lArrayID.length];
				for (int i = 0; i < lArrayID.length; i++)
				{
					strArrayName[i] = getName(lArrayID[i]);
				}
				showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank, false);
			}
			catch (Exception ex)
			{
				Log.print(ex.toString());
			}
		}
    }
	
    //Boxu Add 2010-12-01 增加汇款速度"普通/加急"
	public static final class remitSpeedType
    {
        public static final long GENERAL = 1;	//普通

        public static final long RAPID = 2;		//加急
        
        public static final String getName(long lCode) throws Exception
        {
        	 String strReturn = ""; //初始化返回值
             switch ((int) lCode)
             {
                 case (int) GENERAL:
                     strReturn = "普通";
                     break;
                 case (int) RAPID:
                     strReturn = "加急";
                     break;
             }
             return strReturn;
        }
        
        public static void showList(JspWriter out,String strControlName,long lSelectValue,boolean isNeedAll,boolean isNeedBlank, String strProperty)
        {
        	long[] lArray = null;
        	String[] strArrayName = null;
        	try
        	{
        		lArray = new long[]{GENERAL,RAPID};
        		strArrayName = new String[lArray.length];
        		for(int i=0;i<lArray.length;i++)
        		{
        			strArrayName[i]=getName(lArray[i]);
        		}
        		out.println("<select style=\"height:20px;background-color: #FFFFFF;\" name=\""+strControlName+"\" "+strProperty+">");
        		if(isNeedBlank==true)
        		{
        			if(lSelectValue==-1)
        			{
        				out.println("<option value='-1' selected></option>");
        			}
        			else
        			{
        				out.println("<option value='-1'></option>");
        			}
        		}
        		
        		if(isNeedAll==true)
        		{
        			if(lSelectValue==0)
        			{
        				out.println("<option value='0' selected>全部</option>");
        			}
        			else
        			{
        				out.println("<option value='0'>全部</option>");
        			}
        		}
        		for(int i=0;i<lArray.length;i++)
        		{
        			if(lArray[i]==lSelectValue)
        			{
        				out.println("<option value='"+lArray[i]+"' selected>"+strArrayName[i]+"</option>");
        			}
        			else
        			{
        				out.println("<option value='"+lArray[i]+"' >"+strArrayName[i]+"</option>");
        			}
        		}
        		out.println("</select>");
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
    }
	/*
	 * add by xiang 2010-12-21
	 * 用户密码加密方式
	 */
	public static final class encryptType
    {
		public static final long LARGEFIELD = 1;	//大字段方式加密
		public static final long MD5 = 2;	//大字段方式加密
    }
	/*
	 * add by wangzhen 2011-01-19
	 * 已坏帐放款单记录状态
	 */
	public static final class offBalance
    {
		public static final long YES = 1;	//转表外
		public static final long NO = 2;	//撤销
    }
	
	public static class selectOffice
	{
		/**
	     * 结算切换办事处下拉列表
	     */
	    public static void showOfficeList(JspWriter out,String controlName,String properties,long isSelectValue,boolean isNeedAll,boolean isNeedBlank,long lUserID)
	    {
	    	OfficeBiz biz = new OfficeBiz();
	    	OfficeInfo info = new OfficeInfo();
	    	ArrayList list = new ArrayList();
	    	long[] lArrayID = null;
	    	String[] strArrayName = null;
	    	Iterator it = null;
	    	int i = 0;
	    	Sys_UserAuthorityInfo conditionInfo = new Sys_UserAuthorityInfo();
	    	try
	    	{
	    		conditionInfo.setUserId(lUserID);
	    		list = biz.findOfficeByAuthority(conditionInfo);
	    		if(list!=null)
	    		{
		    		lArrayID = new long[list.size()];
		    		strArrayName = new String[list.size()];
		    		it = list.iterator();
		    		while(it.hasNext())
		    		{
		    			info = (OfficeInfo)it.next();
		    			lArrayID[i] = info.getM_lID();
		    			strArrayName[i] = info.getM_strName();
		    			i++;
		    		}
	    		}
	    		else
	    		{
	    			lArrayID = new long[]{-1};
	    			strArrayName = new String[]{""};
	    		}
	    		out.println("<select style=\"height:20px;background-color: #FFFFFF;\" name=\""+controlName+"\" "+properties+">");
	    		if(isNeedAll==true)
	    		{
	    			if(isSelectValue==0)
	    			{
	    				out.println("<option value='0' selected>全部</option>");
	    			}
	    			else
	    			{
	    				out.println("<option value='0'>全部</option>");
	    			}
	    		}
	    		if(isNeedBlank==true)
	    		{
	    			if(isSelectValue == -1)
	    			{
	    				out.println("<option value='-1' selected></option>");
	    			}
	    			else
	    			{
	    				out.println("<option value='-1'></option>");
	    			}
	    			
	    		}
	    		for(int j = 0;j<lArrayID.length;j++)
	    		{
	    			
	    			if(lArrayID[j]==isSelectValue)
	    			{
	    				out.println("<option value='"+lArrayID[j]+"' selected>"+strArrayName[j]+"</option>");
	    			}
	    			else
	    			{
	    				out.println("<option value='"+lArrayID[j]+"'>"+strArrayName[j]+"</option>");
	    			}
	    		}
	    		out.println("</select>");
	    		

	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    
	    public static void showOfficeListByModule(JspWriter out,String controlName,String properties,long isSelectValue,boolean isNeedAll,boolean isNeedBlank,long lUserID,long moduleID)
	    {
	    	OfficeBiz biz = new OfficeBiz();
	    	OfficeInfo info = new OfficeInfo();
	    	ArrayList list = new ArrayList();
	    	long[] lArrayID = null;
	    	String[] strArrayName = null;
	    	Iterator it = null;
	    	int i = 0;
	    	QueryOfficeInfo queryOfficeInfo = new QueryOfficeInfo();
	    	try
	    	{
	    		queryOfficeInfo.setLUserID(lUserID);
	    		queryOfficeInfo.setLModelID(moduleID);
	    		list = biz.findOfficeByModule(queryOfficeInfo);
	    		if(list!=null)
	    		{
		    		lArrayID = new long[list.size()];
		    		strArrayName = new String[list.size()];
		    		it = list.iterator();
		    		while(it.hasNext())
		    		{
		    			info = (OfficeInfo)it.next();
		    			lArrayID[i] = info.getM_lID();
		    			strArrayName[i] = info.getM_strName();
		    			i++;
		    		}
	    		}
	    		else
	    		{
	    			lArrayID = new long[]{-1};
	    			strArrayName = new String[]{""};
	    		}
	    		out.println("<select style=\"height:20px;background-color: #FFFFFF;\" name=\""+controlName+"\" "+properties+">");
	    		if(isNeedAll==true)
	    		{
	    			if(isSelectValue==0)
	    			{
	    				out.println("<option value='0' selected>全部</option>");
	    			}
	    			else
	    			{
	    				out.println("<option value='0'>全部</option>");
	    			}
	    		}
	    		if(isNeedBlank==true)
	    		{
	    			if(isSelectValue == -1)
	    			{
	    				out.println("<option value='-1' selected></option>");
	    			}
	    			else
	    			{
	    				out.println("<option value='-1'></option>");
	    			}
	    			
	    		}
	    		for(int j = 0;j<lArrayID.length;j++)
	    		{
	    			
	    			if(lArrayID[j]==isSelectValue)
	    			{
	    				out.println("<option value='"+lArrayID[j]+"' selected>"+strArrayName[j]+"</option>");
	    			}
	    			else
	    			{
	    				out.println("<option value='"+lArrayID[j]+"'>"+strArrayName[j]+"</option>");
	    			}
	    		}
	    		out.println("</select>");
	    		

	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	}
    
	/**
     * 操作日志 操作类型
     * @author JBPan
     * May 25, 2012 CopyRight by Isoftstone
     */
	public static final class  LoggerOfOperationType{
		public static final int CREATETEMPSAVE = 1;		//创建暂存 结算柜台业务新增页面点击【暂存】按钮           
 		public static final int MODIFYTEMPSAVE = 2;		//修改暂存 结算柜台业务修改页面点击【暂存】按钮       
 		public static final int CREATESAVE = 3;			//创建保存 结算柜台业务新增页面点击【保存】按钮         
 		public static final int MODIFYSAVE = 4;			//修改保存 结算柜台业务修改页面点击【保存】按钮         
 		public static final int DELETE = 5;				//删除  结算柜台业务修改页面点击【删除】按钮              
 		public static final int CHECK = 8;				//复核  结算柜台业务复核页面点击【复核】按钮              
 		public static final int CANCELCHECK = 9;		//取消复核  结算柜台业务取消复核页面点击【取消复核】按钮
 		public static final int INITAPPROVAL = 25;		//提交审批                                            
 		public static final int SAVEANDINITAPPROVAL = 26;	//保存并提交审批		                              
 		public static final int DOAPPRVOAL = 27; 			//审批                                                
 		public static final int CANCELAPPROVAL = 29;		//取消审批                                          
 		public static final int CANCELAPPLY = 30;			//取消申请                                            
 		public static final int CANCEL = 33;				//撤销   通知存款支取通知 点击【撤销】按钮              
 		public static final int PREDRAWINTEREST = 34;		//计提利息                                          
 		public static final int INTERESTSETTLEMENT = 35;	//结算                                            
 		public static final int CLEANPREDRAWINTEREST = 36;	//冲销计提
 		public static final int ACCEPT = 42;				//接收 网银指令接收页面 点击网银指令号 链接
 		public static final int RECIEVE = 37;				//接收确认 网银指令接收页面 点击【确认】按钮                
 		public static final int REFLUSE = 38;				//拒绝 网银指令接收页面 点击【拒绝】按钮                
 		public static final int ABANDON = 43;				//放弃 网银指令接收页面 点击【放弃】按钮 
 		public static final int BOOTSTRAP = 39;				//开机                                                
 		public static final int POWEROFF = 40;				//关机                                                
 		public static final int UPDATE = 41;				//更新 科目更新页面 点击【更新科目号】按钮              
 		public static final int SQUAREUP = 12; 				// 勾账                                               
 		public static final int CANCELSQUAREUP = 13; 		// 取消勾账                                         
 		public static final int SEND = 15; 					// 发送                                                 
 		public static final int ACTIVATE = 44; 				//激活
 		public static final int RETURN = 45;				//返回修正
		
		public static final String getName(long lCode) {
			String strReturn = "";
			switch ((int) lCode) {
			case CREATETEMPSAVE:
				strReturn = "创建暂存";
				break;
			case MODIFYTEMPSAVE:
				strReturn = "修改暂存";
				break;
			case CREATESAVE:
				strReturn = "创建保存";
				break;
			case MODIFYSAVE:
				strReturn = "修改保存";
				break;
			case DELETE:
				strReturn = "删除";
				break;
			case CHECK:
				strReturn = "复核";
				break;
			case CANCELCHECK:
				strReturn = "取消复核";
				break;
			case INITAPPROVAL:
				strReturn = "提交审批";
				break;
			case SAVEANDINITAPPROVAL:
				strReturn = "保存并提交审批";
				break;				
			case DOAPPRVOAL:
				strReturn = "审批";
				break;
			case CANCELAPPROVAL:
				strReturn = "取消审批";
				break;
			case CANCELAPPLY:
				strReturn = "取消申请";
				break;
			case CANCEL:
				strReturn = "撤销";
				break;	
			case PREDRAWINTEREST:
				strReturn = "计提";
				break;	
			case INTERESTSETTLEMENT:
				strReturn = "结算";
				break;	
			case CLEANPREDRAWINTEREST:
				strReturn = "冲销计提";
				break;	
			case ACCEPT:
				strReturn = "接收";
				break;	
			case RECIEVE:
				strReturn = "接收确认";
				break;	
			case REFLUSE:
				strReturn = "拒绝";
				break;	 
			case ABANDON:
				strReturn = "放弃";
				break;
			case BOOTSTRAP:
				strReturn = "开机";
				break;	
			case POWEROFF:
				strReturn = "关机";
				break;	
			case UPDATE:
				strReturn = "更新";
				break;	
			case SQUAREUP:
				strReturn = "勾账";
				break;	
			case CANCELSQUAREUP:
				strReturn = "取消勾账";
				break;	
			case SEND:
				strReturn = "发送";
				break;
			case ACTIVATE:
				strReturn = "激活";
				break;
			case RETURN:
				strReturn = "返回修正";
				break;
			}
			return strReturn;

		}

		public static final long[] getAllCode() {
			long[] lTemp = { CREATETEMPSAVE, MODIFYTEMPSAVE, CREATESAVE,MODIFYSAVE , DELETE ,CHECK , CANCELCHECK ,INITAPPROVAL ,SAVEANDINITAPPROVAL ,		
			DOAPPRVOAL, CANCELAPPROVAL ,CANCELAPPLY, CANCEL, PREDRAWINTEREST , INTERESTSETTLEMENT , CLEANPREDRAWINTEREST , ACCEPT, RECIEVE , REFLUSE ,ABANDON, BOOTSTRAP ,		
			POWEROFF ,UPDATE, SQUAREUP, CANCELSQUAREUP, SEND,ACTIVATE, RETURN};
			return lTemp;
		}
		
		/**
		 * 判断某种操作是否需要记录操作日志
		 * @param lCode
		 * @return
		 */
		public static final boolean isNeedLog(long lCode){
			boolean isNeed = true;
			long[] allCode = getAllCode();
			ArrayList tempList = new ArrayList();
			for(int i = 0; i < allCode.length; i++)
			{
				tempList.add(new Long(allCode[i]));
			}
			if(!tempList.contains(new Long(lCode)))
			{
				isNeed = false;
			}			
			return isNeed;
		}
	} 

	
	public static final class LoggerOfOperResult{
		public static final String FAIL = "失败";
		public static final String SUCCESS = "成功";
	}
}