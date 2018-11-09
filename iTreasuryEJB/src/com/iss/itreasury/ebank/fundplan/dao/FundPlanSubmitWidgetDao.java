/**
 * jlzhang
 * Oct 14, 2008
 */
package com.iss.itreasury.ebank.fundplan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.iss.itreasury.dao.ITreasuryDAOException;
import com.iss.itreasury.dao.SettlementDAO;
import com.iss.itreasury.ebank.fundplan.model.FundPlanInfo;
import com.iss.itreasury.ebank.fundplan.model.FundPlanParamInfo;
import com.iss.itreasury.ebank.util.OBConstant.SettInstrStatus;

/**
 * @author xintan
 * 
 * 资金计划申报模板
 *
 */
public class FundPlanSubmitWidgetDao extends SettlementDAO {
		
	/*
	 * desc:在标签中构造表格时使用，根据级别码获取项目列表个数
	 * @param levelcode 一级项目的级别码
	 * @return 数量
	 * */
	public int getRowSpan(String levelcode, long modelId, long office, long currency) throws Exception{
		try {
			initDAO();			
			String strSQL ="SELECT count(config.id) count FROM OB_CAPITALPLANCONFIG config "
				+ "where config.code like '" + levelcode + "%' "
				+ "and currencyid=" + currency + " and officeid=" + office + " and statusid!=0 ";
			
			if(modelId>0)
			{
				strSQL += " and config.modelId=" + modelId;
			}
			
			prepareStatement(strSQL);
			ResultSet rs = executeQuery();
			if(rs.next())
			{
				return rs.getInt("count");
			}
			else return 0;
		} catch (Exception e) {
			throw new ITreasuryDAOException("数据操作失败!",e);
		}
		finally
		{
			finalizeDAO();
		}
	}
	

	/*
	 * desc:获取一级和二级项目列表
	 * */
	public List getCapitalPlanContentList(FundPlanParamInfo queryInfo) 
	throws Exception
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer strSQL = new StringBuffer();
		List list=new ArrayList();
		try {
			conn = this.getConnection();
			
			strSQL.append(" select * from ");
			strSQL.append(" (select config.id configid, config.name, config.parentid, config.layerid, config.code, config.expression "); 
			strSQL.append(" from ob_capitalplanconfig config where config.officeid = " + queryInfo.getOffice()
					+ " and config.currencyid = " + queryInfo.getCurrency() 
					+ " and config.modelid=" + queryInfo.getModelId() + " order by code ");
			strSQL.append(" ) templete, ");
			strSQL.append(" (select planitem.id planitemid, planitem.capitalplanconfigid, planitem.remark ");
			strSQL.append(" , planitem.total ");
			strSQL.append(" , planitem.mondaycapital ");
			strSQL.append(" , planitem.tuesdaycapital ");
			strSQL.append(" , planitem.wednesdaycapital ");
			strSQL.append(" , planitem.thursdaycapital ");
			strSQL.append(" , planitem.fridaycapital ");
			strSQL.append(" , planitem.nextweekcapital ");
			strSQL.append(" from ob_capitalplan plan, ob_subcapitalplan planitem ");
			strSQL.append(" where plan.id = planitem.capitalplanid and planitem.statusid!=" + SettInstrStatus.DELETE);
			if(queryInfo.getCapitalplanId() > 0)
			{
				strSQL.append(" and plan.id = "+ queryInfo.getCapitalplanId() +" ");
			}
			if(queryInfo.getClientId() > 0)
			{
				strSQL.append(" and plan.clientid = "+ queryInfo.getClientId() +" ");
			}
			if(queryInfo.getDateFrom() != null && queryInfo.getDateFrom().length() > 0)
			{
				strSQL.append(" and plan.startdate = to_date('"+ queryInfo.getDateFrom() +"', 'yyyy-mm-dd') ");
			}
			if(queryInfo.getDateTo() != null && queryInfo.getDateTo().length() > 0)
			{
				strSQL.append(" and plan.enddate = to_date('"+ queryInfo.getDateTo() +"', 'yyyy-mm-dd') ");
			}
			strSQL.append(" and plan.officeid = " + queryInfo.getOffice() 
					+ " and plan.currencyid = " + queryInfo.getCurrency() 
					+ " and plan.modelid= " + queryInfo.getModelId());
			strSQL.append(" ) capitalplanitem ");
			strSQL.append(" where templete.configid = capitalplanitem.capitalplanconfigid(+) ");
			strSQL.append(" order by code ");
			
			System.out.println(strSQL.toString());
			ps = conn.prepareStatement(strSQL.toString());
			rs = ps.executeQuery();
			
			while(rs.next()){
				FundPlanInfo info =new FundPlanInfo();
				info.setConfigid(rs.getLong("configid"));
				info.setName(rs.getString("name"));								//配置名称
				info.setParentid(rs.getLong("parentid"));						//父ID
				info.setLayerid(rs.getLong("layerid"));							//配置层次
				info.setCode(rs.getString("code"));								//配置编码
				info.setExpression(rs.getString("expression"));					//公式
				info.setPlanitemid(rs.getLong("planitemid"));					//资金计划子表ID
				info.setCapitalplanconfigid(rs.getLong("capitalplanconfigid"));	//资金计划配置项ID
				info.setTotal(rs.getDouble("total"));							//合计
				info.setMondaycapital(rs.getDouble("mondaycapital"));			//星期一
				info.setTuesdaycapital(rs.getDouble("tuesdaycapital"));			//星期二
				info.setWednesdaycapital(rs.getDouble("wednesdaycapital"));		//星期三
				info.setThursdaycapital(rs.getDouble("thursdaycapital"));		//星期四
				info.setFridaycapital(rs.getDouble("fridaycapital"));			//星期五
				info.setNextweekcapital(rs.getDouble("nextweekcapital"));		//下一周
				info.setRemark(rs.getString("remark"));							//备注
				
				list.add(info);
			}
		
		} catch (Exception e) {
			throw new ITreasuryDAOException(e.getMessage(),e);
		}
		finally
		{
			this.cleanup(rs);
			this.cleanup(ps);
			this.cleanup(conn);
		}
		
		return list;
	}	
}