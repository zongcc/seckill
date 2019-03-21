package com.zongcc.nio;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author chunchengzong
 * @date 2019-01-07 15:50
 **/
public class SelectorTest {
    Process server;
    SelectorClient client;

    @Before
    public void setup() throws IOException, InterruptedException {
        server = SelectorService.start();
        client = SelectorClient.start();
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }

    @After
    public void teardown() throws IOException {
        server.destroy();
        server.destroyForcibly();
        SelectorClient.stop();
    }
}