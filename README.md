# AutomationExercise End-to-End Test Automation Framework (UI + API)

Target Application:
- UI: https://automationexercise.com
- UI Test Cases: https://automationexercise.com/test_cases
- API: https://automationexercise.com/api_list

## Tech Stack (Mandatory)
Java · Selenium WebDriver · TestNG · RestAssured · Maven · Allure Reporting · Page Object Model (POM)

---

## Group Members
- Nikoloz Ejibadze

---

## Test Case Ownership / Mapping

### UI Test Cases (10)
Automated by **Nikoloz Ejibadze**:
- **TC2**: Login User with correct email and password
- **TC3**: Login User with incorrect email and password
- **TC4**: Logout User
- **TC5**: Register User with existing email
- **TC6**: Contact Us Form
- **TC7**: Verify Test Cases Page
- **TC8**: Verify All Products and product detail page
- **TC9**: Search Product
- **TC10**: Verify Subscription in home page
- **TC12**: Add Products in Cart

### API Test Cases (10)
Automated by **Nikoloz Ejibadze**:
- **API 1**: GET `/api/productsList`
- **API 2**: POST `/api/productsList` (Method Not Supported)
- **API 3**: GET `/api/brandsList`
- **API 4**: PUT `/api/brandsList` (Method Not Supported)
- **API 5**: POST `/api/searchProduct`
- **API 6**: POST `/api/searchProduct` (without `search_product`)
- **API 7**: POST `/api/verifyLogin`
- **API 8**: POST `/api/verifyLogin` (without email)
- **API 9**: DELETE `/api/verifyLogin` (Method Not Supported)
- **API 10**: POST `/api/verifyLogin` (invalid details)

> Notes:
> - Some AutomationExercise API “response codes” are returned inside the JSON body (`responseCode`) even when HTTP status is 200.

---

## Project Structure
- `src/test/java/com/e2e/ui/pages` — Page Objects (POM)
- `src/test/java/com/e2e/ui/tests` — UI tests
- `src/test/java/com/e2e/api/client` — RestAssured API clients
- `src/test/java/com/e2e/api/tests` — API tests
- `src/test/java/com/e2e/core` — Driver factory, waits, base tests, config loader
- `src/test/resources/config.properties` — runtime configuration
- `target/allure-results` — Allure raw results output

---

## Requirements
- Java 17+
- Maven
- ChromeDriver or GeckoDriver in PATH
- Allure CLI installed (for viewing reports)

Arch Linux (optional install):
```bash
sudo pacman -S maven allure
```
---

## Test Stack Run Commands

### Run the full test suite (UI + API)
```bash
mvn clean test
allure serve target/allure-results
```
