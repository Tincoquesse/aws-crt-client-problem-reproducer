package org.acme.models

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class DynamoEntity(
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("entityId")
    var entityId: String,
)
