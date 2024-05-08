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

1. Creación de nuevo usuario.

| Consideraciones aplicadas en este proceso |
|--- |
| Validación de correo existente, se lanza excepción en caso de encontrarse registrado previamente. |
| Validación de correo con formato correcto mediante el uso de una expresión regular, esta expresión regular es configurable mediante el archivo application.properties con nombre: user.nisum.email.regex . |
| Validación de password con formato correcto mediante el uso de una expresión regular, esta expresión regular es configurable mediante el archivo application.properties con nombre: user.nisum.password.regex , el valor lo obtiene de una variable de entorno (user.nisum.env.password.regex) especificada en el despliegue del proyecto. |
| Generación de token con JWT (JSON Web Token). |
| Pruebas unitarias con junit. |

2. Listar usuarios existentes en la base de datos.

| Consideraciones aplicadas en este proceso |
|--- |
| Se utiliza el token JWT generado en la creación de usuario. |
| El token debe ser válido y no estar expirado, el token tiene una duración de un minuto. |

### Diagrama de la solución

![DIAGRAMA_v2](https://github.com/jbozada/userRegister/assets/12485654/d2f468a4-73fc-476d-8183-e9f3fe28b57e)


## Ejecución de proyecto en sistema operativo Linux (Se requiere maven 3.6.3 y java 11 instalados y seteados)

- clonar el proyecto en el directorio local que deseen:

```git clone https://github.com/jbozada/userRegister.git ```

- Ingresar al directorio del proyecto clonado.

```cd userRegister/ ```

- Generar jar del proyecto

```
mvn clean && mvn install -DskipTests
```

NOTA: En caso de querer levantar el proyecto con la propiedad con valor de variable de entorno, debemos modificar el application.properties descomentar linea correspondiente y ejecutar el comando de esta forma:

```
mvn clean && mvn install -DskipTests -Duser.nisum.env.password.regex="^(?=.*\d)(?=.*[a-zA-Z]).{7,}$"
```

- Levantar el proyecto con Maven, debe estar libre el puerto 8080.

```
mvn spring-boot:run
```

NOTA: En caso de querer levantar el proyecto con la propiedad con valor de variable de entorno, debemos modificar el application.properties descomentar linea correspondiente y ejecutar el comando de esta forma:

```
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Duser.nisum.env.password.regex=^(?=.*\d)(?=.*[a-zA-Z]).{7,}$"
```

## Pruebas del proyecto

Cuando el proyecto este ejecutándose se puede acceder por el siguiente link a los servicios REST que expone el proyecto:
[Swagger-UI](http://localhost:8080/swagger-ui/index.html)

Aquí veremos los 2 enpoints generados para esta solución y podemos ejecutarlos siguiendo las  instrucciones detalladas a continuación:

1.- Ingresar al link del swagger:

![1](https://github.com/jbozada/userRegister/assets/12485654/80dd67c9-0d2e-4ab1-829b-b25443727ea1)

2.- Desplegar la pestaña "user-register-controller", allí veremos los 2 endpoints publicados:

![2](https://github.com/jbozada/userRegister/assets/12485654/8da912e8-7e91-40df-82f4-079c24274571)

3.- Por ejemplo, para probar la creación de un nuevo usuario desplegamos la pestaña que dice "POST /userRegister/add Add user" y luego dar click en el botón "Try it out":

![3](https://github.com/jbozada/userRegister/assets/12485654/89b67d41-54dd-4e68-965d-f429b97d8234)

4.- Luego podemos modificar el request cargado por defecto o ejecutarlo directamente mediante el botón "Execute", luego de esto nos mostrará la respuesta del servicio:

![4](https://github.com/jbozada/userRegister/assets/12485654/c9f76754-ed97-4fda-be18-80b3b27b3cef)

![5](https://github.com/jbozada/userRegister/assets/12485654/25612749-06e4-4c24-a407-b1b3f3cfa593)

5.- Se puede modificar el request del servicio, ejecutar nuevamente y validar todas las restricciones implementadas en este servicio (usuario existente, password incorrecta, correo inválido).

![6](https://github.com/jbozada/userRegister/assets/12485654/29ee6582-16a4-433e-80af-fb16eddd5efb)

6.- Para probar el servicio de busqueda de usuarios solo debe realizar estos pasos mencionados anteriormente sobre la pestaña que dice "GET /userRegister/getAll Get all the users".
