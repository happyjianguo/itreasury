/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   Licence.java

package com.iss.system.security;

import com.iss.system.action.ActionException;
import com.iss.system.dataentity.LicenceBean;
import java.io.*;
import java.util.zip.ZipFile;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

// Referenced classes of package com.iss.system.security:
//            SinatureUtil

public final class Licence
{

    private Licence()
    {
    }

    public static final LicenceBean[] loadLicenceInfo(String strFileName)
        throws ActionException
    {
        FileInputStream fisLicence = null;
        LicenceBean licences[] = (LicenceBean[])null;
        try
        {
            fisLicence = new FileInputStream(strFileName);
            licences = loadLicenceInfo(((InputStream) (fisLicence)));
            fisLicence.close();
        }
        catch(Exception e)
        {
            throw new ActionException(ActionException.SYSTEM_MESSAGE, "error.com.iss.system.licence.notfound");
        }
        return licences;
    }

    public static final LicenceBean[] loadLicenceInfo(InputStream isLicence)
        throws ActionException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        LicenceBean arrLicences[] = (LicenceBean[])null;
        try
        {
            db = dbf.newDocumentBuilder();
            Document doc = null;
            doc = db.parse(isLicence);
            Element root = doc.getDocumentElement();
            Node node = root.getFirstChild();
            NodeList nodelist = root.getElementsByTagName("licence");
            arrLicences = new LicenceBean[nodelist.getLength()];
            Element eleLicence = null;
            LicenceBean licenceInfo = null;
            NodeList nodeCaption = null;
            Element eleCaption = null;
            Text textCaption = null;
            String strValue = "";
            for(int i = 0; i < nodelist.getLength(); i++)
            {
                eleLicence = (Element)nodelist.item(i);
                licenceInfo = new LicenceBean();
                nodeCaption = eleLicence.getElementsByTagName(TITLE_TAG);
                if(nodeCaption.getLength() == 1)
                {
                    eleCaption = (Element)nodeCaption.item(0);
                    textCaption = (Text)eleCaption.getFirstChild();
                    if(textCaption == null)
                        strValue = "";
                    else
                        strValue = textCaption.getNodeValue();
                    licenceInfo.setTitle(strValue);
                }
                nodeCaption = eleLicence.getElementsByTagName(PRODUCTNAME_TAG);
                if(nodeCaption.getLength() == 1)
                {
                    eleCaption = (Element)nodeCaption.item(0);
                    textCaption = (Text)eleCaption.getFirstChild();
                    if(textCaption == null)
                        strValue = "";
                    else
                        strValue = textCaption.getNodeValue();
                    licenceInfo.setProductName(strValue);
                }
                nodeCaption = eleLicence.getElementsByTagName(VERSION_VERSION);
                if(nodeCaption.getLength() == 1)
                {
                    eleCaption = (Element)nodeCaption.item(0);
                    textCaption = (Text)eleCaption.getFirstChild();
                    if(textCaption == null)
                        strValue = "";
                    else
                        strValue = textCaption.getNodeValue();
                    licenceInfo.setVersion(strValue);
                }
                nodeCaption = eleLicence.getElementsByTagName(PATCH_ERSION_NO_TAG);
                if(nodeCaption.getLength() == 1)
                {
                    eleCaption = (Element)nodeCaption.item(0);
                    textCaption = (Text)eleCaption.getFirstChild();
                    if(textCaption == null)
                        strValue = "";
                    else
                        strValue = textCaption.getNodeValue();
                    licenceInfo.setPatchVersionNo(strValue);
                }
                nodeCaption = eleLicence.getElementsByTagName(CUSTOMNAME_TAG);
                if(nodeCaption.getLength() == 1)
                {
                    eleCaption = (Element)nodeCaption.item(0);
                    textCaption = (Text)eleCaption.getFirstChild();
                    if(textCaption == null)
                        strValue = "";
                    else
                        strValue = textCaption.getNodeValue();
                    licenceInfo.setCustomName(strValue);
                }
                nodeCaption = eleLicence.getElementsByTagName(EFFECTIVEDATE_TAG);
                if(nodeCaption.getLength() == 1)
                {
                    eleCaption = (Element)nodeCaption.item(0);
                    textCaption = (Text)eleCaption.getFirstChild();
                    if(textCaption == null)
                        strValue = "";
                    else
                        strValue = textCaption.getNodeValue();
                    licenceInfo.setEffectiveDate(strValue);
                }
                nodeCaption = eleLicence.getElementsByTagName(EXPIREDATE_TAG);
                if(nodeCaption.getLength() == 1)
                {
                    eleCaption = (Element)nodeCaption.item(0);
                    textCaption = (Text)eleCaption.getFirstChild();
                    if(textCaption == null)
                        strValue = "";
                    else
                        strValue = textCaption.getNodeValue();
                    licenceInfo.setExpireDate(strValue);
                }
                arrLicences[i] = licenceInfo;
            }

        }
        catch(Exception e)
        {
            throw new ActionException(ActionException.SYSTEM_MESSAGE, "error.com.iss.system.licence.load.failed", e.getMessage());
        }
        return arrLicences;
    }

    public static final LicenceBean[] loadLicenceInfoFromZipFile(String strLicenceFileName, String strLicenceInfoEntyName)
        throws ActionException
    {
        BufferedInputStream bisLicenceInfo = null;
        LicenceBean licences[] = (LicenceBean[])null;
        try
        {
            ZipFile zipfile = new ZipFile(strLicenceFileName);
            java.util.zip.ZipEntry zipEntry = zipfile.getEntry(strLicenceInfoEntyName);
            bisLicenceInfo = new BufferedInputStream(zipfile.getInputStream(zipEntry));
            licences = loadLicenceInfo(bisLicenceInfo);
            bisLicenceInfo.close();
            zipfile.close();
        }
        catch(Exception e)
        {
            throw new ActionException(ActionException.SYSTEM_MESSAGE, "error.com.iss.system.licence.load.failed", e.getMessage());
        }
        return licences;
    }

    public static final LicenceBean[] verifySinature(String strLicenceFileName, String strLicenceInfoEntyName)
        throws ActionException
    {
        LicenceBean licences[] = (LicenceBean[])null;
        File licenseFile = new File(strLicenceFileName);
        if(licenseFile.exists())
            try
            {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                if(classLoader == null)
                    classLoader = java.lang.Class.class.getClassLoader();
                String strPubKeyResourcePath = "com/iss/iam/" + SinatureUtil.PUBKEY_FILE;
                BufferedInputStream bisPublicKey = new BufferedInputStream(classLoader.getResourceAsStream(strPubKeyResourcePath));
                byte bytePublicKey[] = new byte[bisPublicKey.available()];
                bisPublicKey.read(bytePublicKey);
                bisPublicKey.close();
                boolean bValidate = SinatureUtil.verifySign(bytePublicKey, strLicenceFileName, SinatureUtil.LICENCEINFO_FILE, SinatureUtil.SIGNATURE_FILE);
                if(bValidate)
                    licences = loadLicenceInfoFromZipFile(strLicenceFileName, strLicenceInfoEntyName);
            }
            catch(ActionException e)
            {
                throw e;
            }
            catch(Exception e)
            {
                throw new ActionException(ActionException.SYSTEM_MESSAGE, "error.com.iss.system.licence.load.failed", e.getMessage());
            }
        return licences;
    }

    public static final void saveLicence(String strStoragePath, String strLicenceFileName, InputStream isLicence)
        throws ActionException
    {
        int nBufferLen = 4096;
        try
        {
            FileOutputStream fosLicence = new FileOutputStream(strStoragePath + strLicenceFileName);
            BufferedInputStream bisLicence = new BufferedInputStream(isLicence, nBufferLen);
            byte byteData[] = new byte[nBufferLen];
            int nRead;
            while((nRead = bisLicence.read(byteData, 0, nBufferLen)) != -1) 
                fosLicence.write(byteData, 0, nRead);
            bisLicence.close();
            fosLicence.close();
        }
        catch(Exception e)
        {
            ActionException ae = new ActionException();
            ae.AddException(ActionException.SYSTEM_MESSAGE, "error.com.iss.system.licence.upload.failed", e.getMessage());
            throw ae;
        }
    }

    static final String LICENCE_TAG = "licence";
    static String TITLE_TAG = "title";
    static String PRODUCTNAME_TAG = "product-name";
    static String VERSION_VERSION = "version";
    static String PATCH_ERSION_NO_TAG = "patch-version-no";
    static String CUSTOMNAME_TAG = "custom-name";
    static String EFFECTIVEDATE_TAG = "effective-date";
    static String EXPIREDATE_TAG = "expire-date";

}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 31 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/