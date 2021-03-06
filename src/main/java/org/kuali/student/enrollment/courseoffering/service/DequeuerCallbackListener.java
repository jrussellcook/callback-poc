/**
 * Copyright 2014 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 */
package org.kuali.student.enrollment.courseoffering.service;

import org.kuali.student.enrollment.academicrecord.service.SubscriptionActionEnum;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.jws.WebParam;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

/**
 *
 * @author Kuali Student Team
 */
@javax.jws.WebService(serviceName = "SOAPService",
        portName = "SOAPPort",
        targetNamespace = CourseOfferingCallbackNamespaceConstants.NAMESPACE,
        endpointInterface = "org.kuali.student.enrollment.courseoffering.service.CourseOfferingSubscriptionService")
public class DequeuerCallbackListener implements CourseOfferingSubscriptionService, MessageListener {
    private static final Logger log = LoggerFactory.getLogger(DequeuerCallbackListener.class);

    @Resource
    CourseOfferingCallbackService courseOfferingCallbackService;

    public DequeuerCallbackListener() {
        super();
    }

    public DequeuerCallbackListener(CourseOfferingCallbackService courseOfferingCallbackService) {
        this.courseOfferingCallbackService = courseOfferingCallbackService;
    }

    public void initSubscription() {
        Endpoint.publish("http://localhost:9000/SoapContext/SoapPort", this);
        log.info("DequeuerCallbackListener published itself as a subscription endpoint");
    }

    private static class Selector {
        String offeringId;
        String termId;
        String code;
        String offeringTypeKey;
        SubscriptionActionEnum action;
        CourseOfferingCallbackService callback;

        public Selector(SubscriptionActionEnum action, CourseOfferingCallbackService callback) {
            this.action = action;
            this.callback = callback;
        }
    }
    private final transient Map<String, Selector> callbacks
            = new LinkedHashMap<String, Selector>();

    @Override
    public String subscribeToCourseOfferings(SubscriptionActionEnum action, W3CEndpointReference callbackObject,
                                             ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("operation has not been implemented");
    }

    @Override
    public String subscribeToCourseOfferingsByIds(SubscriptionActionEnum action,
                                                  List<String> courseOfferingIds,
                                                  W3CEndpointReference callbackObject,
                                                  ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        throw new OperationFailedException("operation has not been implemented");
    }

    @Override
    public String subscribeToActivityOfferings(
            SubscriptionActionEnum action,
            W3CEndpointReference callbackObject,
            ContextInfo contextInfo) {
        WebServiceFeature[] wfs = new WebServiceFeature[] {};
        CourseOfferingCallbackService port = callbackObject.getPort(CourseOfferingCallbackService.class, wfs);

        String id = null;
        try {
            id = UUID.randomUUID().toString();
            Selector selector = new Selector(action, port);
            callbacks.put(id, selector);
            log.info("CourseOfferingCallbackService added to CourseOfferingSubscriptionService subscriber list");
            return id;
        } catch(Exception e) {
            log.error("Exception occurred at subscribeToActivityOfferings ", e);
        }
        return id;
    }

    @Override
    public String subscribeToActivityOfferingsByTerm(@WebParam(partName = "callback_action", name = "SubscribeToActivityOfferingsByTerm", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") SubscriptionActionEnum action, @WebParam(partName = "callback_term_id", name = "SubscribeToActivityOfferingsByTerm", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") String termId, @WebParam(partName = "callback_object", name = "SubscribeToActivityOfferingsByTerm", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") W3CEndpointReference callbackObject, @WebParam(partName = "callback_context", name = "SubscribeToActivityOfferingsByTerm", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("operation has not been implemented");
    }

    @Override
    public String subscribeToActivityOfferingsByActivity(@WebParam(partName = "callback_action", name = "SubscribeToActivityOfferingsByActivity", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") SubscriptionActionEnum action, @WebParam(partName = "callback_activity_id", name = "SubscribeToActivityOfferingsByActivity", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") String activityId, @WebParam(partName = "callback_object", name = "SubscribeToActivityOfferingsByActivity", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") W3CEndpointReference callbackObject, @WebParam(partName = "callback_context", name = "SubscribeToActivityOfferingsByActivity", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("operation has not been implemented");
    }

    @Override
    public String subscribeToActivityOfferingsByType(@WebParam(partName = "callback_action", name = "SubscribeToActivityOfferingsByType", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") SubscriptionActionEnum action, @WebParam(partName = "callback_activity_type_key", name = "SubscribeToActivityOfferingsByType", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") String activityOfferingTypeKey, @WebParam(partName = "callback_object", name = "SubscribeToActivityOfferingsByType", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") W3CEndpointReference callbackObject, @WebParam(partName = "callback_context", name = "SubscribeToActivityOfferingsByType", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("operation has not been implemented");
    }

    @Override
    public String subscribeToCourseOfferingsByTerm(@WebParam(partName = "callback_action", name = "SubscribeToCourseOfferingsByTerm", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") SubscriptionActionEnum action, @WebParam(partName = "callback_term_id", name = "SubscribeToCourseOfferingsByTerm", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") String termId, @WebParam(partName = "callback_object", name = "SubscribeToCourseOfferingsByTerm", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") W3CEndpointReference callbackObject, @WebParam(partName = "callback_context", name = "SubscribeToCourseOfferingsByTerm", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("operation has not been implemented");
    }

    @Override
    public String subscribeToCourseOfferingsByCourse(@WebParam(partName = "callback_action", name = "SubscribeToCourseOfferingsByCourse", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") SubscriptionActionEnum action, @WebParam(partName = "callback_course_id", name = "SubscribeToCourseOfferingsByCourse", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") String courseId, @WebParam(partName = "callback_object", name = "SubscribeToCourseOfferingsByCourse", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") W3CEndpointReference callbackObject, @WebParam(partName = "callback_context", name = "SubscribeToCourseOfferingsByCourse", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("operation has not been implemented");
    }

    @Override
    public String subscribeToCourseOfferingsByType(@WebParam(partName = "callback_action", name = "SubscribeToCourseOfferingsByCourse", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") SubscriptionActionEnum action, @WebParam(partName = "callback_course_offering_type_key", name = "SubscribeToCourseOfferingsByCourse", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") String courseOfferingTypeKey, @WebParam(partName = "callback_object", name = "SubscribeToCourseOfferingsByCourse", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") W3CEndpointReference callbackObject, @WebParam(partName = "callback_context", name = "SubscribeToCourseOfferingsByCourse", targetNamespace = "http://student.kuali.org/wsdl/courseOfferingCallbackService") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("operation has not been implemented");
    }

    @Override
    public StatusInfo removeSubscription(String subscriptionId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        this.callbacks.remove(subscriptionId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    protected void fireSelectedCallbacks(Selector target, String id) {
        // consider running this in a different thread so it does not block the main call
        for (Selector selector : this.callbacks.values()) {
            if (matches(selector, target)) {
                callCallback(selector.callback, target.action, id);
            }
        }
    }

    protected void callCallback(CourseOfferingCallbackService callback,
                                SubscriptionActionEnum action,
                                String id) {
        switch (action) {
            case UPDATE:
                callback.updateActivityOfferings(Arrays.asList(new String[] {id}), new ContextInfo());
                break;
            default:
                throw new RuntimeException("operation has not been implemented");
        }
    }

    protected boolean matches(Selector selector, Selector target) {
        if (!matches(selector.action, target.action)) {
            return true;
        }
        if (selector.termId != null) {
            if (!selector.termId.equals(target.termId)) {
                return false;
            }
        }
        if (selector.code != null) {
            if (!selector.code.equals(target.code)) {
                return false;
            }
        }
        if (selector.offeringTypeKey != null) {
            if (!selector.offeringTypeKey.equals(target.offeringTypeKey)) {
                return false;
            }
        }
        if (selector.offeringId != null) {
            if (!selector.offeringId.equals(target.offeringId)) {
                return false;
            }
        }
        return true;
    }

    protected boolean matches(SubscriptionActionEnum selector, SubscriptionActionEnum target) {
        if (selector.equals(target)) {
            return true;
        }
        if (selector.equals(SubscriptionActionEnum.ANY)) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void onMessage(Message message) {
        try {
            String methodName = message.getStringProperty(EnqueuerCallbackListener.EVENT_QUEUE_MESSAGE_METHOD_NAME);
            String offeringId = message.getStringProperty(EnqueuerCallbackListener.EVENT_QUEUE_MESSAGE_OFFERING_ID);

            if("updateActivityOffering".equals(methodName)) {
                Selector target = new Selector(SubscriptionActionEnum.UPDATE, null);
                target.offeringId = offeringId;
                this.fireSelectedCallbacks(target, offeringId);
            } else {
                log.warn(methodName + " not supported");
            }
        } catch(JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
