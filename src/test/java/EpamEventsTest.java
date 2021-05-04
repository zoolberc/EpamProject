import driver.DriverConfiguration;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import pages.EventsPage;

import java.text.ParseException;

public class EpamEventsTest extends DriverConfiguration {

    @Test
    @DisplayName("Просмотр предстоящих мероприятий")
    public void viewUpcomingEvents() {
        homePage
                .openHomePage()
                .goToEventsTab();
        eventsPage
                .checkCountFutureEvents();
    }

    @Test
    @DisplayName("Просмотр карточек мероприятий")
    public void viewingEventCards(){
        homePage
                .openHomePage()
                .goToEventsTab();
        eventsPage
                .goToPastEvents()
                .checkEventCard();
    }

    @Test
    @DisplayName("Валидация дат предстоящих мероприятий")
    public void validatingDatesUpcomingEvents() throws ParseException, EventsPage.DateException {
        homePage
                .openHomePage()
                .goToEventsTab();
        eventsPage
                .clickUpcomingEvents()
                .validationDate();
    }

    @Test
    @DisplayName("Просмотр прошедших мероприятий в Канаде")
    public void viewPastEvents() throws ParseException, EventsPage.DateException {
        homePage
                .openHomePage()
                .goToEventsTab();
        eventsPage
                .goToPastEvents()
                .selectLocation()
                .checkCountPastEvents()
                .validationDateLessCurrent();
    }

    @Test
    @DisplayName("Фильтрация докладов по категориям")
    public void filteringReports() throws Exception {
        homePage
                .openHomePage()
                .goToVideoTab();
        videoPage
                .clickMoreFilter()
                .setFilter()
                .checkFilter();
    }

    @Test
    @DisplayName("Поиск докладов по ключевому слову")
    public void searchReports() throws Exception {
        homePage
                .openHomePage()
                .goToVideoTab();
        videoPage
                .searchReports()
                .checkReports();

    }

}
