package org.test.web.protobuf;


public class OrgBuilder {

    public static CustomerProtos.Organization build() {
        CustomerProtos.Customer.EmailAddress emailAddress = CustomerProtos.Customer.EmailAddress.newBuilder()
                .setEmail("test-private@email.com").setType(CustomerProtos.Customer.EmailType.PRIVATE).build();

        CustomerProtos.Customer.EmailAddress emailAddress2 = CustomerProtos.Customer.EmailAddress.newBuilder()
                .setEmail("test-professional@email.com").setType(CustomerProtos.Customer.EmailType.PROFESSIONAL).build();

        CustomerProtos.Customer customer = CustomerProtos.Customer.newBuilder()
                .setId(1234).setFirstName("Raymond").setLastName("Smith")
                .addEmail(emailAddress).addEmail(emailAddress2).build();

        CustomerProtos.Organization org = CustomerProtos.Organization.newBuilder().setName("太阳伞").addCustomer(customer).build();

        return org;
    }
}
