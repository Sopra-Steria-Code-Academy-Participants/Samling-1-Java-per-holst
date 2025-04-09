package com.soprasteria.demo;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.RequestOptions;

@UsePlaywright(PlayWrightJavaTest.CustomOptions.class) // Bootstrap al Playwright opsætning
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PlayWrightJavaTest {

    // opsætning specifik til tests i denne test klasse.
    public static class CustomOptions implements OptionsFactory {
        @Override
        public Options getOptions() {
            return new Options()
                    .setHeadless(false) // hvis du vil se browseren starte for browser tests
                    .setApiRequestOptions(new APIRequest.NewContextOptions()
                            .setBaseURL("http://127.0.0.1:8080"));
        }
    }

    @Test
    public void testApi(APIRequestContext request) {
        APIResponse issues = request.get("/v1/hello/world");
        SoftAssertions.assertSoftly(softAssertion -> {
            softAssertion.assertThat(issues.ok());
            softAssertion.assertThat(issues.status()).isEqualTo(200);
            softAssertion.assertThat(issues.text()).isEqualTo("Hello .net noobs!!");
        });
    }

    @Test
    public void testNoPost(APIRequestContext request) {
        APIResponse issues = request.post("/v1/hello/world",
                RequestOptions.create().setData("it's me"));
        SoftAssertions.assertSoftly(softAssertion -> {
            softAssertion.assertThat(issues.ok()).isFalse();
            softAssertion.assertThat(issues.status()).isEqualTo(405);
            softAssertion.assertThat(issues.text()).contains("Method Not Allowed");
        });
    }

    @Test
    public void testNoPut(APIRequestContext request) {
        APIResponse issues = request.put("/v1/hello/world",
                RequestOptions.create().setData("it's me"));
        SoftAssertions.assertSoftly(softAssertion -> {
            softAssertion.assertThat(issues.ok()).isFalse();
            softAssertion.assertThat(issues.status()).isEqualTo(405);
            softAssertion.assertThat(issues.text()).contains("Method Not Allowed");
        });
    }

    @Test
    public void testHeadAPI(APIRequestContext request) {
        APIResponse issues = request.head("/v1/hello/world");
        SoftAssertions.assertSoftly(softAssertion -> {
            softAssertion.assertThat(issues.ok());
            softAssertion.assertThat(issues.status()).isEqualTo(200);
            softAssertion.assertThat(issues.text()).isEmpty();
        });
    }

}
