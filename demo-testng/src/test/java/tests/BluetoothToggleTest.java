package tests;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

/**
 * AI 生成的第一版（未手改）：打开设置 → 进蓝牙页 → 关开关 → 断言。
 * 定位信息来自真实会话：蓝牙入口 //*[@text="蓝牙"]，开关 android:id/switch_widget。
 */
public class BluetoothToggleTest {

    private AndroidDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("a416d98")
                .setAppPackage("com.android.settings")
                .setAppActivity(".Settings")
                .setNoReset(true);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test
    public void turnOffBluetooth() {
        // 1. 进蓝牙设置页
        WebElement bluetoothEntry = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='蓝牙']")));
        bluetoothEntry.click();

        // 2. 关掉主开关
        WebElement switchWidget = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("android:id/switch_widget")));
        switchWidget.click();

        // 3. 断言已关闭
        wait.until(d -> "关闭".equals(
                d.findElement(By.id("android:id/switch_widget")).getAttribute("text")));
        Assert.assertEquals(
                driver.findElement(By.id("android:id/switch_widget")).getAttribute("text"),
                "关闭",
                "蓝牙主开关应处于关闭状态");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
