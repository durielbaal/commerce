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
En esta sección, proporcionamos una descripción general de la funcionalidad de la aplicación sin entrar en detalles profundos, 
los cuales se abordarán en las secciones siguientes.

## 2.- arquitectura
Este proyecto sigue el patrón de Arquitectura Hexagonal, también conocida como Arquitectura de Puertos y Adaptadores. 
Este patrón busca desacoplar la lógica de negocio (el dominio) de los detalles de implementación, como el acceso a la base de datos, los servicios externos y las interfaces de usuario. 
El objetivo es crear un sistema flexible, escalable y fácil de mantener, permitiendo que los cambios en los detalles de implementación no afecten la lógica de negocio central.

Estructura y Dominios

El proyecto está estructurado en varios dominios que representan áreas de negocio diferentes.
Cada dominio sigue de forma estricta la arquitectura hexagonal dentro de su propio contexto,
lo que permite una independencia clara entre ellos.

Dominio api:

Este dominio contiene todos los datos y funcionalidades relacionadas con una entidad de negocio específica, como "Inditex". En este caso, tenemos:
Price: Gestión de precios de productos.

User: Gestión de usuarios, su autenticación y autorización.

El dominio api agrupa todos los datos y servicios corelacionados con una entidad o negocio específico.

Por ejemplo, si consideramos que "Inditex" es una marca o un cliente, todos los servicios relacionados con Inditex estarán dentro de este dominio.
Esto incluye modelos de datos, casos de uso, puertos de entrada y salida, y la lógica de negocio que maneja los precios, usuarios, etc.
 
Dominio cosentino:

De manera similar a api, podemos tener otro dominio como cosentino, que está relacionado con otro negocio o cliente, en este caso Cosentino.
Dentro de este dominio, tendríamos:

Materiales: Gestión de materiales.

Empleado: Gestión de los empleados dentro de Cosentino.

Explotaciones: Gestión de explotaciones o proyectos asociados.

Cada dominio, ya sea api, cosentino o cualquier otro, es independiente y contiene los datos y la lógica de negocio específica. 
Este enfoque permite que cada dominio funcione de manera autónoma, pero se pueda integrar con otros dominios cuando sea necesario.

Estructura del Proyecto
Dominio (api, cosentino, etc.): Contiene los modelos, casos de uso y puertos específicos de cada negocio.

Modelos: Representan las entidades principales de cada dominio, como Price, User, Material, Empleado.

Casos de Uso: Contienen la lógica de negocio, como la obtención de precios o la gestión de usuarios.

Puertos de Entrada y Salida: Son interfaces que definen cómo interactuar con la lógica de negocio de manera desacoplada, tanto hacia afuera como hacia adentro.

Configuration: Contiene todas las configuraciones necesarias para el correcto funcionamiento del sistema, como configuraciones de seguridad, bases de datos, conectores con servicios externos, etc.

Shared: Contiene elementos comunes que pueden ser utilizados por todos los dominios, como utilidades, excepciones, configuraciones globales o clases compartidas entre múltiples dominios.

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
}

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
