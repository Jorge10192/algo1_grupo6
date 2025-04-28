# Documentación de Análisis

## Objetivo

El objetivo será desarrollar una librería en **Java** que permita manipular y analizar datos en formato tabular (de dos dimensiones).  
Ofrecerá diversas operaciones y funciones para facilitar el trabajo de un analista de datos que desee trabajar con Java.  
Además, se busca que la librería sea **extensible** para futuras incorporaciones de funcionalidades.

---

## Alcance

El sistema podrá realizar las siguientes funciones:

- Brindar información básica sobre los datos: cantidad de filas, columnas, etiquetas de filas/columnas y tipos de datos de las columnas.
- Acceso indexado a filas, columnas o celdas.
- Soporte de lectura y escritura de estructuras en memoria en formato **CSV**.
- Visualización sencilla de los datos en formato texto.
- Generación y modificación de la estructura tabular.
- Selección y filtrado de datos.
- Copia profunda de estructuras de datos, independientes en memoria.
- Concatenación de tablas.
- Ordenamiento de filas por uno o más criterios de columnas.
- Imputación de valores faltantes.
- Muestreo aleatorio de filas.

---

## Descripción de alto nivel del sistema

El sistema trabajará sobre una estructura tabular (por ejemplo, un archivo **CSV**) permitiendo:

- Visualización y manipulación de los datos desde Java.
- Acceso a información básica como cantidad de filas y columnas, tipos de datos, valores faltantes, valores máximos/mínimos, etc.
- Filtrado de filas según condiciones.
- Acceso indexado a nivel fila y columna.
- Modificación de la estructura tabular.
- Realización de copias profundas independientes.

---

## Requerimientos funcionales

### Información estructural

- Manipular y analizar datos en forma tabular.
- Conocer cantidad de filas y columnas.
- Conocer etiquetas de filas y columnas.
- Conocer tipos de datos de las columnas:
  - Tipos soportados: numérico, booleano, cadena.
  - Valores faltantes representados como `N/A`.
- Acceso a datos tanto por fila como por columna.
- Las etiquetas pueden ser enteros o cadenas.

### Carga y generación

Una estructura tabular puede generarse a partir de:

- Archivos **CSV**.
- Copia profunda de otra estructura.
- Estructura de dos dimensiones nativa de Java.
- Secuencia lineal nativa de Java.

### Acceso y selección

- Modificación directa de celdas.
- Inserción de columnas a partir de otras columnas o secuencias lineales de Java.
- Eliminación de filas o columnas.
- Selección parcial usando listas de etiquetas.

### Operaciones

- Selección parcial mediante filtros aplicados a valores.
- Copia profunda de la estructura.
- Concatenación de dos estructuras.
- Ordenamiento de filas por uno o más criterios.
- Imputación de valores faltantes con valores literales.
- Muestreo aleatorio de filas (porcentaje del total).

---

## Requerimientos no funcionales

### Restricciones técnicas

- Implementación en **Java 8** o superior.
- Uso exclusivo de estructuras nativas de Java (sin librerías externas salvo autorización).

### Interoperabilidad

- Lectura y escritura en formato **CSV**, incluyendo opciones de delimitador y encabezado.

### Mantenibilidad

- Sistema fácil de modificar y mantener.
- Incorporación de nueva funcionalidad con mínimo impacto en el código existente.

### Usabilidad

- Presentación clara y sencilla de la salida en formato de texto tabular.

### Gestión de errores

- Captura de todos los errores en tiempo de ejecución.
- Clasificación mediante una jerarquía de excepciones.

### Rendimiento

- Deseable un mecanismo para medir el costo de ejecución en tiempo.

---

## Desglose de Requerimientos Funcionales y No Funcionales

### Alcance del Proyecto

---

### Requerimientos Funcionales

#### Macro-requerimiento 1: Estructura de las tablas

- **RF 1.1**: Inicializar la tabla con una cantidad conocida de filas y columnas.
- **RF 1.2**: Obtener etiquetas de filas y columnas.
- **RF 1.3**: Clasificar las columnas por tipo de dato.
- **RF 1.4**: Representar valores faltantes con `N/A`.

#### Macro-requerimiento 2: Carga de datos

- **RF 2.1**: Importar datos desde archivo **CSV**.
- **RF 2.2**: Generar tabla a partir de listas/arrays nativos de Java.
- **RF 2.3**: Importar y crear nuevas filas o columnas.
- **RF 2.4**: Crear una copia profunda de una tabla.

#### Macro-requerimiento 3: Acceso y modificación

- **RF 3.1**: Acceso mediante un índice o etiqueta.
- **RF 3.2**: Filtrar datos mediante una condición.
- **RF 3.3**: Buscar un determinado valor.
- **RF 3.4**: Ordenar filas a partir de un criterio.

---

### Requerimientos No Funcionales

#### Macro-requerimiento 1: Restricciones técnicas

- **RNF 1.1**: Implementación en Java 8 o superior.
- **RNF 1.2**: No utilizar librerías externas sin autorización.

#### Macro-requerimiento 2: Interoperabilidad

- **RNF 2.1**: Soportar lectura y escritura de archivos **CSV**.

#### Macro-requerimiento 3: Mantenibilidad

- **RNF 3.1**: Sistema modular y extensible.

#### Macro-requerimiento 4: Usabilidad

- **RNF 4.1**: Representación clara y amigable de los datos.

#### Macro-requerimiento 5: Gestión de errores

- **RNF 5.1**: Captura y clasificación de errores mediante jerarquía de excepciones.

#### Macro-requerimiento 6: Rendimiento

- **RNF 6.1**: Operaciones eficientes para archivos de hasta 10,000 filas y 50 columnas.
