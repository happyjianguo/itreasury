package com.iss.itreasury.loan.assureloan.bizlogic;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeMap;
import com.iss.itreasury.loan.assureloan.dao.AssureQueryDao;
import com.iss.itreasury.loan.assureloan.dataentity.AssureQueryInfo;
import com.iss.itreasury.loan.assureloan.dataentity.AssureResultInfo;
import com.iss.itreasury.loan.assureloan.dataentity.AssureTempInfo;
import com.iss.itreasury.loan.assureloan.dataentity.AssureUsedInfo;
import com.iss.itreasury.loan.util.LOANConstant.BankCreditVariety;

public class AssureQueryBiz {
	
	
	
	public List getUsedAssrueInfo(AssureQueryInfo info)  {
		AssureQueryDao dao = new AssureQueryDao();
		TreeMap map  = new TreeMap();
		
		try {
			
			List lstAssure = dao.getAssrueInfo(info);
			List lstClient[] = dao.getClient(info.getStartClient(), info.getEndClient());

			if (lstAssure != null) {
				for (int i = 0; i < lstAssure.size(); i++) {
					AssureTempInfo assureInfo = (AssureTempInfo)lstAssure.get(i);
					List[] clients = filterClient(assureInfo.getCompanyCode(), lstClient[0], lstClient[1]);
					
					if(clients[0] != null || clients[0].size() > 0) {
						put(map, assureInfo, clients[0], clients[1]);
						List lstUsedAssure = dao.getUsedAssureInfo(assureInfo.getBankCreditId(), parseClient(clients[0]), assureInfo.getVariety());
						if (lstUsedAssure != null) {
							for (int j = 0; j < lstUsedAssure.size(); j++) {
								AssureUsedInfo usedInfo = (AssureUsedInfo)lstUsedAssure.get(j);
								put(map, assureInfo.getContractNo(), usedInfo);
							}
						}
					}
				}
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		
		return parse(map);
	}
	
	private List[] filterClient(String clients, List lstClientCode, List lstClientName) {
		ArrayList lstCode = new ArrayList();
		ArrayList lstName = new ArrayList();
		ArrayList[] rtnClient = new ArrayList[]{lstCode, lstName};
		StringTokenizer st = new StringTokenizer(clients,",");
		
		//循环取得每个成员单位代码
	      while (st.hasMoreTokens()) {
	        String clientCode = st.nextToken();
	        int i = lstClientCode.indexOf(clientCode);
	        if (i >= 0) {
	        	lstCode.add(clientCode);
	        	lstName.add(lstClientName.get(i));
	        }
	      }
	     
	      return rtnClient;  
	}
	
	private String parseClient(List list) {
		StringBuffer clients = new StringBuffer();
		
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				clients.append(list.get(i));
				clients.append(",");
			}
		}
		
		String temp = clients.toString();
		return temp.endsWith(",") ? temp.substring(0,temp.length()-1): temp;
	}
	
	private void put(TreeMap map, AssureTempInfo assureInfo, List clientCodes, List clientNames) {
		TreeMap tmClient = new TreeMap();
		map.put(assureInfo.getContractNo(), tmClient);
		
		for (int i = 0; i < clientCodes.size(); i++) {
			String code = (String)clientCodes.get(i);
			AssureResultInfo info = new AssureResultInfo();
			info.setContractNo(assureInfo.getContractNo());
			info.setAmount(assureInfo.getAmount());
			info.setClientCode(code);
			info.setClientName((String)clientNames.get(i));
			tmClient.put(code, info);
		}
	}
	
	private void put(TreeMap map, String contractNo,AssureUsedInfo usedInfo) {
		if (map.containsKey(contractNo)) {
			TreeMap subMap = (TreeMap)map.get(contractNo);
			
			if (subMap.containsKey(usedInfo.getClientCode())) {
				AssureResultInfo info = (AssureResultInfo)subMap.get(usedInfo.getClientCode());
				info.setClientName(usedInfo.getClientName());
				setAmount(info, usedInfo.getVariety(), usedInfo.getAmount());
			}
		}
	}
	
	public void setAmount(AssureResultInfo info, int variety, double amount) {
		switch (variety) {
			case (int)BankCreditVariety.SHORTTERMLOAN:
				info.setShortTermAmount(amount);
				break;
			case (int)BankCreditVariety.LONGTERMLOAN:
				info.setLongTermAmount(amount);
				break;
			case (int)BankCreditVariety.LETTEROFCREDIT:
				info.setLetterCreditAmount(amount);
				break;
			case (int)BankCreditVariety.LETTEROFIGUARANTEE:
				info.setLetterGuaranteeAmount(amount);
				break;
			case (int)BankCreditVariety.PROVEOFCREDIT:
				info.setCreditProveAmount(amount);
				break;
			case (int)BankCreditVariety.ACCEPTBILL:
				info.setAcceptBillAmount(amount);
				break;
			default:
				break;
		}
	}
	
	private List parse(TreeMap map) {
		ArrayList lstResult = new ArrayList();
		java.util.Iterator iter = map.keySet().iterator();
		
		while (iter.hasNext()) {
			TreeMap temp = (TreeMap)map.get(iter.next());			
			java.util.Iterator iter1 = temp.keySet().iterator();
			
			while (iter1.hasNext()) {
				lstResult.add(temp.get(iter1.next()));
			}
		}
		


		return lstResult;
	}
	
	
}
