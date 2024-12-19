package lakhan.patidar.runnerz.user;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;

public record User(
    Integer id,
    String name,
    String username,
    String email,
    Address address,
    String phone,
    String website,
    Company company
) {
}
