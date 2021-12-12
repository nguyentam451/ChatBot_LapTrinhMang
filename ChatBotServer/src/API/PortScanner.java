package API;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortScanner {

    public static String quetport(String inputScanner,int x, int y) {
        String res = "";
        try {

            final ExecutorService es = Executors.newCachedThreadPool();
        //    System.out.print("Please input the ip address you would like to scan for open ports: ");
        //    Scanner inputScanner = new Scanner(System.in);

        //    final String ip = inputScanner.nextLine();
            final String ip = inputScanner;
            final int timeout = 200;
            final List<Future<ScanResult>> futures = new ArrayList<>();
            for (int port = x; port <= y; port++) {
                // for (int port = 1; port <= 80; port++) {
                futures.add(portIsOpen(es, ip, port, timeout));
            }
            es.awaitTermination(200L, TimeUnit.MILLISECONDS);
            int openPorts = 0;
            for (final Future<ScanResult> f : futures) {
                try {
                    if (f.get().isOpen()) {
                        openPorts++;
                        // System.out.println(f.get().getPort());
                        res += String.valueOf(f.get().getPort()) + '\n';
                        //System.out.println(res);
                    }
                } catch (ExecutionException ex) {
                    // Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);
                }
            }
          //  System.out.println("There are " + openPorts + " open ports on host " + ip + " (probed with a timeout of "
          //          + timeout + "ms)");
            res += "Có " + openPorts + " được mở trên host " + ip + " (probed with a timeout of "
                    + timeout + "ms)";
            es.shutdown();
            //  return null;
        } catch (InterruptedException ex) {
            Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        // return null;
        return res;
    }
    

    public static void main(final String... args) throws InterruptedException, ExecutionException {
       // System.out.println(quetport("127.0.0.1",5000, 60000));
        
    }

    public static Future<ScanResult> portIsOpen(final ExecutorService es, final String ip, final int port,
            final int timeout) {
        return es.submit(new Callable<ScanResult>() {
            @Override
            public ScanResult call() {
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(ip, port), timeout);
                    socket.close();
                    return new ScanResult(port, true);
                } catch (Exception ex) {
                    return new ScanResult(port, false);
                }
            }
        });
    }

    public static class ScanResult {

        private int port;

        private boolean isOpen;

        public ScanResult(int port, boolean isOpen) {
            super();
            this.port = port;
            this.isOpen = isOpen;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean isOpen) {
            this.isOpen = isOpen;
        }

    }
}
