package com.iss.itreasury.fcinterface.bankportal.util;

public final class ChineseCurrency
{
	private static String[] CN = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	private static String[] LP = { "仟", "佰", "拾", "亿", "仟", "佰", "拾", "万", "仟", "佰", "拾", "元" };
	private static String[] RP = { "角", "分" };

	/**
	 * 显示中文大写的金额数值
	 * 必须保证金额字符串是由“数”组成
	 */
	public static String showChinese(String sAmount)
	{
		return showChinese(sAmount, 1);
	}
	/**
	 * 显示中文大写的金额数值
	 * @param sAmount 金额字符串
	 * @param lCurrencyID 币种
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
			sResult = "零元";
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
					sResult = sResult + "零";
				}
				catch (Exception ex)
				{
				}
			}

			switch ((int) lCurrencyID)
			{
				case 1 :
				default :
					sResult = sResult + "元";
					break;
				case 2 :
					sResult = "美元" + sResult + "元";
					break;
				case 3 :
					sResult = "英镑" + sResult + "镑";
					break;
				case 4 :
					sResult = "欧元" + sResult + "元";
					break;
				case 5 :
					sResult = "日元" + sResult + "元";
					break;
				case 6 :
					sResult = "新加坡元" + sResult + "元";
					break;
			}

			sResult = sResult + getRightOfPoint(sRight, lCurrencyID);
			if (isFullRight(sRight, lCurrencyID) == true)
			{
				sResult = sResult + "整";
			}
		}
		if(bIsNegative)
		{
			return "负"+sResult;
		}
		else
		{
			return sResult;
		}
	}

	/**
	 * 传入币种标志 传出币种中文单位
	 * @param nData
	 */
	public static String showChinese(long lCurrencyID)
	{
		String sResult = "";

		switch ((int) lCurrencyID)
		{
			case (int)1:
			default :
				sResult = "元";
				break;
			/*case (int)Constant.CurrencyType.USD :
				sResult = "美元";
				break;
			case (int)Constant.CurrencyType.UKP :
				sResult = "英镑";
				break;
			case (int)Constant.CurrencyType.ED :
				sResult = "欧元";
				break;
			case (int)Constant.CurrencyType.JP :
				sResult = "日元";
				break;
			case (int)Constant.CurrencyType.SP :
				sResult = "新加坡元";
				break;*/
		}
		return sResult;
	}

	/**
	 * 左值个位是否有值
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
	 * 得到金额的左值
	 */
	public static String getLeftOfPoint(String sLeft)
	{
		long lYiNum = 0; //亿位数
		long lWalNum = 0; //万位数
		long lRemailNum = 0; //剩余的数字
		long lThousandBelowNum = 0; //千以下的数据
		String strReturn = "";

		lRemailNum = Long.valueOf(sLeft).longValue();
		lYiNum = lRemailNum / 100000000;
		lRemailNum = lRemailNum - lYiNum * 100000000;
		lWalNum = lRemailNum / 10000;
		lRemailNum = lRemailNum - lWalNum * 10000;

		//如果亿位数>0，则处理
		if (lYiNum > 0)
		{
			//如果大于1万，递归
			if (lYiNum >= 10000)
			{
				strReturn = strReturn + getLeftOfPoint(String.valueOf(lYiNum));
			}
			else
			{
				strReturn = strReturn + getThousandChineseData(lYiNum);
			}
			strReturn = strReturn + "亿";
			//说明有可能补0
			if (lWalNum > 0 || lRemailNum > 0)
			{
				//说明亿位为0
				if ((double)(lYiNum) / (double)10 == lYiNum / 10)
				{
					strReturn = strReturn + "零";
				}
				else
				{
					if (lWalNum < 1000)
					{
						strReturn = strReturn + "零";
					}
				}
			}
		}
		if (lWalNum > 0)
		{
			strReturn = strReturn + getThousandChineseData(lWalNum);
			strReturn = strReturn + "万";
			if (lRemailNum > 0)
			{
				if ((double)(lWalNum) / (double)10 == lWalNum / 10)
				{ //说明万位为0
					strReturn = strReturn + "零";
					System.out.println("1----");
				}
				else
				{
					if (lRemailNum < 1000)
					{
						strReturn = strReturn + "零";
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
	 * 得到千以下数据的中文
	 */
	public static String getThousandChineseData(long lData)
	{
		String strReturn = "";
		if (lData > 9999)
		{
			return "错误的千";
		}
		else
		{
			long lRemailNum = lData; //剩余的数
			long lThousandNum = 0; //千位数
			long lHundredNum = 0; //百位数
			long lTelNum = 0; //十位数
			long lNum = 0; //个位数

			lThousandNum = lRemailNum / 1000; //千位数
			lRemailNum = lRemailNum - lThousandNum * 1000;
			lHundredNum = lRemailNum / 100;
			lRemailNum = lRemailNum - lHundredNum * 100;
			lTelNum = lRemailNum / 10;
			lNum = lRemailNum - lTelNum * 10;
			if (lThousandNum > 0)
			{
				strReturn = strReturn + CN[(int) lThousandNum] + "仟";
			}
			if (lHundredNum > 0)
			{
				strReturn = strReturn + CN[(int) lHundredNum] + "佰";
			}
			if (lTelNum > 0)
			{
				if (lThousandNum > 0 && lHundredNum == 0)
				{
					strReturn = strReturn + "零";
				}
				strReturn = strReturn + CN[(int) lTelNum] + "拾";
			}
			if (lNum > 0)
			{
				if (lTelNum == 0)
				{
					if (lThousandNum > 0 || lHundredNum > 0)
					{
						strReturn = strReturn + "零";
					}
				}
				strReturn = strReturn + CN[(int) lNum];
			}
		}
		return strReturn;
	}

	/**
	 * 根据金额右边的值判断，是否加整
	 * @param sRight 右边的值
	 * @param lCurrencyID 币种
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
					case 1 : //人民币
						if (Integer.parseInt(sRight) >= 10)
						{

							if ((sRight.substring(1, 2)).equals("0") || (sRight.substring(1, 2)).equals(""))
							{
								bIsFull = true;
							}
						}
						break;
					case 2 : //美元
					case 3 : //英镑
					case 4 : //欧元
					case 6 : //新加坡元
						if (Integer.parseInt(sRight) == 0)
						{
							bIsFull = true;
						}
						break;
					case 5 : //日元
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
				sb = sb + "零";
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

					sb = sb + getThousandChineseData(lRight) + "美分";
					break;
				case 3 :
					sb = sb + getThousandChineseData(lRight) + "便士";
					break;
				case 4 :
					sb = sb + getThousandChineseData(lRight) + "欧分";
					break;
			}
		}
		return sb;
	}

	public static String format ( double dValue , int lScale )
	{
	    //负数，则装化为正数后进行四舍五入
		boolean bFlag = false ;
		if (dValue < 0)
		{
			bFlag = true ;
			dValue = -dValue ;
		}
		long lPrecision = 10000 ; //精度值，为了避免double型出现偏差，增加校验位
		long l45Value = lPrecision / 2 - 1 ; //四舍五入的判断值
		long lLength = 1 ; //乘的数字
		for (int i = 0; i < lScale; i++)
		{
			lLength = lLength * 10 ; //比如保留2位，乘以100
		}
		long lValue = (long) dValue ; //值的整数部分
		long lValue1 = (long) ((dValue - lValue) * lLength) ; //乘以保留位数
		long lValue2 = (long) ((dValue - lValue) * lLength * lPrecision) ; //
		long lLastValue = lValue2 - (lValue2 / lPrecision) * lPrecision ;
		if (lLastValue >= l45Value)
		{
			lValue1++ ;
		}
		dValue = lValue + (double) lValue1 / lLength ; //四舍五入后的值
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