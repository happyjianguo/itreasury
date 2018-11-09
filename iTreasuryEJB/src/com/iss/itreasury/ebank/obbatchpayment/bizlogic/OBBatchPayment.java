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
     * У���ַ����еķǷ��ַ��������Ƿ��ַ������׳��쳣�����򷵻غϷ����ַ���
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
     * У���ַ����еķǷ��ַ��������Ƿ��ַ���������true��
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
    *validateUploadFile  ��֤Excel��Ϣ
    *
    */
    
    public UpLoanReturnInfo validateUploadFile (String fileName,long lUserID,long lCurrencyID,double dAmount,long lCount)throws Exception
    {
    	UpLoanReturnInfo upLoanReturnInfo = null;
     	String strAdd ="";//ÿ�δ��ϴ��ļ��ж�����һ����Ԫ��
    	String strAdd1 ="";//ÿ�δ��ϴ��ļ��ж����ĸ�����Ƶ�Ԫ��ֵ��strAdd1�����á�
    	OBFinanceInstrDao obfinanceInstrDao=new OBFinanceInstrDao();
   	 	AccountBalanceInfo accountBalanceInfo = null;
	   	long InstructionID = -1;
	 	double UsableBalance=0.0; 
	 	int index=0;
	 	int j = 1;		//�ӵڼ��п�ʼ������(0�����1��)
	 	int indexNow = 0; //��ǰ��(��ѭ��)
	 	int indexCount = 15;	//Ϊ��������һ�У��տ����
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
				upLoanReturnInfo.setReason("���ܵ���յ�Excel�ļ���");
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
						//��Excel�е��������ı�������
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
						{
							strAdd=cell.getStringCellValue();
						}
						// ��Excel�е���������ֵ
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
						{
							double d = cell.getNumericCellValue();
							strAdd=DataFormat.format(d,2);
							
						}
						//��Excel�е������ǹ�ʽ
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
						case 1 ://����ܱ���
							if(!strAdd.trim().equals(""))
							{
								if(!String.valueOf(lCount).equals(strAdd)  )
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(j);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("�ܱ�������");							
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
						//��Excel�е��������ı�������
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
						{
							strAdd=cell.getStringCellValue();
						}
						// ��Excel�е���������ֵ
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
						{
							double d = cell.getNumericCellValue();
							strAdd=DataFormat.format(d,2);
							
						}
						//��Excel�е������ǹ�ʽ
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
					
					long payeeID = -1;		//�����ⲿ�˻�����ID
					System.out.println("strAdd =================="+strAdd);
					
					switch (index)
					{
						case 0: //����˻�ID
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
										upLoanReturnInfo.setReason("�������в����ø���˻���Ȩ��");
										return upLoanReturnInfo;
									}
							
								}
								else
								{
									
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("����˻�������");
									
									return upLoanReturnInfo;
								}
							}
							break;
						case 1: //��ʽ
							if(strAdd.trim().equals("���л��"))
							{
								transType = OBConstant.SettRemitType.BANKPAY;
						
						
							}
							else if(strAdd.trim().equals("�ڲ�ת��"))
							{
								transType = OBConstant.SettRemitType.INTERNALVIREMENT;
						
							}
							break;
						case 2 ://�տ���ƣ�Ϊ�����¼�
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
										upLoanReturnInfo.setReason("�տ���Ƴ��Ȳ��ܴ���40������(80���ֽ�)");
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
								upLoanReturnInfo.setReason("�տ���Ʋ���Ϊ��");							
								return upLoanReturnInfo;
							}
							 break;
						case 3 ://�տ�˻�ID
							if(!strAdd.trim().equals(""))
							{
								//�տ�˻����ֻ�������ֺ��ַ�
								if(!validateLimitString(strAdd))
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
								    upLoanReturnInfo.setPositionRow(i);
								    upLoanReturnInfo.setPositionCol(index);
								    upLoanReturnInfo.setReason("�տ�˻���Ų��Ϸ����޸�");
								    return upLoanReturnInfo;
								}
								
								strAdd=strAdd.replaceAll(","," ").trim();
								
								System.out.println("========================"+strAdd);

							    if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)		//�ڲ�ת��
								{
								    
									payeeID = obBatchPaymentDAO.findPayeeAccountID(strAdd.trim(), strAdd1.trim());

								   if(payeeID<0)
								   {
										upLoanReturnInfo = new UpLoanReturnInfo ();
									    upLoanReturnInfo.setPositionRow(i);
									    upLoanReturnInfo.setPositionCol(index);
									    upLoanReturnInfo.setReason("�տ�˻�������");
									    return upLoanReturnInfo;
									} 
								   else if(clientCode.equals(strAdd.trim()))
								   {
									   upLoanReturnInfo = new UpLoanReturnInfo ();
									   upLoanReturnInfo.setPositionRow(i);
									   upLoanReturnInfo.setPositionCol(index);
									   upLoanReturnInfo.setReason("�տ�˻���ź͸���ʻ������ͬ");
									   return upLoanReturnInfo;
								   }

								} 
							    else if (transType == OBConstant.SettRemitType.BANKPAY)//���л��
							    {
							    	if(strAdd.length()>50)
							    	{
							    		upLoanReturnInfo = new UpLoanReturnInfo ();
							    		upLoanReturnInfo.setPositionRow(i);
							    		upLoanReturnInfo.setPositionCol(index);
							    		upLoanReturnInfo.setReason("�տ�˻���ų��Ȳ��ܴ���50");
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
								upLoanReturnInfo.setReason("�տ�˻�Ϊ��");
								
								return upLoanReturnInfo;
							}
							break;	
						case 4://����ʡ
	
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
							
								if(strAdd.trim().equals(""))
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("ȱ�ٻ���ʡ");
									
									return upLoanReturnInfo;
								}	
								else if(strAdd.trim().length()>10)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("����ʡ���Ȳ��ܴ���10");
								}
							}
							break;
						case 5://������

							if(transType == OBConstant.SettRemitType.BANKPAY)	//���л��
							{
								if(strAdd.trim().equals(""))
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("ȱ�ٻ�����");
									
									return upLoanReturnInfo;
								}
								else if(strAdd.trim().length()>10)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("�����г��Ȳ��ܴ���10");
								}
							}
							break;
						case 6://����������
							if(transType == OBConstant.SettRemitType.BANKPAY)	//���л��
							{

								if(strAdd.trim().equals(""))
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("ȱ�ٻ���������");
									return upLoanReturnInfo;
								}
								else if(strAdd.trim().length()>40)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("���������Ƴ��Ȳ��ܴ���40");
								}
							}
							break;
						case 7: //������CNAPS��
							if(transType == OBConstant.SettRemitType.BANKPAY)	//���л��
							{
								if(strAdd.trim().length()>20)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("������CNAPS�ų��Ȳ��ܴ���20");
								}
								
							}
							break;
						case 8://�����л�����
							if(transType == OBConstant.SettRemitType.BANKPAY)	//���л��
							{
								if(strAdd.trim().length()>20)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("�����л����ų��Ȳ��ܴ���20");
								}

							}
							break;
						case 9://���������к�
							if(transType == OBConstant.SettRemitType.BANKPAY)	//���л��
							{
								if(strAdd.trim().length()>20)
								{
									upLoanReturnInfo = new UpLoanReturnInfo ();
									upLoanReturnInfo.setPositionRow(i);
									upLoanReturnInfo.setPositionCol(index);
									upLoanReturnInfo.setReason("���������кų��Ȳ��ܴ���20");
								}
							}
							break;
						case 10 ://���
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
								upLoanReturnInfo.setReason("ȱ�ٽ��");
								
								return upLoanReturnInfo;
							}
							break;
	
						 case 14 :// ���̶���;/ժҪ

							 if(strAdd.trim().getBytes().length>12)
							 {
								 upLoanReturnInfo = new UpLoanReturnInfo ();
								 upLoanReturnInfo.setPositionRow(i);
								 upLoanReturnInfo.setPositionCol(index);
								 upLoanReturnInfo.setReason("��ע���ݲ��ܳ���6������");							
								 return upLoanReturnInfo;
							 }
							 else if(strAdd.trim().equals(""))
							 {
								 upLoanReturnInfo = new UpLoanReturnInfo ();
							     upLoanReturnInfo.setPositionRow(i);	
							     upLoanReturnInfo.setPositionCol(index);							    
							     upLoanReturnInfo.setReason("��ע���ݲ���Ϊ��");	
							     return upLoanReturnInfo;
							 }
							 break;
						 default:
							 break;
						    
							
					}//switch
					
					
				}//for ��

				strAdd="";
				tempAmount += singleAmount;
				tempCount = i-1;
				System.out.println("tempAmount======"+DataFormat.formatDouble(tempAmount)+"=======tempCount======"+tempCount);
				
			}//for ��
			
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
					 upLoanReturnInfo.setReason("�˻�"+(String)entry.getKey()+"�������Ȳ���");		
					 return upLoanReturnInfo;
				 }				
			}
			
			if( DataFormat.formatDouble(tempAmount)  != dAmount)
			{
				upLoanReturnInfo = new UpLoanReturnInfo ();
				upLoanReturnInfo.setPositionRow(0);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason("�ܽ����Ŀ����");
				
				return upLoanReturnInfo;
			}				
			else if(lCount != tempCount)
			{
				System.out.print("*****************"+lCount+"@@@@@@@@@@@@@@@@@@@@@@@"+tempCount);
				upLoanReturnInfo = new UpLoanReturnInfo ();
				upLoanReturnInfo.setPositionRow(0);
				upLoanReturnInfo.setPositionCol(0);
				upLoanReturnInfo.setReason("�ܱ�������");
				
				return upLoanReturnInfo;
			}
			
	 		
	 		
	 		
	 	}catch(Exception e)
	 	{
	 		e.printStackTrace();
	 	}
    	
    	return upLoanReturnInfo;
    }
    /**
     * ��ȡJSON��
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
		long totalCols = 15;  //�������
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
			totalRows = Long.parseLong(strTotalRows);  //�������
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
						//��Excel�е��������ı�������
						if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) 
						{
							strAdd=cell.getStringCellValue();
						}
						// ��Excel�е���������ֵ
						else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
						{
							d = cell.getNumericCellValue();
							strAdd=DataFormat.format(d,2);
							
						}
						//��Excel�е������ǹ�ʽ
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
						case 0:  //����˻����
							strJSON = strJSON + "{\"sPayerAcctNo\":\""+strAdd+"\",";
						break;
						case 1: //��ʽ
							if(strAdd.trim().equals("���л��"))
							{
								transType = OBConstant.SettRemitType.BANKPAY;
								strJSON = strJSON + "\"lRemitType\":"+transType+",";
						
							}
							else if(strAdd.trim().equals("�ڲ�ת��"))
							{
								transType = OBConstant.SettRemitType.INTERNALVIREMENT;
								strJSON = strJSON + "\"lRemitType\":"+transType+",";
							}
							break;
						case 2: //�տ����
							 String lastCode=strAdd.trim().substring(strAdd.trim().length()-1);					   
						     long lReturn =OBConstant.Arry.getSameCode(lastCode);					     
						     if(lReturn > 0) {
						    	 strAdd1=strAdd.trim().substring(0,strAdd.trim().length());
						    	 strJSON = strJSON + "\"sPayeeName\":\""+strAdd1+"\",";
						     }else{
						    	  strJSON = strJSON + "\"sPayeeName\":\""+strAdd+"\",";
									 
								  
						     }	
						     break;
						case 3 ://�տ�˻����
							strAdd=strAdd.replaceAll(","," ").trim();
							strJSON = strJSON + "\"sPayeeAcctNo\":\""+strAdd+"\",";
							break;
						case 4://����ʡ
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeProv\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeProv\":\"\",";
							}
							
							break;
						case 5://������
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeCity\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeCity\":\"\",";
							}
							break;
						case 6://����������
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeBankName\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeBankName\":\"\",";
							}
								
							break;
						case 7 : //������CNAPS��
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeBankCNAPSNO\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeBankCNAPSNO\":\"\",";
							}
								
							break;
						case 8 : //�����л�����
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeBankOrgNO\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeBankOrgNO\":\"\",";
							}
							break;
						case 9 : //���������к�
							if(transType == OBConstant.SettRemitType.BANKPAY)
							{
								strJSON = strJSON + "\"sPayeeBankExchangeNO\":\""+strAdd.trim()+"\",";
							}
							else if(transType == OBConstant.SettRemitType.INTERNALVIREMENT)
							{
								strJSON = strJSON + "\"sPayeeBankExchangeNO\":\"\",";
							}
							break;
						case 10 ://���
							//singleAmount=Double.valueOf(DataFormat.reverseFormatAmount(strAdd)).doubleValue();	
							
							strJSON = strJSON + "\"dAmount\":\""+strAdd+"\",";
							break;
						case 11 ://�Ƿ�ͬ��
							if(strAdd.trim().equals("��")){
								strJSON = strJSON + "\"isSameBank\":"+OBConstant.IsSameBank.ISYES+",";
						    
							}
							else if(strAdd.trim().equals("��")){
								strJSON = strJSON + "\"isSameBank\":"+2+",";  //�ر��������Զ��������ݵ�����
							}
							break;
						case 12 :// �������
							if(!strAdd.trim().equals(""))
							{
								if(strAdd.trim().equals("����")){
									strJSON = strJSON + "\"lRemitArea\":"+Constant.remitAreaType.NATIVE+",";
								     
								}
								else if(strAdd.trim().equals("���")){
									strJSON = strJSON + "\"lRemitArea\":"+Constant.remitAreaType.DEVIATIONISM+",";
								}
							}
							
							break;
						case 13 :// ����ٶ�
							if(!strAdd.trim().equals(""))
							{
								if(strAdd.trim().equals("��ͨ")){
									strJSON = strJSON + "\"lRemitSpeed\":"+Constant.remitSpeedType.GENERAL+",";
									    
								}
								else if(strAdd.trim().equals("�Ӽ�")){
									strJSON = strJSON + "\"lRemitSpeed\":"+Constant.remitSpeedType.RAPID+",";
										
								}
							}
								
						    break;	
						case 14 :// ���̶���;/ժҪ
							strJSON = strJSON + "\"sNote\":\""+strAdd.trim()+"\"}";
							break;
						default:
							break;
						
							
							
							
							
					}  //switch
					
				} //for ��
				strAdd="";
				if(r<showRow-1)
				{
					strJSON = strJSON + ",";
				}
				strJSON = strJSON + "\n";
				
				
			}//for  ��
			strJSON = strJSON + "]}";
			

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
    	
    	return strJSON;
    }
    
    /**
     * ��JSON��ת����FinanceInfo����
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
	       		//����ʻ�У��
	       		if(!obBatchPayInfo.getsPayerAcctNo().trim().equals("") && NameRef.getAccountIdByNo(NameRef.setAccontLine(obBatchPayInfo.getsPayerAcctNo().trim())) > 0)
	       		{
	       			if(!obfinanceInstrDao.checkAccountIsOwnedByUser(NameRef.getAccountIdByNo(NameRef.setAccontLine(obBatchPayInfo.getsPayerAcctNo().trim())),lUserID,lCurrencyID))
	       			{
	       				col = 1;
	       				throw new Exception("�������и��ʻ��Ĳ���Ȩ�ޣ� \n �ڵ�"+row+"�У���"+col+"��");
	       			}
	       		}
	       		else
	       		{
	       			col = 1;
	       			throw new Exception("����ʻ������ڣ� \n �ڵ�"+row+"�У���"+col+"��");
	       		}
	     */  		
	       		//����ʻ���Ϣ
	       		financeInfo.setPayerAcctNo(obBatchPayInfo.getsPayerAcctNo());
	       		financeInfo.setPayerAcctID(NameRef.getAccountIdByNo(NameRef.setAccontLine(obBatchPayInfo.getsPayerAcctNo())));
	       		financeInfo.setPayerName(NameRef.getAccountNameByID(financeInfo.getPayerAcctID()));
	       		
	       		//��ʽ
	       		financeInfo.setRemitType(obBatchPayInfo.getlRemitType());
	       		if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.BANKPAY)
	       		{
	       			financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_BANKPAY);
	       		}
	       		else if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT)
	       		{
	       			financeInfo.setTransType(OBConstant.SettInstrType.CAPTRANSFER_INTERNALVIREMENT);
	       		}
	       		
	       		//�տ����
	       		financeInfo.setPayeeName(obBatchPayInfo.getsPayeeName());
	/*       		
	       		//�տ���У��
	       		if(!validateLimitString(obBatchPayInfo.getsPayeeAcctNo()))
	       		{
	       			col = 4;
	       			throw new Exception("�տ�˻���Ų��Ϸ�! \n �ڵ�"+row+"�У���"+col+"��");
	       		}
	   */    		
	       		//�տ�ʻ���ż�id��Ϣ
	       		if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.BANKPAY)
	       		{
	       			payeeID = obBatchPaymentDAO.findEbankAccountID(obBatchPayInfo.getsPayeeAcctNo(), obBatchPayInfo.getsPayeeName());
	       			financeInfo.setPayeeAcctNo(obBatchPayInfo.getsPayeeAcctNo().trim());
	       			if(payeeID>0)
	       			{
	       				financeInfo.setPayeeAcctID(payeeID);
	       			}
	       		}
	       		else if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.INTERNALVIREMENT){  //�ڲ�ת��
	       			
	       			payeeID = obBatchPaymentDAO.findPayeeAccountID(obBatchPayInfo.getsPayeeAcctNo(),obBatchPayInfo.getsPayeeName());
	       		//	if(payeeID>0)
	       		//	{
	       				financeInfo.setPayeeAcctNo(obBatchPayInfo.getsPayeeAcctNo().trim());
	       				financeInfo.setPayeeAcctID(payeeID);
	       		//	}
	       		//	else
	       		//	{
	       		//		col = 4;
	       		//		throw new Exception("�տ�˻�������! \n �ڵ�"+row+"�У���"+col+"��");
	       		//	}
	       			
	       		}
	       		
	       		
	       		
	       		if(obBatchPayInfo.getlRemitType()==OBConstant.SettRemitType.BANKPAY)
	       		{
	       			financeInfo.setPayeeProv(obBatchPayInfo.getsPayeeProv().trim());   //����ʡ
	       			financeInfo.setPayeeCity(obBatchPayInfo.getsPayeeCity().trim());   //������
	       			financeInfo.setPayeeBankName(obBatchPayInfo.getsPayeeBankName().trim());   //������
	       			if(obBatchPayInfo.getsPayeeBankCNAPSNO()!=null)
	       			{
	       				financeInfo.setSPayeeBankCNAPSNO(obBatchPayInfo.getsPayeeBankCNAPSNO());  //CNAPS��
	       			}
	       			if(obBatchPayInfo.getsPayeeBankExchangeNO()!=null)
	       			{
	       				financeInfo.setSPayeeBankExchangeNO(obBatchPayInfo.getsPayeeBankExchangeNO());  //���к�
	       			}
	       			if(obBatchPayInfo.getsPayeeBankOrgNO()!=null)
	       			{
	       				financeInfo.setSPayeeBankOrgNO(obBatchPayInfo.getsPayeeBankOrgNO());  //������
	       			}
	       			
	       		}
	       		

	       		
	       		//���
	       		singleAmount = Double.valueOf(DataFormat.reverseFormatAmount(obBatchPayInfo.getdAmount())).doubleValue();
	       		financeInfo.setAmount(singleAmount);
	       		
	       		//�Ƿ�ͬ��
	       		if(obBatchPayInfo.getIsSameBank()==OBConstant.IsSameBank.ISYES)
	       		{
	       			financeInfo.setIsSameBank(OBConstant.IsSameBank.ISYES);
	       		}
	       		else if(obBatchPayInfo.getIsSameBank()==2)
	       		{
	       			financeInfo.setIsSameBank(OBConstant.IsSameBank.ISNO);
	       		}
	       		
	       		//�������
	       		financeInfo.setRemitArea(obBatchPayInfo.getlRemitArea());
	       		
	       		//����ٶ�
	       		financeInfo.setRemitSpeed(obBatchPayInfo.getlRemitSpeed());
	       		
	       		//�����;
	       		financeInfo.setNote(obBatchPayInfo.getsNote().trim());
	       		
	       		//��������Ĭ��������ԴΪ����
	       		financeInfo.setSource(SETTConstant.ExtSystemSource.EBANK);
	       		
	       		//����
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
     * ��ȡ���κ�
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
