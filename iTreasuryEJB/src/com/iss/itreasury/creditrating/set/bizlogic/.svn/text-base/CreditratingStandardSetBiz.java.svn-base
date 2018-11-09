package com.iss.itreasury.creditrating.set.bizlogic;


import java.util.ArrayList;

import com.iss.itreasury.creditrating.set.dao.CreditratingStandardSetDao;
import com.iss.itreasury.creditrating.set.dataentity.CreditratingStandardInfo;
import com.iss.itreasury.creditrating.set.dataentity.CreditratingSubStandardInfo;

import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.system.dao.PageLoader;

public class CreditratingStandardSetBiz 
{
	
	private static Log4j log4j = null;
	
	public CreditratingStandardSetBiz()
	{
		log4j = new Log4j(Constant.ModuleType.LOAN, this);
	}
	
	public PageLoader queryBySearchPage(CreditratingStandardInfo info) throws Exception
	{
		PageLoader pageLoader = null;
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("");
		try
		{
			pageLoader = standardsetdao.searchstandardinfo(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return pageLoader;
	}
	
	public PageLoader querySubdetail(CreditratingSubStandardInfo info) throws Exception
	{
		PageLoader pageLoader = null;
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("");
		try
		{
			pageLoader = standardsetdao.searchsubstandardinfo(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return pageLoader;
	}
	
	public long saverecord(CreditratingStandardInfo info) throws Exception
	{
		long countrecord=-1;
		
		String checkstate="true";
		
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("CRERT_STANDARDRATING");
		try
		{
			checkstate=standardsetdao.checknamainuse(info.getName().trim(),"CRERT_STANDARDRATING",-1,info.getId());
			if(checkstate=="false")
			{
				return countrecord=-2;
			}
			countrecord=standardsetdao.saverecord(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		
		return countrecord;
	}
	
	public long saverdetailecord(CreditratingSubStandardInfo info) throws Exception
	{
		long countrecord=-1;
		String checkstate="";
		
		String checkmix=String.valueOf(info.getMixvalue());
		String checkmax=String.valueOf(info.getMaxvalue());
		System.out.println(checkmix);
		System.out.println(checkmax);
		
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("CRERT_SUBSTANDARDRATING");
		try
		{
			checkstate=standardsetdao.checknamainuse(info.getName().trim(),"CRERT_SUBSTANDARDRATING",-1,info.getStandardratingid());
			if(checkstate=="false")
			{
				return countrecord=-2;
			}
			if(info.getMixvalue()!=-99999&&info.getMaxvalue()==-99999)
			{
				checkstate=standardsetdao.checkmixvalue(info,-1);
			}
			else if(info.getMixvalue()==-99999&&info.getMaxvalue()!=-99999)
			{
				checkstate=standardsetdao.checkmaxvalue(info,-1);
			}
			else if(info.getMixvalue()!=-99999&&info.getMaxvalue()!=-99999)
			{
				checkstate=standardsetdao.checkall(info,-1);
			}
			else
			{
				checkstate="true";
			}
			if(checkstate=="true")
			{
				countrecord=standardsetdao.savedetailrecord(info);
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		
		return countrecord;
	}
	
	
	
	public CreditratingStandardInfo searchstandardinfobyid(CreditratingSubStandardInfo info) throws Exception
	{
		CreditratingStandardInfo queryinfo=new CreditratingStandardInfo();
		
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao();
		try
		{
			queryinfo=standardsetdao.searchstandardinfobyid(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		
		return queryinfo;
	}
	
	
	public CreditratingSubStandardInfo searchsubstandardinfobyid(CreditratingSubStandardInfo info) throws Exception
	{
		CreditratingSubStandardInfo queryinfo=new CreditratingSubStandardInfo();
		
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao();
		try
		{
			queryinfo=standardsetdao.searchsubstandardinfobyid(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		
		return queryinfo;
	}
	public String updaterecord(CreditratingStandardInfo info) throws Exception
	{
		String checkstate="true";
		
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("CRERT_STANDARDRATING");
		try
		{
			checkstate=standardsetdao.checknamainuse(info.getName(), "CRERT_STANDARDRATING", info.getId(),info.getId());
			if(checkstate=="true")
			{
				standardsetdao.updaterecord(info);
			}
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
	return checkstate;
		
	}
	
	public String updaterdetailecord(CreditratingSubStandardInfo info) throws Exception
	{
		String checkstate="true";
		
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("CRERT_SUBSTANDARDRATING");
		try
		{
			checkstate=standardsetdao.checknamainuse(info.getName().trim(),"CRERT_SUBSTANDARDRATING",info.getId(),info.getStandardratingid());
			if(checkstate=="false")
			{
				return checkstate="nameinuse";
			}
			if(info.getMixvalue()!=-99999&&info.getMaxvalue()==-99999)
			{
				checkstate=standardsetdao.checkmixvalue(info,info.getId());
			}
			else if(info.getMixvalue()==-99999&&info.getMaxvalue()!=-99999)
			{
				checkstate=standardsetdao.checkmaxvalue(info,info.getId());
			}
			else if(info.getMixvalue()!=-99999&&info.getMaxvalue()!=-99999)
			{
				checkstate=standardsetdao.checkall(info,info.getId());
			}
			else
			{
				checkstate="true";
			}
			if(checkstate=="true")
			{
				standardsetdao.updatedetailrecord(info);
			}
			
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return checkstate;
	}
	
	public String delrecord(CreditratingStandardInfo info) throws Exception
	{
		String checkstate="true";
		ArrayList list=new ArrayList();
		
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("CRERT_STANDARDRATING");
		
		try
		{
			checkstate=standardsetdao.checkstanddel(info.getId(), "standardrating");
			if(checkstate=="false")
			{
				return checkstate;
			}
			standardsetdao.delrecord(info);
			list=standardsetdao.searchsubstandardinfobyid(info);
			System.out.println(list.size());
			for(int i=0;i<list.size();i++)
			{
				CreditratingSubStandardInfo subinfo=new CreditratingSubStandardInfo();
				subinfo=(CreditratingSubStandardInfo)list.get(i);
				
				//System.out.println(info.getInputuserid());
				subinfo.setOfficeid(info.getOfficeid());
				subinfo.setInputuserid(info.getInputuserid());
				subinfo.setCurrencyid(info.getCurrencyid());
				subinfo.setState(Constant.RecordStatus.INVALID);
				subinfo.setInputdate(info.getInputdate());
				deldetailrecord(subinfo);
			}
			
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		
		return checkstate;
	}
	
	public String deldetailrecord(CreditratingSubStandardInfo info) throws Exception
	{
		
		String checkstate="true";
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("CRERT_SUBSTANDARDRATING");
		try
		{
			checkstate=standardsetdao.checkstanddel(info.getId(), "substandardrating");
			if(checkstate=="false")
			{
				return checkstate;
			}
			standardsetdao.deldetailrecord(info);
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return checkstate;
		
	}
	
	public String checkstanddel(long id,String table_name) throws Exception
	{
		
		String checkstate="true";
		CreditratingStandardSetDao standardsetdao = new CreditratingStandardSetDao("");
		try
		{
			checkstate=standardsetdao.checkstanddel(id, table_name);
			if(checkstate=="false")
			{
				return checkstate;
			}
			
		}
		catch (Exception e)
		{
			log4j.error(e.toString());
			throw new IException("Gen_E001");
		}
		return checkstate;
		
	}
	

}
