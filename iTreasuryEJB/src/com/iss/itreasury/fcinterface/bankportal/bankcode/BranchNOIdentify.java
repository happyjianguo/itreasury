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
	 * 银行类别文件路径
	 */
	private static String  fileRootName = "bank_type.property";

	/**
	 * 当前银行名称文件的缓存，存储所有银行的名称及其别名
	 * 当前按二维数组的形式存放：
	 * 第一维按银行类型区分，第二维的第一个元素存放当前银行的标准名称（允许为空），其他均为其别名
	 */
	private static ArrayList bankType = null;
	/**
	 * 当前银行名称文件的修改时间，当文件被修改后，用来动态加载该文件
	 */

	private static long fileModifyTime = -1;
	
	/**
	 * 当前银行名称文件的缓存，存储所有银行的标准名称和行别号
	 */
	
	private static ArrayList bankTypeName =null;
	/**
	 * 从文件加载银行名称到缓存中
	 *
	 */
	private static void loadBankNameFile(File bankNameFile) throws Exception
	{
		bankType = new ArrayList(32);
		bankTypeName = new ArrayList(15);
		if(!bankNameFile.exists())
		{
			throw new Exception("银行类别配置文件"+bankNameFile.getName()+"不存在");
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
				    				errMsg.append("银行类别配置文件"+bankNameFile.getName()+"不合法\n");
				    			}
				    			errMsg.append("第"+lineNum+"行第"+matchNum+"个名称与第"+(i+1)+"行第"+(j+1)+"个名称重复\n");
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
    				errMsg.append("银行类别配置文件"+bankNameFile.getName()+"不合法\n");
    			}
				errMsg.append("第"+lineNum+"行数据无效\n");
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
				    				errMsg.append("银行类别配置文件"+bankNameFile.getName()+"不合法\n");
				    			}
				    			errMsg.append("第"+lineNum+"行第"+matchNum+"个名称与第"+(i+1)+"行第"+(j+1)+"个名称重复\n");
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
	 * 判断两个字符串是否相互包含
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
	 * 根据输入的银行名称获取该银行的类别编号
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
			throw new Exception("银行类别配置文件"+fileRootName+"不存在");
		}
		
//		logger.debug("银行名称文件的最后修改时间："+bankNameFile.lastModified());
//		logger.debug("银行名称文件的上次读入时间："+fileModifyTime);
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
//		    			logger.debug("“"+branchName+"”与第"+(i+1)+"行第"+(j+1)+"个名称匹配");
		    			return (String)al.get(1);
		    		}
	    		}
	    	}
	    }
		
		return null;
	}
	
	/**
	 * 根据输入的银行名称获取该银行的标准名称
	 * @param branchName
	 * @return
	 * @throws Exception
	 */
	public static String getStanderBankNameByBankName(String inputName)throws Exception
	{
		String branchName = filterBlank(inputName);
		if(branchName == null || branchName.length() <=0 )
		{
			throw new Exception("输入的参数为空");
		}
		
		File bankNameFile = new File(fileRootName);
		if(!bankNameFile.exists())
		{
			throw new Exception("银行类别配置文件"+fileRootName+"不存在");
		}
		
//		logger.debug("银行名称文件的最后修改时间："+bankNameFile.lastModified());
//		logger.debug("银行名称文件的上次读入时间："+fileModifyTime);
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
//		    			logger.debug("“"+branchName+"”与第"+(i+1)+"行第"+(j+1)+"个名称匹配");
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
			throw new Exception("银行类别配置文件"+fileRootName+"不存在");
		}
		
//		logger.debug("银行名称文件的最后修改时间："+bankNameFile.lastModified());
//		logger.debug("银行名称文件的上次读入时间："+fileModifyTime);
		if(fileModifyTime != bankNameFile.lastModified())
		{
			loadBankNameFile(bankNameFile);
		}
		return bankTypeName;
		
	}
	
	/**
	 * 过滤字符串中的空格
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
			getBankTypeCodeByBankName("工行");
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
