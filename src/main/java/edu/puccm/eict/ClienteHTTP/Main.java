package edu.puccm.eict.ClienteHTTP;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
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

            HttpClient cliente = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            tipo = response.headers().firstValue("Content-Type").orElse("");

            String body = response.body();

            Document document = Jsoup.parse(body,url);

            String html = document.toString();


            IO.println("\n");
            IO.println("1.Tipo de archivo: "+tipo);

            if(!tipo.isEmpty() && tipo.startsWith("text/html")){
                IO.println("2.Total de lineas: "+ totalLineas(html));
                IO.println("3.Total parrafos: "+totalParrafos(document));
                IO.println("4.Total de imagenes en parrafos: "+ totalImagenesParrafos(document));
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

    public static int totalLineas(String html) throws IOException {
        return html.split("\\R").length;
    }

    public static int totalParrafos(Document document) throws IOException {
        Elements parrafos = document.select("p");
        return parrafos.size();
    }

    public static int totalImagenesParrafos(Document document){
        Elements imagenes = document.select("p img");
        return imagenes.size();
    }


}
