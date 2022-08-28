package advisor.model.data;

import org.junit.jupiter.api.Test;

class ServerDataTest {

    @Test
    void getJsonObjectWithError() {
        System.out.println(ServerData.getJsonObjectWithError("msg"));
    }
}