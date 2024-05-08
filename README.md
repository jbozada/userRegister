# Ejercicio técnico Java - Nisum Latam
### Autor: Jesús Alberto Bozada Plúa

### Tecnologías utilizadas en el desarrollo del proyecto

- Sistema operativo Linux
- [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3.6.3](https://maven.apache.org/docs/3.6.3/release-notes.html)
- [Git](https://git-scm.com/downloads)
- Spring Boot
- JPA
- Hibernate
- H2
- Maven
- Autenticación con Token JWT
- Pruebas Unitarias con Junit

### Funcionalidades desarrolladas

1. Creación de nuevo usuario mediante el endpoint POST: http://localhost:8080/userRegister/add

Request body:

```
{
  "name": "Juan Rodriguezz",
  "email": "juan@rodriguez.org",
  "password": "hunter2",
  "phones": [
    {
      "number": "1234567",
      "citycode": "1",
      "countrycode": "57"
    }
  ]
}
```

| Consideraciones aplicadas en este proceso |
|--- |
| Validación de correo existente, se lanza excepción en caso de encontrarse registrado previamente.|
| Validación de correo con formato correcto mediante el uso de una expresión regular, esta expresión regular es configurable mediante el archivo application.properties con nombre: user.nisum.email.regex . |
| Validación de password con formato correcto mediante el uso de una expresión regular, esta expresión regular es configurable mediante el archivo application.properties con nombre: user.nisum.password.regex , el valor lo obtiene de una variable de entorno (user.nisum.env.password.regex) especificada en el despliegue del proyecto. |
| Generación de token con JWT (JSON Web Token) |
| Pruebas unitarias con junit |

2. Listar usuarios existentes en la base de datos mediante el endpoint GET: http://localhost:8080/userRegister/getAll

Request param:

```
name: token , value: eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6Imp1YW5Acm9kcmlndWV6Lm9yZ3MiLCJpYXQiOjE3MTUxMTc3NjcsImV4cCI6MTcxNTExNzgyN30.S2ZaGoxR-G5caODXeVt1x7U_0uTWBUsHXlHdX__zcwo
```


### Diagrama de la solución

![DIAGRAMA_v2](https://github.com/jbozada/userRegister/assets/12485654/8b3da46e-484b-41ac-80c7-19caedb916fa)

## Ejecución de proyecto en sistema operativo Linux (Se requiere maven 3.6.3 y java 11 instalados y seteados)

- clonar el proyecto en el directorio local que deseen: ```$ git clone https://github.com/jbozada/userRegister.git ```

- Ingresar al directorio del proyecto clonado.

  ```$ cd  userRegister/ ```

- Generar jar del proyecto

```
$ mvn clean && mvn install -DskipTests -Duser.nisum.env.password.regex="^(?=.*\d)(?=.*[a-zA-Z]).{7,}$"
```

- Levantar el proyecto con Maven, debe estar libre el puerto 8080.

```
$ mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Duser.nisum.env.password.regex=^(?=.*\d)(?=.*[a-zA-Z]).{7,}$"
```


## Pruebas del proyecto

Cuando el proyecto este ejecutándose se puede acceder por el siguiente link a los servicios REST que expone el proyecto:
[Swagger-UI](http://localhost:8080/swagger-ui/index.html)

Aquí veremos los 2 enpoints generados para esta solución y podemos ejecutarlos siguiendo las  instrucciones detalladas a continuación:

1.- Ingresar al link del swagger:

![1](https://github.com/jbozada/userRegister/assets/12485654/a7c7428b-a478-4380-a847-ba1173cf1d06)

2.- Desplegar la pestaña "user-register-controller", allí veremos los 2 endpoints publicados:

![2](https://github.com/jbozada/userRegister/assets/12485654/d5d1c3bc-d7b2-4d7e-bf3f-24457bca63e5)

3.- Por ejemplo, para probar la creación de un nuevo usuario desplegamos la pestaña que dice "POST /userRegister/add Add user" y luego dar click en el botón "Try it out":

![3](https://github.com/jbozada/userRegister/assets/12485654/3061f28c-bbac-4dbd-b55f-7ae215f850ec)

4.- Luego podemos modificar el request cargado por defecto o ejecutarlo directamente mediante el botón "Execute", luego de esto nos mostrará la respuesta del servicio:

![4](https://github.com/jbozada/userRegister/assets/12485654/9368ee61-e952-4f0a-b29a-1419557c8a6b)

![5](https://github.com/jbozada/userRegister/assets/12485654/0e8c8e20-e9a9-482b-ad24-c99629836a66)

5.- Se puede modificar el request del servicio, ejecutar nuevamente y validar todas las restricciones implementadas en este servicio (usuario existente, password incorrecta, correo inválido).

![6](https://github.com/jbozada/userRegister/assets/12485654/25700096-65cd-4dc0-a9a0-e4389eeb4691)

6.- Para probar el servicio de busqueda de usuarios solo debe realizar estos pasos mencionados anteriormente sobre la pestaña que dice "GET /userRegister/getAll Get all the users".
