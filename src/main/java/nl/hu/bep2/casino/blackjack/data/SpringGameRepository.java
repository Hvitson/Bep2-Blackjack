package nl.hu.bep2.casino.blackjack.data;

import nl.hu.bep2.casino.blackjack.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringGameRepository extends JpaRepository<Game, UUID> {
    Optional<Game> findById(UUID id);

//    @Query("select g from games g where g.username = :username")
//    UUID selectAllGamesFromUser(@Param("username") String username);
}
