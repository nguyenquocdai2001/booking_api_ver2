package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.exception.GenericResponseExceptionHandler;
import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.request.LoyaltyRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;

import java.util.Optional;
import java.util.UUID;

public interface ILoyaltyService {
    Optional<Loyalty> getLoyaltyByRank(String rank) throws NotFoundException, GenericResponseExceptionHandler;
    GenericResponse addNewLoyalty(Loyalty request) throws GenericResponseExceptionHandler;
    Optional<Loyalty> getLoyaltyByDiscount(int discount);
    Iterable<Loyalty> getAllLoyalty();
    GenericResponse updateLoyalty(UUID id, LoyaltyRequest request) throws NotFoundException, GenericResponseExceptionHandler;
    GenericResponse deleteLoyalty(UUID id) throws NotFoundException;
    Optional<Loyalty> getLoyaltyByPrice(double price) throws GenericResponseExceptionHandler;
    Loyalty getLoyaltyById(UUID id) throws NotFoundException;

}
