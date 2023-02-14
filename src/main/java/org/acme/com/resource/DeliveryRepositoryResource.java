package org.acme.com.resource;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.acme.com.model.Delivery;
import org.acme.com.repository.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("delivery")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DeliveryRepositoryResource {

    @Inject
    DeliveryRepository deliveryRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryRepositoryResource.class.getName());

    @GET
    public List<Delivery> get() {
        return deliveryRepository.listAll();
    }

    @GET
    @Path("{id}")
    public Delivery getSingle(Long id) {
      Delivery entity = deliveryRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Delivery with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Delivery delivery) {
        if (delivery.getQuantity() == 0) {
            throw new WebApplicationException("Delivery was invalidly set on request.", 422);
        }
        deliveryRepository.persist(delivery);
        return Response.ok(delivery).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Delivery update(Long id, Delivery delivery) {
        if (delivery.getStatus() == null) {
            throw new WebApplicationException("Delivery was not set on request.", 422);
        }

        Delivery entity = deliveryRepository.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Delivery with id of " + id + " does not exist.", 404);
        }

        entity.setStatus(delivery.getStatus());  
        
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Long id) {
      Delivery entity = deliveryRepository.findById(id);
      if (entity == null) {
          throw new WebApplicationException("Delivery with id of " + id + " does not exist.", 404);
      }

      deliveryRepository.delete(entity);
      return Response.status(204).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}