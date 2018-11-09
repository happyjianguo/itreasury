package com.iss.itreasury.integratedCredit.CreditUtil;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.iss.itreasury.ebank.fileUploadDown.util.FileType;

public class Tools {
	
//	public static Timestamp time(String year,String moth)
//	{
//		String timestamp = "";
//		函数格式：Timestamp.valueOf("2008-05-01 00:00:00")
//	    timestamp = year +  "-" + moth + "-01 00:00:00";
//		return Timestamp.valueOf(timestamp);
//	}
    public static Timestamp time(String year,String moth)
    {
        if(year==null||year.trim().equals(""))
        {
            return null;
        }
        if(moth==null||moth.trim().equals(""))
        {
        	return null;
        }
        
        Date d=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        /* 参数str在传来之前应做一下处理，并符合yyyy-MM-dd*/
        try
        {
            d=sdf.parse(year+"-"+moth);
        }
        catch(ParseException e)
        {
            e.printStackTrace();
        }
        return new Timestamp(d.getTime());
    }
    
    //返回yyyy-mm-dd
    public static String time(Timestamp t)
    {
    	String str = "";
    	int year = t.getYear()+1900;
    	int moth = t.getMonth()+1;
  
    	str = "'" + year + "-" + moth + "-" + t.getDate() + "'";
    	
    	return str;
    }
	
//  根据类型编号返回对应的类型名称
/*	public static String getFileType(long type)
	{
		String fileType = "其它资料";
		
		if(type == 1)
		{
			fileType = FileType.V1;
		}else
		if(type == 2)
		{
			fileType = FileType.V2;
		}else
		if(type == 3)
		{
			fileType = FileType.V3;
		}else if(type == 4)
		{
			fileType = FileType.V4;
		}else if(type == 5)
		{
			fileType = FileType.V5;
		}
		
		return fileType;
	}
    	*/
	public static String fileName(String fileExt)
	{
		String fileName = "";
		fileName = Tools.timeName() + "." + fileExt;
		return fileName;
	}
	
	//根据传过来的日期返回 yyyy年mm月
	public static String getDateToStr(Timestamp timestamp)
	{
		String str = "";	
		int year = timestamp.getYear()+1900;
		int moth = timestamp.getMonth()+1;
		str = year + "年" + moth + "月";
		return str;
	}
	
	//去掉文件的扩展名
	public static String getFileName(String file)
	{
		String str = "";
		if(file == null)
		{
			return "";
		}else
		{
			int i = file.lastIndexOf(".");
			//System.out.println(i);
			if(i>=0)
			{
				str = file.substring(0, i);
			}
			else
			{
				str = file;
			}
		}
		return str;
	}
	
	public static List split(String str)
	{
		System.out.println("***dwj***进入Tools类***");
		String age[] = str.split(",");
		List list = new ArrayList();
		
		for(int i = 0;i<age.length;i++)
		{
			if(!"".equals(age[i].trim()))
			{
				list.add(age[i]);
			}
		}
		
		if(list.size()<=0)
		{
			list = null;
		}
		return list;
	}
	
	//把数组中的字符串以逗号分割
	public static String strArray(String str[])
	{
		String strs = "";
		//System.out.println("数组长度：" + str.length);
		if(str!=null)
		{
			for(int i=0;i<str.length;i++)
			{
				if(str[i]!=null)
				{
					if(!"".equals(str[i].trim()))
					{
						strs = strs + str[i] + ",";
					}
				}
			}
		}
		if(strs.length()>0)
		{
			strs = strs.substring(0, strs.length()-1);
		}
		
		return strs;
	}
	
	private static String timeName()
	{
		String timeName = "";
		Date date = new Date();
		long l = date.getTime();
		timeName = Long.toString(l);
		return timeName;
	}
	

	 /**
	  * 作者：杜维君
	  * 说明：去掉字符串中的逗号
	  */
	 public String splitComma(String str)
	 {
		 String str1 = "";
		 
		 if(str!=null)
		 {
			 String age[] = str.split(",");
			 
			 if(age!=null)
			 {
				 for(int i=0;i<age.length;i++)
				 {
					 str1 = str1 + age[i];
				 }	 
			 }	 
		 }
		  
		 return str1;
	 }
	
	

	
	//测试
	public static void main(String[] ages)
	{
		
		String str[] = {"  ","  ","   "};
		String str1 = Tools.strArray(str);
		System.out.println(str1);
//		String str = "";
//		
//		List list = new ArrayList();
//		
//		list = Tools.split(str);
//		
//		System.out.println(list);
		//Tools.time("2008", "06");
		//System.out.println(Tools.time("2008", "06"));
		
//		String str = Tools.timeName();
//		System.out.println(str);
//		str = Tools.fileName("kdsf");
//		System.out.println(str);
//		Tools.time("2008", "06");
//		System.out.println(Tools.time("2008", "06"));
//		
//		String str = Tools.getFileType(1);
//		System.out.println(str);
//		str = Tools.getFileType(2);
//		System.out.println(str);
//		str = Tools.getFileType(3);
//		System.out.println(str);
//		str = Tools.getFileType(4);
//		System.out.println(str);
//		str = Tools.getFileType(5);
//		System.out.println(str);
//		str = Tools.getFileType(6);
//		System.out.println(str);
		
//		Timestamp timestamp = null;
//		timestamp = Timestamp.valueOf("2008-05-05 00:00:00");
//		String str = Tools.time(timestamp);
//		System.out.println("shijian: " + str);
		
//		System.out.println(timestamp.getYear()+1900);
//		System.out.println(timestamp.getMonth()+1);
//		System.out.println(timestamp.getDay());
//	    timestamp = year +  "-" + moth + "-01 00:00:00";
//		return Timestamp.valueOf(timestamp);
		
//		String str = null;
//		String str2 = Tools.getFileName(str);
//		System.out.println(str2);
//		
//		String aa = Tools.fileName("exe");
//		System.out.println(aa);
//		
//		
//		System.out.println(Tools.getFileType(1));
	}
	
}
