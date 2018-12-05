//package org.navychi.framework.product.manage.netty;
//
//import com.alibaba.dubbo.remoting.transport.netty4.NettyServerHandler;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.PooledByteBufAllocator;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.util.concurrent.DefaultThreadFactory;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class NettyServer {
//
//    public static void main(String[] args) {
//
//        ServerBootstrap bootstrap = new ServerBootstrap();
//
//        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NettyServerBoss", true));
//        EventLoopGroup workerGroup = new NioEventLoopGroup(10, new DefaultThreadFactory("NettyServerWorker", true));
//
//        try {
//            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//                    .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
//                    .childOption(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
//                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
//                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
//
//                        @Override
//                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                            nioSocketChannel.pipeline().addLast("handler", new NettyServerHandler());
//                        }
//                    });
//
//            ChannelFuture channelFuture = bootstrap.bind(19999).sync();
//            channelFuture.channel().closeFuture().sync();
//
//        } catch (Exception e) {
//            log.error(e.getCause().getMessage());
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }
//    }
//
//}
