package com.iss.itreasury.evoucher.voucheraccount.bizlogic;

import java.sql.Connection;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.voucheraccount.dao.SubVoucherTypeDAO;
import com.iss.itreasury.evoucher.voucheraccount.dao.VoucherTypeDAO;
import com.iss.itreasury.evoucher.voucheraccount.dataentity.SubVoucherTypeInfo;
import com.iss.itreasury.evoucher.voucheraccount.dataentity.VoucherTypeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;


public class VoucherTypeBiz {
	
	/**
	 * 分页查询显示账户回单属性设置
	 * @param info
	 * @return PageLoader
	 * @throws VoucherException
	 */
	public PageLoader getVouchTypeList(VoucherTypeInfo info) throws Exception{
		
		Connection con = null;
		PageLoader pageLoader = null;
		
		try{
			con = Database.getConnection();
			
			VoucherTypeDAO vouDAO = new VoucherTypeDAO(con);
			
			pageLoader = vouDAO.getVouchTypeList(info);

			
		}catch(Exception e){
			e.printStackTrace();
			throw new IException("查询失败：" + e.getMessage());
			
		}finally{
			
			if(con!=null){
				con.close();
				con = null;
			}
		}
		
		return pageLoader;
	}
	
	
	/**
	 * 初始化指定账户组的账户为普通账户类型
	 * 如果没有指定账户组，默认为活期账户
	 * @throws VoucherException
	 */
	public long initVoucherTypeAccount(long accountGroupID,VoucherTypeInfo info)throws Exception{
		
		long lRnt = -1 ;
		
		Connection con = Database.getConnection();
		
		try{
			
			con.setAutoCommit(false);
			VoucherTypeDAO vouDAO = new VoucherTypeDAO(con);
			
			if(accountGroupID<=0){
				accountGroupID = SETTConstant.AccountGroupType.CURRENT ;
			}
	
			lRnt = vouDAO.initVoucherTypeAccount(accountGroupID, info);
			lRnt = vouDAO.updateSysVoucher();
			
			con.commit();
			
		}catch(Exception e){
			con.rollback();
			e.printStackTrace();
			throw e;
		}finally{
			if(con!=null){
				con.close();
				con = null ;
			}
		}
		
		return lRnt;
		
	}
	
	
	
	/**
	 * 账户回单设置是否已经完成初始化
	 * @return
	 * @throws Exception
	 */
	public boolean isInitVoucher()throws Exception{
		
		VoucherTypeDAO vouDAO = new VoucherTypeDAO();
		
		boolean bRnt = false ;
		try{
			bRnt = vouDAO.isInitVoucher();
		}catch(Exception e){
			throw e;
		}
		
		return bRnt;
	}
	
	
	/**
	 * 根据ID查询实体Info
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public VoucherTypeInfo getByID(long id) throws Exception{
		
		VoucherTypeDAO vouDAO = new VoucherTypeDAO();
		VoucherTypeInfo info = null ;
		
		try{
			info = (VoucherTypeInfo)vouDAO.findByID(id, VoucherTypeInfo.class);
		}catch(Exception ex){
			throw ex;
		}
		
		return info;
	}
	
	
	/**
	 * 回单设置修改
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long updateVoucherAccountType(VoucherTypeInfo info) throws Exception{
		
		long lRtn = -1 ;
		try{
			VoucherTypeDAO vouDAO = new VoucherTypeDAO();
			vouDAO.update(info);
			lRtn = 1 ;
		}catch(Exception e){
			lRtn = -1 ;
			throw e ;
		}
		
		return lRtn;
	}
	
	
	/**
	 * 删除回单设置
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public long deleteVoucherAccountType(long id) throws Exception{
		
		long lRtn = -1 ;
		
		VoucherTypeDAO vouDAO = new VoucherTypeDAO();
		SubVoucherTypeDAO subDAO = new SubVoucherTypeDAO();
		
		try{
			// 页面js判断是否存在归口子账户，此处不需要程序判断
			//if(!subDAO.isExistSubVoucherAccount(id)){
				vouDAO.delete(id);
				//vouDAO.deletePhysically(id);
				lRtn = 1 ;
			//}else{
			//	throw new Exception("此账户下存在归口子账户，不能删除！");
			//}
		}catch(Exception e){
			lRtn = -1 ;
			throw e ;
		}
		
		return lRtn;
	}
	
	
	/**
	 * 增加一个账户的回单设置
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public long addVoucherAccountType(VoucherTypeInfo info) throws Exception{
		
		long lRtn = -1 ;
		try{
			VoucherTypeDAO vouDAO = new VoucherTypeDAO();
			if(vouDAO.isExistVoucherAccount(info.getAccountid(), info.getOfficeid(), info.getCurrencyid())){
				throw new Exception("此账户已经做过回单设置，请检查！");
			}else{
				lRtn = vouDAO.add(info);
				//vouDAO.deletePhysically(id);
			}

		}catch(Exception e){
			lRtn = -1 ;
			e.printStackTrace();
			throw e ;
		}
		
		return lRtn;
	}
	
	
	/**
	 * @see  查询某一归口账户下的所有归口子账户
	 * @param voucherID
	 * @return
	 * @throws Exception
	 */
	public Collection getSubAccountByVoucherID(long voucherID)throws Exception{
		
		SubVoucherTypeDAO subDAO = new SubVoucherTypeDAO();
		Vector v = new Vector();
		try{
			v = (Vector)subDAO.getSubAccountByVoucherID(voucherID);
		}catch(Exception e){
			throw e ;
		}
		return v ;
	}
	
	
	
	
	/**
	 * @see 查询回单设置中所有的普通账户
	 * @param officeid
	 * @param currencyid
	 * @return
	 * @throws Exception
	 */
	public Collection getAllVoucherAccount(long id,long officeid,long currencyid)throws Exception{
		
		VoucherTypeDAO vouDAO = new VoucherTypeDAO();
		Vector v = new Vector();
		try{
			v = (Vector)vouDAO.getAllVoucherAccount(id,officeid, currencyid);
		}catch(Exception e){
			throw e ;
		}
		return v ;
	}
	
	
	
	/**
	 * @see 修改归口账户信息，同时保存此账户的所有关联的归口子账户
	 * @param mainInfo  归口账户Info
	 * @param subInfo   归口子账户Info
	 * @return
	 * @throws Exception
	 */
	public long saveSubVoucherAccountType(VoucherTypeInfo mainInfo,SubVoucherTypeInfo subInfo) throws Exception 
	{
		long lRtn = -1 ;
		
		Connection con = null ;
		
		try{
			
			con = Database.getConnection() ;
			con.setAutoCommit(false);
			
			VoucherTypeDAO    mainDAO = new VoucherTypeDAO(con);
			SubVoucherTypeDAO subDAO  = new SubVoucherTypeDAO(con); 
			
			// 1  保存归口账户信息
			mainDAO.update(mainInfo);	
			
			// 2 先删除归口账户下的归口子账户
			subDAO.deleteSubVoucherAccount(subInfo.getVoucherid());
			
			// 3 修改原始归口子账户信息为普通账户
			mainDAO.updateSubAccountToNormalAccount(mainInfo.getSubaccountids());
			
			// 4 保存归口子账户信息并且修改新的归口子账户信息
			subDAO.saveSubVoucherAccount(subInfo);
			
			con.commit();
			
		}catch(Exception e){
			
			con.rollback();
			e.printStackTrace();
			throw e ;
			
		}finally{
			try{
				if(con!=null){
					con.close();
					con = null;
				}
			}catch(Exception ex){
				ex.printStackTrace();
				throw ex ;
			}
		}
		
		return lRtn;
		
	}

}
