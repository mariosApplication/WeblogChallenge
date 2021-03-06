# Weblogchallenge - Application Readme
This is a solution to the processing & analytical part of the weblog challenge assignment. It is implemented in `Scala` using the `Spark` framework.

## Setup
1. Navigate to the project directory
1. Extract the input data file: `gunzip -k data/2015_07_22_mktplace_shop_web_log_sample.log.gz data/`
1. Run the `SBT` console: `sbt`
1. Enter the `Scala` console: `console`
1. Run the analysis: `WeblogChallenge.runAnalysis()`
1. After `~2 minutes` the results should be in the `results` directory (assuming you are using a modern computer...).

## Run the tests
The project contains a test suit which covers the whole `Engine` package, except from the `AnalyticGoals` file (due to time restrictions). The test suit includes some integration tests which use files from the `test/resources` folder.

To run the tests, execute on the project directory: `sbt test`

## Code description

The codebase consists of three main parts, namely the `WeblogChallenge` and `AnalyticalGoals` files and the `Engine` package. The `AnalyticalGoals` file exposes an API which tackles each of the goals 1-4. The `Engine` package contains the implementation details of those API methods. Finally the `WeblogChallenge` file provides the method `runAnalysis` which runs steps 1-4 and persists the results to the directory `./results`. 

## Some of the results
1. Average session time: `37.96` seconds
1. Total number of unique url visits: `438,886`
1. Top 5 most engaged users (ip address and max session time):
    1. `213.239.204.204:35094`: `34.42645`
    1. `103.29.159.138:57045`:  `34.42343`
    1. `203.191.34.178:10400`:  `34.42243`
    1. `78.46.60.71:58504`:     `34.41436`
    1. `54.169.191.85:15462`:   `34.36146`

---
---
---

# WeblogChallenge
This is an interview challenge for Paytm Labs. Please feel free to fork. Pull Requests will be ignored.

The challenge is to make make analytical observations about the data using the distributed tools below.

## Processing & Analytical goals:

1. Sessionize the web log by IP. Sessionize = aggregrate all page hits by visitor/IP during a fixed time window.
    https://en.wikipedia.org/wiki/Session_(web_analytics)

2. Determine the average session time

3. Determine unique URL visits per session. To clarify, count a hit to a unique URL only once per session.

4. Find the most engaged users, ie the IPs with the longest session times

## Additional questions for Machine Learning Engineer (MLE) candidates:
1. Predict the expected load (requests/second) in the next minute

2. Predict the session length for a given IP

3. Predict the number of unique URL visits by a given IP

### Tools allowed (in no particular order):
- Spark (any language, but prefer Scala or Java)
- Pig
- MapReduce (Hadoop 2.x only)
- Flink
- Cascading, Cascalog, or Scalding

If you need Hadoop, we suggest 
HDP Sandbox:
http://hortonworks.com/hdp/downloads/
or 
CDH QuickStart VM:
http://www.cloudera.com/content/cloudera/en/downloads.html


### Additional notes:
- You are allowed to use whatever libraries/parsers/solutions you can find provided you can explain the functions you are implementing in detail.
- IP addresses do not guarantee distinct users, but this is the limitation of the data. As a bonus, consider what additional data would help make better analytical conclusions
- For this dataset, complete the sessionization by time window rather than navigation. Feel free to determine the best session window time on your own, or start with 15 minutes.
- The log file was taken from an AWS Elastic Load Balancer:
http://docs.aws.amazon.com/ElasticLoadBalancing/latest/DeveloperGuide/access-log-collection.html#access-log-entry-format



## How to complete this challenge:

A. Fork this repo in github
    https://github.com/PaytmLabs/WeblogChallenge

B. Complete the processing and analytics as defined first to the best of your ability with the time provided.

C. Place notes in your code to help with clarity where appropriate. Make it readable enough to present to the Paytm Labs interview team.

D. Complete your work in your own github repo and send the results to us and/or present them during your interview.

## What are we looking for? What does this prove?

We want to see how you handle:
- New technologies and frameworks
- Messy (ie real) data
- Understanding data transformation
This is not a pass or fail test, we want to hear about your challenges and your successes with this particular problem.
