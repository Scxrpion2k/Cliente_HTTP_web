package edu.puccm.eict.ClienteHTTP;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
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
            IO.println("\n");
            IO.println("1.Tipo de archivo: "+tipo);

            if(tipo != null && tipo.startsWith("text/html")){
                IO.println("2.Total de lineas: "+ totalLineas(url));





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

    public static int totalLineas(String url) throws IOException {
        String html = Jsoup.connect(url).ignoreContentType(true).execute().body();
        return html.split("\\R").length;
    }
}
