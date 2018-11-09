package com.iss.itreasury.fcinterface.bankportal.util;

public final class ChineseCurrency
{
	private static String[] CN = { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };
	private static String[] LP = { "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��", "ʰ", "Ԫ" };
	private static String[] RP = { "��", "��" };

	/**
	 * ��ʾ���Ĵ�д�Ľ����ֵ
	 * ���뱣֤����ַ������ɡ��������
	 */
	public static String showChinese(String sAmount)
	{
		return showChinese(sAmount, 1);
	}
	/**
	 * ��ʾ���Ĵ�д�Ľ����ֵ
	 * @param sAmount ����ַ���
	 * @param lCurrencyID ����
	 * @return
	 */
	public static String showChinese(String sAmount, long lCurrencyID)
	{
		String sResult = "";
		boolean bIsNegative = false;
		if(sAmount.indexOf("-") == 0)
		{
			sAmount = sAmount.substring(1,sAmount.length());
			bIsNegative = true;
		}
		if (sAmount == null || sAmount == "")
		{
			sResult = "��Ԫ";
		}
		else
		{
			int nPoint = sAmount.indexOf(".");
			String sLeft = "";
			String sRight = "";
			if (nPoint != -1)
			{
				sLeft = sAmount.substring(0, nPoint);
				sRight = sAmount.substring(nPoint + 1, sAmount.length());
			}
			else
			{
				sLeft = sAmount;
			}
			sResult = getLeftOfPoint(sLeft);

			if (isLeftHaveSingle(sLeft))
			{
				try
				{
					sResult = sResult + "��";
				}
				catch (Exception ex)
				{
				}
			}

			switch ((int) lCurrencyID)
			{
				case 1 :
				default :
					sResult = sResult + "Ԫ";
					break;
				case 2 :
					sResult = "��Ԫ" + sResult + "Ԫ";
					break;
				case 3 :
					sResult = "Ӣ��" + sResult + "��";
					break;
				case 4 :
					sResult = "ŷԪ" + sResult + "Ԫ";
					break;
				case 5 :
					sResult = "��Ԫ" + sResult + "Ԫ";
					break;
				case 6 :
					sResult = "�¼���Ԫ" + sResult + "Ԫ";
					break;
			}

			sResult = sResult + getRightOfPoint(sRight, lCurrencyID);
			if (isFullRight(sRight, lCurrencyID) == true)
			{
				sResult = sResult + "��";
			}
		}
		if(bIsNegative)
		{
			return "��"+sResult;
		}
		else
		{
			return sResult;
		}
	}

	/**
	 * ������ֱ�־ �����������ĵ�λ
	 * @param nData
	 */
	public static String showChinese(long lCurrencyID)
	{
		String sResult = "";

		switch ((int) lCurrencyID)
		{
			case (int)1:
			default :
				sResult = "Ԫ";
				break;
			/*case (int)Constant.CurrencyType.USD :
				sResult = "��Ԫ";
				break;
			case (int)Constant.CurrencyType.UKP :
				sResult = "Ӣ��";
				break;
			case (int)Constant.CurrencyType.ED :
				sResult = "ŷԪ";
				break;
			case (int)Constant.CurrencyType.JP :
				sResult = "��Ԫ";
				break;
			case (int)Constant.CurrencyType.SP :
				sResult = "�¼���Ԫ";
				break;*/
		}
		return sResult;
	}

	/**
	 * ��ֵ��λ�Ƿ���ֵ
	 * @param nData
	 */
	public static boolean isLeftHaveSingle(String strData)
	{
		if (Integer.parseInt(strData.substring(strData.length() - 1, strData.length())) > 0)
		{
			return false;
		}
		else
			if (strData.length() > 1)
			{
				return false;
			}
			else
			{
				return true;
			}
	}

	/**
	 * �õ�������ֵ
	 */
	public static String getLeftOfPoint(String sLeft)
	{
		long lYiNum = 0; //��λ��
		long lWalNum = 0; //��λ��
		long lRemailNum = 0; //ʣ�������
		long lThousandBelowNum = 0; //ǧ���µ�����
		String strReturn = "";

		lRemailNum = Long.valueOf(sLeft).longValue();
		lYiNum = lRemailNum / 100000000;
		lRemailNum = lRemailNum - lYiNum * 100000000;
		lWalNum = lRemailNum / 10000;
		lRemailNum = lRemailNum - lWalNum * 10000;

		//�����λ��>0������
		if (lYiNum > 0)
		{
			//�������1�򣬵ݹ�
			if (lYiNum >= 10000)
			{
				strReturn = strReturn + getLeftOfPoint(String.valueOf(lYiNum));
			}
			else
			{
				strReturn = strReturn + getThousandChineseData(lYiNum);
			}
			strReturn = strReturn + "��";
			//˵���п��ܲ�0
			if (lWalNum > 0 || lRemailNum > 0)
			{
				//˵����λΪ0
				if ((double)(lYiNum) / (double)10 == lYiNum / 10)
				{
					strReturn = strReturn + "��";
				}
				else
				{
					if (lWalNum < 1000)
					{
						strReturn = strReturn + "��";
					}
				}
			}
		}
		if (lWalNum > 0)
		{
			strReturn = strReturn + getThousandChineseData(lWalNum);
			strReturn = strReturn + "��";
			if (lRemailNum > 0)
			{
				if ((double)(lWalNum) / (double)10 == lWalNum / 10)
				{ //˵����λΪ0
					strReturn = strReturn + "��";
					System.out.println("1----");
				}
				else
				{
					if (lRemailNum < 1000)
					{
						strReturn = strReturn + "��";
						System.out.println("2----");
					}
				}
			}
		}
		if (lRemailNum > 0)
		{
			strReturn = strReturn + getThousandChineseData(lRemailNum);
		}
		return strReturn;
	}
	/**
	 * �õ�ǧ�������ݵ�����
	 */
	public static String getThousandChineseData(long lData)
	{
		String strReturn = "";
		if (lData > 9999)
		{
			return "�����ǧ";
		}
		else
		{
			long lRemailNum = lData; //ʣ�����
			long lThousandNum = 0; //ǧλ��
			long lHundredNum = 0; //��λ��
			long lTelNum = 0; //ʮλ��
			long lNum = 0; //��λ��

			lThousandNum = lRemailNum / 1000; //ǧλ��
			lRemailNum = lRemailNum - lThousandNum * 1000;
			lHundredNum = lRemailNum / 100;
			lRemailNum = lRemailNum - lHundredNum * 100;
			lTelNum = lRemailNum / 10;
			lNum = lRemailNum - lTelNum * 10;
			if (lThousandNum > 0)
			{
				strReturn = strReturn + CN[(int) lThousandNum] + "Ǫ";
			}
			if (lHundredNum > 0)
			{
				strReturn = strReturn + CN[(int) lHundredNum] + "��";
			}
			if (lTelNum > 0)
			{
				if (lThousandNum > 0 && lHundredNum == 0)
				{
					strReturn = strReturn + "��";
				}
				strReturn = strReturn + CN[(int) lTelNum] + "ʰ";
			}
			if (lNum > 0)
			{
				if (lTelNum == 0)
				{
					if (lThousandNum > 0 || lHundredNum > 0)
					{
						strReturn = strReturn + "��";
					}
				}
				strReturn = strReturn + CN[(int) lNum];
			}
		}
		return strReturn;
	}

	/**
	 * ���ݽ���ұߵ�ֵ�жϣ��Ƿ����
	 * @param sRight �ұߵ�ֵ
	 * @param lCurrencyID ����
	 */
	public static boolean isFullRight(String sRight, long lCurrencyID)
	{
		if (sRight.equals(""))
		{
			sRight = "00";
		}
		else
		{
			if (sRight.length() == 1)
			{
				sRight = sRight + "0";
			}
		}
		boolean bIsFull = false;
		if (sRight.equals(""))
		{
			bIsFull = true;
		}
		else
		{
			sRight = sRight.substring(0, 2);
			if (Integer.parseInt(sRight) == 0)
			{
				bIsFull = true;
			}
			else
			{
				switch ((int) lCurrencyID)
				{
					case 1 : //�����
						if (Integer.parseInt(sRight) >= 10)
						{

							if ((sRight.substring(1, 2)).equals("0") || (sRight.substring(1, 2)).equals(""))
							{
								bIsFull = true;
							}
						}
						break;
					case 2 : //��Ԫ
					case 3 : //Ӣ��
					case 4 : //ŷԪ
					case 6 : //�¼���Ԫ
						if (Integer.parseInt(sRight) == 0)
						{
							bIsFull = true;
						}
						break;
					case 5 : //��Ԫ
						bIsFull = true;
						break;

				}
			}
		}
		return bIsFull;

	}

	public static String getRightOfPoint(String sRight, long lCurrencyID)
	{
		if (sRight.equals(""))
		{
			sRight = "00";
		}
		else
		{
			if (sRight.length() == 1)
			{
				sRight = sRight + "0";
			}
		}
		sRight = sRight.substring(0, 2);
		long lRight = Long.valueOf(sRight).longValue();
		String sb = "";
		if (lRight > 0)
		{
			if (lRight < 10)
			{
				sb = sb + "��";
			}
			switch ((int) lCurrencyID)
			{
				case 1 :
				default :
					for (int i = 0; i < sRight.length(); i++)
					{
						long lNum = Long.valueOf(sRight.substring(i, i + 1)).longValue();
						if (lNum != 0)
						{
							sb = sb + CN[(int) lNum];
							sb = sb + RP[i];
						}
					}
					break;
				case 2 :

					sb = sb + getThousandChineseData(lRight) + "����";
					break;
				case 3 :
					sb = sb + getThousandChineseData(lRight) + "��ʿ";
					break;
				case 4 :
					sb = sb + getThousandChineseData(lRight) + "ŷ��";
					break;
			}
		}
		return sb;
	}

	public static String format ( double dValue , int lScale )
	{
	    //��������װ��Ϊ�����������������
		boolean bFlag = false ;
		if (dValue < 0)
		{
			bFlag = true ;
			dValue = -dValue ;
		}
		long lPrecision = 10000 ; //����ֵ��Ϊ�˱���double�ͳ���ƫ�����У��λ
		long l45Value = lPrecision / 2 - 1 ; //����������ж�ֵ
		long lLength = 1 ; //�˵�����
		for (int i = 0; i < lScale; i++)
		{
			lLength = lLength * 10 ; //���籣��2λ������100
		}
		long lValue = (long) dValue ; //ֵ����������
		long lValue1 = (long) ((dValue - lValue) * lLength) ; //���Ա���λ��
		long lValue2 = (long) ((dValue - lValue) * lLength * lPrecision) ; //
		long lLastValue = lValue2 - (lValue2 / lPrecision) * lPrecision ;
		if (lLastValue >= l45Value)
		{
			lValue1++ ;
		}
		dValue = lValue + (double) lValue1 / lLength ; //����������ֵ
		if (bFlag)
		{
			dValue = -dValue ;
		}
		java.math.BigDecimal bd = new java.math.BigDecimal ( dValue ) ;
		bd = bd.setScale ( lScale , java.math.BigDecimal.ROUND_HALF_UP ) ;
		return bd.toString ( ) ;	
	}
	
	public static void main(String[] args)
	{
       //System.out.println(showChinese("500.0"));
		System.out.println(showChinese(format(5000,2)));
	}

}