/*
 * Created on 2004-7-13
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.report.bizlogic;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastAmountDAO;
import com.iss.itreasury.treasuryplan.etl.extract.dao.Trea_ForecastBalanceDAO;
import com.iss.itreasury.treasuryplan.etl.transform.bizlogic.ForecastDataTransformer;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dao.Trea_TPTemplateItemDAO;
import com.iss.itreasury.treasuryplan.etl.transform.dataentity.TPTemplateInfo;
import com.iss.itreasury.treasuryplan.query.paraminfo.QueryDataInfo;
import com.iss.itreasury.treasuryplan.report.dao.Trea_TPForecastDataDAO;
import com.iss.itreasury.treasuryplan.report.dataentity.TemplateDetailInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TemplateInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TPForecastDataConditionInfo;
import com.iss.itreasury.treasuryplan.report.dataentity.TPForecastDataInfo;
import com.iss.itreasury.treasuryplan.setting.dao.Trea_SystemParameterDAO;
import com.iss.itreasury.treasuryplan.util.TPConstant;
import com.iss.itreasury.util.DataFormat;
import com.iss.itreasury.util.Database;

/**
 * @author wlming
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class ReportBean
{
	public ArrayList columnList = null; 
	/**
	 * 查询模板树总共有几级 
	 */
	public long getLevelCount() throws Exception
	{
		Trea_TPForecastDataDAO dao = new Trea_TPForecastDataDAO();
		return dao.getLevelCount();
	}
	/**
	 * 检查用户所属于的部门/公司资金计划内，是否有未完成的资金计划版本（状态不等于“已审核”或“已拒绝”）
	 * @param conditionInfo
	 * @return
	 * @throws Exception
	 */
	public boolean isContainUncompletePlan(TPForecastDataConditionInfo conditionInfo) throws Exception
	{
		Trea_TPForecastDataDAO dao = null;
		try
		{
			dao = new Trea_TPForecastDataDAO();
			return dao.isContainUncompletePlan(conditionInfo);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	/**
	 * @author yuanxue
	 * @param v
	 * @param list
	 * @return 
	 */

    public Collection getDetailByContion(Vector vTemplate ,ArrayList list)throws Exception
    {
    	TPForecastDataInfo returnInfo = null ;
		TemplateDetailInfo tempSumInfo = null;
    	TemplateInfo templateInfo = null ; 
    	String []aryDateString= null ;
    	Vector v = new Vector() ;
    	aryDateString = (String[])(list.toArray(new String[0]));
    	try
    	{
			if (vTemplate != null && vTemplate.size() > 0)
			{
				Iterator it = vTemplate.iterator();
				while (it.hasNext())
				{   
					// 外层LineNO 循环
					TemplateInfo info = (TemplateInfo) it.next();
					ArrayList detailInfos = info.getDetailInfos();	
					System.out.println("detailInfos.size()"+detailInfos.size());	
					for (int i = 0; i < detailInfos.size(); i++)
					{
						//内层日期金额循环
					    System.out.println("---inner date time "+aryDateString[i]);
					    System.out.println("---inner linno "+info.getLineNo());
						returnInfo = new TPForecastDataInfo();
						tempSumInfo = (TemplateDetailInfo)detailInfos.get(i);					
						returnInfo.setAuthorizedDepartment(info.getAuthorizedDepartment());
						returnInfo.setAuthorizedUser(info.getAuthorizedUser());
						returnInfo.setIsLeaf(info.getIsLeaf());
						returnInfo.setIsNeedSum(info.getIsNeedSum());
						returnInfo.setLineID(info.getLineID());
						returnInfo.setLineLevel(info.getLineLevel());
						returnInfo.setLineName(info.getLineName());
						returnInfo.setParentLineID(info.getParentLineID());
						returnInfo.setLineNo(info.getLineNo());
						returnInfo.setTransactionDate(DataFormat.getDateTime(aryDateString[i]));
						returnInfo.setForecastAmount(tempSumInfo.getForecastAmount());
						returnInfo.setPlanAmount(tempSumInfo.getPlanAmount());
						v.add(returnInfo);
						System.out.println("here end ");	
					}
					
				}
			}	
  
    	}
    	catch(Exception e){
    		throw e;
    	}
    	
     	return v;
    }
	/**
	 * 根据条件查找预测数据
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Collection find(TPForecastDataConditionInfo conditionInfo) throws Exception
	{
		Trea_TPForecastDataDAO dao = null;
		try
		{
			dao = new Trea_TPForecastDataDAO();
			return dao.find(conditionInfo);
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}
	/**
	 * 根据按周汇总的方式条件查找预测数据
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 * @author yuanxue
	 */	
	public Collection findByWeek(TPForecastDataConditionInfo conditionInfo) throws Exception
	{
		Trea_TPForecastDataDAO dao = null;
		Vector v = new Vector();
		Collection ctemp = null;
		Vector vtemp =new Vector();
		try
		{
           if(conditionInfo != null)
			System.out.println("--conditionInfo");
           
			dao = new Trea_TPForecastDataDAO();
			QueryDataInfo qInfo = new QueryDataInfo();
			qInfo.setStartDate(conditionInfo.getTransactionDateStart());
			qInfo.setEndDate(conditionInfo.getTransactionDateEnd());
			qInfo.setCurrencyID(conditionInfo.getCurrencyID());
			qInfo.setOfficeID(conditionInfo.getOfficeID());
			qInfo.setUserID(conditionInfo.getAuthorizedUserID());
			qInfo.setDepartmentID(conditionInfo.getAuthorizedDepartmentID());
			v.addAll(dao.find(conditionInfo));
			System.out.println(" v.size()"+v.size());
			vtemp = dealActaulData(qInfo,v,TPConstant.GatherType.WEEKSUM);
			System.out.println("  vtemp.size()" + vtemp.size());
			System.out.println("columnList.size()"+columnList.size());
			ctemp = getDetailByContion(vtemp,columnList);
			return ctemp;
		}
		catch (Exception ex)
		{
			throw ex;
		}
	}	
	/**
	 * 根据按月汇总的方式条件查找预测数据
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 * @author yuanxue
	 */		
	public Collection findByMonth(TPForecastDataConditionInfo conditionInfo) throws Exception
	{
		Trea_TPForecastDataDAO dao = null;
		Vector v = new Vector();
		Collection ctemp = null;
		Vector vtemp =new Vector();
		try	
		{
			dao = new Trea_TPForecastDataDAO();
			QueryDataInfo qInfo = new QueryDataInfo();
			qInfo.setStartDate(conditionInfo.getTransactionDateStart());
			qInfo.setEndDate(conditionInfo.getTransactionDateEnd());
			qInfo.setCurrencyID(conditionInfo.getCurrencyID());
			qInfo.setOfficeID(conditionInfo.getOfficeID());
			qInfo.setUserID(conditionInfo.getAuthorizedUserID());
			qInfo.setDepartmentID(conditionInfo.getAuthorizedDepartmentID());
			v.addAll(dao.find(conditionInfo));
			System.out.println(" v.size()"+v.size());
			vtemp = dealActaulData(qInfo,v,TPConstant.GatherType.MONTHSUM);
			System.out.println("  vtemp.size()" + vtemp.size());
			System.out.println("columnList.size()"+columnList.size());
			ctemp = getDetailByContion(vtemp,columnList);
			return ctemp;	}
		catch (Exception ex)
		{
			throw ex;
		}
	}	
	/**
	 * 汇总实际数据
	 * @param info
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private Vector dealActaulData(QueryDataInfo qInfo,Vector list,long gatherType) throws Exception
	{
		try 
		{
			Vector rtnList = new  Vector();
			ArrayList detailList = new  ArrayList();
			 columnList = new  ArrayList();		
			
			DataFormat df = new DataFormat();
			
			if(list!=null && list.size()!=0)
			{
				//临时行号，确定数据条数			
				long tempLineID = -1;
				//临时列号，确定列名
				long tempColumnID = -1;
				//临时周，确定按周汇总列数
				int tempWeek =0;
				int tempWeek1 =0;
				
				//临时月份，确定按月汇总列数
				int tempMonth =0;
				int tempMonth1 =0;
				//临时年份，确定按月汇总列数
				int tempYear =0;
				int tempYear1 =0;
				
				
				//临时对应汇总数据
				TemplateDetailInfo tempSumInfo = new TemplateDetailInfo();
				TemplateInfo templateInfo = new TemplateInfo(); 
				for(int i=0;i<list.size();i++)
				{
					TPForecastDataInfo info = new TPForecastDataInfo();
					info = (TPForecastDataInfo)list.elementAt(i);					
					//临时对应数据
					TemplateDetailInfo templateDetailInfo = new TemplateDetailInfo();
					
					
					switch ((int) gatherType)
					{
						case TPConstant.GatherType.WEEKSUM :                             //按周
						{		
							
							//判断显示一条纪录
							if(tempLineID!=info.getLineID())
							{
								if(tempLineID!=-1)  //不是第一行									
								{
									//处理最后一列	
									
									if(tempWeek1!=0)
									{										
										detailList.add(tempSumInfo);
										tempSumInfo = new  TemplateDetailInfo();
										if(tempWeek1!=1)
										{
											//detailList.add(tempSumInfo);
											if(tempColumnID==-1)
											{
											//	log.info("---------生成列名数据1---------");
												columnList.add(DataFormat.getDateString(qInfo.getEndDate()));
											}
										}										
									}																			
									
									
									templateInfo.setDetailInfos(detailList);
									
									rtnList.add(templateInfo);
									
									//清空明细纪录
									detailList= new ArrayList();
									templateInfo = new TemplateInfo(); 
									
									tempWeek=df.getWeekDay(info.getTransactionDate());
									//templateDetailInfo.setActualAmount(info.getActualAmount());
									//templateDetailInfo.setDifferenceAmount(info.getActualAmount()-info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									
									detailList.add(templateDetailInfo);	
									
									//修改临时列号
									tempColumnID=info.getLineID();
									
									//处理最后一行
									if(i==list.size()-1)
									{
										
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setIsNeedSum(info.getIsNeedSum());
										templateInfo.setDetailInfos(detailList);
										templateInfo.setAuthorizedDepartment(info.getAuthorizedDepartment());
										templateInfo.setAuthorizedUser(info.getAuthorizedUser());
										rtnList.add(templateInfo);	
									
										
									}
								
								}
								else
								{		
									
									tempWeek=df.getWeekDay(info.getTransactionDate());										
								//	tempWeek = 1; // 固定周一
								//	templateDetailInfo.setActualAmount(info.getActualAmount());
								//	templateDetailInfo.setDifferenceAmount(info.getActualAmount()-info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									
									detailList.add(templateDetailInfo);		
									
									
								//	log.info("---------生成列名数据2---------");
									columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									
									if(i==list.size()-1)
									{										
										templateInfo.setDetailInfos(detailList);	
										
										rtnList.add(templateInfo);	
									}
								}
								
								tempLineID=info.getLineID();
								templateInfo.setIsLeaf(info.getIsLeaf());
								templateInfo.setLineID(info.getLineID());
								templateInfo.setLineLevel(info.getLineLevel());
								templateInfo.setLineName(info.getLineName());
								templateInfo.setLineNo(info.getLineNo());
								templateInfo.setParentLineID(info.getParentLineID());
								templateInfo.setIsNeedSum(info.getIsNeedSum());
								templateInfo.setAuthorizedDepartment(info.getAuthorizedDepartment());
								templateInfo.setAuthorizedUser(info.getAuthorizedUser());								
								tempWeek1 =0;//将临时的清为0 
							}
							else
							{							
								if(tempWeek==1)
								{								
									if(tempWeek1==1)
									{
										tempWeek1=df.getWeekDay(info.getTransactionDate());	
										
										
										detailList.add(tempSumInfo);
									
										tempSumInfo =new  TemplateDetailInfo();
									//	tempSumInfo.setActualAmount(info.getActualAmount()+tempSumInfo.getActualAmount());
										tempSumInfo.setForecastAmount(info.getForecastAmount()+tempSumInfo.getForecastAmount());
								//		tempSumInfo.setDifferenceAmount(info.getDifferenceAmount()+tempSumInfo.getDifferenceAmount());
										tempSumInfo.setPlanAmount(info.getPlanAmount()+tempSumInfo.getPlanAmount());
										
										//处理最后一行最后一列									
										if(i==list.size()-1)
										{												
											detailList.add(tempSumInfo);
											templateInfo.setDetailInfos(detailList);	
											
											rtnList.add(templateInfo);	
										}
									}
									else
									{										
										tempWeek1=df.getWeekDay(info.getTransactionDate());
										
									//	tempSumInfo.setActualAmount(info.getActualAmount()+tempSumInfo.getActualAmount());
										tempSumInfo.setForecastAmount(info.getForecastAmount()+tempSumInfo.getForecastAmount());
									//	tempSumInfo.setDifferenceAmount(info.getDifferenceAmount()+tempSumInfo.getDifferenceAmount());
										tempSumInfo.setPlanAmount(info.getPlanAmount()+tempSumInfo.getPlanAmount());
										if(tempColumnID==-1 && tempWeek1==1)
										{
									//		log.info("---------生成列名数据3---------");
											columnList.add(DataFormat.getDateString(info.getTransactionDate()));
										}
										//处理最后一行最后一列
										
										if(i==list.size()-1)
										{	
										//	log.info("------处理最后一行最后一列3----------");											
												detailList.add(tempSumInfo);
									//		log.info("------处理最后一行最后一列3----------"+detailList.size());
												templateInfo.setDetailInfos(detailList);															
												rtnList.add(templateInfo);
												
										}
									}								
									
								}
								else
								{								
									tempWeek=df.getWeekDay(info.getTransactionDate());
							
								//	templateDetailInfo.setActualAmount(info.getActualAmount());
								//	templateDetailInfo.setDifferenceAmount(info.getActualAmount()-info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									
									detailList.add(templateDetailInfo);
									
									if(tempColumnID==-1)
									{
									//	log.info("---------生成列名数据4---------");
										columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									}
									//处理最后一行最后一列
									if(i==list.size()-1)
									{										
										templateInfo.setDetailInfos(detailList);															
										rtnList.add(templateInfo);	
									}
									
								}
							}
							
						}	
							break;
						case TPConstant.GatherType.MONTHSUM :                              //按月
						{						
							//判断显示一条纪录
							if(tempLineID!=info.getLineID())
							{
								if(tempLineID!=-1)  //不是第一次
								{
									//处理最后一列									
									if(tempMonth1!=0)
									{	
										detailList.add(tempSumInfo);	
										tempSumInfo = new  TemplateDetailInfo();
										if(tempColumnID==-1)
										{
										//	log.info("---------生成列名数据1---------");
											columnList.add(DataFormat.getDateString(qInfo.getEndDate()));
										}
									}
									
									templateInfo.setDetailInfos(detailList);
									
									rtnList.add(templateInfo);
									//清空明细纪录
									detailList= new ArrayList();
									templateInfo = new TemplateInfo();
									
									tempMonth1 =0;
									
									tempMonth=info.getTransactionDate().getMonth();
									
								//	templateDetailInfo.setActualAmount(info.getActualAmount());
								//	templateDetailInfo.setDifferenceAmount(info.getActualAmount()-info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);									
									
									//修改临时列号								
									tempColumnID=info.getLineID();
									//处理最后一行最后一列
									if(i==list.size()-1)
									{	
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setIsNeedSum(info.getIsNeedSum());
										templateInfo.setAuthorizedDepartment(info.getAuthorizedDepartment());
										templateInfo.setAuthorizedUser(info.getAuthorizedUser());
										templateInfo.setDetailInfos(detailList);
									//	log.info("---------最后一行列数----"+detailList.size());
										rtnList.add(templateInfo);	
									}
									
								}
								else
								{
									tempMonth=info.getTransactionDate().getMonth();
								//	templateDetailInfo.setActualAmount(info.getActualAmount());
								//	templateDetailInfo.setDifferenceAmount(info.getActualAmount()-info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);									
									if(tempColumnID==-1)
									{
									//	log.info("---------生成列名数据2---------");
										columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									}
									
									if(i==list.size()-1)
									{	
										templateInfo.setIsLeaf(info.getIsLeaf());
										templateInfo.setLineID(info.getLineID());
										templateInfo.setLineLevel(info.getLineLevel());
										templateInfo.setLineName(info.getLineName());
										templateInfo.setLineNo(info.getLineNo());
										templateInfo.setParentLineID(info.getParentLineID());
										templateInfo.setIsNeedSum(info.getIsNeedSum());
										templateInfo.setAuthorizedDepartment(info.getAuthorizedDepartment());
										templateInfo.setAuthorizedUser(info.getAuthorizedUser());										
										templateInfo.setDetailInfos(detailList);															
										rtnList.add(templateInfo);	
									}
								}
								
								tempLineID=info.getLineID();
								templateInfo.setIsLeaf(info.getIsLeaf());
								templateInfo.setLineID(info.getLineID());
								templateInfo.setLineLevel(info.getLineLevel());
								templateInfo.setLineName(info.getLineName());
								templateInfo.setLineNo(info.getLineNo());
								templateInfo.setIsNeedSum(info.getIsNeedSum());
								templateInfo.setAuthorizedDepartment(info.getAuthorizedDepartment());
								templateInfo.setAuthorizedUser(info.getAuthorizedUser());								
								templateInfo.setParentLineID(info.getParentLineID());
							}
							else
							{									
								if(tempMonth==info.getTransactionDate().getMonth())
								{
								//	templateDetailInfo.setActualAmount(info.getActualAmount());
								//	templateDetailInfo.setDifferenceAmount(info.getActualAmount()-info.getPlanAmount());
									templateDetailInfo.setForecastAmount(info.getForecastAmount());
									templateDetailInfo.setPlanAmount(info.getPlanAmount());
									detailList.add(templateDetailInfo);								
									if(tempColumnID==-1)
									{
								//		log.info("---------生成列名数据3---------");
										columnList.add(DataFormat.getDateString(info.getTransactionDate()));
									}
									
									if(i==list.size()-1)
									{										
										templateInfo.setDetailInfos(detailList);	
								//		log.info("---------最后一行列数1----"+detailList.size());
										rtnList.add(templateInfo);	
									}
								}
								else
								{								
									//以后几个月的汇总数据
									if(tempMonth1==0)
									{
									//	tempSumInfo.setActualAmount(info.getActualAmount()+tempSumInfo.getActualAmount());
										tempSumInfo.setForecastAmount(info.getForecastAmount()+tempSumInfo.getForecastAmount());
									//	tempSumInfo.setDifferenceAmount(info.getDifferenceAmount()+tempSumInfo.getDifferenceAmount());
										tempSumInfo.setPlanAmount(info.getPlanAmount()+tempSumInfo.getPlanAmount());
										tempMonth1=info.getTransactionDate().getMonth();
										
										if(i==list.size()-1)
										{		
											detailList.add(tempSumInfo);
							//				log.info("---------最后一行列数2----"+detailList.size());
											templateInfo.setDetailInfos(detailList);															
											rtnList.add(templateInfo);	
										}
									}
									else
									{
										if(tempMonth1 == info.getTransactionDate().getMonth())
										{
									//		tempSumInfo.setActualAmount(info.getActualAmount()+tempSumInfo.getActualAmount());
											tempSumInfo.setForecastAmount(info.getForecastAmount()+tempSumInfo.getForecastAmount());
									//		tempSumInfo.setDifferenceAmount(info.getDifferenceAmount()+tempSumInfo.getDifferenceAmount());
											tempSumInfo.setPlanAmount(info.getPlanAmount()+tempSumInfo.getPlanAmount());
											
											if(i==list.size()-1)
											{		
												detailList.add(tempSumInfo);
								//				log.info("---------最后一行列数3----"+detailList.size());
												templateInfo.setDetailInfos(detailList);															
												rtnList.add(templateInfo);	
											}
										}
										else
										{											
											detailList.add(tempSumInfo);											
											if(tempColumnID==-1)
											{
									//			log.info("---------生成列名数据3---------");
												columnList.add(DataFormat.getDateString(UtilOperation.getNextNDay(info.getTransactionDate(),-1)));
											}
											tempSumInfo = new  TemplateDetailInfo();
											tempMonth1=info.getTransactionDate().getMonth();
											
									//		tempSumInfo.setActualAmount(info.getActualAmount()+tempSumInfo.getActualAmount());
											tempSumInfo.setForecastAmount(info.getForecastAmount()+tempSumInfo.getForecastAmount());
									//		tempSumInfo.setDifferenceAmount(info.getDifferenceAmount()+tempSumInfo.getDifferenceAmount());
											tempSumInfo.setPlanAmount(info.getPlanAmount()+tempSumInfo.getPlanAmount());
											
											if(i==list.size()-1)
											{		
												detailList.add(tempSumInfo);
									//			log.info("---------最后一行列数4----"+detailList.size());
												templateInfo.setDetailInfos(detailList);															
												rtnList.add(templateInfo);	
											}
										}
									}
										
								}
																
							}
							
						}	
							break;					
								
					}
				}
			}	
			return rtnList;
		} 
		catch (Exception e) 
		{
		//	log.info("***********************");
			e.printStackTrace();
			throw e;	
		
			// TODO Auto-generated catch block
			
		}
	}
	
	/**
	 * 更新预测数据（注意：是部分更新）
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public void update(Vector v,Timestamp sDate,Timestamp eDate,long officeID,long currencyID) throws Exception
	{
		Connection con = null;
		Trea_TPForecastDataDAO dao = null;
		ForecastDataTransformer forecastdatatransformer = null;
		Trea_TPTemplateDAO templateDAO = null;
		Timestamp inputTime = DataFormat.getTreasuryPlanExecuteDate();
		try
		{
			forecastdatatransformer = new ForecastDataTransformer();
			con =  Database.getConnection();
			dao = new Trea_TPForecastDataDAO(con);
			templateDAO = new Trea_TPTemplateDAO(con);
			
			if (v != null)
			{
				Iterator it = (Iterator)v.iterator();
				while(it.hasNext())
				{
					TPForecastDataInfo info = (TPForecastDataInfo)it.next();
					info.setInputTime(inputTime);
					dao.update(info);
				}
				
				//调用汇总的方法
				while(!sDate.after(eDate))
				{				
					//旧的算法
					//forecastdatatransformer.sumAmountDependOnLineLevel(officeID,currencyID,sDate,new Trea_TPForecastDataDAO(con),true);
					//优化后的算法
					forecastdatatransformer.sumAmountDependOnLineID(officeID,currencyID,sDate,new Trea_TPForecastDataDAO(con),true,v);
					/*dao.setAmountFieldNameAsPlanAmount();
					double forcastAmount1 = dao.getAmountByLineIDAndTransactionDate(officeID,currencyID,1,sDate);				
					double forcastAmount2 = dao.getAmountByLineIDAndTransactionDate(officeID,currencyID,2,sDate);				
					double forcastAmount1830 = dao.getAmountByLineIDAndTransactionDate(officeID,currencyID,1830,sDate);				
					//期末可用余额
					double endBalance = forcastAmount1 + forcastAmount2 - forcastAmount1830;
					//更新期末余额						
					dao.updateAmountByTransactionDateAndLineID(officeID,currencyID,sDate,3662,endBalance,inputTime);
					//更新第二日期初余额
					dao.updateAmountByTransactionDateAndLineID(officeID,currencyID,DataFormat.getNextDate(sDate,1),1,endBalance,inputTime);
					dao.resetAmountFieldName();*/
					sDate = DataFormat.getNextDate(sDate,1);
				}
		
			}				
			
			con.close();
			con = null;
		}
		catch (Exception ex)
		{
			throw ex;
		}	
		finally
		{
			if (con != null)
			{
				con.close();
				con = null;
			}
		}
	}
	/**
	 * 从预测数据中组装模板详细信息
	 * @param forecastInfo
	 * @return
	 * @throws Exception
	 */
	private TemplateDetailInfo getTemplateDetailInfoFromForecastInfo(TPForecastDataInfo forecastInfo) throws Exception
	{
		TemplateDetailInfo detailInfo = new TemplateDetailInfo();
		detailInfo.setTransactionDate(forecastInfo.getTransactionDate());
		detailInfo.setForecastAmount(forecastInfo.getForecastAmount());
		detailInfo.setPlanAmount(forecastInfo.getPlanAmount());
		detailInfo.setDifferenceAmount(forecastInfo.getPlanAmount()-forecastInfo.getForecastAmount());
		return detailInfo;
	}
	/**
	 * 从预测数据中组装模板主信息
	 * @param forecastInfo
	 * @return
	 * @throws Exception
	 */
	private TemplateInfo getTemplateInfoFromForecastInfo(TPForecastDataInfo forecastInfo) throws Exception
	{
		TemplateInfo info = new TemplateInfo();
		info.setLineID(forecastInfo.getLineID());
		info.setLineNo(forecastInfo.getLineNo());
		info.setLineName(forecastInfo.getLineName());
		info.setLineLevel(forecastInfo.getLineLevel());
		info.setIsLeaf(forecastInfo.getIsLeaf());
		info.setParentLineID(forecastInfo.getParentLineID());
		return info;
	}
	/**
	 * 从预测数据类中得到模板数据类
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Vector getTemplateInfoFromForecastData(Vector fV) throws Exception
	{
		Vector tV = new Vector();
		try
		{
			if (fV != null)
			{
				Iterator it = (Iterator)fV.iterator();

				String strLastLineNo = "";
				TemplateInfo templateInfo = null;
				TemplateDetailInfo detailInfo = null;
				ArrayList aryList = null;
				while(it.hasNext())
				{
					TPForecastDataInfo forecastInfo = (TPForecastDataInfo)it.next();
					
					detailInfo = this.getTemplateDetailInfoFromForecastInfo(forecastInfo);
					
					if (!(strLastLineNo != null && forecastInfo.getLineNo() != null && strLastLineNo.equals(forecastInfo.getLineNo())))
					{
						//
						if (templateInfo != null)
						{
							//将ArrayList转换成array,并加入主信息类
							templateInfo.setDetailInfos(aryList);
							tV.add(templateInfo);
						}
						aryList = new ArrayList();
						templateInfo = this.getTemplateInfoFromForecastInfo(forecastInfo);
						aryList.add(detailInfo);
					}
					else
					{
						aryList.add(detailInfo);
					}
					//得到上次行项目编号
					strLastLineNo = templateInfo.getLineNo();
					
					//最后一个
					if (it.hasNext() == false)
					{
						//将ArrayList转换成array,并加入主信息类
						templateInfo.setDetailInfos(aryList);
						tV.add(templateInfo);
					}					
				}
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return ((tV != null && tV.size() > 0 )? tV : null);
	}
	/**
	 * 从模板数据类中得到预测数据类
	 * @param conditionInfo
	 * @return Collection
	 * @throws Exception
	 */
	public Vector getForecastDataFromTemplateInfo(Vector tV) throws Exception
	{
		Vector fV = new Vector();
		try
		{
			if (tV != null)
			{
				Iterator it = (Iterator)tV.iterator();

				TemplateInfo templateInfo = null;
				ArrayList detailInfos = null;
				TPForecastDataInfo forecastInfo = null;
				
				while(it.hasNext())
				{
					templateInfo = (TemplateInfo)it.next();
					detailInfos = templateInfo.getDetailInfos();
					for (int i=0; i<detailInfos.size();i++)
					{
						forecastInfo = new TPForecastDataInfo();
						forecastInfo.setLineID(templateInfo.getLineID());
						forecastInfo.setLineNo(templateInfo.getLineNo());
						forecastInfo.setLineName(templateInfo.getLineName());
						forecastInfo.setLineLevel(templateInfo.getLineLevel());
						forecastInfo.setParentLineID(templateInfo.getParentLineID());
						forecastInfo.setIsLeaf(templateInfo.getIsLeaf());
						forecastInfo.setTransactionDate(((TemplateDetailInfo)detailInfos.get(i)).getTransactionDate());
						forecastInfo.setForecastAmount(((TemplateDetailInfo)detailInfos.get(i)).getForecastAmount());
						forecastInfo.setPlanAmount(((TemplateDetailInfo)detailInfos.get(i)).getPlanAmount());

						fV.add(forecastInfo);
					}
				}
			}
		}
		catch(Exception ex)
		{
			throw ex;
		}
		return ((fV != null && fV.size() > 0 )? fV : null);
	}
}
