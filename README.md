# Sistema de Cajero Automático

## Descripción del Proyecto
Este proyecto implementa un sencillo cajero automático en Java que permite a un único usuario (Steven) consultar su saldo y realizar retiros. El nombre y el saldo se almacenan de forma persistente en un archivo binario (`saldo.dat`), y el programa ofrece un menú por consola con limpieza automática de pantalla y control de entradas erróneas.

---

## Características

- **Persistencia de datos**  
  Guarda el nombre de usuario y su saldo en un archivo binario (`saldo.dat`) usando `DataOutputStream`/`DataInputStream`.

- **Menú interactivo**  
  Opciones para:
  1. Consultar saldo  
  2. Retirar dinero  
  3. Salir  

- **Limpieza de consola**  
  Cada vez que se vuelve al menú, la pantalla se limpia mediante códigos ANSI (funciona en macOS y Linux).

- **Validación de entrada**  
  Control de `InputMismatchException` para que solo se acepten números al introducir opciones y montos.

- **Manejo de errores de I/O**  
  Uso de **try-with-resources** para apertura/lectura/escritura del archivo y captura de posibles `IOException`.

---

## Requisitos

- JDK 8 o superior  
- Terminal compatible con códigos ANSI (ej. Terminal de macOS, Linux)

---

## Estructura de Archivos

```
/mi-proyecto-cajero/
│
├── src/
│   └── CajeroAutomatico.java
│
├── saldo.dat           ← Archivo binario que almacena nombre y saldo
└── README.md
```

---

## Instalación y Compilación

1. Clona o descarga el repositorio:
   ```bash
   git clone https://turepo.git/mi-proyecto-cajero.git
   cd mi-proyecto-cajero
   ```

2. Compila el código:
   ```bash
   javac src/CajeroAutomatico.java
   ```

3. Asegúrate de que `saldo.dat` existe en la carpeta de trabajo. Si no, el programa lo crea con saldo inicial de 1000.0 y nombre “Steven”.

---

## Uso

Ejecuta el programa desde la carpeta raíz:
```bash
java -cp src CajeroAutomatico
```

Verás el menú:
```
----- Sistema de Cajero Automático -----
1. Consultar saldo
2. Retirar dinero
3. Salir
```

- **Consultar saldo:** muestra  
  ```
  Steven tu saldo actual es: X.0
  ```
- **Retirar dinero:** solicita el monto, valida que sea numérico y que el saldo alcance, actualiza `saldo.dat`.
- **Salir:** termina el programa limpiamente.

---

## Detalles de Implementación

- **Clase `CajeroAutomatico`**  
  - **Constructor**: inicializa `Scanner` y crea `saldo.dat` si no existe.  
  - **iniciar()**: bucle principal que limpia consola, muestra menú, lee y procesa opción, espera “Enter” para continuar.  
  - **limpiarConsola()**: imprime `\033[H\033[2J` + `flush()` para limpiar el terminal.  
  - **solicitarOpcion()** y **retirarDinero()**: validan entradas con bucles y capturan `InputMismatchException`.  
  - **leerCuenta()**: (try-with-resources) abre `saldo.dat`, lee `UTF` (nombre) y `double` (saldo), retorna `new Cuenta(nombre, saldo)`.  
  - **guardarCuenta(double)**: reescribe `saldo.dat` con `writeUTF(nombre)` + `writeDouble(nuevoSaldo)`.

- **Clase interna `Cuenta`**  
  Agrupa `String nombre` y `double saldo`.

---

## Licencia

MIT © 2025  