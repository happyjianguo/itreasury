function showDetail(CWBID, CWBNo)
{
    window.open('/operation/export/cwb.do?operate=showDetail&CWBEntity.ID=' + CWBID + '&CWBEntity.CWBNo=' + CWBNo,'zx','width=740,height=400,scrollbars=yes,left=1px');
}

/*author:feiluo
*2005.7.30
*以下执行扣关的放大镜功能
*所对应的jsp为：icomanualclose.jsp
*/
function showMagnifierIco(goodsNo)
{					
    window.open('/operation/export/bwbinfo/magnifierAction.do?goods_Id='+goodsNo.value,'zx','width=452,height=450,scrollbars=no,left=1px');
}
