package com.iss.itreasury.ebank.obbatchpayment.bizlogic;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import net.sf.json.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import com.iss.itreasury.ebank.obbatchpayment.dataentity.OBBatchPayInfo;

import com.iss.itreasury.dataentity.UpLoanReturnInfo;
import com.iss.itreasury.ebank.obbatchpayment.dao.OBBatchPaymentDAO;
import com.iss.itreasury.ebank.obfinanceinstr.dao.OBFinanceInstrDao;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.AccountBalanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;

import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Env;


public class OBBatchPayment {

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
    *validateUploadFile  验证Excel信息
    *
    */
    
    public UpLoanReturnInfo validateUploadFile (String fileName,long lUserID,long lCurrencyID,double dAmount,long lCount)throws Exception
    {
    	UpLoanReturnInfo upLoanReturnInfo = null;
     	String strAdd ="";//每次从上传文件中读到的一个单元。
    	String strAdd1 ="";//每次从上传文件中读到的付款方名称单元付值给strAdd1，备用。
    	OBFinanceInstrDao obfinanceInstrDao=new OBFinanceInstrDao();
   	 	AccountBalanceInfo accountBalanceInfo = null;
	   	long InstructionID = -1;
	 	double UsableBalance=0.0; 
	 	int index=0;
	 	int j = 1;		//从第几行开始读数据(0代表第1行)
	 	int indexNow = 0; //当前列(内循环)
	 	int indexCount = 15;	//为华联新增一列：收款方名称
	 	double singleAmount = 0.0;
	 	double tempAmount = 0.0;
	 	long tempCount = 0;
	    String [][] array = new String[(int)lCount][2];
	    Map map = new HashMap();
	    String clientCode="";
	    Double codeAmout = null;
	    double amount = 0.00;
	 	OBBatchPaymentDAO obBatchPaymentDAO = new OBBatchPaymentDAO();
	 	
	 	
	 	try
	 	{
	 		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(Env.UPLOAD_PATH + "ebank/batchpay/"+fileName));
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
			
			if(j==1)
			{
				row = sheet.getRow(j); 
				
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
	                    	double d = cell.getNumericCellValue();
	                    	strAdd=DataFormat.format(d,0);                  	
						}    
	                    
					}
					else
					{
						strAdd="";
					}
					
					if (strAdd==null)
					{
						strAdd="";
					}else{
						strAdd=strAdd.trim();
						System.out.println("============="+strAdd);
					}
					
					switch(index)
					{
						case 1 ://汇款总笔数
							if(!strAdd.trim().equals(""))
							{
								if(!String.valueOf(lCount).equals(strAdd)  )
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(j);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("总笔数不对");							
									return upLoanReturnInfo;						
								}
							}
							strAdd="";
					}
					
				} //for  index
				
				j++;
			}
			for (int i=j;i<showrow;i++)
			{
				row = sheet.getRow(i); 
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
					else
					{
						strAdd = "";
					}
					
					if (strAdd==null)
					{
						strAdd="";
					}else{
						strAdd=strAdd.trim();
					}
					
					long payeeID = -1;		//查找外部账户资料ID
					System.out.println("strAdd =================="+strAdd);
					
					switch (index)
					{
						case 0: //付款方账户ID
							clientCode=	strAdd.trim();
							if(!map.containsKey(clientCode))
							{
								map.put(clientCode, new Double(0.00));
								if(!strAdd.trim().equals("") && NameRef.getAccountIdByNo(NameRef.setAccontLine(strAdd.trim())) > 0)
								{
	
									System.out.println("=====================clientCode================="+clientCode);
									if(!obfinanceInstrDao.checkAccountIsOwnedByUser(NameRef.getAccountIdByNo(NameRef.setAccontLine(strAdd.trim())),lUserID,lCurrencyID))
									{
										upLoanReturnInfo = new UpLoanReturnInfo ();
										upLoanReturnInfo.setPositionRow(i);
										upLoanReturnInfo.setPositionCol(index);
										upLoanReturnInfo.setReason("您不具有操作该付款方账户的权限");
										return upLoanReturnInfo;
									}
							
								}
								else
								{
									
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("付款方账户不存在");
									
									return upLoanReturnInfo;
								}
							}
							break;
						case 1: //汇款方式
							if(strAdd.trim().equals("银行汇款"))
							{
								transType = OBConstant.SettRemitType.BANKPAY;
						
						
							}
							else if(strAdd.trim().equals("内部转账"))
							{
								transType = OBConstant.SettRemitType.INTERNALVIREMENT;
						
							}
							break;
						case 2 ://收款方名称，为华联新加
							System.out.println("strAdd.trim()="+strAdd.trim());
							if(!strAdd.trim().equals(""))
							{
								if(transType ==OBConstant.SettRemitType.BANKPAY)
								{
									if(strAdd.trim().getBytes().length>80)
									{
										upLoanReturnInfo = new UpLoanReturnInfo ();
										upLoanReturnInfo.setPositionRow(i);
										upLoanReturnInfo.setPositionCol(index);
										upLoanReturnInfo.setReason("收款方名称长度不能大于40个汉字(80个字节)");
										return upLoanReturnInfo;
									}
								}else
								{
									 String lastCode=strAdd.trim().substring(strAdd.trim().length()-1);					   
								     long lReturn =OBConstant.Arry.getSameCode(lastCode);					     
								     if(lReturn > 0) {
								    	 strAdd1=strAdd.trim().substring(0,strAdd.trim().length());
								     }else{
										  strAdd1=strAdd;	 
								     }	
								}
							}
							else
							{
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
								    upLoanReturnInfo.setReason("收款方账户编号不合法请修改");
								    return upLoanReturnInfo;
								}
								
								strAdd=strAdd.replaceAll(","," ").trim();
								
								System.out.println("========================"+strAdd);

							    if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)		//内部转账
								{
								    
									payeeID = obBatchPaymentDAO.findPayeeAccountID(strAdd.trim(), strAdd1.trim());

								   if(payeeID<0)
								   {
										upLoanReturnInfo = new UpLoanReturnInfo ();
									    upLoanReturnInfo.setPositionRow(i);
									    upLoanReturnInfo.setPositionCol(index);
									    upLoanReturnInfo.setReason("收款方账户不存在");
									    return upLoanReturnInfo;
									} 
								   else if(clientCode.equals(strAdd.trim()))
								   {
									   upLoanReturnInfo = new UpLoanReturnInfo ();
									   upLoanReturnInfo.setPositionRow(i);
									   upLoanReturnInfo.setPositionCol(index);
									   upLoanReturnInfo.setReason("收款方账户编号和付款方帐户编号相同");
									   return upLoanReturnInfo;
								   }

								} 
							    else if (transType == OBConstant.SettRemitType.BANKPAY)//银行汇款
							    {
							    	if(strAdd.length()>50)
							    	{
							    		upLoanReturnInfo = new UpLoanReturnInfo ();
							    		upLoanReturnInfo.setPositionRow(i);
							    		upLoanReturnInfo.setPositionCol(index);
							    		upLoanReturnInfo.setReason("收款方账户编号长度不能大于50");
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
	
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
							
								if(strAdd.trim().equals(""))
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("缺少汇入省");
									
									return upLoanReturnInfo;
								}	
								else if(strAdd.trim().length()>10)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("汇入省长度不能大于10");
								}
							}
							break;
						case 5://汇入市

							if(transType == OBConstant.SettRemitType.BANKPAY)	//银行汇款
							{
								if(strAdd.trim().equals(""))
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("缺少汇入市");
									
									return upLoanReturnInfo;
								}
								else if(strAdd.trim().length()>10)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("汇入市长度不能大于10");
								}
							}
							break;
						case 6://汇入行名称
							if(transType == OBConstant.SettRemitType.BANKPAY)	//银行汇款
							{

								if(strAdd.trim().equals(""))
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("缺少汇入行名称");
									return upLoanReturnInfo;
								}
								else if(strAdd.trim().length()>40)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("汇入行名称长度不能大于40");
								}
							}
							break;
						case 7: //汇入行CNAPS号
							if(transType == OBConstant.SettRemitType.BANKPAY)	//银行汇款
							{
								if(strAdd.trim().length()>20)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("汇入行CNAPS号长度不能大于20");
								}
								
							}
							break;
						case 8://汇入行机构号
							if(transType == OBConstant.SettRemitType.BANKPAY)	//银行汇款
							{
								if(strAdd.trim().length()>20)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("汇入行机构号长度不能大于20");
								}

							}
							break;
						case 9://汇入行联行号
							if(transType == OBConstant.SettRemitType.BANKPAY)	//银行汇款
							{
								if(strAdd.trim().length()>20)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("汇入行联行号长度不能大于20");
								}
							}
							break;
						case 10 ://金额
							if(!strAdd.trim().equals(""))
							{
								singleAmount=Double.valueOf(DataFormat.reverseFormatAmount(strAdd)).doubleValue();		
								if(map.containsKey(clientCode))
								{
									codeAmout = (Double)map.get(clientCode);
									amount = DataFormat.formatDouble(codeAmout.doubleValue());
									amount += DataFormat.formatDouble(Double.valueOf(DataFormat.reverseFormatAmount(strAdd)).doubleValue());
									map.put(clientCode, new Double(amount));
								}else
								{
									map.put(clientCode, new Double(singleAmount));
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
	
						 case 14 :// 汇款固定用途/摘要

							 if(strAdd.trim().getBytes().length>12)
							 {
								 upLoanReturnInfo = new UpLoanReturnInfo ();
								 upLoanReturnInfo.setPositionRow(i);
								 upLoanReturnInfo.setPositionCol(index);
								 upLoanReturnInfo.setReason("备注内容不能超过6个汉字");							
								 return upLoanReturnInfo;
							 }
							 else if(strAdd.trim().equals(""))
							 {
								 upLoanReturnInfo = new UpLoanReturnInfo ();
							     upLoanReturnInfo.setPositionRow(i);	
							     upLoanReturnInfo.setPositionCol(index);							    
							     upLoanReturnInfo.setReason("备注内容不能为空");	
							     return upLoanReturnInfo;
							 }
							 break;
						 default:
							 break;
						    
							
					}//switch
					
					
				}//for 列

				strAdd="";
				tempAmount += singleAmount;
				tempCount = i-1;
				System.out.println("tempAmount======"+DataFormat.formatDouble(tempAmount)+"=======tempCount======"+tempCount);
				
			}//for 行
			
			Set set = map.entrySet();
			Iterator it = set.iterator();
			double tt;
			Double temp;
			while(it.hasNext())
			{
				Map.Entry entry = (Map.Entry) it.next();
				accountBalanceInfo = obfinanceInstrDao.getCurrBalanceByAccountID(NameRef.getAccountIdByNo(NameRef.setAccontLine((String)entry.getKey())),lCurrencyID, InstructionID);
				UsableBalance=accountBalanceInfo.getUsableBalance(); 
				temp = (Double)entry.getValue();
				tt=DataFormat.formatDouble(UsableBalance)-DataFormat.formatDouble(temp.doubleValue());
				 if(tt<0){						 
					 upLoanReturnInfo = new UpLoanReturnInfo ();
					 upLoanReturnInfo.setReason("账户"+(String)entry.getKey()+"可用余额度不足");		
					 return upLoanReturnInfo;
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
			
	 		
	 		
	 		
	 	}catch(Exception e)
	 	{
	 		e.printStackTrace();
	 	}
    	
    	return upLoanReturnInfo;
    }
    /**
     * 获取JSON串
     * @param path
     * @return
     * @throws Exception
     */
    public String getJSONString(String path) throws Exception
    {
    	String strJSON = "";
    	HSSFWorkbook wb = null;
		HSSFSheet sheet = null;
		HSSFRow row =  null;
		HSSFCell cell = null;
		String strTotalRows = "";
		long totalRows = -1;
		long totalCols = 15;  //最大列数
		String strAdd = "";
		String strAdd1 = "";
		long transType = -1;
		//double singleAmount = 0.00;
		long showRow = -1;
		
		try
		{
	    	POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(path));
	    	
			
			if (fs!=null)
			{			
				wb = new HSSFWorkbook(fs);			
			}
			if (wb!=null)
			{
				sheet = wb.getSheetAt(0);
			}
			
			cell = sheet.getRow(1).getCell((short)1);
			
			double d = cell.getNumericCellValue();
			strTotalRows=DataFormat.format(d,0);
			totalRows = Long.parseLong(strTotalRows);  //最大行数
			showRow = totalRows+2;
			
			strJSON = strJSON + "{\"rows\":[ \n";  
			
			for(int r=2;r<showRow;r++)
			{
				row = sheet.getRow(r); 
				for(int c=0;c<totalCols;c++)
				{
					cell = row.getCell((short)c);
					
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
							d = cell.getNumericCellValue();
							strAdd=DataFormat.format(d,2);
							
						}
						//在Excel中的类型是公式
	                    else if( cell.getCellType() ==HSSFCell.CELL_TYPE_FORMULA)
	                    {						    
	                    	
	                    	strAdd=cell.getStringCellValue().toString();						
						}    
	                    
					}	
					else
					{
						strAdd = "";
					}
					
					if (strAdd==null)
					{
						strAdd= "";
					}else{
						strAdd=strAdd.trim();
					}	
					
					switch(c)
					{
						case 0:  //付款方账户编号
							strJSON = strJSON + "{\"sPayerAcctNo\":\""+strAdd+"\",";
						break;
						case 1: //汇款方式
							if(strAdd.trim().equals("银行汇款"))
							{
								transType = OBConstant.SettRemitType.BANKPAY;
								strJSON = strJSON + "\"lRemitType\":"+transType+",";
						
							}
							else if(strAdd.trim().equals("内部转账"))
							{
								transType = OBConstant.SettRemitType.INTERNALVIREMENT;
								strJSON = strJSON + "\"lRemitType\":"+transType+",";
							}
							break;
						case 2: //收款方名称
							 String lastCode=strAdd.trim().substring(strAdd.trim().length()-1);					   
						     long lReturn =OBConstant.Arry.getSameCode(lastCode);					     
						     if(lReturn > 0) {
						    	 strAdd1=strAdd.trim().substring(0,strAdd.trim().length());
						    	 strJSON = strJSON + "\"sPayeeName\":\""+strAdd1+"\",";
						     }else{
						    	  strJSON = strJSON + "\"sPayeeName\":\""+strAdd+"\",";
									 
								  
						     }	
						     break;
						case 3 ://收款方账户编号
							strAdd=strAdd.replaceAll(","," ").trim();
							strJSON = strJSON + "\"sPayeeAcctNo\":\""+strAdd+"\",";
							break;
						case 4://汇入省
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeProv\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeProv\":\"\",";
							}
							
							break;
						case 5://汇入市
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeCity\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeCity\":\"\",";
							}
							break;
						case 6://汇入行名称
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeBankName\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeBankName\":\"\",";
							}
								
							break;
						case 7 : //汇入行CNAPS号
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeBankCNAPSNO\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeBankCNAPSNO\":\"\",";
							}
								
							break;
						case 8 : //汇入行机构号
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeBankOrgNO\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeBankOrgNO\":\"\",";
							}
							break;
						case 9 : //汇入行联行号
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeBankExchangeNO\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeBankExchangeNO\":\"\",";
							}
							break;
						case 10 ://金额
							//singleAmount=Double.valueOf(DataFormat.reverseFormatAmount(strAdd)).doubleValue();	
							
							strJSON = strJSON + "\"dAmount\":\""+strAdd+"\",";
							break;
						case 11 ://是否同行
							if(strAdd.trim().equals("是")){
								strJSON = strJSON + "\"isSameBank\":"+OBConstant.IsSameBank.ISYES+",";
						    
							}
							else if(strAdd.trim().equals("否")){
								strJSON = strJSON + "\"isSameBank\":"+2+",";  //回避下拉框自动带出数据的问题
							}
							break;
						case 12 :// 汇款区域
							if(!strAdd.trim().equals(""))
							{
								if(strAdd.trim().equals("本地")){
									strJSON = strJSON + "\"lRemitArea\":"+Constant.remitAreaType.NATIVE+",";
								     
								}
								else if(strAdd.trim().equals("异地")){
									strJSON = strJSON + "\"lRemitArea\":"+Constant.remitAreaType.DEVIATIONISM+",";
								}
							}
							
							break;
						case 13 :// 汇款速度
							if(!strAdd.trim().equals(""))
							{
								if(strAdd.trim().equals("普通")){
									strJSON = strJSON + "\"lRemitSpeed\":"+Constant.remitSpeedType.GENERAL+",";
									    
								}
								else if(strAdd.trim().equals("加急")){
									strJSON = strJSON + "\"lRemitSpeed\":"+Constant.remitSpeedType.RAPID+",";
										
								}
							}
								
						    break;	
						case 14 :// 汇款固定用途/摘要
							strJSON = strJSON + "\"sNote\":\""+strAdd.trim()+"\"}";
							break;
						default:
							break;
						
							
							
							
							
					}  //switch
					
				} //for 列
				strAdd="";
				if(r<showRow-1)
				{
					strJSON = strJSON + ",";
				}
				strJSON = strJSON + "\n";
				
				
			}//for  行
			strJSON = strJSON + "]}";
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
    	
    	return strJSON;
    }
    
    /**
     * 将JSON串转换成FinanceInfo数组
     * @param strJSON
     * @param sBatchNo
     * @param lUserID
     * @param lCurrencyID
     * @param lOfficeID
     * @param lClientID
     * @return
     * @throws Exception
     */
    public FinanceInfo[] translateJSONString(String strJSON,String sBatchNo,long lUserID,long lCurrencyID,long lOfficeID,long lClientID) throws Exception
    {
    	FinanceInfo[] financeInfoArray = null;
    	OBBatchPayInfo obBatchPayInfo = null;
    	FinanceInfo financeInfo = null;
   // 	OBFinanceInstrDao obfinanceInstrDao=null;
    	OBBatchPaymentDAO obBatchPaymentDAO = null;
    	long payeeID = -1;	
   // 	long row = -1;
   // 	long col = -1;
    	double singleAmount = 0.00;
    	try{
	       	JSONArray array = JSONArray.fromObject(strJSON); 
	       	financeInfoArray = new FinanceInfo[array.size()];
	//       	obfinanceInstrDao=new OBFinanceInstrDao();
	       	obBatchPaymentDAO = new OBBatchPaymentDAO();
	       	for(int i=0;i<array.size();i++)
	       	{
	 //      		row = i+1;
	       		financeInfo = new FinanceInfo();
	       		obBatchPayInfo = new OBBatchPayInfo();
	       		JSONObject jsonObject = array.getJSONObject(i); 
	       		obBatchPayInfo=(OBBatchPayInfo)JSONObject.toBean(jsonObject, OBBatchPayInfo.class);

	/*       		
	       		//付款方帐户校验
	       		if(!obBatchPayInfo.getsPayerAcctNo().trim().equals("") && NameRef.getAccountIdByNo(NameRef.setAccontLine(obBatchPayInfo.getsPayerAcctNo().trim())) > 0)
	       		{
	       			if(!obfinanceInstrDao.checkAccountIsOwnedByUser(NameRef.getAccountIdByNo(NameRef.setAccontLine(obBatchPayInfo.getsPayerAcctNo().trim())),lUserID,lCurrencyID))
	       			{
	       				col = 1;
	       				throw new Exception("您不具有该帐户的操作权限！ \n 在第"+row+"行，第"+col+"列");
	       			}
	       		}
	       		else
	       		{
	       			col = 1;
	       			throw new Exception("付款方帐户不存在！ \n 在第"+row+"行，第"+col+"列");
	       		}
	     */  		
	       		//付款方帐户信息
	       		financeInfo.setPayerAcctNo(obBatchPayInfo.getsPayerAcctNo());
	       		financeInfo.setPayerAcctID(NameRef.getAccountIdByNo(NameRef.setAccontLine(obBatchPayInfo.getsPayerAcctNo())));
	       		financeInfo.setPayerName(NameRef.getAccountNameByID(financeInfo.getPayerAcctID()));
	       		
	       		//汇款方式
	       		financeInfo.setRemitType(obBatchPayInfo.getlRemitType());
	       		if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.BANKPAY)
	       		{
	       			financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
	       		}
	       		else if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT)
	       		{
	       			financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
	       		}
	       		
	       		//收款方名称
	       		financeInfo.setPayeeName(obBatchPayInfo.getsPayeeName());
	/*       		
	       		//收款方编号校验
	       		if(!validateLimitString(obBatchPayInfo.getsPayeeAcctNo()))
	       		{
	       			col = 4;
	       			throw new Exception("收款方账户编号不合法! \n 在第"+row+"行，第"+col+"列");
	       		}
	   */    		
	       		//收款方帐户编号及id信息
	       		if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.BANKPAY)
	       		{
	       			payeeID = obBatchPaymentDAO.findEbankAccountID(obBatchPayInfo.getsPayeeAcctNo(), obBatchPayInfo.getsPayeeName());
	       			financeInfo.setPayeeAcctNo(obBatchPayInfo.getsPayeeAcctNo().trim());
	       			if(payeeID>0)
	       			{
	       				financeInfo.setPayeeAcctID(payeeID);
	       			}
	       		}
	       		else if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT){  //内部转账
	       			
	       			payeeID = obBatchPaymentDAO.findPayeeAccountID(obBatchPayInfo.getsPayeeAcctNo(),obBatchPayInfo.getsPayeeName());
	       		//	if(payeeID>0)
	       		//	{
	       				financeInfo.setPayeeAcctNo(obBatchPayInfo.getsPayeeAcctNo().trim());
	       				financeInfo.setPayeeAcctID(payeeID);
	       		//	}
	       		//	else
	       		//	{
	       		//		col = 4;
	       		//		throw new Exception("收款方账户不存在! \n 在第"+row+"行，第"+col+"列");
	       		//	}
	       			
	       		}
	       		
	       		
	       		
	       		if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.BANKPAY)
	       		{
	       			financeInfo.setPayeeProv(obBatchPayInfo.getsPayeeProv().trim());   //汇入省
	       			financeInfo.setPayeeCity(obBatchPayInfo.getsPayeeCity().trim());   //汇入市
	       			financeInfo.setPayeeBankName(obBatchPayInfo.getsPayeeBankName().trim());   //汇入行
	       			if(obBatchPayInfo.getsPayeeBankCNAPSNO()!=null)
	       			{
	       				financeInfo.setSPayeeBankCNAPSNO(obBatchPayInfo.getsPayeeBankCNAPSNO());  //CNAPS号
	       			}
	       			if(obBatchPayInfo.getsPayeeBankExchangeNO()!=null)
	       			{
	       				financeInfo.setSPayeeBankExchangeNO(obBatchPayInfo.getsPayeeBankExchangeNO());  //联行号
	       			}
	       			if(obBatchPayInfo.getsPayeeBankOrgNO()!=null)
	       			{
	       				financeInfo.setSPayeeBankOrgNO(obBatchPayInfo.getsPayeeBankOrgNO());  //机构号
	       			}
	       			
	       		}
	       		

	       		
	       		//金额
	       		singleAmount = Double.valueOf(DataFormat.reverseFormatAmount(obBatchPayInfo.getdAmount())).doubleValue();
	       		financeInfo.setAmount(singleAmount);
	       		
	       		//是否同行
	       		if(obBatchPayInfo.getIsSameBank()==OBConstant.IsSameBank.ISYES)
	       		{
	       			financeInfo.setIsSameBank(OBConstant.IsSameBank.ISYES);
	       		}
	       		else if(obBatchPayInfo.getIsSameBank()==2)
	       		{
	       			financeInfo.setIsSameBank(OBConstant.IsSameBank.ISNO);
	       		}
	       		
	       		//汇款区域
	       		financeInfo.setRemitArea(obBatchPayInfo.getlRemitArea());
	       		
	       		//汇款速度
	       		financeInfo.setRemitSpeed(obBatchPayInfo.getlRemitSpeed());
	       		
	       		//汇款用途
	       		financeInfo.setNote(obBatchPayInfo.getsNote().trim());
	       		
	       		//批量付款默认数据来源为网银
	       		financeInfo.setSource(SETTConstant.ExtSystemSource.EBANK);
	       		
	       		//其他
	       		financeInfo.setOfficeID(lOfficeID);
				financeInfo.setCurrencyID(lCurrencyID);
				financeInfo.setExecuteDate(Env.getSystemDate(lOfficeID,lCurrencyID));
				financeInfo.setConfirmDate(Env.getSystemDate(lOfficeID,lCurrencyID));
				financeInfo.setStatus(OBConstant.SettInstrStatus.SAVE);
				financeInfo.setConfirmUserID(lUserID);
				financeInfo.setClientID(lClientID);
	       		
				financeInfo.setSBatchNo(sBatchNo);
	       		financeInfoArray[i] = financeInfo;
	       		

	       	}//for
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return financeInfoArray;
    	
    }
    /**
     * 获取批次号
     * @param lOfficeID
     * @param lCurrencyID
     * @return
     * @throws Exception
     */
    public String getBatchNo(long lOfficeID,long lCurrencyID) throws Exception
    {
    	String sBatchNo = "";
    	OBBatchPaymentDAO obBatchPaymentDAO = new OBBatchPaymentDAO();
    	sBatchNo=obBatchPaymentDAO.getBatchNo(lOfficeID, lCurrencyID);
    	return sBatchNo;
    	
    }

}
