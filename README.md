## springboot-webcrawler-example
A simple web crawler built with Spring Boot.

### OverView
- The crawler should be limited to one domain - 
so when you start with https://monzo.com/, it would crawl all pages within monzo.com, 
but not follow external links, for example to the Facebook and Twitter accounts.
(currently, this is an optional feature, as you can decide that per request).  
- Given a URL, it should print a simple site map, showing the links between pages.
Ideally, write it as you would a production piece of code.  
- Bonus points for tests and making it as fast as possible!

- This exercise is not meant to test whether you can program at all, 
but instead, you should think of it as a software design test. 
This means that we care less about a fancy UI or rendering the resulting sitemap
nicely and more about how your program is structured, the trade-offs you've made,
what behaviour the program exhibits etc..

### Technologies
- Java 11
- Spring Boot
- Maven

