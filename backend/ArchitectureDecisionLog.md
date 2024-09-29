Backend
=======

REST endpoints
--------------

- plural
- separated with hyphens
- There are two type of endpoints for every ressource:
    - The "open" endpoints in the path "/api/open"
    - The "closed" endpoints which can be called with keycloak authentification in the path "
      /api/closed"
- Always return a ResponseEntity and not a dto itself

Data Loading
------------

- All data will always be loaded in the main page component and then passed to the underlying
  componenets. This leads to less requests because all the data can always be loaded in one request.
- All the data will be loaded from endpoints which always deliver only one data type. E.g. when a
  newsfeed entry has to be loaded and also the creator users for every newsfeed first the newsfeed
  entries will be loaded, in the frontend the creator uids extracted and then in a second call the
  creators will be fetched. This leads to a little bit more code in the frontend but on the other
  side
  you do not have to create separate endpoints for each combination of data.

General development rules
-------------------------

- The only way to get fast is to get well. The first point should be always to refactor everything
  as
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
- Rich Editor: [Quill](https://quilljs.com/playground/react)
- Calendar: [Fullcalendar](https://fullcalendar.io/)

Frontend
========

Code
----

- We use semicolons at the end of the lines of JavaScript code

Concepts
--------

- Requests for updates should only be sent when data is dirty.

Symbols
-------

- Used library
  is  [Material](https://fonts.google.com/icons?icon.set=Material+Icons&icon.style=Outlined)
- Defined in text_icons

Business
========

Wording
-------
Wording is for code and frontend

- firstName
- lastName
- gender: male, female, other
- dancingRole: leader, follower, both

Business partners
-----------------

- Somebody must always be available and respond to alarms or customer problems.

Corporate design
----------------

- Background Color:
- Main Color:
- Admin Color:

Besprechen mit Daniel:

- Testing:
    - Soviel ich weiß, soll man immer Komponenten möglichst isoliert von allem anderen Testen. Das
      würde bedeuten alles immer zu Mocken. Ich bin mir nicht socher ob das nicht unverhältnismäßig
      mehr
      arbeit ist, da man immer alle Methodenaufrufe manuell mocken muss.
    - Testrichtlinien:
        - Folgendes wird durch Application Context Tests getestet
            - Repositories
            - Http-Return Codes von Controller
            - Authentifizierung von Controllern
        - Alles andere wird durch Unit Tests getestet