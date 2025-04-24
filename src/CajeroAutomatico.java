import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;


// Clase principal que simula un sistema de cajero automático.
public class CajeroAutomatico {

    // Constantes para definir el archivo, saldo inicial y nombre del usuario.
    private static final String ARCHIVO_SALDO = "saldo.dat";
    private static final double SALDO_INICIAL = 1000.0;
    private static final String NOMBRE_USUARIO = "Steven";
    private Scanner scanner;  // Objeto para leer la entrada del usuario.
    
    
    // Clase que representa la cuenta del usuario.
    // Contiene el nombre del usuario y su saldo actual.
    private class Cuenta {
        String nombre;  // Nombre del usuario.
        double saldo;   // Saldo disponible en la cuenta.
        
        public Cuenta(String nombre, double saldo) {
            this.nombre = nombre;
            this.saldo = saldo;
        }
    }
    
    
    // Método para limpiar la consola 
    private void limpiarConsola() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    

    // Muestra el menú de opciones del sistema.
    private void mostrarMenu() {
        System.out.println("\n----- Sistema de Cajero Automático -----");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Retirar dinero");
        System.out.println("3. Salir");
    }
    
    // Método que inicia el sistema y controla el flujo principal del programa.
    // Se encarga de mostrar el menú, solicitar la opción y procesar la acción.
    public void iniciar() {
        try {
            int opcion;
            do {
                limpiarConsola();    // Limpia la pantalla 
                mostrarMenu();       // Muestra las opciones disponibles.
                opcion = solicitarOpcion();  // Solicita la opción
                procesarOpcion(opcion);        // Procesa la opción seleccionada.
                if (opcion != 3) {
                    // Permite al usuario visualizar el resultado antes de limpiar la pantalla.
                    System.out.println("\nPresione Enter para continuar...");
                    scanner.nextLine();
                    scanner.nextLine(); // Espera a que el usuario presione Enter.
                }
            } while (opcion != 3); // Repite hasta que el usuario elija salir.
            scanner.close();
        } catch (Exception e) {
            System.out.println("Error al procesar la petición: " + e.getMessage());
        }
    }
    
    // Solicita al usuario que ingrese una opción numérica.
    private int solicitarOpcion() {
        int opcion = 0;
        boolean inputValido = false;
        do {
            System.out.print("Seleccione una opción: ");
            try {
                opcion = scanner.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                scanner.next(); // Descarta la entrada inválida.
            }
        } while (!inputValido);
        return opcion;
    }
    
    // Procesa la opción seleccionada
    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                consultarSaldo();
                break;
            case 2:
                retirarDinero();
                break;
            case 3:
                System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                break;
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
        }
    }
    
    // Consulta y muestra el saldo actual del usuario
    private void consultarSaldo() {
        Cuenta cuenta = leerCuenta();
        System.out.println(cuenta.nombre + " tu saldo actual es: " + cuenta.saldo);
    }
    

    // Permite al usuario retirar dinero de su cuenta.
    private void retirarDinero() {
        Cuenta cuenta = leerCuenta();
        double saldoActual = cuenta.saldo;
        double cantidad = 0;
        boolean inputValido = false;
        // Valida la entrada del monto y verifica que el saldo sea suficiente.
        do {
            System.out.print("----");
            consultarSaldo(); // Muestra el saldo actual antes de la operación.
            System.out.print("Ingrese la cantidad a retirar: ");
            try {
                cantidad = scanner.nextDouble();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número válido.");
                scanner.next(); // Descarta la entrada inválida.
            }
        } while (!inputValido);
    
        if (cantidad <= saldoActual) {
            double nuevoSaldo = saldoActual - cantidad;
            if (guardarCuenta(nuevoSaldo)) {
                System.out.println("Retiro exitoso. Nuevo saldo: " + nuevoSaldo);
                // Si la operación es exitosa, actualiza el saldo en el archivo.
            } else {
                System.out.println("Error al actualizar el saldo.");
            }
        } else {
            System.out.println("Saldo insuficiente para retirar esa cantidad.");
        }
    }
    
    //Lee la cuenta del usuario (nombre y saldo) desde el archivo "saldo.dat".
    private Cuenta leerCuenta() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(ARCHIVO_SALDO))) {
            String nombre = dis.readUTF();
            double saldo = dis.readDouble();
            //Retorna Un objeto Cuenta con los datos leídos del archivo.
            return new Cuenta(nombre, saldo);
        } catch (IOException e) {
            System.out.println("Error al leer la cuenta: " + e.getMessage());
            // Si ocurre un error, retorna una cuenta con saldo 0.
            return new Cuenta(NOMBRE_USUARIO, 0);
        }
    }
    

    //Guarda la cuenta actualizada en el archivo "saldo.dat".
    private boolean guardarCuenta(double nuevoSaldo) {
        try (DataOutputStream dis = new DataOutputStream(new FileOutputStream(ARCHIVO_SALDO))) {
            dis.writeUTF(NOMBRE_USUARIO);
            // nuevoSaldo El saldo resultante de la operación.
            dis.writeDouble(nuevoSaldo);
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar la cuenta: " + e.getMessage());
            return false;
        }
    }
    
    // Inicializa el archivo "saldo.dat" con el nombre de usuario y el saldo inicial,
    private void inicializarSaldo() {
        File archivo = new File(ARCHIVO_SALDO);
        if (!archivo.exists()) {
            try (DataOutputStream dis = new DataOutputStream(new FileOutputStream(archivo))) {
                dis.writeUTF(NOMBRE_USUARIO);
                dis.writeDouble(SALDO_INICIAL);
            } catch (IOException e) {
                System.out.println("Error al inicializar la cuenta: " + e.getMessage());
            }
        }
    }
    

    public CajeroAutomatico() {
        scanner = new Scanner(System.in);
        inicializarSaldo();
    }
    

    public static void main(String[] args) {
        CajeroAutomatico cajero = new CajeroAutomatico();
        cajero.iniciar();
    }
}
