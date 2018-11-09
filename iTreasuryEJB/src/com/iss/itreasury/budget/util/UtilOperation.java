package com.iss.itreasury.budget.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;

import com.iss.itreasury.budget.exception.BudgetException;
import com.iss.itreasury.budget.executecontrol.dao.Budget_ItemSumDAO;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.budget.executecontrol.dataentity.ControlInfo;
import com.iss.itreasury.budget.setting.dao.Budget_ItemPrivilegeDAO;
import com.iss.itreasury.budget.setting.dao.Budget_ParameterDAO;
import com.iss.itreasury.budget.setting.dataentity.ParameterInfo;
import com.iss.itreasury.budget.templet.dao.Budget_TempletDAO;
import com.iss.itreasury.budget.templet.dataentity.BudgetTempletInfo;
import com.iss.itreasury.dao.ITreasuryDAOException;
/**
 * @author weilu
 公用工具类 To  change this generated comment edit the template variable "typecomment":
 Window>Preferences>Java>Templates. To enable and disable the creation of type
 comments go to Window>Preferences>Java>Code Generation.
 */
public class UtilOperation
{
	protected Log4j log = new Log4j(Constant.ModuleType.BILL, this);
	
	
	/**
	 * 得到系统参数Info
	 * @return
	 * @throws Exception
	 */
	public static ParameterInfo getParameter() throws Exception
	{
		Budget_ParameterDAO dao = new Budget_ParameterDAO();
		return dao.findParameter();
		
	}
	/**
	 * 校验交易账户是否控制
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long checkAccountBudget(ControlInfo info) throws Exception {
		Budget_ItemPrivilegeDAO dao = new Budget_ItemPrivilegeDAO();
		return dao.findItemPivilege(info);
	}
	
	/**
	* 对外公用接口（结算或网银都调此方法）
	* 校验预算项目是否超出预算，关联结算交易(未复核交易)，
    * 预算执行情况汇总，网银指令表（未接收指令），账户交易明细
    * 区分刚性还是柔性预算
    * 
	*/
    public ControlInfo checkBudget(ControlInfo info) throws Exception {
    	ControlInfo  infoRtn  = new ControlInfo();
    	try {
			Budget_ItemSumDAO dao = new Budget_ItemSumDAO();
			Budget_TempletDAO templetDao = new Budget_TempletDAO();
			
			Budget_ItemPrivilegeDAO pvDao = new Budget_ItemPrivilegeDAO();
			
			BudgetTempletInfo templetInfo=new BudgetTempletInfo();
			templetInfo = (BudgetTempletInfo) templetDao.findByID(info.getItemID(),new BudgetTempletInfo().getClass());
			
			//校验是否存在预算,不存在则抛异常
			long lFlag = dao.checkBudgetItem(info);
			if(lFlag==0)
			{
				throw new IException("没有符合条件的预算存在，请先编制预算！");
			}
			
			long lCheck=pvDao.findItemPivilege(info);
			if(lCheck<0)
			{
				infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.UNBUDGET);
				return infoRtn;
			}
			
			double balance = dao.getMinBudgetBalance(info);
			System.out.println("---可用最小预算金额----------***"+balance);
			
			double suppleBalance = dao.getMinSuppleBudgetBalance(info);
			System.out.println("---可用最小柔性提示预算金额----------***"+suppleBalance);
			
			double unCheckAmount = dao.getUnCheckAmount(info);			
			System.out.println("---活期交易累计未复核金额----------**"+unCheckAmount);
			
			double unImportAmount = dao.getUnImportAmount(info);
			
			System.out.println("---交易累计未导入金额----------*"+unImportAmount);
			
			double unAcceptAmount = dao.getUnAcceptAmount(info);
			
			System.out.println("---网银指令累计未接收金额----------*"+unAcceptAmount);
			
			if(templetInfo.getControlType()==BUDGETConstant.BooleanValue.ISTRUE)    //项目是否控制
			{
				//保存时判断			
				if(info.getCheckType()==BUDGETConstant.TransactionStatus.SAVE)
				{				
					
					if(balance-unCheckAmount-unImportAmount-unAcceptAmount<info.getAmount())
					{
						//throw new BudgetException("Budget_E005",null);
						infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.OVERRIGIDITY);
						infoRtn.setErrMsg("项目" + BUDGETNameRef.getItemNoByID(info.getItemID()) + "超出预算！" );
					}
					else							
					{
						if(suppleBalance-unCheckAmount-unImportAmount-unAcceptAmount<info.getAmount())
						{
							infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.OVERSUPPLENESS);
							infoRtn.setErrMsg("项目" + BUDGETNameRef.getItemNoByID(info.getItemID()) + "透支，是否继续！" );
						}
						else
						{
							infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.PASS);
						}
					}			
				}
				else if(info.getCheckType()==BUDGETConstant.TransactionStatus.CHECK)  //复核时判断
				{
				
					if(balance-unImportAmount<info.getAmount())
					{
						//throw new BudgetException("Budget_E005",null);
						
						infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.OVERRIGIDITY);
						infoRtn.setErrMsg("项目" + BUDGETNameRef.getItemNoByID(info.getItemID()) + "超出预算！" );
					}
					else
					{
						if(suppleBalance-unImportAmount<info.getAmount())
						{
							infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.OVERSUPPLENESS);
							infoRtn.setErrMsg("项目" + BUDGETNameRef.getItemNoByID(info.getItemID()) + "透支，是否继续！" );
						}
						else
						{
							infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.PASS);
						}
					}					
			
				}
				else
				{
					return infoRtn;
				}
			}
			else           //该项目不控制，查找上级项目
			{
				if(info.getCheckType()==3)    //不校验
				{
					return infoRtn;
				}
				else
				{
					BudgetTempletInfo templetParentInfo=new BudgetTempletInfo();
					if(templetInfo.getParentItemID()>0)
					{
						templetParentInfo = (BudgetTempletInfo) templetDao.findByID(templetInfo.getParentItemID(),new BudgetTempletInfo().getClass());					
						info.setItemID(templetParentInfo.getId());
						infoRtn = checkBudget(info);
					}
				}
			}
			
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BudgetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        //return dao.checkBudget(info);
        return infoRtn;
        
    }
    
    public static String dataentityToString(Object dataentity)
	{

		StringBuffer res = new StringBuffer(dataentity.getClass().getName() + " \n");
		Method[] methods = dataentity.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++)
		{
			Method tmp = methods[i];
			String mName = tmp.getName();
			if (mName.startsWith("get"))
			{
				String fName = mName.substring(3);
				res.append(fName + " = ");
				try
				{
					Object o = tmp.invoke(dataentity, new Object[]{});
					if (o == null)
					{
						res.append(" null \n");
						continue;
					}
					if (o instanceof Double)
					{
						res.append("" + ((Double) o).doubleValue() + " \n");
					}
					if (o instanceof Float)
					{
						res.append("" + ((Float) o).floatValue() + " \n");
					}
					else if (o instanceof Long)
					{
						res.append("" + ((Long) o).longValue() + " \n");
					}
					else if (o instanceof String)
					{
						res.append("" + (String) o + " \n");
					}
					else if (o instanceof Timestamp)
					{
						res.append("" + ((Timestamp) o).toString() + " \n");
					}
					else
						continue;
				}
				catch (IllegalAccessException e)
				{
					continue;
				}
				catch (InvocationTargetException e)
				{
					continue;
				}
			}

		}
		return res.toString();
	}
	
}