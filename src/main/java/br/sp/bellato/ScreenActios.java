package br.sp.bellato;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.CommandExecutionHelper;
import io.appium.java_client.HasSettings;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;

import javax.sound.midi.Sequence;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static net.bytebuddy.matcher.ElementMatchers.is;

public class ScreenActios {
    private final WebDriver driver;

    public ScreenActios(WebDriver driver) {
        this.driver = driver;
    }


    public void pressAndroidKey(AndroidKey key) {
        ((AndroidDriver) driver).pressKey(new KeyEvent(key));
    }

    public void pressIOSKey(String key) {
        String iosKeyPath = "//XCUIElementTypeKeyboard//XCUIElementTypeKey[@name='%s']";
        By by = By.xpath(String.format(iosKeyPath, key));
        driver.findElement(by).click();
    }

    public void pressIOSKeys(String keys) {
        for (int i = 0; i < keys.length(); i++) {
            pressIOSKey(keys.substring(i, i + 1));
        }
    }

    public boolean isDisplayed(WebElement element, Duration duration) {
        try {
            return await().atMost(duration).until(() -> element.isDisplayed(), is(true));
        } catch (ConditionTimeoutException e) {
            return false;
        }
    }

    public void fingerSwipe(int fromX, int fromY, int toX, int toY, Duration duration) {
        List<Sequence> sequences = new ArrayList<>();

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Sequence swipe = new Sequence(finger, 0);
        swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), fromX, fromY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(duration, PointerInput.Origin.viewport(), toX, toY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        sequences.add(swipe);

        driver.perform(sequences);
    }

    public void fingerSwipe(
            double fromXFraction,
            double fromYFraction,
            double toXFraction,
            double toYFraction,
            Duration duration) {
        final Dimension size = driver.manage().window().getSize();
        final int fromX = calcCoord(size.width, fromXFraction);
        final int fromY = calcCoord(size.height, fromYFraction);
        final int toX = calcCoord(size.width, toXFraction);
        final int toY = calcCoord(size.height, toYFraction);

        fingerSwipe(fromX, fromY, toX, toY, duration);
    }

    public void swipe(int fromX, int fromY, int toX, int toY, Duration duration) {
        final Dimension size = driver.manage().window().getSize();
        final int fX = keepCoordWithinBounds(fromX, 0, size.width);
        final int fY = keepCoordWithinBounds(fromY, 0, size.height);
        final int tX = keepCoordWithinBounds(toX, 0, size.width);
        final int tY = keepCoordWithinBounds(toY, 0, size.height);
        (new TouchAction((PerformsTouchActions) driver)).press(point(fX, fY))
                .waitAction(waitOptions(duration))
                .moveTo(point(tX, tY))
                .release()
                .perform();
    }

    public void swipe(final int fromX, final int fromY, final int toX, final int toY) {
        swipe(fromX, fromY, toX, toY, ofMillis(1_500));
    }

    public void swipe(double fromXFraction,
                      double fromYFraction,
                      double toXFraction,
                      double toYFraction,
                      Duration duration) {
        final Dimension size = driver.manage().window().getSize();
        final int fromX = calcCoord(size.width, fromXFraction);
        final int fromY = calcCoord(size.height, fromYFraction);
        final int toX = calcCoord(size.width, toXFraction);
        final int toY = calcCoord(size.height, toYFraction);
        swipe(fromX, fromY, toX, toY, duration);
    }

    public void swipe(final double fromXFraction,
                      final double fromYFraction,
                      final double toXFraction,
                      final double toYFraction) {
        swipe(fromXFraction, fromYFraction, toXFraction, toYFraction, ofMillis(1_500));
    }

    public static int calcCoord(final int max, final double fraction) {
        return (int) ((double) max * fraction);
    }

    protected static int keepCoordWithinBounds(final int coord, final int lb, final int ub) {
        if (lb < 0 || lb >= ub) {
            throw new IllegalArgumentException(String.format("The 'lower bound' must be greater than or "
                    + "equal to zero and less than the 'upper bound' but: (lb=%d, ub=%d)", lb, ub));
        }
        if (coord <= lb) {
            return 1;
        } else if (coord >= ub) {
            return ub - 1;
        } else {
            return coord;
        }
    }

    public void swipeDownTheCentre() {
        swipe(0.5, 0.25, 0.5, 0.75);
    }

    public void swipeUpTheCentre() {
        swipe(0.5, 0.75, 0.5, 0.25);
    }

    public void swipeDownTheLeftSide() {
        swipe(0.01, 0.25, 0.01, 0.75);
    }

    public void swipeUpTheLeftSide() {
        swipe(0.01, 0.75, 0.01, 0.25);
    }

    public void swipeDownTheRightSide() {
        swipe(0.99, 0.25, 0.99, 0.75);
    }

    public void swipeUpTheRightSide() {
        swipe(0.99, 0.75, 0.99, 0.25);
    }

    public void swipeFromLeftToRight() {
        swipe(0.1, 0.5, 0.9, 0.5);
    }

    public void swipeFromRightToLeft() {
        swipe(0.9, 0.5, 0.1, 0.5);
    }

    public void swipeWithLongPress(double fromXPercentage,
                                   double fromYPercentage,
                                   double toXPercentage,
                                   double toYPercentage) {

        Dimension dimension = driver.manage().window().getSize();

        int x0 = keepCoordWithinBounds((int) (fromXPercentage * dimension.width), 0, dimension.width);
        int y0 = keepCoordWithinBounds((int) (fromYPercentage * dimension.height), 0, dimension.height);
        int x1 = keepCoordWithinBounds((int) (toXPercentage * dimension.width), 0, dimension.width);
        int y1 = keepCoordWithinBounds((int) (toYPercentage * dimension.height), 0, dimension.height);

        swipeWithLongPress(x0, y0, x1, y1);
    }

    public boolean appiumSlideDownAndSerch(By by, int attempts) {

        boolean displayed = false;
        int counter = 0;

        do {
            try {
                displayed = driver.findElement(by).isDisplayed();
            } catch (Exception e) {

            }
            if (!displayed) {
                mobileActions.verticalSwipeUp();
            }
            counter++;
        }
        while (!displayed && counter < attempts);

        return displayed;
    }

    public boolean appiumSlideDownAndSerch(WebElement element) {
        return appiumSlideDownAndSerch(element, 1);
    }

    public boolean appiumSlideDownAndSerch(By by) {
        return appiumSlideDownAndSerch(by, 10);
    }

    public boolean dontTransferScheduled(WebElement element) {
        boolean displayed = false;
        try {
            displayed = element.isDisplayed();
        } catch (Exception e) {

        }
        return !displayed;
    }

    public boolean appiumSlideDownAndSerch(WebElement element, int attempts) {

        boolean displayed = false;
        int counter = 0;

        do {
            try {
                displayed = element.isDisplayed();
            } catch (Exception e) {

            }
            if (!displayed) {
                mobileActions.verticalSwipeUp();
            }
            counter++;
        }
        while (!displayed && counter < attempts);

        return displayed;
    }

    public void swipeWithLongPress(int x1, int y1, int x2, int y2) {
        new TouchAction<>((PerformsTouchActions) driver)
                .longPress(PointOption.point(x1, y1))
                .waitAction(waitOptions(ofSeconds(3)))
                .moveTo(PointOption.point(x2, y2))
                .release().perform();
    }

    public void swipeElementHorizontal(WebElement element, double inicio, double fim) {
        int y = element.getLocation().y + (element.getSize().height / 2);

        int start_x = (int) (element.getSize().width * inicio);
        int end_x = (int) (element.getSize().width * fim);

        new TouchAction<>((PerformsTouchActions) driver)
                .longPress(PointOption.point(start_x, y))
                .waitAction(waitOptions(ofSeconds(2)))
                .moveTo(PointOption.point(end_x, y))
                .release().perform();
    }

    public void swipeElementHorizontal(WebElement element, double inicio, double fim, int slideLimit) {
        int y = element.getLocation().y + (element.getSize().height / 2);

        int start_x = (int) (element.getSize().width * inicio);
        int end_x = (int) (element.getSize().width * fim);

        while (slideLimit > 0) {

            swipeWithLongPress(start_x, y, end_x, y);
            slideLimit--;
        }
    }

    public boolean swipeElementHorizontal(By by, double x1, double y1, double x2, double y2, int slideLimit) {

        boolean displayed = false;
        int counter = 0;

        do {
            try {
                displayed = driver.findElement(by).isDisplayed();
            } catch (Exception e) {

            }
            if (!displayed) {
                swipeWithLongPress(x1,y1,x2,y2);
            }
            counter++;
        }
        while (!displayed && counter < slideLimit);

        return displayed;
    }

    public boolean swipeElementHorizontal(WebElement refCard, WebElement elementTargetCard, double inicio, double fim, int slideLimit) {
        await(ofSeconds(12));
        int y = refCard.getLocation().y + (refCard.getSize().height / 2);

        int start_x = (int) (refCard.getSize().width * inicio);
        int end_x = (int) (refCard.getSize().width * fim);

        while (slideLimit > 0) {
            if (isDisplayed(elementTargetCard, ofSeconds(10))) {
                return true;
            }

            new TouchAction<>((PerformsTouchActions) driver)
                    .press(PointOption.point(start_x, y))
                    .waitAction(waitOptions(ofSeconds(10)))
                    .moveTo(PointOption.point(end_x, y))
                    .release().perform();
            slideLimit--;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    public void tap(final int x, final int y) {
        (new TouchAction((PerformsTouchActions) driver)).tap(point(x, y)).perform();
    }

    public Point getRegionOfElement(WebElement element, double percentX, double percentY) {
        Rectangle rect = element.getRect();
        int x = (int) (rect.getX() + rect.getWidth() * percentX);
        int y = (int) (rect.getY() + rect.getHeight() * percentY);
        return new Point(x, y);
    }

    public void tapRegionOfElement(WebElement element, double percentX, double percentY) {
        Point coordinates = getRegionOfElement(element, percentX, percentY);

        System.out.println("Clicking on the coordinates " + "X: " + coordinates.getX() + " | " + "Y: " + coordinates.getY());

        new TouchAction<>((PerformsTouchActions) driver)
                .tap(PointOption.point(coordinates.getX(), coordinates.getY()))
                .perform();
    }

    public void tapDimension(double xPercentage, double yPercentage) {
        Dimension dimension = driver.manage().window().getSize();
        int x = (int) (xPercentage * dimension.width);
        int y = (int) (yPercentage * dimension.height);

        new TouchAction<>((PerformsTouchActions) driver)
                .tap(PointOption.point(x, y))
                .perform();
    }

    public void tap(final WebElement element) {
        mobileActions.tapOn(element);
    }

    public void tapPositionElement(WebElement element) {
        Dimension dimension = element.getSize();
        Point location = element.getLocation();

        int xClick = location.getX() + 1;
        int yClick = location.getY() + 1;

        mobileActions.tapOn(xClick, yClick);
    }

    public void longPress(final int x, final int y) {
        (new TouchAction((PerformsTouchActions) driver)).press(point(x, y))
                .waitAction(waitOptions(ofSeconds(2L)))
                .release()
                .perform();
    }

    public boolean findCard(WebElement refCard, WebElement elementTargetCard, int slideLimit, int fragmentSlide) {
        Dimension dimensionCard = refCard.getSize();
        Point locationCard = refCard.getLocation();

        int y = locationCard.getY() + (dimensionCard.getHeight() / 2);
        int x1 = locationCard.getX();
        int x2 = x1 + (dimensionCard.getWidth() / fragmentSlide);

        while (slideLimit > 0) {
            if (isDisplayed(elementTargetCard, ofSeconds(10))) {
                return true;
            }

            swipeWithLongPress(x2, y, x1, y);
            slideLimit--;
        }
        return false;
    }

    public boolean tapMiddleCardToLeft(WebElement refCard, int slideLimit, int fragmentSlide) {
        Dimension dimensionCard = refCard.getSize();
        Point locationCard = refCard.getLocation();

        int y = locationCard.getY() + (dimensionCard.getHeight() / 2);
        int x1 = locationCard.getX();
        int x2 = x1 + (dimensionCard.getWidth() / fragmentSlide);

        while (slideLimit > 0) {
            if (isDisplayed(refCard, ofSeconds(10))) {
                return true;
            }

            swipeWithLongPress(x2, y, x1, y);
            slideLimit--;
        }

        return false;
    }

    public boolean tapMiddleCard(WebElement refCard, int slideLimit) {
        Dimension dimensionCard = refCard.getSize();
        Point locationCard = refCard.getLocation();

        int y = locationCard.getY() + (dimensionCard.getHeight() / 2);
        int x1 = locationCard.getX();
        int x2 = x1 + (dimensionCard.getWidth() / 3);

        while (slideLimit > 0) {

            swipeWithLongPress(x2, y, x1, y);
            slideLimit--;
        }

        return false;
    }

    public void tapElementCard(WebElement refCard, WebElement elementTargetCard, int slideLimit, int fragmentSlide) {
        if (findCard(refCard, elementTargetCard, slideLimit, fragmentSlide)) {
            elementTargetCard.click();
        }
    }

    public void tapOn(double percentX, double percentY) {
        Dimension screenSize = driver.manage().window().getSize();
        int x = (int) (screenSize.getWidth() * percentX);
        int y = (int) (screenSize.getHeight() * percentY);

        mobileActions.tapOn(x, y);
    }

    public boolean tapCard(WebElement refCard, int slideLimit) {
        Dimension dimensionCard = refCard.getSize();
        Point locationCard = refCard.getLocation();

        int y = locationCard.getY() + (dimensionCard.getHeight() / 2);
        int x1 = locationCard.getX();
        int x2 = x1 + dimensionCard.getWidth();

        while (slideLimit > 0) {

            swipeWithLongPress(x2, y, x1, y);
            slideLimit--;
        }

        return false;
    }

    public void findElementCardWithLongPress(WebElement refCard, WebElement elementTargetCard, int slideLimit, int fragmentSlide) {
        Dimension dimensionCard = refCard.getSize();
        Point locationCard = refCard.getLocation();

        int y = locationCard.getY() + (dimensionCard.getHeight() / 2);
        int x1 = locationCard.getX();
        int x2 = x1 + (dimensionCard.getWidth() / fragmentSlide);

        swipeWithLongPress(x1, y, x2, y);
    }

    public void longPress(final WebElement element) {
        mobileActions.longPress(element);
    }

    public Optional<WebElement> findElement(final By androidLocator, final By iosLocator) {
        try {
            if (MobilePlatform.ANDROID.matches(driver)) {
                return Optional.ofNullable(driver.findElement(androidLocator));
            } else if (MobilePlatform.IOS.matches(driver)) {
                return Optional.ofNullable(driver.findElement(iosLocator));
            } else {
                return Optional.empty();
            }
        } catch (NotFoundException | StaleElementReferenceException x) {
            return Optional.empty();
        }
    }

    public List<WebElement> findElements(final By androidLocator, final By iosLocator) {
        try {
            if (MobilePlatform.ANDROID.matches(driver)) {
                return ImmutableList.copyOf(driver.findElements(androidLocator));
            } else if (MobilePlatform.IOS.matches(driver)) {
                return ImmutableList.copyOf(driver.findElements(iosLocator));
            } else {
                return ImmutableList.of();
            }
        } catch (NotFoundException | StaleElementReferenceException x) {
            return ImmutableList.of();
        }
    }

    public Optional<WebElement> findElement(final WebElement ancestor,
                                            final By androidLocator,
                                            final By iosLocator) {
        try {
            if (MobilePlatform.ANDROID.matches(driver)) {
                return Optional.ofNullable(ancestor.findElement(androidLocator));
            } else if (MobilePlatform.IOS.matches(driver)) {
                return Optional.ofNullable(ancestor.findElement(iosLocator));
            } else {
                return Optional.empty();
            }
        } catch (NotFoundException | StaleElementReferenceException x) {
            return Optional.empty();
        }
    }

    public List<WebElement> findElements(final WebElement ancestor,
                                         final By androidLocator,
                                         final By iosLocator) {
        try {
            if (MobilePlatform.ANDROID.matches(driver)) {
                return ImmutableList.copyOf(ancestor.findElements(androidLocator));
            } else if (MobilePlatform.IOS.matches(driver)) {
                return ImmutableList.copyOf(ancestor.findElements(iosLocator));
            } else {
                return ImmutableList.of();
            }
        } catch (NotFoundException | StaleElementReferenceException x) {
            return ImmutableList.of();
        }
    }

    @Override
    public Optional<String> read(final WebElement element) {
        if (MobilePlatform.ANDROID.matches(driver)) {
            return Optional.ofNullable(element.getText());
        } else if (MobilePlatform.IOS.matches(driver)) {
            return Optional.ofNullable(element.getAttribute("name"));
        } else {
            return Optional.empty();
        }
    }

    public ScreenActions setAppiumSettings(String key, Object value) {
        ImmutableMap<String, Object> map1 = ImmutableMap.<String, Object>builder()
                .put(key, value)
                .build();
        ImmutableMap<String, Object> map2 = ImmutableMap.<String, Object>builder()
                .put("settings", map1)
                .build();
        CommandExecutionHelper.execute(driver, new AbstractMap.SimpleEntry<>("setSettings", map2));
        return this;
    }

    public Map<String, Object> getAppiumSettings() {
        return ((HasSettings) driver).getSettings();
    }

    @Override
    public boolean isAndroid() {
        return isAndroid(driver);
    }

    @Override
    public boolean isIOS() {
        return isIOS(driver);
    }

    @SneakyThrows
    @Override
    public <T> Optional<T> ifAndroid(Callable<T> callable) {
        if (isAndroid()) {
            return Optional.ofNullable(callable.call());
        } else {
            return Optional.empty();
        }
    }

    public ScreenActions ifAndroid(ExceptionRunnable runnable) {
        if (isAndroid()) {
            runnable.run();
        }
        return this;
    }

    public <T> Optional<T> ifIOS(Callable<T> callable) {
        if (isIOS()) {
            return Optional.ofNullable(callable.call());
        } else {
            return Optional.empty();
        }
    }

    @SneakyThrows
    @Override
    public ScreenActions ifIOS(ExceptionRunnable runnable) {
        if (isIOS()) {
            runnable.run();
        }
        return this;
    }

    @Override
    public Device device() {
        String udid = driver.getCapabilities()
                .getCapability("udid")
                .toString();
        return objectMapper.convertValue(udid, Device.class);
    }

    @Override
    public boolean isDeviceOneOf(Device... devices) {
        Objects.requireNonNull(devices, "'devices' should not be null");
        return Stream.of(devices)
                .anyMatch(device -> device == device());
    }

    @Override
    public boolean isChecked(WebElement element) {
        if (isAndroid()) {
            return "true".equals(element.getAttribute("checked"));

        } else if (isIOS()) {
            return "1".equals(element.getAttribute("value"));

        } else {
            return false;
        }
    }


}
