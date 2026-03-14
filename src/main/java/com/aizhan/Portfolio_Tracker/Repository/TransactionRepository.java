package com.aizhan.Portfolio_Tracker.Repository;
import com.aizhan.Portfolio_Tracker.Entity.Portfolio;
import com.aizhan.Portfolio_Tracker.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findByPortfolioOrderByTimestampDesc(Portfolio portfolio);
    List<Transaction> findByPortfolioIdOrderByTimestampDesc(Long portfolioId);
}