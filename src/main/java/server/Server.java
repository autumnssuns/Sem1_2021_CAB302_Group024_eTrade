package server;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.OrganisationalUnit;
import common.dataClasses.Stock;
import common.dataClasses.User;
import server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses.CasesToResponse;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.LoginSystem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Server implements IServer{
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int port;
    private boolean firstRun;

    /**
     * this is the timeout in between accepting clients, not reading from the socket itself.
     */
    private static final int SOCKET_ACCEPT_TIMEOUT = 100;
    private AtomicBoolean running = new AtomicBoolean(true);
    /**
     * Initiate the server and
     * //TODO initiate the database addess
     * @throws InvalidArgumentValueException
     */
    public Server() throws InvalidArgumentValueException {
        Properties props = new Properties();
        InputStream in = null;
        try{
            in = this.getClass().getClassLoader().getResourceAsStream("server-config.properties");
            props.load(in);
            in.close();

            // specify the data source, username and password
            port = Integer.parseInt(props.getProperty("port"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        firstRun = true;
        new MockDatabase();
    }

    /**
     * Starts listening for client.
     * @throws IOException
     */
    public void Start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(SOCKET_ACCEPT_TIMEOUT);
            // Temporary prompt message
            System.out.println("Running on port " + port + "...");
            int connectionNumber = 0;
            while (true) {
                if (!running.get()) {
                    break;
                }

                try {
                    final Socket socket = serverSocket.accept();
                    System.out.println(String.format("Accepted client connection - # %d", connectionNumber));
                    final int currentNumber = connectionNumber;
                    final Thread clientThread = new Thread(() -> handleConnection(socket));
                    connectionNumber++;
                    clientThread.start();
                } catch (SocketTimeoutException ignored) {
                    // Do nothing. A timeout is normal- we just want the socket to
                    // occasionally timeout so we can check if the server is still running
                } catch (Exception e) {
                    // We will report other exceptions by printing the stack trace, but we
                    // will not shut down the server. A exception can happen due to a
                    // client malfunction (or malicious client)
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles a single socket connection
     * @param socket The socket to handle
     */
    private void handleConnection(Socket socket) {
        try {
            // Initiates the IO streams
            final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());

            // Wait until the requests is sent through
            while (true) {
                try {
                    // Create a response and write to stream
                    final Request request = (Request) inputStream.readObject();
                    System.out.println(request.getAction());
                    TimeUnit.SECONDS.sleep(1);
                    Response response = createResponse(request);
                    outputStream.writeObject(response);
                    // Closes the socket after writing
                    socket.close();
                } catch (SocketTimeoutException e) {
                    continue;
                } catch (InvalidArgumentValueException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            System.out.println(String.format("Connection %s closed", socket.toString()));
        }
    }

    /**
     * Closes the server (stop listening).
     * @throws IOException
     */
    public void Close() throws IOException{
        try {
            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a response based on the request.
     * @param clientRequest The request sent by client.
     * @return An appropriate response.
     * @throws InvalidArgumentValueException
     */
    @Override
    public Response createResponse(Request clientRequest) throws InvalidArgumentValueException {
        // Unidentified requests are denied by default
        //Get senders' information
        User sender = clientRequest.getUser();
        Response serverResponse = new Response(false, null);
        switch (clientRequest.getAction()){
            case "init":
                if (firstRun){
                    CasesToResponse.initiate();
                    firstRun = false;
                    serverResponse = new Response(true, null);
                }
                break;

            case "login":
                    serverResponse = CasesToResponse.Login(clientRequest);
                break;

            case "query users":
                serverResponse = MockDatabase.queryUsers(clientRequest);
                break;

            case "query assets":
                serverResponse = MockDatabase.queryAssets(clientRequest);
                break;

            case "query organisationalUnits":
                serverResponse = MockDatabase.queryOrganisations(clientRequest);
                break;

            case "query stocks":
                serverResponse = MockDatabase.queryStocks(clientRequest);
                break;

            case "query organisational unit":
                serverResponse = MockDatabase.queryOrganisationalUnit(clientRequest);
                break;

            case "query stock":
                serverResponse = MockDatabase.queryStock(clientRequest);
                break;

            case "query orders":
                serverResponse = MockDatabase.queryOrders(clientRequest);
                break;

            case "add":
                serverResponse = MockDatabase.add(clientRequest);
                break;

            case "edit":
                serverResponse = MockDatabase.edit(clientRequest);
                break;

            case "delete":
                serverResponse = MockDatabase.delete(clientRequest);
                break;
        }
        return serverResponse;
    }
}