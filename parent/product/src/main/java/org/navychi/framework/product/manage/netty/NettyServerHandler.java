//package org.navychi.framework.product.manage.netty;
//
//import io.netty.channel.ChannelDuplexHandler;
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelPromise;
//import io.netty.util.ReferenceCountUtil;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@ChannelHandler.Sharable
//public class NettyServerHandler extends ChannelDuplexHandler {
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("channelActive");
//        super.channelActive(ctx);
//    }
//
//    @Override
//    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
//        log.info("disconnect");
//        super.disconnect(ctx, promise);
//    }
//
//    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        log.info("write");
//        super.write(ctx, msg, promise);
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        log.info("channelInactive");
//        super.channelInactive(ctx);
//    }
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.info("channelRead");
//        super.channelRead(ctx, msg);
//        ReferenceCountUtil.release(msg);
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        log.info("exceptionCaught");
//        super.exceptionCaught(ctx, cause);
//    }
//}
