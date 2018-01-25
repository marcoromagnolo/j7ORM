# j7ORM

[![Build Status](https://travis-ci.org/marcoromagnolo/j7orm.svg?branch=master)](https://travis-ci.org/marcoromagnolo/j7orm)  
**Maven Repository:** [j7orm-1.0.jar](http://search.maven.org/#artifactdetails%7Cj7orm%7Cj7orm%7C1.0%7Cjar)  
**Wiki:** [User Guide 1.0](https://github.com/marcoromagnolo/j7orm/wiki/j7ORM-1.0)  
**Documentation:** [Javadocs 1.0](http://marcoromagnolo.github.io/j7orm/api-docs/1.0/javadoc/index.html)  
**License:** [Apache 2.0](http://www.apache.org/licenses/LICENSE-2.0)  

## Getting Started

This is the simplest ORM (Object Relational Mapping) to work with all popular databases (RDBMS) 
- Mysql
- Postgres
- SQLite
- H2
- HSQLDB
- Derby
- Oracle
- DB2

### Prerequisites
You must comply with these requirements before implement j7ORM in your application: 
- JVM >= 7
- JDBC dependencies correctly configured in your project
- Database correctly installed and accessible with your credentials

### Example with Mysql
Create a **db** schema if you haven't yet
~~~~
CREATE DATABASE j7orm;
~~~~
Create **table** with ID (AUTO_INCREMENT) and VERSION columns
~~~~
CREATE TABLE person
(
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  version BIGINT DEFAULT NULL,
  name varchar(255) NOT NULL,
);
~~~~
Create **Table class** extending `j7orm.AbstractTable` with `@Table` and `@Column` annotations
~~~~
@Table(name = "person")
public class PersonTable extends AbstractTable {
    @Column(name = "name")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
~~~~
Create **Entity** class extending PersonTable and implementing `j7orm.Entity`
~~~~
public class PersonEntity extends Personable implements Entity {}
~~~~
Create **DAO interface** extending `j7orm.DAO` with generics
~~~~
public interface PersonDAO<T> extends DAO<T> {}
~~~~
Create **DAO implementation** class extending `j7orm.AbstractDAO` and generics configured with entity class
~~~~
public class PersonDAOImpl extends AbstractDAO<PersonEntity> implements PersonDAO<PersonEntity> {}
~~~~
Configure **j7orm.properties** file with entities, database credentials and parameters
~~~~
db.type=mysql
db.host=localhost
db.port=3306
db.database=j7orm
db.user=root
db.password=
entity.1=com.example.entity.PersonEntity
~~~~
Now you can **select() insert() update() and delete()** from Person table
~~~~
public static void main() throws ConnectionException, OrmException {
    // Load DB instance from j7orm.properties
    DB db = DBFactory.getInstance();
    
    // Initialize DAO with DBConnection
    PersonDAO<PersonEntity> dao = new PersonDAOImpl(db.getConnection());
    
    //Create your entity and set values
    PersonEntity entity = new PersonEntity();
    entity.setName("Marco");
    
    // test DAO functionality
    dao.insert(entity);
    dao.find(1L);
    entity.setName("Marco2");
    dao.update(entity);
    dao.delete(entity);
    
    // Disconnect
    DB.getInstance().disconnect();
}
~~~~
You can also use `j7orm.GenericDAO` by using entity on fly without create DAOs for each entity

### Insight
if you want to increase your j7ORM knowledge follow this [link](https://github.com/marcoromagnolo/j7orm/wiki)
