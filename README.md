# spring-boot-lambda serverless API on Localstack

This is a simple project that demonstrates how to deploy and run a SpringBoot application as a Lambda function locally using [Localstack](https://www.localstack.cloud/) (running in Docker).

The spring-boot-lambda project, created with [`aws-serverless-java-container`](https://github.com/aws/serverless-java-container).

The project defines a simple `/ping` resource that can accept `GET` requests with its tests.


## Pre-requisites
* [Java21](https://openjdk.org/projects/jdk/21/)
* [Docker](https://docs.docker.com/get-started/get-docker/)
* [Docker Compose](https://docs.docker.com/compose/)
* [AWS CLI](https://aws.amazon.com/cli/)
* [SAM CLI](https://github.com/awslabs/aws-sam-cli)
* [AWS Local](https://github.com/localstack/awscli-local)
* [SAM Local](https://github.com/localstack/aws-sam-cli-local)


## Building the project (optional)
You can build the Java project directly using the Maven wrapper if you wish. This is optional since samlocal build will build the java project automatically. 
```bash
./mvnw clean install
```

## Deploying and running on LocalStack
First, spin up LocalStack on Docker Compose
```bash
docker compose up
```
Then, in another tab, run: 
```bash
samlocal build
samlocal deploy --config-file=samconfig.toml --no-confirm-changeset
```
After running these steps, you should be presented with an endpoint you can use to test the API on local (the API ID in the URL will be different): 
```
CloudFormation outputs from deployed stack
------------------------------------------
Outputs                                                                                                              
------------------------------------------
Key                 LocalStackLambdaApiPing                                                                          
Description         URL for Lambda application running on Localstack                                                 
Value               http://<REST_API>.execute-api.localhost.localstack.cloud:4566/Prod/ping  
```

```bash
curl http://xxxxxxx.execute-api.localhost.localstack.cloud:4566/Prod/ping | jq
```


## Testing locally with the SAM CLI

From the project root folder - where the `template.yml` file is located - start the API with the SAM CLI.

```bash
$ sam local start-api

...
Mounting com.amazonaws.serverless.archetypes.StreamLambdaHandler::handleRequest (java11) at http://127.0.0.1:3000/{proxy+} [OPTIONS GET HEAD POST PUT DELETE PATCH]
...
```

Using a new shell, you can send a test ping request to your API:

```bash
$ curl -s http://127.0.0.1:3000/ping | jq

{
    "pong": "Hello, World!"
}
``` 

## Deploying to AWS
To deploy the application in your AWS account, you can use the SAM CLI's guided deployment process and follow the instructions on the screen

```
$ sam deploy --guided
```

Once the deployment is completed, the SAM CLI will print out the stack's outputs, including the new application URL. You can use `curl` or a web browser to make a call to the URL

```
...
-------------------------------------------------------------------------------------------------------------
OutputKey-Description                        OutputValue
-------------------------------------------------------------------------------------------------------------
SpringBootLambdaApi - URL for application            https://xxxxxxxxxx.execute-api.us-west-2.amazonaws.com/Prod/pets
-------------------------------------------------------------------------------------------------------------
```

Copy the `OutputValue` into a browser or use curl to test your first request:

```bash
$ curl -s https://xxxxxxx.execute-api.us-west-2.amazonaws.com/Prod/ping | jq

{
    "pong": "Hello, World!"
}
```
