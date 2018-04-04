package pageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

public class TestPage {
    String base_url = "http://localhost:8080/login?from=%2F";
    WebDriver driver = null;

    @BeforeClass
    public void beforeClass() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:/course/epam/tat19/HT1/lib/chromedriver.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("chrome.switches", Arrays.asList("--homepage=about:blank"));
        driver = new ChromeDriver(capabilities);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }

    @Test
    public void sampleTest() {

        driver.get(base_url);
        PageObjectTest page = new PageObjectTest(driver);

        //Логирование пользователя
        page.logUserName("Sviatlana");
        page.logUserPassword("5vet@");
        page.logUserSubmit();

        //1-проверка содержит ли страница ссылку Manage Genkins

        Assert.assertTrue(page.pageTextContains("Manage Jenkins", page.manageJenkins_locator), "No such link found!");
        //2-действие: "Кликаем по ссылке Manage Genkins"
        page.submitLinkManageJenkins();

        //3-проверка содержит ли страница ссылку Manage Users
        Assert.assertTrue(page.pageTextContains("Manage Users", page.manageUsers_locator), "No such link 'Manage Users' found!");
        Assert.assertTrue(page.pageTextContains("Create/delete/modify users that can log in to this Jenkins", page.manageUsersAction_locator),
                "No such link 'Create/delete/modify users that can log in to this Jenkins' found!");
        //3-действие: "Кликаем по ссылке Manage Users"
        page.submitLinkManageUsers();

        //4-проверка содержит ли страница ссылку Create User"
        Assert.assertTrue(page.pageTextContains("Create User",page.createUser_locator),"No such link 'Create Use' found!");
        //4-действие: "Кликаем по ссылке Create User"
        page.submitLinkCreateUser();

        // 5-проверка: "Страница содержит форму с полями «Username», «Password», «Confirm password»,«Full name»
        //  «E-mail address» и кнопкой отправки данных «Create User».
        Assert.assertTrue(page.isFormPresentForReal(), "No suitable forms found!");

        // 6-действие: "В поле «User» ввести «username»."
        page.setName("someuser");
        // 6-проверка: "Значение появляется в поле."
        Assert.assertEquals(page.getName(), "someuser", "Unable to fill 'User name' field");


        // 7-действие: "В поле «Password» ввести «somepassword»."
        page.setPassword1("somepassword");
        // 7-проверка: "Значение появляется в поле."
        Assert.assertEquals(page.getPassword1(), "somepassword", "Unable to fill 'Password1' field");


        // 8-действие: "В поле «Confirm password» ввести «somepassword»."
        page.setPassword2("somepassword");
        // 8-проверка: "Значение появляется в поле."
        Assert.assertEquals(page.getPassword2(), "somepassword", "Unable to fill 'Password2' field");


        // 9-действие: "В поле «Full name» выбрать пол «Some Full Name»."
        page.setFullName("Some Full Name");
        // 9-проверка: "Значение появляется в поле."
        Assert.assertEquals(page.getFullName(), "Some Full Name", "Unable to fill 'Some Full Name' field");

        // 10-действие: "В поле «E-mail address» выбрать пол «some@addr.dom»."
        page.setEmail("some@addr.dom");
        // 10-проверка: "Значение появляется в поле."
        Assert.assertEquals(page.getEmail(), "some@addr.dom", "Unable to fill 'some@addr.dom' field");

        // 11-действие: " Нажать «Create User»."
        page.submitForm();

        // 12-проверка: " В таблице появляется новый пользователь"
        Assert.assertTrue(page.pageTextContains("someuser",page.someUser_locator),"No such 'user' found!");

        // 13-проверка: " В таблице появляется ссылка user/someuser/delete"
        Assert.assertTrue(page.linkIsPresent(page.deleteSomeUser_locator),"No such link 'user/someuser/delete' found!");

        //14-действие: "Кликаем по ссылке user/someuser/delete"
        page.deleteUser();

        // 15-проверка: " На странице появляется надпись "Are you sure about deleting the user from Jenkins""
        //Проверка наличия кнопки "Yes"
        Assert.assertTrue(page.pageTextContains("Are you sure about deleting the user from Jenkins?",page.checkDeleteMessage_locator),
                "No such message 'Are you sure about deleting the user from Jenkins' found!");
        Assert.assertTrue(page.pageTextContains("Yes",page.deleteUser_locator),"No such button 'Yes' found!");

        //16-действие: "Кликаем по ссылке кнопке "Yes""
        page.deleteUserButtonClick();

        //17-действие: "Проверка отсутсвия на странице кнопки с надписью «Yes», ячейки с текстом «someuser», а так же
        // ссылки с атрибутом  «user/admin/delete»"
        Assert.assertFalse(page.isElementPresent(page.deleteUser_locator),"Button 'Yes' found on page!");
        Assert.assertFalse(page.pageTextContains("someuser", page.someUser_locator),"Such link 'someuser' found on page!");
        Assert.assertFalse(page.isElementPresent(page.deleteAdmin_locator),"Such link'user/admin/delete' found on page!");

    }
}
