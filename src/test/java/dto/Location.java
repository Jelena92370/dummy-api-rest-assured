package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Location {
    public String street;
    public String city;
    public String state;
    public String country;
    public String timezone;
}
