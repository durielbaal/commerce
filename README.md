Readme

CASO DE USO DE LA APLICACIÓN:
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