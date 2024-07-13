import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<Persona> personas = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatos();
        menuPrincipal();
    }

    private static void cargarDatos() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get("personas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                personas.add(new Persona(datos[0], Integer.parseInt(datos[1])));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private static void menuPrincipal() {
        int op;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nMenú Principal");
        System.out.println("1. Agregar persona");
        System.out.println("2. Mostrar personas");
        System.out.println("3. Eliminar persona");
        System.out.println("4. Modificar persona");
        System.out.println("5. Exportar a archivo de texto");
        System.out.println("6. Salir");

        System.out.print("Ingrese opción: ");
        op = scanner.nextInt();
        scanner.nextLine();

        while (op != 6) {
            switch (op) {
                case 1:
                    agregarPersona();
                    break;
                case 2:
                    mostrarPersonas();
                    break;
                case 3:
                    eliminarPersona(scanner);
                    break;
                case 4:
                    modificarEdad(scanner);
                    break;
                case 5:
                    exportarPersonas();
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

            System.out.println("\nMenú Principal");
            System.out.println("1. Agregar persona");
            System.out.println("2. Mostrar personas");
            System.out.println("3. Eliminar persona");
            System.out.println("4. Modificar persona");
            System.out.println("5. Exportar a archivo de texto");
            System.out.println("6. Salir");

            System.out.print("Ingrese opción: ");
            op = scanner.nextInt();
            scanner.nextLine();
        }

        System.out.println("¡Gracias por usar el sistema! Hasta luego.");
    }

    private static void agregarPersona() {
        System.out.print("Ingrese el nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();

        personas.add(new Persona(nombre, edad));
        System.out.println("Persona agregada con exito.");
    }

    private static void mostrarPersonas() {
        if (personas.isEmpty()) {
            System.out.println("No hay personas registradas. Vuelva a intentarlo");
        } else {
            System.out.println("\nLista de Personas");
            personas.forEach(System.out::println);
        }
    }

    private static void eliminarPersona(Scanner scanner) {
        if (Main.personas.isEmpty()) {
            System.out.println("No hay personas registradas para eliminar.");
            return;
        }

        System.out.print("Ingrese el nombre de la persona que desea eliminar: ");
        String nombre = scanner.nextLine();

        Main.personas.removeIf(persona -> persona.getNombre().equals(nombre));
        System.out.println("Persona eliminada con exito.");
    }

    private static void modificarEdad(Scanner scanner) {
        if (Main.personas.isEmpty()) {
            System.out.println("No hay personas registradas para modificar.");
            return;
        }

        System.out.print("Ingrese el nombre de la persona la cual desea modificar: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la nueva edad: ");
        int nuevaEdad = scanner.nextInt();
        scanner.nextLine();

        for (Persona persona : Main.personas) {
            if (persona.getNombre().equals(nombre)) {
                persona.setEdad(nuevaEdad);
                System.out.println("Edad modificada con exito.");
                return;
            }
        }

        System.out.println("Persona no encontrada. Intente nuevamente");
    }

    private static void exportarPersonas() {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get("resultado.txt"))) {
            List<String> nombresMayusculas = personas.stream()
                    .filter(p -> p.getEdad() >= 18)
                    .map(p -> p.getNombre().toUpperCase())
                    .sorted()
                    .toList();

            nombresMayusculas.forEach(nombre -> {
                try {
                    bw.write(nombre);
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println("Error al escribir en el archivo: " + e.getMessage());
                }
            });

            System.out.println("Se han exportado " + nombresMayusculas.size() + " personas al archivo resultado.txt, puede verificar.");
        } catch (IOException e) {
            System.out.println("Error al exportar datos: " + e.getMessage());
        }
    }
}