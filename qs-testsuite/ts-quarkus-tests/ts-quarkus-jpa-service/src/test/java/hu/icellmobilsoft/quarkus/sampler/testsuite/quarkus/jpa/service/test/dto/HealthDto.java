package hu.icellmobilsoft.quarkus.sampler.testsuite.quarkus.jpa.service.test.dto;

/**
 * Health Dto
 */
public class HealthDto {

    /**
     * Status
     */
    private String status;

    /**
     * Default Constructor that the yasson can init object
     */
    public HealthDto() {
    }

    /**
     * Getter for status
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for status
     *
     * @param status
     *            status
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
