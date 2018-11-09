/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   IAMProKeyGen.java

package com.iss.system.security;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// Referenced classes of package com.iss.system.security:
//            SinatureUtil

public class IAMProKeyGen
{

    public IAMProKeyGen()
    {
    }

    public void kg(String prikeyFile, String pubkeyFile)
    {
        if(prikeyFile == null)
            prikeyFile = SinatureUtil.PRIVATEKEY_FILE;
        if(pubkeyFile == null)
            pubkeyFile = SinatureUtil.PUBKEY_FILE;
        try
        {
            if(!SinatureUtil.createKey(prikeyFile, pubkeyFile))
                System.out.println("error occured!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sign(String strPrivateKeyFile, String strLicenceInfo, String strSignFile, String strLicenceFile)
    {
        if(strPrivateKeyFile == null)
            strPrivateKeyFile = SinatureUtil.PRIVATEKEY_FILE;
        if(strLicenceInfo == null)
            strLicenceInfo = SinatureUtil.LICENCEINFO_FILE;
        if(strSignFile == null)
            strSignFile = SinatureUtil.SIGNATURE_FILE;
        if(strLicenceFile == null)
            strLicenceFile = SinatureUtil.LICENCE_FILE;
        try
        {
            BufferedInputStream bisPrivateKey = new BufferedInputStream(new FileInputStream(strPrivateKeyFile));
            byte byteEncodedPrivateKey[] = new byte[bisPrivateKey.available()];
            bisPrivateKey.read(byteEncodedPrivateKey);
            bisPrivateKey.close();
            BufferedInputStream bisMessage = new BufferedInputStream(new FileInputStream(strLicenceInfo));
            byte byteMessage[] = new byte[bisMessage.available()];
            bisMessage.read(byteMessage);
            bisMessage.close();
            byte byteSign[] = SinatureUtil.signData(byteEncodedPrivateKey, byteMessage);
            if(byteSign != null)
            {
                String strFileNames[] = {
                    strLicenceInfo, strSignFile
                };
                byte byteContent[][] = {
                    byteMessage, byteSign
                };
                if(!createZip(strLicenceFile, strFileNames, byteContent))
                    System.out.println("error occured!");
            } else
            {
                System.out.println("error occured!");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void verifySign(String strPubkeyFile, String strLicenceInfo, String strSignFile, String strLicenceFile)
    {
        if(strPubkeyFile == null)
            strPubkeyFile = SinatureUtil.PUBKEY_FILE;
        if(strLicenceInfo == null)
            strLicenceInfo = SinatureUtil.LICENCEINFO_FILE;
        if(strSignFile == null)
            strSignFile = SinatureUtil.SIGNATURE_FILE;
        if(strLicenceFile == null)
            strLicenceFile = SinatureUtil.LICENCE_FILE;
        try
        {
            BufferedInputStream bisPubKey = new BufferedInputStream(new FileInputStream(strPubkeyFile));
            byte bytePubKey[] = new byte[bisPubKey.available()];
            bisPubKey.read(bytePubKey);
            bisPubKey.close();
            if(SinatureUtil.verifySign(bytePubKey, strLicenceFile, strLicenceInfo, strSignFile))
                System.out.println("VERIFY SINATURE SUCCEEDED! IT IS A LEGAL IAM PRO LICENCE!");
            else
                System.out.println("VERIFY SINATURE FAILED! IT IS A UNLEGAL IAM PRO LICENCE!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {
        IAMProKeyGen iAMProKeyGen = new IAMProKeyGen();
        if(args.length == 0 || args.length > 5)
            System.out.println("usage:  \n\t -kg [priKeyFilename] [pubkeyFilename] \n\t\tGenerate key pair;  \n\t -sign [prikeyFilename] [licenceInfoFilename] [licenceFilename] \n\t\tsignature file and generature licence;\n\t -ver [pubkeyFile] [LicenceFile]");
        else
        if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("-kg"))
                iAMProKeyGen.kg(null, null);
            if(args[0].equalsIgnoreCase("-sign"))
                iAMProKeyGen.sign(null, null, null, null);
            if(args[0].equalsIgnoreCase("-ver"))
                iAMProKeyGen.verifySign(null, null, null, null);
        } else
        if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("-kg"))
                iAMProKeyGen.kg(args[1], null);
            if(args[0].equalsIgnoreCase("-sign"))
                iAMProKeyGen.sign(args[1], null, null, null);
            if(args[0].equalsIgnoreCase("-ver"))
                iAMProKeyGen.verifySign(args[1], null, null, null);
        } else
        if(args.length == 3)
        {
            if(args[0].equalsIgnoreCase("-kg"))
                iAMProKeyGen.kg(args[1], args[2]);
            if(args[0].equalsIgnoreCase("-sign"))
                iAMProKeyGen.sign(args[1], args[2], null, null);
            if(args[0].equalsIgnoreCase("-ver"))
                iAMProKeyGen.verifySign(args[1], null, null, args[2]);
        } else
        if(args.length == 4)
        {
            if(args[0].equalsIgnoreCase("-kg"))
                iAMProKeyGen.kg(args[1], args[2]);
            if(args[0].equalsIgnoreCase("-sign"))
                iAMProKeyGen.sign(args[1], args[2], null, args[3]);
        }
    }

    public boolean createZip(String strZipFileName, String strFileNames[], byte byteContents[][])
    {
        boolean ifSucceed = false;
        int nBufferSize = 2048;
        try
        {
            ZipOutputStream zosLicence = new ZipOutputStream(new FileOutputStream(strZipFileName));
            byte data[] = new byte[nBufferSize];
            String strZipEntryName = null;
            for(int i = 0; i < strFileNames.length; i++)
            {
                strZipEntryName = strFileNames[i].substring(strFileNames[i].lastIndexOf("\\") + 1, strFileNames[i].length());
                ZipEntry zipEntry = new ZipEntry(strZipEntryName);
                zosLicence.putNextEntry(zipEntry);
                zosLicence.write(byteContents[i]);
            }

            zosLicence.close();
            ifSucceed = true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return ifSucceed;
    }
}



/***** DECOMPILATION REPORT *****

	DECOMPILED FROM: D:\My Documents\itreasury4.0\lib\iss_framework.jar


	TOTAL TIME: 31 ms


	JAD REPORTED MESSAGES/ERRORS:


	EXIT STATUS:	0


	CAUGHT EXCEPTIONS:

 ********************************/