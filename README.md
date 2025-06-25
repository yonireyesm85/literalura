# LiterAlura 📖

Proyecto del challenge de Java + Spring Boot en Alura Latam.  
LiterAlura es una API REST para gestionar una biblioteca virtual de libros, utilizando datos de la API pública [Gutendex](https://gutendex.com/).

---

## 🚀 Tecnologías Utilizadas

- ✅ Java 17
- ✅ Spring Boot 3.2.3
- ✅ Maven 4+
- ✅ PostgreSQL 16+
- ✅ Spring Data JPA
- ✅ API externa: Gutendex
- ✅ IntelliJ IDEA

---

## 🧠 Objetivo del Proyecto

- Consumir libros desde una API externa (Gutendex)
- Guardar y gestionar libros, autores y categorías
- Filtrar autores vivos por año de nacimiento
- Exponer endpoints RESTful organizados con DTOs
- Practicar principios de clean code, capas de servicio y buenas prácticas en Java

---

## 🏗️ Arquitectura del Proyecto

```
literAlura/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.yhoni.literalura/
│   │   │       ├── controller/
│   │   │       ├── dto/
│   │   │       ├── model/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── LiterAluraApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── schema.sql (opcional)
├── pom.xml
└── README.md
```

---

## 🔗 API Gutendex

- URL base: `https://gutendex.com/books/`
- Parámetros utilizados:
  - `search` para buscar libros por título
- Datos mapeados:
  - Título del libro
  - Autor(es)
  - Año de nacimiento/muerte del autor
  - Idioma
  - Número de descargas

---

## 📦 Endpoints Principales

| Método | Ruta                  | Descripción                          |
|--------|-----------------------|--------------------------------------|
| `GET`  | `/libros`             | Lista todos los libros               |
| `POST` | `/libros`             | Guarda un nuevo libro desde Gutendex |
| `GET`  | `/autores`            | Lista todos los autores              |
| `GET`  | `/autores/vivos`      | Filtra autores vivos por año         |
| `GET`  | `/categorias`         | Lista todas las categorías           |

---

## 🧪 Vista del Menú en Consola

![Menú Literalura](menu_literalura.jpg)

---

## ⚙️ Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/tuusuario/literalura.git
cd literalura
```

### 2. Configurar base de datos PostgreSQL

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

---

## ✅ TODOs y mejoras futuras

- [x] Integración con API Gutendex
- [x] Persistencia con JPA
- [x] DTOs para control de respuestas
- [x] Filtros por autor vivo
- [ ] Validación de entrada
- [ ] Documentación Swagger
- [ ] Pruebas unitarias y de integración

---

## ✍️ Autor

Desarrollado con pasión por **Yhoni** ⚔️  
Challenge realizado como parte del programa **Alura Latam - Oracle ONE**

---

## 🧙‍♂️ Frase del Sensei

> *“Un buen código se lee como un buen libro: claro, con propósito y con historia.”*

---

<img src="bender el tremendo.png" alt="Yhoni - dev" width="40"/> **Yhoni - dev**
