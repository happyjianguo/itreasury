package com.iss.itreasury.fcinterface.bankportal.bankcode;
/**
 * fszhu
 * 2008-11-27
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import com.iss.itreasury.bs.util.regex.Matcher;
import com.iss.itreasury.bs.util.regex.Pattern;


public class BranchNOIdentify 
{
	
	
	/** 
	 * ��������ļ�·��
	 */
	private static String  fileRootName = "bank_type.property";

	/**
	 * ��ǰ���������ļ��Ļ��棬�洢�������е����Ƽ������
	 * ��ǰ����ά�������ʽ��ţ�
	 * ��һά�������������֣��ڶ�ά�ĵ�һ��Ԫ�ش�ŵ�ǰ���еı�׼���ƣ�����Ϊ�գ���������Ϊ�����
	 */
	private static ArrayList bankType = null;
	/**
	 * ��ǰ���������ļ����޸�ʱ�䣬���ļ����޸ĺ�������̬���ظ��ļ�
	 */

	private static long fileModifyTime = -1;
	
	/**
	 * ��ǰ���������ļ��Ļ��棬�洢�������еı�׼���ƺ��б��
	 */
	
	private static ArrayList bankTypeName =null;
	/**
	 * ���ļ������������Ƶ�������
	 *
	 */
	private static void loadBankNameFile(File bankNameFile) throws Exception
	{
		bankType = new ArrayList(32);
		bankTypeName = new ArrayList(15);
		if(!bankNameFile.exists())
		{
			throw new Exception("������������ļ�"+bankNameFile.getName()+"������");
		}
		
		FileReader fileReader = new FileReader(bankNameFile);
		BufferedReader reader = new BufferedReader(fileReader);
		
		Pattern mainNamePattern = Pattern.compile("(.*?)=(.*)");
		Pattern otherNamePattern = Pattern.compile("(.*?);");
		Matcher mainNameMatcher = null;
		Matcher otherNameMatcher = null;
		
		String strLine = null;
		int lineNum = 0;
		
		StringBuffer errMsg = new StringBuffer();
		
		while ((strLine = reader.readLine()) != null
				&& strLine.trim().length() > 0)
		{
			lineNum++;
//			logger.debug("line:" + lineNum + " " + strLine);
			
			mainNameMatcher = mainNamePattern.matcher(strLine);
			//System.out.println(mainNameMatcher+"111111111");
			
			
			String matchStr = null;
			String subMatchStr = null;
			int matchNum = 0;
			ArrayList bankName = new ArrayList(16);
			
			if(mainNameMatcher.find())
		    {
		    	matchNum++;
		    	matchStr = mainNameMatcher.group(1).trim();
		    	//System.out.println("    matchNum:" + matchNum + " " + matchStr);
		    	bankTypeName.add(matchStr);
//		    	logger.debug("    matchNum:" + matchNum + " " + matchStr);
		      	
		      	if(matchStr != null && matchStr.length() > 0)
		      	{
				    for(int i = 0; i < bankType.size(); i++)
				    {
				    	ArrayList al = (ArrayList)bankType.get(i);
				    	for(int j = 0; j < al.size(); j++)
				    	{
				    		String strTemp = (String)al.get(j);
				    		if(matchStr != null && matchStr.length() > 0
				    				&& strTemp != null && strTemp.length() > 0
									&& isRepeatName(matchStr, (String)al.get(j)))
				    		{
				    			if(errMsg.toString().length() <= 0)
				    			{
				    				errMsg.append("������������ļ�"+bankNameFile.getName()+"���Ϸ�\n");
				    			}
				    			errMsg.append("��"+lineNum+"�е�"+matchNum+"���������"+(i+1)+"�е�"+(j+1)+"�������ظ�\n");
				    		}
				    	}
				    }
		      	}
		      	bankName.add(matchStr);
		      	
		      	subMatchStr = mainNameMatcher.group(2).trim();
		    }
			else
			{
				if(errMsg.toString().length() <= 0)
    			{
    				errMsg.append("������������ļ�"+bankNameFile.getName()+"���Ϸ�\n");
    			}
				errMsg.append("��"+lineNum+"��������Ч\n");
				continue;
			}
			
//			logger.debug("    group(2):" + subMatchStr);
			otherNameMatcher = otherNamePattern.matcher(subMatchStr);
			matchStr = null;
			
			while (otherNameMatcher.find())
		    {
		    	matchNum++;
		    	matchStr = otherNameMatcher.group(1).trim();
//		    	logger.debug("    matchNum:" + matchNum + " " + matchStr);
		      	
		      	if(matchStr != null && matchStr.length() > 0)
		      	{
				    for(int i = 0; i < bankType.size(); i++)
				    {
				    	ArrayList al = (ArrayList)bankType.get(i);
				    	for(int j = 0; j < al.size(); j++)
				    	{
				    		String strTemp = (String)al.get(j);
				    		if(matchStr != null && matchStr.length() > 0
				    				&& strTemp != null && strTemp.length() > 0
									&& isRepeatName(matchStr, strTemp))
				    		{
				    			if(errMsg.toString().length() <= 0)
				    			{
				    				errMsg.append("������������ļ�"+bankNameFile.getName()+"���Ϸ�\n");
				    			}
				    			errMsg.append("��"+lineNum+"�е�"+matchNum+"���������"+(i+1)+"�е�"+(j+1)+"�������ظ�\n");
				    		}
				    	}
				    }
				    bankName.add(matchStr);
		      	}
		    }
	      	if(bankName.size() > 0)
	      	{
	      		bankType.add(bankName);
	      	}
		}
		
		if(errMsg.toString().length() > 0)
		{
			throw new Exception(errMsg.toString());
		}
		
		fileModifyTime = bankNameFile.lastModified();
	}
	/**
	 * �ж������ַ����Ƿ��໥����
	 * @param matchStr
	 * @param object
	 * @return
	 */
	private static boolean isRepeatName(String matchStr, String object) throws Exception
	{
		Pattern matchPattern = Pattern.compile(matchStr);
		Matcher nameMatcher = matchPattern.matcher(object);
		if(nameMatcher.find())
		{
			return true;
		}
		else
		{
			matchPattern = Pattern.compile(object);
			nameMatcher = matchPattern.matcher(matchStr);
			if(nameMatcher.find())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	/**
	 * ����������������ƻ�ȡ�����е������
	 * @param branchName
	 * @return
	 * @throws Exception
	 */
	public static String getBankTypeCodeByBankName(String inputName)throws Exception
	{
		String branchName = filterBlank(inputName);
		if(branchName == null || branchName.length() <=0 )
		{
			return null;
		}
		
		File bankNameFile = new File(fileRootName);
		if(!bankNameFile.exists())
		{
			throw new Exception("������������ļ�"+fileRootName+"������");
		}
		
//		logger.debug("���������ļ�������޸�ʱ�䣺"+bankNameFile.lastModified());
//		logger.debug("���������ļ����ϴζ���ʱ�䣺"+fileModifyTime);
		if(fileModifyTime != bankNameFile.lastModified())
		{
			loadBankNameFile(bankNameFile);
		}
		
		String bankName = null;
		Pattern matchPattern = null;
		Matcher nameMatcher = null;
		
		for(int i = 0; i < bankType.size(); i++)
	    {
	    	ArrayList al = (ArrayList)bankType.get(i);
	    	for(int j = 0; j < al.size(); j++)
	    	{
	    		bankName = (String)al.get(j);
	    		if(bankName != null && bankName.trim().length() > 0)
	    		{
		    		matchPattern = Pattern.compile(bankName);
		    		nameMatcher = matchPattern.matcher(branchName);
		    		if(nameMatcher.find())
		    		{
//		    			logger.debug("��"+branchName+"�����"+(i+1)+"�е�"+(j+1)+"������ƥ��");
		    			return (String)al.get(1);
		    		}
	    		}
	    	}
	    }
		
		return null;
	}
	
	/**
	 * ����������������ƻ�ȡ�����еı�׼����
	 * @param branchName
	 * @return
	 * @throws Exception
	 */
	public static String getStanderBankNameByBankName(String inputName)throws Exception
	{
		String branchName = filterBlank(inputName);
		if(branchName == null || branchName.length() <=0 )
		{
			throw new Exception("����Ĳ���Ϊ��");
		}
		
		File bankNameFile = new File(fileRootName);
		if(!bankNameFile.exists())
		{
			throw new Exception("������������ļ�"+fileRootName+"������");
		}
		
//		logger.debug("���������ļ�������޸�ʱ�䣺"+bankNameFile.lastModified());
//		logger.debug("���������ļ����ϴζ���ʱ�䣺"+fileModifyTime);
		if(fileModifyTime != bankNameFile.lastModified())
		{
			loadBankNameFile(bankNameFile);
		}
		
		String bankName = null;
		Pattern matchPattern = null;
		Matcher nameMatcher = null;
		
		for(int i = 0; i < bankType.size(); i++)
	    {
	    	ArrayList al = (ArrayList)bankType.get(i);
	    	for(int j = 0; j < al.size(); j++)
	    	{
	    		bankName = (String)al.get(j);
	    		if(bankName != null && bankName.trim().length() > 0)
	    		{
		    		matchPattern = Pattern.compile(bankName);
		    		nameMatcher = matchPattern.matcher(branchName);
		    		if(nameMatcher.find())
		    		{
//		    			logger.debug("��"+branchName+"�����"+(i+1)+"�е�"+(j+1)+"������ƥ��");
		    			return (String)al.get(0);
		    		}
	    		}
	    	}
	    }
		
		return null;
	}
	
	
	public static ArrayList getBankNames()throws Exception
	{
		File bankNameFile = new File(fileRootName);
		if(!bankNameFile.exists())
		{
			throw new Exception("������������ļ�"+fileRootName+"������");
		}
		
//		logger.debug("���������ļ�������޸�ʱ�䣺"+bankNameFile.lastModified());
//		logger.debug("���������ļ����ϴζ���ʱ�䣺"+fileModifyTime);
		if(fileModifyTime != bankNameFile.lastModified())
		{
			loadBankNameFile(bankNameFile);
		}
		return bankTypeName;
		
	}
	
	/**
	 * �����ַ����еĿո�
	 * @param sourceStr
	 * @return
	 */
	public static String filterBlank(String sourceStr)
	{
		if(sourceStr == null || sourceStr.length() <= 0)
		{
			return sourceStr;
		}
		
		StringBuffer strBuff = new StringBuffer();
		
		for(int i = 0; i < sourceStr.length(); i++)
		{
			char charTemp = sourceStr.charAt(i);
			if(charTemp != ' ' && charTemp != '\t')
			{
				strBuff.append(charTemp);
			}
		}
		
		return strBuff.toString();
	}
	public static void main(String[] args)
	{
		try {
			getBankTypeCodeByBankName("����");
			ArrayList al=getBankNames();
			System.out.println("1111"+al.size());
			Date d1 = new Date();
			for(int i=0;i<al.size();i++)
			{
				System.out.println((String) al.get(i));
				String a=getBankTypeCodeByBankName((String) al.get(i));
				System.out.println(a);
			}
			Date d2 = new Date();
			System.out.println(d2.getTime()-d1.getTime());
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
