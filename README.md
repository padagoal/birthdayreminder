# Birthday Reminder Program

This is a Java program that sends birthday greetings via SMS or email to a list of contacts stored in a SQLite database or CSV file. It also sends reminders to other contacts in the database about the birthdays.

## Features

- Sends personalized greetings to contacts on their birthdays
- Handles leap year birthdays correctly by sending greetings on February 28th instead of February 29th
- Sends reminders to other contacts in the database about the birthdays
- Can read contact data from either a SQLite database or a CSV file
- Supports configuration of SMTP settings for sending email greetings
- Uses log4j for logging information and errors

## Dependencies

- Java 8 or higher
- Maven
- SQLite JDBC driver (for SQLite database usage)
- OpenCSV (for CSV file usage)
- Apache Commons Email (for email sending)
- log4j (for logging)

## Installation and Usage

1. Clone the repository to your local machine.
2. Install the above dependencies using Maven.
3. Modify the configuration file `mail.properties` with your SMTP and/or Twilio account information.
4. Add your contact data to either a SQLite database or a CSV file using the appropriate schema, in the main folder `./friends.db` or `./friends.csv`
5. Run first on the directory `mvn compile`
6. Then run the program using the command `mvn exec:java -Dexec.mainClass="com.jobsity.birthdayreminder.BirthdayReminder"`

## Configuration

The `mail.properties` file contains the following properties:

- `mail.smtp.host`: The SMTP host to use for sending email greetings.
- `mail.smtp.socketFactory.port`: The SMTP port to use for sending email greetings.
- `mail.smtp.port`: The SMTP port to use for sending email greetings.
- `mail.user`: The username to use for authenticating with the SMTP server.
- `mail.password`: The password to use for authenticating with the SMTP server.
- `mail.from`: The email address to use as the sender for email greetings.

## Contact Data

The program can read contact data from either a SQLite database or a CSV file. The contact data must include the following fields in the correct order:
For the SQLite, the table must be name `friends`

- `id`: The unique identifier for the contact.
- `first_name`: The contact's first name.
- `last_name`: The contact's last name.
- `date_of_birth`: The contact's birthdate in the format yyyy/MM/dd.
- `email`: The contact's email address.
- `phone`: The contact's phone number.


## Logging

The program uses log4j for logging information and errors. The logs are written to a file named `birthday-reminder.log` in the project directory.



