# Cinema Room REST Service
RESTful API for booking seats at the virtual cinema

## How does API work
###Note that:
+ When talking about 'pojo' means 'Plain Old Java Object' that is automatically converted to JSON by the Spring Web
+ Every '...Info' class is pojo that either required as the body of `POST` request or being used as body for the response to the request

### Controllers:
1) [AvailableSeatsController](#AvailableSeatsController)
2) [PurchaseTicketController](#PurchaseTicketController)
3) [SellingStatisticsController](#SellingStatisticsController)

### AvailableSeatsController
Endpoints:
+ `GET /seats`
Returns [AvailableSeatsInfo](https://github.com/SmartOven/JetBrainsAcademy/blob/main/CinemaRoomRESTService/src/main/java/cinema/pojo/info/AvailableSeatsInfo.java) pojo

### PurchaseTicketController
Endpoints:
+ `POST /purchase` requires body with [SeatInfo](https://github.com/SmartOven/JetBrainsAcademy/blob/main/CinemaRoomRESTService/src/main/java/cinema/pojo/info/SeatInfo.java) pojo
Returns [PurchasedTicketInfo](https://github.com/SmartOven/JetBrainsAcademy/blob/main/CinemaRoomRESTService/src/main/java/cinema/pojo/info/PurchasedTicketInfo.java) pojo
+ `POST /return` requires body with [TicketTokenInfo](https://github.com/SmartOven/JetBrainsAcademy/blob/main/CinemaRoomRESTService/src/main/java/cinema/pojo/info/TicketTokenInfo.java) pojo
Returns [ReturnedTicketInfo](https://github.com/SmartOven/JetBrainsAcademy/blob/main/CinemaRoomRESTService/src/main/java/cinema/pojo/info/ReturnedTicketInfo.java) pojo

### SellingStatisticsController
Endpoints:
+ `POST /stats` requires body with `String` password
Returns [SellingStatisticsInfo](https://github.com/SmartOven/JetBrainsAcademy/blob/main/CinemaRoomRESTService/src/main/java/cinema/pojo/info/SellingStatisticsInfo.java) pojo

## Testing API
[Test folder](https://github.com/SmartOven/JetBrainsAcademy/tree/main/CinemaRoomRESTService/src/test), that contains Postman API tests collection and the documentation for it (in README.md)

## Project architecture
Architecture map:
```
cinema
|
|--> controllers
|    |--> REST Controllers
|
|--> exceptions
|    |--> Custom exceptions
|    |--> Exception handlers
|
|--> pojo
      |
      |--> Cinema class
      |--> Seat class
      |--> info
            --> All pojo classes 
```