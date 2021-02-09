# Websocker chat webservice

[![Logo](https://img.shields.io/badge/vert.x-3.9.5-purple.svg)](https://vertx.io")

This application was generated using http://start.vertx.io

---

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Environment Variables](#environment-variables)
* [Setup](#setup)
* [Features](#features)
* [References](#references)
* [Contact](#contact)

---

## General info
Real-time chat interface where multiple users can interact with each other by sending messages using websocket.

---

## Technologies
* Vert.x - Version 3.9.5
* Vue - Version 3.0.5

---

## Environment Variables
Following table is a **mandatory** environment variables used in this project.

| Variable Name | Datatype | Description |
| --- | --- | --- |
| APP_HOST | String | Server host |
| APP_PORT | Number | Server port |

---

## Setup
To launch your tests:
```
./mvnw clean test
```

To package your application:
```
./mvnw clean package
```

To run your application:
```
./mvnw clean compile exec:java
```

---

## Features
- [x] User is prompted to enter a username when he visits the chat app. The username will be stored in the application
- [x] User can see an `input field` where he can type a new message
- [x] By pressing the `enter` key or by clicking on the `send` button the text will be displayed in the `chat box` alongside his username (e.g. `John Doe: Hello World!`)

---

## References
* [Vert.x Documentation](https://vertx.io/docs/)
* [Vert.x Stack Overflow](https://stackoverflow.com/questions/tagged/vert.x?sort=newest&pageSize=15)
* [Vert.x User Group](https://groups.google.com/forum/?fromgroups#!forum/vertx)
* [Vert.x Gitter](https://gitter.im/eclipse-vertx/vertx-users)
* [App ideas: Chat App](https://github.com/florinpop17/app-ideas/blob/master/Projects/3-Advanced/Chat-App.md)
* [Web sockets with Vert.x and SockJS](https://itnext.io/web-sockets-with-vert-x-and-sockjs-1f0710264eea)

---

## Contact
Created by [Anas Juwaidi](mailto:anas.didi95@gmail.com)
