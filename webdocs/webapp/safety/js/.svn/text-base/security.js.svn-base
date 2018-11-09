
function sign(format,form)
{
	var originalValue = format.formatData();
	security.filter({
		CertCN:document.getElementsByName("safety_signature_session_certificateInfo_commonName")[0].value,
		issuerDN:document.getElementsByName("safety_signature_session_certificateInfo_issuerDN")[0].value,
		CertSN:document.getElementsByName("safety_signature_session_certificateInfo_serialNumber")[0].value
	});
	var signatureValue = security.sign({
		originalData:originalValue
	});
	form.safety_signature_signatureValue.value = signatureValue;
	form.safety_signature_originalValue.value = originalValue;				
	
}