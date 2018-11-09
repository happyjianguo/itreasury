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
		 * ��ģ��õ������Bean;
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
	 * ��ģ��õ������Bean;
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
	* ����̨���˵õ������Bean;  ��̨���˵������Ǹ��������ݿ��еõ�����������GLType
	*/
	public static GLExtendBaseBean getGLExtendBean(long lOfficeID, long lCurrencyID) throws Exception
	{
		GLExtendBaseBean bean = null;
		try{
					
			GlSettingInfo glSettingInfo=new GlSettingInfo();
			glSettingInfo=GLU850Bean.getGlSettingInfo(lOfficeID,lCurrencyID);
			
			if(glSettingInfo.getGlName().equalsIgnoreCase("GENERSOFT"))
			{
				//�˳��ӿ�
			    Log.print("���˽ӿ�=GENERSOFT(�˳�),--��ʼ������GLGenerSoftBean��");
				bean = new GLGenerSoftBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("KINGDEE"))
			{
				//����ӿ�
			    Log.print("���˽ӿ�=KINGDEE(���),--��ʼ������GLKingDeeBean��");
				bean = new GLKingDeeBean();			
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ANYI"))
			{
				//���׽ӿ�
			    Log.print("���˽ӿ�=ANYI(����),--��ʼ������GLAnyiR9Bean��");
				bean = new GLAnyiR9Bean();			
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ISSACNT"))
			{
				//ISSACNT�ӿ�
			    Log.print("���˽ӿ�=ISSACNT(��ͨ�������ģ��),--��ʼ������GLISSACNTBean��");
				bean = new GLISSACNTBean();			
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ORACLE"))
			{
				//ORACLE
				Log.print("���˽ӿ�=ORACLE,--��ʼ������GLOracleFinBean��");
				bean = new GLOracleFinBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ORACLE_CPF"))
			{
				//ORACLE_CPF
				Log.print("���˽ӿ�=ORACLE_CPF(oracle����),--��ʼ������GLOracleFinBean_cpf��");
				bean = new GLOracleFinBean_cpf();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("U850"))
			{
				//����U850
			    Log.print("���˽ӿ�=U850,--��ʼ������GLU850Bean��");
				bean = new GLU850Bean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("GASOFT"))
			{
				//�ڲ�����
			    Log.print("���ʽӿ�=GASOFT,--��ʼ������GASOFT��");
				bean = new GLGaSoftBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("NC"))
			{
				//����NC
			    Log.print("���ʽӿ�=NC,--��ʼ������NCBean��");
				bean = new NCBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("JOINCHEER"))
			{
				//�������
				Log.print("���ʽӿ�=JOINCHEER,--��ʼ������JOINCHEER��");
				bean = new GLJoinCheerBean();
			}
			else if(glSettingInfo.getGlName().equalsIgnoreCase("ISOFTSTONE"))
			{
				//�ڲ�����
			    Log.print("���˽ӿ�=ISOFTSTONE,--��ʼ������GLISoftStoneBean��");
				bean = new GLISoftStoneBean();
			}
			else{
				//�ڲ������Ƿ�֧Ĭ�ϵĴ���
			    Log.print("���˽ӿ�=ISOFTSTONE���Ƿ�֧Ĭ�ϵĴ���,--��ʼ������GLISoftStoneBean��");
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
