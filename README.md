# Java API con Javalin, Guice y RxJava

Este proyecto implementa una API REST usando:
- **Javalin**: Framework web ligero
- **Google Guice**: Inyección de dependencias
- **RxJava**: Programación reactiva y asíncrona
- **Virtual Threads**: Para mejor rendimiento
- **Mockito & JUnit**: Testing completo

## Arquitectura

```
Controller (Javalin) → Service (RxJava) → Client (RxJava) → External API
```

### Flujo de datos:
1. **UsersController**: Maneja requests HTTP y usa RxJava para operaciones asíncronas
2. **UsersService**: Lógica de negocio con transformación de datos usando RxJava
3. **UsersClient**: Simula llamadas a API externa con RxJava
4. **Guice**: Inyecta todas las dependencias automáticamente
5. **RxJavalin**: Wrapper transparente que aplica RxHttpHandler.auto automáticamente

## Endpoints disponibles

- `GET /users/{id}` - Obtener usuario por ID
- `GET /users/username/{username}` - Obtener usuario por username

## Ejecutar la aplicación

### Opción 1: Con Maven (si está instalado)
```bash
mvn compile exec:java -Dexec.mainClass="org.iskaypet.Application"
```

### Opción 2: Con Java directamente
```bash
# Compilar
javac -cp "target/classes:$(find ~/.m2/repository -name "*.jar" | tr '\n' ':')" src/main/java/org/iskaypet/*.java src/main/java/org/iskaypet/*/*.java

# Ejecutar
java -cp "target/classes:$(find ~/.m2/repository -name "*.jar" | tr '\n' ':')" org.iskaypet.Application
```

### Opción 3: Con IDE
Ejecutar directamente la clase `Application.java`

## Testing

### Ejecutar todos los tests
```bash
mvn test
```

### Tests incluidos:

#### **Unit Tests:**
- `UsersClientImplTest`: Tests del cliente que simula llamadas a API externa
- `UsersServiceImplTest`: Tests del servicio con mocks del cliente
- `UsersControllerTest`: Tests del controller con mocks del servicio
- `RxHttpHandlerTest`: Tests del handler HTTP genérico
- `RxJavalinTest`: Tests del wrapper de Javalin

#### **Integration Tests:**
- `UserFlowIntegrationTest`: Tests de flujo completo con Guice

### Cobertura de tests:
- ✅ **Controllers**: Tests con mocks de servicios
- ✅ **Services**: Tests con mocks de clients
- ✅ **Clients**: Tests de implementación real
- ✅ **Utils**: Tests de RxHttpHandler y RxJavalin
- ✅ **Integration**: Tests de flujo completo con DI
- ✅ **Error Handling**: Tests de manejo de errores
- ✅ **RxJava**: Tests de Observables y operadores

## Características

- ✅ **Virtual Threads**: Mejor rendimiento para operaciones I/O
- ✅ **RxJava**: Programación reactiva y asíncrona
- ✅ **Guice**: Inyección de dependencias
- ✅ **Javalin**: Framework web moderno y ligero
- ✅ **Error Handling**: Manejo robusto de errores con RxJava
- ✅ **Testing**: Cobertura completa con Mockito y JUnit
- ✅ **RxJavalin**: Transparencia automática para endpoints Rx

## Ejemplo de uso

```bash
# Obtener usuario por ID
curl http://localhost:8080/users/1

# Obtener usuario por username
curl http://localhost:8080/users/username/johndoe
```

## Respuesta esperada

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "username": "johndoe"
}
```

## Estructura del proyecto

```
src/
├── main/java/org/iskaypet/
│   ├── clients/           # Clientes para APIs externas
│   ├── controllers/       # Controladores HTTP
│   ├── dto/              # Data Transfer Objects
│   ├── services/          # Lógica de negocio
│   ├── util/              # Utilidades (RxJavalin, RxHttpHandler)
│   └── config/            # Configuración de Guice
└── test/java/org/iskaypet/
    ├── clients/           # Tests de clients
    ├── controllers/       # Tests de controllers
    ├── services/          # Tests de services
    ├── util/              # Tests de utilidades
    └── integration/       # Tests de integración
``` 