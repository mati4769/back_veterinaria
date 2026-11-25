# Plan de Pruebas - Veterinaria San Marcos

## Objetivo

Validar el correcto funcionamiento del backend de la Veterinaria San Marcos mediante pruebas unitarias utilizando JUnit y generación de datos ficticios con DataFaker.

---

## Herramientas

- Java 17
- Spring Boot
- JUnit
- Mockito
- DataFaker

---

## Metodología

Cada prueba sigue el patrón **AAA**:

- **Arrange:** se preparan los datos con DataFaker y se simula el repositorio con Mockito.
- **Act:** se ejecuta el método que se quiere probar.
- **Assert:** se verifica el resultado con las aserciones de JUnit.

Las pruebas de los services no usan la base de datos: el repositorio se reemplaza por un objeto simulado (`@Mock`).

---

## Casos de Prueba

| ID | Clase | Caso de Prueba | Objetivo | Resultado Esperado | Tipo |
| ----- | -------------------- | ------------------------------ | ------------------------------------------------------------- | ------------------------------------------------- | -------- |
| CP-01 | ProductoServiceTest | Obtener todos los productos | Validar que el service entregue la lista de productos. | La lista tiene la misma cantidad de productos. | Unitaria |
| CP-02 | ProductoServiceTest | Buscar producto por ID | Validar la búsqueda de un producto existente. | El producto existe y tiene el mismo título. | Unitaria |
| CP-03 | ProductoServiceTest | Guardar producto | Validar que un producto nuevo se guarde. | El producto guardado no es nulo y mantiene su precio. | Unitaria |
| CP-04 | ProductoServiceTest | Actualizar producto existente | Validar que la actualización use el ID de la URL. | El producto actualizado queda con el ID indicado. | Unitaria |
| CP-05 | ProductoServiceTest | Actualizar producto que no existe | Validar el error al actualizar un ID inexistente. | Se lanza una excepción. | Unitaria |
| CP-06 | ProductoServiceTest | Eliminar producto | Validar que se elimine un producto existente. | Se llama a `deleteById` con el ID indicado. | Unitaria |
| CP-07 | ProductoServiceTest | Eliminar producto que no existe | Validar el error al eliminar un ID inexistente. | Se lanza una excepción. | Unitaria |
| CP-08 | AdminServiceTest | Login con credenciales correctas | Validar el inicio de sesión de un admin. | El login retorna verdadero. | Unitaria |
| CP-09 | AdminServiceTest | Login con contraseña incorrecta | Validar que se rechace una contraseña equivocada. | El login retorna falso. | Unitaria |
| CP-10 | AdminServiceTest | Login con correo que no existe | Validar que se rechace un correo no registrado. | El login retorna falso. | Unitaria |
| CP-11 | JwtUtilTest | Generar y leer token | Validar que el token JWT generado sea válido y guarde el rol. | El token es válido y contiene el mismo correo y el rol ADMIN. | Unitaria |
| CP-12 | JwtUtilTest | Token inventado no es válido | Validar que se rechace un token falso. | La validación retorna falso. | Unitaria |
| CP-13 | ClienteServiceTest | Login de cliente con credenciales correctas | Validar el inicio de sesión de un cliente (rol USER). | El login retorna verdadero. | Unitaria |
| CP-14 | ClienteServiceTest | Login de cliente con contraseña incorrecta | Validar que se rechace la contraseña equivocada de un cliente. | El login retorna falso. | Unitaria |

---

## Cambios de la 3ra evaluación (roles USER y ADMIN)

- El login ahora reconoce dos roles: **ADMIN** (usuarios de la tabla `admin`) y **USER** (usuarios de la tabla `cliente`). El token JWT guarda el rol.
- **CP-11 (modificada):** además del correo, revisa que el token guarde el rol `ADMIN`.
- **CP-13 y CP-14 (nuevas, `ClienteServiceTest`):** prueban el login de un cliente, que es el que entra con rol USER.
- **Total:** 14 pruebas unitarias + `contextLoads` = 15 pruebas.
