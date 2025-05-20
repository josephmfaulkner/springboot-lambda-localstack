# spring-boot-lambda serverless API on Localstack

This is a simple project that demonstrates how to deploy and run a SpringBoot application as a Lambda function locally using [Localstack](https://www.localstack.cloud/) (running in Docker).

The spring-boot-lambda project, created with [`aws-serverless-java-container`](https://github.com/aws/serverless-java-container).

The project defines a simple `/ping` resource that can accept `GET` requests with its tests.


## Pre-requisites
* [Visual Studio Code](https://code.visualstudio.com/)
* [Java21](https://openjdk.org/projects/jdk/21/)
* [Docker](https://docs.docker.com/get-started/get-docker/)
* [Docker Compose](https://docs.docker.com/compose/)
* [AWS CLI](https://aws.amazon.com/cli/)
* [SAM CLI](https://github.com/awslabs/aws-sam-cli)
* [AWS Local](https://github.com/localstack/awscli-local)
* [SAM Local](https://github.com/localstack/aws-sam-cli-local)


Open the project using VS Code 
```
code ServerlessApp.code-workspace
```
## Try it yourself

### Building the project
Build the Java code using VS Code task `Clean and Build All Services` by either 
- Navigating from the main Toolbar `Terminal` > `Run Task` and select `Clean and Build All Services`
- Use keyboard shortcut `ctrl + shift + p` to open the Command Palette and select `Clean and Build All Services`


### Deploying and running on LocalStack
To spin up LocalStack on Docker Compose and deploy the Serverless app to it, run the VS Code Task: 
`Start/Deploy Localstack (Dockercompose)`

After running these steps, you should be presented with two local endpoints you can use to test the API on local: 
```
CloudFormation outputs from deployed stack
------------------------------------------
Outputs                                                                                                              
------------------------------------------
Key                 LocalStackLambdaApiPing                                                                                                                       
Description         URL for Lambda application running on Localstack                                                                                              
Value               http://appapi.execute-api.localhost.localstack.cloud:4566/local/ping                                                                          

Key                 LocalStackLambdaApiBTest                                                                                                                      
Description         URL for Lambda application running on Localstack                                                                                              
Value               http://appapib.execute-api.localhost.localstack.cloud:4566/local/test 
```

You can try these out yourself using the VS Code Task `Test Ping Local Services`

The terminal output should look something like this: 

```bash
+ curl http://appapi.execute-api.localhost.localstack.cloud:4566/local/test
+ jq
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    50  100    50    0     0      9      0  0:00:05  0:00:05 --:--:--    10
{
  "message": "Hello from the Main Lambda REST API!"
}
+ curl http://appapib.execute-api.localhost.localstack.cloud:4566/local/test
+ jq
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    36  100    36    0     0      8      0  0:00:04  0:00:04 --:--:--     8
{
  "message": "Hello from Test API B!"
}
```

### Make a change, rebuild and reload
Let's try making some small changes to the API services and rebuilding the project to see the changes running through the API endpoints.

Change line 17 of `
lambda-functions/restApi/src/main/java/com/javatechie/controller/PingController.java`

For example:
```
response.put("message", "Hello from the Main Lambda REST API! Nice to meet you!");
```

Change line 17 of `lambda-functions/restApi_B/src/main/java/com/restApiB/controller/TestController.java`

For example: 
```
response.put("message", "Hello from Test API B! I think I've seen you before.");
```
Now, use VS Code Task `Rebuild All Services (skip tests)` to rebuild the services and run `Test Ping Local Services` again to see the changes take effect: 

```
+ curl http://appapi.execute-api.localhost.localstack.cloud:4566/local/test
+ jq
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    68  100    68    0     0     15      0  0:00:04  0:00:04 --:--:--    15
{
  "message": "Hello from the Main Lambda REST API! Nice to meet you!"
}
+ curl http://appapib.execute-api.localhost.localstack.cloud:4566/local/test
+ jq
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    67  100    67    0     0     18      0  0:00:03  0:00:03 --:--:--    18
{
  "message": "Hello from Test API B!  I think I've seen you before."
}
```

### Live debugging

First set some breakpoints in the 'Test' controllers. Then, open `Run and Debug` (shortcut `ctrl + shift + d`) and select `Debug All Services`. When you run `Test Ping Local Services`, you should see the app pause at the breakpoints you set. 

https://github.com/user-attachments/assets/3bc87153-993c-402c-9554-d6c364db1bc5


### Stop and spin down Localstack (Docker compose)
Finally, to stop Localstack, run VS Task `Stop Localstack (Dockercompose)`
Note that since Localstack is ephemeral by default, this will remove the deployed application as well. 
