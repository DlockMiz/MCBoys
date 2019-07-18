package com.mcboys.mcboys.repositories;
import com.mcboys.mcboys.models.McServer;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface McServerRepository extends CrudRepository<McServer, String>{
    McServer findByServerName(String serverName);
}
