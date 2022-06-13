package com.cc.library.base.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SocketUtil {

    private String ip;
    private Socket socket;

    public SocketUtil(String ip){
        this.ip = ip;
    }


    public String sentMsg(String msg){
        String result = "";
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip,6666),5000);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes());
            outputStream.flush();
            socket.shutdownOutput();

            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = null;
            StringBuffer buffer = new StringBuffer();
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
            result = buffer.toString();

            br.close();
            outputStream.close();
            socket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }
}
