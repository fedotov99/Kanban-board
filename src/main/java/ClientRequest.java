import java.io.Serializable;

public class ClientRequest implements Serializable {
    Object data;
    RequestType requestType;

    public ClientRequest() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }
}
