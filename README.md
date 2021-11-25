<h1>Home Accountant</h1>

<h4><a href="http://my-home-accountant.herokuapp.com/">Java Enterprise application</a> to account for expenses with registration/authorization and role-based access rights (USER, ADMIN).</h4>

Admin could create/edit/delete users, users - manage their profile and expenses via UI (AJAX) and REST with basic authorization. \
When registering, the user specifies the currency and monthly spending limit (optional).\
Users can create categories, where they can specify an expense limit and its period (day, week, month, etc.) within that category.\
Expenses could be filtered by category, date and time.

All REST interface covered with JUnit tests by Spring MVC Test and Spring Security Test.

**<a href="http://my-home-accountant.herokuapp.com/swagger-ui.html">Swagger REST API Documentation</a>**

***
Application stack:\
<a href="http://projects.spring.io/spring-security/">Spring Security</a>,
<a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
<a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
<a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security
Test</a>,
<a href="http://hibernate.org/orm/">Hibernate ORM</a>,
<a href="http://hibernate.org/validator/">Hibernate Validator</a>,
<a href="http://www.slf4j.org/">SLF4J</a>,
<a href="https://github.com/FasterXML/jackson">Json Jackson</a>,
<a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
<a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
<a href="http://tomcat.apache.org/">Apache Tomcat</a>,
<a href="http://www.webjars.org/">WebJars</a>,
<a href="http://datatables.net/">DataTables plugin</a>,
<a href="http://ehcache.org">EHCACHE</a>,
<a href="http://www.postgresql.org/">PostgreSQL</a>,
<a href="http://junit.org/">JUnit</a>,
<a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>,
<a href="http://jquery.com/">jQuery</a>,
<a href="http://ned.im/noty/">jQuery notification</a>,
<a href="http://getbootstrap.com/">Bootstrap</a>.
