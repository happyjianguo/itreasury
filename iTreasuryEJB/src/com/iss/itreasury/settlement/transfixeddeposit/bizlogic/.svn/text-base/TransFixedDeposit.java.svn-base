/*
 * Created on 2003-8-7
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.iss.itreasury.settlement.transfixeddeposit.bizlogic;
import javax.ejb.SessionBean;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.*;

import com.iss.itreasury.settlement.transcurrentdeposit.dataentity.TransCurrentDepositAssembler;
import com.iss.itreasury.settlement.transfixeddeposit.dataentity.*;
import com.iss.itreasury.util.IRollbackException;
/**
 * @author yiwang
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface TransFixedDeposit extends javax.ejb.EJBObject
{
	//开立交易的保存
	public long openSave(TransFixedOpenInfo info) throws IRollbackException,RemoteException;
	//开立交易的暂存
	public long openTempSave(TransFixedOpenInfo info) throws IRollbackException,RemoteException;
	//开立交易的匹配
	public Collection openMatch(TransFixedOpenInfo info) throws IRollbackException,RemoteException;
	//开立交易的链接查找
	public Collection openFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException;
	//added by qhzhou 2007.6.26
	//开立交易的链接查找,过滤由于定期支取生成的开立存单
	public Collection openFindByStatus(QueryByStatusConditionInfo info,boolean isFilt) throws IRollbackException,RemoteException;
	//开立交易的根据ID查找
	public TransFixedOpenInfo openFindByID(long lID) throws IRollbackException,RemoteException;
	//开立交易的根据交易号查找
	public TransFixedOpenInfo openFindByTransNo(String strTransNo) throws IRollbackException,RemoteException;
	//开立交易的复核
	public long openCheck(TransFixedOpenInfo info) throws IRollbackException,RemoteException;
	//开立交易的取消复核
	public long openCancelCheck(TransFixedOpenInfo info) throws IRollbackException,RemoteException;
	//开立交易的删除
	public long openDelete(TransFixedOpenInfo info) throws IRollbackException,RemoteException;
	//支取交易的保存
	public long drawSave(TransFixedDrawInfo info) throws IRollbackException,RemoteException;
	//支取交易的暂存
	public long drawTempSave(TransFixedDrawInfo info) throws IRollbackException,RemoteException;
	//支取交易的匹配
	public Collection drawMatch(TransFixedDrawInfo info) throws IRollbackException,RemoteException;
	//支取交易的链接查找
	public Collection drawFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException;
	//支取交易的根据交易号查找
	public TransFixedDrawInfo drawFindByTransNo(String strTransNo) throws IRollbackException,RemoteException;
	//支取交易的继续
	public TransFixedDrawInfo drawNext(TransFixedDrawInfo info) throws IRollbackException,RemoteException;
	//支取交易的根据ID查找
	public TransFixedDrawInfo drawFindByID(long lID) throws IRollbackException,RemoteException;
	//支取交易的复核
	public long drawCheck(TransFixedDrawInfo info) throws IRollbackException,RemoteException;
	//支取交易的取消复核
	public long drawCancelCheck(TransFixedDrawInfo info) throws IRollbackException,RemoteException;
	//支取交易的删除
	public long drawDelete(TransFixedDrawInfo info) throws IRollbackException,RemoteException;
	//续期转存交易的保存
	public long continueSave(TransFixedContinueInfo info) throws IRollbackException,RemoteException;
	//续期转存交易的暂存
	public long continueTempSave(TransFixedContinueInfo info) throws IRollbackException,RemoteException;
	//续期转存交易的匹配
	public Collection continueMatch(TransFixedContinueInfo info) throws IRollbackException,RemoteException;
	//续期转存交易的链接查找
	public Collection continueFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException;
	//续期转存交易的继续
	public TransFixedContinueInfo continueNext(TransFixedContinueInfo info) throws IRollbackException,RemoteException;
	//续期转存交易的根据ID查找
	public TransFixedContinueInfo continueFindByID(long lID) throws IRollbackException,RemoteException;
	//续期转存交易的根据交易号查找
	public TransFixedContinueInfo continueFindByTransNo(String strTransNo) throws IRollbackException,RemoteException;
	//续期转存交易的复核
	public long continueCheck(TransFixedContinueInfo info) throws IRollbackException,RemoteException;
	//续期转存交易的取消复核
	public long continueCancelCheck(TransFixedContinueInfo info) throws IRollbackException,RemoteException;
	//续期转存交易的删除
	public long continueDelete(TransFixedContinueInfo info) throws IRollbackException,RemoteException;	
	//根据存单编号查询定期（通知） 开立交易明细的方法：
	public TransFixedOpenInfo openFindByDepositNo(String DepositNo)
			throws IRollbackException, RemoteException;
	//根据存单编号查询定期（通知） 开立交易明细的方法：	 
	public TransFixedOpenInfo openFindByOldDepositNo(String oldDepositNo)
			throws IRollbackException, RemoteException; 
	//换开定期存单交易的保存
	public long changeSave(TransFixedChangeInfo info) throws IRollbackException,RemoteException;
	//换开定期存单交易的暂存
	public long changeTempSave(TransFixedChangeInfo info) throws IRollbackException,RemoteException;
	//换开定期存单交易的匹配
	public Collection changeMatch(TransFixedChangeInfo info) throws IRollbackException,RemoteException;
	//换开定期存单交易的链接查找
	public Collection changeFindByStatus(QueryByStatusConditionInfo info) throws IRollbackException,RemoteException;
	//换开定期存单交易的根据ID查找
	public TransFixedChangeInfo changeFindByID(long lID) throws IRollbackException,RemoteException;
	//换开定期存单交易的根据交易号查找
	public TransFixedChangeInfo changeFindByTransNo(String strTransNo) throws IRollbackException,RemoteException;
	//换开定期存单交易的复核
	public long changeCheck(TransFixedChangeInfo info) throws IRollbackException,RemoteException;
	//换开定期存单交易的取消复核
	public long changeCancelCheck(TransFixedChangeInfo info) throws IRollbackException,RemoteException;
	//换开定期存单交易的删除
	public long changeDelete(TransFixedChangeInfo info) throws IRollbackException,RemoteException;
	//审批方法（开立）/
	public long doApproval(TransFixedOpenInfo info)throws RemoteException, IRollbackException;
	//审批方法（支取）/
	public long doApproval(TransFixedDrawInfo info)throws RemoteException, IRollbackException;
	//	审批方法（转存）/
	public long doApproval(TransFixedContinueInfo info)throws RemoteException, IRollbackException;
	//取消审批方法（开立）/
	public long cancelApproval(TransFixedOpenInfo info)throws RemoteException, IRollbackException;
	//取消审批方法（支取）/
	public long cancelApproval(TransFixedDrawInfo info)throws RemoteException, IRollbackException;
	//取消审批方法（转存）/
	public long cancelApproval(TransFixedContinueInfo info)throws RemoteException, IRollbackException;
}
