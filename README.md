# API de Gestion de Estudiantes

API REST desarrollada con Spring Boot para la gestion de estudiantes, implementando arquitectura MVC, almacenamiento en memoria y CI/CD con GitHub Actions.

## Tabla de Contenidos

- [Caracteristicas](#caracteristicas)
- [Tecnologias](#tecnologias)
- [Requisitos Previos](#requisitos-previos)
- [Instalacion y Ejecucion](#instalacion-y-ejecucion)
- [Arquitectura del Proyecto](#arquitectura-del-proyecto)
- [Endpoints de la API](#endpoints-de-la-api)
- [Ejemplos de Uso](#ejemplos-de-uso)
- [Gestion de Versiones (GitOps)](#gestion-de-versiones-gitops)
- [CI/CD Pipeline](#cicd-pipeline)
- [Gestion de Versiones (GitOps)](#gestion-de-versiones-gitops)
- [Modelo de Datos](#modelo-de-datos)
- [Manejo de Errores](#manejo-de-errores)
- [Testing](#testing)
- [Autor](#autor)
- [Licencia](#licencia)
- [Contribuciones](#contribuciones)
- [Soporte](#soporte)
- [Enlaces Utiles](#enlaces-útiles)

---

## Caracteristicas

- API REST con endpoints POST y GET.
- Validacion de datos con Jakarta Bean Validation.
- Almacenamiento en memoria (ConcurrentHashMap).
- Validacion de IDs Unicos.
- Arquitectura MVC (Modelo Vista Controlador).
- Manejo global de excepciones.
- CI/CD automatizado con GitHub Actions.
- Versionamiento semantico automatico.
- Estrategia Trunk-Based Development.

---

## Tecnologias

- **Lenguaje de programacion:** Java 25.
- **Framework principal:** Spring Boot 4.0.2.
- **Gestion de dependencias y build:** Gradle 8.x.
- **CI/CD y automatizacion:** GitHub Actions.
- **Validacion de datos:** Jakarta Validation: 3.x.

### Dependencias Principales:

- **Spring Web:** Creacion de API REST.
- **Spring Boot DevTools:** Desarrollo agil con hot-reload.
- **Validation:** Validacion de beans con anotaciones.

---

## Requisitos Previos

- Java 25 o superior.
- Gradle 8.x.
- Git.
- Postman, cURL o Thunder Client (para pruebas).

---

## Instalacion y Ejecucion

### 1. Clonar el Repositorio

```bash
git clone https://github.com/pjg09/ExamenDevOps.git
cd ExamenDevOps
```

### 2. Compilar el Proyecto

```bash
./gradlew build
```

### 3. Ejecutar la Aplicacion

```bash
./gradlew bootRun
```

La aplicacion estara disponible en `http://localhost:8080`

### 4. Ejecutar Tests

```bash
./gradlew test
```

---

## Arquitectura del Proyecto

El proyecto sigue el patron **MVC** organizado en capas:

```
src/main/java/com/upb/examendevops/
├── ExamenDevOpsApplication.java      # Clase principal
├── controller/
│   └── EstudianteController.java     # Capa de presentacion (endpoints REST)
├── service/
│   └── EstudianteService.java        # Capa de logica de negocio
├── model/
│   └── Estudiante.java               # Capa de modelo (entidad)
└── exception/
    └── GlobalExceptionHandler.java   # Manejo centralizado de errores
```

### Responsabilidad por Capa:

- **Controller:** Recibe requests HTTP, valida y retorna responses.
- **Service:** Contiene logica de negocio y validacion de IDs unicos.
- **Model:** Define la estructura de datos del estudiante.
- **Exception Handler:** Maneja errores y genera responses HTTP consistentes.

---

## Endpoints de la API

### Base URL: `http://localhost:8080`

* **Descripcion:** Crear un nuevo estudiante
    * **Metodo:** POST
    * **Endpoint:** `/estudiantes`
    * **Codigo de exito:** 201 Created

* **Descripcion:** Obtener todos los estudiantes
    * **Metodo:** GET
    * **Endpoint:** `/estudiantes`
    * **Codigo de exito:** 200 OK

---

## Ejemplos de Uso

### 1. Crear un Estudiante (POST)

**Request:**
```bash
curl -X POST http://localhost:8080/estudiantes \
  -H "Content-Type: application/json"
  -d '{
    "id": "000325987",
    "nombre": "Pedro Gomez",
    "carrera": "Ingenieria de Sistemas"
  }'
```

**Response (201 Created):**
```json
{
  "id": "000325987",
  "nombre": "Pedro Gomez",
  "carrera": "Ingenieria de Sistemas"
}
```

---

### 2. Obtener Todos los Estudiantes (GET)

**Request:**
```bash
curl http://localhost:8080/estudiantes
```

**Response (200 OK):**
```json
[
  {
    "id": "000545514",
    "nombre": "Smil Ortega",
    "carrera": "Ingenieria Industrial"
  },
  {
    "id": "000511978",
    "nombre": "Julian Amariles",
    "carrera": "Ingenieria en Ciencia de Datos"
  }
]
```

---

### 3. Error: ID Duplicado (POST)


**Request:**
```bash
curl -X POST http://localhost:8080/estudiantes \
  -H "Content-Type: application/json" \
  -d '{
    "id": "000325987",
    "nombre": "Pedro Gomez",
    "carrera": "Ingenieria en Diseño de Entretenimiento Digital"
  }'
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2026-02-11T23:00:00",
  "status": 400,
  "error": "Bad Request",
  "mensaje": "Ya existe un estudiante con el ID: 000325987"
}
```

---

### 4. Error: Validacion de Datos (POST)

**Request:**
```bash
curl -X POST http://localhost:8080/estudiantes \
  -H "Content-Type: application/json" \
  -d '{
    "id": "12345",
    "nombre": "",
    "carrera": "Diseño Grafico"
  }'
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2026-02-11T23:00:00",
  "status": 400,
  "error": "Bad Request",
  "mensaje": "El ID debe tener exactamente 9 dígitos numericos"
}
```

---

## Gestion de Versiones (GitOps)

### Estrategia: Trunk-Based Development

El proyecto utiliza **Trunk-Based Development** con las siguientes caracteristicas:

- **Rama principal:** `main` (protegida)
- **Ramas de corta duracion:** `feature/*`, `fix/*`, `chore/*`
- **Integracion continua:** Merge frecuente mediante Pull Requests
- **Sin push directo a main:** Solo via pull request despues de pasar CI

### Flujo de Trabajo

```bash
# 1. Crear rama desde main
git checkout main
git pull origin main
git checkout -b feature/nueva-funcionalidad

# 2. Desarrollar y commitear
git add .
git commit -m "feat: agregar nueva funcionalidad"
git push origin feature/nueva-funcionalidad

# 3. Crear pull request en GitHub
# 4. CI/CD ejecutado automaticamente
# 5. Merge a main despues de aprobacion
# 6. Release automatico generado

# 7. Limpiar rama local
git checkout main
git pull origin main
git branch -d feature/nueva-funcionalidad
```

---

## CI/CD Pipeline

### GitHub Actiones Workflow

El proyecto incluye un pipeline CI/CD automatizado que se ejectua en cada push o pull request a la rama `main`.

**Archivo:** `.github/workflows/ci-cd.yml`

### Herramientas y Tecnologías del Pipeline

#### 1. **GitHub Actions**
- **Qué es:** Plataforma de automatización nativa de GitHub
- **Por qué se usa:** Permite ejecutar workflows automáticos sin necesidad de servidores externos
- **En este proyecto:** Ejecuta el build, tests y genera releases automáticamente

#### 2. **actions/checkout@v4**
- **Qué es:** Action oficial para clonar el repositorio
- **Por qué se usa:** Necesario para que el runner de GitHub tenga acceso al código fuente
- **En este proyecto:** Clona el código con historial completo (`fetch-depth: 0`) para calcular versiones

#### 3. **actions/setup-java@v4**
- **Qué es:** Action oficial para configurar el entorno Java
- **Por qué se usa:** Instala y configura la versión específica de Java requerida
- **En este proyecto:** Configura Java 25 con distribución Temurin y caché de Gradle para builds más rápidos

#### 4. **Gradle**
- **Qué es:** Herramienta de automatización de builds para Java
- **Por qué se usa:** Compila el proyecto, ejecuta tests y genera el archivo JAR
- **En este proyecto:** 
  - `./gradlew build` --> Compila el código
  - `./gradlew test` --> Ejecuta los tests unitarios
  - Genera el artefacto `estudiantes-api-*.jar`

#### 5. **actions/upload-artifact@v4** y **actions/download-artifact@v4**
- **Qué es:** Actions para compartir archivos entre jobs del workflow
- **Por qué se usa:** Permite que el job de `release` use el JAR generado por el job de `build`
- **En este proyecto:** Transfiere el JAR compilado entre los jobs `build` y `release`

#### 6. **softprops/action-gh-release@v2**
- **Qué es:** Action de terceros para crear GitHub Releases
- **Por qué se usa:** Automatiza la creación de releases con tags y archivos adjuntos
- **En este proyecto:** Crea releases automáticos con versionamiento semántico y adjunta el JAR

#### 7. **Bash Scripting**
- **Qué es:** Lenguaje de scripting para automatización en Unix/Linux
- **Por qué se usa:** Permite lógica personalizada para calcular versiones
- **En este proyecto:** Script que:
  - Lee el último tag de Git
  - Analiza los commits desde ese tag
  - Determina si es cambio MAJOR, MINOR o PATCH
  - Calcula la nueva versión semántica

### Jobs del Pipeline:

#### 1. **Build** (Siempre se ejecuta)
```yaml
Trigger: push o pull_request a main
Runner: ubuntu-latest
Pasos:
  1. Checkout del código con historial completo
  2. Setup de Java 25 con caché de Gradle
  3. Otorgar permisos de ejecución a gradlew
  4. Compilar con Gradle (./gradlew build)
  5. Ejecutar tests (./gradlew test)
  6. Subir JAR como artefacto
```

**Propósito:** Validar que el código compila y los tests pasan antes de cualquier merge.

#### 2. **Release** (Solo en main después de merge)
```yaml
Trigger: push a main (después de merge de PR)
Dependencia: Job 'build' debe completarse exitosamente
Runner: ubuntu-latest
Pasos:
  1. Checkout del código con historial completo
  2. Descargar JAR del job anterior
  3. Ejecutar script de cálculo de versión:
     - Obtener último tag (v0.0.0 si no existe)
     - Analizar commits desde último tag
     - Detectar tipo de cambio (feat/fix/BREAKING)
     - Calcular nueva versión
  4. Crear GitHub Release con:
     - Tag de la nueva versión
     - Archivo JAR adjunto
     - Notas del release
```

**Propósito:** Generar automáticamente releases versionados cada vez que se integra código a `main`.

### Versionamiento Semantico Automatico

El pipeline analiza los mensajes del commit usando **Conventional Commits** y calcula la version automaticamente:

* **Tipo de Commit:** `feat:`
    * **Ejemplo:** `feat: agregar endpoint`
    * **Incremento:** MINOR
    * **Version:** v1.0.0 --> v1.1.0

* **Tipo de Commit:** `fix:`
    * **Ejemplo:** `fix: corregir validacion`
    * **Incremento:** PATCH
    * **Version:** v1.1.0 --> v1.1.1

* **Tipo de Commit:** `BREAKING CHANGE:`
    * **Ejemplo:** `BREAKING CHANGE: cambiar API`
    * **Incremento:** MAJOR
    * **Version:** v1.1.1 --> v2.0.0

* **Tipo de Commit:** `chore:`
    * **Ejemplo:** `chore: actualizar README`
    * **Incremento:** PATCH
    * **Version:** v1.1.1 --> v1.1.2

**Algoritmo de Versionamiento:**
```bash
1. Obtener último tag de Git (ej: v1.2.3)
2. Extraer números: MAJOR=1, MINOR=2, PATCH=3
3. Leer todos los commits desde v1.2.3 hasta HEAD
4. Buscar palabras clave:
   - Si encuentra "BREAKING CHANGE" o "major:" -- MAJOR++, MINOR=0, PATCH=0
   - Si encuentra "feat:" o "feature:" -- MINOR++, PATCH=0
   - En cualquier otro caso -- PATCH++
5. Nueva versión: v2.0.0 o v1.3.0 o v1.2.4
6. Crear tag y release en GitHub
```

### Convención de Commits
```bash
# Feature (nueva funcionalidad)
git commit -m "feat: implementar endpoint de búsqueda"

# Fix (corrección de bug)
git commit -m "fix: resolver problema con IDs duplicados"

# Breaking Change (cambio incompatible)
git commit -m "feat: refactorizar API

BREAKING CHANGE: estructura de respuesta modificada"

# Chore (mantenimiento)
git commit -m "chore: actualizar dependencias"

# Docs (documentación)
git commit -m "docs: mejorar README"
```

### Permisos Requeridos

El workflow necesita permisos especiales configurados en el archivo YAML:
```yaml
permissions:
  contents: write    # Para crear tags y releases
  packages: write    # Para publicar artefactos
```

### Verificar Ejecución del Pipeline

1. **Ver workflows:** Ve a la pestaña "Actions" en tu repositorio de GitHub
2. **Inspeccionar jobs:** Click en cualquier workflow run para ver logs detallados
3. **Verificar releases:** Ve a la sección "Releases" para ver las versiones generadas

## Gestion de Versiones (GitOps)

### Estrategia: Trunk-Based Development

#### ¿Por qué Trunk-Based en lugar de GitFlow?

**Trunk-Based Development** fue elegido sobre GitFlow por las siguientes razones:

#### Ventajas para este Proyecto:

1. **Simplicidad y Rapidez**
   - Solo una rama principal (`main`)
   - Ramas de feature de corta duración (horas/días)
   - Menos complejidad en la gestión de ramas
   - **Ideal para:** Equipos pequeños o proyectos académicos

2. **Integración Continua Real**
   - Merge frecuente a `main` (varias veces al día)
   - Detección temprana de conflictos
   - Feedback rápido del CI/CD
   - **Ideal para:** Proyectos con CI/CD automatizado

3. **Menos Overhead**
   - No requiere mantener múltiples ramas de larga duración
   - No hay merges complejos entre `develop` y `main`
   - No hay hotfix branches separados
   - **Ideal para:** Proyectos con entregas continuas

4. **Alineado con DevOps y CI/CD**
   - Cada merge a `main` genera un release automático
   - Versionamiento semántico transparente
   - Deploy continuo posible
   - **Ideal para:** Automatización completa

#### Por qué NO GitFlow:

GitFlow es excelente para proyectos grandes con releases planificados, pero para este proyecto sería **excesivo** porque:

1. **Complejidad innecesaria:**
   - Requiere ramas `main`, `develop`, `release/*`, `hotfix/*`
   - Merges en múltiples direcciones
   - Más difícil de mantener para un equipo pequeño

2. **Integración lenta:**
   - Código vive en `develop` por mucho tiempo antes de llegar a `main`
   - Conflictos más grandes y difíciles de resolver
   - Feedback del CI más tardío

3. **No aprovecha CI/CD:**
   - Releases manuales en lugar de automáticos
   - Versionamiento manual en lugar de semántico automático

   ### Reglas de Trunk-Based en este Proyecto:

1. **Rama principal protegida:** `main`
   - No se puede hacer push directo
   - Solo merge vía Pull Request
   - CI debe pasar antes de merge

2. **Ramas de corta duración:**
   - Prefijos: `feature/`, `fix/`, `chore/`, `docs/`
   - Vida útil: Algunas horas
   - Borrar después del merge

3. **Pull Requests obligatorios:**
   - Toda rama debe mergearse vía PR
   - CI ejecuta automáticamente
   - Review opcional (recomendado en equipos)

4. **Releases automáticos:**
   - Cada merge a `main` crea un release
   - Versionamiento basado en commits
   - JAR adjunto automáticamente

   ### Flujo de Trabajo Completo:
```bash
# 1. Sincronizar con main
git checkout main
git pull origin main

# 2. Crear rama de feature
git checkout -b feature/nueva-funcionalidad

# 3. Desarrollar
# ... hacer cambios ...
git add .
git commit -m "feat: agregar nueva funcionalidad"

# 4. Pushear rama
git push origin feature/nueva-funcionalidad

# 5. Crear Pull Request en GitHub
# - CI ejecuta automáticamente
# - Si CI pasa  -- Se puede mergear
# - Si CI falla  -- Corregir y volver a pushear

# 6. Mergear PR en GitHub
# - Automáticamente se crea Release
# - Versión calculada según commits

# 7. Limpiar rama local
git checkout main
git pull origin main
git branch -d feature/nueva-funcionalidad
```

### Casos de Uso:

**Caso 1: Nueva funcionalidad**
```bash
git checkout -b feature/endpoint-delete
# ... código ...
git commit -m "feat: agregar endpoint DELETE /estudiantes/{id}"
# PR -- Merge -- Release v1.1.0
```

**Caso 2: Bug fix**
```bash
git checkout -b fix/validacion-id
# ... código ...
git commit -m "fix: corregir validación de IDs duplicados"
# PR -- Merge -- Release v1.0.1
```

**Caso 3: Cambio incompatible**
```bash
git checkout -b feature/refactor-api
# ... código ...
git commit -m "feat: refactorizar estructura de respuesta

BREAKING CHANGE: formato JSON de respuesta modificado"
# PR -- Merge -- Release v2.0.0
```

### Beneficios Observados:

- Pipeline ejecutado más de 10 veces durante desarrollo
- Cada feature mergeada en < 1 día
- 0 conflictos de merge complejos
- Releases automáticos sin intervención manual
- Historial de commits limpio y trazable

## Modelo de Datos

### Entidad: Estudiante
```java
{
  "id": String,      // ID único de 9 dígitos numéricos (documento)
  "nombre": String,  // Nombre completo (no vacío)
  "carrera": String  // Carrera que cursa (no vacía)
}
```

### Validaciones:

* **Campo:** `id`
    * **Reglas:** No nulo, exactamente 9 digitos, solo numerico, unico en el sistema
    * **Mensaje de Error:** El ID debe tener exactamente 9 digitos numericos

* **Campo:** `nombre`
    * **Reglas:** No vacio, no solo espacios
    * **Mensaje de Error:** El nombre no puede estar vacio

* **Campo:** `carrera`
    * **Reglas:** No vacio, no solo espacios
    * **Mensaje de Error:** La carrera no puede estar vacia


### Ejemplos de IDs Válidos:
- `"123456789"`
- `"012345678"` (mantiene el cero inicial)
- `"999999999"`

### Ejemplos de IDs Inválidos:
- `"12345678"` (solo 8 dígitos)
- `"1234567890"` (10 dígitos)
- `"12345678a"` (contiene letra)
- `"123 456 789"` (contiene espacios)

---

## Manejo de Errores

### Estructura de Respuestas de Error

Todas las respuestas de error siguen el formato:
```json
{
  "timestamp": "2026-02-11T23:00:00",
  "status": 400,
  "error": "Bad Request",
  "mensaje": "Descripción específica del error"
}
```

### Tipos de Errores Manejados:

1. **Validación de Jakarta Bean Validation**
   - Trigger: Anotaciones `@NotBlank`, `@Pattern`
   - Response: 400 con campos específicos con error

2. **ID Duplicado**
   - Trigger: Intentar crear estudiante con ID existente
   - Response: 400 con mensaje "Ya existe un estudiante con el ID: XXX"

3. **Validación en Setters**
   - Trigger: Datos inválidos durante deserialización JSON
   - Response: 400 con mensaje específico de validación

4. **Errores Inesperados**
   - Trigger: Cualquier excepción no manejada
   - Response: 500 con mensaje genérico

---

## Testing

### Checklist de Pruebas:

- [x] POST con datos válidos → 201 Created
- [x] POST con ID duplicado → 400 Bad Request
- [x] POST con ID inválido (< 9 dígitos) → 400 Bad Request
- [x] POST con nombre vacío → 400 Bad Request
- [x] POST con carrera vacía → 400 Bad Request
- [x] POST con múltiples errores → 400 Bad Request
- [x] GET con estudiantes → 200 OK + lista
- [x] GET sin estudiantes → 200 OK + lista vacía

### Ejecutar Tests Manuales:

Ver sección [Ejemplos de Uso](#ejemplos-de-uso) para comandos cURL completos.

---

## Autor

**Nombre:** Pedro Jose Gomez Lopez
**ID:** 000325987
**Institución:** Universidad Pontificia Bolivariana
**Carrera:** Ingenieria de Sistemas e Informatica 
**Curso:** Plataformas de Programación Empresarial  
**Proyecto:** Examen DevOps - Ciclo de Vida del Software, GitOps y Arquitectura MVC

---

## Licencia

Este proyecto es de uso académico para el curso de Plataformas de Programación Empresarial.

---

## Contribuciones

Este es un proyecto académico individual. No se aceptan contribuciones externas.

---

## Soporte

Para preguntas o problemas relacionados con el proyecto, abrir un issue en el repositorio de GitHub.

---

## Enlaces Útiles

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación de Jakarta Bean Validation](https://jakarta.ee/specifications/bean-validation/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Trunk-Based Development](https://trunkbaseddevelopment.com/)

---

**Última actualización:** Febrero 2026