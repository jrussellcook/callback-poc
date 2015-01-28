package org.kuali.student.enrollment.courseoffering.service.cxf;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingCallbackNamespaceConstants;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingSubscriptionService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class was generated by Apache CXF 2.7.12
 * 2014-08-25T09:43:06.079-04:00
 * Generated source version: 2.7.12
 * 
 */
@WebServiceClient(name = "SOAPService", 
                  wsdlLocation = "file:/src/main/resources/basic_callback.wsdl",
                  targetNamespace = CourseOfferingCallbackNamespaceConstants.NAMESPACE)
public class SOAPService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName(CourseOfferingCallbackNamespaceConstants.NAMESPACE, "SOAPService");
    public final static QName SOAPPort = new QName(CourseOfferingCallbackNamespaceConstants.NAMESPACE, "SOAPPort");
    static {
        URL url = null;
        try {
            url = new URL("file:/src/main/resources/basic_callback.wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(SOAPService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "file:/src/main/resources/basic_callback.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public SOAPService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SOAPService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SOAPService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns CourseOfferingSubscriptionService
     */
    @WebEndpoint(name = "SOAPPort")
    public CourseOfferingSubscriptionService getSOAPPort() {
        return super.getPort(SOAPPort, CourseOfferingSubscriptionService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CourseOfferingSubscriptionService
     */
    @WebEndpoint(name = "SOAPPort")
    public CourseOfferingSubscriptionService getSOAPPort(WebServiceFeature... features) {
        return super.getPort(SOAPPort, CourseOfferingSubscriptionService.class, features);
    }

}