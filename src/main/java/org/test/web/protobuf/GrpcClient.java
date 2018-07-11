package org.test.web.protobuf;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.UUID;

public class GrpcClient {

    public static void main(String[] args) {
        RpcCall.CallReq req = RpcCall.CallReq.newBuilder().setReqSn(UUID.randomUUID().toString()).build();

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        TestServiceGrpc.TestServiceBlockingStub testServiceBlockingStub = TestServiceGrpc.newBlockingStub(channel);

        RpcCall.CallRsp rsp = testServiceBlockingStub.call(req);

        System.out.println(rsp);
    }

}
