#!/usr/bin/env bash

HOST="https://192.168.56.10:9200"
USER="elastic"
PASS="ldCpAdLSaUsvvCqk*ALw"

echo "Creating test service index..."

curl -k -u "$USER:$PASS" \
     -X PUT "$HOST/service" \
     -H "Content-Type: application/json" \
     -d @create-service-index.json

echo "Done!"