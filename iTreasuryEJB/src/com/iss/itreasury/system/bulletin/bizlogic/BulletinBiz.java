/*
 * Created on 2006-9-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.iss.itreasury.system.bulletin.bizlogic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.iss.itreasury.ebank.obfinanceinstr.bizlogic.QueryCheckInfoBiz;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.FinanceInfo;
import com.iss.itreasury.ebank.obfinanceinstr.dataentity.Query_FinanceInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.ebank.util.OBSignatureConstant;
import com.iss.itreasury.safety.facade.SecurityFacadeInterface;
import com.iss.itreasury.safety.facade.factory.SecurityFacadeFactory;
import com.iss.itreasury.settlement.util.NameRef;
import com.iss.itreasury.system.bulletin.dataentity.BulletinInfo;
import com.iss.itreasury.system.bulletin.dao.BulletinDao;
import com.iss.itreasury.util.IException;
import com.iss.sysframe.pager.dataentity.PagerDepictBaseInfo;
import com.iss.sysframe.pager.dataentity.PagerInfo;
import com.iss.sysframe.pager.dataentity.PagerTypeConstant;
import com.iss.sysframe.pager.dataentity.ResultPagerRowInfo;
import com.iss.sysframe.pager.tools.PagerTools;
import com.iss.system.dao.PageLoader;

import java.lang.String;

import javax.servlet.ServletRequest;


/**
 * @author lenovo
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class BulletinBiz {
	
	BulletinDao dao = new BulletinDao();
	
	//新增公告信息，将info里的信息insert到表bulletin里
	public long add(BulletinInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			BulletinDao dao = new BulletinDao();
			lret = dao.add(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	修改公告信息，将info里的信息update到表bulletin里
	public long modify(BulletinInfo info) throws Exception
	{
		long lret = -1;
		try
		{
			BulletinDao dao = new BulletinDao();
			lret = dao.modify(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return lret;
	}
	
	//	查询公告信息，按info里的信息查询表bulletin
	//	返回由BulletinInfo组成的集合
	public Collection findByCondition(BulletinInfo info) throws Exception
	{
		Vector vret = null;
		try
		{
			BulletinDao dao = new BulletinDao();
			vret = dao.findByCondition(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return vret;
	}
	
	/**
	 * modify by leiyang 20081120
	 * 网银查找公告信息新方法
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public String findByBulletinString(BulletinInfo info)  throws Exception{
		String strBulletin = "";
		try
		{
			BulletinDao dao = new BulletinDao();
			strBulletin = dao.findByBulletinString(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		return strBulletin;
	}

	public PageLoader findWithPage(BulletinInfo info) throws Exception
	{
		PageLoader loader = null;
		
		try
		{
			BulletinDao dao = new BulletinDao();
			loader = dao.findWithPage(info);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IException("Gen_E001");
		}
		System.out.print("执行成功");
		System.out.print(loader);
		return loader;
	}
	public static void main(String[] args){
		//Calendar rightNow = Calendar.getInstance();
	

		// System.out.println(rightNow.get(1));
		// System.out.println(rightNow.get(2));
		// System.out.println(rightNow.get(5));
		/*Vector v=new Vector();
		BulletinInfo info=new BulletinInfo();
		BulletinDao bd=new BulletinDao();
		try {
			v=bd.findByCondition(info);
			System.out.println("查询成功");
			
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}*/
		BulletinInfo info=new BulletinInfo();
		info.setModifyId(3);
		info.setClients("01-0001");
		BulletinBiz bd=new BulletinBiz();
		try {
			bd.findWithPage(info);
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

	public PagerInfo query(BulletinInfo info) throws Exception {
		// TODO Auto-generated method stub
		PagerInfo pagerInfo = null;
		String sql = null;
		try {
			pagerInfo = new PagerInfo();
			sql = dao.query(info);
			pagerInfo.setSqlString(sql);
			
			ArrayList depictList = new ArrayList();
			PagerDepictBaseInfo baseInfo = null;
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("id");
			baseInfo.setExtension(true);
			baseInfo.setExtensionName(new String[]{"id", "id"});
			baseInfo.setExtensionType(new long[]{PagerTypeConstant.STRING, PagerTypeConstant.LONG});
			baseInfo.setExtensionString(PagerTypeConstant.LOGOTYPE + "," + PagerTypeConstant.LOGOTYPE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("releaseDate");
			baseInfo.setDisplayType(PagerTypeConstant.DATE);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("header");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("userName");
			baseInfo.setDisplayType(PagerTypeConstant.STRING);
			depictList.add(baseInfo);
			
			baseInfo = new PagerDepictBaseInfo();
			baseInfo.setDisplayName("STATUSID");
			baseInfo.setDisplayType(PagerTypeConstant.FUNCTION);
			baseInfo.setExtensionMothod(BulletinBiz.class, "getStatus", new Class[]{long.class});
			depictList.add(baseInfo);
			
			pagerInfo.setDepictList(depictList);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
	
	public String getStatus(long STATUSID){
		String strSTATUS = "";
		if(STATUSID == 1){
			strSTATUS = "发布中";
	 	}
	 	else if(STATUSID==2){
	 		strSTATUS = "已结束";
	 	}
	 	else if(STATUSID==3){
	 		strSTATUS = "已取消";
	 	}
		return strSTATUS;
	}
	
	/**
	 * 公告查询
	 * @param bulletinInfo
	 * @return
	 * @throws Exception
	 */
	public PagerInfo notice(BulletinInfo bulletinInfo) throws Exception{
		PagerInfo pagerInfo = null;
		String sql = null;
		try
		{
			pagerInfo = new PagerInfo();
			Map paramMap = new HashMap();
			paramMap.put("bulletinInfo", bulletinInfo);
			
			sql = "select 1 from userinfo";
			
			//得到查询SQL
			pagerInfo.setSqlString(sql);
			
			pagerInfo.setExtensionMothod(BulletinBiz.class, "noticeResultSetHandle", paramMap);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("====>查询异常", e);
		}
		return pagerInfo;
	}
    
	/**
	 * 公告查询
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public ArrayList noticeResultSetHandle(ResultSet rs, Map paramMap) throws Exception{
		
		ArrayList resultList = new ArrayList(); //最终返回结果
		ArrayList cellList = null;//列
		ResultPagerRowInfo rowInfo = null;//行
		String strBulletin = "";
		BulletinBiz bulletinBiz=new BulletinBiz();
		BulletinInfo bulletinInfo = (BulletinInfo)paramMap.get("bulletinInfo");
		strBulletin = bulletinBiz.findByBulletinString(bulletinInfo);
		
		try{
			
			if(!strBulletin.equals("")){
				strBulletin = strBulletin.replaceAll("\n","<br>");
				strBulletin = strBulletin.replaceAll(" ","&nbsp;");
				String 	bulletins[] = strBulletin.split("::");
				for(int i=0; i<bulletins.length; i++){
			    	String bulletin_context[] = bulletins[i].split(";;");
			    	//存储列数据
					cellList = new ArrayList();
					PagerTools.returnCellList(cellList,bulletin_context[0]);		
					PagerTools.returnCellList(cellList,bulletin_context[1]);
					//存储行数据
					rowInfo = new ResultPagerRowInfo();
					rowInfo.setCell(cellList);
					//返回数据
					resultList.add(rowInfo);
				}
			}
			    	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
		return resultList;
	
	}
	
}
