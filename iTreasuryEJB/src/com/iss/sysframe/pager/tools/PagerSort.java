package com.iss.sysframe.pager.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.ResultInfo;
import com.iss.sysframe.util.DataFormat;

public class PagerSort {
	
	
	/**
	 * ��PagerInfo�еĽ����������
	 * @param sortName
	 * @param sortOrder
	 * @param firstNum
	 * @param lastNum
	 * @param pagerInfo
	 * @throws Exception
	 */
	public static void sortAllPagerInfo(String sortName, String sortOrder, PagerInfo pagerInfo) throws Exception
	{
		ResultInfo sourceInfo = pagerInfo.getResultInfo();
		PagerComparator pagerComparator = null;
		
		try
		{
			if(sourceInfo != null && sourceInfo.getResultList() != null && sourceInfo.getResultList().size() > 0)
			{
				pagerComparator = new PagerComparator(sortName);
				Collections.sort(sourceInfo.getResultList(), pagerComparator);
				
				if(sortOrder.equals("desc"))
				{
					Collections.reverse(sourceInfo.getResultList());
				}

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("��PagerInfo�еĽ����������ʱ�쳣", e);
		}
	}
	/**
	 * ��PagerInfo�еĽ����������
	 * @param sortName
	 * @param sortOrder
	 * @param firstNum
	 * @param lastNum
	 * @param pagerInfo
	 * @throws Exception
	 */
	public static void sortPagerInfo(long fromIndex, long toIndex, String sortName, String sortOrder, PagerInfo pagerInfo) throws Exception
	{
		ResultInfo sourceInfo = pagerInfo.getResultInfo();
		PagerComparator pagerComparator = null;
		List subList = null;
		
		int endIndex = 0;
		
		try
		{
			if(sourceInfo != null && sourceInfo.getResultList() != null && sourceInfo.getResultList().size() > 0)
			{
				pagerComparator = new PagerComparator(sortName);
				Collections.sort(sourceInfo.getResultList(), pagerComparator);
				
				if(sortOrder.equals("desc"))
				{
					Collections.reverse(sourceInfo.getResultList());
				}
				
				if(toIndex < sourceInfo.getResultList().size())
				{
					endIndex = (int)toIndex;
				}
				else
				{
					endIndex = sourceInfo.getResultList().size();
				}
				
				//���н���ĺϼ�ͳ��
				
				//�ϼ�
				String strUsertext = null;//���	pagerInfo��usertext������
				String[] singleTexts=null;//���pagerInfo��usertext�����ݰ���;���ָ��Ľ����
					
				if( pagerInfo.getUsertext()!=null && !pagerInfo.getUsertext().equals("")){
					strUsertext = pagerInfo.getUsertext();
					if(strUsertext.indexOf("{")>0&&strUsertext.indexOf("}")>strUsertext.indexOf("{")){
	
						singleTexts=strUsertext.split(";");
						int singleTextNum=singleTexts.length; //���pagerInfo��usertext�����ݰ���;���ָ��Ľ������Ŀ;
						
						StringBuffer textBuffer= new StringBuffer(); 
						
						for(int i=0;i<singleTextNum;i++){
							
							//��Ż��ܵ��ֶ�
							String displayName = singleTexts[i].substring(singleTexts[i].indexOf("{") + 1, singleTexts[i].indexOf("}"));
							
					    	Iterator sourceIter = sourceInfo.getResultList().iterator();
					    	
					    	String methodName = "get"+displayName.substring(0,1).toUpperCase()+ displayName.substring(1);
					    	//Class actionClazz = sourceInfo.getResultClass();
					    	
					    	Method execMethod = sourceInfo.getResultClass().getMethod(methodName, new Class[]{});//�õ������÷�����method����
					    	
					    	double dataTotal = 0.00;
					    	while(sourceIter.hasNext()){
					    		Object actionObj = sourceIter.next();
					    		
					    		dataTotal = dataTotal;// + Double.valueOf(execMethod.invoke(actionObj, null).toString());
					    		
					    	}
					    	
					    	singleTexts[i] = "<b>"+singleTexts[i].substring(0, singleTexts[i].indexOf("{"))+"</b><em>"+DataFormat.formatAmount(dataTotal, 2)+"</em>";
							if(i>1&&(i)%3==0){
								textBuffer.append("<br>&nbsp;&nbsp;");
							}else{
								textBuffer.append("&nbsp;&nbsp;");
							}
							textBuffer.append(singleTexts[i]);
						}

						pagerInfo.setUsertext(textBuffer.toString());
					}
				}
			
				subList  = sourceInfo.getResultList().subList((int)fromIndex, endIndex);
				sourceInfo.setResultList(subList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("��PagerInfo�еĽ����������ʱ�쳣", e);
		}
	}
	
	/**
	 * ��һ��List�еĽ����������
	 * @param sortName
	 * @param sortOrder
	 * @param fromIndex
	 * @param toIndex
	 * @param list
	 * @throws Exception
	 */
	public static void sortList(String sortName, String sortOrder, long fromIndex, long toIndex, List list) throws Exception
	{
		PagerComparator pagerComparator = null;
		List subList = null;
		
		int endIndex = 0;
		
		try
		{
			if(list != null && list.size() > 0)
			{
				pagerComparator = new PagerComparator(sortName);
				Collections.sort(list, pagerComparator);
				
				if(sortOrder.equals("desc"))
				{
					Collections.reverse(list);
				}
				
				if(toIndex < list.size())
				{
					endIndex = (int)toIndex;
				}
				else
				{
					endIndex = list.size();
				}
				
				subList  = list.subList((int)fromIndex, endIndex);
				list = new ArrayList(subList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("��PagerInfo�еĽ����������ʱ�쳣", e);
		}
	}

}
