package com.iss.itreasury.ebank.magnifier.bizlogic;

import java.util.ArrayList;
import java.util.Map;
import com.iss.itreasury.ebank.magnifier.dataentity.PagerDepictBaseInfo;
import com.iss.itreasury.ebank.magnifier.dataentity.PagerInfo;
import com.iss.itreasury.ebank.util.PagerTypeConstant;


public class Suggest {
	public PagerInfo doQuery(Map map) throws Exception{
		PagerInfo pagerInfo = new PagerInfo();

		String colmodel = (String) map.get("colModel");
		String colmodeltype = (String) map.get("colModelType");
		String colmodellink = (String) map.get("colModelLink");
		String elementNames = (String) map.get("elementNames");
		String elements = (String) map.get("elements");
		String elementstype = (String) map.get("elementsType");
		String form = (String) map.get("form");
		String name = (String) map.get("name");
		String sql = (String) map.get("sql");
		String query = (String) map.get("query");
		String qtype = (String) map.get("qtype");
		String nextfocus = (String) map.get("nextFocus");
		String callback = (String) map.get("callback");

		//拼装SQL语句
		if ((query != null && !query.equals(""))
				&& (qtype != null && !qtype.equals(""))) {
			sql = "select * from (" + sql + ") where 1=1";
			sql += " and " + qtype + " like '%" + query + "%'";
		}

		// 置入SQL语句
		pagerInfo.setSqlString(sql);

		// 生成页面脚本代码
		String[] elementNamesList = elementNames.split(",");
		StringBuffer elBuffer = new StringBuffer();
		elBuffer.append("[");
		for (int i = 0; i < elementNamesList.length; i++) {
			elBuffer.append("{name:'" + elementNamesList[i] + "',value:'"+PagerTypeConstant.LOGOTYPE+"'},");
		}
		elBuffer = new StringBuffer(elBuffer.toString().substring(0,
				elBuffer.toString().length() - 1)
				+ "]");

		// 窗口类型数组
		String[] strColmodeltype = colmodeltype.split(",");
		long[] lColmodeltype = new long[strColmodeltype.length];
		for (int j = 0; j < strColmodeltype.length; j++) {
			lColmodeltype[j] = Long.parseLong(strColmodeltype[j]);
		}


		// 生成页面配置信息
		ArrayList depictList = new ArrayList();
		PagerDepictBaseInfo baseInfo = null;

		String[] colmodels = colmodel.split(",");
		for (int i = 0; i < colmodels.length; i++) {
			if ((colmodellink == null || colmodellink.equals("") || colmodellink
					.equalsIgnoreCase("null"))
					|| (colmodellink != null && !colmodellink.equals("") && colmodellink
							.indexOf(colmodels[i]) >= 0)) {// 带链接的字段
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName(colmodels[i]);
				baseInfo.setDisplayType(lColmodeltype[i]);
				baseInfo.setExtension(true);
				baseInfo.setExtensionName(elements.concat(",").concat(colmodels[i]).split(","));

				// 类型数组
				String[] strElementstypes = elementstype.concat(",").concat(new Long(lColmodeltype[i]).toString()).split(",");
				long[] lElementstypes = new long[strElementstypes.length];
				for (int j = 0; j < strElementstypes.length; j++) {
					lElementstypes[j] = Long.parseLong(strElementstypes[j]);
				}
				baseInfo.setExtensionType(lElementstypes);
				
				baseInfo.setExtensionString("<a href=\"javascript:"
						+ "$.suggest.fillForm({" + "form:'" + form + "',name:'"
						+ name + "',el:" + elBuffer.toString()
						+ ",nextFocus : '" + nextfocus + "',callback:'"
						+ callback + "'})\">"+PagerTypeConstant.LOGOTYPE+"</a>");
				depictList.add(baseInfo);
			} else {
				baseInfo = new PagerDepictBaseInfo();
				baseInfo.setDisplayName(colmodels[i]);
				baseInfo.setDisplayType(lColmodeltype[i]);
				depictList.add(baseInfo);
			}
		}

		// 置入配置信息
		pagerInfo.setDepictList(depictList);
		return pagerInfo;
	}
}
