#!/bin/bash

STARTED_AT=$1

echo "Started At: $STARTED_AT" ## `startedAt` from github api contains meaning less time by concurrency restrictions.
CURRENT_TIME=$(date -u +"%Y-%m-%dT%H:%M:%SZ") ## `updatedAt` doesn't work as expected.
echo "Current Time: $CURRENT_TIME"

STARTED_SEC=$(date -d "$STARTED_AT" +%s)
CURRENT_SEC=$(date -d "$CURRENT_TIME" +%s)

echo "Started Seconds: $STARTED_SEC"
echo "Current Seconds: $CURRENT_SEC"

ELAPSED_SECONDS=$(($CURRENT_SEC - $STARTED_SEC))
ELAPSED_MINUTES=$(($ELAPSED_SECONDS / 60))
REMAINING_SECONDS=$(($ELAPSED_SECONDS % 60))

echo "Elapsed time: $ELAPSED_MINUTES minutes and $REMAINING_SECONDS seconds"

ELAPSED_TIME="${ELAPSED_MINUTES} min ${REMAINING_SECONDS} sec"
echo "elapsed_time=$ELAPSED_TIME" >> $GITHUB_OUTPUT
