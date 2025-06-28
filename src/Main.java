package src;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        // Paso 1. Crear el cliente
        HttpClient client = HttpClient.newHttpClient();

        // Paso 2. Crear la solicitud
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/1ca2cb35a6d217604bdcbc6b/latest/USD"))
                .build();

        // Paso 3. Obtener la respuesta de la API
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        // Verificar que la respuesta fue exitosa
        if (!json.get("result").getAsString().equals("success")) {
            System.out.println("Error al obtener los datos de la API.");
            return;
        }

        // Paso 4. Obtener las tasas de conversión desde "conversion_rates"
        JsonObject rates = json.getAsJsonObject("conversion_rates");

        double tasaUSD_ARS = rates.get("ARS").getAsDouble();
        double tasaUSD_BRL = rates.get("BRL").getAsDouble();
        double tasaUSD_COP = rates.get("COP").getAsDouble();

        // Paso 5. Iniciar menú interactivo
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("******************************************************");
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("║          Bienvenido al conversor de monedas      ║");
            System.out.println("╚══════════════════════════════════════════════════╝");

            System.out.println("1. DÓLAR a PESO ARGENTINO");
            System.out.println("2. PESO ARGENTINO a DÓLAR");
            System.out.println("3. DÓLAR a REAL BRASILEÑO");
            System.out.println("4. REAL BRASILEÑO a DÓLAR");
            System.out.println("5. DÓLAR a PESO COLOMBIANO");
            System.out.println("6. PESO COLOMBIANO a DÓLAR");
            System.out.println("7. SALIR");
            System.out.println(" ");
            System.out.print("Elige una opción:   ");
            opcion = scanner.nextInt();

            if (opcion >= 1 && opcion <= 6) {
                System.out.println(" ");
                System.out.print("Ingresa el valor que deseas convertir: ");
                double monto = scanner.nextDouble();
                double resultado = 0;

                switch (opcion) {
                    case 1:
                        resultado = monto * tasaUSD_ARS;
                        System.out.println("Resultado: $" + String.format("%.2f", resultado) + " ARS");
                        break;
                    case 2:
                        resultado = monto / tasaUSD_ARS;
                        System.out.println("Resultado: $" + String.format("%.2f", resultado) + " USD");
                        break;
                    case 3:
                        resultado = monto * tasaUSD_BRL;
                        System.out.println("Resultado: R$" + String.format("%.2f", resultado) + " BRL");
                        break;
                    case 4:
                        resultado = monto / tasaUSD_BRL;
                        System.out.println("Resultado: $" + String.format("%.2f", resultado) + " USD");
                        break;
                    case 5:
                        resultado = monto * tasaUSD_COP;
                        System.out.println("Resultado: $" + String.format("%.2f", resultado) + " COP");
                        break;
                    case 6:
                        resultado = monto / tasaUSD_COP;
                        System.out.println("Resultado: $" + String.format("%.2f", resultado) + " USD");
                        break;
                }
            } else if (opcion != 7) {
                System.out.println("Opción inválida, intenta de nuevo.");
            }

        } while (opcion != 7);

        System.out.println("¡Gracias por usar el conversor!");
        scanner.close();
    }
}