# Library Management System - Project Overview
This project is a Library Management System designed to automate and streamline the operations of a library. It handles books, authors, publishers, categories, users, and related services such as checkouts, reservations, and fines. The system provides a robust backend powered by Spring Boot, offering CRUD operations and RESTful APIs for managing the various entities and their relationships.

## Features
1. **Library Management**: Manage library branches, including their addresses, contact details, and associated books.

2. **Book Management**: Handle books with details like title, ISBN, published date, available copies, and total copies. Books are linked to authors, publishers, categories, and the library they belong to.

3. **Author & Publisher Management**: Maintain author and publisher information. Books are associated with both to keep track of their respective details.

4. **Checkout System**: Manage the lending and returning of books, with fields for checkout date, due date, return date, and status (borrowed, returned, overdue).

5. **Reservation System**: Users can reserve books with statuses for pending, fulfilled, or canceled reservations.

6. **Fine System**: Track overdue fines for users, including fine amount, payment status, and related checkouts.

7. **User Management**: Handle library users, with roles like admin, librarian, and member, providing access control and functionality depending on their role.

## Entities
The system includes the following core entities:

- **Library**: Represents different library branches.
- **Book**: Contains information about books available in the library.
- **Author**: Stores information about book authors.
- **Publisher**: Stores publisher information for books.
- **Category**: Classifies books under various categories.
- **Checkout**: Manages book loans, returns, and their statuses.
- **Reservation**: Handles book reservations for users.
- **Fine**: Tracks fines for overdue checkouts.
- **User**: Represents system users with roles like admin, librarian, and member.

## User Roles
- **Admin**: Has complete access to manage libraries, books, users, and other operations.
- **Librarian**: Can manage books, checkouts, reservations, and fines.
- **Member**: Can view available books, check out, return, and reserve books, as well as view and pay fines.

## Technical Specifications
- **Backend**: Spring Boot for backend services with REST APIs for managing all entities and relationships.
- **Database**: Supports relational databases, with MySQL as the default.
- **APIs**: RESTful APIs for communication between the backend and any frontend or third-party system.
- **Frontend Integration**: Can be integrated with Angular, React, or other frontend frameworks for a complete user interface.

## Conclusion
This Library Management System is designed to automate and simplify library operations. By leveraging Spring Boot, it provides an extendable and scalable solution that can be adapted to libraries of any size.
