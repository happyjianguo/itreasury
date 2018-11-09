<%--
 页面名称 ： exp_c.jsp
 页面功能 :  批量导入网银指令
 作    者 ： 郑凯
 日    期 ： 2007-04-16
 特殊说明 ：
 实现操作说明：
 修改历史 ：
--%>
<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.iss.itreasury.util.*" %>
<%@ page import="com.iss.itreasury.dataentity.*" %>
<%@ page import="com.iss.itreasury.settlement.util.NameRef" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dataentity.*" %>
<%@ page import="com.iss.itreasury.ebank.util.OBConstant" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.bizlogic.*" %>
<%@ page import="com.iss.itreasury.ebank.util.*" %>
<%@ page import="org.apache.poi.hssf.usermodel.*" %>
<%@ page import="com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao"%>
<%@ page import="org.apache.poi.poifs.filesystem.POIFSFileSystem" %>
<%@ page import="com.iss.itreasury.system.translog.dataentity.*"%>
<%@ page import="com.iss.itreasury.system.translog.bizlogic.*"%>
<%@ page import="java.util.regex.*"%>
<%@ page import="com.iss.itreasury.settlement.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="com.iss.itreasury.safety.util.*"%>
<%@ page import="com.iss.itreasury.safety.facade.factory.*"%>
<%@ page import="com.iss.itreasury.safety.facade.imp.*"%>
<%@ page import="com.iss.itreasury.safety.facade.*"%>
<jsp:useBean id="mySmartUpload" scope="page" class="com.jspsmart.upload.SmartUpload" />
<%@ page import="com.iss.itreasury.settlement.setting.bizlogic.OBCommitTimeBiz"%>
<jsp:useBean id="sessionMng" scope="session" class="com.iss.itreasury.ebank.util.SessionOB"></jsp:useBean>
<%String strContext = request.getContextPath();%>
<%!
TransInfo transinfo = new TransInfo();

    /**
     * 校验字符串中的非法字符，若含非法字符串，抛出异常，否则返回合法的字符串
     * @param inputStr
     * @return String
     * @throws IllegalBusinessDataException
     */
    public static boolean validateLimitString(String inputStr) 
    {
    	String regexp = "[0-9\\-]*";
		return validateStringByRegexp(inputStr,regexp);
	}

    /**
     * 校验字符串中的非法字符，若含非法字符串，返回true，
     * @param inputStr
     * @return boolean
     * @throws IllegalBusinessDataException
     */
    private static boolean validateStringByRegexp(String inputStr,String regexp) 
    {

		try {		
			Pattern pattern = Pattern.compile(regexp);
			Matcher matcher = null;
			matcher = pattern.matcher(inputStr);
			return matcher.matches();
			
		} catch (Exception e) {	
			//e.printStackTrace();
			return false;
		}
    }
/**
*saveUploadFileToDateBase  保存上传文档的信息到数据库
*保存各类上传文档的信息到数据库
*/
public UpLoanReturnInfo saveUploadFileToDateBase (
						com.jspsmart.upload.SmartUpload mySmartUpload,
						long lUserID,
						long lClientID,
						long lCurrencyID,
						long lOfficeID,
						double dAmount,
						long lCount,
						String upURL,
						SessionOB sessionMng,
						ServletRequest request) throws Exception
{
//	System.out.println("xiaozhiwei-=====================================");
	Vector conditionVector = new Vector ();
	String strAdd ="";//每次从上传文件中读到的一个单元。
	String strAdd1 ="";//每次从上传文件中读到的付款方名称单元付值给strAdd1，备用。
	UpLoanReturnInfo upLoanReturnInfo = null;
	OBFinanceInstrDao obfinanceInstrDao=new OBFinanceInstrDao();
	 AccountBalanceInfo accountBalanceInfo = null;
	 
	long InstructionID = -1;
	double UsableBalance=0.0; 
	int index=0;
	int j = 1;		//从第几行开始读数据(0代表第1行)
	int indexNow = 0; //当前列(内循环)
	int indexCount = 15;	//财企接口新增3个字段
	double tempAmount = 0.0;
	long tempCount = 0;
    String [][] array = new String[(int)lCount][2];
    String clientCode="";
    String  fixUsed = "" ; //定义固定用途
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	DecimalFormat r = null;
	double d1 = 0.00;
	String URL = "";
	URL = Env.UPLOAD_PATH+"ebank/upload/";
	java.io.File file = new java.io.File(URL);
	if(!file.exists())
	{
		file.mkdirs();
	}

	com.jspsmart.upload.File myFile = mySmartUpload.getFiles().getFile(0);
	
	if (!myFile.isMissing())
	{
		myFile.saveAs(URL + myFile.getFileName());
	}
	
	//Env.UPLOAD_PATH
	/* 初始化EJB */
	OBFinanceInstrHome financeInstrHome = null;
	OBFinanceInstr financeInstr = null;
	financeInstrHome = (OBFinanceInstrHome)EJBObject.getEJBHome("OBFinanceInstrHome");
	financeInstr = financeInstrHome.create();
	
	//验证信息
//	OBFinanceInstrDao financeDAO = new OBFinanceInstrDao();
//	AccountBalanceInfo abInfo = new AccountBalanceInfo();
	System.out.println("========================="+URL);
	try{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(URL + myFile.getFileName()));		
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		HSSFRow row =  null;
		HSSFCell cell = null;
		if (fs!=null)
		{			
			wb = new HSSFWorkbook(fs);			
		}
		if (wb!=null)
		{
			sheet = wb.getSheetAt(0);
		}
		if (sheet==null)
		{
			upLoanReturnInfo = new UpLoanReturnInfo ();
			upLoanReturnInfo.setPositionRow(0);
			upLoanReturnInfo.setPositionCol(0);
			upLoanReturnInfo.setReason("不能导入空的Excel文件！");
			
			return upLoanReturnInfo;
		}
        long showrow=lCount+2;
		long transType = -1;		
		
		if(j==1){			
			row = sheet.getRow(j); 
			
			
			System.out.println(row);
//			FinanceInfo financeInfo = new FinanceInfo();					
			for  (index = indexNow; index < indexCount; index++)
			{
				cell = row.getCell((short)index);
				if (cell!=null)
				{
					if(index==1)//总笔数
					{
						  if( cell.getCellType() ==HSSFCell.CELL_TYPE_FORMULA)
                    	  {						    
		                    	double d = cell.getNumericCellValue();
		                    	strAdd=DataFormat.format(d,0);                  	
						  } 
					}else if(index==10) //总金额
					{
						if( cell.getCellType() ==HSSFCell.CELL_TYPE_FORMULA)
                    	  {			
                    	  		r=new DecimalFormat();			    
		                    	double d = cell.getNumericCellValue();
		                    	r.applyPattern("#0.00");
		                    	d1 = new Double(r.format(d)).doubleValue();
								
						  } 
					}

				}				
				if (strAdd==null)
				{
					strAdd="";
				}else{
					strAdd.trim();
					System.out.println("============="+strAdd);
				}	
				
								
				switch (index)
				{
				case 1 ://汇款总笔数
					if(!strAdd.trim().equals(""))
					{
						if(!String.valueOf(lCount).equals(strAdd))
						{
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("总笔数不对");							
							return upLoanReturnInfo;						
						}
					}
					strAdd="";	
					break;
				case 10: //汇款总金额
					if(d1!=0.0)
					{

						if(d1!=dAmount)
						{
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(j);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("总金额不对");							
							return upLoanReturnInfo;
						}
					}
					else
					{
						upLoanReturnInfo = new UpLoanReturnInfo ();
						upLoanReturnInfo.setPositionRow(j);
						upLoanReturnInfo.setPositionCol(index);
						upLoanReturnInfo.setReason("总金额不能为0");							
						return upLoanReturnInfo;
					}
					break;
				default:
					break;
				}
			}
			
			j++;
		
		}				
		for (int i=j;i<showrow;i++)
		{
			//解决BUG #14161::（78:8081站点）网银批量付款上传问题 qushuang 2012-08-24
			//付款方账户号
			long strPayerAcctID =0;
			//收款方账户号
			long strPayeeAcctID=0;
			//记录当前收款方账户号列Index
			int payeeAcctIndex =0;
			//解决BUG #14161 end
			row = sheet.getRow(i); 
			System.out.println(row);
			FinanceInfo financeInfo = new FinanceInfo();
			//0-付款方账号,1-汇款方式,2-收款方账号,3-收款方名称，4-金额,5-汇款用途
			System.out.print("11111111111111111111111111111111111111111=======jjjjj======="+i);
			for  (index = indexNow; index < indexCount; index++)
			{
				cell = row.getCell((short)index);
				if (cell!=null)
				{
					//在Excel中的类型是文本、常规
					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
					{
						strAdd=cell.getStringCellValue();
					}
					// 在Excel中的类型是数值
					else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
					{
						double d = cell.getNumericCellValue();
						strAdd=DataFormat.format(d,2);
						
					}
					//在Excel中的类型是公式
                    else if( cell.getCellType() ==HSSFCell.CELL_TYPE_FORMULA)
                    {						    
                    	
                    	strAdd=cell.getStringCellValue().toString();						
					}    
                    
				}				
				if (strAdd==null)
				{
					strAdd="";
				}else{
					strAdd.trim();
				}		
										
				long payeeID = -1;		//查找外部账户资料ID
				System.out.println("strAdd =================="+strAdd);
				switch (index)
				{
					case 0 ://付款方账户ID
						if(!strAdd.trim().equals("") && NameRef.getAccountIdByNo(NameRef.setAccontLine(strAdd.trim())) > 0)
						{    
							clientCode=	strAdd.trim();		
							System.out.println("=====================clientCode================="+clientCode);
							if (!obfinanceInstrDao.checkAccountIsOwnedByUser(NameRef.getAccountIdByNo(NameRef.setAccontLine(strAdd.trim())),lUserID,lCurrencyID))
							{
								upLoanReturnInfo = new UpLoanReturnInfo ();
								upLoanReturnInfo.setPositionRow(i);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setReason("您不具有操作该付款方账户的权限");
								return upLoanReturnInfo;
								
							}														
							financeInfo.setPayerAcctID(NameRef.getAccountIdByNo(NameRef.setAccontLine(strAdd.trim())));
							financeInfo.setPayerAcctNo(strAdd.trim());
							financeInfo.setPayerName(NameRef.getAccountNameByID(financeInfo.getPayerAcctID()));
							//解决BUG #14161::（78:8081站点）网银批量付款上传问题 qushuang 2012-08-24
							strPayerAcctID = NameRef.getAccountIdByNo(NameRef.setAccontLine(strAdd.trim()));
							//解决BUG #14161 end
						}
						else
						{
							
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("付款方账户不存在");
							
							return upLoanReturnInfo;
							
						}
					break;
					case 1 ://汇款方式
						if(strAdd.trim().equals("银行汇款"))
						{
							financeInfo.setRemitType(OBConstant.SettRemitType.BANKPAY);
							financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
							//System.out.println("??????????????????????????????"+financeInfo.getRemitType());
							//System.out.println("??????????????????????????????"+financeInfo.getTransType());
							transType = OBConstant.SettRemitType.BANKPAY;
							//System.out.println("??????????????????????????????"+transType);
						}
						else if(strAdd.trim().equals("内部转账"))
						{
							financeInfo.setRemitType(OBConstant.SettRemitType.INTERNALVIREMENT);
							financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
							transType = OBConstant.SettRemitType.INTERNALVIREMENT;
						}
					   
					break;
					case 2 ://收款方名称，为华联新加
					   if(!strAdd.trim().equals("")){					    
					     String lastCode=strAdd.trim().substring(strAdd.trim().length()-1);					   
					     long lReturn =OBConstant.Arry.getSameCode(lastCode);					     
					     if(lReturn > 0) {
					    	 strAdd1=strAdd.trim().substring(0,strAdd.trim().length());
					    	 financeInfo.setPayeeName(strAdd1);
					     }else{
					    	 financeInfo.setPayeeName(strAdd.trim());
							  strAdd1=strAdd;	 
					     }					   					  
					   System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~setPayeeName~~===="+strAdd1.trim());
					   }else{							
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("收款方名称不能为空");							
							return upLoanReturnInfo;
						}
					    break;
					case 3 ://收款方账户ID
						if(!strAdd.trim().equals(""))
						{	
							//收款方账户编号只能是数字和字符
							if(!validateLimitString(strAdd))
							{
								upLoanReturnInfo = new UpLoanReturnInfo ();
							    upLoanReturnInfo.setPositionRow(i);
							    upLoanReturnInfo.setPositionCol(index);
							    int m=i+1;
							    upLoanReturnInfo.setReason("第"+m+"行收款方账户编号不合法请修改");
							
							    return upLoanReturnInfo;
							}
							//						
							strAdd=strAdd.replaceAll(","," ").trim();
							
							//added by mzh_fu 2008/05/30 解决账号逗号问题
							//modify by xwhe 2008-10-14 导入模板收款方账号列单元格属性修改成文本，处理农行账号。
							//strAdd=DataFormat.format(Double.parseDouble(strAdd),0);
							
							System.out.println("========================"+strAdd);
							if(transType == OBConstant.SettRemitType.BANKPAY)			//银行汇款
							{
						     	con = Database.getConnection();
								StringBuffer sql = new StringBuffer();								
								sql.append("select id from ob_payeeinfo where sPayeeAcctno = '"+ strAdd.trim()+ "'and spayeename='"+ strAdd1.trim()+ "' and nStatusID = 1");
								System.out.println("*******************sqlsqlsql***********"+sql);
								 financeInfo.setPayeeAcctNo(strAdd.trim());
								 //解决BUG #14161::（78:8081站点）网银批量付款上传问题 qushuang 2012-08-24
								 strPayeeAcctID = NameRef.getAccountIdByNo(NameRef.setAccontLine(strAdd.trim()));
								 payeeAcctIndex = index;
								 //解决BUG #14161 end
								ps = con.prepareStatement(sql.toString());
								rs = ps.executeQuery();								
								if(rs != null && rs.next())
								{
									payeeID = rs.getLong("id");
								}
								if(payeeID > 0)
								{
									financeInfo.setPayeeAcctID(payeeID);
									System.out.println("-------收款方账户ID---------"+financeInfo.getPayeeAcctID());
								}
								if(con != null)
								{
									con.close();
									con = null;
								}
								if(ps != null)
								{
									ps.close();
									ps = null;
								}
								if(rs != null)
								{
									rs.close();
									rs = null;
								}
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)		//内部转账
							{
							    if(clientCode.equals(strAdd.trim())) throw new IException("第"+(showrow-2)+"条记录错误：付款方帐号不能与收款方帐号相同");
								
								con = Database.getConnection();
								StringBuffer sql = new StringBuffer();
								
								sql.append("select id from sett_account t where  t.saccountno = '"+ strAdd.trim() + "'and t.sname='"+ strAdd1.trim()+ "' and t.nstatusid = 1");
								System.out.println("*******************sqlsqlsql22222222222222222222***********"+sql);
								financeInfo.setPayeeAcctNo(strAdd.trim());
								System.out.println("========内部转账========="+financeInfo.getPayeeAcctNo());
								ps = con.prepareStatement(sql.toString());
								rs = ps.executeQuery();
								
								if(rs != null && rs.next())
								{
									payeeID = rs.getLong("id");
								}								
								if(con != null)
								{
									con.close();
									con = null;
								}
								if(ps != null)
								{
									ps.close();
									ps = null;
								}
								if(rs != null)
								{
									rs.close();
									rs = null;
								}  
								
								if(payeeID > 0)
								{
									financeInfo.setPayeeAcctID(payeeID);
									System.out.println("-------收款方账户ID---------"+financeInfo.getPayeeAcctID());
								}
								else{
								upLoanReturnInfo = new UpLoanReturnInfo ();
							    upLoanReturnInfo.setPositionRow(i);
							    upLoanReturnInfo.setPositionCol(index);
							    int m=i+1;
							    upLoanReturnInfo.setReason("第"+m+"行收款方账户不存在");
							
							    return upLoanReturnInfo;
								} 
							}
						}
						else
						{
							System.out.println("===================strAdd===="+strAdd);
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("收款方账户为空");
							
							return upLoanReturnInfo;
						}
					break;					
					case 4://汇入省
					if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)	//内部转账
					{
						financeInfo.setPayeeProv(strAdd.trim());	
					}
					else{
						if(!strAdd.trim().equals(""))
						{
						financeInfo.setPayeeProv(strAdd.trim());
							
						}
						else
						{
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("缺少汇入省");
							return upLoanReturnInfo;
						}	
						
					}
					
					break;
					case 5://汇入市
						if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)	//内部转账
						{
							financeInfo.setPayeeCity(strAdd.trim());	
						}
						else{
					if(!strAdd.trim().equals(""))
						{
							financeInfo.setPayeeCity(strAdd);
						}
						else
						{
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("缺少汇入市");
							
							return upLoanReturnInfo;
						}
						}
					break;					
					case 6://汇入行名称
						if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)	//内部转账
						{
							financeInfo.setPayeeBankName(strAdd.trim());	
						}
						else{
					if(!strAdd.trim().equals(""))
						{
							financeInfo.setPayeeBankName(strAdd);
						}
						else
						{
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("缺少汇入行名称");
							
							return upLoanReturnInfo;
						}
						}
					break;					
					case 7:  //汇入行CNAPS号
						if(transType == OBConstant.SettRemitType.BANKPAY)
						{
							financeInfo.setSPayeeBankCNAPSNO(strAdd);
						}
						break;
					case 8:  //汇入行机构号
						if(transType == OBConstant.SettRemitType.BANKPAY)
						{
							financeInfo.setSPayeeBankOrgNO(strAdd);
						}
						break;
					case 9:  //汇入行联行号
						if(transType == OBConstant.SettRemitType.BANKPAY)
						{
							financeInfo.setSPayeeBankExchangeNO(strAdd);
						}
						break;
					case 10 ://金额
						if(!strAdd.trim().equals(""))
						{
							financeInfo.setAmount(Double.valueOf(DataFormat.reverseFormatAmount(strAdd)).doubleValue());																			
							if(i==2){
								array[0][0]=clientCode;
								array[0][1]=strAdd.trim();
							}
							else{		
								double codeAmout =0.00;
								for(int arri=(int)lCount-1;arri>=0;arri--){
									
									if(array [arri][0]!=null){
										int test = 0;
										for(int k=0;k<arri+1;k++){											
											if(array [k][0].equals(clientCode)){
											   codeAmout=DataFormat.formatDouble(Double.valueOf(DataFormat.reverseFormatAmount(array [k][1])).doubleValue());
											   codeAmout+=DataFormat.formatDouble(Double.valueOf(DataFormat.reverseFormatAmount(strAdd)).doubleValue());
											   array [k][1]=String.valueOf(codeAmout);
											   test++;
											}											
										}
										
										if(test == 0) {												
											array [arri+1][0]=clientCode.toString();
											array [arri+1][1]=strAdd.trim().toString();
										}
										break;
									}		
									
								}
								
								
							}													
						}
						else
						{
							upLoanReturnInfo = new UpLoanReturnInfo ();
							upLoanReturnInfo.setPositionRow(i);
							upLoanReturnInfo.setPositionCol(index);
							upLoanReturnInfo.setReason("缺少金额");
							
							return upLoanReturnInfo;
						}
					break;

					case 11 ://是否同行
						if(!strAdd.trim().equals(""))
						{
							if(strAdd.trim().equals("是")){
						     financeInfo.setIsSameBank(OBConstant.IsSameBank.ISYES);	
							}else if(strAdd.trim().equals("否")){
								financeInfo.setIsSameBank(OBConstant.IsSameBank.ISNO);
							}
							
						}
						//modify by xwhe 2008-07-23 国电不需要此校验
						//else
						//{
						//	upLoanReturnInfo = new UpLoanReturnInfo ();
						//	upLoanReturnInfo.setPositionRow(i);
						//	upLoanReturnInfo.setPositionCol(index);
						//	upLoanReturnInfo.setReason("缺少是否同行类别");
							
						//	return upLoanReturnInfo;
						//} 
				    break;	

/***************************** Boxu Modify 2010-12-02 修改为汇款区域"本地/异地" 模板已更新 *****************************/
//					case 9 :// 是否同城
//						if(!strAdd.trim().equals(""))
//						{
//							if(strAdd.trim().equals("是")){
//							     financeInfo.setIsDiffLocal(OBConstant.IsDiffLocal.ISYES);	
//								}else if(strAdd.trim().equals("否")){
//									financeInfo.setIsDiffLocal(OBConstant.IsDiffLocal.ISNO);
//								}
//						}
//						//modify by xwhe 2008-07-23 国电不需要此校验
//						//else
//						//{
//						//	upLoanReturnInfo = new UpLoanReturnInfo ();
//						//	upLoanReturnInfo.setPositionRow(i);
//						//	upLoanReturnInfo.setPositionCol(index);
//						//	upLoanReturnInfo.setReason("缺少是否异地类别");
//						//	
//						//	return upLoanReturnInfo;
//						//}
//						  
//				    break;	

				    case 12 :// 汇款区域
						if(!strAdd.trim().equals(""))
						{
							if(strAdd.trim().equals("本地")){
							     financeInfo.setIsDiffLocal(Constant.remitAreaType.NATIVE);	
								}else if(strAdd.trim().equals("异地")){
									financeInfo.setRemitArea(Constant.remitAreaType.DEVIATIONISM);
								}
						}
						
				    break;	
				    case 13 :// 汇款速度
						if(!strAdd.trim().equals(""))
						{
							if(strAdd.trim().equals("普通")){
							     financeInfo.setIsDiffLocal(Constant.remitSpeedType.GENERAL);	
								}else if(strAdd.trim().equals("加急")){
									financeInfo.setRemitSpeed(Constant.remitSpeedType.RAPID);
								}
						}
						
				    break;	
					case 14 :// 汇款固定用途/摘要
						
							   if(strAdd.trim().getBytes().length>12){
								upLoanReturnInfo = new UpLoanReturnInfo ();
								upLoanReturnInfo.setPositionRow(i);
								upLoanReturnInfo.setPositionCol(index);
								upLoanReturnInfo.setReason("备注内容不能超过6个汉字");							
								return upLoanReturnInfo;								
							   }
							   else if(
							   strAdd.trim().equals("")){						
							     upLoanReturnInfo = new UpLoanReturnInfo ();
							     upLoanReturnInfo.setPositionRow(i);	
							     upLoanReturnInfo.setPositionCol(index);							    
							     upLoanReturnInfo.setReason("备注内容不能为空");	
							     return upLoanReturnInfo;
							   }
							     fixUsed =strAdd.trim();
							     financeInfo.setNote(fixUsed);
						
					break;					
				default :
						break;									
				}
				//解决BUG #14161::（78:8081站点）网银批量付款上传问题
				if(strPayerAcctID!=0&&strPayeeAcctID!=0&&strPayerAcctID == strPayeeAcctID)
				{
					upLoanReturnInfo = new UpLoanReturnInfo ();
					upLoanReturnInfo.setPositionRow(i);
					upLoanReturnInfo.setPositionCol(payeeAcctIndex);
					upLoanReturnInfo.setReason("收款方、付款方账户为同一账户,不可导入");
					return upLoanReturnInfo;
				}
				//解决BUG #14161 end
				strAdd="";
			}
			 for(int tttt=0;tttt<array.length;tttt++){
				 System.out.println(tttt+"=====code==="+array[tttt][0]+"=====amout======="+array[tttt][1]);				 
			 }
			
			//批量付款数据来源默认为网银
			financeInfo.setSource(SETTConstant.ExtSystemSource.EBANK);
			
			//其他信息
			lOfficeID = obfinanceInstrDao.findOfficeByAccountId(financeInfo.getPayerAcctID());
			financeInfo.setOfficeID(lOfficeID);
			financeInfo.setCurrencyID(lCurrencyID);
			financeInfo.setExecuteDate(Env.getSystemDate(lOfficeID,lCurrencyID));
			financeInfo.setConfirmDate(Env.getSystemDate(lOfficeID,lCurrencyID));
			financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
			financeInfo.setConfirmUserID(lUserID);
			financeInfo.setClientID(lClientID);
			
			tempAmount += financeInfo.getAmount();
			tempCount = i-1;
			System.out.println("tempAmount======"+DataFormat.formatDouble(tempAmount)+"=======tempCount======"+tempCount);
			conditionVector.addElement(financeInfo);
		}
		
		 if(array.length>0){			
			 for(int fast=0;fast<array.length;fast++){
				 if(array[fast][0]!=null){
					 accountBalanceInfo = obfinanceInstrDao.getCurrBalanceByAccountID(NameRef.getAccountIdByNo(NameRef.setAccontLine(array[fast][0])),lCurrencyID, InstructionID);
					 UsableBalance=accountBalanceInfo.getUsableBalance(); 
					 double tt=DataFormat.formatDouble(UsableBalance)-DataFormat.formatDouble(Double.valueOf(DataFormat.reverseFormatAmount(array[fast][1])).doubleValue());
					 if(tt<0){						 
						 upLoanReturnInfo = new UpLoanReturnInfo ();
						 upLoanReturnInfo.setReason("账户"+array[fast][0]+"可用余额度不足");		
						 return upLoanReturnInfo;
					 }
				 }
				 
			 }
				
		}
		
						
		if( DataFormat.formatDouble(tempAmount)  != dAmount)
		{
			upLoanReturnInfo = new UpLoanReturnInfo ();
			upLoanReturnInfo.setPositionRow(0);
			upLoanReturnInfo.setPositionCol(0);
			upLoanReturnInfo.setReason("总金额数目不对");
			
			return upLoanReturnInfo;
		}				
	else if(lCount != tempCount)
		{
		System.out.print("*****************"+lCount+"@@@@@@@@@@@@@@@@@@@@@@@"+tempCount);
			upLoanReturnInfo = new UpLoanReturnInfo ();
			upLoanReturnInfo.setPositionRow(0);
			upLoanReturnInfo.setPositionCol(0);
			upLoanReturnInfo.setReason("总笔数不对");
			
			return upLoanReturnInfo;
		}
		else 
			if(conditionVector != null && conditionVector.size() > 0)
		{
			String sBatchNo = "";	//批次号
			String sBatchNoHead = ""; //批次头
			String sBatchNoMid = "";	//批次中
			String sBatchNoEnd = "";	//批次尾
			long lBatchNoEnd = 1; //批次流水号
			long lInstructionID = -1;	//指令号
			
			con = Database.getConnection();
			StringBuffer sql = new StringBuffer();
			Timestamp timeExecute = Env.getSystemDate(lOfficeID,lCurrencyID);
			String strExecute = DataFormat.getDateString(Env.getSystemDate(lOfficeID,lCurrencyID));
			
			sBatchNoMid = strExecute.substring(0,4) + strExecute.substring(5,7) + strExecute.substring(8,10);
			
			
			//取已有最大批次号
			sql.append("select max(substr(sBatchNo,9,5)) sBatchNo from ob_financeinstr where substr(sBatchNo,0,8) = '" + sBatchNoMid + "'");
			
			System.out.println("=====取批次流水号最大值sql=====: " + sql.toString());
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			
			if(rs != null && rs.next())
			{
				if(rs.getString("sBatchNo") != null && !rs.getString("sBatchNo").equals(""))
				{
					lBatchNoEnd = Long.valueOf(rs.getString("sBatchNo")).longValue() + 1;
				}
			}
			if(lBatchNoEnd / 100 > 0)
			{
				sBatchNoEnd = "0" + String.valueOf(lBatchNoEnd).toString();
			}
			if(lBatchNoEnd / 10 > 0)
			{
				sBatchNoEnd = "00" + String.valueOf(lBatchNoEnd).toString();
			}
			else
			{
				sBatchNoEnd = "000" + String.valueOf(lBatchNoEnd).toString();
			}
			
			sBatchNo = sBatchNoHead + sBatchNoMid + sBatchNoEnd;
			
			Iterator it = conditionVector.iterator();
			
			//网银最晚提交时间效验
			OBCommitTimeBiz commitTime = new OBCommitTimeBiz();
			commitTime.validateOBCommitTime(timeExecute,sessionMng.m_lOfficeID,sessionMng.m_lCurrencyID);
			while(it.hasNext())
			{
				System.out.println("11");
				FinanceInfo financeInfo = (FinanceInfo)it.next();
				financeInfo.setSBatchNo(sBatchNo);
				financeInfo.setTimestamp(System.nanoTime());

				try
				{
					SecurityFacadeFactory factory = SecurityFacadeFactory.getInstance();
					SecurityFacadeInterface<? super ServletRequest,? super FinanceInfo> facade = factory.createSecurityFacade();
					facade.sign(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY,financeInfo);
					lInstructionID = financeInstr.addCapitalTrans(financeInfo);
					transinfo.setStatus(Constant.SUCCESSFUL);
					transinfo.setActionType(Constant.TransLogActionType.inport);
				}catch(Exception ex)
				{
					transinfo.setStatus(Constant.FAIL);
					transinfo.setActionType(Constant.TransLogActionType.inport);
					ex.printStackTrace();
					throw new IException(ex.getMessage());
				}
				finally
				{
						if(transinfo.getStatus()!=-1)
						{
							TranslogBiz translofbiz= new TranslogBiz();
							transinfo.setHostip(request.getRemoteAddr());
							transinfo.setHostname(request.getRemoteHost());
							transinfo.setTransType(financeInfo.getTransType());
							translofbiz.saveTransLogInfo(sessionMng,financeInfo,transinfo); 
						
						}
				}
				System.out.println("保存后返回的ID"+lInstructionID);
			}
				
			if(lInstructionID > 0)
			{
				upLoanReturnInfo = new UpLoanReturnInfo ();
				upLoanReturnInfo.setIsOk(true);
				upLoanReturnInfo.setPositionRow(0);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason("导入成功");
			}
			else
			{
				upLoanReturnInfo = new UpLoanReturnInfo ();
				upLoanReturnInfo.setIsOk(false);
				upLoanReturnInfo.setPositionRow(0);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason("导入失败");
			}

		}
				
	}

	 finally {//added by mzh_fu 2008/04/08 解决内存泄露问题
		if(con != null)
		{
			if(!con.isClosed())
				con.close();
			con = null;
		}
		if(ps != null)
		{
			ps.close();
			ps = null;
		}
		if(rs != null)
		{
			rs.close();
			rs = null;
		}
	}
	return upLoanReturnInfo; 
}
/****************上传文件方法结束***********************/

 %>
<%

	/* 标题固定变量 */
	String strTitle = "批量支付";

	/* 实现菜单控制 */
	long lShowMenu = OBConstant.ShowMenu.YES;
	String strMenu = (String)request.getParameter("menu");
	Log.print("--------strMenu="+strMenu);
	if ((strMenu != null) && (strMenu.equals("hidden")))
	{
	    lShowMenu = OBConstant.ShowMenu.NO;
	}
	request.setAttribute("menu", strMenu);
	
try{
	//用户登录检测 
    if (sessionMng.isLogin() == false)
    {
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E002");
    	out.flush();
    	return;
    }

    // 判断用户是否有权限 
    if (sessionMng.hasRight(request) == false)
    {
        out.println(sessionMng.hasRight(request));
    	OBHtml.showCommonMessage(out, sessionMng, strTitle, "",1, "Gen_E003");
    	out.flush();
    	return;
    }
        
	//设置上传文件路径
    //因为上传路径不是根目录路径，直接修改为根目录 add 08-03-02 for 华联
	//java.net.URL url = this.getClass().getClassLoader().getResource("upload/");
	//java.io.File file = new java.io.File(url.getFile());
	
	String upURL = "/upload/";
	//upURL = file.getPath();
	
    //获得页面参数
    double dAmount = 0.0;			//总金额
    long lCount = 0;				//总笔数
   
    String strTemp = null;
	strTemp = (String)request.getParameter("dAmount");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
		dAmount = DataFormat.parseNumber(strTemp);
	}
	strTemp = (String)request.getParameter("lCount");
	if (strTemp != null && strTemp.trim().length() > 0)
	{
		lCount = Long.valueOf(strTemp).longValue();
	}

	
    //上传文件
	mySmartUpload.initialize(pageContext);
	mySmartUpload.upload();
	
	UpLoanReturnInfo upLoanReturnInfo = null;
	upLoanReturnInfo = saveUploadFileToDateBase(mySmartUpload,sessionMng.m_lUserID,sessionMng.m_lClientID,sessionMng.m_lCurrencyID,sessionMng.m_lOfficeID,dAmount,lCount,upURL,sessionMng,request);
	
	/* 在请求中保存结果对象 */
    request.setAttribute("upLoanReturnInfo",upLoanReturnInfo);
	
    
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "exp_add.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
    
	/* forward到结果页面 */
    rd.forward(request, response);

	
}
catch(Exception exp)
{
	sessionMng.getActionMessages().addMessage(exp.getMessage());
	exp.printStackTrace();
	
	PageControllerInfo pageControllerInfo = new PageControllerInfo();
	pageControllerInfo.setSessionMng(sessionMng);
	pageControllerInfo.setUrl(strContext + "exp_add.jsp");
	//分发
	RequestDispatcher rd = request.getRequestDispatcher(PageController.getDispatcherURL(pageControllerInfo));
    
	/* forward到结果页面 */
    rd.forward(request, response);
}
%>