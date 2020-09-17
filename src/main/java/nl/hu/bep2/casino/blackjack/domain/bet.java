package nl.hu.bep2.casino.blackjack.domain;

public class bet {
    private Long totalBet;
    private Long amount;

    private bet(){

    }

    public Long getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(Long totalBet) {
        this.totalBet = totalBet;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}


