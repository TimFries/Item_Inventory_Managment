# Item_Inventory_Managment
Java desktop application for item inventory management of retail stores and warehouses.<br />
<br />
Version 1.0<br />
Overview:<br />
  The application was designed as a item inventory management system with a 4-teir user permission level.  The application <br />
  is written in Java, Java FXML, and the database is in MySQL.  The basic function of the application is to manage item<br /> 
  inventories for different warehouse and retail stores.  A user can create a new user and all usernames are case sensitive.<br />
    SuperAdmin:<br />
      - Can change the permission levels of SuperAdmin users down to Normal users or Noraml users up to SuperAdmin.<br />
      - Can view other application users information except password hash.<br />
      - Can view retail stores and warehouses that have a item inventory table.<br />
      - Can create a new warhouse or retail store item inventory table.<br />
      - Can delete existing warehouse or retail store item inventory tables.<br />
      - Can view item inventory tables of a selected warehouse or retail store.<br />
      - Can add/delete items from an existing warehouse or retail store inventory table.<br />
      - Can edit the information of existing items on a item inventory table.<br />
      - Can view application logs that consist of (Username, DATETIME(), action username commited)<br />
          -ex.   Admin    2017-11-10 3:55PM   Admin changed the permission level of User1 from Admin to Manager.<br />
    Admin:<br />
      - Can do everything SuperAdmin can do but can't see user information of users with a permission level of SuperAdmin<br />
    Manager:<br />
      - Can change the permission levels of Manager users down to Normal users or Noraml users up to Manager.<br />
      - Can view other application user information that have permission levels or Noraml users.<br />
      - Can view warehouse and retail stores that have a item inventory table.<br />
      - Can view item inventory tables of a selected warehouse or retail store.<br />
      - Can add/delete items from an existing warehouse or retail store inventory table.<br />
      - Can edit the information of existing items on a item inventory table.<br />
      - Can edit the information of existing items on a item inventory table.<br />
      - Can view application logs that consist of (Username, DATETIME(), action username commited)<br />
      - Can't delete/create warehouse or retail stores.<br />
    Normal User:<br />
      - Can view warehouse and retail stores that have a item inventory table.<br />
      - Can view item inventory tables of a selected warehouse or retail store.<br />
      - Can't view logs<br />
      - Can't view other application user information.<br />
      - *******CAN ONLY VIEW CAN'T EDIT/CREATE/DELETE ITEMS, WAREHOUSES, OR RETAIL STORES******<br />
      
      
MySQL:<br />
  The tables and databases are managed by two different users app_control and UserCreator.  Each have limited permissions<br />
  to the MySQL server (a localhost was used for testing) and their databases and tables.  UserCreator is mainly used during <br />
  the new user creation process of the application.  UserCreator can also only access the app_users table in the users database.<br />
  UserCreator can only use select and insert mysql commands.  App_Control has access to all tables in the app_tables <br />
  database (contains each warehouse and retail store item inventory table).  App_control also has access to the log table <br />
  in the logs database and the app_users table in the app_users database.  App_Control can only use select, insert, <br />
  create, drop, alter, and update mysql commands.<br />
  <br />
<br />
Future Changes:<br />
  Update UIs<br />
  Add graph for item sale trends during a year<br />
  TBD<br />
