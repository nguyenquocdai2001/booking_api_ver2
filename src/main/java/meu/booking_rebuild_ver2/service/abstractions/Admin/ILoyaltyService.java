package meu.booking_rebuild_ver2.service.abstractions.Admin;

import meu.booking_rebuild_ver2.exception.NotFoundException;
import meu.booking_rebuild_ver2.model.Admin.Loyalty;
import meu.booking_rebuild_ver2.request.LoyaltyRequest;
import meu.booking_rebuild_ver2.response.GenericResponse;

import java.util.Optional;
import java.util.UUID;

public interface ILoyaltyService {
    Optional<Loyalty> getLoyaltyByRank(String rank) throws NotFoundException;
    GenericResponse addNewLoyalty(Loyalty request);
    Optional<Loyalty> getLoyaltyByDiscount(int discount);
    Iterable<Loyalty> getAllLoyalty();
    GenericResponse updateLoyalty(UUID id, LoyaltyRequest request) throws NotFoundException;
    GenericResponse deleteLoyalty(UUID id) throws NotFoundException;
    Optional<Loyalty> getLoyaltyByPrice(double price);
}
