package com.iss.itreasury.project.gzbfcl.settlement.bizlogic;


import java.util.Collection;
import java.util.Iterator;


import com.iss.itreasury.project.gzbfcl.settlement.dao.FoundsdispatchDao;
import com.iss.itreasury.project.gzbfcl.settlement.dao.FoundsdispatchDetailDao;
import com.iss.itreasury.project.gzbfcl.util.CreateCode;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FoundsdispatchDetailInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.FoundsdispatchInfo;
import com.iss.itreasury.project.gzbfcl.settlement.dataentity.QueryFoundsdispatchDetailInfo;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.DataFormat;
import com.iss.system.dao.PageLoader;




public class FoundsdispatchBiz {
	
  	private static FoundsdispatchBiz fdSave = null;
	public static FoundsdispatchBiz getInstence()
	{
		if(fdSave ==null)
		{
			fdSave = new FoundsdispatchBiz();
		}
		return fdSave;
	}
	/**
	 * �����ʽ������
	 * 
	 * ����������������ʽ����������������ʽ��������룬�����ʽ��������Ϣ��Ȼ�������ʽ��������ϸ��Ϣ���ʽ���������˫����Ϣ��
	 * 
	 * ������޸ı��棬�ȸ��������ʽ��������Ϣ��
	 * 			Ȼ�󽫴��ʽ�������������ϸ��Ϣ״̬��Ϊ0
	 * 			Ȼ�����Ҫ�������ϸ��Ϣ�Ƿ���id��ѡ��������е���ϸ��Ϣ������������ϸ��Ϣ
	 */
	 public synchronized long saveFoundsdispatchInfo(FoundsdispatchInfo info) throws Exception
	 {
		 FoundsdispatchDao dao=new FoundsdispatchDao();
		 FoundsdispatchDetailDao detaildao=new FoundsdispatchDetailDao();
		 //FoundsdispatchDetailInfo detailinfo=new FoundsdispatchDetailInfo();
		 long foundsDispatchID=-1;
		 long foundsDispatchDetailID=-1;
		 Collection list = null;
		 if(info.getId()<0){
			
			 //�����ʽ���������
			String lTmpCode="";
			CreateCode createcode=new CreateCode();
			lTmpCode=createcode.createFoundsDispatatchCode(Long.valueOf((DataFormat.getDateString(info.getInputTime())).substring(0, 4)).longValue(),info.getCurrentID(),info.getOfficeID());
			info.setFoundsDispatchCode(lTmpCode);
			
			foundsDispatchID=dao.add(info);
			list = info.getDetail();
			Iterator it= list.iterator();
			while(it.hasNext())
			{
				FoundsdispatchDetailInfo detailinfo=(FoundsdispatchDetailInfo)it.next();
				
				detailinfo.setFoundsDispatchID(foundsDispatchID);
			

				foundsDispatchDetailID=detaildao.add(detailinfo);
	
			}
			
			
		 }
		 else{
			 
			 dao.update(info);
			 list = info.getDetail();
			 Iterator it= list.iterator();
			 //�Ƚ������ʽ��������ϸ״̬ȫ��Ϊ0�������±������������޸ĵ���ϸ��Ϣ
			 Collection list2 = null;
			 list2 =this.findFoundsdispatchDetailInfoByFoundsdipatchID(info.getId());
			 Iterator it2= list2.iterator();
			 while(it2.hasNext())
			 {
				 FoundsdispatchDetailInfo detailinfo=(FoundsdispatchDetailInfo)it2.next();
				 detailinfo.setStatusID(Constant.RecordStatus.INVALID);
				 detaildao.update(detailinfo);	 
			 }
			 
			 while(it.hasNext())
			 {
				 FoundsdispatchDetailInfo detailinfo=(FoundsdispatchDetailInfo)it.next();
				 
				 detailinfo.setFoundsDispatchID(info.getId());
				 detailinfo.setStatusID(Constant.RecordStatus.VALID);
			
				 if(detailinfo.getId()<0){
					 foundsDispatchDetailID=detaildao.add(detailinfo);
				 }
				 else{
					 detaildao.update(detailinfo);
				 }
				 
			 }
			 
			 foundsDispatchID=info.getId();
		 }

		 return foundsDispatchID;

				
	}
	 /*
	  * ɾ���ʽ�������������ϸ��Ϣ
	  * 
	  * 
	  * */
	 
	 public boolean deleteFoundsdispatchInfo(FoundsdispatchInfo info) throws Exception
	 {
		 FoundsdispatchDao dao=new FoundsdispatchDao();
		 FoundsdispatchDetailDao detaildao=new FoundsdispatchDetailDao();

		 dao.updateStatus(info.getId(), Constant.RecordStatus.INVALID);
		 //ɾ���ʽ������������ϸ
		 Collection detaillist = null;
		 detaillist =this.findFoundsdispatchDetailInfoByFoundsdipatchID(info.getId());
		 Iterator it2= detaillist.iterator();
		 while(it2.hasNext())
		 {
			 FoundsdispatchDetailInfo detailinfo=(FoundsdispatchDetailInfo)it2.next();
			 detaildao.updateStatus(detailinfo.getId(), Constant.RecordStatus.INVALID);	 
		 }
		 
		 return true;
	 }
	 
	 /*
	  * �ʽ������PageLoader��ҳ��ѯ
	  * 
	  * 
	  * */	 
	public PageLoader findQueryFoundsdispatchDetailInfos(QueryFoundsdispatchDetailInfo qInfo)
	{
		PageLoader pageLoader = null;
		String sql = "";
		try 
		{
			FoundsdispatchDetailDao detaildao = new FoundsdispatchDetailDao();
			sql = detaildao.getQueryFoundsdispatchDetailInfo(qInfo);
			//sql = detaildao.getFoundsdispatchInfo(qInfo);
			System.out.print(sql);
			pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
			pageLoader.initPageLoader(new AppContext(), sql, "*", "", (int) Constant.PageControl.CODE_PAGELINECOUNT, "com.iss.itreasury.project.gzbfcl.settlement.dataentity.QueryFoundsdispatchDetailInfo", null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageLoader;
	}
	 /*
	  * ͨ���ʽ������ID��ѯ��������ϸ��Ϣ
	  * 
	  * 
	  * */	 
	 public Collection findQueryFoundsdispatchDetailInfobyFoundsdipatchID(long foundsdispatchID) throws Exception
	 {
		 FoundsdispatchDetailDao detaildao=new FoundsdispatchDetailDao();
		 
		 return detaildao.findQueryFoundsdispatchDetailInfobyFoundsdipatchID(foundsdispatchID);
		 
	 }
	
	 /*
	  * ͨ���ʽ������ID��ѯ��������ϸ��Ϣ
	  * 
	  * 
	  * */
	 
	 public Collection findQueryFoundsdispatchDetailInfobyFoundsdipatchIDandConditions(QueryFoundsdispatchDetailInfo queryDetailInfo) throws Exception
	 {
		 FoundsdispatchDetailDao detaildao=new FoundsdispatchDetailDao();
		 
		 return detaildao.findQueryFoundsdispatchDetailInfobyFoundsdipatchIDandConditions(queryDetailInfo);
		 
	 }
	 
	 public FoundsdispatchInfo findFoundsdispatchInfobyID(long ID) throws Exception
	 {
		 FoundsdispatchDao dao=new FoundsdispatchDao();
		 
		 return (FoundsdispatchInfo)dao.findByID(ID,FoundsdispatchInfo.class);
		 
	 }
	 public FoundsdispatchDetailInfo findFoundsdispatchDetailInfobyID(long ID) throws Exception
	 {
		 FoundsdispatchDetailDao detaildao=new FoundsdispatchDetailDao();
		 
		 return (FoundsdispatchDetailInfo)detaildao.findByID(ID,FoundsdispatchDetailInfo.class);
		 
	 }
	 
	 /*
	  * ͨ���ʽ������ID��ѯ��������ϸ��Ϣ
	  * 
	  * 
	  * */
	 public Collection findFoundsdispatchDetailInfoByFoundsdipatchID(long ID) throws Exception
	 {
		 FoundsdispatchDetailDao detaildao=new FoundsdispatchDetailDao();
		 
		 return (Collection)detaildao.findFoundsdispatchDetailInfoByFoundsdipatchID(ID);
		 
	 }
	

}
