package org.test.web.protobuf;


import io.grpc.stub.StreamObserver;

import java.util.Random;

public class TestServiceImpl extends TestServiceGrpc.TestServiceImplBase {

    @Override
    public void call(RpcCall.CallReq request, StreamObserver<RpcCall.CallRsp> responseObserver) {
        RpcCall.CallRsp callRsp = RpcCall.CallRsp.newBuilder().setGreeting("Nice day..." + new Random().nextInt()).build();
        responseObserver.onNext(callRsp);
        responseObserver.onCompleted();
    }
}
