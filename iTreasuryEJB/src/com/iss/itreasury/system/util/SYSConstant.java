/*
 * Created on 2004-11-26
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.util;
import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * @author hyzeng
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SYSConstant {

	public static class privilegeType {
		public static final int privilegeType_1 = 1; //��ѯȨ��
		public static final int privilegeType_2 = 2; //����Ȩ��
	}
	
	public static class SysCheckStatus
	{
		public static final long DELETED = 0; //��ɾ��
	 	public static final long UNCHECK = 2;	//δ����
	    public static final long CHECK = 1; //�Ѹ���		  
		    
	    public static String getName(long lCode)
	    {
	        String strReturn = ""; 
	        switch ((int) lCode)
            {
                case (int) DELETED:
                strReturn = "��ɾ��";
                break;
                case (int) UNCHECK:
                    strReturn = "δ����";
                    break;
                case (int) CHECK:
                    strReturn = "�Ѹ���";
                    break;
                default:
                    strReturn = "�Ѹ���";
                    break;
            }
	        return strReturn;
	    }    
	   public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                { UNCHECK,CHECK,DELETED};
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.system.util.SYSConstant$SysCheckStatus", officeID,
					currencyID);
		}

	        /**
	         * ��ʾ�����б�
	         * 
	         * @param out
	         * @param strControlName,
	         *            �ؼ�����
	         * @param nType���ؼ����ͣ�0����ʾȫ����
	         * @param lSelectValue
	         * @param isNeedAll���Ƿ���Ҫ��ȫ���
	         * @param isNeedBlank
	         *            �Ƿ���Ҫ�հ���
	         * @param strProperty
	         */
	        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
	        {
	            long[] lArrayID = null;
	            String[] strArrayName = null;
	            try
	            {
	                switch (nType)
	                {
	                    case 0:
	                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
	                        break;
	                    case 1:
	                        lArrayID = getAllCode();
	                        break;
	                    case 2:
	                    	lArrayID = new long[]{CHECK,UNCHECK,};
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
     * ��ʾͨ�������б�
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
            out.println("<select class=\"select\" name=\"" + strControlName + "\" " + strProperty + ">");
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
            if (isNeedAll == true)
            {
                if (lSelectValue == 0)
                {
                    out.println("<option value='0' selected>ȫ��</option>");
                }
                else
                {
                    out.println("<option value='0'>ȫ��</option>");
                }
            }
            out.println("</select>");
        }
        catch (Exception ex)
        {
            Log.print("��ʾ�����б�����쳣��" + ex.toString());
        }
    }
    
    public static class CodingRuleStatus
	{
		public static final long DELETED = 0; //��ɾ��
	    public static final long SAVED = 1; //�ѱ���		  
		    
	    public static String getName(long lCode)
	    {
	        String strReturn = ""; 
	        switch ((int) lCode)
            {
                case (int) DELETED:
                strReturn = "��ɾ��";
                	break;
                case (int) SAVED:
                    strReturn = "�ѱ���";
                    break;
            }
	        return strReturn;
	    }    
	   public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                {SAVED};
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.system.util.SYSConstant$CodingRuleStatus", officeID,
					currencyID);
		}

	        /**
	         * ��ʾ�����б�
	         * 
	         * @param out
	         * @param strControlName,
	         *            �ؼ�����
	         * @param nType���ؼ����ͣ�0����ʾȫ����
	         * @param lSelectValue
	         * @param isNeedAll���Ƿ���Ҫ��ȫ���
	         * @param isNeedBlank
	         *            �Ƿ���Ҫ�հ���
	         * @param strProperty
	         */
	        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
	        {
	            long[] lArrayID = null;
	            String[] strArrayName = null;
	            try
	            {
	                switch (nType)
	                {
	                    case 0:
	                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
	                        break;
	                    case 1:
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
    

    public static class SerialNumberStatus
	{
		public static final long DELETED = 0; //��ɾ��
	    public static final long SAVED = 1; //�ѱ���		  
		    
	    public static String getName(long lCode)
	    {
	        String strReturn = ""; 
	        switch ((int) lCode)
            {
                case (int) DELETED:
                strReturn = "��ɾ��";
                	break;
                case (int) SAVED:
                    strReturn = "�ѱ���";
                    break;
            }
	        return strReturn;
	    }    
	   public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                {SAVED};
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.system.util.SYSConstant$SerialNumberStatus", officeID,
					currencyID);
		}

	        /**
	         * ��ʾ�����б�
	         * 
	         * @param out
	         * @param strControlName,
	         *            �ؼ�����
	         * @param nType���ؼ����ͣ�0����ʾȫ����
	         * @param lSelectValue
	         * @param isNeedAll���Ƿ���Ҫ��ȫ���
	         * @param isNeedBlank
	         *            �Ƿ���Ҫ�հ���
	         * @param strProperty
	         */
	        public static final void showList(JspWriter out, String strControlName, int nType, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lOfficeID,long lCurrencyID)
	        {
	            long[] lArrayID = null;
	            String[] strArrayName = null;
	            try
	            {
	                switch (nType)
	                {
	                    case 0:
	                        lArrayID = getAllCode(lOfficeID,lCurrencyID);
	                        break;
	                    case 1:
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
    
    public static class SysAuthority
    {
    	public static final long NOTAUTHORITY = 0;  //δ��Ȩ
    	public static final long ALREADYAUTHORITY = 1;  //����Ȩ
    	public static final long INAUTHORIZATION = 2;  //��Ȩ��
    	
    	public static String getName(long lCode)
    	{
    		String returnName = "";
    		switch((int) lCode)
    		{
    			case (int)NOTAUTHORITY:
    				returnName = "δ��Ȩ";
    				break;
    			case (int)ALREADYAUTHORITY:
    				returnName = "����Ȩ";
    				break;
    			case (int)INAUTHORIZATION:
    				returnName = "��Ȩ��";
    				break;
    			default:
    				break;
    		}
    		return returnName;
    	}
    	
    	public static final long[] getAllCode(long lType)
    	{
    		long[] lReturn = null;
    		if(lType==1)
    		{
    			lReturn = new long[]{NOTAUTHORITY,INAUTHORIZATION,ALREADYAUTHORITY};
    		}
    		else if(lType ==2)
    		{
    			lReturn = new long[]{INAUTHORIZATION,ALREADYAUTHORITY};
    		}
    		
    		
    		return lReturn;
    	}
    	
    	public static final void showList(JspWriter out, String strControlName, long lSelectValue, boolean isNeedAll, boolean isNeedBlank, String strProperty,long lType)
    	{
    		long[] lArrayID = null;
    		String[] strArrayName = null;
    		try
    		{
    			lArrayID = getAllCode(lType);
    			strArrayName = new String[lArrayID.length];
                for (int i = 0; i < lArrayID.length; i++)
                {
                    strArrayName[i] = getName(lArrayID[i]);
                }
                showCommonList(out, strControlName, lArrayID, strArrayName, lSelectValue, isNeedAll, strProperty, isNeedBlank);
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    	
    }
    
    public static class belongToClient
    {
    	public static final long ISBELONG = 1; //���ڸÿͻ���
    	public static final long NOTBELONG = 2; //�����ڸÿͻ���
    }
    
    public static class isAdmin
    {
    	public static final long isAdmin = 1;  //�ǹ���Ա
    	public static final long isNotAdmin = 2;  //���ǹ���Ա
    	
    	public static String getName(long l)
    	{
    		String returnName = "";
    		switch((int)l)
    		{
    			case 1:
    				returnName = "��";
    				break;
    			case 2:
    				returnName = "��";
    				break;
    			default:
    				break;
    				
    		}
    		return returnName;
    	}
    	
     public static void showAdminList(JspWriter out,String controlName,String properties,long isSelectValue,boolean isNeedAll,boolean isNeedBlank)
     {
    	 long[] lArrayID = null;
    	 String[] strArrayName = null;
    	 try
    	 {
    		 lArrayID = new long[]{isAdmin,isNotAdmin};
    		 strArrayName = new String[lArrayID.length];
    		 for(int i=0;i<lArrayID.length;i++)
    		 {
    			 strArrayName[i] = getName(lArrayID[i]);
    		 }
    		 out.println("<select class=\"select\" name=\""+controlName+"\" "+properties+">");
     		if(isNeedAll==true)
    		{
    			if(isSelectValue==0)
    			{
    				out.println("<option value='0' selected>ȫ��</option>");
    			}
    			else
    			{
    				out.println("<option value='0'>ȫ��</option>");
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
}