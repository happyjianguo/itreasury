package com.iss.itreasury.util;

//import com.iss.itreasury.bs.util.BranchIdentify;


/**
 * @author rongyang
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckExternalAccountInfoUtil
{
    /**
     * 根据各个银行银企接口地需要检查外部账户信息地有效性。  
     * <br>只有当账户号有效时(账户号字符串不等于null且不为空时有效)才执行校验。当账户号无效时不会生成银行指令，因此不需校验；
     * <br>当账户号有效时，账户名称，开户行名称也必须有效，有效地标准是可以从开户行名称中判断出对应地银行类型；
     * <br>当账户号有效时且开户行名称也有效时，就根据相应
     * @param accountNo 账户号
     * @param accountName 账户名称
     * @param provinceName 账户开户地省名
     * @param cityName 账户开户地城市名
     * @param branchName 账户开户行名称,校验地方式取决于该参数指定地银行类型。该参数必须有效
     * @return String 放回校验结果描述信息，null表示校验通过
     */
    public static String check(
        String accountNo,
        String accountName,
        String provinceName,
        String cityName,
        String branchName)
    {
        String checkResult = null;

        if ((accountNo == null || "".equals(accountNo))
            && (accountName == null || "".equals(accountName))
            && (branchName == null || "".equals(branchName)))
        {
            //无外部账户信息
            return null;
        }

        if (accountNo == null || "".equals(accountNo))
        {
            return new String("账户号无效");
        }
        if (accountName == null || "".equals(accountName))
        {
            return new String("账户名称无效");
        }
		//根据api修改银行名称的判断方法。

		/*try
		{
			if (branchName == null || "".equals(branchName)
					|| !BranchIdentify.identifyBankByBranchName(branchName))
			{
				return new String("开户行名称无效");
			}
		} catch (Exception e)
		{
			System.out.println("判断开户行名称出错");
			e.printStackTrace();
			return new String("判断开户行名称出错");
		}*/


        return checkResult;
    }

    /**
     * 校验此数据提交给人民银行是否有效
     * @param accountNo
     * @param accountName
     * @param provinceName
     * @param cityName
     * @param branchName
     * @return String 返回null标识校验通过
     */
    /*private static String checkForBC(
        String accountNo,
        String accountName,
        String provinceName,
        String cityName,
        String branchName)
    {
        return null;
    }*/

    /**
     * 校验此数据提交给工商银行是否有效
     * @param accountNo
     * @param accountName
     * @param provinceName
     * @param cityName
     * @param branchName
     * @return String 返回null标识校验通过
     */
    /*private static String checkForICBC(
        String accountNo,
        String accountName,
        String provinceName,
        String cityName,
        String branchName)
    {
        return null;
    }
*/
    /**
     * 校验此数据提交给建设银行是否有效，调用此方法时账户号，账户名称，开户行名称都已有效
     * <br>建行校验逻辑：
     * <br>当开户行名称标识为当前账户是建行账户时，省份名称，城市名称必须有效，当省份是河南省时还必须校验城市名称是不是固定的几个；
     * <br>当开户行名称标识为当前账户不是建行账户时，不检查省份名称和城市名称，当前即有效
     * @param accountNo
     * @param accountName
     * @param provinceName
     * @param cityName
     * @param branchName
     * @return String 返回null标识校验通过
     */
    /*
    private static String checkForCCB(
        String accountNo,
        String accountName,
        String provinceName,
        String cityName,
        String branchName)
    {
        //        if (!BranchIdentify.isCCB(branchName))
        //        {
        //            return null;
        //        }

        long province = ProvinceConstant.getProvinceID(provinceName);
        boolean validated = false;
        switch ((int) province)
        {
            case (int) ProvinceConstant.PROVINCE1 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE2 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE3 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE4 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE5 :
                if (cityName != null && !("".equals(cityName)))
                {
                    validated = true;
                }
                break;
            case (int) ProvinceConstant.PROVINCE6 :
                if (cityName != null && !("".equals(cityName)))
                {
                    validated = true;
                }
                break;
            case (int) ProvinceConstant.PROVINCE7 :
                if (cityName != null && !("".equals(cityName)))
                {
                    validated = true;
                }
                break;
            case (int) ProvinceConstant.PROVINCE8 :
                if (cityName != null && !("".equals(cityName)))
                {
                    validated = true;
                }
                break;
            case (int) ProvinceConstant.PROVINCE9 :
                if (cityName != null && !("".equals(cityName)))
                {
                    validated = true;
                }
                break;
            case (int) ProvinceConstant.PROVINCE10 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE11 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE12 :
            	validated = true;
                break;
//          河南原多个分中心已于2004-7合并为一个,无需再检查市名
//                if (cityName.indexOf("安阳") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("濮阳") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("鹤壁") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("新乡") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("焦作") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("商丘") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("开封") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("郑州") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("洛阳") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("三门峡") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("周口") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("许昌") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("平顶山") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("漯河") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("驻马店") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("南阳") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                if (cityName.indexOf("信阳") >= 0)
//                {
//                    validated = true;
//                    break;
//                }
//                break;
            case (int) ProvinceConstant.PROVINCE13 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE14 :
                if (cityName != null && !("".equals(cityName)))
                {
                    validated = true;
                }
                break;
            case (int) ProvinceConstant.PROVINCE15 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE16 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE17 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE18 :
                if (cityName != null && !("".equals(cityName)))
                {
                    validated = true;
                }
                break;
            case (int) ProvinceConstant.PROVINCE19 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE20 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE21 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE22 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE23 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE24 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE25 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE26 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE27 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE28 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE29 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE30 :
                validated = true;
                break;
            case (int) ProvinceConstant.PROVINCE31 :
                validated = true;
                break;
        }

        if (validated == true)
        {
            return null;
        }
        return new String("账户开户地信息无效");
    }

    public static void main(String[] args)
    {
        String test = check("1", "1", "北京", "", "建行 总行营业部");
        System.out.println((test == null) ? "校验成功" : test);
        System.out.println("=============");

        test = check("1", "1", "", "", "建行");
        System.out.println((test == null) ? "校验成功" : test);
        System.out.println("=============");

        test = check("1", "1", "河南", "", "建行");
        System.out.println((test == null) ? "校验成功" : test);
        System.out.println("=============");

//        test = check("1", "1", "", "", "工行");
//        System.out.println((test == null) ? "校验成功" : test);
//        System.out.println("=============");
//
//        test = check("1", "1", "辽宁", "大连", "建行");
//        System.out.println((test == null) ? "校验成功" : test);
//        System.out.println("=============");
//
//        test = check("1", "1", "辽宁", "沈阳", "建行");
//        System.out.println((test == null) ? "校验成功" : test);
//        System.out.println("=============");
//
//        test = check("1", "1", "辽宁", "", "建行");
//        System.out.println((test == null) ? "校验成功" : test);
//        System.out.println("=============");
    }*/
}
