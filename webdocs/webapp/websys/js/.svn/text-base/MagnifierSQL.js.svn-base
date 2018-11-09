
/**
 * 新增用户放大镜
 * nOfficeID 办事处ID
 * sClientNo 客户编号
 */
function getAddClientSQL(nOfficeID,sClientNo)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
	sql += " and (a.ISINSTITUTIONALCLIENT <=0 or a.ISINSTITUTIONALCLIENT is null )";
	sql += " and a.id not in ";
	sql += " (select distinct o.clientid ";
	sql += " from ob_admin_of_user o ";
	sql += " where o.isbelongtoclient = 2) ";
	if(nOfficeID>-1)
	{
		sql += " and a.nofficeid = "+nOfficeID;
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%25" + sClientNo + "%25' or a.sName like '%25" + sClientNo + "%25')";
	}	
	sql += " order by a.sCode";
	return sql;
}

function getAllClientSQL(nOfficeID,sClientNo)
{
	var sql = "select distinct  a.sCode as ClientNo, a.ID as ClientID,a.sname as ClientName,b.ID as OfficeID, b.sCode as OfficeNo, b.sName as OfficeName,1 as FromClient, a.nlevelcode as levelcode";
	sql += " from client a, office b ";
	sql += " where a.nofficeid = b.id and a.nStatusID > 0";
	sql += " and (a.ISINSTITUTIONALCLIENT <=0 or a.ISINSTITUTIONALCLIENT is null )";
	if(nOfficeID>-1)
	{
		sql += " and a.nofficeid = "+nOfficeID;
	}
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (a.sCode like '%25" + sClientNo + "%25' or a.sName like '%25" + sClientNo + "%25')";
	}	
	sql += " order by a.sCode";
	return sql;
}

function getCheckedClientSQL(nOfficeID,sClientNo)
{
	var sql =" select distinct b.sname OfficeName, c.sname ClientName, c.scode ClientNo, c.id ClientID,c.nlevelcode levelcode,1 FromClient ";
	sql += " from ob_user a, office b, client c ";
	sql += " where b.id = c.nofficeid ";
	sql += " and c.id = a.NCLIENTID ";
	sql += " and a.nsaid = -1 ";
	sql += " and a.NSTATUS =1 ";
	if(nOfficeID>-1)
	{
		sql += " and b.id ="+nOfficeID;
	}
	sql += " and (c.ISINSTITUTIONALCLIENT <=0 or c.ISINSTITUTIONALCLIENT is null ) ";
	if (sClientNo != null && sClientNo != "") 
	{
		sql += " and (c.sCode like '%25" + sClientNo + "%25' or c.sName like '%25" + sClientNo + "%25')";
	}
	sql += " order by c.sCode ";
	return sql;

}


