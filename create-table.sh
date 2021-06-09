#!/bin/bash

docker-compose up -d

aws dynamodb create-table \
  --endpoint-url http://localhost:8000 \
  --table-name person \
  --attribute-definitions AttributeName=id,AttributeType=S \
  --key-schema AttributeName=id,KeyType=HASH \
  --provisioned-throughput ReadCapacityUnits=10,WriteCapacityUnits=10 \
  --region us-east-1

aws dynamodb put-item \
  --endpoint-url http://localhost:8000 \
  --table-name person \
  --item '{ "id": {"S": "13b1d716-8605-48ce-a1e7-f6ea0bebfb77"}, "birthday": {"S":"1990-01-01T00:00:00.000Z"}, "createdAt": {"S":"2021-06-09T03:54:12.174Z"}, "email": {"S":"lucas@mitri.dev"}, "name": {"S":"Lucas"}, "taxpayerId": {"S":"01234567890"} }' \
  --region us-east-1