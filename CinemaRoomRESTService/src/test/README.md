# Testing this service via Postman
HOW TO simply test the service using Postman requests  
[File](https://github.com/SmartOven/JetBrainsAcademy/blob/main/CinemaRoomRESTService/src/test/CinemaRoomRESTService.postman_collection.json) with Postman requests

## Purchase/return ticket test
1) Start the service
2) Get list of available seats and see the default list
3) Purchase ticket for seat with `row = 1, column = 1` and copy token to the buffer
4) Get list of available seats and see the difference
5) Return ticket for seat with token from the buffer
6) Get list of available seats and see that everything went back to default

## Showing statistics test
1) Start the service
2) Check statistics with no password parameter and see the error
3) Check statistics with wrong password parameter and see the error
4) Check statistics with correct password parameter and see default the statistics
5) Purchase ticket, then check statistics again and see that it changed
6) Return purchased ticker, then check statistics again and see that it went back to default
