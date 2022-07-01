package servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import resourceServer.ResourceServer;
import resourceServer.ResourceServerController;
import resourceServer.ResourceServerControllerMBean;
import resources.TestResource;
import sax.ReadXMLFileSAX;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class PathServlet extends HttpServlet {

    static final Logger logger = LogManager.getLogger(PathServlet.class.getName());
    public static final String PAGE_URL = "/resources";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        String path = request.getParameter("path");

        logger.info("path: " + path);

        //Тест: "HW-6/data/MySqlResource.xdb"

        TestResource resource = (TestResource) ReadXMLFileSAX.readXML(path);

        logger.info("\n" + "Class: " + resource.getClass() +
                "\n" + "name: " + resource.getName() +
                "\n" + "age: " + resource.getAge());

        ResourceServer resourceServer = new ResourceServer(resource);

        ResourceServerControllerMBean resourceServerStatics = new ResourceServerController(resourceServer);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = null;
        try {

            name = new ObjectName("Admin:type=ResourceServerController");
            mbs.registerMBean(resourceServerStatics, name);

        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
