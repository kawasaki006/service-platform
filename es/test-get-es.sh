#!/usr/bin/env bash

HOST="https://192.168.56.10:9200"
USER="elastic"
PASS="ldCpAdLSaUsvvCqk*ALw"

echo "Fetching from es..."

curl -k -u "$USER:$PASS" \
    -X GET "$HOST/service/_search?pretty" \
    -H "Content-Type: application/json" \
    -d '
    {
      "query": {
        "match_all": {}
      },
      "size": 10
    }'

echo "Done!"