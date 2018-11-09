package com.iss.itreasury.util;
/**
 * created by kevin(刘连凯)
 * time 2011-05-18
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.iss.itreasury.system.history.dataentity.HistoryAdviseInfo;
import com.iss.itreasury.settlement.util.NameRef;
public class SortUtil {
	/**
	 * 功能描述：对审批记录、提交和取消审批记录的合并，并按时间进行排序，特别注意的是，osList和sysList的传值不能混乱
	 * @param osList：osList的内容是从OS_HISTORYSTEP等相关表中查询得到的审批的记录
	 * @param sysList：sysList是提交和取消审批的记录
	 * @return
	 */
	public static List AdviseRecordSort(List osList,List sysList){
		List list=new ArrayList();
		List originalList=new ArrayList();
		if(osList==null&&sysList==null){
			return null;
		}
		HistoryAdviseInfo info=null;
		List osVector = new ArrayList();
		if ( (osList!=null)&&(osList.size()>0) )
		{ 			
				
			Iterator osit = osList.iterator();
	   		while(osit.hasNext())
			{
			    info=new HistoryAdviseInfo();
				Object[] string = new Object[5];
				string = (Object[])osit.next();					
				info.setOperator(NameRef.getUserNameByID(Long.parseLong((String)string[0])));
				info.setOpTime((java.sql.Timestamp)string[1]);
				info.setAction(DataFormat.formatNullString((String)string[4]));
				info.setAdvise(DataFormat.formatNullString((String)string[2])+DataFormat.formatNullString((String)string[3]));
				osVector.add(info);
			}
		}
		int index=0;
		if(osVector!=null&&osVector.size()>0){
			for(int i=0;i<osVector.size();i++){
				originalList.add(index++, osVector.get(i));
			}		
		}		
		if(sysList!=null&&sysList.size()>0){
			for(int y=0;y<sysList.size();y++){
				try{
					Iterator sysit = ((List)sysList.get(y)).iterator();
			   		while(sysit.hasNext())
					{
					    info=(HistoryAdviseInfo)sysit.next();				
					    originalList.add(index++, info);
					}
				}catch(Exception e){
					System.out.print("转换sysList为HistoryAdviseInfo.class异常");					
				}				
			}			
		}			
		list=Sort(originalList);
		return list;
		
	}
	public static List Sort(List list){		
		List templist=new ArrayList(1);
		for(int i=0 ; i < list.size() ; ++i){			 
			 for(int j=0; j <list.size() - i - 1; ++j){
				 if(((HistoryAdviseInfo)list.get(j)).getOpTime().compareTo(((HistoryAdviseInfo)list.get(j+1)).getOpTime())>=0){					
					 templist.add(list.get(j));					
					 list.set(j, list.get(j+1));					
					 list.set(j+1, templist.get(0)); 
					 templist.clear();
                }
		     }
          }		
		 return list;		
	}	
}
