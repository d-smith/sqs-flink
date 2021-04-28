# sqs-flink

Example of writing a Flink connector and sink for SQS messages.

## Sample job

The sample job (Streaning Job) shows how to use the connector and sink to read a message from q1 via the
connector, apply a filter to work on messages with an a attribute value of 'good', and change the 'type' attribute
value before writing the message via the sink to q2.

```console
15:14:40,912 INFO  org.ds.connector.sqs.SQSConnector                            [] - collected {MessageId: 3e08db3b-a135-4577-aaa2-e81abf67d944,ReceiptHandle: AQEB3Iu4UWaOM1PQQIuuh5kxDyd24MoYZ045nGpvCP727DtNXOvirONmlOHKJ4YazQIVBq91sF1ACy6r8FRq82xx8EhSnT7xj8YSBp/Vo4wQ5XfyulNYhVHD38uDhDsOJO8Bny52IvVSFJgPON/oeg2SxejmrLjmrWpCCQb2O/P3/mmZzInRlV8STaVCXI6oF5Bq9yGfVEb6JCMFbvsL3kKeaq3rg8PA6/UWCW1tXQnnalsTSBFXaWjgMhTtUdv2DiJN53L9XpxW+fcOdkU8uPvMc6ADzQfurEM5OPgNBKLu8GFGqSsJsD+RkZRXpoOeS4B7My7yA30ipV7U3hKMNGWX8QhBr7y/T7C0MCkQCtFgQ4sIfkyoVqszCVvzFUmitp63,MD5OfBody: 238e5f55e06f15574b4a14b8b0eac2db,Body: {"a":"good","type":"a","b":2},Attributes: {ApproximateReceiveCount=1, SentTimestamp=1619648080827, SenderId=AIDAIAMDTQDP35ANSQX7I, ApproximateFirstReceiveTimestamp=1619648080833},MessageAttributes: {}} from https://sqs.us-east-1.amazonaws.com/nnnnnnnnnnnn/q1
15:14:41,241 INFO  org.ds.xforms.EventTypeMapper                                [] - transforming type value to new-a
15:14:41,279 INFO  org.ds.xforms.AttributeValueFilter                           [] - tuple to filter is {a=good, b=2, type=a}
15:14:41,297 INFO  org.ds.connector.sqs.SQSSink                                 [] - write {"a":"good","b":2,"type":"new-a"} to https://sqs.us-east-1.amazonaws.com/nnnnnnnnnnnn/q2
15:14:41,760 INFO  org.ds.connector.sqs.SQSSink                                 [] - delete upstream sqs message
```

## misc

Create a queue

```
aws sqs create-queue --queue-name q1
```

Send a message to the queue

```console
aws sqs send-message  --queue-url https://sqs.us-east-1.amazonaws.com/nnnnnnnnnnnn/q1  --message-body '{"a":"good","type":"a","b":2}'
```