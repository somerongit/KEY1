import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    static final int PORT = 1234;

    private static DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private byte[] buffer;

    public Client(DatagramSocket datagramSocket, InetAddress inetAddress) {
        Client.datagramSocket = datagramSocket;
        this.inetAddress = inetAddress;
    }

    public void sendThenRecive() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String messageToSend = scanner.nextLine();
                buffer = messageToSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, PORT);
                datagramSocket.send(datagramPacket);
                datagramSocket.receive(datagramPacket);
                String messageFromServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("This server says you said: " + messageFromServer);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress inetAddress = InetAddress.getByName("localhost");
        Client client = new Client(datagramSocket, inetAddress);
        System.out.println("Save datagram packets to a server...");
        client.sendThenRecive();
    }

}
