/*
 * Created on 2004-2-11
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.iss.itreasury.closesystem;
import com.iss.itreasury.closesystem.basebean.FunctionBaseBean;
import com.iss.itreasury.closesystem.basebean.GLExtendBaseBean;
import com.iss.itreasury.closesystem.basebean.GLWithinBaseBean;
import com.iss.itreasury.closesystem.securities.SecGLWithBean;
import com.iss.itreasury.closesystem.settlement.SettGLWithinDao;
import com.iss.itreasury.closesystem.settlement.settFunctionBean;
import com.iss.itreasury.glinterface.gasoft.GLGaSoftBean;
import com.iss.itreasury.glinterface.genersoft.GLGenerSoftBean;
import com.iss.itreasury.glinterface.isoftstone.GLISoftStoneBean;
import com.iss.itreasury.glinterface.kingdee.GLKingDeeBean;
import com.iss.itreasury.glinterface.anyi.GLAnyiR9Bean;
import com.iss.itreasury.glinterface.issacnt.GLISSACNTBean;
import com.iss.itreasury.glinterface.joinCheer.GLJoinCheerBean;
import com.iss.itreasury.glinterface.nc.NCBean;
import com.iss.itreasury.glinterface.oracle.GLOracleFinBean;
import com.iss.itreasury.glinterface.oracle_cpf.GLOracleFinBean_cpf;
import com.iss.itreasury.glinterface.u850.GLU850Bean;
import com.iss.itreasury.system.setting.dataentity.GlSettingInfo;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.IException;
import com.iss.itreasury.util.Log;
/**
 * @author yychen
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BeanFactory
{
	/**
	 * 
	 */
	public BeanFactory()
	{
		super();

	}
	public static void main(String[] args)
	{
		
	}
	/*
		 * 按模块得到所需的Bean;
		 * */
	public static FunctionBaseBean getFunctionBean(long lModelID) throws Exception
	{
		FunctionBaseBean bean = null;
		try
		{
			switch ((int) lModelID)
			{
				case (int) Constant.ModuleType.SETTLEMENT :
					bean = new settFunctionBean();
					break;
				case (int) Constant.ModuleType.SECURITIES :
					break;
				default :
					break;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return bean;
	}
	/*
	 * 按模块得到所需的Bean;
	 * */
	public static GLWithinBaseBean getGLWithinBean(long lModelID) throws Exception
	{
		GLWithinBaseBean bean = null;
		try
		{
			switch ((int) lModelID)
			{
				case (int) Constant.ModuleType.SETTLEMENT :
					bean = new SettGLWithinDao();
					break;
				case (int) Constant.ModuleType.SECURITIES :
					bean = new SecGLWithBean();
					break;					
				default :
					bean = new SettGLWithinDao();
					break;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		return bean;
	}

	
	/*
	* 按后台总账得到所需的Bean;  后台总账的类型是根据在数据库中得到的总账类型GLType
	*/
	public static GLExtendBaseBean getGLExtendBean(long lOfficeID, long lCurrencyID) throws Exception
	{
		GLExtendBaseBean bean = null;
		try{
					
			GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
			
			if(glSettingInfo.getGlName().equalsIgnoreCase("GENERSOFT"))
			{
				//浪潮接口
			    Log.print("总账接口=GENERSOFT(浪潮),--初始化：（GLGenerSoftBean）");
				bean = new GLGenerSoftBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("KINGDEE"))
			{
				//金蝶接口
			    Log.print("总账接口=KINGDEE(金蝶),--初始化：（GLKingDeeBean）");
				bean = new GLKingDeeBean();			
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ANYI"))
			{
				//安易接口
			    Log.print("总账接口=ANYI(安易),--初始化：（GLAnyiR9Bean）");
				bean = new GLAnyiR9Bean();			
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ISSACNT"))
			{
				//ISSACNT接口
			    Log.print("总账接口=ISSACNT(软通财务核算模块),--初始化：（GLISSACNTBean）");
				bean = new GLISSACNTBean();			
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ORACLE"))
			{
				//ORACLE
				Log.print("总账接口=ORACLE,--初始化：（GLOracleFinBean）");
				bean = new GLOracleFinBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ORACLE_CPF"))
			{
				//ORACLE_CPF
				Log.print("总账接口=ORACLE_CPF(oracle中油),--初始化：（GLOracleFinBean_cpf）");
				bean = new GLOracleFinBean_cpf();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("U850"))
			{
				//用友U850
			    Log.print("总账接口=U850,--初始化：（GLU850Bean）");
				bean = new GLU850Bean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("GASOFT"))
			{
				//内部财务
			    Log.print("总帐接口=GASOFT,--初始化：（GASOFT）");
				bean = new GLGaSoftBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("NC"))
			{
				//用友NC
			    Log.print("总帐接口=NC,--初始化：（NCBean）");
				bean = new NCBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("JOINCHEER"))
			{
				//久其财务
				Log.print("总帐接口=JOINCHEER,--初始化：（JOINCHEER）");
				bean = new GLJoinCheerBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ISOFTSTONE"))
			{
				//内部财务
			    Log.print("总账接口=ISOFTSTONE,--初始化：（GLISoftStoneBean）");
				bean = new GLISoftStoneBean();
			}
			else{
				//内部财务（是分支默认的处理）
			    Log.print("总账接口=ISOFTSTONE（是分支默认的处理）,--初始化：（GLISoftStoneBean）");
				bean = new GLISoftStoneBean();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		return bean;
	}
	
}
