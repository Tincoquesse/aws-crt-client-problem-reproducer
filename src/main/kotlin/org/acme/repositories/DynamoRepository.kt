package org.acme.repositories

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import org.acme.models.DynamoEntity
import org.eclipse.microprofile.config.inject.ConfigProperty
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@ApplicationScoped
class DynamoRepository(
    dynamoClient: DynamoDbEnhancedAsyncClient,
    @ConfigProperty(name = "aws.client.dynamo-table-name") tableName: String
) {
    private val tableSchema = TableSchema.fromClass(DynamoEntity::class.java)
    private val table = dynamoClient.table(tableName, tableSchema)

    fun updateItem(entity: DynamoEntity): Uni<DynamoEntity> {
        return Uni.createFrom().completionStage { table.updateItem(entity) }
    }
}