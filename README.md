

## Getting started

This repo was kickstarted based on [Akka's HTTP quickstart template](https://developer.lightbend.com/guides/akka-http-quickstart-scala/)

- [Follow instructions from Akka](https://developer.lightbend.com/guides/akka-http-quickstart-scala/) 
- 


## Live testing App

```
curl -H "Content-type: application/json" -X POST -d '{"user_id": "3404", "event_name": "user.login", "tracking_version": "v5.0", "user_action": "login", "timestamp": "2018-06-17T20:28:11.478Z"}' http://localhost:7777/api/v1.0/ingest
```