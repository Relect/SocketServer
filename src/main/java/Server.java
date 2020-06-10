import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static PrintWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен!"); // хорошо бы серверу
                while (true) {
                    //   объявить о своем запуске
                    //сокет для общения
                    Socket clientSocket = server.accept(); // accept() будет ждать пока
                    System.out.println("Клиент подключился!");
                    //кто-нибудь не захочет подключиться
                    try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                        // к созданию потоков ввода/вывода.
                        // теперь мы можем принимать сообщения
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        // и отправлять
                        out = new PrintWriter(clientSocket.getOutputStream());
                        System.out.println("Получение данных...");
                        String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                        if (word.contains("my_api")) {
                            System.out.println("Обработка данных...");
                            word = word.substring(word.indexOf(" ") + 1, word.lastIndexOf(" ") + 1);
                            System.out.println(Service.key(word));
                            System.out.println(Service.value(word));

                            // не долго думая отвечает клиенту
                            out.write("HTTP/1.1 200 OK\r\n");
                            out.write("Content-Type: text/html; charset=utf-8\r\n");
                            out.write("\r\n");
                            out.write("Привет, это Сервер! Подтверждаю, вы написали : " + word + "\r\n");
                            out.flush(); // выталкиваем все из буфера
                        } else {
                            System.out.println("Bad Request:" + word);
                        }
                    } finally {
                        // в любом случае сокет будет закрыт
                        // потоки тоже хорошо бы закрыть
                        in.close();
                        out.close();
                        clientSocket.close();
                        System.out.println("Клиент отключён");
                    }
                }
            } finally {
                System.out.println("Сервер закрыт!");
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}