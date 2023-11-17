import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    // Arrays que almacenan nombres de los jugadores, sus posiciones y precios
    static String[] jugadores = {"Ran Takahashi", "Wilfredo Leon", "Earvin Ngapeth", "Jae-Hyun Lee", "Leonel Marshall",
            "Yuji Nishida", "Bartosz Kurek", "Stephen Boyer", "Georg Grozer", "Ivan Zaytsev",
            "Bruno Rezende", "Mihajlo Mitic", "Georgi Bratoev", "Jay Blankenau",
            "Lucas Saatkamp", "Tobias Krick", "Kevin Le Roux", "Robertlandy Simon",
            "Jenia Grebennikov", "Serio Dutra Santos"};
    static String[] posiciones = {"OH", "OH", "OH", "OH", "OH",
            "OPP", "OPP", "OPP", "OPP", "OPP",
            "S", "S", "S", "S",
            "MB", "MB", "MB", "MB",
            "L", "L"};
    static int[] precios = {20000000, 10000000, 10000000, 10000000, 8000000,
            20000000, 20000000, 10000000, 10000000, 10000000,
            25000000, 25000000, 15000000, 15000000,
            15000000, 15000000, 10000000, 10000000,
            5000000, 5000000};

    // Contadores de jugadores fichados por posición
    static int ohFichados = 0;
    static int oppFichados = 0;
    static int sFichados = 0;
    static int mbFichados = 0;
    static int lFichados = 0;

    // Presupuesto total y presupuesto para jugadores de banquillo
    static int presupuesto = 100000000;
    static int presupuestoBanquillo = 30000000;

    // Lista de jugadores fichados y objeto Scanner para entrada del usuario
    static ArrayList<String> jugadoresFichados = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Mensaje de bienvenida y presentación de opciones al usuario
        System.out.println("Bienvenido al Fantasy de jugadores de Volleyball!");
        System.out.println("[A] Mostrar jugadores disponibles.");
        System.out.println("[B] Mostrar plantilla de jugadores fichados.");
        System.out.println("[C] Devolver jugador.");
        System.out.println("[D] Mostrar presupuesto actual.");
        System.out.println("[EXIT] Salir del programa");
        System.out.println("NOTA: También puedes mostrar jugadores por posiciones introduciendo sus siglas (OH, OPP, S, MB, L).");
        System.out.println("Presupuesto: " + presupuesto + "€");

        // Bucle para que el usuario seleccione jugadores hasta completar la plantilla principal
        while (jugadoresFichados.size() <= 6) {
            System.out.println("Introduce el ID del jugador que quieres fichar u otra opción: ");
            String entrada = scanner.nextLine();
            eleccionOpcion(entrada, 1);
        }

        // Mensaje para fichar jugadores de banquillo y mostrar presupuesto de banquillo disponible
        System.out.println("A continuación, ficha a los jugadores de banquillo.\nPresupuesto para jugadores de banquillo: " + presupuestoBanquillo + "€");

        // Bucle para que el usuario seleccione jugadores de banquillo
        while (jugadoresFichados.size() <= 8) {
            System.out.println("Introduce el ID del jugador que quieres fichar u otra opción: ");
            String entrada = scanner.nextLine();
            eleccionOpcion(entrada, 2);
        }

        // Mostrar la plantilla final
        mostrarFichados();
    }

    // Método que gestiona las opciones seleccionadas por el usuario
    public static void eleccionOpcion(String entrada, int modo) {
        int seleccion;

        // Estructura de control switch para manejar diferentes opciones
        switch (entrada) {
            case "A" -> {
                if (modo == 1) {
                    mostrarJugadores(1);
                } else if (modo == 2) {
                    mostrarJugadores(2);
                }
            }
            case "B" -> {
                mostrarFichados();
            }
            case "C" -> {
                devolverJugador();
            }
            case "D" -> {
                if (modo == 1) {
                    verPresupuesto();
                } else if (modo == 2) {
                    verPresupuestoBanquillo();
                }
            }
            case "OH", "OPP", "S", "MB", "L" -> {
                if (modo == 1) {
                    mostrarJugadores(1, entrada);
                } else if (modo == 2) {
                    mostrarJugadores(2, entrada);
                }
            }
            case "EXIT" -> {
                System.exit(1);
            }
            default -> {
                // Convertir la entrada a un número para seleccionar un jugador
                seleccion = Integer.parseInt(entrada) - 1;

                // Fichar jugadores según el modo (regular o banquillo) y la posición
                if (modo == 1) {
                    ficharJugador(seleccion);
                } else if (modo == 2) {
                    ficharJugadoresBanquillo(seleccion);
                }
            }
        }
    }

    // Método para mostrar jugadores disponibles según el modo (regular o banquillo)
    public static void mostrarJugadores(int modo) {
        // Impresión de encabezado de tabla
        System.out.println("-----------------------------------------------------------");
        System.out.println("           Jugador          |       Precio      | Posicion");
        System.out.println("-----------------------------------------------------------");

        // Iterar y eliminar jugadores no disponibles para la compra y mostrar los restantes
        if (modo == 1) {
            for (int i = 0; i < jugadores.length; i++) {
                if (comprobarFichados(i)) continue;
                if (precios[i] <= presupuesto) {
                    System.out.printf("[%2d] \uD83C\uDFD0 %-20s [Precio %-9s€]    %-2s\n", (i + 1), jugadores[i], precios[i], posiciones[i]);
                }
            }
        } else if (modo == 2) {
            for (int i = 0; i < jugadores.length; i++) {
                if (comprobarFichados(i)) continue;
                if (precios[i] <= presupuestoBanquillo) {
                    System.out.printf("[%2d] \uD83C\uDFD0 %-20s [Precio %-9s€]    %-2s\n", (i + 1), jugadores[i], precios[i], posiciones[i]);
                }
            }
        }
    }

    // Mismo metodo pero usando polimorfismo para mostrar jugadores filtrados por posición
    public static void mostrarJugadores(int modo, String posicion) {
        // Impresión de encabezado de tabla
        System.out.println("-----------------------------------------------------------");
        System.out.println("           Jugador          |       Precio      | Posicion");
        System.out.println("-----------------------------------------------------------");

        // Iterar y eliminar jugadores no disponibles para la compra y mostrar los restantes
        if (modo == 1) {
            for (int i = 0; i < jugadores.length; i++) {
                if (comprobarFichados(i)) continue;
                if (precios[i] <= presupuesto && posiciones[i].equals(posicion)) {
                    System.out.printf("[%2d] \uD83C\uDFD0 %-20s [Precio %-9s€]    %-2s\n", (i + 1), jugadores[i], precios[i], posiciones[i]);
                }
            }
        } else if (modo == 2) {
            for (int i = 0; i < jugadores.length; i++) {
                if (comprobarFichados(i)) continue;
                if (precios[i] <= presupuestoBanquillo && posiciones[i].equals(posicion)) {
                    System.out.printf("[%2d] \uD83C\uDFD0 %-20s [Precio %-9s€]    %-2s\n", (i + 1), jugadores[i], precios[i], posiciones[i]);
                }
            }
        }
    }

    // Método para comprobar si un jugador ya ha sido fichado
    public static boolean comprobarFichados(int i) {
        for (int j = 0; j < jugadoresFichados.size(); j++) {
            if (jugadores[i].equals(jugadoresFichados.get(j))) {
                return true;
            }
        }
        return false;
    }

    // Método para fichar jugadores regulares según la posición y presupuesto disponible
    public static void ficharJugador(int seleccion) {
        // Verificar si el jugador ya ha sido fichado
        if (comprobarRepetidos(seleccion)) {
            System.out.println("Ya has fichado a este jugador.");
            return;
        }

        // Fichar jugadores según posición, presupuesto y límite por posición
        if (posiciones[seleccion].equals("OH") && ohFichados < 2 && (presupuesto - precios[seleccion]) >= 0) {
            jugadoresFichados.add(jugadores[seleccion]);
            presupuesto -= precios[seleccion];
            ohFichados++;
        } else if (posiciones[seleccion].equals("OPP") && oppFichados < 2 && (presupuesto - precios[seleccion]) >= 0) {
            jugadoresFichados.add(jugadores[seleccion]);
            presupuesto -= precios[seleccion];
            oppFichados++;
        } else if (posiciones[seleccion].equals("S") && sFichados < 1 && (presupuesto - precios[seleccion]) >= 0) {
            jugadoresFichados.add(jugadores[seleccion]);
            presupuesto -= precios[seleccion];
            sFichados++;
        } else if (posiciones[seleccion].equals("MB") && mbFichados < 1 && (presupuesto - precios[seleccion]) >= 0) {
            jugadoresFichados.add(jugadores[seleccion]);
            presupuesto -= precios[seleccion];
            mbFichados++;
        } else if (posiciones[seleccion].equals("L") && lFichados < 1 && (presupuesto - precios[seleccion]) >= 0) {
            jugadoresFichados.add(jugadores[seleccion]);
            presupuesto -= precios[seleccion];
            lFichados++;
        } else {
            System.out.println("No puedes fichar a este jugador, ya has llenado la posición " + posiciones[seleccion] +  " o no tienes suficiente presupuesto.");
        }
    }

    // Método para fichar jugadores de banquillo según presupuesto disponible
    public static void ficharJugadoresBanquillo(int seleccion) {
        // Verificar si el jugador ya ha sido fichado
        if (comprobarRepetidos(seleccion)) {
            System.out.println("Ya has fichado a este jugador.");
            return;
        }

        // Fichar jugadores de banquillo según presupuesto
        if (presupuestoBanquillo - precios[seleccion] >= 0) {
            jugadoresFichados.add(jugadores[seleccion]);
            presupuestoBanquillo -= precios[seleccion];
        } else {
            System.out.println("No tienes suficiente presupuesto para fichar a este jugador.");
        }
    }

    // Método para devolver un jugador fichado y ajustar presupuesto y contadores
    public static void devolverJugador() {
        int jugadorADevolver = 0;
        int seleccion;

        // Verificar si hay jugadores fichados para devolver
        if (jugadoresFichados.size() <= 0) {
            System.out.println("No tienes jugadores fichados así que no puedes devolver a nadie.");
            return;
        }

        // Mostrar la plantilla de jugadores fichados
        mostrarFichados();

        // Bucle para obtener la selección válida del usuario
        while (true) {
            System.out.println("Introduce el ID del jugador que quieres devolver:");
            seleccion = Integer.parseInt(scanner.nextLine()) - 1;

            // Verificar si la selección es válida
            if (seleccion >= jugadoresFichados.size()) {
                System.out.println("Selección inválida, vuelve a intentarlo.");
            } else {
                break;
            }
        }

        // Encontrar la posición del jugador a devolver en la lista original de jugadores
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadoresFichados.get(seleccion).equals(jugadores[i])) {
                jugadorADevolver = i;
                break;
            }
        }

        // Actualizar contadores y presupuesto al devolver el jugador
        switch (posiciones[jugadorADevolver]) {
            case "OH" -> ohFichados--;
            case "OPP" -> oppFichados--;
            case "S" -> sFichados--;
            case "MB" -> mbFichados--;
            case "L" -> lFichados--;
        }

        // Mostrar mensaje de devolución y ajustar presupuesto
        System.out.println("Has devuelto a " + jugadoresFichados.get(seleccion) + ", obtienes " + precios[jugadorADevolver] + "€");
        presupuesto += precios[jugadorADevolver];
        jugadoresFichados.remove(seleccion);
    }

    // Método para mostrar el presupuesto disponible para jugadores regulares
    public static void verPresupuesto() {
        System.out.println(presupuesto + "€");
    }

    // Método para mostrar el presupuesto disponible para jugadores de banquillo
    public static void verPresupuestoBanquillo() {
        System.out.println(presupuestoBanquillo + "€");
    }

    // Método para verificar si un jugador ya ha sido fichado
    public static boolean comprobarRepetidos(int seleccion) {
        for (String jugadoresFichado : jugadoresFichados) {
            if (jugadoresFichado.equals(jugadores[seleccion])) {
                return true;
            }
        }
        return false;
    }

    // Método para mostrar la plantilla de jugadores fichados
    public static void mostrarFichados() {
        for (int i = 0; i < jugadoresFichados.size(); i++) {
            System.out.println("[" + (i + 1) + "] " +  jugadoresFichados.get(i));
        }
    }
}