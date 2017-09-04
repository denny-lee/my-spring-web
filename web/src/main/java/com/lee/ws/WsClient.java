package com.lee.ws;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WsClient {

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
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        String url = "ws://localhost:8080/war/chat";
        StompSessionHandler sessionHandler = new MyStompSessionHandler();
        StompSession session = stompClient.connect(url, sessionHandler).get();
        session.subscribe("/topic/ccdd", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders stompHeaders) {
                return OutMsg.class;
            }

            @Override
            public void handleFrame(StompHeaders stompHeaders, Object o) {

            }
        });
        session.send("/topic/ccdd", "{\"name\":\"baba\"}");
        Thread.sleep(60000);
    }
}
