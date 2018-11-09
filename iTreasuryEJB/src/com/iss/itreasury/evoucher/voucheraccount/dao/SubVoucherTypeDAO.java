package com.iss.itreasury.evoucher.voucheraccount.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.evoucher.setting.dao.PrintDAO;
import com.iss.itreasury.evoucher.voucheraccount.dataentity.SubVoucherTypeInfo;
import com.iss.itreasury.evoucher.voucheraccount.dataentity.VoucherTypeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;

public class SubVoucherTypeDAO extends PrintDAO {

	
	public SubVoucherTypeDAO() {
		super("print_SubVoucherType");
	}
	
	public SubVoucherTypeDAO(String tableName) {
		super(tableName);
	}

	public SubVoucherTypeDAO(Connection conn) {
		super("print_SubVoucherType", conn);
	}

	
	/**
	 * @see  查询某一归口账户下的所有归口子账户
	 * @param voucherID
	 * @return
	 * @throws Exception
	 */
	public Collection getSubAccountByVoucherID(long voucherID)throws Exception{
		
		Collection c = null;
		
		Vector vector = new Vector();
		SubVoucherTypeInfo subInfo = null;
		
		
		String sql  = " select sv.*,ac.saccountno,ac.naccounttypeid,ac.sname from print_SubVoucherType sv ,sett_account ac " ;
		       sql += " where sv.accountid = ac.id and sv.voucherid = ? and sv.statusid > 0 order by sv.accountid " ;
			
		try {
			
			initDAO();
			prepareStatement(sql);
			transPS.setLong(1, voucherID);
			executeQuery();
			
			while(transRS.next()){
				
				subInfo = new SubVoucherTypeInfo();
				subInfo.setAccountid(transRS.getLong("accountid"));
				subInfo.setAccountno(transRS.getString("saccountno"));
				subInfo.setAccountName(transRS.getString("sname"));
				
				vector.add(subInfo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("获取归口子账户查询失败!");
			
		}finally{
			
			this.finalizeDAO();
		}
		
		return vector.size()>0?vector:null ;
		
	}
	
	
	/**
	 * @see 添加归口账户信息
	 * @param subInfo
	 * @return
	 * @throws Exception
	 */
	public long saveSubVoucherAccount(SubVoucherTypeInfo subInfo) throws Exception {
		
		long lRtn = -1;
		VoucherTypeInfo info = null ;
		
		long mainAccountID = subInfo.getAccountid();
		
		try{
			
			this.initDAO();
			String[] ids = subInfo.getSubIDs() ;

			
			if(ids !=null && ids.length>0){
				for(int i=0;i<ids.length;i++)
				{
					
					info = new VoucherTypeInfo();
					subInfo.setAccountid(Long.valueOf(ids[i]).longValue());
					
					// 增加归口账户下的归口子账户
					this.add(subInfo);
					
					info.setVouchertypeid(subInfo.getVouchertypeid());            //归口子账户
					info.setPlacetypeid(subInfo.getPlacetypeid());
					info.setAccountid(subInfo.getAccountid());
					info.setParrentaccountid(mainAccountID);
					
					// 修改已经添加为归口子账户的普通账户信息
					this.updateVoucherAccount(info);
					
				}
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new Exception("增加归口子账户信息失败");
			
		}finally{
			this.finalizeDAO();
		}
		
		return lRtn ;
	}
	
	
	
	/**
	 * @see 物理删除
	 *      根据归口账户ID删除所有的归口子账户
	 * @param vouchID
	 * @throws Exception
	 */
	public void deleteSubVoucherAccount(long vouchID) throws Exception
	{
		String sql = " delete from print_subvouchertype ps where  ps.voucherid = ? " ;
		
		try{
			this.initDAO();
			
			this.prepareStatement(sql);
			
			this.transPS.setLong(1, vouchID);
			
			this.executeUpdate();
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new Exception("删除归口子账户信息失败!");
		}finally{
			
			this.finalizeDAO();
		}
	}
	
	
	
	/**
	 * @see 普通账户添加到归口账户以后成为归口子账户，修改其信息
	 *     info.getVouchertypeid()  回单属性
	 *     info.getPlacetypeid()    网点属性
	 *     info.getAccountid()      账户
	 *     必须赋值
	 * @param info
	 * @throws Exception
	 */
	public void updateVoucherAccount(VoucherTypeInfo info) throws Exception{
		
		String sql = " update print_VoucherType pv set pv.vouchertypeid = ? , pv.placetypeid = ?,pv.parrentAccountID = ? where pv.accountid = ? and pv.statusid > 0 " ;
		
		try{
			
			this.initDAO();
			this.prepareStatement(sql);
			this.transPS.setLong(1, info.getVouchertypeid());
			this.transPS.setLong(2, info.getPlacetypeid());
			this.transPS.setLong(3, info.getParrentaccountid());
			this.transPS.setLong(4, info.getAccountid());
			
			this.executeUpdate();
			
		}catch(Exception e){
			
			e.printStackTrace();
			throw new Exception("修改归口子账户信息失败");
			
		}finally{
			
			this.finalizeDAO();
		}
		
	} 
	
	
	/**
	 * @see  判断某一账户下面是否存在归口账户信息
	 * @param voucherAccountID
	 * @return
	 * @throws Exception
	 */
	public boolean isExistSubVoucherAccount(long voucherID) throws Exception{
		
		boolean bRtn = false ;
		long lCount = -1 ;
		
		String sql = " select count(ps.id) as num from print_subvouchertype ps where ps.voucherID = ? and ps.statusID > 0 " ;
		
		try{
			this.initDAO();
			
			this.prepareStatement(sql);
			
			this.transPS.setLong(1, voucherID);
			
			this.executeQuery();
			
			while(transRS.next()){
				lCount = transRS.getLong("num");
				if(lCount>0){
					bRtn = true ;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("判断某一账户下面是否存在归口子账户信息失败");
		}finally{
			this.finalizeDAO();
		}
		
		return bRtn ;
		
	}
	
	
}
