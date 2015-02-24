package rms.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rms.server.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

}
