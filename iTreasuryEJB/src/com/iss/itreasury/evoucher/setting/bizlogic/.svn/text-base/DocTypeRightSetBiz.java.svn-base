package com.iss.itreasury.evoucher.setting.bizlogic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dao.DocTypeRightSetDao;
import com.iss.itreasury.evoucher.setting.dataentity.DocTypeRightEntity;
import com.iss.itreasury.util.Database;

public class DocTypeRightSetBiz {

	/**
	 * 获取有权限的信息
	 * @param dtre
	 * @return
	 * @throws VoucherException 
	 */
	public List getHasRightDocs(DocTypeRightEntity dtre) throws VoucherException{
		DocTypeRightSetDao dao = new DocTypeRightSetDao();
		List list = new ArrayList();
		try {
			Collection c = dao.findByCondition(dtre);
			Iterator it = c.iterator();
			while(it.hasNext()){
				DocTypeRightEntity d = (DocTypeRightEntity)it.next();
				list.add(d.getNbilltypeid()+"");
			}
			return list;
		} catch (ITreasuryDAOException e) {
			// TODO Auto-generated catch block
			throw new VoucherException("Gen_E001", e);
		}
	}
	
	/**
	 * 保存权限
	 * @param ids
	 * @throws ITreasuryDAOException 
	 * @throws SQLException 
	 * @throws VoucherException 
	 */
	public void setRight(String ids,long userId,long ncurrencyid,long nofficeid) throws ITreasuryDAOException, SQLException, VoucherException{
		Connection con=null;
		try{
			con=Database.getConnection();
			DocTypeRightSetDao dao = new DocTypeRightSetDao(con);
		if(ids==null||"".equals(ids)){
			dao.setRight(ncurrencyid, nofficeid);
		}else{
			String insertIds[]=ids.split(",");
			dao.setRight(ncurrencyid, nofficeid);
			dao.setUseMaxID();
			for(int i=0;i<insertIds.length;i++){
				long id = Long.parseLong(insertIds[i]);
				DocTypeRightEntity d = new DocTypeRightEntity();
				d.setDtinputdate(new Date());
				d.setInputuserid(userId+"");
				d.setNbilltypeid(id);
				d.setNcurrencyid(ncurrencyid);
				d.setNofficeid(nofficeid);
				d.setNissignature(1);
				dao.add(d);
			}	
		}
		}catch (Exception e) {
			// TODO: handle exception
			throw new VoucherException("Gen_E001", e);
		}finally{
			if(con!=null||!con.isClosed()){
				con.close();
				con=null;
			}
		}
	}
	
	
}
