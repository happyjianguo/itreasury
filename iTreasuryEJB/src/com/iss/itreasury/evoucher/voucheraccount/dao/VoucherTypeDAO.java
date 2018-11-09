package com.iss.itreasury.evoucher.voucheraccount.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.iss.itreasury.evoucher.base.VoucherException;
import com.iss.itreasury.evoucher.setting.dao.PrintDAO;
import com.iss.itreasury.evoucher.voucheraccount.dataentity.SubVoucherTypeInfo;
import com.iss.itreasury.evoucher.voucheraccount.dataentity.VoucherTypeInfo;
import com.iss.itreasury.settlement.util.SETTConstant;
import com.iss.itreasury.util.AppContext;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.util.Database;
import com.iss.itreasury.util.IException;
import com.iss.system.dao.PageLoader;

public class VoucherTypeDAO extends PrintDAO {

	
	public VoucherTypeDAO() {
		super("print_VoucherType");
	}

	public VoucherTypeDAO(String tableName) {
		super(tableName);
	}

	public VoucherTypeDAO(Connection conn) {
		super("print_VoucherType", conn);
	}
	
	
	/**
	 * ��ҳ��ѯ��ʾ�˻��ص���������
	 * @param info
	 * @return PageLoader
	 * @throws VoucherException
	 */
	public PageLoader getVouchTypeList(VoucherTypeInfo info) throws VoucherException {
		
		PageLoader pageLoader = (PageLoader) com.iss.system.BaseObjectFactory.getBaseObject("com.iss.system.dao.PageLoader");
		
		StringBuffer m_sbSelect = new StringBuffer();
		StringBuffer m_sbFrom = new StringBuffer();
		StringBuffer m_sbWhere = new StringBuffer();
		
		m_sbSelect.append(" * ") ;
		m_sbFrom.append(" print_VoucherType ") ;
		m_sbWhere.append(" 1=1 and vouchertypeid <> 3 and   statusid > 0 ");
		
		
		// �ͻ�ID
		if(info.getClientid()>0){
			m_sbWhere.append(" and clientid = " + info.getClientid());
		}
		// �˻�ID
		if(info.getAccountid()>0){
			m_sbWhere.append(" and accountid = " + info.getAccountid());
		}
		// �ص�����
		if(info.getVouchertypeid()>0){
			m_sbWhere.append(" and vouchertypeid = " + info.getVouchertypeid());
		}
		// ��������
		if(info.getPlacetypeid()>0){
			m_sbWhere.append(" and placetypeid = " + info.getPlacetypeid());
		}
		// ���´�
		if(info.getOfficeid()>0){
			m_sbWhere.append(" and officeid = " + info.getOfficeid());
		}
		// ����
		if(info.getCurrencyid()>0){
			m_sbWhere.append(" and currencyid = " + info.getCurrencyid());
		}
		
    	pageLoader.initPageLoader(
    			new AppContext(),
    			m_sbFrom.toString(),
    			m_sbSelect.toString(),
    			m_sbWhere.toString(),
    			(int) Constant.PageControl.CODE_PAGELINECOUNT,
    			"com.iss.itreasury.evoucher.voucheraccount.dataentity.VoucherTypeInfo",
    			null);
    	pageLoader.setOrderBy(" order by accountid ");
		return pageLoader ;
	}
	
	/**
	 * ��ʼ��ָ���˻�����˻�Ϊ��ͨ�˻�����
	 * @throws VoucherException
	 */
	public long initVoucherTypeAccount(long accountGroupID,VoucherTypeInfo info)throws Exception{
		
		String sql = "" ;
		
		Collection col = null; 
		VoucherTypeInfo colinfo = new VoucherTypeInfo();
		
		long lRtn = -1 ;
		
		try {
			
			initDAO();

			col = this.getAccountInfoByGroupID(accountGroupID,info.getOfficeid(),info.getCurrencyid());
			
			if(col!=null){
				Iterator it = col.iterator();
				while(it.hasNext()){
					colinfo = (VoucherTypeInfo)it.next();
					
					sql =  " Insert into print_VoucherType " ;
					sql += " (ID,CLIENTID,ACCOUNTID,VOUCHERTYPEID,PLACETYPEID,INPUTUSERID,INPUTDATE,CHECKUSERID,CHECKDATE,OFFICEID,CURRENCYID,STATUSID,MEMO )";
					sql += " values(SEQ_print_VoucherType.Nextval,?,?,?,?,?,?,?,?,?,?,?,?) " ;
					
					prepareStatement(sql);

					transPS.setLong(1, colinfo.getClientid());
					transPS.setLong(2, colinfo.getAccountid());
					transPS.setLong(3, SETTConstant.VoucherAccountType.NORMAL);
					transPS.setLong(4, SETTConstant.VoucherPlaceType.INSIDE);
					transPS.setLong(5, info.getInputuserid());
					transPS.setTimestamp(6, info.getInputdate());
					transPS.setLong(7, info.getCheckuserid());
					transPS.setTimestamp(8, info.getCheckdate());
					transPS.setLong(9, info.getOfficeid());
					transPS.setLong(10, info.getCurrencyid());
					transPS.setLong(11, info.getStatusid());
					transPS.setString(12, info.getMemo());
					
					this.executeUpdate();
					
				}

				lRtn = 1 ;
				
			}else{
				throw new IException("û�в�ѯ������˻�����˻�!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("��ʼ��ʧ��!");
			
		}finally{		
			this.finalizeDAO();
		}
		
		return lRtn ;
	}
	
	
	/**
	 * �����˻������Ͳ�ѯ���е��˻�
	 * @param accountGroupID
	 * @return
	 * @throws Exception
	 */
	public Collection getAccountInfoByGroupID(long accountGroupID,long officeID,long currencyID)throws Exception{
		
		Vector vector = new Vector();
		VoucherTypeInfo info = null;

		String sql = " select a.id,a.saccountno,a.nclientid,a.sname from sett_account a ,sett_accounttype b " ;
		       sql+= " where a.naccounttypeid = b.id and  a.nstatusid > 0 and a.nofficeid = ? and a.ncurrencyid = ? and a.ncheckstatusid = ?  and b.naccountgroupid = ? " ;
		
		try {
			
			initDAO();
			prepareStatement(sql);
			
			transPS.setLong(1, officeID);
			transPS.setLong(2, currencyID);
			transPS.setLong(3, SETTConstant.AccountCheckStatus.CHECK);
			transPS.setLong(4, accountGroupID);
			
			executeQuery();
			
			while(transRS.next()){
				info = new VoucherTypeInfo();
				info.setClientid(transRS.getLong("nclientid"));
				info.setAccountid(transRS.getLong("id"));
				
				vector.add(info);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("��ʼ��ʧ��!");
			
		}finally{
			
			this.finalizeDAO();
		}
		
		return vector.size()>0?vector:null ;
		
	}
	
	/**
	 * �˻��ص������Ƿ��Ѿ���ɳ�ʼ��
	 * @return
	 * @throws Exception
	 */
	public boolean isInitVoucher()throws Exception{
		
		boolean bRnt = false ;
		long pvalue = -1 ;


		String sql = " select e.propvalue from sys_enumerate e where e.function = ? " ;
		
		try {
			
			/*
			 * propvalue = 0 �Ѿ���ʼ  =1 ��û�г�ʼ��
			 */
			initDAO();
			prepareStatement(sql);
			
			transPS.setString(1, Constant.SystemConstantTable.VOUCHERINIT);

			executeQuery();
			
			if(transRS.next()){
				pvalue = transRS.getLong("propvalue") ;
				if(pvalue==0){
					bRnt = true ;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("�ж��Ƿ��Ѿ���ʼ������ʧ��!");
			
		}finally{
			
			this.finalizeDAO();
		}
		
		return bRnt;
	}

	/**
	 * �˻��ص������Ƿ��Ѿ���ɳ�ʼ��
	 * @return
	 * @throws Exception
	 */
	public long updateSysVoucher()throws Exception{
		
		long lRnt = -1 ;

		String sql = " update sys_enumerate  e set e.propvalue = 0 where e.function = ? " ;
		
		try {

			initDAO();
			prepareStatement(sql);
			
			transPS.setString(1, Constant.SystemConstantTable.VOUCHERINIT);

			lRnt = this.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("��ʼ��ʧ��!");
			
		}finally{
			this.finalizeDAO();
		}
		
		return lRnt;
	}
	
	
	/**
	 * У���˻��Ƿ��Ѿ������ص�����
	 * 
	 * @param accountid
	 * @param officeid
	 * @param currencyid
	 * @return
	 * @throws Exception
	 */
	public boolean isExistVoucherAccount(long accountid,long officeid,long currencyid) throws Exception
	{
		boolean BRnt = false ;

		long num = -1 ;
		String sql = " select count(id) num from print_vouchertype pv where pv.accountid=? and pv.officeid=? and pv.currencyid=? and pv.statusid > 0 " ;
		
		try {

			initDAO();
			prepareStatement(sql);
			
			transPS.setLong(1, accountid);
			transPS.setLong(2, officeid);
			transPS.setLong(3, currencyid);
			
			executeQuery();
			
			while(transRS.next()){
				num = transRS.getLong("num");
				if(num>0){
					BRnt =  true ;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("��ѯ�˻��Ƿ������ص�����ʧ��!");
			
		}finally{

			finalizeDAO();
		}
		
		return BRnt;
	}
	
	
	/**
	 * @see ��ѯ�ص����������е���ͨ�˻�(���˱�������������˻���
	 * @param officeid
	 * @param currencyid
	 * @return
	 * @throws Exception
	 */
	public Collection getAllVoucherAccount(long id,long officeid,long currencyid)throws Exception{
		
		Collection c = null;
		
		Vector vector = new Vector();
		VoucherTypeInfo info = null;
		
		String sql  = " select pv.* , ac.saccountno,ac.naccounttypeid,ac.sname from print_VoucherType pv , sett_account ac " ;
               sql += " where pv.accountid = ac.id and pv.id <> ? and  pv.vouchertypeid = 1  and officeid = ? and currencyid = ?  and pv.statusid > 0 order by pv.accountid  " ; 
			
		try {

			initDAO();
			prepareStatement(sql);
			
			transPS.setLong(1, id);
			transPS.setLong(2, officeid);
			transPS.setLong(3, currencyid);
			
			executeQuery();
			
			while(transRS.next()){
				
				info = new VoucherTypeInfo();
				info.setAccountid(transRS.getLong("accountid"));
				info.setAccountno(transRS.getString("saccountno"));
				info.setAccountName(transRS.getString("sname"));
				
				vector.add(info);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IException("��ȡ������˻���ѯʧ��!");
			
		}finally{
			
			finalizeDAO();
		}
		
		return vector.size()>0?vector:null ;
		
	}
	
	
	
	/**
	 * @see �����˻�ID�޸� �˻����Դӹ�����˻��� ��ͨ�˻�����
	 * @param subids
	 * @throws Exception
	 */
	public void updateSubAccountToNormalAccount(String subids)throws Exception{
		
		if(subids!=null && !subids.equals(""))
		{
			String sql = " update print_VoucherType pv set pv.vouchertypeid = 1 where pv.accountid in ("+subids+") and pv.statusid > 0  " ;
			
			try{
				
				this.initDAO();
				
				this.prepareStatement(sql);
				
				this.executeUpdate();
				
			}catch(Exception e){
				
				e.printStackTrace();
				throw new Exception("�޸Ĺ�����˻����Ե���ͨ�˻�����ʧ�ܣ�");
				
			}finally{
				
				this.finalizeDAO();
				
			}
		}
		
	}
	
}
