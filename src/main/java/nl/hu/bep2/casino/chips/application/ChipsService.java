package nl.hu.bep2.casino.chips.application;

import nl.hu.bep2.casino.chips.data.Chips;
import nl.hu.bep2.casino.chips.data.SpringChipsRepository;
import nl.hu.bep2.casino.security.data.User;
import nl.hu.bep2.casino.security.data.SpringUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ChipsService {
    private final SpringUserRepository userRepository;
    private final SpringChipsRepository chipsRepository;

    public ChipsService(SpringUserRepository userRepository, SpringChipsRepository chipsRepository) {
        this.userRepository = userRepository;
        this.chipsRepository = chipsRepository;
    }

    public Optional<Chips> findBalance(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return this.chipsRepository.findByUser(user);
    }

    public void depositChips(String username, Long amount) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Chips chips = this.chipsRepository.findByUser(user)
            .orElse(new Chips(user, 0L));
        chips.deposit(amount);

        this.chipsRepository.save(chips);
    }

    public void withdrawChips(String username, Long amount) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        Chips chips = this.chipsRepository.findByUser(user)
                .orElse(new Chips(user, 0L));
        chips.withdraw(amount);
    }

    public List<Chips> findAllChipsDesc() {
        return chipsRepository.findByOrderByAmountDesc();
    }
}
