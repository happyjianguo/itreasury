/*
 * Created on 2006-3-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.dataconvert.util;

/**
 * @author yinghu
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TRF_Constant {

    //上传文件的路径
    public static final String UPLOADPATH="D:/upload/dataconvert/user_data/";
    //初始化数据库sql文件的url
    public static final String INITDATABASESQLFILEURL="d:/sql.txt";
    
    /**
     * 服务级别
     */
    public static class ServiceLevel {
        public static final long IMPORTANTGUEST = 1;

        public static final long HONOUREDGUEST = 2;

        public static final long COMMONGUEST = 3;

        public static final long RISKGUEST = 4;

        public static final String getName(long lCode) {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode) {
            case (int) IMPORTANTGUEST:
                strReturn = "重点客户";
                break;
            case (int) HONOUREDGUEST:
                strReturn = "贵宾客户";
                break;
            case (int) COMMONGUEST:
                strReturn = "普通客户";
                break;
            case (int) RISKGUEST:
                strReturn = "风险客户";
                break;
            }
            return strReturn;
        }

        public static final long getValue(String name) {
            if ("重点客户".equalsIgnoreCase(name)) {
                return 1;
            }
            if ("贵宾客户".equalsIgnoreCase(name)) {
                return 2;
            }
            if ("普通客户".equalsIgnoreCase(name)) {
                return 3;
            }
            if ("风险客户".equalsIgnoreCase(name)) {
                return 4;
            }
            return -1;
        }
    }
    
    /**
     * 表中记录的nstatus
     */
    public static class RecordStatus
    {
        public static final long VALID = 1; //有效

        public static final long INVALID = 0; //无效(删除)

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
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("有效".equalsIgnoreCase(name)){
                return 1;
            }
            if("无效".equalsIgnoreCase(name)){
                return 0;
            }
            return -1;
        }
    }
    
    /**
     * 账户状态
     */
    public static class AccountStatus
    {
        public static final long NORMAL = 1; //正常

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) NORMAL:
                    strReturn = "正常";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("正常".equalsIgnoreCase(name)){
                return 1;
            }
            return -1;
        }
    }
    
    /**
     * 子账户状态
     */
    public static class SubAccountStatus
    {
        public static final long NORMAL = 1; //未结清

        public static final long FINISH = 2; //已结清

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) NORMAL:
                    strReturn = "未结清";
                    break;
                case (int) FINISH:
                    strReturn = "已结清";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("未结清".equalsIgnoreCase(name)){
                return 1;
            }
            if("已结清".equalsIgnoreCase(name)){
                return 2;
            }
            return -1;
        }
    }    
        
    /**
     * 是,否
     */
    public static class YesOrNo
    {
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
        
        public static final long getValue(String name){
            if("是".equalsIgnoreCase(name)){
                return 1;
            }
            if("否".equalsIgnoreCase(name)){
                return 2;
            }
            return -1;
        }
    }
    
    /**
     * 账户复核状态 
     */
    public static class AccountCheckStatus
    {
        public static final long NEWSAVE = 1; //新增

        public static final long OLDSAVE = 2; //已修改

        public static final long BATCHSAVE = 3; //批量修改

        public static final long CHECK = 4; //复核

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) NEWSAVE:
                    strReturn = "未复核";
                    break;
                case (int) OLDSAVE:
                    strReturn = "已修改未复核";
                    break;
                case (int) BATCHSAVE:
                    strReturn = "批量修改未复核";
                    break;
                case (int) CHECK:
                    strReturn = "已复核";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("未复核".equalsIgnoreCase(name)){
                return 1;
            }
            if("已修改未复核".equalsIgnoreCase(name)){
                return 2;
            }
            if("批量修改未复核".equalsIgnoreCase(name)){
                return 3;
            }
            if("已复核".equalsIgnoreCase(name)){
                return 4;
            }
            return -1;
        }
    }  
    
    /**
     * 币种
     */
    public static class CurrencyType
    {
        //币种：来自于中油一期----//haier 最新币种
        public static final long RMB = 1; //人民币---------------/不统一/ CNY
        public static final long USD = 2; //美元
        
        public static final String getName(long lCode){
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) RMB:
                    strReturn = "人民币";
                    break;
                case (int) USD:
                    strReturn = "美元";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("人民币".equalsIgnoreCase(name)){
                return 1;
            }
            if("美元".equalsIgnoreCase(name)){
                return 2;
            }
            return -1;
        }
    } 
    
    /**
     * 透支类型
     */
    public static class AccountOverDraftType
    {
        public static final long ALL = 1; //允许透支(或其他)

        public static final long CONSIGN = 2; //允许委托收款透支(或委托收款)

        public static final long INTEREST = 3; //允许付息透支(或付息)

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) ALL:
                    strReturn = "允许透支";
                    break;
                case (int) CONSIGN:
                    strReturn = "允许委托收款透支";
                    break;
                case (int) INTEREST:
                    strReturn = "允许付息透支";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("允许透支".equalsIgnoreCase(name)){
                return 1;
            }
            if("允许委托收款透支".equalsIgnoreCase(name)){
                return 2;
            }
            if("允许付息透支".equalsIgnoreCase(name)){
                return 3;
            }
            return -1;
        }
        
    }   
    
    /**
     * 资金流向类型
     * @author yqwu
     */
    public static class CapitalType
    {
        public static final int IRRESPECTIVE = 0; //无关

        public static final int INTERNAL = 1; //内部转账

        public static final int BANK = 2; //银行
        
        public static final int GENERALLEDGER = 3; //总账类

        public static final String getName(long lCode)
        {
            String strReturn = "";
            switch ((int) lCode)
            {
                case IRRESPECTIVE:
                    strReturn = "无关";
                    break;
                case INTERNAL:
                    strReturn = "内部转账";
                    break;
                case BANK:
                    strReturn = "银行";
                    break;
                case GENERALLEDGER:
                    strReturn = "总账类";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("无关".equalsIgnoreCase(name)){
                return 0;
            }
            if("内部转账".equalsIgnoreCase(name)){
                return 1;
            }
            if("银行".equalsIgnoreCase(name)){
                return 2;
            }
            if("总账类".equalsIgnoreCase(name)){
                return 3;
            }
            return -1;
        }
    } 
    
    /**
     * 放还款
     */
    public static class PayOrReturn
    {
        public static final long PAY = 1; //放款

        public static final long RETURN = 2; //还款

        public static final String getName(long lCode)
        {
            String strReturn = ""; //初始化返回值
            switch ((int) lCode)
            {
                case (int) PAY:
                    strReturn = "放款";
                    break;
                case (int) RETURN:
                    strReturn = "还款";
                    break;
            }
            return strReturn;
        }
        
        public static final long getValue(String name){
            if("放款".equalsIgnoreCase(name)){
                return 1;
            }
            if("还款".equalsIgnoreCase(name)){
                return 2;
            }
            return -1;
        }
    }
    
    /**
     * 手续费率类型
     */
	public static class ChargeRatePayType
	{
		public static final long ONETIME = 1;
		public static final long YEAR = 2;
		public static final long QUARTER = 3;

		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ONETIME :
					strReturn = "一次性";
					break;
				case (int) YEAR :
					strReturn = "按年";
					break;
				case (int) QUARTER :
					strReturn = "按季";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("一次性".equalsIgnoreCase(name)){
		        return 1;
		    }
		    if("按年".equalsIgnoreCase(name)){
		        return 2;
		    }
		    if("按季".equalsIgnoreCase(name)){
		        return 3;
		    }
		    return -1;
		}
	}
	
	/**
	 * 风险评级
	 */
	public static class VentureLevel
	{
		//风险评级
		public static final long A = 1; //"正常";
		public static final long B = 2; //"关注";
		public static final long C = 3; //"次级";
		public static final long D = 4; //"可疑";
		public static final long E = 5; //"损失";
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) A :
					strReturn = "正常";
					break;
				case (int) B :
					strReturn = "关注";
					break;
				case (int) C :
					strReturn = "次级";
					break;
				case (int) D :
					strReturn = "可疑";
					break;
				case (int) E :
					strReturn = "损失";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("正常".equalsIgnoreCase(name)){
		        return 1;
		    }
		    if("关注".equalsIgnoreCase(name)){
		        return 2;
		    }
		    if("次级".equalsIgnoreCase(name)){
		        return 3;
		    }
		    if("可疑".equalsIgnoreCase(name)){
		        return 4;
		    }
		    if("损失".equalsIgnoreCase(name)){
		        return 5;
		    }
		    return -1;
		}
	}
	
	/**
	 * 担保类型1
	 */
	public static class AssureType1
	{
	    //担保类型1
	    public static final long FINANCING = 1; //融资担保
		public static final long NONFINANCING = 2; //非融资担保
		public static final long OTHER = 3; //其他		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) FINANCING :
					strReturn = "融资担保";
					break;
				case (int) NONFINANCING :
					strReturn = "非融资担保";
					break;
				case (int) OTHER :
					strReturn = "其他";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("融资担保".equalsIgnoreCase(name)){
		        return 1;
		    }
		    if("非融资担保".equalsIgnoreCase(name)){
		        return 2;
		    }
		    if("其他".equalsIgnoreCase(name)){
		        return 3;
		    }
		    return -1;
		}
	}	
	
	/**
	 * 担保类型2
	 */
	public static class AssureType2
	{
	    //担保类型2
	    public static final long LOAN = 1; //贷款担保
		public static final long HOMELAND = 2; //贸易项下的国内担保
		public static final long OVERSEAS = 3; //贸易项下的国外担保
		public static final long TENDER = 4; //招投标担保
		public static final long PERFORM = 5; //履约担保
		public static final long IMPAWN = 6; //质保
		public static final long PREPAYMENT = 7; //预付款担保
		public static final long OTHER = 8; //其他
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) LOAN :
					strReturn = "贷款担保";
					break;
				case (int) HOMELAND :
					strReturn = "贸易项下的国内担保";
					break;
				case (int) OVERSEAS :
					strReturn = "贸易项下的国外担保";
					break;
				case (int) TENDER :
					strReturn = "招投标担保";
					break;
				case (int) PERFORM :
					strReturn = "履约担保";
					break;
				case (int) IMPAWN :
					strReturn = "质保";
					break;
				case (int) PREPAYMENT :
					strReturn = "预付款担保";
					break;
				case (int) OTHER :
					strReturn = "其他";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("贷款担保".equalsIgnoreCase(name)){
		        return 1;
		    }
		    if("贸易项下的国内担保".equalsIgnoreCase(name)){
		        return 2;
		    }
		    if("贸易项下的国外担保".equalsIgnoreCase(name)){
		        return 3;
		    }
		    if("招投标担保".equalsIgnoreCase(name)){
		        return 4;
		    }
		    if("履约担保".equalsIgnoreCase(name)){
		        return 5;
		    }
		    if("质保".equalsIgnoreCase(name)){
		        return 6;
		    }
		    if("预付款担保".equalsIgnoreCase(name)){
		        return 7;
		    }
		    if("其他".equalsIgnoreCase(name)){
		        return 8;
		    }
		    return -1;
		}
	}
	
	/**
	 * 贷款类型
	 */
	public static class LoanType   
	{
 		//贷款类型
		public static final long ZY = 1; //自营贷款
		public static final long WT = 2; //委托贷款
		public static final long TX = 3; //贴现
		public static final long ZGXE = 4; //最高限额
		public static final long YT = 5; //银团贷款
		public static final long ZTX = 6; //转贴现
		public static final long MFXD = 7; //买方信贷
		public static final long DB = 8; //担保
		public static final long OTHER = 9; //其他
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) ZY :
					strReturn = "自营贷款";
					break;
				case (int) WT :
					strReturn = "委托贷款";
					break;
				case (int) TX :
					strReturn = "贴现";
					break;
				case (int) ZGXE :
					strReturn = "最高限额贷款";
					break;
				case (int) YT :
					strReturn = "银团贷款";
					break;
				case (int) ZTX :
					strReturn = "转贴现";
					break;
				case (int) MFXD :
					strReturn = "买方信贷";
					break;
				case (int) DB :
					strReturn = "担保";
					break;
				case (int) OTHER :
					strReturn = "其他";
					break;
			}
			return strReturn;
		}

		public static final long getValue(String name){
		    if("自营贷款".equalsIgnoreCase(name)){
		        return ZY;
		    }
		    if("委托贷款".equalsIgnoreCase(name)){
		        return WT;
		    }
		    if("贴现".equalsIgnoreCase(name)){
		        return TX;
		    }
		    if("最高限额贷款".equalsIgnoreCase(name)){
		        return ZGXE;
		    }
		    if("银团贷款".equalsIgnoreCase(name)){
		        return YT;
		    }
		    if("转贴现".equalsIgnoreCase(name)){
		        return ZTX;
		    }
		    if("买方信贷".equalsIgnoreCase(name)){
		        return MFXD;
		    }
		    if("担保".equalsIgnoreCase(name)){
		        return DB;
		    }
		    if("其他".equalsIgnoreCase(name)){
		        return OTHER;
		    }
		    return -1;
		}
	}
	
	/**
	 * 汇票种类
	 */
	public static class DraftType
	{
		//汇票种类
		public static final long BANK = 1; //银行承兑汇票
		public static final long BIZ = 2; //商业承兑汇票
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) BANK :
					strReturn = "银行承兑汇票";
					break;
				case (int) BIZ :
					strReturn = "商业承兑汇票";
					break;
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("银行承兑汇票".equals(name)){
		        return 1;
		    }
		    if("商业承兑汇票".equals(name)){
		        return 2;
		    }
		    return -1;
		}
	}	
	
	/**
	 * 数据移植错误类型
	 */
	public static class DataConvertErrorType
	{
		public static final long STRINGTRANSERR = 1; //字符串转换错误
		public static final long NUMBERTRANSERR = 2; //数值转换错误
		public static final long DATETRANSERR=3;//日期转换错误
		public static final long NULLERR=4;//非空错误
		public static final long NULLLINKERR=5;//非空连接判定错误
		public static final long IMPORTERR=11;//数据导入到正式表错误
		
		public static final String getName(long lCode)
		{
			String strReturn = ""; //初始化返回值
			switch ((int) lCode)
			{
				case (int) STRINGTRANSERR :
					strReturn = "字符串转换错误";
					break;
				case (int) NUMBERTRANSERR :
					strReturn = "数值转换错误";
					break;
				case (int) DATETRANSERR :
					strReturn = "日期转换错误";
					break;
				case (int) NULLERR :
					strReturn = "非空错误";
					break;			
				case (int) NULLLINKERR :
					strReturn = "非空连接判定错误";
					break;		
				case (int) IMPORTERR :
					strReturn = "数据导入到正式表错误";
					break;	
			}
			return strReturn;
		}
		
		public static final long getValue(String name){
		    if("字符串转换错误".equals(name)){
		        return 1;
		    }
		    if("数值转换错误".equals(name)){
		        return 2;
		    }
		    if("日期转换错误".equals(name)){
		        return 3;
		    }
		    if("非空错误".equals(name)){
		        return 4;
		    }
		    if("非空连接判定错误".equals(name)){
		        return 5;
		    }
		    if("数据导入到正式表错误".equals(name)){
		        return 11;
		    }
		    return -1;
		}
	}
	
}