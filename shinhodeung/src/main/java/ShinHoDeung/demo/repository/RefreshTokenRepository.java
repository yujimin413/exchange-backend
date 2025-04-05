package ShinHoDeung.demo.repository;

import org.springframework.data.repository.CrudRepository;

import ShinHoDeung.demo.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
