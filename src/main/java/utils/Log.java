package utils;

import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.io.PrintWriter;


public class Log {

// Initialize Log4j logs

    public  static Logger Log = Logger.getLogger(Log.class.getName());//

    // This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite

    public static void startTestCase(String sTestCaseName){

        Log.info("********************************************************************");
        Log.info("$$$$$$$$$$$$$$$$$$$$$  "+sTestCaseName+ "  $$$$$$$$$$$$$$$$$$$$$$$$$");
        Log.info("********************************************************************");
    }

    //This is to print log for the ending of the test case
    public static void endTestCase(String sTestCaseName){

        Log.info("XXXXXXXXXXXXXXXXXXXXXXX "+"-E---N---D-"+" XXXXXXXXXXXXXXXXXXXXXX");
    }

    // Need to create these methods, so that they can be called
    public static void info(String message) {
        Log.info(message);
    }
    public static void warn(String message) {
        Log.warn(message);
    }
    public static void error(String message) {
        Log.error(message);
    }
    public static void fatal(String message) {
        Log.fatal(message);
    }
    public static void debug(String message) {
        Log.debug(message);
    }

    public void clearLog() throws Exception {
        FileWriter fw = new FileWriter( new String(System.getProperty("user.dir"))+"/log/testlog.log");
        PrintWriter pw = new PrintWriter(fw);
        pw.println("");
        pw.flush();
        pw.close();
    }
}