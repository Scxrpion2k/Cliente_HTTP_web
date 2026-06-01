package edu.puccm.eict.ClienteHTTP;


import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String url;
        String tipo;
        do {
            IO.println("Introduce una URL: ");
            url = scanner.nextLine().trim();


            if(!esValida(url)){
                IO.println("URL invalida");
            }

            Connection.Response response = Jsoup.connect(url).ignoreContentType(true).execute();
            tipo = response.contentType();
            IO.println("El tipo es: "+tipo);

            if(tipo != null && tipo.startsWith("text/html")){

                return;
            };


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
