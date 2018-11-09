/* Generated by Together */
package com.iss.itreasury.budget.util;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Log;

/**
 * @author weilu
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BUDGETConstant extends com.iss.itreasury.util.Constant
{
    /**
     * ������ҵ������
     * @author weilu
     */
	public static class ApprovalType
	{
	    public static final long CURRENT = 1;		//������λ����
	    public static final long TOTAL = 2;			//�¼���λ����
	}
	
	//����״̬
	public static class TransactionStatus
    {
        public static final long DELETED = 0; //��ɾ��

        public static final long TEMPSAVE = 1; //�ݴ�

        public static final long SAVE = 2; //���棨δ���ˣ�

        public static final long CHECK = 3; //�Ѹ���

        public static final long NOTSIGN = 4; //δǩ��

        public static final long SIGN = 5; //��ǩ��

        public static final long CONFIRM = 6; //��ȷ��

        public static final long CIRCLE = 7; //�ѹ���
    }
	
	
	//�Ƿ���Ч
	public static class BooleanValue
	{
	     public static final long ISFALSE = 0; //false or no

	     public static final long ISTRUE = 1; //true or yes
	}
	
	/**
	 * Ԥ��У�鷵��ֵ
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static class BudgetCheckCode
	{
	     public static final long PASS = 100; //û��Ԥ��

	     public static final long OVERSUPPLENESS = 200; //������Ԥ��
	     
	     public static final long OVERRIGIDITY = 300; //������Ԥ��	
	     
	     public static final long UNBUDGET = 400; //�˻�������Ԥ��
	     
	}
	
	/**
	 * Ԥ��
	 * @author xrli
	 *
	 * To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Generation - Code and Comments
	 */
	public static class BudgetFlag
	{
	    public static final long CONSTITUTE = 1;	//Ԥ�����
	    public static final long ADJUST = 2;		//Ԥ�����
	    public static final long TOTAL = 3;			//Ԥ�����
	    
	    public static String getCode(long lCode)
	    {
	        String strReturn = "";
	        switch ((int) lCode)
            {
                case (int) CONSTITUTE:
                    strReturn = "YS";
                    break;
                case (int) ADJUST:
                    strReturn = "TZ";
                    break;
                case (int) TOTAL:
                    strReturn = "HZ";
                    break;
            }
	        return strReturn;
	    }
	    
	}
	
	public static class BudgetType
	{
	    public static final long RIGIDITY= 1;	//����Ԥ��
	    public static final long SUPPLENESS = 2;//����Ԥ��
	    
	}
	
	public static class BudgetPeriod
	{
	    public static final long Y = 1;			//���Ԥ��
	    public static final long H = 2;			//����Ԥ��
	    public static final long Q = 3;			//����Ԥ��
	    public static final long M = 4;			//��Ԥ��
	    public static final long T = 5;			//ѮԤ��
	    public static final long W = 6;			//��Ԥ��
	    public static final long D = 7;			//��Ԥ��
	    public static final long X1 = 8;		//˫��Ԥ��
	    public static final long X2 = 9;		//�ڲ��ض�Ԥ��
	    
	    public static final String getName(long lCode)
        {
            String strReturn = "";
            switch ((int) lCode)
            {
                case (int) Y:
                    strReturn = "��";
                    break;
                case (int) H:
                    strReturn = "����";
                    break;
                case (int) Q:
                    strReturn = "����";
                    break;
                case (int) M:
                    strReturn = "��";
                    break;
                case (int) T:
                    strReturn = "Ѯ";
                    break;
                case (int) W:
                    strReturn = "��";
                    break;
                case (int) D:
                    strReturn = "��";
                    break;
                case (int) X1:
                    strReturn = "˫��";
                    break;
                case (int) X2:
                    strReturn = "����";
                    break;
            }
            return strReturn;
        }
	    public static final String getCode(long lCode)
        {
            String strReturn = "";
            switch ((int) lCode)
            {
                case (int) Y:
                    strReturn = "Y";
                    break;
                case (int) H:
                    strReturn = "H";
                    break;
                case (int) Q:
                    strReturn = "Q";
                    break;
                case (int) M:
                    strReturn = "M";
                    break;
                case (int) T:
                    strReturn = "T";
                    break;
                case (int) W:
                    strReturn = "W";
                    break;
                case (int) D:
                    strReturn = "D";
                    break;
                case (int) X1:
                    strReturn = "X1";
                    break;
                case (int) X2:
                    strReturn = "X2";
                    break;
            }
            return strReturn;
        }

        public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                { Y,H,Q,M,T,W,D,X1,X2 };
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.budget.util.BUDGETConstant$BudgetPeriod", officeID,
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
	 * Ԥ�������λ
	 * @author weilu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static class BudgetUnit
	{
	    public static final long YUAN = 1;			//Ԫ
	    public static final long QIANYUAN = 2;			//ǧԪ
	    public static final long WANYUAN = 3;			//��Ԫ
	    
	    public static String getName(long lCode)
	    {
	        String strReturn = "";
            switch ((int) lCode)
            {
                case (int) YUAN:
                    strReturn = "Ԫ";
                    break;
                case (int) QIANYUAN:
                    strReturn = "ǧԪ";
                    break;
                case (int) WANYUAN:
                    strReturn = "��Ԫ";
                    break;
            }
            return strReturn;
	    }
	    public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                { YUAN,QIANYUAN,WANYUAN };
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.budget.util.BUDGETConstant$BudgetUnit", officeID,
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
	 * Ԥ���������
	 * @author weilu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static class BudgetControlType
	{
	    public static final long RIGID = 1;				//����Ԥ��
	    public static final long FLEXIBLE = 2;			//����Ԥ��
	    
	    public static String getName(long lCode)
	    {
	        String strReturn = "";
            switch ((int) lCode)
            {
                case (int) RIGID:
                    strReturn = "����Ԥ��";
                    break;
                case (int) FLEXIBLE:
                    strReturn = "����Ԥ��";
                    break;
            }
            return strReturn;
	    }
	    public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                { RIGID, FLEXIBLE};
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.budget.util.BUDGETConstant$BudgetControlType", officeID,
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
	 * Ԥ����Ʒ�ʽ
	 * @author weilu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static class BudgetControlMode
	{
	    public static final long TOTAL = 1;				//�ܶ����
	    public static final long DETAIL = 2;			//��ϸ����
	    
	    public static String getName(long lCode)
	    {
	        String strReturn = "";
            switch ((int) lCode)
            {
                case (int) TOTAL:
                    strReturn = "�ܶ����";
                    break;
                case (int) DETAIL:
                    strReturn = "��ϸ����";
                    break;
            }
            return strReturn;
	    }
	    public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                { TOTAL, DETAIL};
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.budget.util.BUDGETConstant$BudgetControlMode", officeID,
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
	 * Ԥ����Ŀ��ʽ�ļ��㷽ʽ
	 * @author weilu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static class BudgetCaculateType
	{
	    public static final long AUTO = 1;				//�Զ�����
	    public static final long MANUAL = 2;			//�ֹ�����
	    
	    public static String getName(long lCode)
	    {
	        String strReturn = "";
            switch ((int) lCode)
            {
                case (int) AUTO:
                    strReturn = "�Զ�����";
                    break;
                case (int) MANUAL:
                    strReturn = "�ֹ�����";
                    break;
            }
            return strReturn;
	    }
	    public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                { AUTO, MANUAL};
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.budget.util.BUDGETConstant$BudgetCaculateType", officeID,
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
	 * ִ�����������Դ
	 * @author weilu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static class DataSource
	{
	    public static final long BUDGETINPUT = 1;		//Ԥ��¼��
	    public static final long BUDGETIMPORT = 2;		//Ԥ�㵼��
	    public static final long EBANK = 3;				//����
	    public static final long SETTLEMENT = 4;		//����
	    
	    public static String getName(long lCode)
	    {
	        String strReturn = "";
            switch ((int) lCode)
            {
                case (int) BUDGETINPUT:
                    strReturn = "Ԥ��¼��";
                    break;
                case (int) BUDGETIMPORT:
                    strReturn = "Ԥ�㵼��";
                    break;
                case (int) EBANK:
                    strReturn = "����";
                    break;
                case (int) SETTLEMENT:
                    strReturn = "����";
                    break;
            }
            return strReturn;
	    }
	}
	
	/**
	 * ��Ŀģ��������
	 * @author liuyang
	 *
	 */
	public static class BudgetFormula
    {

        public static final String  ADD = "+"; //��
        public static final String  SUBTRACT = "-"; //��
        public static final String  MULTIPLY = "*"; //��
        public static final String  DIVIDE = "/"; //��    
        public static final String[] getAllCode()
        {
            String[] lTemp =
            { ADD, SUBTRACT, MULTIPLY, DIVIDE};
            return lTemp;
        }
        /**
         * ��ʾ�����б�
         * 
         * @param out
         * @param strControlName, *
         *            �ؼ�����
         * @param nType���ؼ����ͣ�0����ʾȫ������
         * @param lSelectValue
         * @param isNeedAll���Ƿ���Ҫ��ȫ���
         * @param isNeedBlank
         *            �Ƿ���Ҫ�հ���
         * @param strProperty
         */
        public static final void showList(JspWriter out, String strControlName, int nType, String lSelectValue, boolean isNeedAll, boolean isNeedBlank,
                String strProperty)
        {
            String[] lArrayID = null;
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
                    strArrayName[i] = lArrayID[i];
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
	 * ��Ŀģ�����֧����
	 * @author liuyang
	 *
	 */
	public static class BudgetInComeType
	{
	    public static final long income = 1;	//����
	    public static final long payout = 2;	//֧��
	    public static final long sub = 3;       //����
	    public static final long other = 4;     //����
	    
	    public static String getName(long lCode)
	    {
	        String strReturn = "";
            switch ((int) lCode)
            {
                case (int) income:
                    strReturn = "����";
                    break;
                case (int) payout:
                    strReturn = "֧��";
                    break;
                case (int) sub:
                    strReturn = "����";
                    break;
                case (int) other:
                    strReturn = "����";
                    break;
            }
            return strReturn;
	    }
	    public static final long[] getAllCode()
        {
            long[] lReturn;
                long[] lTemp =
                { income, payout,sub,other};
                lReturn = lTemp;

            return lReturn;
        }
        public static final long[] getAllCode(long officeID, long currencyID) {
			return Constant.getAllCode(
					"com.iss.itreasury.budget.util.BUDGETConstant$BudgetInComeType", officeID,
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
	 * ��ʾ
	 * @author weilu
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static class BudgetColumnList
	{
	    public static final long LASTBUDGET = 1;		//����Ԥ����
	    public static final long LASTEXECUTE = 2;		//����ʵ����
	    public static final long LASTYEARBUDGET = 3;	//����ͬ�ڵ�Ԥ����
	    public static final long LASTYEAREXECUTE = 4;	//����ͬ�ڵ�ʵ����
	    public static final long CURRENTBUDGET = 5;		//��ǰԤ����
	    public static final long CURRENTEXECUTE = 6;	//��ǰִ����
	    public static final long ORIGINALAMOUNT = 7;	//ԭʼԤ����
	    public static final long EXCUTESCALE = 8;		//ִ�б���
	    public static final long ADJUSTSCALE = 9;		//��������
	    public static final long CURRENTAMOUNT = 10;	//���ν��
	    public static final long UNCHECKBUDGET = 11;	//δ��˵�Ԥ����
	    
	    //Ԥ����ʷ�Աȷ���
	    public static final long ABUDGETAMOUNT=12;//A Ԥ����
	    public static final long AEXCUTEAMOUNT=13;//A ִ�н��
	    public static final long BBUDGETAMOUNT=14;//B Ԥ����
	    public static final long BEXCUTEAMOUNT=15;//B ִ�н��	    
	    public static final long BUDGETSUB = 16;	//Ԥ����
	    public static final long BUDGETRATE = 17;	//Ԥ��������	    
	    public static final long EXCUTESUB = 18;	//ִ�в��
	    public static final long EXCUTERATE = 19;	//ִ��������
	    
	    //Ԥ��ִ���������
	    public static final long ADJUSTBALANCE = 21;	//�������
	    public static final long TOTALAMOUNT = 22;	//��Ԥ����(�����ӣ����ͬCURRENTBUDGET)
	    public static final long SUB = 23;	//Ԥ������ִ�н��Ĳ�	��
	    public static final long BUDGETSTRUCTURE = 24;//Ԥ��ṹ
	    public static final long EXECUTESTRUCTURE = 25;//ִ�нṹ
	    	    
	    
	    public static String getName(long lCode)
	    {
	        String strReturn = "";
            switch ((int) lCode)
            {
                case (int) LASTBUDGET:
                    strReturn = "����Ԥ����";
                    break;
                case (int) LASTEXECUTE:
                    strReturn = "����ʵ�ʽ��";
                    break;
                case (int) LASTYEARBUDGET:
                    strReturn = "����ͬ�ڵ�Ԥ����";
                    break;
                case (int) LASTYEAREXECUTE:
                    strReturn = "����ͬ�ڵ�ʵ�ʽ��";
                    break;
                case (int) CURRENTBUDGET:
                    strReturn = "��ǰԤ����";
                    break;
                case (int) CURRENTEXECUTE:
                    strReturn = "ִ�н��";
                    break;
                case (int) ORIGINALAMOUNT:
                    strReturn = "ԭʼԤ����";
                    break;
                case (int) EXCUTESCALE:
                    strReturn = "ִ�б���(%)";
                    break;
                case (int) ADJUSTSCALE:
                    strReturn = "��������(%)";
                    break;
                case (int) CURRENTAMOUNT:
                    strReturn = "���ν��";
                    break;
                case (int) UNCHECKBUDGET:
                    strReturn = "Ԥ����";
                    break;
                    
                case (int) ABUDGETAMOUNT:
                    strReturn = "A Ԥ����";
                    break;
                case (int) AEXCUTEAMOUNT:
                    strReturn = "A ִ�н��";
                    break;
                case (int) BBUDGETAMOUNT:
                    strReturn = "B Ԥ����";
                    break;
                case (int) BEXCUTEAMOUNT:
                    strReturn = "B ִ�н��";
                    break;
                case (int) BUDGETSUB:
                    strReturn = "Ԥ����";
                    break;
                case (int) BUDGETRATE:
                    strReturn = "Ԥ��������(%)";
                    break;
                case (int) EXCUTESUB:
                    strReturn = "ִ�в��";
                    break;
                case (int) EXCUTERATE:
                    strReturn = "ִ��������(%)";
                    break;
                case (int) ADJUSTBALANCE:
                    strReturn = "�������";
                    break;             
                case (int) SUB:
                    strReturn = "���";
                    break;
                case (int) TOTALAMOUNT:
                    strReturn = "��Ԥ����";
                    break;
                case (int) BUDGETSTRUCTURE:
                    strReturn = "Ԥ��ṹ";
                    break;
                case (int) EXECUTESTRUCTURE:
                    strReturn = "ִ�нṹ";
                    break;
            }
            return strReturn;
	    }
	}
	
	public static class ConstituteStatus{
	    public static final long SAVE = 1;		//�ѱ���
	    public static final long COMMIT = 2;	//���ύ
	    public static final long CHECKING = 3;	//�����
	    public static final long CURRENTCHECK = 4;	//����λ������
	    public static final long UPPERCHECK = 5;	//�ϼ���λ������
	    public static final long LASTCHECK = 6;		//����������
	    public static final long REFUSE = 7;	//�ܾ�
	    public static final long RETURN = 8;	//�����޸�
	    public static final long DELETE = 0;	//��ɾ��
	    
	    public static String getName(long lCode)
	    {
	        String strReturn = "";
            switch ((int) lCode)
            {
                case (int) SAVE:
                    strReturn = "�ѱ���";
                    break;
                case (int) COMMIT:
                    strReturn = "���ύ";
                    break;
                case (int) CHECKING:
                    strReturn = "�����";
                    break;
                case (int) CURRENTCHECK:
                    strReturn = "����λ������";
                    break;  
                case (int) UPPERCHECK:
                    strReturn = "�ϼ���λ������";
                    break;  
                case (int) LASTCHECK:
                    strReturn = "����������";
                    break;
                case (int) REFUSE:
                    strReturn = "�Ѿܾ�";
                    break;
                case (int) RETURN:
                    strReturn = "�����޸�";
                    break;
                case (int) DELETE:
                    strReturn = "��ɾ��";
                    break;
                default :
                    strReturn = "δ����";
            }
            return strReturn;
	    }
	}
}
