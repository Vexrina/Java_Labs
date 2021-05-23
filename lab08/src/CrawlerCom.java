import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class Crawler {

    // непросмотренные сайты
    static LinkedList<URLDepthPair> unviewed = new LinkedList<URLDepthPair>();
    // просмотренные сайты
    static LinkedList<URLDepthPair> viewedLink = new LinkedList<URLDepthPair>();

    // вывод результата в консольку
    public static void showResult(LinkedList<URLDepthPair> viewedLink) {
        for (URLDepthPair c : viewedLink)
            System.out.println("Depth : " + c.getDepth() + "\tLink : " + c.getURL());
    }


    // запрос сайита
    public static void request(PrintWriter out, URLDepthPair pair) throws MalformedURLException {//throws - метод может вызвать исключение
        out.println("GET " + pair.getPath() + " HTTP/1.1");
        out.println("Host: " + pair.getHost());
        out.println("Connection: close");
        out.println();
        out.flush();
    }

    public static void Process(String pair, int maxDepth) throws IOException {
        // добавляем первый сайт
        unviewed.add(new URLDepthPair(pair, 0));

        // пока список не кончится
        while (!unviewed.isEmpty()) {
            // работаем с первым из списка ЮРЛ и удаляем его из непросмотренных
            URLDepthPair currentPair = unviewed.removeFirst();

            // пока не упремся в предел по глубине
            if (currentPair.depth < maxDepth) {

                // открываем сокет
                Socket my_socket = new Socket(currentPair.getHost(), 80);
                my_socket.setSoTimeout(1000);
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(my_socket.getInputStream()));
                    PrintWriter out = new PrintWriter(my_socket.getOutputStream(), true);
                    // запрышиваем сайт
                    request(out, currentPair);
                    String line;

                    // пока сервер не прекратит присылать строки
                    while ((line = in.readLine()) != null) {
                        // проверка на наличие ссылок в строке
                        if (line.indexOf(currentPair.URL_PREFIX) != -1 && line.indexOf('"') != -1) {

                            StringBuilder currentLink = new StringBuilder();
                            // индекс, с которого начинается ссылка
                            int i = line.indexOf(currentPair.URL_PREFIX);
                            // перебор
                            while (line.charAt(i) != '"' && line.charAt(i) != ' ') {
                                if (line.charAt(i) == '<') {
                                    currentLink.deleteCharAt(currentLink.length() - 1);
                                    break;
                                } else {
                                    currentLink.append(line.charAt(i));
                                    i++;
                                }
                            }
                            // новая пара из найденной ссылки
                            URLDepthPair newPair = new URLDepthPair(currentLink.toString(), currentPair.depth + 1);
                            // если ссылки нет ни в одном списке и не равен предыдущей ссылке
                            if (URLDepthPair.check(unviewed, newPair) && URLDepthPair.check(viewedLink, newPair) && !currentPair.URL.equals(newPair.URL))
                                unviewed.add(newPair);
                        }
                    }
                    my_socket.close();
                } catch (SocketTimeoutException e) {
                    my_socket.close();
                }
            }

            viewedLink.add(currentPair);
        }
        // вывод резов в консоль
        showResult(viewedLink);
    }

    public static void main(String[] args) {
        String[] arg = new String[]{"http://www.google.com/", "50"};
        try {
            Process(arg[0], Integer.parseInt(arg[1]));
        } catch (NumberFormatException | IOException e) {
            System.out.println("usage: java crawler " + arg[0] + " " + arg[1]);
        }
    }
}