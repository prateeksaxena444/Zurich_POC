package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = createInstance("test-output/ExtentReport.html");
        }
        return extent;
    }

    public static ExtentReports createInstance(String fileName) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

        htmlReporter.config().setDocumentTitle("ZURICH DEMO - Automation Report");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.DARK);  // You can set LIGHT or DARK theme

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "LocalHost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User Name", "Prateek");

        return extent;
    }
}
