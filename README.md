

## Getting started

This repo was kickstarted based on [Akka's HTTP quickstart template](https://developer.lightbend.com/guides/akka-http-quickstart-scala/)

- [Follow instructions from Akka](https://developer.lightbend.com/guides/akka-http-quickstart-scala/) 
- 


## Live testing App

```
curl -H "Content-type: application/json" -X POST -d '{"uuid": "blah", "event_name": "user_login", "provider": "auth_provider", "version": "1.0", "published_at":"2018", "data": {"name":"buh", "kkk": 1}}' http://localhost:7777/api/1.0.0/ingest
```