# spring-intro
Create a Spring-based module, which handles event ticket booking.

Based on the attached source code model:

[x] 1. Implement three service classes:

UserService

EventService

ticket service

which should contain user, event, and booking-related functionality accordingly. Create an implementation of the BookingFacade interface which should delegate method calls to services mentioned above.

(0.5 point)

[x] 2. Configure spring application context based on the XML config file. (0.5 point)

[X] 3. Implement DAO objects for each of the domain model entities (User, Event, Ticket). They should store in and retrieve data from a common in-memory storage - java map. Each entity should be stored under a separate namespace, so you could list particular entity types. (0.5 point)

[x] Example for ticket - map entry {"ticket:" à {}}.

E.g. {"ticket:12345" à {"id" : 12345, "place" : 23, ......}}

[x] 4. Storage should be implemented as a separate spring bean. Implement the ability to initialize storage with some prepared data from the file during the application start (use spring bean post-processing features). Path to the concrete file should be set using property placeholder and external property file. (1 point)

[x] 5. DAO with storage bean should be inserted into services beans using auto wiring. Services beans should be injected into the facade using constructor-based injections. The rest of the injections should be done in a setter-based way. (1 point)

[ ] 6. Cover code with unit tests. (0.5 point)

[ ] 7. Code should contain proper logging. (0.5 point)

[ ] 8. Create several integration tests that instantiate the Application Context directly, lookup facade bean and perform some real-life scenario (e.g. create user, then create event, then book ticket for this event for the user, then cancel it). (0.5 point)