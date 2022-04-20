package com.example.springbootwebcrawler.service;

import com.example.springbootwebcrawler.tools.ScanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j(topic = "WebCrawler")
@Service
public class WebCrawlerServiceImpl implements WebCrawlerService {

    private final ScanUtils scanUtils;
    private Pattern pattern;

    @Value("${default-pattern}")
    private String defaultPattern;


    @Autowired
    public WebCrawlerServiceImpl(ScanUtils scanUtils) {
        this.scanUtils=scanUtils;
    }

    @PostConstruct
    private void init(){
        log.info("default-pattern:{}",defaultPattern);
        // create a regex pattern
        // the url is a single word ,and we'll be excluding two types of protocols
        pattern = Pattern.compile(defaultPattern);
    }

    @Override
    public List<String> scan(String rootURL, boolean rootOnly, Integer breakpoint) throws IOException {

        //urls to be scanned
        Queue<String> urlQueue = new LinkedList<>();
        //already scanned urls
        List<String> visitedURLs = new ArrayList<>();

        UriComponents uriComponents = scanUtils.analyze(rootURL);
        log.info("host: {}", uriComponents.getHost());

        //initialize the queue with root url
        urlQueue.add(rootURL);
        visitedURLs.add(rootURL);

        Matcher matcher;

        if (rootOnly){
            while (!urlQueue.isEmpty()) {
                // remove the head url string from this queue to begin traverse.
                URL url = new URL(urlQueue.remove());

                matcher = pattern.matcher(scanUtils.getHtmlContent(url));
                // Each time the regex matches a URL in the HTML,
                // add it to the queue for the next traverse and to the list of visited URLs.
                breakpoint = getBreakpoint(urlQueue, visitedURLs, matcher, breakpoint,uriComponents.getHost());

                // exit the outermost loop if it reaches the breakpoint.
                if (breakpoint == 0) break;
            }
        }else {
            while (!urlQueue.isEmpty()) {
                URL url = new URL(urlQueue.remove());
                matcher = pattern.matcher(scanUtils.getHtmlContent(url));
                // case we needed to include all results
                breakpoint = getBreakpoint(urlQueue, visitedURLs, matcher, breakpoint);
                if (breakpoint == 0) break;
            }
        }
        log.info("num of results: {}", visitedURLs.size());
        return visitedURLs;
    }

    private int getBreakpoint(Queue<String>urlQueue ,List<String>visitedURLs, Matcher matcher, int breakpoint) {
        while (matcher.find()) {
            String currentURL = matcher.group();

            if (!visitedURLs.contains(currentURL)) {
                visitedURLs.add(currentURL);
                urlQueue.add(currentURL);
            }
            // exit the loop if it reaches the breakpoint.
            if (breakpoint == 0) {
                break;
            }
            breakpoint--;
        }
        return breakpoint;
    }


    private int getBreakpoint(Queue<String>urlQueue ,List<String>visitedURLs, Matcher matcher, int breakpoint, String domain) {
        while (matcher.find()) {
            String currentURL = matcher.group();

            if (!visitedURLs.contains(currentURL) && currentURL.contains(domain)) {
                visitedURLs.add(currentURL);
                urlQueue.add(currentURL);
            }
            // exit the loop if it reaches the breakpoint.
            if (breakpoint == 0)
                break;

            breakpoint--;
        }
        return breakpoint;
    }
}
