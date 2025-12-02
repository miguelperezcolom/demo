-- 1. Crea el usuario (usa una contraseña segura)
CREATE USER 'debezium_user'@'%' IDENTIFIED BY 'tu_contraseña_cdc_segura';

-- 2. Concede permisos de replicación (Crucial para leer el Binlog)
GRANT REPLICATION SLAVE ON *.* TO 'debezium_user'@'%';

-- 3. Concede permisos de selección (Para la lectura inicial de las tablas)
GRANT SELECT ON *.* TO 'debezium_user'@'%';

-- 4. Aplicar cambios
FLUSH PRIVILEGES;