package resourceServer;

public class ResourceServerController implements ResourceServerControllerMBean {
    private ResourceServer resourceServer;

    public ResourceServerController(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    public String getName() {
        return resourceServer.getTestResource().getName();
    }

    @Override
    public int getAge() {
        return  resourceServer.getTestResource().getAge();
    }
}
