package server;

import server.Server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ServerState {
    Stopped(true, "Run"),
    Running(true, "Maintenance", "Stop"),
    Maintenance(true, "Run", "Stop");

    final List<String> inputs;
    final static Map<String, ServerState> map = new HashMap<String, ServerState>();
    final boolean explicit;

    ServerState(boolean expression, String... in) {
        inputs = Arrays.asList(in);
        explicit = expression;
    }

    public ServerState nextState(String input, ServerState current) {
        if (inputs.contains(input)) {
            return map.getOrDefault(input, current);
        }
        return current;
    }
}
