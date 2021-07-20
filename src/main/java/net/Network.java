package net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import sample.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class Network {
    private SocketChannel channel;
    private static String HOST;
    private static int PORT;
    private File file = new File(getClass().getClassLoader().getResource("host.txt").getFile());
    private Scanner scanner;

    public Network(Callback onMessageReceivedCallback) {
        try {
            scanner = new Scanner(file);
            String hostPort = scanner.nextLine();
            HOST = hostPort.substring(0,hostPort.indexOf(":"));
            PORT = Integer.parseInt(hostPort.substring(hostPort.indexOf(":")+1));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Thread t = new Thread(() -> {
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline()
                                        .addLast(
                                                new LineBasedFrameDecoder(1024*50),
                                                new StringDecoder(StandardCharsets.UTF_8),
                                                new StringEncoder(StandardCharsets.UTF_8),
                                                new ClientHandler(onMessageReceivedCallback)
                                                );
                            }
                        });
                ChannelFuture future = b.connect(HOST, PORT).sync();
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        });
        t.start();
    }

    public void close() {
        channel.close();
    }

    public void sendMessage(String str) {
        channel.writeAndFlush(str+"\n");
    }
}