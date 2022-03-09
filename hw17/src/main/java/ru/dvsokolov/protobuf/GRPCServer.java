package ru.dvsokolov.protobuf;


import io.grpc.BindableService;
import io.grpc.ServerBuilder;
import ru.dvsokolov.protobuf.service.RealDBServiceImpl;
import ru.dvsokolov.protobuf.service.RemoteDBServiceImpl;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var dbService = new RealDBServiceImpl();
        var remoteDBService = new RemoteDBServiceImpl(dbService);

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService((BindableService) remoteDBService).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
