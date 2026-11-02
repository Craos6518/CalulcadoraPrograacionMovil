# Calculadora Móvil - Estilo iOS (Android Nativo)

## Descripción del Proyecto

Este proyecto implementa una aplicación de calculadora móvil para Android, desarrollada con Kotlin y Jetpack Compose. La aplicación permite a los usuarios realizar operaciones aritméticas básicas y visualizar un historial de sus cálculos. La interfaz está inspirada en el diseño de la calculadora de iOS.

## Objetivo

Desarrollar una aplicación Android nativa con Kotlin y Jetpack Compose que reproduzca una calculadora inspirada en la interfaz de iOS. La aplicación debe permitir realizar operaciones aritméticas básicas, conservar un historial de operaciones y permitir la navegación entre ambas pantallas. El proyecto debe evidenciar el uso de MVVM, la separación de responsabilidades y buenas prácticas de programación.

## Requisitos Funcionales

### Interfaz de Calculadora
*   **Botones**: Mostrar botones numéricos del 0 al 9 y las operaciones `+`, `-`, `×`, `÷`, `%`, y cambio de signo (`+/−`).
*   **Acciones especiales**: Incluir las acciones `AC` (limpiar) y `=` (calcular).
*   **Visualización**: Mostrar de forma legible el número ingresado y el resultado o estado actual de la operación.
*   **Diseño consistente**: Mantener una distribución visual consistente con la referencia de calculadora estilo iOS y adaptar razonablemente los elementos al tamaño de pantalla.

### Lógica de Cálculo
*   **Operaciones consecutivas**: Permitir operaciones consecutivas según el comportamiento definido para la calculadora.
*   **Números decimales**: Procesar números decimales correctamente y evitar separadores o conversiones inconsistentes.
*   **Manejo de errores**: Prevenir errores como división por cero y entradas inválidas; informar el estado de forma comprensible, sin cerrar la aplicación.
*   **Comportamiento coherente**: Definir y mantener un comportamiento coherente para `AC`, cambio de signo, porcentaje y pulsaciones repetidas de `=`.n
### Historial
*   **Visualización**: Mostrar las operaciones realizadas en una lista, incluyendo expresión y resultado (por ejemplo: `5 + 3 = 8`).
*   **Actualización**: Actualizar el historial después de completar una operación válida.
*   **Orden y estado**: Conservar el orden de las operaciones y mostrar un estado vacío cuando todavía no existan registros.

### Navegación y Estado
*   **Navegación**: Permitir navegar de la calculadora al historial y regresar sin comportamientos inesperados.
*   **Sincronización de estado**: Mantener el estado de la interfaz sincronizado con el ViewModel y actualizar la pantalla cuando cambie.

## Requisitos Técnicos y Buenas Prácticas

*   **Tecnología**: Kotlin + Jetpack Compose para la interfaz. No utilizar layouts XML (excepto archivos XML de recursos en `res/values` para cadenas o colores).
*   **Arquitectura**: Implementar **MVVM** (Model-View-ViewModel) para separar la interfaz (Composable), el estado y las acciones de presentación (ViewModel) y la lógica de cálculo (Model). Los Composables no deben ejecutar cálculos de negocio ni contener reglas de operación.
*   **Estado Observable**: Exponer desde el ViewModel un estado observable (`LiveData`/`State`) y recolectarlo correctamente desde Compose. La UI debe representar el estado y enviar eventos al ViewModel.
*   **Recursos de Texto**: Todo texto fijo visible para el usuario debe declararse en `res/values/strings.xml` y consumirse desde Composables mediante `stringResource(R.string.nombre_recurso)`.
*   **Recursos de Color**: Los colores definidos en `res/values/colors.xml` deben consumirse mediante `colorResource(R.color.nombre_recurso)`.
*   **ViewModel sin recursos de UI**: No llamar `stringResource()` ni `colorResource()` desde el ViewModel. El ViewModel debe exponer estados semánticos o datos, y la UI debe traducirlos a recursos.
*   **Código Limpio**: Usar nombres descriptivos en inglés, mantener una convención consistente, evitar abreviaturas confusas y eliminar código comentado, duplicación y variables globales innecesarias.
*   **Funciones y Componentes**: Crear funciones y Composables pequeños con una responsabilidad clara. Extraer componentes reutilizables cuando reduzcan duplicación y mejoren la legibilidad.
*   **Organización**: Organizar archivos por responsabilidad, manteniendo separadas las pantallas, componentes de UI, ViewModel, modelos y lógica de cálculo.
*   **Manejo de Errores**: Validar los casos límite y representar errores de forma controlada. No ocultar fallos ni permitir cierres inesperados.
*   **Git y Entrega**: Publicar el proyecto completo en un repositorio Git con un `.gitignore` apropiado para Android Studio.

## Arquitectura y Decisiones de Diseño

El proyecto sigue principios de diseño moderno de Android, buscando modularidad, reactividad y una UI declarativa.

1.  **Jetpack Compose para la UI**: Adoptado por su enfoque declarativo, simplificando la construcción de interfaces de usuario complejas y reactivas. Facilita la creación de componentes reutilizables y mejora la legibilidad del código UI.
2.  **Patrón MVVM (Model-View-ViewModel)**:
    *   **View (Vistas Composable)**: `Calculadora.kt` y `Historial.kt` observan datos del ViewModel y reaccionan a interacciones del usuario.
    *   **ViewModel (`ModeloVistaCalculadora.kt`)**: Intermediario entre Modelo y Vista. Contiene la lógica de la UI y expone datos observables (usando `LiveData`).
    *   **Model (`EvaluadorCalculadora.kt`)**: Contiene la lógica de negocio pura, como la evaluación de expresiones matemáticas.
3.  **Manejo de Estado con `LiveData`**: Utilizado para la gestión y observación del estado de la UI, consciente del ciclo de vida de los componentes de Android, previniendo fugas de memoria.
4.  **Navegación con Jetpack Compose Navigation**: Empleado para manejar la transición entre la pantalla de la calculadora y la pantalla del historial.
5.  **Motor JavaScript Rhino para Evaluación de Expresiones**: Integrado para la evaluación flexible y robusta de expresiones matemáticas, evitando la necesidad de implementar un *parser* y *evaluador* desde cero en Kotlin. Esto introduce una dependencia externa.
6.  **Theming de Material Design 3**: Adoptado para un aspecto moderno y consistente de la aplicación, utilizando los componentes y directrices más recientes de Android. Los temas se definen en `Color.kt`, `Theme.kt`, `Type.kt` y `themes.xml`.

## Estructura del Proyecto (Archivos Clave)

*   `app/src/main/java/com/craos6518/calulcadoraprograacionmovil/MainActivity.kt`: Punto de entrada de la aplicación. Configura el tema, la navegación y el ViewModel principal.
*   `app/src/main/java/com/craos6518/calulcadoraprograacionmovil/Calculadora.kt`: Define la interfaz de usuario principal de la calculadora (Composable).
*   `app/src/main/java/com/craos6518/calulcadoraprograacionmovil/Historial.kt`: Define la interfaz de usuario para mostrar el historial de operaciones (Composable).
*   `app/src/main/java/com/craos6518/calulcadoraprograacionmovil/ModeloVistaCalculadora.kt`: ViewModel principal. Contiene la lógica de negocio de la calculadora, gestiona el estado de la UI y utiliza `EvaluadorCalculadora`.
*   `app/src/main/java/com/craos6518/calulcadoraprograacionmovil/EvaluadorCalculadora.kt`: Contiene la lógica para evaluar expresiones matemáticas utilizando el motor JavaScript Rhino.
*   `app/src/main/java/com/craos6518/calulcadoraprograacionmovil/ui/theme/Color.kt`: Define los colores utilizados en la aplicación.
*   `app/src/main/java/com/craos6518/calulcadoraprograacionmovil/ui/theme/Theme.kt`: Define el tema de la aplicación, configurando el esquema de colores y tipografía de Material Design 3.
*   `app/src/main/java/com/craos6518/calulcadoraprograacionmovil/ui/theme/Type.kt`: Define la tipografía utilizada en la aplicación.
*   `app/src/main/res/values/strings.xml`: Archivo de recursos de cadenas de texto para la aplicación (internacionalización).
*   `app/src/main/res/values/colors.xml`: Archivo de recursos de colores básicos (para temas XML).
*   `app/src/main/res/values/themes.xml`: Define el tema XML base de la aplicación para el sistema Android.

## Flujo de Ejecución

1.  **Inicio de la App**: `MainActivity.kt` se inicia, creando una instancia de `ModeloVistaCalculadora` y configurando la navegación con Jetpack Compose.
2.  **Pantalla de Calculadora (`Calculadora.kt`)**: La UI observa `ecuacionText`, `resultadosText` y `isError` del `ModeloVistaCalculadora`. Las interacciones del usuario (`clickBoton`) actualizan el estado interno del ViewModel. El `ModeloVistaCalculadora` utiliza `EvaluadorCalculadora` para los cálculos, y Jetpack Compose re-renderiza la UI automáticamente.
3.  **Pantalla de Historial (`Historial.kt`)**: Se accede desde la calculadora. Observa la lista `historial` del `ModeloVistaCalculadora`. Permite borrar el historial o volver a la calculadora.

## Calificación (Rúbrica)

| Criterio          | Puntaje Máximo |
| :---------------- | :------------- |
| Lógica            | 25             |
| MVVM              | 25             |
| Código limpio     | 15             |
| Interfaz          | 15             |
| Navegación        | 10             |
| Historial         | 10             |
| **Total**         | **100 pts**    |

## Consideraciones Adicionales y Posibles Mejoras

*   **Manejo de Errores**: Aunque se capturan excepciones de Rhino, se podrían proporcionar mensajes de error más específicos al usuario (ej. división por cero, sintaxis inválida).
*   **Persistencia del Historial**: Actualmente, el historial se almacena en memoria y se pierde al cerrar la aplicación. Para persistencia, se podría usar `Room` (base de datos SQLite) o `DataStore`.
*   **Testing**: Se incluyen archivos de test unitario (`EvaluadorCalculadoraTest.kt`, `ModeloVistaCalculadoraTest.kt`) y de instrumentación (`CalculadoraUiTest.kt`, `ExampleInstrumentedTest.kt`), lo que indica un enfoque en la calidad del código.
*   **Accesibilidad**: Se podría mejorar la accesibilidad añadiendo `contentDescription` a los botones y asegurando un tamaño de texto adecuado para lectores de pantalla.

## Cómo Ejecutar el Proyecto

1.  **Clonar el Repositorio**:
    ```bash
    git clone <URL_DEL_REPOSITORIO>
    cd CalulcadoraPrograacionMovil
    ```
2.  **Abrir en Android Studio**: Importa el proyecto en Android Studio (versión compatible con Jetpack Compose y Kotlin).
3.  **Sincronizar Gradle**: Deja que Gradle sincronice las dependencias del proyecto.
4.  **Ejecutar en Emulador/Dispositivo**: Selecciona un emulador de Android o conecta un dispositivo físico y ejecuta la aplicación.

---

**Docente**: Ing. Edisson Andrés Montes
**Universidad**: Universidad Tecnológica de Pereira
**Programa**: Tecnología en Desarrollo de Software
**Asignatura**: Programación para Móviles
