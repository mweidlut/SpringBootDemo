package org.test.web.protobuf;


import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

    public static void main(String[] args) throws Exception {
        GrpcServer grpcServer = new GrpcServer();
        grpcServer.start();
    }

    private void start() throws Exception {
        int port = 50051;
        //这个部分启动server
        Server server = ServerBuilder.forPort(port)
                .addService(new TestServiceImpl())
                .build();

        server.start();
        server.awaitTermination();
    }
}
