package com.kimseongje.springbootwebmvc2;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest2 {

    @Autowired
    WebClient webClient;    // HtmlUnit을 이용한 Web Test

    @Test
    public void hello() throws Exception {
        HtmlPage htmlPage = webClient.getPage("/hello");
        HtmlHeading1 h1 = htmlPage.getFirstByXPath("//h1");
        assertThat(h1.getTextContent()).isEqualToIgnoringCase("Seongje");

    }
}
