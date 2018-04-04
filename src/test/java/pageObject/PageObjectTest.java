package pageObject;

import com.google.common.base.Predicate;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class PageObjectTest {
    private WebDriverWait wait;
    private final WebDriver driver;

    // Подготовка данных для входа.
    By login_locator=By.id("j_username");
    By password_locator=By.xpath("//*[@id=\"main-panel\"]/div/form/table/tbody/tr[2]/td[2]/input");
    By logDataSubmit_locator=By.id("yui-gen1");

    // Подготовка элементов страницы.
    By body_locator = By.xpath("//body");
    By manageJenkins_locator=By.xpath("//*[@id=\"tasks\"]/div[4]/a[2]");
    By manageUsers_locator = By.xpath("//*[@id=\"main-panel\"]/div[17]/a/dl/dt");
    By manageUsersAction_locator = By.xpath("//*[@id=\"main-panel\"]/div[17]/a/dl/dd[1]");
    By createUser_locator = By.xpath("//*[@id=\"tasks\"]/div[3]/a[2]");
    By form_locator = By.xpath("//*[@id=\"main-panel\"]/form");
    By username_locator = By.id("username");
    By password1_locator = By.name("password1");
    By password2_locator = By.name("password2");
    By fullName_locator = By.name("fullname");
    By email_locator=By.name("email");
    By createUserForm_locator=By.id("yui-gen5");
    By someUser_locator=By.xpath("//*[@id=\"people\"]/tbody/tr[2]/td[2]/a");
    By deleteSomeUser_locator=By.xpath("//*[@id=\"people\"]/tbody/tr[2]/td[4]/a[2]");
    By deleteAdmin_locator=By.cssSelector("#people > tbody > tr:nth-child(2) > td:nth-child(4) > a:nth-child(2)");
    By checkDeleteMessage_locator=By.name("delete");
    By deleteUser_locator=By.id("yui-gen5");


    public PageObjectTest(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 30);

        // Провекрка того факта, что мы на верной странице.
        if ((!driver.getTitle().equals("Jenkins")) ||
                (!driver.getCurrentUrl().equals("http://localhost:8080/login?from=%2F"))) {
            throw new IllegalStateException("Wrong site page!");
        }
    }
    // Переход со страницы логирования на главную.
    public PageObjectTest logUserName(String login){
        driver.findElement(login_locator).clear();
        driver.findElement(login_locator).sendKeys(login);
        return this;
    }
    public PageObjectTest logUserPassword(String password){
        driver.findElement(password_locator).clear();
        driver.findElement(password_locator).sendKeys(password);
        return this;
    }
    public PageObjectTest logUserSubmit(){
        driver.findElement(logDataSubmit_locator).click();
        return this;
    }

    // Переход с главной страницы к заполнению формы.
    public PageObjectTest submitLinkManageJenkins() {
        driver.findElement(manageJenkins_locator).click();
        return this;
    }
    public PageObjectTest submitLinkManageUsers() {
        driver.findElement(manageUsers_locator).click();
        return this;
    }
    public PageObjectTest submitLinkManageUsersAction() {
        driver.findElement(manageUsersAction_locator).click();
        return this;
    }
    public PageObjectTest submitLinkCreateUser() {
        driver.findElement(createUser_locator).click();
        return this;
    }

    // Поиск формы и опредения типов путей.
    public boolean isFormPresentForReal() {
         waitForLoad(driver);
        WebElement form = driver.findElement(By.xpath("//form[contains(@action,'securityRealm/createAccountByAdmin')]"));
        if (form == null) {
            return false;
        }

        List<WebElement> textElements = form.findElements(By.xpath("//input[@type='text' and not(text())]"));
        if (textElements.size()!=3) {
            return false;
        }
        List<WebElement> passElements = form.findElements(By.xpath("//input[@type='password' and not(text())]"));
        if (passElements.size()!=2) {
            return false;
        }
        return true;
    }

    // Заполнение имени.
    public PageObjectTest setName(String name) {
        driver.findElement(username_locator).clear();
        driver.findElement(username_locator).sendKeys(name);
        return this;
    }

    // Заполнение пороля 1й формы.
    public PageObjectTest setPassword1(String password1) {
        driver.findElement(password1_locator).clear();
        driver.findElement(password1_locator).sendKeys(password1);
        return this;
    }

    // Заполнение пороля 2й формы.
    public PageObjectTest setPassword2(String password2) {
        driver.findElement(password2_locator).clear();
        driver.findElement(password2_locator).sendKeys(password2);
        return this;
    }

    // Заполнение полного имени.
    public PageObjectTest setFullName(String fullName) {
        driver.findElement(fullName_locator).clear();
        driver.findElement(fullName_locator).sendKeys(fullName);
        return this;
    }

    // Указание e-mail.
    public PageObjectTest setEmail(String email) {
        driver.findElement(email_locator).clear();
        driver.findElement(email_locator).sendKeys(email);
        return this;
    }

    // Заполнение всех полей формы.
    public PageObjectTest setAllFields(String name, String password1, String password2, String fullName, String email) {
        setName(name);
        setPassword1(password1);
        setPassword2(password2);
        setFullName(fullName);
        setEmail(email);
        return this;
    }
    // Отправка данных из формы.
    public PageObjectTest submitForm() {
        driver.findElement(createUserForm_locator).click();
        return this;
    }

    // Проверить наличие ссылки на странице
    public boolean linkIsPresent( By locator) {
        return driver.findElement(locator).isEnabled();
    }
    // Для упрощения отправки данных.
    public PageObjectTest submitAllFilledForm(String name, String password1, String password2, String fullName, String email) {
        setAllFields(name, password1,password2,fullName,email);
        return submitForm();
    }

    // Поиск элементов на странице.
    public boolean isElementPresent(By locator) {
        boolean elementCondition = false;
        try{
            elementCondition = driver.findElement(locator).isDisplayed();
        }
        catch (NoSuchElementException e){
            return  elementCondition;
        }
        return elementCondition;
    }

    // Удаление вновь созданного пользователя.
    public PageObjectTest deleteUser () {
        driver.findElement(deleteSomeUser_locator).click();
        return this;
    }
    public PageObjectTest deleteUserButtonClick () {
        driver.findElement(deleteUser_locator).click();
        return this;
    }


    // Проверка вхождения подстроки в текст страницы.
    public boolean pageTextContains(String search_string, By locator) {
        return driver.findElement(locator).getText().contains(search_string);
    }

    // Получение значения имени.
    public String getName() {
        return driver.findElement(username_locator).getAttribute("value");
    }

    // Получение значения password1.
    public String getPassword1() {
        return driver.findElement(password1_locator).getAttribute("value");
    }
    // Получение значения password2.
    public String getPassword2() {
        return driver.findElement(password2_locator).getAttribute("value");
    }

    // Получение значения полного имени.
    public String getFullName() {
        return driver.findElement(fullName_locator).getAttribute("value");
    }
    // Получение значения полного e-mail.
    public String getEmail() {
        return driver.findElement(email_locator).getAttribute("value");
    }

    void waitForLoad(WebDriver driver) {
        Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {
            public boolean apply(WebDriver input) {
                return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
            }

        };
        new FluentWait<WebDriver>(driver).until(pageLoaded);
    }
}

