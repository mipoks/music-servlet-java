How to set your database connection?

1. Implement interface ConnectionGiver
(package design.kfu.helper.database)
2. Change row in MainService
connectionGiver = new YourConnectionGiver();
(package design.kfu.service)

Important! Your ConnectionGiver should give new Connection for each method call getConnection()

Example:
class ConnectionLocal in
package design.kfu.helper.database.implementation


SQL commands were written for PostgreSQL. If you want to use other DBMS, you should check and correct them in all classes of package design.kfu.repository.implementation.database
(if it is neccessary)
