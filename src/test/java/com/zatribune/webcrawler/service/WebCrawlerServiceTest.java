package com.zatribune.webcrawler.service;

import com.zatribune.webcrawler.tools.ScanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {
        "default-pattern=\\\\b((https|http?|ftp|file)://[-a-zA-Z\\\\d+&@#/%?=~_|!:,.;]*[-a-zA-Z\\\\d+&@#/%=~_|])",
})
@ExtendWith(SpringExtension.class)
class WebCrawlerServiceTest {


    @Value("${default-pattern}")
    String defaultPattern;
    private WebCrawlerService service;

    @MockBean
    private ScanUtils scanUtils;

    @BeforeEach
    void setUp() {

        service=new WebCrawlerServiceImpl(scanUtils);
        ReflectionTestUtils.setField(service,"pattern", Pattern.compile(defaultPattern));
    }

    @Test
    void whenScanWithValidInput_thenReturnSuccessfully() throws IOException, URISyntaxException {
        URL url = new URL("https://www.zatribune.com/");

        UriComponents uriComponents= UriComponentsBuilder.fromUri(url.toURI()).build();

        //clearly there are 2 links here
        String html = "<!doctype html><html lang='en'><head><meta charset='utf-8'><title>Test Webpage</title><meta name='author' content='Muhammad Ali'><meta property='og:url' content='https://www.zatribune.com/a-basic-html-template/'><link rel='stylesheet' href='css/styles.css?v=1.0'></head><body><!-- your content here... --><a href='https://www.w3schools.com'>Visit W3Schools.com!</a></body></html>";


        when(scanUtils.getHtmlContent(any(URL.class))).thenReturn(html);

        when(scanUtils.analyze(anyString())).thenReturn(uriComponents);

        System.out.println(service);
        List<String> result = service.scan(url.toString(), true, 10);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}