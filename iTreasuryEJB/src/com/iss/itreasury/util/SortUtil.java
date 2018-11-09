package com.iss.itreasury.util;
/**
 * created by kevin(������)
 * time 2011-05-18
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.iss.itreasury.system.history.dataentity.HistoryAdviseInfo;
import com.iss.itreasury.settlement.util.NameRef;
public class SortUtil {
	/**
	 * ������������������¼���ύ��ȡ��������¼�ĺϲ�������ʱ����������ر�ע����ǣ�osList��sysList�Ĵ�ֵ���ܻ���
	 * @param osList��osList�������Ǵ�OS_HISTORYSTEP����ر��в�ѯ�õ��������ļ�¼
	 * @param sysList��sysList���ύ��ȡ�������ļ�¼
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
					System.out.print("ת��sysListΪHistoryAdviseInfo.class�쳣");					
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
