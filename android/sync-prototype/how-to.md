1. install couchbase server community edition

    http://www.couchbase.com/nosql-databases/downloads#

2. install couchbase sync gateway

        brew install sync_gateway

3. start couchbase server, config admin account, etc

4. in couchbase server Admin console(http://127.0.0.1:8091/index.html), create a new bucket called "demodb"

5. start sync gateway with the script under sync-gateway folder: start_sync_gateway_server.sh

6. create a user for sync-gateway with the script: create_user.sh

    after running the script, check the sync gateway admin console(http://localhost:4985/_admin/db/demodb/users) to see if a user named user1 is created
    and make sure this user has access to a channel also called user1, that's how we can achieve data isolation between users

7. create a session for user1 with the script: create_session.sh

    this script will print something like:
    
        {"session_id":"a469f18027647e4957ffd1743e2ea33ce0386dbc","expires":"2016-02-21T17:51:43.071175586+08:00","cookie_name":"SyncGatewaySession"}
    
    this will be needed as a cookie for the android client to send requests to sync gate way at port 4984
    
    (4984 is the default port that sync gateway receives requests from its clients, 4985 is the admin port that only receives requests from localhost)

8. put the cookie created in step 7 in the android app code:
        
        //......
        Replication pullReplication = database.createPullReplication(syncUrl);
        pullReplication.setCookie("SyncGatewaySession", "a469f18027647e4957ffd1743e2ea33ce0386dbc", null, 86400000000000L, false, false);
        pullReplication.setChannels(asList("user1"));
        pullReplication.setContinuous(true);

        Replication pushReplication = database.createPushReplication(syncUrl);
        pushReplication.setCookie("SyncGatewaySession", "a469f18027647e4957ffd1743e2ea33ce0386dbc", null, 86400000000000L, false, false);
        pushReplication.setChannels(asList("user1"));
        pushReplication.setContinuous(true);
        
        //......
        Document document = database.createDocument();
        
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("channels", asList("user1"));

    The cookie will make sure sync gateway will treat the android app as authorized user.
    
    The channels setting will make sure this android app will mark documents that it syncs up as created by user1, and only syncs down data that was created by itself.

9. then start the app in genymotion, it'll be able to sync up data to the server and able to sync down data from the server after local tablet data is cleared.

10. to try out user data isolation, create another user that has access to another channel, create a session for it, put the session id cookie and channels in the code.
    then run it in another android emulator, you will see that the two users will only has access to the documents that they have each created.