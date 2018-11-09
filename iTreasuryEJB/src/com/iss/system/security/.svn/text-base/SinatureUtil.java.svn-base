/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   SinatureUtil.java

package com.iss.system.security;

import com.iss.system.action.ActionException;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.zip.ZipFile;

public final class SinatureUtil
{

    private SinatureUtil()
    {
    }

    public static boolean createKey(String strPriFile, String strPubFile)
        throws ActionException
    {
        boolean blnValidated = false;
        try
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            keyGen.initialize(1024, new SecureRandom());
            KeyPair pair = keyGen.generateKeyPair();
            java.security.PublicKey pub = pair.getPublic();
            java.security.PrivateKey priv = pair.getPrivate();
            byte pubKey[] = pub.getEncoded();
            byte priKey[] = priv.getEncoded();
            FileOutputStream fileOut = new FileOutputStream(strPubFile);
            fileOut.write(pubKey);
            fileOut.close();
            fileOut = new FileOutputStream(strPriFile);
            fileOut.write(priKey);
            fileOut.close();
            blnValidated = true;
        }
        catch(Exception e)
        {
            throw new ActionException("error.com.iss.system.licence.sign.failed", e.getMessage());
        }
        return blnValidated;
    }

    public static byte[] signData(byte byteEncodedPrivateKey[], byte byteMessage[])
        throws ActionException
    {
        try
        {
            PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(byteEncodedPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            java.security.PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);
            Signature dsaSignature = Signature.getInstance("DSA");
            dsaSignature.initSign(priKey);
            dsaSignature.update(byteMessage);
            return dsaSignature.sign();
        }
        catch(Exception e)
        {
            throw new ActionException("error.com.iss.system.licence.sign.failed", e.getMessage());
        }
    }

    public static boolean verifySign(byte byteEncodedPublicKey[], byte byteMessage[], byte byteSinature[])
        throws ActionException
    {
        try
        {
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(byteEncodedPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("DSA");
            java.security.PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
            Signature sign = Signature.getInstance("DSA");
            sign.initVerify(pubKey);
            sign.update(byteMessage);
            return sign.verify(byteSinature);
        }
        catch(Exception e)
        {
            throw new ActionException("error.com.iss.iam.licence.verify.failed", e.getMessage());
        }
    }

    public static byte[] signData(byte byteEncodedPrivateKey[], String strMessageFile)
        throws ActionException
    {
        InputStream is = null;
        byte sign[] = (byte[])null;
        try
        {
            is = new FileInputStream(strMessageFile);
            sign = signData(byteEncodedPrivateKey, is);
            if(is != null)
                is.close();
            return sign;
        }
        catch(IOException e)
        {
            throw new ActionException("error.com.iss.system.licence.sign.failedtoreaddata", e.getMessage());
        }
    }

    public static byte[] signData(byte byteEncodedPrivateKey[], InputStream isMessage)
        throws ActionException
    {
        boolean bool = false;
        try
        {
            BufferedInputStream bisMessage = new BufferedInputStream(isMessage);
            byte byteMessage[] = new byte[isMessage.available()];
            bisMessage.read(byteMessage);
            bisMessage.close();
            return signData(byteEncodedPrivateKey, byteMessage);
        }
        catch(IOException e)
        {
            throw new ActionException("error.com.iss.system.licence.verify.failedtoreaddata", e.getMessage());
        }
    }

    public static boolean verifySign(byte bytePublicKey[], String strLicenceFileName, String strLicenceInfoFile, String strSinatureFile)
        throws ActionException
    {
        try
        {
            ZipFile zipfile = new ZipFile(strLicenceFileName);
            java.util.zip.ZipEntry zipEntry = zipfile.getEntry(strLicenceInfoFile);
            BufferedInputStream bisLicenceInfo = new BufferedInputStream(zipfile.getInputStream(zipEntry));
            byte byteMessage[] = new byte[bisLicenceInfo.available()];
            bisLicenceInfo.read(byteMessage);
            bisLicenceInfo.close();
            zipEntry = zipfile.getEntry(strSinatureFile);
            BufferedInputStream bisSinature = new BufferedInputStream(zipfile.getInputStream(zipEntry));
            byte byteSign[] = new byte[bisSinature.available()];
            bisSinature.read(byteSign);
            bisSinature.close();
            return verifySign(bytePublicKey, byteMessage, byteSign);
        }
        catch(IOException e)
        {
            throw new ActionException("error.com.iss.system.licence.verify.failedtoreaddata", e.getMessage());
        }
    }

    public static String PRIVATEKEY_FILE = "iss-prikey.dat";
    public static String PUBKEY_FILE = "iss-pubkey.dat";
    public static String SIGNATURE_FILE = "iss-sinature.dat";
    public static String LICENCE_FILE = "iss-licence.jar";
    public static String LICENCEINFO_FILE = "iss-licence.xml";

}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 62 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/