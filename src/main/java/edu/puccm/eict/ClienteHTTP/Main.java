package edu.puccm.eict.ClienteHTTP;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String url;
        do {
            IO.println("Introduce una URL: ");
            url = scanner.nextLine().trim();


            if(!esValida(url)){
                IO.println("URL invalida");
            }
        } while (!esValida(url));

        scanner.close();

    }

    public static boolean esValida(String url){
        try {
            HttpClient cliente = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

            HttpResponse<Void> response = cliente.send(request, HttpResponse.BodyHandlers.discarding());

            return response.statusCode() >= 200 && response.statusCode() <400;



        } catch (Exception e){
            return false;
        }

    }
}
