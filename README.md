# Sistema de Gestión de Biblioteca

## Información del estudiante
- **Nombre:** Glenis Lizeth Ulloa Ortega
- **Número de cuenta:** 251012161
- **Asignatura:** Programación Orientada a Objetos
- **Proyecto:** Sistema de Gestión de Biblioteca

## 1. Descripción del proyecto
Este proyecto consiste en desarrollar un sistema de gestión de biblioteca utilizando Java, Maven, JDBC y SQLite.
El sistema permite administrar dos entidades relacionadas: autores y libros. Un autor puede tener varios libros y cada libro pertenece a un solo autor.
Se implementa una arquitectura por capas para separar el modelo, acceso a datos, servicios y presentación.

## 2. Tecnologías utilizadas
- Java 17
- Apache NetBeans
- Maven
- SQLite
- JDBC
- Git y GitHub

## 3. Organización del proyecto
El proyecto está organizado en los siguientes paquetes:
- `hn.uth.poo.biblioteca.modelo`: contiene las clases Autor y Libro.
- `hn.uth.poo.biblioteca.excepciones`: contiene DatosException y DatoInvalidoException.
- `hn.uth.poo.biblioteca.dao`: contiene Conexion y las interfaces DAO.
- `hn.uth.poo.biblioteca.dao.impl`: contiene AutorDAOImpl y LibroDAOImpl.
- `hn.uth.poo.biblioteca.servicio`: contiene ServicioAutores y ServicioLibros.
- `hn.uth.poo.biblioteca.app`: contiene la clase Main y el menú de consola.

## 4. Base de datos
La aplicación utiliza SQLite mediante JDBC.
La base de datos se llama `biblioteca.db` y contiene dos tablas:

**Tabla autores**
- id
- nombre
- nacionalidad

**Tabla libros**
- id
- isbn
- titulo
- anio
- precio
- autor_id

La columna `autor_id` es una clave foránea que referencia a `autores(id)`.
El archivo `biblioteca.sql` contiene el script de creación de las tablas.
La conexión activa las claves foráneas mediante `PRAGMA foreign_keys = ON`.

## 5. Funcionalidades

### Gestión de autores
- Crear autor.
- Listar autores.
- Buscar autor por ID.
- Actualizar autor.
- Eliminar autor.

### Gestión de libros
- Crear libro.
- Listar libros.
- Buscar libro por ID.
- Actualizar libro.
- Eliminar libro.
- Listar libros por autor.

## 6. Reglas de negocio
El sistema implementa las siguientes validaciones:
1. El nombre del autor es obligatorio.
2. El título del libro es obligatorio.
3. El ISBN no puede repetirse.
4. El año de publicación debe estar entre 1450 y el año actual.
5. El precio debe ser mayor o igual a cero.
6. El autor debe existir antes de registrar un libro.
7. No se puede eliminar un autor que tenga libros registrados.

Las reglas se implementan en la capa de servicio mediante excepciones propias.
## 9. Repositorio

Proyecto desarrollado como práctica académica de programación orientada a objetos, utilizando interfaces DAO, implementación con `implements`, persistencia SQLite mediante JDBC y separación por capas.
