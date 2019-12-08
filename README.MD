# Short URL Generation web service

## Overview
Web service for short URL creation and forwarding to original ones

## API documentation
All calls to API must be started with http://localhost:8080/

Application exposes following REST endpoints

| Http method | Endpoint               |  Description                                                  |                      
|-------------|------------------------|---------------------------------------------------------------|
| POST        | /url/short             | Creates and returns short URL for the given full URL          |
| GET         | /{shortUrl}            | Forward to full URL that corresponds to the given short URL   |

## Structure Overview
This service use Spring MVC framework with Spring boot, Spring JPA to work with data storage, H2 In-Memory
database, and micrometer to generate custom metrics.  

### Generating short URL
There are two ways to generate short url with the following cons: 
 - generate random string
    - cons: with growing number of short urls in the application, there is also increase a probability that new 
    randomly generated string already exist. So we need to catch such cases and generate another value.
 - compute a hash (MD5 or SHA256) from short URL.
    - cons: if different users enters the same URL - they will get same short URL, what is not acceptable
 
It has been decided to generate short URL as random string generation. In generated strings can be used letters both
lower and upper case and digits from 0 to 9. The length of generated URL can be adjusted in `application.yaml` file,
property: `app-properties: short-url-length`. With `short-url-length:10` possible number of unique combinations
is: 839 quadrillion what is pretty much acceptable number. 

### Metrics
`micrometer` library is used to generate custom metrics. Following metrics are generated

| Metric description                                                                     | Endpoint to metric value                                  |
|----------------------------------------------------------------------------------------|-----------------------------------------------------------|
| number of request to `/url/short` endpoint                                             | /actuator/metrics/url.request.create.short.url            |
| number of forward by short url request                                                 | /actuator/metrics/url.request.forward.to.full.url         |
| number of successful forward by short url request                                      | /actuator/metrics/url.request.forward.to.full.url.success |
| number of unsuccessful forward by short url request                                    | /actuator/metrics/url.request.forward.to.full.url.failed  |
| value in percentage, how many short URLs out of all available random values was used   | /actuator/metrics/unique.values.used.percentage           |
