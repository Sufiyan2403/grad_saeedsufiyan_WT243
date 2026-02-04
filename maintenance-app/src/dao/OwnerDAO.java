package dao;

import java.util.List;
import model.Owner;

public interface OwnerDAO{
    int createOwner(Owner owner);

    boolean updateOwner(Owner owner);

    boolean deleteOwner(int ownerId);

    Owner findById(int ownerId);

    Owner findByUserId(int userId);

    List<Owner> findAll();
}
