package org.test.web.ufx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.security.Security;

/**
 * User: Thinkpad
 * Date: 2019/5/14 14:38
 */
//@Service
public class UFXBootstrap implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(UFXBootstrap.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("UFXBoostrap start...");

        UFXApp app = new UFXApp();
        try {
            Security.setProperty("jdk.tls.disabledAlgorithms", "");
            app.Connect();

            app.login();

            /*String entrustno = app.entrust();

            app.entrustQry(entrustno);

            app.dealQry(entrustno);

            app.withdraw(entrustno);*/

        } catch (Exception e) {
            try {
                app.DisConnect();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
