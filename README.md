# Mail-Order-Pharmacy
Guide to deploying your website on localhost:

Build your project:
First, make sure you have Maven installed on your system. Then, open your Spring Tool Suite and navigate to your project folder. Clean build your project by clicking on the "Project" tab and then "Clean". Once the cleaning process is complete, click on "Run As" and then "Maven build...". In the "Goals" field, enter "clean install" and then click on "Run".

Launch the microservices:
The microservices should be launched in the following order: auth, drug, refill, subscription, and webapp. To launch each microservice, navigate to its folder within your project and then right-click on each service and select run and in run select run as spring boot app.

Test the application:
Once all the microservices are launched, you can test your application by accessing the webapp microservice at http://localhost:9076/webportal/ (assuming that you have not changed the default port number in your application properties). You can use the credentials provided by the auth microservice to log in and test the functionality of your application.
In summary, deploying your website on localhost involves building your project using Maven and launching each microservice using the spring tool suit in the correct order. Once all the microservices are launched, you can test your application by accessing the webapp microservice at http://localhost:9076/webportal.
