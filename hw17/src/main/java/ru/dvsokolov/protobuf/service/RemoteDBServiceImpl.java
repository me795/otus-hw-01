package ru.dvsokolov.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.dvsokolov.protobuf.generated.Counter;
import ru.dvsokolov.protobuf.generated.Interval;
import ru.dvsokolov.protobuf.generated.RemoteDBServiceGrpc;

import java.util.List;

public class RemoteDBServiceImpl extends RemoteDBServiceGrpc.RemoteDBServiceImplBase {

    private final RealDBService realDBService;

    public RemoteDBServiceImpl(RealDBService realDBService) {
        this.realDBService = realDBService;
    }

    @Override
    public void startCount(Interval request, StreamObserver<Counter> responseObserver) {
        List<Long> longList = realDBService.startCount(request.getStart(), request.getEnd());
        longList.forEach(l -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(getCounterObject(l));
        });
        responseObserver.onCompleted();
    }

    private Counter getCounterObject(Long l) {
        return Counter.newBuilder()
                .setValue(l)
                .build();
    }
}
