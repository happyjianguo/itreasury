/* Generated by Together */

package com.iss.itreasury.clientmanage.client.bizlogic;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;



import com.iss.itreasury.clientmanage.client.dao.ClientDAO;
import com.iss.itreasury.clientmanage.client.dao.CorporationDAO;

import com.iss.itreasury.clientmanage.client.dao.NatureDAO;
import com.iss.itreasury.clientmanage.client.dataentity.CorporationInfo;
import com.iss.itreasury.clientmanage.client.dataentity.NatureInfo;
import com.iss.itreasury.clientmanage.client.dataentity.QueryClientInfo;
import com.iss.itreasury.clientmanage.dataentity.ClientInfo;

import com.iss.itreasury.command.Command;



import com.iss.itreasury.util.Database;


 
/* Generated by Together */

public class NatureCmd extends Command{
	  /**
	   *���ӿͻ���Ϣ
	 * @throws Exception
	   * */
	public long add(NatureInfo dataEntity) throws Exception{
    	long myid = -1;
    	Connection con = null;
    	con = Database.getConnection();
    	NatureDAO  naturedao = new NatureDAO(con);
    
    	naturedao.setUseMaxID();
    	
    	myid = naturedao.add(dataEntity);
    	
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	
    	return myid;
    }

    /**
     *���¿ͻ���Ϣ
     * @throws Exception
     * */
    public void update(NatureInfo nDataEntity,ClientInfo cDataEntity) throws Exception{
    	
    	Connection con = null;
    	con = Database.getConnection();
    	NatureDAO  naturedao = new NatureDAO(con);
    	ClientDAO clientdao =  new ClientDAO(con);
    	//���¸��ͻ���Ϣ
    	clientdao.update(cDataEntity);
        //������Ȼ����Ϣ
    	naturedao.update(nDataEntity);
    	
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    }

    public void delete(long id) throws Exception{
    	Connection con = null;
    	con = Database.getConnection();
    	
    	NatureDAO  naturedao = new NatureDAO(con);
    	ClientDAO  clientdao =  new ClientDAO(con);
    
    	clientdao.delete(id);//ɾ������Ϣ
    	
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	
    }

   
    
    /**
     *�����Ȼ�˿ͻ���Ϣ���������ͻ���Ϣ
     * @throws Exception
     * */
    public NatureInfo load(long id) throws Exception {
        //�����Ȼ�˿ͻ���Ϣ
    	Connection con = null;
    	con = Database.getConnection();
    	NatureInfo natureinfo = new NatureInfo();
    	ClientInfo clientinfo = new ClientInfo();
    	
    	NatureDAO  naturedao = new NatureDAO(con);
    	
    	ClientDAO  clientdao =  new ClientDAO(con);
    	
    	natureinfo = naturedao.findByclietID(id);
    	clientinfo = (ClientInfo)clientdao.findByID(id,clientinfo.getClass());
    	 //��ø���Ȼ�˿ͻ���Ϣ�ĸ��ͻ���Ϣ
    	natureinfo.setClientInfo(clientinfo);
    	
    	return natureinfo;
       
    }
    
    /**
     * 
     * @throws Exception
     * 
     *
     * TODO To change the template for this generated type comment go to
     * Window - Preferences - Java - Code Style - Code Templates
     */
    public Collection findByCondition(NatureInfo dataEntity) throws Exception
    {
    	Collection data = null;
    	Connection con = null;
    	try{
	    	con = Database.getConnection();
	    	CorporationInfo corporationinfo = new CorporationInfo();
	    	ClientInfo clientinfo = new ClientInfo();
	    	
	    	CorporationDAO  corporationdao = new CorporationDAO(con);
	    	ClientDAO clientdao = new ClientDAO(con);
	    	
	    	data = corporationdao.findByCondition(dataEntity);
    	
    	}catch(Exception e){
    		throw e;    		
    	}finally{
        	if(con != null)
        	{
        		con.close();
        		con=null;
        	}
    	}

    	return data;
    }
    
    /**
     * 
     * @param dataEntity
     * @return
     * @throws Exception
     * @author chuanliu
     */
    public Collection findCondition(NatureInfo dataEntity) throws Exception
	{
    	Collection data = null;
    	Connection con = null;
    	con = Database.getConnection();
//    	NatureInfo natureInfo = new NatureInfo();
//    	ClientInfo clientinfo = new ClientInfo();
    	
    	NatureDAO naturedao = new NatureDAO(con);
//    	ClientDAO clientdao = new ClientDAO(con);
    	
    	data = naturedao.findByCondition(dataEntity);
    	if(con != null)
    	{
    		con.close();
    		con=null;
    	}
    	return data;
	}
    public static void main(String []args) throws Exception
	{
    	NatureCmd onecmd = new NatureCmd();
    	
    	NatureInfo natureinfo = new NatureInfo();
    	
    	
    	onecmd.delete(107);
    	
    	
    	
    	
		
	}
    

}

