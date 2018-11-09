package com.iss.itreasury.ebank.fundplan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAO;
import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.ebank.fundplan.dataentity.SubCapitalPlanInfo;
import com.iss.itreasury.ebank.fundplan.model.FundPlanInfo;
import com.iss.itreasury.ebank.util.OBConstant;
import com.iss.itreasury.util.Database;

public class SubCapitalPlanDao extends ITreasuryDAO {
	public SubCapitalPlanDao()
	{
		super("ob_subcapitalplan");
		this.setUseMaxID();		
	}
	public SubCapitalPlanDao(Connection conn)
	{
		super("ob_subcapitalplan", conn);
		this.setUseMaxID();
	}
	
	//private PreparedStatement ps = null;
	private ResultSet rs = null;
	//Connection conn = null;
		
	/**
	 * added by ylguo
	 * 实质上这个方法和基类的update方法类似，添加了一个SQL条件
	 * @param ITreasuryBaseDataEntity dataEntity
	 *        long capitalplanId
	 * @throws ITreasuryDAOException
	 */
//	private void updateForSubCapital(ITreasuryBaseDataEntity dataEntity,long capitalplanId)	throws ITreasuryDAOException {
//		try {
//			initDAO();
//
//			StringBuffer buffer = new StringBuffer();
//			buffer.append("UPDATE " + strTableName + " SET \n");
//
//			String[] buffers = getAllFieldNameBuffer(dataEntity,DAO_OPERATION_UPDATE);
//			buffer.append(buffers[0]);
//			buffer.append(" WHERE ID = " + dataEntity.getId());
//			buffer.append(" and capitalplanId = " + capitalplanId);
//
//			String strSQL = buffer.toString();
//			log.debug(strSQL);
//			prepareStatement(strSQL);
//			setPrepareStatementByDataEntity(dataEntity, DAO_OPERATION_UPDATE,
//			buffers[0].toString());
//
//			executeUpdate();
//		} catch (ITreasuryDAOException ide) {
//	throw ide;
//		} finally {
//			finalizeDAO();
//		}
//
//}
	
	/**
	 * added by ylguo
	 * 更新ob_subcapital表,一次更新21条记录，这21条记录的CapitalPlanID字段必须相同
	 * @param SubCapitalPlanInfo info
	 * @return
	 * @throws Exception
	 */
	public long updateSubCapital(SubCapitalPlanInfo info)throws Exception{
		
		
		long retuenId = info.getId();
		try {
			if(info.getId() > 0)
			{
				initDAO();
				this.update(info);
			}
			
		} 
		catch (Exception e) {
			throw new ITreasuryDAOException("数据更新表OB_SUBCAPITALPLAN失败!",e);
		}
		finally
		{
			this.finalizeDAO();
		}
		
		return retuenId;
	}
	

	/**
	 * @param capitalplanId  资金计划主表ID
	 * @throws ITreasuryDAOException
	 */
	public void delSubCapitalplan(long capitalplanId) throws ITreasuryDAOException {
		try{
			StringBuffer sbSQL = new StringBuffer();
			sbSQL.append("update " + strTableName + " set statusid=0 ");
			sbSQL.append("where capitalplanId=" + capitalplanId);
			
			System.out.println(sbSQL);
			initDAO();
			prepareStatement(sbSQL.toString());
			executeUpdate();
			
		}catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(), e);
		}finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo
	 * 复核：将子表的同一个资金计划的相应的21条记录的状态置为2
	 * @param long capitalplanId
	 * @throws ITreasuryDAOException
	 */
	public void checkSubCapitalplan(long capitalplanId) throws ITreasuryDAOException {
		StringBuffer sql = new StringBuffer();
		sql.append("update ob_subcapitalplan os  set os.statusid=2 where os.capitalplanId =");
		sql.append(capitalplanId);
		try{
			initDAO();
			prepareStatement(sql.toString());
			executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("复核资金计划时，修改OB_SUBCAPITALPLAN中主表ID为"+capitalplanId+"状态失败!",e);
		}
		finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo
	 * 取消复核：将子表的同一个资金计划的相应的21条记录的状态置为1
	 * @param long capitalplanId
	 * @throws ITreasuryDAOException
	 */
	public void discheckSubCapitalplan(long capitalplanId) throws ITreasuryDAOException {
		StringBuffer sql = new StringBuffer();
		sql.append("update ob_subcapitalplan os  set os.statusid=1 where os.capitalplanId =");
		sql.append(capitalplanId);
		try{
			initDAO();
			prepareStatement(sql.toString());
			executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("取消复核资金计划时，修改OB_SUBCAPITALPLAN中主表ID为"+capitalplanId+"状态失败!",e);
		}
		finally{
			finalizeDAO();
		}
	}
	
	/**
	 * added by ylguo 按照开始时间来关联主表，对资金计划子表数据按周进行汇总
	 * 这个方法供FundPlanCreateWidgetBiz类调用
	 * @param String startdate
	 * @return List 封装了FundPlanInfo实例,只有其部分属性
	 * @throws SQLException 
	 */
	public List findByStartdate(String startdate)throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select  \n");
		sql.append("        ocf.id ID,\n"+
				   "		ocf.name name,\n"+
				   "		ocf.parentid parentid,\n"+
				   "		ocf.layerid layerid,\n"+
				   "		ocf.code code,\n"+
				   "		ocf.MODELID MODELID,\n"+
				   "		ocf.OFFICEID OFFICEID,\n"+
				   "		ocf.CURRENCYID CURRENCYID,\n"+
				   "		ocf.expression expression,\n");
		sql.append("        TOTAL, \n");
		sql.append("        MONDAYCAPITAL, \n");
		sql.append("        TUESDAYCAPITAL, \n");
		sql.append("        WEDNESDAYCAPITAL, \n");
		sql.append("        THURSDAYCAPITAL, \n");
		sql.append("        FRIDAYCAPITAL, \n");
		sql.append("        NEXTWEEKCAPITAL \n");
		sql.append("from ob_capitalplanconfig config, \n");
		sql.append("     (select  subplan.capitalplanconfigid configid, \n");
		sql.append("              sum(subplan.total) TOTAL, \n");
		sql.append("              sum(subplan.mondaycapital) MONDAYCAPITAL, \n");
		sql.append("              sum(subplan.tuesdaycapital) TUESDAYCAPITAL, \n");
		sql.append("              sum(subplan.wednesdaycapital) WEDNESDAYCAPITAL, \n");
		sql.append("              sum(subplan.thursdaycapital) THURSDAYCAPITAL, \n");
		sql.append("              sum(subplan.fridaycapital) FRIDAYCAPITAL, \n");
		sql.append("              sum(subplan.nextweekcapital) NEXTWEEKCAPITAL \n");
		sql.append("      from   ob_subcapitalplan subplan \n");
		sql.append("      where  subplan.capitalplanid in ( \n");
		sql.append("                                       select oc.id from ob_capitalplan oc \n");
		sql.append("                                       where oc.startdate = to_date('"+startdate+"','yyyy-mm-dd') \n");
		sql.append("                                       and oc.statusid =20 \n");
		sql.append("                                       ) \n");
		sql.append("      group by subplan.capitalplanconfigid \n");
		sql.append("      ) subplansum, \n");
		sql.append("        ob_capitalplanconfig ocf \n");
		sql.append("where config.id=subplansum.configid(+) \n");
		sql.append("and ocf.id = subplansum.configid \n");
		sql.append("order by id \n");
		try{
			//Connection conn = Database.getConnection();
			//ps = conn.prepareStatement(sql.toString());
			//rs = ps.executeQuery();
			initDAO();
			prepareStatement(sql.toString());
			rs = executeQuery();
			List list = new ArrayList();
			while(rs.next()){
				FundPlanInfo info = new FundPlanInfo();
				info.setConfigid(rs.getLong("ID"));								//配置编号
				info.setName(rs.getString("name"));								//配置名称
				info.setParentid(rs.getLong("parentid"));						//父ID
				info.setLayerid(rs.getLong("layerid"));							//配置层次
				info.setCode(rs.getString("code"));								//配置编码
				info.setOfficeId(rs.getLong("OFFICEID"));
				info.setModelId(rs.getLong("MODELID"));
				info.setCurrencyId(rs.getLong("CURRENCYID"));
				info.setExpression("");					                        //公式
				info.setPlanitemid(-1);					                        //资金计划子表ID
				info.setCapitalplanconfigid(-1);	                            //资金计划配置项ID
				info.setTotal(rs.getDouble("TOTAL"));                           //合计
				info.setMondaycapital(rs.getDouble("MONDAYCAPITAL"));           //星期一
				info.setTuesdaycapital(rs.getDouble("TUESDAYCAPITAL"));         //星期二
				info.setWednesdaycapital(rs.getDouble("WEDNESDAYCAPITAL"));     //星期三
				info.setThursdaycapital(rs.getDouble("THURSDAYCAPITAL"));       //星期四
				info.setFridaycapital(rs.getDouble("FRIDAYCAPITAL"));           //星期五 
				info.setNextweekcapital(rs.getDouble("NEXTWEEKCAPITAL"));       //下一周
                info.setRemark(null);                                              //备注
				list.add(info);
			}
			//ps.close();
			//rs.close();
			//conn.close();
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("资金计划汇总详情单查询失败!",e);
		}finally{
			rs.close();
			finalizeDAO();
			
		}
	}
	

	public void auditingAllSubCapitalplans(String strCpID[],String strAction) throws ITreasuryDAOException {
		StringBuffer sql = new StringBuffer();
		String temp = "";
		if(strAction.equals("toCommit")|| strAction.equals("toAuditing"))
		{
			sql.append("update ob_subcapitalplan os  set os.statusid="+OBConstant.SettInstrStatus.APPROVALED+" where os.capitalplanId in ");
		}
		else if(strAction.equals("toRefuse") || strAction.equals("toDisAuditing"))
		{
			sql.append("update ob_subcapitalplan os  set os.statusid="+OBConstant.SettInstrStatus.SAVE+" where os.capitalplanId in ");
		}
		sql.append(" ( ");
		for(int i =0;i<strCpID.length;i++)
		{
			temp+=strCpID[i]+",";
		}
		sql.append(temp.substring(0, temp.length()-1));
		sql.append(" ) ");
		try{
			prepareStatement(sql.toString());
			executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("批量审批资金计划时，修改OB_SUBCAPITALPLAN中主表ID为状态失败!",e);
		}
	}
	
	/**
	 * 查询是否存在状态已经被修改得数据
	 * @param strCpID
	 * @param strAction
	 * @param auditingUserid
	 * @return
	 * @throws ITreasuryDAOException
	 */
	public boolean checkAuditingAllSubCapitalplansStatus(String strCpID[],String strAction) throws ITreasuryDAOException{
		boolean istrue = false;
		StringBuffer sql = new StringBuffer();
		String temp = "";
		
		sql.append("select oc.statusid from  ob_subcapitalplan oc  where ");
		sql.append("    oc.CAPITALPLANID in");
		sql.append(" ( ");
		for(int i =0;i<strCpID.length;i++)
		{
			temp+=strCpID[i]+",";
		}
		sql.append(temp.substring(0, temp.length()-1));
		sql.append(" ) ");
		
		try{
			prepareStatement(sql.toString());
			transRS = executeQuery();
			while(transRS.next())
			{
				if(strAction.equals("toCommit") || strAction.equals("toRefuse") || strAction.equals("toAuditing"))
				{
					if(transRS.getLong("statusid")!=OBConstant.SettInstrStatus.CHECK)
					{
						istrue = true;
					}
				}
				//取消审批
				else if( strAction.equals("toDisAuditing"))
				{
					if(transRS.getLong("statusid")!=OBConstant.SettInstrStatus.APPROVALED)
					{
						istrue = true;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new ITreasuryDAOException("资金计划：，查询OB_CAPITALPLAN数据错误!",e);
		}
		return istrue;
	}
	
	
}