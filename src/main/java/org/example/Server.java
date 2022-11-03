import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8484);) {
            System.out.println("Сервер запущен!");
            System.out.println("Давай сыграем в 'Игру в города'! Напиши название любого города:");
            String currentCity = null;
            char lastCharacter = '\u0000';
            char firstCharacter = '\u0000';

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String newCity;

                    if (currentCity == null) {
                        out.println("???");
                        newCity = in.readLine();
                        out.println("OK");
                        currentCity = newCity;
                        System.out.println("Текущее название города: " + currentCity);
                    } else {
                        out.println(currentCity);
                        newCity = in.readLine();
                        firstCharacter = newCity.toLowerCase().charAt(0);
                        lastCharacter = currentCity.toLowerCase().charAt(currentCity.length() - 1);
                        if (firstCharacter == lastCharacter) {
                            currentCity = newCity;
                            out.println("OK");
                            System.out.println("Текущее название города: " + currentCity);
                        } else {
                            out.println("NOT OK");
                            System.out.println("Название города было введено неверно, текущее название города: " + currentCity);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}