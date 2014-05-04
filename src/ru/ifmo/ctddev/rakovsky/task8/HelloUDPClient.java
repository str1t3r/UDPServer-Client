package ru.ifmo.ctddev.rakovsky.task8;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class HelloUDPClient {

    public HelloUDPClient(String ip, int port, String cmd) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runner(i, ip, port, cmd)).start();
        }
    }

    private class Runner implements Runnable {
        private String ip;
        private int port;
        private String cmd;

        Runner(int id, String ip, int port, String cmd) {
            this.ip = ip;
            this.port = port;
            this.cmd = cmd + "_" + String.valueOf(id);
        }

        @Override
        public void run() {
            try (DatagramSocket socket = new DatagramSocket()) {
                int i = 0;
                while (true) {
                    byte[] buf = new byte[1024];
                    String sendCmd = cmd + "_" + i;
                    byte[] cmdBytes = sendCmd.getBytes("UTF-8");
                    DatagramPacket sendPacket = new DatagramPacket(cmdBytes, cmdBytes.length, InetAddress.getByName(ip), port);
                    socket.send(sendPacket);
                    DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
                    socket.receive(receivePacket);
                    String received = new String(receivePacket.getData()).trim();
                    System.out.println(received);
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
            HelloUDPClient client = new HelloUDPClient(args[0], Integer.parseInt(args[1]), args[2]);
    }
}
