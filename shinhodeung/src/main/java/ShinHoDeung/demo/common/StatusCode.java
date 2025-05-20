package ShinHoDeung.demo.common;

import org.springframework.stereotype.Component;

@Component
public class StatusCode {
    // ==================================================================
    // Global
    
    public final String SSU2000 = "2000";
    public final String SSU2000_MSG = "OK";

    public final String SSU4000 = "4000";
    public final String SSU4000_MSG = "Bad Request";

    public final String SSU4001 = "4001";
    public final String SSU4001_MSG = "Unauthorized";

    public final String SSU4003 = "4003";
    public final String SSU4003_MSG = "Forbidden";

    public final String SSU4004 = "4004";
    public final String SSU4004_MSG = "Not found";

    public final String SSU5000 = "5000";
    public final String SSU5000_MSG = "Internal Server Error";
    
    // ==================================================================
}
