package nl.hu.bep2.casino.blackjack.data;

import nl.hu.bep2.casino.blackjack.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringGameRepository extends JpaRepository<Game, UUID> {
    Optional<Game> findById(UUID id);
    List<Game> findAllByUsername(String username);
}
