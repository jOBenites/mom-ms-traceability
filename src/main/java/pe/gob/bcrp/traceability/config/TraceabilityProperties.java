package pe.gob.bcrp.traceability.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "traceability")
public class TraceabilityProperties {

    private boolean enabled = true;
    private boolean restEndpointsEnabled = false;
    private String tablePrefix = "";
    private boolean asyncProcessing = false;

    // Getters and Setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public boolean isRestEndpointsEnabled() { return restEndpointsEnabled; }
    public void setRestEndpointsEnabled(boolean restEndpointsEnabled) { this.restEndpointsEnabled = restEndpointsEnabled; }

    public String getTablePrefix() { return tablePrefix; }
    public void setTablePrefix(String tablePrefix) { this.tablePrefix = tablePrefix; }

    public boolean isAsyncProcessing() { return asyncProcessing; }
    public void setAsyncProcessing(boolean asyncProcessing) { this.asyncProcessing = asyncProcessing; }
}
