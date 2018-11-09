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
 ���ù����� To  change this generated comment edit the template variable "typecomment":
 Window>Preferences>Java>Templates. To enable and disable the creation of type
 comments go to Window>Preferences>Java>Code Generation.
 */
public class UtilOperation
{
	protected Log4j log = new Log4j(Constant.ModuleType.BILL, this);
	
	
	/**
	 * �õ�ϵͳ����Info
	 * @return
	 * @throws Exception
	 */
	public static ParameterInfo getParameter() throws Exception
	{
		Budget_ParameterDAO dao = new Budget_ParameterDAO();
		return dao.findParameter();
		
	}
	/**
	 * У�齻���˻��Ƿ����
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long checkAccountBudget(ControlInfo info) throws Exception {
		Budget_ItemPrivilegeDAO dao = new Budget_ItemPrivilegeDAO();
		return dao.findItemPivilege(info);
	}
	
	/**
	* ���⹫�ýӿڣ���������������˷�����
	* У��Ԥ����Ŀ�Ƿ񳬳�Ԥ�㣬�������㽻��(δ���˽���)��
    * Ԥ��ִ��������ܣ�����ָ���δ����ָ����˻�������ϸ
    * ���ָ��Ի�������Ԥ��
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
			
			//У���Ƿ����Ԥ��,�����������쳣
			long lFlag = dao.checkBudgetItem(info);
			if(lFlag==0)
			{
				throw new IException("û�з���������Ԥ����ڣ����ȱ���Ԥ�㣡");
			}
			
			long lCheck=pvDao.findItemPivilege(info);
			if(lCheck<0)
			{
				infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.UNBUDGET);
				return infoRtn;
			}
			
			double balance = dao.getMinBudgetBalance(info);
			System.out.println("---������СԤ����----------***"+balance);
			
			double suppleBalance = dao.getMinSuppleBudgetBalance(info);
			System.out.println("---������С������ʾԤ����----------***"+suppleBalance);
			
			double unCheckAmount = dao.getUnCheckAmount(info);			
			System.out.println("---���ڽ����ۼ�δ���˽��----------**"+unCheckAmount);
			
			double unImportAmount = dao.getUnImportAmount(info);
			
			System.out.println("---�����ۼ�δ������----------*"+unImportAmount);
			
			double unAcceptAmount = dao.getUnAcceptAmount(info);
			
			System.out.println("---����ָ���ۼ�δ���ս��----------*"+unAcceptAmount);
			
			if(templetInfo.getControlType()==BUDGETConstant.BooleanValue.ISTRUE)    //��Ŀ�Ƿ����
			{
				//����ʱ�ж�			
				if(info.getCheckType()==BUDGETConstant.TransactionStatus.SAVE)
				{				
					
					if(balance-unCheckAmount-unImportAmount-unAcceptAmount<info.getAmount())
					{
						//throw new BudgetException("Budget_E005",null);
						infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.OVERRIGIDITY);
						infoRtn.setErrMsg("��Ŀ" + BUDGETNameRef.getItemNoByID(info.getItemID()) + "����Ԥ�㣡" );
					}
					else							
					{
						if(suppleBalance-unCheckAmount-unImportAmount-unAcceptAmount<info.getAmount())
						{
							infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.OVERSUPPLENESS);
							infoRtn.setErrMsg("��Ŀ" + BUDGETNameRef.getItemNoByID(info.getItemID()) + "͸֧���Ƿ������" );
						}
						else
						{
							infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.PASS);
						}
					}			
				}
				else if(info.getCheckType()==BUDGETConstant.TransactionStatus.CHECK)  //����ʱ�ж�
				{
				
					if(balance-unImportAmount<info.getAmount())
					{
						//throw new BudgetException("Budget_E005",null);
						
						infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.OVERRIGIDITY);
						infoRtn.setErrMsg("��Ŀ" + BUDGETNameRef.getItemNoByID(info.getItemID()) + "����Ԥ�㣡" );
					}
					else
					{
						if(suppleBalance-unImportAmount<info.getAmount())
						{
							infoRtn.setErrCode(BUDGETConstant.BudgetCheckCode.OVERSUPPLENESS);
							infoRtn.setErrMsg("��Ŀ" + BUDGETNameRef.getItemNoByID(info.getItemID()) + "͸֧���Ƿ������" );
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
			else           //����Ŀ�����ƣ������ϼ���Ŀ
			{
				if(info.getCheckType()==3)    //��У��
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