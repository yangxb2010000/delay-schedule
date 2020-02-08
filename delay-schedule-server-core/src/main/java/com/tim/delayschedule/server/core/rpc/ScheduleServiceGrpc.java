package com.tim.delayschedule.server.core.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.27.0)",
    comments = "Source: scheduleServerGrpc.proto")
public final class ScheduleServiceGrpc {

  private ScheduleServiceGrpc() {}

  public static final String SERVICE_NAME = "ScheduleService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest,
      com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply> getPushTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PushTask",
      requestType = com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest.class,
      responseType = com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest,
      com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply> getPushTaskMethod() {
    io.grpc.MethodDescriptor<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest, com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply> getPushTaskMethod;
    if ((getPushTaskMethod = ScheduleServiceGrpc.getPushTaskMethod) == null) {
      synchronized (ScheduleServiceGrpc.class) {
        if ((getPushTaskMethod = ScheduleServiceGrpc.getPushTaskMethod) == null) {
          ScheduleServiceGrpc.getPushTaskMethod = getPushTaskMethod =
              io.grpc.MethodDescriptor.<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest, com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PushTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleServiceMethodDescriptorSupplier("PushTask"))
              .build();
        }
      }
    }
    return getPushTaskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest,
      com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply> getReleaseSlotMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ReleaseSlot",
      requestType = com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest.class,
      responseType = com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest,
      com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply> getReleaseSlotMethod() {
    io.grpc.MethodDescriptor<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest, com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply> getReleaseSlotMethod;
    if ((getReleaseSlotMethod = ScheduleServiceGrpc.getReleaseSlotMethod) == null) {
      synchronized (ScheduleServiceGrpc.class) {
        if ((getReleaseSlotMethod = ScheduleServiceGrpc.getReleaseSlotMethod) == null) {
          ScheduleServiceGrpc.getReleaseSlotMethod = getReleaseSlotMethod =
              io.grpc.MethodDescriptor.<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest, com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ReleaseSlot"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply.getDefaultInstance()))
              .setSchemaDescriptor(new ScheduleServiceMethodDescriptorSupplier("ReleaseSlot"))
              .build();
        }
      }
    }
    return getReleaseSlotMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ScheduleServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceStub>() {
        @Override
        public ScheduleServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleServiceStub(channel, callOptions);
        }
      };
    return ScheduleServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ScheduleServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceBlockingStub>() {
        @Override
        public ScheduleServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleServiceBlockingStub(channel, callOptions);
        }
      };
    return ScheduleServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ScheduleServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ScheduleServiceFutureStub>() {
        @Override
        public ScheduleServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ScheduleServiceFutureStub(channel, callOptions);
        }
      };
    return ScheduleServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ScheduleServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void pushTask(com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest request,
        io.grpc.stub.StreamObserver<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply> responseObserver) {
      asyncUnimplementedUnaryCall(getPushTaskMethod(), responseObserver);
    }

    /**
     */
    public void releaseSlot(com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest request,
        io.grpc.stub.StreamObserver<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply> responseObserver) {
      asyncUnimplementedUnaryCall(getReleaseSlotMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPushTaskMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest,
                com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply>(
                  this, METHODID_PUSH_TASK)))
          .addMethod(
            getReleaseSlotMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest,
                com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply>(
                  this, METHODID_RELEASE_SLOT)))
          .build();
    }
  }

  /**
   */
  public static final class ScheduleServiceStub extends io.grpc.stub.AbstractAsyncStub<ScheduleServiceStub> {
    private ScheduleServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ScheduleServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleServiceStub(channel, callOptions);
    }

    /**
     */
    public void pushTask(com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest request,
        io.grpc.stub.StreamObserver<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPushTaskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void releaseSlot(com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest request,
        io.grpc.stub.StreamObserver<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getReleaseSlotMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ScheduleServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ScheduleServiceBlockingStub> {
    private ScheduleServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ScheduleServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply pushTask(com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest request) {
      return blockingUnaryCall(
          getChannel(), getPushTaskMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply releaseSlot(com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest request) {
      return blockingUnaryCall(
          getChannel(), getReleaseSlotMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ScheduleServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ScheduleServiceFutureStub> {
    private ScheduleServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected ScheduleServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ScheduleServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply> pushTask(
        com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPushTaskMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply> releaseSlot(
        com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getReleaseSlotMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PUSH_TASK = 0;
  private static final int METHODID_RELEASE_SLOT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ScheduleServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ScheduleServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PUSH_TASK:
          serviceImpl.pushTask((com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskRequest) request,
              (io.grpc.stub.StreamObserver<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.PushTaskReply>) responseObserver);
          break;
        case METHODID_RELEASE_SLOT:
          serviceImpl.releaseSlot((com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotRequest) request,
              (io.grpc.stub.StreamObserver<com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.ReleaseSlotReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ScheduleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ScheduleServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.tim.delayschedule.server.core.rpc.ScheduleServerGrpc.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ScheduleService");
    }
  }

  private static final class ScheduleServiceFileDescriptorSupplier
      extends ScheduleServiceBaseDescriptorSupplier {
    ScheduleServiceFileDescriptorSupplier() {}
  }

  private static final class ScheduleServiceMethodDescriptorSupplier
      extends ScheduleServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ScheduleServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ScheduleServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ScheduleServiceFileDescriptorSupplier())
              .addMethod(getPushTaskMethod())
              .addMethod(getReleaseSlotMethod())
              .build();
        }
      }
    }
    return result;
  }
}
