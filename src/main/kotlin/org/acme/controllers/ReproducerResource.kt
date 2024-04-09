package org.acme.controllers

import io.smallrye.mutiny.Uni
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.acme.models.ReproducerEntity
import org.acme.repositories.ReproducerRepository

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/reproducer")
class ReproducerResource(
    private val repository: ReproducerRepository
) {

    @PUT
    @Path("/test/{id}")
    fun saveItem(
        @PathParam("id") id: String
    ): Uni<ReproducerEntity> {
        val item = ReproducerEntity(entityId = id)
        return repository.updateItem(item)
    }
}