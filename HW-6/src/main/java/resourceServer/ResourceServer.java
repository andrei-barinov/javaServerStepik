package resourceServer;


import resources.TestResource;

public class ResourceServer {
    private TestResource testResource;

    public ResourceServer(TestResource testResource) {
        this.testResource = testResource;
    }

    public TestResource getTestResource() {
        return testResource;
    }
}
