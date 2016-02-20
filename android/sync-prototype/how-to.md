1. install couchbase server community edition

    http://www.couchbase.com/nosql-databases/downloads#

2. install couchbase sync gateway

        brew install sync_gateway

3. start couchbase server config admin account, etc

4. in couchbase server Admin console, create a new bucket called "demodb"

5. start sync gateway with the script under sync-gateway folder: start_sync_gateway_server.sh

6. create a user for sync-gateway with the script: create_user.sh

    after running the script, check http://localhost:4985/_admin/db/demodb/users (the sync gateway admin console) to see if a user named user1 is created

7. create a session for user1 with the script: create_session.sh

    this script will print something like:
    
        {"session_id":"a469f18027647e4957ffd1743e2ea33ce0386dbc","expires":"2016-02-21T17:51:43.071175586+08:00","cookie_name":"SyncGatewaySession"}
    
    this will be needed as a cookie for the android client to send requests to sync gate way at port 4984
    
    (4984 is the default port that sync gateway receives requests from its clients, 4985 is the admin port that only receives requests localhost requests)

8. put the cookie created in step 7 in the android app code:

        Replication pullReplication = database.createPullReplication(syncUrl);

        pullReplication.setCookie("SyncGatewaySession", "a469f18027647e4957ffd1743e2ea33ce0386dbc", null, 86400000000000L, false, false);
        pullReplication.setContinuous(true);

        Replication pushReplication = database.createPushReplication(syncUrl);
        pushReplication.setCookie("SyncGatewaySession", "a469f18027647e4957ffd1743e2ea33ce0386dbc", null, 86400000000000L, false, false);
        pushReplication.setContinuous(true);

9. then start the app in genymotion, it'll be able to sync up data to the server and able to sync down data from the server after local tablet data is cleared
