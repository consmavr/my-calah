<a name="readme-top"></a>

# Mancalah game
Mancalah game

## Run with docker 
The easiest way to run the application is, at the root of the project, to simply run:
  ```
  docker-compose up
  ```
No other actions are needed. You can then access the application at `localhost:3000`

## Run the application manually
#### Frontend

First install all dependencies. In `front-end` directory run:
```
npm install
```

Then run the front-end with:
```
npm run dev
```
It will expose the frontend at `localhost:3000` by default.

#### Backend
To build the backend, in `back-end` directory run:

```
mvn clean install
```

To run the backend:
```
mvn spring-boot:run
```
This will expose the backend at `localhost:8080` by default.


## Description

### Front end

The front-end aplication is a simple react application, created with vite, to display the current state of the board. Numbers on the pits represend the number of seeds in that pot. In each turn the only the highlited (white border) pits can be selected. 

The user can create a new game which will available after refresh(unless the backend has been restarted).

To create a new game while another one is ognoing, the user needs to click on new game.

An announcement is shown when the game is over and the current score is shown as well (the score is the number of seeds in each of the player stores)

### Backend
The backed is based on:
- Java 17
- Spring boot v3.1.5
- Maven v3.9.2  

The main components of the backend are:

- Pits. Houses and stores 
- Board. The board composed of houses and stores
- Players. Players are responsible for moving the seeds on their part of the board
- Game. The game doesn't interact with the state of the board but decides which player's turn it is, when the game is over and who is the winner

### Decisions
- Used spring boot for rest APIs mostly becuase i am more familiar with that.
- Decided to use rest API instead of websockets because it's easier to set up.
- Decided not to use any database for persisting information because of simpler implementation. The state of the board is presisted using ConcurrentHashMap.
- Since the focus of this project is the backend i didn't add any tests in the front-end :)

