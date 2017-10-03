package com.lee.ws;

import com.lee.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WsClient {
    private static final Logger logger = LoggerFactory.getLogger(WsClient.class);

    public static void main(String[] args) throws Exception {
        /*List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.doHandshake(new MyClientHandler(), "ws://localhost:8080/war/myHandler");*/


        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompClient.setMessageConverter(new StringMessageConverter());
        String url = "ws://192.168.0.105:8080/war/dak/msgcenter";
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
//        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
//        headers.add("user", "loly");
        final StompSession session = stompClient.connect(url, sessionHandler).get();
        TaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                session.send("/app/heartbeat", "ping");
            }
        }, 3000);
        stompClient.setTaskScheduler(scheduler);
        session.subscribe("/queue/hello", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                if (o instanceof String) {
                    String m = (String) o;
                    logger.info("received msg : {}", m);
                }
            }
        });
        session.subscribe("/queue/heartbeat", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {
                if (o instanceof String) {
                    String m = (String) o;
                    logger.info("received heartbeat: {}", m);
                }
            }
        });
        System.in.read();
        session.disconnect();
        stompClient.stop();
//        OrderStatus a = new OrderStatus();
//        a.setOrderNo("X0001");
//        a.setOrderStatus("UNDELIVERED");
//        Thread.sleep(5000);
//        session.send("/app/hello/myuserid", a);
//        Thread.sleep(40000);
    }
}
