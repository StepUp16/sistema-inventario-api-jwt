# sistema-inventario-api-jwt
# 📦 Spring Boot Inventory API - Secured with JWT

![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-MariaDB-003545?style=for-the-badge&logo=mysql)
![AWS RDS](https://img.shields.io/badge/AWS-RDS-232F3E?style=for-the-badge&logo=amazon-aws)
![JWT](https://img.shields.io/badge/Security-JWT-black?style=for-the-badge&logo=json-web-tokens)

API RESTful robusta construida con **Spring Boot** para la gestión de inventario (Productos y Usuarios). Implementa seguridad avanzada mediante **JWT (JSON Web Tokens)** y está configurada para desplegarse conectada a una base de datos en la nube **AWS RDS**.

---

## 🚀 Características Principales

* 🔐 **Autenticación Segura:** Login y Registro de usuarios con encriptación de contraseñas.
* 🛡️ **Autorización JWT:** Protección de rutas mediante *Bearer Tokens*.
* ☁️ **Cloud Ready:** Configuración lista para conectar con **AWS RDS (MariaDB/MySQL)**.
* 📦 **CRUD Completo:** Gestión total de productos (Crear, Leer, Actualizar, Eliminar).
* 📱 **Mobile Backend:** Diseñado para ser consumido por clientes Android (Retrofit).

---

## 🛠️ Configuración del Proyecto

### 1. Propiedades (`application.properties`)

Para conectar el proyecto a la nube, configura `src/main/resources/application.properties`.

> ⚠️ **IMPORTANTE:** Reemplaza los valores con tus credenciales reales de AWS.

```properties
# Configuración del Servidor
server.port=8085
server.servlet.context-path=/api/v1/demoapirestdam235

# Conexión a Base de Datos (AWS RDS / Local)
spring.datasource.url=jdbc:mariadb://<TU_ENDPOINT_AWS>:3306/api_db?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=admin
spring.datasource.password=<TU_PASSWORD_SECRETO>

# Driver y Dialecto
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect

# JPA - Actualización automática de tablas
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
