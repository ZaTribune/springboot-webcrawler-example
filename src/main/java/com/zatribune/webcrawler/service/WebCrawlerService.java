package com.zatribune.webcrawler.service;

import java.io.IOException;
import java.util.List;

public interface WebCrawlerService {

    List<String> scan(String rootURL, boolean rootOnly, Integer breakPoint) throws IOException;
}
