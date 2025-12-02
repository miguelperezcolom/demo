-- *** 1. Creación del esquema (base de datos) ***
CREATE DATABASE IF NOT EXISTS mapeados;

-- *** 2. Creación del usuario de Debezium ***
-- (El '%' permite la conexión desde cualquier host, necesario dentro de K8s)
CREATE USER 'debezium_user'@'%' IDENTIFIED BY 'tu_contraseña_cdc_segura';

-- *** 3. Otorgar permisos esenciales para CDC ***
-- REPLICATION SLAVE y REPLICATION CLIENT: Necesarios para leer el Binlog.
-- SELECT: Necesario para la instantánea inicial (snapshot).
-- RELOAD: Necesario para gestionar el Binlog (rotación/cambios).
-- LOCK TABLES: Necesario para asegurar una instantánea inicial consistente.
-- BINLOG MONITOR: Un permiso específico de MariaDB/MySQL que fue necesario para monitorear el Binlog.
GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT, LOCK TABLES, BINLOG MONITOR ON *.* TO 'debezium_user'@'%';

-- Aplicar los cambios
FLUSH PRIVILEGES;

-- *** 4. Opcional: Cargar la estructura y los datos ***
-- El archivo db_mapeados_backup.sql se aplicará por separado, pero si tienes scripts de creación de tablas, agrégalos aquí.
USE mapeados;
-- Contenido de db_mapeados_backup.sql (CREATE TABLE, INSERT INTO) se aplicaría aquí.