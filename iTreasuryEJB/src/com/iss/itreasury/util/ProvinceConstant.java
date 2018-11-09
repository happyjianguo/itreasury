package com.iss.itreasury.util;

/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ProvinceConstant
{
    public final static long PROVINCE1 = 1;
    public final static long PROVINCE2 = 2;
    public final static long PROVINCE3 = 3;
    public final static long PROVINCE4 = 4;
    public final static long PROVINCE5 = 5;
    public final static long PROVINCE6 = 6;
    public final static long PROVINCE7 = 7;
    public final static long PROVINCE8 = 8;
    public final static long PROVINCE9 = 9;
    public final static long PROVINCE10 = 10;
    public final static long PROVINCE11 = 11;
    public final static long PROVINCE12 = 12;
    public final static long PROVINCE13 = 13;
    public final static long PROVINCE14 = 14;
    public final static long PROVINCE15 = 15;
    public final static long PROVINCE16 = 16;
    public final static long PROVINCE17 = 17;
    public final static long PROVINCE18 = 18;
    public final static long PROVINCE19 = 19;
    public final static long PROVINCE20 = 20;
    public final static long PROVINCE21 = 21;
    public final static long PROVINCE22 = 22;
    public final static long PROVINCE23 = 23;
    public final static long PROVINCE24 = 24;
    public final static long PROVINCE25 = 25;
    public final static long PROVINCE26 = 26;
    public final static long PROVINCE27 = 27;
    public final static long PROVINCE28 = 28;
    public final static long PROVINCE29 = 29;
    public final static long PROVINCE30 = 30;
    public final static long PROVINCE31 = 31;
    
    public static final String getName(long lCode)
    {
        String strReturn = "";
        switch ((int) lCode)
        {
            case (int) PROVINCE1 :
                strReturn = "������";
                break;
            case (int) PROVINCE2 :
                strReturn = "�Ϻ���";
                break;
            case (int) PROVINCE3 :
                strReturn = "�����";
                break;
            case (int) PROVINCE4 :
                strReturn = "������";
                break;
            case (int) PROVINCE5 :
                strReturn = "�㶫ʡ";
                break;
            case (int) PROVINCE6 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE7 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE8 :
                strReturn = "�㽭ʡ";
                break;
            case (int) PROVINCE9 :
                strReturn = "ɽ��ʡ";
                break;
            case (int) PROVINCE10 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE11 :
                strReturn = "�Ĵ�ʡ";
                break;
            case (int) PROVINCE12 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE13 :
                strReturn = "�ӱ�ʡ";
                break;
            case (int) PROVINCE14 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE15 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE16 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE17 :
                strReturn = "������ʡ";
                break;
            case (int) PROVINCE18 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE19 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE20 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE21 :
                strReturn = "ɽ��ʡ";
                break;
            case (int) PROVINCE22 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE23 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE24 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE25 :
                strReturn = "����ʡ";
                break;
            case (int) PROVINCE26 :
                strReturn = "�ຣʡ";
                break;
            case (int) PROVINCE27 :
                strReturn = "����׳��������";
                break;
            case (int) PROVINCE28 :
                strReturn = "���ɹ�������";
                break;
            case (int) PROVINCE29 :
                strReturn = "�½�ά�����������";
                break;
            case (int) PROVINCE30 :
                strReturn = "���ز���������";
                break;
            case (int) PROVINCE31 :
                strReturn = "���Ļ���������";
                break;
        }
        return strReturn;
    }
    
    public static final long[] getAllCode()
    {
        long[] lTemp = {PROVINCE1,PROVINCE2,PROVINCE3,PROVINCE4,PROVINCE5,PROVINCE6,PROVINCE7,PROVINCE8,PROVINCE9,
        		PROVINCE10,PROVINCE11,PROVINCE12,PROVINCE13,PROVINCE14,PROVINCE15,PROVINCE16,PROVINCE17,PROVINCE18,PROVINCE19,
				PROVINCE20,PROVINCE21,PROVINCE22,PROVINCE23,PROVINCE24,PROVINCE25,PROVINCE26,PROVINCE27,PROVINCE28,PROVINCE29,
				PROVINCE30,PROVINCE31};
        return lTemp;
    }
    
    /**
     * ����ʡ�����Ƶõ���Ӧ�ر���ֵ���������ȷ���Ż���Чֵ-1
     * @param provinceName
     * @return long
     */
    public static final long getProvinceID(String provinceName)
    {
    	long lreturn = -1;
    	
    	if (provinceName == null || "".equals(provinceName))
    	{
    		return lreturn;
    	}
    	
    	long[] provinces = getAllCode();
    	int length = provinceName.length();
    	for (int i = 0; i < provinces.length; i++)
    	{
    		String provinceTemp = getName(provinces[i]);
    		if (provinceTemp.length() < length)
    		{
    			continue;
    		}
    		if (provinceTemp.substring(0, length).equals(provinceName))
    		{
    			lreturn = provinces[i];
    			break;
    		}
    	}
    	
    	return lreturn;
    }
    
    public static void main(String[] args)
	{
    	long l = getProvinceID("�½�ά�����������");
    	System.out.println(getName(l));
    }
}
