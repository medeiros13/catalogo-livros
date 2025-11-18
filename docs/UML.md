# 📐 UML – Catálogo de Livros

## 📦 Diagrama de Classes

Book
- id: Long
- title: String
- author: String
- publishYear: Integer
- genreId: Long

Genre
- id: Long
- name: String

BookDao
+ findAll()
+ findById(id)
+ insert(book)
+ update(book)
+ delete(id)

GenreDao
+ findAll()

BookListServlet
BookCreateServlet
BookEditServlet
BookDeleteServlet
BookSearchServlet