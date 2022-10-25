# Battery API with Spring Boot 
BY AUGUSTINE ITALIANO
gus0166@gmail.com 

A test api for powerledger to store batteries and search through the database using post codes.

Prerequisites:
- Java 8 (or higher)
- MySQL workbench installed (with mysql already configured)
- Postman (this will help with testing)

To CONNECT to your mysql database using MYSQL WORKBENCH:
- click the '+' sign and add a new MySQL Connection
- Call the connection name whatever you wish
- Make sure the port number matches what is on the datasource.url line in application.properties 
- Click 'Test Connection' and put your password in, a successful display should appear 
- Click 'Ok' and you should see the new connection, double click on it to open it up
- Under the 'Navigator' on the left hand side, make sure you are under the SCHEMA tab (check at the bottom of the navigator tab box)
- Right click and 'Create schema'
- Input the name after the local host portion of the datasource.url line
E.g. localhost:3306/crudusers?useSSL=false&serverTimezone=UTC 
                       ^
    We will call the new schema 'cruduser'
-  Click 'Apply' and 'Finish' 
- Right click the schema under the navigator and click 'Refresh' when you add new data to the database!


To ADD a list of batteries: 
localhost:8080/save

[
    {
    "name": "tesla_batt_01",
    "postcode": "6149",
    "wattage": "100.001"
    },
    {
    "name": "tesla_batt_02",
    "postcode": "6199",
    "wattage": "2321.001"
    },
    {
    "name": "tesla_batt_03",
    "postcode": "6249",
    "wattage": "300.001"
    },
    {
    "name": "tesla_batt_04",
    "postcode": "6549",
    "wattage": "900.001"
    }
]

To GET a list of batteries:
localhost:8080/get?p1=<insert a lower bound postcode>&p2=<insert a upper bound postcode>

Output expected for above tests when all are retreived within range: 
++++++++++++++++++++++
Name: tesla_batt_01
Postcode: 6149
Wattage: 100.001
++++++++++++++++++++++
++++++++++++++++++++++
Name: tesla_batt_02
Postcode: 6199
Wattage: 2321.0
++++++++++++++++++++++
++++++++++++++++++++++
Name: tesla_batt_03
Postcode: 6249
Wattage: 300.001
++++++++++++++++++++++
++++++++++++++++++++++
Name: tesla_batt_04
Postcode: 6549
Wattage: 900.001
++++++++++++++++++++++
Total Wattage: 3621.0029830932617
Average Wattage: 905.2507457733154
Number of batteries: 4