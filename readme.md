# Biblioteca

## Requirements

We have to develop an app where:

- **Users**
  - can log in and log out from their __sessions__.
  - can CRUD __personal collections__.
  - can CRUD books in their __personal desk__.
  - can Publish and UnPublish books into the __global catalogue__ from their __personal desk__.
- **Books**:
  - can be read by anyone.
  - They have Title, Writer, Abstract and Chapter list.
  - Users can give stars to their favourites books.
  - Users can leave comments in the book (as an entity).
- **Chapters**:
  - can be read by anyone.
  - They have title, writer, publication date and creation date.
  - They also have Writer Warnings, body of text and Writer Start and End Notes.
  - Users can leave comments in each chapter
- Global **Catalogue** of books.
  - Books in the global catalogue can be read for everyone, not only logged users.
- **Categories** of books.
  - Categories can have zero or one parent category.
  - Categories can have zero or more children category.
  - A book can be into one or more categories.
- There are several kinds of **tags**:
  - **Book-Tag**: Tags for Books and about the characters, locations, epoch...
  - **Reader-Tag**: Tags for Users as Readers, about their selected preferences.
  - **Writer-Tag**: Tags for Users as Writers, about their selected preferences

## Screen scope

- **Login**/SignUp Screen
- **HomePage**, catalogue and categories Screen
- **Book** Screen
- **Chapter** Screen
- User **Profile** Screen
- User **desk** screen

## Screen capabilities and requirements

### Login Screen
There is:
  Username input
  Password input
  login button

**Username** rules:
  - Can't have spaces
  - At least it must have 4 characters.
  - Can't have more than 16 characters.
  - Characters must be alphanumerics.
  - 