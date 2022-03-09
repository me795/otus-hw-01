package ru.dvsokolov.protobuf;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ru.dvsokolov.protobuf.clientservice.ValueProcessor;
import ru.dvsokolov.protobuf.generated.Counter;
import ru.dvsokolov.protobuf.generated.RemoteDBServiceGrpc;
import ru.dvsokolov.protobuf.generated.Interval;

import java.util.concurrent.CountDownLatch;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8191;

    public static void main(String[] args) throws InterruptedException {
        var channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        var valueProcessor = new ValueProcessor();


        System.out.println("Let's get start!");

        var thread = new Thread(() -> {
            for (int i = 0; i < 50; i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (valueProcessor) {
                    valueProcessor.calculateCurrentValue();
                }
            }
            Thread.currentThread().interrupt();
        });

        var latch = new CountDownLatch(1);
        var newStub = RemoteDBServiceGrpc.newStub(channel);
        newStub.startCount(Interval.newBuilder().setStart(0).setEnd(30).build(), new StreamObserver<Counter>() {
            @Override
            public void onNext(Counter c) {
                synchronized (valueProcessor) {
                    System.out.printf("{Server value: %d}%n",
                            c.getValue());
                    valueProcessor.setCurrentServerValue(c.getValue());
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                System.out.println("\n\nThat's all folks!");
                latch.countDown();
            }
        });

        thread.start();

        latch.await();

        channel.shutdown();
        thread.join();
    }
}
