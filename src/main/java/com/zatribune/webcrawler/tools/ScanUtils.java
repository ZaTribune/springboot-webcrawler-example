package com.zatribune.webcrawler.tools;

import com.zatribune.webcrawler.error.BackendException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScanUtils {

    public UriComponents analyze(String url) {
        //or I think to check for the host/domain within the first segment
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(URI.create(url));
        return builder.buildAndExpand();
    }

    public String getHtmlContent(URL url){
        // reading the webpage to a String
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            // read the whole HTML document specified by URL using a bufferedReader
            return bufferedReader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new BackendException(String.format("Error connecting to host: %s", url),e);
        }
    }
}
