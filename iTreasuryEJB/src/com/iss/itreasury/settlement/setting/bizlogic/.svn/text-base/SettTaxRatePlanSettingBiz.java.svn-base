package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;
import java.util.Iterator;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_TaxRatePlanSettingDAO;
import com.iss.itreasury.settlement.setting.dao.Sett_TaxRatePlanSettingItemDAO;
import com.iss.itreasury.settlement.setting.dataentity.TaxRatePlanSettingInfo;
import com.iss.itreasury.settlement.setting.dataentity.TaxRatePlanSettingItemInfo;
import com.iss.itreasury.settlement.util.UtilOperation;
import com.iss.itreasury.util.Constant;
public class SettTaxRatePlanSettingBiz
{
	Sett_TaxRatePlanSettingDAO dao1 = null;
	public SettTaxRatePlanSettingBiz(){
		dao1 = new Sett_TaxRatePlanSettingDAO();
	}
	
	public long getPlanMaxId()throws Exception{
		return dao1.getMaxId();
	}
	
	
	/*
	public static void main(String[] aaa) throws SettlementException
	{
		System.out.println("ddddddddddd");
		SettTaxRatePlanSettingBiz a = new SettTaxRatePlanSettingBiz();
		TaxRatePlanSettingInfo info = new TaxRatePlanSettingInfo();
		info.setSname("SNAME");
		Collection coll = a.findAll();
		try{
		a.save(info);
		System.out.print("save");
		}catch(Exception e){}
		System.out.println("ddddddddddd  coll =  "+coll);
	}*/
	
    public Collection findAll() throws SettlementException
    {
        Collection coll = null;

        TaxRatePlanSettingInfo info = new TaxRatePlanSettingInfo();

        info.setStatusID(Constant.RecordStatus.VALID);

        try 
        {
        	coll = dao1.findByCondition(info, " order by ID ");
        }
        catch ( ITreasuryDAOException e ) 
        {
            e.printStackTrace();
            
            throw new SettlementException();
        }
        finally 
        {
            try 
            {
                dao1.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }

        return coll;
    }
	public Collection findTaxRatePlanSettingBetweenID(long fromId,long toId) throws SettlementException,Exception{
	return dao1.findTaxRatePlanSettingBetweenID( fromId, toId);
	}

	//查询
	public TaxRatePlanSettingInfo findById(long id) throws SettlementException,Exception{
	TaxRatePlanSettingInfo resultInfo = null;
    try 
        {
            resultInfo = new TaxRatePlanSettingInfo();

            resultInfo = (TaxRatePlanSettingInfo) dao1.findByID(id, (new TaxRatePlanSettingInfo()).getClass());

        }
        catch ( ITreasuryDAOException e ) 
        {
            e.printStackTrace();
            throw new SettlementException();
        }
        finally 
        {
            try 
            {
                dao1.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }

        }
        return resultInfo;
    }
	    /**存TaxRatePlanSettingInfo
     * @description: long
     * @param CommissionSettingInfo
     * @return
     * @throws Exception
     */
	public long save(TaxRatePlanSettingInfo info) throws SettlementException, Exception
    {
        long lReturn = -1;
        if(dao1.isSameName(info)==0){
            if ( info.getId()<0 ) //新加入
            {
            	lReturn =dao1.add(info);
            	
            }else{

                dao1.update(info);

                lReturn = info.getId();
            }
    	}else 
            {
                lReturn = 0;
            }
        return lReturn;
    }

	    public void delete(long id) throws SettlementException
    {
        TaxRatePlanSettingInfo info = new TaxRatePlanSettingInfo();
        info.setId(id);
        info.setStatusID(Constant.RecordStatus.INVALID);
        try 
        {
            dao1.update(info);
        }
        catch ( Exception e ) 
        {
            e.printStackTrace();
            throw new SettlementException();
        }
        finally 
        {
            try 
            {
                dao1.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }

    }

    /**
     * Added bu zwsun, 2007/8/10
     * @param interestAmount
     * @param interestTaxPlanId
     * @return
     * @throws Exception
     * 得到所有的利率的列表 第一个利率为主利率, 用逗号隔开
     */
    public String findAllInterestTaxRate(long interestTaxPlanId) throws Exception
    {
    	StringBuffer allRateBuffer = new StringBuffer();    //所有的利率	 	
    	
//    	double interestTax = 0.00; // 利息税费
    	
    	double mainRate    = 0.00; //利率计划对应的主利率
    	
//    	double mainTax     = 0.00; //主利率对应的主利息税费（唯一）
//    	
//    	double itemTax     = 0.00; //非主利率对应的利息税费（多条）
    	
    	try 
    	{
    		Sett_TaxRatePlanSettingItemDAO iteDAO = new Sett_TaxRatePlanSettingItemDAO();
    		
    		//得到利息税费率计划对应的主利率
    		mainRate = iteDAO.getMainRate(interestTaxPlanId);
			
    		allRateBuffer.append(mainRate);
    		
    		//计算出利息税费主体
//			mainTax = UtilOperation.Arith.round( UtilOperation.Arith.round(interestAmount,2) * mainRate/100 , 2 );
			
//			System.out.println("〉〉〉〉〉〉〉〉利息税费主体 = "+ mainTax);
			
			//得到一条利息税费计划的非主费率结果集
			Collection coll = iteDAO.getOtherRates(interestTaxPlanId);
			
			Iterator it = null;
			
			TaxRatePlanSettingItemInfo itemInfo = null;
			
			if( coll != null)
			{				
				it = coll.iterator();
				
				if(it.hasNext())
				{
					for(int i=0;i<coll.size();i++)
					{
						itemInfo = (TaxRatePlanSettingItemInfo)it.next();
						
						allRateBuffer.append(","+itemInfo.getTaxRate());
						
						System.out.println("〉〉〉〉〉〉〉〉利息税费 非主费率 "+i+" = "+ itemInfo.getTaxRate());
						
//						itemTax += UtilOperation.Arith.round( mainTax*itemInfo.getTaxRate()/100 , 2);
					}
					
					//利息税费总和 = 利息税费主体 + 利息税费子项
//					interestTax = mainTax + itemTax;
				}
//				else
//				{
//					interestTax = mainTax;
////				}
			}
//			else
//			{
//				interestTax = mainTax;
//			}
		}
		catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
//    	System.out.println("〉〉〉〉〉〉〉〉利息税费总和 = "+ interestTax);
    	
    	
    	return allRateBuffer.toString();
    }	    
	    
    /**
     * @param interestAmount
     * @param interestTaxPlanId
     * @return
     * @throws Exception
     * 根据利息金额和利息税费率计划，计算出利息税费
     */
    public double findInterestTaxByConditions(double interestAmount , long interestTaxPlanId) throws Exception
    {
    	double interestTax = 0.00; // 利息税费
    	
    	double mainRate    = 0.00; //利率计划对应的主利率
    	
    	double mainTax     = 0.00; //主利率对应的主利息税费（唯一）
    	
    	double itemTax     = 0.00; //非主利率对应的利息税费（多条）
    	
    	try 
    	{
    		Sett_TaxRatePlanSettingItemDAO iteDAO = new Sett_TaxRatePlanSettingItemDAO();
    		
    		//得到利息税费率计划对应的主利率
    		mainRate = iteDAO.getMainRate(interestTaxPlanId);
			
    		//计算出利息税费主体
			mainTax = UtilOperation.Arith.round( UtilOperation.Arith.round(interestAmount,2) * mainRate/100 , 2 );
			
			System.out.println("〉〉〉〉〉〉〉〉利息税费主体 = "+ mainTax);
			
			//得到一条利息税费计划的非主费率结果集
			Collection coll = iteDAO.getOtherRates(interestTaxPlanId);

			if( coll != null)
			{				
				Iterator it = coll.iterator();
				TaxRatePlanSettingItemInfo itemInfo = null;				
				
				while(it.hasNext())
				{
					itemInfo = (TaxRatePlanSettingItemInfo)it.next();
					itemTax += UtilOperation.Arith.round( mainTax*itemInfo.getTaxRate()/100 , 2);
				}
			}
			
			//利息税费总和 = 利息税费主体 + 利息税费子项
			interestTax = mainTax + itemTax;			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println("〉〉〉〉〉〉〉〉利息税费总和 = "+ interestTax);
    	
    	return interestTax;
    }
}
