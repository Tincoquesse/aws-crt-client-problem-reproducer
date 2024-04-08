package org.acme.repositories

import jakarta.inject.Inject
import org.eclipse.microprofile.config.ConfigProvider
import org.junit.jupiter.api.BeforeAll
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.CreateTableRequest
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ProvisionedThroughput
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import java.net.URI

open class DynamoTestBase {

    @Inject
    lateinit var repo: DynamoRepository

    companion object {
        @JvmStatic
        @BeforeAll
        fun setup() {
            val client = initializeClient()

            client.createTable(
                CreateTableRequest.builder()
                    .attributeDefinitions(
                        AttributeDefinition.builder().attributeName("entityId").attributeType(ScalarAttributeType.S)
                            .build()
                    )

                    .keySchema(
                        KeySchemaElement.builder().attributeName("entityId").keyType(KeyType.HASH).build(),
                    )
                    .provisionedThroughput(
                        ProvisionedThroughput.builder()
                            .readCapacityUnits(10)
                            .writeCapacityUnits(10)
                            .build()
                    )
                    .tableName("testTable")
                    .build()
            )
        }

        private fun initializeClient(): DynamoDbClient {
            return DynamoDbClient.builder()
                .endpointOverride(URI.create(getConfigValue("quarkus.dynamodb.endpoint-override")))
                .region(Region.of(getConfigValue("quarkus.dynamodb.aws.region")))
                .credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                            getConfigValue("quarkus.dynamodb.aws.credentials.static-provider.access-key-id"),
                            getConfigValue("quarkus.dynamodb.aws.credentials.static-provider.secret-access-key")
                        )
                    )
                )
                .build()
        }

        private fun getConfigValue(propertyName: String): String {
            return ConfigProvider.getConfig().getConfigValue(propertyName).rawValue
        }
    }
}