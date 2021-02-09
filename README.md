# Application for cultural center management

## Brief description

It is the backend part of an application that supports the management of cultural centres. 

The programme provides users with many functions, for example:
* sending messages to the institution,
* signing up for events and rating them,
* taking part in competitions and voting,
* writing articles,
* applying for jobs.

Confirmation of the event registration is sent by email to the user.

Users with the administrator role can additionally manage the content of all tabs and manage the visibility of work submitted by users.

The application was built according to the REST architectural style. Authentication and authorisation is carried out using JWT.
All files uploaded by users are stored in dropbox

Link to frontend part: https://github.com/mat1911/cultural-center-frontend

## Configuration

For proper working, the application.properties file should be appropriately filled in.

Sample file content:

```properties
#DATABASE
spring.jpa.hibernate.ddl-auto=
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=

#JWT TOKENS
tokens.access.expiration-time-ms=
tokens.refresh.expiration-time-ms=
tokens.refresh.property=access-token-expiration-time
tokens.prefix=Bearer

#DROPBOX
dropbox.accesstoken=
dropbox.datafolder=

#EMAIL
spring.mail.host = smtp.gmail.com
spring.mail.port = 587
spring.mail.properties.mail.smtp.starttls.enable = true
spring.mail.properties.mail.smtp.starttls.required = true
spring.mail.properties.mail.smtp.auth = true
spring.mail.properties.mail.smtp.connectiontimeout = 5000
spring.mail.properties.mail.smtp.timeout = 5000
spring.mail.properties.mail.smtp.writetimeout = 5000
spring.mail.username = 
spring.mail.password = 
```

## Screenshots

News page

![News page](https://drive.google.com/uc?export=view&id=1Fk-9QMW5ZUff85M22BZRLeK--vJmxlOe)

<br />

Articles page

![Articles page](https://drive.google.com/uc?export=view&id=107TcMuNdW8_fgXV4TKNRtymsUxS782Nx)

<br />

Page to manage article visibility by admin

![Admin - articles](https://drive.google.com/uc?export=view&id=13xNW6btePUjZfR7mfMLFm56gTXiGgpwP)


<br />

Page to manage article visibility by admin

![Contact page](https://drive.google.com/uc?export=view&id=1_8s_DRaqN33i73EjgxyxxzBqk6cn2FEU)


<br />

User events page

![User events page](https://drive.google.com/uc?export=view&id=1GuDRnhX0vkBd4RSX9XcLc0IUKT1W5ScC)


<br />

Job offers page

![Job offers page](https://drive.google.com/uc?export=view&id=1NfTfSFwD98JgaABA5zN948g0E3hmqTMQ)
