package com.e2e.core;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public abstract class BaseApiTest {
    // ApiBase.spec() handles baseUri + Allure filter per request
}
