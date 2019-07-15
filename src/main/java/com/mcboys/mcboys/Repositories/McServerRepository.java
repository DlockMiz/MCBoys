package com.mcboys.mcboys.Repositories;
import com.mcboys.mcboys.Models.McServer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface McServerRepository extends CrudRepository<McServer, String>{}
