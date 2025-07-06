-- Habilitar extensión para UUID en PostgreSQL
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabla de roles
CREATE TABLE roles (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL
);

-- Tabla principal de usuarios
CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  email VARCHAR(100) UNIQUE NOT NULL,
  username VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  enabled BOOLEAN DEFAULT TRUE,
  role_id INT NOT NULL REFERENCES roles(id)
);

-- Tabla de clientes (clientes finales del e-commerce)
CREATE TABLE clients (
  id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
  full_name VARCHAR(150) NOT NULL,
  phone VARCHAR(30),
  address TEXT,
  loyalty_points INT DEFAULT 0
);

-- Tabla de conductores
CREATE TABLE drivers (
  id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
  full_name VARCHAR(150) NOT NULL,
  license_number VARCHAR(50),
  vehicle_plate VARCHAR(20),
  available BOOLEAN DEFAULT TRUE,
  hire_date DATE
);

-- Tabla de encargados de inventario
CREATE TABLE inventory_managers (
  id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
  full_name VARCHAR(150) NOT NULL,
  assigned_warehouse_code VARCHAR(30),
  pending_audits INT DEFAULT 0,
  hire_date DATE
);

-- Tabla de gestores de venta
CREATE TABLE sales_managers (
  id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
  full_name VARCHAR(150) NOT NULL,
  region VARCHAR(50),
  goal_achieved BOOLEAN DEFAULT FALSE,
  hire_date DATE
);

-- Tabla de administradores
CREATE TABLE admins (
  id UUID PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
  full_name VARCHAR(150) NOT NULL,
  permissions TEXT,
  hire_date DATE
);

-- Insertar roles base
INSERT INTO roles (name) VALUES
  ('CLIENT'),
  ('DRIVER'),
  ('INVENTORY_MANAGER'),
  ('SALES_MANAGER'),
  ('ADMIN');
