Backend
=======

REST endpoints
--------------
- plural
- separated with hyphens
- There are two type of endpoints for every ressource:
  - The "open" endpoints in the path "/api/open"
  - The "closed" endpoints which can be called with keycloak authentification in the path "/api/closed"

Data Loading
------------
- All data will always be loaded in the main page component and then passed to the underlying
componenets. This leads to less requests because all the data can always be loaded in one request.
- All the data will be loaded from endpoints which always deliver only one data type. E.g. when a 
newsfeed entry has to be loaded and also the creator users for every newsfeed first the newsfeed 
entries will be loaded, in the frontend the creator uids extracted and then in a second call the 
creators will be fetched. This leads to a little bit more code in the frontend but on the other side
you do not have to create separate endpoints for each combination of data.

General development rules
-------------------------
- The only way to get well is to get fast. The first point should be always to refactor everything as
  well as possible.
- Test coverage should be always as near to 100% as possible

Payment
-------
As a payment provider we plan to use [Stripe](https://stripe.com/de).

Used technologies
-----------------
- Data mapping: [Mapstruct](https://mapstruct.org/)
- Backend: Spring Boot
- Database: Postgres
- Frontend: React
- Toasts: [Toastify](https://fkhadra.github.io/react-toastify/introduction/)

Frontend
========

Symbols
-------
- Used library is  [Material](https://fonts.google.com/icons?icon.set=Material+Icons&icon.style=Outlined)
- Defined in text_icons


Business
========

Wording
-------
Wording is for code and frontend
- firstName
- lastName
- gender: male, female, other
- role: leader, follower, both


Business partners
-----------------
- Somebody must always be available and respond to alarms or customer problems.