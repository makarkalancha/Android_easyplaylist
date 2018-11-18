package com.makco.easyplaylist.exception;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Process;

import com.makco.easyplaylist.engine.ExceptionActivity;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final static String LINE_SEPARATOR = "\n";
    private final static int MAX = 10;
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");

    private final Activity myActivity;

    public ExceptionHandler(Activity context) {
        this.myActivity = context;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        StringBuilder errorReport = new StringBuilder();
        try {
            String stackTrace = ExceptionUtils.getStackTrace(e);


            String currentDateTime = simpleDateFormat.format(new Date());
            errorReport.append(currentDateTime);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("************ CAUSE OF ERROR ************");
            errorReport.append(LINE_SEPARATOR);
            errorReport.append(stackTrace);

            errorReport.append(LINE_SEPARATOR);
            errorReport.append("************ DEVICE INFORMATION ***********");
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Brand: ");
            errorReport.append(Build.BRAND);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Device: ");
            errorReport.append(Build.DEVICE);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Model: ");
            errorReport.append(Build.MODEL);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Id: ");
            errorReport.append(Build.ID);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Product: ");
            errorReport.append(Build.PRODUCT);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("************ FIRMWARE ************");
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("SDK: ");
            errorReport.append(Build.VERSION.SDK_INT);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Release: ");
            errorReport.append(Build.VERSION.RELEASE);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Incremental: ");
            errorReport.append(Build.VERSION.INCREMENTAL);
            errorReport.append(LINE_SEPARATOR);


            File dir = new File(Environment.getExternalStorageDirectory(), "com.makco.easyplaylist");
            boolean dirMkdir = false;
            if (!dir.exists() || !dir.isDirectory()) {
                dirMkdir = dir.mkdirs();
            }else {
                dirMkdir = true;
            }

            if (dirMkdir) {
                File[] files = dir.listFiles();
                if (files.length > (MAX - 1)) {
                    TreeSet<String> fileNameSet = new TreeSet<>();
                    for (File file : files) {
                        fileNameSet.add(file.getName());
                    }
                    File fileToDelete = new File(dir, fileNameSet.first());
                    fileToDelete.delete();
                }

                File newExceptionFile = new File(dir, currentDateTime);
                FileOutputStream fos = new FileOutputStream(newExceptionFile);
                try{
                    fos.write(errorReport.toString().getBytes());
                }finally {
                    fos.close();
                }
            }else {
                throw new Exception("Cannot create folder " + dir.getAbsolutePath());
            }


        }catch (Exception exc){

            StringBuilder newException = new StringBuilder();
            newException.append(ExceptionUtils.getStackTrace(exc));
            newException.append(LINE_SEPARATOR);
            newException.append("while trying to save exception:");
            newException.append(LINE_SEPARATOR);
            newException.append(errorReport);

            Intent intent = new Intent(myActivity, ExceptionActivity.class);
            intent.putExtra("error", newException.toString());

            try {
                myActivity.startActivity(intent);
            }catch (Exception e1) {
                System.out.println(ExceptionUtils.getStackTrace(e1));
            }

        }finally {
            Process.killProcess(Process.myPid());
            System.exit(10);
        }

    }
}