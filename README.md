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

- El sistema debe permitir manipular y analizar datos en forma tabular
- Se debe poder conocer la cantidad de filas y columnas.
- Se debe poder conocer las etiquetas de filas y columnas.
- Se debe poder conocer los tipos de datos de las columnas.
- Los tipos de datos soportados para una columna son: numérico, booleano, cadena.
- Debe existir un valor especial que haga referencia a valores faltantes (N/A).
- Se debe poder acceder a los datos tanto a nivel de fila como de columna.
- Una etiqueta (label) puede ser en formato numérico entero o cadena.


### Carga y generación

Una estructura tabular puede generarse a partir de:

- Archivos **CSV**.
- Copia profunda de otra estructura.
- Estructura de dos dimensiones nativa de Java.
- Secuencia lineal nativa de Java.

### Acceso y selección

- Una estructura tabular puede modificarse de las siguientes formas:
  - Accediendo directo a una celda y asignando un nuevo valor.
  - Insertando una columna a partir de otra columna (con misma cantidad de elementos que     filas). 
  - Insertando una columna nueva a partir de una secuencia lineal nativa de Java (con misma       cantidad de elementos que filas).
  - Eliminando una columna o eliminando una fila.
- El sistema debe permitir la selección parcial de la estructura tabular a través de una lista de etiquetas de cada índice.


### Operaciones

- El sistema debe permitir la selección parcial de la estructura tabular a través de un filtro aplicado a valores de las celdas. 
- Se debe permitir la copia profunda de los elementos de la estructura para generar una nueva con mismos valores, pero independiente de la estructura original en memoria.
- Se debe permitir generar una nueva estructura tabular a partir de la concatenación de dos estructuras existentes.
- Se debe permitir ordenar las filas de la estructura según un criterio (ascendente o descendente) sobre una o más columnas.
- El sistema debe incorporar la posibilidad de modificar (rellenar) las celdas con valores faltantes con cierto valor literal indicado.
- El sistema debe ofrecer una selección aleatoria de filas según un porcentaje del total de la estructura. 


---

## Requerimientos no funcionales

### Restricciones técnicas

- La implementación debe estar en Java versión 8 o superior.
- Se pueden usar estructuras de datos nativas de Java, pero no se deben usar librerías externas salvo autorización.


### Interoperabilidad

- Se debe soportar lectura y escritura en formato CSV, incluyendo opciones de delimitador y encabezado.

### Mantenibilidad

- El sistema debe ser fácil de modificar y mantener a lo largo del tiempo.
- El sistema debe permitir incorporar nueva funcionalidad con el menor impacto posible sobre el código existente.


### Usabilidad

- La salida tabular debe ser presentada en formato de texto, de forma clara y sencilla.

### Gestión de errores

- Se debe capturar de todos los errores en tiempo de ejecución.
- Debe haber una clasificación de errores mediante una jerarquía de excepciones.

### Rendimiento

- Es deseable disponer de algún mecanismo que permita cuantificar el costo de ejecución de las operaciones, en términos de tiempo.

---

## Desglose de Requerimientos Funcionales y No Funcionales

### Vista complementaria:

Se presenta un desglose de los requerimientos funcionales y no funcionales en formato de macro-requerimientos, como forma de organizar de manera esquemática lo detallado anteriormente.


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
