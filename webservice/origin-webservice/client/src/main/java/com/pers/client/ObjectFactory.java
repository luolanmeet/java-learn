
package com.pers.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.pers.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _User_QNAME = new QName("http://service.pers/", "user");
    private final static QName _ChangeUserResponse_QNAME = new QName("http://service.pers/", "changeUserResponse");
    private final static QName _QueryUser_QNAME = new QName("http://service.pers/", "queryUser");
    private final static QName _ChangeUser_QNAME = new QName("http://service.pers/", "changeUser");
    private final static QName _QueryUserResponse_QNAME = new QName("http://service.pers/", "queryUserResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.pers.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryUserResponse }
     * 
     */
    public QueryUserResponse createQueryUserResponse() {
        return new QueryUserResponse();
    }

    /**
     * Create an instance of {@link ChangeUser }
     * 
     */
    public ChangeUser createChangeUser() {
        return new ChangeUser();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link QueryUser }
     * 
     */
    public QueryUser createQueryUser() {
        return new QueryUser();
    }

    /**
     * Create an instance of {@link ChangeUserResponse }
     * 
     */
    public ChangeUserResponse createChangeUserResponse() {
        return new ChangeUserResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link User }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.pers/", name = "user")
    public JAXBElement<User> createUser(User value) {
        return new JAXBElement<User>(_User_QNAME, User.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.pers/", name = "changeUserResponse")
    public JAXBElement<ChangeUserResponse> createChangeUserResponse(ChangeUserResponse value) {
        return new JAXBElement<ChangeUserResponse>(_ChangeUserResponse_QNAME, ChangeUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.pers/", name = "queryUser")
    public JAXBElement<QueryUser> createQueryUser(QueryUser value) {
        return new JAXBElement<QueryUser>(_QueryUser_QNAME, QueryUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.pers/", name = "changeUser")
    public JAXBElement<ChangeUser> createChangeUser(ChangeUser value) {
        return new JAXBElement<ChangeUser>(_ChangeUser_QNAME, ChangeUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.pers/", name = "queryUserResponse")
    public JAXBElement<QueryUserResponse> createQueryUserResponse(QueryUserResponse value) {
        return new JAXBElement<QueryUserResponse>(_QueryUserResponse_QNAME, QueryUserResponse.class, null, value);
    }

}
