package server;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;

public final class MockServer implements IServer{

    boolean firstRun = true;

    public MockServer() throws InvalidArgumentValueException {
        new MockDatabase();
    }

    @Override
    public Response createResponse(Request request) throws Exception {
        // Unidentified requests are denied by default
        Response response = new Response(false, null);
        switch (request.getAction()){
            case "init":
                if (firstRun){
                    MockDatabase.initiate();
                    firstRun = false;
                    response = new Response(true, null);
                }
                break;

            case "login":
                response = MockDatabase.login(request);
                break;

            case "query users":
                response = MockDatabase.queryUsers(request);
                break;

            case "query assets":
                response = MockDatabase.queryAssets(request);
                break;

            case "query organisationalUnits":
                response = MockDatabase.queryOrganisations(request);
                break;

            case "query stocks":
                response = MockDatabase.queryStocks(request);
                break;

            case "query organisational unit":
                response = MockDatabase.queryOrganisationalUnit(request);
                break;

            case "query stock":
                response = MockDatabase.queryStock(request);
                break;

            case "query orders":
                response = MockDatabase.queryOrders(request);
                break;

            case "add":
                response = MockDatabase.add(request);
                break;

            case "edit":
                response = MockDatabase.edit(request);
                break;

            case "delete":
                response = MockDatabase.delete(request);
                break;
        }
        return response;
    }
}