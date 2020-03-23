package client.client.config;

public enum CodeStatus {
    CREATED(201),
    OK(200),
    CONFLICT(409),
    NOT_FOUND(404),
    UNAUTHORIZED(401);

    public final int getCode() {
        return code;
    }

    private final int code;
    CodeStatus(int code){
        this.code =code;

    }




}
