package tech.shunzi.testdev.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {

    private UserDto userDto;
    private String addressGuid;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getAddressGuid() {
        return addressGuid;
    }

    public void setAddressGuid(String addressGuid) {
        this.addressGuid = addressGuid;
    }
}
