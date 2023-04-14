<h1><b>Deployment guide for Online Pharmacy</b></h1>

<h3><b>Build your project</b></h3>

<li>First, make sure you have Maven installed on your system.</li>
<li>, open your Spring Tool Suite and navigate to your project folder. Clean build your project by clicking on the "Project" tab and then "Clean".</li>
<li>Once the cleaning process is complete, click on "Run As" and then "Maven build...". In the "Goals" field, enter "clean install" and then click on "Run".</li>

<br>
<h3><b>Launch the microservices:</b></h3>
<li>The microservices should be launched in the following order: auth, drug, refill, subscription, and webapp. </li>
<li>To launch each microservice, navigate to its folder within your project and then right-click on each service and select run and in run select run as spring boot app.</li>

<br>
<h3><b>Test the application:</b></h3>
<li>Once all the microservices are launched, you can test your application by accessing the webapp microservice at http://localhost:9076/webportal/ </li>
<li>You can use the credentials provided by the auth microservice to log in and test the functionality of your application.</li>
<br>

<h1><b>In summary</b</h1><br>

<h5>deploying your website on localhost involves building your project using Maven and launching each microservice using the spring tool suit in the correct order. Once all the microservices are launched, you can test your application by accessing the webapp microservice at http://localhost:9076/webportal.</h5>