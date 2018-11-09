/*
 * CLASS   : SECMagnifier
 * FUNCTION: 证券系统放大镜类
 * VERSION : 1.0.0
 * DATE    : 2004/02/23
 * AUTHOR  : lgwang
 * HISTORY : 2004/02/23 新建
 *         :
 */

/**
 * Methods:需要查找放大镜请搜索放大镜前面的编号
 * 
 * //--------------------- 通用放大镜-----------------------
 * Magnifier0010: createClientCtrl()					//客户(业务单位)放大镜,返回NAME
 * Magnifier0020: createClientCtrl() 					//客户（业务单位）放大镜,返回CODE,带出NAME
 * 
 * Magnifier0030: createCounterPartCtrl()				//交易对手放大镜,受业务单位ID元素影响,返回NAME,用transactionTypeId区分不同情况,使用视图SEC_VCounterClientMagnifier
 * Magnifier0040: createCounterPartCtrl()				//交易对手放大镜,受业务单位ID元素影响,返回CODE,带出NAME,用transactionTypeId区分不同情况,使用视图SEC_VCounterClientMagnifier
 *
 * Magnifier0120: createCounterpartBankCtrl()			//交易对手开户行放大镜,受办事处和币种控制,受交易对手ID控制
 * Magnifier0121: createCounterpartBankCtrl()			//交易对手开户行放大镜,受办事处和币种控制,可显示某一种类交易对手的开户行,受交易对手ID控制
 * 
 * Magnifier0130: createClientBankCtrl()				//公司开户行放大镜,返回NAME,带出帐户CODE和NAME,使用视图SEC_VClientBankMagnifier
 * 
 * Magnifier0140: createUserCtrl()						//用户放大镜,收办事处币种限制,可以过滤掉当前用户
 * 
 * Magnifier0150: createStockHolderAccountCtrl()		//股东帐户放大镜,显示帐户Code,带出名称
 * Magnifier0151: createStockHolderAccountCtrl()		//股东帐户放大镜,显示名称,受业务单位影响
 * Magnifier0160: createStockHolderAccountCtrl()		//股东帐户放大镜,显示帐户名称
 * 
 * 
 * 
 * Magnifier0170: createExchangeCenterCodeCtrl()		//证交所放大镜,返回代码,带出名称
 * 
 * Magnifier0180: createSecuritiesCtrl()				//证券放大镜,受币种控制,返回证券名称,受交易类型影响
 * Magnifier0190: createSecuritiesCtrl()				//证券放大镜,受币种控制,返回证券代码,带出证券名称,受交易类型影响
 * Magnifier0181: createSecuritiesCtrl()				//证券放大镜,受币种控制,返回证券名称,用typeId确定显示的证券种类,设置用
 * Magnifier0182: createSecuritiesCtrl()				//证券放大镜,返回证券名称,受证券类型ID元素控制,查询用
 * Magnifier0183: createSecuritiesCtrl()				//证券放大镜,受币种控制,返回证券代码,带出证券名称和期限,受交易类型影响
 * Magnifier0184: createSecuritiesCtrl()				//证券放大镜,返回证券代码,带出名称,受证券类型ID元素控制
 * 
 * Magnifier0185: createSecuritiesCtrl()				//债券放大镜,返回证券代码,带出名称,查找该债券承销下已收取债券
 * 
 * Magnifier0200: createAccountCtrl()					//资金帐户(typeId=0) / 交易帐户(typeId=1)放大镜,受业务单位ID元素影响,返回Account Code,
 * 										当显示资金帐户时,带出股东帐户代码和股东帐户名称,显示交易帐户时带出基金帐户代码和名称,反带出业务单位和交易对手的名称和ID
 * Magnifier0201: createAccountCtrl()					//资金帐户(typeId=0) / 交易帐户(typeId=1)放大镜,受业务单位ID元素影响,返回Account Code,
 * 										当显示资金帐户时,带出股东帐户代码和股东帐户名称,显示交易帐户时带出基金帐户代码和名称,反带出业务单位和交易对手的名称和ID,和交易对手的代码
 *
 * Magnifier0080: createRemarkCtrl()					//备注放大镜,返回DESCRIPTION,用typeId控制类型,0为全部,使用视图SEC_VRemarkMagnifier
 *
 * Magnifier0081: createSubjectCtrl()					//科目放大镜,返回CODE,带出NAME
 *
 * Magnifier0082: createApplyContractCtrl()				//合同放大镜,返回代码,收业务类型,录入人,下一个复核人,状态限制
 * Magnifier0083: createApplyContractCtrl()				//合同放大镜,返回代码,收业务类型,录入人,下一个复核人,状态限制,限制了资产形势
 * Magnifier0084: createApplyContractCtrl2()			//合同放大镜,返回代码,收业务类型,录入人,下一个复核人,状态限制,加了合同日期的限制
 * Magnifier0085: createApplyContractCtrl3()			//合同放大镜,返回代码,受业务类型,录入人,下一个复核人,状态限制；合同的状态为：未执行、执行中、已展期
 *
 * //------------------------------查询用放大镜
 * Magnifier0011: createSecuritiesTypeCtrl()			//证券类型放大镜,返回名称
 * Magnifier0018: createSecuritiesTypeCtrl()			//证券类型放大镜,返回代码,带出名称
 *
 * Magnifier0012: createBusinessTypeCtrl()				//业务类型放大镜,返回名称,带出业务属性
 * Magnifier0013: createBusinessTypeCtrl()				//业务类型放大镜,返回代码,带出名称,业务属性
 *  
 * Magnifier0014: createTransactionTypeCtrl()			//交易类型放大镜,返回名称,受业务类型制约
 * Magnifier0017: createTransactionTypeCtrl()			//交易类型放大镜,返回代码,带出名称,受业务类型制约
 * 
 * Magnifier0015: createBusinessAttributeCtrl()			//交易性质放大镜
 * Magnifier0016: createBusinessTypeCtrlOne()			//业务类型放大镜,返回名称,受业务性质ID元素影响								
 *
 *
 * //------------------------------特殊放大镜
 * Magnifier0049: createCounterPartCtrl()				//交易对手放大镜,查询,设置专用,返回NAME,显示所有状态为正常的交易对手
 * Magnifier0050: createCounterPartCtrl()				//交易对手放大镜,查询,设置专用,返回CODE,带出NAME,显示所有状态为正常的交易对手
 * 
 * Magnifier0060: createCounterPartCtrl()				//特殊放大镜1,申请专用,交易对手放大镜带有限制条件,返回CODE,带出NAME,使用视图SEC_VCounterpartMagnifier_1
 * Magnifier0070: createCounterPartCtrl()				//特殊放大镜2,申请专用,交易对手放大镜,返回CODE,带出NAME,使用视图SEC_VCounterpartMagnifier_2
 * 
 * Magnifier0100: createApplyFormCtrl()					//申请书放大镜***,自带查询条件放大镜
 * 
 * Magnifier0110: createDeliveryOrderCtrl()				//交割单放大镜***,自带查询条件放大镜
 * 
 * Magnifier0090: createRemarkCodeCtrl()				//备注编号放大镜,设置专用,返回CODE,带出DESCRIPTION,用typeId控制类型,0为全部,使用视图SEC_VRemarkMagnifier
 * 
 * Magnifier0091: createSettAccountCtrl()				//活期帐户放大镜,多文本框,显示结算帐户表中的活期帐户
 * 									此放大镜显示四个文本框,分别显示活期帐户的四个部分,命名都为%accountName%+Ctrl,如果页面置焦点需要以数组控制
 */

package com.iss.itreasury.securities.util;


import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;

import javax.servlet.jsp.JspWriter;

import com.iss.itreasury.bill.util.BILLMagnifier;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.securities.exception.SecuritiesException;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;


public class SECMagnifier1
{
	
	/**Magnifier0010
	 * 业务单位放大镜Magnifier001
	 * @param out								jspWriter
	 * @param mainElementName					页面客户名称显示文本框名称,返回业务单位名称
	 * @param hiddenElementName					客户ID隐含域的名称,业务单位ID
	 * @param hiddenElementValue				客户ID的初始值
	 * 
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createClientCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};				//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};
	
		String[] strOperators = {"like","like","="};
		String[] strLogicOperators = {"or","and"};
	
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getClientNameByID(idElementIniValue);//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"客户编号","客户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-客户放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_CLIENT";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	/**Magnifier0020
		 * 业务单位放大镜
		 * @param out								jspWriter
		 * @param mainElementName					页面客户名称显示文本框名称,业务单位代码元素名称
		 * @param hiddenElementName					客户ID隐含域的名称,业务单位ID元素名称
		 * @param hiddenElementValue				客户ID的初始值
		 * 
		 * @param clientNameElementName				带出业务单位名称元素名称
		 * 
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createClientCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String clientNameElementName,
				
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};							//会影响到放大镜的初始值

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","="};
			String[] strLogicOperators = {"or","and"};
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getClientCodeByID(idElementIniValue);//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={clientNameElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={"NAME"};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={NameRef.getClientNameByID(idElementIniValue)};
		
			String[] strListTitleDisplayNames={"客户编号","客户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-客户放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "sec_client";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}


	/**Magnifier0030
	 * 交易对手放大镜,通用放大镜
	 * @param out								jspWriter
	 * @param displayElementName				页面客户名称显示文本框名称,交易对手名称
	 * @param idElementName						客户ID隐含域的名称,交易对手ID
	 * @param idElementIniValue					客户ID的初始值,交易对手ID初始值
	 * 
	 * @param clientIdElementName				业务单位ID的元素名称,影响放大镜
	 * @param transactionTypeId					交易类型ID,会影响放大镜显示的交易对手类型的值
	 * 											如果需要特定类型的交易对手,那么传入该交易对手的代码,如下:
	 *						 							0:  所有类型
	 *													1:	开户行
	 *						 							2:	开户营业部
	 *													3:	开户基金管理公司
	 *													4:	银行间交易对手
	 *													5:	债券分销商
	 *  
	 * @param typeId	
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String clientIdElementName,
			
			long transactionTypeId,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName,clientIdElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"COUNTERPARTCODE","CLIENTID"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={"",NameRef.getClientIdsByCounterpartId(idElementIniValue)};							//会影响到放大镜的初始值
		
		String[] counterpartTypeFields = MagnifierHelper.getCounterpartTypeFields(transactionTypeId);   //获得交易对手类型字段名称
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		/**
		 * 获得所有该交易类型的交易对手类型
		 */
		if (counterpartTypeFields != null && counterpartTypeFields.length>0){	//如果交易对手类型不为空显示该交易对手
			strCtrlValues = new String[counterpartTypeFields.length];
			strCtrlFields = new String[counterpartTypeFields.length];

			for(int n=0;n<counterpartTypeFields.length;n++){					//类型约束值
				strCtrlValues[n] = String.valueOf(SECConstant.TRUE);
			}
			for(int n=0;n<counterpartTypeFields.length;n++){					//类型对应字段
				strCtrlFields[n] = counterpartTypeFields[n];
			}

			strOperators = new String[3+counterpartTypeFields.length];
			strLogicOperators = new String[2+counterpartTypeFields.length];

			strOperators[0] = "like";
			strOperators[1] = "like";
			strOperators[2] = "=";

			
			for(int n=0;n<counterpartTypeFields.length;n++){					//运算符号
				strOperators[3+n] = "=";
			}
			
			strLogicOperators[0] = "or";
			strLogicOperators[1] = "and";

			
			for(int n=0;n<counterpartTypeFields.length;n++){					//逻辑运算符号
				if (n==0) strLogicOperators[2+n] = "and";
				else strLogicOperators[2+n] = "or";
			}
		}
		else{							//如果交易对手类型为空显示全部类型
			throw new SecuritiesException("交易对手放大镜,传入transactionTypeId错误",null);
		}
	
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="COUNTERPARTNAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getCounterpartNameByID(idElementIniValue);							//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};			//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"COUNTERPARTID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames = null;
		String[] strListTitleDisplayFields = null;
		
		if (counterpartTypeFields.length == 1 && counterpartTypeFields[0].equals("IsBankOfDeposit")){
			strListTitleDisplayNames=new String[]{"开户营业部名称","业务单位"}; 	//放大镜弹出页面的显示名称=;可以有多列
			strListTitleDisplayFields=new String[]{"COUNTERPARTNAME","CLIENTNAME"}; 			//显示名称的对应字段
		}
		else{
			strListTitleDisplayNames=new String[]{"交易对手代码","交易对手名称","业务单位"}; 	//放大镜弹出页面的显示名称=;可以有多列
			strListTitleDisplayFields=new String[]{"COUNTERPARTCODE","COUNTERPARTNAME","CLIENTNAME"}; 			//显示名称的对应字段
		}
		String strTableName = "SEC_VCounterClientMagnifier";

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-交易对手放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
		
		/**
		 * 如果当前的交易对手是债券分销商或者银行间交易对手,那么:
		 */
		if (counterpartTypeFields.length == 1 && 
				(counterpartTypeFields[0].equals("IsInterBankCounterpart") 
						|| counterpartTypeFields[0].equals("IsSecuritiesUnderwriter")
						|| counterpartTypeFields[0].equals("IsFloaters")
						|| counterpartTypeFields[0].equals("IsSecurityCo"))){
			
			strCtrlElementNames=new String[]{displayElementName};			//会影响到放大镜显示得值的参数
			strCtrlElementFields=new String[]{"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
			strCtrlElementValues=new String[]{""};							//会影响到放大镜的初始值
			
			strCtrlValues = new String[counterpartTypeFields.length+1];
			strCtrlFields = new String[counterpartTypeFields.length+1];
			
			strCtrlValues[0] = String.valueOf(String.valueOf(SECConstant.SettingStatus.CHECKED));
			strCtrlFields[0] = "STATUSID";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//类型约束值
				strCtrlValues[n+1] = String.valueOf(SECConstant.TRUE);
			}
			for(int n=0;n<counterpartTypeFields.length;n++){					//类型对应字段
				strCtrlFields[n+1] = counterpartTypeFields[n];
			}

			strOperators = new String[3+counterpartTypeFields.length];
			strLogicOperators = new String[2+counterpartTypeFields.length];
			
			strOperators[0] = "like";
			strOperators[1] = "like";
			strOperators[2] = "=";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//运算符号
				strOperators[3+n] = "=";
			}
			
			strLogicOperators[0] = "or";
			strLogicOperators[1] = "and";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//逻辑运算符号
				if (n==0) strLogicOperators[2+n] = "and";
				else strLogicOperators[2+n] = "or";
			}
			
			strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
			strVisibleElementValues=NameRef.getCounterpartNameByID(idElementIniValue);							//显示元素的初始值?????
			
			strHiddenElementNames=new String[]{idElementName};			//主页面隐含域里的id=;要产生页面元素
			strHiddenElementFields=new String[]{"ID"}; 					//id对应的字段名
			strHiddenElementValues=new String[]{String.valueOf(idElementIniValue)};	//返回的默认值
			
			strListTitleDisplayNames=new String[]{"交易对手代码","交易对手名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			strListTitleDisplayFields=new String[]{"CODE","NAME"}; 			//显示名称的对应字段
			
			strTableName = "SEC_counterPart";
		}
		
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	/**Magnifier0040
	 * 交易对手放大镜,通用放大镜二
	 * @param out								jspWriter
	 * @param displayElementName				页面交易对手代码显示文本框名称
	 * @param idElementName						交易对手ID隐含域的名称,交易对手ID
	 * @param idElementIniValue					交易对手ID的初始值
	 * 
	 * @param clientIdElementName				交易对手ID元素名称,影响放大镜带出值
	 * @param transactionTypeId					交易类型Id,用来确定要带出的交易对手类型
	 * 											如果需要特定类型的交易对手,那么传入该交易对手的代码,如下:
	 *						 							0:  所有类型
	 *													1:	开户行
	 *						 							2:	开户营业部
	 *													3:	开户基金管理公司
	 *													4:	银行间交易对手
	 *													5:	债券分销商
	 *  
	 * @param counterpartNameElementName		带出的交易对手名称 
	 * 
	 * @param typeId							
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//带出的值
			String clientIdElementName, 
			
			long transactionTypeId,								//交易类型Id,用来确定要带出的交易对手类型
			
			String counterpartNameElementName,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName,clientIdElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"COUNTERPARTCODE","CLIENTID"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={"",NameRef.getClientIdsByCounterpartId(idElementIniValue)};							//会影响到放大镜的初始值
		
		String[] counterpartTypeFields = MagnifierHelper.getCounterpartTypeFields(transactionTypeId);   //获得交易对手类型字段名称
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		
		String[] strOperators = null;
		String[] strLogicOperators = null;
		Log.print("交易类型ID:"+transactionTypeId);
		
		if (counterpartTypeFields!=null){
			for (int n=0;n<counterpartTypeFields.length;n++){
				Log.print("交易对手类型:"+counterpartTypeFields[n]);
			}
		}
		
		
		/**
		 * 获得所有该交易类型的交易对手类型
		 */
		if (counterpartTypeFields != null && counterpartTypeFields.length>0){	//如果交易对手类型不为空显示该交易对手
			strCtrlValues = new String[counterpartTypeFields.length];
			strCtrlFields = new String[counterpartTypeFields.length];
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//类型约束值
				strCtrlValues[n] = String.valueOf(SECConstant.TRUE);
			}
			for(int n=0;n<counterpartTypeFields.length;n++){					//类型对应字段
				strCtrlFields[n] = counterpartTypeFields[n];
			}

			strOperators = new String[3+counterpartTypeFields.length];
			strLogicOperators = new String[2+counterpartTypeFields.length];

			strOperators[0] = "like";
			strOperators[1] = "like";
			strOperators[2] = "=";

			
			for(int n=0;n<counterpartTypeFields.length;n++){					//运算符号
				strOperators[3+n] = "=";
			}
			
			strLogicOperators[0] = "or";
			strLogicOperators[1] = "and";

			
			for(int n=0;n<counterpartTypeFields.length;n++){					//逻辑运算符号
				if (n==0) strLogicOperators[2+n] = "and";
				else strLogicOperators[2+n] = "or";
			}
		}
		else{							//如果交易对手类型为空显示全部类型
			throw new SecuritiesException("交易对手放大镜,传入transactionTypeId错误",null);
		}
		
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="COUNTERPARTCODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);							//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};			//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"COUNTERPARTID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		String[] strOtherElementNames={counterpartNameElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"COUNTERPARTNAME"};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={NameRef.getCounterpartNameByID(idElementIniValue)};
		
		String[] strListTitleDisplayNames = null;
		String[] strListTitleDisplayFields = null;
		
		if (transactionTypeId == 2 || (counterpartTypeFields.length == 1 && counterpartTypeFields[0].equals("IsBankOfDeposit"))){
			strListTitleDisplayNames=new String[]{"开户营业部名称","业务单位"}; 	//放大镜弹出页面的显示名称=;可以有多列
			strListTitleDisplayFields=new String[]{"COUNTERPARTNAME","CLIENTNAME"}; 			//显示名称的对应字段
		}
		else{
			strListTitleDisplayNames=new String[]{"交易对手代码","交易对手名称","业务单位"}; 	//放大镜弹出页面的显示名称=;可以有多列
			strListTitleDisplayFields=new String[]{"COUNTERPARTCODE","COUNTERPARTNAME","CLIENTNAME"}; 			//显示名称的对应字段
		}
		
		
		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
		
		String strWindowTitle="证券-交易对手放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
		
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VCounterClientMagnifier";
		
		boolean blnShowOnly = showOnly;								//只供显示标志位
		
		/**
		 * 如果当前的交易对手是债券分销商或者银行间交易对手,那么:
		 */
		if (counterpartTypeFields.length == 1 && 
				(counterpartTypeFields[0].equals("IsInterBankCounterpart") 
						|| counterpartTypeFields[0].equals("IsSecuritiesUnderwriter")
						|| counterpartTypeFields[0].equals("IsFloaters")
						|| counterpartTypeFields[0].equals("IsSecurityCo"))){
			
			strCtrlElementNames=new String[]{displayElementName};			//会影响到放大镜显示得值的参数
			strCtrlElementFields=new String[]{"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
			strCtrlElementValues=new String[]{""};							//会影响到放大镜的初始值
			
			
			strCtrlValues = new String[counterpartTypeFields.length+1];
			strCtrlFields = new String[counterpartTypeFields.length+1];
			
			strCtrlValues[0] = String.valueOf(String.valueOf(SECConstant.SettingStatus.CHECKED));
			strCtrlFields[0] = "STATUSID";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//类型约束值
				strCtrlValues[n+1] = String.valueOf(SECConstant.TRUE);
			}
			for(int n=0;n<counterpartTypeFields.length;n++){					//类型对应字段
				strCtrlFields[n+1] = counterpartTypeFields[n];
			}
			
			strOperators = new String[3+counterpartTypeFields.length];
			strLogicOperators = new String[2+counterpartTypeFields.length];
			
			strOperators[0] = "like";
			strOperators[1] = "like";
			strOperators[2] = "=";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//运算符号
				strOperators[3+n] = "=";
			}
			
			strLogicOperators[0] = "or";
			strLogicOperators[1] = "and";
			
			for(int n=0;n<counterpartTypeFields.length;n++){					//逻辑运算符号
				if (n==0) strLogicOperators[2+n] = "and";
				else strLogicOperators[2+n] = "or";
			}
			
			strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
			strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);							//显示元素的初始值?????
			
			strHiddenElementNames=new String[]{idElementName};			//主页面隐含域里的id=;要产生页面元素
			strHiddenElementFields=new String[]{"ID"}; 					//id对应的字段名
			strHiddenElementValues=new String[]{String.valueOf(idElementIniValue)};	//返回的默认值
			
			strOtherElementNames=new String[]{counterpartNameElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
			strOtherElementFields=new String[]{"NAME"};							//其他需要返回值的元素对应的数据库字段
			strOtherElementValues=new String[]{NameRef.getCounterpartNameByID(idElementIniValue)};
		
			
			strListTitleDisplayNames=new String[]{"交易对手代码","交易对手名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			strListTitleDisplayFields=new String[]{"CODE","NAME"}; 			//显示名称的对应字段
			
			strTableName = "SEC_counterPart";
		}
		
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	
	/**Magnifier0049
		 * 交易对手放大镜,设置放大镜二,
		 * @param out								jspWriter
		 * @param displayElementName				页面客户名称显示文本框名称,交易对手名称
		 * @param idElementName						客户ID隐含域的名称,交易对手ID
		 * @param idElementIniValue					客户ID的初始值
		 * 
		 * @param typeId							类型码
		 * 									0:所有交易对手
		 * 									1:只显示去除开户营业部的交易对手 
		 * 									2:只显示开户营业部
		 * 									3:只显示银行间交易对手
		 * 									4:只显示基金管理公司
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createCounterPartCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
				
			if (typeId >4 || typeId <0){
				throw new SecuritiesException("交易对手放大镜,传入typeId错误",null);
			}
			
			String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
		

			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			
			String[] strOperators = null;
			String[] strLogicOperators = null;
			
			if (typeId == 0){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
				strCtrlFields = new String[]{"STATUSID"};
				
				strOperators = new String[]{"like","like","="};
				strLogicOperators = new String[]{"or","and"};
			}
			else if (typeId == 1){		//只显示去除开户营业部的交易对手
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"0","null"};
				strCtrlFields = new String[]{"STATUSID","ISBANKOFDEPOSIT","ISBANKOFDEPOSIT"};
				
				strOperators = new String[]{"like","like","=","=","is"};
				strLogicOperators = new String[]{"or","and","and","or"};
			}
			else if (typeId == 2){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1"};
				strCtrlFields = new String[]{"STATUSID","ISBANKOFDEPOSIT"};
				
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};
			}
			else if (typeId == 3){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1"};
				strCtrlFields = new String[]{"STATUSID","ISINTERBANKCOUNTERPART"};
				
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};
			}
			else if (typeId == 4){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1"};
				strCtrlFields = new String[]{"STATUSID","ISFUNDMANAGEMENTCO"};
				
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};
			}
		
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getCounterpartNameByID(idElementIniValue);							//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};			//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"交易对手代码","交易对手名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

					String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
					String strWindowTitle="";
					switch((int)typeId){
					case 0:						
					case 1:
						strWindowTitle="证券-交易对手放大镜";
						break;
					case 2:
						strWindowTitle="证券-开户营业部放大镜";
						strListTitleDisplayNames[0]="开户营业部代码";
						strListTitleDisplayNames[1]="开户营业部名称";
						break;
					case 3:
						strWindowTitle="证券-银行间交易对手放大镜";
						strListTitleDisplayNames[0]="银行间交易对手代码";
						strListTitleDisplayNames[1]="银行间交易对手名称";
						break;
					case 4:
						strWindowTitle="证券-基金管理公司放大镜";
						strListTitleDisplayNames[0]="基金管理公司代码";
						strListTitleDisplayNames[1]="基金管理公司名称";
						break;
					}	
					//String strWindowTitle="证券-交易对手放大镜";					//放大镜弹出窗口标题
					int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
					int intSQL=4;												//数据查询Sql 
					String strTableName = "sec_counterpart";
	
					boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}


/**Magnifier0050
	 * 交易对手放大镜,设置放大镜一
	 * @param out								jspWriter
	 * @param displayElementName				页面客户名称显示文本框名称,交易对手代码
	 * @param idElementName						客户ID隐含域的名称,交易对手ID
	 * @param idElementIniValue					客户ID的初始值
	 * 
	 * @param counterpartNameElementName					交易对手名称元素名称
	 *
	 * @param typeId							类型码
	 * 									0:所有交易对手
	 * 									1:只显示去除开户营业部的交易对手 
	 * 									2:只显示开户营业部
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
		
			String counterpartNameElementName,			

			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
			
		if (typeId >2 || typeId <0){
			throw new SecuritiesException("交易对手放大镜,传入typeId错误",null);
		}
		String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
		

		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		if (typeId == 0){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
			
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else if (typeId == 1){		//只显示去除开户营业部的交易对手
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1","null"};
			strCtrlFields = new String[]{"STATUSID","ISBANKOFDEPOSIT","ISBANKOFDEPOSIT"};
			
			strOperators = new String[]{"like","like","=","<>","is"};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
		else if (typeId == 2){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1"};
			strCtrlFields = new String[]{"STATUSID","ISBANKOFDEPOSIT"};
			
			strOperators = new String[]{"like","like","=","="};
			strLogicOperators = new String[]{"or","and","and"};
		}

		
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);//显示元素的初始值
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};			//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		String[] strOtherElementNames={counterpartNameElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"NAME"};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={NameRef.getCounterpartNameByID(idElementIniValue)};
		
		String[] strListTitleDisplayNames={"交易对手代码","交易对手名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

				String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
				String strWindowTitle="证券-交易对手放大镜";					//放大镜弹出窗口标题
				int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
				int intSQL=4;												//数据查询Sql 
				String strTableName = "sec_counterpart";
	
				boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}



	/**Magnifier0060
	 * 交易对手放大镜	业务申请,资金拆借业务专用
	 * @param out								jspWriter
	 * @param displayElementName				页面客户名称显示文本框名称
	 * @param idElementName						客户ID隐含域的名称
	 * @param idElementIniValue					客户ID的初始值
	 * 
	 * @param String transactionTypeElementName 影响放大镜的交易类型元素
	 * 
	 * @param counterPartNameElementName		带出的交易对手名称 
	 * 
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//影响放大镜的交易类型元素
			long transactionType,
			
			//带出的值
			String counterPartNameElementName, 
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE"};					//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};										//会影响到放大镜的初始值

		String[] strCtrlValues = {String.valueOf(SECConstant.CounterpartStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};

		String[] strOperators = {"like","like","="};
		String[] strLogicOperators = {"or","and"};
	
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);							//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};			//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"id"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		String[] strOtherElementNames={counterPartNameElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"NAME"};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={NameRef.getCounterpartNameByID(idElementIniValue)};
			
		String[] strListTitleDisplayNames={"交易对手代码","交易对手名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点

		String strWindowTitle="证券-交易对手放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "";
		if (transactionType == SECConstant.BusinessType.CAPITAL_IN_CREDIT_EXTENSION.CAPITAL_IN_CREDIT_EXTENSION){
			strTableName = "SEC_VCounterpartMagnifier_2";
		}
		else if (transactionType == SECConstant.BusinessType.CAPITAL_OUT_CREDIT_EXTENSION.CAPITAL_OUT_CREDIT_EXTENSION){
			strTableName = "SEC_VCounterpartMagnifier_3";
		}
		 
	
		boolean blnShowOnly = showOnly;								//只供显示标志位

		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	
	/**Magnifier0070
	 * 交易对手放大镜		业务申请,资金拆借业务专用
	 * @param out								jspWriter
	 * @param displayElementName				页面客户名称显示文本框名称
	 * @param idElementName						客户ID隐含域的名称
	 * @param idElementIniValue					客户ID的初始值
	 * 
	 * @param String transactionTypeElementName 影响放大镜的交易类型元素
	 * 
	 * @param counterPartNameElementName		带出的交易对手名称 
	 * @param String transactionStartDateElementName,
	 * @param String transactionEndDateElementName,
	 * @param String pledgeSecuritiesAmountElementName,
	 * @param String termElementName,
	 * 
	 * 
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createCounterPartCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//影响放大镜的交易类型元素
			String transactionTypeElementName,
			
			//带出的值
			String counterPartNameElementName,
			String transactionStartDateElementName,
			String transactionEndDateElementName,
			String pledgeSecuritiesAmountElementName,
			String termElementName,
			String sumpledgesecuritiesamountElementName,
			String sumpledgesecuritiesamountElementName1,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	displayElementName,
										transactionTypeElementName
										};//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE","transactionTypeId"};					//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={"",""};										//会影响到放大镜的初始值

		String[] strCtrlValues = {String.valueOf(SECConstant.CounterpartStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};

		String[] strOperators = {"like","like","=","="};
		String[] strLogicOperators = {"or","and","and"};
	
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getCounterpartCodeByID(idElementIniValue);							//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};			//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"id"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		String[] strOtherElementNames={	counterPartNameElementName,
										transactionStartDateElementName,
										transactionEndDateElementName,
										pledgeSecuritiesAmountElementName,
										termElementName,
										sumpledgesecuritiesamountElementName,
										sumpledgesecuritiesamountElementName1};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"NAME",
										"transactionStartDate",
										"transactionEndDate",
										"pledgeSecuritiesAmount",
										"term",
										"sumpledgesecuritiesamount1",
										"sumpledgesecuritiesamount2"};									//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={NameRef.getCounterpartNameByID(idElementIniValue),
										NameRef.getTransactionStartDateByCounterpartID(idElementIniValue),
										NameRef.getTransactionEndDateByCounterpartID(idElementIniValue),
										NameRef.getPledgeSecuritiesAmountByCounterpartID(idElementIniValue),
										NameRef.getTermByCounterpartID(idElementIniValue),
										"",
										""};
			
		String[] strListTitleDisplayNames={"交易对手代码","交易对手名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","NAME"}; 				//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点

		String strWindowTitle="证券-交易对手放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VCounterpartMagnifier_1";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	/**Magnifier0080
	 * 摘要放大镜
	 * @param out								jspWriter
	 * @param displayElementName				显示元素名称,返回摘要描述
	 * @param displayElementIniValue			显示元素初始值
	 * 
	 * @param typeId
	 * 摘要类型,以交易类型作为区分,传入交易类型码,获得公共摘要和本类型摘要,"0"为获得全部
	 * 
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createRemarkCtrl(
			JspWriter out,
			String displayElementName,
			String displayElementIniValue,
			
			long typeId,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
	
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		if (typeId == 0){				//显示全部
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
	
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else{							//只显示全局摘要和针对类型的摘要
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(typeId),"0"};
			strCtrlFields = new String[]{"STATUSID","BUSINESSTYPEID","BUSINESSTYPEID"};
	
			strOperators = new String[]{"like","like","=","=","="};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
		
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="DESCRIPTION";				//显示元素对应数据库的字段名.
		String strVisibleElementValues=displayElementIniValue;			//显示元素的初始值?????
		
		String strVisibleElementType="textarea";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={"remarkID"};							//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 						//id对应的字段名
		String[] strHiddenElementValues={""};							//返回的默认值
		
		String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};	
		
		String[] strListTitleDisplayNames={"审批语编号","审批语描述","业务类型"};	 //放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","DESCRIPTION","BUSINESSTYPENAME"};//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-审批语放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VRemarkMagnifier";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}

	/**Magnifier0090
	 * 备注编号放大镜
	 * @param out								jspWriter
	 * @param displayElementName				页面备注编号显示文本框名称
	 * @param displayElementName				备注编号ID隐含域的名称
	 * @param idElementIniValue					备注编号ID的初始值
	 * 
	 * @param remarkDescElementName				带出来的备注描述元素名称
	 * 
	 * @param typeId
	 * 摘要类型,以交易类型作为区分,传入交易类型码,获得公共摘要和本类型摘要,"0"为获得全部
	 * 
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createRemarkCodeCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String remarkDescElementName,
			
			long typeId,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};				//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
		
		
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
	
		String[] strOperators = null;
		String[] strLogicOperators = null;
		
		if (typeId == 0){				//显示全部
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
	
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else{							//只显示全局摘要和针对类型的摘要
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(typeId),"0"};
			strCtrlFields = new String[]{"STATUSID","BUSINESSTYPEID","BUSINESSTYPEID"};
	
			strOperators = new String[]{"like","like","=","=","="};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
		

		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getRemarkCodeByID(idElementIniValue);//显示元素的初始值?????

		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中

		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值

		String[] strOtherElementNames={remarkDescElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"DESCRIPTION"};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={NameRef.getRemarkDescByID(idElementIniValue)};

		String[] strListTitleDisplayNames={"审批语编号","审批语描述"};	 //放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","DESCRIPTION"};//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点

		String strWindowTitle="证券-审批语放大镜";				//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置

		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VRemarkMagnifier";

		boolean blnShowOnly = showOnly;								//只供显示标志位

		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,

						strCtrlValues,
						strCtrlFields,

						strOperators,
						strLogicOperators,

						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,

						strVisibleElementType,
						strVisibleElementProperty,

						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,

						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,

						strListTitleDisplayNames,
						strListTitleDisplayFields,

						strnextFocusElementNames,

						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}


	
	/**Magnifier0100
	 * 申请书放大镜
	 * @param out
	 * @param displayElementName						//显示CODE元素的名称
	 * @param idElementName						//id元素名称
	 * @param idElementIniValue						//id的初始值
	 * -----------------------------控制元素
	 * @param 封装入放大镜
	 * -----------------------------
	 * long currencyId,								//币种
	 * long officeId,								//办事处
	 * -----------------------------
	 * @param visibleElementProperty				//显示CODE元素的名称
	 * @param nextFocusElementNames					//下一个焦点位置
	 * @param showOnly								//是否只作显示
	 * @throws Exception
	 */
	public static void createApplyFormCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			long currencyId,
			long officeId,
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	"secTransactionTypeId",
										"secApplyFormInputDateFrom",
										"secApplyFormInputDateTo",
										"secTransactionDateFrom",
										"secTransactionDateTo",
										"secSystemTransactionCode",
										"secClientId",
										"secCounterpartId",
										"secPledgeSecuritiesAmountFrom",
										"secPledgeSecuritiesAmountTo",
										"secAmountFrom",
										"secAmountTo",
										"secPriceFrom",
										"secPriceTo",
										"secQuantityFrom",
										"secQuantityTo",
										"secSecuritiesId",
										"secApplyFormStatusId",
										"secStockId"
										};							//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={	"TRANSACTIONTYPEID",
										"_INPUTDATE",
										"_INPUTDATE",
										"_TRANSACTIONDATE",
										"_TRANSACTIONDATE",
										"SYSTEMTRANSACTIONCODE",
										"CLIENTID",
										"COUNTERPARTID",			//10
										"PLEDGESECURITIESAMOUNT",
										"PLEDGESECURITIESAMOUNT",
										"AMOUNT",
										"AMOUNT",
										"PRICE",					//15
										"PRICE",
										"QUANTITY",
										"QUANTITY",
										"SECURITIESID",				//20
										"STATUSID",
										"STOCKID"
										};							//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={	"",
										"",
										"",
										"",
										"",							//5
										"",
										"",
										"",
										"",
										"",							//10
										"",
										"",
										"",
										"",
										"",							//15
										"",
										"",
										"",
										""
										};							//会影响到放大镜的初始值

		String[] strCtrlValues = {String.valueOf(currencyId),String.valueOf(officeId)};
		String[] strCtrlFields = {"CURRENCYID","OFFICEID"};
		
		/**
		 * 操作符
		 */
		String[] strOperators = {		"like",
										"=",
										">=",
										"<=",						//5
										">=",
										"<=",
										"=",
										"=",						//10
										"=",
										">=",
										"<=",
										">=",
										"<=",						//15
										">=",
										"<=",
										">=",
										"<=",
										"=",
										"in",
										"=",
										"=",
										"="};						//24
		
		/**
		 * 操作符
		 */
		String[] strLogicOperators = {};
	
		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getApplyFormCodeByID(idElementIniValue);			//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"id"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"申请书ID","申请书代码"}; 	//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"id","CODE"}; 			//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-申请书放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "Sec_ApplyForm";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
	
	/**Magnifier0110
	 * 交割单放大镜
	 * @param out
	 * 
	 * @param displayElementName			//显示元素名称,交割单编号
	 * @param idElementName					//id元素名称,交割单ID
	 * @param idElementIniValue				//放大镜初始值
	 * 
	 * @param currencyId					//币种ID
	 * @param officeId						//办事处ID
	 * 
	 * @param typeId						//
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createDeliveryOrderCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			long currencyId,
			long officeId,
			
			long typeId,			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	"transactionTypeId",
										"transactionDateFrom",
										"transactionDateTo",
										"systemTransactionCode",
										"clientId",							//5
										"counterpartId",
										"amountFrom",
										"amountTo",
										"statusId",
										"accountId",
										"securitiesId",
										"priceFrom",
										"priceTo",
										"transactionQuantityFrom",			//15
										"transactionQuantityTo",
										"netPriceFrom",
										"netPriceTo",						//18
										"quantityFrom",
										"quantityTo"
										};							//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={	"TRANSACTIONTYPEID",
										"_TRANSACTIONDATE",
										"_TRANSACTIONDATE",
										"SYSTEMTRANSACTIONCODE",
										"CLIENTID",							//5
										"COUNTERPARTID",
										"AMOUNT",
										"AMOUNT",
										"STATUSID",
										"ACCOUNTID",
										"SECURITIESID",
										"PRICE",
										"PRICE",
										"TRANSACTIONQUANTITY",				//15
										"TRANSACTIONQUANTITY",
										"NETPRICE",
										"NETPRICE",							//18
										"QUANTITY",
										"QUANTITY"
										};							//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={	"",
										"",
										"",
										"",
										"",									//5
										"",
										"",
										"",
										"",									//10
										"",
										"",
										"",
										"",
										"",									//15
										"",
										"",
										"",									//18
										"",
										""
										};							//会影响到放大镜的初始值

		String[] strCtrlValues = {String.valueOf(currencyId),String.valueOf(officeId)};
		String[] strCtrlFields = {"CURRENCYID","OFFICEID"};
		
		/**
		 * 操作符
		 */
		String[] strOperators = {		"like",
										"=",
										">=",
										"<=",
										"=",								//5
										"=",
										"=",
										">=",
										"<=",
										"=",
										"=",
										"=",
										">=",
										"<=",								//15
										">=",
										"<=",
										">=",
										"<=",									//19
										">=",
										"<=",
										"=",
										"="
									};
		
		/**
		 * 操作符
		 */
		String[] strLogicOperators = {};
	
		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getDeliveryOrderCodeByID(idElementIniValue);			//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"交割单代码"}; 	//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE"}; 	//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-交割单放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_DeliveryOrder";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
	/**Magnifier0120
	 * 交易对手开户行放大镜
	 * @param out
	 * 
	 * @param displayElementName					//显示元素名称,交易对手开户行名称
	 * @param idElementName							//ID元素名称,交易对手开户行ID
	 * @param idElementIniValue						//交易对手开户行初始值
	 * ---------控制值
	 * @param officeId								//办事处Id
	 * @param currencyId							//币种Id
	 * 
	 * ---------控制元素
	 * @param counterpartIdElementName				//交易对手元素名称
	 * 
	 * ---------影响元素
	 * @param accountCode							//开户帐户代码
	 * @param accountName							//开户帐户名称
	 * 
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createCounterpartBankCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//控制参数
			long officeId,
			long currencyId,
			String counterpartIdElementName,
			//控制参数
			
			//回置参数
			String accountCode,
			String accountName,
			//回置参数
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	displayElementName,
										counterpartIdElementName,
										};							//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"id",
										"CounterpartID",
										};							//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={	"",
										String.valueOf(idElementIniValue)};						//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空

		String[] strCtrlValues = {String.valueOf(officeId),String.valueOf(currencyId),String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"OFFICEID","CURRENCYID","STATUSID"};
		
		/**
		 * 操作符
		 */
		String[] strOperators = {		"like",
										"like",
										"=",
										"=",
										"=",
										"="};
		/**
		 * 操作符
		 */
		String[] strLogicOperators = {	"or",
										"and",
										"and",
										"and",
										"and"};
	
		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="BANKNAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getCounterpartBankNameByBankID(idElementIniValue);			//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		
		
		String[] strOtherElementNames={accountCode,accountName};	//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"BANKACCOUNTCODE","BANKACCOUNTNAME"};//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={
										NameRef.getCounterpartAccountCodeByBankID(idElementIniValue),
										NameRef.getCounterpartAccountNameByBankID(idElementIniValue)};
		
		String[] strListTitleDisplayNames={"交易对手开户行名称","银行帐户名称"};//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"BANKNAME","BANKACCOUNTNAME"}; 		//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
		
		String strWindowTitle="证券-交易对手开户行放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_CounterpartBankAccount";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}	
	
	/**Magnifier0121
	 * 交易对手开户行放大镜,可以显示指定交易对手类型的开户行
	 * @param out
	 * 
	 * @param displayElementName					//显示元素名称,交易对手开户行名称
	 * @param idElementName							//ID元素名称,交易对手开户行ID
	 * @param idElementIniValue						//交易对手开户行初始值
	 * ---------控制值
	 * @param officeId								//办事处Id
	 * @param currencyId							//币种Id
	 * @param counterpartTypeId						//指定的交易对手类型ID
	 * ---------控制元素
	 * @param counterpartIdElementName				//交易对手元素名称
	 * 
	 * ---------影响元素
	 * @param accountCode							//开户帐户代码
	 * @param accountName							//开户帐户名称
	 * 
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createCounterpartBankCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//控制参数
			long officeId,
			long currencyId,
			
			long counterpartTypeId,
			
			String counterpartIdElementName,
			//控制参数
			
			//回置参数
			String accountCode,
			String accountName,
			//回置参数
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={	displayElementName,
										counterpartIdElementName,
										};							//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"id",
										"CounterpartID",
										};							//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={	"",
										String.valueOf(idElementIniValue)};						//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
		
		/**
		 * 操作符
		 */
		String[] strOperators = new String[]{	"like",
										"like",
										"=",
										"=",
										"=",
										"="};
		/**
		 * 操作符
		 */
		String[] strLogicOperators = new String[]{"or",
										"and",
										"and",
										"and",
										"and"};
		String[] strCtrlValues = null; 
		String[] strCtrlFields = null;
		
		boolean isNotAll = false;
		if (counterpartTypeId == 0){
			strCtrlValues = new String[]{String.valueOf(officeId),String.valueOf(currencyId),String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"OFFICEID","CURRENCYID","STATUSID"};
		}
		else if (counterpartTypeId == SECConstant.CounterpartObjectType.INTER_BANK_COUNTERPART){//银行间交易对手
			strCtrlValues = new String[]{	String.valueOf(officeId),
										String.valueOf(currencyId),
										String.valueOf(SECConstant.SettingStatus.CHECKED),
										String.valueOf(SECConstant.TRUE)};
			strCtrlFields = new String[]{"OFFICEID","CURRENCYID","STATUSID","ISINTERBANKCOUNTERPART"};
			isNotAll = true;
		}
		else if (counterpartTypeId == SECConstant.CounterpartObjectType.FUND_MANAGEMENT_CO){	//开户基金管理公司
			strCtrlValues = new String[]{	String.valueOf(officeId),
										String.valueOf(currencyId),
										String.valueOf(SECConstant.SettingStatus.CHECKED),
										String.valueOf(SECConstant.TRUE)};
			strCtrlFields = new String[]{"OFFICEID","CURRENCYID","STATUSID","ISFUNDMANAGEMENTCO"};
			isNotAll = true;
		}
		else if (counterpartTypeId == SECConstant.CounterpartObjectType.SECURITIES_UNDERWRITER){//债券分销商
			strCtrlValues = new String[]{	String.valueOf(officeId),
										String.valueOf(currencyId),
										String.valueOf(SECConstant.SettingStatus.CHECKED),
										String.valueOf(SECConstant.TRUE)};
			strCtrlFields = new String[]{"OFFICEID","CURRENCYID","STATUSID","ISSECURITIESUNDERWRITER"};
			isNotAll = true;
		}
		else{
			throw new SecuritiesException("此交易对手类型交易对手开户行放大镜还未支持",null);
		}
		
		if (isNotAll){
			strOperators = new String[]{	"like",
					"like",
					"=",
					"=",
					"=",
					"=",
					"="};

			strLogicOperators = new String[]{"or",
					"and",
					"and",
					"and",
					"and",
					"and"};
		}
		
		
		
	
		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="BANKNAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getCounterpartBankNameByBankID(idElementIniValue);			//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		
		
		String[] strOtherElementNames={accountCode,accountName};	//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"BANKACCOUNTCODE","BANKACCOUNTNAME"};//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={
										NameRef.getCounterpartAccountCodeByBankID(idElementIniValue),
										NameRef.getCounterpartAccountNameByBankID(idElementIniValue)};
		
		String[] strListTitleDisplayNames={"交易对手开户行名称","银行帐户名称"};//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"BANKNAME","BANKACCOUNTNAME"}; 		//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
		
		String strWindowTitle="证券-交易对手开户行放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VCounterAccountMagnifier";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}	
	

	/**Magnifier0130
	 * 公司开户行放大镜
	 * @param out
	 * 
	 * @param displayElementName					//显示元素名称,公司开户行名称
	 * @param idElementName							//ID元素名称,公司开户行ID
	 * @param idElementIniValue						//公司开户行初始值
	 * 
	 * ---------控制值
	 * @param officeId								//办事处
	 * @param currencyId							//币种
	 * 
	 * 
	 * ---------影响元素
	 * @param accountCode							//开户帐户代码
	 * @param accountName							//开户帐户名称
	 * 
	 * @param visibleElementProperty				//显示元素的附加属性
	 * @param nextFocusElementNames					//下一个焦点元素名称
	 * @param showOnly								//是否只做显示
	 * @throws Exception
	 */
	public static void createClientBankCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//控制参数
			long officeId,
			long currencyId,
			//控制参数
			
			//回置参数
			String accountCode,						//开户行代码
			String accountName,						//开户行名称
			//回置参数
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};		//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"SCODE"};		//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={	""};					//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空

		String[] strCtrlValues = {String.valueOf(officeId),String.valueOf(currencyId)};
		String[] strCtrlFields = {"NOFFICEID","NCURRENCYID"};
		
		/**
		 * 操作符
		 */
		String[] strOperators = {		"like",
										"like",
										"=",
										"="};
		/**
		 * 操作符
		 */
		String[] strLogicOperators = {	"or",
										"and",
										"and"};
	
		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="SNAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getClientBankNameByBankID(idElementIniValue);			//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		
		
		String[] strOtherElementNames={accountCode,accountName};	//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"SBANKACCOUNTCODE","SENTERPRISENAME"};//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={
										NameRef.getClientAccountCodeByBankID(idElementIniValue),
										NameRef.getClientAccountNameByBankID(idElementIniValue)
										};
		
		String[] strListTitleDisplayNames={"业务单位开户行代码","业务单位开户行名称","帐户代码"};//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"SCODE","SNAME","SBANKACCOUNTCODE"}; 		//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
		
		String strWindowTitle="证券-业务单位开户行放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "sett_branch";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}	
	
	/**Magnifier0140
	 * 用户放大镜
	 * @param out
	 * 
	 * @param displayElementName
	 * @param idElementName
	 * @param idElementIniValue
	 * 
	 * ---------控制值
	 * @param officeId								//办事处
	 * @param currencyId							//币种
	 * @param exceptUserId							//去除的用户代码
	 * 
	 * 
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createUserCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			//控制值
			long officeId,						//办事处ID
			long currencyId,					//币种
			String exceptUserId,				//排除用户ID
			//控制值
			
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={};							//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={};							//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={};							//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空

		String[] strCtrlValues = {String.valueOf(officeId),String.valueOf(currencyId),exceptUserId};
		String[] strCtrlFields = {"nOfficeId","nCurrencyId","id"};
		
		/**
		 * 操作符
		 */
		String[] strOperators = {"like","=","=","<>"};
		/**
		 * 逻辑操作符
		 */
		String[] strLogicOperators = {"and","and","and",};
	
		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="SNAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getUserNameCodeByID(idElementIniValue);			//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"id"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		
		
		String[] strOtherElementNames={};	//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"用户名称"};//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"SNAME"}; 		//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
		
		String strWindowTitle="证券-用户放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "userInfo";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}	


	/**Magnifier0150
		 * 股东帐户放大镜
		 * @param out								jspWriter
		 * @param mainElementName					页面客户名称显示文本框名称
		 * @param hiddenElementName					客户ID隐含域的名称
		 * @param hiddenElementValue				客户ID的初始值
		 * 
		 * @param accountNameElementName			股东帐户名称元素名称
		 * 
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createStockHolderAccountCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String clientIdElementName,
				String clientNameElementName,
				
				String accountNameElementName,
				
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName,clientIdElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"CODE","CLIENTID"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={"",""};							//会影响到放大镜的初始值

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","=","="};
			String[] strLogicOperators = {"or","and","and"};
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="code";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getStockHolderAccountCodeByID(idElementIniValue);//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		
			String[] strOtherElementNames={accountNameElementName,clientIdElementName,clientNameElementName};	//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={"NAME","CLIENTID","CLIENTNAME"};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={NameRef.getStockHolderAccountNameByID(idElementIniValue),"",""};
		
			String[] strListTitleDisplayNames={"股东帐户编号","股东帐户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-股东帐户信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_VStockHolderMagnifier";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}

	/**Magnifier0151
		 * 股东帐户放大镜,受业务单位ID元素内容影响
		 * @param out								jspWriter
		 * @param mainElementName					页面客户名称显示文本框名称
		 * @param hiddenElementName					客户ID隐含域的名称
		 * @param hiddenElementValue				客户ID的初始值
		 * 
		 * @param clientIdElementName				影响放大镜的业务单位ID元素名称
		 * @param clientNameElementName				反带回的业务单位名称
		 * 
		 * @param typeId							类型码
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createStockHolderAccountCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String clientIdElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName,clientIdElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"CODE","CLIENTID"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={"",""};							//会影响到放大镜的初始值

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","=","="};
			String[] strLogicOperators = {"or","and","and"};
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getStockHolderAccountNameByID(idElementIniValue);//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"股东帐户编号","股东帐户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-股东帐户信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_VStockHolderMagnifier";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}

		
	/**Magnifier0160
		 * 股东帐户信息
		 * @param out								jspWriter
		 * @param mainElementName					页面客户名称显示文本框名称,返回股东帐户名称
		 * @param hiddenElementName					客户ID隐含域的名称
		 * @param hiddenElementValue				客户ID的初始值
		 * 
		 * 
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createStockHolderAccountCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
			
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};							//会影响到放大镜的初始值

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","="};
			String[] strLogicOperators = {"or","and"};
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getStockHolderAccountNameByID(idElementIniValue);//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"股东帐户编号","股东帐户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-股东帐户信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_StockHolderAccount";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}
		
		
	/**Magnifier0170
		 * 证交所信息
		 * @param out								jspWriter
		 * @param mainElementName					页面客户名称显示文本框名称
		 * @param hiddenElementName					客户ID隐含域的名称
		 * @param hiddenElementValue				客户ID的初始值
		 * 
		 * @param exchangeCenterElementName			股东帐户名称元素名称
		 * 
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createExchangeCenterCodeCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String exchangeCenterElementName,
				
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};							//会影响到放大镜的初始值

			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
	
			String[] strOperators = {"like","like","="};
			String[] strLogicOperators = {"or","and"};
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getExchangeCenterCodeByID(idElementIniValue);//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={exchangeCenterElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={"NAME"};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={NameRef.getExchangeCenterNameByID(idElementIniValue)};
		
			String[] strListTitleDisplayNames={"证交所编号","证交所名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-证交所信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_ExchangeCenter";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}		

		
	/**Magnifier0180
		 * 证券信息
		 * @param out								jspWriter
		 * @param displayElementName				显示文本框名称 , 证券名称
		 * @param idElementName						隐含域的名称 , 证券ID
		 * @param idElementIniValue					ID的初始值 , 证券ID
		 * 
		 * @param currencyId						币种
		 * @param transactionTypeId					交易类型代码
		 * 
		 * @param typeId							类型码,只用于债转股页面 0:可转债代码 1:转成股票代码
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long currencyId,					//币种
				long transactionTypeId,				//交易类型
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (!(typeId == 0 || typeId == 1)){
				throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
			}
			
			String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={MagnifierHelper.getSecuritiesCodeField(transactionTypeId)};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};							//会影响到放大镜的初始值


			String securitiesTypeIds = MagnifierHelper.getSecuritiesTypeIds(transactionTypeId,currencyId,typeId);										//拼出所有的类型串
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators = null; 
			String[] strLogicOperators = null;

			if (securitiesTypeIds!=null && securitiesTypeIds.length()>0){						//有确定的类型
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),securitiesTypeIds};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID","TYPEID"};
				strOperators = new String[]{"like","like","=","=","in"};
				strLogicOperators = new String[]{"or","and","and","and"};
			}
			else{												//传入错误
				throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
				/*strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID"};
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};*/
			}
			
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="SHORTNAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getSecuritiesNameByID(idElementIniValue);//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"一级代码","二级代码","证券简称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-证券信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=2;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_Securities";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}

		/**Magnifier0183  证券放大镜,受币种控制,返回证券代码,带出证券名称和期限,受交易类型影响
		 * 证券信息
		 * @param out								jspWriter
		 * @param displayElementName				显示文本框名称 , 证券代码
		 * @param idElementName						隐含域的名称 , 证券ID
		 * @param idElementIniValue					ID的初始值 , 证券ID
		 * 
		 * @param currencyId						币种
		 * @param transactionTypeId					交易类型代码
		 * 
		 * @param securitiesNameElementName			带出证券名称元素名称
		 * @param termElementName					带出期限元素名称
		 * 
		 * @param typeId							类型码,只用于债转股页面 0:可转债代码 1:转成股票代码
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long currencyId,					//币种
				long transactionTypeId,				//交易类型
				
				String securitiesNameElementName,
				String termElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (!(typeId == 0 || typeId == 1)){
				throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
			}
			
			String[] strCtrlElementNames={};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={};							//会影响到放大镜的初始值


			String securitiesTypeIds = MagnifierHelper.getSecuritiesTypeIds(transactionTypeId,currencyId,typeId);										//拼出所有的类型串
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators = null; 
			String[] strLogicOperators = null;

			if (securitiesTypeIds!=null && securitiesTypeIds.length()>0){						//有确定的类型
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),securitiesTypeIds};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID","TYPEID"};
				strOperators = new String[]{"like","=","=","in"};
				strLogicOperators = new String[]{"and","and","and"};
			}
			else{												//传入错误
				throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
				/*strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID"};
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};*/
			}
			
			String field = MagnifierHelper.getSecuritiesCodeField(transactionTypeId);
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields= field;						//显示元素对应数据库的字段名.
			String iniCode = "";
			if (field.equalsIgnoreCase("securitiesCode1")){
				iniCode = NameRef.getSecuritiesCodeByID(idElementIniValue);
			}
			else{
				iniCode = NameRef.getSecuritiesCode2ByID(idElementIniValue);
			}
			String strVisibleElementValues=iniCode;//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={securitiesNameElementName,termElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={"SHORTNAME","TERM"};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={NameRef.getSecuritiesNameByID(idElementIniValue),NameRef.getPledgeTermBySecuritiesId(idElementIniValue)};
		
			String[] strListTitleDisplayNames={"一级代码","二级代码","证券简称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-证券信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=2;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_Securities";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}	
		
		
	/**Magnifier0181
		 * 证券信息,返回证券名称,设置用
		 * @param out								jspWriter
		 * @param displayElementName				显示文本框名称 , 证券名称
		 * @param idElementName						隐含域的名称 , 证券ID
		 * @param idElementIniValue					ID的初始值 , 证券ID
		 * 
		 * @param currencyId						币种
		 * 
		 * @param typeId							类型码,输入常量中的哪种证券类型的ID获得哪种证券
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long currencyId,					//币种
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			
			String[] strCtrlElementNames={};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={};							//会影响到放大镜的初始值

			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators = null;
			String[] strLogicOperators = null;
			
			if (typeId == 0){				//查全部
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID"};
				strOperators = new String[]{"like","=","="};
				strLogicOperators = new String[]{"and","and"};
			}
			else{							//查单项
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(typeId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID","TYPEID"};
				strOperators = new String[]{"like","=","=","="};
				strLogicOperators = new String[]{"and","and","and"};
			}
			
			
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="SHORTNAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getSecuritiesNameByID(idElementIniValue);//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"一级代码","二级代码","证券简称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-证券信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=2;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_Securities";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}		

		/**Magnifier0182
		 * 证券信息,受证券类型ID元素影响
		 * @param out								jspWriter
		 * @param displayElementName				显示文本框名称 , 证券名称
		 * @param idElementName						隐含域的名称 , 证券ID
		 * @param idElementIniValue					ID的初始值 , 证券ID
		 * 
		 * @param securitiesTypeIdElementName		证券类型元素名称
		 * 
		 * @param typeId							类型码
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String securitiesTypeIdElementName,	//证券类型元素名称
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (!(typeId == 0)){
				throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
			}
			
			String[] strCtrlElementNames={securitiesTypeIdElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"TYPEID"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={NameRef.getSecuritiesTypeIDBySecuritiesID(idElementIniValue)};							//会影响到放大镜的初始值
			
			
			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};

			String[] strOperators = {"like","in","="}; 
			String[] strLogicOperators = {"and","and"};
		
			
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="SHORTNAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getSecuritiesNameByID(idElementIniValue);//显示元素的初始值?????
			
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
			
			String[] strListTitleDisplayNames={"一级代码","二级代码","证券简称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//显示名称的对应字段
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-证券信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=2;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_Securities";
			
			boolean blnShowOnly = showOnly;								//只供显示标志位
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}		
		
		/**Magnifier0184
		 * 证券信息,受证券类型ID元素影响
		 * @param out								jspWriter
		 * @param displayElementName				显示文本框名称 , 证券代码
		 * @param idElementName						隐含域的名称 , 证券ID
		 * @param idElementIniValue					ID的初始值 , 证券ID
		 * 
		 * @param securitiesTypeIdElementName		证券类型元素名称
		 * @param securitiesNameElementName			证券名称元素名称
		 * 
		 * @param typeId							类型码,传入 1,返回一级代码,2,返回二级代码,3,返回三级代码
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String securitiesTypeIdElementName,	//证券类型元素名称
				
				String securitiesNameElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (typeId<0 || typeId>3){
				throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
			}
			
			String[] strCtrlElementNames={securitiesTypeIdElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"TYPEID"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={NameRef.getSecuritiesTypeIDBySecuritiesID(idElementIniValue)};							//会影响到放大镜的初始值
			
			
			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
			
			String[] strOperators = {"like","=","="}; 
			String[] strLogicOperators = {"and","and"};
			
			String securitieCode = "SECURITIESCODE" + typeId;
			String securitieIniCode = NameRef.getSecuritiesCodeByID(idElementIniValue,securitieCode);
			
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields=securitieCode;					//显示元素对应数据库的字段名.
			String strVisibleElementValues=securitieIniCode;//显示元素的初始值?????
			
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
			String[] strOtherElementNames={securitiesNameElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={"SHORTNAME"};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={NameRef.getSecuritiesNameByID(idElementIniValue)};
			
			String[] strListTitleDisplayNames={"一级代码","二级代码","证券简称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//显示名称的对应字段
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-证券信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=2;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_Securities";
			
			boolean blnShowOnly = showOnly;								//只供显示标志位
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}			
		
		
	/**Magnifier0185
	 * 证券信息,受证券类型ID元素影响
	 * @param out								jspWriter
	 * @param displayElementName				显示文本框名称 , 证券代码
	 * @param idElementName						隐含域的名称 , 证券ID
	 * @param idElementIniValue					ID的初始值 , 证券ID
	 * 
	 * @param contractIdElementName				合同号元素名称
	 * 
	 * @param securitiesNameElementName			证券名称元素名称
	 * @param noticeWithSecuritiesElementName	带出的noticeWithSecuritiesId
	 * 
	 * @param typeId							类型码,传入 1,返回一级代码,2,返回二级代码,3,返回三级代码
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createSecuritiesCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
				
			String contractIdElementName,
				
			String securitiesNameElementName,
			String noticeWithSecuritiesElementName,
				
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
			
		if (typeId<0 || typeId>4){
			throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
		}
			
		String[] strCtrlElementNames={contractIdElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CONTRACTID"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
			
			
		String[] strCtrlValues = {String.valueOf(Constant.RecordStatus.VALID),
									String.valueOf(SECConstant.NoticeFormStatus.CHECKED),
									String.valueOf(SECConstant.BusinessType.BOND_UNDERWRITING.BOND_DRAWBACK_NOTIFY)};
		String[] strCtrlFields = {"noticeWithStatusId","noticeFormStatusId","transactionTypeId"};
			
		String[] strOperators = {"like","=","=","=","="}; 
		String[] strLogicOperators = {"and","and","and","and"};
			
		String securitieCode = "SECURITIESCODE" + typeId;
		String securitieIniCode = NameRef.getSecuritiesCodeByID(idElementIniValue,securitieCode);
			
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields=securitieCode;					//显示元素对应数据库的字段名.
		String strVisibleElementValues=securitieIniCode;//显示元素的初始值?????
			
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
		String[] strOtherElementNames={securitiesNameElementName,noticeWithSecuritiesElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"SHORTNAME","noticeWithSecId"};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={NameRef.getSecuritiesNameByID(idElementIniValue),
										NameRef.getNoticeWithSecuritiesId(idElementIniValue)};
			
		String[] strListTitleDisplayNames={"一级代码","二级代码","证券简称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//显示名称的对应字段
			
		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-证券信息放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=2;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_NoticeSecMagnifier";
			
		boolean blnShowOnly = showOnly;								//只供显示标志位
			
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
					
				strCtrlValues,
				strCtrlFields,
					
				strOperators,
				strLogicOperators,
					
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
					
				strVisibleElementType,
				strVisibleElementProperty,
					
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
					
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
					
				strListTitleDisplayNames,
				strListTitleDisplayFields,
					
				strnextFocusElementNames,
					
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}			
		
		
		
		
	/**Magnifier0190
		 * 证券信息
		 * @param out								jspWriter
		 * @param displayElementName				显示文本框名称 , 证券代码
		 * @param idElementName						隐含域的名称 , 证券ID
		 * @param idElementIniValue					ID的初始值 , 证券ID
		 * 
		 * @param currencyId						币种
		 * @param transactionTypeId					交易类型代码,按照不同的交易返回不同类型的证券代码
		 * 										如果没有交易类型代码,只知道要那种指定的证券代码类型则:
		 * 											1:SecuritiesCode1 一级代码
		 * 											2:SecuritiesCode2 二级代码
		 * 											3:SecuritiesCode3 增发代码
		 * 											4:SecuritiesCode4 其他代码
		 * @param securitiesNameElementName			证券名称
		 * 
		 * @param typeId							类型码,只用于债转股页面 0:可转债代码 1:转成股票代码
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createSecuritiesCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long currencyId,
				long transactionTypeId,				//交易类型
				
				String securitiesNameElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{

			if (!(typeId == 0 || typeId == 1)){
				throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
			}
			String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={MagnifierHelper.getSecuritiesCodeField(transactionTypeId)};	//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};							//会影响到放大镜的初始值

			String securitiesTypeIds = MagnifierHelper.getSecuritiesTypeIds(transactionTypeId,currencyId,typeId);
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators = null; 
			String[] strLogicOperators = null;
			
			if (securitiesTypeIds!=null && securitiesTypeIds.length()>0){						//有确定的类型
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),securitiesTypeIds};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID","TYPEID"};
				strOperators = new String[]{"like","like","=","=","in"};
				strLogicOperators = new String[]{"or","and","and","and"};
			}
			else{												//没有类型限制
				throw new SecuritiesException("证券放大镜,传入的typeId错误",null);
				/*strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
				strCtrlFields = new String[]{"STATUSID","CURRENCYID"};
				strOperators = new String[]{"like","like","=","="};
				strLogicOperators = new String[]{"or","and","and"};*/
			}
			
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields=MagnifierHelper.getSecuritiesCodeField(transactionTypeId);						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getSecuritiesCodeByID(idElementIniValue,MagnifierHelper.getSecuritiesCodeField(transactionTypeId));//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={securitiesNameElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={"SHORTNAME"};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={NameRef.getSecuritiesNameByID(idElementIniValue)};
		
			String[] strListTitleDisplayNames={"一级代码","二级代码","证券简称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"SECURITIESCODE1","SECURITIESCODE2","SHORTNAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-证券信息放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=2;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_Securities";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementType,
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							strTableName,
							blnShowOnly
							);
		}		

	/**
	 * 资金帐户/交易帐户放大镜Magnifier0200
	 * @param out
	 * @param displayElementName							//资金帐户Name元素名称
	 * @param idElementName									//资金帐户Id元素名称
	 * @param idElementIniValue								//资金帐户Id初始值
	 * 控制元素
	 * @param clientIdElementName							//业务单位放大镜,会影响到资金帐户放大镜内容
	 * 控制值
	 * @param currencyId									//币种
	 * 
	 * 带出值
	 * @param holderAccountCodeElementName					//放大镜带出的帐户Code元素名称,资金帐户带出股东帐号,交易帐户带出基金帐号
	 * @param holderAccountNameElementName					//放大镜带出的帐户Name元素名称,资金帐户带出股东帐户,交易帐户带出基金帐户
	 * @param clientNameElementName							//业务单位名称元素名称
	 * @param counterpartIdElementName						//开户营业部Id元素名称 		//控制值
	 * @param counterpartNameElementName					//开户营业部name元素名称
	 * 带出值
	 * 
	 * @param typeId										//类型码 0:用于资金帐户 1:用户交易帐户
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createAccountCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String clientIdElementName,
			
			long currencyId,
			
			String holderAccountCodeElementName,
			String holderAccountNameElementName,
			String clientNameElementName,
			String counterpartIdElementName,
			String counterpartNameElementName,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		
			//校验typeId
		if (!(typeId == 0 || typeId == 1)){
			throw new SecuritiesException("资金帐户放大镜传入的typeId错误",null);
		}
		
		String[] strCtrlElementNames={displayElementName,clientIdElementName,counterpartIdElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"ACCOUNTCODE","CLIENTID","COUNTERPARTID"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={"",NameRef.getClientIDByAccountID(idElementIniValue),NameRef.getCounterpartIDByAccountID(idElementIniValue)};											//会影响到放大镜的初始值
		
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
		String[] strCtrlFields = {"STATUSID","CURRENCYID"};
		
		if (typeId == 0){					//资金帐户
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(SECConstant.AccountType.SECURITIES_ACCOUNT)};
			strCtrlFields = new String[]{"STATUSID","CURRENCYID","ACCOUNTTYPEID"};
		}
		else if (typeId == 1){				//交易帐户
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(SECConstant.AccountType.OPENFUND_ACCOUNT)};
			strCtrlFields = new String[]{"STATUSID","CURRENCYID","ACCOUNTTYPEID"};
		}
		
		String[] strOperators = {"like","like","=","=","=","=","="};
		String[] strLogicOperators = {"or","and","and","and","and","and"};
		
		
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields = "ACCOUNTCODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getAccountCodeById(idElementIniValue);//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		String[] strOtherElementNames={	holderAccountCodeElementName,
										holderAccountNameElementName,
										clientIdElementName,
										clientNameElementName,
										counterpartIdElementName,
										counterpartNameElementName,
										clientIdElementName+counterpartNameElementName};	//其他和本放大镜关联的元素=;即当返回时也要返回
		
		String[] strOtherElementFields = null;
		String[] strOtherElementValues = null;
		if (typeId == 0){									//资金帐户
			strOtherElementFields=new String[]{"HOLDERACCOUNTCODE",
												"HOLDERACCOUNTNAME",
												"CLIENTID",
												"CLIENTNAME",
												"COUNTERPARTID",
												"COUNTERPARTNAME",
												"CLIENTID"};		//其他需要返回值的元素对应的数据库字段
			strOtherElementValues=new String[]{NameRef.getStockHolderAccountCodeByAccountId(idElementIniValue),
												NameRef.getStockHolderAccountNameByAccountId(idElementIniValue),
												"",
												"",
												"",
												"",
												""};
		}
		else if (typeId == 1){								//交易帐户
			strOtherElementFields=new String[]{"CODE",
												"ACCOUNTNAME",
												"CLIENTID",
												"CLIENTNAME",
												"COUNTERPARTID",
												"COUNTERPARTNAME",
												"CLIENTID"};		//其他需要返回值的元素对应的数据库字段
			strOtherElementValues=new String[]{NameRef.getCodeByAccountID(idElementIniValue),
												NameRef.getAccountNameById(idElementIniValue),
												"",
												"",
												"",
												"",
												""};
		}
		
		
		
		String[] strListTitleDisplayNames = null;
		
		if (typeId == 0){
			strListTitleDisplayNames = new String[]{"资金帐户代码","资金帐户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		}
		else if (typeId == 1){
			strListTitleDisplayNames = new String[]{"交易帐户代码","交易帐户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		}
		
		String[] strListTitleDisplayFields={"ACCOUNTCODE","ACCOUNTNAME"}; 	//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-资金帐户放大镜";					//放大镜弹出窗口标题
		
		if (typeId == 1){
			strWindowTitle="证券-交易帐户放大镜";					//放大镜弹出窗口标题
		}
		
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置

		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VAccountMagnifier";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
	/**
	 * 资金帐户/交易帐户放大镜Magnifier0201
	 * @param out
	 * @param displayElementName							//资金帐户Name元素名称
	 * @param idElementName									//资金帐户Id元素名称
	 * @param idElementIniValue								//资金帐户Id初始值
	 * 控制元素
	 * @param clientIdElementName							//业务单位放大镜,会影响到资金帐户放大镜内容
	 * 控制值
	 * @param currencyId									//币种
	 * 
	 * 带出值
	 * @param holderAccountCodeElementName					//放大镜带出的帐户Code元素名称,资金帐户带出股东帐号,交易帐户带出基金帐号
	 * @param holderAccountNameElementName					//放大镜带出的帐户Name元素名称,资金帐户带出股东帐户,交易帐户带出基金帐户
	 * @param clientNameElementName							//业务单位名称元素名称
	 * @param counterpartIdElementName						//开户营业部Id元素名称 		//控制值
	 * @param counterpartNameElementName					//开户营业部name元素名称
	 * @param counterpartCodeElementName					//开户营业部Code元素名称
	 * 带出值
	 * 
	 * @param typeId										//类型码 0:用于资金帐户 1:用户交易帐户
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createAccountCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			String clientIdElementName,
			
			long currencyId,
			
			String holderAccountCodeElementName,
			String holderAccountNameElementName,
			String clientNameElementName,
			String counterpartIdElementName,
			String counterpartNameElementName,
			String counterpartCodeElementName,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		
			//校验typeId
		if (!(typeId == 0 || typeId == 1)){
			throw new SecuritiesException("资金帐户放大镜传入的typeId错误",null);
		}
		
		String[] strCtrlElementNames={displayElementName,clientIdElementName,counterpartIdElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"ACCOUNTCODE","CLIENTID","COUNTERPARTID"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={"",NameRef.getClientIDByAccountID(idElementIniValue),NameRef.getCounterpartIDByAccountID(idElementIniValue)};											//会影响到放大镜的初始值
		
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId)};
		String[] strCtrlFields = {"STATUSID","CURRENCYID"};
		
		if (typeId == 0){					//资金帐户
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(SECConstant.AccountType.SECURITIES_ACCOUNT)};
			strCtrlFields = new String[]{"STATUSID","CURRENCYID","ACCOUNTTYPEID"};
		}
		else if (typeId == 1){				//交易帐户
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),String.valueOf(currencyId),String.valueOf(SECConstant.AccountType.OPENFUND_ACCOUNT)};
			strCtrlFields = new String[]{"STATUSID","CURRENCYID","ACCOUNTTYPEID"};
		}
		
		String[] strOperators = {"like","like","=","=","=","=","="};
		String[] strLogicOperators = {"or","and","and","and","and","and"};
		
		
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields = "ACCOUNTCODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getAccountCodeById(idElementIniValue);//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
		String[] strOtherElementNames={	holderAccountCodeElementName,
										holderAccountNameElementName,
										clientIdElementName,
										clientNameElementName,
										counterpartIdElementName,
										counterpartNameElementName,
										counterpartCodeElementName,
										clientIdElementName+counterpartCodeElementName};	//其他和本放大镜关联的元素=;即当返回时也要返回
		
		String[] strOtherElementFields = null;
		String[] strOtherElementValues = null;
		if (typeId == 0){									//资金帐户
			strOtherElementFields=new String[]{"HOLDERACCOUNTCODE",
												"HOLDERACCOUNTNAME",
												"CLIENTID",
												"CLIENTNAME",
												"COUNTERPARTID",
												"COUNTERPARTNAME",
												"COUNTERPARTCODE",
												"CLIENTID"};		//其他需要返回值的元素对应的数据库字段
			strOtherElementValues=new String[]{NameRef.getStockHolderAccountCodeByAccountId(idElementIniValue),
												NameRef.getStockHolderAccountNameByAccountId(idElementIniValue),
												"",
												"",
												"",
												"",
												"",
												""};
		}
		else if (typeId == 1){								//交易帐户
			strOtherElementFields=new String[]{"CODE",
												"ACCOUNTNAME",
												"CLIENTID",
												"CLIENTNAME",
												"COUNTERPARTID",
												"COUNTERPARTNAME",
												"COUNTERPARTCODE",
												"CLIENTID"};		//其他需要返回值的元素对应的数据库字段
			strOtherElementValues=new String[]{NameRef.getCodeByAccountID(idElementIniValue),
												NameRef.getAccountNameById(idElementIniValue),
												"",
												"",
												"",
												"",
												"",
												""};
		}
		
		
		
		String[] strListTitleDisplayNames = null;
		
		if (typeId == 0){
			strListTitleDisplayNames = new String[]{"资金帐户代码","资金帐户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		}
		else if (typeId == 1){
			strListTitleDisplayNames = new String[]{"交易帐户代码","交易帐户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
		}
		
		String[] strListTitleDisplayFields={"ACCOUNTCODE","ACCOUNTNAME"}; 	//显示名称的对应字段

		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-资金帐户放大镜";					//放大镜弹出窗口标题
		
		if (typeId == 1){
			strWindowTitle="证券-交易帐户放大镜";					//放大镜弹出窗口标题
		}
		
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置

		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VAccountMagnifier";
	
		boolean blnShowOnly = showOnly;								//只供显示标志位
				
		showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
						
						strCtrlValues,
						strCtrlFields,
						
						strOperators,
						strLogicOperators,
						
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
							
						strVisibleElementType,
						strVisibleElementProperty,
						
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
							
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
				
						strListTitleDisplayNames,
						strListTitleDisplayFields,
						
						strnextFocusElementNames,
						
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
	}
	
	
	/**Magnifier0011:证券类型放大镜
	 * @param out
	 * @param displayElementName							//证券类型名称元素名称
	 * @param idElementName									//证券类型ID元素名称
	 * @param idElementIniValue								//证券类型Id初始值
	 * 
	 * @param typeId										
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createSecuritiesTypeCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
			
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};							//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE"};							//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};								//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};
		
		/**
		 * 操作符
		 */
		String[] strOperators = {"like","like","="};
		/**
		 * 逻辑操作符
		 */
		String[] strLogicOperators = {"or","and"};
		
		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues="";			//显示元素的初始值?????
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		
		
		String[] strOtherElementNames={};	//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};
		
		String[] strListTitleDisplayNames={"证券类型代码","证券类型名称"};//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","NAME"}; 		//显示名称的对应字段
		
		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
		
		String strWindowTitle="证券-证券类型放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=1;								//放大镜页面弹出时默认停留的位置
		
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_SecuritiesType";
		
		boolean blnShowOnly = showOnly;								//只供显示标志位
		
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
				
				strCtrlValues,
				strCtrlFields,
				
				strOperators,
				strLogicOperators,
				
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
				
				strVisibleElementType,
				strVisibleElementProperty,
				
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
				
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
				
				strListTitleDisplayNames,
				strListTitleDisplayFields,
				
				strnextFocusElementNames,
				
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}
		
	/**Magnifier0018:证券类型放大镜,返回代码,带出名称
	 * @param out
	 * @param displayElementName							//证券类型代码元素名称
	 * @param idElementName									//证券类型ID元素名称
	 * @param idElementIniValue								//证券类型Id初始值
	 * 
	 * @param securitiesTypeName							//证券类型名称元素名称
	 * 
	 * @param typeId										//类型码
	 * 											0:返回代码,带出名称
	 * 											1:返回名称,带出代码		
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createSecuritiesTypeCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String securitiesTypeName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
			)throws Exception{
		
		if (typeId>1 || typeId<0){
			throw new SecuritiesException("证券类型放大镜,传入typeId错误",null);
		}
		
		String[] strCtrlElementNames = null;							//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields = null;							//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues = null;								//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
		
		if (typeId == 0){
			strCtrlElementNames = new String[]{displayElementName};							//会影响到放大镜显示得值的参数
			strCtrlElementFields = new String[]{"NAME"};							//影响到放大镜显示值得参数对应得数据库字段
			strCtrlElementValues = new String[]{""};								//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
		}
		else if (typeId ==1){
			strCtrlElementNames = new String[]{displayElementName};							//会影响到放大镜显示得值的参数
			strCtrlElementFields = new String[]{"CODE"};							//影响到放大镜显示值得参数对应得数据库字段
			strCtrlElementValues = new String[]{""};								//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
		}
		
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};
		
		/**
		 * 操作符
		 */
		String[] strOperators = {"like","like","="};
		/**
		 * 逻辑操作符
		 */
		String[] strLogicOperators = {"or","and"};
		
		String strVisibleElementNames=null;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields=null;						//显示元素对应数据库的字段名.
		String strVisibleElementValues=null;							//显示元素的初始值?????
		
		if (typeId == 0){
			strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
			strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
			strVisibleElementValues="";							//显示元素的初始值?????
		}
		else if (typeId == 1){
			strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
			strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
			strVisibleElementValues="";							//显示元素的初始值?????
		}
		
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		
		
		String[] strOtherElementNames=null;	//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields=null;//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues=null;
			
			if (typeId == 0){
				strOtherElementNames=new String[]{securitiesTypeName};	//其他和本放大镜关联的元素=;即当返回时也要返回
				strOtherElementFields=new String[]{"NAME"};//其他需要返回值的元素对应的数据库字段
				strOtherElementValues=new String[]{""};
			}
			else if (typeId == 1){
				strOtherElementNames=new String[]{securitiesTypeName};	//其他和本放大镜关联的元素=;即当返回时也要返回
				strOtherElementFields=new String[]{"CODE"};//其他需要返回值的元素对应的数据库字段
				strOtherElementValues=new String[]{""};
			}
		
		String[] strListTitleDisplayNames={"证券类型代码","证券类型名称"};//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE","NAME"}; 		//显示名称的对应字段
		
		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
		
		String strWindowTitle="证券-证券类型放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=1;								//放大镜页面弹出时默认停留的位置
		
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_SecuritiesType";
		
		boolean blnShowOnly = showOnly;								//只供显示标志位
		
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
				
				strCtrlValues,
				strCtrlFields,
				
				strOperators,
				strLogicOperators,
				
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
				
				strVisibleElementType,
				strVisibleElementProperty,
				
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
				
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
				
				strListTitleDisplayNames,
				strListTitleDisplayFields,
				
				strnextFocusElementNames,
				
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}
	
	
	/**Magnifier0012:业务类型放大镜,返回名称,带出业务属性ID
		 * @param out
		 * @param displayElementName							//业务类型名称元素名称
		 * @param idElementName									//业务类型ID元素名称
		 * @param idElementIniValue								//业务类型Id初始值
		 * 
		 * @param bussinessAttributeElementName					//带出的业务属性元素名称
		 * 
		 * @param typeId										//类型码
		 * 												0:全部
		 * 												1:去除拆入拆出授信业务
		 * 												2:只显示拆入拆出授信业务
		 * @param visibleElementProperty
		 * @param nextFocusElementNames
		 * @param showOnly
		 * @throws Exception
		 */
		public static void createBusinessTypeCtrl(
					JspWriter out,
					String displayElementName,
					String idElementName,
					long idElementIniValue,
					
					String bussinessAttributeElementName,
					
					long typeId,
					String visibleElementProperty,
					String[] nextFocusElementNames,
					boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};							//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"CODE"};							//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};								//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
		
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			/**
			 * 操作符
			 */
			String[] strOperators = null;
			/**
			 * 逻辑操作符
			 */
			String[] strLogicOperators = null;
			if (typeId == 0){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
				strCtrlFields = new String[]{"STATUSID"};
				
				strOperators = new String[]{"like","like","="};
				strLogicOperators = new String[]{"or","and"};
			}
			else if (typeId == 1){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"11","12"};
				strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
				strOperators = new String[]{"like","like","=","<>","<>"};
				strLogicOperators = new String[]{"or","and","and","and"};
			}
			else if (typeId == 2){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"11","12"};
				strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
				strOperators = new String[]{"like","like","=","=","="};
				strLogicOperators = new String[]{"and","and","and","or"};
			}
		
			String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues="";			//显示元素的初始值?????
		
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		
		
			String[] strOtherElementNames={bussinessAttributeElementName};	//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={"BUSINESSATTRIBUTEID"};//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={""};
		
			String[] strListTitleDisplayNames={"业务类型代码","业务类型名称"};//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 		//显示名称的对应字段
		
			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-业务类型放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=1;								//放大镜页面弹出时默认停留的位置
		
			int intSQL=4;												//数据查询Sql 
			String strTableName = "sec_businessType";
		
			boolean blnShowOnly = showOnly;								//只供显示标志位
		
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
				
					strCtrlValues,
					strCtrlFields,
				
					strOperators,
					strLogicOperators,
				
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
				
					strVisibleElementType,
					strVisibleElementProperty,
				
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
				
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
				
					strListTitleDisplayNames,
					strListTitleDisplayFields,
				
					strnextFocusElementNames,
				
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
		
		
	/**Magnifier0013:业务类型放大镜 ,返回代码,带出名称和业务属性
		 * @param out
		 * @param displayElementName							//业务类型代码元素名称
		 * @param idElementName									//业务类型ID元素名称
		 * @param idElementIniValue								//业务类型Id初始值
		 * 
		 * @param businessTypeNameElementName					//带出的业务类型名称元素名称
		 * @param bussinessAttributeElementName					//带出的业务属性元素名称
		 * 
		 * @param typeId										//类型码
		 * 												0:返回代码,带出名称
		 * 												1:返回名称,带出代码
		 * @param visibleElementProperty
		 * @param nextFocusElementNames
		 * @param showOnly
		 * @throws Exception
		 */
		public static void createBusinessTypeCtrl(
					JspWriter out,
					String displayElementName,
					String idElementName,
					long idElementIniValue,
					
					String businessTypeNameElementName,
					String businessAttributeElementName,
					
					long typeId,
					String visibleElementProperty,
					String[] nextFocusElementNames,
					boolean showOnly
				)throws Exception{
			
			if (typeId<0 || typeId>1){
				throw new SecuritiesException("业务类型放大镜传入typeId错误",null);
			}
			
			String[] strCtrlElementNames = null;							//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields = null;							//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues = null;								//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
			
			if (typeId == 0){
				strCtrlElementNames = new String[]{displayElementName};							//会影响到放大镜显示得值的参数
				strCtrlElementFields = new String[]{"NAME"};							//影响到放大镜显示值得参数对应得数据库字段
				strCtrlElementValues = new String[]{""};								//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
			}
			else if (typeId ==1){
				strCtrlElementNames = new String[]{displayElementName};							//会影响到放大镜显示得值的参数
				strCtrlElementFields = new String[]{"CODE"};							//影响到放大镜显示值得参数对应得数据库字段
				strCtrlElementValues = new String[]{""};								//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
			}
			
			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
		
			/**
			 * 操作符
			 */
			String[] strOperators = {"like","like","="};
			/**
			 * 逻辑操作符
			 */
			String[] strLogicOperators = {"or","and"};
		
			String strVisibleElementNames=null;			//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields=null;						//显示元素对应数据库的字段名.
			String strVisibleElementValues=null;							//显示元素的初始值?????
			
			if (typeId == 0){
				strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
				strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
				strVisibleElementValues="";							//显示元素的初始值?????
			}
			else if (typeId == 1){
				strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
				strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
				strVisibleElementValues="";							//显示元素的初始值?????
			}
			
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
		
		
		
			String[] strOtherElementNames=null;	//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields=null;//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues=null;
			
			if (typeId == 0){
				strOtherElementNames=new String[]{businessTypeNameElementName,businessAttributeElementName};	//其他和本放大镜关联的元素=;即当返回时也要返回
				strOtherElementFields=new String[]{"NAME","BUSINESSATTRIBUTEID"};//其他需要返回值的元素对应的数据库字段
				strOtherElementValues=new String[]{"",""};
			}
			else if (typeId == 1){
				strOtherElementNames=new String[]{businessTypeNameElementName,businessAttributeElementName};	//其他和本放大镜关联的元素=;即当返回时也要返回
				strOtherElementFields=new String[]{"CODE","BUSINESSATTRIBUTEID"};//其他需要返回值的元素对应的数据库字段
				strOtherElementValues=new String[]{"",""};
			}
		
			String[] strListTitleDisplayNames={"业务类型代码","业务类型名称"};//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 		//显示名称的对应字段
		
			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-业务类型放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=1;								//放大镜页面弹出时默认停留的位置
		
			int intSQL=4;												//数据查询Sql 
			String strTableName = "sec_businessType";
		
			boolean blnShowOnly = showOnly;								//只供显示标志位
		
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
				
					strCtrlValues,
					strCtrlFields,
				
					strOperators,
					strLogicOperators,
				
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
				
					strVisibleElementType,
					strVisibleElementProperty,
				
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
				
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
				
					strListTitleDisplayNames,
					strListTitleDisplayFields,
				
					strnextFocusElementNames,
				
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
	
		
	
		/**Magnifier0014:交易类型放大镜 ,返回名称,受业务类型元素约束
		 * @param out
		 * @param displayElementName							//交易类型名称元素名称
		 * @param idElementName									//交易类型ID元素名称
		 * @param idElementIniValue								//交易类型Id初始值
		 * 
		 * @param businessTypeIdElementName						//业务类型Id元素名称,约束本放大镜显示值
		 * 
		 * @param typeId										// 类型码: 
		 * 										0:全部交易类型
		 * 										1:显示去除资金拆入授信和资金拆出授信的所有交易类型
		 * 										2:只显示资金拆入授信和资金拆出授信
		 * @param visibleElementProperty
		 * @param nextFocusElementNames
		 * @param showOnly
		 * @throws Exception
		 */
		public static void createTransactionTypeCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String businessTypeIdElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (typeId>2 || typeId<0){
				throw new SecuritiesException("交易类型放大镜,传入typeId错误",null);
			}
			
			String[] strCtrlElementNames={businessTypeIdElementName};	//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"BUSINESSTYPEID"};			//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};							//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			/**
			 * 操作符
			 */
			String[] strOperators = null;
			/**
			 * 逻辑操作符
			 */
			String[] strLogicOperators = null;
			if (typeId == 0){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
				strCtrlFields = new String[]{"STATUSID"};
				
				strOperators = new String[]{"like","like","="};
				strLogicOperators = new String[]{"or","and"};
			}
			else if (typeId == 1){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1101,1201,8501,8502"};
				strCtrlFields = new String[]{"STATUSID","ID"};
				
				strOperators = new String[]{"like","like","=","not in"};
				strLogicOperators = new String[]{"or","and","and"};
			}
			else if (typeId == 2){
				strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1101","1201"};
				strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
				strOperators = new String[]{"like","like","=","=","="};
				strLogicOperators = new String[]{"and","and","and","or"};
			}
			
			
			String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues="";			//显示元素的初始值?????
			
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
			
			
			
			String[] strOtherElementNames={};	//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
			
			String[] strListTitleDisplayNames={"交易类型名称"};//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"NAME"}; 		//显示名称的对应字段
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-交易类型放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "sec_transactionType";
			
			boolean blnShowOnly = showOnly;								//只供显示标志位
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
		
	/**Magnifier0017:交易类型放大镜 ,返回名称,受业务类型元素约束,会反带出业务类型ID和业务类型名称
	 * @param out
	 * @param displayElementName							//交易类型名称元素名称
	 * @param idElementName									//交易类型ID元素名称
	 * @param idElementIniValue								//交易类型Id初始值
	 * 
	 * @param businessTypeIdElementName						//业务类型Id元素名称,约束本放大镜显示值,选了交易类型会反带出来
	 * @param businessTypeNameElementName					//业务类型名称元素名称,约束本放大镜显示值,选了交易类型会反带出来
	 * 
	 * @param typeId										// 类型码: 
	 * 										0:全部交易类型
	 * 										1:显示去除资金拆入授信和资金拆出授信的所有交易类型
	 * 										2:只显示资金拆入授信和资金拆出授信
	 * @param visibleElementProperty
	 * @param nextFocusElementNames
	 * @param showOnly
	 * @throws Exception
	 */
	public static void createTransactionTypeCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
				
			String businessTypeIdElementName,
			String businessTypeNameElementName,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
			
		if (typeId>2 || typeId<0){
			throw new SecuritiesException("交易类型放大镜,传入typeId错误",null);
		}
			
		String[] strCtrlElementNames={businessTypeIdElementName};	//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"BUSINESSTYPEID"};			//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};							//会影响到放大镜的初始值,第一个为模糊查询设置,初始值一定为空
			
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		/**
		 * 操作符
		 */
		String[] strOperators = null;
		/**
		 * 逻辑操作符
		 */
		String[] strLogicOperators = null;
		if (typeId == 0){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED)};
			strCtrlFields = new String[]{"STATUSID"};
				
			strOperators = new String[]{"like","like","="};
			strLogicOperators = new String[]{"or","and"};
		}
		else if (typeId == 1){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1101","1201"};
			strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
			strOperators = new String[]{"like","like","=","<>","<>"};
			strLogicOperators = new String[]{"or","and","and","and"};
		}
		else if (typeId == 2){
			strCtrlValues = new String[]{String.valueOf(SECConstant.SettingStatus.CHECKED),"1101","1201"};
			strCtrlFields = new String[]{"STATUSID","ID","ID"};
				
			strOperators = new String[]{"like","like","=","=","="};
			strLogicOperators = new String[]{"or","and","and","or"};
		}
			
			
		String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues="";			//显示元素的初始值?????
			
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};						//返回的默认值
			
			
			
		String[] strOtherElementNames={businessTypeIdElementName,businessTypeNameElementName};	//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={"BUSINESSTYPEID","BUSINESSTYPENAME"};//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={"",""};
			
		String[] strListTitleDisplayNames={"交易类型名称"};//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"NAME"}; 		//显示名称的对应字段
			
		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-交易类型放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_VBusiTransTypeMagnifier";
			
		boolean blnShowOnly = showOnly;								//只供显示标志位
			
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
					
				strCtrlValues,
				strCtrlFields,
					
				strOperators,
				strLogicOperators,
					
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
					
				strVisibleElementType,
				strVisibleElementProperty,
					
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
					
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
					
				strListTitleDisplayNames,
				strListTitleDisplayFields,
					
				strnextFocusElementNames,
					
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}
		
		
		/**Magnifier0081
		 * 科目放大镜
		 * @param out								jspWriter
		 * @param mainElementName					页面显示文本框名称,科目代码元素名称
		 * @param hiddenElementName					隐含域的名称,科目ID元素名称
		 * @param hiddenElementValue				科目ID的初始值,String类型,科目的代码
		 * 
		 * @param subjectNameElementName				带出科目名称元素名称
		 * 
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createSubjectCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				String codeElementIniValue,				//科目代码
				
				String subjectNameElementName,
				
				long currencyId,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"sSubjectCode"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
			
			String[] strCtrlValues = {"1",String.valueOf(currencyId)};
			String[] strCtrlFields = {"NSTATUS","NCURRENCYID"};
			
			String[] strOperators = {"like","like","=","="};
			String[] strLogicOperators = {"or","and","and"};
			
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="sSubjectCode";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=codeElementIniValue;//显示元素的初始值?????
			
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
			String subjectIdTmp = NameRef.getSubjectIDByCode(codeElementIniValue);
			long subjectId = -1;
			if (subjectIdTmp!=null && subjectIdTmp.trim().length()>0){
				subjectId = Long.parseLong(subjectIdTmp);
			}
			
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(subjectId)};	//返回的默认值
			
			String[] strOtherElementNames={subjectNameElementName};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={"sSubjectName"};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={NameRef.getSubjectNameByID(subjectId)};
			
			String[] strListTitleDisplayNames={"科目编号","科目名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"sSubjectCode","sSubjectName"}; 			//显示名称的对应字段
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-科目放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SETT_VGLSUBJECTDEFINITION";
			
			boolean blnShowOnly = showOnly;								//只供显示标志位
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
	/**Magnifier0015
	 * 业务性质放大镜
	 * @param out								jspWriter
	 * @param mainElementName					页面显示文本框名称,业务性质的名称
	 * @param idElementName						隐含域的名称,业务性质的ID
	 * @param idElementIniValue					ID的初始值
	 * 
	 * @param typeId							类型码
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createBusinessAttributeCtrl(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
		String[] strCtrlElementNames={displayElementName};			//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"CODE"};						//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={""};							//会影响到放大镜的初始值
			
		String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
		String[] strCtrlFields = {"STATUSID"};
			
		String[] strOperators = {"like","like","="};
		String[] strLogicOperators = {"or","and"};
			
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getBusinessAttributeNameById(idElementIniValue);//显示元素的初始值?????
			
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			

		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
		String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};
			
		String[] strListTitleDisplayNames={"业务性质名称"}; 		//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"NAME"}; 				//显示名称的对应字段
			
		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-业务性质放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "sec_businessAttribute";
			
		boolean blnShowOnly = showOnly;								//只供显示标志位
			
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
					
				strCtrlValues,
				strCtrlFields,
					
				strOperators,
				strLogicOperators,
					
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
					
				strVisibleElementType,
				strVisibleElementProperty,
					
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
					
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
					
				strListTitleDisplayNames,
				strListTitleDisplayFields,
					
				strnextFocusElementNames,
					
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}		
	
	/**Magnifier0016
		 * 业务类型放大镜,受业务性质元素制约
		 * @param out								jspWriter
		 * @param mainElementName					页面显示文本框名称,业务类型的名称
		 * @param idElementName						隐含域的名称,业务类型的ID
		 * @param idElementIniValue					ID的初始值
		 * 
		 * @param businessAttributeElementName		业务性质元素名称,制约放大镜内容
		 * 
		 * @param typeId							类型码
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createBusinessTypeCtrl_One(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String businessAttributeElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={displayElementName,businessAttributeElementName};			//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"CODE","BUSINESSATTRIBUTEID"};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={"",""};							//会影响到放大镜的初始值
			
			String[] strCtrlValues = {String.valueOf(SECConstant.SettingStatus.CHECKED)};
			String[] strCtrlFields = {"STATUSID"};
			
			String[] strOperators = {"like","like","=","="};
			String[] strLogicOperators = {"or","and","and"};
			
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="NAME";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getBusinessTypeByID(idElementIniValue);//显示元素的初始值?????
			
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			

			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
			
			String[] strListTitleDisplayNames={"业务类型代码","业务类型名称"}; 		//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE","NAME"}; 				//显示名称的对应字段
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-业务性质放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "sec_businesstype";
			
			boolean blnShowOnly = showOnly;								//只供显示标志位
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}		
	
		/**Magnifier0082
		 * 合同放大镜	返回合同代码
		 * @param out								jspWriter
		 * @param mainElementName					页面名称显示文本框名称,返回合同代码
		 * @param hiddenElementName					客户ID隐含域的名称,合同ID
		 * @param hiddenElementValue				合同ID的初始值
		 * 
		 * ---控制变量
		 * @param transactionTypeId					交易类型ID
		 * @param inputUserId						录入人
		 * @param nextCheckUserId					下一个审批人
		 * @param statusIdElementName				合同状态元素名称
		 * @param counterpartIdElementName			交易对手Id元素名称
		 * 
		 * @param typeId							类型码		
		 * 													0,全部 1,只显示已激活状态的合同 2,显示所有外币的已激活状态的合同(currencyId != 1)
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createApplyContractCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				String transactionTypeId,
				long inputUserId,
				long nextCheckUserId,
				String statusIdElementName,
				String couterpartIdElementName,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			
			if (typeId<0 || typeId >2){
				throw new SecuritiesException("申请合同放大镜,传入typeId错误",null);
			}
			
			String[] strCtrlElementNames={statusIdElementName,couterpartIdElementName};				//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={"STATUSID","COUNTERPARTID"};	//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={"",NameRef.getCounterpartIDByContractID(idElementIniValue)};							//会影响到放大镜的初始值
			
			String[] strCtrlValues = null;
			String[] strCtrlFields = null;
			String[] strOperators  = null;
			String[] strLogicOperators =null;
			
			if (typeId ==0){
				strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId)};
				strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID"};
				
				strOperators = new String[]{"like","in","=","in","=","="};
				strLogicOperators = new String[]{"and","and","and","and","and"};
			}
			else if (typeId == 1){
				strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId),String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE)};
				strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID","STATUSID"};
				
				strOperators = new String[]{"like","in","in","in","=","=","in"};
				strLogicOperators = new String[]{"and","and","and","and","and","and"};
			}
			else if (typeId == 2){
				strCtrlValues = new String[]{String.valueOf(transactionTypeId),
												String.valueOf(inputUserId),
												String.valueOf(nextCheckUserId),
												String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE),
												String.valueOf(Constant.CurrencyType.RMB)};
				strCtrlFields = new String[]{"TRANSACTIONTYPEID",
												"INPUTUSERID",
												"NEXTCHECKUSERID",
												"STATUSID",
												"CURRENCYID"};
				
				strOperators = new String[]{"like","in","=","=","=","=","in","<>"};
				strLogicOperators = new String[]{"and","and","and","and","and","and","and"};
			}
			
			
			
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getContractCodeByID(idElementIniValue);//显示元素的初始值?????
			
			String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
			String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
			
			String[] strListTitleDisplayNames={"合同代码"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"CODE"}; 			//显示名称的对应字段
			
			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-合同放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SEC_APPLYCONTRACT";
			
			boolean blnShowOnly = showOnly;								//只供显示标志位
			
			showZoomCtrl(out,
					strCtrlElementNames,
					strCtrlElementFields,
					strCtrlElementValues,
					
					strCtrlValues,
					strCtrlFields,
					
					strOperators,
					strLogicOperators,
					
					strVisibleElementNames,
					strVisibleElementFields,
					strVisibleElementValues,
					
					strVisibleElementType,
					strVisibleElementProperty,
					
					strHiddenElementNames,
					strHiddenElementFields,
					strHiddenElementValues,
					
					strOtherElementNames,
					strOtherElementFields,
					strOtherElementValues,
					
					strListTitleDisplayNames,
					strListTitleDisplayFields,
					
					strnextFocusElementNames,
					
					strWindowTitle,
					intListAnchorPosition,
					intSQL,
					strTableName,
					blnShowOnly
					);
		}
		

	/**Magnifier0083
			 * 合同放大镜	返回合同代码
			 * @param out								jspWriter
			 * @param mainElementName					页面名称显示文本框名称,返回合同代码
			 * @param hiddenElementName					客户ID隐含域的名称,合同ID
			 * @param hiddenElementValue				合同ID的初始值
			 * 
			 * ---控制变量
			 * @param transactionTypeId					交易类型ID
			 * @param inputUserId						录入人
			 * @param nextCheckUserId					下一个审批人
			 * @param statusIdElementName				合同状态元素名称
			 * @param counterpartIdElementName			交易对手Id元素名称
			 * @param capitalTypeElementName			资产形式元素名称,确定显示的资产形势
			 * 
			 * @param typeId							类型码		
			 * 													0,全部 1,只显示已激活状态的合同
			 * @param visibleElementProperty			主显示元素的自定义属性
			 * @param nextFocusElementNames				下一个焦点位置
			 * @param showOnly							是否仅作为显示内容(用于复核页面)
			 * @throws Exception
			 */
			public static void createApplyContractCtrl(
					JspWriter out,
					String displayElementName,
					String idElementName,
					long idElementIniValue,
				
					long transactionTypeId,
					long inputUserId,
					long nextCheckUserId,
					String statusIdElementName,
					String couterpartIdElementName,
					String capitalTypeElementName,
					
					long typeId,
					String visibleElementProperty,
					String[] nextFocusElementNames,
					boolean showOnly
					)throws Exception{
			
				if (typeId<0 || typeId >1){
					throw new SecuritiesException("申请合同放大镜,传入typeId错误",null);
				}
				
				String[] strCtrlElementNames={statusIdElementName,couterpartIdElementName,capitalTypeElementName};				//会影响到放大镜显示得值的参数
				String[] strCtrlElementFields={"STATUSID","COUNTERPARTID","INTERESTTYPEID"};	//影响到放大镜显示值得参数对应得数据库字段
				String[] strCtrlElementValues={"",NameRef.getCounterpartIDByContractID(idElementIniValue),""};							//会影响到放大镜的初始值
			
				String[] strCtrlValues = null;
				String[] strCtrlFields = null;
				String[] strOperators  = null;
				String[] strLogicOperators =null;
			
				if (typeId ==0){
					strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId)};
					strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID"};
				
					strOperators = new String[]{"like","=","=","in","=","=","="};
					strLogicOperators = new String[]{"and","and","and","and","and","and"};
				}
				else if (typeId == 1){
					strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId),String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE)};
					strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID","STATUSID"};
				
					strOperators = new String[]{"like","=","=","in","=","=","=","in"};
					strLogicOperators = new String[]{"and","and","and","and","and","and","and"};
				}
			
			
			
				String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
				String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
				String strVisibleElementValues=NameRef.getContractCodeByID(idElementIniValue);//显示元素的初始值?????
			
				String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
				String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
				String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
				String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
				String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
				String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
				String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
				String[] strOtherElementValues={};
			
				String[] strListTitleDisplayNames={"合同代码"}; 	//放大镜弹出页面的显示名称=;可以有多列
				String[] strListTitleDisplayFields={"CODE"}; 			//显示名称的对应字段
			
				String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
				String strWindowTitle="证券-合同放大镜";					//放大镜弹出窗口标题
				int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
				int intSQL=4;												//数据查询Sql 
				String strTableName = "SEC_APPLYCONTRACT";
			
				boolean blnShowOnly = showOnly;								//只供显示标志位
			
				showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
					
						strCtrlValues,
						strCtrlFields,
					
						strOperators,
						strLogicOperators,
					
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
					
						strVisibleElementType,
						strVisibleElementProperty,
					
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
					
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
					
						strListTitleDisplayNames,
						strListTitleDisplayFields,
					
						strnextFocusElementNames,
					
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
			}
			
			/**Magnifier0085
			 * 合同放大镜	返回合同代码
			 * @param out								jspWriter
			 * @param mainElementName					页面名称显示文本框名称,返回合同代码
			 * @param hiddenElementName					客户ID隐含域的名称,合同ID
			 * @param hiddenElementValue				合同ID的初始值
			 * 
			 * ---控制变量
			 * @param transactionTypeId					交易类型ID
			 * @param inputUserId						录入人
			 * @param nextCheckUserId					下一个审批人
			 * @param statusIdElementName				合同状态元素名称
			 * @param counterpartIdElementName			交易对手Id元素名称
			 * @param capitalTypeElementName			资产形式元素名称,确定显示的资产形势
			 * 
			 * @param typeId							类型码		
			 * 													0,全部 1,只显示已激活状态的合同
			 * @param visibleElementProperty			主显示元素的自定义属性
			 * @param nextFocusElementNames				下一个焦点位置
			 * @param showOnly							是否仅作为显示内容(用于复核页面)
			 * @throws Exception
			 */
			public static void createApplyContractCtrl3(
					JspWriter out,
					String displayElementName,
					String idElementName,
					long idElementIniValue,
				
					long transactionTypeId,
					long inputUserId,
					long nextCheckUserId,
					String statusIdElementName,
					String couterpartIdElementName,
					String capitalTypeElementName,
					
					long typeId,
					String visibleElementProperty,
					String[] nextFocusElementNames,
					boolean showOnly
					)throws Exception{
			
				if (typeId<0 || typeId >1){
					throw new SecuritiesException("合同业务放大镜,传入typeId错误",null);
				}
				
				String[] strCtrlElementNames={statusIdElementName,couterpartIdElementName,capitalTypeElementName};				//会影响到放大镜显示得值的参数
				String[] strCtrlElementFields={"STATUSID","COUNTERPARTID","INTERESTTYPEID"};	                                //影响到放大镜显示值得参数对应得数据库字段
				String[] strCtrlElementValues={"",NameRef.getCounterpartIDByContractID(idElementIniValue),""};					//会影响到放大镜的初始值
			
				String[] strCtrlValues = null;
				String[] strCtrlFields = null;
				String[] strOperators  = null;
				String[] strLogicOperators =null;
			
				if (typeId ==0){
					strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId)};
					strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID"};
				
					strOperators = new String[]{"like","=","=","in","=","=","="};
					strLogicOperators = new String[]{"and","and","and","and","and","and"};
				}
				else if (typeId == 1){
					strCtrlValues = new String[]{String.valueOf(transactionTypeId),String.valueOf(inputUserId),String.valueOf(nextCheckUserId),String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE+","+SECConstant.ContractStatus.EXTEND)};
					strCtrlFields = new String[]{"TRANSACTIONTYPEID","INPUTUSERID","NEXTCHECKUSERID","STATUSID"};
				
					strOperators = new String[]{"like","=","=","in","=","=","=","in"};
					strLogicOperators = new String[]{"and","and","and","and","and","and","and"};
				}
			
			
			
				String strVisibleElementNames=displayElementName;			//页面显示元素的名称 =;要产生页面元素
				String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
				String strVisibleElementValues=NameRef.getContractCodeByID(idElementIniValue);//显示元素的初始值?????
			
				String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
				String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
				String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
				String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
				String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
				String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
				String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
				String[] strOtherElementValues={};
			
				String[] strListTitleDisplayNames={"合同代码"}; 	        //放大镜弹出页面的显示名称=;可以有多列
				String[] strListTitleDisplayFields={"CODE"}; 		    	//显示名称的对应字段
			
				String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
				String strWindowTitle="证券-合同放大镜";					//放大镜弹出窗口标题
				int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
				int intSQL=4;												//数据查询Sql 
				String strTableName = "SEC_APPLYCONTRACT";
			
				boolean blnShowOnly = showOnly;								//只供显示标志位
			
				showZoomCtrl(out,
						strCtrlElementNames,
						strCtrlElementFields,
						strCtrlElementValues,
					
						strCtrlValues,
						strCtrlFields,
					
						strOperators,
						strLogicOperators,
					
						strVisibleElementNames,
						strVisibleElementFields,
						strVisibleElementValues,
					
						strVisibleElementType,
						strVisibleElementProperty,
					
						strHiddenElementNames,
						strHiddenElementFields,
						strHiddenElementValues,
					
						strOtherElementNames,
						strOtherElementFields,
						strOtherElementValues,
					
						strListTitleDisplayNames,
						strListTitleDisplayFields,
					
						strnextFocusElementNames,
					
						strWindowTitle,
						intListAnchorPosition,
						intSQL,
						strTableName,
						blnShowOnly
						);
			}
			

	/**Magnifier0084
	 * 合同放大镜	返回合同代码
	 * @param out								jspWriter
	 * @param mainElementName					页面名称显示文本框名称,返回合同代码
	 * @param hiddenElementName					客户ID隐含域的名称,合同ID
	 * @param hiddenElementValue				合同ID的初始值
	 * 
	 * ---控制变量
	 * @param transactionTypeId					交易类型ID
	 * @param inputUserId						录入人
	 * @param nextCheckUserId					下一个审批人
	 * @param statusIdElementName				合同状态元素名称
	 * @param counterpartIdElementName			交易对手Id元素名称
	 * 
	 * @param contractInterestDate				合同利息日期
	 * 
	 * @param typeId							类型码		
	 * 													0,全部 1,只显示执行中状态的合同 2,显示所有外币的已激活状态的合同(currencyId != 1)
	 * @param visibleElementProperty			主显示元素的自定义属性
	 * @param nextFocusElementNames				下一个焦点位置
	 * @param showOnly							是否仅作为显示内容(用于复核页面)
	 * @throws Exception
	 */
	public static void createApplyContractCtrl2(
			JspWriter out,
			String displayElementName,
			String idElementName,
			long idElementIniValue,
				
			String transactionTypeId,
			long inputUserId,
			long nextCheckUserId,
			String statusIdElementName,
			String couterpartIdElementName,
			
			String contractInterestDate,
			
			long typeId,
			String visibleElementProperty,
			String[] nextFocusElementNames,
			boolean showOnly
			)throws Exception{
			
		if (typeId<0 || typeId >2){
			throw new SecuritiesException("申请合同放大镜,传入typeId错误",null);
		}
			
		String[] strCtrlElementNames={statusIdElementName,couterpartIdElementName};				//会影响到放大镜显示得值的参数
		String[] strCtrlElementFields={"STATUSID","COUNTERPARTID"};	//影响到放大镜显示值得参数对应得数据库字段
		String[] strCtrlElementValues={"",NameRef.getCounterpartIDByContractID(idElementIniValue)};							//会影响到放大镜的初始值
			
		String[] strCtrlValues = null;
		String[] strCtrlFields = null;
		String[] strOperators  = null;
		String[] strLogicOperators =null;
		transactionTypeId="'"+transactionTypeId+"'";	
		if (typeId ==0){
			strCtrlValues = new String[]{String.valueOf(transactionTypeId),
										String.valueOf(inputUserId),
										String.valueOf(nextCheckUserId),
										contractInterestDate};
			strCtrlFields = new String[]{"TRANSACTIONTYPEID",
										"INPUTUSERID",
										"NEXTCHECKUSERID",
										"_CONTRACTINTERESTDATE"};
				
			strOperators = new String[]{"like","in","=","in","=","=","<"};
			strLogicOperators = new String[]{"and","and","and","and","and","and"};
		}
		else if (typeId == 1){
			strCtrlValues = new String[]{String.valueOf(transactionTypeId),
								String.valueOf(inputUserId),
					String.valueOf(nextCheckUserId),
					String.valueOf(SECConstant.ContractStatus.NOTACTIVE+","+SECConstant.ContractStatus.ACTIVE),
					contractInterestDate};
			strCtrlFields = new String[]{"TRANSACTIONTYPEID",
										"INPUTUSERID",
										"NEXTCHECKUSERID",
										"STATUSID",
										"_CONTRACTINTERESTDATE"};
				
			strOperators = new String[]{"like","in","=","in","=","=","in","<"};
			strLogicOperators = new String[]{"and","and","and","and","and","and","and"};
		}
		else if (typeId == 2){
			strCtrlValues = new String[]{String.valueOf(transactionTypeId),
											String.valueOf(inputUserId),
											String.valueOf(nextCheckUserId),
											String.valueOf(SECConstant.ContractStatus.ACTIVE),
											String.valueOf(Constant.CurrencyType.RMB),
											contractInterestDate};
			strCtrlFields = new String[]{"TRANSACTIONTYPEID",
											"INPUTUSERID",
											"NEXTCHECKUSERID",
											"STATUSID",
											"CURRENCYID",
											"_CONTRACTINTERESTDATE"};
				
			strOperators = new String[]{"like","in","=","in","=","=","=","=","<"};
			strLogicOperators = new String[]{"and","and","and","and","and","and","and","and"};
		}
			
			
			
		String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
		String strVisibleElementFields="CODE";						//显示元素对应数据库的字段名.
		String strVisibleElementValues=NameRef.getContractCodeByID(idElementIniValue);//显示元素的初始值?????
			
		String strVisibleElementType="text";						//显示元素的类型=;"text"=;"textarea"
		String strVisibleElementProperty=visibleElementProperty + " class='box'";//属性=;写入到放大镜显示文本域中
			
		String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
		String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
		String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
			
		String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
		String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
		String[] strOtherElementValues={};
			
		String[] strListTitleDisplayNames={"合同代码"}; 	//放大镜弹出页面的显示名称=;可以有多列
		String[] strListTitleDisplayFields={"CODE"}; 			//显示名称的对应字段
			
		String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
		String strWindowTitle="证券-合同放大镜";					//放大镜弹出窗口标题
		int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
		int intSQL=4;												//数据查询Sql 
		String strTableName = "SEC_APPLYCONTRACT";
			
		boolean blnShowOnly = showOnly;								//只供显示标志位
			
		showZoomCtrl(out,
				strCtrlElementNames,
				strCtrlElementFields,
				strCtrlElementValues,
					
				strCtrlValues,
				strCtrlFields,
					
				strOperators,
				strLogicOperators,
					
				strVisibleElementNames,
				strVisibleElementFields,
				strVisibleElementValues,
					
				strVisibleElementType,
				strVisibleElementProperty,
					
				strHiddenElementNames,
				strHiddenElementFields,
				strHiddenElementValues,
					
				strOtherElementNames,
				strOtherElementFields,
				strOtherElementValues,
					
				strListTitleDisplayNames,
				strListTitleDisplayFields,
					
				strnextFocusElementNames,
					
				strWindowTitle,
				intListAnchorPosition,
				intSQL,
				strTableName,
				blnShowOnly
				);
	}
			
			
	//测试帐户放大镜	
		
	/**Magnifier0010
		 * 帐户放大镜放大镜Magnifier001
		 * @param out								jspWriter
		 * @param mainElementName					页面客户名称显示文本框名称,返回业务单位名称
		 * @param hiddenElementName					客户ID隐含域的名称,业务单位ID
		 * @param hiddenElementValue				客户ID的初始值
		 * 
		 * @param typeId							类型码,因为更改,此处传入币种   **********
		 * @param visibleElementProperty			主显示元素的自定义属性
		 * @param nextFocusElementNames				下一个焦点位置
		 * @param showOnly							是否仅作为显示内容(用于复核页面)
		 * @throws Exception
		 */
		public static void createSettAccountCtrl(
				JspWriter out,
				String displayElementName,
				String idElementName,
				long idElementIniValue,
				
				long typeId,
				String visibleElementProperty,
				String[] nextFocusElementNames,
				boolean showOnly
				)throws Exception{
			String[] strCtrlElementNames={};				//会影响到放大镜显示得值的参数
			String[] strCtrlElementFields={};						//影响到放大镜显示值得参数对应得数据库字段
			String[] strCtrlElementValues={};							//会影响到放大镜的初始值
		
			
			//zpli modify 2005-09-14 
			//TODO: 证券 待确定
			String[] strCtrlValues = {	String.valueOf(SETTConstant.AccountGroupType.CURRENT),
					String.valueOf(SETTConstant.AccountCheckStatus.CHECK),
					String.valueOf(SETTConstant.AccountStatus.NORMAL+","+SETTConstant.AccountStatus.SEALUP),
					String.valueOf(typeId)};

			/*String[] strCtrlValues = {	String.valueOf(SETTConstant.AccountType.CURRENTDEPOSIT),
										String.valueOf(SETTConstant.AccountCheckStatus.CHECK),
										String.valueOf(SETTConstant.AccountStatus.NORMAL+","+SETTConstant.AccountStatus.SEALUP),
										String.valueOf(typeId)};*/
			String[] strCtrlFields = {"NACCOUNTTYPEID","NCHECKSTATUSID","NSTATUSID","nCurrencyID"};
	
			String[] strOperators = {"like","=","=","in","="};
			String[] strLogicOperators = {"and","and","and","and"};
	
			String strVisibleElementNames=displayElementName;				//页面显示元素的名称 =;要产生页面元素
			String strVisibleElementFields="SACCOUNTNO";						//显示元素对应数据库的字段名.
			String strVisibleElementValues=NameRef.getSettAccountNoById(idElementIniValue);//显示元素的初始值?????
		
			String strVisibleElementProperty=visibleElementProperty + "size='4' maxlength='4' class='box'";//属性=;写入到放大镜显示文本域中
		
			String[] strHiddenElementNames={idElementName};				//主页面隐含域里的id=;要产生页面元素
			String[] strHiddenElementFields={"ID"}; 					//id对应的字段名
			String[] strHiddenElementValues={String.valueOf(idElementIniValue)};	//返回的默认值
		
			String[] strOtherElementNames={};							//其他和本放大镜关联的元素=;即当返回时也要返回
			String[] strOtherElementFields={};							//其他需要返回值的元素对应的数据库字段
			String[] strOtherElementValues={};
		
			String[] strListTitleDisplayNames={"帐户编号","帐户名称"}; 	//放大镜弹出页面的显示名称=;可以有多列
			String[] strListTitleDisplayFields={"SACCOUNTNO","SNAME"}; 			//显示名称的对应字段

			String[] strnextFocusElementNames=nextFocusElementNames;	//下一个焦点
			
			String strWindowTitle="证券-帐户放大镜";					//放大镜弹出窗口标题
			int intListAnchorPosition=0;								//放大镜页面弹出时默认停留的位置
			
			int intSQL=4;												//数据查询Sql 
			String strTableName = "SETT_ACCOUNT";
	
			boolean blnShowOnly = showOnly;								//只供显示标志位
				
			showMultiZoomCtrl(out,
							strCtrlElementNames,
							strCtrlElementFields,
							strCtrlElementValues,
						
							strCtrlValues,
							strCtrlFields,
						
							strOperators,
							strLogicOperators,
						
							strVisibleElementNames,
							strVisibleElementFields,
							strVisibleElementValues,
							
							strVisibleElementProperty,
						
							strHiddenElementNames,
							strHiddenElementFields,
							strHiddenElementValues,
							
							strOtherElementNames,
							strOtherElementFields,
							strOtherElementValues,
				
							strListTitleDisplayNames,
							strListTitleDisplayFields,
						
							strnextFocusElementNames,
						
							strWindowTitle,
							intListAnchorPosition,
							intSQL,
							4,
							"-",
							strTableName,
							blnShowOnly
							);
		}
		
	//-----------------------------------------------------------
	
		
	/**
	 * 普通放大镜生成html主方法
	 * @param out
	 * @param strCtrlElementNames						//会影响到放大镜的页面元素名称
	 * @param strCtrlElementFields						//会影响到放大镜的页面元素对应的数据库字段
	 * @param strCtrlElementValues						//影响到放大镜页面元素的后台校验初始值
	 * 
	 * @param strCtrlValues								//会影响到放大镜的值
	 * @param strCtrlFields								//会影响到放大镜的值对应的数据库字段
	 * 
	 * String[] strOperators,							//每一个控制参数的操作符,如">","<","like","in"
	 * String[] strLogicOperators,						//两个where子句之间的逻辑运算符,只有两个"and"和"or"
	 * 
	 * @param strVisibleElementNames					//页面显示元素的名称 ,要产生页面元素
	 * @param strVisibleElementFields					//显示元素对应数据库的字段名.
	 * @param strVisibleElementValues					//显示元素的初始值
	 * 
	 * @param strVisibleElementType						//显示元素的类型,"text","textarea"
	 * @param strVisibleElementProperty 				//属性,写入到放大镜显示文本域中
	 *  
	 * @param strHiddenElementNames						//主页面隐含域里名称,要产生隐含域
	 * @param strHiddenElementFields 					//id对应的字段名
	 * @param strHiddenElementValues					//初始值
	 * 
	 * @param strOtherElementNames						//其他和本放大镜关联的元素,即当返回时也要返回
	 * @param strOtherElementFields						//其他需要返回值的元素对应的数据库字段
	 * 
	 * @param strListTitleDisplayNames 					//放大镜弹出页面的显示名称,可以有多列
	 * @param strListTitleDisplayFields 				//显示名称的对应字段
	 * 
	 * @param strNextFocusElementNames					//下一个焦点
	 * 
	 * @param strWindowTitle							//放大镜弹出窗口标题
	 * @param intListAnchorPosition						//放大镜页面弹出时默认停留的位置
	 * @param intSQL									//数据查询Sql代码,确定使用第几组sql
	 * @param strName									//表名 
	 * @param blnShowOnly								//标志位,确定当前放大镜是否只是用于显示
	 * 											(比如复核页面,全部置灰,参数不向后传递),如果是,则不会写入弹出窗口代码
	 * @throws Exception
	 */
	public static void showZoomCtrl(
			JspWriter out,
		
			String[] strCtrlElementNames,
			String[] strCtrlElementFields,
			String[] strCtrlElementValues,
			
			String[] strCtrlValues,
			String[] strCtrlFields,
			
			String[] strOperators,
			String[] strLogicOperators,
			
			String strVisibleElementNames,
			String strVisibleElementFields,
			String strVisibleElementValues,

			String strVisibleElementType,
			String strVisibleElementProperty,
			
			String[] strHiddenElementNames,
			String[] strHiddenElementFields,
			String[] strHiddenElementValues,

			String[] strOtherElementNames,
			String[] strOtherElementFields,
			String[] strOtherElementValues,
			
			String[] strListTitleDisplayNames,
			String[] strListTitleDisplayFields,

			String[] strNextFocusElementNames,
			
			String strWindowTitle,
			int intListAnchorPosition,
			
			int intSQL,
			String strName,
			boolean blnShowOnly
			)throws Exception{
        
			if (strVisibleElementNames==null || strVisibleElementNames.trim().length()==0){
		
			}
			
			/**进行转化*/
			strCtrlElementNames			= convertNullToSpace(strCtrlElementNames);
			strCtrlElementFields		= convertNullToSpace(strCtrlElementFields);
			strCtrlElementValues		= convertNullToSpace(strCtrlElementValues);
			strCtrlValues				= convertNullToSpace(strCtrlValues);
			strCtrlFields				= convertNullToSpace(strCtrlFields);
			
			strVisibleElementValues 	= convertNullToSpace(strVisibleElementValues);
			
			strVisibleElementType 		= convertNullToSpace(strVisibleElementType);
			
			strHiddenElementNames		= convertNullToSpace(strHiddenElementNames);
			strHiddenElementFields		= convertNullToSpace(strHiddenElementFields);
			strHiddenElementValues		= convertNullToSpace(strHiddenElementValues);
			
			strOtherElementNames		= convertNullToSpace(strOtherElementNames);
			strOtherElementFields		= convertNullToSpace(strOtherElementFields);
			strOtherElementValues		= convertNullToSpace(strOtherElementValues);
			
			strListTitleDisplayNames	= convertNullToSpace(strListTitleDisplayNames);
			strListTitleDisplayFields	= convertNullToSpace(strListTitleDisplayFields);
						
			strWindowTitle 				= convertNullToSpace(strWindowTitle);
			strVisibleElementProperty 	= convertNullToSpace(strVisibleElementProperty);
			
			strNextFocusElementNames	= convertNullToSpace(strNextFocusElementNames);
			/**转化结束*/
			
			/**校验有效值*/
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementFields,true)){
				throw new SecuritiesException("控制元素'名称'和'字段'不匹配!",null);
			}
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementValues,true)){
				throw new SecuritiesException("控制元素'名称'和'初始值'不匹配!",null);
			}
			if (!isElementsMatchFields(strCtrlValues,strCtrlFields,true)){
				throw new SecuritiesException("控制值'值'和'字段'不匹配!",null);
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementFields,false)){		//不允许为空
				throw new SecuritiesException("隐含元素'名称'和'字段'不匹配!",null);
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementValues,true)){
				throw new SecuritiesException("隐含元素'名称'和'初始值'不匹配!",null);
			}
			if (!isElementsMatchFields(strOtherElementNames,strOtherElementFields,true)){
				throw new SecuritiesException("接收元素'名称'和'字段'不匹配!",null);
			}
			if (!isElementsMatchFields(strOtherElementFields,strOtherElementValues,true)){
				throw new SecuritiesException("接收元素'字段'和'初始值'不匹配",null);
			}
			if (!isElementsMatchFields(strListTitleDisplayNames,strListTitleDisplayFields,false)){	//不允许为空
				throw new SecuritiesException("窗口'描述'和'字段'不匹配!",null);
			}
			
			if (strVisibleElementNames.equals("") || strVisibleElementFields.equals("")){			//不允许为空
				throw new SecuritiesException("主元素'名称'和'字段'不匹配!",null);
			}
			/**校验结束*/
			
			StringBuffer sbOutputContent = new StringBuffer(1024);				//输出内容
			StringBuffer sbJsOnkeyDown = new StringBuffer(256);					//?后面传递的参数
			StringBuffer sbJsOnFocus = new StringBuffer(128);					//当放大镜获得焦点时出发,填充校验域
        	
        	
        	
				  /**        添加可见元素名称     	 **/
			sbJsOnkeyDown.append("openWindow(new Array('" + strVisibleElementNames + "'");

			for (int n=0;n<strHiddenElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementNames[n] + "'");
			}
			for (int n=0;n<strOtherElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**        添加可见元素对应数据库字段名称     	 **/
			sbJsOnkeyDown.append("new Array('" + strVisibleElementFields + "'");
			for (int n=0;n<strHiddenElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementFields[n] + "'");
			}
			for (int n=0;n<strOtherElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");

			/**			添加控制元素的值		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
			/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
		
				/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlValues.length;n++){
				if (n==0) 
					sbJsOnkeyDown.append(strCtrlValues[n]);
				else sbJsOnkeyDown.append(",'" + strCtrlValues[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        
				/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			添加控制元素对应得运算符		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			添加控制元素之间的逻辑运算符		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strLogicOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strLogicOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strLogicOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**			添加弹出窗口显示字段的描述		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + URLEncoder.encode(strListTitleDisplayNames[n]) + "'");
				else sbJsOnkeyDown.append(",'" + URLEncoder.encode(strListTitleDisplayNames[n]) + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
				  /**			添加弹出窗口显示字段名字		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strListTitleDisplayFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strListTitleDisplayFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
			sbJsOnkeyDown.append("'"+URLEncoder.encode(strWindowTitle)+"', \n");
			sbJsOnkeyDown.append("'"+intListAnchorPosition+"', \n");
			sbJsOnkeyDown.append("'"+intSQL+"', \n");
			sbJsOnkeyDown.append("'"+strName+"', \n");
		
			/**			添加下一个焦点位置		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strNextFocusElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strNextFocusElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strNextFocusElementNames[n] + "'");
			}
			sbJsOnkeyDown.append(")) \n");

			
			if (blnShowOnly) strVisibleElementProperty +=" disabled";			//如果只做显示,那么置为disabled
			if (strVisibleElementType.toLowerCase().equals("text") || strVisibleElementType.equals("")){
				sbOutputContent.append("<input type='" + strVisibleElementType + 
				"' name='" + strVisibleElementNames + 
				"' value='" + strVisibleElementValues + 
				"' " + strVisibleElementProperty);
			
				if (!blnShowOnly){
					sbOutputContent.append(" onKeyDown=\"if(event.keyCode==13)" + sbJsOnkeyDown.toString() + " \"" );
				
					if (strHiddenElementNames!=null){
						sbOutputContent.append(" onChange=\"");
						for (int n=0;n<strHiddenElementNames.length;n++){
							sbOutputContent.append("document.all.");
							sbOutputContent.append(strHiddenElementNames[n]+".value=''; \n");
							if (n<(strHiddenElementNames.length-1)){sbOutputContent.append(",");}
						}
						for (int n=0;n<strCtrlElementNames.length;n++){				//影响放大镜的元素的校验域和初始值
							if (strCtrlElementNames[n]!=null && strCtrlElementNames[n].trim().length()>0 && !strCtrlElementNames[n].equals(strVisibleElementNames)){
								sbOutputContent.append("document.all.");
								sbOutputContent.append(strCtrlElementNames[n] + strVisibleElementNames);
								sbOutputContent.append(".value=''; \n");
							}
						}
						sbOutputContent.append("\"");
					}
				}
			
				sbOutputContent.append("> \n");
			}
			else if(strVisibleElementType.toLowerCase().equals("textarea")){
				sbOutputContent.append("<textArea name='" + strVisibleElementNames +
				"' " + strVisibleElementProperty);
			
				if (!blnShowOnly){
					sbOutputContent.append(" onKeyDown=\"if(event.keyCode==13)" + sbJsOnkeyDown.toString()+ "\"" );
				}
			
				sbOutputContent.append(">");
				sbOutputContent.append(strVisibleElementValues + "</textArea> \n");
			}
			if (!blnShowOnly){
				sbOutputContent.append("<a href='#1'><img name='imgButton'" +
				" src='/websec/image/icon.gif' border=0 " +
				" onclick=\"" + sbJsOnkeyDown.toString() +
				"\"></a> \n");
			}
			
			
			for (int n=0;n<strHiddenElementNames.length;n++){
				sbOutputContent.append("<input type='hidden' name='" + strHiddenElementNames[n] + 
				"' value='" + strHiddenElementValues[n] + "'> \n");
			}
		
			if (!blnShowOnly){
				for (int n=0;n<strCtrlElementNames.length;n++){				//影响放大镜的元素的校验域和初始值
					if (strCtrlElementNames[n]!=null && strCtrlElementNames[n].trim().length()>0 && !strCtrlElementNames[n].equals(strVisibleElementNames)){
						sbOutputContent.append("<input type='hidden' name='" + 
								(strCtrlElementNames[n] + strVisibleElementNames) + 
								"' value='" + strCtrlElementValues[n] + "'> \n");
					}
				}
			}
			if (strOtherElementNames != null && strOtherElementNames.length > 0){//影响对象的初始值赋值
				sbOutputContent.append("<script language='javascript'> \n");
				sbOutputContent.append("	setTimeout(\"");
				for (int n=0;n<strOtherElementNames.length;n++){
					if (strOtherElementNames[n]!=null && strOtherElementNames[n].trim().length()>0 
							&& strOtherElementValues[n]!=null && strOtherElementValues[n].length()>0){
						sbOutputContent.append("document.all."+strOtherElementNames[n].trim()+".value='"+strOtherElementValues[n]+"';");
					}
				}
				sbOutputContent.append("	\",100); \n");
				sbOutputContent.append("</script> \n");
			}
				
			out.println(sbOutputContent.toString());
			System.out.println("呀呀呀"+sbOutputContent.toString());
		}
	
	
	/**
	 * 多文本框放大镜生成html主方法
	 * @param out
	 * @param strCtrlElementNames						//会影响到放大镜的页面元素名称
	 * @param strCtrlElementFields						//会影响到放大镜的页面元素对应的数据库字段
	 * @param strCtrlElementValues						//影响到放大镜页面元素的后台校验初始值
	 * 
	 * @param strCtrlValues								//会影响到放大镜的值
	 * @param strCtrlFields								//会影响到放大镜的值对应的数据库字段
	 * 
	 * String[] strOperators,							//每一个控制参数的操作符,如">","<","like","in"
	 * String[] strLogicOperators,						//两个where子句之间的逻辑运算符,只有两个"and"和"or"
	 * 
	 * @param strVisibleElementNames					//页面显示元素的名称 ,要产生页面元素
	 * @param strVisibleElementFields					//显示元素对应数据库的字段名.
	 * @param strVisibleElementValues					//显示元素的初始值
	 * 
	 * @param strVisibleElementProperty 				//属性,写入到放大镜显示文本域中
	 *  
	 * @param strHiddenElementNames						//主页面隐含域里名称,要产生隐含域
	 * @param strHiddenElementFields 					//id对应的字段名
	 * @param strHiddenElementValues					//初始值
	 * 
	 * @param strOtherElementNames						//其他和本放大镜关联的元素,即当返回时也要返回
	 * @param strOtherElementFields						//其他需要返回值的元素对应的数据库字段
	 * 
	 * @param strListTitleDisplayNames 					//放大镜弹出页面的显示名称,可以有多列
	 * @param strListTitleDisplayFields 				//显示名称的对应字段
	 * 
	 * @param strNextFocusElementNames					//下一个焦点
	 * 
	 * @param strWindowTitle							//放大镜弹出窗口标题
	 * @param intListAnchorPosition						//放大镜页面弹出时默认停留的位置
	 * @param intSQL									//数据查询Sql代码,确定使用第几组sql
	 * @param textNum									//文本框数量
	 * @param delims									//分隔符
	 * @param strName									//表名 
	 * @param blnShowOnly								//标志位,确定当前放大镜是否只是用于显示
	 * 											(比如复核页面,全部置灰,参数不向后传递),如果是,则不会写入弹出窗口代码
	 * @throws Exception
	 */
	public static void showMultiZoomCtrl(
			JspWriter out,
		
			String[] strCtrlElementNames,
			String[] strCtrlElementFields,
			String[] strCtrlElementValues,
			
			String[] strCtrlValues,
			String[] strCtrlFields,
			
			String[] strOperators,
			String[] strLogicOperators,
			
			String strVisibleElementNames,
			String strVisibleElementFields,
			String strVisibleElementValues,

			String strVisibleElementProperty,
			
			String[] strHiddenElementNames,
			String[] strHiddenElementFields,
			String[] strHiddenElementValues,

			String[] strOtherElementNames,
			String[] strOtherElementFields,
			String[] strOtherElementValues,
			
			String[] strListTitleDisplayNames,
			String[] strListTitleDisplayFields,

			String[] strNextFocusElementNames,
			
			String strWindowTitle,
			int intListAnchorPosition,
			
			int intSQL,
			int textNum,
			String delims,
			String strName,
			boolean blnShowOnly
			)throws Exception{
        
			if (strVisibleElementNames==null || strVisibleElementNames.trim().length()==0){
		
			}
			
			/**进行转化*/
			strCtrlElementNames			= convertNullToSpace(strCtrlElementNames);
			strCtrlElementFields		= convertNullToSpace(strCtrlElementFields);
			strCtrlElementValues		= convertNullToSpace(strCtrlElementValues);
			strCtrlValues				= convertNullToSpace(strCtrlValues);
			strCtrlFields				= convertNullToSpace(strCtrlFields);
			
			strVisibleElementValues 	= convertNullToSpace(strVisibleElementValues);
			
			strHiddenElementNames		= convertNullToSpace(strHiddenElementNames);
			strHiddenElementFields		= convertNullToSpace(strHiddenElementFields);
			strHiddenElementValues		= convertNullToSpace(strHiddenElementValues);
			
			strOtherElementNames		= convertNullToSpace(strOtherElementNames);
			strOtherElementFields		= convertNullToSpace(strOtherElementFields);
			strOtherElementValues		= convertNullToSpace(strOtherElementValues);
			
			strListTitleDisplayNames	= convertNullToSpace(strListTitleDisplayNames);
			strListTitleDisplayFields	= convertNullToSpace(strListTitleDisplayFields);
						
			strWindowTitle 				= convertNullToSpace(strWindowTitle);
			strVisibleElementProperty 	= convertNullToSpace(strVisibleElementProperty);
			
			strNextFocusElementNames	= convertNullToSpace(strNextFocusElementNames);
			/**转化结束*/
			
			/**校验有效值*/
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementFields,true)){
				throw new SecuritiesException("控制元素'名称'和'字段'不匹配!",null);
			}
			if (!isElementsMatchFields(strCtrlElementNames,strCtrlElementValues,true)){
				throw new SecuritiesException("控制元素'名称'和'初始值'不匹配!",null);
			}
			if (!isElementsMatchFields(strCtrlValues,strCtrlFields,true)){
				throw new SecuritiesException("控制值'值'和'字段'不匹配!",null);
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementFields,false)){		//不允许为空
				throw new SecuritiesException("隐含元素'名称'和'字段'不匹配!",null);
			}
			if (!isElementsMatchFields(strHiddenElementNames,strHiddenElementValues,true)){
				throw new SecuritiesException("隐含元素'名称'和'初始值'不匹配!",null);
			}
			if (!isElementsMatchFields(strOtherElementNames,strOtherElementFields,true)){
				throw new SecuritiesException("接收元素'名称'和'字段'不匹配!",null);
			}
			if (!isElementsMatchFields(strOtherElementFields,strOtherElementValues,true)){
				throw new SecuritiesException("接收元素'字段'和'初始值'不匹配",null);
			}
			if (!isElementsMatchFields(strListTitleDisplayNames,strListTitleDisplayFields,false)){	//不允许为空
				throw new SecuritiesException("窗口'描述'和'字段'不匹配!",null);
			}
			
			if (strVisibleElementNames.equals("") || strVisibleElementFields.equals("")){			//不允许为空
				throw new SecuritiesException("主元素'名称'和'字段'不匹配!",null);
			}
			
			if (delims == null || delims.equals("")){
				throw new SecuritiesException ("请指定分隔符!",null);
			}
			/**校验结束*/
			
			StringBuffer sbOutputContent = new StringBuffer(1024);				//输出内容
			StringBuffer sbJsOnkeyDown = new StringBuffer(256);					//?后面传递的参数
			StringBuffer sbJsOnFocus = new StringBuffer(128);					//当放大镜获得焦点时出发,填充校验域
        	
        	
        	
				  /**        添加可见元素名称     	 **/
			sbJsOnkeyDown.append("openWindow(new Array('" + strVisibleElementNames + "'");

			for (int n=0;n<strHiddenElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementNames[n] + "'");
			}
			for (int n=0;n<strOtherElementNames.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**        添加可见元素对应数据库字段名称     	 **/
			sbJsOnkeyDown.append("new Array('" + strVisibleElementFields + "'");
			for (int n=0;n<strHiddenElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strHiddenElementFields[n] + "'");
			}
			for (int n=0;n<strOtherElementFields.length;n++){
				sbJsOnkeyDown.append(",'" + strOtherElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");

			/**			添加控制元素的值		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
			/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlElementFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlElementFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlElementFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
		
				/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlValues.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlValues[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlValues[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        
				/**			添加控制元素对应得字段名称		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strCtrlFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strCtrlFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strCtrlFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			添加控制元素对应得运算符		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				/**			添加控制元素之间的逻辑运算符		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strLogicOperators.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strLogicOperators[n] + "'");
				else sbJsOnkeyDown.append(",'" + strLogicOperators[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
			
				  /**			添加弹出窗口显示字段的描述		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strListTitleDisplayNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strListTitleDisplayNames[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
				  /**			添加弹出窗口显示字段名字		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strListTitleDisplayFields.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strListTitleDisplayFields[n] + "'");
				else sbJsOnkeyDown.append(",'" + strListTitleDisplayFields[n] + "'");
			}
			sbJsOnkeyDown.append("), \n");
        	
			sbJsOnkeyDown.append("'"+strWindowTitle+"', \n");
			sbJsOnkeyDown.append("'"+intListAnchorPosition+"', \n");
			sbJsOnkeyDown.append("'"+intSQL+"', \n");
			sbJsOnkeyDown.append("'"+strName+"', \n");
		
			/**			添加下一个焦点位置		**/
			sbJsOnkeyDown.append("new Array(");
			for (int n=0;n<strNextFocusElementNames.length;n++){
				if (n==0) sbJsOnkeyDown.append("'" + strNextFocusElementNames[n] + "'");
				else sbJsOnkeyDown.append(",'" + strNextFocusElementNames[n] + "'");
			}
			sbJsOnkeyDown.append(")) \n");

			
			if (blnShowOnly) strVisibleElementProperty +=" disabled";			//如果只做显示,那么置为disabled
		/**
		 * 显示多个文本框
		 */
			if (blnShowOnly) strVisibleElementProperty +=" disabled";			//如果只做显示,那么置为disabled
			for (int n=0;n<textNum;n++){
				sbOutputContent.append("<input type='text"+ 
				"' name='" + strVisibleElementNames + "Ctrl" + 					//名称分成多个文本框,名称加Ctrl后缀
				"' value='" + 
				"' " + strVisibleElementProperty);
			
				if (!blnShowOnly){
					if (n < (textNum-1)){
						sbOutputContent.append(" onFocus=\"nextfield='" + strVisibleElementNames + "Ctrl[" + (n+1) + "]'\" \n");
						sbOutputContent.append(" onKeyDown=\"handleKeyEvent('" + strVisibleElementNames + "Ctrl'," + n + ") \" \n" );
					}
					else{
						sbOutputContent.append(" onKeyDown=\"changeAccountNo('" + delims + "',document.all." + strVisibleElementNames + ",'" + strVisibleElementNames + "Ctrl');if(event.keyCode==13)" + sbJsOnkeyDown.toString() + "; \n" );
						sbOutputContent.append("handleKeyEvent('" + strVisibleElementNames + "Ctrl'," + n + ") \" \n");
					}
					
				
					if (strHiddenElementNames!=null){
						sbOutputContent.append(" onKeyUp=\"");
						for (int i=0;i<strHiddenElementNames.length;i++){
							sbOutputContent.append("document.all.");
							sbOutputContent.append(strHiddenElementNames[i]+".value=''; \n");
							if (i<(strHiddenElementNames.length-1)){sbOutputContent.append(",");}
						}
						for (int i=0;i<strCtrlElementNames.length;i++){				//影响放大镜的元素的校验域和初始值
							if (strCtrlElementNames[i]!=null && strCtrlElementNames[i].trim().length()>0 && !strCtrlElementNames[i].equals(strVisibleElementNames)){
								sbOutputContent.append("document.all.");
								sbOutputContent.append(strCtrlElementNames[i] + strVisibleElementNames);
								sbOutputContent.append(".value=''; \n");
							}
						}
					}
				}
				sbOutputContent.append("\">");
				if (n<(textNum-1)) sbOutputContent.append(delims);
			}
			sbOutputContent.append("<input type='hidden' name='" + strVisibleElementNames + "' value='" + strVisibleElementValues + "' \n" +
					"onpropertychange='splitAccount(\"" + delims + "\",document.all." + strVisibleElementNames + ",\"" + strVisibleElementNames + "Ctrl\")'> \n");
			sbOutputContent.append("<script language='javascript'> \n");			//写入帐户初始值
			sbOutputContent.append("	splitAccount(document.all." + strVisibleElementNames + ",'" + strVisibleElementNames + "Ctrl'); \n");
			sbOutputContent.append("</script> \n");
		/**
		 * 多文本框显示结束
		 */
			if (!blnShowOnly){
				sbOutputContent.append("<a href='#1'><img name='imgButton'" +
				" src='/websec/image/icon.gif' border=0 " +
				" onclick=\"changeAccountNo('" + delims + "',document.all." + strVisibleElementNames + ",'" + strVisibleElementNames + "Ctrl');" + sbJsOnkeyDown.toString() + "\"></a> \n");
			}
			
			
			for (int n=0;n<strHiddenElementNames.length;n++){
				sbOutputContent.append("<input type='hidden' name='" + strHiddenElementNames[n] + 
				"' value='" + strHiddenElementValues[n] + "'> \n");
			}
		
			if (!blnShowOnly){
				for (int n=0;n<strCtrlElementNames.length;n++){				//影响放大镜的元素的校验域和初始值
					if (strCtrlElementNames[n]!=null && strCtrlElementNames[n].trim().length()>0 && !strCtrlElementNames[n].equals(strVisibleElementNames)){
						sbOutputContent.append("<input type='hidden' name='" + 
								(strCtrlElementNames[n] + strVisibleElementNames) + 
								"' value='" + strCtrlElementValues[n] + "'> \n");
					}
				}
			}
			
			sbOutputContent.append("<script language='javascript'> \n");
			sbOutputContent.append("		splitAccount('" + delims + "',document.all." + strVisibleElementNames + ",'" + strVisibleElementNames + "Ctrl') \n");
			sbOutputContent.append("</script> \n");
			
			if (strOtherElementNames != null && strOtherElementNames.length > 0){//影响对象的初始值赋值
				sbOutputContent.append("<script language='javascript'> \n");
				sbOutputContent.append("	setTimeout(\"");
				for (int n=0;n<strOtherElementNames.length;n++){
					if (strOtherElementNames[n]!=null && strOtherElementNames[n].trim().length()>0 
							&& strOtherElementValues[n]!=null && strOtherElementValues[n].length()>0){
						sbOutputContent.append("document.all."+strOtherElementNames[n].trim()+".value='"+strOtherElementValues[n]+"';");
					}
				}
				sbOutputContent.append("	\",100); \n");
				sbOutputContent.append("</script> \n");
			}
				
			out.println(sbOutputContent.toString());
		}
	
	
	
	
	/**
	 * 转换null成""
	 * @param str
	 * @return
	 */
	private static String convertNullToSpace(String str){
		return str == null?"":str;
	}
	/**
	 * 转换字符串数组里面的null为""
	 * @param str
	 * @return
	 */
	private static String[] convertNullToSpace(String[] str){
		String[] strArray = null;
		if (str!=null){
			for (int n=0;n<str.length;n++){
				str[n] = (str[n]==null?"":str[n]);
			}
			strArray = str;
		}
		else {strArray = new String[]{};}
		return strArray;
	}
    
	/**
	 * 判定元素名称和数据库字段是否对称
	 * @param strElements
	 * @param strFields
	 * @return
	 */
	private static boolean isElementsMatchFields(String[] strElements,String[] strFields,boolean blnPermitNoMember){
		boolean blnMatch = false;
		if (!blnPermitNoMember){			//不可以为null
			if (strElements.length == strFields.length && strElements.length>0) blnMatch = true;
		}
		else{								//可以为null
			if (strElements.length == strFields.length) blnMatch = true;
		}
	    
		return blnMatch;
	}
    
	/**
	 * build magnifier SQL
	 * @param strFields
	 * @param strCtrlFields
	 * @param strOperators
	 * @param strCtrlValues
	 * @param strLogics
	 * @param strTableName
	 * @return
	 */
	public String buildSql(String[] strFields,String[] strCtrlFields,String[] strOperators,String[] strCtrlValues,String[] strLogics,String strTableName){
		final String[] STR_SPECIAL_OPERATOR = {"like","between","in","not in","is","is not"};	//special operators
		final String STR_LOGIC_OPERATOR_OR  = "or";					//
		
		StringBuffer sbWhere = new StringBuffer(128);
		StringBuffer sbSql = new StringBuffer(256);
		
		Stack sckExpression = new Stack();
		Stack sckLogic  = new Stack();
		
		//如果逻辑运算符没有传入,启用默认逻辑运算,所有全是"and"
		if (strLogics == null || strLogics.length == 0){
			strLogics = new String[strOperators.length-1];
			for (int n=0;n<strLogics.length;n++){
				strLogics[n] = "and";
			}
		}
		
		
		String[] strCtrlExpression = new String[strCtrlFields.length];
		
		strFields = this.filterSameString(strFields);
		sbSql.append("select distinct \n");
		for (int n=0;n<strFields.length;n++){
			if (n==0) {sbSql.append(strFields[n]);}
			else{sbSql.append("," + strFields[n]);}
			}
		
		sbSql.append("\n from " + strTableName);
		sbSql.append(" where 1=1 \n");
		
		if (strCtrlFields!=null && strCtrlFields.length>0){
						//Integrate each expression , build "where" expression
			for (int n=0;n<strCtrlFields.length;n++){
				String strExpressionTemp = "";
				
				int intSpecialOperatorType = -1;		//special operator sign
				
				if (strCtrlValues[n]!=null && strCtrlValues[n].length()>0 && !strCtrlValues[n].equals("-1")){
					for (int i=0;i<STR_SPECIAL_OPERATOR.length;i++){
						if (strOperators[n].trim().toLowerCase().equals(STR_SPECIAL_OPERATOR[i])){
							intSpecialOperatorType = i;
							break;
							}//end if
						}//end for i
		
					if (intSpecialOperatorType >= 0){		//current operator is special operator
						switch (intSpecialOperatorType){
							case 0:{						//current operator is "like"
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " '%" +strCtrlValues[n] + "%'";
								break;
								}//end case
							case 1:{						//current operator is "between"
								strExpressionTemp = "(" + strCtrlFields[n] + " " + strOperators[n] + " " + strCtrlValues[n] + ")";
								break;
								}//end case
							case 2:{
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " (" + strCtrlValues[n] + ")";
								break;
								}//end case
							case 3:{
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " (" + strCtrlValues[n] + ")";
								break;
								}//end case
							case 4:{
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " " + strCtrlValues[n];
								break;
								}
							case 5:{
								strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " " + strCtrlValues[n];
								break;
								}
							}//end switch
						}//end if
					else{									//current operator is normal operator
						if (strCtrlFields[n].startsWith("_")){
							strExpressionTemp = "to_char(" + strCtrlFields[n].substring(1) + ",'yyyy-mm-dd') " + strOperators[n] + "'" + strCtrlValues[n] +"'";
							}
						else{
							strExpressionTemp = strCtrlFields[n] + " " + strOperators[n] + " '" + DataFormat.reverseFormatAmount(strCtrlValues[n]) + "'";
							}
						}
					strCtrlExpression[n] = strExpressionTemp;
					}
				}//end for n
	
			sckExpression.push(strCtrlExpression[0]);		//push first expression
			for (int n=0;n<strLogics.length;n++){
				String strTemp = "";
				if (strLogics[n].toLowerCase().equals(STR_LOGIC_OPERATOR_OR)){

					String strFirstExp = (String)sckExpression.pop();
					String strSecondExp = strCtrlExpression[n+1];

					if (strFirstExp != null && strSecondExp != null){
						strTemp = "(" + (String)strFirstExp + " " + strLogics[n] + " " + strCtrlExpression[n+1] +")";
						sckExpression.push(strTemp);
						}
					else if (strFirstExp != null){
						strTemp = strFirstExp;
						sckExpression.push(strTemp);
						}
					else if (strSecondExp !=null){
						strTemp = strSecondExp;
						sckExpression.push(strTemp);
						}
					}//end if
				else{
					if (strCtrlExpression[n+1]!=null){
						sckExpression.push(strCtrlExpression[n+1]);
						}
					}
				}//end for n
			
			int intSize = sckExpression.size();
			for (int n=0;n<intSize;n++){
				String strExp = (String)sckExpression.pop();
				if (strExp !=null){
					if (sbWhere.toString() == null){
						sbWhere.append(strExp + " \n");
						}
					else{
						sbWhere.append(" and " + strExp + " \n");
						}
					}
				}
			}
		if (sbWhere.toString()!=null){
			sbSql.append(sbWhere.toString());
			}
			
		return sbSql.toString();
		}//end method buildSql
	
	/**
	 * 过滤相同得字符串,忽略大小写
	 * @param str
	 * @return
	 */
	public static String[] filterSameString(String[] str){
		String[] strRtn = null;
		Stack s = new Stack();
		
		for (int n=0;n<str.length;n++){
			boolean hasSameStr = false;
			for (int i=n+1;i<str.length;i++){
				if (str[n].equalsIgnoreCase(str[i])){
					hasSameStr = true;
					break;
					}
				}//end for i
			if (!hasSameStr){
				s.push(str[n]);
				}
			}//end for n
		strRtn = new String[s.size()];
		for (int n=0;n<strRtn.length;n++){
			strRtn[n] = (String)s.pop();
			}//end for n
		return strRtn;
		}//end method filterSameString
	 /**
	 *
	 * @param sMainFields
	 * @param sReturnFields
	 * @param sDisplayFields
	 * @param sSQL
	 * @return
	 * @throws Exception
	 */
	public static Vector getCommonSelectList(String[] sMainFields,
		String[] sDisplayFields, String sSQL)
		throws Exception
	{
		CommonSelectInfo info = null;
		PreparedStatement ps = null, ps1 = null;
		ResultSet rs = null, rs1 = null;
		Connection conn = null;
		Vector vResult = new Vector();
		try {
			conn = Database.getConnection();
			ps = conn.prepareStatement(sSQL);
			rs = ps.executeQuery();
			while (rs.next()) {
				info = new CommonSelectInfo();
				//获取放大镜查询的主字段
				Object[] oMainCols = new Object[sMainFields.length];
				for (int i = 0; i < sMainFields.length; i++) {
					oMainCols[i] = rs.getObject(sMainFields[i]);
				}

				//需要在放大镜中显示的字段
				Object[] oDisplayCols = new Object[sDisplayFields.length];
				for (int i = 0; i < sDisplayFields.length; i++) {
					oDisplayCols[i] = rs.getObject(sDisplayFields[i]); //<PK>
				}

				info.setMainCols(oMainCols);

				info.setDisplayCols(oDisplayCols);

				vResult.add(info);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("发生数据库错误！");
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception _ex) {
				System.out.println("关闭数据库连接时发生数据库错误！");
			}
		}

		return vResult.size() > 0 ? vResult : null;
	}

	public static void showZoomCtrl(JspWriter out, String strMagnifierName,
			String strFormName, String strPrefix, String[] strMainNames,
			String[] strMainFields, String[] strReturnNames,
			String[] strReturnFields, String strReturnInitValues,
			String[] strReturnValues, String[] strDisplayNames,
			String[] strDisplayFields, int nIndex, String strMainProperty,
			String strSQL, String[] strMatchValue, String strNextControls,
			String strTitle, String strFirstTD, String strSecondTD,
			boolean blnIsOptional, boolean blnIsRateCtrl) throws Exception {
		String strButtonName = "button";
		try {
			//检查放大镜参数
			//checkValue(strMainNames, strMainFields, true);
			//checkValue(strReturnNames, strReturnFields, strReturnValues, false);
			//checkValue(strDisplayNames, strDisplayFields, true);
			if (strMagnifierName == null || strFormName == null
					|| strFormName.equals("") || strSQL == null
					|| strSQL.equals("")) {
				throw new Exception();
			}
			if (strNextControls == null) {
				throw new Exception();
			}
			if (strMatchValue == null)//|| strMatchValue.equals(""))
			{
				strMatchValue = new String[1];
				strMatchValue[0] = strMainFields[0];
			} else {
				if (strMatchValue[0] == null || strMatchValue[0].equals("")) {
					strMatchValue[0] = strMainFields[0];
				}
			}

			if (strFirstTD == null) {
				strFirstTD = "";
			}
			if (strSecondTD == null) {
				strSecondTD = "";
			}

			if (strReturnInitValues == null) {
				strReturnInitValues = "";
			}

			//检查完毕
			//设置前缀
			if (strPrefix != null && !strPrefix.trim().equals("")) {
				for (int i = 0; i < strMainNames.length; i++) {
					strMainNames[i] = strPrefix + strMainNames[i];
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					strReturnNames[i] = strPrefix + strReturnNames[i];
				}
			}
			//弹出窗口的属性
			String sFeatures = null;
			if (strDisplayNames.length < 3) {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=520,height=450,scrollbars=yes";
			} else {
				sFeatures = "toolbar=no,menubar=no,resizable=no,location=no,width=624,height=540,scrollbars=yes";
			}
			//生成传递给弹出窗口的参数字符串
			String strParam = "";
			strParam = "strFormName=" + strFormName;
			strParam += "&strMagnifierName="
					+ URLEncoder.encode(strMagnifierName);
			strParam += "&nIndex=" + nIndex;

			if (!isSQL(strSQL)) {
				strParam += "&strSQL= select * from ( '+" + strSQL
						+ "+' ) where 1=1 '+get" + strMainNames[0] + "("
						+ strMainNames[0] + ".value)+'";
			} else {
				strParam += "&strSQL= select * from ( " + strSQL
						+ " ) where 1=1 '+get" + strMainNames[0] + "("
						+ strMainNames[0] + ".value)+'";
			}

			if (strNextControls != null && !strNextControls.equals("")) {
				strParam += "&strNextControls=" + strNextControls;
			}

			for (int i = 0; i < strMainNames.length; i++) {
				strParam += "&strMainNames=" + strMainNames[i];
				strParam += "&strMainFields=" + strMainFields[i];
			}

			if (strReturnNames != null) {
				boolean bValue = false;
				if (strReturnValues != null
						&& strReturnValues.length == strReturnNames.length) {
					bValue = true;
				}
				for (int i = 0; i < strReturnNames.length; i++) {
					//生成数组参数
					strParam += "&strReturnNames=" + strReturnNames[i];
					strParam += "&strReturnFields=" + strReturnFields[i];
					if (bValue) {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\" value=\""
								+ strReturnValues[i] + "\">");
					} else {
						out.println("<input type=\"hidden\" name=\""
								+ strReturnNames[i] + "\">");
					}
				}
			}

			for (int i = 0; i < strDisplayNames.length; i++) {
				//生成数组参数
				strParam += "&strDisplayNames="
						+ URLEncoder.encode(strDisplayNames[i]);
				strParam += "&strDisplayFields=" + strDisplayFields[i];
			}
			//Log.print("strParam = " + strParam);
			//生成查询按钮的事件字符串
			String strTmp = "";

			/*  TOCONFIG—TODELETE  */
			/*
			 * 产品化不再区分项目 
			 * ninh 
			 * 2005-03-24
			 */

			//	            if(Env.getProjectName().equals("cpf"))//特殊处理中油
			//	            {
			//	                strTmp = "cpfLoan";
			//	            }
			//	            else
			//	            {
			//	                strTmp = "iTreasury-loan";
			//	            }
			strTmp = "iTreasury-loan";

			/*  TOCONFIG—END  */

			String sOnKeydown = "if(" + strFormName + "." + strMainNames[0]
					+ ".disabled == false) {gnIsSelectCtrl=1;window.open('"
					+ Env.URL_PREFIX + "/" + strTmp
					+ "/magnifier/ShowMagnifierZoom.jsp?" + strParam
					+ "', 'SelectAnything', '" + sFeatures + "', false);}";
			//
			String sOnKeyUp = "";
			if (strReturnNames != null) {
				for (int i = 0; i < strReturnNames.length; i++) {
					sOnKeyUp += strReturnNames[i] + ".value = -1; ";
				}
			}

			int iPos = -1;
			//显示控件
			if (iPos == -1) {
				out
						.println("<td "
								+ strFirstTD
								+ " >"
								+ strTitle
								+ "：&nbsp;"
								+ "<img name=\""
								+ strButtonName
								+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 onclick=\""
								+ sOnKeydown + "\"></td>");
			} else {
				out
						.println("<td "
								+ strFirstTD
								+ " >"
								+ strTitle
								+ ":&nbsp;"
								+ "<img name=\""
								+ strButtonName
								+ "\" style=\"cursor: hand;\" src='/webloan/image/icon.gif' border=0 ></td>");
			}

			//blnIsOptional,是否可选项（仅对摘要、现金流向放大镜有效）
			if (blnIsOptional == true) {
				if (blnIsRateCtrl == true) {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty + ">%</td>");
				} else {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty + "></td>");
				}
			} else {
				if (blnIsRateCtrl == true) {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"tar\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\">%</td>");
				} else {
					out.println("<td " + strSecondTD
							+ " ><input type=\"text\" name=\""
							+ strMainNames[0] + "\" value=\""
							+ strReturnInitValues + "\" class=\"box\" "
							+ strMainProperty
							+ " onKeyDown=\"if(event.keyCode==13) "
							+ sOnKeydown + "\" onKeyUp=\"" + sOnKeyUp
							+ "\"></td>");
				}
			}

			out.println("<script language=\"JavaScript\">");
			out.println("function get" + strMainNames[0] + "(str)");
			out.println("{");
			out.println("   var sql = \"\"; ");
			out.println("   if (str != null && str != \"\") ");
			out.println("   {");
			if (strMatchValue == null) {
				out.println(" sql += \"\"; ");
			} else {
				if (strMatchValue.length == 1) {
					out.println(" sql +=  \" and " + strMatchValue[0]
							+ " like '" + URLEncoder.encode("%") + "\"+str+\""
							+ URLEncoder.encode("%") + "'\"; ");
				} else {
					out.println(" sql +=  \" and  ( \";");
					for (int i = 0; i < strMatchValue.length; i++) {
						if (i == 0) {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \"  " + strMatchValue[i]
										+ " like '" + URLEncoder.encode("%")
										+ "\"+str+\"" + URLEncoder.encode("%")
										+ "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						} else {
							if (strMatchValue[i] != null
									|| !strMatchValue[i].equals("")) {
								out.println(" sql +=  \" or  "
										+ strMatchValue[i] + " like '"
										+ URLEncoder.encode("%") + "\"+str+\""
										+ URLEncoder.encode("%") + "'\"; ");
							} else {
								out.println(" sql +=  \" 1=1 \"; ");
							}
						}
					}
					out.println(" sql +=  \" ) \";");
				}
				//					out.println(" sql +=  \" and " + strMatchValue + " like '" + URLEncoder.encode("%") + "\"+str+\"" + URLEncoder.encode("%") + "'\";    ");
			}
			out.println("   }");
			out.println("    ");
			out.println("   return sql;    ");
			out.println("}");
			out.println("</SCRIPT> ");
		} catch (Exception exp) {
			throw exp;
		}
	}

	private static boolean isSQL(String strSQL) {
		String strTemp = strSQL.toLowerCase();
		int nIndex = strTemp.indexOf("select ");
		if (nIndex == -1) {
			return false;
		}
		nIndex = strTemp.indexOf(" from ");
		if (nIndex == -1) {
			return false;
		}
		return true;
	}
	/**
	 * 显示证券子类型放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param strLoanTypeID 证券种类
	 * @param strFormName 主页面表单名称
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateSubLoanTypeCtrl(long lOfficeID, long lCurrencyID,
			JspWriter out, String strLoanTypeID, String strFormName,
			String strPrefix, String strMainProperty, String strNextControls)
			throws Exception {
		try {
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*===================="
					+ URLEncoder.encode("证券子类型放大镜") + "=================*/");
			out.println("function " + strPrefix
					+ "getSubLoanTypeSQL(nOfficeID,lLoanTypeID)");
			out.println("{");
			out
					.println("	var sql = \"select a.ID, a.LoanTypeID, a.Code, a.Name \";");
			out
					.println("	sql += \" from sec_secTypeSetting a, sec_secTypeRelation b \";");
			out
					.println("	sql += \" where a.ID = b.subLoanTypeID and a.statusID = 1 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and b.officeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("	if (lLoanTypeID > 0) ");
			out.println("	{");
			out.println("		sql += \" and a.loanTypeID = \" + lLoanTypeID; ");
			out.println("	}");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
			String strMagnifierName = "业务子类型";
			String[] strMainNames = { "txtSubLoanTypeName",
					"txtSubLoanTypeCode", };
			String[] strMainFields = { "Name", "Code" };
			String[] strReturnNames = { "hidSubLoanTypeID", "hidLoanTypeID" };
			String[] strReturnFields = { "ID", "LoanTypeID" };
			String[] strReturnValues = { "-1", "-1" };
			String[] strDisplayNames = { "业务子类型名称", "业务子类型编码" };
			String[] strDisplayFields = { "Name", "Code" };
			int nIndexOffice = 0;
			String strSQL = strPrefix + "getSubLoanTypeSQL(" + lOfficeID + ","
					+ strFormName + "." + strPrefix + strLoanTypeID + ".value)";

			showZoomCtrl(out, strMagnifierName, strFormName, strPrefix,
					strMainNames, strMainFields, strReturnNames,
					strReturnFields, "", strReturnValues, strDisplayNames,
					strDisplayFields, nIndexOffice, strMainProperty, strSQL,
					"", strNextControls, strMagnifierName, "", "");
		} catch (Exception exp) {
			throw exp;
		}
	}

	/**
	 * 显示普通放大镜
	 * @param JspWriter out
	 * @param String strMagnifierName 放大镜的名称
	 * @param String strFormName 主页面表单名称
	 * @param strPrefix strPrefix 控件名称前缀
	 * @param String[] strMainNames 放大镜回显栏位值列表
	 * @param String[] strMainFields 放大镜回显栏位对应的表格字段
	 * @param String[] strReturnNames 放大镜返回值列表(隐含值)
	 * @param String[] strReturnFields 放大镜返回值(隐含值)对应的表格字段列表
	 * @param String   strReturnInitValues 放大镜回显栏位对应的初始值
	 * @param String[] strReturnValues 放大镜返回值(隐含值)对应的初始值
	 * @param String[] strDisplayNames 放大镜小窗口显示的栏位名称
	 * @param String[] strDisplayFields 放大镜小窗口显示栏位对应的表格字段
	 * @param nIndex 确定选择项,从0开始,如果小于0,则默认所有栏位都可选择,如果大于或等于strDisplayNames.length,则没有选择项
	 * @param strMainProperty 放大镜栏位属性
	 * @param strSQL 放大镜查询SQL语句
	 * @param strMatchValue 放大镜要模糊匹配的字段
	 * @param strNextControls 设置下一个焦点
	 * @param strTitle 栏位标题
	 * @param strFirstTD 第一个TD的属性
	 * @param strSecondTD 第二个TD的属性 
	 * @throws Exception
	 */
	public static void showZoomCtrl(JspWriter out, String strMagnifierName,
			String strFormName, String strPrefix, String[] strMainNames,
			String[] strMainFields, String[] strReturnNames,
			String[] strReturnFields, String strReturnInitValues,
			String[] strReturnValues, String[] strDisplayNames,
			String[] strDisplayFields, int nIndex, String strMainProperty,
			String strSQL, String strMatchValue, String strNextControls,
			String strTitle, String strFirstTD, String strSecondTD)
			throws Exception {
		//2004-11-18 模糊匹配支持多字段匹配
		String[] strMatchValues = new String[1];
		strMatchValues[0] = strMatchValue;

		showZoomCtrl(out, strMagnifierName, strFormName, strPrefix,
				strMainNames, strMainFields, strReturnNames, strReturnFields,
				strReturnInitValues, strReturnValues, strDisplayNames,
				strDisplayFields, nIndex, strMainProperty, strSQL,
				strMatchValues, strNextControls, strTitle, strFirstTD,
				strSecondTD, false, false);
	}
	/**
	 * 显示审批流放大镜
	 * @param lOfficeID 办事处ID
	 * @param lCurrencyID 币种
	 * @param out
	 * @param lApprovalID 审批流ID
	 * @param strFormName 主页面表单名称
	 * @param strControlName 主页面控件名称 
	 * @param strPrefix 控件名称前缀
	 * @param strMainProperty 放大镜栏位属性
	 * @param strReturnName 放大镜隐含返回值
	 * @param strSQL 放大镜查询SQL语句
	 * @param strNextControls 设置下一个焦点
	 * @throws Exception
	 */
	public static void CreateApprovalSettingCtrl(long lOfficeID, long lCurrencyID, JspWriter out, long lApprovalID, String strFormName, String strControlName, String strPrefix, String strMainProperty, String strReturnName, String strNextControls) throws Exception
	{
		try
		{
			//输出SQL到页面
			out.println("<script language=\"javascript\">");
			out.println("/*====================" + URLEncoder.encode("显示审批流放大镜") + "=================*/");
			out.println("function " + strPrefix + "getApprovalSettingSQL(nOfficeID,lApprovalID,sname)");
			out.println("{");
			out.println("	var sql = \"select ID, sName \";");
			out.println("	sql += \" from loan_approvalSetting \";");
			out.println("	sql += \" where nStatusID = 2 \";");
			out.println(" ");
			out.println("   if (nOfficeID > 0)");
			out.println("	{");
			out.println("		sql += \" and nOfficeID = \" + nOfficeID; ");
			out.println("	}");
			out.println("   if (lApprovalID > 0)");
			out.println("	{");
			out.println("		sql += \" and ID = \" + lApprovalID; ");
			out.println("	}");
			out.println("   if (sname != null && sname != \"\")");
			out.println("   {");
			out.println("       sql += \" and sname like '%\" + sname + \"%'\"");
			out.println("   }");
			out.println("	return sql;");
			out.println("}");
			out.println("</SCRIPT> ");
			String strMagnifierName = "审批流";
			String[] strMainNames = { strControlName };
			String[] strMainFields = { "sName" };
			if(strReturnName.equals(""))
			{
				strReturnName = "hidApprovalID";
			}
			String[] strReturnNames = { strReturnName };
			String[] strReturnFields = { "ID" };
			String[] strReturnValues = { "-1" };
			String strReturnInitValues = "";
			String[] strDisplayNames = { URLEncoder.encode("审批流编号"), URLEncoder.encode("审批流名称") };
			String[] strDisplayFields = { "ID", "sName" };
			String strMatchValue = "";
			int nIndexOffice = 0;
			String name = DataFormat.toChinese(strFormName+"."+strControlName+".value");
			String strSQL = strPrefix + "getApprovalSettingSQL(" + lOfficeID + ","  + lApprovalID + "," + name + ")";
						
			SECMagnifier.showZoomCtrl(
				out,
				strMagnifierName,
				strFormName,
				strPrefix,
				strMainNames,
				strMainFields,
				strReturnNames,
				strReturnFields,
				strReturnInitValues,
				strReturnValues,
				strDisplayNames,
				strDisplayFields,
				nIndexOffice,
				strMainProperty,
				strSQL,
				strMatchValue,
				strNextControls,
				strMagnifierName,
				"",
				"");
		}
		catch (Exception exp)
		{
			throw exp;
		}
	}
}