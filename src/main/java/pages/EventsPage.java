package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class EventsPage {
    public WebDriver driver;
    public Logger logger = LogManager.getLogger(EventsPage.class);

    public EventsPage(WebDriver driver) {
        this.driver = driver;
    }


    @FindBy(xpath = "//div[@class='evnt-events-column cell-3']")
    private List<WebElement> events;

    @FindBy(xpath = "//span[@class='evnt-tab-counter evnt-label small white']")
    private List<WebElement> spanCountEvents;

    @FindBy(xpath = "//span[contains(text(), 'Past Events')]")
    private WebElement pastEventsSpan;

    @FindBy(xpath = "//*[@class = 'language']/child::span")
    private WebElement eventLanguage;

    @FindBy(xpath = "//*[@class = 'evnt-event-name']/child::h1")
    private WebElement eventTitle;

    @FindBy(xpath = "//span[@class = 'date']")
    private WebElement eventDate;

    @FindBy(xpath = "//span[@class = 'status reg-close']")
    private WebElement eventRegistrationInformation;

    @FindBy(xpath = "//*[@class = 'evnt-people-cell speakers']//child::div")
    private WebElement eventSpeaker;

    @FindBy(xpath = "//span[@class='evnt-tab-text desktop' and text()='Upcoming events']")
    private WebElement spanUpcomingEvents;

    @FindBy(xpath = "//*[@id='filter_location']")
    private WebElement locationFilter;

    @FindBy(xpath = "//*[@data-value='Canada']")
    private WebElement locationCanada;

    @FindBy(xpath = "//*[@class='date']")
    private List<WebElement> dateEvent;

    @FindBy(xpath = "//*[@class='evnt-tab-link nav-link active']//child::span")
    private WebElement checkEventsActive;

    public void checkCountFutureEvents() {
        int countEvents = events.size();
        logger.debug("Checking the number of cards equal to the counter on the Upcoming Events button");
        int countEventsOnLabel = Integer.parseInt(spanCountEvents.get(0).getText());
        assertEquals(countEvents, countEventsOnLabel);
        logger.info("The number of cards is equal to the counter on the Upcoming Events button");
    }

    public EventsPage checkCountPastEvents() {
        try {
            long lastHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");

            while (true) {
                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                sleep(2000);
                long newHeight = (long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollHeight");
                if (newHeight == lastHeight) {
                    break;
                }
                lastHeight = newHeight;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int countEvents = events.size();
        logger.debug("Checking the number of cards equal to the counter on the Past Events button");
        int countEventsOnLabel = Integer.parseInt(spanCountEvents.get(1).getText());
        assertEquals(countEvents, countEventsOnLabel);
        logger.info("The number of cards is equal to the counter on the Past Events button");
        return this;
    }

    public EventsPage goToPastEvents() {
        pastEventsSpan.isDisplayed();
        pastEventsSpan.click();
        logger.debug("Checking the display of past event cards on the page");
        assertEquals(checkEventsActive.getText(), "PAST EVENTS");
        logger.info("The page displays cards of past events");
        return this;
    }

    public void checkEventCard() {
        logger.debug("Checking the correct display of information on the event card");
        eventLanguage.isDisplayed();
        eventTitle.isDisplayed();
        eventDate.isDisplayed();
        eventRegistrationInformation.isDisplayed();
        eventSpeaker.isDisplayed();
        logger.info("Information on the event card is displayed correctly");
    }

    public EventsPage clickUpcomingEvents() {
        spanUpcomingEvents.isDisplayed();
        spanUpcomingEvents.click();
        logger.debug("Checking the display of upcoming event cards on the page");
        assertEquals(checkEventsActive.getText(), "UPCOMING EVENTS");
        logger.info("The page displays cards of upcoming events");
        return this;
    }

    /************************
     * Валидация даты посредством разделения даты из карточки события, если имеется разделитель "-", затем превращенеие их в тип Date для сравнения  *
     * с текущей датой
     ************************/
    public void validationDate() throws ParseException, DateException {
        logger.debug("Date validation on the event card");
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy", Locale.US);

        for (WebElement webElement : dateEvent) {

            String dateEv = webElement.getText();

            if (dateEv.contains("-")) {
                String[] date1 = dateEv.split("-");

                String dateFrom = date1[1].substring(1);
                String dateTo = date1[0] + date1[1].substring(date1[1].length() - 4);

                Date parseDateFrom = format.parse(dateFrom);
                Date parseDateTo = format.parse(dateTo);

                if (!(parseDateFrom.getTime() <= currentDate.getTime() & parseDateTo.getTime() >= currentDate.getTime() | parseDateFrom.getTime() > currentDate.getTime())) {
                    throw new DateException("Event ended");
                }
            } else {
                Date dateEvent = format.parse(dateEv);
                if (!(dateEvent.getTime() >= currentDate.getTime())) {
                    throw new DateException("Event ended");
                }
            }
        }
        logger.info("The dates on the card are displayed correctly");
    }

    public void validationDateLessCurrent() throws ParseException, DateException {
        logger.debug("Date validation on the event card");
        Date currentDate = new Date();

        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy", Locale.US);

        for (WebElement webElement : dateEvent) {

            String dateEv = webElement.getText();

            if (dateEv.contains("-")) {
                if (dateEv.length() > 15) {
                    String[] date1 = dateEv.split("-");

                    String dateFrom = date1[1].substring(1);
                    String dateTo = date1[0] + date1[1].substring(date1[1].length() - 4);

                    Date parseDateFrom = format.parse(dateFrom);
                    Date parseDateTo = format.parse(dateTo);

                    if (!(parseDateFrom.getTime() < currentDate.getTime() & parseDateTo.getTime() < currentDate.getTime())) {
                        throw new DateException("Event date is incorrect");
                    } else {
                        Date dateEvent = format.parse(dateEv);
                        if (!(dateEvent.getTime() < currentDate.getTime())) {
                            throw new DateException("Event date is incorrect");
                        }
                    }
                } else {
                    String[] date1 = dateEv.split("-");
                    String dateFrom = date1[1].substring(1);
                    String dateTo = date1[0] + date1[1].substring(date1[1].length() - 8);
                    Date parseDateFrom = format.parse(dateFrom);
                    Date parseDateTo = format.parse(dateTo);

                    if (!(parseDateFrom.getTime() < currentDate.getTime() & parseDateTo.getTime() < currentDate.getTime())) {
                        throw new DateException("Event date is incorrect");
                    }
                }
            } else {
                Date dateEvent = format.parse(dateEv);
                if (!(dateEvent.getTime() < currentDate.getTime())) {
                    throw new DateException("Event date is incorrect");
                }
            }
        }
        logger.info("The dates on the card are displayed correctly");
    }

    public EventsPage selectLocation() {
        logger.info("Location selection");
        locationFilter.isDisplayed();
        locationFilter.click();
        locationCanada.isDisplayed();
        locationCanada.click();
        return this;
    }

    public static class DateException extends Exception {
        public DateException(String message) {
            super(message);
        }
    }
}
