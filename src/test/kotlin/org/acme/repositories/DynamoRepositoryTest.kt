package org.acme.repositories

import io.quarkus.test.junit.QuarkusTest
import org.acme.models.DynamoEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

@QuarkusTest
class DynamoRepositoryTest : DynamoTestBase() {

    @Test
    fun `Save new item in Dynamo DB`() {
        // given
        val item = DynamoEntity(entityId = "testId")

        // when & then
        val savedEntity = repo.updateItem(item)
            .await()
            .indefinitely()

        // then
        Assertions.assertEquals(savedEntity.entityId, "testId")
    }
}