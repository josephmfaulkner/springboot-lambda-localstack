#!/bin/bash
set -x #echo on

curl http://appapi.execute-api.localhost.localstack.cloud:4566/local/test | jq
curl http://appapib.execute-api.localhost.localstack.cloud:4566/local/test | jq
