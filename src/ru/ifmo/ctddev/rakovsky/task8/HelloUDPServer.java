package ru.ifmo.ctddev.rakovsky.task8;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class HelloUDPServer {

    private int port;

    private final static String QUIT_CMD = "quit";
    HelloUDPServer(int port) {
        this.port = port;
    }

    public void runServer() {
       try (DatagramSocket socket = new DatagramSocket(port)) {
            while (true) {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String cmd = new String(packet.getData()).trim();
                if (cmd.equals(QUIT_CMD)) {
                    break;
                }
                cmd = "Hello, " + cmd;
                buf = cmd.getBytes("UTF-8");
                DatagramPacket sendPacket = new DatagramPacket(buf, 0, packet.getAddress(), packet.getPort());
                sendPacket.setData(buf);
                sendPacket.setLength(cmd.length());
                socket.send(sendPacket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HelloUDPServer server = new HelloUDPServer(Integer.parseInt(args[0]));
        server.runServer();
    }
}


