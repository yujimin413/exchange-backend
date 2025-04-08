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
    
    // ==================================================================
    // Login (01)

    /**
     * SSU2010
     */
    public final String SSU2010 = "2010";
    public final String SSU2010_MSG = "Login success";

    /**
     * SSU4010
     */
    public final String SSU4010 = "4010";
    public final String SSU4010_MSG = "Failed to authenticate with uSaint";

    // ==================================================================

    // ==================================================================
    // Profile (02)

    /**
     * SSU2020
     */
    public final String SSU2020 = "SSU2020";
    public final String SSU2020_MSG = "Get profile success";

    //
    // ==================================================================
}
