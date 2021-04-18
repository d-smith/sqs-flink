# sqs-flink

Flink connector - feed a flink app from messages in SQS

State: basic read in place, next steps will be a transformation step, 
and determining where the message should be deleted from the queue.

## misc

Create a queue

```
aws sqs create-queue --queue-name q1
```

Send a message to the queue

```console
aws sqs send-message  --queue-url https://sqs.us-east-1.amazonaws.com/427848627088/q1 --message-body "yo
```