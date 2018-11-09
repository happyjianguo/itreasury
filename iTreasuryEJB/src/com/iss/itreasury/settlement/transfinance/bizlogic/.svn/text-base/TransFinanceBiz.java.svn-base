package com.iss.itreasury.settlement.transfinance.bizlogic;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.iss.itreasury.loan.bizdelegation.LeaseholdNoticeDelegation;
import com.iss.itreasury.loan.contract.bizlogic.ContractBiz;
import com.iss.itreasury.loan.contract.dao.ContractDao;
import com.iss.itreasury.loan.contract.dataentity.ContractInfo;
import com.iss.itreasury.loan.contract.dataentity.RateInfo;
import com.iss.itreasury.loan.repayplan.dao.RepayPlanDao;
import com.iss.itreasury.loan.repayplan.dataentity.RepayPlanInfo;
import com.iss.itreasury.loan.util.LOANNameRef;
import com.iss.itreasury.settlement.transfinance.dao.Sett_TransReturnFinanceDao;
import com.iss.itreasury.settlement.transfinance.dataentity.TransQueryFinanceNewInfo;
import com.iss.itreasury.settlement.transfinance.dataentity.TransReturnFinanceNewInfo;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.EJBHomeFactory;
import com.iss.itreasury.util.Env;
import com.iss.itreasury.util.IException;
/**
 * @author afzhu
 * 融资租赁匡算、批量还款的业务处理类
 */
public class TransFinanceBiz {
	
	
	Sett_TransReturnFinanceDao dao=null;
	TransFinance tfejb = null;
	
	public TransFinanceBiz() throws Exception{
		
		try
		{
			TransFinanceHome home = (TransFinanceHome) EJBHomeFactory.getFactory().lookUpHome(TransFinanceHome.class);
			tfejb = (TransFinance) home.create();
		}
		catch (Exception e)
		{
			throw new Exception();
		}
	}
	public List getFinanceRentList(TransQueryFinanceNewInfo queryEntity) throws Exception
	{
		LeaseholdNoticeDelegation leaseholdNoticeDelegation = new LeaseholdNoticeDelegation();	
		ContractBiz contractBiz = new ContractBiz();
		//add by yunchang
		//date 2010-08-06
		//function 参数设置
		double surplusRecognizanceAmount = 0.0d;
		double recognizanceAmount = 0.0d;
		double allNoPayMount = 0.0d;
		String issueList = "";
		ContractInfo contractInfo = new ContractInfo();
		RepayPlanDao repayPlan = new RepayPlanDao();
		RepayPlanInfo repayPlanInfo = new RepayPlanInfo();
		List returnList = null;
		try{
			ContractDao cd = new ContractDao();
			dao=new Sett_TransReturnFinanceDao();
			List testList=dao.getFinanceRentList(queryEntity);
			returnList = new ArrayList();
			long contractIds[] = new long[testList.size()];
			String ts =queryEntity.getExecuteDate();
			//add by zwxiao 查询连续未付次数的时候使用开机时间来查询
			String systemDate = Env.getSystemDateString(queryEntity.getNofficeid(), queryEntity.getNcurrencyid());//开机时间
			TransReturnFinanceNewInfo trfn=null;
			//双层循环合并记录
			for(int i=0;i<testList.size();i++)
			{
				
				trfn=(TransReturnFinanceNewInfo)testList.get(i);
				RateInfo ri = cd.getLatelyRateForRZZL(trfn.getContractID(),DataFormat.getDateTime(queryEntity.getExecuteDate()));//获得调整后的利率
	//			RateInfo ri = cd.getLatelyRateForRZZLPlan(trfn.getContractID(),DataFormat.getDateTime(queryEntity.getExecuteDate()));//获得调整后的利率
				trfn.setSissue(new Integer(trfn.getIssue()).toString());
				trfn.setRate(ri.getRate());//设置利率
				contractIds[i]=trfn.getContractID();//合同ID数组,用来查连续未付次数和累计迟付次数
				for(int j=0;j<testList.size();j++)
				{
					if(i==j)continue;//如果是相同记录则跳过
					if(trfn.getContractID()==((TransReturnFinanceNewInfo)testList.get(j)).getContractID())//循环匹配相同合同
					{
						trfn.setSissue(trfn.getSissue()+","+((TransReturnFinanceNewInfo)testList.get(j)).getIssue());// 把期数合并
						trfn.setCurrentPaymentAmount(trfn.getCurrentPaymentAmount()+((TransReturnFinanceNewInfo)testList.get(j)).getCurrentPaymentAmount());//把本期还款余额相加
						trfn.setRepaymentBalance(trfn.getCurrentPaymentAmount());//把还款余额先默认赋值为本期还款余额总合
						trfn.setPrincipal(trfn.getPrincipal()+((TransReturnFinanceNewInfo)testList.get(j)).getPrincipal());//把本金相加
						trfn.setInterest(trfn.getInterest()+((TransReturnFinanceNewInfo)testList.get(j)).getInterest());//把利息相加
						testList.remove(j);//移除相同合同，防止重复合并
						j=j-1;//由于合并过的记录被删除，list中记录向前替换，所以要把循环从上一回开始
					}
				}
				returnList.add(trfn);//最终合并后的返回集
			}
			if(testList!=null && testList.size() > 0)
			{
				List ljcf = LOANNameRef.getContractLateSum(contractIds);//累计迟付次数
				List lxwf = LOANNameRef.getContractOutstandingSum(contractIds,systemDate);//连续未付次数
				List hkye = dao.getRepaymentBalance(contractIds);//已还款总额数
				
				//累计迟付次数
				for(int n=0;n<ljcf.size();n++)
				{
					TransReturnFinanceNewInfo trfn_ljcf=(TransReturnFinanceNewInfo)ljcf.get(n);
					for(int f=0;f<returnList.size();f++)
					{
						trfn=(TransReturnFinanceNewInfo)returnList.get(f);
						if(trfn_ljcf.getContractID()==trfn.getContractID())
						{
							trfn.setLjcf(trfn_ljcf.getLjcf());
							//add by yunchang
							//date 2010-08-05
							//添加保证金余额查询
							surplusRecognizanceAmount = leaseholdNoticeDelegation.getSurplusRecognizanceAmount(trfn.getContractID());
							trfn.setSurplusRecognizanceAmount(surplusRecognizanceAmount);
							//add by yunchang
							//date 2010-08-05
							//function 添加要求保证金金额
							contractInfo = contractBiz.findByID(trfn.getContractID());
							trfn.setRecognizanceAmount(contractInfo.getRecognizanceAmount());
							//add by yunchang
							//date 2010-08-05
							//function 离匡算日期最近的那笔收款通知单。
							repayPlanInfo = repayPlan.getLastPayMount(trfn.getContractID(), ts); 
							trfn.setLastPayMount(repayPlanInfo.getDPlanPayAmount());//本期应付租金
							trfn.setLastPayMountAll(DataFormat.formatDouble(repayPlanInfo.getDPlanPayAmount(),2)+DataFormat.formatDouble(repayPlanInfo.getMINTERESTAMOUNT(),2));//本期应付总计
							trfn.setIssuebenqi(repayPlanInfo.getIssue());//本期租金偿付对应期数
							//add by yunchang
							//date 2010-08-05
							//function 加入逾期未付租金
							allNoPayMount = repayPlan.getAllNoPayMount(trfn.getContractID(), systemDate);
							trfn.setAllNoPayMount(allNoPayMount);
							//add by yunchang
							//date 2010-08-07
							//function 计入逾期未付期数
							issueList = repayPlan.getIssueList(trfn.getContractID(), systemDate);
							trfn.setIssueList(issueList);
							returnList.set(f, trfn);							
						}
					}
					
				}
				//连续未付次数
				for(int m=0;m<lxwf.size();m++)
				{
					TransReturnFinanceNewInfo trfn_lxwf=(TransReturnFinanceNewInfo)lxwf.get(m);
					for(int h=0;h<returnList.size();h++)
					{
						trfn=(TransReturnFinanceNewInfo)returnList.get(h);
						if(trfn_lxwf.getContractID()==trfn.getContractID())
						{
							trfn.setLxwf(trfn_lxwf.getLxwf());
							//trfn.setAllNoPayMount(trfn_lxwf.getAllNoPayMount());
							returnList.set(h, trfn);
						}
					}
					
				}
				//已还款总额数
				if(hkye!=null && hkye.size()>0)
				{
					for(int m=0;m<hkye.size();m++)
					{
						TransReturnFinanceNewInfo trfn_hkye=(TransReturnFinanceNewInfo)hkye.get(m);
						for(int h=0;h<returnList.size();h++)
						{
							trfn=(TransReturnFinanceNewInfo)returnList.get(h);
							if(trfn_hkye.getContractID()==trfn.getContractID())
							{
								trfn.setRepaymentBalance(trfn.getMexamineamount()-trfn_hkye.getAmountfrom());//批准金额减去已还款总额
								returnList.set(h, trfn);
							}
//							else
//							{
//								trfn.setRepaymentBalance(trfn.getMexamineamount());//批准金额减去已还款总额
//								returnList.set(h, trfn);
//							}
						}
						//hkye.remove(m);
						
					}
				}
				else
				{
					//double countMoney = 0.0;
					for(int m=0;m<returnList.size();m++)
					{
						TransReturnFinanceNewInfo trfn_hkye=(TransReturnFinanceNewInfo)returnList.get(m);
						trfn_hkye.setRepaymentBalance(trfn_hkye.getMexamineamount());
						returnList.set(m, trfn_hkye);
						/*countMoney = trfn_hkye.getCurrentPaymentAmount();//本期还款余额
						for(int h=0;h<returnList.size();h++)
						{
							if(m==h)continue;
							trfn=(TransReturnFinanceNewInfo)returnList.get(h);
							if(trfn_hkye.getContractID()==trfn.getContractID())
							{
								countMoney  += trfn.getCurrentPaymentAmount();//把同一笔合同的本期应还余额相加
							}
						}
						trfn_hkye.setRepaymentBalance(countMoney);
						returnList.set(m, trfn_hkye);*/
					}
						
					
				}
						
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return returnList;
		
	}
	/**
	 * @author afzhu
	 * @param contractIds 合同ID字符串
	 * @return
	 * 通过合同ID查询融资租赁匡算实体
	 */
	public List getFinanceRentListByContractId(String contractIds,int flag,String exeDate) throws Exception
	{
		
		List returnList = new ArrayList();
		try{
		String sContractId[] = contractIds.split(",");
		int contractId[] = new int[sContractId.length];
		for(int i=0;i<sContractId.length;i++)
		{
			contractId[i]=Integer.parseInt(sContractId[i]);
		}
		dao=new Sett_TransReturnFinanceDao();
		List testList = dao.getFinanceRentListByContractId(contractId,exeDate);
		if(flag==1)return testList;
		TransReturnFinanceNewInfo trfn=null;
		//双层循环合并记录
		for(int i=0;i<testList.size();i++)
		{
			trfn=(TransReturnFinanceNewInfo)testList.get(i);
			trfn.setSissue(new Integer(trfn.getIssue()).toString());
			for(int j=0;j<testList.size();j++)
			{
				if(i==j)continue;//如果是相同记录则跳过
				if(trfn.getContractID()==((TransReturnFinanceNewInfo)testList.get(j)).getContractID())//循环匹配相同合同
				{
					trfn.setSissue(trfn.getSissue()+","+((TransReturnFinanceNewInfo)testList.get(j)).getIssue());// 把期数合并
					trfn.setCurrentPaymentAmount(trfn.getCurrentPaymentAmount()+((TransReturnFinanceNewInfo)testList.get(j)).getCurrentPaymentAmount());//把本期还款余额相加
					testList.remove(j);//移除相同合同，防止重复合并
					j=j-1;//由于合并过的记录被删除，list中记录向前替换，所以要把循环从上一回开始
				}
			}
			returnList.add(trfn);//最终合并后的返回集
			
			/************/
			//测试数据
			/*
			for(int j=0;j<10;j++)
			{
			TransReturnFinanceNewInfo trfn1=new TransReturnFinanceNewInfo();
			trfn1.setClientName(trfn.getClientName());
			trfn1.setContractID(trfn.getContractID());
			trfn1.setContractCode(trfn.getContractCode());
			trfn1.setStartDate(trfn.getStartDate());
			returnList.add(trfn1);
			}
			*/
			//测试数据
			/************/
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return returnList;
		
	}
	/**
	 * @author afzhu
	 * @return
	 * 查询合同打印最大流水号
	 */
	public TransReturnFinanceNewInfo.SubReturnFinance getContractNum() throws Exception
	{
		
		TransReturnFinanceNewInfo.SubReturnFinance subrf =null;
		try
		{
		dao=new Sett_TransReturnFinanceDao();
		subrf= dao.getContractNum();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return subrf;
	}
	
	/**
	 * @author afzhu
	 * @param sub 合同存储流水号实体(内部类)
	 * @throws Exception
	 */
	public void saveFinance(TransReturnFinanceNewInfo.SubReturnFinance sub) throws Exception
	{
		try
		{
		dao=new Sett_TransReturnFinanceDao();
		dao.saveFinance(sub);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		
	}
	
	/**
	 * @author afzhu
	 * @param queryEntity 查询条件实体
	 * @return 返回批量还款List
	 * @throws Exception
	 */
	public List getQuantityRepaymentList(TransQueryFinanceNewInfo queryEntity) throws Exception
	{
			
		List returnList = null;
		try{
			
			dao=new Sett_TransReturnFinanceDao();
			System.out.println("========================查询批量还款实体列表==========================");
			returnList=dao.getFinanceRentList(queryEntity);
			System.out.println("========================查询批量还款实体列表==========================");
			long contractIds[] = new long[returnList.size()];
			String ts =queryEntity.getExecuteDate();
			//add by zwxiao 查询连续未付次数的时候使用开机时间来查询
			String systemDate = Env.getSystemDateString(queryEntity.getNofficeid(), queryEntity.getNcurrencyid());//开机时间
			TransReturnFinanceNewInfo trfn=null;
			for(int i=0;i<contractIds.length;i++)
			{
				trfn = (TransReturnFinanceNewInfo)returnList.get(i);
				contractIds[i]= trfn.getContractID();
			}
			if(returnList!=null && returnList.size()>0)
			{
					System.out.println("========================获取累计迟付次数==========================");
					List ljcf = LOANNameRef.getContractLateSum(contractIds);//累计迟付次数
					System.out.println("========================获取累计迟付次数==========================");
					System.out.println("========================获取连续未付次数==========================");
					List lxwf = LOANNameRef.getContractOutstandingSum(contractIds,systemDate);//连续未付次数
					System.out.println("========================获取连续未付次数==========================");
					System.out.println("========================获取已还款总额数==========================");
					List hkye = dao.getRepaymentBalance(contractIds);//已还款总额数
					System.out.println("========================获取已还款总额数==========================");
					//累计迟付次数
					for(int n=0;n<ljcf.size();n++)
					{
						TransReturnFinanceNewInfo trfn_ljcf=(TransReturnFinanceNewInfo)ljcf.get(n);
						for(int f=0;f<returnList.size();f++)
						{
							trfn=(TransReturnFinanceNewInfo)returnList.get(f);
							if(trfn_ljcf.getContractID()==trfn.getContractID())
							{
								trfn.setLjcf(trfn_ljcf.getLjcf());
								returnList.set(f, trfn);
							}
						}
						
					}
					//连续未付次数
					for(int m=0;m<lxwf.size();m++)
					{
						TransReturnFinanceNewInfo trfn_lxwf=(TransReturnFinanceNewInfo)lxwf.get(m);
						for(int h=0;h<returnList.size();h++)
						{
							trfn=(TransReturnFinanceNewInfo)returnList.get(h);
							if(trfn_lxwf.getContractID()==trfn.getContractID())
							{
								trfn.setLxwf(trfn_lxwf.getLxwf());
								returnList.set(h, trfn);
							}
						}
						
					}
					//已还款总额数
					if(hkye!=null && hkye.size()>0)
					{
						for(int m=0;m<hkye.size();m++)
						{
							TransReturnFinanceNewInfo trfn_hkye=(TransReturnFinanceNewInfo)hkye.get(m);
							for(int h=0;h<returnList.size();h++)
							{
								trfn=(TransReturnFinanceNewInfo)returnList.get(h);
								if(trfn_hkye.getContractID()==trfn.getContractID())
								{
									trfn.setRepaymentBalance(trfn.getMexamineamount()-trfn_hkye.getAmountfrom());//批准金额减去已还款总额
									returnList.set(h, trfn);
								}
							}
							
						}
					}
					else
					{
						//double countMoney = 0.0;
						for(int m=0;m<returnList.size();m++)
						{
							TransReturnFinanceNewInfo trfn_hkye=(TransReturnFinanceNewInfo)returnList.get(m);
							trfn_hkye.setRepaymentBalance(trfn_hkye.getMexamineamount());
							returnList.set(m, trfn_hkye);
							/*countMoney = trfn_hkye.getCurrentPaymentAmount();//本期还款余额
							for(int h=0;h<returnList.size();h++)
							{
								if(m==h)continue;
								trfn=(TransReturnFinanceNewInfo)returnList.get(h);
								if(trfn_hkye.getContractID()==trfn.getContractID())
								{
									countMoney  += trfn.getCurrentPaymentAmount();//把同一笔合同的本期应还余额相加
								}
							}
							trfn_hkye.setRepaymentBalance(countMoney);
							returnList.set(m, trfn_hkye);*/
						}
				
					
					}
					
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return returnList;
		
	}
	

	/**
	 * 批量还款
	 * @author afzhu
	 * @param qt_quantityRepaymentList 前台传入的要结算的实体类集合
	 * @param balanceType 结算方式
	 */
	public List quantityRepaymentBalance(List qt_quantityRepaymentList,String balanceType,TransQueryFinanceNewInfo queryEntity) throws Exception
	{
		TransReturnFinanceNewInfo trfn = null;
		TransReturnFinanceNewInfo return_trfn = null;
		List failList = new ArrayList();//结算失败记录结果集
	//	List successList = new ArrayList();//结算成功记录结果集
		List groupEach = null;//存储每组结算记录
		List finalList = null;
		dao=new Sett_TransReturnFinanceDao();
		try
		{
			//把结算成功和失败的记录分离开
			for(int i=0;i<qt_quantityRepaymentList.size();i++)
			{
				groupEach = (ArrayList)qt_quantityRepaymentList.get(i);
				System.out.println("========================批量还款结算---开始检查活期账户和保证金账户==========================");
				System.out.println("========================批量还款结算---结束检查活期账户和保证金账户==========================");
				System.out.println("========================批量还款结算---开始检查活期账户和保证金账户==========================");
				List test = dao.checkAccount(groupEach, balanceType);//检查结算帐户
				System.out.println("========================批量还款结算---结束检查活期账户和保证金账户==========================");
				List successGroup = new ArrayList();//结算成功记录组
				for(int j=0;j<test.size();j++)
				{
					return_trfn = (TransReturnFinanceNewInfo)test.get(j);
					if(return_trfn.getFlag()==0)//如果该条记录结算成功
					{
						successGroup.add(return_trfn);
					}
					else//如果该条记录结算失败
					{
						failList.add(return_trfn);
					}
				}
				List fail = quantityRepaymentBalance_createRecord(successGroup,balanceType);//处理每一组,返回的是失败的记录
				if(fail!=null && fail.size()!=0)//如果存在结算失败结果集,那把每组的失败结果添加到失败结果集中
				{
					failList.addAll(fail);
				}
				//successList.add(successGroup);
				
			}
			
			//对结算成功的记录生成相应的数据库记录
			/*for(int i = 0;i<successList.size();i++)//分别传入每组结算记录,如果其中一组抛出异常,不会影响其他组
			{
				List testGroup = (ArrayList)successList.get(i);
				List fail = quantityRepaymentBalance_createRecord(testGroup,balanceType);//处理每一组,返回的是失败的记录
				if(fail!=null && fail.size()!=0)//如果存在结算失败结果集,那把每组的失败结果添加到失败结果集中
				{
					failList.addAll(fail);
				}
				
			}*/
			finalList =getQuantityRepaymentList(queryEntity);////结果处理完后,要重新执行一遍计算功能,然后把失败结果和计算结果合并返回
			for(int m =0;m<finalList.size();m++)
			{
				TransReturnFinanceNewInfo test1=(TransReturnFinanceNewInfo)finalList.get(m);
				for(int n=0;n<failList.size();n++)
				{
					TransReturnFinanceNewInfo test2=(TransReturnFinanceNewInfo)failList.get(n);
					if(test1.getContractID() == test2.getContractID() && test1.getIssue()==test2.getIssue())
					{
						test2.setRepaymentBalance(test1.getRepaymentBalance());//设置最新还款余额
						test2.setLxwf(test1.getLxwf());//设置最新的连续未付次数
						finalList.set(m, test2);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RemoteException();
		}
		return finalList;
	}
	
	/**
	 * 批量还款生成记录
	 * @author afzhu
	 * @param qt_quantityRepaymentList
	 * @param balanceType
	 * @return
	 */
	public List quantityRepaymentBalance_createRecord(List successGroup,String balanceType)
	{
		List failList = new ArrayList();
		TransReturnFinanceNewInfo trfn = null;
		int i=0;
		try{
			for(i=0;i<successGroup.size();i++)
			{
				trfn = (TransReturnFinanceNewInfo)successGroup.get(i);
				int isFail = tfejb.quantityRepaymentBalance_createRecord(trfn, balanceType);
				if(isFail==-1)
				{
					new Exception();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//如果该组中有一条记录结算失败,那么该条记录之后的所有记录默认为失败
			for(int j = i ;j<successGroup.size();j++)
			{
				trfn = (TransReturnFinanceNewInfo)successGroup.get(j);
				trfn.setRemark("失败:生成会计分录、账户明细时或扣款时出错.原因:"+e.getMessage());
				trfn.setFlag(-1);
				failList.add(trfn);
			}
		}
		return failList;
	}
	/**
	 * 
	 * 批量还款---链接查找
	 * @author afzhu
	 * @param userid 当前用户ID
	 * @param exeDate 执行日(开机日)
	 * @return
	 * @throws Exception
	 */
	public List getHrefFindList(long userid,String exeDate) throws Exception
	{
		List returnList = new ArrayList();
		dao=new Sett_TransReturnFinanceDao();
		System.out.println("===============获取链接查找列表==============");
		returnList=dao.getHrefFindList(userid, exeDate);
		System.out.println("===============获取链接查找列表==============");
		return returnList;
	}
	
	/**
	 * 批量还款---链接查找---删除操作
	 * @author afzhu
	 * @param deleteParam
	 * @throws Exception
	 */
	public List hrefFindDelete(String deleteParam,long userid,String exeDate) throws Exception
	{
		System.out.println("===============链接查找删除记录开始==============");
		tfejb.hrefFindDelete(deleteParam);
		System.out.println("===============链接查找删除记录结束==============");
		List returnList = getHrefFindList(userid,exeDate);
		return returnList;
	}
	
	/**
	 * 批量还款----链接查找---设置累计迟付
	 * @author afzhu
	 * @param transNo
	 * @throws Exception
	 */
	public List  updateLjcf(String transNo,long userid,String exeDate) throws Exception
	{
		int flag=0;//0为正常,-1为失败
		List returnList = null;
		try
		{
			JSONObject jo=new JSONObject(transNo);
			String transNoArr[] = jo.getString("transNo").split("&");
			returnList = getHrefFindList(userid,exeDate);
			dao=new Sett_TransReturnFinanceDao();
			dao.updateLjcf(transNoArr);
			returnList.add(new Integer(flag));
		}
		catch(Exception e)
		{
			flag=-1;
			if(returnList!=null)
			{
				returnList.add(new Integer(flag));
			}
			e.printStackTrace();
			throw new IException(e.getMessage());
		}
		return returnList;
	}
	
	/**
	 * @param contractids
	 * 
	 * 判断批量还款--链接查找--删除是否存在提前还款
	 * 
	 */
	public String checkHrefFindDel(String contractids) throws Exception
	{
		dao=new Sett_TransReturnFinanceDao();
		String returnValue = dao.checkHrefFindDel(contractids);
		return returnValue;
	}
}
