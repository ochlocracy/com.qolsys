package Reporting;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import org.apache.log4j.Logger;
import utils.ConfigProps;
import utils.Setup;

import java.io.File;

public class ReportSetup extends Setup{

    ExtentReports report;
    ExtentTest log;
    String page_name = "";
    Logger logger = Logger.getLogger(page_name);

    public ReportSetup() throws Exception {
        ConfigProps.init();
    }

    //testAreaName is either Smoke, Sanity, or Regression
    public void create_report(String reportName) throws InterruptedException {
        String file = projectPath + "/extent-config.xml";
        report = new ExtentReports(projectPath + "/Report/AutomationReport.html");
        report.loadConfig(new File(file));
        report
                .addSystemInfo("User Name", "Automation")
                .addSystemInfo("Software Version", softwareVersion());
        log = report.startTest(reportName);
    }

    public void add_to_report(String testCaseName) {
        report = new ExtentReports(projectPath + "/Report/"+testCaseName+".html", false);
        log = report.startTest(testCaseName);
    }

}
