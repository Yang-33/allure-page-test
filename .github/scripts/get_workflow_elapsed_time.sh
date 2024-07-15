#!/bin/bash

RUN_ID=$1

echo "Run ID: $RUN_ID"

STARTED_AT=$(gh run view $RUN_ID --json startedAt | jq -r '.startedAt')
UPDATED_AT=$(gh run view $RUN_ID --json updatedAt | jq -r '.updatedAt')

echo "Started At: $STARTED_AT"
echo "Updated At: $UPDATED_AT"

STARTED_SEC=$(date -d "$STARTED_AT" +%s)
UPDATED_SEC=$(date -d "$UPDATED_AT" +%s)

echo "Started Seconds: $STARTED_SEC"
echo "Updated Seconds: $UPDATED_SEC"

ELAPSED_SECONDS=$(($UPDATED_SEC - $STARTED_SEC))
ELAPSED_MINUTES=$(($ELAPSED_SECONDS / 60))
REMAINING_SECONDS=$(($ELAPSED_SECONDS % 60))

echo "Elapsed time: $ELAPSED_MINUTES minutes and $REMAINING_SECONDS seconds"

ELAPSED_TIME="${ELAPSED_MINUTES} min ${REMAINING_SECONDS} sec"
echo "ELAPSED_TIME=$ELAPSED_TIME" >> $GITHUB_OUTPUT
