# Item_Inventory_Managment
Java desktop application for item inventory management of retail stores and warehouses.

Version 1.0
Overview:
  The application was designed as a item inventory management system with a 4-teir user permission level.  The application is written in Java, Java FXML, and the database is in MySQL.  The basic function of the application is to manage item inventories for different warehouse and retail stores.  A user can create a new user and all usernames are case sensitive.
    SuperAdmin:
      - Can change the permission levels of SuperAdmin users down to Normal users or Noraml users up to SuperAdmin.
      - Can view other application users information except password hash.
      - Can view retail stores and warehouses that have a item inventory table.
      - Can create a new warhouse or retail store item inventory table.
      - Can delete existing warehouse or retail store item inventory tables.
      - Can view item inventory tables of a selected warehouse or retail store.
      - Can add/delete items from an existing warehouse or retail store inventory table.
      - Can edit the information of existing items on a item inventory table.
      - Can view application logs that consist of (Username, DATETIME(), action username commited)
          -ex.   Admin    2017-11-10 3:55PM   Admin changed the permission level of User1 from Admin to Manager.
    Admin:
      - Can do everything SuperAdmin can do but can't see user information of users with a permission level of SuperAdmin
    Manager:
      - Can change the permission levels of Manager users down to Normal users or Noraml users up to Manager.
      - Can view other application user information that have permission levels or Noraml users.
      - Can view warehouse and retail stores that have a item inventory table.
      - Can view item inventory tables of a selected warehouse or retail store.
      - Can add/delete items from an existing warehouse or retail store inventory table.
      - Can edit the information of existing items on a item inventory table.
      - Can edit the information of existing items on a item inventory table.
      - Can view application logs that consist of (Username, DATETIME(), action username commited)
      - Can't delete/create warehouse or retail stores.
    Normal User:
      - Can view warehouse and retail stores that have a item inventory table.
      - Can view item inventory tables of a selected warehouse or retail store.
      - Can't view logs
      - Can't view other application user information.
      - *******CAN ONLY VIEW CAN'T EDIT/CREATE/DELETE ITEMS, WAREHOUSES, OR RETAIL STORES******
      
      
MySQL:
  The tables and databases are managed by two different users app_control and UserCreator.  Each have limited permissions to the MySQL server (a localhost was used for testing) and their databases and tables.  UserCreator is mainly used during the new user creation process of the application.  UserCreator can also only access the app_users table in the users database.  UserCreator can only use select and insert mysql commands.  App_Control has access to all tables in the app_tables database (contains each warehouse and retail store item inventory table).  App_control also has access to the log table in the logs database and the app_users table in the app_users database.  App_Control can only use select, insert, create, drop, alter, and update mysql commands.
  

Future Changes:
  Update UIs
  Add graph for item sale trends during a year
  TBD
