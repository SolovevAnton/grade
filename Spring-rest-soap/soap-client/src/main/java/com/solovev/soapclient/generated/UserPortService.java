
package com.solovev.soapclient.generated;

import jakarta.xml.ws.*;

import javax.xml.namespace.QName;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 3.0.2
 * Generated source version: 3.0
 */
@WebServiceClient(name = "UserPortService", targetNamespace = "http://localhost:8080/user", wsdlLocation = "http://localhost:8080/ws/users.wsdl")
public class UserPortService
        extends Service {

    private final static URL USERPORTSERVICE_WSDL_LOCATION;
    private final static WebServiceException USERPORTSERVICE_EXCEPTION;
    private final static QName USERPORTSERVICE_QNAME = new QName("http://localhost:8080/user", "UserPortService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8080/ws/users.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        USERPORTSERVICE_WSDL_LOCATION = url;
        USERPORTSERVICE_EXCEPTION = e;
    }

    public UserPortService() {
        super(__getWsdlLocation(), USERPORTSERVICE_QNAME);
    }

    public UserPortService(WebServiceFeature... features) {
        super(__getWsdlLocation(), USERPORTSERVICE_QNAME, features);
    }

    public UserPortService(URL wsdlLocation) {
        super(wsdlLocation, USERPORTSERVICE_QNAME);
    }

    public UserPortService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, USERPORTSERVICE_QNAME, features);
    }

    public UserPortService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UserPortService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * @return returns UserPort
     */
    @WebEndpoint(name = "UserPortSoap11")
    public UserPort getUserPortSoap11() {
        return super.getPort(new QName("http://localhost:8080/user", "UserPortSoap11"), UserPort.class);
    }

    /**
     * @param features A list of {@link jakarta.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns UserPort
     */
    @WebEndpoint(name = "UserPortSoap11")
    public UserPort getUserPortSoap11(WebServiceFeature... features) {
        return super.getPort(new QName("http://localhost:8080/user", "UserPortSoap11"), UserPort.class, features);
    }

    private static URL __getWsdlLocation() {
        if (USERPORTSERVICE_EXCEPTION != null) {
            throw USERPORTSERVICE_EXCEPTION;
        }
        return USERPORTSERVICE_WSDL_LOCATION;
    }

}
