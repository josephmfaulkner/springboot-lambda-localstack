#!/bin/bash

curl http://appapi.execute-api.localhost.localstack.cloud:4566/local/ping | jq
curl http://appapib.execute-api.localhost.localstack.cloud:4566/local/test | jq
