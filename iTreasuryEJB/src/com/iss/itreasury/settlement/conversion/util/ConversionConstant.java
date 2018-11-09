/*
 * Created on 2004-11-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.settlement.conversion.util;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConversionConstant {

	/**
	 * 
	 */
	public ConversionConstant() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @author Administrator
	 *
	 * TODO To change the template for this generated type comment go to
	 * Window - Preferences - Java - Code Style - Code Templates
	 */
	public static class Currency{
		public static final long CNY = 1; // '人民币元'
		public static final long USD = 2; // '美元'
		public static final long EUR = 3; // '欧元'
		public static final long ASF = 4; // '记账瑞士法郎'
		public static final long ATS = 5; // '奥地利先令'
		public static final long AUD = 6; // '澳大利亚元'
		public static final long BEF = 7; // '比利时法郎'
		public static final long BRL = 8; // '巴西里亚尔'
		public static final long CAD = 9; // '加拿大元'
		public static final long CHF = 10; // '瑞士法郎'
		public static final long DEM = 11; // '德国马克'
		public static final long DKK = 12; // '丹麦克朗'
		public static final long ECU = 13; // '欧洲货币单位'
		public static final long ESP = 14; // '西班牙比赛塔'
		public static final long FIM = 15; // '芬兰马克'
		public static final long FRF = 16; // '法国法郎'
		public static final long GBP = 17; // '英国英镑'
		public static final long HKD = 18; // '港元'
		public static final long IDR = 19; // '印尼盾'
		public static final long INR = 20; // '印度卢比'
		public static final long IQD = 21; // '伊拉克第纳尔'
		public static final long IRR = 22; // '伊朗里亚尔'
		public static final long ITL = 23; // '意大利里拉'
		public static final long JOD = 24; // '约旦第纳尔'
		public static final long JPY = 25; // '日本元'
		public static final long KRW = 26; // '韩国圆'
		public static final long KWD = 27; // '科威特第纳尔'
		public static final long MOP = 28; // '澳门元'
		public static final long MXP = 29; // '墨西哥比索'
		public static final long MYR = 30; // '马来西亚林吉特'
		public static final long NLG = 31; // '荷兰盾'
		public static final long NOK = 32; // '挪威克朗'
		public static final long NPR = 33; // '尼泊尔卢比'
		public static final long NZD = 34; // '新西兰元'
		public static final long PHP = 35; // '菲律宾比索'
		public static final long PKR = 36; // '巴基斯坦卢比'
		public static final long RUB = 37; // '俄国卢布'
		public static final long SEK = 38; // '瑞典克朗'
		public static final long SGD = 39; // '新加坡元'
		public static final long SLL = 40; // '赛拉利昂利昂'
		public static final long THB = 41; // '泰国铢'
		public static final long TWD = 42; // '台湾元'
		public static final long TZS = 43; // '坦桑尼亚先令'
		public static final long XDR = 44; // '特别提款权'
		
		public static final String getNameByID(long id){
			String rnt = "";
			if(id==1){ rnt="CNY";}
			else if(id==2){ rnt="USD";}
			else if(id==3){ rnt="EUR";}
			else if(id==4){ rnt="ASF";}
			else if(id==5){ rnt="ATS";}
			else if(id==6){ rnt="AUD";}
			else if(id==7){ rnt="BEF";}
			else if(id==8){ rnt="BRL";}
			else if(id==9){ rnt="CAD";}
			else if(id==10){ rnt="CHF";}
			else if(id==11){ rnt="DEM";}
			else if(id==12){ rnt="DKK";}
			else if(id==13){ rnt="ECU";}
			else if(id==14){ rnt="ESP";}
			else if(id==15){ rnt="FIM";}
			else if(id==16){ rnt="FRF";}
			else if(id==17){ rnt="GBP";}
			else if(id==18){ rnt="HKD";}
			else if(id==19){ rnt="IDR";}
			else if(id==20){ rnt="INR";}
			else if(id==21){ rnt="IQD";}
			else if(id==22){ rnt="IRR";}
			else if(id==23){ rnt="ITL";}
			else if(id==24){ rnt="JOD";}
			else if(id==25){ rnt="JPY";}
			else if(id==26){ rnt="KRW";}
			else if(id==27){ rnt="KWD";}
			else if(id==28){ rnt="MOP";}
			else if(id==29){ rnt="MXP";}
			else if(id==30){ rnt="MYR";}
			else if(id==31){ rnt="NLG";}
			else if(id==32){ rnt="NOK";}
			else if(id==33){ rnt="NPR";}
			else if(id==34){ rnt="NZD";}
			else if(id==35){ rnt="PHP";}
			else if(id==36){ rnt="PKR";}
			else if(id==37){ rnt="RUB";}
			else if(id==38){ rnt="SEK";}
			else if(id==39){ rnt="SGD";}
			else if(id==40){ rnt="SLL";}
			else if(id==41){ rnt="THB";}
			else if(id==42){ rnt="TWD";}
			else if(id==43){ rnt="TZS";}
			else if(id==44){ rnt="XDR";}
			return rnt;
		}
		
		public static final long getIDByName(String name){
			long rnt = 0;
			if(name.equals("CNY")){ rnt=1;}
			else if(name.equals("USD")){ rnt=2;}
			else if(name.equals("EUR")){ rnt=3;}
			else if(name.equals("ASF")){ rnt=4;}
			else if(name.equals("ATS")){ rnt=5;}
			else if(name.equals("AUD")){ rnt=6;}
			else if(name.equals("BEF")){ rnt=7;}
			else if(name.equals("BRL")){ rnt=8;}
			else if(name.equals("CAD")){ rnt=9;}
			else if(name.equals("CHF")){ rnt=10;}
			else if(name.equals("DEM")){ rnt=11;}
			else if(name.equals("DKK")){ rnt=12;}
			else if(name.equals("ECU")){ rnt=13;}
			else if(name.equals("ESP")){ rnt=14;}
			else if(name.equals("FIM")){ rnt=15;}
			else if(name.equals("FRF")){ rnt=16;}
			else if(name.equals("GBP")){ rnt=17;}
			else if(name.equals("HKD")){ rnt=18;}
			else if(name.equals("IDR")){ rnt=19;}
			else if(name.equals("INR")){ rnt=20;}
			else if(name.equals("IQD")){ rnt=21;}
			else if(name.equals("IRR")){ rnt=22;}
			else if(name.equals("ITL")){ rnt=23;}
			else if(name.equals("JOD")){ rnt=24;}
			else if(name.equals("JPY")){ rnt=25;}
			else if(name.equals("KRW")){ rnt=26;}
			else if(name.equals("KWD")){ rnt=27;}
			else if(name.equals("MOP")){ rnt=28;}
			else if(name.equals("MXP")){ rnt=29;}
			else if(name.equals("MYR")){ rnt=30;}
			else if(name.equals("NLG")){ rnt=31;}
			else if(name.equals("NOK")){ rnt=32;}
			else if(name.equals("NPR")){ rnt=33;}
			else if(name.equals("NZD")){ rnt=34;}
			else if(name.equals("PHP")){ rnt=35;}
			else if(name.equals("PKR")){ rnt=36;}
			else if(name.equals("RUB")){ rnt=37;}
			else if(name.equals("SEK")){ rnt=38;}
			else if(name.equals("SGD")){ rnt=39;}
			else if(name.equals("SLL")){ rnt=40;}
			else if(name.equals("THB")){ rnt=41;}
			else if(name.equals("TWD")){ rnt=42;}
			else if(name.equals("TZS")){ rnt=43;}
			else if(name.equals("XDR")){ rnt=44;}

			return rnt;
		}
		

	}

	public static void main(String[] args) {
	}
}
