/*
 * Created on 2004-07-28
 *
 * To change the template for this generated type comment go to 
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.iss.itreasury.treasuryplan.mywork.bizlogic;

import java.sql.SQLException;
import java.util.Collection;

import com.iss.itreasury.util.Log4j;
import com.iss.itreasury.util.Constant;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.treasuryplan.mywork.dao.MyWorkDAO;
import com.iss.itreasury.treasuryplan.mywork.dataentity.MyWorkInfo;

/**
 * Title:        		iTreasury
 * Description:         
 * Copyright:           Copyright (c) 2004
 * Company:             iSoftStone
 * @author              hewen hu
 * @version
 *  Date of Creation    2004-07-28
 */
public class MyWorkOperation {
	protected Log4j log = new Log4j(Constant.ModuleType.PLAN, this);
	protected MyWorkDAO dao = null;

	/**
	 * ���캯��
	 * @param  nothing
	 * @return nothing
	 * @exception nothing
	 */
	public MyWorkOperation() {
		dao = new MyWorkDAO();
	}

	/**
	 * ����ϵͳ����
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException
	 */
	public long add(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.add(info);
	}

	/**
	 * �޸�ϵͳ����
	 * @param  MyWorkInfo info
	 * @return void
	 * @exception throws SQLException
	 */
	public void update(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		dao.update(info);
	}

	/**
	 * ����ϵͳ����
	 * @param  MyWorkInfo info
	 * @return void
	 * @exception throws SQLException
	 */
	public long save(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		dao.setUseMaxID();
		return dao.add(info);
	}

	/**
	 * ɾ������ϵͳ����
	 * @param  MyWorkInfo info
	 * @return void
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public void delete(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		info.setStatusID(0);
		dao.update(info);
	}

	/**
	 * ������������
	 * @param  MyWorkInfo info
	 * @return Collection
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public Collection findByCondition(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.findByCondition(info);
	}

	/**
	 * ͨ��״̬ȡ���ʽ�ƻ�����(�����)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getNoCheckCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getNoCheckCountByStatusID(info);
	}

	/**
	 * ͨ��״̬ȡ���ʽ�ƻ�����(׫д)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getSaveCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getSaveCountByStatusID(info);
	}

	/**
	 * ͨ��״̬ȡ���ʽ�ƻ�����(���ύ)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getSubmitCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getSubmitCountByStatusID(info);
	}

	/**
	 * ͨ��״̬ȡ���ʽ�ƻ�����(�����)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getCheckCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getCheckCountByStatusID(info);
	}

	/**
	 * ͨ��״̬ȡ���ʽ�ƻ�����(�Ѿܾ�)
	 * @param  MyWorkInfo info
	 * @return long
	 * @exception throws SQLException, ITreasuryDAOException
	 */
	public long getRefuseCountByStatusID(MyWorkInfo info) throws SQLException, ITreasuryDAOException {
		return dao.getRefuseCountByStatusID(info);
	}
}
