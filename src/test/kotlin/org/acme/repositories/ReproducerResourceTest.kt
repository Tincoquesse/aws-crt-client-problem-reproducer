package org.acme.repositories

import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.acme.models.ReproducerEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@QuarkusTest
class ReproducerResourceTest : DynamoTestBase() {

    @Test
    fun `Save item with given ID`() {
        When {
            put("/reproducer/test/testId")
        } Then {
            statusCode(200)
        } Extract {
            val response = body().`as`(ReproducerEntity::class.java)
            assertEquals(response.entityId, "testId")
        }
    }
}