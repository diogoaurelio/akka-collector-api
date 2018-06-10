

## Getting started

This repo was kickstarted based on [Akka's HTTP quickstart template](https://developer.lightbend.com/guides/akka-http-quickstart-scala/)

- [Follow instructions from Akka](https://developer.lightbend.com/guides/akka-http-quickstart-scala/) 
- 


## Live testing App

```
curl -H "Content-type: application/json" -X POST -d '{"name": "MrX", "age": 31, "countryOfResidence": "Canada"}' http://localhost:8080/users

curl -H "Content-type: application/json" -X POST -d '{"name": "Anonymous", "age": 55, "countryOfResidence": "Iceland"}' http://localhost:8080/users

curl -H "Content-type: application/json" -X POST -d '{"name": "Bill", "age": 67, "countryOfResidence": "USA"}' http://localhost:8080/users
```