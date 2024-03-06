package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String registerDate;
    private String updatedDate;

    public String title;

    public String picture;
    public String gender;

    public String dateOfBirth;
    public String phone;
    public Location location;


}
