package com.throne.consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ApplicationService {

    private Socket socket;

    public void start() throws IOException {
     //socket start
        socket = new Socket("localhost", 8090);

        InputStream ınputStream = socket.getInputStream();

        //if ()
    }
}
