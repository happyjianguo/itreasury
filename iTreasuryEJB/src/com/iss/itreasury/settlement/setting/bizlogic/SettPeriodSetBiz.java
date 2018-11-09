/*
 * Created on 2004-1-12
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.setting.bizlogic;

import java.util.Collection;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.settlement.base.SettlementException;
import com.iss.itreasury.settlement.setting.dao.Sett_PeriodSetDao;
import com.iss.itreasury.settlement.setting.dataentity.PeriodSetInfo;
import com.iss.itreasury.util.IException;

/**
 * @author yangliu
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SettPeriodSetBiz
{
	Sett_PeriodSetDao dao = null;

    /**
     * Constructor for SettPeriodSetBiz
     */
    public SettPeriodSetBiz()
    {
        dao = new Sett_PeriodSetDao();
    }
    
	/**
	 * �½����޸ļ�¼
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long save(PeriodSetInfo info)throws  IException{
		long retlong=-1;
		try{
			if(info.getId()>0){
				retlong=dao.add(info);				
			}else{
				dao.update(info);
				retlong=info.getId();				
			}
		}catch(Exception ex){throw new IException("Gen_E001");}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		return retlong;
	}
	
	/**
	 * ����ID��ѯ��¼��ϸ��Ϣ
	 * @param id
	 * @return
	 * @throws IException
	 */
	public PeriodSetInfo findById(long id) throws IException
	{
		PeriodSetInfo info=null;
		try{
			info = (PeriodSetInfo)dao.findByID(id,PeriodSetInfo.class);
		}catch (IException ie){			
			throw new IException("Gen_E001");
		}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		
		return info;
	}
	
	/**
	 * ���ݲ�ѯ������ѯ��¼
	 * @param info
	 * @return
	 * @throws IException
	 */
	public Collection queryByCondition(PeriodSetInfo info)throws IException{	
		Collection coll=null;
		try{
			
			coll = dao.findByCondition(info);
		}catch (IException ie){			
			throw new IException("Gen_E001");
		}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		return coll;
	}
	
	/**
	 * ���ݲ�ѯ������ѯ��¼����ʹ�ù�������
	 * @param info
	 * @return
	 * @throws IException
	 */
	public PeriodSetInfo findByCondition(PeriodSetInfo info)throws IException{
		List list = null;
		PeriodSetInfo periodSetInfo = new PeriodSetInfo();
		try{
			list = dao.findByCondition(info);
			if(list != null && list.size() > 0){
				periodSetInfo = (PeriodSetInfo)list.get(0);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return periodSetInfo;
	}
	
	/**
	 * ���ݼ�¼IDɾ����¼
	 * @param id
	 * @return
	 * @throws IException
	 */
	public long deleteById(long id)throws IException{	
		long retlong =-1;
		try{
			PeriodSetInfo info=new PeriodSetInfo();
			info.setId(id);
			info.setStatusId(0);//��ʽ�����ñ�����ʾ
			dao.update(info);
			retlong = id;
		}catch (IException ie){			
			throw new IException("Gen_E001");
		}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		return retlong;
	}
	
	/**
	 * ���ݼ�¼IDɾ����¼
	 * @param id
	 * @return
	 * @throws IException
	 */
	public long update(PeriodSetInfo info)throws IException{	
		long retlong =-1;
		try{
			dao.update(info);		
			retlong = 1;
		}catch (IException ie){			
			throw new IException("Gen_E001");
		}
		finally 
        {
            try 
            {
                dao.clearDAO();
            }
            catch ( ITreasuryDAOException e1 ) 
            {
                e1.printStackTrace();
                throw new SettlementException();
            }
        }
		return retlong;
	}
	
	/**
	 * ������¼
	 */
	public long addPeropdSet(PeriodSetInfo info)throws IException{
		long id = -1;
		try{
			id = dao.addPeropdSet(info);
		}catch(IException ie){
			throw new IException("Gen_E001");
		}
		return id;
	}
	
	/**
	public static void main(String[] args) throws Exception {
		PeriodSetInfo info = new PeriodSetInfo();
		info.setId(1);//id
		
		info.setInputDate(Env.getSystemDateTime());//��������
		
		info.setInputUserId(1);//������
		
		info.setModifyDate(Env.getSystemDateTime() );//�޸�����
		
		info.setModifyUserId(1);//�޸���
		
		info.setOfficeId(1);//���´�;
		
		info.setOldPeriod(30);//ԭ����
		
		info.setOldStartDate(Env.getSystemDateTime());//ԭ��ʼ����
		
		info.setPeriod(30);//������
		
		info.setStartDate(Env.getSystemDateTime());//�¿�ʼ���� 
					
		info.setPeriodName("11");//�ƻ���
		
		info.setStatusId(1);//״̬
		
		info.setCurrencyId(1);//�ƻ����Һ�
		
		SettPeriodSetBiz biz = new SettPeriodSetBiz();
		
		long lReturn = biz.update(info);
		
		System.out.println("lReturn========"+lReturn);
	}
	**/

}
