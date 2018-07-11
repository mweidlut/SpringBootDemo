package org.test.web.protobuf;

/**
 * User: weimeng
 * Date: 2018/7/11 9:56
 * https://www.cnblogs.com/liugh/p/7505533.html
 */
public class Test {

    public static void main(String[] args) {
        CustomerProtos.Organization org = OrgBuilder.build();
        byte[] results = org.toByteArray();

        System.out.println(new String(results));
        System.out.println(org.getCustomerList().get(0).getFirstName());
    }
}
