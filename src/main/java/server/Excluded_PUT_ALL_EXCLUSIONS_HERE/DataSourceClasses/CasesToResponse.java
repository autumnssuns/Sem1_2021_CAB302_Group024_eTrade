package server.Excluded_PUT_ALL_EXCLUSIONS_HERE.DataSourceClasses;

import common.Exceptions.InvalidArgumentValueException;
import common.Request;
import common.Response;
import common.dataClasses.*;
import server.WorkingFeatures_PLEASE_DO_NOT_EXCLUDE.HashPassword;

import java.time.LocalDateTime;

public class CasesToResponse {

    public static void initiate() throws InvalidArgumentValueException {
        UserDataSource users = new UserDataSource();
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        StockDataSource stockDataSource = new StockDataSource();

        users.addUser(new User(1, "Dan Tran", "dan", "123", "user", 0));
        users.addUser(new User(2, "Daniel Pham", "duy", "abcd", "user", 1));
        users.addUser(new User(3, "Linh Hoang", "lyn", "password", "user", 2));
        users.addUser(new User(4, "Rodo Nguyen", "rodo", "rodo", "user", 3));

        assetsDataSource.addAsset(new Asset(0, "CPU Hours", "CPU for rent"));
        assetsDataSource.addAsset(new Asset(1, "10 GB Database Server", "Remove SQL Server"));
        assetsDataSource.addAsset(new Asset(2, "A Generic Video Game", "Nothing is more generic than this."));
        assetsDataSource.addAsset(new Asset(3, "Coffin Dance Video", "You know what this is"));
        DataCollection<Asset> assets = assetsDataSource.getAssetList();
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.addOrganisation(new OrganisationalUnit(0, "The Justice League", 9999.0f));
        organisationsDataSource.addOrganisation(new OrganisationalUnit(1, "The supervillains", 5555.0f));
        organisationsDataSource.addOrganisation(new OrganisationalUnit(2, "The random civilians", 3000.0f));
        organisationsDataSource.addOrganisation(new OrganisationalUnit(3, "The brokers", 3000.0f));

        Stock stock0 = new Stock(0);
        Stock stock1 = new Stock(1);
        Stock stock2 = new Stock(2);
        Stock stock3 = new Stock(3);
        stock0.add(new Item(assets.get(0), 99));
        stock0.add(new Item(assets.get(1), 99));
        stock0.add(new Item(assets.get(2), 99));
        stock0.add(new Item(assets.get(3), 99));
        stockDataSource.UpdateUnitStock(stock0);
        stock1.add(new Item(assets.get(0), 99));
        stock1.add(new Item(assets.get(1), 99));
        stock1.add(new Item(assets.get(2), 99));
        stock1.add(new Item(assets.get(3), 99));
        stockDataSource.UpdateUnitStock(stock1);
        stock2.add(new Item(assets.get(0), 10));
        stock2.add(new Item(assets.get(1), 10));
        stock2.add(new Item(assets.get(2), 10));
        stock2.add(new Item(assets.get(3), 10));
        stockDataSource.UpdateUnitStock(stock1);

//        add(new Order(0, Order.Type.SELL, 0, 0, 99, 0, 10f, null, LocalDateTime.of(2021, 5, 6, 16, 52), Order.Status.PENDING));
//        add(new Order(1, Order.Type.SELL, 0, 1, 99, 0, 3f, null, LocalDateTime.of(2021, 5, 6, 13, 42), Order.Status.PENDING));
//        add(new Order(2, Order.Type.SELL, 0, 2, 99, 0, 4f, null, LocalDateTime.of(2021, 5, 6, 7, 45), Order.Status.PENDING));
//        add(new Order(3, Order.Type.SELL, 0, 3, 99, 0, 5f, null, LocalDateTime.of(2021, 5, 6, 22, 00), Order.Status.PENDING));
//        add(new Order(4, Order.Type.SELL, 1, 0, 55, 0, 8f, null, LocalDateTime.of(2021, 5, 7, 21, 52), Order.Status.PENDING));
//        add(new Order(5, Order.Type.SELL, 1, 1, 55, 0, 7f, null, LocalDateTime.of(2021, 5, 7, 15, 26), Order.Status.PENDING));
//        add(new Order(6, Order.Type.SELL, 1, 2, 55, 0, 8f, null, LocalDateTime.of(2021, 5, 7, 18, 28), Order.Status.PENDING));
//        add(new Order(7, Order.Type.SELL, 1, 3, 50, 0, 9f, null, LocalDateTime.of(2021, 5, 7, 13, 36), Order.Status.PENDING));
//        add(new Order(8, Order.Type.BUY, 2, 0, 40, 0, 10f, null, LocalDateTime.of(2021, 5, 8, 14, 45), Order.Status.PENDING));
//        add(new Order(9, Order.Type.BUY, 2, 1, 40, 0, 10.5f, null, LocalDateTime.of(2021, 5, 8, 11, 14), Order.Status.PENDING));
//        add(new Order(10, Order.Type.BUY, 2, 2, 40, 0, 11.5f, null, LocalDateTime.of(2021, 5, 8, 7, 15), Order.Status.PENDING));
//        add(new Order(11, Order.Type.BUY, 2, 3, 40, 0, 12.5f, null, LocalDateTime.of(2021, 5, 8, 4, 20), Order.Status.PENDING));
//        add(new Order(12, Order.Type.BUY, 3, 0, 50, 0, 13.5f, null, LocalDateTime.of(2021, 5, 9, 6, 21), Order.Status.PENDING));
//        add(new Order(13, Order.Type.BUY, 3, 1, 50, 0, 12.5f, null, LocalDateTime.of(2021, 5, 9, 8, 30), Order.Status.PENDING));
//        add(new Order(14, Order.Type.BUY, 3, 2, 50, 0, 14.5f, null, LocalDateTime.of(2021, 5, 9, 0, 11), Order.Status.PENDING));
//        add(new Order(15, Order.Type.BUY, 3, 3, 50, 0, 15.5f, null, LocalDateTime.of(2021, 5, 9, 3, 42), Order.Status.PENDING));
    }


    //Todo: Add comment / description

    public static Response Login(Request request)
    {
        Response serverResponse = new Response(false, null);
        User sender = request.getUser();
        UserDataSource userdata = new UserDataSource();
        User userInData = userdata.getUser(request.getUser().getUsername());
        if(userInData != null)
        {
            if(sender.getUsername().equals(userInData.getUsername())
            && HashPassword.HashPassword(sender.getPassword()).equals(userInData.getPassword()))
            {
                serverResponse = new Response(true,userInData);
            }
        }

        return  serverResponse;
    }
    //Main type of methods to response to request
    // (each type contains classes: Asset, Organisation, Order,
    // stock, transaction(considering) and User)

    //Todo: Overload Add method
    public static <T extends IData> Response add(Request<T> request){
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if(type.equals(User.class)) {
            return add((User) attachment);
        }
        else if(type.equals(OrganisationalUnit.class)){
            return add((OrganisationalUnit) attachment);
        }
        else if (type.equals(Asset.class)){
            return  add((Asset) attachment);
        }
        else if (type.equals(Order.class)){
            return add((Order) attachment);
        }
        else if (type.equals(Stock.class)){
            return add((Stock) attachment);
        }
        return null;
    }

    //User type
    public static Response add(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.addUser(attachment);
        Response response = new Response(true, null);
        return response;
    }

    //Organisational Unit Type
    public static Response add(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.addOrganisation(attachment);
        Response response = new Response(true, null);
        return response;
    }
    //Asset Type
    public static Response add(Asset attachment) {
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.addAsset(attachment);
        Response response = new Response(true, null);
        return response;
    }
    //Order Type
    public static Response add(Order attachment){
            OrderDataSource orderDataSource = new OrderDataSource();
            orderDataSource.addOrder(attachment);
            Response response = new Response(true, null);
            return response;
        }

    public static Response addAnItem(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.AddAnItem(attachment);
        Response response = new Response(true,null);
        return response;
    }

    /**
     * Add a stock to an org unit
     * @param attachment
     * @return Response object
     */
    public static Response add(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.UpdateUnitStock(attachment);
        Response response = new Response(true, null);
        return response;
    }



    //Todo: Overload Edit method
    public static <T extends IData> Response edit(Request<T> request){
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if (type.equals(User.class)){
            return edit((User) attachment);
        }
        else if (type.equals(Asset.class)){
            return edit((Asset) attachment);
        }
        else if (type.equals(OrganisationalUnit.class)){
            return edit((OrganisationalUnit) attachment);
        }
        else if (type.equals(Order.class)){
            return edit((Order) attachment);
        }
        else if (type.equals(Stock.class)){
            return edit((Stock) attachment);
        }
        return null;
    }
    //User Type
    public static Response edit(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.editUser(attachment);
        Response response = new Response(true, attachment);
        return response;
    }
    //Organisational Unit Type
    public static Response edit(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.editOrganisation(attachment);
        Response response = new Response(true, attachment);
        return response;
    }
    //Asset Type
    public static Response edit(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.editAsset(attachment);
        Response response = new Response(true, attachment);
        return response;
    }
    //Order Type
    public static Response edit(Order attachment){
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.editOrder(attachment);
        Response response = new Response(true, attachment);
        return response;
    }

    /**
     * Edit quantity of an item in a stock of an org unit
     * @param attachment
     * @return Response object
     */
    public static Response edit(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.EditItemQuantity(attachment);
        Response response = new Response(true, attachment);
        return response;
    }

    //Todo: Overload Query method
    public static <T extends IData> Response query(Request<T> request) {
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if (type.equals(User.class)){
            return query((User) attachment);
        }
        else if (type.equals(Asset.class)){
            return query((Asset) attachment);
        }
        else if (type.equals(OrganisationalUnit.class)){
            return query((OrganisationalUnit) attachment);
        }
        else if (type.equals(Order.class)) {
            return query((Order) attachment);
        }

        return null;

    }
    //User Type
    public static Response query(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        attachment = userDataSource.getUser(attachment.getUsername());
        Response response = new Response(true, attachment);
        return response;
    }
    //Organisational Unit Type
    public static Response query(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        attachment = organisationsDataSource.getOrganisation(attachment.getId());
        Response response = new Response(true, attachment);
        return response;
    }
    //Asset Type
    public static Response query(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        attachment = assetsDataSource.getAsset(attachment.getId());
        Response response = new Response(true, attachment);
        return response;
    }
    //Order Type
    public static Response query(Order attachment) {
        OrderDataSource orderDataSource = new OrderDataSource();
        attachment = orderDataSource.getOrder(attachment.getOrderId());
        Response response = new Response(true, attachment);
        return response;
    }


    /**
     * Query all stock from an org unit
     * @param attachment
     * @return Response object
     */
    public static Response queryStock(User attachment){
        StockDataSource stockDataSource = new StockDataSource();
        Stock unitStock = stockDataSource.GetStock(attachment);
        Response response = new Response(true, unitStock);
        return response;
    }


    //Todo: Overload Delete method
    public static <T extends IData> Response delete(Request<T> request) {
        T attachment = request.getAttachment();
        Class<T> type = request.getAttachmentType();
        if (type.equals(User.class)){
            return delete((User) attachment);
        }
        else if (type.equals(Asset.class)){
            return delete((Asset) attachment);
        }
        else if (type.equals(OrganisationalUnit.class)){
            return delete((OrganisationalUnit) attachment);
        }
        else if (type.equals(Order.class)){
            return delete((Order) attachment);
        }
        else if (type.equals(Stock.class)){
            return delete((Stock) attachment);
        }
        return null;
    }
    //User Type
    public static Response delete(User attachment){
        UserDataSource userDataSource = new UserDataSource();
        userDataSource.deleteUser(attachment.getUserId());
        Response response = new Response(true, null);
        return response;
    }

    //Organisational Unit Type
    public static Response delete(OrganisationalUnit attachment){
        OrganisationsDataSource organisationsDataSource = new OrganisationsDataSource();
        organisationsDataSource.deleteOrganisation(attachment.getId());
        Response response = new Response(true, null);
        return response;
    }

    //Asset Type
    public static Response delete(Asset attachment){
        AssetsDataSource assetsDataSource = new AssetsDataSource();
        assetsDataSource.deleteAsset(attachment.getId());
        Response response = new Response(true, null);
        return response;
    }

    //Order Type
    public static Response delete(Order attachment){
        OrderDataSource orderDataSource = new OrderDataSource();
        orderDataSource.deleteOrder(attachment.getOrderId());
        Response response = new Response(true, null);
        return response;
    }

    /**
     * Delete a stock of an org unit
     * @param attachment
     * @return Response object
     */
    public static Response delete(Stock attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.DeleteStock(attachment);
        Response response = new Response(true, null);
        return response;
    }

    public static Response deleteAnItem(Request attachment){
        StockDataSource stockDataSource = new StockDataSource();
        stockDataSource.DeleteAnItem((Stock) attachment.getAttachment());
        Response response = new Response(true, null);
        return  response;
    }
}
