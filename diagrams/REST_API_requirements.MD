# FTB REST API requirements.

This document contains high-level requirements for the FTB's REST API.

## 0. Security

- REQ00.01 Any REST API call shall be secured with HTTP Basic Auth mechanism.
- REQ00.02 "ADMIN" role users only shall be able to access the API.

## 1. Resources: USER

- REQ01.01 The API shall support CRUD operations for the resource.
- REQ01.02 The API shall allow to search for a user by ``username``.
- REQ01.03 The API shall not allow to create user records having duplicate ``usernames``.
- REQ01.04 The API shall not allow to create user records having duplicate ``e-mails``.
- REQ01.05 Any successful operation response except for `CREATE` shall have HTTP Status code `200`.
- REQ01.06 Any successful `CREATE` operation response shall have HTTP Status code `201`.

## 2. Resources: Aircraft

- REQ02.01 The API shall support CRUD operations for the resource.
- REQ02.02 The API shall allow to search for an aircraft by ``model name``.
- REQ02.03 The API shall allow to search for an aircraft by ``manufacturer name``.
- REQ02.04 Any successful operation response except for `CREATE` shall have HTTP Status code `200`.
- REQ02.05 Any successful `CREATE` operation response shall have HTTP Status code `201`.

## 3. Resources: Airport

- REQ03.01 The API shall support CRUD operations for the resource.
- REQ03.02 Any successful operation response except for `CREATE` shall have HTTP Status code `200`.
- REQ03.03 Any successful `CREATE` operation response shall have HTTP Status code `201`.

## 4. Resources: Flight

- REQ04.01 The API shall support CRUD operations for the resource.
- REQ04.02 The API shall allow to search for an aircraft by ``flightNumber``.
- REQ04.03 The API shall allow to search for an aircraft by ``departureAirportCode``, ``destinationAirportCode``
  , ``departureDate``.
- REQ04.04 Any date field shall be formatted as ``yyyy-mm-dd``. Additional Time/Time Zone info allowed.
- REQ04.05 The API shall allow to book a ticket for a flight.
- REQ04.06 The API shall allow to verify booking for a flight.
- REQ04.07 The API shall allow to cancel booking for a flight.
- REQ04.08 Any successful operation response except for `CREATE` shall have HTTP Status code `200`.
- REQ04.09 Any successful `CREATE` operation response shall have HTTP Status code `201`.

## 5. Resources: Passenger

- REQ05.01 The API shall support CRUD operations for the resource.
- REQ05.02 Any successful operation response except for `CREATE` shall have HTTP Status code `200`.
- REQ05.03 Any successful `CREATE` operation response shall have HTTP Status code `201`.

## 6. Misc

- REQ06.01 The API shall allow to generate fake test data.
- REQ06.02 The application shall provide a human-friendly GUI for the REST API (e.g. Swagger UI).

