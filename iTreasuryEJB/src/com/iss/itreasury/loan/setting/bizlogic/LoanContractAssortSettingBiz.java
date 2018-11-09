/* Generated by Together */

package com.iss.itreasury.loan.setting.bizlogic;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.iss.itreasury.command.Command;
import com.iss.itreasury.loan.setting.dao.LoanContractAssortSettingDAO;
import com.iss.itreasury.loan.setting.dataentity.LoanContractAssortSettingInfo;
import com.iss.itreasury.loan.util.LOANConstant;
import com.iss.itreasury.util.Database;

public class LoanContractAssortSettingBiz extends Command {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ����һ���ֶ�����������Ϣ��������ǰ�����ͬfieldID �ļ�¼�Ƿ��Ѿ����ڣ�������ڲ���������
	 * 
	 * @throws Exception
	 */
	public long save(Collection c) throws Exception {
		Connection con = null;
		LoanContractAssortSettingDAO dao = null;
		LoanContractAssortSettingInfo info = null;

		try {
			con = Database.getConnection();
			long size = c.size();
			if (c != null && size > 0) {
				dao = new LoanContractAssortSettingDAO(con);
				dao.setUseMaxID();
				for (int i = 0; i < size; i++) {
					ArrayList l = (ArrayList) c;
					info = (LoanContractAssortSettingInfo) l.get(i);
					if (info.getId() <= 0) {
						dao.add(info);
					} else {
						dao.update(info);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			clearConn(con);
		}
		return 1;
	}

	/**
	 * �޸��Զ����ֶ�����������Ϣ��fieldID�������޸�
	 * 
	 * @throws Exception
	 */
	//    
	// public long update(String[] FieldID,String[] name,Timestamp
	// InputDate,long InputUserID,long StatusID) throws Exception
	// {
	// Connection con = null;
	// LoanContractAssortSettingDAO LoanContractAssortSettingDAO = null;
	// LoanContractAssortSettingInfo CFInfo = new
	// LoanContractAssortSettingInfo();
	// long lReturn = -1;
	//    	
	// try
	// {
	// con = Database.getConnection();
	// LoanContractAssortSettingDAO = new LoanContractAssortSettingDAO(con);
	//    		
	// CFInfo.setInputDate(InputDate);
	// CFInfo.setInputUserID(InputUserID);
	// CFInfo.setStatusID(StatusID);
	//    		
	// for (int i = 0;name != null && i<name.length;i++)
	// {
	//    			
	// CFInfo.setName(name[i]);
	// CFInfo.setFieldID(Long.parseLong(FieldID[i]));
	// if(LoanContractAssortSettingDAO.checkByFieldInfo(CFInfo)>0)
	// {
	// lReturn = -2;//������ͬ���Ƶ�FieldID�����ܱ���
	// }
	// else
	// {
	// lReturn = LoanContractAssortSettingDAO.update(CFInfo);
	// }
	// }
	// }
	// catch(Exception e)
	// {
	// if(con != null)
	// {
	// con.close();
	// con = null;
	// }
	// }
	// finally
	// {
	// try
	// {
	// if(con != null)
	// {
	// con.close();
	// con = null;
	// }
	// }
	// catch(Exception ex)
	// {
	// throw new Exception(ex.getMessage());
	// }
	// }
	// return (lReturn);
	// }
	/**
	 * ɾ����¼
	 */
	public void delete(long id) {
	}

	/**
	 * �Զ����ֶ��������ÿ����Ƿ��Ѿ�����fieldID��ͬ�ļ�¼
	 */
	public int exists(Vector vResult) {
		
		return 1;
	}


	/**
	 * ����������ѯ�������ͨ��ITreasuryDAO����ط���ִ�в�ѯ
	 * 
	 * @throws Exception
	 */
	public Vector findByCondition(long officeID, long currencyID)
			throws Exception {
		{
			Connection con = null;
			LoanContractAssortSettingDAO loanContractAssortSettingdao = null;
			Vector v = new Vector();
			try {
				// con = Database.get_jdbc_connection();
				con = Database.getConnection();
				loanContractAssortSettingdao = new LoanContractAssortSettingDAO(
						con);

				// ��ѯ����������Ϣ
				v = loanContractAssortSettingdao.findAttributeInfo(
						officeID, currencyID);

				con.close();
				con = null;
			} catch (Exception e) {
				if (con != null) {
					con.close();
					con = null;
				}
			} finally {
				try {
					if (con != null) {
						con.close();
						con = null;
					}
				} catch (Exception ex) {
					throw new Exception(ex.getMessage());
				}
			}
			return (v);
		}
	}

	public String getFieldname(int FieldID) throws Exception {
		String str = "";
		Connection con = null;
		try {
			con = Database.getConnection();
			str = new LoanContractAssortSettingDAO(con).getFieldname(FieldID);
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			clearConn(con);
		}
	}

	public String getFieldname(long FieldID, long officeID, long currencyID)
			throws Exception {
		String str = "";
		Connection con = null;
		try {
			con = Database.getConnection();
			str = new LoanContractAssortSettingDAO(con).getFieldname(FieldID,
					officeID, currencyID);
			return str;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			clearConn(con);
		}
	}

	/**
	 * �жϺ�ͬ����������Դ<p>
	 * <li>���û���δ�޸Ĺ������ͬ���࣬��ϵͳ��LOANConstant��������ȡ����ͬ����Ĭ�ϵġ����������ࡱ��������ҵ����1����������ҵ����2�������Ƿ�����ʻ���ܡ��ĸ�����
	 * <li>���û��Ѿ����й������ͬ�����޸ģ���ϵͳ�����ݿ���в����Ӧ���ĸ�����<p>
	 * <br>ϵͳ������������һ�ַ�ʽ�в�ѯ���������������һ��Vector��
	 * <br>�÷������Ա������ͬ����ѡ�������б��������ͬ�����޸ġ������ͬ�ύҳ�������ͬ����ҳʹ��
	 * 
	 * @param FieldID
	 * @param officeID
	 * @param currencyID
	 * @return
	 * @throws Exception
	 */
	public Vector getContractVector(long officeID,
			long currencyID) throws Exception {
		Connection con = null;
		LoanContractAssortSettingDAO loanContractAssortSettingdao = new LoanContractAssortSettingDAO();
		LoanContractAssortSettingInfo loanContractAssortSettingInfo = null;
		Vector v = new Vector();
		try {
			// con = Database.get_jdbc_connection();
			con = Database.getConnection();
			loanContractAssortSettingdao = new LoanContractAssortSettingDAO(con);

			// ��ѯ����������Ϣ
			v = loanContractAssortSettingdao.findAttributeInfo(
					officeID, currencyID);

			if (v.size() == 0) {
				long[] lAllTransTypeCode = LOANConstant.AssortSetType
						.getAllCode();
				for (int i = 0; i < lAllTransTypeCode.length; i++) {
					String loanTypeName = LOANConstant.AssortSetType
							.getName(lAllTransTypeCode[i]);
					long loanTypeFieldID = lAllTransTypeCode[i];
					System.out.println("-----" + loanTypeFieldID + "-----");
					System.out.println(loanTypeName);
					loanContractAssortSettingInfo = new LoanContractAssortSettingInfo();
					loanContractAssortSettingInfo.setFieldID(loanTypeFieldID);
					loanContractAssortSettingInfo.setName(loanTypeName);

					v.add(loanContractAssortSettingInfo);
				}
			}

			con.close();
			con = null;
		} catch (Exception e) {
			if (con != null) {
				con.close();
				con = null;
			}
		}
		return v;
	}
}