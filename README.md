Readme

1. [Introducción](#introducción)
2. [Arquitectura](#arquitectura)
3. [Diseño de base de datos](#baseDeDatos)
4. [Seguridad y cobertura de la aplicación](#SeguridadCobertura)
5. [Instalación](#instalación)
6. [Uso](#uso)
7. [Contribuciones](#contribuciones)
8. [Licencia](#licencia)

## 1.-Introducción
Este proyecto permite obtener información detallada sobre las tarifas disponibles en fechas específicas. 
Para acceder a estos datos, es necesario iniciar sesión previamente en la aplicación.
En esta sección, proporcionamos una descripción general de la funcionalidad de la aplicación sin entrar
en detalles profundos, los cuales se abordarán en las secciones siguientes.

## 2.- arquitectura

## 3.- baseDeDatos

## 4.- SeguridadCobertura

## 5.- instalación

## 6.- uso
Debes ejecutar la aplicación en este orden:
1.-http://localhost:8080/auth/security/login
Body:
puedes usar cualquiera de estos 2:
{
"username":"admin",
"password":"admin"
},
{
"username":"user",
"password":"user"
}
Authorization: Sin autorización.
Al ejecutar te generará un token para el siguiente endpoint,
que es el que nos interesa.

2.-http://localhost:8080/price/get/bargains
Body:
{
"brandId": 1,
"productId": 35455,
"certainDate": "2020-06-14T16:00:00"
}
Authorization: Bearer Token e introduces el token generado en el
primer token.

Al ejecutar, te devolverá los datos correspondientes.

## 7.- contribuciones

## 8.- licencia
