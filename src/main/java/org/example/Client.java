import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket("127.0.0.1", 8484);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String currentCity = in.readLine();
            if (currentCity.equals("???")) {
                System.out.println("Введите название города:");
            } else {
                char lastCharacter = currentCity.toLowerCase().charAt(currentCity.length() - 1);
                System.out.println("Текущее название города: " + currentCity);
                System.out.println("Введите город, название которого начинается на букву '" + lastCharacter + "'");
            }
            Scanner scanner = new Scanner(System.in);
            String newCity = scanner.nextLine();
            out.println(newCity);
            currentCity = in.readLine();

            if (currentCity.equals("OK")) {
                System.out.println("Название города принято");
            } else if (currentCity.equals("NOT OK")) {
                System.out.println("Вы ввели неверное название города!");
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}