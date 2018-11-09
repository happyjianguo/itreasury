package com.iss.itreasury.craftbrother.transferloancontract.counterparty.bizlogic;

import java.sql.Connection;
import java.util.HashMap;

import com.iss.system.dao.PageLoader;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao.CounterparBankDao;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dao.CounterpartDao;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartBankInfo;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.CounterpartInfo;
import com.iss.itreasury.craftbrother.transferloancontract.counterparty.dataentity.QueryCounterpartInfo;
import com.iss.itreasury.craftbrother.transferloancontract.transferapply.dao.TransferApplyDao;
import com.iss.itreasury.craftbrother.transferloancontract.transfercontract.dao.TransferContractDao;
import com.iss.itreasury.craftbrother.util.CRAconstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class CounterpartBiz 
{
	/**
	 * 说明：查询交易对手
	 * 作者:minzhao
	 * 日期:Aug 5, 2009
	 * 参数:@param info
	 * 参数:@return
	 * 参数:@throws Exception
	 * 返回类型:PageLoader
	 */
	public PageLoader QueryCounterpart(QueryCounterpartInfo info) throws Exception
	{
		
		PageLoader pageloader = null;
		try
		{
			CounterpartDao countpartdao=new CounterpartDao();
			pageloader=countpartdao.queryCounterpartInfo(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("查询交易对手信息失败！");
		}
		return pageloader;
	}
	
	/**
	 * 说明：保存交易对手设置信息
	 * 作者:minzhao
	 * 日期:Aug 5, 2009
	 * 参数:@param info
	 * 参数:@return
	 * 参数:@throws IException
	 * 返回类型:long
	 */
	public long save(CounterpartInfo info) throws IException
	{
		long returnvalue=-1;
		try {
			Connection  conn = Database.getConnection();
			conn.setAutoCommit(false);
			try
			{
				CounterpartDao countpartdao=new CounterpartDao(conn);
				CounterpartInfo checkinfo=countpartdao.checkcounterpartcode(info);
				if(checkinfo.getId()>0)
				{
					throw new IException("交易对手编号重复，请重新输入！");
				}
				else
				{
					for(int i=0;i<info.getInfo().length;i++)
					{
						for(int j=info.getInfo().length-1;j>i;j--)
						{
							if(info.getInfo()[i].getCounterpartbankno().equals(info.getInfo()[j].getCounterpartbankno()))
							{
								throw new IException("交易对手银行账号重复，请重新输入！");
							}
						}
					}
					returnvalue=countpartdao.add(info);
					CounterparBankDao countdao=new CounterparBankDao(conn);
					CounterpartBankInfo bankinfo=new CounterpartBankInfo();
					for(int i=0;i<info.getInfo().length;i++)
					{
						bankinfo=info.getInfo()[i];
						bankinfo.setCounterpartid(returnvalue);
						countdao.add(bankinfo);
					}
				}
				conn.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				conn.rollback();
				throw new IException(e.getMessage());
			}
			finally
			{
				conn.close();
				conn=null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
			
		}
		
		return returnvalue;
		
		
	}
	
	public CounterpartInfo QueryCounterpartInfo(CounterpartInfo info) throws Exception
	{
		
		CounterpartInfo counterpartinfo = new CounterpartInfo();
		try
		{
			CounterpartDao countpartdao=new CounterpartDao();
			counterpartinfo=countpartdao.QueryCounterpartInfo(info);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("查询交易对手信息出错！");
		}
		return counterpartinfo;
	}
	
	
	/**
	 * 说明：更新交易对手信息
	 * 作者:minzhao
	 * 日期:Aug 10, 2009
	 * 参数:@param newinfo
	 * 参数:@param oldinfo
	 * 参数:@throws IException
	 * 返回类型:void
	 */
	public void update(CounterpartInfo newinfo,CounterpartInfo oldinfo) throws IException
	{
		
		try {
			Connection  conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			try
			{
				TransferContractDao transfercontractdao=new TransferContractDao();
				CounterpartDao countpartdao=new CounterpartDao(conn);
				CounterparBankDao countdao=new CounterparBankDao(conn);
				HashMap map=new HashMap();
				CounterpartBankInfo bankinfo=null;
				CounterpartBankInfo oldbankinfo=null;
				//判断交易对手编号是否重复
				CounterpartInfo checkinfo=countpartdao.checkcounterpartcode(newinfo);
				if(checkinfo.getId()>0)
				{
					throw new IException("交易对手编号重复，请重新输入！");
				}
				//将旧的bankinfo放在map中
				for(int i=0;i<oldinfo.getInfo().length;i++)
				{
					bankinfo=(CounterpartBankInfo)oldinfo.getInfo()[i];
					map.put(String.valueOf(bankinfo.getId()), bankinfo);
				}
				//循环判断输入的bankinfo是否重复
				for(int i=0;i<newinfo.getInfo().length;i++)
				{
					for(int j=newinfo.getInfo().length-1;j>i;j--)
					{
						if(newinfo.getInfo()[i].getCounterpartbankno().equals(newinfo.getInfo()[j].getCounterpartbankno()))
						{
							throw new IException("交易对手银行账号"+newinfo.getInfo()[j].getCounterpartbankno()+"重复，请重新输入！");
						}
					}
				}
				
				countpartdao.update(newinfo);
				
				//处理字表信息
				for(int i=0;i<newinfo.getInfo().length;i++)
				{
					bankinfo=newinfo.getInfo()[i];
					System.out.println(map.get(String.valueOf(bankinfo.getId())));
					oldbankinfo=(CounterpartBankInfo)map.get(String.valueOf(bankinfo.getId()));
					if(map.get(String.valueOf(bankinfo.getId()))!=null)
					{//该条数据在字表中存在修改操作
						//判断是否被应用
						if(transfercontractdao.checkCounterPart(bankinfo.getId())>0)
						{
							//判断该条信息是否在前台修改过
							if(!bankinfo.getCounterpartbankno().equals(oldbankinfo.getCounterpartbankno())
									||!bankinfo.getCounterpartbankname().equals(oldbankinfo.getCounterpartbankname())
									||!bankinfo.getCounterparname().equals(oldbankinfo.getCounterparname())
									||!bankinfo.getCity().equals(oldbankinfo.getCity())
									||!bankinfo.getProvince().equals(oldbankinfo.getProvince())
									)
							{
								throw new IException("交易对手银行账户号"+bankinfo.getCounterpartbankno()+"已被引用，不能修改！");
							}
						}
						else
						{
							bankinfo.setCounterpartid(newinfo.getId());
							countdao.update(bankinfo);
						}
						map.remove(String.valueOf(bankinfo.getId()));
					}
					else
					{//数据不存在新增操作
						bankinfo.setCounterpartid(newinfo.getId());
						countdao.add(bankinfo);
					}
					
				}
				//原有数据删除
				for(int i=0;i<map.size();i++)
				{
					oldbankinfo=(CounterpartBankInfo)map.values().toArray()[i];
					if(transfercontractdao.checkCounterPart(oldbankinfo.getId())>0)
					{
						throw new IException("交易对手银行账户号"+oldbankinfo.getCounterpartbankno()+"已被引用，不能删除！");
					}
					else
					{
						oldbankinfo.setStatusid(CRAconstant.TransactionType.counterpartBank.INVALID);
						countdao.update(oldbankinfo);
					}
				}
				conn.commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				conn.rollback();
				throw new IException(e.getMessage());
			}
			finally
			{
				conn.close();
				conn=null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException(e.getMessage());
			
		}
		
		
		
	}
	/**
	 * 说明：简单查询
	 * 作者:minzhao
	 * 日期:Aug 10, 2009
	 * 参数:@param info
	 * 参数:@return
	 * 参数:@throws Exception
	 * 返回类型:CounterpartBankInfo
	 */
	public CounterpartBankInfo findInfoByID(CounterpartBankInfo info) throws Exception
	{
		
		CounterpartBankInfo bankInfo = new CounterpartBankInfo();
		try
		{
			CounterparBankDao counterparbankdao=new CounterparBankDao();
			bankInfo=(CounterpartBankInfo)counterparbankdao.findByID(info.getId(), CounterpartBankInfo.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException("查询交易对手开户行出错！");
		}
		return bankInfo;
	}
	
	public long deleteCounterInfo(CounterpartInfo info) throws Exception
	{
		long counterid=-1;
		
		try 
		{
			Connection  conn = Database.getConnection();
			conn.setAutoCommit(false);
			
			TransferApplyDao applydao=new TransferApplyDao();
			TransferContractDao transfercontractdao=new TransferContractDao();
			CounterpartDao countpartdao=new CounterpartDao(conn);
			CounterparBankDao countdao=new CounterparBankDao(conn);
			
			try
			{

				info.setStatusid(CRAconstant.TransactionType.counterpartBank.INVALID);
				if(applydao.checkCounterPart(info.getId())>0)
				{
					throw new IException("交易对手已被引用，不能删除！");
				}
				countpartdao.update(info);
				for(int i=0;i<info.getInfo().length;i++)
				{
					CounterpartBankInfo bankinfo=new CounterpartBankInfo();
					bankinfo=info.getInfo()[i];
					bankinfo.setStatusid(CRAconstant.TransactionType.counterpartBank.INVALID);
					if(transfercontractdao.checkCounterPart(bankinfo.getId())>0)
					{
						throw new IException("交易对手银行账户号"+bankinfo.getCounterpartbankno()+"已被引用，不能删除！");
					}
					countdao.update(bankinfo);
				}
				
				conn.commit();
			}
			catch(Exception e)
			{
				throw new IException(e.getMessage()); 
			}
			finally
			{
				conn.close();
				conn=null;
			}
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new IException (e.getMessage());
		}
	
		return counterid;
	}

}
