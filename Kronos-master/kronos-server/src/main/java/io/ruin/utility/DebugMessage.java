package io.ruin.utility;

public class DebugMessage {

    private final StringBuilder sb = new StringBuilder();

    public DebugMessage add(String name, Object value) {
        sb.append(name).append("=").append(value).append("  ");
        return this;
    }

    public String toString() {
        return sb.toString().trim();
    }

}
