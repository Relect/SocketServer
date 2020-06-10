import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static PrintWriter out; // поток записи в сокет

    public static void main(String[] args) {
        try {
            while (true) {
                try {
                    server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                    System.out.println("Сервер запущен!"); // хорошо бы серверу
                    //   объявить о своем запуске
                    clientSocket = server.accept(); // accept() будет ждать пока
                    //кто-нибудь не захочет подключиться
                    try { // установив связь и воссоздав сокет для общения с клиентом можно перейти
                        // к созданию потоков ввода/вывода.
                        // теперь мы можем принимать сообщения
                        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        // и отправлять
                        out = new PrintWriter(clientSocket.getOutputStream());

                        String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                        System.out.println(word);
                        if (word != "GET /favicon.ico HTTP/1.1")
                        {
                            Service str = new Service();
                            word = word.substring(word.indexOf(" ") + 1, word.lastIndexOf(" ")+1);
                            System.out.println(str.key(word));
                            System.out.println(str.value(word));
                        }
//                        System.out.println(word);
                        // не долго думая отвечает клиенту
                        out.write("HTTP/1.1 200 OK");
                        out.write("Content-Type: text/html; charset=utf-8");
                        out.write("");
                        out.write("Привет, это Сервер! Подтверждаю, вы написали : " + word + "\n");
                        out.flush(); // выталкиваем все из буфера

                    } finally { // в любом случае сокет будет закрыт
                        clientSocket.close();
                        // потоки тоже хорошо бы закрыть
                        in.close();
                        out.close();
                    }
                } finally {
                    System.out.println("Сервер закрыт!");
                    server.close();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}