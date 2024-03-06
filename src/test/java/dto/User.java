package dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor @Builder
public class User {
    public String id;
    public String title;
    public String firstName;
    public String lastName;
    public String picture;
    public String gender;
    public String email;
    public String dateOfBirth;
    public String phone;
    public Location location;

    public String registerDate;
    public String updatedDate;

}
