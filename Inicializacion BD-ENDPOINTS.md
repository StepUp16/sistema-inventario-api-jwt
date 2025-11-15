# 📘 README – Inicialización de Base de Datos y Endpoints API

## 2. Script SQL de Inicialización

Ejecuta este script en tu gestor de base de datos (**HeidiSQL, DBeaver, MySQL Workbench**) para crear la base, tablas, corregir auto-incrementos e insertar datos semilla.

```sql
-- 1. Crear la Base de Datos
CREATE DATABASE IF NOT EXISTS api_db;
USE api_db;

-- 2. Crear Tabla de Usuarios
CREATE TABLE user (
  id int(11) NOT NULL,
  name varchar(256) NOT NULL,
  last_name varchar(256) NOT NULL,
  email varchar(128) NOT NULL,
  password varchar(256) NOT NULL,
  active bit(1) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE utf8mb4_general_ci;

-- 3. Crear Tabla de Productos
CREATE TABLE product(
  code int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  status bit(1) DEFAULT NULL,
  PRIMARY KEY (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4_general_ci;

-- 4. FIX IMPORTANTE: Aplicar Auto-Incremento a Usuarios
ALTER TABLE user MODIFY id INT(11) NOT NULL AUTO_INCREMENT;

-- 5. Datos de prueba (Productos)
INSERT INTO product VALUES 
(1,'Mochila Adidas', 1), 
(2, 'USB 2.0 Kingston', 1);

-- 6. Datos de prueba (Usuario Manual)
-- Password original: Temp123 (hasheado)
INSERT INTO user VALUES
(1,'Juan','Pérez','juan.perez@example.com','{bcrypt}$2a$10$uvWNfki.WxQhUD6e51di0uvC9G0DiGly7/PvyLR0Ay0mpFV24YkLG', 1);

#📡 Endpoints de la API

--Base URL:
http://localhost:8085/api/v1/demoapirestdam235

## 👤 Autenticación (Público)

| Método | Endpoint         | Descripción             | Body |
|--------|------------------|-------------------------|------|
| POST   | /auth/register   | Registrar nuevo usuario | JSON |
| POST   | /auth/login      | Iniciar sesión          | JSON |

---

## 📦 Productos (Privado)

🔒 **Requiere Header:**


| Método | Endpoint           | Descripción           | Body |
|--------|--------------------|-----------------------|------|
| GET    | /products          | Listar productos      | N/A  |
| POST   | /products          | Crear producto        | JSON |
| PUT    | /products          | Actualizar producto   | JSON |
| DELETE | /products/{id}     | Eliminar producto     | N/A  |

---

## 🧪 Ejemplos de Uso (Postman)

### 1. Registrar Usuario
**POST** `/auth/register`

```json
{
    "name": "Juan",
    "lastName": "Pérez",
    "email": "juan.perez@example.com",
    "password": "12345"
}
