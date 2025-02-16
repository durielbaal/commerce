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

## 3.- Base de datos
Este proyecto utiliza una base de datos H2 en memoria, junto con Flyway para gestionar las migraciones y crear automáticamente la estructura de la base de datos al iniciar la aplicación.

Para simplificar el desarrollo, se ha optado por utilizar la misma base de datos tanto en la ejecución de la aplicación como en los tests. No obstante, soy consciente de que lo ideal sería emplear Testcontainers para la ejecución de pruebas en un entorno más realista, pero he priorizado la agilidad en la implementación.

Estructura de la Base de Datos
El esquema de la base de datos consta de dos tablas principales:

User: Contiene los datos de los usuarios y está relacionada con la gestión de seguridad.

Price: Representa las tarifas y es el núcleo de la prueba técnica.

Consideraciones sobre el Diseño de la Base de Datos
Desde un punto de vista estrictamente relacional, la tabla Price no debería ser una entidad independiente, sino una tabla intermedia que relacione múltiples entidades.

El diseño más correcto implicaría la existencia de las siguientes tablas:

Brand: Representa la marca asociada a la tarifa.

PriceListProduct: Define la lista de precios y los productos involucrados.

Curr: Contiene información sobre la moneda utilizada.

La tabla Price debería actuar como una tabla de unión entre estas entidades, almacenando las relaciones adecuadas.

Sin embargo, para agilizar el desarrollo de la prueba técnica, se ha implementado Price como una entidad independiente en lugar de una tabla intermedia.

El esquema de la base de datos se encuentra en:
📂 main/resources/db/migration/V1__create_table.sql

## 4.- SeguridadCobertura
Gestión de Resiliencia con Resilience4j

Este proyecto implementa Resilience4j para mejorar la resiliencia y estabilidad del sistema mediante el uso de Circuit Breaker y Rate Limiter en los servicios críticos.

Circuit Breaker

El Circuit Breaker protege el sistema de fallos repetitivos en servicios externos, evitando la degradación del rendimiento. Su funcionamiento sigue tres estados principales:

Cerrado (Closed): Las llamadas se ejecutan normalmente hasta que se detectan fallos repetitivos.

Abierto (Open): Si el número de fallos supera un umbral, las llamadas se bloquean temporalmente para evitar sobrecarga.

Semiabierto (Half-Open): Se permiten algunas llamadas para verificar si el servicio se ha recuperado antes de volver al estado cerrado.

En este proyecto, el Circuit Breaker está configurado para los servicios de cada dominio(los useCaseImpl), con los siguientes parámetros:

Si el 50% de las llamadas fallan, el circuito se abre.

Se considera una llamada lenta si supera los 2 segundos.

En estado Half-Open, solo se permiten 2 llamadas antes de decidir si se cierra el circuito.

Se espera 5 segundos antes de cerrar el circuito en estado Half-Open.

El circuito analiza un total de 10 llamadas para determinar su estado.

Se requieren al menos 5 llamadas antes de evaluar si el servicio está fallando.

Cuando el circuito está abierto, permanece así por 10 segundos antes de intentar reabrirse.
La transición de Open a Half-Open es automática.

Si el circuito está abierto por saturación de llamadas, 
el método fallbackPrice/fallbackUser, se activa para evitar un impacto en la estabilidad del sistema.

Rate Limiter
   
El Rate Limiter restringe la cantidad de solicitudes permitidas en un intervalo de tiempo para evitar sobrecargas en el sistema.

En este proyecto, el Rate Limiter para priceService está configurado con los siguientes parámetros:

Se permiten 5 solicitudes en un período de 10 segundos.

Una vez alcanzado el límite, las solicitudes adicionales deben esperar 1 segundo antes de volver a intentarlo.

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
