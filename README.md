# Juego del Gato (Tic-Tac-Toe)

Bienvenido al repositorio **Juego-Gato**, un proyecto simple del clásico juego del gato (también conocido como Tic-Tac-Toe o Tres en Línea). Este juego está diseñado para que dos jugadores compitan colocando sus símbolos (X y O) en un tablero de 3x3, buscando formar una línea horizontal, vertical o diagonal.

## ¿Cómo funciona el proyecto?

El funcionamiento del juego es el siguiente:

- **Interfaz:** El usuario verá un tablero de 3x3 donde podrá seleccionar la casilla donde desea colocar su símbolo (X u O).
- **Turnos:** El juego alterna automáticamente entre los dos jugadores. El jugador 1 inicia con el símbolo "X" y el jugador 2 con el símbolo "O".
- **Reglas:**
  - Los jugadores no pueden seleccionar una casilla ya ocupada.
  - El juego detecta automáticamente si un jugador gana, mostrando el mensaje correspondiente.
  - Si todas las casillas están ocupadas y nadie ha ganado, el juego termina en empate.
- **Reinicio:** Al finalizar una partida, el usuario puede reiniciar el tablero para comenzar una nueva.

## Estructura del proyecto

El proyecto está compuesto principalmente por los siguientes archivos/directorios:
- `src/` - Código fuente principal del juego.
- `src/main/java/` - Código fuente de la aplicación
- `src/test/java/` - Pruebas unitarias
- `README.md` - Este archivo de documentación.
- `pom.xml` - Configuración de Maven y dependencias

## ¿Cómo ejecutar el juego?

### Requisitos previos:
- **JDK 24** (Java Development Kit 24)
- **Maven** (para gestión de dependencias)

### Pasos para ejecutar:

1. **Clona este repositorio:**
   ```bash
   git clone https://github.com/karlojr16/Juego-Gato.git
   ```

2. **Navega al directorio del proyecto:**
   ```bash
   cd Juego-Gato
   ```

3. **Compila y ejecuta el proyecto:**
   ```bash
   mvn clean javafx:run
   ```

### Comandos adicionales:
- **Compilar el proyecto:** `mvn compile`
- **Ejecutar pruebas:** `mvn test`
- **Crear JAR ejecutable:** `mvn package`

## Manual Técnico de Pruebas Unitarias

### Descripción General
El proyecto incluye un conjunto completo de pruebas unitarias implementadas con **JUnit 5** para garantizar la calidad y funcionalidad del código.

### Framework de Testing
- **JUnit Jupiter 5.10.2** - Framework principal de pruebas
- **Maven Surefire Plugin 3.2.5** - Para ejecución de pruebas

### Estructura de Pruebas
```
src/test/java/
└── model/
    └── JugadorTest.java
```

### Pruebas Implementadas

#### Clase: `JugadorTest`
**Ubicación:** `src/test/java/model/JugadorTest.java`

**Pruebas de Constructores:**
- `testConstructorPorDefecto()` - Verifica el constructor por defecto
- `testConstructorConNombreYVictorias()` - Prueba constructor con nombre y victorias
- `testConstructorCompleto()` - Valida constructor completo con todos los parámetros

**Pruebas de Getters y Setters:**
- `testSettersYGetters()` - Verifica funcionalidad de setters y getters
- `testSimboloValido()` - Valida asignación de símbolos válidos (X, O)
- `testNombreVacio()` - Prueba con nombre vacío
- `testNombreNull()` - Prueba con nombre nulo

**Pruebas de Funcionalidad:**
- `testGanarPartida()` - Verifica incremento de victorias
- `testIncrementarVictoria()` - Prueba método de incremento
- `testMetodosCompatibilidad()` - Valida métodos de compatibilidad (getWins/setWins)
- `testMultiplesVictorias()` - Prueba múltiples victorias consecutivas
- `testVictoriasNegativas()` - Valida comportamiento con victorias negativas

### Ejecución de Pruebas

#### Ejecutar todas las pruebas:
```bash
mvn test
```

#### Ejecutar pruebas específicas:
```bash
mvn test -Dtest=JugadorTest
```

#### Ejecutar una prueba específica:
```bash
mvn test -Dtest=JugadorTest#testConstructorPorDefecto
```

#### Generar reporte de cobertura:
```bash
mvn test jacoco:report
```

### Cobertura de Pruebas
Las pruebas cubren los siguientes aspectos de la clase `Jugador`:
- ✅ Constructores (3 variantes)
- ✅ Getters y Setters
- ✅ Funcionalidad de victorias
- ✅ Validación de símbolos
- ✅ Casos edge (nulos, vacíos, negativos)
- ✅ Métodos de compatibilidad

### Configuración de Pruebas
El proyecto utiliza la siguiente configuración en `pom.xml`:
```xml
<properties>
    <junit.version>5.10.2</junit.version>
</properties>
```

### Resultados Esperados
Al ejecutar las pruebas, deberías ver:
- ✅ Todas las pruebas pasando (verde)
- ✅ Cobertura de código del 100% en la clase Jugador
- ✅ Reporte detallado de ejecución

### Mejores Prácticas Implementadas
- **Arrange-Act-Assert**: Estructura clara en cada prueba
- **Nombres descriptivos**: Nombres de pruebas que explican qué se está probando
- **Pruebas independientes**: Cada prueba es independiente de las demás
- **Setup común**: Uso de `@BeforeEach` para configuración común

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas mejorar el juego, arreglar bugs o agregar nuevas funcionalidades, realiza un _fork_ y envía tu _pull request_.

### Guías para contribuir:
1. Asegúrate de que todas las pruebas pasen
2. Agrega pruebas para nuevas funcionalidades
3. Mantén la cobertura de código alta
4. Sigue las convenciones de nomenclatura

## Autor

Desarrollado por [Octavio Chong](https://github.com/FakeMoradin2) y [Karlo Ontiveros](https://github.com/karlojr16).

---

¡Diviértete jugando al Gato!
